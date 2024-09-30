package ch.admin.bar.siard2.api;

import java.io.IOException;

public interface RecordExtract {
  Table getTable();
  
  RecordExtract getParentRecordExtract();
  
  long getOffset();
  
  long getDelta();
  
  String getLabel();
  
  Record getRecord();
  
  int getRecordExtracts();
  
  RecordExtract getRecordExtract(int paramInt) throws IOException;
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\RecordExtract.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */