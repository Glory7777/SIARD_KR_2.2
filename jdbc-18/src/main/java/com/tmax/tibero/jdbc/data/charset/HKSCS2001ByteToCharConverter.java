package com.tmax.tibero.jdbc.data.charset;

public class HKSCS2001ByteToCharConverter {
  private static final char[] HKSCS2001_TO_UNICODE_PAGE8C = new char[] { 
      '\u0A3B', '\u1CFE', '\u5273', '\u37A6', '\u02C9', '\u2D8F', '\u414E', '\u1D77', '\u12F5', '\u0620', 
      '\u45CD', 'Y', '\u4830', '\u1622', '\u4F32', '\u30A7', '\u31F6', '\u1E91', '\u1819', '\u20BA', 
      '\u3E81', '\u5307', '\u018B', '\u3A80', '\u0610', '\u24E4', '\u2102', '\u0BAE', '\u4D0F', '\u0409', 
      '\u1A63', '\u54BA', '\u0523', '\u2C0F', '\u38FD', '\u252A', '\u5147', '\u4FEA', '\u3455', '\u1D4D', 
      '\u0C24', '\u3C7E', '\u33F4', '\u22D9', '\u4EE3', '\u37A7', '\u23DD', '\u08A3', '\u09F0', '\u0ABC', 
      '\u082F', '\u0917', '\u37A8', '\u0D34', '\u288B', '\u0F92', '\u0FD0', '\u3BB6', '\u1492', '\u1499', 
      '\u15C2', '\u3D12', '\u178B', '\u3FF9', '\u1919', '\u1A43', '\u4063', '\u1BFF', '\u38FD', '\u1F00', 
      '\u4205', '\u208C', '\u03DB', '\u4413', '\u1115', '\u21B9', '\u2E83', '\u47A4', '\u4695', '\u2593', 
      '\u26EC', '\u27C3', '\u296C', '\u2AF8', '\u2B97', '\u37A9', '\u2D90', '\u37AA', '\u2FB9', '\u37AB', 
      '\u30CF', '\u2B5F', '\u36E0', '\u3221', '\u37AC', '\u50B9', '\u393F', '\u0471', '\u05A2', '\u101A', 
      '\u38FD', '\u38FD', '\u38FD', '\u3568', '\u186B', '\u0576', '\u0E3D', '\u38FD', '\u2BD6', '\u437B', 
      '\u2ABF', '\u4C0D', '\u0781', '\u4A74', '\u137B', '\u4915', '\u4BBE', '\u37AD', '\u37AE', '\u1196', 
      '\u37AF', '\u38FD', '\u295B' };
  
  private static final int[] HKSCS2001_TO_UNICODE_PAGES = new int[] { 
      13568, 15360, 15616, 15872, 16384, 16896, 19200, 19456, 19968, 20224, 
      20480, 20736, 21248, 21504, 22272, 22528, 23040, 23296, 23552, 23808, 
      24064, 24320, 24832, 25856, 26368, 26880, 27136, 27648, 27904, 28672, 
      28928, 29184, 29440, 29696, 30208, 30464, 30720, 31232, 31488, 31744, 
      32000, 32256, 33280, 34048, 34304, 34816, 35584, 36352, 36608, 37120, 
      37376, 37632, 38656, 38912, 39168, 40704, 65280, 136192, 137472, 138496, 
      139264, 141056, 143872, 144128, 146432, 147712, 148736, 149760, 150016, 151808, 
      153088, 154624, 154880, 158464, 158976, 159488, 160000, 165632, 166144, 166400, 
      167168, 170240, 171008, 172288, 172544 };
  
  protected boolean subMode = true;
  
  protected char[] subChars = new char[] { '?' };
  
  public int convert(byte paramByte1, byte paramByte2, char[] paramArrayOfchar, int paramInt) {
    if (paramByte1 == -116 && ((paramByte2 >= 64 && paramByte2 < Byte.MAX_VALUE) || (paramByte2 >= -95 && paramByte2 < -1))) {
      byte b;
      if (paramByte2 < 0 && paramByte2 >= 161) {
        b = 98;
      } else {
        b = 64;
      } 
      int i = 157 * ((paramByte1 & 0xFF) - 128) + paramByte2 - b;
      int j = 65533;
      if (i < 2007) {
        short s = (short)HKSCS2001_TO_UNICODE_PAGE8C[i - 1884];
        j = HKSCS2001_TO_UNICODE_PAGES[s >> 8] | s & 0xFF;
      } 
      if (j > 65535) {
        paramArrayOfchar[paramInt] = (char)(j >> 16);
        paramArrayOfchar[paramInt + 1] = (char)(j & 0xFFFF);
        return 2;
      } 
      if (j != 65533 || j <= 65535) {
        paramArrayOfchar[paramInt] = (char)j;
        return 0;
      } 
    } 
    return -1;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\charset\HKSCS2001ByteToCharConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */