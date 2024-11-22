package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgFactory {
  public static TbMsg createMessage(int paramInt) throws SQLException {
    switch (paramInt) {
      case 0:
        return new TbMsgConnectReply();
      case 2:
        return new TbMsgSessInfoReply();
      case 4:
        return new TbMsgPrepareReply();
      case 8:
        return new TbMsgExecuteRsetReply();
      case 9:
        return new TbMsgExecutePivotReply();
      case 10:
        return new TbMsgExecuteRsetNoDescReply();
      case 11:
        return new TbMsgExecutePrefetchReply();
      case 12:
        return new TbMsgExecutePrefetchNoDescReply();
      case 13:
        return new TbMsgExecuteCountReply();
      case 14:
        return new TbMsgExecuteCallReply();
      case 15:
        return new TbMsgExecutePsmReply();
      case 16:
        return new TbMsgExecuteNeedDataReply();
      case 17:
        return new TbMsgExecuteInvalidPlanReply();
      case 19:
        return new TbMsgFetchReply();
      case 21:
        return new TbMsgFetchPivotReply();
      case 32:
        return (TbMsg)new TbMsgBatchUpdateReply();
      case 34:
        return new TbMsgLobLengthReply();
      case 36:
        return new TbMsgLobReadReply();
      case 38:
        return new TbMsgLobTruncReply();
      case 40:
        return new TbMsgLobInstrReply();
      case 42:
        return new TbMsgLobWriteReply();
      case 44:
        return new TbMsgLobCreateTempReply();
      case 47:
        return new TbMsgLobInlobReply();
      case 49:
        return new TbMsgLobOpenReply();
      case 51:
        return new TbMsgLobCloseReply();
      case 53:
        return new TbMsgLongReadReply();
      case 56:
        return new TbMsgDplPrepareReply();
      case 58:
        return (TbMsg)new TbMsgDplLoadStreamReply();
      case 70:
        return new TbMsgXaRecoverReply();
      case 74:
        return new TbMsgGetLastExecutedSqlinfoReply();
      case 75:
        return new TbMsgOkReply();
      case 76:
        return (TbMsg)new TbMsgEreply();
      case 136:
        return new TbMsgDblinkHeteroInfoReply();
      case 140:
        return (TbMsg)new TbMsgExtprocExecuteReply();
      case 144:
        return new TbMsgReadinessToAcceptSslConn();
      case 165:
        return new TbMsgDblinkLogonReply();
      case 173:
        return new TbMsgModifyTcpSndbufSizeReply();
      case 174:
        return new TbMsgModifyTcpRcvbufSizeReply();
      case 177:
        return new TbMsgGetTcpSndbufSizeReply();
      case 178:
        return new TbMsgGetTcpRcvbufSizeReply();
      case 183:
        return new TbMsgExecutePsmPrefetchReply();
      case 186:
        return new TbMsgExecuteUdtCallReply();
      case 187:
        return new TbMsgExecuteUdtPsmReply();
      case 249:
        return new TbMsgGetLastExecutedSqlinfo2Reply();
      case 283:
        return new TbMsgSesskeyReply();
      case 286:
        return (TbMsg)new TbMsgBatchRsetReply();
      case 327:
        return new TbMsgObsGreetingReply();
      case 351:
        return new TbMsgHlDown();
      case 352:
        return new TbMsgHlAdminToHyperData();
      case 353:
        return new TbMsgHlHyperDataToAdmin();
      case 354:
        return new TbMsgHlUserCustom();
      case 355:
        return new TbMsgHlSetBufferCount();
      case 356:
        return new TbMsgHlCreateBufferPool();
      case 357:
        return new TbMsgHlInitiateLoad();
      case 358:
        return new TbMsgHlInitiateLoadReply();
      case 359:
        return new TbMsgHlLoadData();
      case 360:
        return new TbMsgHlLoadDataWithLob();
      case 361:
        return new TbMsgHlLoadDataReply();
      case 362:
        return new TbMsgHlLogInfoArray();
      case 363:
        return new TbMsgHlFinishLoad();
      case 364:
        return new TbMsgHlFinishLoadReply();
      case 365:
        return new TbMsgHlAdminConnect();
      case 366:
        return new TbMsgHlActive();
      case 367:
        return new TbMsgHlActiveReply();
      case 368:
        return new TbMsgHlAdminUpdateStatus();
      case 372:
        return new TbMsgHlDbConnectRequest();
      case 373:
        return new TbMsgHlDbConnectReply();
      case 374:
        return new TbMsgHlTableExtractRequest();
      case 375:
        return new TbMsgHlInitiateAdaptiveLoad();
      case 376:
        return new TbMsgHlAdaptiveLoadData();
      case 377:
        return new TbMsgHlFinishAdaptiveLoad();
      case 379:
        return new TbMsgHssvrGreetingReply();
    } 
    throw TbError.newSQLException(-590728, paramInt);
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbMsgFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */