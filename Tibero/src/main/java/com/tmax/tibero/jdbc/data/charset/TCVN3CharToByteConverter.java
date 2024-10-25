package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class TCVN3CharToByteConverter {
  private static final short[] UNICODE_TO_TCVN3_IDX_PAGE1 = new short[] { 
      63, 63, 162, 63, 63, 63, 63, 63, 63, 63, 
      163, 63, 63, 63, 63, 63, 63, 63, 63, 63, 
      164, 63, 63, 63, 63, 63, 63, 63, 63, 63, 
      63, 63, 181, 184, 169, 183, 63, 63, 63, 63, 
      204, 208, 170, 63, 215, 221, 63, 63, 63, 63, 
      223, 227, 171, 226, 63, 63, 63, 239, 243, 63, 
      63, 253, 63, 63 };
  
  private static final char[] UNICODE_TO_TCVN3_IDX_PAGE2 = new char[] { 
      '?', '?', '¡', '¨', '?', '?', '?', '?', '?', '?', 
      '?', '?', '?', '?', '?', '?', '§', '®', '?', '?', 
      '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', 
      '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', 
      '?', 'Ü', '?', '?', '?', '?', '?', '?', '?', '?', 
      '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', 
      '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', 
      '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', 
      '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', 
      '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', 
      '?', '?', '?', '?', '?', 'ò', '?', '?', '?', '?', 
      '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', 
      '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', 
      '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', 
      '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', 
      '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', 
      '¥', '¬', '?', '?', '?', '?', '?', '?', '?', '?', 
      '?', '?', '?', '?', '?', '¦', '­', '?', '?', '?', 
      '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', 
      '?', '?' };
  
  private static final char[] UNICODE_TO_TCVN3_IDX_PAGE3 = new char[] { 
      '?', '¹', '?', '¶', '?', 'Ê', '?', 'Ç', '?', 'È', 
      '?', 'É', '?', 'Ë', '?', '¾', '?', '»', '?', '¼', 
      '?', '½', '?', 'Æ', '?', 'Ñ', '?', 'Î', '?', 'Ï', 
      '?', 'Õ', '?', 'Ò', '?', 'Ó', '?', 'Ô', '?', 'Ö', 
      '?', 'Ø', '?', 'Þ', '?', 'ä', '?', 'á', '?', 'è', 
      '?', 'å', '?', 'æ', '?', 'ç', '?', 'é', '?', 'í', 
      '?', 'ê', '?', 'ë', '?', 'ì', '?', 'î', '?', 'ô', 
      '?', 'ñ', '?', 'ø', '?', 'õ', '?', 'ö', '?', '÷', 
      '?', 'ù', '?', 'ú', '?', 'þ', '?', 'û', '?', 'ü', 
      '?', '?', '?', '?', '?', '?' };
  
  protected boolean subMode = true;
  
  protected byte[] subBytes = new byte[] { 63 };
  
  public boolean canConvert(char paramChar) {
    return (paramChar < '' || (paramChar >= 'À' && paramChar < 'ÿ') || (paramChar >= 'Ā' && paramChar < 'ƿ') || (paramChar >= 'Ạ' && paramChar < 'ỿ'));
  }
  
  protected int convSingleByte(char paramChar, byte[] paramArrayOfbyte) {
    if (paramChar < '') {
      paramArrayOfbyte[0] = (byte)(paramChar & 0x7F);
      return 1;
    } 
    return 0;
  }
  
  public int convCharArr(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    int i = paramInt1;
    int j = paramInt3;
    while (i < paramInt2 && j < paramInt4) {
      char c = paramArrayOfchar[i];
      if (c < '') {
        paramArrayOfbyte[j++] = (byte)c;
      } else {
        byte b;
        if ((short)c >= 192 && c < 'ÿ') {
          b = (char)UNICODE_TO_TCVN3_IDX_PAGE1[c - 192];
        } else if (c >= 'Ā' && c < 'ƿ') {
          b = UNICODE_TO_TCVN3_IDX_PAGE2[c - 256];
        } else if (c >= 'Ạ' && c < 'ỿ') {
          b = UNICODE_TO_TCVN3_IDX_PAGE3[c - 7840];
        } else {
          b = 63;
        } 
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


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\TCVN3CharToByteConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */