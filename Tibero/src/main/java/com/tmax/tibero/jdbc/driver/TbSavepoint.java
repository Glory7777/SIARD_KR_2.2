package com.tmax.tibero.jdbc.driver;

import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;
import java.sql.Savepoint;

public class TbSavepoint implements Savepoint {
  private static int ID_TYPE = 1;
  
  private static int NAME_TYPE = 2;
  
  private String name = null;
  
  private int id = getNextId();
  
  private int type = 1;
  
  private static int seedId = 0;
  
  protected TbSavepoint() {}
  
  protected TbSavepoint(String paramString) {
    this();
    this.name = paramString;
    this.type = 2;
  }
  
  public int getSavepointId() throws SQLException {
    if (this.type == NAME_TYPE)
      throw TbError.newSQLException(-90634); 
    return this.id;
  }
  
  public String getSavepointName() throws SQLException {
    if (this.type == ID_TYPE)
      throw TbError.newSQLException(-90633); 
    return this.name;
  }
  
  private synchronized int getNextId() {
    seedId = (seedId + 1) % 65535;
    return seedId;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\driver\TbSavepoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */