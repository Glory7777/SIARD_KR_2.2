package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.data.TbDate;
import com.tmax.tibero.jdbc.data.TbRAW;
import com.tmax.tibero.jdbc.data.TbTimestamp;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLType;

public abstract class TbPreparedStatement extends TbStatement implements PreparedStatement {
  public abstract ParamContainer getParamContainer();
  
  public abstract byte[] getPPID();
  
  public abstract void setBinaryDouble(int paramInt, double paramDouble) throws SQLException;
  
  public abstract void setBinaryFloat(int paramInt, float paramFloat) throws SQLException;
  
  public abstract void setBytes(int paramInt1, int paramInt2, byte[] paramArrayOfbyte) throws SQLException;
  
  public abstract void setRAW(int paramInt, TbRAW paramTbRAW) throws SQLException;
  
  public abstract void setRAW(int paramInt1, int paramInt2, TbRAW paramTbRAW) throws SQLException;
  
  public abstract void setFixedCHAR(int paramInt, String paramString) throws SQLException;
  
  public abstract void setNClob(int paramInt, Clob paramClob) throws SQLException;
  
  public abstract void setNString(int paramInt, String paramString) throws SQLException;
  
  public abstract void setPoolable(boolean paramBoolean) throws SQLException;
  
  public abstract void setTbDate(int paramInt, TbDate paramTbDate) throws SQLException;
  
  public abstract void setTbTimestamp(int paramInt, TbTimestamp paramTbTimestamp) throws SQLException;
  
  public abstract void setObject(int paramInt1, Object paramObject, SQLType paramSQLType, int paramInt2) throws SQLException;
  
  public abstract void setObject(int paramInt, Object paramObject, SQLType paramSQLType) throws SQLException;
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\TbPreparedStatement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */