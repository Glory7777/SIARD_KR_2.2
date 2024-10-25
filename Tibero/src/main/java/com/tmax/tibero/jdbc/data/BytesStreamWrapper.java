package com.tmax.tibero.jdbc.data;

import java.io.IOException;
import java.io.InputStream;

public class BytesStreamWrapper extends InputStream {
  private InputStream srcInput;
  
  private byte[] byteData;
  
  private int byteLen;
  
  private int byteDataOff;
  
  public BytesStreamWrapper(InputStream paramInputStream, byte[] paramArrayOfbyte, int paramInt) {
    this.srcInput = paramInputStream;
    this.byteData = paramArrayOfbyte;
    this.byteLen = paramInt;
    this.byteDataOff = 0;
  }
  
  public int read() throws IOException {
    int i = 0;
    if (this.byteDataOff < this.byteLen) {
      i = 0xFF & this.byteData[this.byteDataOff];
      this.byteDataOff++;
    } else {
      return this.srcInput.read();
    } 
    return i;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\BytesStreamWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */