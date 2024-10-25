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


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdb\\util\TbResourceBundle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */