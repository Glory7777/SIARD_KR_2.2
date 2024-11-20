package com.tmax.tibero.jdbc.dpl.binder;

import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.dpl.TbDirPathStream;
import com.tmax.tibero.jdbc.driver.TbConnection;
import java.sql.SQLException;

public class DPLIntBinder extends DPLBinder {
  public void bind(TbConnection paramTbConnection, TbDirPathStream paramTbDirPathStream, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2) throws SQLException {
    DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
    int i = paramTbDirPathStream.getDPLMetaData().getDataType(paramInt1 + 1);
    int j = paramTbDirPathStream.getParamInt(paramInt1);
    paramTbStreamDataWriter.makeBufferAvailable(26);
    byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    int m = paramTbStreamDataWriter.getBufferedDataSize();
    paramTbStreamDataWriter.writeInt(0, 4);
    int n = paramTbStreamDataWriter.getBufferedDataSize();
    int k = dataTypeConverter.castFromInt(arrayOfByte, n, j, i);
    paramTbStreamDataWriter.moveOffset(k);
    paramTbStreamDataWriter.writePaddingDPL(k);
    paramTbStreamDataWriter.reWriteInt(m, k, 4);
  }
  
  public int bind(TbConnection paramTbConnection, TbDirPathStream paramTbDirPathStream, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, boolean paramBoolean) throws SQLException {
    if (!paramBoolean)
      bind(paramTbConnection, paramTbDirPathStream, paramTbStreamDataWriter, paramInt1, paramInt2); 
    return 0;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\dpl\binder\DPLIntBinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */