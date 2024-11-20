package com.tmax.tibero.jdbc.ext;

import java.sql.PreparedStatement;

public interface TbStatementEventHandler {
  void notifyExceptionEvent(PreparedStatement paramPreparedStatement, Exception paramException);
  
  void notifyClosedEvent();
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\ext\TbStatementEventHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */