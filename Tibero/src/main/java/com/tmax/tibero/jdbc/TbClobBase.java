package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.comm.TbClobAccessor;
import com.tmax.tibero.jdbc.comm.TbLobAccessor;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Clob;
import java.sql.SQLException;

public abstract class TbClobBase extends TbLob implements Clob {
  public TbClobBase(TbConnection paramTbConnection, byte[] paramArrayOfbyte, boolean paramBoolean) {
    super(paramTbConnection, paramArrayOfbyte, paramBoolean);
  }
  
  public InputStream getAsciiStream() throws SQLException {
    checkInvalidActionOnEmpty();
    return new TbLobInputStream(this, 1L, 2147483647L);
  }
  
  public int getBufferSize() throws SQLException {
    checkInvalidActionOnEmpty();
    return TbLob.getMaxChunkSize();
  }
  
  public Reader getCharacterStream() throws SQLException {
    checkInvalidActionOnEmpty();
    return new TbClobReader(this, 1L, Long.MAX_VALUE);
  }
  
  public Reader getCharacterStream(long paramLong1, long paramLong2) throws SQLException {
    checkInvalidActionOnEmpty();
    if (paramLong1 < 1L)
      throw TbError.newSQLException(-590764, paramLong1); 
    if (paramLong2 < 0L)
      throw TbError.newSQLException(-590765, paramLong2); 
    return new TbClobReader(this, paramLong1, paramLong2);
  }
  
  public long getChars(long paramLong, char[] paramArrayOfchar) throws SQLException {
    return getChars(paramLong, paramArrayOfchar, 0L, paramArrayOfchar.length);
  }
  
  public long getChars(long paramLong1, char[] paramArrayOfchar, long paramLong2) throws SQLException {
    return getChars(paramLong1, paramArrayOfchar, 0L, paramLong2);
  }
  
  public long getChars(long paramLong1, char[] paramArrayOfchar, long paramLong2, long paramLong3) throws SQLException {
    checkInvalidActionOnEmpty();
    if (paramLong1 < 1L)
      throw TbError.newSQLException(-590764, paramLong1); 
    if (paramLong3 < 0L)
      throw TbError.newSQLException(-590765, paramLong3); 
    if (paramLong3 > paramArrayOfchar.length)
      paramLong3 = paramArrayOfchar.length; 
    if (paramLong3 == 0L)
      return 0L; 
    long l1 = getLengthInternal();
    long l2 = getClobAccessor().read(this, paramLong1 - 1L, paramArrayOfchar, paramLong2, paramLong3);
    if (l1 > -1L && paramLong1 == 1L && l1 <= l2 && getIsTempLob() && freeTmpLobOnEOF())
      getLobAccessor().freeTemporary(this); 
    return l2;
  }
  
  protected TbClobAccessor getClobAccessor() {
    return getConnection().getClobAccessor();
  }
  
  protected TbLobAccessor getLobAccessor() {
    return (TbLobAccessor)getClobAccessor();
  }
  
  public String getSubString(long paramLong, int paramInt) throws SQLException {
    checkInvalidActionOnEmpty();
    if (paramLong < 1L)
      throw TbError.newSQLException(-590764, paramLong); 
    if (paramInt < 0)
      throw TbError.newSQLException(-590765, paramInt); 
    if (paramInt == 0)
      return new String(); 
    char[] arrayOfChar = new char[paramInt];
    long l = getChars(paramLong, arrayOfChar);
    return (l > 0L) ? new String(arrayOfChar, 0, (int)l) : new String();
  }
  
  public long position(Clob paramClob, long paramLong) throws SQLException {
    return position((TbLob)paramClob, paramLong);
  }
  
  public long position(String paramString, long paramLong) throws SQLException {
    checkInvalidActionOnEmpty();
    if (paramLong < 1L)
      throw TbError.newSQLException(-590766, "" + paramLong); 
    return getClobAccessor().position(this, paramString.toCharArray(), paramLong - 1L);
  }
  
  public long putChars(long paramLong, char[] paramArrayOfchar) throws SQLException {
    return putCharsInternal(paramLong, paramArrayOfchar, 0L, paramArrayOfchar.length);
  }
  
  public long putChars(long paramLong1, char[] paramArrayOfchar, long paramLong2) throws SQLException {
    return putCharsInternal(paramLong1, paramArrayOfchar, 0L, paramLong2);
  }
  
  public int putChars(long paramLong1, char[] paramArrayOfchar, long paramLong2, long paramLong3) throws SQLException {
    return (int)putCharsInternal(paramLong1, paramArrayOfchar, paramLong2, paramLong3);
  }
  
  private long putCharsInternal(long paramLong1, char[] paramArrayOfchar, long paramLong2, long paramLong3) throws SQLException {
    checkInvalidActionOnEmpty();
    if (paramLong1 < 1L)
      throw TbError.newSQLException(-590764, paramLong1); 
    if (paramLong3 < 0L)
      throw TbError.newSQLException(-590765, paramLong3); 
    if (paramLong2 + paramLong3 > paramArrayOfchar.length)
      paramLong3 = paramArrayOfchar.length - paramLong2; 
    return (paramLong3 <= 0L) ? 0L : getClobAccessor().write(this, paramLong1 - 1L, paramArrayOfchar, paramLong2, paramLong3);
  }
  
  public OutputStream setAsciiStream(long paramLong) throws SQLException {
    checkInvalidActionOnEmpty();
    return new TbLobOutputStream(this, (paramLong == 0L) ? 1L : paramLong);
  }
  
  public Writer setCharacterStream(long paramLong) throws SQLException {
    checkInvalidActionOnEmpty();
    return new TbClobWriter(this, (paramLong == 0L) ? 1L : paramLong);
  }
  
  public int setString(long paramLong, String paramString) throws SQLException {
    checkInvalidActionOnEmpty();
    if (paramString == null)
      throw TbError.newSQLException(-590768); 
    char[] arrayOfChar = paramString.toCharArray();
    return (int)putCharsInternal(paramLong, arrayOfChar, 0L, arrayOfChar.length);
  }
  
  public int setString(long paramLong, String paramString, int paramInt1, int paramInt2) throws SQLException {
    checkInvalidActionOnEmpty();
    if (paramInt2 < 0)
      throw TbError.newSQLException(-590765, paramInt2); 
    if (paramString == null)
      throw TbError.newSQLException(-590768); 
    if (paramInt2 == 0)
      return 0; 
    char[] arrayOfChar = new char[paramInt2];
    paramString.getChars(paramInt1, paramInt1 + paramInt2, arrayOfChar, 0);
    return (int)putCharsInternal(paramLong, arrayOfChar, 0L, paramInt2);
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\TbClobBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */