package com.tmax.tibero.jdbc.data.binder;

import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.driver.TbConnection;
import java.sql.SQLException;

public class NullBinder extends Binder {
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    paramTbStreamDataWriter.writeRpcolData(null, 0);
  }
  
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) throws SQLException {
    paramTbStreamDataWriter.writeRpcolData(null, 0, paramBoolean);
  }
  
  public void bindDFR(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, long paramLong) throws SQLException {}
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\binder\NullBinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */