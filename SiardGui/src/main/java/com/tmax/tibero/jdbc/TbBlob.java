package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.comm.TbBlobAccessor;
import com.tmax.tibero.jdbc.comm.TbLobAccessor;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;

public class TbBlob extends TbLob implements Blob {
  private static final TbBlob EMPTY_BLOB = new TbBlob(null, new byte[1], false);
  
  public TbBlob(TbConnection paramTbConnection, byte[] paramArrayOfbyte, boolean paramBoolean) {
    super(paramTbConnection, paramArrayOfbyte, paramBoolean);
  }
  
  public static TbBlob createEmptyBlob() {
    return EMPTY_BLOB;
  }
  
  public static TbBlob createTemporary(Connection paramConnection) throws SQLException {
    if (!(paramConnection instanceof TbConnection))
      throw TbError.newSQLException(-590713); 
    return ((TbConnection)paramConnection).createTbBlob();
  }
  
  public static void freeTemporary(TbBlob paramTbBlob) throws SQLException {
    if (paramTbBlob == null)
      throw TbError.newSQLException(-590713); 
    paramTbBlob.free();
  }
  
  public void freeTemporary() throws SQLException {
    free();
  }
  
  public OutputStream getBinaryOutputStream() throws SQLException {
    return setBinaryStream(1L);
  }
  
  public OutputStream getBinaryOutputStream(long paramLong) throws SQLException {
    return setBinaryStream(paramLong);
  }
  
  public InputStream getBinaryStream() throws SQLException {
    checkInvalidActionOnEmpty();
    return new TbLobInputStream(this, 1L, 2147483647L);
  }
  
  public InputStream getBinaryStream(long paramLong1, long paramLong2) throws SQLException {
    checkInvalidActionOnEmpty();
    if (paramLong1 < 1L)
      throw TbError.newSQLException(-590764, paramLong1); 
    if (paramLong2 < 0L)
      throw TbError.newSQLException(-590765, paramLong2); 
    return new TbLobInputStream(this, paramLong1, paramLong2);
  }
  
  private TbBlobAccessor getBlobAccessor() {
    return getConnection().getBlobAccessor();
  }
  
  protected TbLobAccessor getLobAccessor() {
    return (TbLobAccessor)getBlobAccessor();
  }
  
  public long getBytes(long paramLong, byte[] paramArrayOfbyte) throws SQLException {
    return getBytes(paramLong, paramArrayOfbyte, 0L, paramArrayOfbyte.length);
  }
  
  public long getBytes(long paramLong1, byte[] paramArrayOfbyte, long paramLong2) throws SQLException {
    return getBytes(paramLong1, paramArrayOfbyte, 0L, paramLong2);
  }
  
  public long getBytes(long paramLong1, byte[] paramArrayOfbyte, long paramLong2, long paramLong3) throws SQLException {
    checkInvalidActionOnEmpty();
    if (paramLong1 < 1L)
      throw TbError.newSQLException(-590764, paramLong1); 
    if (paramLong3 < 0L)
      throw TbError.newSQLException(-590765, paramLong3); 
    if (paramLong3 > paramArrayOfbyte.length)
      paramLong3 = paramArrayOfbyte.length; 
    if (paramLong3 == 0L)
      return 0L; 
    long l1 = getBlobAccessor().read(this, paramLong1 - 1L, paramArrayOfbyte, paramLong2, paramLong3);
    long l2 = getLengthInternal();
    if (l2 > -1L && paramLong1 == 1L && l2 <= l1 && getIsTempLob() && freeTmpLobOnEOF())
      getLobAccessor().freeTemporary(this); 
    return l1;
  }
  
  public byte[] getBytes(long paramLong, int paramInt) throws SQLException {
    checkInvalidActionOnEmpty();
    if (paramLong < 1L)
      throw TbError.newSQLException(-590764, paramLong); 
    if (paramInt < 0)
      throw TbError.newSQLException(-590765, paramInt); 
    if (paramInt == 0)
      return new byte[0]; 
    byte[] arrayOfByte1 = null;
    byte[] arrayOfByte2 = new byte[paramInt];
    long l = getBytes(paramLong, arrayOfByte2);
    if (l < paramInt) {
      arrayOfByte1 = new byte[(int)l];
      System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, (int)l);
      arrayOfByte2 = null;
    } else if (l == paramInt) {
      arrayOfByte1 = arrayOfByte2;
    } 
    return arrayOfByte1;
  }
  
  public long position(Blob paramBlob, long paramLong) throws SQLException {
    return position((TbLob)paramBlob, paramLong);
  }
  
  public long position(byte[] paramArrayOfbyte, long paramLong) throws SQLException {
    checkInvalidActionOnEmpty();
    if (paramLong < 1L)
      throw TbError.newSQLException(-590766, paramLong); 
    return getBlobAccessor().position(this, paramArrayOfbyte, paramLong - 1L);
  }
  
  public OutputStream setBinaryStream(long paramLong) throws SQLException {
    checkInvalidActionOnEmpty();
    return new TbLobOutputStream(this, (paramLong == 0L) ? 1L : paramLong);
  }
  
  public int setBytes(long paramLong, byte[] paramArrayOfbyte) throws SQLException {
    return setBytes(paramLong, paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public int setBytes(long paramLong, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    checkInvalidActionOnEmpty();
    if (paramLong < 1L)
      throw TbError.newSQLException(-590764, paramLong); 
    if (paramInt2 < 0)
      throw TbError.newSQLException(-590765, paramInt2); 
    if (paramInt1 + paramInt2 > paramArrayOfbyte.length)
      paramInt2 = paramArrayOfbyte.length - paramInt1; 
    return (paramInt2 <= 0) ? 0 : (int)getBlobAccessor().write(this, paramLong - 1L, paramArrayOfbyte, paramInt1, paramInt2);
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\TbBlob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */