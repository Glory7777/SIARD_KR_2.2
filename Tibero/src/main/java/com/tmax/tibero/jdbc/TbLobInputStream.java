package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.err.TbError;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class TbLobInputStream extends InputStream {
  private TbLob lob = null;
  
  private int bufOffset = 0;
  
  private long lobOffset = 0L;
  
  private int fetchedSize = 0;
  
  private long totalSize = 0L;
  
  private long totalFetchedSize = 0L;
  
  private char[] charBuf = null;
  
  private byte[] byteBuf = null;
  
  private boolean isBlob = false;
  
  private boolean opened = false;
  
  public TbLobInputStream(TbLob paramTbLob, long paramLong1, long paramLong2) throws SQLException {
    if (paramTbLob == null)
      throw TbError.newSQLException(-590767); 
    this.lob = paramTbLob;
    this.bufOffset = 0;
    this.lobOffset = paramLong1 - 1L;
    this.fetchedSize = 0;
    this.totalSize = paramLong2;
    this.totalFetchedSize = 0L;
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
  
  void checkClosed() throws IOException {
    if (!this.opened)
      throw new IOException(TbError.getMsg(-90900)); 
  }
  
  public void close() throws IOException {
    this.opened = false;
  }
  
  private int getRemainedBufferSize() {
    return this.fetchedSize - this.bufOffset;
  }
  
  public char[] getCharBuf() {
    return this.charBuf;
  }
  
  public int read() throws IOException {
    int i = 0;
    checkClosed();
    if (getRemainedBufferSize() == 0) {
      if (this.lob.isEndOfStream()) {
        if (this.lob.getIsTempLob() && this.lob.freeTmpLobOnEOF()) {
          try {
            this.lob.free();
          } catch (SQLException sQLException) {}
          return -1;
        } 
        return -1;
      } 
      try {
        readNextBuffer();
        return read();
      } catch (SQLException sQLException) {
        throw new IOException(sQLException.getMessage());
      } 
    } 
    if (this.isBlob) {
      i = this.byteBuf[this.bufOffset++];
    } else {
      i = this.charBuf[this.bufOffset++];
    } 
    if (i < 0)
      i += 256; 
    return i;
  }
  
  private void readNextBuffer() throws SQLException {
    long l1 = 0L;
    long l2 = this.totalSize - this.totalFetchedSize;
    if (this.isBlob) {
      if (this.byteBuf.length < l2) {
        l1 = ((TbBlob)this.lob).getBytes(this.lobOffset + 1L, this.byteBuf);
      } else {
        l1 = ((TbBlob)this.lob).getBytes(this.lobOffset + 1L, this.byteBuf, 0L, l2);
        this.lob.setEndOfStream(true);
      } 
    } else {
      l1 = ((TbClob)this.lob).getChars(this.lobOffset + 1L, this.charBuf);
    } 
    this.totalFetchedSize += l1;
    this.fetchedSize = (int)l1;
    this.lobOffset += l1;
    this.bufOffset = 0;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\TbLobInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */