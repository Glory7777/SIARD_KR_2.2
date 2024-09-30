package ch.admin.bar.siard2.api;

import java.io.IOException;

public interface MetaRole extends MetaSearch {
  MetaData getParentMetaData();
  
  String getName();
  
  void setAdmin(String paramString) throws IOException;
  
  String getAdmin();
  
  void setDescription(String paramString);
  
  String getDescription();
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\MetaRole.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */