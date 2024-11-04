package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;

public class Charset {
  public static final int ASCII = 0;
  
  public static final int EUC_KR = 1;
  
  public static final int MSWIN949 = 2;
  
  public static final int UTF_8 = 3;
  
  public static final int UTF_16 = 4;
  
  public static final int SJIS = 5;
  
  public static final int JA16SJIS = 6;
  
  public static final int JA16SJIS_TILDE = 7;
  
  public static final int EUC_JP = 8;
  
  public static final int EUC_JP_TILDE = 9;
  
  public static final int VN8VN3 = 10;
  
  public static final int GBK = 11;
  
  public static final int MSWIN1252 = 12;
  
  public static final int MSWIN950 = 13;
  
  public static final int MSWIN1251 = 14;
  
  public static final int ISO8859P1 = 15;
  
  public static final int ISO8859P2 = 16;
  
  public static final int ISO8859P9 = 17;
  
  public static final int ISO8859P15 = 18;
  
  public static final int KOI8R = 19;
  
  public static final int ISO8859P5 = 20;
  
  public static final int CP866 = 21;
  
  public static final int TIS620 = 22;
  
  public static final int MSWIN1253 = 23;
  
  public static final int ISO8859P7 = 24;
  
  public static final int MSWIN1256 = 25;
  
  public static final int ISO8859P6 = 26;
  
  public static final int SJISTILDE = 27;
  
  public static final int ZHT16BIG5 = 28;
  
  public static final int ZHT16MSWIN950 = 29;
  
  public static final int GB18030 = 30;
  
  public static final int ISO8859P8 = 31;
  
  public static final int EUC_TW = 32;
  
  public static final int MAX_CHAR_SET = 33;
  
  public static final String[] name = new String[] { 
      "ASCII", "EUC-KR", "MSWIN949", "UTF-8", "UTF-16", "SHIFT-JIS", "JA16SJIS", "JA16SJISTILDE", "JA16EUC", "JA16EUCTILDE", 
      "VN8VN3", "GBK", "WE8MSWIN1252", "ZHT16HKSCS", "CL8MSWIN1251", "WE8ISO8859P1", "EE8ISO8859P2", "WE8ISO8859P9", "WE8ISO8859P15", "CL8KOI8R", 
      "CL8ISO8859P5", "CP866", "TH8TISASCII", "EL8MSWIN1253", "EL8ISO8859P7", "AR8MSWIN1256", "AR8ISO8859P6", "SJISTILDE", "ZHT16BIG5", "ZHT16MSWIN950", 
      "GB18030", "IW8ISO8859P8", "EUC-TW" };
  
  private static final String[][] aliases = new String[][] { 
      { "US7ASCII" }, { "EUCKR", "KO16KSC5601" }, { "CP949", "KO16MSWIN949" }, { "UTF8" }, { "UTF-16BE", "AL16UTF16" }, { "SJIS", "JA16SJIS" }, { "CP932" }, { "CP932" }, { "EUC-JP" }, { "EUC-JP" }, 
      { "TCVN" }, { "CP936", "ZHS16GBK" }, { "CP1252", "MSWIN1252" }, { "CP950", "MSWIN950" }, { "CP1251", "MSWIN1251" }, { "ISO8859P1", "ISO-8859-1" }, { "ISO8859P2", "ISO-8859-2" }, { "ISO8859P9", "ISO-8859-9" }, { "ISO8859P15", "ISO-8859-15" }, { "KOI8-R" }, 
      { "ISO8859P5", "ISO-8859-5" }, { "RUCP866" }, { "TIS620" }, { "CP1253", "MSWIN1253" }, { "ISO8859P7", "ISO-8859-7" }, { "CP1256", "MSWIN1256" }, { "ISO8859P6", "ISO-8859-6" }, {}, {}, {}, 
      { "ISO8859P8", "ISO-8859-8" }, { "EUCTW", "ZHT32EUC" } };
  
  public static int getCharset(String paramString) {
    if (paramString == null)
      return -1; 
    byte b;
    for (b = 0; b < name.length; b++) {
      if (name[b].equalsIgnoreCase(paramString))
        return b; 
    } 
    for (b = 0; b < aliases.length; b++) {
      for (byte b1 = 0; b1 < (aliases[b]).length; b1++) {
        if (aliases[b][b1].equalsIgnoreCase(paramString))
          return b; 
      } 
    } 
    return -1;
  }
  
  public static String getName(int paramInt) throws SQLException {
    if (paramInt < 0 || paramInt >= 33)
      throw TbError.newSQLException(-590718); 
    return name[paramInt];
  }
  
  public static void verify(int paramInt) throws SQLException {
    if (paramInt < 0 || paramInt >= 33)
      throw TbError.newSQLException(-590718); 
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\Charset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */