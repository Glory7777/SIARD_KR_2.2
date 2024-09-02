package ch.admin.bar.siard2.api;

import ch.enterag.utils.background.Progress;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public interface Table extends Search {
  Schema getParentSchema();
  
  MetaTable getMetaTable();
  
  void exportTableSchema(OutputStream paramOutputStream) throws IOException;
  
  boolean isValid();
  
  boolean isEmpty();
  
  RecordDispenser openRecords() throws IOException;
  
  RecordRetainer createRecords() throws IOException;
  
  RecordExtract getRecordExtract() throws IOException;
  
  void sort(boolean paramBoolean, int paramInt, Progress paramProgress) throws IOException;
  
  boolean getAscending();
  
  int getSortColumn();
  
  void exportAsHtml(OutputStream paramOutputStream, File paramFile) throws IOException;
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\Table.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */