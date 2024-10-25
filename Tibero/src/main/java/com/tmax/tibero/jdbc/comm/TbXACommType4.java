package com.tmax.tibero.jdbc.comm;

import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.ext.TbXAException;
import com.tmax.tibero.jdbc.ext.TbXid;
import com.tmax.tibero.jdbc.msg.TbMsgSend;
import com.tmax.tibero.jdbc.msg.TbMsgXaRecoverReply;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import com.tmax.tibero.jdbc.util.TbCommon;
import java.sql.SQLException;
import javax.transaction.xa.Xid;

public class TbXACommType4 extends TbCommType4 implements TbXAComm {
  public TbXACommType4(TbConnection paramTbConnection) {
    super(paramTbConnection);
  }
  
  private void deserializeXid(byte[] paramArrayOfbyte, TbXid[] paramArrayOfTbXid) throws TbXAException {
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    byte[] arrayOfByte1 = null;
    byte[] arrayOfByte2 = null;
    int n = paramArrayOfTbXid.length;
    for (byte b = 0; b < n; b++) {
      j = TbCommon.bytes2Int(paramArrayOfbyte, i, 8);
      i += true;
      k = TbCommon.bytes2Int(paramArrayOfbyte, i, 8);
      arrayOfByte1 = new byte[k];
      i += true;
      m = TbCommon.bytes2Int(paramArrayOfbyte, i, 8);
      arrayOfByte2 = new byte[m];
      i += true;
      System.arraycopy(paramArrayOfbyte, i, arrayOfByte1, 0, k);
      i += k;
      System.arraycopy(paramArrayOfbyte, i, arrayOfByte2, 0, m);
      i += m;
      i += TbXid.SERIALIZED_SIZE - 24 - k - m;
      paramArrayOfTbXid[b] = new TbXid(j, arrayOfByte1, arrayOfByte2);
    } 
  }
  
  private void doXAErrorReply(int paramInt, TbMsg paramTbMsg) throws TbXAException, SQLException {
    SQLException sQLException = getErrorMessage(paramInt, paramTbMsg);
    throw processException(sQLException);
  }
  
  private int doXAForgetErrorReply(TbMsg paramTbMsg) throws TbXAException {
    int i = -3;
    try {
      SQLException sQLException = getErrorMessage(-90542, paramTbMsg);
      i = mappingXAErrorCode(sQLException.getErrorCode());
      if (i != 3 && i != -4)
        throw new TbXAException(i, sQLException.getMessage()); 
    } catch (SQLException sQLException) {
      processException(sQLException);
    } 
    return i;
  }
  
  private int doXAPrepareErrorReply(TbMsg paramTbMsg) throws TbXAException {
    int i = -3;
    try {
      SQLException sQLException = getErrorMessage(-90529, paramTbMsg);
      i = mappingXAErrorCode(sQLException.getErrorCode());
      if (i != 3 && i != -4)
        throw new TbXAException(i, sQLException.getMessage()); 
    } catch (SQLException sQLException) {
      processException(sQLException);
    } 
    return i;
  }
  
  private Xid[] doXARecoverOK(TbMsgXaRecoverReply paramTbMsgXaRecoverReply) throws TbXAException {
    try {
      int i = paramTbMsgXaRecoverReply.xidCount;
      TbXid[] arrayOfTbXid = new TbXid[i];
      byte[] arrayOfByte = paramTbMsgXaRecoverReply.xids;
      int j = arrayOfByte.length;
      if (j != i * TbXid.SERIALIZED_SIZE)
        throw TbError.newSQLException(-590730); 
      if (i > 0)
        deserializeXid(arrayOfByte, arrayOfTbXid); 
      return (Xid[])arrayOfTbXid;
    } catch (SQLException sQLException) {
      throw processException(sQLException);
    } 
  }
  
  private int mappingXAErrorCode(int paramInt) {
    switch (paramInt) {
      case -25001:
        return -6;
      case -25005:
        return -5;
      case -25002:
        return -4;
      case -25007:
        return 3;
      case -25008:
        return -8;
      case -25010:
        return 7;
      case -25011:
        return 6;
      case -25012:
        return 5;
      case -25009:
        return -3;
      case -25003:
        return -9;
      case -25004:
        return -2;
      case -25006:
        return 100;
      case -25013:
        return 4;
    } 
    return (paramInt <= -90400 && paramInt > -90500) ? -7 : -3;
  }
  
  private TbXAException processException(SQLException paramSQLException) {
    int i = paramSQLException.getErrorCode();
    TbXAException tbXAException = new TbXAException(mappingXAErrorCode(i), paramSQLException.getMessage());
    tbXAException.initCause(paramSQLException);
    return tbXAException;
  }
  
  private void serializeXid(Xid paramXid, byte[] paramArrayOfbyte) {
    int i = 0;
    int j = paramXid.getFormatId();
    byte[] arrayOfByte1 = paramXid.getGlobalTransactionId();
    byte[] arrayOfByte2 = paramXid.getBranchQualifier();
    int k = arrayOfByte1.length + arrayOfByte2.length;
    TbCommon.long2Bytes(j, paramArrayOfbyte, i, 8);
    i += true;
    TbCommon.long2Bytes(arrayOfByte1.length, paramArrayOfbyte, i, 8);
    i += true;
    TbCommon.long2Bytes(arrayOfByte2.length, paramArrayOfbyte, i, 8);
    i += true;
    System.arraycopy(arrayOfByte1, 0, paramArrayOfbyte, i, arrayOfByte1.length);
    i += arrayOfByte1.length;
    System.arraycopy(arrayOfByte2, 0, paramArrayOfbyte, i, arrayOfByte2.length);
    i += arrayOfByte2.length;
    if (k < TbXid.DATA_SIZE)
      for (byte b = 0; b < TbXid.DATA_SIZE - k; b++)
        paramArrayOfbyte[i] = 0;  
  }
  
  public void xaCommit(Xid paramXid, boolean paramBoolean) throws TbXAException {
    byte[] arrayOfByte = new byte[TbXid.SERIALIZED_SIZE];
    serializeXid(paramXid, arrayOfByte);
    boolean bool = paramBoolean ? true : false;
    synchronized (this.stream) {
      TbMsgSend.XA(this.stream, 4, bool, arrayOfByte, arrayOfByte.length, 0);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 75:
          return;
        case 76:
          doXAErrorReply(-90530, tbMsg);
          return;
      } 
      throw new TbXAException(-6, "Invalid protocol: " + tbMsg.getMsgType());
    } 
  }
  
  public void xaEnd(Xid paramXid, int paramInt) throws TbXAException {
    byte[] arrayOfByte = new byte[TbXid.SERIALIZED_SIZE];
    serializeXid(paramXid, arrayOfByte);
    synchronized (this.stream) {
      TbMsgSend.XA(this.stream, 1, paramInt, arrayOfByte, arrayOfByte.length, 0);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 75:
          return;
        case 76:
          doXAErrorReply(-90528, tbMsg);
          return;
      } 
      throw new TbXAException(-6, "Invalid protocol: " + tbMsg.getMsgType());
    } 
  }
  
  public int xaForget(Xid paramXid) throws TbXAException {
    byte[] arrayOfByte = new byte[TbXid.SERIALIZED_SIZE];
    serializeXid(paramXid, arrayOfByte);
    synchronized (this.stream) {
      TbMsgSend.XA(this.stream, 5, 0, arrayOfByte, arrayOfByte.length, 0);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 75:
          return 0;
        case 76:
          return doXAForgetErrorReply(tbMsg);
      } 
      throw new TbXAException(-6, "Invalid protocol: " + tbMsg.getMsgType());
    } 
  }
  
  public void xaOpen() throws SQLException {
    TbMsg tbMsg = null;
    synchronized (this.stream) {
      TbMsgSend.XA_OPEN(this.stream, 0, 500, 60, 0);
      tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 75:
          return;
        case 76:
          return;
      } 
      throw TbError.newSQLException(-90402, tbMsg.getMsgType());
    } 
  }
  
  public int xaPrepare(Xid paramXid) throws TbXAException {
    byte[] arrayOfByte = new byte[TbXid.SERIALIZED_SIZE];
    serializeXid(paramXid, arrayOfByte);
    synchronized (this.stream) {
      TbMsgSend.XA(this.stream, 3, 0, arrayOfByte, arrayOfByte.length, 0);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 75:
          return 0;
        case 76:
          return doXAPrepareErrorReply(tbMsg);
      } 
      throw new TbXAException(-6, "Invalid protocol: " + tbMsg.getMsgType());
    } 
  }
  
  public Xid[] xaRecover(int paramInt) throws TbXAException {
    synchronized (this.stream) {
      TbMsgSend.XA_RECOVER(this.stream);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 70:
          return doXARecoverOK((TbMsgXaRecoverReply)tbMsg);
        case 76:
          doXAErrorReply(-90532, tbMsg);
          return null;
      } 
      throw new TbXAException(-6, "Invalid protocol: " + tbMsg.getMsgType());
    } 
  }
  
  public void xaRollback(Xid paramXid) throws TbXAException {
    byte[] arrayOfByte = new byte[TbXid.SERIALIZED_SIZE];
    serializeXid(paramXid, arrayOfByte);
    synchronized (this.stream) {
      TbMsgSend.XA(this.stream, 2, 0, arrayOfByte, arrayOfByte.length, 0);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 75:
          return;
        case 76:
          doXAErrorReply(-90531, tbMsg);
          return;
      } 
      throw new TbXAException(-6, "Invalid protocol: " + tbMsg.getMsgType());
    } 
  }
  
  public void xaStart(Xid paramXid, int paramInt) throws TbXAException {
    byte[] arrayOfByte = new byte[TbXid.SERIALIZED_SIZE];
    serializeXid(paramXid, arrayOfByte);
    synchronized (this.stream) {
      TbMsgSend.XA(this.stream, 0, paramInt & 0xFFFEFFFF, arrayOfByte, arrayOfByte.length, paramInt & 0x10000);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 75:
          return;
        case 76:
          doXAErrorReply(-90527, tbMsg);
          return;
      } 
      throw new TbXAException(-6, "Invalid protocol: " + tbMsg.getMsgType());
    } 
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\comm\TbXACommType4.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */