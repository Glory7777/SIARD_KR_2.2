package ch.admin.bar.siard2.api;

import ch.admin.bar.siard2.api.generated.CategoryType;
import java.io.IOException;

public interface MetaType extends MetaSearch {
  MetaSchema getParentMetaSchema();
  
  boolean isValid();
  
  String getName();
  
  void setCategory(String paramString) throws IOException;
  
  String getCategory();
  
  CategoryType getCategoryType();
  
  void setUnderSchema(String paramString) throws IOException;
  
  String getUnderSchema();
  
  void setUnderType(String paramString) throws IOException;
  
  String getUnderType();
  
  void setInstantiable(boolean paramBoolean) throws IOException;
  
  boolean isInstantiable();
  
  void setFinal(boolean paramBoolean) throws IOException;
  
  boolean isFinal();
  
  void setBase(String paramString) throws IOException;
  
  void setBasePreType(int paramInt1, long paramLong, int paramInt2) throws IOException;
  
  String getBase();
  
  int getBasePreType();
  
  long getBaseLength();
  
  int getBaseScale();
  
  int getMetaAttributes();
  
  MetaAttribute getMetaAttribute(int paramInt);
  
  MetaAttribute getMetaAttribute(String paramString);
  
  MetaAttribute createMetaAttribute(String paramString) throws IOException;
  
  void setDescription(String paramString);
  
  String getDescription();
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\MetaType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */