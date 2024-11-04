package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class TbRef implements Ref {
  public final int REF_TOID_OFFSET = 1;
  
  public final int REF_ROID_OFFSET = 17;
  
  public final int REF_ROWID_OFFSET = 33;
  
  public final int REF_TOID_LEN = 16;
  
  public final int REF_ROID_LEN = 16;
  
  public final int REF_ROWID_LEN = 10;
  
  public final int REF_LEN = 43;
  
  private byte[] rawData;
  
  private TbStructDescriptor realDesc;
  
  private String hexEncodedTOID;
  
  private TbConnection conn;
  
  public TbRef(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, TbConnection paramTbConnection) {
    this.rawData = new byte[paramInt2];
    System.arraycopy(paramArrayOfbyte, paramInt1, this.rawData, 0, paramInt2);
    setHexTOID();
    this.conn = paramTbConnection;
  }
  
  public String getBaseTypeName() throws SQLException {
    try {
      if (this.conn != null)
        this.realDesc = TbStructDescriptor.createDescriptor(this, (Connection)this.conn); 
    } catch (SQLException sQLException) {
      throw TbError.newSQLException(-90612, sQLException);
    } 
    return this.realDesc.getSQLTypeName();
  }
  
  public Object getObject(Map<String, Class<?>> paramMap) throws SQLException {
    String str = "SELECT DEREF(?) FROM DUAL";
    PreparedStatement preparedStatement = null;
    try {
      preparedStatement = this.conn.prepareStatement(str);
      preparedStatement.setRef(1, this);
      ResultSet resultSet = preparedStatement.executeQuery();
      resultSet.next();
      return resultSet.getObject(1, paramMap);
    } finally {
      if (preparedStatement != null)
        try {
          preparedStatement.close();
        } catch (Exception exception) {} 
    } 
  }
  
  public Object getObject() throws SQLException {
    String str = "SELECT DEREF(?) FROM DUAL";
    PreparedStatement preparedStatement = null;
    try {
      preparedStatement = this.conn.prepareStatement(str);
      preparedStatement.setRef(1, this);
      ResultSet resultSet = preparedStatement.executeQuery();
      resultSet.next();
      return resultSet.getObject(1);
    } finally {
      if (preparedStatement != null)
        try {
          preparedStatement.close();
        } catch (Exception exception) {} 
    } 
  }
  
  public void setObject(Object paramObject) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  private void setHexTOID() {
    char[] arrayOfChar = "0123456789ABCDEF".toCharArray();
    StringBuilder stringBuilder = new StringBuilder(32);
    for (byte b = 0; b < 16; b++) {
      stringBuilder.append(arrayOfChar[this.rawData[b + 1] >> 4 & 0xF]);
      stringBuilder.append(arrayOfChar[this.rawData[b + 1] & 0xF]);
    } 
    this.hexEncodedTOID = stringBuilder.toString();
  }
  
  public String getHexTOID() {
    return this.hexEncodedTOID;
  }
  
  public byte[] getRawData() {
    return this.rawData;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\TbRef.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */