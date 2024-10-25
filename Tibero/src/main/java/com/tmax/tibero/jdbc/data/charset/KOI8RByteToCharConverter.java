package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class KOI8RByteToCharConverter {
  protected boolean subMode = true;
  
  protected char[] subChars = new char[] { '?' };
  
  private static final char[] KOI8R_TO_UNICODE_PAGE = new char[] { 
      '─', '│', '┌', '┐', '└', '┘', '├', '┤', '┬', '┴', 
      '┼', '▀', '▄', '█', '▌', '▐', '░', '▒', '▓', '⌠', 
      '■', '∙', '√', '≈', '≤', '≥', ' ', '⌡', '°', '²', 
      '·', '÷', '═', '║', '╒', 'ё', '╓', '╔', '╕', '╖', 
      '╗', '╘', '╙', '╚', '╛', '╜', '╝', '╞', '╟', '╠', 
      '╡', 'Ё', '╢', '╣', '╤', '╥', '╦', '╧', '╨', '╩', 
      '╪', '╫', '╬', '©', 'ю', 'а', 'б', 'ц', 'д', 'е', 
      'ф', 'г', 'х', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 
      'п', 'я', 'р', 'с', 'т', 'у', 'ж', 'в', 'ь', 'ы', 
      'з', 'ш', 'э', 'щ', 'ч', 'ъ', 'Ю', 'А', 'Б', 'Ц', 
      'Д', 'Е', 'Ф', 'Г', 'Х', 'И', 'Й', 'К', 'Л', 'М', 
      'Н', 'О', 'П', 'Я', 'Р', 'С', 'Т', 'У', 'Ж', 'В', 
      'Ь', 'Ы', 'З', 'Ш', 'Э', 'Щ', 'Ч', 'Ъ' };
  
  private int decodeFromUcs(byte paramByte1, byte paramByte2) {
    return (paramByte1 << 8) + (paramByte2 & 0xFF);
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
      k = KOI8R_TO_UNICODE_PAGE[k - 128];
      paramArrayOfchar[j++] = (char)k;
      i++;
    } 
    return j - paramInt3;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\KOI8RByteToCharConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */