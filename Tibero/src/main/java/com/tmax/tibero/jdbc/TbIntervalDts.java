package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.util.TbCommon;
import java.text.NumberFormat;

public class TbIntervalDts {
  private final int MID_INT = 1073741824;
  
  public static final int TB_INTERVAL_DTS_SIZE = 12;
  
  private byte[] bytes;
  
  private int precision = 2;
  
  private int scale = 6;
  
  public TbIntervalDts(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    this.bytes = paramArrayOfbyte;
    if (paramInt1 >= 0)
      this.precision = paramInt1; 
    if (paramInt2 >= 0)
      this.scale = paramInt2; 
  }
  
  public TbIntervalDts(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    if (this.bytes == null)
      this.bytes = new byte[12]; 
    getBigEndianBytesFromInt(paramInt1, this.bytes, 0);
    int i = paramInt2 * 3600 + paramInt3 * 60 + paramInt4;
    getBigEndianBytesFromInt(i, this.bytes, 4);
    getBigEndianBytesFromInt(paramInt5, this.bytes, 8);
  }
  
  public String toString() {
    byte b = 9;
    boolean bool = true;
    int i = getIntFromBigEndianBytes(this.bytes, 0);
    int j = getIntFromBigEndianBytes(this.bytes, 4);
    int k = getIntFromBigEndianBytes(this.bytes, 8);
    j = Math.abs(j);
    int m = j / 3600;
    j -= m * 3600;
    int n = j / 60;
    j -= n * 60;
    int i1 = j;
    NumberFormat numberFormat1 = NumberFormat.getInstance();
    numberFormat1.setMinimumIntegerDigits(2);
    NumberFormat numberFormat2 = NumberFormat.getInstance();
    numberFormat2.setGroupingUsed(false);
    numberFormat2.setMinimumIntegerDigits(9);
    k = Math.abs(k);
    String str = numberFormat2.format(k);
    str = str.substring(0, this.scale);
    NumberFormat numberFormat3 = NumberFormat.getInstance();
    numberFormat3.setGroupingUsed(false);
    numberFormat3.setMinimumIntegerDigits(this.precision);
    StringBuffer stringBuffer = new StringBuffer();
    if (i >= 0)
      stringBuffer.append("+"); 
    stringBuffer.append(numberFormat3.format(i) + " " + numberFormat1.format(m) + ":" + numberFormat1.format(n) + ":" + numberFormat1.format(i1) + "." + str);
    return stringBuffer.toString();
  }
  
  private int getIntFromBigEndianBytes(byte[] paramArrayOfbyte, int paramInt) {
    int i = 0;
    for (byte b = 0; b < 4; b++)
      i |= (0xFF & paramArrayOfbyte[paramInt + b]) << 8 * (3 - b); 
    i -= 1073741824;
    return i;
  }
  
  private void getBigEndianBytesFromInt(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
    paramInt1 += 1073741824;
    TbCommon.int2Bytes(paramInt1, paramArrayOfbyte, paramInt2, 4);
  }
  
  public byte[] getBytes() {
    return this.bytes;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\TbIntervalDts.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */