package ch.admin.bar.siard2.api;

import ch.enterag.utils.DU;
import java.io.IOException;

public interface MetaSearch {
  String getFindString();
  
  int getFoundElement() throws IOException;
  
  String getFoundString(DU paramDU) throws IOException;
  
  int getFoundOffset();
  
  void find(String paramString, boolean paramBoolean) throws IOException;
  
  MetaSearch findNext(DU paramDU) throws IOException;
  
  boolean canFindNext();
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\MetaSearch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */