package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;

public class ByteToCharDoubleByte {
  protected boolean subMode = true;
  
  protected char[] subChars = new char[] { '?' };
  
  protected short[] index1;
  
  protected String[] index2;
  
  protected int start;
  
  protected int end;
  
  protected static final char REPLACE_CHAR = '�';
  
  public int convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
    int i = 0;
    int j = 0;
    byte b = 0;
    i = paramInt3;
    for (j = paramInt1; j < paramInt2; j += b1) {
      byte b1;
      int k;
      if (!b) {
        k = paramArrayOfbyte[j];
        b1 = 1;
      } else {
        k = b;
        b = 0;
        b1 = 0;
      } 
      char c = convSingleByte(k);
      if (c == '�') {
        if (j + b1 >= paramInt2) {
          b = (byte)k;
          j += b1;
          break;
        } 
        k &= 0xFF;
        int m = paramArrayOfbyte[j + b1] & 0xFF;
        b1++;
        c = getUnicode(k, m);
      } 
      if (c == '�')
        if (this.subMode) {
          c = this.subChars[0];
        } else {
          c = this.subChars[0];
        }  
      if (i >= paramInt4)
        c = this.subChars[0]; 
      paramArrayOfchar[i++] = c;
    } 
    return i - paramInt3;
  }
  
  protected char convSingleByte(int paramInt) {
    return (paramInt >= 0) ? (char)paramInt : '�';
  }
  
  public short[] getIndex1() {
    return this.index1;
  }
  
  public String[] getIndex2() {
    return this.index2;
  }
  
  protected char getUnicode(int paramInt1, int paramInt2) throws SQLException {
    if (paramInt1 < 0 || paramInt1 > this.index1.length || paramInt2 < this.start || paramInt2 > this.end)
      return '�'; 
    try {
      int i = (this.index1[paramInt1] & 0xF) * (this.end - this.start + 1) + paramInt2 - this.start;
      return this.index2[this.index1[paramInt1] >> 4].charAt(i);
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      throw TbError.newSQLException(-590743, arrayIndexOutOfBoundsException.getMessage());
    } 
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\ByteToCharDoubleByte.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */