package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class MS949Encoder implements CharsetEncoder {
  private MS949CharToByteConverter conv = null;
  
  private int maxBytesPerChar = 0;
  
  public MS949Encoder() {
    this.conv = new MS949CharToByteConverter();
    this.maxBytesPerChar = this.conv.getMaxBytesPerChar();
  }
  
  public int charsToBytes(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    return this.conv.convCharArr(paramArrayOfchar, paramInt1, paramInt1 + paramInt2, paramArrayOfbyte, paramInt3, paramInt3 + paramInt4);
  }
  
  public int charsToFixedBytes(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    int i = paramInt3;
    for (int j = paramInt1; j < paramInt1 + paramInt2; j++) {
      if (paramArrayOfchar[j] < '') {
        paramArrayOfbyte[i++] = 0;
        paramArrayOfbyte[i++] = (byte)paramArrayOfchar[j];
      } else {
        int k = this.conv.convCharArr(paramArrayOfchar, j, j + 1, paramArrayOfbyte, i, i + this.maxBytesPerChar);
        if (k == 0) {
          paramArrayOfbyte[i] = 0;
          paramArrayOfbyte[i + 1] = 0;
        } else if (k == 1) {
          paramArrayOfbyte[i + 1] = paramArrayOfbyte[i];
          paramArrayOfbyte[i] = 0;
        } 
        i += this.maxBytesPerChar;
      } 
    } 
    return i - paramInt3;
  }
  
  public int getEndingBytePos(byte[] paramArrayOfbyte, int paramInt) {
    return isEndingByte(paramArrayOfbyte, paramInt) ? paramInt : (paramInt + 1);
  }
  
  public int getLeadingBytePos(byte[] paramArrayOfbyte, int paramInt) {
    return isLeadingByte(paramArrayOfbyte, paramInt) ? paramInt : (paramInt - 1);
  }
  
  public int getMaxBytesPerChar() {
    return this.maxBytesPerChar;
  }
  
  public boolean isEndingByte(byte[] paramArrayOfbyte, int paramInt) {
    return (paramArrayOfbyte[paramInt] >= 0) ? true : (!isLeadingByte(paramArrayOfbyte, paramInt));
  }
  
  public boolean isLeadingByte(byte[] paramArrayOfbyte, int paramInt) {
    int i;
    for (i = paramInt; i >= 0 && paramArrayOfbyte[i] < 0; i--);
    return (i == paramInt || (paramInt - i) % 2 == 1);
  }
  
  public byte[] stringToBytes(String paramString) throws SQLException {
    byte[] arrayOfByte1 = new byte[paramString.length() * getMaxBytesPerChar()];
    int i = this.conv.convString(paramString, 0, paramString.length(), arrayOfByte1, 0, arrayOfByte1.length);
    byte[] arrayOfByte2 = new byte[i];
    System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, i);
    return arrayOfByte2;
  }
  
  public int stringToBytes(byte[] paramArrayOfbyte, int paramInt, String paramString) throws SQLException {
    return this.conv.convString(paramString, 0, paramString.length(), paramArrayOfbyte, paramInt, paramArrayOfbyte.length);
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\MS949Encoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */