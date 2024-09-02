package ch.admin.bar.siard2.api;

import java.io.IOException;
import java.net.URI;
import java.util.List;

public interface MetaValue extends MetaSearch {
  MetaColumn getAncestorMetaColumn();
  
  String getName();
  
  int getPosition();
  
  void setLobFolder(URI paramURI) throws IOException;
  
  URI getLobFolder();
  
  URI getAbsoluteLobFolder();
  
  String getType() throws IOException;
  
  int getPreType() throws IOException;
  
  String getTypeOriginal() throws IOException;
  
  String getTypeSchema() throws IOException;
  
  String getTypeName() throws IOException;
  
  long getLength() throws IOException;
  
  int getScale() throws IOException;
  
  long getMaxLength() throws IOException;
  
  MetaType getMetaType() throws IOException;
  
  void setMimeType(String paramString) throws IOException;
  
  String getMimeType();
  
  int getMetaFields() throws IOException;
  
  MetaField getMetaField(int paramInt) throws IOException;
  
  MetaField getMetaField(String paramString) throws IOException;
  
  MetaField createMetaField() throws IOException;
  
  int getCardinality() throws IOException;
  
  void setDescription(String paramString);
  
  String getDescription();
  
  List<List<String>> getNames(boolean paramBoolean1, boolean paramBoolean2) throws IOException;
  
  String getType(List<String> paramList) throws IOException;
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\MetaValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */