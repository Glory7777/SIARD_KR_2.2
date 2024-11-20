package com.tmax.tibero.jdbc.comm;

import com.tmax.tibero.jdbc.ext.TbXAException;
import java.sql.SQLException;
import javax.transaction.xa.Xid;

public interface TbXAComm {
  void xaOpen() throws SQLException;
  
  void xaStart(Xid paramXid, int paramInt) throws TbXAException, SQLException;
  
  void xaEnd(Xid paramXid, int paramInt) throws TbXAException, SQLException;
  
  void xaCommit(Xid paramXid, boolean paramBoolean) throws TbXAException, SQLException;
  
  void xaRollback(Xid paramXid) throws TbXAException, SQLException;
  
  Xid[] xaRecover(int paramInt) throws TbXAException, SQLException;
  
  int xaPrepare(Xid paramXid) throws TbXAException, SQLException;
  
  int xaForget(Xid paramXid) throws TbXAException, SQLException;
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\comm\TbXAComm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */