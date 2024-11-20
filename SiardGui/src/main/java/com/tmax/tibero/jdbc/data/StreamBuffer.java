package com.tmax.tibero.jdbc.data;

public class StreamBuffer {
  private byte[] rawBytes;
  
  private int initSize;
  
  private int curSize;
  
  private int curDataSize;
  
  public StreamBuffer(int paramInt) {
    this.rawBytes = new byte[paramInt];
    this.initSize = paramInt;
    this.curSize = paramInt;
    this.curDataSize = 0;
  }
  
  public StreamBuffer(byte[] paramArrayOfbyte) {
    this.rawBytes = paramArrayOfbyte;
    this.initSize = paramArrayOfbyte.length;
    this.curSize = paramArrayOfbyte.length;
    this.curDataSize = 0;
  }
  
  public void copyMultiBytes(int paramInt1, int paramInt2, byte[] paramArrayOfbyte) {
    makeBufferAvailable(paramInt1);
    System.arraycopy(paramArrayOfbyte, paramInt2, this.rawBytes, this.curDataSize, paramInt1);
    moveOffset(paramInt1);
  }
  
  public void copySingleByte(byte paramByte) {
    makeBufferAvailable(1);
    this.rawBytes[this.curDataSize++] = paramByte;
  }
  
  public void delete() {
    this.initSize = 0;
    reset();
  }
  
  public int getCurDataSize() {
    return this.curDataSize;
  }
  
  public byte[] getRawBytes() {
    return this.rawBytes;
  }
  
  public int getRemained() {
    return this.curSize - this.curDataSize;
  }
  
  public int getSize() {
    return this.curSize;
  }
  
  public void growDoubleUp(int paramInt) {
    int i = (paramInt > this.curSize) ? (paramInt + this.curSize) : (this.curSize * 2);
    byte[] arrayOfByte = this.rawBytes;
    this.rawBytes = new byte[i];
    System.arraycopy(arrayOfByte, 0, this.rawBytes, 0, this.curDataSize);
    arrayOfByte = null;
    this.curSize = i;
  }
  
  public void init() {
    this.curDataSize = 0;
    if (this.rawBytes == null)
      resize(this.curSize); 
  }
  
  public void init(int paramInt) {
    if (this.curSize < paramInt) {
      resize(paramInt);
    } else {
      init();
    } 
  }
  
  public void makeBufferAvailable(int paramInt) {
    if (this.curDataSize + paramInt > this.curSize)
      growDoubleUp(paramInt); 
  }
  
  public void moveOffset(int paramInt) {
    this.curDataSize += paramInt;
  }
  
  public void putData(int paramInt, byte paramByte) {
    makeBufferAvailable(paramInt);
    for (byte b = 0; b < paramInt; b++)
      this.rawBytes[this.curDataSize + b] = paramByte; 
    moveOffset(paramInt);
  }
  
  public void reset() {
    this.rawBytes = null;
    this.curSize = 0;
    this.curDataSize = 0;
  }
  
  public void resize(int paramInt) {
    this.rawBytes = new byte[paramInt];
    this.curSize = paramInt;
    this.curDataSize = 0;
  }
  
  public void setCurDataSize(int paramInt) {
    this.curDataSize = paramInt;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\StreamBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */