package com.tmax.tibero.jdbc.data;

import com.tmax.tibero.jdbc.util.TbCommon;
import java.util.Calendar;

public class TbTime {
  static final short OFFSET_HOUR_OF_DAY = 0;
  
  static final short OFFSET_MINUTE = 1;
  
  static final short OFFSET_SECOND = 2;
  
  static final short OFFSET_DATE_END = 7;
  
  public static final char DELIMITER_TIME = ':';
  
  public static final char DELIMITER_BLANK = ' ';
  
  public static final char DELIMITER_MILLIS = '.';
  
  private byte[] data;
  
  public TbTime(byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte == null)
      throw new IllegalArgumentException("data should not be null"); 
    if (paramArrayOfbyte.length != 8)
      throw new IllegalArgumentException("invalid array size"); 
    this.data = paramArrayOfbyte;
  }
  
  public TbTime(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.data = new byte[8];
    if (paramInt4 > 999999999 || paramInt4 < 0)
      throw new IllegalArgumentException("nanos > 999999999 or < 0"); 
    this.data[0] = (byte)paramInt1;
    this.data[1] = (byte)paramInt2;
    this.data[2] = (byte)paramInt3;
    this.data[7] = 0;
    TbCommon.int2Bytes(paramInt4, this.data, 4, 4);
  }
  
  public TbTime(long paramLong) {
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
    int i3 = (int)(paramLong % 1000L * 1000000L);
    if (i3 < 0)
      i3 = 1000000000 + i3; 
    this.data[0] = (byte)n;
    this.data[1] = (byte)i1;
    this.data[2] = (byte)i2;
    this.data[7] = 0;
    TbCommon.int2Bytes(i3, this.data, 4, 4);
  }
  
  public byte[] getBytes() {
    return this.data;
  }
  
  public int getHourOfDay() {
    return 0xFF & this.data[0];
  }
  
  public int getMinutes() {
    return 0xFF & this.data[1];
  }
  
  public int getSeconds() {
    return 0xFF & this.data[2];
  }
  
  public int getNanos() {
    return TbCommon.bytes2Int(this.data, 4, 4);
  }
  
  public String toString() {
    char[] arrayOfChar = new char[20];
    int i = 0;
    i += writeFormattedInt(arrayOfChar, i, 2, getHourOfDay());
    arrayOfChar[i++] = ':';
    i += writeFormattedInt(arrayOfChar, i, 2, getMinutes());
    arrayOfChar[i++] = ':';
    i += writeFormattedInt(arrayOfChar, i, 2, getSeconds());
    int j = getNanos();
    if (j < 0)
      j = 0; 
    arrayOfChar[i++] = '.';
    String str = Integer.toString(j);
    int k = str.length();
    byte b;
    for (b = 0; b < 9; b++) {
      if (9 - b > k) {
        arrayOfChar[i + b] = '0';
      } else {
        arrayOfChar[i + b] = str.charAt(b - 9 + k);
      } 
    } 
    for (b = 4; b > 0 && arrayOfChar[i + b] == '0'; b--);
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


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\TbTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */