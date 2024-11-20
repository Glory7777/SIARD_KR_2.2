package com.tmax.tibero.jdbc.driver;

import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.data.Row;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.util.TbSQLParser;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TbRSSensitive extends TbRSScrollable {
  protected TbPreparedStatement refetchStmt = null;
  
  private int[] rowIndices = null;
  
  private List<byte[]> refetchRowids = null;
  
  private int lastRowIdParamCnt = 0;
  
  private int startRowIndex = -1;
  
  private int stopRowIndex = -1;
  
  private static final int REFRESH_FETCH_SIZE = 1;
  
  protected TbRSSensitive(TbStatement paramTbStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfbyte) throws SQLException {
    super(paramTbStatement, paramInt1, paramInt2, paramInt3, paramArrayOfbyte);
    this.rowIndices = new int[paramTbStatement.getFetchSize()];
    this.refetchRowids = (List)new ArrayList<>(paramTbStatement.getFetchSize());
  }
  
  public synchronized boolean absolute(int paramInt) throws SQLException {
    if (super.absolute(paramInt)) {
      refreshRow();
      return true;
    } 
    return false;
  }
  
  private void buildRefetchStatement(int paramInt) throws SQLException {
    if (this.lastRowIdParamCnt != paramInt) {
      if (this.refetchStmt != null)
        try {
          this.refetchStmt.close();
        } catch (SQLException sQLException) {
          addWarning(TbError.newSQLWarning(-590708, sQLException));
        } finally {
          this.refetchStmt = null;
        }  
      String str = TbSQLParser.getRowIdAddedRefetchSql(this.stmt.getSqlWithRowId(), paramInt);
      this.refetchStmt = new TbPreparedStatement(this.stmt.conn, str);
      this.lastRowIdParamCnt = paramInt;
      if (this.stmt instanceof ParamContainer)
        this.refetchStmt.impl().copyBindParamInfo((ParamContainer)this.stmt); 
    } 
    int i = this.refetchStmt.getParamContainer().getParameterCnt();
    int j = i - this.lastRowIdParamCnt;
    for (byte b = 0; b < this.lastRowIdParamCnt; b++)
      this.refetchStmt.setBytes(j + b + 1, 3, this.refetchRowids.get(b)); 
  }
  
  public synchronized void close() throws SQLException {
    try {
      super.close();
    } finally {
      if (this.refetchStmt != null)
        try {
          this.refetchStmt.close();
        } catch (Exception exception) {
        
        } finally {
          this.refetchStmt = null;
        }  
    } 
  }
  
  protected void fetchRowsChunk() throws SQLException {
    super.fetchRowsChunk();
    if (this.currentFetchCount > 0) {
      this.stopRowIndex = this.rowsFetchedCnt - 1;
      this.startRowIndex = this.rowsFetchedCnt - this.currentFetchCount;
    } 
  }
  
  private int fillRowIndexForRefetch(int paramInt) throws SQLException {
    byte b = 0;
    if (this.fetchDirection == 1001) {
      int i = Math.max(this.currentRowIndex - paramInt, 0);
      int j = this.currentRowIndex;
      while (j > i) {
        this.rowIndices[b] = j;
        this.refetchRowids.add(b, getRowAt(j).getRawBytes(1));
        j--;
        b++;
      } 
    } else {
      int i = Math.min(this.currentRowIndex + paramInt, this.rowsFetchedCnt);
      int j = this.currentRowIndex;
      while (j < i) {
        this.rowIndices[b] = j;
        this.refetchRowids.add(b, getRowAt(j).getRawBytes(1));
        j++;
        b++;
      } 
    } 
    return b;
  }
  
  public synchronized boolean first() throws SQLException {
    if (super.first()) {
      refreshRow();
      return true;
    } 
    return false;
  }
  
  private boolean isByteArrayEqual(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    if (paramArrayOfbyte1.length != paramArrayOfbyte2.length)
      return false; 
    for (byte b = 0; b < paramArrayOfbyte1.length; b++) {
      if (paramArrayOfbyte1[b] != paramArrayOfbyte2[b])
        return false; 
    } 
    return true;
  }
  
  public synchronized boolean last() throws SQLException {
    if (super.last()) {
      refreshRow();
      return true;
    } 
    return false;
  }
  
  public synchronized boolean next() throws SQLException {
    if (super.next()) {
      refreshRow();
      return true;
    } 
    return false;
  }
  
  public synchronized boolean previous() throws SQLException {
    if (super.previous()) {
      refreshRow();
      return true;
    } 
    return false;
  }
  
  private void refreshCachedRows(int paramInt) throws SQLException {
    this.refetchStmt.setFetchSize(paramInt);
    TbRSFwOnly tbRSFwOnly = (TbRSFwOnly)this.refetchStmt.executeQuery();
    while (tbRSFwOnly.next()) {
      byte[] arrayOfByte = tbRSFwOnly.getBytes(1);
      for (byte b = 0; b < paramInt; b++) {
        if (isByteArrayEqual(arrayOfByte, this.refetchRowids.get(b))) {
          Row row1 = tbRSFwOnly.getCurrentRow();
          Row row2 = new Row();
          row2.duplicate(row1);
          setRowAt(this.rowIndices[b], row2);
          break;
        } 
      } 
    } 
    try {
      tbRSFwOnly.close();
    } catch (SQLException sQLException) {
      addWarning(TbError.newSQLWarning(-590708, sQLException));
    } 
  }
  
  protected boolean isCurrentWindow(int paramInt) {
    return (paramInt >= this.startRowIndex && paramInt <= this.stopRowIndex);
  }
  
  public synchronized void refreshRow() throws SQLException {
    int i = refreshRowForced(1);
    if (this.fetchDirection == 1000) {
      this.startRowIndex = this.currentRowIndex;
      this.stopRowIndex = this.currentRowIndex + i - 1;
    } else {
      this.startRowIndex = this.currentRowIndex - i + 1;
      this.stopRowIndex = this.currentRowIndex;
    } 
  }
  
  protected int refreshRowForced(int paramInt) throws SQLException {
    if (!this.rsetType.useRowId())
      throw TbError.newSQLException(-590724); 
    try {
      int i = fillRowIndexForRefetch(paramInt);
      buildRefetchStatement(i);
      refreshCachedRows(i);
      return i;
    } catch (SQLException sQLException) {
      throw TbError.newSQLException(-90626, sQLException);
    } 
  }
  
  public synchronized boolean relative(int paramInt) throws SQLException {
    if (super.relative(paramInt)) {
      refreshRow();
      return true;
    } 
    return false;
  }
  
  protected void removeCurrentRow() throws SQLException {
    checkRowIndex(this.currentRowIndex);
    this.rows.remove(this.currentRowIndex);
    if (this.currentRowIndex < this.startRowIndex)
      this.startRowIndex--; 
    if (this.currentRowIndex < this.stopRowIndex)
      this.stopRowIndex--; 
    this.currentRowIndex--;
    this.rowsFetchedCnt--;
  }
  
  public void reset() {
    super.reset();
    this.refetchStmt = null;
  }
  
  protected boolean isFoCsrEnabled() {
    return false;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\driver\TbRSSensitive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */