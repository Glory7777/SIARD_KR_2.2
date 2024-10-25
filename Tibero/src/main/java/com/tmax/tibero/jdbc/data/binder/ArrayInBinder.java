package com.tmax.tibero.jdbc.data.binder;

import tibero.jdbc.TbArray;
import com.tmax.tibero.jdbc.TbArrayDescriptor;
import com.tmax.tibero.jdbc.TbStruct;
import com.tmax.tibero.jdbc.TbStructDescriptor;
import com.tmax.tibero.jdbc.comm.TbStream;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.BindItem;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.util.TbCommon;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

public class ArrayInBinder extends Binder {
  private byte[] bytesDFR = null;
  
  public boolean isDFR() {
    return (this.bytesDFR != null);
  }
  
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
    BindItem bindItem = paramParamContainer.getBindData().getBindItem(paramInt2);
    TbArrayDescriptor tbArrayDescriptor = (TbArrayDescriptor)bindItem.getTypeDescriptor();
    String str = tbArrayDescriptor.getOID();
    int i = tbArrayDescriptor.getTobjID();
    int j = tbArrayDescriptor.getVersionNo();
    int k = paramTbStreamDataWriter.getBufferedDataSize();
    paramTbStreamDataWriter.makeBufferAvailable(43);
    byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    int n = 40;
    paramTbStreamDataWriter.writeByte((byte)n);
    int i1 = 1;
    int m = dataTypeConverter.fromString(arrayOfByte, k + i1, str);
    paramTbStreamDataWriter.moveOffset(m);
    paramTbStreamDataWriter.writeInt(i, 4);
    paramTbStreamDataWriter.writeInt(j, 4);
    int i2 = 0;
    int i3 = paramTbStreamDataWriter.getBufferedDataSize();
    paramTbStreamDataWriter.writeByte((byte)i2);
    byte b1 = 1;
    paramTbStreamDataWriter.writeByte((byte)-120);
    paramTbStreamDataWriter.writeByte((byte)1);
    int i4 = paramTbStreamDataWriter.getBufferedDataSize();
    int i5 = 0;
    paramTbStreamDataWriter.writeByte((byte)i5);
    for (byte b2 = 0; b2 < 3; b2++)
      paramTbStreamDataWriter.writeByte((byte)0); 
    TbArray tbArray = null;
    tbArray = (TbArray)paramParamContainer.getParamArray(paramInt1, paramInt2);
    paramTbStreamDataWriter.makeBufferAvailable(5);
    arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    int i6 = tbArray.length();
    int i7 = paramTbStreamDataWriter.getBufferedDataSize();
    byte b3 = 0;
    if (i6 <= 250) {
      TbCommon.int2Bytes(i6, arrayOfByte, i7, 1);
      b3 = 1;
    } else if (i6 <= 254) {
      arrayOfByte[i7] = -2;
      TbCommon.int2Bytes(i6, arrayOfByte, i7 + 1, 2);
      b3 = 3;
    } else {
      arrayOfByte[i7] = -3;
      TbCommon.int2Bytes(i6, arrayOfByte, i7 + 1, 4);
      b3 = 5;
    } 
    paramTbStreamDataWriter.moveOffset(b3);
    byte b4 = 0;
    LinkedList<ArrayUdtNode> linkedList = new LinkedList();
    ArrayUdtNode arrayUdtNode1 = new ArrayUdtNode(tbArray);
    linkedList.addFirst(arrayUdtNode1);
    ArrayUdtNode arrayUdtNode2 = arrayUdtNode1;
    if (paramTbConnection.serverInfo.getProtocolMajorVersion() >= 2 && paramTbConnection.serverInfo.getProtocolMinorVersion() >= 16) {
      DataTypeConverter.RPCOL_NULLOBJ = -5;
      DataTypeConverter.RPCOL_5BYTE = -3;
    } else {
      DataTypeConverter.RPCOL_NULLOBJ = -3;
      DataTypeConverter.RPCOL_5BYTE = -5;
    } 
    while (!linkedList.isEmpty()) {
      UdtNode udtNode;
      int i10;
      ParamContainer paramContainer;
      Binder binder;
      Object[] arrayOfObject = arrayUdtNode2.getAttributes();
      if (arrayOfObject == null) {
        if (!b4) {
          paramTbStreamDataWriter.writeByte(DataTypeConverter.RPCOL_NULLOBJ);
        } else {
          paramTbStreamDataWriter.writeByte((byte)-4);
          paramTbStreamDataWriter.writeByte((byte)b4);
        } 
        b4 = 0;
        linkedList.remove(0);
        if (linkedList.isEmpty())
          continue; 
        udtNode = linkedList.getFirst();
        continue;
      } 
      int i8 = udtNode.attrProcessed;
      udtNode.attrProcessed++;
      Object object = arrayOfObject[i8];
      int i9 = 0;
      if (udtNode instanceof ObjUdtNode) {
        i9 = ((TbStructDescriptor)udtNode.getDescriptor()).getAttributeTypes()[i8];
      } else if (udtNode instanceof ArrayUdtNode) {
        i9 = ((TbArrayDescriptor)udtNode.getDescriptor()).getElementType();
      } else {
        throw TbError.newSQLException(-90664);
      } 
      switch (i9) {
        case 28:
        case 32:
          if (object instanceof TbStruct || object instanceof java.sql.SQLData) {
            ObjUdtNode objUdtNode;
            if (object instanceof java.sql.SQLData) {
              TbStruct tbStruct = (TbStruct)TbStruct.toStruct(object, (Connection)paramTbConnection);
              objUdtNode = new ObjUdtNode(tbStruct);
            } else {
              objUdtNode = new ObjUdtNode((TbStruct)object);
            } 
            if (!((TbStructDescriptor)objUdtNode.getDescriptor()).isFinal()) {
              int[] arrayOfInt1 = ObjUdtNode.writeNotFinalObject(paramTbStreamDataWriter, (TbStructDescriptor)objUdtNode.desc);
              linkedList.addFirst(objUdtNode);
              udtNode = objUdtNode;
              udtNode.setOffset(arrayOfInt1);
              continue;
            } 
            int[] arrayOfInt = ObjUdtNode.writeNotFinalObject(paramTbStreamDataWriter, (TbStructDescriptor)objUdtNode.desc);
            linkedList.addFirst(objUdtNode);
            udtNode = objUdtNode;
            udtNode.setOffset(arrayOfInt);
            b4++;
            continue;
          } 
          if (b4 == 0) {
            paramTbStreamDataWriter.writeByte(DataTypeConverter.RPCOL_NULLOBJ);
            break;
          } 
          b4++;
          paramTbStreamDataWriter.writeByte((byte)-4);
          paramTbStreamDataWriter.writeByte((byte)b4);
          b4 = 0;
          break;
        case 29:
        case 30:
          if (object instanceof TbArray) {
            ArrayUdtNode arrayUdtNode = new ArrayUdtNode((TbArray)object);
            int i11 = arrayUdtNode.getNumOfFields();
            int[] arrayOfInt = ArrayUdtNode.writeArrayMeta(paramTbStreamDataWriter, (TbArrayDescriptor)arrayUdtNode.desc, i11);
            linkedList.addFirst(arrayUdtNode);
            udtNode = arrayUdtNode;
            udtNode.setOffset(arrayOfInt);
            continue;
          } 
          if (b4 == 0) {
            paramTbStreamDataWriter.writeByte(DataTypeConverter.RPCOL_NULLOBJ);
            break;
          } 
          b4++;
          paramTbStreamDataWriter.writeByte((byte)-4);
          paramTbStreamDataWriter.writeByte((byte)b4);
          b4 = 0;
          break;
        default:
          i10 = paramTbStreamDataWriter.getBufferedDataSize();
          paramContainer = udtNode.getSubParams();
          binder = paramContainer.getBinder(-1, i8);
          binder.bind(paramTbConnection, paramContainer, paramTbStreamDataWriter, -1, i8, paramContainer.getBindData().getBindItem(i8).getLength(), false);
          arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
          if (paramTbStreamDataWriter.getBufferedDataSize() - i10 == 2 && arrayOfByte[i10] == 0)
            paramTbStreamDataWriter.setCurDataSize(i10 + 1); 
          b4 = 0;
          break;
      } 
      while (udtNode.attrProcessed >= udtNode.getNumOfFields()) {
        int i11 = 0;
        if (udtNode instanceof ObjUdtNode) {
          i11 = ((TbStructDescriptor)udtNode.getDescriptor()).getAttributeTypes()[i8];
        } else if (udtNode instanceof ArrayUdtNode) {
          i11 = ((TbArrayDescriptor)udtNode.getDescriptor()).getElementType();
        } 
        if (linkedList.size() != 1)
          udtNode.rewriteRpLen(paramTbStreamDataWriter); 
        linkedList.remove(0);
        if (linkedList.isEmpty())
          break; 
        udtNode = linkedList.getFirst();
      } 
    } 
    i5 = paramTbStreamDataWriter.getBufferedDataSize() - k - 40 - i1 - b1;
    paramTbStreamDataWriter.makeBufferAvailable(4);
    if (i5 <= 250) {
      paramTbStreamDataWriter.reWriteInt(i4, i5, 1);
    } else if (i5 <= 65535) {
      byte[] arrayOfByte1 = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
      System.arraycopy(arrayOfByte1, i4 + 1, arrayOfByte1, i4 + 3, i5 - 2 - 1);
      paramTbStreamDataWriter.moveOffset(2);
      paramTbStreamDataWriter.reWriteInt(i4, 254, 1);
      paramTbStreamDataWriter.reWriteInt(i4 + 1, i5 + 2, 2);
    } else {
      byte[] arrayOfByte1 = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
      System.arraycopy(arrayOfByte1, i4 + 1, arrayOfByte1, i4 + 5, i5);
      paramTbStreamDataWriter.moveOffset(4);
      paramTbStreamDataWriter.reWriteInt(i4, 253, 1);
      paramTbStreamDataWriter.reWriteInt(i4 + 1, i5 + 4, 4);
    } 
    i2 = paramTbStreamDataWriter.getBufferedDataSize() - k - i1;
    paramTbStreamDataWriter.makeBufferAvailable(4);
    if (i2 <= 250) {
      b1 = 1;
      paramTbStreamDataWriter.reWriteInt(i3, i2, 1);
    } else if (i2 <= 65535) {
      b1 = 3;
      byte[] arrayOfByte1 = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
      i2 += 2;
      System.arraycopy(arrayOfByte1, i3 + 1, arrayOfByte1, i3 + 3, i5 + 3);
      paramTbStreamDataWriter.moveOffset(2);
      paramTbStreamDataWriter.reWriteInt(i3, 254, 1);
      paramTbStreamDataWriter.reWriteInt(i3 + 1, i2, 2);
    } else {
      b1 = 5;
      byte[] arrayOfByte1 = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
      i2 += 4;
      System.arraycopy(arrayOfByte1, i3 + 1, arrayOfByte1, i3 + 5, i2);
      paramTbStreamDataWriter.moveOffset(4);
      paramTbStreamDataWriter.reWriteInt(i3, 253, 1);
      paramTbStreamDataWriter.reWriteInt(i3 + 1, i2, 4);
    } 
    n = i2;
    paramTbStreamDataWriter.makeBufferAvailable(4);
    if (n <= 250) {
      i1 = 1;
      paramTbStreamDataWriter.reWriteInt(k, n, 1);
    } else if (n <= 65535) {
      i1 = 3;
      byte[] arrayOfByte1 = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
      System.arraycopy(arrayOfByte1, k + 1, arrayOfByte1, k + 3, n);
      paramTbStreamDataWriter.moveOffset(2);
      paramTbStreamDataWriter.reWriteInt(k, 254, 1);
      paramTbStreamDataWriter.reWriteInt(k + 1, n, 2);
    } else {
      this.bytesDFR = new byte[n - m - 8 - 5];
      byte[] arrayOfByte1 = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
      System.arraycopy(arrayOfByte1, k + 1 + m + 8 + 5, this.bytesDFR, 0, n - m - 8 - 5);
      paramTbStreamDataWriter.reWriteInt(k, m + 8, 1);
      paramTbStreamDataWriter.getStreamBuf().setCurDataSize(k + m + 8 + 1);
      n = 1;
      i1 = m + 8;
    } 
    paramTbStreamDataWriter.writePadding(n + i1);
  }
  
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) throws SQLException {}
  
  public void bindDFR(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, long paramLong) throws SQLException {
    TbStream tbStream = paramTbConnection.getTbComm().getStream();
    int i = paramTbStreamDataWriter.getBufferedDataSize();
    paramTbStreamDataWriter.setCurDataSize(i);
    paramTbStreamDataWriter.writeInt(this.bytesDFR.length, 4);
    paramTbStreamDataWriter.writeBytes(this.bytesDFR, 0, this.bytesDFR.length);
    paramTbStreamDataWriter.writePadding(this.bytesDFR.length);
    paramTbStreamDataWriter.reWriteInt(4, paramTbStreamDataWriter.getBufferedDataSize() - 16, 4);
    tbStream.flush();
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\binder\ArrayInBinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */