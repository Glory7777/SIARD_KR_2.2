package com.tmax.tibero.jdbc.data;

import java.util.Calendar;

public class TbDate {
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
  
  public static final char DELIMITER_BLANK = ' ';
  
  private byte[] data;
  
  public TbDate(byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte == null)
      throw new IllegalArgumentException("data should not be null"); 
    if (paramArrayOfbyte.length != 8)
      throw new IllegalArgumentException("invalid array size"); 
    this.data = paramArrayOfbyte;
  }
  
  public TbDate(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    this.data = new byte[8];
    this.data[0] = (byte)(paramInt1 / 100 + 100);
    this.data[1] = (byte)(paramInt1 % 100 + 100);
    this.data[2] = (byte)paramInt2;
    this.data[3] = (byte)paramInt3;
    this.data[4] = (byte)paramInt4;
    this.data[5] = (byte)paramInt5;
    this.data[6] = (byte)paramInt6;
    this.data[7] = 0;
  }
  
  public TbDate(long paramLong) {
    this.data = new byte[8];
    Calendar calendar = Calendar.getInstance();
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
    this.data[0] = (byte)(j / 100 + 100);
    this.data[1] = (byte)(j % 100 + 100);
    this.data[2] = (byte)k;
    this.data[3] = (byte)m;
    this.data[4] = (byte)n;
    this.data[5] = (byte)i1;
    this.data[6] = (byte)i2;
    this.data[7] = 0;
  }
  
  public byte[] getBytes() {
    return this.data;
  }
  
  public int getYear() {
    int i = 0xFF & this.data[0];
    int j = 0xFF & this.data[1];
    return (i - 100) * 100 + j - 100;
  }
  
  public int getMonth() {
    return 0xFF & this.data[2];
  }
  
  public int getDayOfMonth() {
    return 0xFF & this.data[3];
  }
  
  public int getHourOfDay() {
    return 0xFF & this.data[4];
  }
  
  public int getMinutes() {
    return 0xFF & this.data[5];
  }
  
  public int getSeconds() {
    return 0xFF & this.data[6];
  }
  
  public String toString() {
    char[] arrayOfChar = new char[20];
    int i = 0;
    int j = getYear();
    if (j < 10000 && j >= 0) {
      i += writeFormattedInt(arrayOfChar, i, 4, getYear());
    } else {
      String str = Integer.toString(j);
      byte b;
      for (b = 0; b < str.length(); b++)
        arrayOfChar[i + b] = str.charAt(b); 
      i += b;
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


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\TbDate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */