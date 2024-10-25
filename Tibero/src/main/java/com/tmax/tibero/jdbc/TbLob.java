package com.tmax.tibero.jdbc;

import com.tmax.tibero.DriverConstants;
import com.tmax.tibero.jdbc.comm.TbLobAccessor;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;

public abstract class TbLob {
  public static final int MODE_READONLY = 0;
  
  public static final int MODE_READWRITE = 1;
  
  public static final int LOBLOC_LEN = 96;
  
  public static final int LOBLOC_DATA_APPENDED = 4;
  
  public static final int LOBLOC_IS_TEMPORARY = 4;
  
  private static int maxChunkSize = 32000;
  
  private TbConnection conn = null;
  
  private byte[] locator = null;
  
  private byte[] data = null;
  
  private boolean endOfStream = false;
  
  private boolean isEmpty = false;
  
  private boolean isDataAppendMode = false;
  
  private boolean isTempLob = false;
  
  private boolean freeTmpLobOnEOF = false;
  
  private long length = -1L;
  
  public TbLob(TbConnection paramTbConnection, byte[] paramArrayOfbyte, boolean paramBoolean) {
    this.conn = paramTbConnection;
    if (paramArrayOfbyte.length == 1) {
      this.locator = paramArrayOfbyte;
      this.isEmpty = true;
    } else if ((paramArrayOfbyte[5] & 0x4) == 4) {
      this.locator = new byte[96];
      System.arraycopy(paramArrayOfbyte, 0, this.locator, 0, 96);
      int i = paramArrayOfbyte.length - 96;
      this.data = new byte[i];
      System.arraycopy(paramArrayOfbyte, 96, this.data, 0, i);
      this.isDataAppendMode = true;
    } else if ((paramArrayOfbyte[4] & 0x4) == 4) {
      this.locator = paramArrayOfbyte;
      this.freeTmpLobOnEOF = paramBoolean;
      this.isTempLob = true;
    } else {
      this.locator = paramArrayOfbyte;
    } 
  }
  
  public void checkInvalidActionOnEmpty() throws SQLException {
    if (this.isEmpty)
      throw TbError.newSQLException(-90629); 
  }
  
  public void close() throws SQLException {
    checkInvalidActionOnEmpty();
  }
  
  public void free() throws SQLException {
    checkInvalidActionOnEmpty();
    getLobAccessor().freeTemporary(this);
  }
  
  public TbConnection getConnection() {
    return this.conn;
  }
  
  public byte[] getLobData() {
    return this.data;
  }
  
  public byte[] getLocator() {
    return this.locator;
  }
  
  public boolean getIsTempLob() {
    return this.isTempLob;
  }
  
  public boolean freeTmpLobOnEOF() {
    return this.freeTmpLobOnEOF;
  }
  
  protected abstract TbLobAccessor getLobAccessor();
  
  private int getFixedSlobLen() {
    int i = this.locator[0] << 8;
    i += this.locator[1];
    return i;
  }
  
  public int getLocatorLength() {
    return !isInline() ? ((this.locator == null) ? 0 : this.locator.length) : (((this.locator[4] & 0x4) != 0) ? this.locator.length : getFixedSlobLen());
  }
  
  public boolean isEndOfStream() {
    return this.endOfStream;
  }
  
  public long length() throws SQLException {
    checkInvalidActionOnEmpty();
    if (this.length < 0L)
      if (this.isDataAppendMode) {
        if (this.data == null) {
          this.length = 0L;
        } else {
          this.length = this.data.length;
        } 
      } else {
        this.length = getLobAccessor().length(this);
      }  
    return this.length;
  }
  
  protected long getLengthInternal() {
    return this.length;
  }
  
  public void open(int paramInt) throws SQLException {
    checkInvalidActionOnEmpty();
    if (paramInt != 0 && paramInt != 1)
      throw TbError.newSQLException(-590769); 
  }
  
  public long position(TbLob paramTbLob, long paramLong) throws SQLException {
    checkInvalidActionOnEmpty();
    if (paramLong < 1L)
      throw TbError.newSQLException(-590766, paramLong); 
    return getLobAccessor().position(this, paramTbLob, paramLong - 1L);
  }
  
  public void setEndOfStream(boolean paramBoolean) {
    this.endOfStream = paramBoolean;
  }
  
  public void setLocator(byte[] paramArrayOfbyte) {
    this.locator = paramArrayOfbyte;
  }
  
  public static void setMaxChunkSize(int paramInt) {
    if (paramInt >= 8 * DriverConstants.MEGA) {
      maxChunkSize = 8 * DriverConstants.MEGA;
    } else {
      maxChunkSize = paramInt;
    } 
  }
  
  public static int getMaxChunkSize() {
    return maxChunkSize;
  }
  
  public void truncate(long paramLong) throws SQLException {
    checkInvalidActionOnEmpty();
    if (paramLong < 0L)
      throw TbError.newSQLException(-590765, paramLong); 
    getLobAccessor().truncate(this, paramLong);
  }
  
  public boolean isInline() {
    return !(this.isEmpty || (this.locator[5] & 0x10) == 0);
  }
  
  public boolean isXML() {
    return !(this.isEmpty || (this.locator[5] & 0x8) == 0);
  }
  
  public boolean isRemote() {
    return !(this.isEmpty || (this.locator[5] & 0x2) == 0);
  }
  
  public int getIlobLength() {
    return this.locator.length - getFixedSlobLen();
  }
  
  public int readIlob(int paramInt1, char[] paramArrayOfchar, byte[] paramArrayOfbyte, int paramInt2, int paramInt3, DataTypeConverter paramDataTypeConverter, boolean paramBoolean) throws SQLException {
    int j;
    int k;
    int i = 0;
    if (paramBoolean) {
      i = getIlobLength() - getFixedSlobLen();
      if (paramInt1 > i)
        throw TbError.newSQLException(-590785, paramInt1); 
      j = 2 * getFixedSlobLen() + paramInt1;
      k = Math.min(paramInt3, i - paramInt1);
    } else {
      i = getIlobLength();
      if (paramInt1 > i)
        throw TbError.newSQLException(-590785, paramInt1); 
      j = getFixedSlobLen() + paramInt1;
      k = Math.min(paramInt3, i - paramInt1);
    } 
    if (k == 0) {
      this.endOfStream = true;
      return 0;
    } 
    if (k < paramInt3)
      this.endOfStream = true; 
    if (this instanceof TbClob || this instanceof TbNClob)
      return paramDataTypeConverter.fixedBytesToChars(this.locator, j, k, paramArrayOfchar, paramInt2, paramArrayOfchar.length - paramInt2); 
    System.arraycopy(this.locator, j, paramArrayOfbyte, paramInt2, k);
    return k;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\TbLob.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */