package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class MS1252CharToByteConverter {
  private static final short[] UNICODE_TO_MS1252_IDX_PAGE1 = new short[] { 
      0, 0, 140, 156, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 138, 154, 0, 0, 
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
      159, 0, 0, 0, 0, 142, 158, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 131, 0, 0, 0, 
      0, 0 };
  
  private static final char[] UNICODE_TO_MS1252_IDX_PAGE2 = new char[] { 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '', Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE };
  
  private static final char[] UNICODE_TO_MS1252_IDX_PAGE20 = new char[] { 
      Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '', '', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '', '', 
      '', Character.MIN_VALUE, '', '', '', Character.MIN_VALUE, '', '', '', Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, '', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, Character.MIN_VALUE, '', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, 
      Character.MIN_VALUE, '', '', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE };
  
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
        if (c >= ' ' && c < 'Ā') {
          b = (char)c;
        } else if ((short)c >= 336 && c < 'Ƙ') {
          b = (char)UNICODE_TO_MS1252_IDX_PAGE1[c - 336];
        } else if (c >= 'ˀ' && c < 'ˠ') {
          b = UNICODE_TO_MS1252_IDX_PAGE2[c - 704];
        } else if (c >= '‐' && c < '⁀') {
          b = UNICODE_TO_MS1252_IDX_PAGE20[c - 8208];
        } else if (c == '€') {
          b = 128;
        } else if (c == '™') {
          b = 153;
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


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\MS1252CharToByteConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */