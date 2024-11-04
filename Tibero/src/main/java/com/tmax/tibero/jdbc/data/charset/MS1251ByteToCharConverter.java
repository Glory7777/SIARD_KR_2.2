package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;

public class MS1251ByteToCharConverter {
  protected boolean subMode = true;
  
  protected char[] subChars = new char[] { '?' };
  
  private static final char[] MS1251_TO_UNICODE_PAGE = new char[] { 
      'Ђ', 'Ѓ', '‚', 'ѓ', '„', '…', '†', '‡', '€', '‰', 
      'Љ', '‹', 'Њ', 'Ќ', 'Ћ', 'Џ', 'ђ', '‘', '’', '“', 
      '”', '•', '–', '—', '�', '™', 'љ', '›', 'њ', 'ќ', 
      'ћ', 'џ', ' ', 'Ў', 'ў', 'Ј', '¤', 'Ґ', '¦', '§', 
      'Ё', '©', 'Є', '«', '¬', '­', '®', 'Ї', '°', '±', 
      'І', 'і', 'ґ', 'µ', '¶', '·', 'ё', '№', 'є', '»', 
      'ј', 'Ѕ', 'ѕ', 'ї', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 
      'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 
      'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 
      'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', 'а', 'б', 'в', 'г', 
      'д', 'е', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 
      'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 
      'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я' };
  
  private int decodeFromUcs(byte paramByte1, byte paramByte2) {
    return (paramByte1 << 8) + (paramByte2 & 0xFF);
  }
  
  private void encodeUCharToUCS2(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
    int bool = 0;
    byte b = (byte)paramInt2;
    paramArrayOfchar[paramInt1] = (char)bool;
    paramArrayOfchar[paramInt1++] = (char)b;
  }
  
  public int convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
    int i = paramInt1;
    int j = paramInt3;
    while (i < paramInt2) {
      int k = decodeFromUcs((byte)0, paramArrayOfbyte[i]);
      if (k < 128) {
        paramArrayOfchar[j++] = (char)k;
        i++;
        continue;
      } 
      if (k == 152) {
        paramArrayOfchar[j++] = '';
        i++;
        continue;
      } 
      int m = k - 128;
      k = MS1251_TO_UNICODE_PAGE[m];
      if (k != 65533) {
        paramArrayOfchar[j++] = (char)k;
        i++;
        continue;
      } 
      if (this.subMode) {
        encodeUCharToUCS2(paramArrayOfchar, j, this.subChars[0]);
        j++;
        continue;
      } 
      throw TbError.newSQLException(-590742, k);
    } 
    return j - paramInt3;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\MS1251ByteToCharConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */