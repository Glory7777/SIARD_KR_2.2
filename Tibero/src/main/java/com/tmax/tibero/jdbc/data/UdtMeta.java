package com.tmax.tibero.jdbc.data;

import tibero.jdbc.TbArray;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class UdtMeta implements SQLData {
  private int version;
  
  private UdtInfo udtId;
  
  private TbArray attrMetaArr;
  
  private TbArray hierarchyInfoArr;
  
  private int hierarchyCnt;
  
  private int elemOrAttrCnt;
  
  public String getSQLTypeName() throws SQLException {
    return "SYS.UDT_META_T";
  }
  
  public void readSQL(SQLInput paramSQLInput, String paramString) throws SQLException {
    this.version = paramSQLInput.readInt();
    this.udtId = (UdtInfo)paramSQLInput.readObject();
    this.attrMetaArr = (TbArray)paramSQLInput.readArray();
    this.hierarchyInfoArr = (TbArray)paramSQLInput.readArray();
    this.hierarchyCnt = paramSQLInput.readInt();
    this.elemOrAttrCnt = paramSQLInput.readInt();
  }
  
  public void writeSQL(SQLOutput paramSQLOutput) throws SQLException {}
  
  public int getVersion() {
    return this.version;
  }
  
  public UdtInfo getUdtId() {
    return this.udtId;
  }
  
  public TbArray getAttrMetaArr() {
    return this.attrMetaArr;
  }
  
  public TbArray getHierarchyInfoArr() {
    return this.hierarchyInfoArr;
  }
  
  public int getHierarchyCnt() {
    return this.hierarchyCnt;
  }
  
  public int getElemOrAttrCnt() {
    return this.elemOrAttrCnt;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(128);
    stringBuilder.append("UdtMeta[version=").append(this.version).append("/udtId=").append(this.udtId).append("/attrMetaArr=").append(this.attrMetaArr).append("/hierarchyInfoArr=").append(this.hierarchyInfoArr).append("/hierarchyCnt=").append(this.hierarchyCnt).append("/elemOrAttrCnt=").append(this.elemOrAttrCnt).append("]");
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\UdtMeta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */