package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.err.TbError;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

public class TbClobWriter extends Writer {
  private TbClobBase clob = null;
  
  private int bufOffset = 0;
  
  private long lobOffset = 0L;
  
  private boolean opened = false;
  
  private char[] charBuf = null;
  
  public TbClobWriter(TbClobBase paramTbClobBase, long paramLong) throws SQLException {
    if (paramTbClobBase == null)
      throw TbError.newSQLException(-590767); 
    if (paramLong < 1L)
      throw TbError.newSQLException(-590764, paramLong); 
    this.clob = paramTbClobBase;
    this.lobOffset = paramLong;
    this.bufOffset = 0;
    this.opened = true;
    this.charBuf = new char[TbLob.getMaxChunkSize()];
  }
  
  private void checkClosed() throws IOException {
    if (!this.opened)
      throw new IOException(TbError.getMsg(-90900)); 
  }
  
  public void close() throws IOException {
    checkClosed();
    try {
      flushBuffer();
    } catch (SQLException sQLException) {
      throw new IOException(sQLException.getMessage());
    } 
    this.opened = false;
  }
  
  public void flush() throws IOException {
    checkClosed();
    try {
      flushBuffer();
    } catch (SQLException sQLException) {
      throw new IOException(sQLException.getMessage());
    } 
  }
  
  private void flushBuffer() throws SQLException {
    if (this.bufOffset > 0) {
      int i = this.clob.putChars(this.lobOffset, this.charBuf, 0L, this.bufOffset);
      this.lobOffset += i;
      this.bufOffset = 0;
    } 
  }
  
  private int getRemainedInBuffer() {
    return TbLob.getMaxChunkSize() - this.bufOffset;
  }
  
  public void write(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException {
    if (paramArrayOfchar == null)
      throw new NullPointerException(); 
    if (paramInt1 < 0 || paramInt1 > paramArrayOfchar.length || paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOfchar.length || paramInt1 + paramInt2 < 0)
      throw new IndexOutOfBoundsException(); 
    if (paramInt2 == 0)
      return; 
    checkClosed();
    int i = paramInt2;
    int j = getRemainedInBuffer();
    int k = paramInt1;
    while (i > 0) {
      int m = (j > i) ? i : j;
      System.arraycopy(paramArrayOfchar, k, this.charBuf, this.bufOffset, m);
      k += m;
      this.bufOffset += m;
      j -= m;
      i -= m;
      if (j == 0)
        try {
          flushBuffer();
          j = getRemainedInBuffer();
        } catch (SQLException sQLException) {
          throw new IOException(sQLException.getMessage());
        }  
    } 
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\TbClobWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */