package com.tmax.tibero.jdbc.ext;

import java.sql.SQLException;

public interface TbConnectionEventHandler {
  void notifyExceptionEvent(SQLException paramSQLException);
  
  void notifyClosedEvent();
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\ext\TbConnectionEventHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */