package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class EUCCNDecoder implements CharsetDecoder {
  private EUCCNByteToCharConverter conv = null;
  
  public EUCCNDecoder() {
    this.conv = new EUCCNByteToCharConverter();
  }
  
  public int bytesToChars(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
    return this.conv.convert(paramArrayOfbyte, paramInt1, paramInt1 + paramInt2, paramArrayOfchar, paramInt3, paramInt3 + paramInt4);
  }
  
  public String bytesToString(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    try {
      return new String(paramArrayOfbyte, paramInt1, paramInt2, "EUC-CN");
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      throw TbError.newSQLException(-590714);
    } 
  }
  
  public String bytesToString(byte[] paramArrayOfbyte) throws SQLException {
    return bytesToString(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public int fixedBytesToChars(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
    return 0;
  }
  
  public String fixedBytesToString(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    return null;
  }
  
  public String fixedBytesToString(byte[] paramArrayOfbyte) throws SQLException {
    return null;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\charset\EUCCNDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */