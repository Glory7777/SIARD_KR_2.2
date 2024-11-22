package com.tmax.tibero.jdbc.dpl.binder;

import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.data.StreamBuffer;
import com.tmax.tibero.jdbc.dpl.TbDirPathStream;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

public class DPLReaderBinder extends DPLBinder {
    private final int EXTRA_DATA_LENGTH = 12;

    private final int DPL_CHUNK_SIZE = 1628000;

    private int length;

    private byte[] byteBuf = new byte[16384];

    private int offset;

    private int unReadBytes = 0;

    public void bind(TbConnection paramTbConnection, TbDirPathStream paramTbDirPathStream, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2) throws SQLException {
        DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
        Reader reader = paramTbDirPathStream.getParamReader(paramInt1);
        StreamBuffer streamBuffer = paramTbStreamDataWriter.getStreamBuf();
        int i = paramTbDirPathStream.getDPLMetaData().getDataType(paramInt1 + 1);
        char[] arrayOfChar = new char[4096];
        byte[] arrayOfByte = new byte[16384];
        int readChars = 0;
        int readBufferLength = 0;
        int convertedBytes = 0;
        int writeByteCnt = 0;
        try {
            if (streamBuffer.getRemained() <= 12)
                paramTbDirPathStream.dirPathLoadStream(paramTbStreamDataWriter, 0);
            paramTbStreamDataWriter.writeInt(0, 4);
            while (paramInt2 >= 0) {
                readChars = (paramInt2 > 4096) ? 4096 : paramInt2;
                readBufferLength = reader.read(arrayOfChar, 0, readChars);
                if (readBufferLength <= 0) {
                    if (writeByteCnt > 0) {
                        paramTbStreamDataWriter.reWriteInt(streamBuffer.getCurDataSize() - writeByteCnt - 4, writeByteCnt, 4);
                        paramTbStreamDataWriter.writePaddingDPL(writeByteCnt);
                    }
                    return;
                }
                if (i == 13 || i == 20) {
                    convertedBytes = dataTypeConverter.charsToFixedBytes(arrayOfChar, 0, readBufferLength, arrayOfByte, 0, arrayOfByte.length);
                } else {
                    convertedBytes = dataTypeConverter.charsToBytes(arrayOfChar, 0, readBufferLength, arrayOfByte, 0, arrayOfByte.length);
                }
                int n = 0;
                int i1 = 0;
                int i2 = 0;
                while (true) {
                    i1 = streamBuffer.getRemained();
                    i2 = convertedBytes - n;
                    if (i2 > i1 - 12 && i1 > 12) {
                        i1 -= 12;
                        paramTbStreamDataWriter.writeBytes(arrayOfByte, n, i1);
                        writeByteCnt += i1;
                        n += i1;
                        paramTbStreamDataWriter.reWriteInt(streamBuffer.getCurDataSize() - writeByteCnt - 4, writeByteCnt, 4);
                        paramTbStreamDataWriter.writePaddingDPL(writeByteCnt);
                        paramTbDirPathStream.dirPathLoadStream(paramTbStreamDataWriter, 1);
                        writeByteCnt = 0;
                        paramTbStreamDataWriter.writeInt(0, 4);
                        continue;
                    }
                    if (i2 >= 1628000 - writeByteCnt - 12) {
                        i2 = 1628000 - writeByteCnt - 12;
                        paramTbStreamDataWriter.writeBytes(arrayOfByte, n, i2);
                        writeByteCnt += i2;
                        n += i2;
                        paramTbStreamDataWriter.reWriteInt(streamBuffer.getCurDataSize() - writeByteCnt - 4, writeByteCnt, 4);
                        paramTbStreamDataWriter.writePaddingDPL(writeByteCnt);
                        paramTbDirPathStream.dirPathLoadStream(paramTbStreamDataWriter, 1);
                        writeByteCnt = 0;
                        paramTbStreamDataWriter.writeInt(0, 4);
                        continue;
                    }
                    paramTbStreamDataWriter.writeBytes(arrayOfByte, n, i2);
                    writeByteCnt += i2;
                    n += i2;
                    break;
                }
                paramInt2 -= readBufferLength;
            }
        } catch (IOException iOException) {
            throw TbError.newSQLException(-90202, iOException.getMessage());
        }
    }

    public int bind(TbConnection paramTbConnection, TbDirPathStream paramTbDirPathStream, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, boolean paramBoolean) throws SQLException {
        DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
        Reader reader = paramTbDirPathStream.getParamReader(paramInt1);
        StreamBuffer streamBuffer = paramTbStreamDataWriter.getStreamBuf();
        int i = paramTbDirPathStream.getDPLMetaData().getDataType(paramInt1 + 1);
        char[] arrayOfChar = new char[4096];
        int readChars = 0;
        int readBufferLength = 0;
        int convertedBytes = 0;
        int writeByteCnt = 0;
        if (!paramBoolean) {
            this.length = paramInt2;
            this.offset = 0;
        }
        try {
            if (streamBuffer.getRemained() <= 12)
                return 3;
            paramTbStreamDataWriter.writeInt(0, 4);
            if (paramBoolean) {
                writeByteCnt = this.unReadBytes;
                paramTbStreamDataWriter.writeBytes(this.byteBuf, this.offset, writeByteCnt);
            }
            while (this.length >= 0) {
                readChars = (this.length > 4096) ? 4096 : this.length;
                readBufferLength = reader.read(arrayOfChar, 0, readChars);
                if (readBufferLength <= 0) {
                    paramTbStreamDataWriter.reWriteInt(streamBuffer.getCurDataSize() - writeByteCnt - 4, writeByteCnt, 4);
                    paramTbStreamDataWriter.writePaddingDPL(writeByteCnt);
                    return 0;
                }
                if (i == 13 || i == 20) {
                    convertedBytes = dataTypeConverter.charsToFixedBytes(arrayOfChar, 0, readBufferLength, this.byteBuf, 0, this.byteBuf.length);
                } else {
                    convertedBytes = dataTypeConverter.charsToBytes(arrayOfChar, 0, readBufferLength, this.byteBuf, 0, this.byteBuf.length);
                }
                int n = streamBuffer.getRemained();
                this.offset = 0;
                this.unReadBytes = convertedBytes;
                if (this.unReadBytes > n - 12) {
                    n -= 12;
                    paramTbStreamDataWriter.writeBytes(this.byteBuf, this.offset, n);
                    writeByteCnt += n;
                    this.offset = n;
                    this.unReadBytes -= n;
                    paramTbStreamDataWriter.reWriteInt(streamBuffer.getCurDataSize() - writeByteCnt - 4, writeByteCnt, 4);
                    paramTbStreamDataWriter.writePaddingDPL(writeByteCnt);
                    this.length -= readBufferLength;
                    return 1;
                }
                paramTbStreamDataWriter.writeBytes(this.byteBuf, this.offset, this.unReadBytes);
                writeByteCnt += this.unReadBytes;
                this.length -= readBufferLength;
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