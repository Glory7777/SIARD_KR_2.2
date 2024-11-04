package com.tmax.tibero.jdbc.rowset;

import com.tmax.tibero.jdbc.err.TbError;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

public class TbRow implements Serializable {
  private static final long serialVersionUID = -8999757353034075305L;
  
  protected Object[] originalCols;
  
  protected Object[] currentCols;
  
  protected boolean[] isColumnChanged;
  
  private boolean[] isNull;
  
  protected boolean deleted = false;
  
  protected boolean updated = false;
  
  protected boolean inserted = false;
  
  protected int columnCount;
  
  private int insertedColumnCnt;
  
  public TbRow(int paramInt) {
    this.columnCount = paramInt;
    this.originalCols = new Object[paramInt];
    this.currentCols = new Object[paramInt];
    this.isColumnChanged = new boolean[paramInt];
    this.isNull = new boolean[paramInt];
  }
  
  public TbRow(int paramInt, boolean paramBoolean) {
    this(paramInt);
    this.inserted = paramBoolean;
    this.insertedColumnCnt = 0;
  }
  
  public TbRow(int paramInt, Object[] paramArrayOfObject) {
    this(paramInt);
    System.arraycopy(paramArrayOfObject, 0, this.originalCols, 0, paramInt);
  }
  
  public void cancelDeleted() {
    this.deleted = false;
  }
  
  public void cancelInserted() {
    this.inserted = false;
  }
  
  public void cancelUpdated() {
    this.insertedColumnCnt = 0;
    for (byte b = 0; b < this.columnCount; b++)
      this.isColumnChanged[b] = false; 
    this.currentCols = null;
    this.currentCols = new Object[this.columnCount];
  }
  
  public void commitChangedColumns() {
    for (byte b = 0; b < this.columnCount; b++) {
      if (this.isColumnChanged[b]) {
        this.originalCols[b] = this.currentCols[b];
        this.currentCols[b] = null;
        this.isColumnChanged[b] = false;
      } 
    } 
    this.updated = false;
  }
  
  public TbRow createCopy() throws SQLException {
    TbRow tbRow = new TbRow(this.columnCount);
    for (byte b = 0; b < this.columnCount; b++) {
      tbRow.originalCols[b] = getCopy(this.originalCols[b]);
      tbRow.currentCols[b] = getCopy(this.currentCols[b]);
    } 
    System.arraycopy(this.isColumnChanged, 0, tbRow.isColumnChanged, 0, this.columnCount);
    tbRow.insertedColumnCnt = this.insertedColumnCnt;
    tbRow.deleted = this.deleted;
    tbRow.inserted = this.inserted;
    tbRow.updated = this.updated;
    return tbRow;
  }
  
  public Object getChangedColumn(int paramInt) {
    return this.currentCols[paramInt - 1];
  }
  
  public Object getColumn(int paramInt) {
    return this.originalCols[paramInt - 1];
  }
  
  public Object getCopy(Object paramObject) throws SQLException {
    if (paramObject == null)
      return null; 
    try {
      if (paramObject instanceof String)
        return new String((String)paramObject); 
      if (paramObject instanceof Number)
        return new BigDecimal(((Number)paramObject).toString()); 
      if (paramObject instanceof Date)
        return new Date(((Date)paramObject).getTime()); 
      if (paramObject instanceof Timestamp)
        return new Timestamp(((Timestamp)paramObject).getTime()); 
      if (paramObject instanceof InputStream)
        return new DataInputStream((InputStream)paramObject); 
      if (paramObject instanceof OutputStream)
        return new DataOutputStream((OutputStream)paramObject); 
      throw TbError.newSQLException(-90842, paramObject.toString());
    } catch (Exception exception) {
      throw TbError.newSQLException(-90830, exception.getMessage());
    } 
  }
  
  public Object[] getOriginalRow() {
    return this.originalCols;
  }
  
  public void insertRow() {
    this.isColumnChanged = null;
    this.isColumnChanged = new boolean[this.columnCount];
    System.arraycopy(this.currentCols, 0, this.originalCols, 0, this.columnCount);
    this.currentCols = null;
    this.currentCols = new Object[this.columnCount];
  }
  
  public boolean isColumnChanged(int paramInt) {
    return this.isColumnChanged[paramInt - 1];
  }
  
  public boolean isDeleted() {
    return this.deleted;
  }
  
  public boolean isInserted() {
    return this.inserted;
  }
  
  boolean isNull(int paramInt) throws SQLException {
    return this.isNull[paramInt - 1];
  }
  
  public boolean isPopulationCompleted() {
    return !this.inserted ? false : ((this.insertedColumnCnt == this.columnCount));
  }
  
  public boolean isUpdated() {
    if (this.inserted || this.deleted)
      return false; 
    for (byte b = 0; b < this.columnCount; b++) {
      if (this.isColumnChanged[b])
        return true; 
    } 
    return false;
  }
  
  public void markDeleted(boolean paramBoolean) {
    this.deleted = paramBoolean;
  }
  
  public void markInserted(boolean paramBoolean) {
    this.inserted = paramBoolean;
  }
  
  void markNull(int paramInt, boolean paramBoolean) throws SQLException {
    this.isNull[paramInt - 1] = paramBoolean;
  }
  
  public void markUpdated(boolean paramBoolean) {
    this.updated = paramBoolean;
    if (!paramBoolean)
      cancelUpdated(); 
  }
  
  public void reset() {
    this.originalCols = null;
    this.currentCols = null;
    this.isColumnChanged = null;
    this.deleted = false;
    this.updated = false;
    this.inserted = false;
    this.columnCount = 0;
  }
  
  public void setColumn(int paramInt, Object paramObject) {
    if (this.inserted)
      this.insertedColumnCnt++; 
    this.originalCols[paramInt - 1] = paramObject;
  }
  
  public Collection<Object> toCollection() {
    Vector<Object> vector = new Vector(this.columnCount);
    for (byte b = 1; b <= this.columnCount; b++)
      vector.add(isColumnChanged(b) ? getChangedColumn(b) : getColumn(b)); 
    return vector;
  }
  
  public void updateObject(int paramInt, Object paramObject) {
    if (this.inserted)
      this.insertedColumnCnt++; 
    this.isColumnChanged[paramInt - 1] = true;
    this.currentCols[paramInt - 1] = paramObject;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\rowset\TbRow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */