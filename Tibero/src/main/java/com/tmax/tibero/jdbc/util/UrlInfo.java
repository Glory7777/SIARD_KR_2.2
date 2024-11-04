package com.tmax.tibero.jdbc.util;

import java.util.Vector;

class UrlInfo {
  String driverType = "thin";
  
  String protocol = null;
  
  int failoverType = 0;
  
  Boolean isLoadBalance = null;
  
  boolean isInternalUrl = false;
  
  String databaseName;
  
  String userName;
  
  String password;
  
  Vector nodeList = new Vector();
  
  public String getDriverType() {
    return this.driverType;
  }
  
  public void setDriverType(String paramString) {
    this.driverType = paramString;
  }
  
  public String getProtocol() {
    return this.protocol;
  }
  
  public void setProtocol(String paramString) {
    this.protocol = paramString;
  }
  
  public int getFailover() {
    return this.failoverType;
  }
  
  public void setFailover(int paramInt) {
    this.failoverType = paramInt;
  }
  
  public Boolean isLoadBalance() {
    return this.isLoadBalance;
  }
  
  public void setLoadBalance(boolean paramBoolean) {
    this.isLoadBalance = paramBoolean ? Boolean.TRUE : Boolean.FALSE;
  }
  
  public boolean isInternalUrl() {
    return this.isInternalUrl;
  }
  
  public void setInternalUrl(boolean paramBoolean) {
    this.isInternalUrl = paramBoolean;
  }
  
  public String getDatabaseName() {
    return this.databaseName;
  }
  
  public void setDatabaseName(String paramString) {
    this.databaseName = paramString;
  }
  
  public String getUserName() {
    return this.userName;
  }
  
  public void setUserName(String paramString) {
    this.userName = paramString;
  }
  
  public String getPassword() {
    return this.password;
  }
  
  public void setPassword(String paramString) {
    this.password = paramString;
  }
  
  public Vector getNodeList() {
    return this.nodeList;
  }
  
  public void setNodeList(Vector paramVector) {
    this.nodeList = paramVector;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdb\\util\UrlInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */