package com.tmax.tibero.jdbc.util;

public interface TbUrlParserConstants {
  public static final int EOF = 0;
  
  public static final int IP4ADDR = 5;
  
  public static final int IP6ADDR = 6;
  
  public static final int DEFAULT_KEYWORD = 7;
  
  public static final int CONNECTION_KEYWORD = 8;
  
  public static final int JDBC_KEYWORD = 9;
  
  public static final int driverType = 10;
  
  public static final int DESCRIPTION_KEYWORD = 11;
  
  public static final int FAILOVER_KEYWORD = 12;
  
  public static final int LOADBALANCE_KEYWORD = 13;
  
  public static final int PROTOCOL_KEYWORD = 14;
  
  public static final int ON = 15;
  
  public static final int OFF = 16;
  
  public static final int DATABASENAME_KEYWORD = 17;
  
  public static final int ADDRLIST_KEYWORD = 18;
  
  public static final int ADDR_KEYWORD = 19;
  
  public static final int HOST_KEYWORD = 20;
  
  public static final int PORT_KEYWORD = 21;
  
  public static final int PROTOCOL = 22;
  
  public static final int NONE_KEYWORD = 23;
  
  public static final int SESSION_KEYWORD = 24;
  
  public static final int CURSOR_KEYWORD = 25;
  
  public static final int LBRACE = 26;
  
  public static final int RBRACE = 27;
  
  public static final int LBRACKET = 28;
  
  public static final int RBRACKET = 29;
  
  public static final int SEMICOLON = 30;
  
  public static final int COMMA = 31;
  
  public static final int DOT = 32;
  
  public static final int DOLLAR = 33;
  
  public static final int STAR = 34;
  
  public static final int DIGIT = 35;
  
  public static final int INTEGER = 36;
  
  public static final int LOWER = 37;
  
  public static final int UPPER = 38;
  
  public static final int SPECIAL = 39;
  
  public static final int APOSTROPHE = 40;
  
  public static final int CHARACTER = 41;
  
  public static final int ID = 42;
  
  public static final int DEFAULT = 0;
  
  public static final String[] tokenImage = new String[] { 
      "<EOF>", "\" \"", "\"\\t\"", "\"\\n\"", "\"\\r\"", "<IP4ADDR>", "<IP6ADDR>", "<DEFAULT_KEYWORD>", "<CONNECTION_KEYWORD>", "<JDBC_KEYWORD>", 
      "<driverType>", "<DESCRIPTION_KEYWORD>", "<FAILOVER_KEYWORD>", "<LOADBALANCE_KEYWORD>", "<PROTOCOL_KEYWORD>", "<ON>", "<OFF>", "<DATABASENAME_KEYWORD>", "<ADDRLIST_KEYWORD>", "<ADDR_KEYWORD>", 
      "<HOST_KEYWORD>", "<PORT_KEYWORD>", "<PROTOCOL>", "<NONE_KEYWORD>", "<SESSION_KEYWORD>", "<CURSOR_KEYWORD>", "\"{\"", "\"}\"", "\"[\"", "\"]\"", 
      "\";\"", "\",\"", "\".\"", "\"$\"", "\"*\"", "<DIGIT>", "<INTEGER>", "<LOWER>", "<UPPER>", "<SPECIAL>", 
      "\"\\'\"", "<CHARACTER>", "<ID>", "\":\"", "\"@(\"", "\"=\"", "\")\"", "\"(\"", "\"@\"", "\"/\"" };
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdb\\util\TbUrlParserConstants.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */