package ch.admin.bar.siard2.api;

import java.io.IOException;

public interface MetaParameter extends MetaSearch {
  MetaRoutine getParentMetaRoutine();
  
  boolean isValid();
  
  String getName();
  
  int getPosition();
  
  void setMode(String paramString) throws IOException;
  
  String getMode();
  
  void setType(String paramString) throws IOException;
  
  void setPreType(int paramInt1, long paramLong, int paramInt2) throws IOException;
  
  String getType();
  
  int getPreType();
  
  void setTypeOriginal(String paramString) throws IOException;
  
  String getTypeOriginal();
  
  void setTypeSchema(String paramString) throws IOException;
  
  String getTypeSchema();
  
  void setTypeName(String paramString) throws IOException;
  
  String getTypeName();
  
  MetaType getMetaType();
  
  void setCardinality(int paramInt) throws IOException;
  
  int getCardinality();
  
  void setDescription(String paramString);
  
  String getDescription();
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\MetaParameter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */