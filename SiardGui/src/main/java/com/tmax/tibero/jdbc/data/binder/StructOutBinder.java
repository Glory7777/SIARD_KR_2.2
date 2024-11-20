package com.tmax.tibero.jdbc.data.binder;

import com.tmax.tibero.jdbc.TbStructDescriptor;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.BindItem;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;

public class StructOutBinder extends Binder {
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
    BindItem bindItem = paramParamContainer.getBindData().getBindItem(paramInt2);
    Object object = null;
    TbStructDescriptor tbStructDescriptor = (TbStructDescriptor)bindItem.getTypeDescriptor();
    String str = tbStructDescriptor.getOID();
    int i = tbStructDescriptor.getTobjID();
    int j = tbStructDescriptor.getVersionNo();
    int k = paramTbStreamDataWriter.getBufferedDataSize();
    paramTbStreamDataWriter.makeBufferAvailable(43);
    byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    arrayOfByte[k] = 40;
    byte b = 1;
    int m = dataTypeConverter.fromString(arrayOfByte, k + b, str);
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


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\binder\StructOutBinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */