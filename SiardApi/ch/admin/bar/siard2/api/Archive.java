package ch.admin.bar.siard2.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Archive {
  public static final String sSIARD2_META_DATA_XSD_RESOURCE = "/ch/admin/bar/siard2/api/res/metadata.xsd";
  
  public static final String sSIARD2_META_DATA_NAMESPACE = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd";
  
  public static final String sSIARD2_GENERIC_TABLE_XSD_RESOURCE = "/ch/admin/bar/siard2/api/res/table.xsd";
  
  public static final String sSIARD2_TABLE_NAMESPACE = "http://www.bar.admin.ch/xmlns/siard/2/table.xsd";
  
  public static final String sSIARD_DEFAULT_EXTENSION = "siard";
  
  public static final String sMETA_DATA_VERSION_1_0 = "1.0";
  
  public static final String sMETA_DATA_VERSION_2_0 = "2.0";
  
  public static final String sMETA_DATA_VERSION_2_1 = "2.1";
  
  public static final String sMETA_DATA_VERSION = "2.2";
  
  public static final int iDEFAULT_MAX_INLINE_SIZE = 4000;
  
  File getFile();
  
  boolean canModifyPrimaryData();
  
  void setMaxInlineSize(int paramInt) throws IOException;
  
  int getMaxInlineSize();
  
  void setMaxLobsPerFolder(int paramInt) throws IOException;
  
  int getMaxLobsPerFolder();
  
  void exportMetaDataSchema(OutputStream paramOutputStream) throws IOException;
  
  void exportGenericTableSchema(OutputStream paramOutputStream) throws IOException;
  
  void exportMetaData(OutputStream paramOutputStream) throws IOException;
  
  void importMetaDataTemplate(InputStream paramInputStream) throws IOException;
  
  void open(File paramFile) throws IOException;
  
  void create(File paramFile) throws IOException;
  
  void close() throws IOException;
  
  MetaData getMetaData();
  
  void loadMetaData() throws IOException;
  
  void saveMetaData() throws IOException;
  
  int getSchemas();
  
  Schema getSchema(int paramInt);
  
  Schema getSchema(String paramString);
  
  Schema createSchema(String paramString) throws IOException;
  
  boolean isEmpty();
  
  boolean isValid();
  
  boolean isPrimaryDataUnchanged() throws IOException;
  
  boolean isMetaDataUnchanged();
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\Archive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */