package com.tmax.tibero.jdbc.data.binder;

import com.tmax.tibero.jdbc.TbArray;
import com.tmax.tibero.jdbc.TbArrayDescriptor;
import com.tmax.tibero.jdbc.TbTypeDescriptor;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.util.TbCommon;

import java.sql.SQLException;

class ArrayUdtNode extends UdtNode {
    ArrayUdtNode(TbArray paramTbArray) throws SQLException {
        this.UDT = paramTbArray;
        this.desc = (TbTypeDescriptor) paramTbArray.getDescriptor();
        this.attrs = (Object[]) paramTbArray.getArray();
        this.subParams = (ParamContainer) paramTbArray.getSubParams();
        this.isFinal = true;
    }

    public static int[] writeArrayMeta(TbStreamDataWriter paramTbStreamDataWriter, TbArrayDescriptor paramTbArrayDescriptor, int paramInt) throws SQLException {
        byte b;
        int rpLen2 = 0;
        int i = paramTbStreamDataWriter.getBufferedDataSize();
        paramTbStreamDataWriter.writeByte((byte) rpLen2);
        int rpColLen2 = 1;
        paramTbStreamDataWriter.writeByte((byte) -120);
        paramTbStreamDataWriter.writeByte((byte) 1);
        int j = paramTbStreamDataWriter.getBufferedDataSize();
        paramTbStreamDataWriter.writeByte((byte) 0);
        int k;
        for (k = 0; k < 3; k++)
            paramTbStreamDataWriter.writeByte((byte) 0);
        k = paramTbStreamDataWriter.getBufferedDataSize();
        paramTbStreamDataWriter.makeBufferAvailable(5);
        byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
        if (paramInt <= 250) {
            TbCommon.int2Bytes(paramInt, arrayOfByte, k, 1);
            b = 1;
        } else if (paramInt <= 254) {
            arrayOfByte[k] = -2;
            TbCommon.int2Bytes(paramInt, arrayOfByte, k + 1, 2);
            b = 3;
        } else {
            arrayOfByte[k] = -3;
            TbCommon.int2Bytes(paramInt, arrayOfByte, k + 1, 4);
            b = 5;
        }
        paramTbStreamDataWriter.moveOffset(b);
        return new int[]{i, j};
    }

    public ParamContainer getSubParams() {
        return (ParamContainer) ((TbArray) this.UDT).getSubParams();
    }

    public void rewriteRpLen(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {
        boolean bool = true;
        byte b = 1;
        if (this.offset2 < 0 || this.offset3 < 0)
            throw TbError.newSQLException(-90664);
        paramTbStreamDataWriter.makeBufferAvailable(8);
        int j = paramTbStreamDataWriter.getBufferedDataSize() - this.offset2 - b;
        if (j <= 250) {
            paramTbStreamDataWriter.reWriteInt(this.offset3, j, 1);
        } else if (j <= 65535) {
            byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
            System.arraycopy(arrayOfByte, this.offset3 + 1, arrayOfByte, this.offset3 + 3, j);
            paramTbStreamDataWriter.moveOffset(2);
            paramTbStreamDataWriter.reWriteInt(this.offset3, 254, 1);
            paramTbStreamDataWriter.reWriteInt(this.offset3 + 1, j + 2, 2);
        } else {
            byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
            System.arraycopy(arrayOfByte, this.offset3 + 1, arrayOfByte, this.offset3 + 5, j);
            paramTbStreamDataWriter.moveOffset(4);
            paramTbStreamDataWriter.reWriteInt(this.offset3, 253, 1);
            paramTbStreamDataWriter.reWriteInt(this.offset3 + 1, j + 4, 4);
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
            paramTbStreamDataWriter.reWriteInt(this.offset2 + 1, i + 2, 2);
        } else {
            b = 5;
            byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
            System.arraycopy(arrayOfByte, this.offset2 + 1, arrayOfByte, this.offset2 + 5, i);
            paramTbStreamDataWriter.moveOffset(4);
            paramTbStreamDataWriter.reWriteInt(this.offset2, 253, 1);
            paramTbStreamDataWriter.reWriteInt(this.offset2 + 1, i + 4, 4);
        }
    }

    public int getNumOfFields() {
        TbArray tbArray = (TbArray) this.UDT;
        return tbArray.length();
    }
}
