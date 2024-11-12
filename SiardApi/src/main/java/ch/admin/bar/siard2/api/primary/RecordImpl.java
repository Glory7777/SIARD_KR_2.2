package ch.admin.bar.siard2.api.primary;

import ch.admin.bar.siard2.api.*;
import ch.admin.bar.siard2.api.Record;
import ch.admin.bar.siard2.api.generated.table.ObjectFactory;
import ch.admin.bar.siard2.api.generated.table.RecordType;
import ch.admin.bar.siard2.api.meta.MetaColumnImpl;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;

import ch.admin.bar.siard2.api.utli.SearchUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class RecordImpl implements Record {
    private static Document _doc = null;

    public static Document getDocument() throws IOException {
        if (_doc == null) {

            DocumentBuilder db = TableImpl.getDocumentBuilder();
            _doc = db.newDocument();
        }
        return _doc;
    }

    private URI _uriTemporaryLobFolder = null;

    public URI getTemporaryLobFolder() {
        return this._uriTemporaryLobFolder;
    }

    private final ObjectFactory _of = new ObjectFactory();
    private Map<String, Cell> _mapCells = null;
    private Map<String, Cell> getCurrentCellMap() {
        return this._mapCells == null ? new HashMap<>() : this._mapCells;
    }

    @Override
    public boolean containsInCell(String searchTerm) {
        Map<String, Cell> currentCellMap = this.getCurrentCellMap();
        return currentCellMap.values()
                .stream()
                .anyMatch(cell -> {
                    try {
                        String type = cell.getMetaValue().getType();
                        if (type.startsWith("BLOB")) return false;
                        String cellValue = cell.getString();
                        SearchUtil searchUtil = new SearchUtil(searchTerm);
                        return searchUtil.matches(cellValue);
                    } catch (IOException e) {
                        throw new RuntimeException("test failed");
                    }
                });
    }

    private Map<String, Cell> getCellMap() throws IOException {
        if (this._mapCells == null) {

            this._mapCells = new HashMap<>();

            for (int iColumn = 0; iColumn < getParentTable().getMetaTable().getMetaColumns(); iColumn++) {

                MetaColumnImpl mci = (MetaColumnImpl) getParentTable().getMetaTable().getMetaColumn(iColumn);
                String sColumnTag = CellImpl.getColumnTag(iColumn);
                if (this._mapCells.get(sColumnTag) == null)
                    this._mapCells.put(sColumnTag, CellImpl.newInstance(this, iColumn, mci, null));
            }
        }
        return this._mapCells;
    }

    private Table _tableParent = null;
    private long _lRecord = -1L;

    public long getRecord() {
        return this._lRecord;
    }

    public Table getParentTable() {
        return this._tableParent;
    }

    private RecordType _rt = null;


    private void setRecordType(RecordType rt) throws IOException {
        for (int iColumn = 0; iColumn < rt.getAny().size(); iColumn++) {

            Element elCell = (Element) rt.getAny().get(iColumn);
            String sTag = elCell.getLocalName();
            int iIndex = CellImpl.getIndex(elCell.getLocalName());
            MetaColumnImpl mc = (MetaColumnImpl) getParentTable().getMetaTable().getMetaColumn(iIndex);
            Cell cell = CellImpl.newInstance(this, iIndex, mc, elCell);
            getCellMap().put(sTag, cell);
        }
        this._rt = rt;
    }


    RecordType getRecordType() throws IOException {
        this._rt.getAny().clear();
        for (int iColumn = 0; iColumn < getParentTable().getMetaTable().getMetaColumns(); iColumn++) {

            Cell cell = getCellMap().get(CellImpl.getColumnTag(iColumn));
            if (!cell.isNull()) {

                Element elCell = ((CellImpl) cell).getValue();
                if (elCell != null)
                    this._rt.getAny().add(elCell);
            }
        }
        return this._rt;
    }


    private RecordImpl(Table tableParent, long lRecord, URI uriTemporaryLobFolder) throws IOException {
        this._tableParent = tableParent;
        this._lRecord = lRecord;
        this._uriTemporaryLobFolder = uriTemporaryLobFolder;
        setRecordType(this._of.createRecordType());
    }


    public static Record newInstance(Table tableParent, long lRecord, URI uriTemporaryLobFolder) throws IOException {
        return new RecordImpl(tableParent, lRecord, uriTemporaryLobFolder);
    }


    private RecordImpl(Table tableParent, long lRecord, RecordType rt) throws IOException {
        this._tableParent = tableParent;
        this._lRecord = lRecord;
        if (rt.getAny().size() > 0) {
            Element el = (Element) rt.getAny().get(0);
            String textContent = el.getTextContent();
//            System.out.println("textContent = " + textContent);
            if (el.getOwnerDocument() != getDocument())
                _doc = el.getOwnerDocument();
        }
        setRecordType(rt);
    }

    public static Record newInstance(Table tableParent, long lRecord, RecordType rt) throws IOException {
        return new RecordImpl(tableParent, lRecord, rt);
    }

    public int getCells() throws IOException {
        return getCellMap().size();
    }

    public Cell getCell(int iCell) throws IOException {
        String sTag = CellImpl.getColumnTag(iCell);
        Cell cell = getCellMap().get(sTag);
        return cell;
    }

    public List<Value> getValues(boolean bSupportsArrays, boolean bSupportsUdts) throws IOException {
        List<Value> listValues = new ArrayList<>();
        for (int iCell = 0; iCell < getCells(); iCell++)
            listValues.addAll(getCell(iCell).getValues(bSupportsArrays, bSupportsUdts));
        return listValues;
    }
}