package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class ISO8859P8Encoder implements CharsetEncoder {
  public int charsToBytes(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    try {
      char[] arrayOfChar = new char[paramInt2];
      System.arraycopy(paramArrayOfchar, paramInt1, arrayOfChar, 0, paramInt2);
      byte[] arrayOfByte = (new String(arrayOfChar)).getBytes("ISO-8859-8");
      System.arraycopy(arrayOfByte, 0, paramArrayOfbyte, paramInt3, arrayOfByte.length);
      return arrayOfByte.length;
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      throw TbError.newSQLException(-590714);
    } 
  }
  
  public int charsToFixedBytes(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    throw TbError.newSQLException(-590714);
  }
  
  public int getEndingBytePos(byte[] paramArrayOfbyte, int paramInt) {
    return paramInt;
  }
  
  public int getLeadingBytePos(byte[] paramArrayOfbyte, int paramInt) {
    return paramInt;
  }
  
  public int getMaxBytesPerChar() {
    return 1;
  }
  
  public boolean isEndingByte(byte[] paramArrayOfbyte, int paramInt) {
    return true;
  }
  
  public boolean isLeadingByte(byte[] paramArrayOfbyte, int paramInt) {
    return true;
  }
  
  public byte[] stringToBytes(String paramString) throws SQLException {
    try {
      return paramString.getBytes("ISO-8859-8");
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      throw TbError.newSQLException(-590714);
    } 
  }
  
  public int stringToBytes(byte[] paramArrayOfbyte, int paramInt, String paramString) throws SQLException {
    try {
      byte[] arrayOfByte = paramString.getBytes("ISO-8859-8");
      System.arraycopy(arrayOfByte, 0, paramArrayOfbyte, paramInt, arrayOfByte.length);
      return arrayOfByte.length;
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      throw TbError.newSQLException(-590714);
    } 
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\charset\ISO8859P8Encoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */