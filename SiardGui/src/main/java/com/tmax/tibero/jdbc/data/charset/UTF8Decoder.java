package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;

public class UTF8Decoder implements CharsetDecoder {
  private UTF8ByteToCharConverter conv = null;
  
  public UTF8Decoder() {
    this.conv = new UTF8ByteToCharConverter();
  }
  
  public int bytesToChars(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
    return this.conv.convert(paramArrayOfbyte, paramInt1, paramInt1 + paramInt2, paramArrayOfchar, paramInt3, paramInt3 + paramInt4);
  }
  
  public String bytesToString(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    int i = paramInt2;
    char[] arrayOfChar = new char[i];
    int j = bytesToChars(paramArrayOfbyte, paramInt1, paramInt2, arrayOfChar, 0, i);
    return new String(arrayOfChar, 0, j);
  }
  
  public String bytesToString(byte[] paramArrayOfbyte) throws SQLException {
    return bytesToString(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public int fixedBytesToChars(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
    throw TbError.newSQLException(-590714);
  }
  
  public String fixedBytesToString(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    return null;
  }
  
  public String fixedBytesToString(byte[] paramArrayOfbyte) throws SQLException {
    return null;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\charset\UTF8Decoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */