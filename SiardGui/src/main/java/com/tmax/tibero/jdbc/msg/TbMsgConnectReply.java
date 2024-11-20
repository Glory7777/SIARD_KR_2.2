package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgConnectReply extends TbMsg {
  public int protocolMajor;
  
  public int protocolMinor;
  
  public int charset;
  
  public int svrIsBigendian;
  
  public int svrIsNanobase;
  
  public int tbMajor;
  
  public int tbMinor;
  
  public String tbProductName;
  
  public String tbProductVersion;
  
  public int mthrPid;
  
  public int cps;
  
  public int ncharset;
  
  public int flags;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.protocolMajor = paramTbStreamDataReader.readInt32();
    this.protocolMinor = paramTbStreamDataReader.readInt32();
    this.charset = paramTbStreamDataReader.readInt32();
    this.svrIsBigendian = paramTbStreamDataReader.readInt32();
    this.svrIsNanobase = paramTbStreamDataReader.readInt32();
    this.tbMajor = paramTbStreamDataReader.readInt32();
    this.tbMinor = paramTbStreamDataReader.readInt32();
    int i = paramTbStreamDataReader.readInt32();
    this.tbProductName = paramTbStreamDataReader.readDBDecodedPadString(i);
    int j = paramTbStreamDataReader.readInt32();
    this.tbProductVersion = paramTbStreamDataReader.readDBDecodedPadString(j);
    this.mthrPid = paramTbStreamDataReader.readInt32();
    this.cps = paramTbStreamDataReader.readInt32();
    this.ncharset = paramTbStreamDataReader.readInt32();
    this.flags = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbMsgConnectReply.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */