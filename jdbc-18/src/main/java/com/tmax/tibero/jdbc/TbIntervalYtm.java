package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.util.TbCommon;
import java.text.NumberFormat;

public class TbIntervalYtm {
  private final int MID_INT = 1073741824;
  
  public static final int TB_INTERVAL_YTM_SIZE = 5;
  
  private byte[] bytes;
  
  private int precision = 2;
  
  public TbIntervalYtm(byte[] paramArrayOfbyte, int paramInt) {
    this.bytes = paramArrayOfbyte;
    if (paramInt >= 0)
      this.precision = paramInt; 
  }
  
  public TbIntervalYtm(int paramInt1, int paramInt2) {
    if (this.bytes == null)
      this.bytes = new byte[5]; 
    paramInt1 += 1073741824;
    TbCommon.int2Bytes(paramInt1, this.bytes, 0, 4);
    paramInt2 += 11;
    this.bytes[4] = (byte)paramInt2;
  }
  
  public String toString() {
    int i = getIntFromBigEndianBytes(this.bytes, 0);
    int j = (0xFF & this.bytes[4]) - 11;
    boolean bool = (i >= 0 && j >= 0) ? true : false;
    NumberFormat numberFormat1 = NumberFormat.getIntegerInstance();
    numberFormat1.setGroupingUsed(false);
    numberFormat1.setMinimumIntegerDigits(this.precision);
    i = Math.abs(i);
    NumberFormat numberFormat2 = NumberFormat.getIntegerInstance();
    numberFormat2.setGroupingUsed(false);
    numberFormat2.setMinimumIntegerDigits(2);
    j = Math.abs(j);
    StringBuffer stringBuffer = new StringBuffer();
    if (bool) {
      stringBuffer.append("+");
    } else {
      stringBuffer.append("-");
    } 
    stringBuffer.append(numberFormat1.format(i) + "-" + numberFormat2.format(j));
    return stringBuffer.toString();
  }
  
  private int getIntFromBigEndianBytes(byte[] paramArrayOfbyte, int paramInt) {
    int i = 0;
    for (byte b = 0; b < 4; b++)
      i |= (0xFF & paramArrayOfbyte[paramInt + b]) << 8 * (3 - b); 
    i -= 1073741824;
    return i;
  }
  
  public byte[] getBytes() {
    return this.bytes;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\TbIntervalYtm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */