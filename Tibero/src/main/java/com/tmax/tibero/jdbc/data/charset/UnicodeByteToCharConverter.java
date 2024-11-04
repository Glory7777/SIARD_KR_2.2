package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;

public class UnicodeByteToCharConverter {
  protected boolean subMode = true;
  
  protected char[] subChars = new char[] { '�' };
  
  int originalByteOrder = 0;
  
  boolean usesMark = true;
  
  public UnicodeByteToCharConverter() {}
  
  protected UnicodeByteToCharConverter(int paramInt, boolean paramBoolean) {}
  
  public int convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
    int m;
    byte b1 = 1;
    boolean bool1 = false;
    boolean bool2 = false;
    int i = 0;
    boolean bool3 = false;
    if (paramInt1 >= paramInt2)
      return 0; 
    byte b2 = 0;
    int j = paramInt1;
    int k = paramInt3;
    if (bool3) {
      m = i & 0xFF;
      bool3 = false;
    } else {
      m = paramArrayOfbyte[j++] & 0xFF;
    } 
    b2 = 1;
    if (bool1 && !bool2 && j < paramInt2) {
      int n = paramArrayOfbyte[j++] & 0xFF;
      b2 = 2;
      char c = (char)(m << 8 | n);
      byte b = 0;
      if (c == '﻿') {
        b = 1;
      } else if (c == '￾') {
        b = 2;
      } 
      if (b1 != 1) {
        if (b == 0)
          throw TbError.newSQLException(-590745); 
        b1 = b;
        if (j < paramInt2) {
          m = paramArrayOfbyte[j++] & 0xFF;
          b2 = 1;
        } 
      } else if (b == 0) {
        j--;
        b2 = 1;
      } else if (b1 == b) {
        if (j < paramInt2) {
          m = paramArrayOfbyte[j++] & 0xFF;
          b2 = 1;
        } 
      } else {
        throw TbError.newSQLException(-590746);
      } 
      bool2 = true;
    } 
    while (j < paramInt2) {
      char c;
      int n = paramArrayOfbyte[j++] & 0xFF;
      b2 = 2;
      if (b1 == 1) {
        c = (char)(m << 8 | n);
      } else {
        c = (char)(n << 8 | m);
      } 
      if (c == '￾')
        throw TbError.newSQLException(-590747); 
      if (k >= paramInt4)
        throw TbError.newSQLException(-590744, k + " >= " + paramInt4); 
      paramArrayOfchar[k++] = c;
      if (j < paramInt2) {
        m = paramArrayOfbyte[j++] & 0xFF;
        b2 = 1;
      } 
    } 
    if (b2 == 1) {
      i = m;
      bool3 = true;
    } 
    return k - paramInt3;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\UnicodeByteToCharConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */