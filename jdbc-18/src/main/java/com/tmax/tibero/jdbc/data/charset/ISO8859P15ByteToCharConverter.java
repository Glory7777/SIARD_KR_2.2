package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class ISO8859P15ByteToCharConverter {
  protected boolean subMode = true;
  
  protected char[] subChars = new char[] { '?' };
  
  private static final char[] ISO8859P15_TO_UNICODE_PAGE = new char[] { 
      '\u00A0', '\u00A1', '\u00A2', '\u00A3', '\u20AC', '\u00A5', '\u0160', '\u00A7', '\u0161', '\u00A9', 
      '\u00AA', '\u00AB', '\u00AC', '\u00AD', '\u00AE', '\u00AF', '\u00B0', '\u00B1', '\u00B2', '\u00B3', 
      '\u017D', '\u00B5', '\u00B6', '\u00B7', '\u017E', '\u00B9', '\u00BA', '\u00BB', '\u0152', '\u0153', 
      '\u0178', '\u00BF' };
  
  private int decodeFromUcs(byte paramByte1, byte paramByte2) {
    return (paramByte1 << 8) + (paramByte2 & 0xFF);
  }
  
  public int convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
    int i = paramInt1;
    int j = paramInt3;
    while (i < paramInt2) {
      int k = decodeFromUcs((byte)0, paramArrayOfbyte[i]);
      if (k >= 160 && k < 192) {
        k = ISO8859P15_TO_UNICODE_PAGE[k - 160];
        paramArrayOfchar[j++] = (char)k;
        i++;
        continue;
      } 
      paramArrayOfchar[j++] = (char)k;
      i++;
    } 
    return j - paramInt3;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\charset\ISO8859P15ByteToCharConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */