package com.tmax.tibero.jdbc.data;

import com.tmax.tibero.jdbc.util.TbCommon;
import java.util.Calendar;
import java.util.TimeZone;

public class TbTimestamp {
  static final short OFFSET_CENTURY = 0;
  
  static final short OFFSET_YEAR = 1;
  
  static final short OFFSET_MONTH = 2;
  
  static final short OFFSET_DAY_OF_MONTH = 3;
  
  static final short OFFSET_HOUR_OF_DAY = 4;
  
  static final short OFFSET_MINUTE = 5;
  
  static final short OFFSET_SECOND = 6;
  
  static final short OFFSET_DATE_END = 7;
  
  public static final char DELIMITER_DATE = '-';
  
  public static final char DELIMITER_TIME = ':';
  
  public static final char DELIMITER_MILLIS = '.';
  
  public static final char DELIMITER_BLANK = ' ';
  
  private byte[] bytes;
  
  public TbTimestamp(byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte == null)
      throw new IllegalArgumentException("data should not be null"); 
    if (paramArrayOfbyte.length != 12)
      throw new IllegalArgumentException("invalid array size"); 
    this.bytes = paramArrayOfbyte;
  }
  
  public TbTimestamp(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
    this.bytes = new byte[12];
    if (paramInt7 > 999999999 || paramInt7 < 0)
      throw new IllegalArgumentException("nanos > 999999999 or < 0"); 
    this.bytes[0] = (byte)(paramInt1 / 100 + 100);
    this.bytes[1] = (byte)(paramInt1 % 100 + 100);
    this.bytes[2] = (byte)paramInt2;
    this.bytes[3] = (byte)paramInt3;
    this.bytes[4] = (byte)paramInt4;
    this.bytes[5] = (byte)paramInt5;
    this.bytes[6] = (byte)paramInt6;
    this.bytes[7] = 0;
    TbCommon.int2Bytes(paramInt7, this.bytes, 8, 4);
  }
  
  public TbTimestamp(long paramLong) {
    this.bytes = new byte[12];
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(paramLong);
    int i = calendar.get(1);
    int j = calendar.get(2) + 1;
    int k = calendar.get(5);
    int m = calendar.get(11);
    int n = calendar.get(12);
    int i1 = calendar.get(13);
    int i2 = (int)(paramLong % 1000L * 1000000L);
    if (i2 < 0)
      i2 = 1000000000 + i2; 
    this.bytes[0] = (byte)(i / 100 + 100);
    this.bytes[1] = (byte)(i % 100 + 100);
    this.bytes[2] = (byte)j;
    this.bytes[3] = (byte)k;
    this.bytes[4] = (byte)m;
    this.bytes[5] = (byte)n;
    this.bytes[6] = (byte)i1;
    this.bytes[7] = 0;
    TbCommon.int2Bytes(i2, this.bytes, 8, 4);
  }
  
  public TbTimestamp(long paramLong, TimeZone paramTimeZone) {
    this.bytes = new byte[12];
    Calendar calendar = Calendar.getInstance(paramTimeZone);
    calendar.setTimeInMillis(paramLong);
    int i = calendar.get(0);
    int j = calendar.get(1);
    if (i == 0)
      j = j * -1 + 1; 
    int k = calendar.get(2) + 1;
    int m = calendar.get(5);
    int n = calendar.get(11);
    int i1 = calendar.get(12);
    int i2 = calendar.get(13);
    int i3 = (int)(paramLong % 1000L * 1000000L);
    if (i3 < 0)
      i3 = 1000000000 + i3; 
    this.bytes[0] = (byte)(j / 100 + 100);
    this.bytes[1] = (byte)(j % 100 + 100);
    this.bytes[2] = (byte)k;
    this.bytes[3] = (byte)m;
    this.bytes[4] = (byte)n;
    this.bytes[5] = (byte)i1;
    this.bytes[6] = (byte)i2;
    this.bytes[7] = 0;
    TbCommon.int2Bytes(i3, this.bytes, 8, 4);
  }
  
  public byte[] getBytes() {
    return this.bytes;
  }
  
  public void setBytes(byte[] paramArrayOfbyte) {
    this.bytes = paramArrayOfbyte;
  }
  
  public int getYear() {
    int i = 0xFF & this.bytes[0];
    int j = 0xFF & this.bytes[1];
    return (i - 100) * 100 + j - 100;
  }
  
  public int getDayOfMonth() {
    return 0xFF & this.bytes[3];
  }
  
  public int getHourOfDay() {
    return 0xFF & this.bytes[4];
  }
  
  public int getMinutes() {
    return 0xFF & this.bytes[5];
  }
  
  public int getSeconds() {
    return 0xFF & this.bytes[6];
  }
  
  public int getMonth() {
    return 0xFF & this.bytes[2];
  }
  
  public int getNanos() {
    return TbCommon.bytes2Int(this.bytes, 8, 4);
  }
  
  public String toString() {
    char[] arrayOfChar = new char[30];
    int i = 0;
    int j = getYear();
    if (j < 10000 && j >= 0) {
      i += writeFormattedInt(arrayOfChar, i, 4, getYear());
    } else {
      String str1 = Integer.toString(j);
      byte b1;
      for (b1 = 0; b1 < str1.length(); b1++)
        arrayOfChar[i + b1] = str1.charAt(b1); 
      i += b1;
    } 
    arrayOfChar[i++] = '-';
    i += writeFormattedInt(arrayOfChar, i, 2, getMonth());
    arrayOfChar[i++] = '-';
    i += writeFormattedInt(arrayOfChar, i, 2, getDayOfMonth());
    arrayOfChar[i++] = ' ';
    i += writeFormattedInt(arrayOfChar, i, 2, getHourOfDay());
    arrayOfChar[i++] = ':';
    i += writeFormattedInt(arrayOfChar, i, 2, getMinutes());
    arrayOfChar[i++] = ':';
    i += writeFormattedInt(arrayOfChar, i, 2, getSeconds());
    int k = getNanos();
    if (k < 0)
      k = 0; 
    arrayOfChar[i++] = '.';
    String str = Integer.toString(k);
    int m = str.length();
    byte b;
    for (b = 0; b < 9; b++) {
      if (9 - b > m) {
        arrayOfChar[i + b] = '0';
      } else {
        arrayOfChar[i + b] = str.charAt(b - 9 + m);
      } 
    } 
    for (b = 8; b > 0 && arrayOfChar[i + b] == '0'; b--);
    i += b + 1;
    return new String(arrayOfChar, 0, i);
  }
  
  private static int writeFormattedInt(char[] paramArrayOfchar, int paramInt1, int paramInt2, int paramInt3) {
    for (int i = paramInt1 + paramInt2 - 1; i >= paramInt1; i--) {
      int j = paramInt3 % 10;
      paramArrayOfchar[i] = (char)(48 + j);
      paramInt3 /= 10;
    } 
    return paramInt2;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\TbTimestamp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */