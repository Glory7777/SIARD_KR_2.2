package com.tmax.tibero.jdbc.data;

import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.util.TbCommon;
import java.sql.SQLException;

public class Row {
  private int columnCount;
  
  private int[] columnOffset;
  
  private int[] columnLength;
  
  private int[] columnLengthQuotient;
  
  private Object[] rowChunk;
  
  public Row() {}
  
  public Row(int paramInt) {
    this.columnCount = paramInt;
    this.columnOffset = new int[paramInt];
    this.columnLength = new int[paramInt];
    this.columnLengthQuotient = new int[paramInt];
    this.rowChunk = new Object[paramInt];
  }
  
  public int buildRowData(byte[] paramArrayOfbyte, int paramInt, Column[] paramArrayOfColumn) {
    int i = paramInt + 3;
    for (byte b = 0; b < this.columnCount; b++) {
      int j = TbCommon.bytes2Int(paramArrayOfbyte, i, 1);
      if (j <= 250) {
        i++;
      } else {
        j = TbCommon.bytes2Int(paramArrayOfbyte, i + 1, 2);
        i += 3;
      } 
      this.rowChunk[b] = paramArrayOfbyte;
      if (j > 0 && paramArrayOfColumn[b].getDataType() == 1) {
        this.columnOffset[b] = i - 1;
        this.columnLength[b] = j + 1;
      } else {
        this.columnOffset[b] = i;
        this.columnLength[b] = j;
      } 
      i += (j < 0) ? 0 : j;
    } 
    return i - paramInt;
  }
  
  public void close() {
    this.columnCount = 0;
    this.columnOffset = null;
    this.columnLength = null;
    this.columnLengthQuotient = null;
    this.rowChunk = null;
  }
  
  public void duplicate(Row paramRow) {
    this.columnCount = paramRow.columnCount;
    if (this.columnLength == null)
      this.columnLength = new int[this.columnCount]; 
    System.arraycopy(paramRow.columnLength, 0, this.columnLength, 0, this.columnCount);
    if (this.columnLengthQuotient == null)
      this.columnLengthQuotient = new int[this.columnCount]; 
    System.arraycopy(paramRow.columnLengthQuotient, 0, this.columnLengthQuotient, 0, this.columnCount);
    if (this.columnOffset == null)
      this.columnOffset = new int[this.columnCount]; 
    System.arraycopy(paramRow.columnOffset, 0, this.columnOffset, 0, this.columnCount);
    if (this.rowChunk == null)
      this.rowChunk = new Object[this.columnCount]; 
    System.arraycopy(paramRow.rowChunk, 0, this.rowChunk, 0, this.columnCount);
  }
  
  public int getColCnt() {
    return this.columnCount;
  }
  
  public int getColumnLength(int paramInt) {
    return this.columnLength[paramInt - 1];
  }
  
  public int getColumnLengthQuotient(int paramInt) {
    return this.columnLengthQuotient[paramInt - 1];
  }
  
  public int getColumnOffset(int paramInt) {
    return this.columnOffset[paramInt - 1];
  }
  
  public byte[] getRawBytes(int paramInt) throws SQLException {
    byte[] arrayOfByte = new byte[this.columnLength[paramInt - 1]];
    Object object = this.rowChunk[paramInt - 1];
    if (object instanceof byte[]) {
      System.arraycopy(object, this.columnOffset[paramInt - 1], arrayOfByte, 0, this.columnLength[paramInt - 1]);
    } else {
      throw TbError.newSQLException(-590705, object.toString());
    } 
    return arrayOfByte;
  }
  
  public Object getRowChunk(int paramInt) {
    return this.rowChunk[paramInt - 1];
  }
  
  public boolean isNull(int paramInt) throws SQLException {
    return (this.columnLength[paramInt - 1] <= 0);
  }
  
  public void setUpdatedColumn(int paramInt1, int paramInt2, Object paramObject) {
    if (this.rowChunk == null)
      this.rowChunk = new Object[this.columnCount]; 
    this.columnOffset[paramInt1 - 1] = 0;
    this.columnLength[paramInt1 - 1] = paramInt2;
    this.rowChunk[paramInt1 - 1] = paramObject;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\Row.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */