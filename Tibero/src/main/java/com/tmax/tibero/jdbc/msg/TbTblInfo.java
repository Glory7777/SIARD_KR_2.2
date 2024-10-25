package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbTblInfo {
  public int gtid;
  
  public int tabletNo;
  
  public void set(int paramInt1, int paramInt2) {
    this.gtid = paramInt1;
    this.tabletNo = paramInt2;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.gtid = paramTbStreamDataReader.readInt32();
    this.tabletNo = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbTblInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */