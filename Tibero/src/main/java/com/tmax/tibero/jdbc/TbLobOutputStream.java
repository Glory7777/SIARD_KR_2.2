package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.err.TbError;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public class TbLobOutputStream extends OutputStream {
  private TbLob lob = null;
  
  private int bufOffset = 0;
  
  private long lobOffset = 0L;
  
  private boolean opened = false;
  
  private boolean isBlob = false;
  
  private byte[] byteBuf = null;
  
  private char[] charBuf = null;
  
  public TbLobOutputStream(TbLob paramTbLob, long paramLong) throws SQLException {
    if (paramTbLob == null)
      throw TbError.newSQLException(-590767); 
    if (paramLong < 1L)
      throw TbError.newSQLException(-590764, paramLong); 
    this.lob = paramTbLob;
    this.lobOffset = paramLong - 1L;
    this.bufOffset = 0;
    this.opened = true;
    if (paramTbLob instanceof TbClobBase) {
      this.charBuf = new char[TbLob.getMaxChunkSize()];
      this.isBlob = false;
    } else if (paramTbLob instanceof TbBlob) {
      this.byteBuf = new byte[TbLob.getMaxChunkSize()];
      this.isBlob = true;
    } else {
      throw TbError.newSQLException(-590770, paramTbLob.toString());
    } 
  }
  
  private void checkClosed() throws IOException {
    if (!this.opened)
      throw new IOException(TbError.getMsg(-90900)); 
  }
  
  public void close() throws IOException {
    try {
      flushBuffer();
    } catch (SQLException sQLException) {
      throw new IOException(sQLException.getMessage());
    } 
    this.opened = false;
  }
  
  public void flush() throws IOException {
    try {
      flushBuffer();
    } catch (SQLException sQLException) {
      throw new IOException(sQLException.getMessage());
    } 
  }
  
  private void flushBuffer() throws SQLException {
    int i = 0;
    if (this.isBlob) {
      i = ((TbBlob)this.lob).setBytes(this.lobOffset + 1L, this.byteBuf, 0, this.bufOffset);
    } else {
      i = ((TbClob)this.lob).putChars(this.lobOffset + 1L, this.charBuf, 0L, this.bufOffset);
    } 
    this.lobOffset += i;
    this.bufOffset = 0;
  }
  
  private int getRemainedInBuffer() {
    return TbLob.getMaxChunkSize() - this.bufOffset;
  }
  
  public void write(int paramInt) throws IOException {
    checkClosed();
    if (getRemainedInBuffer() == 0)
      try {
        flushBuffer();
      } catch (SQLException sQLException) {
        throw new IOException(sQLException.getMessage());
      }  
    if (this.isBlob) {
      this.byteBuf[this.bufOffset++] = (byte)paramInt;
    } else {
      this.charBuf[this.bufOffset++] = (char)paramInt;
    } 
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\TbLobOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */