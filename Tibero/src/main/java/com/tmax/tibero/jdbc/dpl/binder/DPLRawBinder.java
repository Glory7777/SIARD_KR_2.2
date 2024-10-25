package com.tmax.tibero.jdbc.dpl.binder;

import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.dpl.TbDirPathStream;
import com.tmax.tibero.jdbc.driver.TbConnection;
import java.sql.SQLException;

public class DPLRawBinder extends DPLBinder {
  public void bind(TbConnection paramTbConnection, TbDirPathStream paramTbDirPathStream, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2) throws SQLException {
    byte[] arrayOfByte1 = paramTbDirPathStream.getParamBytes(paramInt1);
    paramTbStreamDataWriter.makeBufferAvailable(arrayOfByte1.length + 4);
    byte[] arrayOfByte2 = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    int i = paramTbStreamDataWriter.getBufferedDataSize();
    paramTbStreamDataWriter.writeInt(0, 4);
    int j = paramTbStreamDataWriter.getBufferedDataSize();
    System.arraycopy(arrayOfByte1, 0, arrayOfByte2, j, arrayOfByte1.length);
    paramTbStreamDataWriter.moveOffset(arrayOfByte1.length);
    paramTbStreamDataWriter.writePaddingDPL(arrayOfByte1.length);
    paramTbStreamDataWriter.reWriteInt(i, arrayOfByte1.length, 4);
  }
  
  public int bind(TbConnection paramTbConnection, TbDirPathStream paramTbDirPathStream, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, boolean paramBoolean) throws SQLException {
    if (!paramBoolean)
      bind(paramTbConnection, paramTbDirPathStream, paramTbStreamDataWriter, paramInt1, paramInt2); 
    return 0;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\dpl\binder\DPLRawBinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */