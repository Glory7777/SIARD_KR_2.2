package com.tmax.tibero.jdbc.ext;

import com.tmax.tibero.jdbc.TbStatement;
import java.sql.SQLException;

public class TbStatementCacheEntry {
  int stmtType;
  
  TbStatement stmt;
  
  protected TbStatementCacheEntry prev = null;
  
  protected TbStatementCacheEntry next = null;
  
  public TbStatementCacheEntry(int paramInt) {
    this.stmtType = paramInt;
  }
  
  public void clear() throws SQLException {
    if (this.stmt != null) {
      this.stmt.closeInternal();
      this.stmt = null;
    } 
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\ext\TbStatementCacheEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */