package com.tmax.tibero.jdbc.ext;

import java.sql.PreparedStatement;

public interface TbStatementEventHandler {
  void notifyExceptionEvent(PreparedStatement paramPreparedStatement, Exception paramException);
  
  void notifyClosedEvent();
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\ext\TbStatementEventHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */