package ch.admin.bar.siard2.api;

public interface MetaUser extends MetaSearch {
  MetaData getParentMetaData();
  
  String getName();
  
  void setDescription(String paramString);
  
  String getDescription();
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\MetaUser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */