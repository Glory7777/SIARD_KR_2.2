package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class MS1251Decoder implements CharsetDecoder {
  private MS1251ByteToCharConverter conv = null;
  
  public MS1251Decoder() {
    this.conv = new MS1251ByteToCharConverter();
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
    int i = paramInt3;
    for (int j = paramInt1; j < paramInt1 + paramInt2; j += 2) {
      if (paramArrayOfbyte[j] == 0) {
        if (paramArrayOfbyte[j + 1] < 0) {
          paramArrayOfchar[i++] = (char)(paramArrayOfbyte[j + 1] + 256);
        } else {
          paramArrayOfchar[i++] = (char)paramArrayOfbyte[j + 1];
        } 
      } else {
        i += this.conv.convert(paramArrayOfbyte, j, j + 2, paramArrayOfchar, i, i + 2);
      } 
    } 
    return i - paramInt3;
  }
  
  public String fixedBytesToString(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    return null;
  }
  
  public String fixedBytesToString(byte[] paramArrayOfbyte) throws SQLException {
    return null;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\MS1251Decoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */