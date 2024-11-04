package com.tmax.tibero.jdbc.data.binder;

import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.driver.TbConnection;
import java.sql.SQLException;

public class BinaryFloatBinder extends Binder {
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
    float f = paramParamContainer.getParamFloat(paramInt1, paramInt2);
    int i = paramTbStreamDataWriter.getBufferedDataSize();
    int j = 0;
    paramTbStreamDataWriter.makeBufferAvailable(23);
    byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    j = dataTypeConverter.fromBinaryFloat(arrayOfByte, i + 1, f);
    arrayOfByte[i] = (byte)j;
    paramTbStreamDataWriter.moveOffset(j + 1);
    paramTbStreamDataWriter.writePadding(j + 1);
  }
  
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) throws SQLException {
    DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
    float f = paramParamContainer.getParamFloat(paramInt1, paramInt2);
    int i = paramTbStreamDataWriter.getBufferedDataSize();
    int j = 0;
    paramTbStreamDataWriter.makeBufferAvailable(23);
    byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    j = dataTypeConverter.fromBinaryFloat(arrayOfByte, i + 1, f);
    arrayOfByte[i] = (byte)j;
    paramTbStreamDataWriter.moveOffset(j + 1);
    if (paramBoolean)
      paramTbStreamDataWriter.writePadding(j + 1); 
  }
  
  public void bindDFR(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, long paramLong) throws SQLException {}
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\binder\BinaryFloatBinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */