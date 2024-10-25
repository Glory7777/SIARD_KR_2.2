package com.tmax.tibero.jdbc.data.charset;

public class MS950ExtByteToCharConverter {
  private static final char[] CP950EXT_TO_UNICODE_PAGEF9 = new char[] { 
      '碁', '銹', '裏', '墻', '恒', '粧', '嫺', '╔', '╦', '╗', 
      '╠', '╬', '╣', '╚', '╩', '╝', '╒', '╤', '╕', '╞', 
      '╪', '╡', '╘', '╧', '╛', '╓', '╥', '╖', '╟', '╫', 
      '╢', '╙', '╨', '╜', '║', '═', '╭', '╮', '╰', '╯', 
      '▓' };
  
  protected boolean subMode = true;
  
  protected char[] subChars = new char[] { '?' };
  
  public int convert(byte paramByte1, byte paramByte2, char[] paramArrayOfchar, int paramInt) {
    if (paramByte1 == -7 && ((paramByte2 >= 64 && paramByte2 < Byte.MAX_VALUE) || (paramByte2 >= -95 && paramByte2 < -1))) {
      byte b = ((paramByte2 & 0xFF) >= 161) ? 98 : 64;
      int i = 157 * ((paramByte1 & 0xFF) - 161) + (paramByte2 & 0xFF) - b;
      char c = '�';
      if (i >= 13932 && i < 13973)
        c = CP950EXT_TO_UNICODE_PAGEF9[i - 13932]; 
      if (c != '�') {
        paramArrayOfchar[paramInt] = (char)c;
        return 0;
      } 
    } 
    return -1;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\MS950ExtByteToCharConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */