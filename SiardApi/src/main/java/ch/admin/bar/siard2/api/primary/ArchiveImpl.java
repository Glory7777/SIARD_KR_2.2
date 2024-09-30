package ch.admin.bar.siard2.api.primary;

import ch.admin.bar.siard2.api.*;
import ch.admin.bar.siard2.api.generated.*;
import ch.admin.bar.siard2.api.meta.MetaDataImpl;
import ch.admin.bar.siard2.api.meta.MetaSchemaImpl;
import ch.enterag.utils.BU;
import ch.enterag.utils.StopWatch;
import ch.enterag.utils.zip.EntryOutputStream;
import ch.enterag.utils.zip.FileEntry;
import ch.enterag.utils.zip.Zip64File;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;
import java.util.stream.Collectors;

public class ArchiveImpl implements Archive {
    private static final ObjectFactory _of = new ObjectFactory();
    public static final String sSIARD2_TABLE_TEMPLATE_XSD_RESOURCE = "/ch/admin/bar/siard2/api/res/table0.xsd";
    private static final String _sHEADER_FOLDER = "header/";
    private static final String _sCONTENT_FOLDER = "content/";
    private static final String _sSIARDVERSION_FOLDER = "siardversion/";
    private static final String _sMETADATA_XML = "metadata.xml";
    private static final String _sMETADATA_XSD = "metadata.xsd";
    private static final String _sGENERIC_TABLE_XSD = "table.xsd";
    private static final String _sMETADATA_XSL = "metadata.xsl";
    private static final String sPRONOM_ID = "siardversion";
    static final int _iBUFFER_SIZE = 8192;
    static final DigestTypeType _dttDEFAULT_DIGEST_ALGORITHM;
    static final String _sDEFAULT_ENCODING = "UTF-8";
    static final String _sATTR_FILE = "file";
    static final String _sATTR_LENGTH = "length";
    static final String _sATTR_DIGEST_TYPE = "digestType";
    static final String _sATTR_MESSAGE_DIGEST = "digest";
    static final String _sATTR_DLURLPATHONLY = "dlurlpathonly";
    public StopWatch _swValid = StopWatch.getInstance();
    private boolean _bValid = false;
    private Zip64File _zipFile = null;
    private String _sPreviousMetaDataVersion = "2.2";
    private boolean _bModifyPrimaryData = false;
    private boolean _bMetaDataModified = false;
    private int _iMaxInlineSize = 4000;
    private int _iMaxLobsPerFolder = -1;
    private MetaData _md = null;
    private final Map<String, Schema> _mapSchemas = new HashMap();
    private final Map<String, Schema> _mapSelectedSchemas = new HashMap<>();
    private final Map<String, List<String>> _mapSelectedSchemaTableMap = new HashMap<>();

    public static String getHeaderFolder() {
        return "header/";
    }

    public static String getContentFolder() {
        return "content/";
    }

    public static String getSiardVersionFolder() {
        return "header/siardversion/2.2/";
    }

    public static String getSiardVersionFolder21() {
        return "header/siardversion/2.1/";
    }

    public static String getSiardVersionFolder20() {
        return "header/siardversion/2.0/";
    }

    public static String getMetaDataXml() {
        return "header/metadata.xml";
    }

    public static String getMetaDataXsd() {
        return "header/metadata.xsd";
    }

    public static String getGenericTableXsd() {
        return "header/table.xsd";
    }

    public static String getMetaDataXsl() {
        return "header/metadata.xsl";
    }

    public Zip64File getZipFile() {
        return this._zipFile;
    }

    public boolean existsFileEntry(String sEntryName) throws IOException {
        boolean bExists = false;
        if (!sEntryName.endsWith("/")) {
            if (this.getZipFile().getFileEntry(sEntryName) != null) {
                bExists = true;
            }

            return bExists;
        } else {
            throw new IOException("ZIP file entries must not end with \"/\"!");
        }
    }

    public boolean existsFolderEntry(String sEntryName) throws IOException {
        boolean bExists = false;
        if (sEntryName.endsWith("/")) {
            if (this.getZipFile().getFileEntry(sEntryName) != null) {
                bExists = true;
            }

            return bExists;
        } else {
            throw new IOException("ZIP folder entries must end with \"/\"!");
        }
    }

    public void createFolderEntry(String sEntryName) throws IOException {
        if (sEntryName.endsWith("/")) {
            if (!this.existsFolderEntry(sEntryName)) {
                int iLastSlash = sEntryName.substring(0, sEntryName.length() - 1).lastIndexOf(47);
                if (iLastSlash > 0) {
                    String sParentFolder = sEntryName.substring(0, iLastSlash + 1);
                    if (!this.existsFolderEntry(sParentFolder)) {
                        this.createFolderEntry(sParentFolder);
                    }
                }

                EntryOutputStream eos = this.getZipFile().openEntryOutputStream(sEntryName, 0, new Date());
                eos.close();
            } else {
                throw new IOException("Folder " + sEntryName + " exists already!");
            }
        } else {
            throw new IOException("Folder names must end with \"/\"!");
        }
    }

    private void removeFolderEntry(String folder) throws IOException {
        this.getZipFile().delete(folder);
    }

    public InputStream openFileEntry(String sEntryName) throws IOException {
        return this.getZipFile().openEntryInputStream(sEntryName);
    }

    public OutputStream createFileEntry(String sEntryName) throws IOException {
        return this.getZipFile().openEntryOutputStream(sEntryName, 8, new Date());
    }

    private ArchiveImpl() {
    }

    public static Archive newInstance() {
        return new ArchiveImpl();
    }

    public File getFile() {
        return new File(this.getZipFile().getDiskFile().getFileName());
    }

    public String getPreviousMetaDataVersion() {
        return this._sPreviousMetaDataVersion;
    }

    public boolean canModifyPrimaryData() {
        return this._bModifyPrimaryData;
    }

    public boolean isMetaDataDifferent(Object oOld, Object oNew) {
        boolean bDifferent = !Objects.equals(oOld, oNew);

        if (bDifferent) {
            this._bMetaDataModified = true;
        }

        return bDifferent;
    }

    public void setMaxInlineSize(int iMaxInlineSize) throws IOException {
        if (this.canModifyPrimaryData() && this.isEmpty()) {
            this._iMaxInlineSize = iMaxInlineSize;
        } else {
            throw new IOException("Maximum inline size can only be set for SIARD archives that are empty!");
        }
    }

    public int getMaxInlineSize() {
        return this._iMaxInlineSize;
    }

    public void setMaxLobsPerFolder(int iMaxLobsPerFolder) throws IOException {
        if (this.canModifyPrimaryData() && this.isEmpty()) {
            this._iMaxLobsPerFolder = iMaxLobsPerFolder;
        } else {
            throw new IOException("Maximum number of external LOBs per folder can only be set for SIARD archives that are empty!");
        }
    }

    public int getMaxLobsPerFolder() {
        return this._iMaxLobsPerFolder;
    }

    public MetaData getMetaData() {
        return this._md;
    }

    private void exportResource(String sResource, OutputStream os) throws IOException {
        URL urlXsd = Archive.class.getResource(sResource);
        if (urlXsd != null) {
            byte[] buffer = new byte[8192];
            InputStream isXsd = urlXsd.openStream();

            for (int iRead = isXsd.read(buffer); iRead != -1; iRead = isXsd.read(buffer)) {
                if (iRead > 0) {
                    os.write(buffer, 0, iRead);
                }
            }

            isXsd.close();
            os.close();
        } else {
            throw new IOException("Resource " + sResource + " not in JAR!");
        }
    }

    public void exportMetaDataSchema(OutputStream osXsd) throws IOException {
//    this.exportResource("/ch/admin/bar/siard2/api/res/metadata.xsd", osXsd);
        this.exportResource("/res/metadata.xsd", osXsd);
    }

    public void exportGenericTableSchema(OutputStream osXsd) throws IOException {
//    this.exportResource("/ch/admin/bar/siard2/api/res/table.xsd", osXsd);
        this.exportResource("/res/table.xsd", osXsd);
    }

    private void exportMetaData(OutputStream osXml, boolean bValidate) throws IOException {
        MetaDataImpl mdi = (MetaDataImpl) this.getMetaData();

        try {
            MetaDataXml.writeXml(mdi.getSiardArchive(), osXml, bValidate);
            osXml.close();
            if (this.isEmpty()) {
                this._bMetaDataModified = false;
            }

        } catch (JAXBException var5) {
            JAXBException je = var5;
            throw new IOException("Error exporting metadata!", je);
        }
    }

    public void exportMetaData(OutputStream osXml) throws IOException {
        this.exportMetaData(osXml, false);
    }

    public void importMetaDataTemplate(InputStream isXml) throws IOException {
        SiardArchive saTemplate = MetaDataXml.readSiard22Xml(isXml);
        if (saTemplate == null) {
            throw new IOException("Error importing metadata!");
        } else {
            if (this.getZipFile() == null) {
                File fileArchive = File.createTempFile("mdo", ".siard");
                fileArchive.delete();
                this.create(fileArchive);
                fileArchive.deleteOnExit();
                this._md = MetaDataImpl.newInstance(this, saTemplate);
                SchemasType sts = saTemplate.getSchemas();
                if (sts != null) {
                    for (int iSchema = 0; iSchema < sts.getSchema().size(); ++iSchema) {
                        SchemaType st = sts.getSchema().get(iSchema);
                        this.createSchema(st.getName());
                    }
                }

                this._bMetaDataModified = false;
            }

            MetaDataImpl mdi = (MetaDataImpl) this.getMetaData();
            mdi.setTemplate(saTemplate);
        }
    }

    public void registerSchema(String sName, Schema schema) {
        this._mapSchemas.put(sName, schema);
    }

    public void loadMetaData() throws IOException {
        FileEntry feMetaData = this.getZipFile().getFileEntry(getMetaDataXml());
        if (feMetaData != null) {
            SiardArchive sa;
            InputStream isMetaData;
            if (this.existsFolderEntry(getSiardVersionFolder())) {
                isMetaData = this.openFileEntry(getMetaDataXml());
                sa = MetaDataXml.readSiard22Xml(isMetaData);
                isMetaData.close();
            } else if (this.existsFolderEntry(getSiardVersionFolder21())) {
                isMetaData = this.openFileEntry(getMetaDataXml());
                sa = MetaDataXml.readSiard21Xml(isMetaData);
                isMetaData.close();
                this._sPreviousMetaDataVersion = "2.1";
            } else {
                if (this.existsFolderEntry(getSiardVersionFolder20())) {
                    throw new IOException("Unsupported SIARD format version 2.0!");
                }

                isMetaData = this.openFileEntry(getMetaDataXml());
                sa = MetaDataXml.readAndConvertSiard10Xml(isMetaData);
                isMetaData.close();
                this._sPreviousMetaDataVersion = "1.0";
            }

            if (sa == null) {
                throw new IOException("Invalid SIARD meta data!");
            } else {
                this._md = MetaDataImpl.newInstance(this, sa);
            }
        } else {
            throw new IOException("Invalid SIARD file (missing metadata.xml)!");
        }
    }

    public void open(File file) throws IOException {
        if (!file.exists()) {
            throw new IOException("SIARD file " + file.getAbsolutePath() + " does not exist!");
        } else {
            this._zipFile = new Zip64File(file);
            this.loadMetaData();
            SiardArchive sa = ((MetaDataImpl) this.getMetaData()).getSiardArchive();
            SchemasType sts = sa.getSchemas();

            for (int iSchema = 0; iSchema < sts.getSchema().size(); ++iSchema) {
                SchemaType st = sts.getSchema().get(iSchema);
                SchemaImpl.newInstance(this, st.getName());
            }

            this._bModifyPrimaryData = false;
            this._bMetaDataModified = false;
            this.validate();
        }
    }

    public void create(File file) throws IOException {
        if (!file.exists()) {
            this._zipFile = new Zip64File(file);
            this._bModifyPrimaryData = true;
            this._bMetaDataModified = true;
            this.createFolderEntry("content/");
            this._md = MetaDataImpl.newInstance(this, MetaDataImpl.createSiardArchive());
        } else {
            throw new FileAlreadyExistsException("File " + file.getAbsolutePath() + " exists already!");
        }
    }

    private MessageDigestType getMessageDigest(DigestTypeType dtt) throws IOException {
        FileEntry feHeader = this.getZipFile().getFileEntry("header/");
        long lPrimaryEnd = this.getZipFile().getDiskFile().length();
        if (feHeader != null) {
            lPrimaryEnd = feHeader.getOffset();
        }

        byte[] bufDigest = this.getZipFile().getDiskFile().digest(dtt.value(), 0L, lPrimaryEnd);
        MessageDigestType md = _of.createMessageDigestType();
        md.setDigestType(dtt);
        md.setDigest(BU.toHex(bufDigest));
        return md;
    }

    private void reset() {
        this._zipFile = null;
        this._sPreviousMetaDataVersion = "2.2";
        this._bModifyPrimaryData = false;
        this._bMetaDataModified = false;
        this._iMaxInlineSize = 4000;
        this._iMaxLobsPerFolder = -1;
        this._md = null;
    }

    public void saveMetaData() throws IOException {
        if (this._bMetaDataModified) {
            MetaData md = this.getMetaData();
            String version = md.getVersion();
            if (version.equals("1.0") || version.equals("2.0")) {
                this.getZipFile().delete(getMetaDataXsl());
                this.getZipFile().delete(getMetaDataXsd());
                this.createFolderEntry("header/" + sPRONOM_ID + "/" + "2.2" + "/");
                OutputStream eos = this.createFileEntry(getMetaDataXsd());
                this.exportMetaDataSchema(eos);
                eos = this.createFileEntry(getGenericTableXsd());
                this.exportGenericTableSchema(eos);
                this._sPreviousMetaDataVersion = "2.2";
            }

            if (version.equals("2.1")) {
                this.getZipFile().delete(getMetaDataXsl());
                this.getZipFile().delete(getMetaDataXsd());
                this.removeFolderEntry("header/" + sPRONOM_ID + "/" + "2.1" + "/");
                this.createFolderEntry("header/" + sPRONOM_ID + "/" + "2.2" + "/");
            }

            FileEntry feMetadata = this.getZipFile().getFileEntry(getMetaDataXml());
            if (feMetadata != null) {
                this.getZipFile().delete(getMetaDataXml());
            }

            OutputStream eos = this.createFileEntry(getMetaDataXml());
            this.exportMetaData(eos, true);
            this._bMetaDataModified = false;
        }

    }

    public void close() throws IOException {
        if (this.getZipFile() != null) {
            if (this.canModifyPrimaryData()) {
                MetaDataImpl mdi = (MetaDataImpl) this.getMetaData();
                mdi.setMessageDigest(this.getMessageDigest(_dttDEFAULT_DIGEST_ALGORITHM));
                this.createFolderEntry("header/");
                String sVersionFolder = "header/" + sPRONOM_ID + "/" + "2.2" + "/";
                this.createFolderEntry(sVersionFolder);
                OutputStream eos = this.createFileEntry(getMetaDataXsd());
                this.exportMetaDataSchema(eos);
                eos = this.createFileEntry(getGenericTableXsd());
                this.exportGenericTableSchema(eos);
            }

            this.saveMetaData();
            this.getZipFile().close();
            this.reset();
        } else {
            throw new IOException("Archive was not open!");
        }
    }

    public int getSchemas() {
        return this.getMetaData().getMetaSchemas();
    }

    public Schema getSchema(int iSchema) {
        Schema schema = null;
        MetaSchema ms = this.getMetaData().getMetaSchema(iSchema);
        if (ms != null) {
            String sName = ms.getName();
            schema = this.getSchema(sName);
        }

        return schema;
    }

    public Schema getSchema(String sName) {
        return this._mapSchemas.get(sName);
    }

    public Schema createSchema(String sName) throws IOException {
        Schema schema = null;
        if (this.canModifyPrimaryData()) {
            if (this._mapSchemas.get(sName) != null) {
                throw new IOException("Schema name must be unique within database!");
            } else {
                schema = SchemaImpl.newInstance(this, sName);
                MetaDataImpl mdi = (MetaDataImpl) this.getMetaData();
                SiardArchive saTemplate = mdi.getTemplate();
                if (saTemplate != null) {
                    SchemasType sts = saTemplate.getSchemas();
                    if (sts != null) {
                        SchemaType stTemplate = null;

                        for (int iSchema = 0; iSchema < sts.getSchema().size(); ++iSchema) {
                            SchemaType stTry = sts.getSchema().get(iSchema);
                            if (sName.equals(stTry.getName())) {
                                stTemplate = stTry;
                            }
                        }

                        if (stTemplate != null) {
                            MetaSchemaImpl msi = (MetaSchemaImpl) schema.getMetaSchema();
                            msi.setTemplate(stTemplate);
                        }
                    }
                }

                return schema;
            }
        } else {
            throw new IOException("Schema cannot be created!\r\nSIARD archive is not open for modification of primary data!");
        }
    }

    public boolean isEmpty() {
        boolean bEmpty = true;
        Iterator<String> iterSchema = this._mapSchemas.keySet().iterator();

        while (iterSchema.hasNext()) {
            String sName = iterSchema.next();
            Schema schema = this.getSchema(sName);
            if (!schema.isEmpty()) {
                bEmpty = false;
            }
        }

        return bEmpty;
    }

    private void validate() {
        this._swValid.start();
        this._bValid = this.getMetaData().isValid();
        if (this._bValid && this.getSchemas() < 1) {
            this._bValid = false;
        }

        for (int iSchema = 0; this._bValid && iSchema < this.getSchemas(); ++iSchema) {
            Schema schema = this.getSchema(iSchema);
            if (schema == null) {
                this._bValid = false;
            } else if (!schema.isValid()) {
                this._bValid = false;
            }
        }

        this._swValid.stop();
    }

    public boolean isValid() {
        if (this.canModifyPrimaryData()) {
            this.validate();
        }

        return this._bValid;
    }

    public boolean isPrimaryDataUnchanged() throws IOException {
        boolean bUnchanged = false;

        for (int i = 0; i < this.getMetaData().getMessageDigest().size(); ++i) {
            MessageDigestType md = this.getMetaData().getMessageDigest().get(i);
            bUnchanged = md.getDigest().equals(this.getMessageDigest(md.getDigestType()).getDigest());
        }

        return bUnchanged;
    }

    public boolean isMetaDataUnchanged() {
        return !this._bMetaDataModified;
    }

    static {
        _dttDEFAULT_DIGEST_ALGORITHM = DigestTypeType.MD_5;
    }

    /**
     * 선택한 스키마로 교체. 스키마와 엔티티 이름이 같은 엔트리를 찾아 교체한다
     *
     * @param selectedSchemaTableMap
     */
    @Override
    public void replaceWithSelectedSchemas(Map<String, List<String>> selectedSchemaTableMap) {
        HashMap<String, Schema> nSchemaMap = new HashMap<>();

        // 선택된 엔티티 추출
//        selectedSchemaTableMap.forEach(
//                (schemaName, tableList) -> tableList.forEach(
//                        tableName -> {_mapSchemas.computeIfPresent(
//                                schemaName, ( schema) -> nSchemaMap.get(schemaName)
//                        )
//                )
//        );

//        _mapSchemas.clear();
//        _mapSchemas.putAll(nSchemaMap);

        replaceSchemas(
                selectedSchemaTableMap.entrySet()
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        Map.Entry::getKey, // schema name
                                        entry -> {
                                            Schema schema = _mapSchemas.get(entry.getKey());
                                            schema.replaceWithSelectedTables(entry.getValue());
                                            return schema;
                                        }
                                )
                        )
        );


    }

    private void replaceSchemas(Map<String, Schema> newSchemaMap) {
        _mapSchemas.clear();
        _mapSchemas.putAll(newSchemaMap);
        _mapSelectedSchemas.putAll(newSchemaMap);
    }

    @Override
    public Map<String, Schema> getSelectedSchemaMap() {
        return this._mapSelectedSchemas;
    }

    @Override
    public void replaceWithSelectedSchemaMap(Map<String, Schema> selectedSchemaMap) {
        replaceSchemas(selectedSchemaMap);
    }

    @Override
    public boolean hasSelectedTables() {
        return !this._mapSelectedSchemas.isEmpty();
    }

    @Override
    public Map<String, Schema> getSchemaMap() {
        return this._mapSchemas;
    }

    @Override
    public void setSelectedSchemaTableMap(Map<String, List<String>> selectedSchemaTableMap) {
        this._mapSelectedSchemaTableMap.putAll(selectedSchemaTableMap);
    }

    @Override
    public Map<String, List<String>> getSelectedSchemaTableMap() {

        return this._mapSelectedSchemaTableMap;
    }
}