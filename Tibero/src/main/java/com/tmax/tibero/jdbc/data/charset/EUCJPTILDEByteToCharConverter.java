package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;

public class EUCJPTILDEByteToCharConverter extends JIS0208ByteToCharConverter {
  private JIS0201ByteToCharConverter JIS0201bc = new JIS0201ByteToCharConverter();
  
  private JIS0212ByteToCharConverter JIS0212bc = new JIS0212ByteToCharConverter();
  
  short[] JIS0208Index1 = getIndex1();
  
  String[] JIS0208Index2 = getIndex2();
  
  protected char decode0212(int paramInt1, int paramInt2) throws SQLException {
    return (paramInt1 == -126 && paramInt2 == 55) ? '～' : this.JIS0212bc.getUnicode(paramInt1, paramInt2);
  }
  
  protected char getUnicode(int paramInt1, int paramInt2) throws SQLException {
    if (paramInt1 == 161 && paramInt2 == 193)
      return '～'; 
    if ((paramInt1 & 0xFF) == 161 && (paramInt2 & 0xFF) == 189)
      return '―'; 
    if (paramInt1 == 142)
      return this.JIS0201bc.getUnicode(paramInt2 - 256); 
    if (paramInt1 < 0 || paramInt1 > (getIndex1()).length || paramInt2 < this.start || paramInt2 > this.end)
      return '�'; 
    try {
      int i = (this.JIS0208Index1[paramInt1 - 128] & 0xF) * (this.end - this.start + 1) + paramInt2 - this.start;
      return this.JIS0208Index2[this.JIS0208Index1[paramInt1 - 128] >> 4].charAt(i);
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      throw TbError.newSQLException(-590743, arrayIndexOutOfBoundsException.getMessage());
    } 
  }
  
  public int convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
    int i = 0;
    int j = 0;
    i = paramInt3;
    char c = '�';
    for (j = paramInt1;; j += b) {
      byte b;
      if (j < paramInt2) {
        boolean bool = false;
        int k = paramArrayOfbyte[j] & 0xFF;
        b = 1;
        if ((k & 0x80) == 0) {
          c = (char)k;
        } else {
          int m = paramArrayOfbyte[j + 1] & 0xFF;
          if ((k & 0xFF) == 143) {
            if (m >= 161 && m < 255) {
              if (j + 3 > paramInt2)
                return i; 
              int n = paramArrayOfbyte[j + 2] & 0xFF;
              b += true;
              c = decode0212(m - 128, n - 128);
            } 
          } else {
            if (j + 2 > paramInt2)
              return i; 
            if (m >= 161 && m < 255) {
              b++;
              c = getUnicode(k, m);
            } 
          } 
          bool = (c == '�') ? true : false;
        } 
        if (bool) {
          char[] arrayOfChar;
          if (j + 2 > paramInt2)
            return i; 
          int m = paramArrayOfbyte[j + 1] & 0xFF;
          if (k == 143) {
            if (j + 3 > paramInt2)
              return i; 
            int n = paramArrayOfbyte[j + 2] & 0xFF;
            arrayOfChar = JIS0213ByteToCharConverter.decode(384 + m, n ^ 0x80);
          } else {
            arrayOfChar = JIS0213ByteToCharConverter.decode(128 + k, m ^ 0x80);
          } 
          if (arrayOfChar.length == 1) {
            c = arrayOfChar[0];
          } else {
            if (i + arrayOfChar.length > paramInt4)
              return i - paramInt3; 
            System.arraycopy(arrayOfChar, 0, paramArrayOfchar, i, arrayOfChar.length);
            i += arrayOfChar.length;
            j += b;
          } 
        } 
        if (i + 1 > paramInt4)
          return i - paramInt3; 
        paramArrayOfchar[i++] = c;
      } else {
        break;
      } 
    } 
    return i - paramInt3;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\EUCJPTILDEByteToCharConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */