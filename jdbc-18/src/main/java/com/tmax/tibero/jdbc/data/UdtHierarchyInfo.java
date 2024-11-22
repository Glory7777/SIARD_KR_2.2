package com.tmax.tibero.jdbc.data;

import com.tmax.tibero.jdbc.TbArray;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class UdtHierarchyInfo implements SQLData {
  private UdtInfo udtId;
  
  private TbArray attrIdxArr;
  
  private byte[] properties;
  
  public String getSQLTypeName() throws SQLException {
    return "SYS.HIERARCHY_INFO_T";
  }
  
  public void readSQL(SQLInput paramSQLInput, String paramString) throws SQLException {
    this.udtId = (UdtInfo)paramSQLInput.readObject();
    this.attrIdxArr = (TbArray)paramSQLInput.readArray();
    this.properties = paramSQLInput.readBytes();
  }
  
  public void writeSQL(SQLOutput paramSQLOutput) throws SQLException {}
  
  public UdtInfo getUdtId() {
    return this.udtId;
  }
  
  public TbArray getAttrIdxArr() {
    return this.attrIdxArr;
  }
  
  public byte[] getProperties() {
    return this.properties;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\UdtHierarchyInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */