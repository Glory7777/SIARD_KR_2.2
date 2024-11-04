package com.tmax.tibero.jdbc.data.binder;

import com.tmax.tibero.jdbc.comm.TbStream;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.driver.TbConnection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class StreamBinder extends Binder {
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    paramTbStreamDataWriter.writeRpcolData(null, 0);
  }
  
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) throws SQLException {
    paramTbStreamDataWriter.writeRpcolData(null, 0);
  }
  
  public void bindDFR(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, long paramLong) throws SQLException {
    boolean bool1;
    boolean bool2;
    InputStream inputStream = paramParamContainer.getParamStream(paramInt1, paramInt2);
    int i = paramParamContainer.getBindData().getBindItem(paramInt2).getSQLType();
    TbStream tbStream = paramTbConnection.getTbComm().getStream();
    int j = 0;
    int k = paramTbStreamDataWriter.getBufferedDataSize();
    DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
    int m = 0;
    byte[] arrayOfByte = null;
    switch (i) {
      case -1:
      case 1:
      case 12:
        bool1 = true;
        bool2 = false;
        arrayOfByte = new byte[dataTypeConverter.getMaxBytesPerChar()];
        break;
      case -16:
      case -15:
      case -9:
        bool1 = false;
        bool2 = true;
        arrayOfByte = new byte[dataTypeConverter.getMaxBytesPerNChar()];
        break;
      default:
        bool1 = bool2 = false;
        break;
    } 
    while (paramLong > 0L) {
      paramTbStreamDataWriter.setCurDataSize(k);
      if (paramLong < 65532L) {
        j = (int)paramLong;
      } else {
        j = 65532;
      } 
      paramTbStreamDataWriter.makeBufferAvailable(j + 4);
      byte[] arrayOfByte1 = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
      if (m == 0) {
        j -= m;
        System.arraycopy(arrayOfByte, 0, arrayOfByte1, k + 4, m);
      }
        int n = 0;
        try {
            n = inputStream.read(arrayOfByte1, k + 4 + m, j);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (n == -1) {
        if (m > 0) {
          n = m;
          m = 0;
        } else {
          break;
        } 
      } else {
        n += m;
        int i1 = k + 4 + n - 1;
        int i2 = 0;
        if (bool1 && paramTbStreamDataWriter.reWriteInt(k, 0, 4) > 0 && !dataTypeConverter.isEndingByte(arrayOfByte1, i1)) {
          i2 = dataTypeConverter.getLeadingBytePos(arrayOfByte1, i1);
          m = i1 - i2 + 1;
        } else if (bool2 && paramTbStreamDataWriter.reWriteInt(k, 0, 4) > 0 && !dataTypeConverter.isEndingByteNCharset(arrayOfByte1, i1)) {
          i2 = dataTypeConverter.getLeadingBytePosNCharset(arrayOfByte1, i1);
          m = i1 - i2 + 1;
        } else {
          m = 0;
        } 
        if (m > 0) {
          n -= m;
          System.arraycopy(arrayOfByte1, i2, arrayOfByte, 0, m);
        } 
      } 
      paramTbStreamDataWriter.writeInt(n, 4);
      paramTbStreamDataWriter.moveOffset(n);
      paramTbStreamDataWriter.writePadding(n);
      paramTbStreamDataWriter.reWriteInt(4, paramTbStreamDataWriter.getBufferedDataSize() - 16, 4);
      tbStream.flush();
      paramLong -= n;
    } 
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\binder\StreamBinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */