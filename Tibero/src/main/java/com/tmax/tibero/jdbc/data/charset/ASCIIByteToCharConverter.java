package com.tmax.tibero.jdbc.data.charset;

public class ASCIIByteToCharConverter {
  protected boolean subMode = true;
  
  protected char[] subChars = new char[] { '?' };
  
  public int convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) {
    int i = 0;
    int j = 0;
    i = paramInt3;
    j = paramInt1;
    while (j < paramInt2 && i < paramInt4) {
      byte b = paramArrayOfbyte[j++];
      if (b >= 0) {
        paramArrayOfchar[i++] = (char)b;
        continue;
      } 
      if (this.subMode) {
        paramArrayOfchar[i++] = this.subChars[0];
        continue;
      } 
      paramArrayOfchar[i++] = this.subChars[0];
    } 
    return i - paramInt3;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\ASCIIByteToCharConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */