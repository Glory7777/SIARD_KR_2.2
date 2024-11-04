package com.tmax.tibero.jdbc.ext;

import com.tmax.tibero.jdbc.driver.TbConnection;
import java.sql.SQLException;
import javax.sql.XAConnection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

public class TbXAConnection extends TbPooledConnection implements XAConnection, XAResource {
  private boolean autoCommit = false;
  
  public TbXAConnection(TbConnection paramTbConnection) throws SQLException {
    super(paramTbConnection);
    getPhysicalConnection().setAutoCommit(true);
  }
  
  public void commit(Xid paramXid, boolean paramBoolean) throws XAException {
    if (paramXid == null)
      throw new TbXAException(-5, "Xid is null"); 
    if (getPhysicalConnection().isClosed())
      throw new TbXAException(-7, "Connection is already closed"); 
    getPhysicalConnection().getTbXAComm().xaCommit(paramXid, paramBoolean);
  }
  
  public void end(Xid paramXid, int paramInt) throws XAException {
    if (paramXid == null)
      throw new TbXAException(-5, "Xid is null"); 
    if (getPhysicalConnection().isClosed())
      throw new TbXAException(-7, "Connection is already closed"); 
    try {
      getPhysicalConnection().setTxnMode(0);
      getPhysicalConnection().getTbXAComm().xaEnd(paramXid, paramInt);
    } finally {
      try {
        restoreAutoCommit();
      } catch (SQLException sQLException) {}
    } 
  }
  
  public void forget(Xid paramXid) throws XAException {
    if (paramXid == null)
      throw new TbXAException(-5, "Xid is null"); 
  }
  
  protected TbLogicalConnection getNewLogicalConnection() throws SQLException {
    return new TbLogicalConnection(this, getPhysicalConnection(), true);
  }
  
  public int getTransactionTimeout() throws XAException {
    return -1;
  }
  
  public XAResource getXAResource() {
    return this;
  }
  
  public boolean isSameRM(XAResource paramXAResource) throws XAException {
    TbConnection tbConnection1;
    if (paramXAResource instanceof TbXAConnection) {
      tbConnection1 = ((TbXAConnection)paramXAResource).getPhysicalConnection();
    } else {
      return false;
    } 
    if (tbConnection1 == null || tbConnection1.info == null)
      return false; 
    TbConnection tbConnection2 = getPhysicalConnection();
    String str = tbConnection2.info.getURL();
    return (tbConnection1.equals(tbConnection2) || tbConnection1.info.getURL().equals(str));
  }
  
  public int prepare(Xid paramXid) throws XAException {
    if (paramXid == null)
      throw new TbXAException(-5, "Xid is null"); 
    if (getPhysicalConnection().isClosed())
      throw new TbXAException(-7, "Connection is already closed"); 
    return getPhysicalConnection().getTbXAComm().xaPrepare(paramXid);
  }
  
  public Xid[] recover(int paramInt) throws XAException {
    if ((paramInt & 0x1800000) != paramInt)
      throw new TbXAException(-5, "Invalid flag: " + paramInt); 
    if (getPhysicalConnection().isClosed())
      throw new TbXAException(-7, "Connection is already closed"); 
    return getPhysicalConnection().getTbXAComm().xaRecover(paramInt);
  }
  
  public final void restoreAutoCommit() throws SQLException {
    getPhysicalConnection().setAutoCommit(this.autoCommit);
  }
  
  public void rollback(Xid paramXid) throws XAException {
    if (paramXid == null)
      throw new TbXAException(-5, "Xid is null"); 
    if (getPhysicalConnection().isClosed())
      throw new TbXAException(-7, "Connection is already closed"); 
    getPhysicalConnection().getTbXAComm().xaRollback(paramXid);
  }
  
  private final void saveAutoCommit() throws SQLException {
    this.autoCommit = getPhysicalConnection().getAutoCommit();
  }
  
  public boolean setTransactionTimeout(int paramInt) throws XAException {
    return false;
  }
  
  public void start(Xid paramXid, int paramInt) throws XAException {
    if (paramXid == null)
      throw new TbXAException(-5, "Xid is null"); 
    if (getPhysicalConnection().isClosed())
      throw new TbXAException(-7, "Connection is already closed"); 
    try {
      saveAutoCommit();
      getPhysicalConnection().setAutoCommit(false);
    } catch (SQLException sQLException) {
      throw new TbXAException(sQLException.getMessage());
    } 
    try {
      getPhysicalConnection().getTbXAComm().xaStart(paramXid, paramInt);
      getPhysicalConnection().setTxnMode(2);
    } catch (XAException xAException) {
      try {
        restoreAutoCommit();
      } catch (SQLException sQLException) {}
      throw xAException;
    } catch (Exception exception) {
      try {
        restoreAutoCommit();
      } catch (SQLException sQLException) {}
      throw new TbXAException(exception.getMessage());
    } 
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\ext\TbXAConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */