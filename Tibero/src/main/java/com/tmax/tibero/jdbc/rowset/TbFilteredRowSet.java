package com.tmax.tibero.jdbc.rowset;

import com.tmax.tibero.jdbc.err.TbError;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.Predicate;

public class TbFilteredRowSet extends TbWebRowSet implements FilteredRowSet {
  private static final long serialVersionUID = 8256521646635679356L;
  
  private Predicate predicate;
  
  private boolean isOnInsert;
  
  public boolean absolute(int paramInt) throws SQLException {
    if (this.rowsetType == 1003)
      throw TbError.newSQLException(-590761); 
    if (paramInt == 0 || Math.abs(paramInt) > this.rowCount)
      return false; 
    int i = (paramInt >= 0) ? paramInt : (this.rowCount + paramInt + 1);
    byte b = 0;
    this.currentRowIndex = 0;
    while (b < i && this.currentRowIndex <= this.rowCount) {
      if (next()) {
        b++;
        continue;
      } 
      return false;
    } 
    if (b == i) {
      notifyCursorMoved();
      return true;
    } 
    return false;
  }
  
  protected void checkAndFilterObject(int paramInt, Object paramObject) throws SQLException {
    if (this.predicate != null && !this.predicate.evaluate(paramObject, paramInt))
      throw TbError.newSQLException(-90864); 
  }
  
  public Predicate getFilter() {
    return this.predicate;
  }
  
  public void insertRow() throws SQLException {
    this.isOnInsert = false;
    super.insertRow();
  }
  
  public void moveToInsertRow() throws SQLException {
    this.isOnInsert = true;
    super.moveToInsertRow();
  }
  
  public boolean next() throws SQLException {
    if (this.rowCount <= 0)
      return false; 
    if (this.currentRowIndex >= this.rowCount)
      return false; 
    boolean bool = false;
    while (true) {
      this.currentRowIndex++;
      if (this.predicate != null && (this.predicate == null || !this.predicate.evaluate(this))) {
        if (this.currentRowIndex > this.rowCount)
          break; 
        continue;
      } 
      bool = true;
      break;
    } 
    if (bool) {
      notifyCursorMoved();
      return true;
    } 
    return false;
  }
  
  public boolean previous() throws SQLException {
    if (this.rowsetType == 1003)
      throw TbError.newSQLException(-590761); 
    if (this.rowCount <= 0)
      return false; 
    if (this.currentRowIndex <= 1)
      return false; 
    boolean bool = false;
    while (true) {
      this.currentRowIndex--;
      if (this.predicate != null && (this.predicate == null || !this.predicate.evaluate(this))) {
        if (this.currentRowIndex < 1)
          break; 
        continue;
      } 
      bool = true;
      break;
    } 
    if (bool) {
      notifyCursorMoved();
      return true;
    } 
    return false;
  }
  
  public void setFilter(Predicate paramPredicate) throws SQLException {
    this.predicate = paramPredicate;
  }
  
  public void updateArray(int paramInt, Array paramArray) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramArray); 
    super.updateArray(paramInt, paramArray);
  }
  
  public void updateArray(String paramString, Array paramArray) throws SQLException {
    updateArray(findColumn(paramString), paramArray);
  }
  
  public void updateAsciiStream(int paramInt, InputStream paramInputStream) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramInputStream); 
    super.updateAsciiStream(paramInt, paramInputStream);
  }
  
  public void updateAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt1, paramInputStream); 
    super.updateAsciiStream(paramInt1, paramInputStream, paramInt2);
  }
  
  public void updateAsciiStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramInputStream); 
    super.updateAsciiStream(paramInt, paramInputStream, paramLong);
  }
  
  public void updateAsciiStream(String paramString, InputStream paramInputStream) throws SQLException {
    updateAsciiStream(findColumn(paramString), paramInputStream);
  }
  
  public void updateAsciiStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
    updateAsciiStream(findColumn(paramString), paramInputStream, paramInt);
  }
  
  public void updateAsciiStream(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
    updateAsciiStream(findColumn(paramString), paramInputStream, paramLong);
  }
  
  public void updateBigDecimal(int paramInt, BigDecimal paramBigDecimal) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramBigDecimal); 
    super.updateBigDecimal(paramInt, paramBigDecimal);
  }
  
  public void updateBigDecimal(String paramString, BigDecimal paramBigDecimal) throws SQLException {
    updateBigDecimal(findColumn(paramString), paramBigDecimal);
  }
  
  public void updateBinaryStream(int paramInt, InputStream paramInputStream) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramInputStream); 
    super.updateBinaryStream(paramInt, paramInputStream);
  }
  
  public void updateBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt1, paramInputStream); 
    super.updateBinaryStream(paramInt1, paramInputStream, paramInt2);
  }
  
  public void updateBinaryStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramInputStream); 
    super.updateBinaryStream(paramInt, paramInputStream, paramLong);
  }
  
  public void updateBinaryStream(String paramString, InputStream paramInputStream) throws SQLException {
    updateBinaryStream(findColumn(paramString), paramInputStream);
  }
  
  public void updateBinaryStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
    updateBinaryStream(findColumn(paramString), paramInputStream, paramInt);
  }
  
  public void updateBinaryStream(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
    updateBinaryStream(findColumn(paramString), paramInputStream, paramLong);
  }
  
  public void updateBlob(int paramInt, Blob paramBlob) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramBlob); 
    super.updateBlob(paramInt, paramBlob);
  }
  
  public void updateBlob(int paramInt, InputStream paramInputStream) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramInputStream); 
    super.updateBlob(paramInt, paramInputStream);
  }
  
  public void updateBlob(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramInputStream); 
    super.updateBlob(paramInt, paramInputStream, paramLong);
  }
  
  public void updateBlob(String paramString, Blob paramBlob) throws SQLException {
    updateBlob(findColumn(paramString), paramBlob);
  }
  
  public void updateBlob(String paramString, InputStream paramInputStream) throws SQLException {
    updateBlob(findColumn(paramString), paramInputStream);
  }
  
  public void updateBlob(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
    updateBlob(findColumn(paramString), paramInputStream, paramLong);
  }
  
  public void updateBoolean(int paramInt, boolean paramBoolean) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, new Boolean(paramBoolean)); 
    super.updateBoolean(paramInt, paramBoolean);
  }
  
  public void updateBoolean(String paramString, boolean paramBoolean) throws SQLException {
    updateBoolean(findColumn(paramString), paramBoolean);
  }
  
  public void updateByte(int paramInt, byte paramByte) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, new Byte(paramByte)); 
    super.updateByte(paramInt, paramByte);
  }
  
  public void updateByte(String paramString, byte paramByte) throws SQLException {
    updateByte(findColumn(paramString), paramByte);
  }
  
  public void updateBytes(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramArrayOfbyte); 
    super.updateBytes(paramInt, paramArrayOfbyte);
  }
  
  public void updateBytes(String paramString, byte[] paramArrayOfbyte) throws SQLException {
    updateBytes(findColumn(paramString), paramArrayOfbyte);
  }
  
  public void updateCharacterStream(int paramInt, Reader paramReader) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramReader); 
    super.updateCharacterStream(paramInt, paramReader);
  }
  
  public void updateCharacterStream(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt1, paramReader); 
    super.updateCharacterStream(paramInt1, paramReader, paramInt2);
  }
  
  public void updateCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramReader); 
    super.updateCharacterStream(paramInt, paramReader, paramLong);
  }
  
  public void updateCharacterStream(String paramString, Reader paramReader) throws SQLException {
    updateCharacterStream(findColumn(paramString), paramReader);
  }
  
  public void updateCharacterStream(String paramString, Reader paramReader, int paramInt) throws SQLException {
    updateCharacterStream(findColumn(paramString), paramReader, paramInt);
  }
  
  public void updateCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
    updateCharacterStream(findColumn(paramString), paramReader, paramLong);
  }
  
  public void updateClob(int paramInt, Clob paramClob) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramClob); 
    super.updateClob(paramInt, paramClob);
  }
  
  public void updateClob(int paramInt, Reader paramReader) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramReader); 
    super.updateClob(paramInt, paramReader);
  }
  
  public void updateClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramReader); 
    super.updateClob(paramInt, paramReader, paramLong);
  }
  
  public void updateClob(String paramString, Clob paramClob) throws SQLException {
    updateClob(findColumn(paramString), paramClob);
  }
  
  public void updateClob(String paramString, Reader paramReader) throws SQLException {
    updateClob(findColumn(paramString), paramReader);
  }
  
  public void updateClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
    updateClob(findColumn(paramString), paramReader, paramLong);
  }
  
  public void updateDate(int paramInt, Date paramDate) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramDate); 
    super.updateDate(paramInt, paramDate);
  }
  
  public void updateDate(String paramString, Date paramDate) throws SQLException {
    updateDate(findColumn(paramString), paramDate);
  }
  
  public void updateDouble(int paramInt, double paramDouble) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, new Double(paramDouble)); 
    super.updateDouble(paramInt, paramDouble);
  }
  
  public void updateDouble(String paramString, double paramDouble) throws SQLException {
    updateDouble(findColumn(paramString), paramDouble);
  }
  
  public void updateFloat(int paramInt, float paramFloat) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, new Float(paramFloat)); 
    super.updateFloat(paramInt, paramFloat);
  }
  
  public void updateFloat(String paramString, float paramFloat) throws SQLException {
    updateFloat(findColumn(paramString), paramFloat);
  }
  
  public void updateInt(int paramInt1, int paramInt2) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt1, new Integer(paramInt2)); 
    super.updateInt(paramInt1, paramInt2);
  }
  
  public void updateInt(String paramString, int paramInt) throws SQLException {
    updateInt(findColumn(paramString), paramInt);
  }
  
  public void updateLong(int paramInt, long paramLong) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, new Long(paramLong)); 
    super.updateLong(paramInt, paramLong);
  }
  
  public void updateLong(String paramString, long paramLong) throws SQLException {
    updateLong(findColumn(paramString), paramLong);
  }
  
  public void updateNCharacterStream(int paramInt, Reader paramReader) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramReader); 
    super.updateNCharacterStream(paramInt, paramReader);
  }
  
  public void updateNCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramReader); 
    super.updateNCharacterStream(paramInt, paramReader, paramLong);
  }
  
  public void updateNCharacterStream(String paramString, Reader paramReader) throws SQLException {
    updateNCharacterStream(findColumn(paramString), paramReader);
  }
  
  public void updateNCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
    updateNCharacterStream(findColumn(paramString), paramReader, paramLong);
  }
  
  public void updateNClob(int paramInt, NClob paramNClob) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramNClob); 
    super.updateNClob(paramInt, paramNClob);
  }
  
  public void updateNClob(int paramInt, Reader paramReader) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramReader); 
    super.updateNClob(paramInt, paramReader);
  }
  
  public void updateNClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramReader); 
    super.updateNClob(paramInt, paramReader, paramLong);
  }
  
  public void updateNClob(String paramString, NClob paramNClob) throws SQLException {
    updateNClob(findColumn(paramString), paramNClob);
  }
  
  public void updateNClob(String paramString, Reader paramReader) throws SQLException {
    updateNClob(findColumn(paramString), paramReader);
  }
  
  public void updateNClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
    updateNClob(findColumn(paramString), paramReader, paramLong);
  }
  
  public void updateNString(int paramInt, String paramString) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramString); 
    super.updateNString(paramInt, paramString);
  }
  
  public void updateNString(String paramString1, String paramString2) throws SQLException {
    updateNString(findColumn(paramString1), paramString2);
  }
  
  public void updateObject(int paramInt, Object paramObject) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramObject); 
    super.updateObject(paramInt, paramObject);
  }
  
  public void updateObject(int paramInt1, Object paramObject, int paramInt2) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt1, paramObject); 
    super.updateObject(paramInt1, paramObject, paramInt2);
  }
  
  public void updateObject(String paramString, Object paramObject) throws SQLException {
    updateObject(findColumn(paramString), paramObject);
  }
  
  public void updateObject(String paramString, Object paramObject, int paramInt) throws SQLException {
    updateObject(findColumn(paramString), paramObject, paramInt);
  }
  
  public void updateRef(int paramInt, Ref paramRef) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramRef); 
    super.updateRef(paramInt, paramRef);
  }
  
  public void updateRef(String paramString, Ref paramRef) throws SQLException {
    updateRef(findColumn(paramString), paramRef);
  }
  
  public void updateRowId(int paramInt, RowId paramRowId) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramRowId); 
    super.updateRowId(paramInt, paramRowId);
  }
  
  public void updateRowId(String paramString, RowId paramRowId) throws SQLException {
    updateRowId(findColumn(paramString), paramRowId);
  }
  
  public void updateShort(int paramInt, short paramShort) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, new Short(paramShort)); 
    super.updateShort(paramInt, paramShort);
  }
  
  public void updateShort(String paramString, short paramShort) throws SQLException {
    updateShort(findColumn(paramString), paramShort);
  }
  
  public void updateSQLXML(int paramInt, SQLXML paramSQLXML) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramSQLXML); 
    super.updateSQLXML(paramInt, paramSQLXML);
  }
  
  public void updateSQLXML(String paramString, SQLXML paramSQLXML) throws SQLException {
    updateSQLXML(findColumn(paramString), paramSQLXML);
  }
  
  public void updateString(int paramInt, String paramString) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramString); 
    super.updateString(paramInt, paramString);
  }
  
  public void updateString(String paramString1, String paramString2) throws SQLException {
    updateString(findColumn(paramString1), paramString2);
  }
  
  public void updateTime(int paramInt, Time paramTime) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramTime); 
    super.updateTime(paramInt, paramTime);
  }
  
  public void updateTime(String paramString, Time paramTime) throws SQLException {
    updateTime(findColumn(paramString), paramTime);
  }
  
  public void updateTimestamp(int paramInt, Timestamp paramTimestamp) throws SQLException {
    if (this.isOnInsert)
      checkAndFilterObject(paramInt, paramTimestamp); 
    super.updateTimestamp(paramInt, paramTimestamp);
  }
  
  public void updateTimestamp(String paramString, Timestamp paramTimestamp) throws SQLException {
    updateTimestamp(findColumn(paramString), paramTimestamp);
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\rowset\TbFilteredRowSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */