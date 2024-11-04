package com.tmax.tibero.jdbc.data.charset;

public class HKSCS2001ByteToCharConverter {
  private static final char[] HKSCS2001_TO_UNICODE_PAGE8C = new char[] { 
      '਻', '᳾', '剳', '㞦', 'ˉ', 'ⶏ', '䅎', 'ᵷ', 'ድ', 'ؠ', 
      '䗍', 'Y', '䠰', 'ᘢ', '伲', 'ェ', 'ㇶ', 'ẑ', '᠙', '₺', 
      '㺁', '匇', 'Ƌ', '㪀', 'ؐ', 'ⓤ', 'ℂ', 'ம', '䴏', 'Љ', 
      'ᩣ', '咺', 'ԣ', 'Ⰿ', '㣽', '┪', '兇', '俪', '㑕', 'ᵍ', 
      'త', '㱾', '㏴', '⋙', '代', '㞧', '⏝', 'ࢣ', 'ৰ', '઼', 
      '࠯', 'ग', '㞨', 'ഴ', '⢋', 'ྒ', '࿐', '㮶', 'ᒒ', 'ᒙ', 
      'ᗂ', '㴒', 'ឋ', '㿹', 'ᤙ', 'ᩃ', '䁣', '᯿', '㣽', 'ἀ', 
      '䈅', '₌', 'ϛ', '䐓', 'ᄕ', '↹', '⺃', '䞤', '䚕', '▓', 
      '⛬', '⟃', '⥬', '⫸', '⮗', '㞩', 'ⶐ', '㞪', '⾹', '㞫', 
      'ハ', '⭟', '㛠', '㈡', '㞬', '傹', '㤿', 'ѱ', '֢', 'ယ', 
      '㣽', '㣽', '㣽', '㕨', 'ᡫ', 'ն', '฽', '㣽', '⯖', '䍻', 
      '⪿', '䰍', 'ށ', '䩴', '፻', '䤕', '䮾', '㞭', '㞮', 'ᆖ', 
      '㞯', '㣽', '⥛' };
  
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


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\HKSCS2001ByteToCharConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */