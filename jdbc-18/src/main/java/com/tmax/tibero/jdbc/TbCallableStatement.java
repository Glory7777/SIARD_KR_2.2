package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.data.BindItem;
import com.tmax.tibero.jdbc.data.TbDate;
import com.tmax.tibero.jdbc.data.TbRAW;
import com.tmax.tibero.jdbc.data.TbTimestamp;
import com.tmax.tibero.jdbc.msg.TbPivotInfo;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.Vector;

public abstract class TbCallableStatement extends TbPreparedStatement implements CallableStatement {
  public abstract TbDate getTbDate(int paramInt) throws SQLException;
  
  public abstract TbDate getTbDate(String paramString) throws SQLException;
  
  public abstract BindItem getOutItems(int paramInt);
  
  public abstract Vector<byte[]> getPivotData(int paramInt) throws SQLException;
  
  public abstract TbPivotInfo[] getPivotInfo(int paramInt) throws SQLException;
  
  public abstract TbRAW getRAW(int paramInt) throws SQLException;
  
  public abstract TbRAW getRAW(String paramString) throws SQLException;
  
  public abstract Struct getStruct(int paramInt) throws SQLException;
  
  public abstract TbTimestamp getTbTimestamp(int paramInt) throws SQLException;
  
  public abstract TbTimestamp getTbTimestamp(String paramString) throws SQLException;
  
  public abstract void setRAW(int paramInt, TbRAW paramTbRAW) throws SQLException;
  
  public abstract void setRAW(int paramInt1, int paramInt2, TbRAW paramTbRAW) throws SQLException;
  
  public abstract void setRAW(String paramString, TbRAW paramTbRAW) throws SQLException;
  
  public abstract void setTbDate(String paramString, TbDate paramTbDate) throws SQLException;
  
  public abstract void setOutParam(int paramInt1, int paramInt2, byte[] paramArrayOfbyte, TbResultSet paramTbResultSet) throws SQLException;
  
  public abstract void setTbTimestamp(String paramString, TbTimestamp paramTbTimestamp) throws SQLException;
  
  @Deprecated
  public abstract void setUnicodeStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException;
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\TbCallableStatement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */