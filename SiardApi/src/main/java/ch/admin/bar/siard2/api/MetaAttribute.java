package ch.admin.bar.siard2.api;

import java.io.IOException;

public interface MetaAttribute extends MetaSearch {
  MetaType getParentMetaType();
  
  boolean isValid();
  
  String getName();
  
  int getPosition();
  
  void setType(String paramString) throws IOException;
  
  void setPreType(int paramInt1, long paramLong, int paramInt2) throws IOException;
  
  String getType();
  
  int getPreType();
  
  long getLength();
  
  int getScale();
  
  void setTypeOriginal(String paramString) throws IOException;
  
  String getTypeOriginal();
  
  void setTypeSchema(String paramString) throws IOException;
  
  String getTypeSchema();
  
  void setTypeName(String paramString) throws IOException;
  
  String getTypeName();
  
  MetaType getMetaType();
  
  void setNullable(boolean paramBoolean) throws IOException;
  
  boolean isNullable();
  
  void setDefaultValue(String paramString) throws IOException;
  
  String getDefaultValue();
  
  void setCardinality(int paramInt) throws IOException;
  
  int getCardinality();
  
  void setDescription(String paramString);
  
  String getDescription();
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\MetaAttribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */