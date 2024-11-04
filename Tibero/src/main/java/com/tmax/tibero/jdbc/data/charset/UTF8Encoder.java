package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;

public class UTF8Encoder implements CharsetEncoder {
  private UTF8CharToByteConverter conv = null;
  
  private int maxBytesPerChar = 0;
  
  public UTF8Encoder() {
    this.conv = new UTF8CharToByteConverter();
    this.maxBytesPerChar = this.conv.getMaxBytesPerChar();
  }
  
  public int charsToBytes(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    return this.conv.convCharArr(paramArrayOfchar, paramInt1, paramInt1 + paramInt2, paramArrayOfbyte, paramInt3, paramInt3 + paramInt4);
  }
  
  public int charsToFixedBytes(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    throw TbError.newSQLException(-590714);
  }
  
  public int getEndingBytePos(byte[] paramArrayOfbyte, int paramInt) {
    if (paramArrayOfbyte[paramInt] >= 0)
      return paramInt; 
    int i = getLeadingBytePos(paramArrayOfbyte, paramInt);
    return (paramArrayOfbyte[i] >= -32) ? (i + 2) : (i + 1);
  }
  
  public int getLeadingBytePos(byte[] paramArrayOfbyte, int paramInt) {
    int i;
    for (i = paramInt; (paramArrayOfbyte[i] & 0x80) != 0 && (paramArrayOfbyte[i] & 0x40) == 0; i--);
    return i;
  }
  
  public int getMaxBytesPerChar() {
    return this.maxBytesPerChar;
  }
  
  public boolean isEndingByte(byte[] paramArrayOfbyte, int paramInt) {
    if (paramArrayOfbyte[paramInt] >= 0)
      return true; 
    int i = getLeadingBytePos(paramArrayOfbyte, paramInt);
    return (paramArrayOfbyte[i] >= -32) ? ((paramInt == i + 2)) : ((paramInt == i + 1));
  }
  
  public boolean isLeadingByte(byte[] paramArrayOfbyte, int paramInt) {
    byte b = paramArrayOfbyte[paramInt];
    return ((b & 0x80) == 0 || (b & 0x40) != 0);
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


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\UTF8Encoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */