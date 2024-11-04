package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class ISO8859P5ByteToCharConverter {
  protected boolean subMode = true;
  
  protected char[] subChars = new char[] { '?' };
  
  private static final char[] ISO8859P5_TO_UNICODE_PAGE = new char[] { 
      ' ', 'Ё', 'Ђ', 'Ѓ', 'Є', 'Ѕ', 'І', 'Ї', 'Ј', 'Љ', 
      'Њ', 'Ћ', 'Ќ', '­', 'Ў', 'Џ', 'А', 'Б', 'В', 'Г', 
      'Д', 'Е', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 
      'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 
      'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', 'а', 'б', 
      'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'й', 'к', 'л', 
      'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 
      'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', 
      '№', 'ё', 'ђ', 'ѓ', 'є', 'ѕ', 'і', 'ї', 'ј', 'љ', 
      'њ', 'ћ', 'ќ', '§', 'ў', 'џ' };
  
  private int decodeFromUcs(byte paramByte1, byte paramByte2) {
    return (paramByte1 << 8) + (paramByte2 & 0xFF);
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
      k = ISO8859P5_TO_UNICODE_PAGE[k - 160];
      paramArrayOfchar[j++] = (char)k;
      i++;
    } 
    return j - paramInt3;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\ISO8859P5ByteToCharConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */