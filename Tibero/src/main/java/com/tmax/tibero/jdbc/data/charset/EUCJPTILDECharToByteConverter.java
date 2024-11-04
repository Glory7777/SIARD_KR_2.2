package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;

public class EUCJPTILDECharToByteConverter extends JIS0208CharToByteConverter {
  private JIS0201CharToByteConverter JIS0201cb = new JIS0201CharToByteConverter();
  
  private JIS0212CharToByteConverter JIS0212cb = new JIS0212CharToByteConverter();
  
  short[] JIS0208Index1 = getIndex1();
  
  String[] JIS0208Index2 = getIndex2();
  
  final int MAX_BYTE_SIZE = 3;
  
  static final int[][] EUC_JISX0213__COMP_TABLE_DATA = EUCJPCharToByteConverter.EUC_JISX0213__COMP_TABLE_DATA;
  
  static final int[] EUC_JISX0213__COMBINED_UNICODE_TABLE = EUCJPCharToByteConverter.EUC_JISX0213__COMBINED_UNICODE_TABLE;
  
  public boolean canConvert(char paramChar) throws SQLException {
    byte[] arrayOfByte = new byte[3];
    return !(convSingleByte(paramChar, arrayOfByte) == 0 && convDoubleByte(paramChar) == 0);
  }
  
  protected int convDoubleByte(char paramChar) throws SQLException {
    if (paramChar == '〜' || paramChar == '～') {
      char c1 = '¡';
      char c2 = 'Á';
      return c1 << 8 | c2;
    } 
    if (paramChar == '―')
      paramChar = '—'; 
    try {
      int i = this.JIS0208Index1[(paramChar & 0xFF00) >> 8] << 8;
      char c = this.JIS0208Index2[i >> 12].charAt((i & 0xFFF) + (paramChar & 0xFF));
      if (c != '\000')
        return c + 32896; 
      int j = this.JIS0212cb.convDoubleByte(paramChar);
      return (j == 0) ? j : (j + 9404544);
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      throw TbError.newSQLException(-590743, arrayIndexOutOfBoundsException.getMessage());
    } 
  }
  
  public int getMaxBytesPerChar() {
    return 3;
  }
  
  protected int convSingleByte(char paramChar, byte[] paramArrayOfbyte) {
    if (paramChar == '\000') {
      paramArrayOfbyte[0] = 0;
      return 1;
    } 
    byte b;
    if ((b = this.JIS0201cb.getNative(paramChar)) == 0)
      return 0; 
    if (b > 0 && b < 128) {
      paramArrayOfbyte[0] = b;
      return 1;
    } 
    paramArrayOfbyte[0] = -114;
    paramArrayOfbyte[1] = b;
    return 2;
  }
  
  public int convCharArr(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    int i = 0;
    int j = 0;
    byte[] arrayOfByte = new byte[3];
    j = paramInt3;
    i = paramInt1;
    int k = 0;
    int m = 0;
    int n = 0;
    int[] arrayOfInt = JIS0213CharToByteConverter.JISX0213__CJK_EXT_B_DATA;
    while (i < paramInt2) {
      char c = paramArrayOfchar[i];
      int i1 = convSingleByte(c, arrayOfByte);
      k = m;
      m = parse(c, paramArrayOfchar, i, paramInt2);
      if ((m & 0xFFFF0000) >> 16 == 2)
        for (byte b1 = 0; b1 < arrayOfInt.length; b1++) {
          if (arrayOfInt[b1] == m) {
            int i2 = EUCJPCharToByteConverter.EUC_JISX0213__CJK_EXT_B_TABLE[b1];
            if (i2 > 65535) {
              arrayOfByte[0] = (byte)((i2 & 0xFF0000) >> 16);
              arrayOfByte[1] = (byte)((i2 & 0xFF00) >> 8);
              arrayOfByte[2] = (byte)(i2 & 0xFF);
              i1 = 3;
            } else {
              arrayOfByte[0] = (byte)((i2 & 0xFF00) >> 8);
              arrayOfByte[1] = (byte)(i2 & 0xFF);
              i1 = 2;
            } 
            i++;
          } 
        }  
      if (i1 == 0) {
        int i2 = convDoubleByte(c);
        if (i2 != 0) {
          if (i2 == 9415105 && paramInt2 - i > 1 && paramArrayOfchar[i + 1] == '̀') {
            arrayOfByte[0] = -85;
            arrayOfByte[1] = -60;
            i1 = 2;
            i++;
          } else if ((i2 & 0xFF0000) == 0) {
            arrayOfByte[0] = (byte)((i2 & 0xFF00) >> 8);
            arrayOfByte[1] = (byte)(i2 & 0xFF);
            i1 = 2;
          } else {
            arrayOfByte[0] = -113;
            arrayOfByte[1] = (byte)((i2 & 0xFF00) >> 8);
            arrayOfByte[2] = (byte)(i2 & 0xFF);
            i1 = 3;
          } 
        } else {
          if (k > 0) {
            int i3 = 0;
            int i4 = k << 16 | m;
            int[][] arrayOfInt1 = JIS0213ByteToCharConverter.JISX0213_TO_UCS__COMBINING;
            for (byte b1 = 0; b1 < arrayOfInt1.length; b1++) {
              if (i4 == (arrayOfInt1[b1][0] << 16 | arrayOfInt1[b1][1])) {
                i3 = EUC_JISX0213__COMBINED_UNICODE_TABLE[b1];
                break;
              } 
            } 
            if (i3 > 0) {
              paramArrayOfbyte[j - n] = (byte)(i3 >> 8 & 0xFF | 0x80);
              paramArrayOfbyte[j - n + 1] = (byte)(i3 & 0xFF | 0x80);
              j -= n - 2;
              continue;
            } 
          } 
          i2 = JIS0213CharToByteConverter.convFromUCS4(m);
          if (i2 != 0) {
            byte b1;
            byte b2;
            if (i2 == 741) {
              b1 = 1;
              b2 = 0;
            } else if (i2 == 745) {
              b1 = 1;
              b2 = 1;
            } else if (i2 == 768) {
              b1 = 2;
              b2 = 5;
            } else if (i2 == 769) {
              b1 = 7;
              b2 = 4;
            } else if (i2 == 12442) {
              b1 = 11;
              b2 = 14;
            } else {
              b1 = 0;
              b2 = -1;
            } 
            int i3 = i2 | 0x8080;
            while (b2 > 0 && EUC_JISX0213__COMP_TABLE_DATA[b1][0] != i3) {
              b1++;
              b2--;
            } 
            if (b2 > 0) {
              i3 = EUC_JISX0213__COMP_TABLE_DATA[b1][1];
              arrayOfByte[0] = (byte)(i3 >> 8 & 0xFF);
              arrayOfByte[1] = (byte)(i3 & 0xFF);
              i1 += 2;
            } else if ((i2 & 0x8000) != 0) {
              arrayOfByte[0] = -113;
              arrayOfByte[1] = (byte)(i2 >> 8 & 0xFF | 0x80);
              arrayOfByte[2] = (byte)(i2 & 0xFF | 0x80);
              i1 = 3;
            } else {
              arrayOfByte[0] = (byte)(i2 >> 8 & 0xFF | 0x80);
              arrayOfByte[1] = (byte)(i2 & 0xFF | 0x80);
              i1 = 2;
            } 
          } else {
            throw TbError.newSQLException(-590714, "unknown character");
          } 
        } 
      } 
      n = i1;
      if (paramInt4 - j < i1)
        throw TbError.newSQLException(-590714, "Conversion buffer overflow"); 
      for (byte b = 0; b < i1; b++)
        paramArrayOfbyte[j++] = arrayOfByte[b]; 
      continue;
      i++;
    } 
    return j - paramInt3;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\EUCJPTILDECharToByteConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */