package com.tmax.tibero.jdbc.util;

public class TbCommon {
  private static byte[] POSITIVE_INFINITY_FLOAT_BYTES = new byte[] { -1, Byte.MIN_VALUE, 0, 0 };
  
  private static byte[] NEGATIVE_INFINITY_FLOAT_BYTES = new byte[] { 0, Byte.MAX_VALUE, -1, -1 };
  
  private static byte[] MAX_VALUE_FLOAT_BYTES = new byte[] { Byte.MAX_VALUE, Byte.MAX_VALUE, -1, -1 };
  
  private static byte[] MIN_VALUE_FLOAT_BYTES = new byte[] { 0, 0, 0, 1 };
  
  private static byte[] NAN_FLOAT_BYTES = new byte[] { -1, -64, 0, 0 };
  
  private static byte[] NEGATIVE_ZERO_FLOAT_BYTES = new byte[] { Byte.MIN_VALUE, 0, 0, 0 };
  
  private static byte[] POSITIVE_INFINITY_DOUBLE_BYTES = new byte[] { -1, -16, 0, 0, 0, 0, 0, 0 };
  
  private static byte[] NEGATIVE_INFINITY_DOUBLE_BYTES = new byte[] { 0, 15, -1, -1, -1, -1, -1, -1 };
  
  private static byte[] MAX_VALUE_DOUBLE_BYTES = new byte[] { Byte.MAX_VALUE, -17, -1, -1, -1, -1, -1, -1 };
  
  private static byte[] MIN_VALUE_DOUBLE_BYTES = new byte[] { 0, 0, 0, 0, 0, 0, 0, 1 };
  
  private static byte[] NAN_DOUBLE_BYTES = new byte[] { -1, -8, 0, 0, 0, 0, 0, 0 };
  
  private static byte[] NEGATIVE_ZERO_DOUBLE_BYTES = new byte[] { Byte.MIN_VALUE, 0, 0, 0, 0, 0, 0, 0 };
  
  private static boolean equalsBytes(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2) {
    if (paramArrayOfbyte1 == null || paramArrayOfbyte2 == null)
      return false; 
    int i = paramArrayOfbyte1.length;
    if (paramInt2 != i)
      return false; 
    for (byte b = 0; b < i; b++) {
      if (paramArrayOfbyte1[b] != paramArrayOfbyte2[paramInt1 + b])
        return false; 
    } 
    return true;
  }
  
  public static boolean isPositiveInfinityFloatBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    return equalsBytes(POSITIVE_INFINITY_FLOAT_BYTES, paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public static boolean isNegativeInfinityFloatBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    return equalsBytes(NEGATIVE_INFINITY_FLOAT_BYTES, paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public static boolean isMaxValueFloatBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    return equalsBytes(MAX_VALUE_FLOAT_BYTES, paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public static boolean isMinValueFloatBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    return equalsBytes(MIN_VALUE_FLOAT_BYTES, paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public static boolean isNanFloatBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    return equalsBytes(NAN_FLOAT_BYTES, paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public static boolean isNegativeZeroFloatBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    return equalsBytes(NEGATIVE_ZERO_FLOAT_BYTES, paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public static boolean isPositiveInfinityDoubleBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    return equalsBytes(POSITIVE_INFINITY_DOUBLE_BYTES, paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public static boolean isNegativeInfinityDoubleBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    return equalsBytes(NEGATIVE_INFINITY_DOUBLE_BYTES, paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public static boolean isMaxValueDoubleBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    return equalsBytes(MAX_VALUE_DOUBLE_BYTES, paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public static boolean isMinValueDoubleBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    return equalsBytes(MIN_VALUE_DOUBLE_BYTES, paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public static boolean isNanDoubleBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    return equalsBytes(NAN_DOUBLE_BYTES, paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public static boolean isNegativeZeroDoubleBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    return equalsBytes(NEGATIVE_ZERO_DOUBLE_BYTES, paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public static int bytes2Int(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    int i = 0;
    for (byte b = 0; b < paramInt2; b++)
      i |= (0xFF & paramArrayOfbyte[paramInt1 + b]) << 8 * (paramInt2 - 1 - b); 
    return i;
  }
  
  public static int bytes2IntR(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    int i = 0;
    for (byte b = 0; b < paramInt2; b++)
      i |= (0xFF & paramArrayOfbyte[paramInt1 + b]) << 8 * b; 
    return i;
  }
  
  public static long bytes2Long(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    long l = 0L;
    for (byte b = 0; b < paramInt2; b++)
      l |= (0xFFL & paramArrayOfbyte[paramInt1 + b]) << 8 * (paramInt2 - 1 - b); 
    return l;
  }
  
  public static long bytes2LongR(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    long l = 0L;
    for (byte b = 0; b < paramInt2; b++)
      l |= (0xFFL & paramArrayOfbyte[paramInt1 + b]) << 8 * b; 
    return l;
  }
  
  public static String bytes2String(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    StringBuffer stringBuffer = new StringBuffer(paramInt2 * 2);
    for (byte b = 0; b < paramInt2; b++) {
      stringBuffer.append((char)raw2Hex((byte)((paramArrayOfbyte[paramInt1 + b] & 0xF0) >> 4)));
      stringBuffer.append((char)raw2Hex((byte)(paramArrayOfbyte[paramInt1 + b] & 0xF)));
    } 
    return stringBuffer.toString();
  }
  
  public static boolean getBitmapAt(int paramInt1, int paramInt2) {
    int i = 1;
    i <<= paramInt1;
    return ((paramInt2 & i) != 0);
  }
  
  public static String getEmptyString(String paramString1, String paramString2) {
    return (paramString1 != null) ? paramString1 : paramString2;
  }
  
  public static int getPadLength(int paramInt) {
    int i = (4 - paramInt % 4) % 4;
    return (i == 0) ? 4 : i;
  }
  
  public static int int2Bytes(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3) {
    for (byte b = 0; b < paramInt3; b++)
      paramArrayOfbyte[paramInt2 + paramInt3 - 1 - b] = (byte)(0xFF & paramInt1 >> 8 * b); 
    return paramInt3;
  }
  
  public static int int2BytesR(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3) {
    for (byte b = 0; b < paramInt3; b++)
      paramArrayOfbyte[paramInt2 + b] = (byte)(0xFF & paramInt1 >> 8 * b); 
    return paramInt3;
  }
  
  public static int long2Bytes(long paramLong, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    for (byte b = 0; b < paramInt2; b++)
      paramArrayOfbyte[paramInt1 + paramInt2 - 1 - b] = (byte)(int)(0xFFL & paramLong >> 8 * b); 
    return paramInt2;
  }
  
  public static int long2BytesR(long paramLong, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    for (byte b = 0; b < paramInt2; b++)
      paramArrayOfbyte[paramInt1 + b] = (byte)(int)(0xFFL & paramLong >> 8 * b); 
    return paramInt2;
  }
  
  private static byte raw2Hex(byte paramByte) {
    paramByte = (byte)(paramByte & 0xF);
    return (byte)((paramByte >= 10) ? (paramByte - 10 + 65) : (paramByte + 48));
  }
  
  public static void writePadding(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if (paramInt2 != 0)
      for (byte b = 0; b < paramInt2; b++)
        paramArrayOfbyte[paramInt1 + b] = 0;  
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdb\\util\TbCommon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */