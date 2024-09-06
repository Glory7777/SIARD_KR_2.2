package ch.admin.bar.siardsuite.service.database;

import ch.admin.bar.siard2.api.*;
import ch.admin.bar.siard2.api.Record;
import ch.admin.bar.siard2.api.generated.CategoryType;
import ch.admin.bar.siard2.api.generated.ObjectFactory;
import ch.admin.bar.siard2.api.generated.TableType;
import ch.admin.bar.siard2.api.generated.TablesType;
import ch.admin.bar.siard2.api.meta.MetaSchemaImpl;
import ch.admin.bar.siard2.api.meta.MetaTableImpl;
import ch.admin.bar.siard2.api.primary.*;
import ch.enterag.sqlparser.Interval;
import ch.enterag.sqlparser.SqlLiterals;
import ch.enterag.utils.BU;
import ch.enterag.utils.DU;
import ch.enterag.utils.SU;
import ch.enterag.utils.background.Progress;
import ch.enterag.utils.xml.XU;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class CustomTableImpl extends SearchImpl implements Table {
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
    static DocumentBuilder _db = null;
    private static Transformer _trans = null;
    private SortedTable _stable = null;
    private Schema _schemaParent = null;
    private MetaTable _mt = null;
    private boolean _bCreating = false;

    static DocumentBuilder getDocumentBuilder() throws IOException {
        try {
            if (_db == null) {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(DocumentBuilderFactoryImpl.class.getName(), (ClassLoader)null);
                dbf.setNamespaceAware(true);
                _db = dbf.newDocumentBuilder();
            }
        } catch (ParserConfigurationException var1) {
            ParserConfigurationException pce = var1;
            throw new IOException("DocumentBuilder could not be created!", pce);
        }

        return _db;
    }

    private static Transformer getTransformer() throws IOException {
        try {
            if (_trans == null) {
                TransformerFactory tf = TransformerFactory.newInstance(TransformerFactoryImpl.class.getName(), (ClassLoader)null);
                _trans = tf.newTransformer();
                _trans.setOutputProperty("omit-xml-declaration", "no");
                _trans.setOutputProperty("method", "xml");
                _trans.setOutputProperty("indent", "yes");
                _trans.setOutputProperty("encoding", "UTF-8");
            }
        } catch (TransformerConfigurationException var1) {
            TransformerConfigurationException tce = var1;
            throw new IOException("Transformer could not be created!", tce);
        }

        return _trans;
    }

    public SortedTable getSortedTable() {
        return this._stable;
    }

    public Schema getParentSchema() {
        return this._schemaParent;
    }

    public MetaTable getMetaTable() {
        return this._mt;
    }

    private ArchiveImpl getArchiveImpl() {
        return (ArchiveImpl)this.getParentSchema().getParentArchive();
    }

    String getTableFolder() {
        return ((TempSchemaImpl)this.getParentSchema()).getSchemaFolder() + this.getMetaTable().getFolder() + "/";
    }

    String getTableXsd() {
        return this.getTableFolder() + this.getMetaTable().getFolder() + ".xsd";
    }

    String getTableXml() {
        return this.getTableFolder() + this.getMetaTable().getFolder() + ".xml";
    }

    private void addElement(Element elParent, String sTag, MetaValue mv, boolean bNullable) throws IOException {
        Document doc = elParent.getOwnerDocument();
        Element elElement = doc.createElement("xs:element");
        elParent.appendChild(elElement);
        elElement.setAttribute("name", sTag);
        int iPreType = mv.getPreType();
        if (iPreType != 0 && mv.getMetaFields() == 0) {
            boolean bShort = mv.getMaxLength() <= (long)this.getArchiveImpl().getMaxInlineSize();
            String sXmlType = null;
            switch (iPreType) {
                case -15:
                    sXmlType = bShort ? "xs:string" : "clobType";
                    break;
                case -9:
                    sXmlType = bShort ? "xs:string" : "clobType";
                    break;
                case -5:
                    sXmlType = "xs:integer";
                    break;
                case -3:
                    sXmlType = bShort ? "xs:hexBinary" : "blobType";
                    break;
                case -2:
                    sXmlType = bShort ? "xs:hexBinary" : "blobType";
                    break;
                case 1:
                    sXmlType = bShort ? "xs:string" : "clobType";
                    break;
                case 2:
                    sXmlType = "xs:decimal";
                    break;
                case 3:
                    sXmlType = "xs:decimal";
                    break;
                case 4:
                    sXmlType = "xs:integer";
                    break;
                case 5:
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
                case 12:
                    sXmlType = bShort ? "xs:string" : "clobType";
                    break;
                case 16:
                    sXmlType = "xs:boolean";
                    break;
                case 70:
                    sXmlType = "blobType";
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
                case 1111:
                    sXmlType = "xs:duration";
                    break;
                case 2004:
                    sXmlType = "blobType";
                    break;
                case 2005:
                    sXmlType = "clobType";
                    break;
                case 2009:
                    sXmlType = "clobType";
                    break;
                case 2011:
                    sXmlType = "clobType";
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

            for(int iField = 0; iField < mv.getMetaFields(); ++iField) {
                MetaValue mvField = mv.getMetaField(iField);
                String sTagField = null;
                if (mv.getCardinality() > 0) {
                    sTagField = CellImpl.getElementTag(iField);
                } else {
                    CategoryType cat = mv.getMetaType().getCategoryType();
                    if (cat == CategoryType.UDT) {
                        sTagField = CellImpl.getAttributeTag(iField);
                    }
                }

                this.addElement(elSequence, sTagField, mvField, true);
            }
        }

    }

    public void exportTableSchema(OutputStream osXsd) throws IOException {
        String sEntryName = this.getTableXsd();
        if (this.getArchiveImpl().existsFileEntry(sEntryName)) {
            byte[] buf = new byte[8192];
            InputStream is = this.getArchiveImpl().openFileEntry(sEntryName);

            for(int iRead = is.read(buf); iRead != -1; iRead = is.read(buf)) {
                osXsd.write(buf, 0, iRead);
            }

            is.close();
        } else {
            if (this.getMetaTable().getMetaColumns() <= 0) {
                throw new IOException("Table contains no columns!");
            }

            try {
                InputStream isXsdTable = ArchiveImpl.class.getResourceAsStream("/ch/admin/bar/siard2/api/res/table.xsd");
                Document doc = getDocumentBuilder().parse(isXsdTable);
                Element elAny = (Element)doc.getElementsByTagName("xs:any").item(0);
                Element elSequence = (Element)elAny.getParentNode();
                XU.clearElement(elSequence);

                for(int iColumn = 0; iColumn < this.getMetaTable().getMetaColumns(); ++iColumn) {
                    MetaColumn mc = this.getMetaTable().getMetaColumn(iColumn);
                    String sTag = CellImpl.getColumnTag(iColumn);
                    this.addElement(elSequence, sTag, mc, mc.isNullable());
                }

                getTransformer().transform(new DOMSource(doc), new StreamResult(osXsd));
                osXsd.close();
            } catch (SAXException var10) {
                throw new IOException(var10);
            } catch (TransformerConfigurationException var11) {
                throw new IOException(var11);
            } catch (TransformerException var12) {
                throw new IOException(var12);
            }
        }

    }

    public boolean isEmpty() {
        boolean bEmpty = true;
        ArchiveImpl ai = this.getArchiveImpl();
        if (ai.getZipFile().getFileEntry(this.getTableXml()) != null) {
            bEmpty = false;
        }

        return bEmpty;
    }

    public boolean isValid() {
        boolean bValid = this.getMetaTable().isValid();
        if (bValid && this.getMetaTable().getMetaColumns() == 0) {
            bValid = false;
        }

        RecordDispenser rd = null;

        try {
            rd = this.openRecords();
            long lRowsValidate = Math.min(1024L, this.getMetaTable().getRows());
            rd.skip(lRowsValidate);
            if (lRowsValidate == this.getMetaTable().getRows() && rd.get() != null) {
                bValid = false;
            }
        } catch (IOException var13) {
            bValid = false;
        } finally {
            if (rd != null) {
                try {
                    rd.close();
                } catch (IOException var12) {
                }
            }

        }

        return bValid;
    }

    private CustomTableImpl(Schema schemaParent, String sName) throws IOException {
        this._schemaParent = schemaParent;
        MetaSchemaImpl msi = (MetaSchemaImpl)this.getParentSchema().getMetaSchema();
        TablesType tts = msi.getSchemaType().getTables();
        if (tts == null) {
            tts = _OF.createTablesType();
            msi.getSchemaType().setTables(tts);
        }

        TableType tt = null;

        for(int iTable = 0; tt == null && iTable < tts.getTable().size(); ++iTable) {
            TableType ttTry = (TableType)tts.getTable().get(iTable);
            if (sName.equals(ttTry.getName())) {
                tt = ttTry;
            }
        }

        TempSchemaImpl si = (TempSchemaImpl)this.getParentSchema();
        if (tt == null) {
            String sFolder = "table" + String.valueOf(tts.getTable().size());
            ArchiveImpl ai = this.getArchiveImpl();
            ai.createFolderEntry(si.getSchemaFolder() + sFolder + "/");
            tt = MetaTableImpl.createTableType(sName, sFolder);
            tts.getTable().add(tt);
        }

        this._mt = MetaTableImpl.newInstance(this, tt);
        si.registerTable(sName, this);
    }

    public static Table newInstance(Schema schemaParent, String sName) throws IOException {
        Table table = new CustomTableImpl(schemaParent, sName);
        return table;
    }

    public RecordDispenserImpl openRecords() throws IOException {
        return new RecordDispenserImpl(this);
    }

    public boolean isCreating() {
        return this._bCreating;
    }

    public void setCreating(boolean bCreating) {
        this._bCreating = bCreating;
    }

    public TempRecordRetainerImpl createRecords() throws IOException {
        return new TempRecordRetainerImpl(this);
    }

    public RecordExtract getRecordExtract() throws IOException {
        RecordExtract re = null;
        if (!this.getArchiveImpl().canModifyPrimaryData()) {
            re = RecordExtractImpl.newInstance(this);
            return re;
        } else {
            throw new IOException("Records cannot be read if archive is open for modification!");
        }
    }

    public void sort(boolean bAscending, int iSortColumn, Progress progress) throws IOException {
        SortedTable stable = this._stable;
        if (stable == null) {
            stable = new SortedTableImpl();
        }

        ((SortedTable)stable).sort(this, bAscending, iSortColumn, progress);
        this._stable = (SortedTable)stable;
    }

    public boolean getAscending() {
        boolean bAscending = true;
        if (this._stable != null) {
            bAscending = this._stable.getAscending();
        }

        return bAscending;
    }

    public int getSortColumn() {
        int iSortColumn = -1;
        if (this._stable != null) {
            iSortColumn = this._stable.getSortColumn();
        }

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
            int iRead;
            if (iType != -2 && iType != -3 && iType != 2004 && iType != 70) {
                Reader rdr = value.getReader();
                if (rdr != null) {
                    Writer fwLob = new FileWriter(fileLob);
                    char[] cbuf = new char[8192];

                    for(iRead = rdr.read(cbuf); iRead != -1; iRead = rdr.read(cbuf)) {
                        ((Writer)fwLob).write(cbuf, 0, iRead);
                    }

                    ((Writer)fwLob).close();
                    rdr.close();
                }
            } else {
                InputStream is = value.getInputStream();
                if (is != null) {
                    FileOutputStream fosLob = new FileOutputStream(sFilename);
                    byte[] buf = new byte[8192];

                    for(iRead = is.read(buf); iRead != -1; iRead = is.read(buf)) {
                        fosLob.write(buf, 0, iRead);
                    }

                    fosLob.close();
                    is.close();
                }
            }
        }

        wr.write("<a href=\"" + sFilename + "\">" + sFilename + "</a>");
    }

    private void writeUdtValue(Writer wr, Value value, File folderLobs) throws IOException {
        wr.write("<dl>\r\n");
        MetaValue mv = value.getMetaValue();

        for(int iAttribute = 0; iAttribute < value.getAttributes(); ++iAttribute) {
            MetaField mf = mv.getMetaField(iAttribute);
            wr.write("  <dt>");
            wr.write(SU.toHtml(mf.getName()));
            wr.write("</dt>\r\n");
            wr.write("  <dd>");
            this.writeValue(wr, value.getAttribute(iAttribute), folderLobs);
            wr.write("</dd>\r\n");
        }

        wr.write("</dl>\r\n");
    }

    private void writeArrayValue(Writer wr, Value value, File folderLobs) throws IOException {
        wr.write("<ol>\r\n");

        for(int iElement = 0; iElement < value.getElements(); ++iElement) {
            wr.write("  <li>");
            this.writeValue(wr, value.getElement(iElement), folderLobs);
            wr.write("</li>\r\n");
        }

        wr.write("</ol>\r\n");
    }

    private void writeValue(Writer wr, Value value, File folderLobs) throws IOException {
        DU du = DU.getInstance(Locale.getDefault().getLanguage(), (new SimpleDateFormat()).toPattern());
        if (!value.isNull()) {
            String sFilename = value.getFilename();
            if (sFilename != null) {
                this.writeLinkToLob(wr, value, folderLobs, sFilename);
            } else {
                MetaValue mv = value.getMetaValue();
                MetaType mt = mv.getMetaType();
                if (mt != null && mt.getCategoryType() == CategoryType.UDT) {
                    this.writeUdtValue(wr, value, folderLobs);
                } else if (mv.getCardinality() > 0) {
                    this.writeArrayValue(wr, value, folderLobs);
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
                        case -5:
                            sText = value.getBigInteger().toString();
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
                        case 4:
                            sText = value.getLong().toString();
                            break;
                        case 5:
                            sText = value.getInt().toString();
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
                    }

                    wr.write(SU.toHtml(sText));
                }
            }
        }

    }

    public void exportAsHtml(OutputStream os, File folderLobs) throws IOException {
        OutputStreamWriter oswr = new OutputStreamWriter(os, "UTF-8");
        MetaTable mt = this.getMetaTable();
        oswr.write("<!DOCTYPE html>\r\n");
        oswr.write("<html lang=\"en\">\r\n");
        oswr.write("  <head>\r\n");
        oswr.write("    <title>" + SU.toHtml(mt.getName()) + "</title>\r\n");
        oswr.write("    <meta charset=\"utf-8\" />\r\n");
        oswr.write("  </head>\r\n");
        oswr.write("  <body>\r\n");
        oswr.write("    <table>\r\n");
        oswr.write("      <tr>\r\n");

        for(int iColumn = 0; iColumn < mt.getMetaColumns(); ++iColumn) {
            oswr.write("        <th>");
            oswr.write(SU.toHtml(mt.getMetaColumn(iColumn).getName()));
            oswr.write("</th>\r\n");
        }

        oswr.write("      </tr>\r\n");
        RecordDispenser rd = this.openRecords();

        for(long lRow = 0L; lRow < this.getMetaTable().getRows(); ++lRow) {
            oswr.write("      <tr>\r\n");
            Record record = rd.get();

            for(int iColumn = 0; iColumn < record.getCells(); ++iColumn) {
                oswr.write("        <td>");
                Cell cell = record.getCell(iColumn);
                this.writeValue(oswr, cell, folderLobs);
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

}
