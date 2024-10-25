package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class GB18030Decoder implements CharsetDecoder {
  public int bytesToChars(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
    try {
      String str = new String(paramArrayOfbyte, paramInt1, paramInt2, "GB18030");
      char[] arrayOfChar = str.toCharArray();
      System.arraycopy(arrayOfChar, 0, paramArrayOfchar, paramInt3, paramInt4);
      return paramInt4;
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      throw TbError.newSQLException(-590714);
    } 
  }
  
  public String bytesToString(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    try {
      return new String(paramArrayOfbyte, paramInt1, paramInt2, "GB18030");
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      throw TbError.newSQLException(-590714);
    } 
  }
  
  public String bytesToString(byte[] paramArrayOfbyte) throws SQLException {
    return bytesToString(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public int fixedBytesToChars(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
    throw TbError.newSQLException(-590714);
  }
  
  public String fixedBytesToString(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    throw TbError.newSQLException(-590714);
  }
  
  public String fixedBytesToString(byte[] paramArrayOfbyte) throws SQLException {
    throw TbError.newSQLException(-590714);
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\GB18030Decoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */