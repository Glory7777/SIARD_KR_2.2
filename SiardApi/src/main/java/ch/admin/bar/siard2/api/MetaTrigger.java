package ch.admin.bar.siard2.api;

import java.io.IOException;

public interface MetaTrigger extends MetaSearch {
  MetaTable getParentMetaTable();
  
  boolean isValid();
  
  String getName();
  
  void setActionTime(String paramString) throws IOException;
  
  String getActionTime();
  
  void setTriggerEvent(String paramString) throws IOException;
  
  String getTriggerEvent();
  
  void setAliasList(String paramString) throws IOException;
  
  String getAliasList();
  
  void setTriggeredAction(String paramString) throws IOException;
  
  String getTriggeredAction();
  
  void setDescription(String paramString);
  
  String getDescription();
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\MetaTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */