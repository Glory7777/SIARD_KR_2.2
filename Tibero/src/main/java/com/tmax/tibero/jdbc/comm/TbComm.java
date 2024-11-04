package com.tmax.tibero.jdbc.comm;

import com.tmax.tibero.jdbc.TbSQLInfo;
import com.tmax.tibero.jdbc.TbSQLInfo2;
import com.tmax.tibero.jdbc.data.BatchUpdateInfo;
import com.tmax.tibero.jdbc.dpl.TbDirPathStream;
import com.tmax.tibero.jdbc.driver.TbCallableStatementImpl;
import com.tmax.tibero.jdbc.driver.TbPreparedStatementImpl;
import com.tmax.tibero.jdbc.driver.TbResultSet;
import com.tmax.tibero.jdbc.driver.TbResultSetBase;
import com.tmax.tibero.jdbc.driver.TbSavepoint;
import com.tmax.tibero.jdbc.driver.TbStatement;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.Vector;

public interface TbComm {
  int[] batchUpdateLoop(TbPreparedStatementImpl paramTbPreparedStatementImpl, BatchUpdateInfo paramBatchUpdateInfo) throws SQLException;
  
  void cancelStatement() throws SQLException;
  
  void close() throws SQLException;
  
  void closeCursor(TbResultSet paramTbResultSet, int paramInt) throws SQLException;
  
  void closeSession() throws SQLException;
  
  void commit() throws SQLException;
  
  void createStream() throws SQLException;
  
  void describeConnectInfo() throws SQLException;
  
  void describeSessInfo() throws SQLException;
  
  void dirPathAbort() throws SQLException;
  
  void dirPathDataSave(int paramInt) throws SQLException;
  
  void dirPathFinish() throws SQLException;
  
  void dirPathFlushRow() throws SQLException;
  
  void dirPathLoadStream(TbDirPathStream paramTbDirPathStream, TbStreamDataWriter paramTbStreamDataWriter, int paramInt) throws SQLException;
  
  void dirPathPrepare(TbDirPathStream paramTbDirPathStream) throws SQLException;
  
  void dirPathPrepareParallel(TbDirPathStream paramTbDirPathStream) throws SQLException;
  
  int execute(TbPreparedStatementImpl paramTbPreparedStatementImpl, String paramString, int paramInt) throws SQLException;
  
  int executeDirect(TbStatement paramTbStatement, String paramString) throws SQLException;
  
  void executePivot(TbCallableStatementImpl paramTbCallableStatementImpl, int paramInt1, int paramInt2) throws SQLException;
  
  void fetch(TbStatement paramTbStatement, TbResultSetBase paramTbResultSetBase) throws SQLException;
  
  TbResultSet describeCSRReply(TbStatement paramTbStatement, int paramInt) throws SQLException;
  
  SQLException getErrorMessage(int paramInt, TbMsg paramTbMsg) throws SQLException;
  
  TbSQLInfo getLastExecutedSqlinfo() throws SQLException;
  
  TbSQLInfo2 getLastExecutedSqlinfo2() throws SQLException;
  
  TbStream getStream();
  
  SQLWarning getWarningMessage() throws SQLException;
  
  void logon(boolean paramBoolean) throws SQLException;
  
  void openSession() throws SQLException;
  
  void ping() throws SQLException;
  
  void prepare(TbPreparedStatementImpl paramTbPreparedStatementImpl, String paramString, Vector<Integer> paramVector) throws SQLException;
  
  int prepareExecute(TbPreparedStatementImpl paramTbPreparedStatementImpl, String paramString, int paramInt) throws SQLException;
  
  String readLong(byte[] paramArrayOfbyte) throws SQLException;
  
  byte[] readLongRaw(byte[] paramArrayOfbyte) throws SQLException;
  
  void reset();
  
  void resetSession() throws SQLException;
  
  void rollback() throws SQLException;
  
  void rollback(TbSavepoint paramTbSavepoint) throws SQLException;
  
  void setClientInfo(String[] paramArrayOfString) throws SQLClientInfoException;
  
  void setIsolationLevel(int paramInt) throws SQLException;
  
  void setSavePoint(TbSavepoint paramTbSavepoint) throws SQLException;
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\comm\TbComm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */