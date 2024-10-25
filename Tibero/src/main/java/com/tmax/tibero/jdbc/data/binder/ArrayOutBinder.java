package com.tmax.tibero.jdbc.data.binder;

import com.tmax.tibero.jdbc.TbArrayDescriptor;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;

public class ArrayOutBinder extends Binder {
  public final int COLLECTION_META_LENGTH = 40;
  
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
    TbArrayDescriptor tbArrayDescriptor = (TbArrayDescriptor)paramParamContainer.getBindData().getBindItem(paramInt2).getTypeDescriptor();
    String str = tbArrayDescriptor.getOID();
    int i = tbArrayDescriptor.getTobjID();
    int j = tbArrayDescriptor.getVersionNo();
    int k = paramTbStreamDataWriter.getBufferedDataSize();
    paramTbStreamDataWriter.makeBufferAvailable(43);
    byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    arrayOfByte[k] = 40;
    byte b = 1;
    int m = dataTypeConverter.fromString(arrayOfByte, k + 1, str);
    paramTbStreamDataWriter.moveOffset(m + b);
    paramTbStreamDataWriter.writeInt(i, 4);
    paramTbStreamDataWriter.writeInt(j, 4);
    paramTbStreamDataWriter.writePadding(40 + b);
  }
  
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) throws SQLException {
    throw TbError.newSQLException(-590705);
  }
  
  public void bindDFR(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, long paramLong) throws SQLException {
    throw TbError.newSQLException(-590705);
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\binder\ArrayOutBinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */