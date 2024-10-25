package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class ISO8859P1ByteToCharConverter {
  protected boolean subMode = true;
  
  protected char[] subChars = new char[] { '?' };
  
  private int decodeFromUcs(byte paramByte1, byte paramByte2) {
    return (paramByte1 << 8) + (paramByte2 & 0xFF);
  }
  
  public int convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
    int i = paramInt1;
    int j = paramInt3;
    while (i < paramInt2) {
      int k = decodeFromUcs((byte)0, paramArrayOfbyte[i]);
      paramArrayOfchar[j++] = (char)k;
      i++;
    } 
    return j - paramInt3;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\ISO8859P1ByteToCharConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */