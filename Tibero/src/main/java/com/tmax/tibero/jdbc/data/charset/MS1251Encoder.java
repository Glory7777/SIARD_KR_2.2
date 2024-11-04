package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class MS1251Encoder implements CharsetEncoder {
  private MS1251CharToByteConverter conv = null;
  
  private int maxBytesPerChar = 0;
  
  public MS1251Encoder() {
    this.conv = new MS1251CharToByteConverter();
    this.maxBytesPerChar = this.conv.getMaxBytesPerChar();
  }
  
  public int charsToBytes(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    return this.conv.convCharArr(paramArrayOfchar, paramInt1, paramInt1 + paramInt2, paramArrayOfbyte, paramInt3, paramInt3 + paramInt4);
  }
  
  public int charsToFixedBytes(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    return 0;
  }
  
  public int getEndingBytePos(byte[] paramArrayOfbyte, int paramInt) {
    return paramInt;
  }
  
  public int getLeadingBytePos(byte[] paramArrayOfbyte, int paramInt) {
    return paramInt;
  }
  
  public int getMaxBytesPerChar() {
    return this.maxBytesPerChar;
  }
  
  public boolean isEndingByte(byte[] paramArrayOfbyte, int paramInt) {
    return true;
  }
  
  public boolean isLeadingByte(byte[] paramArrayOfbyte, int paramInt) {
    return true;
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


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\MS1251Encoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */