package ch.admin.bar.siard2.api;

import java.io.IOException;
import java.util.List;

public interface Record {
  Table getParentTable();
  
  long getRecord();
  
  int getCells() throws IOException;
  
  Cell getCell(int paramInt) throws IOException;
  
  List<Value> getValues(boolean paramBoolean1, boolean paramBoolean2) throws IOException;
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\Record.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */