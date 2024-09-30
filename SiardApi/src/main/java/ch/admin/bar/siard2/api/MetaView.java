package ch.admin.bar.siard2.api;

import java.io.IOException;

public interface MetaView extends MetaSearch {
  MetaSchema getParentMetaSchema();
  
  boolean isValid();
  
  String getName();
  
  void setQuery(String paramString);
  
  String getQuery();
  
  void setQueryOriginal(String paramString) throws IOException;
  
  String getQueryOriginal();
  
  void setDescription(String paramString);
  
  String getDescription();
  
  void setRows(long paramLong) throws IOException;
  
  long getRows();
  
  int getMetaColumns();
  
  MetaColumn getMetaColumn(int paramInt);
  
  MetaColumn getMetaColumn(String paramString);
  
  MetaColumn createMetaColumn(String paramString) throws IOException;
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\MetaView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */