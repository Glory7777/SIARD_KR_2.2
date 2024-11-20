package com.tmax.tibero.jdbc.data;

public class ServerInfo {
  public static final int LITTLE_ENDIAN = 0;
  
  public static final int BIG_ENDIAN = 1;
  
  public static final int NANOBASE_SVR = 1;
  
  private int protocolMajorVersion = -1;
  
  private int protocolMinorVersion = -1;
  
  private int serverCharset = -1;
  
  private int serverNCharset = -1;
  
  private int serverEndian = -1;
  
  private int tbMajorVersion = -1;
  
  private int tbMinorVersion = -1;
  
  private String tbProductName;
  
  private String tbProductVersion;
  
  private int serverIsNanobase = -1;
  
  private boolean objBindAvailable = false;
  
  public ServerInfo(int paramInt1, int paramInt2) {
    this.serverCharset = paramInt1;
    this.serverEndian = paramInt2;
  }
  
  public ServerInfo(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, String paramString1, String paramString2, int paramInt7, int paramInt8) {
    this.serverCharset = paramInt1;
    this.serverNCharset = paramInt2;
    this.serverEndian = paramInt3;
    this.serverIsNanobase = paramInt4;
    this.tbMajorVersion = paramInt5;
    this.tbMinorVersion = paramInt6;
    this.tbProductName = paramString1;
    this.tbProductVersion = paramString2;
    this.protocolMajorVersion = paramInt7;
    this.protocolMinorVersion = paramInt8;
    if (this.protocolMajorVersion >= 2 && this.protocolMinorVersion >= 15) {
      this.objBindAvailable = true;
    } else {
      this.objBindAvailable = false;
    } 
  }
  
  public boolean getObjBindAvailable() {
    return this.objBindAvailable;
  }
  
  public int getProtocolMajorVersion() {
    return this.protocolMajorVersion;
  }
  
  public int getProtocolMinorVersion() {
    return this.protocolMinorVersion;
  }
  
  public int getServerCharSet() {
    return this.serverCharset;
  }
  
  public int getServerEndian() {
    return this.serverEndian;
  }
  
  public int getServerIsNanobase() {
    return this.serverIsNanobase;
  }
  
  public int getServerNCharSet() {
    return this.serverNCharset;
  }
  
  public int getTbMajor() {
    return this.tbMajorVersion;
  }
  
  public int getTbMinor() {
    return this.tbMinorVersion;
  }
  
  public String getTbProductName() {
    return this.tbProductName;
  }
  
  public String getTbProductVersion() {
    return this.tbProductVersion;
  }
  
  public void setProtocolMajorVersion(int paramInt) {
    this.protocolMajorVersion = paramInt;
  }
  
  public void setProtocolMinorVersion(int paramInt) {
    this.protocolMinorVersion = paramInt;
  }
  
  public void setServerCharSet(int paramInt) {
    this.serverCharset = paramInt;
  }
  
  public void setServerEndian(int paramInt) {
    this.serverEndian = paramInt;
  }
  
  public void setServerIsNanobase(int paramInt) {
    this.serverIsNanobase = paramInt;
  }
  
  public void setServerNCharSet(int paramInt) {
    this.serverNCharset = paramInt;
  }
  
  public void setTbMajor(int paramInt) {
    this.tbMajorVersion = paramInt;
  }
  
  public void setTbMinor(int paramInt) {
    this.tbMinorVersion = paramInt;
  }
  
  public void setTbProductName(String paramString) {
    this.tbProductName = paramString;
  }
  
  public void setTbProductVersion(String paramString) {
    this.tbProductVersion = paramString;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(192);
    stringBuilder.append("ServerInfo[ProductName=").append(this.tbProductName).append("/ProductVersion=").append(this.tbProductVersion).append("/MajorVer=").append(this.tbMajorVersion).append("/MinorVer=").append(this.tbMinorVersion).append("/ProtocolMajor=").append(this.protocolMajorVersion).append("/ProtocolMinor=").append(this.protocolMinorVersion).append("/CharSet=").append(this.serverCharset).append("/NCharSet=").append(this.serverNCharset).append("/isNanobase=").append(this.serverIsNanobase).append("]");
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\ServerInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */