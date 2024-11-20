package ch.admin.bar.siard2.api.primary;

import ch.admin.bar.siard2.api.Record;
import ch.admin.bar.siard2.api.RecordDispenser;
import ch.admin.bar.siard2.api.Table;
import ch.admin.bar.siard2.api.generated.table.ObjectFactory;
import ch.admin.bar.siard2.api.generated.table.RecordType;
import ch.admin.bar.siard2.api.utli.SearchUtil;
import ch.admin.bar.siard2.api.utli.ValidCellCounter;
import ch.enterag.utils.EU;
import ch.enterag.utils.jaxb.XMLStreamFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;


@Slf4j
public class RecordDispenserImpl implements RecordDispenser {
    private static final ObjectFactory _OF_TABLE = new ObjectFactory();
    private Table _table = null;
    private CountingInputStream _isXml = null;
    private final ValidCellCounter validCellCounter = new ValidCellCounter();
    private boolean anyMatches;

    InputStream getXmlInputStream() {
        return this._isXml;
    }

    private XMLStreamReader _xsr = null;

    XMLStreamReader getXmlStreamReader() {
        return this._xsr;
    }

    private long _lRecord = -1L;


    private class CountingInputStream extends InputStream {
        private InputStream _is = null;
        private long _lCount = 0L;

        public CountingInputStream(InputStream is) {
            this._is = is;
        }


        public int read() throws IOException {
            int iResult = this._is.read();
            if (iResult != -1)
                this._lCount++;
            return iResult;
        }


        public int read(byte[] buf) throws IOException {
            int iResult = this._is.read(buf);
            if (iResult != -1)
                this._lCount += iResult;
            return iResult;
        }


        public int read(byte[] buf, int iOffset, int iLength) throws IOException {
            int iResult = this._is.read(buf, iOffset, iLength);
            if (iResult != -1)
                this._lCount += iResult;
            return iResult;
        }


        public void close() throws IOException {
            this._is.close();
        }

        public long getByteCount() {
            return this._lCount;
        }
    }


    private ArchiveImpl getArchiveImpl() {
        return (ArchiveImpl) this._table.getParentSchema().getParentArchive();
    }


    XMLStreamReader readHeader(InputStream isXsd, InputStream isXml) throws IOException {
        XMLStreamReader xsr = null;


        try {
            if (isXsd != null) {

                xsr = XMLStreamFactory.createXMLStreamReader(isXsd, isXml);
                isXsd.close();
            } else {

                xsr = XMLStreamFactory.createXMLStreamReader(isXml);
            }
            xsr.nextTag();
            if (xsr.isStartElement() && "table".equals(xsr.getLocalName())) {
                xsr.nextTag();
            } else {
                throw new XMLStreamException("Root element <table> not found!");
            }
        } catch (XMLStreamException xse) {
            throw new IOException("XMLStreamReader cannot be created!", xse);
        }
        return xsr;
    }


    public RecordDispenserImpl(Table table) throws IOException {
        this._table = table;
        TableImpl ti = (TableImpl) table;
        if (!ti.isCreating()) {

            ArchiveImpl ai = getArchiveImpl();
            if (ti.getSortedTable() == null) {
                this._isXml = new CountingInputStream(ai.openFileEntry(ti.getTableXml()));
            } else {
                this._isXml = new CountingInputStream(ti.getSortedTable().open());
            }
            InputStream isXsd = ai.openFileEntry(ti.getTableXsd());
            this._xsr = readHeader(isXsd, this._isXml);
            this._lRecord = 0L;
        } else {

            throw new IOException("Table cannot be opened for reading!");
        }
    }


    private Element getRowElement(XMLStreamReader xsr, Document doc) throws XMLStreamException {
        Element elRow = null;
        String sTag = xsr.getLocalName();
        if (xsr.isStartElement() && sTag.equals("row")) {

            elRow = doc.createElementNS(xsr.getNamespaceURI(), xsr.getLocalName());
            List<Element> listElementsStack = new ArrayList<>();
            listElementsStack.add(elRow);
            xsr.next();
            for (; listElementsStack.size() > 0; xsr.next()) {
                Element elNew;
                int i;
                Element elParent;
                Text text;
                switch (xsr.getEventType()) {

                    case 1:
                        elNew = doc.createElementNS(xsr.getNamespaceURI(), xsr.getLocalName());
                        for (i = 0; i < xsr.getAttributeCount(); i++)
                            elNew.setAttribute(xsr.getAttributeLocalName(i), xsr.getAttributeValue(i));
                        listElementsStack.get(0).appendChild(elNew);
                        listElementsStack.add(0, elNew);
                        break;
                    case 2:
                        elParent = listElementsStack.get(0);
                        if (elParent.getTagName().equals(xsr.getLocalName())) {
                            listElementsStack.remove(elParent);
                            break;
                        }
                        System.err.println("Unexpected END ELEMENT!");
                        break;
                    case 4:
                        text = doc.createTextNode(xsr.getText());
                        listElementsStack.get(0).appendChild(text);
                        break;
                }


            }
        }
        return elRow;
    }


    private RecordType getRecordType(XMLStreamReader xsr) throws IOException, XMLStreamException {
        RecordType rt = null;
        String sNamespace = xsr.getNamespaceURI();
        rt = _OF_TABLE.createRecordType();
        Document doc = TableImpl.getDocumentBuilder().newDocument();
        Element elTable = doc.createElementNS(sNamespace, "table");
        doc.appendChild(elTable);
        elTable.setAttribute("xmlns", sNamespace);
        Element elRow = getRowElement(xsr, doc);
        elTable.appendChild(elRow);
        for (int i = 0; i < elRow.getChildNodes().getLength(); i++) {

            Node nodeChild = elRow.getChildNodes().item(i);
            if (nodeChild.getNodeType() == 1) {

                Element elColumn = (Element) nodeChild;
                rt.getAny().add(elColumn);
            }
        }
        if (!xsr.isStartElement() && !xsr.isEndElement())
            xsr.nextTag();
        return rt;
    }

    private RecordType getRecordType(XMLStreamReader xsr, final String searchTerm) throws IOException, XMLStreamException {
        RecordType rt = null;
        String sNamespace = xsr.getNamespaceURI();
        rt = _OF_TABLE.createRecordType();
        Document doc = TableImpl.getDocumentBuilder().newDocument();
        Element elTable = doc.createElementNS(sNamespace, "table");
        doc.appendChild(elTable);
        elTable.setAttribute("xmlns", sNamespace);
        Element elRow = getRowElement(xsr, doc);
        elTable.appendChild(elRow);

        if (containsSearchTerm(elRow, searchTerm)) {
            for (int i = 0; i < elRow.getChildNodes().getLength(); i++) {

                Node nodeChild = elRow.getChildNodes().item(i);
                if (nodeChild.getNodeType() == 1) {

                    Element elColumn = (Element) nodeChild;
                    rt.getAny().add(elColumn);
                }
            }

            if (!xsr.isStartElement() && !xsr.isEndElement()) xsr.nextTag();
        }

        return rt;
    }

    private boolean containsSearchTerm(Element elRow, String searchTerm) {
        SearchUtil searchUtil = new SearchUtil(searchTerm);
        this.anyMatches = false;
        for (int i = 0; i < elRow.getChildNodes().getLength(); i++) {
            Node nodeChild = elRow.getChildNodes().item(i);
            if (nodeChild.getNodeType() == 1) {
                Element elColumn = (Element) nodeChild;
                String textContent = elColumn.getTextContent();
                if (searchUtil.matches(textContent)) {
                    this.anyMatches = true;
                    break;
                }
            }
        }
        return this.anyMatches;
    }

    Record readRecord(XMLStreamReader xsr) throws IOException, XMLStreamException {
        Record record = null;
        if (xsr.isStartElement() && "row".equals(xsr.getLocalName())) {

            RecordType rt = getRecordType(xsr);
            record = RecordImpl.newInstance(this._table, getPosition(), rt);
        } else {

            throw new IOException("Unexpected tag " + xsr.getLocalName() + " encountered");
        }
        return record;
    }

    Record readRecordWithSearchTerm(XMLStreamReader xsr, final String searchTerm) throws IOException, XMLStreamException {
        Record record = null;
        if (xsr.isStartElement() && "row".equals(xsr.getLocalName())) {
            RecordType rt = getRecordType(xsr, searchTerm);
            record = RecordImpl.newInstance(this._table, getPosition(), rt);
            record.getParentTable().setSearchAction();
            if (!rt.getAny().isEmpty()) validCellCounter.addCountAndUpdateTable(record);
        } else {
            throw new IOException("Unexpected tag " + xsr.getLocalName() + " encountered");
        }
        return record;
    }


    public Record get() throws IOException {
        Record record = null;
        try {
            if (this._lRecord < this._table.getMetaTable().getRows()) {
                record = readRecord(this._xsr);
                this._lRecord++;
            }
        } catch (XMLStreamException xse) {
            throw new IOException("Record " + this._lRecord + " cannot be read!", xse);
        }
        return record;
    }

    public Record getWithSearchTerm(final String searchTerm) throws IOException {
        if (searchTerm == null || searchTerm.isEmpty()) return get();

        Record record = null;

        try {
            if (this._lRecord < this._table.getMetaTable().getRows()) {

                record = readRecordWithSearchTerm(this._xsr, searchTerm);
                this._lRecord++;
            }
        } catch (XMLStreamException xse) {
            throw new IOException("Record " + this._lRecord + " cannot be read!", xse);
        }
        return record;
    }


    private long skip(long lSkip, String sTag) throws XMLStreamException {
        long lSkipped = 0L;
        boolean bContinue = (this._xsr.isStartElement() && sTag.equals(this._xsr.getLocalName()));
        while (bContinue && lSkipped < lSkip) {

            this._xsr.next();
            if (this._xsr.isEndElement() && sTag.equals(this._xsr.getLocalName())) {

                lSkipped++;
                this._xsr.nextTag();
                bContinue = (this._xsr.isStartElement() && sTag.equals(this._xsr.getLocalName()));
            }
        }
        return lSkipped;
    }


    public void skip(long lSkip) throws IOException {

        try {
            if (this._lRecord + lSkip > this._table.getMetaTable().getRows())
                lSkip = this._table.getMetaTable().getRows() - this._lRecord;
            if (lSkip == skip(lSkip, "row")) {
                this._lRecord += lSkip;
            } else {
                throw new IOException("Unexpected end of records encountered!");
            }
        } catch (XMLStreamException xse) {
            throw new IOException(lSkip + " records starting with " + this._lRecord + " could not be skipped (" + EU.getExceptionMessage(xse) + ")!");
        }

    }


    public void close() throws IOException {

        try {
            if (this._xsr != null) {

                this._xsr.close();
                this._isXml.close();
                this._xsr = null;
            } else {

                throw new IOException("Table records have not been been opened!");
            }
        } catch (XMLStreamException xse) {
            throw new IOException("XMLStreamReader could not be closed!", xse);
        }
        this._lRecord = -1L;
    }


    public long getPosition() {
        return this._lRecord;
    }


    public long getByteCount() {
        return this._isXml.getByteCount();
    }

    @Override
    public boolean anyMatches() {
        return this.anyMatches;
    }
}
