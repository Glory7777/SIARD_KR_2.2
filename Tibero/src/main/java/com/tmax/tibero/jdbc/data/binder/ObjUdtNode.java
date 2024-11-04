package com.tmax.tibero.jdbc.data.binder;

import com.tmax.tibero.jdbc.TbStruct;
import com.tmax.tibero.jdbc.TbStructDescriptor;
import com.tmax.tibero.jdbc.TbTypeDescriptor;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;

class ObjUdtNode extends UdtNode {
  ObjUdtNode(TbStruct paramTbStruct) throws SQLException {
    this.UDT = paramTbStruct;
    this.desc = (TbTypeDescriptor)paramTbStruct.getDescriptor();
    this.attrs = paramTbStruct.getAttributes();
    this.subParams = paramTbStruct.getSubParams();
    this.isFinal = paramTbStruct.getDescriptor().isFinal();
  }
  
  public static int[] writeNotFinalObject(TbStreamDataWriter paramTbStreamDataWriter, TbStructDescriptor paramTbStructDescriptor) throws SQLException {
    int bool = 0;
    int i = paramTbStreamDataWriter.getBufferedDataSize();
    paramTbStreamDataWriter.writeByte((byte)bool);
    paramTbStreamDataWriter.writeByte((byte)-124);
    paramTbStreamDataWriter.writeByte((byte)1);
    if (!paramTbStructDescriptor.isFinal())
      paramTbStreamDataWriter.writeBytes(DataTypeConverter.RPCOL_UDT_NOT_FINAL); 
    int j = paramTbStreamDataWriter.getBufferedDataSize();
    paramTbStreamDataWriter.writeByte((byte)0);
    return new int[] { i, j };
  }
  
  public ParamContainer getSubParams() {
    return ((TbStruct)this.UDT).getSubParams();
  }
  
  public int getNumOfFields() {
    return ((TbStruct)this.UDT).getNumOfFields();
  }
  
  public void rewriteRpLen(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {
    byte b = 1;
    if (this.offset2 < 0 || this.offset3 < 0)
      throw TbError.newSQLException(-90664); 
    int j = paramTbStreamDataWriter.getBufferedDataSize() - this.offset2 - b;
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


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\binder\ObjUdtNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */