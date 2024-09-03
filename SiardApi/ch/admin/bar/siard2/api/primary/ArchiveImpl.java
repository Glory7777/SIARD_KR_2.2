package ch.admin.bar.siard2.api.primary;

import ch.admin.bar.siard2.api.Archive;
import ch.admin.bar.siard2.api.MetaData;
import ch.admin.bar.siard2.api.Schema;
import ch.admin.bar.siard2.api.generated.*;
import ch.admin.bar.siard2.api.meta.MetaDataImpl;
import ch.enterag.utils.StopWatch;
import ch.enterag.utils.zip.EntryOutputStream;
import ch.enterag.utils.zip.FileEntry;
import ch.enterag.utils.zip.Zip64File;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ArchiveImpl implements Archive {
  private static ObjectFactory _of = new ObjectFactory(); public static final String sSIARD2_TABLE_TEMPLATE_XSD_RESOURCE = "/ch/admin/bar/siard2/api/res/table0.xsd"; private static final String _sHEADER_FOLDER = "header/"; private static final String _sCONTENT_FOLDER = "content/"; private static final String _sSIARDVERSION_FOLDER = "siardversion/"; private static final String _sMETADATA_XML = "metadata.xml"; private static final String _sMETADATA_XSD = "metadata.xsd"; private static final String _sGENERIC_TABLE_XSD = "table.xsd"; private static final String _sMETADATA_XSL = "metadata.xsl";
  
  public static String getHeaderFolder() {
    return "header/";
  } public static String getContentFolder() {
    return "content/";
  }
  public static String getSiardVersionFolder() { return "header/siardversion/2.2/"; }
  public static String getSiardVersionFolder21() { return "header/siardversion/2.1/"; } public static String getSiardVersionFolder20() {
    return "header/siardversion/2.0/";
  } public static String getMetaDataXml() {
    return "header/metadata.xml";
  } public static String getMetaDataXsd() {
    return "header/metadata.xsd";
  } public static String getGenericTableXsd() {
    return "header/table.xsd";
  } public static String getMetaDataXsl() {
    return "header/metadata.xsl";
  } private static String sPRONOM_ID = "siardversion";
  static final int _iBUFFER_SIZE = 8192;
  static final DigestTypeType _dttDEFAULT_DIGEST_ALGORITHM = DigestTypeType.MD_5;
  static final String _sDEFAULT_ENCODING = "UTF-8";
  static final String _sATTR_FILE = "file";
  static final String _sATTR_LENGTH = "length";
  static final String _sATTR_DIGEST_TYPE = "digestType";
  static final String _sATTR_MESSAGE_DIGEST = "digest";
  static final String _sATTR_DLURLPATHONLY = "dlurlpathonly";
  public StopWatch _swValid = StopWatch.getInstance();
  
  private boolean _bValid = false;
  private Zip64File _zipFile = null;
  private String _sPreviousMetaDataVersion;
  private boolean _bModifyPrimaryData;
  private boolean _bMetaDataModified;
  private int _iMaxInlineSize;
  private int _iMaxLobsPerFolder;
  private MetaData _md;
  private Map<String, Schema> _mapSchemas;
  public Zip64File getZipFile() {
    return this._zipFile;
  }

  public boolean existsFileEntry(String sEntryName) throws IOException {
    boolean bExists = false;
    if (!sEntryName.endsWith("/")) {
      
      if (getZipFile().getFileEntry(sEntryName) != null) {
        bExists = true;
      }
    } else {
      throw new IOException("ZIP file entries must not end with \"/\"!");
    }  return bExists;
  }








  
  public boolean existsFolderEntry(String sEntryName) throws IOException {
    boolean bExists = false;
    if (sEntryName.endsWith("/")) {
      
      if (getZipFile().getFileEntry(sEntryName) != null) {
        bExists = true;
      }
    } else {
      throw new IOException("ZIP folder entries must end with \"/\"!");
    }  return bExists;
  }







  
  public void createFolderEntry(String sEntryName) throws IOException {
    if (sEntryName.endsWith("/")) {
      
      if (!existsFolderEntry(sEntryName)) {
        
        int iLastSlash = sEntryName.substring(0, sEntryName.length() - 1).lastIndexOf('/');
        if (iLastSlash > 0) {
          
          String sParentFolder = sEntryName.substring(0, iLastSlash + 1);
          if (!existsFolderEntry(sParentFolder))
            createFolderEntry(sParentFolder); 
        } 
        EntryOutputStream eos = getZipFile().openEntryOutputStream(sEntryName, 0, new Date());
        eos.close();
      } else {
        
        throw new IOException("Folder " + sEntryName + " exists already!");
      } 
    } else {
      throw new IOException("Folder names must end with \"/\"!");
    } 
  }
  private void removeFolderEntry(String folder) throws IOException {
    getZipFile().delete(folder);
  }

  public InputStream openFileEntry(String sEntryName) throws IOException {
    return (InputStream)getZipFile().openEntryInputStream(sEntryName);
  }

  public OutputStream createFileEntry(String sEntryName) throws IOException {
    return (OutputStream)getZipFile().openEntryOutputStream(sEntryName, 8, new Date());
  }

  public static Archive newInstance() {
    return new ArchiveImpl();
  }
  
  public File getFile() {
    return new File(getZipFile().getDiskFile().getFileName());
  }
  public String getPreviousMetaDataVersion() { return this._sPreviousMetaDataVersion; }
  public boolean canModifyPrimaryData() { return this._bModifyPrimaryData; } public boolean isMetaDataDifferent(Object oOld, Object oNew) { boolean bDifferent = true; if (Objects.equals(oOld, oNew)) bDifferent = false;  if (bDifferent) this._bMetaDataModified = true;  return bDifferent; } public void setMaxInlineSize(int iMaxInlineSize) throws IOException { if (canModifyPrimaryData() && isEmpty()) { this._iMaxInlineSize = iMaxInlineSize; } else { throw new IOException("Maximum inline size can only be set for SIARD archives that are empty!"); }  } private ArchiveImpl() { this._sPreviousMetaDataVersion = "2.2";

    
    this._bModifyPrimaryData = false;
    this._bMetaDataModified = false;
    this._iMaxInlineSize = 4000;
    this._iMaxLobsPerFolder = -1;
    this._md = null;

    this._mapSchemas = new HashMap<>(); }
  public int getMaxInlineSize() { return this._iMaxInlineSize; }
  public void setMaxLobsPerFolder(int iMaxLobsPerFolder) throws IOException { if (canModifyPrimaryData() && isEmpty()) { this._iMaxLobsPerFolder = iMaxLobsPerFolder; } else { throw new IOException("Maximum number of external LOBs per folder can only be set for SIARD archives that are empty!"); }  }
  public int getMaxLobsPerFolder() { return this._iMaxLobsPerFolder; } public void registerSchema(String sName, Schema schema) { this._mapSchemas.put(sName, schema); } public MetaData getMetaData() { return this._md; }
  private void exportResource(String sResource, OutputStream os) throws IOException { URL urlXsd = Archive.class.getResource(sResource); if (urlXsd != null) { byte[] buffer = new byte[8192]; InputStream isXsd = urlXsd.openStream(); int iRead; for (iRead = isXsd.read(buffer); iRead != -1; iRead = isXsd.read(buffer)) {
        if (iRead > 0)
          os.write(buffer, 0, iRead); 
      }  isXsd.close(); os.close(); }
    else
    { throw new IOException("Resource " + sResource + " not in JAR!"); }
     }
  public void exportMetaDataSchema(OutputStream osXsd) throws IOException { exportResource("/ch/admin/bar/siard2/api/res/metadata.xsd", osXsd); }
  public void loadMetaData() throws IOException { FileEntry feMetaData = getZipFile().getFileEntry(getMetaDataXml());
    if (feMetaData != null)
    { SiardArchive sa;

      
      if (existsFolderEntry(getSiardVersionFolder())) {
        
        InputStream isMetaData = openFileEntry(getMetaDataXml());
        sa = MetaDataXml.readSiard22Xml(isMetaData);
        isMetaData.close();
      } else if (existsFolderEntry(getSiardVersionFolder21())) {
        InputStream isMetaData = openFileEntry(getMetaDataXml());
        sa = MetaDataXml.readSiard21Xml(isMetaData);
        isMetaData.close();
        this._sPreviousMetaDataVersion = "2.1";
      } else {
        
        if (existsFolderEntry(getSiardVersionFolder20()))
        {
          throw new IOException("Unsupported SIARD format version 2.0!");
        }


        
        InputStream isMetaData = openFileEntry(getMetaDataXml());
        sa = MetaDataXml.readAndConvertSiard10Xml(isMetaData);
        isMetaData.close();
        this._sPreviousMetaDataVersion = "1.0";
      } 
      if (sa == null)
        throw new IOException("Invalid SIARD meta data!"); 
      this._md = MetaDataImpl.newInstance(this, sa); }
    else
    
    { throw new IOException("Invalid SIARD file (missing metadata.xml)!"); }  } public void exportGenericTableSchema(OutputStream osXsd) throws IOException { exportResource("/ch/admin/bar/siard2/api/res/table.xsd", osXsd); }
  private void exportMetaData(OutputStream osXml, boolean bValidate) throws IOException { MetaDataImpl mdi = (MetaDataImpl)getMetaData(); try { MetaDataXml.writeXml(mdi.getSiardArchive(), osXml, bValidate); osXml.close(); if (isEmpty())
        this._bMetaDataModified = false;  } catch (JAXBException je) { throw new IOException("Error exporting metadata!", je); }  }
  public void exportMetaData(OutputStream osXml) throws IOException { exportMetaData(osXml, false); }
  public void importMetaDataTemplate(InputStream isXml) throws IOException { SiardArchive saTemplate = MetaDataXml.readSiard22Xml(isXml); if (saTemplate != null) { if (getZipFile() == null) { File fileArchive = File.createTempFile("mdo", ".siard"); fileArchive.delete(); create(fileArchive); fileArchive.deleteOnExit(); this._md = MetaDataImpl.newInstance(this, saTemplate); SchemasType sts = saTemplate.getSchemas(); if (sts != null)
          for (int iSchema = 0; iSchema < sts.getSchema().size(); iSchema++) { SchemaType st = sts.getSchema().get(iSchema); createSchema(st.getName()); }   this._bMetaDataModified = false; }
       MetaDataImpl mdi = (MetaDataImpl)getMetaData(); mdi.setTemplate(saTemplate); }
    else { throw new IOException("Error importing metadata!"); }
     }
  public void open(File file) throws IOException { if (file.exists()) {

      
      this._zipFile = new Zip64File(file);
      loadMetaData();
      SiardArchive sa = ((MetaDataImpl)getMetaData()).getSiardArchive();
      SchemasType sts = sa.getSchemas();
      for (int iSchema = 0; iSchema < sts.getSchema().size(); iSchema++) {
        
        SchemaType st = sts.getSchema().get(iSchema);
        SchemaImpl.newInstance(this, st.getName());
      } 
      this._bModifyPrimaryData = false;
      this._bMetaDataModified = false;
      
      validate();
    } else {
      
      throw new IOException("SIARD file " + file.getAbsolutePath() + " does not exist!");
    }  }





  
  public void create(File file) throws IOException {
    if (!file.exists()) {

      
      this._zipFile = new Zip64File(file);
      this._bModifyPrimaryData = true;
      this._bMetaDataModified = true;
      createFolderEntry("content/");
      this._md = MetaDataImpl.newInstance(this, MetaDataImpl.createSiardArchive());
    } else {
      
      throw new FileAlreadyExistsException("File " + file.getAbsolutePath() + " exists already!");
    } 
  }







  
  private MessageDigestType getMessageDigest(DigestTypeType dtt) throws IOException {
    FileEntry feHeader = getZipFile().getFileEntry("header/");

    
    long lPrimaryEnd = getZipFile().getDiskFile().length();
    if (feHeader != null) {
      lPrimaryEnd = feHeader.getOffset();
    }
    byte[] bufDigest = getZipFile().getDiskFile().digest(dtt.value(), 0L, lPrimaryEnd);
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

      
      MetaData md = getMetaData();
      String version = md.getVersion();
      if (version.equals("1.0") || version.equals("2.0")) {
        
        getZipFile().delete(getMetaDataXsl());
        getZipFile().delete(getMetaDataXsd());
        
        createFolderEntry("header/" + sPRONOM_ID + "/" + "2.2" + "/");
        
        OutputStream outputStream = createFileEntry(getMetaDataXsd());
        exportMetaDataSchema(outputStream);
        
        outputStream = createFileEntry(getGenericTableXsd());
        exportGenericTableSchema(outputStream);
        this._sPreviousMetaDataVersion = "2.2";
      } 
      
      if (version.equals("2.1")) {
        getZipFile().delete(getMetaDataXsl());
        getZipFile().delete(getMetaDataXsd());
        
        removeFolderEntry("header/" + sPRONOM_ID + "/" + "2.1" + "/");
        createFolderEntry("header/" + sPRONOM_ID + "/" + "2.2" + "/");
      } 

      
      FileEntry feMetadata = getZipFile().getFileEntry(getMetaDataXml());
      if (feMetadata != null) {
        getZipFile().delete(getMetaDataXml());
      }
      OutputStream eos = createFileEntry(getMetaDataXml());
      exportMetaData(eos, true);
      this._bMetaDataModified = false;
    } 
  }





  
  public void close() throws IOException {
    if (getZipFile() != null) {
      
      if (canModifyPrimaryData()) {

        
        MetaDataImpl mdi = (MetaDataImpl)getMetaData();
        mdi.setMessageDigest(getMessageDigest(_dttDEFAULT_DIGEST_ALGORITHM));
        
        createFolderEntry("header/");
        
        String sVersionFolder = "header/" + sPRONOM_ID + "/" + "2.2" + "/";
        createFolderEntry(sVersionFolder);
        
        OutputStream eos = createFileEntry(getMetaDataXsd());
        exportMetaDataSchema(eos);
        
        eos = createFileEntry(getGenericTableXsd());
        exportGenericTableSchema(eos);
      } 
      
      saveMetaData();
      getZipFile().close();
      reset();
    } else {
      
      throw new IOException("Archive was not open!");
    } 
  }
  
  public int getSchemas() {
    return getMetaData().getMetaSchemas();
  }


  
  public Schema getSchema(int iSchema) {
    Schema schema = null;
    MetaSchema ms = getMetaData().getMetaSchema(iSchema);
    if (ms != null) {
      
      String sName = ms.getName();
      schema = getSchema(sName);
    } 
    return schema;
  }
  
  public Schema getSchema(String sName) {
    return this._mapSchemas.get(sName);
  }



  
  public Schema createSchema(String sName) throws IOException {
    Schema schema = null;
    if (canModifyPrimaryData()) {

      
      if (this._mapSchemas.get(sName) == null) {
        
        schema = SchemaImpl.newInstance(this, sName);
        
        MetaDataImpl mdi = (MetaDataImpl)getMetaData();
        SiardArchive saTemplate = mdi.getTemplate();
        if (saTemplate != null) {
          
          SchemasType sts = saTemplate.getSchemas();
          if (sts != null) {
            
            SchemaType stTemplate = null;
            for (int iSchema = 0; iSchema < sts.getSchema().size(); iSchema++) {
              
              SchemaType stTry = sts.getSchema().get(iSchema);
              if (sName.equals(stTry.getName()))
                stTemplate = stTry; 
            } 
            if (stTemplate != null)
            {
              MetaSchemaImpl msi = (MetaSchemaImpl)schema.getMetaSchema();
              msi.setTemplate(stTemplate);
            }
          
          } 
        } 
      } else {
        
        throw new IOException("Schema name must be unique within database!");
      } 
    } else {
      throw new IOException("Schema cannot be created!\r\nSIARD archive is not open for modification of primary data!");
    } 
    return schema;
  }




  
  public boolean isEmpty() {
    boolean bEmpty = true;
    for (Iterator<String> iterSchema = this._mapSchemas.keySet().iterator(); iterSchema.hasNext(); ) {
      
      String sName = iterSchema.next();
      Schema schema = getSchema(sName);
      if (!schema.isEmpty())
        bEmpty = false; 
    } 
    return bEmpty;
  }




  
  private void validate() {
    this._swValid.start();
    this._bValid = getMetaData().isValid();
    if (this._bValid && getSchemas() < 1)
      this._bValid = false; 
    for (int iSchema = 0; this._bValid && iSchema < getSchemas(); iSchema++) {
      
      Schema schema = getSchema(iSchema);
      if (schema == null) {
        this._bValid = false;
      } else if (!schema.isValid()) {
        this._bValid = false;
      } 
    }  this._swValid.stop();
  }




  
  public boolean isValid() {
    if (canModifyPrimaryData())
      validate(); 
    return this._bValid;
  }





  
  public boolean isPrimaryDataUnchanged() throws IOException {
    boolean bUnchanged = false;
    for (int i = 0; i < getMetaData().getMessageDigest().size(); i++) {
      
      MessageDigestType md = getMetaData().getMessageDigest().get(i);
      bUnchanged = md.getDigest().equals(getMessageDigest(md.getDigestType()).getDigest());
    } 
    return bUnchanged;
  }



  
  public boolean isMetaDataUnchanged() {
    return !this._bMetaDataModified;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\primary\ArchiveImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */