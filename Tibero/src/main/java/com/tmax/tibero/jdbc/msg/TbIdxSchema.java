package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbIdxSchema {
  public String idxName;
  
  public int typeNo;
  
  public int flags;
  
  public int icolSchemaListArrayCnt;
  
  public TbIcolSchema[] icolSchemaList;
  
  public void set(String paramString, int paramInt1, int paramInt2, int paramInt3, TbIcolSchema[] paramArrayOfTbIcolSchema) {
    this.idxName = paramString;
    this.typeNo = paramInt1;
    this.flags = paramInt2;
    this.icolSchemaListArrayCnt = paramInt3;
    this.icolSchemaList = paramArrayOfTbIcolSchema;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    int i = paramTbStreamDataReader.readInt32();
    this.idxName = paramTbStreamDataReader.readDBDecodedPadString(i);
    this.typeNo = paramTbStreamDataReader.readInt32();
    this.flags = paramTbStreamDataReader.readInt32();
    int j = paramTbStreamDataReader.readInt32();
    if (j > 0) {
      this.icolSchemaList = new TbIcolSchema[j];
      for (byte b = 0; b < j; b++) {
        this.icolSchemaList[b] = new TbIcolSchema();
        this.icolSchemaList[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.icolSchemaList = null;
    } 
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbIdxSchema.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */