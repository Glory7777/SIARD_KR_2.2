package com.tmax.tibero.jdbc.dpl.binder;

import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.StreamBuffer;
import com.tmax.tibero.jdbc.dpl.TbDirPathStream;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class DPLStreamBinder extends DPLBinder {
  private int length = 0;
  
  private byte[] checkByte = new byte[1];
  
  public void bind(TbConnection paramTbConnection, TbDirPathStream paramTbDirPathStream, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2) throws SQLException {
    InputStream inputStream = paramTbDirPathStream.getParamStream(paramInt1);
    StreamBuffer streamBuffer = paramTbStreamDataWriter.getStreamBuf();
    int i = 0;
    int j = 0;
    int k = 0;
    try {
      if (streamBuffer.getRemained() <= 8)
        paramTbDirPathStream.dirPathLoadStream(paramTbStreamDataWriter, 0); 
      while (paramInt2 >= 0) {
        paramTbStreamDataWriter.writeInt(0, 4);
        j = streamBuffer.getRemained();
        k = (paramInt2 > j - 4) ? (j - 4) : paramInt2;
        i = inputStream.read(streamBuffer.getRawBytes(), streamBuffer.getCurDataSize(), k);
        if (i == -1)
          i = 0; 
        paramTbStreamDataWriter.setCurDataSize(streamBuffer.getCurDataSize() + i);
        paramTbStreamDataWriter.reWriteInt(streamBuffer.getCurDataSize() - i - 4, i, 4);
        int m = paramTbStreamDataWriter.writePaddingDPL(i);
        paramInt2 -= i;
        if (j - i + m <= 4) {
          if (paramInt2 > 0) {
            paramTbDirPathStream.dirPathLoadStream(paramTbStreamDataWriter, 1);
            continue;
          } 
          paramTbDirPathStream.dirPathLoadStream(paramTbStreamDataWriter, 0);
          return;
        } 
        return;
      } 
    } catch (IOException iOException) {
      throw TbError.newSQLException(-90202, iOException.getMessage());
    } 
  }
  
  public int bind(TbConnection paramTbConnection, TbDirPathStream paramTbDirPathStream, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, boolean paramBoolean) throws SQLException {
    InputStream inputStream = paramTbDirPathStream.getParamStream(paramInt1);
    StreamBuffer streamBuffer = paramTbStreamDataWriter.getStreamBuf();
    int i = 0;
    int j = 0;
    int k = 0;
    byte b = 0;
    if (!paramBoolean)
      this.length = paramInt2; 
    try {
      if (streamBuffer.getRemained() <= 8)
        return 3; 
      paramTbStreamDataWriter.writeInt(0, 4);
      if (paramBoolean) {
        paramTbStreamDataWriter.writeBytes(this.checkByte);
        b = 1;
      } 
      j = streamBuffer.getRemained();
      k = (this.length > j - 4) ? (j - 4) : this.length;
      i = inputStream.read(streamBuffer.getRawBytes(), streamBuffer.getCurDataSize(), k);
      if (i == -1)
        i = 0; 
      paramTbStreamDataWriter.setCurDataSize(streamBuffer.getCurDataSize() + i);
      paramTbStreamDataWriter.reWriteInt(streamBuffer.getCurDataSize() - i - 4 - b, i + b, 4);
      int m = paramTbStreamDataWriter.writePaddingDPL(i + b);
      this.length -= i;
      return (j - i + m <= 4) ? ((this.length > 0 && inputStream.read(this.checkByte, 0, 1) > 0) ? 1 : 2) : 0;
    } catch (IOException iOException) {
      throw TbError.newSQLException(-90202, iOException.getMessage());
    } 
  }
  
  public void init() {
    this.length = 0;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\dpl\binder\DPLStreamBinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */