package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class MS1251CharToByteConverter {
  private static final short[] UNICODE_TO_MS1251_IDX_PAGE00 = new short[] { 
      160, 0, 0, 0, 164, 0, 166, 167, 0, 169, 
      0, 171, 172, 173, 174, 0, 176, 177, 0, 0, 
      0, 181, 182, 183, 0, 0, 0, 187, 0, 0, 
      0, 0 };
  
  private static final char[] UNICODE_TO_MS1251_IDX_PAGE04 = new char[] { 
      Character.MIN_VALUE, '¨', '', '', 'ª', '½', '²', '¯', '£', '', 
      '', '', '', Character.MIN_VALUE, '¡', '', 'À', 'Á', 'Â', 'Ã', 
      'Ä', 'Å', 'Æ', 'Ç', 'È', 'É', 'Ê', 'Ë', 'Ì', 'Í', 
      'Î', 'Ï', 'Ð', 'Ñ', 'Ò', 'Ó', 'Ô', 'Õ', 'Ö', '×', 
      'Ø', 'Ù', 'Ú', 'Û', 'Ü', 'Ý', 'Þ', 'ß', 'à', 'á', 
      'â', 'ã', 'ä', 'å', 'æ', 'ç', 'è', 'é', 'ê', 'ë', 
      'ì', 'í', 'î', 'ï', 'ð', 'ñ', 'ò', 'ó', 'ô', 'õ', 
      'ö', '÷', 'ø', 'ù', 'ú', 'û', 'ü', 'ý', 'þ', 'ÿ', 
      Character.MIN_VALUE, '¸', '', '', 'º', '¾', '³', '¿', '¼', '', 
      '', '', '', Character.MIN_VALUE, '¢', '', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '¥', '´', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE };
  
  private static final char[] UNICODE_TO_MS1251_IDX_PAGE20 = new char[] { 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '', '', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '', '', 
      '', Character.MIN_VALUE, '', '', '', Character.MIN_VALUE, '', '', '', Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, '', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, '', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, '', '', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE };
  
  protected boolean subMode = true;
  
  protected byte[] subBytes = new byte[] { 63 };
  
  public int convCharArr(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    int i = paramInt1;
    int j = paramInt3;
    while (i < paramInt2 && j < paramInt4) {
      char c = paramArrayOfchar[i];
      if (c < '') {
        paramArrayOfbyte[j++] = (byte)c;
      } else {
        byte b;
        if (c >= ' ' && c < 'À') {
          b = (char)UNICODE_TO_MS1251_IDX_PAGE00[c - 160];
        } else if ((short)c >= 1024 && c < 'Ҙ') {
          b = UNICODE_TO_MS1251_IDX_PAGE04[c - 1024];
        } else if (c >= '‐' && c < '⁀') {
          b = UNICODE_TO_MS1251_IDX_PAGE20[c - 8208];
        } else if (c == '€') {
          b = 136;
        } else if (c == '№') {
          b = 185;
        } else if (c == '™') {
          b = 153;
        } else if (c == '') {
          b = 152;
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


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\MS1251CharToByteConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */