package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public interface CharsetDecoder {
  int bytesToChars(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException;
  
  String bytesToString(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException;
  
  String bytesToString(byte[] paramArrayOfbyte) throws SQLException;
  
  int fixedBytesToChars(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException;
  
  String fixedBytesToString(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException;
  
  String fixedBytesToString(byte[] paramArrayOfbyte) throws SQLException;
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\charset\CharsetDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */