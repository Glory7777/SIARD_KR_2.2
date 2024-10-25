package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class ISO8859P2ByteToCharConverter {
  protected boolean subMode = true;
  
  protected char[] subChars = new char[] { '?' };
  
  private static final char[] ISO8859P2_TO_UNICODE_PAGE = new char[] { 
      ' ', 'Ą', '˘', 'Ł', '¤', 'Ľ', 'Ś', '§', '¨', 'Š', 
      'Ş', 'Ť', 'Ź', '­', 'Ž', 'Ż', '°', 'ą', '˛', 'ł', 
      '´', 'ľ', 'ś', 'ˇ', '¸', 'š', 'ş', 'ť', 'ź', '˝', 
      'ž', 'ż', 'Ŕ', 'Á', 'Â', 'Ă', 'Ä', 'Ĺ', 'Ć', 'Ç', 
      'Č', 'É', 'Ę', 'Ë', 'Ě', 'Í', 'Î', 'Ď', 'Đ', 'Ń', 
      'Ň', 'Ó', 'Ô', 'Ő', 'Ö', '×', 'Ř', 'Ů', 'Ú', 'Ű', 
      'Ü', 'Ý', 'Ţ', 'ß', 'ŕ', 'á', 'â', 'ă', 'ä', 'ĺ', 
      'ć', 'ç', 'č', 'é', 'ę', 'ë', 'ě', 'í', 'î', 'ď', 
      'đ', 'ń', 'ň', 'ó', 'ô', 'ő', 'ö', '÷', 'ř', 'ů', 
      'ú', 'ű', 'ü', 'ý', 'ţ', '˙' };
  
  private int decodeFromUcs(byte paramByte1, byte paramByte2) {
    return (paramByte1 << 8) + (paramByte2 & 0xFF);
  }
  
  private void encodeUCharToUCS2(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
    boolean bool = false;
    byte b = (byte)paramInt2;
    paramArrayOfchar[paramInt1] = (char)bool;
    paramArrayOfchar[paramInt1++] = (char)b;
  }
  
  public int convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
    int i = paramInt1;
    int j = paramInt3;
    while (i < paramInt2) {
      int k = decodeFromUcs((byte)0, paramArrayOfbyte[i]);
      if (k < 160) {
        paramArrayOfchar[j++] = (char)k;
        i++;
        continue;
      } 
      k = ISO8859P2_TO_UNICODE_PAGE[k - 160];
      paramArrayOfchar[j++] = (char)k;
      i++;
    } 
    return j - paramInt3;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\ISO8859P2ByteToCharConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */