package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class SingleByteEncoder implements CharsetEncoder {
  private static CharToByteSingleByte conv;
  
  private int maxBytePerChar;
  
  public SingleByteEncoder(short[] paramArrayOfshort, String paramString, int paramInt1, int paramInt2, int paramInt3) {
    conv = new CharToByteSingleByte(paramArrayOfshort, paramString, paramInt1, paramInt2, paramInt3);
    this.maxBytePerChar = conv.getMaxBytesPerChar();
  }
  
  public int charsToBytes(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    return conv.convert(paramArrayOfchar, paramInt1, paramInt1 + paramInt2, paramArrayOfbyte, paramInt3, paramInt3 + paramInt4);
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
    return this.maxBytePerChar;
  }
  
  public boolean isEndingByte(byte[] paramArrayOfbyte, int paramInt) {
    return true;
  }
  
  public boolean isLeadingByte(byte[] paramArrayOfbyte, int paramInt) {
    return true;
  }
  
  public byte[] stringToBytes(String paramString) throws SQLException {
    byte[] arrayOfByte1 = new byte[paramString.length() * getMaxBytesPerChar()];
    int i = conv.convert(paramString.toCharArray(), 0, paramString.length(), arrayOfByte1, 0, arrayOfByte1.length);
    byte[] arrayOfByte2 = new byte[i];
    System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, i);
    return arrayOfByte2;
  }
  
  public int stringToBytes(byte[] paramArrayOfbyte, int paramInt, String paramString) throws SQLException {
    return conv.convert(paramString.toCharArray(), 0, paramString.length(), paramArrayOfbyte, paramInt, paramArrayOfbyte.length);
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\SingleByteEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */