package com.tmax.tibero.jdbc.rowset;

import com.tmax.tibero.jdbc.err.TbError;
import java.io.Serializable;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.sql.RowSetMetaData;

public class TbRowSetMetaData implements RowSetMetaData, Serializable {
  private static final long serialVersionUID = -1910875269279117964L;
  
  private int columnCount;
  
  private boolean[] autoIncrement;
  
  private boolean[] caseSensitive;
  
  private String[] catalogName;
  
  private int[] columnDIsplaySize;
  
  private String[] columnLabel;
  
  private String[] columnName;
  
  private int[] columnType;
  
  private String[] columnTypeName;
  
  private boolean[] currency;
  
  private int[] nullable;
  
  private int[] precision;
  
  private int[] scale;
  
  private String[] schemaName;
  
  private boolean[] searchable;
  
  private boolean[] signed;
  
  private String[] tableName;
  
  private boolean[] definatelyWritable;
  
  private boolean[] readOnly;
  
  private boolean[] writable;
  
  private String[] columnClassName;
  
  public TbRowSetMetaData(ResultSetMetaData paramResultSetMetaData) throws SQLException {
    this.columnCount = paramResultSetMetaData.getColumnCount();
    this.autoIncrement = new boolean[this.columnCount];
    this.caseSensitive = new boolean[this.columnCount];
    this.catalogName = new String[this.columnCount];
    this.columnDIsplaySize = new int[this.columnCount];
    this.columnLabel = new String[this.columnCount];
    this.columnName = new String[this.columnCount];
    this.columnType = new int[this.columnCount];
    this.columnTypeName = new String[this.columnCount];
    this.currency = new boolean[this.columnCount];
    this.nullable = new int[this.columnCount];
    this.precision = new int[this.columnCount];
    this.precision = new int[this.columnCount];
    this.scale = new int[this.columnCount];
    this.schemaName = new String[this.columnCount];
    this.searchable = new boolean[this.columnCount];
    this.signed = new boolean[this.columnCount];
    this.tableName = new String[this.columnCount];
    this.definatelyWritable = new boolean[this.columnCount];
    this.readOnly = new boolean[this.columnCount];
    this.writable = new boolean[this.columnCount];
    this.columnClassName = new String[this.columnCount];
    for (byte b = 0; b < this.columnCount; b++) {
      this.autoIncrement[b] = paramResultSetMetaData.isAutoIncrement(b + 1);
      this.caseSensitive[b] = paramResultSetMetaData.isCaseSensitive(b + 1);
      this.catalogName[b] = paramResultSetMetaData.getCatalogName(b + 1);
      this.columnDIsplaySize[b] = paramResultSetMetaData.getColumnDisplaySize(b + 1);
      this.columnLabel[b] = paramResultSetMetaData.getColumnLabel(b + 1);
      this.columnName[b] = paramResultSetMetaData.getColumnName(b + 1);
      this.columnType[b] = paramResultSetMetaData.getColumnType(b + 1);
      this.columnTypeName[b] = paramResultSetMetaData.getColumnTypeName(b + 1);
      this.currency[b] = paramResultSetMetaData.isCurrency(b + 1);
      this.nullable[b] = paramResultSetMetaData.isNullable(b + 1);
      this.precision[b] = paramResultSetMetaData.getPrecision(b + 1);
      this.scale[b] = paramResultSetMetaData.getScale(b + 1);
      this.schemaName[b] = paramResultSetMetaData.getSchemaName(b + 1);
      this.searchable[b] = paramResultSetMetaData.isSearchable(b + 1);
      this.signed[b] = paramResultSetMetaData.isSearchable(b + 1);
      this.tableName[b] = paramResultSetMetaData.getTableName(b + 1);
      this.definatelyWritable[b] = paramResultSetMetaData.isDefinitelyWritable(b + 1);
      this.readOnly[b] = paramResultSetMetaData.isReadOnly(b + 1);
      this.writable[b] = paramResultSetMetaData.isWritable(b + 1);
      this.columnClassName[b] = paramResultSetMetaData.getColumnClassName(b + 1);
    } 
  }
  
  public TbRowSetMetaData(int paramInt) {
    this.columnCount = paramInt;
    this.autoIncrement = new boolean[this.columnCount];
    this.caseSensitive = new boolean[this.columnCount];
    this.catalogName = new String[this.columnCount];
    this.columnDIsplaySize = new int[this.columnCount];
    this.columnLabel = new String[this.columnCount];
    this.columnName = new String[this.columnCount];
    this.columnType = new int[this.columnCount];
    this.columnTypeName = new String[this.columnCount];
    this.currency = new boolean[this.columnCount];
    this.nullable = new int[this.columnCount];
    this.precision = new int[this.columnCount];
    this.precision = new int[this.columnCount];
    this.scale = new int[this.columnCount];
    this.schemaName = new String[this.columnCount];
    this.searchable = new boolean[this.columnCount];
    this.signed = new boolean[this.columnCount];
    this.tableName = new String[this.columnCount];
    this.definatelyWritable = new boolean[this.columnCount];
    this.readOnly = new boolean[this.columnCount];
    this.writable = new boolean[this.columnCount];
    this.columnClassName = new String[this.columnCount];
    for (byte b = 0; b < this.columnCount; b++) {
      this.autoIncrement[b] = true;
      this.caseSensitive[b] = false;
      this.catalogName[b] = "";
      this.columnDIsplaySize[b] = 0;
      this.columnLabel[b] = "";
      this.columnName[b] = "";
      this.columnType[b] = 0;
      this.columnTypeName[b] = "";
      this.currency[b] = false;
      this.nullable[b] = 1;
      this.precision[b] = 0;
      this.scale[b] = 0;
      this.schemaName[b] = "";
      this.searchable[b] = false;
      this.signed[b] = false;
      this.tableName[b] = "";
      this.definatelyWritable[b] = false;
      this.readOnly[b] = false;
      this.writable[b] = false;
      this.columnClassName[b] = "";
    } 
  }
  
  private void checkColumnIndex(int paramInt) throws SQLException {
    if (paramInt < 1 || paramInt > this.columnCount)
      throw TbError.newSQLException(-90834, Integer.toString(paramInt)); 
  }
  
  public String getCatalogName(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return this.catalogName[paramInt - 1];
  }
  
  public String getColumnClassName(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return this.columnClassName[paramInt - 1];
  }
  
  public int getColumnCount() throws SQLException {
    return this.columnCount;
  }
  
  public int getColumnDisplaySize(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return this.columnDIsplaySize[paramInt - 1];
  }
  
  public String getColumnLabel(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return this.columnLabel[paramInt - 1];
  }
  
  public String getColumnName(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return this.columnName[paramInt - 1];
  }
  
  public int getColumnType(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return this.columnType[paramInt - 1];
  }
  
  public String getColumnTypeName(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return this.columnTypeName[paramInt - 1];
  }
  
  public int getPrecision(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return this.precision[paramInt - 1];
  }
  
  public int getScale(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return this.scale[paramInt - 1];
  }
  
  public String getSchemaName(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return this.schemaName[paramInt - 1];
  }
  
  public String getTableName(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return this.tableName[paramInt - 1];
  }
  
  public boolean isAutoIncrement(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return this.autoIncrement[paramInt - 1];
  }
  
  public boolean isCaseSensitive(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return this.caseSensitive[paramInt - 1];
  }
  
  public boolean isCurrency(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return this.currency[paramInt - 1];
  }
  
  public boolean isDefinitelyWritable(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return this.definatelyWritable[paramInt - 1];
  }
  
  public int isNullable(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return this.nullable[paramInt - 1];
  }
  
  public boolean isReadOnly(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return this.readOnly[paramInt - 1];
  }
  
  public boolean isSearchable(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return this.searchable[paramInt - 1];
  }
  
  public boolean isSigned(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return this.signed[paramInt - 1];
  }
  
  public boolean isWrapperFor(Class<?> paramClass) throws SQLException {
    return paramClass.isInstance(this);
  }
  
  public boolean isWritable(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return this.writable[paramInt - 1];
  }
  
  public void setAutoIncrement(int paramInt, boolean paramBoolean) throws SQLException {
    checkColumnIndex(paramInt);
    this.autoIncrement[paramInt - 1] = paramBoolean;
  }
  
  public void setCaseSensitive(int paramInt, boolean paramBoolean) throws SQLException {
    checkColumnIndex(paramInt);
    this.caseSensitive[paramInt - 1] = paramBoolean;
  }
  
  public void setCatalogName(int paramInt, String paramString) throws SQLException {
    checkColumnIndex(paramInt);
    this.catalogName[paramInt - 1] = paramString;
  }
  
  public void setColumnCount(int paramInt) throws SQLException {
    this.columnCount = paramInt;
  }
  
  public void setColumnDisplaySize(int paramInt1, int paramInt2) throws SQLException {
    checkColumnIndex(paramInt1);
    this.columnDIsplaySize[paramInt1 - 1] = paramInt2;
  }
  
  public void setColumnLabel(int paramInt, String paramString) throws SQLException {
    checkColumnIndex(paramInt);
    this.columnLabel[paramInt - 1] = paramString;
  }
  
  public void setColumnName(int paramInt, String paramString) throws SQLException {
    checkColumnIndex(paramInt);
    this.columnName[paramInt - 1] = paramString;
  }
  
  public void setColumnType(int paramInt1, int paramInt2) throws SQLException {
    checkColumnIndex(paramInt1);
    this.columnType[paramInt1 - 1] = paramInt2;
  }
  
  public void setColumnTypeName(int paramInt, String paramString) throws SQLException {
    checkColumnIndex(paramInt);
    this.columnTypeName[paramInt - 1] = paramString;
  }
  
  public void setCurrency(int paramInt, boolean paramBoolean) throws SQLException {
    checkColumnIndex(paramInt);
    this.currency[paramInt - 1] = paramBoolean;
  }
  
  public void setNullable(int paramInt1, int paramInt2) throws SQLException {
    checkColumnIndex(paramInt1);
    this.nullable[paramInt1 - 1] = paramInt2;
  }
  
  public void setPrecision(int paramInt1, int paramInt2) throws SQLException {
    checkColumnIndex(paramInt1);
    this.precision[paramInt1 - 1] = paramInt2;
  }
  
  public void setScale(int paramInt1, int paramInt2) throws SQLException {
    checkColumnIndex(paramInt1);
    this.scale[paramInt1 - 1] = paramInt2;
  }
  
  public void setSchemaName(int paramInt, String paramString) throws SQLException {
    checkColumnIndex(paramInt);
    this.schemaName[paramInt - 1] = paramString;
  }
  
  public void setSearchable(int paramInt, boolean paramBoolean) throws SQLException {
    checkColumnIndex(paramInt);
    this.searchable[paramInt - 1] = paramBoolean;
  }
  
  public void setSigned(int paramInt, boolean paramBoolean) throws SQLException {
    checkColumnIndex(paramInt);
    this.signed[paramInt - 1] = paramBoolean;
  }
  
  public void setTableName(int paramInt, String paramString) throws SQLException {
    checkColumnIndex(paramInt);
    this.tableName[paramInt - 1] = paramString;
  }
  
  public <T> T unwrap(Class<T> paramClass) throws SQLException {
    try {
      return paramClass.cast(this);
    } catch (ClassCastException classCastException) {
      throw TbError.newSQLException(-90657);
    } 
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\rowset\TbRowSetMetaData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */