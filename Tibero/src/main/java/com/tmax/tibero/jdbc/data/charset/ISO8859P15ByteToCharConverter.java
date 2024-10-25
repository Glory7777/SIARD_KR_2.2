package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class ISO8859P15ByteToCharConverter {
  protected boolean subMode = true;
  
  protected char[] subChars = new char[] { '?' };
  
  private static final char[] ISO8859P15_TO_UNICODE_PAGE = new char[] { 
      ' ', '¡', '¢', '£', '€', '¥', 'Š', '§', 'š', '©', 
      'ª', '«', '¬', '­', '®', '¯', '°', '±', '²', '³', 
      'Ž', 'µ', '¶', '·', 'ž', '¹', 'º', '»', 'Œ', 'œ', 
      'Ÿ', '¿' };
  
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


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\ISO8859P15ByteToCharConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */