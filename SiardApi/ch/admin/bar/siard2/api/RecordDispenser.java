package ch.admin.bar.siard2.api;

import java.io.IOException;

public interface RecordDispenser {
  Record get() throws IOException;
  
  void skip(long paramLong) throws IOException;
  
  void close() throws IOException;
  
  long getPosition();
  
  long getByteCount();
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\RecordDispenser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */