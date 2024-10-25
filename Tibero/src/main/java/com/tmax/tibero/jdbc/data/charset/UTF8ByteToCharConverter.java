package com.tmax.tibero.jdbc.data.charset;

public class UTF8ByteToCharConverter {
  protected boolean subMode;
  
  protected char[] subChars = new char[] { '?' };
  
  private byte[] savedBytes = new byte[5];
  
  public int convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) {
    int i = 0;
    int j = 0;
    byte b1 = 0;
    char[] arrayOfChar = new char[2];
    byte b2 = 0;
    if (b1) {
      byte[] arrayOfByte = new byte[paramInt2 - paramInt1 + b1];
      for (byte b = 0; b < b1; b++)
        arrayOfByte[b] = this.savedBytes[b]; 
      System.arraycopy(paramArrayOfbyte, paramInt1, arrayOfByte, b1, paramInt2 - paramInt1);
      paramArrayOfbyte = arrayOfByte;
      paramInt1 = 0;
      paramInt2 = arrayOfByte.length;
      b2 = -b1;
      b1 = 0;
    } 
    i = paramInt3;
    j = paramInt1;
    while (j < paramInt2) {
      byte b3;
      int k = j;
      int m = paramArrayOfbyte[j++] & 0xFF;
      if ((m & 0x80) == 0) {
        arrayOfChar[0] = (char)m;
        b3 = 1;
      } else if ((m & 0xE0) == 192) {
        if (j >= paramInt2) {
          b1 = 1;
          this.savedBytes[0] = (byte)m;
          break;
        } 
        int n = paramArrayOfbyte[j++] & 0xFF;
        if ((n & 0xC0) != 128) {
          j += b2;
          arrayOfChar[0] = this.subChars[0];
          b3 = 1;
        } else {
          arrayOfChar[0] = (char)((m & 0x1F) << 6 | n & 0x3F);
          b3 = 1;
        } 
      } else if ((m & 0xF0) == 224) {
        if (j + 1 >= paramInt2) {
          this.savedBytes[0] = (byte)m;
          if (j >= paramInt2) {
            b1 = 1;
            break;
          } 
          b1 = 2;
          this.savedBytes[1] = paramArrayOfbyte[j++];
          break;
        } 
        int n = paramArrayOfbyte[j++] & 0xFF;
        int i1 = paramArrayOfbyte[j++] & 0xFF;
        if ((n & 0xC0) != 128 || (i1 & 0xC0) != 128) {
          j += b2;
          arrayOfChar[0] = this.subChars[0];
          b3 = 1;
        } else {
          arrayOfChar[0] = (char)((m & 0xF) << 12 | (n & 0x3F) << 6 | i1 & 0x3F);
          b3 = 1;
        } 
      } else if ((m & 0xF8) == 240) {
        if (j + 2 >= paramInt2) {
          this.savedBytes[0] = (byte)m;
          if (j >= paramInt2) {
            b1 = 1;
            break;
          } 
          if (j + 1 >= paramInt2) {
            b1 = 2;
            this.savedBytes[1] = paramArrayOfbyte[j++];
            break;
          } 
          b1 = 3;
          this.savedBytes[1] = paramArrayOfbyte[j++];
          this.savedBytes[2] = paramArrayOfbyte[j++];
          break;
        } 
        int n = paramArrayOfbyte[j++] & 0xFF;
        int i1 = paramArrayOfbyte[j++] & 0xFF;
        int i2 = paramArrayOfbyte[j++] & 0xFF;
        if ((n & 0xC0) != 128 || (i1 & 0xC0) != 128 || (i2 & 0xC0) != 128) {
          j += b2;
          arrayOfChar[0] = this.subChars[0];
          b3 = 1;
        } else {
          int i3 = (0x7 & m) << 18 | (0x3F & n) << 12 | (0x3F & i1) << 6 | 0x3F & i2;
          arrayOfChar[0] = (char)((i3 - 65536) / 1024 + 55296);
          arrayOfChar[1] = (char)((i3 - 65536) % 1024 + 56320);
          b3 = 2;
        } 
      } else {
        j += b2;
        arrayOfChar[0] = this.subChars[0];
        b3 = 1;
      } 
      if (i + b3 > paramInt4) {
        j = k;
        j += b2;
        arrayOfChar[0] = this.subChars[0];
        b3 = 1;
      } 
      for (byte b4 = 0; b4 < b3; b4++)
        paramArrayOfchar[i + b4] = arrayOfChar[b4]; 
      i += b3;
    } 
    j += b2;
    return i - paramInt3;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\UTF8ByteToCharConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */