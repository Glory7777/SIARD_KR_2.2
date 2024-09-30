package ch.admin.bar.siard2.api.primary;

import ch.admin.bar.siard2.api.*;
import ch.admin.bar.siard2.api.Record;
import ch.admin.bar.siard2.api.generated.CategoryType;
import ch.admin.bar.siard2.api.generated.ObjectFactory;
import ch.admin.bar.siard2.api.generated.TableType;
import ch.admin.bar.siard2.api.generated.TablesType;
import ch.admin.bar.siard2.api.meta.MetaSchemaImpl;
import ch.admin.bar.siard2.api.meta.MetaTableImpl;
import ch.enterag.sqlparser.Interval;
import ch.enterag.sqlparser.SqlLiterals;
import ch.enterag.utils.BU;
import ch.enterag.utils.DU;
import ch.enterag.utils.SU;
import ch.enterag.utils.background.Progress;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import ch.enterag.utils.xml.XU;
//import org.apache.xalan.xsltc.trax.TransformerFactoryImpl;
import org.apache.xerces.jaxp.DocumentBuilderFactoryImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class TableImpl extends SearchImpl implements Table {
    public static final String _sTABLE_FOLDER_PREFIX = "table";
    private static final int iBUFFER_SIZE = 8192;
    private static final long lROWS_MAX_VALIDATE = 1024L;
    public static final String _sXML_NS = "xmlns";
    public static final String _sXSI_PREFIX = "xsi";
    public static final String _sTAG_SCHEMA_LOCATION = "schemaLocation";
    public static final String _sTAG_TABLE = "table";
    public static final String _sTAG_RECORD = "row";
    public static final String _sATTR_TABLE_VERSION = "version";
    private static final ObjectFactory _OF = new ObjectFactory();
    private long expectedTableSize;

    static DocumentBuilder _db = null;


    static DocumentBuilder getDocumentBuilder() throws IOException {
        try {
            if (_db == null) {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(DocumentBuilderFactoryImpl.class.getName(), null);

                dbf.setNamespaceAware(true);
                _db = dbf.newDocumentBuilder();
            }
        } catch (ParserConfigurationException pce) {
            throw new IOException("DocumentBuilder could not be created!", pce);
        }
        return _db;
    }

    private static Transformer _trans = null;


    private static Transformer getTransformer() throws IOException {
        try {
            if (_trans == null) {
//        TransformerFactory tf = TransformerFactory.newInstance(TransformerFactoryImpl.class.getName(), null);
                TransformerFactory tf = TransformerFactory.newInstance();
                _trans = tf.newTransformer();
                _trans.setOutputProperty("omit-xml-declaration", "no");
                _trans.setOutputProperty("method", "xml");
                _trans.setOutputProperty("indent", "yes");
                _trans.setOutputProperty("encoding", "UTF-8");
            }
        } catch (TransformerConfigurationException tce) {

            throw new IOException("Transformer could not be created!", tce);
        }
        return _trans;
    }


    private SortedTable _stable = null;

    public SortedTable getSortedTable() {
        return this._stable;
    }

    private Schema _schemaParent = null;

    public Schema getParentSchema() {
        return this._schemaParent;
    }

    private MetaTable _mt = null;
    private boolean _bCreating;

    public MetaTable getMetaTable() {
        return this._mt;
    }

    private ArchiveImpl getArchiveImpl() {
        return (ArchiveImpl) getParentSchema().getParentArchive();
    }


    String getTableFolder() {
        return ((SchemaImpl) getParentSchema()).getSchemaFolder() + getMetaTable().getFolder() + "/";
    }


    String getTableXsd() {
        return getTableFolder() + getMetaTable().getFolder() + ".xsd";
    }


    String getTableXml() {
        return getTableFolder() + getMetaTable().getFolder() + ".xml";
    }


    private void addElement(Element elParent, String sTag, MetaValue mv, boolean bNullable) throws IOException {
        Document doc = elParent.getOwnerDocument();

        Element elElement = doc.createElement("xs:element");
        elParent.appendChild(elElement);
        elElement.setAttribute("name", sTag);
        int iPreType = mv.getPreType();
        if (iPreType != 0 && mv.getMetaFields() == 0) {

            boolean bShort = (mv.getMaxLength() <= getArchiveImpl().getMaxInlineSize());
            String sXmlType = null;
            switch (iPreType) {
                case 1:
                    sXmlType = bShort ? "xs:string" : "clobType";
                    break;
                case 12:
                    sXmlType = bShort ? "xs:string" : "clobType";
                    break;
                case 70:
                    sXmlType = "blobType";
                    break;
                case 2005:
                    sXmlType = "clobType";
                    break;
                case -15:
                    sXmlType = bShort ? "xs:string" : "clobType";
                    break;
                case -9:
                    sXmlType = bShort ? "xs:string" : "clobType";
                    break;
                case 2011:
                    sXmlType = "clobType";
                    break;
                case -2:
                    sXmlType = bShort ? "xs:hexBinary" : "blobType";
                    break;
                case -3:
                    sXmlType = bShort ? "xs:hexBinary" : "blobType";
                    break;
                case 2004:
                    sXmlType = "blobType";
                    break;
                case 2:
                    sXmlType = "xs:decimal";
                    break;
                case 3:
                    sXmlType = "xs:decimal";
                    break;
                case 5:
                    sXmlType = "xs:integer";
                    break;
                case 4:
                    sXmlType = "xs:integer";
                    break;
                case -5:
                    sXmlType = "xs:integer";
                    break;
                case 6:
                    sXmlType = "xs:double";
                    break;
                case 7:
                    sXmlType = "xs:float";
                    break;
                case 8:
                    sXmlType = "xs:double";
                    break;
                case 16:
                    sXmlType = "xs:boolean";
                    break;
                case 91:
                    sXmlType = "dateType";
                    break;
                case 92:
                    sXmlType = "timeType";
                    break;
                case 93:
                    sXmlType = "dateTimeType";
                    break;
                case 2009:
                    sXmlType = "clobType";
                    break;
                case 1111:
                    sXmlType = "xs:duration";
                    break;
            }
            elElement.setAttribute("type", sXmlType);
            if (bNullable) {
                elElement.setAttribute("minOccurs", "0");
            }
        } else {

            elElement.setAttribute("minOccurs", "0");


            Element elComplexType = doc.createElement("xs:complexType");
            elElement.appendChild(elComplexType);


            Element elSequence = doc.createElement("xs:sequence");
            elComplexType.appendChild(elSequence);

            for (int iField = 0; iField < mv.getMetaFields(); iField++) {

                MetaField metaField = mv.getMetaField(iField);
                String sTagField = null;
                if (mv.getCardinality() > 0) {
                    sTagField = CellImpl.getElementTag(iField);
                } else {

                    CategoryType cat = mv.getMetaType().getCategoryType();
                    if (cat == CategoryType.UDT)
                        sTagField = CellImpl.getAttributeTag(iField);
                }
                addElement(elSequence, sTagField, metaField, true);
            }
        }
    }


    public void exportTableSchema(OutputStream osXsd) throws IOException {
        String sEntryName = getTableXsd();
        if (getArchiveImpl().existsFileEntry(sEntryName)) {


            byte[] buf = new byte[8192];
            InputStream is = getArchiveImpl().openFileEntry(sEntryName);
            int iRead;
            for (iRead = is.read(buf); iRead != -1; iRead = is.read(buf))
                osXsd.write(buf, 0, iRead);
            is.close();
        } else if (getMetaTable().getMetaColumns() > 0) {


            try {
                Class<ArchiveImpl> archiveClass = ArchiveImpl.class;
                InputStream isXsdTable = archiveClass.getResourceAsStream("/res/table.xsd");
                Document doc = getDocumentBuilder().parse(isXsdTable);

                Element elAny = (Element) doc.getElementsByTagName("xs:any").item(0);
                Element elSequence = (Element) elAny.getParentNode();
                XU.clearElement(elSequence);
                for (int iColumn = 0; iColumn < getMetaTable().getMetaColumns(); iColumn++) {

                    MetaColumn mc = getMetaTable().getMetaColumn(iColumn);
                    String sTag = CellImpl.getColumnTag(iColumn);
                    addElement(elSequence, sTag, mc, mc.isNullable());
                }

                getTransformer().transform(new DOMSource(doc), new StreamResult(osXsd));
                osXsd.close();
            } catch (SAXException se) {
                throw new IOException(se);
            } catch (TransformerConfigurationException tcfe) {
                throw new IOException(tcfe);
            } catch (TransformerException tfe) {
                throw new IOException(tfe);
            }

        } else {
            throw new IOException("Table contains no columns!");
        }
    }

    public boolean isEmpty() {
        boolean bEmpty = true;
        ArchiveImpl ai = getArchiveImpl();
        if (ai.getZipFile().getFileEntry(getTableXml()) != null)
            bEmpty = false;
        return bEmpty;
    }


    public boolean isValid() {
        boolean bValid = getMetaTable().isValid();
        if (bValid && getMetaTable().getMetaColumns() == 0)
            bValid = false;
        RecordDispenser rd = null;

        try {
            rd = openRecords();
            long lRowsValidate = Math.min(1024L, getMetaTable().getRows());
            rd.skip(lRowsValidate);
            if (lRowsValidate == getMetaTable().getRows() &&
                    rd.get() != null)
                bValid = false;
        } catch (IOException ie) {
            bValid = false;
        } finally {

            if (rd != null) {
                try {
                    rd.close();
                } catch (IOException iOException) {
                }
            }
        }
        return bValid;
    }


    public static Table newInstance(Schema schemaParent, String sName) throws IOException {
        Table table = new TableImpl(schemaParent, sName);
        return table;
    }


    public RecordDispenserImpl openRecords() throws IOException {
        return new RecordDispenserImpl(this);
    }

    private TableImpl(Schema schemaParent, String sName) throws IOException {
        this._bCreating = false;
        this._schemaParent = schemaParent;
        MetaSchemaImpl msi = (MetaSchemaImpl) getParentSchema().getMetaSchema();
        TablesType tts = msi.getSchemaType().getTables();
        if (tts == null) {
            tts = _OF.createTablesType();
            msi.getSchemaType().setTables(tts);
        }
        TableType tt = null;
        for (int iTable = 0; tt == null && iTable < tts.getTable().size(); iTable++) {
            TableType ttTry = tts.getTable().get(iTable);
            if (sName.equals(ttTry.getName()))
                tt = ttTry;
        }
        SchemaImpl si = (SchemaImpl) getParentSchema();
        if (tt == null) {
            String sFolder = "table" + tts.getTable().size();
            ArchiveImpl ai = getArchiveImpl();
//            ai.createFolderEntry(si.getSchemaFolder() + sFolder + "/");
            tt = MetaTableImpl.createTableType(sName, sFolder);
            tts.getTable().add(tt);
        }
        this._mt = MetaTableImpl.newInstance(this, tt);
        si.registerTable(sName, this);
    }

    public boolean isCreating() {
        return this._bCreating;
    }

    public void setCreating(boolean bCreating) {
        this._bCreating = bCreating;
    }


    public RecordRetainerImpl createRecords() throws IOException {
        return new RecordRetainerImpl(this);
    }


    public RecordExtract getRecordExtract() throws IOException {
        RecordExtract re = null;

        if (!getArchiveImpl().canModifyPrimaryData()) {
            re = RecordExtractImpl.newInstance(this);
        } else {
            throw new IOException("Records cannot be read if archive is open for modification!");
        }
        return re;
    }


    public void sort(boolean bAscending, int iSortColumn, Progress progress) throws IOException {
        SortedTable stable = this._stable;
        if (stable == null)
            stable = new SortedTableImpl();
        stable.sort(this, bAscending, iSortColumn, progress);
        this._stable = stable;
    }


    public boolean getAscending() {
        boolean bAscending = true;
        if (this._stable != null)
            bAscending = this._stable.getAscending();
        return bAscending;
    }


    public int getSortColumn() {
        int iSortColumn = -1;
        if (this._stable != null)
            iSortColumn = this._stable.getSortColumn();
        return iSortColumn;
    }


    private void writeLinkToLob(Writer wr, Value value, File folderLobs, String sFilename) throws IOException {
        URI uriAbsoluteFolder = value.getMetaValue().getAbsoluteLobFolder();
        if (uriAbsoluteFolder != null) {

            URI uriExternal = uriAbsoluteFolder.resolve(sFilename);
            sFilename = uriExternal.toURL().toString();

        } else if (folderLobs != null) {


            sFilename = "/" + folderLobs.getAbsolutePath().replace('\\', '/') + "/" + sFilename;
            File fileLob = new File(sFilename);
            fileLob.getParentFile().mkdirs();

            int iType = value.getMetaValue().getPreType();
            if (iType == -2 || iType == -3 || iType == 2004 || iType == 70) {


                InputStream is = value.getInputStream();
                if (is != null) {
                    FileOutputStream fosLob = new FileOutputStream(sFilename);
                    byte[] buf = new byte[8192];
                    int iRead;
                    for (iRead = is.read(buf); iRead != -1; iRead = is.read(buf))
                        fosLob.write(buf, 0, iRead);
                    fosLob.close();
                    is.close();
                }

            } else {

                Reader rdr = value.getReader();
                if (rdr != null) {

                    Writer fwLob = new FileWriter(fileLob);
                    char[] cbuf = new char[8192];
                    int iRead;
                    for (iRead = rdr.read(cbuf); iRead != -1; iRead = rdr.read(cbuf))
                        fwLob.write(cbuf, 0, iRead);
                    fwLob.close();
                    rdr.close();
                }
            }
        }

        wr.write("<a href=\"" + sFilename + "\">" + sFilename + "</a>");
    }


    private void writeUdtValue(Writer wr, Value value, File folderLobs) throws IOException {
        wr.write("<dl>\r\n");
        MetaValue mv = value.getMetaValue();
        for (int iAttribute = 0; iAttribute < value.getAttributes(); iAttribute++) {

            MetaField mf = mv.getMetaField(iAttribute);
            wr.write("  <dt>");
            wr.write(SU.toHtml(mf.getName()));
            wr.write("</dt>\r\n");
            wr.write("  <dd>");
            writeValue(wr, value.getAttribute(iAttribute), folderLobs);
            wr.write("</dd>\r\n");
        }
        wr.write("</dl>\r\n");
    }


    private void writeArrayValue(Writer wr, Value value, File folderLobs) throws IOException {
        wr.write("<ol>\r\n");
        for (int iElement = 0; iElement < value.getElements(); iElement++) {

            wr.write("  <li>");
            writeValue(wr, value.getElement(iElement), folderLobs);
            wr.write("</li>\r\n");
        }
        wr.write("</ol>\r\n");
    }


    private void writeValue(Writer wr, Value value, File folderLobs) throws IOException {
        DU du = DU.getInstance(Locale.getDefault().getLanguage(), (new SimpleDateFormat()).toPattern());
        if (!value.isNull()) {


            String sFilename = value.getFilename();
            if (sFilename != null) {
                writeLinkToLob(wr, value, folderLobs, sFilename);
            } else {

                MetaValue mv = value.getMetaValue();
                MetaType mt = mv.getMetaType();
                if (mt != null && mt.getCategoryType() == CategoryType.UDT) {
                    writeUdtValue(wr, value, folderLobs);
                } else if (mv.getCardinality() > 0) {
                    writeArrayValue(wr, value, folderLobs);
                } else {

                    String sText = null;
                    int iType = mv.getPreType();
                    switch (iType) {

                        case -15:
                        case -9:
                        case 1:
                        case 12:
                        case 70:
                        case 2005:
                        case 2009:
                        case 2011:
                            sText = value.getString();
                            break;
                        case -3:
                        case -2:
                        case 2004:
                            sText = "0x" + BU.toHex(value.getBytes());
                            break;
                        case 2:
                        case 3:
                            sText = value.getBigDecimal().toPlainString();
                            break;
                        case 5:
                            sText = value.getInt().toString();
                            break;
                        case 4:
                            sText = value.getLong().toString();
                            break;
                        case -5:
                            sText = value.getBigInteger().toString();
                            break;
                        case 6:
                        case 8:
                            sText = value.getDouble().toString();
                            break;
                        case 7:
                            sText = value.getFloat().toString();
                            break;
                        case 16:
                            sText = value.getBoolean().toString();
                            break;
                        case 91:
                            sText = du.fromSqlDate(value.getDate());
                            break;
                        case 92:
                            sText = du.fromSqlTime(value.getTime());
                            break;
                        case 93:
                            sText = du.fromSqlTimestamp(value.getTimestamp());
                            break;
                        case 1111:
                            sText = SqlLiterals.formatIntervalLiteral(Interval.fromDuration(value.getDuration()));
                            break;
                    }
                    wr.write(SU.toHtml(sText));
                }
            }
        }
    }


    public void exportAsHtml(OutputStream os, File folderLobs) throws IOException {
        OutputStreamWriter oswr = new OutputStreamWriter(os, StandardCharsets.UTF_8);
        MetaTable mt = getMetaTable();
        oswr.write("<!DOCTYPE html>\r\n");
        oswr.write("<html lang=\"en\">\r\n");
        oswr.write("  <head>\r\n");
        oswr.write("    <title>" + SU.toHtml(mt.getName()) + "</title>\r\n");
        oswr.write("    <meta charset=\"utf-8\" />\r\n");
        oswr.write("  </head>\r\n");
        oswr.write("  <body>\r\n");
        oswr.write("    <table>\r\n");
        oswr.write("      <tr>\r\n");
        for (int iColumn = 0; iColumn < mt.getMetaColumns(); iColumn++) {

            oswr.write("        <th>");
            oswr.write(SU.toHtml(mt.getMetaColumn(iColumn).getName()));
            oswr.write("</th>\r\n");
        }
        oswr.write("      </tr>\r\n");
        RecordDispenser rd = openRecords();
        long lRow;
        for (lRow = 0L; lRow < getMetaTable().getRows(); lRow++) {

            oswr.write("      <tr>\r\n");
            Record record = rd.get();
            for (int i = 0; i < record.getCells(); i++) {

                oswr.write("        <td>");
                Cell cell = record.getCell(i);
                writeValue(oswr, cell, folderLobs);
                oswr.write("</td>\r\n");
            }
            oswr.write("      </tr>\r\n");
        }
        rd.close();
        oswr.write("    </table>\r\n");
        oswr.write("  </body>\r\n");
        oswr.write("</html>\r\n");
        oswr.flush();
    }

    @Override
    public void setExpectedTableSize(long expectedTableSize) {
        this.expectedTableSize = expectedTableSize;
    }

    @Override
    public long getExpectedTableSize() {
        return expectedTableSize;
    }
}