package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class ISO8859P5CharToByteConverter {
  protected boolean subMode = true;
  
  protected byte[] subBytes = new byte[] { 63 };
  
  private static final char[] UNICODE_TO_KOI8R_IDX_PAGE00 = new char[] { 
      ' ', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 'ý', Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '­', Character.MIN_VALUE, Character.MIN_VALUE };
  
  private static final char[] UNICODE_TO_KOI8R_IDX_PAGE04 = new char[] { 
      Character.MIN_VALUE, '¡', '¢', '£', '¤', '¥', '¦', '§', '¨', '©', 
      'ª', '«', '¬', Character.MIN_VALUE, '®', '¯', '°', '±', '²', '³', 
      '´', 'µ', '¶', '·', '¸', '¹', 'º', '»', '¼', '½', 
      '¾', '¿', 'À', 'Á', 'Â', 'Ã', 'Ä', 'Å', 'Æ', 'Ç', 
      'È', 'É', 'Ê', 'Ë', 'Ì', 'Í', 'Î', 'Ï', 'Ð', 'Ñ', 
      'Ò', 'Ó', 'Ô', 'Õ', 'Ö', '×', 'Ø', 'Ù', 'Ú', 'Û', 
      'Ü', 'Ý', 'Þ', 'ß', 'à', 'á', 'â', 'ã', 'ä', 'å', 
      'æ', 'ç', 'è', 'é', 'ê', 'ë', 'ì', 'í', 'î', 'ï', 
      Character.MIN_VALUE, 'ñ', 'ò', 'ó', 'ô', 'õ', 'ö', '÷', 'ø', 'ù', 
      'ú', 'û', 'ü', Character.MIN_VALUE, 'þ', 'ÿ' };
  
  public int convCharArr(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    int i = paramInt1;
    int j = paramInt3;
    while (i < paramInt2 && j < paramInt4) {
      char c = paramArrayOfchar[i];
      if (c < ' ') {
        paramArrayOfbyte[j++] = (byte)c;
      } else {
        byte b;
        if (c >= ' ' && c < '°') {
          b = UNICODE_TO_KOI8R_IDX_PAGE00[c - 160];
        } else if (c >= 'Ѐ' && c < 'Ѡ') {
          b = UNICODE_TO_KOI8R_IDX_PAGE04[c - 1024];
        } else if (c == '№') {
          b = 240;
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


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\ISO8859P5CharToByteConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */