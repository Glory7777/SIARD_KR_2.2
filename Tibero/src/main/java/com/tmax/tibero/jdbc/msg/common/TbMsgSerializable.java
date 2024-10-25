package com.tmax.tibero.jdbc.msg.common;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import java.sql.SQLException;

public interface TbMsgSerializable {
  void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException;
  
  void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException;
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\common\TbMsgSerializable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */