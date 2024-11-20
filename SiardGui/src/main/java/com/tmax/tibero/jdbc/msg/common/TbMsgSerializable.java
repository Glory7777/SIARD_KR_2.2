package com.tmax.tibero.jdbc.msg.common;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import java.sql.SQLException;

public interface TbMsgSerializable {
  void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException;
  
  void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException;
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\common\TbMsgSerializable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */