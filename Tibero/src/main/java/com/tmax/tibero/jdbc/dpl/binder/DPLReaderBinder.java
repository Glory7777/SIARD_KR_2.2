package com.tmax.tibero.jdbc.dpl.binder;

import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.data.StreamBuffer;
import com.tmax.tibero.jdbc.dpl.TbDirPathStream;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

public class DPLReaderBinder extends DPLBinder {
  private final int EXTRA_DATA_LENGTH = 12;
  
  private final int DPL_CHUNK_SIZE = 1628000;
  
  private int length;
  
  private byte[] byteBuf = new byte[16384];
  
  private int offset;
  
  private int unReadBytes = 0;
  
  public void bind(TbConnection paramTbConnection, TbDirPathStream paramTbDirPathStream, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2) throws SQLException {
    DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
    Reader reader = paramTbDirPathStream.getParamReader(paramInt1);
    StreamBuffer streamBuffer = paramTbStreamDataWriter.getStreamBuf();
    int i = paramTbDirPathStream.getDPLMetaData().getDataType(paramInt1 + 1);
    char[] arrayOfChar = new char[4096];
    byte[] arrayOfByte = new byte[16384];
    boolean bool = false;
    int j = 0;
    int k = 0;
    int m = 0;
    try {
      if (streamBuffer.getRemained() <= 12)
        paramTbDirPathStream.dirPathLoadStream(paramTbStreamDataWriter, 0); 
      paramTbStreamDataWriter.writeInt(0, 4);
      while (paramInt2 >= 0) {
        bool = (paramInt2 > 4096) ? true : paramInt2;
        j = reader.read(arrayOfChar, 0, bool);
        if (j <= 0) {
          if (m) {
            paramTbStreamDataWriter.reWriteInt(streamBuffer.getCurDataSize() - m - 4, m, 4);
            paramTbStreamDataWriter.writePaddingDPL(m);
          } 
          return;
        } 
        if (i == 13 || i == 20) {
          k = dataTypeConverter.charsToFixedBytes(arrayOfChar, 0, j, arrayOfByte, 0, arrayOfByte.length);
        } else {
          k = dataTypeConverter.charsToBytes(arrayOfChar, 0, j, arrayOfByte, 0, arrayOfByte.length);
        } 
        int n = 0;
        int i1 = 0;
        int i2 = 0;
        while (true) {
          i1 = streamBuffer.getRemained();
          i2 = k - n;
          if (i2 > i1 - 12 && i1 > 12) {
            i1 -= 12;
            paramTbStreamDataWriter.writeBytes(arrayOfByte, n, i1);
            m += i1;
            n += i1;
            paramTbStreamDataWriter.reWriteInt(streamBuffer.getCurDataSize() - m - 4, m, 4);
            paramTbStreamDataWriter.writePaddingDPL(m);
            paramTbDirPathStream.dirPathLoadStream(paramTbStreamDataWriter, 1);
            m = 0;
            paramTbStreamDataWriter.writeInt(0, 4);
            continue;
          } 
          if (i2 >= 1628000 - m - 12) {
            i2 = 1628000 - m - 12;
            paramTbStreamDataWriter.writeBytes(arrayOfByte, n, i2);
            m += i2;
            n += i2;
            paramTbStreamDataWriter.reWriteInt(streamBuffer.getCurDataSize() - m - 4, m, 4);
            paramTbStreamDataWriter.writePaddingDPL(m);
            paramTbDirPathStream.dirPathLoadStream(paramTbStreamDataWriter, 1);
            m = 0;
            paramTbStreamDataWriter.writeInt(0, 4);
            continue;
          } 
          paramTbStreamDataWriter.writeBytes(arrayOfByte, n, i2);
          m += i2;
          n += i2;
          break;
        } 
        paramInt2 -= j;
      } 
    } catch (IOException iOException) {
      throw TbError.newSQLException(-90202, iOException.getMessage());
    } 
  }
  
  public int bind(TbConnection paramTbConnection, TbDirPathStream paramTbDirPathStream, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, boolean paramBoolean) throws SQLException {
    DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
    Reader reader = paramTbDirPathStream.getParamReader(paramInt1);
    StreamBuffer streamBuffer = paramTbStreamDataWriter.getStreamBuf();
    int i = paramTbDirPathStream.getDPLMetaData().getDataType(paramInt1 + 1);
    char[] arrayOfChar = new char[4096];
    boolean bool = false;
    int j = 0;
    int k = 0;
    int m = 0;
    if (!paramBoolean) {
      this.length = paramInt2;
      this.offset = 0;
    } 
    try {
      if (streamBuffer.getRemained() <= 12)
        return 3; 
      paramTbStreamDataWriter.writeInt(0, 4);
      if (paramBoolean) {
        m = this.unReadBytes;
        paramTbStreamDataWriter.writeBytes(this.byteBuf, this.offset, m);
      } 
      while (this.length >= 0) {
        bool = (this.length > 4096) ? true : this.length;
        j = reader.read(arrayOfChar, 0, bool);
        if (j <= 0) {
          paramTbStreamDataWriter.reWriteInt(streamBuffer.getCurDataSize() - m - 4, m, 4);
          paramTbStreamDataWriter.writePaddingDPL(m);
          return 0;
        } 
        if (i == 13 || i == 20) {
          k = dataTypeConverter.charsToFixedBytes(arrayOfChar, 0, j, this.byteBuf, 0, this.byteBuf.length);
        } else {
          k = dataTypeConverter.charsToBytes(arrayOfChar, 0, j, this.byteBuf, 0, this.byteBuf.length);
        } 
        int n = streamBuffer.getRemained();
        this.offset = 0;
        this.unReadBytes = k;
        if (this.unReadBytes > n - 12) {
          n -= 12;
          paramTbStreamDataWriter.writeBytes(this.byteBuf, this.offset, n);
          m += n;
          this.offset = n;
          this.unReadBytes -= n;
          paramTbStreamDataWriter.reWriteInt(streamBuffer.getCurDataSize() - m - 4, m, 4);
          paramTbStreamDataWriter.writePaddingDPL(m);
          this.length -= j;
          return 1;
        } 
        paramTbStreamDataWriter.writeBytes(this.byteBuf, this.offset, this.unReadBytes);
        m += this.unReadBytes;
        this.length -= j;
      } 
      paramTbStreamDataWriter.reWriteInt(streamBuffer.getCurDataSize() - m - 4, m, 4);
      paramTbStreamDataWriter.writePaddingDPL(m);
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


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\dpl\binder\DPLReaderBinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */