package com.tmax.tibero.jdbc.data.binder;

import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.driver.TbConnection;
import java.sql.SQLException;

public class IntBinder extends Binder {
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
    int i = paramParamContainer.getParamInt(paramInt1, paramInt2);
    int j = paramTbStreamDataWriter.getBufferedDataSize();
    int k = 0;
    paramTbStreamDataWriter.makeBufferAvailable(23);
    byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    k = dataTypeConverter.fromInt(arrayOfByte, j + 1, i);
    arrayOfByte[j] = (byte)k;
    paramTbStreamDataWriter.moveOffset(k + 1);
    paramTbStreamDataWriter.writePadding(k + 1);
  }
  
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) throws SQLException {
    DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
    int i = paramParamContainer.getParamInt(paramInt1, paramInt2);
    int j = paramTbStreamDataWriter.getBufferedDataSize();
    int k = 0;
    paramTbStreamDataWriter.makeBufferAvailable(23);
    byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    k = dataTypeConverter.fromInt(arrayOfByte, j + 1, i);
    arrayOfByte[j] = (byte)k;
    paramTbStreamDataWriter.moveOffset(k + 1);
    if (paramBoolean)
      paramTbStreamDataWriter.writePadding(k + 1); 
  }
  
  public void bindDFR(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, long paramLong) throws SQLException {}
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\binder\IntBinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */