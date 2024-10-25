package com.tmax.tibero.jdbc.data.binder;

import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.driver.TbConnection;
import java.sql.SQLException;

public class BytesBinder extends Binder {
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    byte b;
    byte[] arrayOfByte1 = paramParamContainer.getParamBytes(paramInt1, paramInt2);
    int i = paramInt3;
    int j = paramTbStreamDataWriter.getBufferedDataSize();
    paramTbStreamDataWriter.makeBufferAvailable(i + 3);
    byte[] arrayOfByte2 = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    if (i <= 250) {
      arrayOfByte2[j] = (byte)i;
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, j + 1, i);
      b = 1;
    } else {
      arrayOfByte2[j] = -2;
      arrayOfByte2[j + 1] = (byte)(0xFF & i >> 8);
      arrayOfByte2[j + 2] = (byte)(0xFF & i);
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, j + 3, i);
      b = 3;
    } 
    paramTbStreamDataWriter.moveOffset(i + b);
    paramTbStreamDataWriter.writePadding(i + b);
  }
  
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) throws SQLException {
    byte b;
    byte[] arrayOfByte1 = paramParamContainer.getParamBytes(paramInt1, paramInt2);
    int i = arrayOfByte1.length;
    int j = paramTbStreamDataWriter.getBufferedDataSize();
    paramTbStreamDataWriter.makeBufferAvailable(i + 3);
    byte[] arrayOfByte2 = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    if (i <= 250) {
      arrayOfByte2[j] = (byte)i;
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, j + 1, i);
      b = 1;
    } else {
      arrayOfByte2[j] = -2;
      arrayOfByte2[j + 1] = (byte)(0xFF & i >> 8);
      arrayOfByte2[j + 2] = (byte)(0xFF & i);
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, j + 3, i);
      b = 3;
    } 
    paramTbStreamDataWriter.moveOffset(i + b);
    if (paramBoolean)
      paramTbStreamDataWriter.writePadding(i + b); 
  }
  
  public void bindDFR(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, long paramLong) throws SQLException {}
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\binder\BytesBinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */