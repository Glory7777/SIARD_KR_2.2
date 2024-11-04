package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class EUCJPTILDEDecoder implements CharsetDecoder {
  private EUCJPTILDEByteToCharConverter conv = null;
  
  public EUCJPTILDEDecoder() {
    this.conv = new EUCJPTILDEByteToCharConverter();
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
    return 0;
  }
  
  public String fixedBytesToString(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    return null;
  }
  
  public String fixedBytesToString(byte[] paramArrayOfbyte) throws SQLException {
    return null;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\EUCJPTILDEDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */