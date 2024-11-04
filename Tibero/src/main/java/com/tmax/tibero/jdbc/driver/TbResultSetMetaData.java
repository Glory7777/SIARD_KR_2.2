package com.tmax.tibero.jdbc.driver;

import com.tmax.tibero.jdbc.TbTypeDescriptor;
import com.tmax.tibero.jdbc.data.Column;
import com.tmax.tibero.jdbc.data.DataType;
import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;

public class TbResultSetMetaData implements com.tmax.tibero.jdbc.TbResultSetMetaData {
  private Column[] columnInfoArr = null;
  
  public TbResultSetMetaData(Column[] paramArrayOfColumn, int paramInt) {
    if (paramArrayOfColumn != null) {
      int i = paramArrayOfColumn.length - paramInt;
      this.columnInfoArr = new Column[i];
      for (byte b = 0; b < i; b++)
        this.columnInfoArr[b] = new Column(paramArrayOfColumn[b + paramInt]); 
    } 
  }
  
  public String getCatalogName(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return "";
  }
  
  public String getColumnClassName(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return DataType.getDataTypeClassName(getColumnDataType(paramInt));
  }
  
  public int getColumnCount() throws SQLException {
    if (this.columnInfoArr != null)
      return this.columnInfoArr.length; 
    throw TbError.newSQLException(-590784);
  }
  
  private int getColumnDataType(int paramInt) throws SQLException {
    return this.columnInfoArr[paramInt - 1].getDataType();
  }
  
  private int getColumnMaxLength(int paramInt) throws SQLException {
    return this.columnInfoArr[paramInt - 1].getMaxLength();
  }
  
  public int getColumnDisplaySize(int paramInt) throws SQLException {
    int k;
    checkColumnIndex(paramInt);
    int i = getColumnDataType(paramInt);
    int j = getColumnMaxLength(paramInt);
    switch (i) {
      case 2:
      case 3:
        if (j > 0)
          return j; 
        k = getColumnName(paramInt).length();
        return (k == 0) ? 1 : k;
      case 1:
        return 45;
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
        return 24;
      case 15:
        return 14;
      case 12:
      case 13:
        return 400000000;
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
        return 0;
    } 
    return (j > 0) ? j : 65535;
  }
  
  public String getColumnLabel(int paramInt) throws SQLException {
    return getColumnName(paramInt);
  }
  
  public String getColumnName(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (this.columnInfoArr != null)
      return this.columnInfoArr[paramInt - 1].getName(); 
    throw TbError.newSQLException(-590784);
  }
  
  public int getColumnType(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (this.columnInfoArr != null)
      return this.columnInfoArr[paramInt - 1].getSqlType(); 
    throw TbError.newSQLException(-590784);
  }
  
  public String getColumnTypeName(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    int i = getColumnDataType(paramInt);
    if (DataType.isUDTCategory(i)) {
      TbTypeDescriptor tbTypeDescriptor = this.columnInfoArr[paramInt - 1].getDescriptor();
      if (tbTypeDescriptor instanceof TbTypeDescriptor)
        return tbTypeDescriptor.getSQLTypeName(); 
    } 
    return DataType.getDBTypeName(i, getColumnPrecision(paramInt));
  }
  
  private int getColumnPrecision(int paramInt) throws SQLException {
    return this.columnInfoArr[paramInt - 1].getPrecision();
  }
  
  public int getPrecision(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    int i = getColumnPrecision(paramInt);
    int j = getColumnDataType(paramInt);
    switch (j) {
      case 2:
      case 3:
        return getColumnMaxLength(paramInt);
      case 12:
      case 13:
        return -1;
      case 10:
      case 11:
        return Integer.MAX_VALUE;
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
        return 0;
    } 
    return (i < 0) ? 0 : i;
  }
  
  public int getScale(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    int i = 0;
    int j = getColumnDataType(paramInt);
    switch (j) {
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
        return 0;
    } 
    i = this.columnInfoArr[paramInt - 1].getScale();
    return (i < 0) ? 0 : i;
  }
  
  private void checkColumnIndex(int paramInt) throws SQLException {
    if (this.columnInfoArr != null) {
      int i = this.columnInfoArr.length;
      if (i < 0)
        throw TbError.newSQLException(-90607); 
      if (paramInt <= 0 || paramInt > i)
        throw TbError.newSQLException(-90609, Integer.toString(paramInt)); 
    } else {
      throw TbError.newSQLException(-590784);
    } 
  }
  
  public String getSchemaName(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return "";
  }
  
  public String getTableName(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return "";
  }
  
  public boolean isAutoIncrement(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return false;
  }
  
  public boolean isCaseSensitive(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    int i = getColumnDataType(paramInt);
    return (DataType.isCharacterCategory(i) || i == 13 || i == 10);
  }
  
  public boolean isCurrency(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    int i = getColumnDataType(paramInt);
    return (i == 1);
  }
  
  public boolean isDefinitelyWritable(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return false;
  }
  
  public int isNullable(int paramInt) throws SQLException {
    boolean bool;
    checkColumnIndex(paramInt);
    if (this.columnInfoArr != null) {
      bool = this.columnInfoArr[paramInt - 1].isNullable();
    } else {
      throw TbError.newSQLException(-590784);
    } 
    return bool ? 1 : 0;
  }
  
  public boolean isReadOnly(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return false;
  }
  
  public boolean isSearchable(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    int i = getColumnDataType(paramInt);
    return (i != 11 && i != 10 && i != 12 && i != 13);
  }
  
  public boolean isSigned(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return DataType.isNumberCategory(getColumnDataType(paramInt));
  }
  
  public boolean isWrapperFor(Class<?> paramClass) throws SQLException {
    return paramClass.isInstance(this);
  }
  
  public boolean isWritable(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return true;
  }
  
  public void reset() {
    this.columnInfoArr = null;
  }
  
  public <T> T unwrap(Class<T> paramClass) throws SQLException {
    try {
      return paramClass.cast(this);
    } catch (ClassCastException classCastException) {
      throw TbError.newSQLException(-90657);
    } 
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\driver\TbResultSetMetaData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */