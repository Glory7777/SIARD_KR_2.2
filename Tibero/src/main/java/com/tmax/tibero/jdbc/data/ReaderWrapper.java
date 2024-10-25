package com.tmax.tibero.jdbc.data;

import java.io.IOException;
import java.io.Reader;

public class ReaderWrapper extends Reader {
  protected Reader srcReader;
  
  private char[] charData;
  
  private int charLen;
  
  private int charDataOff;
  
  protected ReaderWrapper(Reader paramReader, char[] paramArrayOfchar, int paramInt) {
    this.srcReader = paramReader;
    this.charData = paramArrayOfchar;
    this.charLen = paramInt;
    this.charDataOff = 0;
  }
  
  public static ReaderWrapper getInstance(Reader paramReader, char[] paramArrayOfchar, int paramInt) {
    return new ReaderWrapper(paramReader, paramArrayOfchar, paramInt);
  }
  
  public void close() throws IOException {
    this.srcReader.close();
  }
  
  public int read(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException {
    int i = 0;
    int j = this.charLen - this.charDataOff;
    if (j > 0) {
      i = (paramInt2 < j) ? paramInt2 : j;
      System.arraycopy(this.charData, this.charDataOff, paramArrayOfchar, paramInt1, i);
      this.charDataOff += i;
    } 
    if (i >= paramInt2)
      return i; 
    int k = this.srcReader.read(paramArrayOfchar, paramInt1 + i, paramInt2 - i);
    return (i > 0 && k < 0) ? i : (i + k);
  }
  
  public int getPreReadCharLen() {
    return this.charLen;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\ReaderWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */