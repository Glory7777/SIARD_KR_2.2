package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgHlTableExtractRequest extends TbMsg {
  public String dbName;
  
  public String dbIp;
  
  public int dbPort;
  
  public int tableNameListArrayCnt;
  
  public TbHlTableInfoList[] tableNameList;
  
  public String password;
  
  public int isBatch;
  
  public int readAsByte;
  
  public int hasXml;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    int i = paramTbStreamDataReader.readInt32();
    this.dbName = paramTbStreamDataReader.readDBDecodedPadString(i);
    int j = paramTbStreamDataReader.readInt32();
    this.dbIp = paramTbStreamDataReader.readDBDecodedPadString(j);
    this.dbPort = paramTbStreamDataReader.readInt32();
    int k = paramTbStreamDataReader.readInt32();
    if (k > 0) {
      this.tableNameList = new TbHlTableInfoList[k];
      for (byte b = 0; b < k; b++) {
        this.tableNameList[b] = new TbHlTableInfoList();
        this.tableNameList[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.tableNameList = null;
    } 
    int m = paramTbStreamDataReader.readInt32();
    this.password = paramTbStreamDataReader.readDBDecodedPadString(m);
    this.isBatch = paramTbStreamDataReader.readInt32();
    this.readAsByte = paramTbStreamDataReader.readInt32();
    this.hasXml = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbMsgHlTableExtractRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */