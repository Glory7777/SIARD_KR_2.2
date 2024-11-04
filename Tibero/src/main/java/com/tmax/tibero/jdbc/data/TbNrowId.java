package com.tmax.tibero.jdbc.data;

import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.util.TbCommon;
import java.sql.SQLException;

public class TbNrowId {
  static final int NROWID_BYTE_CNT = 8;
  
  static final int NEROWID_LEN = 11;
  
  static final int NEROWID_VAL_LAST = 10;
  
  static final int NEROWID_REMAINDER_BIT = 63;
  
  static final int NEROWID_DIVISION_SHIFT = 6;
  
  byte[] nrowid = null;
  
  static final byte[] nrowid_encoding = new byte[] { 
      65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 
      75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 
      85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 
      101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 
      111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 
      121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 
      56, 57, 43, 47 };
  
  private TbConnection conn = null;
  
  public TbNrowId(TbConnection paramTbConnection) {
    this.nrowid = new byte[8];
    this.conn = paramTbConnection;
  }
  
  public String toString() {
    long l;
    byte bool1 = 0;
    int bool2 = (this.conn == null) ? 0 : this.conn.getServerInfo().getServerEndian();
    if (bool2 == 1) {
      l = TbCommon.bytes2Long(this.nrowid, bool1, 8);
    } else {
      l = TbCommon.bytes2LongR(this.nrowid, bool1, 8);
    } 
    byte[] arrayOfByte = new byte[11];
    for (byte b = 10; b >= 0; b--) {
      arrayOfByte[b] = nrowid_encoding[(int)l & 0x3F];
      l >>= 6L;
    } 
    return new String(arrayOfByte);
  }
  
  public byte[] getServerBytes(int paramInt, String paramString) throws SQLException {
    int i = 0;
    long l = 0L;
    byte[] arrayOfByte1 = paramString.getBytes();
    byte[] arrayOfByte2 = new byte[8];
    i = 1;
    byte b = 10;
    while (b > 0) {
      int j = makeRowIdToInt(arrayOfByte1[b]);
      if (j < 0)
        throw TbError.newSQLException(-590753, j); 
      if (Long.MAX_VALUE - l < (i * j))
        throw TbError.newSQLException(-590753, "9223372036854775807 - " + l + " < " + i + " * " + j); 
      l += (i * j);
      b--;
      i <<= 6;
    } 
    if (paramInt == 1) {
      TbCommon.long2Bytes(l, arrayOfByte2, 0, 8);
    } else {
      TbCommon.long2BytesR(l, arrayOfByte2, 0, 8);
    } 
    return arrayOfByte2;
  }
  
  public int getServerBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, String paramString) throws SQLException {
    int i = 0;
    long l = 0L;
    byte[] arrayOfByte = paramString.getBytes();
    i = 1;
    byte b = 10;
    while (b > 0) {
      int j = makeRowIdToInt(arrayOfByte[b]);
      if (j < 0)
        throw TbError.newSQLException(-590753, j); 
      if (Long.MAX_VALUE - l < (i * j))
        throw TbError.newSQLException(-590753, "9223372036854775807 - " + l + " < " + i + " * " + j); 
      l += (i * j);
      b--;
      i <<= 6;
    } 
    if (paramInt2 == 1) {
      TbCommon.long2Bytes(l, paramArrayOfbyte, paramInt1, 8);
    } else {
      TbCommon.long2BytesR(l, paramArrayOfbyte, paramInt1, 8);
    } 
    return 8;
  }
  
  private static int makeRowIdToInt(byte paramByte) {
    return (65 <= paramByte && paramByte <= 90) ? (paramByte - 65 + 0) : ((97 <= paramByte && paramByte <= 122) ? (paramByte - 97 + 26) : ((48 <= paramByte && paramByte <= 57) ? (paramByte - 48 + 52) : ((43 == paramByte) ? 62 : ((47 == paramByte) ? 63 : -1))));
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\TbNrowId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */