package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;

public class UnicodeCharToByteConverter {
  protected boolean subMode = true;
  
  protected byte[] subBytes = new byte[] { 63 };
  
  int originalByteOrder;
  
  boolean usesMark;
  
  public UnicodeCharToByteConverter(int paramInt, boolean paramBoolean) {
    this.originalByteOrder = paramInt;
    this.usesMark = paramBoolean;
  }
  
  public int convCharArr(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    boolean bool1 = false;
    boolean bool2 = false;
    boolean bool3 = true;
    if (paramInt1 >= paramInt2)
      return 0; 
    int i = paramInt1;
    int j = paramInt3;
    int k = paramInt4 - 2;
    if (bool1 && !bool2) {
      if (j > k)
        throw TbError.newSQLException(-590744, j + " > " + k); 
      if (bool3 == true) {
        paramArrayOfbyte[j++] = -2;
        paramArrayOfbyte[j++] = -1;
      } else {
        paramArrayOfbyte[j++] = -1;
        paramArrayOfbyte[j++] = -2;
      } 
      bool2 = true;
    } 
    if (bool3 == true) {
      while (i < paramInt2) {
        if (j > k)
          throw TbError.newSQLException(-590744, j + " > " + k); 
        char c = paramArrayOfchar[i++];
        paramArrayOfbyte[j++] = (byte)(c >> 8);
        paramArrayOfbyte[j++] = (byte)(c & 0xFF);
      } 
    } else {
      while (i < paramInt2) {
        if (j > k)
          throw TbError.newSQLException(-590744, j + " > " + k); 
        char c = paramArrayOfchar[i++];
        paramArrayOfbyte[j++] = (byte)(c & 0xFF);
        paramArrayOfbyte[j++] = (byte)(c >> 8);
      } 
    } 
    return j - paramInt3;
  }
  
  public int convString(String paramString, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    boolean bool1 = false;
    boolean bool2 = false;
    boolean bool3 = true;
    if (paramInt1 >= paramInt2)
      return 0; 
    int i = paramInt1;
    int j = paramInt3;
    int k = paramInt4 - 2;
    if (bool1 && !bool2) {
      if (j > k)
        throw TbError.newSQLException(-590744, j + " > " + k); 
      if (bool3 == true) {
        paramArrayOfbyte[j++] = -2;
        paramArrayOfbyte[j++] = -1;
      } else {
        paramArrayOfbyte[j++] = -1;
        paramArrayOfbyte[j++] = -2;
      } 
      bool2 = true;
    } 
    if (bool3 == true) {
      while (i < paramInt2) {
        if (j > k)
          throw TbError.newSQLException(-590744, j + " > " + k); 
        char c = paramString.charAt(i++);
        paramArrayOfbyte[j++] = (byte)(c >> 8);
        paramArrayOfbyte[j++] = (byte)(c & 0xFF);
      } 
    } else {
      while (i < paramInt2) {
        if (j > k)
          throw TbError.newSQLException(-590744, j + " > " + k); 
        char c = paramString.charAt(i++);
        paramArrayOfbyte[j++] = (byte)(c & 0xFF);
        paramArrayOfbyte[j++] = (byte)(c >> 8);
      } 
    } 
    return j - paramInt3;
  }
  
  public int getMaxBytesPerChar() {
    return 4;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\UnicodeCharToByteConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */