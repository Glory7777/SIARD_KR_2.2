package com.tmax.tibero.jdbc.data;

import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.util.TbCommon;
import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class TbRAW {
  private byte[] data;
  
  static byte hexTo4Bit(char paramChar) throws SQLException {
    byte b = (byte)Character.digit(paramChar, 16);
    if (b == -1)
      throw new SQLException("invalid hexademical number: " + paramChar); 
    return b;
  }
  
  public static byte[] hexString2Bytes(String paramString) throws SQLException {
    byte[] arrayOfByte;
    int i = paramString.length();
    char[] arrayOfChar = new char[i];
    paramString.getChars(0, i, arrayOfChar, 0);
    byte b1 = 0;
    byte b2 = 0;
    if (i == 0)
      return new byte[0]; 
    if (i % 2 > 0) {
      arrayOfByte = new byte[(i + 1) / 2];
      arrayOfByte[b1++] = hexTo4Bit(arrayOfChar[b2++]);
    } else {
      arrayOfByte = new byte[i / 2];
    } 
    while (b1 < arrayOfByte.length) {
      arrayOfByte[b1] = (byte)(hexTo4Bit(arrayOfChar[b2++]) << 4 | hexTo4Bit(arrayOfChar[b2++]));
      b1++;
    } 
    return arrayOfByte;
  }
  
  public static TbRAW newRAW(Object paramObject) throws SQLException {
    return new TbRAW(paramObject);
  }
  
  public static TbRAW oldRAW(Object paramObject) throws SQLException {
    TbRAW tbRAW;
    if (paramObject instanceof String) {
      String str = (String)paramObject;
      byte[] arrayOfByte = null;
      try {
        arrayOfByte = str.getBytes("ISO8859_1");
      } catch (UnsupportedEncodingException unsupportedEncodingException) {
        throw TbError.newSQLException(-590714);
      } 
      tbRAW = new TbRAW(arrayOfByte);
    } else {
      tbRAW = new TbRAW(paramObject);
    } 
    return tbRAW;
  }
  
  public TbRAW() {}
  
  public TbRAW(byte[] paramArrayOfbyte) {
    this.data = paramArrayOfbyte;
  }
  
  public TbRAW(Object paramObject) throws SQLException {
    this();
    if (paramObject instanceof byte[]) {
      setShareBytes((byte[])paramObject);
    } else if (paramObject instanceof String) {
      setShareBytes(hexString2Bytes((String)paramObject));
    } else {
      throw new SQLException("invalid parameter type.");
    } 
  }
  
  public Object toJdbc() throws SQLException {
    return getBytes();
  }
  
  public boolean isConvertibleTo(Class paramClass) {
    String str = paramClass.getName();
    return (str.compareTo("java.lang.String") == 0 || str.compareTo("java.io.InputStream") == 0 || str.compareTo("java.io.Reader") == 0);
  }
  
  public String stringValue() {
    return TbCommon.bytes2String(getBytes(), 0, (int)getLength());
  }
  
  public Reader characterStreamValue() throws SQLException {
    int i = (int)getLength();
    char[] arrayOfChar = new char[i * 2];
    byte[] arrayOfByte = shareBytes();
    bytesToHexChars(arrayOfByte, i, arrayOfChar);
    return new CharArrayReader(arrayOfChar);
  }
  
  public InputStream asciiStreamValue() throws SQLException {
    int i = (int)getLength();
    char[] arrayOfChar = new char[i * 2];
    byte[] arrayOfByte1 = shareBytes();
    bytesToHexChars(arrayOfByte1, i, arrayOfChar);
    byte[] arrayOfByte2 = new byte[i * 2];
    for (byte b = 0; b < i * 2; b++)
      arrayOfByte2[b] = (byte)arrayOfChar[b]; 
    return new ByteArrayInputStream(arrayOfByte2);
  }
  
  public InputStream binaryStreamValue() throws SQLException {
    return getStream();
  }
  
  public Object makeJdbcArray(int paramInt) {
    return new byte[paramInt][];
  }
  
  public byte[] getBytes() {
    byte[] arrayOfByte = new byte[this.data.length];
    System.arraycopy(this.data, 0, arrayOfByte, 0, this.data.length);
    return arrayOfByte;
  }
  
  public InputStream getStream() {
    return new ByteArrayInputStream(this.data);
  }
  
  public byte[] shareBytes() {
    return this.data;
  }
  
  public void setShareBytes(byte[] paramArrayOfbyte) {
    this.data = paramArrayOfbyte;
  }
  
  public void setBytes(byte[] paramArrayOfbyte) {
    int i = paramArrayOfbyte.length;
    this.data = new byte[i];
    System.arraycopy(paramArrayOfbyte, 0, this.data, 0, i);
  }
  
  public long getLength() {
    return (null == this.data) ? 0L : this.data.length;
  }
  
  private void bytesToHexChars(byte[] paramArrayOfbyte, int paramInt, char[] paramArrayOfchar) {
    byte b1 = 0;
    byte b2 = 0;
    while (b1 < paramInt) {
      paramArrayOfchar[b2++] = (char)rawToHex((byte)(paramArrayOfbyte[b1] >> 4 & 0xF));
      paramArrayOfchar[b2++] = (char)rawToHex((byte)(paramArrayOfbyte[b1] & 0xF));
      b1++;
    } 
  }
  
  private byte rawToHex(byte paramByte) {
    paramByte = (byte)(paramByte & 0xF);
    return (byte)((paramByte >= 10) ? (paramByte - 10 + 65) : (paramByte + 48));
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject == null)
      return false; 
    if (paramObject instanceof TbRAW) {
      TbRAW tbRAW = (TbRAW)paramObject;
      if (this.data == null && tbRAW.data == null)
        return true; 
      if ((this.data == null && tbRAW.data != null) || (this.data != null && tbRAW.data == null))
        return false; 
      if (this.data.length != tbRAW.data.length)
        return false; 
      for (byte b = 0; b < this.data.length; b++) {
        if (this.data[b] != tbRAW.data[b])
          return false; 
      } 
      return true;
    } 
    return false;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\TbRAW.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */