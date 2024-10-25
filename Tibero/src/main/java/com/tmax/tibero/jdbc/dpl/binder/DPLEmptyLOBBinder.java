package com.tmax.tibero.jdbc.dpl.binder;

import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.dpl.TbDirPathStream;
import com.tmax.tibero.jdbc.driver.TbConnection;
import java.sql.SQLException;

public class DPLEmptyLOBBinder extends DPLBinder {
  public void bind(TbConnection paramTbConnection, TbDirPathStream paramTbDirPathStream, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2) throws SQLException {
    paramTbStreamDataWriter.writeInt(-1, 4);
  }
  
  public int bind(TbConnection paramTbConnection, TbDirPathStream paramTbDirPathStream, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, boolean paramBoolean) throws SQLException {
    if (!paramBoolean)
      bind(paramTbConnection, paramTbDirPathStream, paramTbStreamDataWriter, paramInt1, paramInt2); 
    return 0;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\dpl\binder\DPLEmptyLOBBinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */