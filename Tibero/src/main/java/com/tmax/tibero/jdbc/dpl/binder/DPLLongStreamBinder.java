package com.tmax.tibero.jdbc.dpl.binder;

import com.tmax.tibero.jdbc.TbDatabaseMetaData;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.StreamBuffer;
import com.tmax.tibero.jdbc.dpl.TbDirPathStream;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;

public class DPLLongStreamBinder extends DPLBinder {
  private int length;
  
  private byte[] convBuf;
  
  private int offset = 0;
  
  private int unReadBytes = 0;
  
  public void bind(TbConnection paramTbConnection, TbDirPathStream paramTbDirPathStream, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2) throws SQLException {
    InputStream inputStream = paramTbDirPathStream.getParamStream(paramInt1);
    StreamBuffer streamBuffer = paramTbStreamDataWriter.getStreamBuf();
    String str1 = paramTbDirPathStream.getDPLMetaData().getClientCharSet();
    String str2 = ((TbDatabaseMetaData)paramTbConnection.getMetaData()).getServerCharSet();
    byte[] arrayOfByte = new byte[16384];
    int i = 0;
    int bool = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    try {
      if (streamBuffer.getRemained() <= 4)
        paramTbDirPathStream.dirPathLoadStream(paramTbStreamDataWriter, 0); 
      paramTbStreamDataWriter.writeInt(0, 4);
      while (paramInt2 >= 0) {
        bool = (paramInt2 > 16384) ? 1 : paramInt2;
        i = inputStream.read(arrayOfByte, 0, bool);
        if (i <= 0) {
          if (j == 0) {
            paramTbStreamDataWriter.reWriteInt(streamBuffer.getCurDataSize() - j - 4, j, 4);
            paramTbStreamDataWriter.writePaddingDPL(j);
          } 
          return;
        } 
        String str = new String(arrayOfByte, 0, i, str1);
        byte[] arrayOfByte1 = str.getBytes(str2);
        k = 0;
        while (true) {
          m = streamBuffer.getRemained();
          n = arrayOfByte1.length - k;
          if (n > m) {
            paramTbStreamDataWriter.writeBytes(arrayOfByte1, k, m);
            j += m;
            k += m;
            paramTbStreamDataWriter.reWriteInt(streamBuffer.getCurDataSize() - j - 4, j, 4);
            paramTbStreamDataWriter.writePaddingDPL(j);
            paramTbDirPathStream.dirPathLoadStream(paramTbStreamDataWriter, 1);
            j = 0;
            paramTbStreamDataWriter.writeInt(0, 4);
            continue;
          } 
          paramTbStreamDataWriter.writeBytes(arrayOfByte1, k, n);
          j += n;
          k += n;
          break;
        } 
        paramInt2 -= i;
      } 
    } catch (IOException iOException) {
      throw TbError.newSQLException(-90202, iOException.getMessage());
    } 
  }
  
  public int bind(TbConnection paramTbConnection, TbDirPathStream paramTbDirPathStream, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, boolean paramBoolean) throws SQLException {
    InputStream inputStream = paramTbDirPathStream.getParamStream(paramInt1);
    StreamBuffer streamBuffer = paramTbStreamDataWriter.getStreamBuf();
    String str1 = paramTbDirPathStream.getDPLMetaData().getClientCharSet();
    String str2 = ((TbDatabaseMetaData)paramTbConnection.getMetaData()).getServerCharSet();
    byte[] arrayOfByte = new byte[16384];
    int i = 0;
    Serializable bool = false;
    int j = 0;
    if (!paramBoolean) {
      this.length = paramInt2;
      this.offset = 0;
    } 
    try {
      if (streamBuffer.getRemained() <= 8)
        return 3; 
      paramTbStreamDataWriter.writeInt(0, 4);
      if (paramBoolean) {
        j = this.unReadBytes;
        paramTbStreamDataWriter.writeBytes(this.convBuf, this.offset, j);
      } 
      while (this.length >= 0) {
        bool = (this.length > 16384) ? true : this.length;
        i = inputStream.read(arrayOfByte, 0, (Integer) bool);
        if (i <= 0) {
          if (j > 0) {
            paramTbStreamDataWriter.reWriteInt(streamBuffer.getCurDataSize() - j - 4, j, 4);
            paramTbStreamDataWriter.writePaddingDPL(j);
          } 
          return 0;
        } 
        String str = new String(arrayOfByte, 0, i, str1);
        this.convBuf = str.getBytes(str2);
        int k = streamBuffer.getRemained();
        this.offset = 0;
        this.unReadBytes = this.convBuf.length;
        if (this.unReadBytes > k - 8) {
          k -= 8;
          paramTbStreamDataWriter.writeBytes(this.convBuf, this.offset, k);
          j += k;
          this.offset += k;
          this.unReadBytes -= k;
          paramTbStreamDataWriter.reWriteInt(streamBuffer.getCurDataSize() - j - 4, j, 4);
          paramTbStreamDataWriter.writePaddingDPL(j);
          this.length -= i;
          return 1;
        } 
        paramTbStreamDataWriter.writeBytes(this.convBuf, this.offset, this.unReadBytes);
        j += this.unReadBytes;
        this.length -= i;
      } 
      paramTbStreamDataWriter.reWriteInt(streamBuffer.getCurDataSize() - j - 4, j, 4);
      paramTbStreamDataWriter.writePaddingDPL(j);
      return 0;
    } catch (IOException iOException) {
      throw TbError.newSQLException(-90202, iOException.getMessage());
    } 
  }
  
  public void init() {
    this.length = 0;
    this.offset = 0;
    this.unReadBytes = 0;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\dpl\binder\DPLLongStreamBinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */