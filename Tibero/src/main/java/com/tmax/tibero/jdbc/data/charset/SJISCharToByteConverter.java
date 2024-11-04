package com.tmax.tibero.jdbc.data.charset;

public class SJISCharToByteConverter extends JIS0208CharToByteConverter {
  private JIS0201CharToByteConverter JIS0201cb = new JIS0201CharToByteConverter();
  
  protected int convDoubleByte(char paramChar) {
    int i = index1[paramChar >> 8] << 8;
    char c = this.index2[i >> 12].charAt((i & 0xFFF) + (paramChar & 0xFF));
    if (c == '\000')
      return 0; 
    int j = c >> 8 & 0xFF;
    int k = c & 0xFF;
    int b1 = (j >= 95) ? 176 : 112;
    byte b2 = (j % 2 != 1) ? 126 : (byte)((k <= 95) ? 31 : 32);
    return (j + 1 >> 1) + b1 << 8 | k + b2;
  }
  
  protected int convSingleByte(char paramChar, byte[] paramArrayOfbyte) {
    if ((paramChar & 0xFF80) == 0) {
      paramArrayOfbyte[0] = (byte)paramChar;
      return 1;
    } 
    byte b;
    if ((b = this.JIS0201cb.getNative(paramChar)) == 0)
      return 0; 
    paramArrayOfbyte[0] = b;
    return 1;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\SJISCharToByteConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */