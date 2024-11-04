package com.tmax.tibero.jdbc.data.binder;

import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.data.TbNumber;
import com.tmax.tibero.jdbc.driver.TbConnection;
import java.math.BigDecimal;
import java.sql.SQLException;

public class BigDecimalBinder extends Binder {
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    BigDecimal bigDecimal = paramParamContainer.getParamBigDecimal(paramInt1, paramInt2);
    int i = paramTbStreamDataWriter.getBufferedDataSize();
    int j = 0;
    paramTbStreamDataWriter.makeBufferAvailable(23);
    byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    j = TbNumber.fromString(arrayOfByte, i + 1, bigDecimal.toString());
    arrayOfByte[i] = (byte)j;
    paramTbStreamDataWriter.moveOffset(j + 1);
    paramTbStreamDataWriter.writePadding(j + 1);
  }
  
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) throws SQLException {
    BigDecimal bigDecimal = paramParamContainer.getParamBigDecimal(paramInt1, paramInt2);
    int i = paramTbStreamDataWriter.getBufferedDataSize();
    int j = 0;
    paramTbStreamDataWriter.makeBufferAvailable(22);
    byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    j = TbNumber.fromString(arrayOfByte, i, bigDecimal.toString());
    paramTbStreamDataWriter.moveOffset(j);
    if (paramBoolean)
      paramTbStreamDataWriter.writePadding(j); 
  }
  
  public void bindDFR(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, long paramLong) throws SQLException {}
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\binder\BigDecimalBinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */