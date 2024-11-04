package com.tmax.tibero.jdbc.data.binder;

import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.data.TbTimestampTZ;
import com.tmax.tibero.jdbc.driver.TbConnection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.TimeZone;

public class TbTimestampTZBinder extends Binder {
  private static final TimeZone TZ_UTC = TimeZone.getTimeZone("UTC");
  
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
    TbTimestampTZ tbTimestampTZ = paramParamContainer.getParamTbTimestampTZ(paramInt1, paramInt2);
    int i = paramTbStreamDataWriter.getBufferedDataSize();
    paramTbStreamDataWriter.makeBufferAvailable(13);
    byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(tbTimestampTZ.getTime());
    int j = dataTypeConverter.fromTimestampTZ(arrayOfByte, i + 1, calendar, tbTimestampTZ.getNanos(), tbTimestampTZ.getTimeZone());
    arrayOfByte[i] = (byte)j;
    paramTbStreamDataWriter.moveOffset(j + 1);
    paramTbStreamDataWriter.writePadding(j + 1);
  }
  
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) throws SQLException {
    DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
    TbTimestampTZ tbTimestampTZ = paramParamContainer.getParamTbTimestampTZ(paramInt1, paramInt2);
    int i = paramTbStreamDataWriter.getBufferedDataSize();
    paramTbStreamDataWriter.makeBufferAvailable(13);
    byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(tbTimestampTZ.getTime());
    int j = dataTypeConverter.fromTimestampTZ(arrayOfByte, i + 1, calendar, tbTimestampTZ.getNanos(), tbTimestampTZ.getTimeZone());
    arrayOfByte[i] = (byte)j;
    paramTbStreamDataWriter.moveOffset(j + 1);
    if (paramBoolean)
      paramTbStreamDataWriter.writePadding(j + 1); 
  }
  
  public void bindDFR(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, long paramLong) throws SQLException {}
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\binder\TbTimestampTZBinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */