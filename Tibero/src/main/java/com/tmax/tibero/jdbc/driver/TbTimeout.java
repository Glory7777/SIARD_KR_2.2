package com.tmax.tibero.jdbc.driver;

import java.sql.SQLException;

public class TbTimeout {
  private static final long INFINITE_TIME = 9223372036854775807L;
  
  private static final TbTimeoutPollingThread pollingThread = new TbTimeoutPollingThread();
  
  private TbStatement stmt;
  
  private long interruptAfter = Long.MAX_VALUE;
  
  static TbTimeout newTimeout() throws SQLException {
    return new TbTimeout();
  }
  
  TbTimeout() {
    pollingThread.add(this);
  }
  
  void cancelStmtOfTimeover(long paramLong) {
    if (paramLong > this.interruptAfter)
      synchronized (this) {
        if (paramLong > this.interruptAfter)
          try {
            this.stmt.cancel();
            this.stmt = null;
            this.interruptAfter = Long.MAX_VALUE;
          } catch (SQLException sQLException) {} 
      }  
  }
  
  synchronized void cancelTimeout() throws SQLException {
    this.stmt = null;
    this.interruptAfter = Long.MAX_VALUE;
  }
  
  void close() {
    pollingThread.remove(this);
  }
  
  synchronized void setTimeout(long paramLong, TbStatement paramTbStatement) throws SQLException {
    this.stmt = paramTbStatement;
    this.interruptAfter = System.currentTimeMillis() + paramLong;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\driver\TbTimeout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */