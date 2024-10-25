package com.tmax.tibero.jdbc.rowset;

import com.tmax.tibero.jdbc.err.TbError;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.StringTokenizer;
import javax.sql.RowSet;
import javax.sql.RowSetInternal;
import javax.sql.RowSetWriter;

public class TbCachedRowSetWriter implements RowSetWriter, Serializable {
  private static final long serialVersionUID = -4465449178814496564L;
  
  private StringBuffer updateClause = new StringBuffer("");
  
  private StringBuffer deleteClause = new StringBuffer("");
  
  private StringBuffer insertClause = new StringBuffer("");
  
  private PreparedStatement insertStmt;
  
  private PreparedStatement updateStmt;
  
  private PreparedStatement deleteStmt;
  
  private ResultSetMetaData rsmd;
  
  private transient Connection conn;
  
  private int columnCount;
  
  private boolean deleteRow(RowSet paramRowSet, TbRow paramTbRow) throws SQLException {
    this.deleteStmt.clearParameters();
    for (byte b = 1; b <= this.columnCount; b++) {
      if (paramTbRow.isNull(b))
        return deleteRowWithNull(paramRowSet, paramTbRow); 
      Object object = paramTbRow.getColumn(b);
      if (object == null) {
        this.deleteStmt.setNull(b, this.rsmd.getColumnType(b));
      } else {
        this.deleteStmt.setObject(b, object);
      } 
    } 
    return (this.deleteStmt.executeUpdate() == 1);
  }
  
  private boolean deleteRowWithNull(RowSet paramRowSet, TbRow paramTbRow) throws SQLException {
    StringBuffer stringBuffer = new StringBuffer("DELETE FROM " + getTableName(paramRowSet) + " WHERE ");
    byte b;
    for (b = 1; b <= this.columnCount; b++) {
      if (b != 1)
        stringBuffer.append(" AND "); 
      if (paramTbRow.isNull(b)) {
        stringBuffer.append(this.rsmd.getColumnName(b) + " IS NULL ");
      } else {
        stringBuffer.append(this.rsmd.getColumnName(b) + " = :" + b);
      } 
    } 
    PreparedStatement preparedStatement = null;
    try {
      preparedStatement = this.conn.prepareStatement(stringBuffer.substring(0, stringBuffer.length()));
      byte b1 = 1;
      byte b2 = 1;
      while (b1 <= this.columnCount) {
        if (!paramTbRow.isNull(b1))
          preparedStatement.setObject(b2++, paramTbRow.getColumn(b1)); 
        b1++;
      } 
      b = (preparedStatement.executeUpdate() == 1) ? 1 : 0;
      if (preparedStatement != null)
        preparedStatement.close(); 
    } catch (SQLException sQLException) {
      if (preparedStatement != null)
        preparedStatement.close(); 
      throw sQLException;
    } 
    return b;
  }
  
  private String getTableName(RowSet paramRowSet) throws SQLException {
    String str1 = paramRowSet.getCommand().toUpperCase();
    int i = str1.indexOf(" FROM ");
    if (i == -1)
      throw TbError.newSQLException(-90821); 
    String str2 = str1.substring(i + 6).trim();
    StringTokenizer stringTokenizer = new StringTokenizer(str2);
    if (stringTokenizer.hasMoreTokens())
      str2 = stringTokenizer.nextToken(); 
    return str2;
  }
  
  private void initSQLStatement(RowSet paramRowSet) throws SQLException {
    this.insertClause = new StringBuffer("INSERT INTO " + getTableName(paramRowSet) + "(");
    this.updateClause = new StringBuffer("UPDATE " + getTableName(paramRowSet) + " SET ");
    this.deleteClause = new StringBuffer("DELETE FROM " + getTableName(paramRowSet) + " WHERE ");
    this.rsmd = paramRowSet.getMetaData();
    this.columnCount = this.rsmd.getColumnCount();
    byte b;
    for (b = 0; b < this.columnCount; b++) {
      if (b != 0)
        this.insertClause.append(", "); 
      this.insertClause.append(this.rsmd.getColumnName(b + 1));
      if (b != 0)
        this.updateClause.append(", "); 
      this.updateClause.append(this.rsmd.getColumnName(b + 1) + " = :" + b);
      if (b != 0)
        this.deleteClause.append(" AND "); 
      this.deleteClause.append(this.rsmd.getColumnName(b + 1) + " = :" + b);
    } 
    this.insertClause.append(") VALUES (");
    this.updateClause.append(" WHERE ");
    for (b = 0; b < this.columnCount; b++) {
      if (b != 0)
        this.insertClause.append(", "); 
      this.insertClause.append(":" + b);
      if (b != 0)
        this.updateClause.append(" AND "); 
      this.updateClause.append(this.rsmd.getColumnName(b + 1) + " = :" + b);
    } 
    this.insertClause.append(")");
    this.insertStmt = this.conn.prepareStatement(this.insertClause.substring(0, this.insertClause.length()));
    this.updateStmt = this.conn.prepareStatement(this.updateClause.substring(0, this.updateClause.length()));
    this.deleteStmt = this.conn.prepareStatement(this.deleteClause.substring(0, this.deleteClause.length()));
  }
  
  private boolean insertRow(TbRow paramTbRow) throws SQLException {
    this.insertStmt.clearParameters();
    for (byte b = 1; b <= this.columnCount; b++) {
      Object object = null;
      object = paramTbRow.isColumnChanged(b) ? paramTbRow.getChangedColumn(b) : paramTbRow.getColumn(b);
      if (object == null) {
        this.insertStmt.setNull(b, this.rsmd.getColumnType(b));
      } else {
        this.insertStmt.setObject(b, object);
      } 
    } 
    return (this.insertStmt.executeUpdate() == 1);
  }
  
  private boolean updateRow(RowSet paramRowSet, TbRow paramTbRow) throws SQLException {
    this.updateStmt.clearParameters();
    byte b;
    for (b = 1; b <= this.columnCount; b++) {
      Object object = null;
      object = paramTbRow.isColumnChanged(b) ? paramTbRow.getChangedColumn(b) : paramTbRow.getColumn(b);
      if (object == null) {
        this.updateStmt.setNull(b, this.rsmd.getColumnType(b));
      } else {
        this.updateStmt.setObject(b, object);
      } 
    } 
    for (b = 1; b <= this.columnCount; b++) {
      if (paramTbRow.isNull(b))
        return updateRowWithNull(paramRowSet, paramTbRow); 
      this.updateStmt.setObject(b + this.columnCount, paramTbRow.getColumn(b));
    } 
    return (this.updateStmt.executeUpdate() == 1);
  }
  
  private boolean updateRowWithNull(RowSet paramRowSet, TbRow paramTbRow) throws SQLException {
    StringBuffer stringBuffer = new StringBuffer("UPDATE " + getTableName(paramRowSet) + " SET ");
    byte b;
    for (b = 1; b <= this.columnCount; b++) {
      if (b != 1)
        stringBuffer.append(", "); 
      stringBuffer.append(this.rsmd.getColumnName(b) + " = :" + b);
    } 
    stringBuffer.append(" WHERE ");
    for (b = 1; b <= this.columnCount; b++) {
      if (b != 1)
        stringBuffer.append(" AND "); 
      if (paramTbRow.isNull(b)) {
        stringBuffer.append(this.rsmd.getColumnName(b) + " IS NULL ");
      } else {
        stringBuffer.append(this.rsmd.getColumnName(b) + " = :" + b);
      } 
    } 
    PreparedStatement preparedStatement = null;
    try {
      preparedStatement = this.conn.prepareStatement(stringBuffer.substring(0, stringBuffer.length()));
      byte b1;
      for (b1 = 1; b1 <= this.columnCount; b1++) {
        Object object = null;
        object = paramTbRow.isColumnChanged(b1) ? paramTbRow.getChangedColumn(b1) : paramTbRow.getColumn(b1);
        if (object == null) {
          preparedStatement.setNull(b1, this.rsmd.getColumnType(b1));
        } else {
          preparedStatement.setObject(b1, object);
        } 
      } 
      b1 = 1;
      byte b2 = 1;
      while (b1 <= this.columnCount) {
        if (!paramTbRow.isNull(b1)) {
          preparedStatement.setObject(b2 + this.columnCount, paramTbRow.getColumn(b1));
          b2++;
        } 
        b1++;
      } 
      b = (preparedStatement.executeUpdate() == 1) ? 1 : 0;
      if (preparedStatement != null)
        preparedStatement.close(); 
    } catch (SQLException sQLException) {
      if (preparedStatement != null)
        preparedStatement.close(); 
      throw sQLException;
    } 
    return b;
  }
  
  public synchronized boolean writeData(RowSetInternal paramRowSetInternal) throws SQLException {
    TbCachedRowSet tbCachedRowSet = (TbCachedRowSet)paramRowSetInternal;
    this.conn = ((TbCachedRowSetReader)tbCachedRowSet.getReader()).getConnection(paramRowSetInternal);
    if (this.conn.getAutoCommit())
      this.conn.setAutoCommit(false); 
    this.conn.setTransactionIsolation(tbCachedRowSet.getTransactionIsolation());
    initSQLStatement(tbCachedRowSet);
    if (this.columnCount < 1) {
      this.conn.close();
      return true;
    } 
    boolean bool = tbCachedRowSet.getShowDeleted();
    tbCachedRowSet.setShowDeleted(true);
    tbCachedRowSet.beforeFirst();
    boolean bool1 = true;
    boolean bool2 = true;
    boolean bool3 = true;
    while (tbCachedRowSet.next()) {
      if (tbCachedRowSet.rowInserted()) {
        if (!tbCachedRowSet.rowDeleted()) {
          TbRow tbRow = tbCachedRowSet.getCurrentRow();
          bool2 = (insertRow(tbRow) || bool2) ? true : false;
        } 
        continue;
      } 
      if (tbCachedRowSet.rowUpdated()) {
        TbRow tbRow = tbCachedRowSet.getCurrentRow();
        bool1 = (updateRow(tbCachedRowSet, tbRow) || bool1) ? true : false;
        continue;
      } 
      if (tbCachedRowSet.rowDeleted()) {
        TbRow tbRow = tbCachedRowSet.getCurrentRow();
        bool3 = (deleteRow(tbCachedRowSet, tbRow) || bool3) ? true : false;
      } 
    } 
    if (bool1 && bool2 && bool3) {
      this.conn.commit();
      tbCachedRowSet.setOriginal();
    } else {
      this.conn.rollback();
    } 
    this.insertStmt.close();
    this.updateStmt.close();
    this.deleteStmt.close();
    if (!tbCachedRowSet.isConnectionOpened())
      this.conn.close(); 
    tbCachedRowSet.setShowDeleted(bool);
    return true;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\rowset\TbCachedRowSetWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */