package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class ISO8859P15CharToByteConverter {
  protected boolean subMode = true;
  
  protected byte[] subBytes = new byte[] { 63 };
  
  private static final char[] UNICODE_TO_ISO8859P15_IDX_PAGE00 = new char[] { 
      ' ', '¡', '¢', '£', Character.MIN_VALUE, '¥', Character.MIN_VALUE, '§', Character.MIN_VALUE, '©', 
      'ª', '«', '¬', '­', '®', '¯', '°', '±', '²', '³', 
      Character.MIN_VALUE, 'µ', '¶', '·', Character.MIN_VALUE, '¹', 'º', '»', Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, '¿' };
  
  private static final char[] UNICODE_TO_ISO8859P15_IDX_PAGE01 = new char[] { 
      Character.MIN_VALUE, Character.MIN_VALUE, '¼', '½', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '¦', '¨', Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 
      '¾', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '´', '¸', Character.MIN_VALUE };
  
  public int convCharArr(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    int i = paramInt1;
    int j = paramInt3;
    while (i < paramInt2 && j < paramInt4) {
      char c = paramArrayOfchar[i];
      if (c < ' ') {
        paramArrayOfbyte[j++] = (byte)c;
      } else {
        byte b;
        if (c >= ' ' && c < 'À') {
          b = (byte) UNICODE_TO_ISO8859P15_IDX_PAGE00[c - 160];
        } else if (c >= 'À' && c < 'Ā') {
          b = (byte) c;
        } else if (c >= 'Ő' && c < 'ƀ') {
          b = (byte) UNICODE_TO_ISO8859P15_IDX_PAGE01[c - 336];
        } else if (c == '€') {
          b = (byte) 164;
        } else {
          b = 63;
        } 
        if (b == 0)
          b = 63; 
        paramArrayOfbyte[j++] = (byte)b;
      } 
      i++;
    } 
    return j - paramInt3;
  }
  
  public int convString(String paramString, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    return convCharArr(paramString.toCharArray(), paramInt1, paramInt2, paramArrayOfbyte, paramInt3, paramInt4);
  }
  
  public int getMaxBytesPerChar() {
    return 1;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\ISO8859P15CharToByteConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */