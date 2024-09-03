package ch.admin.bar.siard2.api.primary;

import ch.admin.bar.siard2.api.Table;
import ch.enterag.utils.background.Progress;

import java.io.IOException;
import java.io.InputStream;

public interface SortedTable {
  InputStream open() throws IOException;
  
  void sort(Table paramTable, boolean paramBoolean, int paramInt, Progress paramProgress) throws IOException;
  
  boolean getAscending();
  
  int getSortColumn();
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\primary\SortedTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */