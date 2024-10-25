package com.tmax.tibero.jdbc.util;

public interface TbUrlParserConstants {
  public static final int EOF = 0;
  
  public static final int IP4ADDR = 5;
  
  public static final int DEFAULT_KEYWORD = 6;
  
  public static final int CONNECTION_KEYWORD = 7;
  
  public static final int JDBC_KEYWORD = 8;
  
  public static final int driverType = 9;
  
  public static final int DESCRIPTION_KEYWORD = 10;
  
  public static final int FAILOVER_KEYWORD = 11;
  
  public static final int LOADBALANCE_KEYWORD = 12;
  
  public static final int PROTOCOL_KEYWORD = 13;
  
  public static final int ON = 14;
  
  public static final int OFF = 15;
  
  public static final int DATABASENAME_KEYWORD = 16;
  
  public static final int ADDRLIST_KEYWORD = 17;
  
  public static final int ADDR_KEYWORD = 18;
  
  public static final int HOST_KEYWORD = 19;
  
  public static final int PORT_KEYWORD = 20;
  
  public static final int PROTOCOL = 21;
  
  public static final int NONE_KEYWORD = 22;
  
  public static final int SESSION_KEYWORD = 23;
  
  public static final int CURSOR_KEYWORD = 24;
  
  public static final int LBRACE = 25;
  
  public static final int RBRACE = 26;
  
  public static final int LBRACKET = 27;
  
  public static final int RBRACKET = 28;
  
  public static final int SEMICOLON = 29;
  
  public static final int COMMA = 30;
  
  public static final int DOT = 31;
  
  public static final int DOLLAR = 32;
  
  public static final int STAR = 33;
  
  public static final int DIGIT = 34;
  
  public static final int INTEGER = 35;
  
  public static final int LOWER = 36;
  
  public static final int UPPER = 37;
  
  public static final int SPECIAL = 38;
  
  public static final int APOSTROPHE = 39;
  
  public static final int CHARACTER = 40;
  
  public static final int ID = 41;
  
  public static final int DEFAULT = 0;
  
  public static final String[] tokenImage = new String[] { 
      "<EOF>", "\" \"", "\"\\t\"", "\"\\n\"", "\"\\r\"", "<IP4ADDR>", "<DEFAULT_KEYWORD>", "<CONNECTION_KEYWORD>", "<JDBC_KEYWORD>", "<driverType>", 
      "<DESCRIPTION_KEYWORD>", "<FAILOVER_KEYWORD>", "<LOADBALANCE_KEYWORD>", "<PROTOCOL_KEYWORD>", "<ON>", "<OFF>", "<DATABASENAME_KEYWORD>", "<ADDRLIST_KEYWORD>", "<ADDR_KEYWORD>", "<HOST_KEYWORD>", 
      "<PORT_KEYWORD>", "<PROTOCOL>", "<NONE_KEYWORD>", "<SESSION_KEYWORD>", "<CURSOR_KEYWORD>", "\"{\"", "\"}\"", "\"[\"", "\"]\"", "\";\"", 
      "\",\"", "\".\"", "\"$\"", "\"*\"", "<DIGIT>", "<INTEGER>", "<LOWER>", "<UPPER>", "<SPECIAL>", "\"\\'\"", 
      "<CHARACTER>", "<ID>", "\":\"", "\"@(\"", "\"=\"", "\")\"", "\"(\"", "\"@\"", "\"/\"" };
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdb\\util\TbUrlParserConstants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */