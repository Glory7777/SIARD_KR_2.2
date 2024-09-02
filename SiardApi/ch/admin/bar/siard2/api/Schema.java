package ch.admin.bar.siard2.api;

import java.io.IOException;

public interface Schema {
  Archive getParentArchive();
  
  MetaSchema getMetaSchema();
  
  boolean isValid();
  
  boolean isEmpty();
  
  int getTables();
  
  Table getTable(int paramInt);
  
  Table getTable(String paramString);
  
  Table createTable(String paramString) throws IOException;
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\Schema.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */