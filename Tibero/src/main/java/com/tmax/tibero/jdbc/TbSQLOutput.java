package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.err.TbError;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.sql.SQLXML;
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;

public class TbSQLOutput implements SQLOutput {
  private TbStructDescriptor descriptor;
  
  private Object[] attributes;
  
  private int index;
  
  private TbConnection conn;
  
  public TbSQLOutput(TbStructDescriptor paramTbStructDescriptor, TbConnection paramTbConnection) {
    this.descriptor = paramTbStructDescriptor;
    this.attributes = new Object[paramTbStructDescriptor.getNumOfFields()];
    this.conn = paramTbConnection;
    this.index = 0;
  }
  
  public TbStruct getStruct() throws SQLException {
    return new TbStruct(this.descriptor, this.conn, this.attributes);
  }
  
  public void writeString(String paramString) throws SQLException {
    this.attributes[this.index++] = paramString;
  }
  
  public void writeBoolean(boolean paramBoolean) throws SQLException {
    this.attributes[this.index++] = Boolean.valueOf(paramBoolean);
  }
  
  public void writeByte(byte paramByte) throws SQLException {
    this.attributes[this.index++] = Integer.valueOf(paramByte);
  }
  
  public void writeShort(short paramShort) throws SQLException {
    this.attributes[this.index++] = Integer.valueOf(paramShort);
  }
  
  public void writeInt(int paramInt) throws SQLException {
    this.attributes[this.index++] = Integer.valueOf(paramInt);
  }
  
  public void writeLong(long paramLong) throws SQLException {
    this.attributes[this.index++] = Long.valueOf(paramLong);
  }
  
  public void writeFloat(float paramFloat) throws SQLException {
    this.attributes[this.index++] = Float.valueOf(paramFloat);
  }
  
  public void writeDouble(double paramDouble) throws SQLException {
    this.attributes[this.index++] = Double.valueOf(paramDouble);
  }
  
  public void writeBigDecimal(BigDecimal paramBigDecimal) throws SQLException {
    this.attributes[this.index++] = paramBigDecimal;
  }
  
  public void writeBytes(byte[] paramArrayOfbyte) throws SQLException {
    this.attributes[this.index++] = paramArrayOfbyte;
  }
  
  public void writeDate(Date paramDate) throws SQLException {
    this.attributes[this.index++] = paramDate;
  }
  
  public void writeTime(Time paramTime) throws SQLException {
    this.attributes[this.index++] = paramTime;
  }
  
  public void writeTimestamp(Timestamp paramTimestamp) throws SQLException {
    this.attributes[this.index++] = paramTimestamp;
  }
  
  public void writeCharacterStream(Reader paramReader) throws SQLException {
    this.attributes[this.index++] = paramReader;
  }
  
  public void writeAsciiStream(InputStream paramInputStream) throws SQLException {
    this.attributes[this.index++] = paramInputStream;
  }
  
  public void writeBinaryStream(InputStream paramInputStream) throws SQLException {
    this.attributes[this.index++] = paramInputStream;
  }
  
  public void writeObject(SQLData paramSQLData) throws SQLException {
    TbStruct tbStruct = null;
    if (paramSQLData != null) {
      TbStructDescriptor tbStructDescriptor = TbStructDescriptor.createDescriptor(paramSQLData.getSQLTypeName(), this.conn);
      SQLOutput sQLOutput = tbStructDescriptor.toSQLOutput();
      paramSQLData.writeSQL(sQLOutput);
      tbStruct = ((TbSQLOutput)sQLOutput).getStruct();
    } 
    writeStruct(tbStruct);
  }
  
  public void writeRef(Ref paramRef) throws SQLException {
    this.attributes[this.index++] = paramRef;
  }
  
  public void writeBlob(Blob paramBlob) throws SQLException {
    this.attributes[this.index++] = paramBlob;
  }
  
  public void writeClob(Clob paramClob) throws SQLException {
    this.attributes[this.index++] = paramClob;
  }
  
  public void writeStruct(Struct paramStruct) throws SQLException {
    this.attributes[this.index++] = paramStruct;
  }
  
  public void writeArray(Array paramArray) throws SQLException {
    this.attributes[this.index++] = paramArray;
  }
  
  public void writeURL(URL paramURL) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public void writeNString(String paramString) throws SQLException {
    this.attributes[this.index++] = paramString;
  }
  
  public void writeNClob(NClob paramNClob) throws SQLException {
    this.attributes[this.index++] = paramNClob;
  }
  
  public void writeRowId(RowId paramRowId) throws SQLException {
    this.attributes[this.index++] = paramRowId;
  }
  
  public void writeSQLXML(SQLXML paramSQLXML) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\TbSQLOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */