package com.tmax.tibero.jdbc.comm;

import com.tmax.tibero.DriverConstants;
import com.tmax.tibero.jdbc.TbBlob;
import com.tmax.tibero.jdbc.TbClobBase;
import com.tmax.tibero.jdbc.TbLob;
import com.tmax.tibero.jdbc.TbResultSet;
import com.tmax.tibero.jdbc.TbSQLInfo;
import com.tmax.tibero.jdbc.TbSQLInfo2;
import com.tmax.tibero.jdbc.data.BatchInfo;
import com.tmax.tibero.jdbc.data.BatchUpdateInfo;
import com.tmax.tibero.jdbc.data.BindData;
import com.tmax.tibero.jdbc.data.BindItem;
import com.tmax.tibero.jdbc.data.ConnectionInfo;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.data.NodeInfo;
import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.data.ServerInfo;
import com.tmax.tibero.jdbc.data.ZoneInfo;
import com.tmax.tibero.jdbc.data.binder.ArrayInBinder;
import com.tmax.tibero.jdbc.data.binder.Binder;
import com.tmax.tibero.jdbc.data.charset.Charset;
import com.tmax.tibero.jdbc.data.charset.CharsetMetaData;
import com.tmax.tibero.jdbc.dpl.TbDirPathMetaData;
import com.tmax.tibero.jdbc.dpl.TbDirPathStream;
import com.tmax.tibero.jdbc.driver.TbCallableStatementImpl;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.driver.TbPreparedStatementImpl;
import com.tmax.tibero.jdbc.driver.TbResultSetBase;
import com.tmax.tibero.jdbc.driver.TbSavepoint;
import com.tmax.tibero.jdbc.driver.TbStatement;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.msg.TbClntInfoParam;
import com.tmax.tibero.jdbc.msg.TbColNameList;
import com.tmax.tibero.jdbc.msg.TbColumnDesc;
import com.tmax.tibero.jdbc.msg.TbMsgBatchUpdateReply;
import com.tmax.tibero.jdbc.msg.TbMsgConnectReply;
import com.tmax.tibero.jdbc.msg.TbMsgDplLoadStreamReply;
import com.tmax.tibero.jdbc.msg.TbMsgDplPrepareReply;
import com.tmax.tibero.jdbc.msg.TbMsgEreply;
import com.tmax.tibero.jdbc.msg.TbMsgExecuteCallReply;
import com.tmax.tibero.jdbc.msg.TbMsgExecuteCountReply;
import com.tmax.tibero.jdbc.msg.TbMsgExecuteNeedDataReply;
import com.tmax.tibero.jdbc.msg.TbMsgExecutePivotReply;
import com.tmax.tibero.jdbc.msg.TbMsgExecutePrefetchNoDescReply;
import com.tmax.tibero.jdbc.msg.TbMsgExecutePrefetchReply;
import com.tmax.tibero.jdbc.msg.TbMsgExecutePsmPrefetchReply;
import com.tmax.tibero.jdbc.msg.TbMsgExecutePsmReply;
import com.tmax.tibero.jdbc.msg.TbMsgExecuteRsetNoDescReply;
import com.tmax.tibero.jdbc.msg.TbMsgExecuteRsetReply;
import com.tmax.tibero.jdbc.msg.TbMsgFetchPivotReply;
import com.tmax.tibero.jdbc.msg.TbMsgFetchReply;
import com.tmax.tibero.jdbc.msg.TbMsgGetLastExecutedSqlinfo2Reply;
import com.tmax.tibero.jdbc.msg.TbMsgGetLastExecutedSqlinfoReply;
import com.tmax.tibero.jdbc.msg.TbMsgLobCloseReply;
import com.tmax.tibero.jdbc.msg.TbMsgLobCreateTempReply;
import com.tmax.tibero.jdbc.msg.TbMsgLobInlobReply;
import com.tmax.tibero.jdbc.msg.TbMsgLobInstrReply;
import com.tmax.tibero.jdbc.msg.TbMsgLobLengthReply;
import com.tmax.tibero.jdbc.msg.TbMsgLobOpenReply;
import com.tmax.tibero.jdbc.msg.TbMsgLobReadReply;
import com.tmax.tibero.jdbc.msg.TbMsgLobTruncReply;
import com.tmax.tibero.jdbc.msg.TbMsgLobWriteReply;
import com.tmax.tibero.jdbc.msg.TbMsgLongReadReply;
import com.tmax.tibero.jdbc.msg.TbMsgOkReply;
import com.tmax.tibero.jdbc.msg.TbMsgPrepareReply;
import com.tmax.tibero.jdbc.msg.TbMsgSend;
import com.tmax.tibero.jdbc.msg.TbMsgSessInfoReply;
import com.tmax.tibero.jdbc.msg.TbMsgSesskeyReply;
import com.tmax.tibero.jdbc.msg.TbNlsParam;
import com.tmax.tibero.jdbc.msg.TbOutParam;
import com.tmax.tibero.jdbc.msg.TbPivotInfo;
import com.tmax.tibero.jdbc.msg.TbPvValType;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import com.tmax.tibero.jdbc.util.TbCommon;
import com.tmax.tibero.jdbc.util.TbSQLTypeScanner;
import java.io.ByteArrayOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.sql.BatchUpdateException;
import java.sql.ClientInfoStatus;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Vector;
import javax.crypto.Cipher;

public class TbCommType4 implements TbComm, TbClobAccessor, TbBlobAccessor {
  public TbConnection conn = null;
  
  public TbStream stream = null;
  
  public DataTypeConverter typeConverter = null;
  
  private Cipher rsa = null;
  
  public TbCommType4() {}
  
  public TbCommType4(TbConnection paramTbConnection) {
    this.conn = paramTbConnection;
    this.typeConverter = paramTbConnection.getTypeConverter();
  }
  
  private int[] batchUpdate(TbPreparedStatementImpl paramTbPreparedStatementImpl, BatchUpdateInfo paramBatchUpdateInfo, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    byte b = 0;
    int i = paramTbPreparedStatementImpl.getParameterCnt();
    int j = paramInt2 - paramInt1;
    boolean bool = true;
    byte[][] arrayOfByte = paramTbPreparedStatementImpl.getParamTypes();
    Binder[][] arrayOfBinder = paramTbPreparedStatementImpl.getBinder();
    BatchUpdateException batchUpdateException = null;
    synchronized (this.stream) {
      try {
        synchronized (this.stream.getWriteStreamBuffer()) {
          TbStreamDataWriter tbStreamDataWriter = this.stream.getMsgWriter();
          this.stream.startWritingPacketData();
          tbStreamDataWriter.writeInt(31, 4);
          tbStreamDataWriter.writeInt(0, 4);
          tbStreamDataWriter.writeLong(0L, 8);
          tbStreamDataWriter.writeLenAndDBEncodedPadString(paramTbPreparedStatementImpl.getOriginalSql());
          int k = tbStreamDataWriter.getBufferedDataSize();
          tbStreamDataWriter.makeBufferAvailable(20);
          tbStreamDataWriter.moveOffset(20);
          int m = tbStreamDataWriter.getBufferedDataSize();
          for (int n = paramInt1; n < paramInt2; n++) {
            BindData bindData = paramBatchUpdateInfo.get(n).getBindData();
            for (byte b1 = 0; b1 < (arrayOfBinder[n]).length; b1++) {
              int i1 = 1;
              i1 |= arrayOfByte[n][b1] << 8 & 0xFFFFFF00;
              tbStreamDataWriter.writeInt(i1, 4);
              arrayOfBinder[n][b1].bind(this.conn, (ParamContainer)paramTbPreparedStatementImpl, tbStreamDataWriter, n, b1, bindData.getBindItem(b1).getLength());
            } 
            b++;
            if (tbStreamDataWriter.getBufferedDataSize() > DriverConstants.BATCH_SEND_SIZE) {
              batchUpdateFlush(paramTbPreparedStatementImpl, k, j, b, paramInt3, bool);
              bool = false;
              b = 0;
              tbStreamDataWriter.setCurDataSize(m);
            } 
          } 
          if (bool || tbStreamDataWriter.getBufferedDataSize() - m > 0)
            batchUpdateFlush(paramTbPreparedStatementImpl, k, j, b, paramInt3, bool); 
        } 
      } catch (SQLException sQLException) {
        int k = sQLException.getErrorCode();
        if (k <= -90400 && k > -90500)
          throw sQLException; 
        batchUpdateException = new BatchUpdateException(sQLException.getMessage(), sQLException.getSQLState(), sQLException.getErrorCode(), new int[0]);
        if (bool)
          throw batchUpdateException; 
        cancelStatement();
      } catch (Exception exception) {
        SQLException sQLException = TbError.newSQLException(-90651, exception);
        batchUpdateException = new BatchUpdateException(sQLException.getMessage(), sQLException.getSQLState(), sQLException.getErrorCode(), new int[0]);
        if (bool)
          throw batchUpdateException; 
        cancelStatement();
      } 
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 32:
          return batchUpdateReply((TbMsgBatchUpdateReply)tbMsg, j, i);
        case 76:
          if (batchUpdateException == null) {
            throwEreply(-90515, tbMsg);
            break;
          } 
          throw batchUpdateException;
      } 
      throwProtocolError(tbMsg.getMsgType());
    } 
    return null;
  }
  
  private void batchUpdateFlush(TbPreparedStatementImpl paramTbPreparedStatementImpl, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) throws SQLException {
    int i = paramTbPreparedStatementImpl.getParameterCnt();
    TbStreamDataWriter tbStreamDataWriter = this.stream.getMsgWriter();
    tbStreamDataWriter.reWriteInt(paramInt1, paramInt2, 4);
    tbStreamDataWriter.reWriteInt(paramInt1 + 4, paramInt3, 4);
    tbStreamDataWriter.reWriteInt(paramInt1 + 8, i, 4);
    if (this.conn.getAutoCommit())
      paramInt4 |= 0x1; 
    if (!paramBoolean)
      paramInt4 |= 0x1000; 
    tbStreamDataWriter.reWriteInt(paramInt1 + 12, paramInt4, 4);
    tbStreamDataWriter.reWriteInt(paramInt1 + 16, paramInt3 * i, 4);
    tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
    this.stream.flush();
  }
  
  public int[] batchUpdateLoop(TbPreparedStatementImpl paramTbPreparedStatementImpl, BatchUpdateInfo paramBatchUpdateInfo) throws SQLException {
    int i = paramTbPreparedStatementImpl.getBatchFlag();
    int j = paramBatchUpdateInfo.getDeferredRowCount();
    int k = paramTbPreparedStatementImpl.getBatchRowCount();
    int m = 0;
    int n = 0;
    int[] arrayOfInt = new int[k];
    try {
      if (j == 0)
        return batchUpdate(paramTbPreparedStatementImpl, paramBatchUpdateInfo, 0, k, i); 
      for (byte b = 0; b < j; b++) {
        int i1 = paramBatchUpdateInfo.getDeferredRowIndex(b);
        BatchInfo batchInfo = paramBatchUpdateInfo.get(i1);
        if (m < i1) {
          int[] arrayOfInt1 = batchUpdate(paramTbPreparedStatementImpl, paramBatchUpdateInfo, m, i1, i);
          System.arraycopy(arrayOfInt1, 0, arrayOfInt, n, arrayOfInt1.length);
          n += arrayOfInt1.length;
          m = i1;
        } 
        paramTbPreparedStatementImpl.setBindData(batchInfo.getBindData());
        arrayOfInt[n] = prepareExecute(paramTbPreparedStatementImpl, paramTbPreparedStatementImpl.getOriginalSql(), batchInfo.getCurrentRowIndex());
        n++;
        m++;
      } 
      if (m < k) {
        int[] arrayOfInt1 = batchUpdate(paramTbPreparedStatementImpl, paramBatchUpdateInfo, m, k, i);
        System.arraycopy(arrayOfInt1, 0, arrayOfInt, n, arrayOfInt1.length);
      } 
      return arrayOfInt;
    } catch (BatchUpdateException batchUpdateException) {
      int i1 = (batchUpdateException.getUpdateCounts()).length;
      int[] arrayOfInt1 = new int[n + i1];
      System.arraycopy(arrayOfInt, 0, arrayOfInt1, 0, n);
      System.arraycopy(batchUpdateException.getUpdateCounts(), 0, arrayOfInt1, n, i1);
      throw new BatchUpdateException(batchUpdateException.getMessage(), batchUpdateException.getSQLState(), batchUpdateException.getErrorCode(), arrayOfInt1, batchUpdateException.getCause());
    } catch (SQLException sQLException) {
      int i1 = sQLException.getErrorCode();
      if (i1 <= -90400 && i1 > -90500)
        throw sQLException; 
      int[] arrayOfInt1 = new int[n];
      System.arraycopy(arrayOfInt, 0, arrayOfInt1, 0, n);
      throw new BatchUpdateException(sQLException.getMessage(), sQLException.getSQLState(), sQLException.getErrorCode(), arrayOfInt1, sQLException);
    } 
  }
  
  private int[] batchUpdateReply(TbMsgBatchUpdateReply paramTbMsgBatchUpdateReply, int paramInt1, int paramInt2) throws SQLException {
    int[] arrayOfInt = new int[paramTbMsgBatchUpdateReply.executedCnt];
    if (paramTbMsgBatchUpdateReply.affectedCnt == null) {
      for (byte b = 0; b < paramTbMsgBatchUpdateReply.executedCnt; b++)
        arrayOfInt[b] = 1; 
    } else {
      for (byte b = 0; b < paramTbMsgBatchUpdateReply.executedCnt; b++)
        arrayOfInt[b] = (paramTbMsgBatchUpdateReply.affectedCnt[b]).cnt; 
    } 
    if (paramInt2 > 0 && paramInt1 != paramTbMsgBatchUpdateReply.executedCnt) {
      SQLException sQLException = paramTbMsgBatchUpdateReply.getException(-90515);
      throw new BatchUpdateException(sQLException.getMessage(), sQLException.getSQLState(), sQLException.getErrorCode(), arrayOfInt, sQLException);
    } 
    return arrayOfInt;
  }
  
  public void cancel() throws SQLException {
    synchronized (this.stream) {
      TbMsgSend.CANCEL(this.stream);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 75:
          justOKReply((TbMsgOkReply)tbMsg);
          break;
        case 76:
          throwEreply(-90526, tbMsg);
          break;
        default:
          throwProtocolError(tbMsg.getMsgType());
          break;
      } 
    } 
  }
  
  public void cancelStatement() throws SQLException {
    TbMsgSend.STMTCANCEL(this.stream, this.conn.getSessionId(), this.conn.getSerialNo(), 0);
  }
  
  public void close() throws SQLException {
    reset();
  }

  @Override
  public void closeCursor(com.tmax.tibero.jdbc.driver.TbResultSet paramTbResultSet, int paramInt) throws SQLException {

  }

  public boolean close(TbLob paramTbLob) throws SQLException {
    return lobClose(paramTbLob);
  }
  
  public void closeCursor(TbResultSet paramTbResultSet, int paramInt) throws SQLException {
    synchronized (this.stream) {
      TbMsgSend.CLOSE_CSR(this.stream, paramInt);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 75:
          justOKReply((TbMsgOkReply)tbMsg);
          break;
        case 76:
          throwEreply(-90507, tbMsg);
          break;
        default:
          throwProtocolError(tbMsg.getMsgType());
          break;
      } 
    } 
  }
  
  public void closeSession() throws SQLException {
    int bool = this.conn.getAutoCommit() ? 1 : 0;
    synchronized (this.stream) {
      TbMsgSend.CLOSE_SESS(this.stream, bool);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 75:
          justOKReply((TbMsgOkReply)tbMsg);
          break;
        case 76:
          throwEreply(-90503, tbMsg);
          break;
        default:
          throwProtocolError(tbMsg.getMsgType());
          break;
      } 
    } 
  }
  
  public void commit() throws SQLException {
    synchronized (this.stream) {
      TbMsgSend.COMMIT(this.stream);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 75:
          justOKReply((TbMsgOkReply)tbMsg);
          break;
        case 76:
          throwEreply(-90510, tbMsg);
          break;
        default:
          throwProtocolError(tbMsg.getMsgType());
          break;
      } 
    } 
  }
  
  public void createStream() throws SQLException {
    String str = null;
    if (this.conn.info.isInternal()) {
      Socket socket = TbSocketRegistry.getSocket();
      if (socket == null)
        throw TbError.newSQLException(-590720); 
      str = "server internally";
      if (this.stream != null) {
        synchronized (this.stream) {
          this.stream = new TbStream(this.conn, socket, this.typeConverter, this.conn.info);
        } 
      } else {
        this.stream = new TbStream(this.conn, socket, this.typeConverter, this.conn.info);
      } 
    } else {
      NodeInfo nodeInfo = this.conn.info.getClusterNode();
      if (nodeInfo == null)
        throw TbError.newSQLException(-590721); 
      while (true) {
        str = nodeInfo.getAddress() + ":" + nodeInfo.getPort();
        if (this.stream != null)
          synchronized (this.stream) {
            this.stream = new TbStream(this.conn, nodeInfo.getAddress(), nodeInfo.getPort(), this.typeConverter, this.conn.info);
            break;
          }  
        try {
          this.stream = new TbStream(this.conn, nodeInfo.getAddress(), nodeInfo.getPort(), this.typeConverter, this.conn.info);
          break;
        } catch (SQLException sQLException) {
          this.stream = null;
          nodeInfo = this.conn.info.getSecondaryNode();
          if (nodeInfo == null)
            throw sQLException; 
        } 
      } 
      nodeInfo = null;
    } 
  }
  
  public byte[] createTemporaryBlob() throws SQLException {
    return lobCreateTemporary(12);
  }
  
  public byte[] createTemporaryClob() throws SQLException {
    return lobCreateTemporary(13);
  }
  
  public byte[] createTemporaryNClob() throws SQLException {
    return lobCreateTemporary(20);
  }
  
  public void describeConnectInfo() throws SQLException {
    synchronized (this.stream) {
      TbMsgSend.DESCRIBE_CONNECT_INFO(this.stream);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 0:
          logonConnectReply((TbMsgConnectReply)tbMsg, false);
          break;
        case 76:
          throwEreply(-90502, tbMsg);
          break;
        default:
          throwProtocolError(tbMsg.getMsgType());
          break;
      } 
    } 
  }
  
  public void describeSessInfo() throws SQLException {
    synchronized (this.stream) {
      TbMsgSend.DESCRIBE_SESS_INFO(this.stream);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 2:
          logonSessInfoReply((TbMsgSessInfoReply)tbMsg);
          break;
        case 76:
          throwEreply(-90502, tbMsg);
          break;
        default:
          throwProtocolError(tbMsg.getMsgType());
          break;
      } 
    } 
  }
  
  public void dirPathAbort() throws SQLException {
    synchronized (this.stream) {
      TbMsgSend.DPL_ABORT(this.stream);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 75:
          justOKReply((TbMsgOkReply)tbMsg);
          break;
        case 76:
          throwEreply(-90541, tbMsg);
          break;
        default:
          throwProtocolError(tbMsg.getMsgType());
          break;
      } 
    } 
  }
  
  public void dirPathDataSave(int paramInt) throws SQLException {
    synchronized (this.stream) {
      TbMsgSend.DPL_DATASAVE(this.stream, paramInt);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 75:
          justOKReply((TbMsgOkReply)tbMsg);
          break;
        case 76:
          throwEreply(-90538, tbMsg);
          break;
        default:
          throwProtocolError(tbMsg.getMsgType());
          break;
      } 
    } 
  }
  
  public void dirPathFinish() throws SQLException {
    synchronized (this.stream) {
      TbMsgSend.DPL_FINISH(this.stream);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 75:
          justOKReply((TbMsgOkReply)tbMsg);
          break;
        case 76:
          throwEreply(-90539, tbMsg);
          break;
        default:
          throwProtocolError(tbMsg.getMsgType());
          break;
      } 
    } 
  }
  
  public void dirPathFlushRow() throws SQLException {
    synchronized (this.stream) {
      TbMsgSend.DPL_FLUSH_ROW(this.stream);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 75:
          justOKReply((TbMsgOkReply)tbMsg);
          break;
        case 76:
          throwEreply(-90540, tbMsg);
          break;
        default:
          throwProtocolError(tbMsg.getMsgType());
          break;
      } 
    } 
  }
  
  public void dirPathLoadStream(TbDirPathStream paramTbDirPathStream, TbStreamDataWriter paramTbStreamDataWriter, int paramInt) throws SQLException {
    synchronized (this.stream) {
      TbMsgDplLoadStreamReply tbMsgDplLoadStreamReply;
      int i;
      int j;
      try {
        paramTbStreamDataWriter.reWriteInt(0, 57, 4);
        paramTbStreamDataWriter.reWriteLong(8, 0L, 8);
        paramTbStreamDataWriter.reWriteInt(16, paramInt, 4);
        paramTbStreamDataWriter.reWriteInt(20, paramTbStreamDataWriter.getBufferedDataSize() - 24, 4);
        paramTbStreamDataWriter.putPadding(4);
        paramTbStreamDataWriter.reWriteInt(4, paramTbStreamDataWriter.getBufferedDataSize() - 16, 4);
        this.stream.flush(paramTbStreamDataWriter);
      } finally {
        paramTbStreamDataWriter.clearDPLBuffer();
      } 
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 58:
          tbMsgDplLoadStreamReply = (TbMsgDplLoadStreamReply)tbMsg;
          i = tbMsgDplLoadStreamReply.rowCnt;
          j = tbMsgDplLoadStreamReply.returnCode;
          paramTbDirPathStream.addRowCnt(i);
          paramTbDirPathStream.addTotalRowCnt(i);
          paramTbDirPathStream.setReturnCode(j);
          if (j == 3)
            throw tbMsgDplLoadStreamReply.getException(-90537); 
          break;
        case 76:
          throwEreply(-90537, tbMsg);
        default:
          throwProtocolError(tbMsg.getMsgType());
          break;
      } 
    } 
  }
  
  public void dirPathPrepare(TbDirPathStream paramTbDirPathStream) throws SQLException {
    TbDirPathMetaData tbDirPathMetaData = paramTbDirPathStream.getDPLMetaData();
    int i = tbDirPathMetaData.getColumnCnt();
    TbColNameList[] arrayOfTbColNameList = new TbColNameList[i];
    byte b;
    for (b = 0; b < i; b++) {
      arrayOfTbColNameList[b] = new TbColNameList();
      arrayOfTbColNameList[b].set(tbDirPathMetaData.getColumn(b + 1));
    } 
    b = (byte) ((tbDirPathMetaData.getLogFlag() == true) ? 1 : 0);
    synchronized (this.stream) {
      TbColumnDesc[] arrayOfTbColumnDesc;
      TbMsgSend.DPL_PREPARE(this.stream, b, tbDirPathMetaData.getSchema(), tbDirPathMetaData.getTable(), i, arrayOfTbColNameList);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 56:
          if (i != ((TbMsgDplPrepareReply)tbMsg).colMeta.length)
            throw TbError.newSQLException(-90544); 
          arrayOfTbColumnDesc = ((TbMsgDplPrepareReply)tbMsg).colMeta;
          paramTbDirPathStream.getDPLMetaData().setColumnMetas(arrayOfTbColumnDesc);
          return;
        case 76:
          throwEreply(-90536, tbMsg);
          break;
      } 
      throwProtocolError(tbMsg.getMsgType());
    } 
  }
  
  public void dirPathPrepareParallel(TbDirPathStream paramTbDirPathStream) throws SQLException {
    TbDirPathMetaData tbDirPathMetaData = paramTbDirPathStream.getDPLMetaData();
    int i = tbDirPathMetaData.getColumnCnt();
    TbColNameList[] arrayOfTbColNameList = new TbColNameList[i];
    byte b;
    for (b = 0; b < i; b++) {
      arrayOfTbColNameList[b] = new TbColNameList();
      arrayOfTbColNameList[b].set(tbDirPathMetaData.getColumn(b + 1));
    } 
    b = (byte) ((tbDirPathMetaData.getLogFlag() == true) ? 1 : 0);
    int bool = (tbDirPathMetaData.getParallelFlag() == true) ? 1 : 0;
    synchronized (this.stream) {
      TbColumnDesc[] arrayOfTbColumnDesc;
      TbMsgSend.DPL_PREPARE_PARALLEL(this.stream, b, bool, tbDirPathMetaData.getSchema(), tbDirPathMetaData.getTable(), tbDirPathMetaData.getPartition(), i, arrayOfTbColNameList);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 56:
          if (i != ((TbMsgDplPrepareReply)tbMsg).colMeta.length)
            throw TbError.newSQLException(-90544); 
          arrayOfTbColumnDesc = ((TbMsgDplPrepareReply)tbMsg).colMeta;
          paramTbDirPathStream.getDPLMetaData().setColumnMetas(arrayOfTbColumnDesc);
          return;
        case 76:
          throwEreply(-90536, tbMsg);
          break;
      } 
      throwProtocolError(tbMsg.getMsgType());
    } 
  }
  
  public int execute(TbPreparedStatementImpl paramTbPreparedStatementImpl, String paramString, int paramInt) throws SQLException {
    synchronized (this.stream) {
      processExecute(paramTbPreparedStatementImpl, paramString, paramInt, true);
      TbMsg tbMsg = this.stream.readMsg();
      while (true) {
        TbMsgExecutePsmReply tbMsgExecutePsmReply;
        TbMsgExecutePsmPrefetchReply tbMsgExecutePsmPrefetchReply;
        switch (tbMsg.getMsgType()) {
          case 75:
            justOKReply((TbMsgOkReply)tbMsg);
            return TbSQLTypeScanner.isPSMStmt(paramTbPreparedStatementImpl.getSqlType()) ? 1 : 0;
          case 2:
            executeSessInfoReply((TbMsgSessInfoReply)tbMsg);
            return 0;
          case 12:
            return (int)executePrefetchNoDescReply(paramTbPreparedStatementImpl, (TbMsgExecutePrefetchNoDescReply)tbMsg);
          case 13:
            return (int)executeCountReply((TbStatement)paramTbPreparedStatementImpl, (TbMsgExecuteCountReply)tbMsg);
          case 8:
            return (int)executeRsetReply((TbStatement)paramTbPreparedStatementImpl, (TbMsgExecuteRsetReply)tbMsg);
          case 10:
            return (int)executeRsetNoDescReply(paramTbPreparedStatementImpl, (TbMsgExecuteRsetNoDescReply)tbMsg);
          case 16:
            return executeNeedDataReply(tbMsg, paramTbPreparedStatementImpl, paramInt);
          case 15:
            tbMsgExecutePsmReply = (TbMsgExecutePsmReply)tbMsg;
            return executeCallReply(paramTbPreparedStatementImpl, paramInt, tbMsgExecutePsmReply.paramData);
          case 9:
            tbMsg = executePivotReply((TbStatement)paramTbPreparedStatementImpl, (TbMsgExecutePivotReply)tbMsg);
            continue;
          case 183:
            tbMsgExecutePsmPrefetchReply = (TbMsgExecutePsmPrefetchReply)tbMsg;
            paramTbPreparedStatementImpl.buildColMetaArray(tbMsgExecutePsmPrefetchReply.colCnt, tbMsgExecutePsmPrefetchReply.hiddenColCnt, tbMsgExecutePsmPrefetchReply.colMeta);
            if (!(paramTbPreparedStatementImpl instanceof TbCallableStatementImpl))
              return 1; 
            return executePsmPrefetchReply((TbCallableStatementImpl)paramTbPreparedStatementImpl, paramInt, tbMsgExecutePsmPrefetchReply);
          case 76:
            return executeEreply(paramTbPreparedStatementImpl, paramString, tbMsg, paramInt);
        } 
        throwProtocolError(tbMsg.getMsgType());
      } 
    } 
  }
  
  private int executeCallReply(TbPreparedStatementImpl paramTbPreparedStatementImpl, int paramInt, TbOutParam[] paramArrayOfTbOutParam) throws SQLException {
    byte b = -1;
    byte b1 = (byte) ((paramArrayOfTbOutParam == null) ? 0 : paramArrayOfTbOutParam.length);
    BindData bindData = paramTbPreparedStatementImpl.getBindData();
    int i = paramTbPreparedStatementImpl.getParameterCnt();
    if (bindData.getOutParameterCnt() != b1)
      throw TbError.newSQLException(-90618); 
    if (paramArrayOfTbOutParam == null || !(paramTbPreparedStatementImpl instanceof TbCallableStatementImpl))
      return 1; 
    TbCallableStatementImpl tbCallableStatementImpl = (TbCallableStatementImpl)paramTbPreparedStatementImpl;
    for (byte b2 = 0; b2 < b1; b2++) {
      while (++b < i && !bindData.isOutParameterOn(b))
        b++; 
      if (b >= i)
        throw TbError.newSQLException(-90618); 
      BindItem bindItem1 = bindData.getBindItem(b);
      BindItem bindItem2 = tbCallableStatementImpl.getOutItems(b);
      bindItem2.set(bindItem1.getSQLType(), (paramArrayOfTbOutParam[b2]).value.length, (paramArrayOfTbOutParam[b2]).colMeta, null);
      tbCallableStatementImpl.setOutParam(b, (paramArrayOfTbOutParam[b2]).dataType, (paramArrayOfTbOutParam[b2]).value, null);
    } 
    return 1;
  }
  
  private int executePsmPrefetchReply(TbCallableStatementImpl paramTbCallableStatementImpl, int paramInt, TbMsgExecutePsmPrefetchReply paramTbMsgExecutePsmPrefetchReply) throws SQLException {
    TbOutParam[] arrayOfTbOutParam = paramTbMsgExecutePsmPrefetchReply.paramData;
    byte b = -1;
    byte b1 = (byte) ((arrayOfTbOutParam == null) ? 0 : arrayOfTbOutParam.length);
    BindData bindData = paramTbCallableStatementImpl.getBindData();
    int i = paramTbCallableStatementImpl.getParameterCnt();
    if (bindData.getOutParameterCnt() != b1)
      throw TbError.newSQLException(-90618); 
    if (arrayOfTbOutParam == null)
      return 1; 
    TbResultSet tbResultSet = processPrefetchedRset((TbStatement)paramTbCallableStatementImpl, paramTbMsgExecutePsmPrefetchReply.colCnt, paramTbMsgExecutePsmPrefetchReply.hiddenColCnt, paramTbMsgExecutePsmPrefetchReply.csrId, paramTbMsgExecutePsmPrefetchReply.colMeta, paramTbMsgExecutePsmPrefetchReply.rowChunkSize, paramTbMsgExecutePsmPrefetchReply.rowCnt, paramTbMsgExecutePsmPrefetchReply.isFetchCompleted, paramTbMsgExecutePsmPrefetchReply.getTsn());
    for (byte b2 = 0; b2 < b1; b2++) {
      while (++b < i && !bindData.isOutParameterOn(b))
        b++; 
      if (b >= i)
        throw TbError.newSQLException(-90618); 
      BindItem bindItem1 = bindData.getBindItem(b);
      BindItem bindItem2 = paramTbCallableStatementImpl.getOutItems(b);
      bindItem2.set(bindItem1.getSQLType(), (arrayOfTbOutParam[b2]).value.length, (arrayOfTbOutParam[b2]).colMeta, null);
      if (tbResultSet != null && (arrayOfTbOutParam[b2]).dataType == 16) {
        paramTbCallableStatementImpl.setOutParam(b, (arrayOfTbOutParam[b2]).dataType, (arrayOfTbOutParam[b2]).value, (TbResultSet)tbResultSet);
        tbResultSet = null;
        paramTbCallableStatementImpl.setPivotInfo(b, paramTbCallableStatementImpl.getPivotInfo());
        Vector vector = paramTbCallableStatementImpl.getPivotData();
        if (vector != null)
          for (Object arrayOfByte : vector)
            paramTbCallableStatementImpl.addPivotData((int) b, (byte[]) arrayOfByte);
      } else {
        paramTbCallableStatementImpl.setOutParam(b, (arrayOfTbOutParam[b2]).dataType, (arrayOfTbOutParam[b2]).value, null);
      } 
    } 
    return 1;
  }
  
  private long executeCountReply(TbStatement paramTbStatement, TbMsgExecuteCountReply paramTbMsgExecuteCountReply) throws SQLException {
    return (0xFFFFFFFF00000000L & paramTbMsgExecuteCountReply.cntHigh << 32L) + (0xFFFFFFFFL & paramTbMsgExecuteCountReply.cntLow);
  }
  
  public int executeDirect(TbStatement paramTbStatement, String paramString) throws SQLException {
    int bool = this.conn.getAutoCommit() ? 1 : 0;
    synchronized (this.stream) {
      TbMsgSend.EXECDIR(this.stream, bool, paramTbStatement.getPreFetchSize(), paramString);
      TbMsg tbMsg = this.stream.readMsg();
      while (true) {
        switch (tbMsg.getMsgType()) {
          case 75:
            justOKReply((TbMsgOkReply)tbMsg);
            return TbSQLTypeScanner.isPSMStmt(paramTbStatement.getSqlType()) ? 1 : 0;
          case 2:
            executeSessInfoReply((TbMsgSessInfoReply)tbMsg);
            return 0;
          case 13:
            return (int)executeCountReply(paramTbStatement, (TbMsgExecuteCountReply)tbMsg);
          case 8:
            return (int)executeRsetReply(paramTbStatement, (TbMsgExecuteRsetReply)tbMsg);
          case 14:
          case 15:
            return 1;
          case 11:
            return (int)executePrefetchReply(paramTbStatement, (TbMsgExecutePrefetchReply)tbMsg);
          case 9:
            tbMsg = executePivotReply(paramTbStatement, (TbMsgExecutePivotReply)tbMsg);
            continue;
          case 76:
            return executeDirectEreply(paramTbStatement, paramString, tbMsg);
        } 
        throwProtocolError(tbMsg.getMsgType());
      } 
    } 
  }
  
  private int executeDirectEreply(TbStatement paramTbStatement, String paramString, TbMsg paramTbMsg) throws SQLException {
    SQLException sQLException = getErrorMessage(-90508, paramTbMsg);
    if (sQLException.getErrorCode() == -12018)
      return executeDirect(paramTbStatement, paramString); 
    throw sQLException;
  }
  
  private int executeEreply(TbPreparedStatementImpl paramTbPreparedStatementImpl, String paramString, TbMsg paramTbMsg, int paramInt) throws SQLException {
    SQLException sQLException = getErrorMessage(-90508, paramTbMsg);
    if (sQLException.getErrorCode() == -12018) {
      paramTbPreparedStatementImpl.setPPID(null);
      paramTbPreparedStatementImpl.buildColMetaArray(0, 0, null);
      return prepareExecute(paramTbPreparedStatementImpl, paramString, paramInt);
    } 
    throw sQLException;
  }
  
  private int executeNeedDataReply(TbMsg paramTbMsg, TbPreparedStatementImpl paramTbPreparedStatementImpl, int paramInt) throws SQLException {
    TbMsgExecuteCallReply tbMsgExecuteCallReply;
    TbMsgExecutePrefetchReply tbMsgExecutePrefetchReply;
    TbMsgExecutePsmReply tbMsgExecutePsmReply;
    BindData bindData = paramTbPreparedStatementImpl.getBindData();
    Binder[][] arrayOfBinder = paramTbPreparedStatementImpl.getBinder();
    TbMsg tbMsg = paramTbMsg;
    int i = paramTbPreparedStatementImpl.getCurCsrId();
    int j = bindData.getDFRParameterCnt();
    TbStreamDataWriter tbStreamDataWriter = this.stream.getMsgWriter();
    while (true) {
      int k = ((TbMsgExecuteNeedDataReply)tbMsg).paramIndex;
      BindItem bindItem = bindData.getBindItem(k);
      if (!(arrayOfBinder[paramInt][k] instanceof ArrayInBinder) && bindItem.getParamMode() != 8)
        throw TbError.newSQLException(-590717, bindItem.toString()); 
      this.stream.startWritingPacketData();
      tbStreamDataWriter.writeInt(54, 4);
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      tbStreamDataWriter.writeInt(k, 4);
      tbStreamDataWriter.writeInt(i, 4);
      arrayOfBinder[paramInt][k].bindDFR(this.conn, (ParamContainer)paramTbPreparedStatementImpl, tbStreamDataWriter, paramInt, k, bindItem.getLength());
      TbMsgSend.PUT_DATA(this.stream, k, i, new byte[0], 0);
      tbMsg = this.stream.readMsg();
      if (tbMsg.getMsgType() == 16) {
        paramTbPreparedStatementImpl.setCurCsrId(((TbMsgExecuteNeedDataReply)tbMsg).csrId);
      } else if (tbMsg.getMsgType() == 76) {
        throwEreply(-90508, tbMsg);
      } else {
        break;
      } 
      j--;
    } 
    switch (tbMsg.getMsgType()) {
      case 14:
        tbMsgExecuteCallReply = (TbMsgExecuteCallReply)tbMsg;
        return executeCallReply(paramTbPreparedStatementImpl, paramInt, tbMsgExecuteCallReply.paramData);
      case 13:
        return (int)executeCountReply((TbStatement)paramTbPreparedStatementImpl, (TbMsgExecuteCountReply)tbMsg);
      case 75:
        justOKReply((TbMsgOkReply)tbMsg);
        return TbSQLTypeScanner.isPSMStmt(paramTbPreparedStatementImpl.getSqlType()) ? 1 : 0;
      case 8:
        return (int)executeRsetReply((TbStatement)paramTbPreparedStatementImpl, (TbMsgExecuteRsetReply)tbMsg);
      case 11:
        tbMsgExecutePrefetchReply = (TbMsgExecutePrefetchReply)tbMsg;
        paramTbPreparedStatementImpl.setPPID(tbMsgExecutePrefetchReply.ppid);
        paramTbPreparedStatementImpl.buildColMetaArray(tbMsgExecutePrefetchReply.colCnt, tbMsgExecutePrefetchReply.hiddenColCnt, tbMsgExecutePrefetchReply.colMeta);
        return (int)executePrefetchReply((TbStatement)paramTbPreparedStatementImpl, tbMsgExecutePrefetchReply);
      case 12:
        return (int)executePrefetchNoDescReply(paramTbPreparedStatementImpl, (TbMsgExecutePrefetchNoDescReply)tbMsg);
      case 15:
        tbMsgExecutePsmReply = (TbMsgExecutePsmReply)tbMsg;
        return executeCallReply(paramTbPreparedStatementImpl, paramInt, tbMsgExecutePsmReply.paramData);
    } 
    throwProtocolError(tbMsg.getMsgType());
    return 0;
  }
  
  public void executePivot(TbCallableStatementImpl paramTbCallableStatementImpl, int paramInt1, int paramInt2) throws SQLException {
    TbMsgSend.EXECUTE_PIVOT(this.stream, paramInt2);
    TbMsg tbMsg = this.stream.readMsg();
    switch (tbMsg.getMsgType()) {
      case 9:
        executePivotReply(paramTbCallableStatementImpl, paramInt1, (TbMsgExecutePivotReply)tbMsg);
        break;
      case 76:
        throwEreply(-90509, tbMsg);
        break;
    } 
  }
  
  private void executePivotReply(TbCallableStatementImpl paramTbCallableStatementImpl, int paramInt, TbMsgExecutePivotReply paramTbMsgExecutePivotReply) throws SQLException {
    TbPivotInfo[] arrayOfTbPivotInfo = new TbPivotInfo[paramTbMsgExecutePivotReply.pivotInfo.length];
    for (byte b = 0; b < paramTbMsgExecutePivotReply.pivotInfo.length; b++) {
      arrayOfTbPivotInfo[b] = new TbPivotInfo();
      (arrayOfTbPivotInfo[b]).colIdx = (paramTbMsgExecutePivotReply.pivotInfo[b]).colIdx;
      (arrayOfTbPivotInfo[b]).chunkCnt = (paramTbMsgExecutePivotReply.pivotInfo[b]).chunkCnt;
      if ((paramTbMsgExecutePivotReply.pivotInfo[b]).valType != null) {
        (arrayOfTbPivotInfo[b]).valType = new TbPvValType[(paramTbMsgExecutePivotReply.pivotInfo[b]).valType.length];
        for (byte b1 = 0; b1 < (paramTbMsgExecutePivotReply.pivotInfo[b]).valType.length; b1++) {
          (arrayOfTbPivotInfo[b]).valType[b1] = new TbPvValType();
          ((arrayOfTbPivotInfo[b]).valType[b1]).type = ((paramTbMsgExecutePivotReply.pivotInfo[b]).valType[b1]).type;
        } 
      } 
    } 
    paramTbCallableStatementImpl.setPivotInfo(paramInt, arrayOfTbPivotInfo);
    paramTbCallableStatementImpl.addPivotData(paramInt, paramTbMsgExecutePivotReply.chunk);
    while (true) {
      TbMsgFetchPivotReply tbMsgFetchPivotReply;
      TbMsgSend.FETCH_PIVOT(this.stream);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 21:
          tbMsgFetchPivotReply = (TbMsgFetchPivotReply)tbMsg;
          if (tbMsgFetchPivotReply.chunkLen <= 0)
            return; 
          paramTbCallableStatementImpl.addPivotData(paramInt, tbMsgFetchPivotReply.chunk);
          continue;
        case 75:
          return;
        case 76:
          throwEreply(-90509, tbMsg);
          break;
      } 
      throwProtocolError(tbMsg.getMsgType());
    } 
  }
  
  private TbMsg executePivotReply(TbStatement paramTbStatement, TbMsgExecutePivotReply paramTbMsgExecutePivotReply) throws SQLException {
    TbPivotInfo[] arrayOfTbPivotInfo = new TbPivotInfo[paramTbMsgExecutePivotReply.pivotInfo.length];
    for (byte b = 0; b < paramTbMsgExecutePivotReply.pivotInfo.length; b++) {
      arrayOfTbPivotInfo[b] = new TbPivotInfo();
      (arrayOfTbPivotInfo[b]).colIdx = (paramTbMsgExecutePivotReply.pivotInfo[b]).colIdx;
      (arrayOfTbPivotInfo[b]).chunkCnt = (paramTbMsgExecutePivotReply.pivotInfo[b]).chunkCnt;
      if ((paramTbMsgExecutePivotReply.pivotInfo[b]).valType != null) {
        (arrayOfTbPivotInfo[b]).valType = new TbPvValType[(paramTbMsgExecutePivotReply.pivotInfo[b]).valType.length];
        for (byte b1 = 0; b1 < (paramTbMsgExecutePivotReply.pivotInfo[b]).valType.length; b1++) {
          (arrayOfTbPivotInfo[b]).valType[b1] = new TbPvValType();
          ((arrayOfTbPivotInfo[b]).valType[b1]).type = ((paramTbMsgExecutePivotReply.pivotInfo[b]).valType[b1]).type;
        } 
      } 
    } 
    paramTbStatement.setPivotInfo(arrayOfTbPivotInfo);
    paramTbStatement.addPivotData(paramTbMsgExecutePivotReply.chunk);
    while (true) {
      TbMsgSend.FETCH_PIVOT(this.stream);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 21:
          paramTbStatement.addPivotData(((TbMsgFetchPivotReply)tbMsg).chunk);
          continue;
      } 
      return tbMsg;
    } 
  }
  
  private long executePrefetchNoDescReply(TbPreparedStatementImpl paramTbPreparedStatementImpl, TbMsgExecutePrefetchNoDescReply paramTbMsgExecutePrefetchNoDescReply) throws SQLException {
    TbResultSet tbResultSet = processPrefetchedRset((TbStatement)paramTbPreparedStatementImpl, paramTbPreparedStatementImpl.getOutColCnt(), paramTbPreparedStatementImpl.getHiddenColCnt(), paramTbMsgExecutePrefetchNoDescReply.csrId, paramTbPreparedStatementImpl.getColMetaArray(), paramTbMsgExecutePrefetchNoDescReply.rowChunkSize, paramTbMsgExecutePrefetchNoDescReply.rowCnt, paramTbMsgExecutePrefetchNoDescReply.isFetchCompleted, paramTbMsgExecutePrefetchNoDescReply.getTsn());
    paramTbPreparedStatementImpl.setResultSet((com.tmax.tibero.jdbc.driver.TbResultSet) tbResultSet);
    return tbResultSet.getUpdateCount();
  }
  
  private long executePrefetchReply(TbStatement paramTbStatement, TbMsgExecutePrefetchReply paramTbMsgExecutePrefetchReply) throws SQLException {
    TbResultSet tbResultSet = processPrefetchedRset(paramTbStatement, paramTbMsgExecutePrefetchReply.colCnt, paramTbMsgExecutePrefetchReply.hiddenColCnt, paramTbMsgExecutePrefetchReply.csrId, paramTbMsgExecutePrefetchReply.colMeta, paramTbMsgExecutePrefetchReply.rowChunkSize, paramTbMsgExecutePrefetchReply.rowCnt, paramTbMsgExecutePrefetchReply.isFetchCompleted, paramTbMsgExecutePrefetchReply.getTsn());
    paramTbStatement.setResultSet((com.tmax.tibero.jdbc.driver.TbResultSet) tbResultSet);
    return tbResultSet.getUpdateCount();
  }
  
  private long executeRsetNoDescReply(TbPreparedStatementImpl paramTbPreparedStatementImpl, TbMsgExecuteRsetNoDescReply paramTbMsgExecuteRsetNoDescReply) throws SQLException {
    TbResultSet tbResultSet = this.typeConverter.toResultSet(paramTbPreparedStatementImpl.getOutColCnt(), paramTbPreparedStatementImpl.getHiddenColCnt(), paramTbMsgExecuteRsetNoDescReply.csrId, paramTbPreparedStatementImpl.getColMetaArray(), (TbStatement)paramTbPreparedStatementImpl, null);
    ((com.tmax.tibero.jdbc.driver.TbResultSet) tbResultSet).setTsn(paramTbMsgExecuteRsetNoDescReply.getTsn());
    paramTbPreparedStatementImpl.setResultSet((com.tmax.tibero.jdbc.driver.TbResultSet) tbResultSet);
    return tbResultSet.getUpdateCount();
  }
  
  private long executeRsetReply(TbStatement paramTbStatement, TbMsgExecuteRsetReply paramTbMsgExecuteRsetReply) throws SQLException {
    TbResultSet tbResultSet = this.typeConverter.toResultSet(paramTbMsgExecuteRsetReply.colCnt, paramTbMsgExecuteRsetReply.hiddenColCnt, paramTbMsgExecuteRsetReply.csrId, paramTbMsgExecuteRsetReply.colMeta, paramTbStatement, null);
    ((com.tmax.tibero.jdbc.driver.TbResultSet) tbResultSet).setTsn(paramTbMsgExecuteRsetReply.getTsn());
    paramTbStatement.setResultSet((com.tmax.tibero.jdbc.driver.TbResultSet) tbResultSet);
    return paramTbMsgExecuteRsetReply.affectedCnt;
  }
  
  private void executeSessInfoReply(TbMsgSessInfoReply paramTbMsgSessInfoReply) {
    this.conn.setSessionId(paramTbMsgSessInfoReply.sessionId);
    this.conn.setSerialNo(paramTbMsgSessInfoReply.serialNo);
    this.conn.setNLSDate((paramTbMsgSessInfoReply.nlsData[0]).nlsParamVal);
    this.conn.setNLSTimestamp((paramTbMsgSessInfoReply.nlsData[2]).nlsParamVal);
    this.conn.setNLSCalandar((paramTbMsgSessInfoReply.nlsData[4]).nlsParamVal);
  }
  
  public void fetch(TbStatement paramTbStatement, TbResultSetBase paramTbResultSetBase) throws SQLException {
    synchronized (this.stream) {
      SQLException sQLException;
      String str;
      int i = paramTbResultSetBase.getFetchSize();
      int j = paramTbResultSetBase.getPreparedFetchCnt();
      if (j <= 0) {
        TbMsgSend.FETCH(this.stream, paramTbResultSetBase.getCursorId(), i);
        j = i * -1;
      } 
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 19:
          fetchReply(paramTbStatement, (TbMsgFetchReply)tbMsg, paramTbResultSetBase);
          j--;
          break;
        case 76:
          sQLException = getErrorMessage(-90509, tbMsg);
          str = sQLException.getSQLState();
          if (sQLException.getErrorCode() == -12018)
            ((TbMsgEreply)tbMsg).changeRootException(new SQLException(TbError.getMsg(-12031), str, -12031)); 
          throwEreply(-90509, tbMsg);
          break;
        default:
          throwProtocolError(tbMsg.getMsgType());
          break;
      } 
      paramTbResultSetBase.setPreparedFetchCnt(j);
    } 
  }
  
  private void fetchReply(TbStatement paramTbStatement, TbMsgFetchReply paramTbMsgFetchReply, TbResultSetBase paramTbResultSetBase) throws SQLException {
    byte[] arrayOfByte = paramTbResultSetBase.getRowChunk(paramTbMsgFetchReply.rowChunkSize);
    this.stream.readChunkData(arrayOfByte, paramTbMsgFetchReply.rowChunkSize);
    paramTbResultSetBase.setFetchCompleted(paramTbMsgFetchReply.isFetchCompleted);
    paramTbResultSetBase.setTsn(paramTbMsgFetchReply.getTsn());
    paramTbResultSetBase.buildRowTable(paramTbMsgFetchReply.rowCnt, arrayOfByte);
  }
  
  public void freeTemporary(TbLob paramTbLob) throws SQLException {
    lobFreeTemporary(paramTbLob);
  }
  
  public com.tmax.tibero.jdbc.driver.TbResultSet describeCSRReply(TbStatement paramTbStatement, int paramInt) throws SQLException {
    synchronized (this.stream) {
      TbMsgSend.DESCRIBE_CSR(this.stream, paramInt);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 11:
          return (com.tmax.tibero.jdbc.driver.TbResultSet) doDescribeCSRPrefetch(paramTbStatement, (TbMsgExecutePrefetchReply)tbMsg);
        case 76:
          throwEreply(-90546, tbMsg);
          break;
        default:
          throwProtocolError(tbMsg.getMsgType());
          break;
      } 
    } 
    return null;
  }
  
  private TbResultSet doDescribeCSRPrefetch(TbStatement paramTbStatement, TbMsgExecutePrefetchReply paramTbMsgExecutePrefetchReply) throws SQLException {
    if (paramTbMsgExecutePrefetchReply.colMeta == null)
      throw TbError.newSQLException(-90644); 
    TbResultSet tbResultSet = processPrefetchedRset(paramTbStatement, paramTbMsgExecutePrefetchReply.colCnt, paramTbMsgExecutePrefetchReply.hiddenColCnt, paramTbMsgExecutePrefetchReply.csrId, paramTbMsgExecutePrefetchReply.colMeta, paramTbMsgExecutePrefetchReply.rowChunkSize, paramTbMsgExecutePrefetchReply.rowCnt, paramTbMsgExecutePrefetchReply.isFetchCompleted, paramTbMsgExecutePrefetchReply.getTsn());
    paramTbStatement.addSubResultSet((com.tmax.tibero.jdbc.driver.TbResultSet) tbResultSet);
    return tbResultSet;
  }
  
  public SQLException getErrorMessage(int paramInt, TbMsg paramTbMsg) throws SQLException {
    TbMsgEreply tbMsgEreply = (TbMsgEreply)paramTbMsg;
    if (tbMsgEreply.flag == Integer.MIN_VALUE)
      reset(); 
    return tbMsgEreply.getException(paramInt);
  }
  
  public TbSQLInfo getLastExecutedSqlinfo() throws SQLException {
    synchronized (this.stream) {
      TbMsgGetLastExecutedSqlinfoReply tbMsgGetLastExecutedSqlinfoReply;
      TbSQLInfo tbSQLInfo;
      TbMsgSend.GET_LAST_EXECUTED_SQLINFO(this.stream, this.conn.getSessionId());
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 74:
          tbMsgGetLastExecutedSqlinfoReply = (TbMsgGetLastExecutedSqlinfoReply)tbMsg;
          tbSQLInfo = new TbSQLInfo();
          tbSQLInfo.setSqlid(tbMsgGetLastExecutedSqlinfoReply.sqlid);
          tbSQLInfo.setHashval(tbMsgGetLastExecutedSqlinfoReply.hashval);
          return tbSQLInfo;
        case 76:
          throwEreply(-90535, tbMsg);
          break;
      } 
      throwProtocolError(tbMsg.getMsgType());
    } 
    return null;
  }
  
  public TbSQLInfo2 getLastExecutedSqlinfo2() throws SQLException {
    synchronized (this.stream) {
      TbMsgGetLastExecutedSqlinfo2Reply tbMsgGetLastExecutedSqlinfo2Reply;
      TbSQLInfo2 tbSQLInfo2;
      TbMsgSend.GET_LAST_EXECUTED_SQLINFO2(this.stream, this.conn.getSessionId());
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 249:
          tbMsgGetLastExecutedSqlinfo2Reply = (TbMsgGetLastExecutedSqlinfo2Reply)tbMsg;
          tbSQLInfo2 = new TbSQLInfo2();
          tbSQLInfo2.setSqlId(tbMsgGetLastExecutedSqlinfo2Reply.sqlid);
          tbSQLInfo2.setChildNum(tbMsgGetLastExecutedSqlinfo2Reply.childNum);
          return tbSQLInfo2;
        case 76:
          throwEreply(-90535, tbMsg);
          break;
      } 
      throwProtocolError(tbMsg.getMsgType());
    } 
    return null;
  }
  
  public TbStream getStream() {
    return this.stream;
  }
  
  protected TbConnection getTbConnection() {
    return this.conn;
  }
  
  public SQLWarning getWarningMessage() throws SQLException {
    return null;
  }
  
  private void justOKReply(TbMsgOkReply paramTbMsgOkReply) {
    if (paramTbMsgOkReply.warningMsg != null && paramTbMsgOkReply.warningMsg.length() > 0)
      this.conn.addWarning(TbError.newSQLWarning(-90500, paramTbMsgOkReply.warningMsg)); 
  }
  
  public long length(TbLob paramTbLob) throws SQLException {
    long l = lobLength(paramTbLob);
    return (paramTbLob instanceof TbBlob) ? l : (l / this.typeConverter.getUCS2MaxBytesPerChar());
  }
  
  private boolean lobClose(TbLob paramTbLob) throws SQLException {
    synchronized (this.stream) {
      TbMsgSend.LOB_CLOSE(this.stream, paramTbLob.getLocator(), paramTbLob.getLocatorLength());
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 51:
          paramTbLob.setLocator(((TbMsgLobCloseReply)tbMsg).slobLoc);
          return true;
        case 76:
          throw getErrorMessage(-90522, tbMsg);
      } 
      throwProtocolError(tbMsg.getMsgType());
    } 
    return false;
  }
  
  private byte[] lobCreateTemporary(int paramInt) throws SQLException {
    synchronized (this.stream) {
      TbMsgSend.LOB_CREATE_TEMP(this.stream, paramInt);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 44:
          return ((TbMsgLobCreateTempReply)tbMsg).slobLoc;
        case 76:
          throw getErrorMessage(-90524, tbMsg);
      } 
      throwProtocolError(tbMsg.getMsgType());
    } 
    return null;
  }
  
  private void lobFreeTemporary(TbLob paramTbLob) throws SQLException {
    synchronized (this.stream) {
      TbMsgSend.LOB_DELETE_TEMP(this.stream, paramTbLob.getLocator(), paramTbLob.getLocatorLength());
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 75:
          justOKReply((TbMsgOkReply)tbMsg);
          break;
        case 76:
          throw getErrorMessage(-90525, tbMsg);
        default:
          throwProtocolError(tbMsg.getMsgType());
          break;
      } 
    } 
  }
  
  private long lobLength(TbLob paramTbLob) throws SQLException {
    if (paramTbLob.isInline())
      return paramTbLob.getIlobLength(); 
    synchronized (this.stream) {
      TbMsgLobLengthReply tbMsgLobLengthReply;
      TbMsgSend.LOB_LENGTH(this.stream, paramTbLob.getLocator(), paramTbLob.getLocatorLength());
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 34:
          tbMsgLobLengthReply = (TbMsgLobLengthReply)tbMsg;
          return (0xFFFFFFFF00000000L & tbMsgLobLengthReply.lenHigh << 32L) + (0xFFFFFFFFL & tbMsgLobLengthReply.lenLow);
        case 76:
          throw getErrorMessage(-90523, tbMsg);
      } 
      throwProtocolError(tbMsg.getMsgType());
    } 
    return 0L;
  }
  
  private boolean lobOpen(TbLob paramTbLob, int paramInt) throws SQLException {
    synchronized (this.stream) {
      TbMsgSend.LOB_OPEN(this.stream, paramInt, paramTbLob.getLocator(), paramTbLob.getLocatorLength());
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 49:
          paramTbLob.setLocator(((TbMsgLobOpenReply)tbMsg).slobLoc);
          return true;
        case 76:
          throw getErrorMessage(-90521, tbMsg);
      } 
      throwProtocolError(tbMsg.getMsgType());
    } 
    return false;
  }
  
  private long lobPosition(TbLob paramTbLob, String paramString, long paramLong) throws SQLException {
    int i = (int)(paramLong >> 32L);
    int j = (int)(paramLong & 0xFFFFFFFFL);
    synchronized (this.stream) {
      TbMsgLobInstrReply tbMsgLobInstrReply;
      TbMsgSend.LOB_INSTR(this.stream, i, j, paramTbLob.getLocator(), paramTbLob.getLocatorLength(), paramString);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 40:
          tbMsgLobInstrReply = (TbMsgLobInstrReply)tbMsg;
          return 0xFFFFFFFF00000000L & (tbMsgLobInstrReply.offsetHigh << 32) + (0xFFFFFFFFL & tbMsgLobInstrReply.offsetLow);
        case 76:
          throw getErrorMessage(-90518, tbMsg);
      } 
      throwProtocolError(tbMsg.getMsgType());
    } 
    return 0L;
  }
  
  private long lobPosition(TbLob paramTbLob1, TbLob paramTbLob2, long paramLong) throws SQLException {
    int i = (int)(paramLong >> 32L);
    int j = (int)(paramLong & 0xFFFFFFFFL);
    synchronized (this.stream) {
      TbMsgLobInlobReply tbMsgLobInlobReply;
      TbMsgSend.LOB_INLOB(this.stream, i, j, paramTbLob1.getLocator(), paramTbLob1.getLocatorLength(), paramTbLob2.getLocator(), paramTbLob2.getLocatorLength());
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 47:
          tbMsgLobInlobReply = (TbMsgLobInlobReply)tbMsg;
          return 0xFFFFFFFF00000000L & (tbMsgLobInlobReply.offsetHigh << 32) + (0xFFFFFFFFL & tbMsgLobInlobReply.offsetLow);
        case 76:
          throw getErrorMessage(-90519, tbMsg);
      } 
      throwProtocolError(tbMsg.getMsgType());
    } 
    return 0L;
  }
  
  private int lobRead(TbLob paramTbLob, long paramLong1, char[] paramArrayOfchar, byte[] paramArrayOfbyte, long paramLong2, int paramInt) throws SQLException {
    if (paramTbLob.isInline()) {
      boolean bool = false;
      if (paramTbLob.isRemote()) {
        if (!paramTbLob.isXML())
          throw TbError.newSQLException(-90902); 
        bool = true;
      } 
      return paramTbLob.readIlob((int)paramLong1, paramArrayOfchar, paramArrayOfbyte, (int)paramLong2, paramInt, this.typeConverter, bool);
    } 
    int i = (int)(paramLong1 >> 32L);
    int j = (int)(paramLong1 & 0xFFFFFFFFL);
    synchronized (this.stream) {
      TbMsgSend.LOB_READ(this.stream, i, j, paramInt, paramTbLob.getLocator(), paramTbLob.getLocatorLength());
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 36:
          return lobReadReply((TbMsgLobReadReply)tbMsg, paramTbLob, paramLong1, paramArrayOfchar, paramArrayOfbyte, paramLong2, paramInt);
        case 76:
          throw getErrorMessage(-90516, tbMsg);
      } 
      throwProtocolError(tbMsg.getMsgType());
    } 
    return 0;
  }
  
  private int lobReadReply(TbMsgLobReadReply paramTbMsgLobReadReply, TbLob paramTbLob, long paramLong1, char[] paramArrayOfchar, byte[] paramArrayOfbyte, long paramLong2, int paramInt) throws SQLException {
    int i = 0;
    if (paramTbMsgLobReadReply.data != null)
      i = paramTbMsgLobReadReply.data.length; 
    if (i == paramInt) {
      paramTbLob.setEndOfStream(false);
    } else {
      paramTbLob.setEndOfStream(true);
    } 
    if (i <= 0)
      return 0; 
    if (paramTbLob instanceof com.tmax.tibero.jdbc.TbClob || paramTbLob instanceof com.tmax.tibero.jdbc.TbNClob)
      return this.typeConverter.fixedBytesToChars(paramTbMsgLobReadReply.data, 0, i, paramArrayOfchar, (int)paramLong2, (int)(paramArrayOfchar.length - paramLong2)); 
    System.arraycopy(paramTbMsgLobReadReply.data, 0, paramArrayOfbyte, (int)paramLong2, i);
    return i;
  }
  
  private void lobTruncate(TbLob paramTbLob, long paramLong) throws SQLException {
    int i = (int)(paramLong >> 32L);
    int j = (int)(paramLong & 0xFFFFFFFFL);
    synchronized (this.stream) {
      TbMsgSend.LOB_TRUNC(this.stream, i, j, paramTbLob.getLocator(), paramTbLob.getLocatorLength());
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 38:
          paramTbLob.setLocator(((TbMsgLobTruncReply)tbMsg).slobLoc);
          break;
        case 76:
          throw getErrorMessage(-90520, tbMsg);
        default:
          throwProtocolError(tbMsg.getMsgType());
          break;
      } 
    } 
  }
  
  private void lobWrite(TbLob paramTbLob, long paramLong1, char[] paramArrayOfchar, byte[] paramArrayOfbyte, long paramLong2, long paramLong3, int paramInt) throws SQLException {
    int i = (int)(paramLong1 >> 32L);
    int j = (int)(paramLong1 & 0xFFFFFFFFL);
    int k = 0;
    byte[] arrayOfByte = null;
    if (paramTbLob instanceof com.tmax.tibero.jdbc.TbClob || paramTbLob instanceof com.tmax.tibero.jdbc.TbNClob) {
      int m = this.typeConverter.getUCS2MaxBytesPerChar() * (int)paramLong3;
      arrayOfByte = new byte[m];
      k = m;
      this.typeConverter.charsToFixedBytes(paramArrayOfchar, (int)paramLong2, (int)paramLong3, arrayOfByte, 0, k);
    } else {
      int m = TbCommon.getPadLength((int)paramLong3);
      arrayOfByte = new byte[(int)paramLong3 + m];
      k = (int)paramLong3;
      System.arraycopy(paramArrayOfbyte, (int)paramLong2, arrayOfByte, 0, (int)paramLong3);
      TbCommon.writePadding(arrayOfByte, (int)paramLong3, m);
    } 
    synchronized (this.stream) {
      TbMsgSend.LOB_WRITE(this.stream, i, j, paramInt, paramTbLob.getLocator(), paramTbLob.getLocatorLength(), arrayOfByte, k);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 75:
          justOKReply((TbMsgOkReply)tbMsg);
          break;
        case 42:
          lobWriteReply(paramTbLob, (TbMsgLobWriteReply)tbMsg, paramInt);
          break;
        case 76:
          throw getErrorMessage(-90517, tbMsg);
        default:
          throwProtocolError(tbMsg.getMsgType());
          break;
      } 
    } 
  }
  
  private void lobWriteReply(TbLob paramTbLob, TbMsgLobWriteReply paramTbMsgLobWriteReply, int paramInt) {
    if ((paramInt & 0x2000000) == 0)
      return; 
    paramTbLob.setLocator(paramTbMsgLobWriteReply.slobLoc);
  }
  
  public synchronized void logon(boolean paramBoolean) throws SQLException {
    while (true) {
      synchronized (this.stream) {
        TbMsg tbMsg = this.stream.readMsg();
        switch (tbMsg.getMsgType()) {
          case 0:
            if (!logonConnectReply((TbMsgConnectReply)tbMsg, paramBoolean))
              return; 
            logonAuthRequest(paramBoolean);
            break;
          case 2:
            logonSessInfoReply((TbMsgSessInfoReply)tbMsg);
            return;
          case 144:
            break;
          case 76:
            logonEreply(tbMsg, paramBoolean);
            return;
          default:
            throwProtocolError(tbMsg.getMsgType());
            break;
        } 
      } 
    } 
  }
  
  private void logonAuthRequest(boolean paramBoolean) throws SQLException {
    String str1 = this.conn.info.getUser();
    String str2 = this.conn.info.getPassword();
    if (str2.length() >= 2 && str2.startsWith("\"") && str2.endsWith("\""))
      str2 = str2.substring(1, str2.length() - 1); 
    String str3 = this.conn.info.getDatabaseName();
    if (str3.length() >= 2 && str3.startsWith("\"") && str3.endsWith("\""))
      str3 = str3.substring(1, str3.length() - 1); 
    String str4 = this.conn.info.getProgramName();
    if (str4 == null)
      str4 = "JDBC Thin Client"; 
    String str5 = System.getProperty("user.name");
    String str6 = null;
    try {
      str6 = InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException unknownHostException) {}
    TbClntInfoParam[] arrayOfTbClntInfoParam = new TbClntInfoParam[7];
    int i;
    for (i = 0; i < 7; i++)
      arrayOfTbClntInfoParam[i] = new TbClntInfoParam(); 
    arrayOfTbClntInfoParam[0].set(0, "-1");
    arrayOfTbClntInfoParam[1].set(1, str4);
    arrayOfTbClntInfoParam[2].set(2, null);
    arrayOfTbClntInfoParam[3].set(3, str5);
    arrayOfTbClntInfoParam[4].set(4, str6);
    i = 0;
    String str7 = this.conn.info.getNewPassword();
    String str8 = null;
    if (str7 != null && str7.length() != 0) {
      i |= 0x10;
      str8 = str2;
      str2 = str7;
    } 
    if (this.rsa != null && this.conn.getSessKey() != null)
      try {
        DataTypeConverter dataTypeConverter = this.conn.getTypeConverter();
        String str10 = this.conn.getSessKey();
        byte[] arrayOfByte1 = DataTypeConverter.tbBase64Decode(str10.getBytes("ASCII"));
        str10 = new String(arrayOfByte1, "ASCII");
        String str11 = str10.substring("-----BEGIN PUBLIC KEY-----\n".length(), str10.lastIndexOf("-----END PUBLIC KEY-----"));
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(DataTypeConverter.base64Decode(str11.getBytes("ASCII")));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        this.rsa.init(1, publicKey);
        byte[] arrayOfByte2 = this.rsa.doFinal(dataTypeConverter.getDBEncodedBytes(str2));
        str2 = DataTypeConverter.tbBase64Encode(arrayOfByte2);
        if (str8 != null) {
          arrayOfByte2 = this.rsa.doFinal(dataTypeConverter.getDBEncodedBytes(str8));
          str8 = DataTypeConverter.tbBase64Encode(arrayOfByte2);
        } 
      } catch (Exception exception) {
        System.err.println(exception);
        throw TbError.newSQLException(-90502);
      }  
    arrayOfTbClntInfoParam[5].set(5, str8);
    TbNlsParam[] arrayOfTbNlsParam = new TbNlsParam[14];
    for (byte b = 0; b < 14; b++)
      arrayOfTbNlsParam[b] = new TbNlsParam(); 
    arrayOfTbNlsParam[0].set(0, null);
    arrayOfTbNlsParam[1].set(1, null);
    arrayOfTbNlsParam[2].set(2, null);
    arrayOfTbNlsParam[3].set(6, null);
    arrayOfTbNlsParam[4].set(3, CharsetMetaData.getNLSLanguage(Locale.getDefault()));
    arrayOfTbNlsParam[5].set(4, null);
    arrayOfTbNlsParam[6].set(5, null);
    TimeZone timeZone = TimeZone.getDefault();
    String str9 = ZoneInfo.convertStandardTimeZoneID(timeZone.getID());
    if (ZoneInfo.getTimeZoneIdByName(str9) == ZoneInfo.TZ_ID_OFFSET.getId().intValue()) {
      int j = timeZone.getRawOffset();
      String str = "";
      if (j >= 0) {
        str = str + "+";
      } else {
        str = str + "-";
      } 
      int k = Math.abs(j) / 3600000;
      int m = j / 60000 % 60;
      if (k >= 10) {
        str = str + String.valueOf(k);
      } else {
        str = str + "0" + String.valueOf(k);
      } 
      if (m == 0) {
        str = str + ":00";
      } else {
        str = str + ":" + String.valueOf(m);
      } 
      timeZone.setID("GMT" + str);
      str9 = ZoneInfo.convertStandardTimeZoneID(timeZone.getID());
    } 
    arrayOfTbNlsParam[7].set(7, str9);
    TbMsgSend.AUTH_REQ_WITH_VER(this.stream, 2, 16, i, str1, str3, str2, arrayOfTbClntInfoParam.length, arrayOfTbClntInfoParam, arrayOfTbNlsParam.length, arrayOfTbNlsParam, 1);
  }
  
  private boolean logonConnectReply(TbMsgConnectReply paramTbMsgConnectReply, boolean paramBoolean) throws SQLException {
    if (this.conn.info.isLoadBalance() && !this.conn.isMiddleOfFailover() && paramTbMsgConnectReply.flags == 4096) {
      ConnectionInfo connectionInfo = this.conn.info;
      try {
        this.conn.close();
      } catch (SQLException sQLException) {}
      this.conn.openConnection(connectionInfo, paramBoolean);
      return false;
    } 
    int i = Charset.getCharset(this.conn.info.getCharacterSet());
    if (i == -1)
      i = paramTbMsgConnectReply.charset; 
    if (paramTbMsgConnectReply.protocolMajor < 2)
      throw TbError.newSQLException(-90203); 
    ServerInfo serverInfo = new ServerInfo(i, paramTbMsgConnectReply.ncharset, paramTbMsgConnectReply.svrIsBigendian, paramTbMsgConnectReply.svrIsNanobase, paramTbMsgConnectReply.tbMajor, paramTbMsgConnectReply.tbMinor, paramTbMsgConnectReply.tbProductName, paramTbMsgConnectReply.tbProductVersion, paramTbMsgConnectReply.protocolMajor, paramTbMsgConnectReply.protocolMinor);
    this.conn.setServerInfo(serverInfo);
    this.conn.setMthrPid(paramTbMsgConnectReply.mthrPid);
    this.typeConverter.setCharset(this.conn.getServerCharSet(), this.conn.getServerNCharSet());
    this.conn.setMaxDFRCharCount();
    if (paramTbMsgConnectReply.protocolMinor >= 2 && paramTbMsgConnectReply.protocolMinor >= 9)
      try {
        this.rsa = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
      } catch (Exception exception) {
        this.conn.addWarning(TbError.newSQLWarning(-90200, exception));
      }  
    if (this.rsa != null) {
      TbMsgSesskeyReply tbMsgSesskeyReply;
      TbMsgSend.REQUEST_SESSKEY(this.stream);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 283:
          tbMsgSesskeyReply = (TbMsgSesskeyReply)tbMsg;
          if (tbMsgSesskeyReply.sesskey == null || tbMsgSesskeyReply.sesskey.length() == 0) {
            this.conn.setSessKey(null);
          } else {
            this.conn.setSessKey(tbMsgSesskeyReply.sesskey);
          } 
          return true;
        case 76:
          logonEreply(tbMsg, paramBoolean);
          return false;
      } 
      throwProtocolError(tbMsg.getMsgType());
    } 
    return true;
  }
  
  private void logonEreply(TbMsg paramTbMsg, boolean paramBoolean) throws SQLException {
    ConnectionInfo connectionInfo = this.conn.info;
    SQLException sQLException = getErrorMessage(-90502, paramTbMsg);
    if (sQLException.getErrorCode() == -12060) {
      try {
        this.conn.close();
      } catch (SQLException sQLException1) {}
      this.conn.openConnection(connectionInfo, paramBoolean);
    } else if (sQLException.getErrorCode() == -12004) {
      logon(paramBoolean);
    } else {
      throw sQLException;
    } 
  }
  
  private void logonSessInfoReply(TbMsgSessInfoReply paramTbMsgSessInfoReply) {
    this.conn.setSessionId(paramTbMsgSessInfoReply.sessionId);
    this.conn.setSerialNo(paramTbMsgSessInfoReply.serialNo);
    this.conn.setNLSDate((paramTbMsgSessInfoReply.nlsData[0]).nlsParamVal);
    this.conn.setNLSTimestamp((paramTbMsgSessInfoReply.nlsData[2]).nlsParamVal);
    if (paramTbMsgSessInfoReply.nlsData.length > 8 && (paramTbMsgSessInfoReply.nlsData[8]).nlsParamVal != null) {
      this.conn.setNLSWarning(true);
      String str = (paramTbMsgSessInfoReply.nlsData[8]).nlsParamVal;
      if (str.equals("Y")) {
        this.conn.addWarning(TbError.newSQLWarning(-90547));
      } else {
        this.conn.addWarning(TbError.newSQLWarning(-90548, new Object[] { str }));
      } 
    } 
  }
  
  public boolean open(TbLob paramTbLob, int paramInt) throws SQLException {
    return lobOpen(paramTbLob, paramInt);
  }
  
  public void openSession() throws SQLException {
    synchronized (this.stream) {
      TbMsgSend.OPEN_SESS(this.stream);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 75:
          justOKReply((TbMsgOkReply)tbMsg);
          break;
        case 76:
          throwEreply(-90504, tbMsg);
          break;
        default:
          throwProtocolError(tbMsg.getMsgType());
          break;
      } 
    } 
  }
  
  public void ping() throws SQLException {
    synchronized (this.stream) {
      TbMsgSend.TB_PING(this.stream, 0);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 75:
          justOKReply((TbMsgOkReply)tbMsg);
          break;
        case 76:
          throwEreply(-90500, tbMsg);
          break;
        default:
          throwProtocolError(tbMsg.getMsgType());
          break;
      } 
    } 
  }
  
  public long position(TbBlob paramTbBlob, byte[] paramArrayOfbyte, long paramLong) throws SQLException {
    return lobPosition((TbLob)paramTbBlob, paramArrayOfbyte.toString(), paramLong);
  }
  
  public long position(TbClobBase paramTbClobBase, char[] paramArrayOfchar, long paramLong) throws SQLException {
    long l = lobPosition((TbLob)paramTbClobBase, paramArrayOfchar.toString(), paramLong);
    return l * this.typeConverter.getUCS2MaxBytesPerChar();
  }
  
  public long position(TbLob paramTbLob1, TbLob paramTbLob2, long paramLong) throws SQLException {
    long l = lobPosition(paramTbLob1, paramTbLob2, paramLong);
    return (paramTbLob1 instanceof TbBlob) ? l : (l * this.typeConverter.getUCS2MaxBytesPerChar());
  }
  
  public void prepare(TbPreparedStatementImpl paramTbPreparedStatementImpl, String paramString, Vector<Integer> paramVector) throws SQLException {
    synchronized (this.stream) {
      TbMsgSend.PREPARE(this.stream, paramString);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 4:
          prepareReply(paramTbPreparedStatementImpl, (TbMsgPrepareReply)tbMsg, paramVector);
          return;
        case 76:
          throwEreply(-90506, tbMsg);
          return;
      } 
      throwProtocolError(tbMsg.getMsgType());
    } 
  }
  
  public int prepareExecute(TbPreparedStatementImpl paramTbPreparedStatementImpl, String paramString, int paramInt) throws SQLException {
    if (paramTbPreparedStatementImpl.getPPID() != null)
      return execute(paramTbPreparedStatementImpl, paramString, paramInt); 
    synchronized (this.stream) {
      processExecute(paramTbPreparedStatementImpl, paramString, paramInt, false);
      TbMsg tbMsg = this.stream.readMsg();
      while (true) {
        TbMsgExecuteCountReply tbMsgExecuteCountReply;
        TbMsgExecutePrefetchReply tbMsgExecutePrefetchReply;
        TbMsgExecuteRsetReply tbMsgExecuteRsetReply;
        TbMsgExecutePsmReply tbMsgExecutePsmReply;
        TbMsgExecuteCallReply tbMsgExecuteCallReply;
        TbMsgExecutePsmPrefetchReply tbMsgExecutePsmPrefetchReply;
        switch (tbMsg.getMsgType()) {
          case 75:
            justOKReply((TbMsgOkReply)tbMsg);
            return TbSQLTypeScanner.isPSMStmt(paramTbPreparedStatementImpl.getSqlType()) ? 1 : 0;
          case 2:
            executeSessInfoReply((TbMsgSessInfoReply)tbMsg);
            return 0;
          case 13:
            tbMsgExecuteCountReply = (TbMsgExecuteCountReply)tbMsg;
            paramTbPreparedStatementImpl.setPPID(tbMsgExecuteCountReply.ppid);
            return (int)executeCountReply((TbStatement)paramTbPreparedStatementImpl, tbMsgExecuteCountReply);
          case 11:
            tbMsgExecutePrefetchReply = (TbMsgExecutePrefetchReply)tbMsg;
            paramTbPreparedStatementImpl.setPPID(tbMsgExecutePrefetchReply.ppid);
            paramTbPreparedStatementImpl.buildColMetaArray(tbMsgExecutePrefetchReply.colCnt, tbMsgExecutePrefetchReply.hiddenColCnt, tbMsgExecutePrefetchReply.colMeta);
            return (int)executePrefetchReply((TbStatement)paramTbPreparedStatementImpl, tbMsgExecutePrefetchReply);
          case 8:
            tbMsgExecuteRsetReply = (TbMsgExecuteRsetReply)tbMsg;
            paramTbPreparedStatementImpl.setPPID(tbMsgExecuteRsetReply.ppid);
            paramTbPreparedStatementImpl.buildColMetaArray(tbMsgExecuteRsetReply.colCnt, tbMsgExecuteRsetReply.hiddenColCnt, tbMsgExecuteRsetReply.colMeta);
            return (int)executeRsetReply((TbStatement)paramTbPreparedStatementImpl, tbMsgExecuteRsetReply);
          case 16:
            return executeNeedDataReply(tbMsg, paramTbPreparedStatementImpl, paramInt);
          case 15:
            tbMsgExecutePsmReply = (TbMsgExecutePsmReply)tbMsg;
            return executeCallReply(paramTbPreparedStatementImpl, paramInt, tbMsgExecutePsmReply.paramData);
          case 14:
            tbMsgExecuteCallReply = (TbMsgExecuteCallReply)tbMsg;
            return executeCallReply(paramTbPreparedStatementImpl, paramInt, tbMsgExecuteCallReply.paramData);
          case 183:
            tbMsgExecutePsmPrefetchReply = (TbMsgExecutePsmPrefetchReply)tbMsg;
            paramTbPreparedStatementImpl.setPPID(tbMsgExecutePsmPrefetchReply.ppid);
            paramTbPreparedStatementImpl.buildColMetaArray(tbMsgExecutePsmPrefetchReply.colCnt, tbMsgExecutePsmPrefetchReply.hiddenColCnt, tbMsgExecutePsmPrefetchReply.colMeta);
            if (!(paramTbPreparedStatementImpl instanceof TbCallableStatementImpl))
              return 1; 
            return executePsmPrefetchReply((TbCallableStatementImpl)paramTbPreparedStatementImpl, paramInt, tbMsgExecutePsmPrefetchReply);
          case 9:
            tbMsg = executePivotReply((TbStatement)paramTbPreparedStatementImpl, (TbMsgExecutePivotReply)tbMsg);
            continue;
          case 76:
            throwEreply(-90508, tbMsg);
            break;
        } 
        throwProtocolError(tbMsg.getMsgType());
      } 
    } 
  }
  
  private void prepareReply(TbPreparedStatementImpl paramTbPreparedStatementImpl, TbMsgPrepareReply paramTbMsgPrepareReply, Vector<Integer> paramVector) {
    if (paramTbMsgPrepareReply.isPreparedDdl == 1) {
      paramTbPreparedStatementImpl.setPPID(null);
      paramTbPreparedStatementImpl.setParameterCnt(0);
    } else {
      int i = paramTbMsgPrepareReply.bindParamCnt;
      paramTbPreparedStatementImpl.setPPID(paramTbMsgPrepareReply.ppid);
      paramTbPreparedStatementImpl.setParameterCnt(i);
      for (byte b = 0; b < i; b++)
        paramVector.add(b, (paramTbMsgPrepareReply.bindParamMeta[b]).type);
      paramTbPreparedStatementImpl.buildColMetaArray(paramTbMsgPrepareReply.outColCnt, paramTbMsgPrepareReply.hiddenColCnt, paramTbMsgPrepareReply.colDesc);
      if (this.conn.getServerInfo().getProtocolMajorVersion() >= 2 && this.conn.getServerInfo().getProtocolMinorVersion() >= 13)
        paramTbPreparedStatementImpl.buildParamMetaArray(paramTbMsgPrepareReply.bindParamMeta); 
    } 
  }
  
  private void processExecute(TbPreparedStatementImpl paramTbPreparedStatementImpl, String paramString, int paramInt, boolean paramBoolean) throws SQLException {
    synchronized (this.stream.getWriteStreamBuffer()) {
      TbStreamDataWriter tbStreamDataWriter = this.stream.getMsgWriter();
      int bool = this.conn.getAutoCommit() ? 1 : 0;
      BindData bindData = paramTbPreparedStatementImpl.getBindData();
      Binder[][] arrayOfBinder = paramTbPreparedStatementImpl.getBinder();
      int i = bindData.getParameterCnt();
      this.stream.startWritingPacketData();
      if (paramBoolean) {
        tbStreamDataWriter.writeInt(5, 4);
      } else {
        tbStreamDataWriter.writeInt(7, 4);
      } 
      tbStreamDataWriter.writeInt(0, 4);
      tbStreamDataWriter.writeLong(0L, 8);
      if (paramBoolean) {
        tbStreamDataWriter.writeBytes(paramTbPreparedStatementImpl.getPPID());
      } else {
        tbStreamDataWriter.writeLenAndDBEncodedPadString((paramString == null) ? "" : paramString);
      } 
      tbStreamDataWriter.writeInt(bool, 4);
      tbStreamDataWriter.writeInt(paramTbPreparedStatementImpl.getPreFetchSize(), 4);
      tbStreamDataWriter.writeInt(i, 4);
      for (byte b = 0; b < i; b++) {
        if (arrayOfBinder[paramInt][b] == null)
          throw TbError.newSQLException(-90627); 
        BindItem bindItem = bindData.getBindItem(b);
        int j = bindItem.getParamMode() & 0xFF | paramTbPreparedStatementImpl.getParamType(paramInt, b) << 8 & 0xFFFFFF00;
        int k = tbStreamDataWriter.getBufferedDataSize();
        tbStreamDataWriter.writeInt(j, 4);
        boolean bool1 = this.conn.serverInfo.getObjBindAvailable();
        boolean bool2 = (paramTbPreparedStatementImpl.getParamType(paramInt, b) == 32) ? true : false;
        boolean bool3 = (paramTbPreparedStatementImpl.getParamType(paramInt, b) == 29) ? true : false;
        boolean bool4 = (paramTbPreparedStatementImpl.getParamType(paramInt, b) == 30) ? true : false;
        if (!bool1 && (bool2 || bool3 || bool4))
          throw TbError.newSQLException(-90402); 
        arrayOfBinder[paramInt][b].bind(this.conn, (ParamContainer)paramTbPreparedStatementImpl, tbStreamDataWriter, paramInt, b, bindItem.getLength());
        if (arrayOfBinder[paramInt][b] instanceof ArrayInBinder && ((ArrayInBinder)arrayOfBinder[paramInt][b]).isDFR()) {
          j = 0x8 | paramTbPreparedStatementImpl.getParamType(paramInt, b) << 8 & 0xFFFFFF00;
          tbStreamDataWriter.reWriteInt(k, j, 4);
        } 
      } 
      tbStreamDataWriter.reWriteInt(4, tbStreamDataWriter.getBufferedDataSize() - 16, 4);
      this.stream.flush();
    } 
  }
  
  private TbResultSet processPrefetchedRset(TbStatement paramTbStatement, int paramInt1, int paramInt2, int paramInt3, TbColumnDesc[] paramArrayOfTbColumnDesc, int paramInt4, int paramInt5, int paramInt6, long paramLong) throws SQLException {
    byte[] arrayOfByte = new byte[paramInt4];
    this.stream.readChunkData(arrayOfByte, paramInt4);
    TbResultSet tbResultSet = this.typeConverter.toResultSet(paramInt1, paramInt2, paramInt3, paramArrayOfTbColumnDesc, paramTbStatement, arrayOfByte);
    tbResultSet.setFetchCompleted(paramInt6);
    ((com.tmax.tibero.jdbc.driver.TbResultSet) tbResultSet).setTsn(paramLong);
    tbResultSet.buildRowTable(paramInt5, arrayOfByte);
    return tbResultSet;
  }
  
  public long read(TbBlob paramTbBlob, long paramLong1, byte[] paramArrayOfbyte, long paramLong2, long paramLong3) throws SQLException {
    int i = 0;
    int j = 0;
    long l = 0L;
    do {
      if (paramLong3 - l >= TbLob.getMaxChunkSize()) {
        i = TbLob.getMaxChunkSize();
      } else {
        i = (int)(paramLong3 - l);
      } 
      j = lobRead((TbLob)paramTbBlob, paramLong1 + l, null, paramArrayOfbyte, paramLong2 + l, i);
      l += j;
    } while (paramLong3 > l && i == j);
    return l;
  }
  
  public long read(TbClobBase paramTbClobBase, long paramLong1, char[] paramArrayOfchar, long paramLong2, long paramLong3) throws SQLException {
    int i = 0;
    int j = 0;
    long l1 = 0L;
    byte b = 2;
    long l2 = paramLong1 * b;
    int k = TbLob.getMaxChunkSize() / b;
    do {
      if (paramLong3 - l1 >= k) {
        i = k;
      } else {
        i = (int)(paramLong3 - l1);
      } 
      j = lobRead((TbLob)paramTbClobBase, l2, paramArrayOfchar, null, paramLong2 + l1, i * b);
      l1 += j;
      l2 += (j * b);
    } while (paramLong3 > l1 && !paramTbClobBase.isEndOfStream());
    return l1;
  }
  
  public String readLong(byte[] paramArrayOfbyte) throws SQLException {
    StringBuffer stringBuffer = new StringBuffer();
    TbMsgLongReadReply tbMsgLongReadReply = null;
    byte[] arrayOfByte = new byte[3];
    int i = 0;
    char[] arrayOfChar = new char[65532];
    while (true) {
      synchronized (this.stream) {
        TbMsgSend.LONG_READ(this.stream, 65532, paramArrayOfbyte, paramArrayOfbyte.length);
        TbMsg tbMsg = this.stream.readMsg();
        switch (tbMsg.getMsgType()) {
          case 53:
            tbMsgLongReadReply = (TbMsgLongReadReply)tbMsg;
            paramArrayOfbyte = new byte[tbMsgLongReadReply.longLoc.length];
            System.arraycopy(tbMsgLongReadReply.longLoc, 0, paramArrayOfbyte, 0, paramArrayOfbyte.length);
            if (tbMsgLongReadReply.data != null && tbMsgLongReadReply.data.length != 0) {
              byte[] arrayOfByte1;
              if (i == 0) {
                arrayOfByte1 = new byte[tbMsgLongReadReply.data.length + i];
                System.arraycopy(arrayOfByte, 0, arrayOfByte1, 0, i);
                System.arraycopy(tbMsgLongReadReply.data, 0, arrayOfByte1, i, tbMsgLongReadReply.data.length);
                i = 0;
              } else {
                arrayOfByte1 = tbMsgLongReadReply.data;
              } 
              int j = this.typeConverter.getEndingBytePos(arrayOfByte1, arrayOfByte1.length - 1);
              if (j > arrayOfByte1.length - 1) {
                j = this.typeConverter.getLeadingBytePos(arrayOfByte1, arrayOfByte1.length - 1);
                i = arrayOfByte1.length - j;
                System.arraycopy(arrayOfByte1, j, arrayOfByte, 0, i);
              } 
              int k = this.typeConverter.bytesToChars(arrayOfByte1, 0, j + 1, arrayOfChar, 0, j + 1);
              stringBuffer.append(arrayOfChar, 0, k);
            } 
            break;
          case 76:
            throw getErrorMessage(-90534, tbMsg);
          default:
            throwProtocolError(tbMsg.getMsgType());
            break;
        } 
      } 
      if (tbMsgLongReadReply.isLastData != 0)
        return stringBuffer.toString(); 
    } 
  }
  
  public byte[] readLongRaw(byte[] paramArrayOfbyte) throws SQLException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    TbMsgLongReadReply tbMsgLongReadReply = null;
    while (true) {
      synchronized (this.stream) {
        TbMsgSend.LONG_READ(this.stream, 65532, paramArrayOfbyte, paramArrayOfbyte.length);
        TbMsg tbMsg = this.stream.readMsg();
        switch (tbMsg.getMsgType()) {
          case 53:
            tbMsgLongReadReply = (TbMsgLongReadReply)tbMsg;
            paramArrayOfbyte = new byte[tbMsgLongReadReply.longLoc.length];
            System.arraycopy(tbMsgLongReadReply.longLoc, 0, paramArrayOfbyte, 0, paramArrayOfbyte.length);
            byteArrayOutputStream.write(tbMsgLongReadReply.data, 0, tbMsgLongReadReply.data.length);
            break;
          case 76:
            throw getErrorMessage(-90534, tbMsg);
          default:
            throwProtocolError(tbMsg.getMsgType());
            break;
        } 
      } 
      if (tbMsgLongReadReply.isLastData != 0)
        return byteArrayOutputStream.toByteArray(); 
    } 
  }
  
  public void reset() {
    try {
      if (this.stream != null)
        synchronized (this.stream) {
          this.stream.close();
          this.stream = null;
        }  
    } catch (SQLException sQLException) {}
    if (this.conn != null) {
      this.conn.reset();
      this.conn = null;
    } 
  }
  
  public void resetSession() throws SQLException {
    synchronized (this.stream) {
      TbMsgSend.RESET_SESS(this.stream);
    } 
  }
  
  public void rollback() throws SQLException {
    synchronized (this.stream) {
      TbMsgSend.ROLLBACK(this.stream, null);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 75:
          justOKReply((TbMsgOkReply)tbMsg);
          break;
        case 76:
          throwEreply(-90511, tbMsg);
          break;
        default:
          throwProtocolError(tbMsg.getMsgType());
          break;
      } 
    } 
  }
  
  public void rollback(TbSavepoint paramTbSavepoint) throws SQLException {
    String str = null;
    if (paramTbSavepoint != null)
      try {
        str = paramTbSavepoint.getSavepointName();
      } catch (SQLException sQLException) {
        str = "SVPT" + paramTbSavepoint.getSavepointId();
      }  
    synchronized (this.stream) {
      TbMsgSend.ROLLBACK(this.stream, str);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 75:
          justOKReply((TbMsgOkReply)tbMsg);
          break;
        case 76:
          throwEreply(-90511, tbMsg);
          break;
        default:
          throwProtocolError(tbMsg.getMsgType());
          break;
      } 
    } 
  }
  
  public void setClientInfo(String[] paramArrayOfString) throws SQLClientInfoException {
    HashMap hashMap = new HashMap<Object, Object>();
    for (byte b = 0; b < paramArrayOfString.length; b++)
      hashMap.put(TbConnection.CLIENT_INFO_KEYS[b], ClientInfoStatus.REASON_UNKNOWN); 
    String str1 = TbError.getMsg(-90545);
    String str2 = TbError.getSQLState(-90545);
    throw new SQLClientInfoException(str1, str2, hashMap);
  }
  
  public void setIsolationLevel(int paramInt) throws SQLException {
    int bool = 0;
    if (paramInt == 2) {
      bool = 0;
    } else if (paramInt == 8) {
      bool = 1;
    } else {
      throw TbError.newSQLException(-590722);
    } 
    synchronized (this.stream) {
      TbMsgSend.SET_ISL_LVL(this.stream, bool);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 75:
          justOKReply((TbMsgOkReply)tbMsg);
          break;
        case 76:
          throwEreply(-90513, tbMsg);
          break;
        default:
          throwProtocolError(tbMsg.getMsgType());
          break;
      } 
    } 
  }
  
  public void setSavePoint(TbSavepoint paramTbSavepoint) throws SQLException {
    String str = null;
    try {
      str = paramTbSavepoint.getSavepointName();
    } catch (SQLException sQLException) {
      str = "SVPT" + paramTbSavepoint.getSavepointId();
    } 
    synchronized (this.stream) {
      TbMsgSend.SAVEPT(this.stream, str);
      TbMsg tbMsg = this.stream.readMsg();
      switch (tbMsg.getMsgType()) {
        case 75:
          justOKReply((TbMsgOkReply)tbMsg);
          break;
        case 76:
          throwEreply(-90514, tbMsg);
          break;
        default:
          throwProtocolError(tbMsg.getMsgType());
          break;
      } 
    } 
  }
  
  private void throwEreply(int paramInt, TbMsg paramTbMsg) throws SQLException {
    SQLException sQLException = getErrorMessage(paramInt, paramTbMsg);
    throw sQLException;
  }
  
  private void throwProtocolError(int paramInt) throws SQLException {
    throw TbError.newSQLException(-590727, paramInt);
  }
  
  public void truncate(TbLob paramTbLob, long paramLong) throws SQLException {
    if (paramTbLob instanceof com.tmax.tibero.jdbc.TbNClob) {
      paramLong *= this.typeConverter.getMaxBytesPerNChar();
    } else if (paramTbLob instanceof com.tmax.tibero.jdbc.TbClob) {
      paramLong *= this.typeConverter.getUCS2MaxBytesPerChar();
    } 
    lobTruncate(paramTbLob, paramLong);
  }
  
  public long write(TbBlob paramTbBlob, long paramLong1, byte[] paramArrayOfbyte, long paramLong2, long paramLong3) throws SQLException {
    boolean bool1 = true;
    boolean bool2 = false;
    int i = 0;
    long l = 0L;
    int j = 0;
    if (paramLong3 <= TbLob.getMaxChunkSize()) {
      lobWrite((TbLob)paramTbBlob, paramLong1, null, paramArrayOfbyte, paramLong2, paramLong3, 50331648);
      return paramLong3;
    } 
    while (true) {
      if (paramLong3 - l > TbLob.getMaxChunkSize()) {
        i = TbLob.getMaxChunkSize();
      } else {
        i = (int)(paramLong3 - l);
        bool2 = true;
      } 
      j = 0;
      if (bool1) {
        j = 16777216;
      } else if (bool2) {
        j = 33554432;
      } 
      try {
        lobWrite((TbLob)paramTbBlob, paramLong1 + l, null, paramArrayOfbyte, paramLong2 + l, i, j);
      } catch (SQLException sQLException) {
        throw sQLException;
      } 
      l += i;
      bool1 = false;
      if (paramLong3 <= l)
        return l; 
    } 
  }
  
  public long write(TbClobBase paramTbClobBase, long paramLong1, char[] paramArrayOfchar, long paramLong2, long paramLong3) throws SQLException {
    boolean bool1 = true;
    boolean bool2 = false;
    int i = 0;
    long l1 = 0L;
    int j = 0;
    byte b = 2;
    int k = TbLob.getMaxChunkSize() / b;
    long l2 = paramLong1 * b;
    if (paramLong3 <= k) {
      lobWrite((TbLob)paramTbClobBase, l2, paramArrayOfchar, null, paramLong2, paramLong3, 50331648);
      return paramLong3;
    } 
    while (true) {
      if (paramLong3 - l1 > k) {
        i = k;
      } else {
        i = (int)(paramLong3 - l1);
        bool2 = true;
      } 
      j = 0;
      if (bool1) {
        j = 16777216;
      } else if (bool2) {
        j = 33554432;
      } 
      try {
        lobWrite((TbLob)paramTbClobBase, l2, paramArrayOfchar, null, paramLong2 + l1, i, j);
      } catch (SQLException sQLException) {
        throw sQLException;
      } 
      l1 += i;
      l2 += (i * b);
      bool1 = false;
      if (paramLong3 <= l1)
        return l1; 
    } 
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\comm\TbCommType4.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */