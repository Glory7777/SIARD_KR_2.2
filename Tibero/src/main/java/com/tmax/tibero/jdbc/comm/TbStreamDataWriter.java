package com.tmax.tibero.jdbc.comm;

import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.data.StreamBuffer;
import com.tmax.tibero.jdbc.util.TbCommon;
import java.sql.SQLException;

public class TbStreamDataWriter {
  private static final int DPL_EXTRA_SIZE = 65536;
  
  protected StreamBuffer buf;
  
  private DataTypeConverter converter;
  
  public TbStreamDataWriter(StreamBuffer paramStreamBuffer) {
    this(paramStreamBuffer, null);
  }
  
  public TbStreamDataWriter(StreamBuffer paramStreamBuffer, DataTypeConverter paramDataTypeConverter) {
    initialize(paramStreamBuffer);
    this.converter = paramDataTypeConverter;
  }
  
  public void clearDPLBuffer() {
    this.buf.setCurDataSize(24);
  }
  
  public int getBufferedDataSize() {
    return (this.buf != null) ? this.buf.getCurDataSize() : 0;
  }
  
  public int getRemainedBufferSize() {
    int i = this.buf.getRemained() - 65536;
    return (i < 0) ? 0 : i;
  }
  
  public StreamBuffer getStreamBuf() {
    return this.buf;
  }
  
  public void initialize(StreamBuffer paramStreamBuffer) {
    paramStreamBuffer.init();
    this.buf = paramStreamBuffer;
  }
  
  public void makeBufferAvailable(int paramInt) {
    this.buf.makeBufferAvailable(paramInt);
  }
  
  public void moveOffset(int paramInt) {
    this.buf.moveOffset(paramInt);
  }
  
  public int putPadding(int paramInt) {
    this.buf.putData(paramInt, (byte)0);
    return paramInt;
  }
  
  public void reset() {
    if (this.buf != null) {
      this.buf.reset();
      this.buf = null;
    } 
    this.converter = null;
  }
  
  public int reWriteInt(int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    TbCommon.int2Bytes(paramInt2, this.buf.getRawBytes(), paramInt1, paramInt3);
    return paramInt3;
  }
  
  public int reWriteLong(int paramInt1, long paramLong, int paramInt2) throws SQLException {
    TbCommon.long2Bytes(paramLong, this.buf.getRawBytes(), paramInt1, paramInt2);
    return paramInt2;
  }
  
  public void setCurDataSize(int paramInt) {
    this.buf.setCurDataSize(paramInt);
  }
  
  public void writeByte(byte paramByte) {
    this.buf.copySingleByte(paramByte);
  }
  
  public int writeBytes(byte[] paramArrayOfbyte) throws SQLException {
    return writeBytes(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public int writeBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    this.buf.copyMultiBytes(paramInt2, paramInt1, paramArrayOfbyte);
    return paramInt2;
  }
  
  public int writeInt(int paramInt1, int paramInt2) throws SQLException {
    this.buf.makeBufferAvailable(paramInt2);
    TbCommon.int2Bytes(paramInt1, this.buf.getRawBytes(), this.buf.getCurDataSize(), paramInt2);
    this.buf.moveOffset(paramInt2);
    return paramInt2;
  }
  
  public int writeLenAndDBEncodedPadString(String paramString) throws SQLException {
    byte[] arrayOfByte = this.converter.getDBEncodedBytes(paramString);
    int i = arrayOfByte.length;
    writeInt(i, 4);
    this.buf.copyMultiBytes(i, 0, arrayOfByte);
    return i + writePadding(i) + 4;
  }
  
  public int writeLong(long paramLong, int paramInt) throws SQLException {
    this.buf.makeBufferAvailable(paramInt);
    TbCommon.long2Bytes(paramLong, this.buf.getRawBytes(), this.buf.getCurDataSize(), paramInt);
    this.buf.moveOffset(paramInt);
    return paramInt;
  }
  
  public int writePadBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    writeBytes(paramArrayOfbyte, paramInt1, paramInt2);
    return paramInt2 + writePadding(paramInt2);
  }
  
  public int writePadding(int paramInt) {
    int i = TbCommon.getPadLength(paramInt);
    putPadding(i);
    return i;
  }
  
  public int writePaddingDPL(int paramInt) {
    int i = (4 - paramInt % 4) % 4;
    putPadding(i);
    return i;
  }
  
  public int writeRpcolData(byte[] paramArrayOfbyte, int paramInt) {
    byte b;
    if (paramInt <= 250) {
      writeByte((byte)paramInt);
      b = 1;
    } else {
      writeByte((byte)-2);
      writeByte((byte)(0xFF & paramInt >> 8));
      writeByte((byte)(0xFF & paramInt));
      b = 3;
    } 
    if (paramInt > 0) {
      writeBytes(paramArrayOfbyte, 0, paramInt);
      return b + paramInt + writePadding(b + paramInt);
    } 
    writeByte((byte)0);
    return b + 1 + writePadding(b + 1);
  }
  
  public int writeRpcolData(byte[] paramArrayOfbyte, int paramInt, boolean paramBoolean) {
    byte b;
    if (paramInt <= 250) {
      writeByte((byte)paramInt);
      b = 1;
    } else {
      writeByte((byte)-2);
      writeByte((byte)(0xFF & paramInt >> 8));
      writeByte((byte)(0xFF & paramInt));
      b = 3;
    } 
    if (paramBoolean) {
      if (paramInt > 0) {
        writeBytes(paramArrayOfbyte, 0, paramInt);
        return b + paramInt + writePadding(b + paramInt);
      } 
      writeByte((byte)0);
      return b + 1 + writePadding(b + 1);
    } 
    if (paramInt > 0) {
      writeBytes(paramArrayOfbyte, 0, paramInt);
      return b + paramInt;
    } 
    writeByte((byte)0);
    return b + 1;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\comm\TbStreamDataWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */