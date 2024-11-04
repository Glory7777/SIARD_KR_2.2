package com.tmax.tibero.jdbc.data.binder;

import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.driver.TbConnection;
import java.sql.SQLException;

public class StringBinder extends Binder {
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    int k;
    byte b;
    DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
    String str = paramParamContainer.getParamString(paramInt1, paramInt2);
    int i = str.length() * dataTypeConverter.getMaxBytesPerChar();
    int j = paramTbStreamDataWriter.getBufferedDataSize();
    paramTbStreamDataWriter.makeBufferAvailable(i + 3);
    byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    if (i <= 250) {
      k = dataTypeConverter.fromString(arrayOfByte, j + 1, str);
      arrayOfByte[j] = (byte)k;
      b = 1;
    } else {
      k = dataTypeConverter.fromString(arrayOfByte, j + 3, str);
      if (k <= 250) {
        arrayOfByte[j] = (byte)k;
        b = 1;
        System.arraycopy(arrayOfByte, j + 3, arrayOfByte, j + 1, k);
      } else {
        arrayOfByte[j] = -2;
        arrayOfByte[j + 1] = (byte)(0xFF & k >> 8);
        arrayOfByte[j + 2] = (byte)(0xFF & k);
        b = 3;
      } 
    } 
    paramTbStreamDataWriter.moveOffset(k + b);
    paramTbStreamDataWriter.writePadding(k + b);
  }
  
  public void bindDFR(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, long paramLong) throws SQLException {}
  
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) throws SQLException {
    int k;
    byte b;
    DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
    String str = paramParamContainer.getParamString(paramInt1, paramInt2);
    int i = str.length() * dataTypeConverter.getMaxBytesPerChar();
    int j = paramTbStreamDataWriter.getBufferedDataSize();
    paramTbStreamDataWriter.makeBufferAvailable(i + 3);
    byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    if (i <= 250) {
      k = dataTypeConverter.fromString(arrayOfByte, j + 1, str);
      arrayOfByte[j] = (byte)k;
      b = 1;
    } else {
      k = dataTypeConverter.fromString(arrayOfByte, j + 3, str);
      if (k <= 250) {
        arrayOfByte[j] = (byte)k;
        b = 1;
        System.arraycopy(arrayOfByte, j + 3, arrayOfByte, j + 1, k);
      } else {
        arrayOfByte[j] = -2;
        arrayOfByte[j + 1] = (byte)(0xFF & k >> 8);
        arrayOfByte[j + 2] = (byte)(0xFF & k);
        b = 3;
      } 
    } 
    paramTbStreamDataWriter.moveOffset(k + b);
    if (paramBoolean)
      paramTbStreamDataWriter.writePadding(k + b); 
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\binder\StringBinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */