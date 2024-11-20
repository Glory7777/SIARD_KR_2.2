package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.err.TbError;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

public class TbClobReader extends Reader {
  private TbClobBase clob = null;
  
  private int bufOffset = 0;
  
  private long lobOffset = 0L;
  
  private int fetchedSize = 0;
  
  private long totalSize = 0L;
  
  private long totalFetchedSize = 0L;
  
  private char[] buf = null;
  
  private boolean opened = false;
  
  public TbClobReader(TbClobBase paramTbClobBase, long paramLong1, long paramLong2) throws SQLException {
    if (paramTbClobBase == null)
      throw TbError.newSQLException(-590767); 
    this.clob = paramTbClobBase;
    this.bufOffset = 0;
    this.lobOffset = paramLong1 - 1L;
    this.fetchedSize = 0;
    this.totalSize = paramLong2;
    this.buf = new char[TbLob.getMaxChunkSize()];
    this.opened = true;
  }
  
  private void checkClosed() throws IOException {
    if (!this.opened)
      throw new IOException(TbError.getMsg(-90900)); 
  }
  
  public void close() throws IOException {
    this.opened = false;
    this.clob = null;
    this.buf = null;
  }
  
  private int getRemainedInBuffer() {
    return this.fetchedSize - this.bufOffset;
  }
  
  public int read(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException {
    if (paramArrayOfchar == null)
      throw new NullPointerException(); 
    if (paramInt1 < 0 || paramInt1 > paramArrayOfchar.length || paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOfchar.length || paramInt1 + paramInt2 < 0)
      throw new IndexOutOfBoundsException(); 
    if (paramInt2 == 0)
      return 0; 
    checkClosed();
    if (getRemainedInBuffer() == 0) {
      if (this.clob.isEndOfStream()) {
        if (this.clob.getIsTempLob() && this.clob.freeTmpLobOnEOF()) {
          try {
            this.clob.free();
          } catch (SQLException sQLException) {}
          return -1;
        } 
        return -1;
      } 
      try {
        readNextBuffer();
      } catch (SQLException sQLException) {
        throw new IOException(sQLException.getMessage());
      } 
    } 
    if (getRemainedInBuffer() >= paramInt2) {
      System.arraycopy(this.buf, this.bufOffset, paramArrayOfchar, paramInt1, paramInt2);
      this.bufOffset += paramInt2;
      return paramInt2;
    } 
    int i = 0;
    while (paramInt2 > i) {
      int j = getRemainedInBuffer();
      if (j > paramInt2 - i)
        j = paramInt2 - i; 
      System.arraycopy(this.buf, this.bufOffset, paramArrayOfchar, paramInt1 + i, j);
      i += j;
      this.bufOffset += j;
      if (getRemainedInBuffer() == 0) {
        if (!this.clob.isEndOfStream()) {
          try {
            readNextBuffer();
          } catch (SQLException sQLException) {
            throw new IOException(sQLException.getMessage());
          } 
          continue;
        } 
        return i;
      } 
    } 
    return i;
  }
  
  private void readNextBuffer() throws SQLException {
    long l1 = 0L;
    long l2 = this.totalSize - this.totalFetchedSize;
    if (this.buf.length < l2) {
      l1 = this.clob.getChars(this.lobOffset + 1L, this.buf);
    } else {
      l1 = this.clob.getChars(this.lobOffset + 1L, this.buf, 0L, l2);
      this.clob.setEndOfStream(true);
    } 
    this.totalFetchedSize += l1;
    this.fetchedSize = (int)l1;
    this.lobOffset += l1;
    this.bufOffset = 0;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\TbClobReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */