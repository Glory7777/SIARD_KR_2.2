package ch.admin.bar.siard2.api;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface Schema {
  Archive getParentArchive();
  
  MetaSchema getMetaSchema();
  
  boolean isValid();
  
  boolean isEmpty();
  
  int getTables();
  
  Table getTable(int paramInt);
  
  Table getTable(String paramString);
  
  Table createTable(String paramString) throws IOException;

  void replaceWithSelectedTables(List<String> selectedTableNameList);

  long getRecordCount();

  Collection<Table> getSelectedTables();
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\Schema.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */