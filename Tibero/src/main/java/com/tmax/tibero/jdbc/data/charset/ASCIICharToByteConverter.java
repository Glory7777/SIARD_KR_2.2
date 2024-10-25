package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;

public class ASCIICharToByteConverter {
  protected boolean subMode = true;
  
  protected byte[] subBytes = new byte[] { 63 };
  
  public boolean canConvert(char paramChar) {
    return (paramChar <= '');
  }
  
  public int convCharArr(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    int i = 0;
    int j = 0;
    char c = Character.MIN_VALUE;
    byte[] arrayOfByte = new byte[1];
    i = paramInt1;
    j = paramInt3;
    if (c) {
      c = Character.MIN_VALUE;
      if (paramArrayOfchar[paramInt1] >= '?' && paramArrayOfchar[paramInt1] <= '?')
        throw TbError.newSQLException(-590742, paramArrayOfchar[paramInt1]); 
      throw TbError.newSQLException(-590743, paramArrayOfchar[paramInt1]);
    } 
    while (i < paramInt2) {
      byte[] arrayOfByte1 = arrayOfByte;
      char c1 = paramArrayOfchar[i];
      int k = 1;
      byte b1 = 1;
      if (c1 >= '?' && c1 <= '?') {
        if (i + 1 == paramInt2) {
          c = c1;
          break;
        } 
        c1 = paramArrayOfchar[i + 1];
        if (c1 >= '?' && c1 <= '?') {
          if (this.subMode) {
            arrayOfByte1 = this.subBytes;
            k = this.subBytes.length;
            b1 = 2;
          } else {
            throw TbError.newSQLException(-590742, c1);
          } 
        } else {
          throw TbError.newSQLException(-590743, c1);
        } 
      } else {
        if (c1 >= '?' && c1 <= '?')
          throw TbError.newSQLException(-590714, "Invalid input"); 
        if (c1 <= '') {
          arrayOfByte1[0] = (byte)c1;
        } else if (this.subMode) {
          arrayOfByte1 = this.subBytes;
          k = this.subBytes.length;
        } else {
          throw TbError.newSQLException(-590742, c1);
        } 
      } 
      if (j + k > paramInt4)
        throw TbError.newSQLException(-590744, j + " + " + k + " > " + paramInt4); 
      for (byte b2 = 0; b2 < k; b2++)
        paramArrayOfbyte[j++] = arrayOfByte1[b2]; 
      i += b1;
    } 
    return j - paramInt3;
  }
  
  public int convString(String paramString, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    int i = 0;
    int j = 0;
    char c = Character.MIN_VALUE;
    byte[] arrayOfByte = new byte[1];
    i = paramInt1;
    j = paramInt3;
    if (c) {
      c = Character.MIN_VALUE;
      if (paramString.charAt(paramInt1) >= '?' && paramString.charAt(paramInt1) <= '?')
        throw TbError.newSQLException(-590742, paramString.charAt(paramInt1)); 
      throw TbError.newSQLException(-590743, paramString.charAt(paramInt1));
    } 
    while (i < paramInt2) {
      byte[] arrayOfByte1 = arrayOfByte;
      char c1 = paramString.charAt(i);
      int k = 1;
      byte b1 = 1;
      if (c1 >= '?' && c1 <= '?') {
        if (i + 1 == paramInt2) {
          c = c1;
          break;
        } 
        c1 = paramString.charAt(i + 1);
        if (c1 >= '?' && c1 <= '?') {
          if (this.subMode) {
            arrayOfByte1 = this.subBytes;
            k = this.subBytes.length;
            b1 = 2;
          } else {
            throw TbError.newSQLException(-590742, c1);
          } 
        } else {
          throw TbError.newSQLException(-590743, c1);
        } 
      } else {
        if (c1 >= '?' && c1 <= '?')
          throw TbError.newSQLException(-590743, c1); 
        if (c1 <= '') {
          arrayOfByte1[0] = (byte)c1;
        } else if (this.subMode) {
          arrayOfByte1 = this.subBytes;
          k = this.subBytes.length;
        } else {
          throw TbError.newSQLException(-590742, c1);
        } 
      } 
      if (j + k > paramInt4)
        throw TbError.newSQLException(-590744, j + " + " + k + " > " + paramInt4); 
      for (byte b2 = 0; b2 < k; b2++)
        paramArrayOfbyte[j++] = arrayOfByte1[b2]; 
      i += b1;
    } 
    return j - paramInt3;
  }
  
  public int getMaxBytesPerChar() {
    return 1;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\ASCIICharToByteConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */