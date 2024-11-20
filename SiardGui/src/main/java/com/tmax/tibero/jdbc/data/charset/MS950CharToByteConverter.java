package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;

public class MS950CharToByteConverter {
  private HKSCS2001CharToByteConverter hkscs2001Converter = new HKSCS2001CharToByteConverter();
  
  private Big5CharToByteConverter big5Converter = new Big5CharToByteConverter();
  
  private Big5ExtCharToByteConverter big5ExtConverter = new Big5ExtCharToByteConverter();
  
  private MS950ExtCharToByteConverter ms950ExtConverter = new MS950ExtCharToByteConverter();
  
  private Big5_2003CharToByteConverter big52003Converter = new Big5_2003CharToByteConverter();
  
  private HKSCS1999CharToByteConverter hkscsConverter = new HKSCS1999CharToByteConverter();
  
  protected boolean subMode = true;
  
  protected byte[] subBytes = new byte[] { 63 };
  
  public int convCharArr(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    int i = paramInt1;
    int j = paramInt3;
    byte[] arrayOfByte = new byte[2];
    while (i < paramInt2 && j < paramInt4) {
      char c = paramArrayOfchar[i];
      if (c < '\u0080') {
        paramArrayOfbyte[j++] = (byte)c;
      } else {
        int k = this.hkscs2001Converter.convert(c, arrayOfByte, 0);
        if (k == 0) {
          paramArrayOfbyte[j++] = arrayOfByte[0];
          paramArrayOfbyte[j++] = arrayOfByte[1];
        } else if (this.ms950ExtConverter.convert(c, arrayOfByte, 0) == 0) {
          paramArrayOfbyte[j++] = arrayOfByte[0];
          paramArrayOfbyte[j++] = arrayOfByte[1];
        } else if (this.big5ExtConverter.convert(c, arrayOfByte, 0) == 0) {
          paramArrayOfbyte[j++] = arrayOfByte[0];
          paramArrayOfbyte[j++] = arrayOfByte[1];
        } else if (this.big5Converter.convert(c, arrayOfByte, 0) == 0) {
          paramArrayOfbyte[j++] = arrayOfByte[0];
          paramArrayOfbyte[j++] = arrayOfByte[1];
        } else if (this.big52003Converter.convert(c, arrayOfByte, 0) == 0) {
          paramArrayOfbyte[j++] = arrayOfByte[0];
          paramArrayOfbyte[j++] = arrayOfByte[1];
        } else if (this.hkscsConverter.convert(c, arrayOfByte, 0) == 0) {
          paramArrayOfbyte[j++] = arrayOfByte[0];
          paramArrayOfbyte[j++] = arrayOfByte[1];
        } else if (this.subMode) {
          paramArrayOfbyte[j++] = -95;
          paramArrayOfbyte[j++] = 72;
        } else {
          throw TbError.newSQLException(-590742, c);
        } 
      } 
      i++;
    } 
    return j - paramInt3;
  }
  
  public int convCharArr(int[] paramArrayOfint, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    int i = paramInt1;
    int j = paramInt3;
    byte[] arrayOfByte = new byte[2];
    while (i < paramInt2 && j < paramInt4) {
      int k = paramArrayOfint[i];
      if (k < 128) {
        paramArrayOfbyte[j++] = (byte)k;
      } else {
        int m = this.hkscs2001Converter.convert(k, arrayOfByte, 0);
        if (m == 0) {
          paramArrayOfbyte[j++] = arrayOfByte[0];
          paramArrayOfbyte[j++] = arrayOfByte[1];
        } else if (this.ms950ExtConverter.convert(k, arrayOfByte, 0) == 0) {
          paramArrayOfbyte[j++] = arrayOfByte[0];
          paramArrayOfbyte[j++] = arrayOfByte[1];
        } else if (this.big5ExtConverter.convert(k, arrayOfByte, 0) == 0) {
          paramArrayOfbyte[j++] = arrayOfByte[0];
          paramArrayOfbyte[j++] = arrayOfByte[1];
        } else if (this.big5Converter.convert(k, arrayOfByte, 0) == 0) {
          paramArrayOfbyte[j++] = arrayOfByte[0];
          paramArrayOfbyte[j++] = arrayOfByte[1];
        } else if (this.subMode) {
          paramArrayOfbyte[j++] = this.subBytes[0];
        } else {
          throw TbError.newSQLException(-590742, k);
        } 
      } 
      i++;
    } 
    return j - paramInt3;
  }
  
  public int convString(String paramString, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    return convCharArr(paramString.toCharArray(), paramInt1, paramInt2, paramArrayOfbyte, paramInt3, paramInt4);
  }
  
  public int getMaxBytesPerChar() {
    return 2;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\charset\MS950CharToByteConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */