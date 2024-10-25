package com.tmax.tibero.jdbc.data.charset;

public class MS932TILDEByteToCharConverter extends MS932DBByteToCharConverter {
  private JIS0201ByteToCharConverter JIS0201bc = new JIS0201ByteToCharConverter();
  
  protected char convSingleByte(int paramInt) {
    return ((paramInt & 0xFF80) == 0) ? (char)paramInt : this.JIS0201bc.getUnicode(paramInt);
  }
  
  protected char getUnicode(int paramInt1, int paramInt2) {
    if (paramInt1 == 129 && paramInt2 == 96)
      return '～'; 
    if (paramInt1 < 0 || paramInt1 > index1.length || paramInt2 < this.start || paramInt2 > this.end)
      return '�'; 
    int i = (index1[paramInt1] & 0xF) * (this.end - this.start + 1) + paramInt2 - this.start;
    return index2[index1[paramInt1] >> 4].charAt(i);
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\MS932TILDEByteToCharConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */