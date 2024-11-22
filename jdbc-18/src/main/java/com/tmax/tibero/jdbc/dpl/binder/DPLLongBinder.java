package com.tmax.tibero.jdbc.dpl.binder;

import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.dpl.TbDirPathStream;
import com.tmax.tibero.jdbc.driver.TbConnection;
import java.sql.SQLException;

public class DPLLongBinder extends DPLBinder {
  public void bind(TbConnection paramTbConnection, TbDirPathStream paramTbDirPathStream, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2) throws SQLException {
    DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
    int i = paramTbDirPathStream.getDPLMetaData().getDataType(paramInt1 + 1);
    long l = paramTbDirPathStream.getParamLong(paramInt1);
    paramTbStreamDataWriter.makeBufferAvailable(26);
    byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    int k = paramTbStreamDataWriter.getBufferedDataSize();
    paramTbStreamDataWriter.writeInt(0, 4);
    int m = paramTbStreamDataWriter.getBufferedDataSize();
    int j = dataTypeConverter.castFromLong(arrayOfByte, m, l, i);
    paramTbStreamDataWriter.moveOffset(j);
    paramTbStreamDataWriter.writePaddingDPL(j);
    paramTbStreamDataWriter.reWriteInt(k, j, 4);
  }
  
  public int bind(TbConnection paramTbConnection, TbDirPathStream paramTbDirPathStream, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, boolean paramBoolean) throws SQLException {
    if (!paramBoolean)
      bind(paramTbConnection, paramTbDirPathStream, paramTbStreamDataWriter, paramInt1, paramInt2); 
    return 0;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\dpl\binder\DPLLongBinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */