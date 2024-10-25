package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class GB18030Encoder implements CharsetEncoder {
  public int charsToBytes(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    try {
      char[] arrayOfChar = new char[paramInt2];
      System.arraycopy(paramArrayOfchar, paramInt1, arrayOfChar, 0, paramInt2);
      byte[] arrayOfByte = (new String(arrayOfChar)).getBytes("GB18030");
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
    int i = getLeadingBytePos(paramArrayOfbyte, paramInt);
    return (paramArrayOfbyte[i] >= 0) ? i : ((paramArrayOfbyte[i + 1] >= 48 && paramArrayOfbyte[i + 1] <= 57) ? ((i + 3 <= paramInt) ? (i + 3) : (i - 1)) : ((i + 1 <= paramInt) ? (i + 1) : (i - 1)));
  }
  
  public int getLeadingBytePos(byte[] paramArrayOfbyte, int paramInt) {
    byte b = 0;
    int i = 0;
    while (i + b <= paramInt) {
      i += b;
      if (paramArrayOfbyte[i] >= 0) {
        b = 1;
        continue;
      } 
      if (paramArrayOfbyte[i + 1] >= 48 && paramArrayOfbyte[i + 1] <= 57) {
        b = 4;
        continue;
      } 
      b = 2;
    } 
    return i;
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
      return paramString.getBytes("GB18030");
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      throw TbError.newSQLException(-590714);
    } 
  }
  
  public int stringToBytes(byte[] paramArrayOfbyte, int paramInt, String paramString) throws SQLException {
    try {
      byte[] arrayOfByte = paramString.getBytes("GB18030");
      System.arraycopy(arrayOfByte, 0, paramArrayOfbyte, paramInt, arrayOfByte.length);
      return arrayOfByte.length;
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      throw TbError.newSQLException(-590714);
    } 
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\GB18030Encoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */