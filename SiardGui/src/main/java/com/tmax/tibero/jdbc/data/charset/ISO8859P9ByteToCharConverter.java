package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class ISO8859P9ByteToCharConverter {
  protected boolean subMode = true;
  
  protected char[] subChars = new char[] { '?' };
  
  private static final char[] ISO8859P9_TO_UNICODE_PAGE = new char[] { 
      '\u011E', '\u00D1', '\u00D2', '\u00D3', '\u00D4', '\u00D5', '\u00D6', '\u00D7', '\u00D8', '\u00D9', 
      '\u00DA', '\u00DB', '\u00DC', '\u0130', '\u015E', '\u00DF', '\u00E0', '\u00E1', '\u00E2', '\u00E3', 
      '\u00E4', '\u00E5', '\u00E6', '\u00E7', '\u00E8', '\u00E9', '\u00EA', '\u00EB', '\u00EC', '\u00ED', 
      '\u00EE', '\u00EF', '\u011F', '\u00F1', '\u00F2', '\u00F3', '\u00F4', '\u00F5', '\u00F6', '\u00F7', 
      '\u00F8', '\u00F9', '\u00FA', '\u00FB', '\u00FC', '\u0131', '\u015F', '\u00FF' };
  
  private int decodeFromUcs(byte paramByte1, byte paramByte2) {
    return (paramByte1 << 8) + (paramByte2 & 0xFF);
  }
  
  public int convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
    int i = paramInt1;
    int j = paramInt3;
    while (i < paramInt2) {
      int k = decodeFromUcs((byte)0, paramArrayOfbyte[i]);
      if (k < 208) {
        paramArrayOfchar[j++] = (char)k;
        i++;
        continue;
      } 
      k = ISO8859P9_TO_UNICODE_PAGE[k - 208];
      paramArrayOfchar[j++] = (char)k;
      i++;
    } 
    return j - paramInt3;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\charset\ISO8859P9ByteToCharConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */