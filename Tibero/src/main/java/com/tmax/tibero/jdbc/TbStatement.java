package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.msg.TbPivotInfo;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public abstract class TbStatement implements Statement {
  public abstract void addPivotData(byte[] paramArrayOfbyte);
  
  public abstract void closeInternal() throws SQLException;
  
  public abstract String getOriginalSql();
  
  public abstract Vector<byte[]> getPivotData();
  
  public abstract TbPivotInfo[] getPivotInfo();
  
  public abstract int getSqlType();
  
  public abstract boolean isPoolable() throws SQLException;
  
  public abstract void resetForCache();
  
  public abstract void setPivotInfo(TbPivotInfo[] paramArrayOfTbPivotInfo);
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\TbStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */