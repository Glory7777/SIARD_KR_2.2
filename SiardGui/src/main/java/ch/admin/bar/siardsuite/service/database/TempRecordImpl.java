//package ch.admin.bar.siardsuite.service.database;
//
//import ch.admin.bar.siard2.api.Cell;
//import ch.admin.bar.siard2.api.Record;
//import ch.admin.bar.siard2.api.Table;
//import ch.admin.bar.siard2.api.Value;
//import ch.admin.bar.siard2.api.generated.table.ObjectFactory;
//import ch.admin.bar.siard2.api.generated.table.RecordType;
//import ch.admin.bar.siard2.api.meta.MetaColumnImpl;
//import ch.admin.bar.siard2.api.primary.CellImpl;
//import ch.admin.bar.siard2.api.primary.RecordImpl;
//import ch.admin.bar.siard2.api.primary.TableImpl;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//
//import javax.xml.parsers.DocumentBuilder;
//import java.io.IOException;
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class TempRecordImpl implements Record {
//
//    private static Document _doc = null;
//    private URI _uriTemporaryLobFolder = null;
//    private ObjectFactory _of = new ObjectFactory();
//    private Map<String, Cell> _mapCells = null;
//    private Table _tableParent = null;
//    private long _lRecord = -1L;
//    private RecordType _rt = null;
//
//    public static Document getDocument() throws IOException {
//        if (_doc == null) {
//            DocumentBuilder db = CustomTableImpl.getDocumentBuilder();
//            _doc = db.newDocument();
//        }
//
//        return _doc;
//    }
//
//    public URI getTemporaryLobFolder() {
//        return this._uriTemporaryLobFolder;
//    }
//
//    private Map<String, Cell> getCellMap() throws IOException {
//        if (this._mapCells == null) {
//            this._mapCells = new HashMap();
//
//            for(int iColumn = 0; iColumn < this.getParentTable().getMetaTable().getMetaColumns(); ++iColumn) {
//                MetaColumnImpl mci = (MetaColumnImpl)this.getParentTable().getMetaTable().getMetaColumn(iColumn);
//                String sColumnTag = CellImpl.getColumnTag(iColumn);
//                if (this._mapCells.get(sColumnTag) == null) {
//                    this._mapCells.put(sColumnTag, CellImpl.newInstance(this, iColumn, mci, (Element)null));
//                }
//            }
//        }
//
//        return this._mapCells;
//    }
//
//    public long getRecord() {
//        return this._lRecord;
//    }
//
//    public Table getParentTable() {
//        return this._tableParent;
//    }
//
//    private void setRecordType(RecordType rt) throws IOException {
//        for(int iColumn = 0; iColumn < rt.getAny().size(); ++iColumn) {
//            Element elCell = (Element)rt.getAny().get(iColumn);
//            String sTag = elCell.getLocalName();
//            int iIndex = CellImpl.getIndex(elCell.getLocalName());
//            MetaColumnImpl mc = (MetaColumnImpl)this.getParentTable().getMetaTable().getMetaColumn(iIndex);
//            Cell cell = CellImpl.newInstance(this, iIndex, mc, elCell);
//            this.getCellMap().put(sTag, cell);
//        }
//
//        this._rt = rt;
//    }
//
//    RecordType getRecordType() throws IOException {
//        this._rt.getAny().clear();
//
//        for(int iColumn = 0; iColumn < this.getParentTable().getMetaTable().getMetaColumns(); ++iColumn) {
//            Cell cell = (Cell)this.getCellMap().get(CellImpl.getColumnTag(iColumn));
//            if (!cell.isNull()) {
//                Element elCell = ((CellImpl)cell).getValue();
//                if (elCell != null) {
//                    this._rt.getAny().add(elCell);
//                }
//            }
//        }
//
//        return this._rt;
//    }
//
//    private TempRecordImpl(Table tableParent, long lRecord, URI uriTemporaryLobFolder) throws IOException {
//        this._tableParent = tableParent;
//        this._lRecord = lRecord;
//        this._uriTemporaryLobFolder = uriTemporaryLobFolder;
//        this.setRecordType(this._of.createRecordType());
//    }
//
//    public static Record newInstance(Table tableParent, long lRecord, URI uriTemporaryLobFolder) throws IOException {
//        return new TempRecordImpl(tableParent, lRecord, uriTemporaryLobFolder);
//    }
//
//    private TempRecordImpl(Table tableParent, long lRecord, RecordType rt) throws IOException {
//        this._tableParent = tableParent;
//        this._lRecord = lRecord;
//        if (rt.getAny().size() > 0) {
//            Element el = (Element)rt.getAny().get(0);
//            if (el.getOwnerDocument() != getDocument()) {
//                _doc = el.getOwnerDocument();
//            }
//        }
//
//        this.setRecordType(rt);
//    }
//
//    public static Record newInstance(Table tableParent, long lRecord, RecordType rt) throws IOException {
//        return new TempRecordImpl(tableParent, lRecord, rt);
//    }
//
//    public int getCells() throws IOException {
//        return this.getCellMap().size();
//    }
//
//    public Cell getCell(int iCell) throws IOException {
//        String sTag = CellImpl.getColumnTag(iCell);
//        Cell cell = (Cell)this.getCellMap().get(sTag);
//        return cell;
//    }
//
//    public List<Value> getValues(boolean bSupportsArrays, boolean bSupportsUdts) throws IOException {
//        List<Value> listValues = new ArrayList();
//
//        for(int iCell = 0; iCell < this.getCells(); ++iCell) {
//            listValues.addAll(this.getCell(iCell).getValues(bSupportsArrays, bSupportsUdts));
//        }
//
//        return listValues;
//    }
//}
