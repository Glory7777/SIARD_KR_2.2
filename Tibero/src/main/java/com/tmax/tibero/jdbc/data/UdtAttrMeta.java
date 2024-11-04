package com.tmax.tibero.jdbc.data;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class UdtAttrMeta implements SQLData {
  private String attrName;
  
  private int typeNo;
  
  private int prec;
  
  private int length;
  
  private int scale;
  
  private String oid;
  
  public String getSQLTypeName() throws SQLException {
    return "SYS.ATTR_META_T";
  }
  
  public void readSQL(SQLInput paramSQLInput, String paramString) throws SQLException {
    this.attrName = paramSQLInput.readString();
    this.typeNo = paramSQLInput.readInt();
    this.prec = paramSQLInput.readInt();
    this.length = paramSQLInput.readInt();
    this.scale = paramSQLInput.readInt();
    this.oid = paramSQLInput.readString();
  }
  
  public void writeSQL(SQLOutput paramSQLOutput) throws SQLException {}
  
  public String getAttrName() {
    return this.attrName;
  }
  
  public int getTypeNo() {
    return this.typeNo;
  }
  
  public int getPrec() {
    return this.prec;
  }
  
  public int getLength() {
    return this.length;
  }
  
  public int getScale() {
    return this.scale;
  }
  
  public String getOid() {
    return this.oid;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(128);
    stringBuilder.append("UdtAttrMeta[attrName=").append(this.attrName).append("/typeNo=").append(this.typeNo).append("/prec=").append(this.prec).append("/length=").append(this.length).append("/scale=").append(this.scale).append("/OID=").append(this.oid).append("]");
    return stringBuilder.toString();
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\UdtAttrMeta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */