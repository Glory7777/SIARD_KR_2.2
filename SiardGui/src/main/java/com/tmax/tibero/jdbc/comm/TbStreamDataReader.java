package com.tmax.tibero.jdbc.comm;

import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.data.StreamBuffer;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.util.TbCommon;
import java.sql.SQLException;

public class TbStreamDataReader {
  private StreamBuffer buf;
  
  private int curReadOffset;
  
  private DataTypeConverter typeConverter = null;
  
  public TbStreamDataReader(StreamBuffer paramStreamBuffer, DataTypeConverter paramDataTypeConverter) {
    this.typeConverter = paramDataTypeConverter;
    initialize(paramStreamBuffer);
  }
  
  public int getBufferedDataSize() {
    return (this.buf != null) ? this.buf.getCurDataSize() : 0;
  }
  
  public int getCurOffset() {
    return this.curReadOffset;
  }
  
  public void initialize(StreamBuffer paramStreamBuffer) {
    this.buf = paramStreamBuffer;
    this.curReadOffset = 0;
  }
  
  private void isBufferAvailable(int paramInt) throws SQLException {
    if (this.buf == null || this.buf.getCurDataSize() <= 0 || this.buf.getCurDataSize() <= this.curReadOffset || this.buf.getCurDataSize() < this.curReadOffset + paramInt)
      throw TbError.newSQLException(-590730); 
  }
  
  public void moveReadOffset(int paramInt) {
    this.curReadOffset += paramInt;
  }
  
  public void readBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    isBufferAvailable(paramInt2);
    System.arraycopy(this.buf.getRawBytes(), this.curReadOffset, paramArrayOfbyte, paramInt1, paramInt2);
    moveReadOffset(paramInt2);
  }
  
  public String readDBDecodedString(int paramInt) throws SQLException {
    isBufferAvailable(paramInt);
    String str = null;
    if (paramInt > 0) {
      str = this.typeConverter.getDBDecodedString(this.buf.getRawBytes(), this.curReadOffset, paramInt);
      moveReadOffset(paramInt);
    } 
    return str;
  }
  
  public String readDBDecodedPadString(int paramInt) throws SQLException {
    isBufferAvailable(paramInt);
    String str = null;
    if (paramInt > 0) {
      str = this.typeConverter.getDBDecodedString(this.buf.getRawBytes(), this.curReadOffset, paramInt);
      moveReadOffset(paramInt);
    } 
    skipPadding(TbCommon.getPadLength(paramInt));
    return str;
  }
  
  public int readInt32() throws SQLException {
    isBufferAvailable(4);
    int i = TbCommon.bytes2Int(this.buf.getRawBytes(), this.curReadOffset, 4);
    moveReadOffset(4);
    return i;
  }
  
  public void readPadBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    readBytes(paramArrayOfbyte, paramInt1, paramInt2);
    skipPadding(TbCommon.getPadLength(paramInt2));
  }
  
  public byte[] readRpcolData() throws SQLException {
    byte[] arrayOfByte1 = new byte[1];
    byte[] arrayOfByte2 = null;
    readBytes(arrayOfByte1, 0, 1);
    if (arrayOfByte1[0] <= -6) {
      byte b = arrayOfByte1[0];
      arrayOfByte2 = new byte[b + 1];
      arrayOfByte2[0] = arrayOfByte1[0];
      readBytes(arrayOfByte2, 1, b);
    } else if (arrayOfByte1[0] == -2) {
      byte[] arrayOfByte = new byte[2];
      readBytes(arrayOfByte, 0, 2);
      int i = 0xFF & arrayOfByte[0] << 8;
      i &= 0xFF & arrayOfByte[1];
      arrayOfByte2 = new byte[i + 3];
      arrayOfByte2[0] = arrayOfByte1[0];
      arrayOfByte2[1] = arrayOfByte[0];
      arrayOfByte2[2] = arrayOfByte[1];
      readBytes(arrayOfByte2, 3, i);
    } else {
      throw TbError.newSQLException(-90405);
    } 
    return arrayOfByte2;
  }
  
  public void reset() {
    this.buf = null;
    this.typeConverter = null;
  }
  
  private void skipPadding(int paramInt) throws SQLException {
    isBufferAvailable(paramInt);
    moveReadOffset(paramInt);
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\comm\TbStreamDataReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */