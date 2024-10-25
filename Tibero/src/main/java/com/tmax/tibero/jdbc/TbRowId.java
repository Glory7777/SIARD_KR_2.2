package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.util.TbCommon;
import java.sql.RowId;
import java.sql.SQLException;

public class TbRowId implements RowId {
  private static final int ROWID_SGMT_BYTE_CNT = 4;
  
  private static final int ROWID_BLOCK_BYTE_CNT = 4;
  
  private static final int ROWID_FILE_BYTE_CNT = 2;
  
  private static final int ROWID_ROW_BYTE_CNT = 2;
  
  private static final byte[] rowid_encoding = new byte[] { 
      65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 
      75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 
      85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 
      101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 
      111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 
      121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 
      56, 57, 43, 47 };
  
  private static final int ROWID_DIVISION_SHIFT = 6;
  
  private static final int ROWID_REMAINDER_BIT = 63;
  
  private static final int ROWID_SGMT_LEN = 6;
  
  private static final int ROWID_FILE_LEN = 3;
  
  private static final int ROWID_BLOCK_LEN = 6;
  
  private static final int ROWID_ROW_LEN = 3;
  
  private static final int ROWID_SGMT_LAST = 5;
  
  private static final int ROWID_FILE_LAST = 8;
  
  private static final int ROWID_BLOCK_LAST = 14;
  
  private static final int ROWID_ROW_LAST = 17;
  
  private static final int ROWID_BYTE_CNT = 12;
  
  private static final long ROWID_SGMT_MAX = 4294967295L;
  
  private static final long ROWID_BLOCK_MAX = 4294967295L;
  
  private static final int ROWID_FILE_MAX = 65535;
  
  private static final int ROWID_ROW_MAX = 65535;
  
  private static final int ROWID_LEN = 18;
  
  private long sgmt;
  
  private long block;
  
  private int file;
  
  private int row;
  
  private byte[] erowid = new byte[12];
  
  private int convSingleRowId(byte paramByte) {
    return (65 <= paramByte && paramByte <= 90) ? (paramByte - 65 + 0) : ((97 <= paramByte && paramByte <= 122) ? (paramByte - 97 + 26) : ((48 <= paramByte && paramByte <= 57) ? (paramByte - 48 + 52) : ((43 == paramByte) ? 62 : ((47 == paramByte) ? 63 : -1))));
  }
  
  public void fromBytes(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) throws SQLException {
    setRowIdFields(0L, 0L, 0, 0);
    if (paramInt1 == 1) {
      this.sgmt = TbCommon.bytes2Long(paramArrayOfbyte, paramInt2, 4);
      paramInt2 += 4;
      this.block = TbCommon.bytes2Long(paramArrayOfbyte, paramInt2, 4);
      paramInt2 += 4;
      this.file = TbCommon.bytes2Int(paramArrayOfbyte, paramInt2, 2);
      paramInt2 += 2;
      this.row = TbCommon.bytes2Int(paramArrayOfbyte, paramInt2, 2);
    } else if (paramInt1 == 0) {
      this.sgmt = TbCommon.bytes2LongR(paramArrayOfbyte, paramInt2, 4);
      paramInt2 += 4;
      this.block = TbCommon.bytes2LongR(paramArrayOfbyte, paramInt2, 4);
      paramInt2 += 4;
      this.file = TbCommon.bytes2IntR(paramArrayOfbyte, paramInt2, 2);
      paramInt2 += 2;
      this.row = TbCommon.bytes2IntR(paramArrayOfbyte, paramInt2, 2);
    } 
    if (paramInt1 == 1) {
      TbCommon.long2Bytes(this.sgmt, this.erowid, 0, 4);
      TbCommon.long2Bytes(this.block, this.erowid, 4, 4);
      TbCommon.int2Bytes(this.file, this.erowid, 8, 2);
      TbCommon.int2Bytes(this.row, this.erowid, 10, 2);
    } else {
      TbCommon.long2BytesR(this.sgmt, this.erowid, 0, 4);
      TbCommon.long2BytesR(this.block, this.erowid, 4, 4);
      TbCommon.int2BytesR(this.file, this.erowid, 8, 2);
      TbCommon.int2BytesR(this.row, this.erowid, 10, 2);
    } 
  }
  
  public int fromString(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, String paramString) throws SQLException {
    byte[] arrayOfByte = paramString.getBytes();
    setRowIdFields(0L, 0L, 0, 0);
    long l = 1L;
    byte b = 5;
    while (b >= 0) {
      int j = convSingleRowId(arrayOfByte[b]);
      if (j < 0)
        throw TbError.newSQLException(-590753, j); 
      if (4294967295L - this.sgmt < l * j)
        throw TbError.newSQLException(-590753, "4294967295 - " + this.sgmt + " < " + l + " * " + j); 
      this.sgmt += l * j;
      j = convSingleRowId(arrayOfByte[9 + b]);
      if (j < 0)
        throw TbError.newSQLException(-590753, j); 
      if (4294967295L - this.block < l * j)
        throw TbError.newSQLException(-590754, "4294967295 - " + this.block + " < " + l + " * " + j); 
      this.block += l * j;
      b--;
      l <<= 6L;
    } 
    int i = 1;
    b = 8;
    while (b > 5) {
      int j = convSingleRowId(arrayOfByte[b]);
      if (j < 0)
        throw TbError.newSQLException(-590753, j); 
      if (65535 - this.file < i * j)
        throw TbError.newSQLException(-590755, "65535 - " + this.file + " < " + i + " * " + j); 
      this.file += i * j;
      j = convSingleRowId(arrayOfByte[9 + b]);
      if (j < 0)
        throw TbError.newSQLException(-590753, j); 
      if (65535 - this.row < i * j)
        throw TbError.newSQLException(-590757, "65535 - " + this.row + " < " + i + " * " + j); 
      this.row += i * j;
      b--;
      i <<= 6;
    } 
    if (paramInt2 == 1) {
      TbCommon.long2Bytes(this.sgmt, paramArrayOfbyte, paramInt1, 4);
      TbCommon.long2Bytes(this.block, paramArrayOfbyte, paramInt1 + 4, 4);
      TbCommon.int2Bytes(this.file, paramArrayOfbyte, paramInt1 + 8, 2);
      TbCommon.int2Bytes(this.row, paramArrayOfbyte, paramInt1 + 10, 2);
    } else {
      TbCommon.long2BytesR(this.sgmt, paramArrayOfbyte, paramInt1, 4);
      TbCommon.long2BytesR(this.block, paramArrayOfbyte, paramInt1 + 4, 4);
      TbCommon.int2BytesR(this.file, paramArrayOfbyte, paramInt1 + 8, 2);
      TbCommon.int2BytesR(this.row, paramArrayOfbyte, paramInt1 + 10, 2);
    } 
    return 12;
  }
  
  public byte[] getBytes() {
    return this.erowid;
  }
  
  private void setRowIdFields(long paramLong1, long paramLong2, int paramInt1, int paramInt2) {
    this.sgmt = paramLong1;
    this.block = paramLong2;
    this.file = paramInt1;
    this.row = paramInt2;
  }
  
  public String toString() {
    byte[] arrayOfByte = new byte[18];
    long l1 = this.sgmt;
    long l2 = this.block;
    int i = this.file;
    int j = this.row;
    byte b;
    for (b = 5; b >= 0; b--) {
      arrayOfByte[b] = rowid_encoding[(int)l1 & 0x3F];
      l1 >>= 6L;
      arrayOfByte[9 + b] = rowid_encoding[(int)l2 & 0x3F];
      l2 >>= 6L;
    } 
    for (b = 8; b > 5; b--) {
      arrayOfByte[b] = rowid_encoding[i & 0x3F];
      i >>= 6;
      arrayOfByte[9 + b] = rowid_encoding[j & 0x3F];
      j >>= 6;
    } 
    return new String(arrayOfByte);
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\TbRowId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */