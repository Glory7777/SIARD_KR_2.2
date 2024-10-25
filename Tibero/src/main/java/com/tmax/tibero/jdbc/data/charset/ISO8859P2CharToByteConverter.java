package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class ISO8859P2CharToByteConverter {
  protected boolean subMode = true;
  
  protected byte[] subBytes = new byte[] { 63 };
  
  private static final char[] UNICODE_TO_ISO8859P2_IDX_PAGE00 = new char[] { 
      ' ', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '¤', Character.MIN_VALUE, Character.MIN_VALUE, '§', '¨', Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '­', Character.MIN_VALUE, Character.MIN_VALUE, '°', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 
      '´', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '¸', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 'Á', 'Â', Character.MIN_VALUE, 'Ä', Character.MIN_VALUE, Character.MIN_VALUE, 'Ç', 
      Character.MIN_VALUE, 'É', Character.MIN_VALUE, 'Ë', Character.MIN_VALUE, 'Í', 'Î', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, 'Ó', 'Ô', Character.MIN_VALUE, 'Ö', '×', Character.MIN_VALUE, Character.MIN_VALUE, 'Ú', Character.MIN_VALUE, 
      'Ü', 'Ý', Character.MIN_VALUE, 'ß', Character.MIN_VALUE, 'á', 'â', Character.MIN_VALUE, 'ä', Character.MIN_VALUE, 
      Character.MIN_VALUE, 'ç', Character.MIN_VALUE, 'é', Character.MIN_VALUE, 'ë', Character.MIN_VALUE, 'í', 'î', Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 'ó', 'ô', Character.MIN_VALUE, 'ö', '÷', Character.MIN_VALUE, Character.MIN_VALUE, 
      'ú', Character.MIN_VALUE, 'ü', 'ý', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 'Ã', 'ã', 
      '¡', '±', 'Æ', 'æ', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 'È', 'è', 
      'Ï', 'ï', 'Ð', 'ð', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 
      'Ê', 'ê', 'Ì', 'ì', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 'Å', 'å', Character.MIN_VALUE, Character.MIN_VALUE, '¥', 'µ', Character.MIN_VALUE, 
      Character.MIN_VALUE, '£', '³', 'Ñ', 'ñ', Character.MIN_VALUE, Character.MIN_VALUE, 'Ò', 'ò', Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 'Õ', 'õ', Character.MIN_VALUE, Character.MIN_VALUE, 
      'À', 'à', Character.MIN_VALUE, Character.MIN_VALUE, 'Ø', 'ø', '¦', '¶', Character.MIN_VALUE, Character.MIN_VALUE, 
      'ª', 'º', '©', '¹', 'Þ', 'þ', '«', '»', Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 'Ù', 'ù', 'Û', 'û', 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '¬', '¼', '¯', 
      '¿', '®', '¾', Character.MIN_VALUE };
  
  private static final char[] UNICODE_TO_ISO8859P2_IDX_PAGE02 = new char[] { 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '·', Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '¢', 'ÿ', Character.MIN_VALUE, '²', Character.MIN_VALUE, '½', 
      Character.MIN_VALUE, Character.MIN_VALUE };
  
  public int convCharArr(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    int i = paramInt1;
    int j = paramInt3;
    while (i < paramInt2 && j < paramInt4) {
      char c = paramArrayOfchar[i];
      if (c < ' ') {
        paramArrayOfbyte[j++] = (byte)c;
      } else {
        byte b;
        if (c >= ' ' && c < 'ƀ') {
          b = UNICODE_TO_ISO8859P2_IDX_PAGE00[c - 160];
        } else if (c >= 'ˀ' && c < 'ˠ') {
          b = UNICODE_TO_ISO8859P2_IDX_PAGE02[c - 704];
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


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\ISO8859P2CharToByteConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */