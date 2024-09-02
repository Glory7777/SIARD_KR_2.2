package ch.admin.bar.siard2.api;

import java.io.IOException;

public interface MetaCheckConstraint extends MetaSearch {
  MetaTable getParentMetaTable();
  
  boolean isValid();
  
  String getName();
  
  void setCondition(String paramString) throws IOException;
  
  String getCondition();
  
  void setDescription(String paramString);
  
  String getDescription();
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\MetaCheckConstraint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */