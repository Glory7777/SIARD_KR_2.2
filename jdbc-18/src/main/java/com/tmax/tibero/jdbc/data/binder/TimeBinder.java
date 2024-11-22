package com.tmax.tibero.jdbc.data.binder;

import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.driver.TbConnection;
import java.sql.SQLException;
import java.util.Calendar;

public class TimeBinder extends Binder {
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
    Calendar calendar = paramParamContainer.getParamCalendar(paramInt1, paramInt2);
    int i = paramTbStreamDataWriter.getBufferedDataSize();
    paramTbStreamDataWriter.makeBufferAvailable(9);
    byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    int j = dataTypeConverter.fromTime(arrayOfByte, i + 1, calendar, 0);
    arrayOfByte[i] = (byte)j;
    paramTbStreamDataWriter.moveOffset(j + 1);
    paramTbStreamDataWriter.writePadding(j + 1);
  }
  
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) throws SQLException {
    DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
    Calendar calendar = paramParamContainer.getParamCalendar(paramInt1, paramInt2);
    int i = paramTbStreamDataWriter.getBufferedDataSize();
    paramTbStreamDataWriter.makeBufferAvailable(9);
    byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    int j = dataTypeConverter.fromTime(arrayOfByte, i + 1, calendar, 0);
    arrayOfByte[i] = (byte)j;
    paramTbStreamDataWriter.moveOffset(j + 1);
    if (paramBoolean)
      paramTbStreamDataWriter.writePadding(j + 1); 
  }
  
  public void bindDFR(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, long paramLong) throws SQLException {}
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\binder\TimeBinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */