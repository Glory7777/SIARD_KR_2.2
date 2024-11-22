package com.tmax.tibero.jdbc.data.charset;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public interface CharsetEncoder {
  int charsToBytes(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException;
  
  int charsToFixedBytes(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException;
  
  int getEndingBytePos(byte[] paramArrayOfbyte, int paramInt);
  
  int getLeadingBytePos(byte[] paramArrayOfbyte, int paramInt);
  
  int getMaxBytesPerChar();
  
  boolean isEndingByte(byte[] paramArrayOfbyte, int paramInt);
  
  boolean isLeadingByte(byte[] paramArrayOfbyte, int paramInt);
  
  byte[] stringToBytes(String paramString) throws SQLException;
  
  int stringToBytes(byte[] paramArrayOfbyte, int paramInt, String paramString) throws SQLException, UnsupportedEncodingException;
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\charset\CharsetEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */