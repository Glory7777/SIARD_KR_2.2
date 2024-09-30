package ch.admin.bar.siard2.api;

import java.io.IOException;

public interface MetaPrivilege extends MetaSearch {
  MetaData getParentMetaData();
  
  String getType();
  
  String getObject();
  
  String getGrantor();
  
  String getGrantee();
  
  void setOption(String paramString) throws IOException;
  
  String getOption();
  
  void setDescription(String paramString);
  
  String getDescription();
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\MetaPrivilege.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */