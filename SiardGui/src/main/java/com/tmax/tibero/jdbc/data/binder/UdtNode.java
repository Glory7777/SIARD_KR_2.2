package com.tmax.tibero.jdbc.data.binder;

import com.tmax.tibero.jdbc.TbArray;
import com.tmax.tibero.jdbc.TbTypeDescriptor;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;

abstract class UdtNode {
  Object UDT;
  
  Object[] attrs;
  
  int attrProcessed = 0;
  
  TbTypeDescriptor desc;
  
  protected int offset2 = -1;
  
  protected int offset3 = -1;
  
  ParamContainer subParams;
  
  boolean isFinal = true;
  
  public boolean isFinal() {
    return this.isFinal;
  }
  
  public Object[] getAttributes() {
    return this.attrs;
  }
  
  public TbTypeDescriptor getDescriptor() {
    return this.desc;
  }
  
  public void setOffset(int[] paramArrayOfint) throws SQLException {
    if (paramArrayOfint.length != 2 || paramArrayOfint[0] >= paramArrayOfint[1])
      throw TbError.newSQLException(-90664); 
    this.offset2 = paramArrayOfint[0];
    this.offset3 = paramArrayOfint[1];
  }
  
  public ParamContainer getSubParams() {
    return (ParamContainer)((TbArray)this.UDT).getSubParams();
  }
  
  public abstract int getNumOfFields();
  
  public void rewriteRpLen(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {
    boolean bool = true;
    byte b = 1;
    if (this.offset2 < 0 || this.offset3 < 0)
      throw TbError.newSQLException(-90664); 
    int j = paramTbStreamDataWriter.getBufferedDataSize() - this.offset2 - 40 - b;
    if (j <= 250) {
      paramTbStreamDataWriter.reWriteInt(this.offset3, j, 1);
    } else if (j <= 65535) {
      byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
      j += 2;
      System.arraycopy(arrayOfByte, this.offset3 + 1, arrayOfByte, this.offset3 + 3, j);
      paramTbStreamDataWriter.moveOffset(2);
      paramTbStreamDataWriter.reWriteInt(this.offset3, 254, 1);
      paramTbStreamDataWriter.reWriteInt(this.offset3 + 1, j, 2);
    } else {
      byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
      j += 4;
      System.arraycopy(arrayOfByte, this.offset3 + 1, arrayOfByte, this.offset3 + 5, j);
      paramTbStreamDataWriter.moveOffset(4);
      paramTbStreamDataWriter.reWriteInt(this.offset3, 253, 1);
      paramTbStreamDataWriter.reWriteInt(this.offset3 + 1, j, 4);
    } 
    int i = j;
    if (i <= 250) {
      b = 1;
      paramTbStreamDataWriter.reWriteInt(this.offset2, i, 1);
    } else if (i <= 65535) {
      b = 3;
      byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
      System.arraycopy(arrayOfByte, this.offset2 + 1, arrayOfByte, this.offset2 + 3, i);
      paramTbStreamDataWriter.moveOffset(2);
      paramTbStreamDataWriter.reWriteInt(this.offset2, 254, 1);
      paramTbStreamDataWriter.reWriteInt(this.offset2 + 1, i, 2);
    } else {
      b = 5;
      byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
      System.arraycopy(arrayOfByte, this.offset2 + 1, arrayOfByte, this.offset2 + 5, i);
      paramTbStreamDataWriter.moveOffset(4);
      paramTbStreamDataWriter.reWriteInt(this.offset2, 253, 1);
      paramTbStreamDataWriter.reWriteInt(this.offset2 + 1, i, 4);
    } 
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\binder\UdtNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */