package com.tmax.tibero.jdbc.data.charset;

public class ByteToCharSingleByte {
  protected boolean subMode = true;
  
  protected char[] subChars = new char[] { '?' };
  
  protected String byteToCharTable;
  
  public ByteToCharSingleByte() {}
  
  public ByteToCharSingleByte(String paramString) {
    this.byteToCharTable = paramString;
  }
  
  public ByteToCharSingleByte(String paramString, boolean paramBoolean, char[] paramArrayOfchar) {
    this.byteToCharTable = paramString;
    this.subMode = paramBoolean;
    this.subChars = paramArrayOfchar;
  }
  
  protected char getUnicode(int paramInt) {
    int i = paramInt + 128;
    return (i >= this.byteToCharTable.length() || i < 0) ? '\uFFFD' : this.byteToCharTable.charAt(i);
  }
  
  public int convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) {
    int i = 0;
    int j = 0;
    i = paramInt3;
    for (j = paramInt1; j < paramInt2; j++) {
      byte b = paramArrayOfbyte[j];
      char c = getUnicode(b);
      if (c == '\uFFFD')
        if (this.subMode) {
          c = this.subChars[0];
        } else {
          c = this.subChars[0];
        }  
      if (i >= paramInt4)
        c = this.subChars[0]; 
      paramArrayOfchar[i] = c;
      i++;
    } 
    return i - paramInt3;
  }
  
  public String getByteToCharTable() {
    return this.byteToCharTable;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\charset\ByteToCharSingleByte.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */