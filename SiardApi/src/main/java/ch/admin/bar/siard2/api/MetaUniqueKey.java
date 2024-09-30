package ch.admin.bar.siard2.api;

import java.io.IOException;

public interface MetaUniqueKey extends MetaSearch {
  MetaTable getParentMetaTable();
  
  boolean isValid();
  
  String getName();
  
  int getColumns();
  
  String getColumn(int paramInt);
  
  void addColumn(String paramString) throws IOException;
  
  String getColumnsString();
  
  void setDescription(String paramString);
  
  String getDescription();
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\MetaUniqueKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */