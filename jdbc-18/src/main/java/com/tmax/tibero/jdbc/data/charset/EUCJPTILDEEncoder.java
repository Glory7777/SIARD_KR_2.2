package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class EUCJPTILDEEncoder implements CharsetEncoder {
  private EUCJPTILDECharToByteConverter conv = null;
  
  private int maxBytesPerChar = 0;
  
  public EUCJPTILDEEncoder() {
    this.conv = new EUCJPTILDECharToByteConverter();
    this.maxBytesPerChar = this.conv.getMaxBytesPerChar();
  }
  
  public int charsToBytes(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    return this.conv.convCharArr(paramArrayOfchar, paramInt1, paramInt1 + paramInt2, paramArrayOfbyte, paramInt3, paramInt3 + paramInt4);
  }
  
  public int charsToFixedBytes(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    return 0;
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
    for (i = paramInt; i - 1 >= 0 && (!isSingleByte(paramArrayOfbyte[i]) || !isSingleByte(paramArrayOfbyte[i - 1])); i--);
    while (i <= paramInt) {
      if (!isSingleByte(paramArrayOfbyte[i])) {
        if (i + 1 == paramInt)
          return false; 
        i++;
      } else if (i == paramInt) {
        return true;
      } 
      i++;
    } 
    return true;
  }
  
  private boolean isSingleByte(byte paramByte) {
    return (paramByte < 128) ? true : ((paramByte >= 161 && paramByte < 224));
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


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\charset\EUCJPTILDEEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */