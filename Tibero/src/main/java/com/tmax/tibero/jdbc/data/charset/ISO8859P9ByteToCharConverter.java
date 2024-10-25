package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class ISO8859P9ByteToCharConverter {
  protected boolean subMode = true;
  
  protected char[] subChars = new char[] { '?' };
  
  private static final char[] ISO8859P9_TO_UNICODE_PAGE = new char[] { 
      'Ğ', 'Ñ', 'Ò', 'Ó', 'Ô', 'Õ', 'Ö', '×', 'Ø', 'Ù', 
      'Ú', 'Û', 'Ü', 'İ', 'Ş', 'ß', 'à', 'á', 'â', 'ã', 
      'ä', 'å', 'æ', 'ç', 'è', 'é', 'ê', 'ë', 'ì', 'í', 
      'î', 'ï', 'ğ', 'ñ', 'ò', 'ó', 'ô', 'õ', 'ö', '÷', 
      'ø', 'ù', 'ú', 'û', 'ü', 'ı', 'ş', 'ÿ' };
  
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


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\ISO8859P9ByteToCharConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */