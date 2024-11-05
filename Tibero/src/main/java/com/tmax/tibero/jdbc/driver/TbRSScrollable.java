package com.tmax.tibero.jdbc.driver;

import com.tmax.tibero.jdbc.data.BindData;
import com.tmax.tibero.jdbc.data.Row;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.util.TbSQLTypeScanner;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.LinkedList;

public class TbRSScrollable extends TbResultSetBase {
  protected AbstractList<Row> rows = null;
  
  private int chunkRowDirOffset;
  
  protected TbRSScrollable(TbStatement paramTbStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfbyte) throws SQLException {
    super(paramTbStatement, paramInt1, paramInt2, paramInt3, paramArrayOfbyte);
    this.rows = new LinkedList<Row>();
    this.currentRowIndex = -1;
    paramTbStatement.conn.addFOActiveResultSet(this);
  }
  
  public synchronized boolean absolute(int paramInt) throws SQLException {
    if (paramInt == 0)
      throw TbError.newSQLException(-590736); 
    if (isRSEmpty())
      return false; 
    if (paramInt > 0) {
      this.currentRowIndex = paramInt - 1;
    } else {
      if (!this.fetchComplete)
        fetchRowsAll(); 
      this.currentRowIndex = this.rowsFetchedCnt + paramInt;
    } 
    return isValidRowIndex(this.currentRowIndex);
  }
  
  public synchronized void afterLast() throws SQLException {
    if (!isRSEmpty()) {
      if (!this.fetchComplete)
        fetchRowsAll(); 
      if (this.stmt.getMaxRows() != 0 && this.currentRowIndex >= this.stmt.getMaxRows()) {
        this.currentRowIndex = this.stmt.getMaxRows();
      } else {
        this.currentRowIndex = this.rowsFetchedCnt;
      } 
    } 
  }
  
  public synchronized void beforeFirst() throws SQLException {
    if (!isRSEmpty())
      this.currentRowIndex = -1; 
  }
  
  public byte[] getRowChunk(int paramInt) {
    this.rowChunk = new byte[paramInt];
    return this.rowChunk;
  }
  
  public void buildRowTable(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
    Row[] arrayOfRow = new Row[paramInt];
    int i = 1;
    if (this.rowsFetchedCnt + paramInt < 0)
      throw TbError.newSQLException(-90613); 
    this.currentFetchCount = paramInt;
    this.rowsFetchedCnt += paramInt;
    for (byte b = 0; b < paramInt; b++) {
      arrayOfRow[b] = new Row(this.columnCount);
      i += arrayOfRow[b].buildRowData(paramArrayOfbyte, i, this.cols);
      this.rows.add(arrayOfRow[b]);
    } 
    this.chunkRowDirOffset = i;
  }
  
  protected void checkRowIndex(int paramInt) throws SQLException {
    if (paramInt < 0)
      throw TbError.newSQLException(-90635); 
    if ((this.stmt.getMaxRows() != 0 && paramInt >= this.stmt.getMaxRows()) || paramInt >= this.rowsFetchedCnt)
      throw TbError.newSQLException(-90624); 
  }
  
  protected void fetchRowsAll() throws SQLException {
    while (this.stmt.getMaxRows() == 0 && this.currentRowIndex < this.stmt.getMaxRows() && !this.fetchComplete)
      fetchRowsChunk(); 
  }
  
  public synchronized boolean first() throws SQLException {
    if (isRSEmpty())
      return false; 
    this.currentRowIndex = 0;
    return isValidRowIndex(this.currentRowIndex);
  }
  
  protected Row getCurrentRow() throws SQLException {
    if (this.currentRowIndex < 0)
      throw TbError.newSQLException(-90635); 
    if (this.currentRowIndex >= this.rowsFetchedCnt)
      throw TbError.newSQLException(-90624); 
    return this.rows.get(this.currentRowIndex);
  }
  
  protected Row getRowAt(int paramInt) throws SQLException {
    return this.rows.get(paramInt);
  }
  
  public synchronized boolean isLast() throws SQLException {
    if (this.stmt.getMaxRows() != 0 && this.currentRowIndex + 1 == this.stmt.getMaxRows())
      return true; 
    if (this.currentRowIndex + 1 == this.rowsFetchedCnt) {
      if (this.fetchComplete)
        return true; 
      fetchRowsChunk();
      return (this.fetchComplete && this.currentRowIndex + 1 == this.rowsFetchedCnt);
    } 
    return false;
  }
  
  protected boolean isRSEmpty() throws SQLException {
    return (this.rowsFetchedCnt != 0) ? false : ((this.rowsFetchedCnt == 0 && this.fetchComplete) ? true : (!isValidRowIndex(0)));
  }
  
  protected boolean isValidRowIndex(int paramInt) throws SQLException {
    if (this.stmt.getMaxRows() != 0 && paramInt >= this.stmt.getMaxRows())
      return false; 
    if (paramInt >= 0 && paramInt < this.rowsFetchedCnt)
      return true; 
    if (paramInt < 0)
      return false; 
    if (paramInt >= this.rowsFetchedCnt) {
      if (this.fetchComplete)
        return false; 
      while (paramInt >= this.rowsFetchedCnt) {
        if (!this.fetchComplete) {
          this.rowChunk = null;
          fetchRowsChunk();
          continue;
        } 
        return false;
      } 
      return true;
    } 
    return false;
  }
  
  public synchronized boolean last() throws SQLException {
    if (isRSEmpty())
      return false; 
    if (!this.fetchComplete)
      fetchRowsAll(); 
    this.currentRowIndex = this.rowsFetchedCnt - 1;
    if (this.stmt.getMaxRows() != 0 && this.currentRowIndex >= this.stmt.getMaxRows())
      this.currentRowIndex = this.stmt.getMaxRows() - 1; 
    return isValidRowIndex(this.currentRowIndex);
  }
  
  public synchronized boolean next() throws SQLException {
    if (this.currentRowIndex < 0) {
      this.currentRowIndex = 0;
    } else {
      this.currentRowIndex++;
    } 
    return isValidRowIndex(this.currentRowIndex);
  }
  
  public synchronized boolean previous() throws SQLException {
    if (isRSEmpty())
      return false; 
    if (this.currentRowIndex > 0 && !isValidRowIndex(this.currentRowIndex)) {
      this.currentRowIndex = this.rowsFetchedCnt - 1;
    } else {
      this.currentRowIndex--;
    } 
    return isValidRowIndex(this.currentRowIndex);
  }

  @Override
  public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
    return null;
  }

  @Override
  public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
    return null;
  }

  protected void recover() throws SQLException {
    setFOECode(0);
    if (this.stmt == null)
      throw TbError.newSQLException(-90702, "stmt=null"); 
    if (!TbSQLTypeScanner.isQueryStmt(this.stmt.getSqlType()))
      throw TbError.newSQLException(-90702, "sqlType=" + this.stmt.getSqlType()); 
    if (this.stmt.getRealRsetType().isSensitive() || this.stmt.getRealRsetType().isUpdatable())
      throw TbError.newSQLException(-90702, "realRsetType=" + this.stmt.getRealRsetType()); 
    if (this.stmt.isReturnAutoGeneratedKeys())
      throw TbError.newSQLException(-90702, "autoGenKeys=true"); 
    if (this.stmt instanceof TbPreparedStatementImpl) {
      TbPreparedStatementImpl tbPreparedStatementImpl = (TbPreparedStatementImpl)this.stmt;
      if (tbPreparedStatementImpl.getBatchRowCount() > 0)
        throw TbError.newSQLException(-90702, "BatchRowCnt=" + tbPreparedStatementImpl.getBatchRowCount()); 
      BindData bindData = tbPreparedStatementImpl.getBindData();
      if (bindData.getDFRParameterCnt() > 0)
        throw TbError.newSQLException(-90702, "DFRParamCnt=" + bindData.getDFRParameterCnt()); 
      tbPreparedStatementImpl.setPPID((byte[])null);
      try {
        tbPreparedStatementImpl.conn.getTbComm().prepareExecute(tbPreparedStatementImpl, tbPreparedStatementImpl.getOriginalSql(), 0);
      } catch (Exception exception) {
        throw TbError.newSQLException(-90701, exception);
      } 
    } else {
      try {
        this.stmt.conn.getTbComm().executeDirect(this.stmt, this.stmt.getOriginalSql());
      } catch (Exception exception) {
        throw TbError.newSQLException(-90701, exception);
      } 
    } 
    if (this.stmt.currentRs == null)
      throw TbError.newSQLException(-90702, "failoverRset=null"); 
    if (!(this.stmt.currentRs instanceof TbRSScrollable))
      throw TbError.newSQLException(-90702, "failoverRsetClass=" + this.stmt.currentRs.getClass().getName()); 
    TbRSScrollable tbRSScrollable = (TbRSScrollable)this.stmt.currentRs;
    byte[] arrayOfByte = null;
    Iterator<Row> iterator = this.rows.iterator();
    while (iterator.hasNext() && tbRSScrollable.next()) {
      Row row = iterator.next();
      if (arrayOfByte != tbRSScrollable.rowChunk) {
        arrayOfByte = tbRSScrollable.rowChunk;
        int i = tbRSScrollable.chunkRowDirOffset;
        long l = -1L;
        Object object = row.getRowChunk(1);
        byte[] arrayOfByte1;
        if (object instanceof byte[] && (arrayOfByte1 = (byte[])object).length > i) {
          l = getCurrentChunkCRC(arrayOfByte1, i, 0L);
          l = getCurrentChunkCRC(arrayOfByte, i, l);
        } 
        if (l != 0L)
          throw TbError.newSQLException(-90702, "failoverRset invalid."); 
      } 
    } 
    this.csrID = tbRSScrollable.csrID;
    this.fetchComplete = tbRSScrollable.fetchComplete;
    this.stmt.setResultSet(this);
    this.typeConverter = this.stmt.conn.getTypeConverter();
    tbRSScrollable.reset();
  }
  
  public synchronized boolean relative(int paramInt) throws SQLException {
    if (isRSEmpty())
      return false; 
    if (isValidRowIndex(this.currentRowIndex)) {
      this.currentRowIndex += paramInt;
      return isValidRowIndex(this.currentRowIndex);
    } 
    throw TbError.newSQLException(-90624);
  }
  
  protected void removeCurrentRow() throws SQLException {
    checkRowIndex(this.currentRowIndex);
    this.rows.remove(this.currentRowIndex);
    this.currentRowIndex--;
    this.rowsFetchedCnt--;
  }
  
  public void reset() {
    super.reset();
    if (this.rows != null) {
      this.rows.clear();
      this.rows = null;
    } 
  }
  
  protected void setRowAt(int paramInt, Row paramRow) throws SQLException {
    this.rows.set(paramInt, paramRow);
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\driver\TbRSScrollable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */