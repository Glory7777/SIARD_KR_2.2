package com.tmax.tibero;

import java.io.PrintWriter;
import java.sql.DriverManager;

public class Debug {
  public static final boolean TRACE = false;
  
  public static final boolean NETWORK = false;
  
  public static final boolean CONNECTION = false;
  
  public static final boolean FAILOVER = false;
  
  private static final int CAT_METHOD = 0;
  
  private static final int CAT_RETURN = 1;
  
  private static final int CAT_MSG = 2;
  
  private static PrintWriter lw = null;
  
  public static String getHexFromBytes(byte[] paramArrayOfbyte) {
    return null;
  }
  
  public static String getHexFromBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    return null;
  }
  
  public static PrintWriter getLogWriter() {
    return lw;
  }
  
  private static String getTimeString() {
    return null;
  }
  
  public static void log(String paramString) {}
  
  public static void logMethod(String paramString, Object[] paramArrayOfObject) {}
  
  public static void logMethod(String paramString, String[] paramArrayOfString, Object[] paramArrayOfObject) {}
  
  public static void logReturn(String paramString, Object paramObject) {}
  
  public static void logThrowable(Throwable paramThrowable) {}
  
  public static void setLogWriter(PrintWriter paramPrintWriter) {
    lw = paramPrintWriter;
  }
  
  static {
    lw = DriverManager.getLogWriter();
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\Debug.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */