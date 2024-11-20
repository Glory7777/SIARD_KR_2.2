package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class ISO8859P5ByteToCharConverter {
  protected boolean subMode = true;
  
  protected char[] subChars = new char[] { '?' };
  
  private static final char[] ISO8859P5_TO_UNICODE_PAGE = new char[] { 
      '\u00A0', '\u0401', '\u0402', '\u0403', '\u0404', '\u0405', '\u0406', '\u0407', '\u0408', '\u0409', 
      '\u040A', '\u040B', '\u040C', '\u00AD', '\u040E', '\u040F', '\u0410', '\u0411', '\u0412', '\u0413', 
      '\u0414', '\u0415', '\u0416', '\u0417', '\u0418', '\u0419', '\u041A', '\u041B', '\u041C', '\u041D', 
      '\u041E', '\u041F', '\u0420', '\u0421', '\u0422', '\u0423', '\u0424', '\u0425', '\u0426', '\u0427', 
      '\u0428', '\u0429', '\u042A', '\u042B', '\u042C', '\u042D', '\u042E', '\u042F', '\u0430', '\u0431', 
      '\u0432', '\u0433', '\u0434', '\u0435', '\u0436', '\u0437', '\u0438', '\u0439', '\u043A', '\u043B', 
      '\u043C', '\u043D', '\u043E', '\u043F', '\u0440', '\u0441', '\u0442', '\u0443', '\u0444', '\u0445', 
      '\u0446', '\u0447', '\u0448', '\u0449', '\u044A', '\u044B', '\u044C', '\u044D', '\u044E', '\u044F', 
      '\u2116', '\u0451', '\u0452', '\u0453', '\u0454', '\u0455', '\u0456', '\u0457', '\u0458', '\u0459', 
      '\u045A', '\u045B', '\u045C', '\u00A7', '\u045E', '\u045F' };
  
  private int decodeFromUcs(byte paramByte1, byte paramByte2) {
    return (paramByte1 << 8) + (paramByte2 & 0xFF);
  }
  
  public int convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
    int i = paramInt1;
    int j = paramInt3;
    while (i < paramInt2) {
      int k = decodeFromUcs((byte)0, paramArrayOfbyte[i]);
      if (k < 160) {
        paramArrayOfchar[j++] = (char)k;
        i++;
        continue;
      } 
      k = ISO8859P5_TO_UNICODE_PAGE[k - 160];
      paramArrayOfchar[j++] = (char)k;
      i++;
    } 
    return j - paramInt3;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\charset\ISO8859P5ByteToCharConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */