package com.tmax.tibero.jdbc.data.binder;

import com.tmax.tibero.jdbc.comm.TbStream;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

public class NReaderBinder extends Binder {
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    paramTbStreamDataWriter.writeRpcolData(null, 0);
  }
  
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) throws SQLException {
    paramTbStreamDataWriter.writeRpcolData(null, 0);
  }
  
  public void bindDFR(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, long paramLong) throws SQLException {
    DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
    Reader reader = paramParamContainer.getParamReader(paramInt1, paramInt2);
    TbStream tbStream = paramTbConnection.getTbComm().getStream();
    char[] arrayOfChar = new char[paramTbConnection.getMaxDFRNCharCount()];
    int i = paramTbStreamDataWriter.getBufferedDataSize();
    try {
      while (paramLong > 0L) {
        int k;
        paramTbStreamDataWriter.setCurDataSize(i);
        if (paramLong < arrayOfChar.length) {
          k = reader.read(arrayOfChar, 0, (int)paramLong);
        } else {
          k = reader.read(arrayOfChar, 0, arrayOfChar.length);
        } 
        if (k == -1)
          break; 
        int m = k * dataTypeConverter.getMaxBytesPerNChar();
        paramTbStreamDataWriter.makeBufferAvailable(m + 4);
        byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
        int j = dataTypeConverter.nCharsToBytes(arrayOfChar, 0, k, arrayOfByte, i + 4, m);
        paramTbStreamDataWriter.writeInt(j, 4);
        paramTbStreamDataWriter.moveOffset(j);
        paramTbStreamDataWriter.writePadding(j);
        paramTbStreamDataWriter.reWriteInt(4, paramTbStreamDataWriter.getBufferedDataSize() - 16, 4);
        tbStream.flush();
        paramLong -= k;
      } 
    } catch (IOException iOException) {
      throw TbError.newSQLException(-90202, iOException.getMessage());
    } 
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\binder\NReaderBinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */