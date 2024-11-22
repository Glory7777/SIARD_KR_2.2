package com.tmax.tibero.jdbc.util;

public interface TbDTFormatParserConstants {
  public static final int EOF = 0;
  
  public static final int COLON = 1;
  
  public static final int COMMA = 2;
  
  public static final int DASH = 3;
  
  public static final int DOLLAR = 4;
  
  public static final int DOT = 5;
  
  public static final int DQUOT = 6;
  
  public static final int LBRACE = 7;
  
  public static final int LBRACKET = 8;
  
  public static final int RBRACE = 9;
  
  public static final int RBRACKET = 10;
  
  public static final int SEMICOLON = 11;
  
  public static final int SLASH = 12;
  
  public static final int SPACE = 13;
  
  public static final int STAR = 14;
  
  public static final int DELIMITER = 15;
  
  public static final int DELIMETER_DQUOT_STR = 16;
  
  public static final int APOSTROPHE = 17;
  
  public static final int CHARACTER = 18;
  
  public static final int DIGIT = 19;
  
  public static final int LOWER = 20;
  
  public static final int SPECIAL = 21;
  
  public static final int UPPER = 22;
  
  public static final int AMPM = 23;
  
  public static final int ERA = 24;
  
  public static final int CENTURY = 25;
  
  public static final int DAY_OF_MONTH = 26;
  
  public static final int DAY_OF_YEAR = 27;
  
  public static final int DAY_OF_WEEK__ABBR = 28;
  
  public static final int DAY_OF_WEEK__NUMBER = 29;
  
  public static final int DAY_OF_WEEK__STRING = 30;
  
  public static final int FORMAT_TRIM = 31;
  
  public static final int FORMAT_EXACT = 32;
  
  public static final int FRAC = 33;
  
  public static final int HOUR_12 = 34;
  
  public static final int HOUR_24 = 35;
  
  public static final int MINUTE = 36;
  
  public static final int MONTH__ABBR = 37;
  
  public static final int MONTH__NUMBER = 38;
  
  public static final int MONTH__ROMAN = 39;
  
  public static final int MONTH__STRING = 40;
  
  public static final int QUATER = 41;
  
  public static final int RECENTLY_YEAR = 42;
  
  public static final int RADIX_CHARACTER = 43;
  
  public static final int SECOND_OF_DAY = 44;
  
  public static final int SECOND_OF_HOUR = 45;
  
  public static final int SIGNED_CENTURY = 46;
  
  public static final int SIGNED_YEAR__NUMBER = 47;
  
  public static final int SIGNED_YEAR__STRING = 48;
  
  public static final int TIMEZONE_DST = 49;
  
  public static final int TIMEZONE_HOUR = 50;
  
  public static final int TIMEZONE_MINUTE = 51;
  
  public static final int TIMEZONE_REGION = 52;
  
  public static final int WEEK_OF_MONTH = 53;
  
  public static final int WEEK_OF_YEAR = 54;
  
  public static final int YEAR__ISO = 55;
  
  public static final int YEAR__NUMBER = 56;
  
  public static final int YEAR__STRING = 57;
  
  public static final int SPECIAL2 = 58;
  
  public static final int DEFAULT = 0;
  
  public static final String[] tokenImage = new String[] { 
      "<EOF>", "\":\"", "\",\"", "\"-\"", "\"$\"", "\".\"", "\"\\\"\"", "\"{\"", "\"[\"", "\"}\"", 
      "\"]\"", "\";\"", "\"/\"", "\" \"", "\"*\"", "<DELIMITER>", "<DELIMETER_DQUOT_STR>", "\"\\'\"", "<CHARACTER>", "<DIGIT>", 
      "<LOWER>", "<SPECIAL>", "<UPPER>", "<AMPM>", "<ERA>", "\"CC\"", "\"DD\"", "\"DDD\"", "\"DY\"", "\"D\"", 
      "\"DAY\"", "\"FM\"", "\"FX\"", "<FRAC>", "<HOUR_12>", "\"HH24\"", "\"MI\"", "\"MON\"", "\"MM\"", "\"RM\"", 
      "\"MONTH\"", "\"Q\"", "<RECENTLY_YEAR>", "\"X\"", "\"SSSSS\"", "\"SS\"", "\"SCC\"", "\"SYYYY\"", "\"SYEAR\"", "\"TZD\"", 
      "\"TZH\"", "\"TZM\"", "\"TZR\"", "\"W\"", "\"WW\"", "<YEAR__ISO>", "<YEAR__NUMBER>", "\"YEAR\"", "<SPECIAL2>" };
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdb\\util\TbDTFormatParserConstants.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */