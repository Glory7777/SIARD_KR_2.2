package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbHlTableInfoList {
  public String tableName;
  
  public String owner;
  
  public int descArrayCnt;
  
  public TbColumnDesc[] desc;
  
  public void set(String paramString1, String paramString2, int paramInt, TbColumnDesc[] paramArrayOfTbColumnDesc) {
    this.tableName = paramString1;
    this.owner = paramString2;
    this.descArrayCnt = paramInt;
    this.desc = paramArrayOfTbColumnDesc;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    int i = paramTbStreamDataReader.readInt32();
    this.tableName = paramTbStreamDataReader.readDBDecodedPadString(i);
    int j = paramTbStreamDataReader.readInt32();
    this.owner = paramTbStreamDataReader.readDBDecodedPadString(j);
    int k = paramTbStreamDataReader.readInt32();
    if (k > 0) {
      this.desc = new TbColumnDesc[k];
      for (byte b = 0; b < k; b++) {
        this.desc[b] = new TbColumnDesc();
        this.desc[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.desc = null;
    } 
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbHlTableInfoList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */