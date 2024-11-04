package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbTblSchema {
  public String tableName;
  
  public int objId;
  
  public int colSchemaListArrayCnt;
  
  public TbColSchema[] colSchemaList;
  
  public int idxSchemaListArrayCnt;
  
  public TbIdxSchema[] idxSchemaList;
  
  public int conSchemaListArrayCnt;
  
  public TbConSchema[] conSchemaList;
  
  public void set(String paramString, int paramInt1, int paramInt2, TbColSchema[] paramArrayOfTbColSchema, int paramInt3, TbIdxSchema[] paramArrayOfTbIdxSchema, int paramInt4, TbConSchema[] paramArrayOfTbConSchema) {
    this.tableName = paramString;
    this.objId = paramInt1;
    this.colSchemaListArrayCnt = paramInt2;
    this.colSchemaList = paramArrayOfTbColSchema;
    this.idxSchemaListArrayCnt = paramInt3;
    this.idxSchemaList = paramArrayOfTbIdxSchema;
    this.conSchemaListArrayCnt = paramInt4;
    this.conSchemaList = paramArrayOfTbConSchema;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    int i = paramTbStreamDataReader.readInt32();
    this.tableName = paramTbStreamDataReader.readDBDecodedPadString(i);
    this.objId = paramTbStreamDataReader.readInt32();
    int j = paramTbStreamDataReader.readInt32();
    if (j > 0) {
      this.colSchemaList = new TbColSchema[j];
      for (byte b = 0; b < j; b++) {
        this.colSchemaList[b] = new TbColSchema();
        this.colSchemaList[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.colSchemaList = null;
    } 
    int k = paramTbStreamDataReader.readInt32();
    if (k > 0) {
      this.idxSchemaList = new TbIdxSchema[k];
      for (byte b = 0; b < k; b++) {
        this.idxSchemaList[b] = new TbIdxSchema();
        this.idxSchemaList[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.idxSchemaList = null;
    } 
    int m = paramTbStreamDataReader.readInt32();
    if (m > 0) {
      this.conSchemaList = new TbConSchema[m];
      for (byte b = 0; b < m; b++) {
        this.conSchemaList[b] = new TbConSchema();
        this.conSchemaList[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.conSchemaList = null;
    } 
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbTblSchema.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */