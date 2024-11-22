package com.tmax.tibero.jdbc.dpl.binder;

import com.tmax.tibero.jdbc.TbDatabaseMetaData;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.StreamBuffer;
import com.tmax.tibero.jdbc.dpl.TbDirPathStream;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class DPLLongStreamBinder extends DPLBinder {
    private int length;

    private byte[] convBuf;

    private int offset = 0;

    private int unReadBytes = 0;

    public void bind(TbConnection paramTbConnection, TbDirPathStream paramTbDirPathStream, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2) throws SQLException {
        InputStream inputStream = paramTbDirPathStream.getParamStream(paramInt1);
        StreamBuffer streamBuffer = paramTbStreamDataWriter.getStreamBuf();
        String str1 = paramTbDirPathStream.getDPLMetaData().getClientCharSet();
        String str2 = ((TbDatabaseMetaData) paramTbConnection.getMetaData()).getServerCharSet();
        byte[] arrayOfByte = new byte[16384];
        int readBufferBytes = 0;
        int readBytes = 0;
        int writeByteCnt = 0;
        int offset = 0;
        int availableBytes = 0;
        int unReadBytes = 0;
        try {
            if (streamBuffer.getRemained() <= 4)
                paramTbDirPathStream.dirPathLoadStream(paramTbStreamDataWriter, 0);
            paramTbStreamDataWriter.writeInt(0, 4);
            while (paramInt2 >= 0) {
                readBytes = (paramInt2 > 16384) ? 16384 : paramInt2;
                readBufferBytes = inputStream.read(arrayOfByte, 0, readBytes);
                if (readBufferBytes <= 0) {
                    if (writeByteCnt > 0) {
                        paramTbStreamDataWriter.reWriteInt(streamBuffer.getCurDataSize() - writeByteCnt - 4, writeByteCnt, 4);
                        paramTbStreamDataWriter.writePaddingDPL(writeByteCnt);
                    }
                    return;
                }
                String str = new String(arrayOfByte, 0, readBufferBytes, str1);
                byte[] arrayOfByte1 = str.getBytes(str2);
                offset = 0;
                while (true) {
                    availableBytes = streamBuffer.getRemained();
                    unReadBytes = arrayOfByte1.length - offset;
                    if (unReadBytes > availableBytes) {
                        paramTbStreamDataWriter.writeBytes(arrayOfByte1, offset, availableBytes);
                        writeByteCnt += availableBytes;
                        offset += availableBytes;
                        paramTbStreamDataWriter.reWriteInt(streamBuffer.getCurDataSize() - writeByteCnt - 4, writeByteCnt, 4);
                        paramTbStreamDataWriter.writePaddingDPL(writeByteCnt);
                        paramTbDirPathStream.dirPathLoadStream(paramTbStreamDataWriter, 1);
                        writeByteCnt = 0;
                        paramTbStreamDataWriter.writeInt(0, 4);
                        continue;
                    }
                    paramTbStreamDataWriter.writeBytes(arrayOfByte1, offset, unReadBytes);
                    writeByteCnt += unReadBytes;
                    offset += unReadBytes;
                    break;
                }
                paramInt2 -= readBufferBytes;
            }
        } catch (IOException iOException) {
            throw TbError.newSQLException(-90202, iOException.getMessage());
        }
    }

    public int bind(TbConnection paramTbConnection, TbDirPathStream paramTbDirPathStream, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, boolean paramBoolean) throws SQLException {
        InputStream inputStream = paramTbDirPathStream.getParamStream(paramInt1);
        StreamBuffer streamBuffer = paramTbStreamDataWriter.getStreamBuf();
        String str1 = paramTbDirPathStream.getDPLMetaData().getClientCharSet();
        String str2 = ((TbDatabaseMetaData) paramTbConnection.getMetaData()).getServerCharSet();
        byte[] arrayOfByte = new byte[16384];
        int readBufferedBytes = 0;
        int readBytes = 0;
        int writeByteCnt = 0;
        if (!paramBoolean) {
            this.length = paramInt2;
            this.offset = 0;
        }
        try {
            if (streamBuffer.getRemained() <= 8)
                return 3;
            paramTbStreamDataWriter.writeInt(0, 4);
            if (paramBoolean) {
                writeByteCnt = this.unReadBytes;
                paramTbStreamDataWriter.writeBytes(this.convBuf, this.offset, writeByteCnt);
            }
            while (this.length >= 0) {
                readBytes = (this.length > 16384) ? 16384 : this.length;
                readBufferedBytes = inputStream.read(arrayOfByte, 0, readBytes);
                if (readBufferedBytes <= 0) {
                    if (writeByteCnt > 0) {
                        paramTbStreamDataWriter.reWriteInt(streamBuffer.getCurDataSize() - writeByteCnt - 4, writeByteCnt, 4);
                        paramTbStreamDataWriter.writePaddingDPL(writeByteCnt);
                    }
                    return 0;
                }
                String str = new String(arrayOfByte, 0, readBufferedBytes, str1);
                this.convBuf = str.getBytes(str2);
                int k = streamBuffer.getRemained();
                this.offset = 0;
                this.unReadBytes = this.convBuf.length;
                if (this.unReadBytes > k - 8) {
                    k -= 8;
                    paramTbStreamDataWriter.writeBytes(this.convBuf, this.offset, k);
                    writeByteCnt += k;
                    this.offset += k;
                    this.unReadBytes -= k;
                    paramTbStreamDataWriter.reWriteInt(streamBuffer.getCurDataSize() - writeByteCnt - 4, writeByteCnt, 4);
                    paramTbStreamDataWriter.writePaddingDPL(writeByteCnt);
                    this.length -= readBufferedBytes;
                    return 1;
                }
                paramTbStreamDataWriter.writeBytes(this.convBuf, this.offset, this.unReadBytes);
                writeByteCnt += this.unReadBytes;
                this.length -= readBufferedBytes;
            }
            paramTbStreamDataWriter.reWriteInt(streamBuffer.getCurDataSize() - writeByteCnt - 4, writeByteCnt, 4);
            paramTbStreamDataWriter.writePaddingDPL(writeByteCnt);
            return 0;
        } catch (IOException iOException) {
            throw TbError.newSQLException(-90202, iOException.getMessage());
        }
    }

    public void init() {
        this.length = 0;
        this.offset = 0;
        this.unReadBytes = 0;
    }
}