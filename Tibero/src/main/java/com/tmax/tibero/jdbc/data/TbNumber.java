package com.tmax.tibero.jdbc.data;

import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.util.TbCommon;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.SQLException;

public class TbNumber {
  private static final int NUMBER_MAX_PREC = 38;
  
  private static final int EXP_BIAS_POS = 65;
  
  private static final int EXP_BIAS_NEG = 62;
  
  private static final int NUMBER_MAX_EXP = 62;
  
  private static final int NUMBER_MIN_EXP = -65;
  
  private static final int NUMBER_TOP = -16;
  
  private static final int NUMBER_BOT = 16;
  
  private static final int NUMBER_BASE = 100;
  
  private static final int NUMBER_MAX_LEN = 21;
  
  private static final int NUMBER_MASK_SIGN = 128;
  
  private static final int NUMBER_MASK_EXP = 127;
  
  private static final byte[] MAX_INTEGER = new byte[] { 6, -59, -107, -81, -80, -92, -81 };
  
  private static final byte[] MIN_INTEGER = new byte[] { 7, 58, 107, 81, 80, 92, 80, -16 };
  
  private static final byte[] MAX_LONG = new byte[] { 
      11, -54, -119, -106, -95, -56, -125, -60, -74, -51, 
      -70, -121 };
  
  private static final byte[] MIN_LONG = new byte[] { 
      12, 53, 119, 106, 95, 56, 125, 60, 74, 51, 
      70, 120, -16 };
  
  private static char[][] coefToChar = new char[][] { 
      { '0', '0' }, { '0', '0' }, { '0', '0' }, { '0', '0' }, { '0', '0' }, { '0', '0' }, { '0', '0' }, { '0', '0' }, { '0', '0' }, { '0', '0' }, 
      { '0', '0' }, { '0', '0' }, { '0', '0' }, { '0', '0' }, { '0', '0' }, { '0', '0' }, { '0', '0' }, { '0', '0' }, { '0', '0' }, { '0', '0' }, 
      { '0', '0' }, { '0', '0' }, { '0', '0' }, { '0', '0' }, { '0', '0' }, { '0', '0' }, { '0', '0' }, { '0', '0' }, { '0', '0' }, { '9', '9' }, 
      { '9', '8' }, { '9', '7' }, { '9', '6' }, { '9', '5' }, { '9', '4' }, { '9', '3' }, { '9', '2' }, { '9', '1' }, { '9', '0' }, { '8', '9' }, 
      { '8', '8' }, { '8', '7' }, { '8', '6' }, { '8', '5' }, { '8', '4' }, { '8', '3' }, { '8', '2' }, { '8', '1' }, { '8', '0' }, { '7', '9' }, 
      { '7', '8' }, { '7', '7' }, { '7', '6' }, { '7', '5' }, { '7', '4' }, { '7', '3' }, { '7', '2' }, { '7', '1' }, { '7', '0' }, { '6', '9' }, 
      { '6', '8' }, { '6', '7' }, { '6', '6' }, { '6', '5' }, { '6', '4' }, { '6', '3' }, { '6', '2' }, { '6', '1' }, { '6', '0' }, { '5', '9' }, 
      { '5', '8' }, { '5', '7' }, { '5', '6' }, { '5', '5' }, { '5', '4' }, { '5', '3' }, { '5', '2' }, { '5', '1' }, { '5', '0' }, { '4', '9' }, 
      { '4', '8' }, { '4', '7' }, { '4', '6' }, { '4', '5' }, { '4', '4' }, { '4', '3' }, { '4', '2' }, { '4', '1' }, { '4', '0' }, { '3', '9' }, 
      { '3', '8' }, { '3', '7' }, { '3', '6' }, { '3', '5' }, { '3', '4' }, { '3', '3' }, { '3', '2' }, { '3', '1' }, { '3', '0' }, { '2', '9' }, 
      { '2', '8' }, { '2', '7' }, { '2', '6' }, { '2', '5' }, { '2', '4' }, { '2', '3' }, { '2', '2' }, { '2', '1' }, { '2', '0' }, { '1', '9' }, 
      { '1', '8' }, { '1', '7' }, { '1', '6' }, { '1', '5' }, { '1', '4' }, { '1', '3' }, { '1', '2' }, { '1', '1' }, { '1', '0' }, { '0', '9' }, 
      { '0', '8' }, { '0', '7' }, { '0', '6' }, { '0', '5' }, { '0', '4' }, { '0', '3' }, { '0', '2' }, { '0', '1' }, { '0', '0' }, { '0', '1' }, 
      { '0', '2' }, { '0', '3' }, { '0', '4' }, { '0', '5' }, { '0', '6' }, { '0', '7' }, { '0', '8' }, { '0', '9' }, { '1', '0' }, { '1', '1' }, 
      { '1', '2' }, { '1', '3' }, { '1', '4' }, { '1', '5' }, { '1', '6' }, { '1', '7' }, { '1', '8' }, { '1', '9' }, { '2', '0' }, { '2', '1' }, 
      { '2', '2' }, { '2', '3' }, { '2', '4' }, { '2', '5' }, { '2', '6' }, { '2', '7' }, { '2', '8' }, { '2', '9' }, { '3', '0' }, { '3', '1' }, 
      { '3', '2' }, { '3', '3' }, { '3', '4' }, { '3', '5' }, { '3', '6' }, { '3', '7' }, { '3', '8' }, { '3', '9' }, { '4', '0' }, { '4', '1' }, 
      { '4', '2' }, { '4', '3' }, { '4', '4' }, { '4', '5' }, { '4', '6' }, { '4', '7' }, { '4', '8' }, { '4', '9' }, { '5', '0' }, { '5', '1' }, 
      { '5', '2' }, { '5', '3' }, { '5', '4' }, { '5', '5' }, { '5', '6' }, { '5', '7' }, { '5', '8' }, { '5', '9' }, { '6', '0' }, { '6', '1' }, 
      { '6', '2' }, { '6', '3' }, { '6', '4' }, { '6', '5' }, { '6', '6' }, { '6', '7' }, { '6', '8' }, { '6', '9' }, { '7', '0' }, { '7', '1' }, 
      { '7', '2' }, { '7', '3' }, { '7', '4' }, { '7', '5' }, { '7', '6' }, { '7', '7' }, { '7', '8' }, { '7', '9' }, { '8', '0' }, { '8', '1' }, 
      { '8', '2' }, { '8', '3' }, { '8', '4' }, { '8', '5' }, { '8', '6' }, { '8', '7' }, { '8', '8' }, { '8', '9' }, { '9', '0' }, { '9', '1' }, 
      { '9', '2' }, { '9', '3' }, { '9', '4' }, { '9', '5' }, { '9', '6' }, { '9', '7' }, { '9', '8' }, { '9', '9' } };
  
  private static int convertCoef(byte[] paramArrayOfbyte, int paramInt1, char[] paramArrayOfchar, int paramInt2, int paramInt3) {
    byte b = 0;
    byte b1 = paramArrayOfbyte[paramInt1 + 2];
    if (b1 < 0)
      b1 += 256; 
    char[] arrayOfChar = coefToChar[b1];
    paramArrayOfchar[paramInt2 + b] = arrayOfChar[0];
    if (arrayOfChar[0] != '0')
      b++; 
    paramArrayOfchar[paramInt2 + b++] = arrayOfChar[1];
    int i = getSiglen(paramArrayOfbyte, paramInt1);
    int j;
    for (j = 1; j < i; j++) {
      b1 = paramArrayOfbyte[paramInt1 + 2 + j];
      if (b1 < 0)
        b1 += 256; 
      arrayOfChar = coefToChar[b1];
      paramArrayOfchar[paramInt2 + b++] = arrayOfChar[0];
      paramArrayOfchar[paramInt2 + b++] = arrayOfChar[1];
    } 
    if (paramArrayOfchar[paramInt2 + b - 1] == '0')
      b--; 
    if (paramInt3 > 0 && paramInt3 < b) {
      for (j = paramInt2 + b; j >= paramInt3 + paramInt2; j--)
        paramArrayOfchar[j + 1] = paramArrayOfchar[j]; 
      paramArrayOfchar[paramInt2 + paramInt3] = '.';
      b++;
    } 
    return b;
  }
  
  private static int convertExp(int paramInt1, int paramInt2, char[] paramArrayOfchar) {
    boolean bool = false;
    paramInt1 = (paramInt1 < 0) ? -paramInt1 : paramInt1;
    if (paramInt1 >= 0 && paramInt1 < 10) {
      paramArrayOfchar[paramInt2++] = '0';
      bool = true;
    } 
    String str = Integer.toString(paramInt1);
    byte b;
    for (b = 0; b < str.length(); b++)
      paramArrayOfchar[paramInt2 + b] = str.charAt(b); 
    return b + (bool ? 1 : 0);
  }
  
  private static byte digitEncode(boolean paramBoolean, int paramInt) {
    return paramBoolean ? (byte)(128 + paramInt) : (byte)(128 - paramInt);
  }
  
  public static int fromInteger(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    byte b = 0;
    int[] arrayOfInt = new int[21];
    boolean bool = (paramInt2 >= 0) ? true : false;
    int i;
    for (i = 0; paramInt2 != 0; i++) {
      int j = paramInt2 / 100;
      arrayOfInt[i] = paramInt2 - 100 * j;
      paramInt2 = j;
    } 
    if (i == 0)
      return toZero(paramArrayOfbyte, paramInt1); 
    if (bool) {
      for (byte b1 = 0; b1 < i; b1++) {
        paramArrayOfbyte[paramInt1 + 2 + b1] = (byte)(128 + arrayOfInt[i - b1 - 1]);
        if (arrayOfInt[i - b1 - 1] != 0)
          b = b1; 
      } 
      paramArrayOfbyte[paramInt1 + 1] = (byte)(0x80 | (byte)(65 + i - 1));
      i = b + 1;
    } else {
      for (byte b1 = 0; b1 < i; b1++) {
        paramArrayOfbyte[paramInt1 + 2 + b1] = (byte)(128 - -arrayOfInt[i - b1 - 1]);
        if (arrayOfInt[i - b1 - 1] != 0)
          b = b1; 
      } 
      paramArrayOfbyte[paramInt1 + 1] = (byte)(62 - i - 1);
      i = b + 1;
      paramArrayOfbyte[paramInt1 + 2 + i] = -16;
      i++;
    } 
    paramArrayOfbyte[paramInt1] = (byte)(i + 1);
    return i + 2;
  }
  
  public static byte[] fromInteger(int paramInt) throws SQLException {
    byte b = 0;
    int[] arrayOfInt = new int[21];
    boolean bool = (paramInt >= 0) ? true : false;
    int i;
    for (i = 0; paramInt != 0; i++) {
      int j = paramInt / 100;
      arrayOfInt[i] = paramInt - 100 * j;
      paramInt = j;
    } 
    if (i == 0)
      return toZero(); 
    byte[] arrayOfByte = new byte[23];
    if (bool) {
      for (byte b1 = 0; b1 < i; b1++) {
        arrayOfByte[2 + b1] = (byte)(128 + arrayOfInt[i - b1 - 1]);
        if (arrayOfInt[i - b1 - 1] != 0)
          b = b1; 
      } 
      arrayOfByte[1] = (byte)(0x80 | (byte)(65 + i - 1));
      i = b + 1;
    } else {
      for (byte b1 = 0; b1 < i; b1++) {
        arrayOfByte[2 + b1] = (byte)(128 - -arrayOfInt[i - b1 - 1]);
        if (arrayOfInt[i - b1 - 1] != 0)
          b = b1; 
      } 
      arrayOfByte[1] = (byte)(62 - i - 1);
      i = b + 1;
      arrayOfByte[2 + i] = -16;
      i++;
    } 
    arrayOfByte[0] = (byte)(i + 1);
    return arrayOfByte;
  }
  
  public static int fromLong(byte[] paramArrayOfbyte, int paramInt, long paramLong) throws SQLException {
    byte b = 0;
    int[] arrayOfInt = new int[21];
    boolean bool = (paramLong >= 0L) ? true : false;
    int i;
    for (i = 0; paramLong != 0L; i++) {
      long l = paramLong / 100L;
      arrayOfInt[i] = (int)(paramLong - 100L * l);
      paramLong = l;
    } 
    if (i == 0)
      return toZero(paramArrayOfbyte, paramInt); 
    if (bool) {
      for (byte b1 = 0; b1 < i; b1++) {
        paramArrayOfbyte[paramInt + 2 + b1] = (byte)(128 + arrayOfInt[i - b1 - 1]);
        if (arrayOfInt[i - b1 - 1] != 0)
          b = b1; 
      } 
      paramArrayOfbyte[paramInt + 1] = (byte)(0x80 | 65 + i - 1);
      i = b + 1;
    } else {
      for (byte b1 = 0; b1 < i; b1++) {
        paramArrayOfbyte[paramInt + 2 + b1] = (byte)(128 - -arrayOfInt[i - b1 - 1]);
        if (arrayOfInt[i - b1 - 1] != 0)
          b = b1; 
      } 
      paramArrayOfbyte[paramInt + 1] = (byte)(62 - i - 1);
      i = b + 1;
      paramArrayOfbyte[paramInt + 2 + i] = -16;
      i++;
    } 
    paramArrayOfbyte[paramInt] = (byte)(i + 1);
    return i + 2;
  }
  
  public static byte[] fromLong(long paramLong) throws SQLException {
    byte b = 0;
    int[] arrayOfInt = new int[21];
    boolean bool = (paramLong >= 0L) ? true : false;
    int i;
    for (i = 0; paramLong != 0L; i++) {
      long l = paramLong / 100L;
      arrayOfInt[i] = (int)(paramLong - 100L * l);
      paramLong = l;
    } 
    if (i == 0)
      return toZero(); 
    byte[] arrayOfByte = new byte[23];
    if (bool) {
      for (byte b1 = 0; b1 < i; b1++) {
        arrayOfByte[2 + b1] = (byte)(128 + arrayOfInt[i - b1 - 1]);
        if (arrayOfInt[i - b1 - 1] != 0)
          b = b1; 
      } 
      arrayOfByte[1] = (byte)(0x80 | 65 + i - 1);
      i = b + 1;
    } else {
      for (byte b1 = 0; b1 < i; b1++) {
        arrayOfByte[2 + b1] = (byte)(128 - -arrayOfInt[i - b1 - 1]);
        if (arrayOfInt[i - b1 - 1] != 0)
          b = b1; 
      } 
      arrayOfByte[1] = (byte)(62 - i - 1);
      i = b + 1;
      arrayOfByte[2 + i] = -16;
      i++;
    } 
    arrayOfByte[0] = (byte)(i + 1);
    return arrayOfByte;
  }
  
  public static int fromString(byte[] paramArrayOfbyte, int paramInt, String paramString) throws SQLException {
    byte[] arrayOfByte = new byte[41];
    byte b = 0;
    StringReader stringReader = new StringReader(paramString);
    try {
      byte b3 = 0;
      byte b4 = -1;
      byte b6 = 0;
      char c = (char)stringReader.read();
      boolean bool1 = true;
      if (c == '-') {
        bool1 = false;
        c = (char)stringReader.read();
      } else if (c == '+') {
        c = (char)stringReader.read();
      } 
      while (c == '0')
        c = (char)stringReader.read(); 
      while (Character.isDigit(c)) {
        if (b3 < 40) {
          arrayOfByte[b3] = (byte)Character.getNumericValue(c);
          if (c != '0')
            b4 = b3; 
        } else if (b3 == 40) {
          b = (byte)Character.getNumericValue(c);
        } 
        b3++;
        c = (char)stringReader.read();
      } 
      byte b5 = b3;
      if (c == '.') {
        c = (char)stringReader.read();
        if (b4 < 0)
          while (c == '0') {
            b6++;
            c = (char)stringReader.read();
          }  
        while (Character.isDigit(c)) {
          if (b3 < 40) {
            arrayOfByte[b3] = (byte)Character.getNumericValue(c);
            if (c != '0')
              b4 = b3; 
          } else if (b3 == 40) {
            b = (byte)Character.getNumericValue(c);
          } 
          b3++;
          c = (char)stringReader.read();
        } 
      } 
      int m = 0;
      boolean bool2 = false;
      if ((((c == 'e') ? 1 : 0) | ((c == 'E') ? 1 : 0)) != 0) {
        c = (char)stringReader.read();
        if (c == '-') {
          bool2 = true;
          c = (char)stringReader.read();
        } else if (c == '+') {
          c = (char)stringReader.read();
        } 
        m = 0;
        while (Character.isDigit(c)) {
          m = m * 10 + Character.getNumericValue(c);
          c = (char)stringReader.read();
        } 
      } 
      if (b4 < 0)
        return toZero(paramArrayOfbyte, paramInt); 
      int n = (b5 > 0) ? (b5 - 1) : -(b6 + 1);
      if (bool2)
        m = -m; 
      m += n;
      boolean bool3 = ((m & 0x1) == 0) ? true : false;
      if (bool3 && b3 >= 40)
        b = arrayOfByte[39]; 
      int k = m >> 1;
      if (k > 62)
        throw TbError.newSQLException(-90653, paramString); 
      if (k < -65)
        throw TbError.newSQLException(-90654, paramString); 
      int j = 1 + (bool1 ? 0 : 1) + (b4 + 1 + 1 + (bool3 ? 1 : 0)) / 2;
      paramArrayOfbyte[paramInt] = (byte)j;
      if (bool1) {
        paramArrayOfbyte[paramInt + 1] = (byte)(0x80 | 65 + k);
      } else {
        paramArrayOfbyte[paramInt + 1] = (byte)(62 - k);
      } 
      boolean bool4 = bool3;
      byte b1 = 0;
      int i = 0;
      byte b2 = 2;
      while (b1 <= b4) {
        if (bool4) {
          paramArrayOfbyte[paramInt + b2++] = digitEncode(bool1, i + arrayOfByte[b1]);
        } else {
          i = arrayOfByte[b1] * 10;
        } 
        bool4 = !bool4 ? true : false;
        b1++;
      } 
      if (bool4)
        paramArrayOfbyte[paramInt + b2++] = digitEncode(bool1, i); 
      if (!bool1)
        paramArrayOfbyte[paramInt + b2++] = -16; 
      if (bool1 && j >= 21) {
        j = 21;
        paramArrayOfbyte[paramInt] = (byte)j;
      } else if (!bool1 && j >= 22) {
        j = 22;
        paramArrayOfbyte[paramInt] = (byte)j;
        paramArrayOfbyte[paramInt + 21 + 1] = -16;
      } 
      if (b >= 5)
        roundingAt41Prec(paramArrayOfbyte, paramInt, bool1, j, k, paramString); 
      byte b7 = (byte) (bool1 ? 0 : 1);
      int i1;
      for (i1 = j - b7; i1 >= 2 && paramArrayOfbyte[paramInt + i1] == Byte.MIN_VALUE; i1--);
      if (i1 < j - b7) {
        if (i1 < 2) {
          paramArrayOfbyte[paramInt] = 1;
          return 2;
        } 
        paramArrayOfbyte[paramInt + i1 + 1] = -16;
        paramArrayOfbyte[paramInt] = (byte)(i1 + b7);
        return i1 + b7 + 1;
      } 
      return j + 1;
    } catch (IOException iOException) {
      return 0;
    } 
  }
  
  public static byte[] fromString(String paramString) throws SQLException {
    byte[] arrayOfByte = new byte[41];
    byte b = 0;
    StringReader stringReader = new StringReader(paramString);
    try {
      byte b3 = 0;
      byte b4 = -1;
      byte b6 = 0;
      char c = (char)stringReader.read();
      boolean bool1 = true;
      if (c == '-') {
        bool1 = false;
        c = (char)stringReader.read();
      } else if (c == '+') {
        c = (char)stringReader.read();
      } 
      while (c == '0')
        c = (char)stringReader.read(); 
      while (Character.isDigit(c)) {
        if (b3 < 40) {
          arrayOfByte[b3] = (byte)Character.getNumericValue(c);
          if (c != '0')
            b4 = b3; 
        } else if (b3 == 40) {
          b = (byte)Character.getNumericValue(c);
        } 
        b3++;
        c = (char)stringReader.read();
      } 
      byte b5 = b3;
      if (c == '.') {
        c = (char)stringReader.read();
        if (b4 < 0)
          while (c == '0') {
            b6++;
            c = (char)stringReader.read();
          }  
        while (Character.isDigit(c)) {
          if (b3 < 40) {
            arrayOfByte[b3] = (byte)Character.getNumericValue(c);
            if (c != '0')
              b4 = b3; 
          } else if (b3 == 40) {
            b = (byte)Character.getNumericValue(c);
          } 
          b3++;
          c = (char)stringReader.read();
        } 
      } 
      int m = 0;
      boolean bool2 = false;
      if ((((c == 'e') ? 1 : 0) | ((c == 'E') ? 1 : 0)) != 0) {
        c = (char)stringReader.read();
        if (c == '-') {
          bool2 = true;
          c = (char)stringReader.read();
        } else if (c == '+') {
          c = (char)stringReader.read();
        } 
        m = 0;
        while (Character.isDigit(c)) {
          m = m * 10 + Character.getNumericValue(c);
          c = (char)stringReader.read();
        } 
      } 
      if (b4 < 0)
        return toZero(); 
      int n = (b5 > 0) ? (b5 - 1) : -(b6 + 1);
      if (bool2)
        m = -m; 
      m += n;
      boolean bool3 = ((m & 0x1) == 0) ? true : false;
      if (bool3 && b3 >= 40)
        b = arrayOfByte[39]; 
      int k = m >> 1;
      if (k > 62)
        throw TbError.newSQLException(-90653, paramString); 
      if (k < -65)
        throw TbError.newSQLException(-90654, paramString); 
      int j = 1 + (bool1 ? 0 : 1) + (b4 + 1 + 1 + (bool3 ? 1 : 0)) / 2;
      byte[] arrayOfByte1 = new byte[j + 1];
      arrayOfByte1[0] = (byte)j;
      if (bool1) {
        arrayOfByte1[1] = (byte)(0x80 | 65 + k);
      } else {
        arrayOfByte1[1] = (byte)(62 - k);
      } 
      boolean bool4 = bool3;
      byte b1 = 0;
      int i = 0;
      byte b2 = 2;
      while (b1 <= b4) {
        if (bool4) {
          arrayOfByte1[b2++] = digitEncode(bool1, i + arrayOfByte[b1]);
        } else {
          i = arrayOfByte[b1] * 10;
        } 
        bool4 = !bool4 ? true : false;
        b1++;
      } 
      if (bool4)
        arrayOfByte1[b2++] = digitEncode(bool1, i); 
      if (!bool1)
        arrayOfByte1[b2++] = -16; 
      if (bool1 && j >= 21) {
        j = 21;
        arrayOfByte1[0] = (byte)j;
      } else if (!bool1 && j >= 22) {
        j = 22;
        arrayOfByte1[0] = (byte)j;
        arrayOfByte1[22] = -16;
      } 
      if (b >= 5)
        roundingAt41Prec(arrayOfByte1, 0, bool1, j, k, paramString); 
      byte b7 = (byte) (bool1 ? 0 : 1);
      int i1;
      for (i1 = j - b7; i1 >= 2 && arrayOfByte1[i1] == Byte.MIN_VALUE; i1--);
      if (i1 < j - b7)
        if (i1 < 2) {
          arrayOfByte1[0] = 1;
        } else {
          arrayOfByte1[i1 + 1] = -16;
          arrayOfByte1[0] = (byte)(i1 + b7);
        }  
      return arrayOfByte1;
    } catch (IOException iOException) {
      return null;
    } 
  }
  
  private static void roundingAt41Prec(byte[] paramArrayOfbyte, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, String paramString) throws SQLException {
    int b2;
    byte b1 = 21;
    if (paramBoolean) {
      int i = getDecoPos(paramArrayOfbyte[paramInt1 + b1]) + 1;
      b2 = (i >= 100) ? 1 : 0;
      i -= b2 * 100;
      paramArrayOfbyte[paramInt1 + b1--] = digitEncode(paramBoolean, i);
      if (b2 == 1)
        while (b1 > 1) {
          i = getDecoPos(paramArrayOfbyte[paramInt1 + b1]) + 1;
          b2 = (i >= 100) ? 1 : 0;
          i -= b2 * 100;
          paramArrayOfbyte[paramInt1 + b1--] = digitEncode(paramBoolean, i);
        }  
    } else {
      int i = getDecoNeg(paramArrayOfbyte[paramInt1 + b1]) + 1;
      b2 = (i >= 100) ? 1 : 0;
      i -= b2 * 100;
      paramArrayOfbyte[paramInt1 + b1--] = digitEncode(paramBoolean, i);
      if (b2 == 1)
        while (b1 > 1) {
          i = getDecoNeg(paramArrayOfbyte[paramInt1 + b1]) + 1;
          b2 = (i >= 100) ? 1 : 0;
          i -= b2 * 100;
          paramArrayOfbyte[paramInt1 + b1--] = digitEncode(paramBoolean, i);
        }  
    } 
    if (b2 == 1) {
      System.arraycopy(paramArrayOfbyte, paramInt1 + 2, paramArrayOfbyte, paramInt1 + 3, 19);
      paramArrayOfbyte[paramInt1 + 2] = digitEncode(paramBoolean, 1);
      if (paramBoolean) {
        paramArrayOfbyte[paramInt1 + 1] = (byte)(0x80 | 65 + getExpPos(paramArrayOfbyte[paramInt1 + 1]) + 1);
      } else {
        paramArrayOfbyte[paramInt1 + 1] = (byte)(62 - getExpNeg(paramArrayOfbyte[paramInt1 + 1]) - 1);
      } 
      if (!paramBoolean)
        paramArrayOfbyte[paramInt1 + 21] = -16; 
      if (paramInt3 + 1 > 62)
        throw TbError.newSQLException(-90653, paramString); 
      if (paramInt3 + 1 < -65)
        throw TbError.newSQLException(-90654, paramString); 
    } 
  }
  
  private static int getDecoNeg(byte paramByte) {
    byte b = (byte)(128 - paramByte);
    return (b < 0) ? (b + 256) : b;
  }
  
  private static int getDecoPos(byte paramByte) {
    byte b = (byte)(paramByte - 128);
    return (b < 0) ? (b + 256) : b;
  }
  
  private static int getExpNeg(byte paramByte) {
    int bool = (paramByte < 0) ? (paramByte + 256) : paramByte;
    return 62 - (bool & 0x7F);
  }
  
  private static int getExpPos(byte paramByte) {
    int bool = (paramByte < 0) ? (paramByte + 256) : paramByte;
    return (bool & 0x7F) - 65;
  }
  
  public static String getNormalForm(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    int i;
    int m;
    int n;
    char[] arrayOfChar = null;
    if (paramArrayOfbyte[paramInt1] == 1)
      return "0"; 
    boolean bool = isPositive(paramArrayOfbyte[paramInt1 + 1]);
    if (bool) {
      m = (getExpPos(paramArrayOfbyte[paramInt1 + 1]) << 1) + ((getDecoPos(paramArrayOfbyte[paramInt1 + 2]) >= 10) ? 1 : 0);
      n = (getSiglen(paramArrayOfbyte, paramInt1) << 1) - ((getDecoPos(paramArrayOfbyte[paramInt1 + 2]) < 10) ? 1 : 0) - lastHalfFilledPos(paramArrayOfbyte, paramInt1);
    } else {
      m = (getExpNeg(paramArrayOfbyte[paramInt1 + 1]) << 1) + ((getDecoNeg(paramArrayOfbyte[paramInt1 + 2]) >= 10) ? 1 : 0);
      n = (getSiglen(paramArrayOfbyte, paramInt1) << 1) - ((getDecoNeg(paramArrayOfbyte[paramInt1 + 2]) < 10) ? 1 : 0) - lastHalfFilledNeg(paramArrayOfbyte, paramInt1);
    } 
    if (m >= 0) {
      i = (bool ? 0 : 1) + ((n <= m + 1) ? (m + 1 + 0 + 0) : (m + 1 + 1 + n - m + 1)) + 1;
    } else {
      i = (bool ? 0 : 1) + 1 + -m - 1 + n + 1;
    } 
    arrayOfChar = new char[i + 1];
    int j = 0;
    if (!bool)
      arrayOfChar[j++] = '-'; 
    int i1 = m + 1;
    if (i1 <= 0) {
      arrayOfChar[j++] = '.';
      for (byte b = 0; b < -i1; b++)
        arrayOfChar[j++] = '0'; 
    } 
    int k = convertCoef(paramArrayOfbyte, paramInt1, arrayOfChar, j, i1);
    j += k;
    if (i1 > k)
      for (byte b = 0; b < i1 - k; b++)
        arrayOfChar[j++] = '0';  
    return new String(arrayOfChar, 0, j);
  }
  
  public static String getRoughSciForm(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    int k;
    int m;
    if (paramArrayOfbyte[paramInt1] == 1)
      return "0E+00"; 
    boolean bool = isPositive(paramArrayOfbyte[paramInt1 + 1]);
    if (bool) {
      k = (getExpPos(paramArrayOfbyte[paramInt1 + 1]) << 1) + ((getDecoPos(paramArrayOfbyte[paramInt1 + 2]) >= 10) ? 1 : 0);
      m = (getSiglen(paramArrayOfbyte, paramInt1) << 1) - ((getDecoPos(paramArrayOfbyte[paramInt1 + 2]) < 10) ? 1 : 0) - lastHalfFilledPos(paramArrayOfbyte, paramInt1);
    } else {
      k = (getExpNeg(paramArrayOfbyte[paramInt1 + 1]) << 1) + ((getDecoNeg(paramArrayOfbyte[paramInt1 + 2]) >= 10) ? 1 : 0);
      m = (getSiglen(paramArrayOfbyte, paramInt1) << 1) - ((getDecoNeg(paramArrayOfbyte[paramInt1 + 2]) < 10) ? 1 : 0) - lastHalfFilledNeg(paramArrayOfbyte, paramInt1);
    } 
    int b = (k >= 100 || k <= -100) ? 3 : 2;
    int j = (bool ? 0 : 1) + m + ((m > 1) ? 1 : 0) + 1 + 1 + b + 1;
    char[] arrayOfChar = new char[j];
    int i = 0;
    if (!bool)
      arrayOfChar[i++] = '-'; 
    i += convertCoef(paramArrayOfbyte, paramInt1, arrayOfChar, i, 1);
    arrayOfChar[i++] = 'E';
    arrayOfChar[i++] = (k >= 0) ? '+' : '-';
    i += convertExp(k, i, arrayOfChar);
    return new String(arrayOfChar, 0, i);
  }
  
  public static int getSiglen(byte[] paramArrayOfbyte, int paramInt) {
    byte b = paramArrayOfbyte[paramInt + 1];
    if (b < 0)
      b += 256; 
    return paramArrayOfbyte[paramInt] - 1 - (((b ^ 0xFFFFFFFF) & 0x80) >> 7);
  }
  
  public static boolean isOrdinary(byte[] paramArrayOfbyte, int paramInt) {
    return (paramArrayOfbyte[paramInt] == 1 || (paramArrayOfbyte[paramInt + 2] != -16 && paramArrayOfbyte[paramInt + 2] != 16));
  }
  
  private static boolean isPositive(byte paramByte) {
    if (paramByte < 0)
      paramByte = (byte)(paramByte + 256); 
    return ((paramByte & 0x80) != 0);
  }
  
  public static boolean isValid(byte[] paramArrayOfbyte, int paramInt) {
    byte b1 = paramArrayOfbyte[paramInt];
    byte b2 = paramArrayOfbyte[paramInt + 1];
    if (b1 > 23)
      return false; 
    if (b2 < 0)
      b2 += 256; 
    switch (b1) {
      case 0:
        return false;
      case 1:
        if (b2 == 128)
          return true; 
      case 2:
        if (paramArrayOfbyte[paramInt + 2] == 240 && (b2 == 255 || b2 == Byte.MAX_VALUE))
          return true; 
        if (paramArrayOfbyte[paramInt + 2] == 16 && (b2 == 128 || b2 == 0))
          return true; 
        break;
    } 
    if (b2 >= 128 && b2 <= 255) {
      int i = b1 - 2;
      for (byte b = 0; b <= i; b++) {
        byte b3 = paramArrayOfbyte[paramInt + 2 + b];
        if (b3 < 0)
          b3 += 256; 
        if (b3 < 128 || b3 > 227)
          return false; 
      } 
      return true;
    } 
    if (b2 >= 0 && b2 <= Byte.MAX_VALUE) {
      int i = b1 - 2;
      for (byte b = 0; b < i; b++) {
        byte b3 = paramArrayOfbyte[paramInt + 2 + b];
        if (b3 < 0)
          b3 += 256; 
        if (b3 < 29 || b3 > 128)
          return false; 
      } 
      return true;
    }
      return false;
  }
  
  private static int lastHalfFilledNeg(byte[] paramArrayOfbyte, int paramInt) {
    int i = 128 - paramArrayOfbyte[paramInt + 2 + getSiglen(paramArrayOfbyte, paramInt) - 1];
    if (i < 0)
      i += 256; 
    return (i % 10 == 0) ? 1 : 0;
  }
  
  private static int lastHalfFilledPos(byte[] paramArrayOfbyte, int paramInt) {
    int i = paramArrayOfbyte[paramInt + 2 + getSiglen(paramArrayOfbyte, paramInt) - 1] - 128;
    if (i < 0)
      i += 256; 
    return (i % 10 == 0) ? 1 : 0;
  }
  
  private static boolean lessThanOrEqualTo(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2) {
    byte b = (paramArrayOfbyte1[paramInt1] <= paramArrayOfbyte2[paramInt2]) ? paramArrayOfbyte1[paramInt1] : paramArrayOfbyte2[paramInt2];
    for (byte b1 = 1; b1 <= b; b1++) {
      if ((byte)(128 + paramArrayOfbyte1[paramInt1 + b1]) < (byte)(128 + paramArrayOfbyte2[paramInt2 + b1]))
        return true; 
      if ((byte)(128 + paramArrayOfbyte1[paramInt1 + b1]) > (byte)(128 + paramArrayOfbyte2[paramInt2 + b1]))
        return false; 
    } 
    return (paramArrayOfbyte1[paramInt1] <= paramArrayOfbyte2[paramInt2]);
  }
  
  public static BigDecimal toBigDecimal(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    if (!isOrdinary(paramArrayOfbyte, paramInt1))
      throw TbError.newSQLException(-590705); 
    String str = getNormalForm(paramArrayOfbyte, paramInt1, paramInt2);
    return new BigDecimal(str);
  }
  
  public static double toDouble(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    String str = getRoughSciForm(paramArrayOfbyte, paramInt1, paramInt2);
    return Double.valueOf(str).doubleValue();
  }
  
  public static double toBinaryDouble(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    double d;
    if (TbCommon.isPositiveInfinityDoubleBytes(paramArrayOfbyte, paramInt1, paramInt2))
      return Double.POSITIVE_INFINITY; 
    if (TbCommon.isNegativeInfinityDoubleBytes(paramArrayOfbyte, paramInt1, paramInt2))
      return Double.NEGATIVE_INFINITY; 
    if (TbCommon.isMaxValueDoubleBytes(paramArrayOfbyte, paramInt1, paramInt2))
      return Double.MAX_VALUE; 
    if (TbCommon.isMinValueDoubleBytes(paramArrayOfbyte, paramInt1, paramInt2))
      return Double.MIN_VALUE; 
    if (TbCommon.isNanDoubleBytes(paramArrayOfbyte, paramInt1, paramInt2))
      return Double.NaN; 
    if (TbCommon.isNegativeZeroDoubleBytes(paramArrayOfbyte, paramInt1, paramInt2))
      return 0.0D; 
    long l = TbCommon.bytes2Long(paramArrayOfbyte, paramInt1, paramInt2);
    if (l < 0L) {
      d = Double.longBitsToDouble(l);
      d *= -1.0D;
    } else if (l > 0L) {
      l ^= 0xFFFFFFFFFFFFFFFFL;
      d = Double.longBitsToDouble(l);
    } else {
      d = Double.longBitsToDouble(l);
    } 
    return d;
  }
  
  public static float toBinaryFloat(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    float f;
    if (TbCommon.isPositiveInfinityFloatBytes(paramArrayOfbyte, paramInt1, paramInt2))
      return Float.POSITIVE_INFINITY; 
    if (TbCommon.isNegativeInfinityFloatBytes(paramArrayOfbyte, paramInt1, paramInt2))
      return Float.NEGATIVE_INFINITY; 
    if (TbCommon.isMaxValueFloatBytes(paramArrayOfbyte, paramInt1, paramInt2))
      return Float.MAX_VALUE; 
    if (TbCommon.isMinValueFloatBytes(paramArrayOfbyte, paramInt1, paramInt2))
      return Float.MIN_VALUE; 
    if (TbCommon.isNanFloatBytes(paramArrayOfbyte, paramInt1, paramInt2))
      return Float.NaN; 
    if (TbCommon.isNegativeZeroFloatBytes(paramArrayOfbyte, paramInt1, paramInt2))
      return 0.0F; 
    int i = TbCommon.bytes2Int(paramArrayOfbyte, paramInt1, paramInt2);
    if (i < 0) {
      f = Float.intBitsToFloat(i);
      f *= -1.0F;
    } else if (i > 0) {
      i ^= 0xFFFFFFFF;
      f = Float.intBitsToFloat(i);
    } else {
      f = Float.intBitsToFloat(i);
    } 
    return f;
  }
  
  public static int toInteger(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    int i;
    int j;
    int k = 0;
    if (paramArrayOfbyte[paramInt1] == 1)
      return 0; 
    if (!isOrdinary(paramArrayOfbyte, paramInt1))
      throw TbError.newSQLException(-590705); 
    if (!lessThanOrEqualTo(MIN_INTEGER, 0, paramArrayOfbyte, paramInt1))
      throw TbError.newSQLException(-90654); 
    if (!lessThanOrEqualTo(paramArrayOfbyte, paramInt1, MAX_INTEGER, 0))
      throw TbError.newSQLException(-90653); 
    boolean bool = isPositive(paramArrayOfbyte[paramInt1 + 1]);
    if (bool) {
      i = getExpPos(paramArrayOfbyte[paramInt1 + 1]);
      j = getSiglen(paramArrayOfbyte, paramInt1);
      j = (j > i + 1) ? (i + 1) : j;
      for (byte b1 = 0; b1 < j; b1++)
        k = k * 100 + getDecoPos(paramArrayOfbyte[paramInt1 + 2 + b1]); 
    } else {
      i = getExpNeg(paramArrayOfbyte[paramInt1 + 1]);
      j = getSiglen(paramArrayOfbyte, paramInt1);
      j = (j > i + 1) ? (i + 1) : j;
      for (byte b1 = 0; b1 < j; b1++)
        k = k * 100 + getDecoNeg(paramArrayOfbyte[paramInt1 + 2 + b1]); 
    } 
    for (byte b = 0; b < i + 1 - j; b++)
      k *= 100; 
    return bool ? k : -k;
  }
  
  public static long toLong(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    int i;
    int j;
    long l = 0L;
    if (paramArrayOfbyte[paramInt1] == 1)
      return 0L; 
    if (!isOrdinary(paramArrayOfbyte, paramInt1))
      throw TbError.newSQLException(-590705); 
    if (!lessThanOrEqualTo(MIN_LONG, 0, paramArrayOfbyte, paramInt1))
      throw TbError.newSQLException(-90654); 
    if (!lessThanOrEqualTo(paramArrayOfbyte, paramInt1, MAX_LONG, 0))
      throw TbError.newSQLException(-90653); 
    boolean bool = isPositive(paramArrayOfbyte[paramInt1 + 1]);
    if (bool) {
      i = getExpPos(paramArrayOfbyte[paramInt1 + 1]);
      j = getSiglen(paramArrayOfbyte, paramInt1);
      j = (j > i + 1) ? (i + 1) : j;
      for (byte b1 = 0; b1 < j; b1++)
        l = l * 100L + getDecoPos(paramArrayOfbyte[paramInt1 + 2 + b1]); 
    } else {
      i = getExpNeg(paramArrayOfbyte[paramInt1 + 1]);
      j = getSiglen(paramArrayOfbyte, paramInt1);
      j = (j > i + 1) ? (i + 1) : j;
      for (byte b1 = 0; b1 < j; b1++)
        l = l * 100L + getDecoNeg(paramArrayOfbyte[paramInt1 + 2 + b1]); 
    } 
    for (byte b = 0; b < i + 1 - j; b++)
      l *= 100L; 
    return bool ? l : -l;
  }
  
  private static byte[] toZero() {
    return new byte[] { 1, Byte.MIN_VALUE };
  }
  
  private static int toZero(byte[] paramArrayOfbyte, int paramInt) {
    paramArrayOfbyte[paramInt] = 1;
    paramArrayOfbyte[paramInt + 1] = Byte.MIN_VALUE;
    return 2;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\TbNumber.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */