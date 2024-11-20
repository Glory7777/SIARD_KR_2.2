package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class EUCTWEncoder implements CharsetEncoder {
  public int charsToBytes(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    try {
      char[] arrayOfChar = new char[paramInt2];
      System.arraycopy(paramArrayOfchar, paramInt1, arrayOfChar, 0, paramInt2);
      byte[] arrayOfByte = (new String(arrayOfChar)).getBytes("EUC-TW");
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
    if (paramArrayOfbyte[paramInt] > 0)
      return paramInt; 
    int i = getLeadingBytePos(paramArrayOfbyte, paramInt);
    return (paramArrayOfbyte[i] == -114) ? ((i + 3 > paramInt) ? (i - 1) : (i + 3)) : ((i + 1 > paramInt) ? (i - 1) : (i + 1));
  }
  
  public int getLeadingBytePos(byte[] paramArrayOfbyte, int paramInt) {
    if (paramArrayOfbyte[paramInt] > 0 || paramArrayOfbyte[paramInt] == -114)
      return paramInt; 
    byte b;
    for (b = 0; paramInt - b > 0 && paramArrayOfbyte[paramInt - b] <= 0 && paramArrayOfbyte[paramInt - b] != -114; b++);
    return (paramArrayOfbyte[paramInt - b] > 0) ? ((b % 2 == 0) ? (paramInt - 1) : paramInt) : ((b < 4 && paramArrayOfbyte[paramInt - b] == -114) ? (paramInt - b) : ((b % 2 == 0) ? paramInt : (paramInt - 1)));
  }
  
  public int getMaxBytesPerChar() {
    return 4;
  }
  
  public boolean isEndingByte(byte[] paramArrayOfbyte, int paramInt) {
    return (paramInt == getEndingBytePos(paramArrayOfbyte, paramInt));
  }
  
  public boolean isLeadingByte(byte[] paramArrayOfbyte, int paramInt) {
    return (paramInt == getLeadingBytePos(paramArrayOfbyte, paramInt));
  }
  
  public byte[] stringToBytes(String paramString) throws SQLException {
    try {
      return paramString.getBytes("EUC-TW");
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      throw TbError.newSQLException(-590714);
    } 
  }
  
  public int stringToBytes(byte[] paramArrayOfbyte, int paramInt, String paramString) throws SQLException {
    try {
      byte[] arrayOfByte = paramString.getBytes("EUC-TW");
      System.arraycopy(arrayOfByte, 0, paramArrayOfbyte, paramInt, arrayOfByte.length);
      return arrayOfByte.length;
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      throw TbError.newSQLException(-590714);
    } 
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\charset\EUCTWEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */