package com.tmax.tibero.jdbc.data;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Arrays;

public class UdtInfo implements SQLData {
  private String userName;
  
  private String schemaName;
  
  private String udtName;
  
  private String oid;
  
  private int tobjID;
  
  private int versionNo;
  
  private int typeNo;
  
  private byte[] properties;
  
  public String getSQLTypeName() throws SQLException {
    return "SYS.UDT_INFO_T";
  }
  
  public void readSQL(SQLInput paramSQLInput, String paramString) throws SQLException {
    this.userName = paramSQLInput.readString();
    this.schemaName = paramSQLInput.readString();
    this.udtName = paramSQLInput.readString();
    this.oid = paramSQLInput.readString();
    this.tobjID = paramSQLInput.readInt();
    this.versionNo = paramSQLInput.readInt();
    this.typeNo = paramSQLInput.readInt();
    this.properties = paramSQLInput.readBytes();
  }
  
  public void writeSQL(SQLOutput paramSQLOutput) throws SQLException {}
  
  public String getUserName() {
    return this.userName;
  }
  
  public String getSchemaName() {
    return this.schemaName;
  }
  
  public String getUdtName() {
    return this.udtName;
  }
  
  public String getOid() {
    return this.oid;
  }
  
  public int getTobjID() {
    return this.tobjID;
  }
  
  public int getVersionNo() {
    return this.versionNo;
  }
  
  public int getTypeNo() {
    return this.typeNo;
  }
  
  public byte[] getProperties() {
    return this.properties;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(128);
    stringBuilder.append("UdtInfo[userName=").append(this.userName).append("/schemaName=").append(this.schemaName).append("/udtName=").append(this.udtName).append("/oid=").append(this.oid).append("/tobjID=").append(this.tobjID).append("/versionNo=").append(this.versionNo).append("/typeNo=").append(this.typeNo).append("/properties=");
    stringBuilder.append((this.properties == null) ? null : Arrays.toString(this.properties));
    stringBuilder.append("]");
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\UdtInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */