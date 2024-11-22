package com.tmax.tibero.jdbc.ext;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

public abstract class TbXAResource implements XAResource {
  public static final int TMENDRSCAN = 8388608;
  
  public static final int TMFAIL = 536870912;
  
  public static final int TMJOIN = 2097152;
  
  public static final int TMNOFLAGS = 0;
  
  public static final int TMONEPHASE = 1073741824;
  
  public static final int TMRESUME = 134217728;
  
  public static final int TMSTARTRSCAN = 16777216;
  
  public static final int TMSUCCESS = 67108864;
  
  public static final int TMSUSPEND = 33554432;
  
  public static final int XA_RDONLY = 3;
  
  public static final int XA_OK = 0;
  
  public static final int TBRTMSERIALIZABLE = 200200822;
  
  public static final int TBRTMREAADONLY = 19791226;
  
  public static final int TBRTMREADWRITE = 19790731;
  
  public static final int TBRTRANSLOOSE = 65536;
  
  public static final int TBRXATIMEOUT = 300;
  
  public abstract void commit(Xid paramXid, boolean paramBoolean) throws XAException;
  
  public abstract void end(Xid paramXid, int paramInt) throws XAException;
  
  public abstract void forget(Xid paramXid) throws XAException;
  
  public abstract int getTransactionTimeout() throws XAException;
  
  public abstract boolean isSameRM(XAResource paramXAResource) throws XAException;
  
  public abstract int prepare(Xid paramXid) throws XAException;
  
  public abstract Xid[] recover(int paramInt) throws XAException;
  
  public abstract void rollback(Xid paramXid) throws XAException;
  
  public abstract boolean setTransactionTimeout(int paramInt) throws XAException;
  
  public abstract void start(Xid paramXid, int paramInt) throws XAException;
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\ext\TbXAResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */