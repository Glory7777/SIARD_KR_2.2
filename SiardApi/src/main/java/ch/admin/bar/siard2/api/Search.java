package ch.admin.bar.siard2.api;

import ch.enterag.utils.DU;
import java.io.IOException;
import java.util.List;

public interface Search {
  String getFindString();
  
  long getFoundRow();
  
  int getFoundPosition();
  
  String getFoundString(DU paramDU) throws IOException;
  
  int getFoundOffset();
  
  void find(List<MetaColumn> paramList, String paramString, boolean paramBoolean) throws IOException;
  
  Cell findNext(DU paramDU) throws IOException;
  
  boolean canFindNext();
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\Search.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */