package ch.admin.bar.siard2.api;

import java.io.IOException;

public interface MetaForeignKey extends MetaSearch {
  MetaTable getParentMetaTable();
  
  boolean isValid();
  
  String getName();
  
  void setReferencedSchema(String paramString) throws IOException;
  
  String getReferencedSchema();
  
  void setReferencedTable(String paramString) throws IOException;
  
  String getReferencedTable();
  
  int getReferences();
  
  String getColumn(int paramInt);
  
  String getReferenced(int paramInt);
  
  void addReference(String paramString1, String paramString2) throws IOException;
  
  String getColumnsString();
  
  String getReferencesString();
  
  void setMatchType(String paramString) throws IOException;
  
  String getMatchType();
  
  void setDeleteAction(String paramString) throws IOException;
  
  String getDeleteAction();
  
  void setUpdateAction(String paramString) throws IOException;
  
  String getUpdateAction();
  
  void setDescription(String paramString);
  
  String getDescription();
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\MetaForeignKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */