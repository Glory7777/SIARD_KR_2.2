package com.tmax.tibero.jdbc.util;

import java.util.ResourceBundle;

public class TbResourceBundle {
  private ResourceBundle bundle;
  
  public static String JDBC_ERROR_BASE_KEY = "JDBC-";
  
  public TbResourceBundle(String paramString) {
    this.bundle = ResourceBundle.getBundle(paramString);
  }
  
  public static String getKey(int paramInt) {
    int i = Math.abs(paramInt);
    return JDBC_ERROR_BASE_KEY + Integer.toString(i);
  }
  
  public String getValue(int paramInt) {
    String str = getKey(paramInt);
    return this.bundle.getString(str);
  }
  
  public String getValue(String paramString) {
    return this.bundle.getString(paramString);
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdb\\util\TbResourceBundle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */