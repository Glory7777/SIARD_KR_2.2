package com.tmax.tibero.jdbc.data.charset;

public class MS950ExtCharToByteConverter {
  private static final char[] CP950EXT_TO_CHARSET = new char[] { 
      '\uF9F9', '\uF9F8', '\uF9E6', '\uF9EF', '\uF9DD', '\uF9E8', '\uF9F1', '\uF9DF', '\uF9EC', '\uF9F5', 
      '\uF9E3', '\uF9EE', '\uF9F7', '\uF9E5', '\uF9E9', '\uF9F2', '\uF9E0', '\uF9EB', '\uF9F4', '\uF9E2', 
      '\uF9E7', '\uF9F0', '\uF9DE', '\uF9ED', '\uF9F6', '\uF9E4', '\uF9EA', '\uF9F3', '\uF9E1', '\uF9FA', 
      '\uF9FB', '\uF9FD', '\uF9FC', '\uF9FE', '\uF9D9', '\uF9DC', '\uF9DA', '\uF9D6', '\uF9DB', '\uF9D8', 
      '\uF9D7' };
  
  private static final char[][] CP950EXT_TO_UNICODE_PAGE25 = new char[][] { { Character.MIN_VALUE, Character.MIN_VALUE }, { Character.MIN_VALUE, Character.MIN_VALUE }, { Character.MIN_VALUE, Character.MIN_VALUE }, { Character.MIN_VALUE, Character.MIN_VALUE }, { Character.MIN_VALUE, Character.MIN_VALUE }, { Character.MIN_VALUE, Character.MAX_VALUE }, { '\020', Character.MAX_VALUE }, { ' ', '\001' }, { '!', Character.MIN_VALUE }, { '!', '\b' } };
  
  private static final char[][] CP950EXT_TO_UNICODE_PAGE58 = new char[][] { 
      { '"', Character.MIN_VALUE }, { '"', Character.MIN_VALUE }, { '"', Character.MIN_VALUE }, { '"', Character.MIN_VALUE }, { '"', Character.MIN_VALUE }, { '"', Character.MIN_VALUE }, { '"', Character.MIN_VALUE }, { '"', Character.MIN_VALUE }, { '"', Character.MIN_VALUE }, { '"', Character.MIN_VALUE }, 
      { '"', Character.MIN_VALUE }, { '"', '\u0800' } };
  
  private static final char[][] CP950EXT_TO_UNICODE_PAGE5A = new char[][] { 
      { '#', Character.MIN_VALUE }, { '#', Character.MIN_VALUE }, { '#', Character.MIN_VALUE }, { '#', Character.MIN_VALUE }, { '#', Character.MIN_VALUE }, { '#', Character.MIN_VALUE }, { '#', Character.MIN_VALUE }, { '#', Character.MIN_VALUE }, { '#', Character.MIN_VALUE }, { '#', Character.MIN_VALUE }, 
      { '#', Character.MIN_VALUE }, { '#', Character.MIN_VALUE }, { '#', Character.MIN_VALUE }, { '#', Character.MIN_VALUE }, { '#', Character.MIN_VALUE }, { '#', '\u0400' } };
  
  private static final char[][] CP950EXT_TO_UNICODE_PAGE60 = new char[][] { { '$', Character.MIN_VALUE }, { '$', Character.MIN_VALUE }, { '$', Character.MIN_VALUE }, { '$', Character.MIN_VALUE }, { '$', Character.MIN_VALUE }, { '$', '\004' } };
  
  private static final char[][] CP950EXT_TO_UNICODE_PAGE78 = new char[][] { { '%', Character.MIN_VALUE }, { '%', Character.MIN_VALUE }, { '%', Character.MIN_VALUE }, { '%', Character.MIN_VALUE }, { '%', Character.MIN_VALUE }, { '%', Character.MIN_VALUE }, { '%', Character.MIN_VALUE }, { '%', Character.MIN_VALUE }, { '%', '\002' } };
  
  private static final char[][] CP950EXT_TO_UNICODE_PAGE7C = new char[][] { 
      { '&', Character.MIN_VALUE }, { '&', Character.MIN_VALUE }, { '&', Character.MIN_VALUE }, { '&', Character.MIN_VALUE }, { '&', Character.MIN_VALUE }, { '&', Character.MIN_VALUE }, { '&', Character.MIN_VALUE }, { '&', Character.MIN_VALUE }, { '&', Character.MIN_VALUE }, { '&', Character.MIN_VALUE }, 
      { '&', '\u0080' } };
  
  private static final char[][] CP950EXT_TO_UNICODE_PAGE88 = new char[][] { 
      { '\'', Character.MIN_VALUE }, { '\'', Character.MIN_VALUE }, { '\'', Character.MIN_VALUE }, { '\'', Character.MIN_VALUE }, { '\'', Character.MIN_VALUE }, { '\'', Character.MIN_VALUE }, { '\'', Character.MIN_VALUE }, { '\'', Character.MIN_VALUE }, { '\'', Character.MIN_VALUE }, { '\'', Character.MIN_VALUE }, 
      { '\'', Character.MIN_VALUE }, { '\'', Character.MIN_VALUE }, { '\'', '\u8000' } };
  
  private static final char[][] CP950EXT_TO_UNICODE_PAGE92 = new char[][] { 
      { '(', Character.MIN_VALUE }, { '(', Character.MIN_VALUE }, { '(', Character.MIN_VALUE }, { '(', Character.MIN_VALUE }, { '(', Character.MIN_VALUE }, { '(', Character.MIN_VALUE }, { '(', Character.MIN_VALUE }, { '(', Character.MIN_VALUE }, { '(', Character.MIN_VALUE }, { '(', Character.MIN_VALUE }, 
      { '(', Character.MIN_VALUE }, { '(', '\u0200' } };
  
  protected boolean subMode = true;
  
  protected byte[] subBytes = new byte[] { 63 };
  
  public int getMaxBytesPerChar() {
    return 2;
  }
  
  public int convert(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
    char[] arrayOfChar = null;
    if (paramInt1 >= 9472 && paramInt1 < 9632) {
      arrayOfChar = CP950EXT_TO_UNICODE_PAGE25[(paramInt1 >> 4) - 592];
    } else if (paramInt1 >= 22528 && paramInt1 < 22720) {
      arrayOfChar = CP950EXT_TO_UNICODE_PAGE58[(paramInt1 >> 4) - 1408];
    } else if (paramInt1 >= 23040 && paramInt1 < 23296) {
      arrayOfChar = CP950EXT_TO_UNICODE_PAGE5A[(paramInt1 >> 4) - 1440];
    } else if (paramInt1 >= 24576 && paramInt1 < 24672) {
      arrayOfChar = CP950EXT_TO_UNICODE_PAGE60[(paramInt1 >> 4) - 1536];
    } else if (paramInt1 >= 30720 && paramInt1 < 30864) {
      arrayOfChar = CP950EXT_TO_UNICODE_PAGE78[(paramInt1 >> 4) - 1920];
    } else if (paramInt1 >= 31744 && paramInt1 < 31920) {
      arrayOfChar = CP950EXT_TO_UNICODE_PAGE7C[(paramInt1 >> 4) - 1984];
    } else if (paramInt1 >= 34816 && paramInt1 < 35024) {
      arrayOfChar = CP950EXT_TO_UNICODE_PAGE88[(paramInt1 >> 4) - 2176];
    } else if (paramInt1 >= 37376 && paramInt1 < 37568) {
      arrayOfChar = CP950EXT_TO_UNICODE_PAGE92[(paramInt1 >> 4) - 2336];
    } 
    if (arrayOfChar != null) {
      char c = arrayOfChar[1];
      int i = paramInt1 & 0xF;
      if ((c & 1 << i) != 0) {
        int j = c & (1 << i) - 1;
        j = (j & 0x5555) + ((j & 0xAAAA) >> 1);
        j = (j & 0x3333) + ((j & 0xCCCC) >> 2);
        j = (j & 0xF0F) + ((j & 0xF0F0) >> 4);
        j = (j & 0xFF) + (j >> 8);
        char c1 = CP950EXT_TO_CHARSET[arrayOfChar[0] + j];
        paramArrayOfbyte[paramInt2] = (byte)(c1 >> 8);
        paramArrayOfbyte[paramInt2 + 1] = (byte)(c1 & 0xFF);
        return 0;
      } 
    } 
    return -1;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\charset\MS950ExtCharToByteConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */