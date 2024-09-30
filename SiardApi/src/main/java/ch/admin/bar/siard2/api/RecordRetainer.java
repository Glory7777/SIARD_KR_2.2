package ch.admin.bar.siard2.api;

import java.io.IOException;

public interface RecordRetainer {
  Record create() throws IOException;
  
  void put(Record paramRecord) throws IOException;
  
  void close() throws IOException;
  
  long getPosition();
  
  long getByteCount();
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\RecordRetainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */