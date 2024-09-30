package ch.enterag.utils;
























public class BU
{
  private static final long lBYTE_MASK = 255L;
  private static final int iBYTE_MASK = 255;
  private static final int iNYBBLE_MASK = 15;
  private static final int iHIGH_BIT_MASK = 128;
  private static String sHexDigits = "0123456789ABCDEF";






  
  private static byte lowByte(int i) {
    i &= 0xFF;
    if (i >= 128)
      i = -256 + i; 
    return (byte)i;
  }








  
  public static short toShort(byte[] bufShort, int iPos) {
    short wShort = 0;
    int iByte = 0;
    if (bufShort.length < iPos + 2)
      throw new IllegalArgumentException("byte buffer for short must have length >= 2!"); 
    for (int i = iPos + 1; i >= iPos; i--) {

      
      wShort = (short)(wShort << 8);
      iByte = bufShort[i];
      
      iByte &= 0xFF;
      
      wShort = (short)(wShort | iByte);
    } 
    return wShort;
  }







  
  public static short toShort(byte[] bufShort) {
    return toShort(bufShort, 0);
  }






  
  public static byte[] fromShort(short wShort) {
    byte[] bufShort = new byte[2];
    for (int i = 1; i >= 0; i--) {
      
      bufShort[1 - i] = lowByte(wShort);
      
      wShort = (short)(wShort >> 8);
    } 
    return bufShort;
  }








  
  public static int toInt(byte[] bufInt, int iPos) {
    int iInt = 0;
    int iByte = 0;
    if (bufInt.length < iPos + 4)
      throw new IllegalArgumentException("byte buffer for int must have length >= 4!"); 
    for (int i = iPos + 3; i >= iPos; i--) {

      
      iInt <<= 8;
      iByte = bufInt[i];
      
      iByte &= 0xFF;
      
      iInt |= iByte;
    } 
    return iInt;
  }







  
  public static int toInt(byte[] bufInt) {
    return toInt(bufInt, 0);
  }






  
  public static byte[] fromInt(int iInt) {
    byte[] bufInt = new byte[4];
    for (int i = 3; i >= 0; i--) {
      
      bufInt[3 - i] = lowByte(iInt);
      
      iInt >>= 8;
    } 
    return bufInt;
  }








  
  public static long toLong(byte[] bufLong, int iPos) {
    long lLong = 0L;
    long lByte = 0L;
    if (bufLong.length < iPos + 8)
      throw new IllegalArgumentException("byte buffer for long must have length >= 8!"); 
    for (int i = iPos + 7; i >= iPos; i--) {

      
      lLong <<= 8L;
      lByte = bufLong[i];
      
      lByte &= 0xFFL;
      
      lLong |= lByte;
    } 
    return lLong;
  }







  
  public static long toLong(byte[] bufLong) {
    return toLong(bufLong, 0);
  }






  
  public static byte[] fromLong(long lLong) {
    byte[] bufLong = new byte[8];
    for (int i = 7; i >= 0; i--) {
      
      bufLong[7 - i] = lowByte((int)(lLong & 0xFFL));
      
      lLong >>= 8L;
    } 
    return bufLong;
  }






  
  public static String toBinary(byte b) {
    StringBuffer sbBinary = new StringBuffer();
    int i = b & 0xFF;
    
    for (int iMask = 128; iMask != 0; iMask >>>= 1) {
      
      if ((i & iMask) != 0) {
        sbBinary.append("1");
      } else {
        sbBinary.append("0");
      } 
    }  return sbBinary.toString();
  }








  
  public static String toBinary(byte[] buffer, int iOffset, int iLength) {
    StringBuffer sbBinary = new StringBuffer();
    for (int i = iOffset; i < iOffset + iLength; i++)
      sbBinary.append(toBinary(buffer[i])); 
    return sbBinary.toString();
  }






  
  public static String toBinary(byte[] buffer) {
    return toBinary(buffer, 0, buffer.length);
  }






  
  public static String toHex(byte b) {
    StringBuffer sbHex = new StringBuffer();
    int i = b & 0xFF;
    int iLow = i & 0xF;
    int iHigh = i >> 4;
    sbHex.append(sHexDigits.charAt(iHigh));
    sbHex.append(sHexDigits.charAt(iLow));
    return sbHex.toString();
  }








  
  public static String toHex(byte[] buffer, int iOffset, int iLength) {
    StringBuffer sbHex = new StringBuffer();
    for (int i = iOffset; i < iOffset + iLength; i++)
      sbHex.append(toHex(buffer[i])); 
    return sbHex.toString();
  }






  
  public static String toHex(byte[] buffer) {
    return toHex(buffer, 0, buffer.length);
  }







  
  public static byte fromHex(char cHigh, char cLow) {
    byte b = 0;
    int iHigh = sHexDigits.indexOf(cHigh);
    int iLow = sHexDigits.indexOf(cLow);
    if (iLow >= 0 && iHigh >= 0) {
      
      int i = iHigh << 4;
      i += iLow;
      b = lowByte(i);
    } else {
      
      throw new IllegalArgumentException("Invalid hex data " + Character.toString(cHigh) + Character.toString(cLow) + "!");
    }  return b;
  }









  
  public static int fromHex(char c3, char c2, char c1, char c0) {
    int i = 0;
    int i3 = sHexDigits.indexOf(c3);
    int i2 = sHexDigits.indexOf(c2);
    int i1 = sHexDigits.indexOf(c1);
    int i0 = sHexDigits.indexOf(c0);
    if (i3 >= 0 && i2 >= 0 && i1 >= 0 && i0 >= 0) {
      i = (i3 << 12) + (i2 << 8) + (i1 << 4) + i0;
    } else {
      throw new IllegalArgumentException("Invalid hex data " + Character.toString(c3) + Character.toString(c2) + Character.toString(c1) + Character.toString(c0) + "!");
    }  return i;
  }






  
  public static byte[] fromHex(String sHex) {
    if ((sHex.length() & 0x1) != 0)
      throw new IllegalArgumentException("Hex string must have even number of hex digits!"); 
    sHex = sHex.toUpperCase();
    byte[] buffer = new byte[sHex.length() / 2];
    for (int i = 0; i < buffer.length; i++)
      buffer[i] = fromHex(sHex.charAt(2 * i), sHex.charAt(2 * i + 1)); 
    return buffer;
  }






  
  public static int fromUnsignedByte(byte by) {
    int iResult = by;
    if (iResult < 0)
      iResult += 256; 
    return iResult;
  }






  
  public static byte toUnsignedByte(int i) {
    if (i > 127)
      i -= 256; 
    byte by = (byte)i;
    return by;
  }









  
  public static int indexOf(byte[] data, byte[] pattern) {
    int iIndex = -1;
    int[] failure = computeFailure(pattern);
    int j = 0;
    for (int i = 0; iIndex < 0 && i < data.length; i++) {
      
      while (j > 0 && pattern[j] != data[i])
        j = failure[j - 1]; 
      if (pattern[j] == data[i])
        j++; 
      if (j == pattern.length)
        iIndex = i - pattern.length + 1; 
    } 
    return iIndex;
  }





  
  private static int[] computeFailure(byte[] pattern) {
    int[] failure = new int[pattern.length];
    int j = 0;
    for (int i = 1; i < pattern.length; i++) {
      
      while (j > 0 && pattern[j] != pattern[i])
        j = failure[j - 1]; 
      if (pattern[j] == pattern[i])
        j++; 
      failure[i] = j;
    } 
    return failure;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\utils\BU.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */