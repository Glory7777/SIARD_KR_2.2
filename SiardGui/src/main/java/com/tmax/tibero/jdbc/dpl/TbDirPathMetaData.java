package com.tmax.tibero.jdbc.dpl;

import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.msg.TbColumnDesc;
import java.sql.SQLException;

public class TbDirPathMetaData {
  public static final boolean NO_LOG = false;
  
  public static final boolean LOG = true;
  
  public static final boolean NO_PARALLEL = false;
  
  public static final boolean PARALLEL = true;
  
  public static final int NO_BIND = 0;
  
  public static final int BIND = 256;
  
  public static final int MANUAL = 0;
  
  public static final int AUTO = 16;
  
  public static final int COMPLETE = 0;
  
  public static final int PARTIAL = 1;
  
  public static final int INIT_BUF_SIZE = 131072;
  
  public static final int CHAR_BUF_SIZE = 4096;
  
  public static final int BYTE_BUF_SIZE = 16384;
  
  private String schema;
  
  private String table;
  
  private String partition;
  
  private int columnCnt;
  
  private boolean logFlag = false;
  
  private boolean parallelFlag = false;
  
  private String clientCharset = null;
  
  private int bufferSize = 131072;
  
  private String[] columnNames;
  
  private TbColumnDesc[] columnMetas;
  
  public TbDirPathMetaData() {
    reset();
  }
  
  public TbDirPathMetaData(String paramString1, String paramString2, String[] paramArrayOfString, boolean paramBoolean) throws SQLException {
    setSchema(paramString1);
    setTable(paramString2);
    this.partition = "";
    this.columnNames = paramArrayOfString;
    this.logFlag = paramBoolean;
    this.columnCnt = paramArrayOfString.length;
    for (byte b = 0; b < this.columnCnt; b++)
      setColumn(b + 1, paramArrayOfString[b]); 
  }
  
  public void checkDirPathMetaData() throws SQLException {
    if (this.schema == null || this.schema.length() == 0)
      throw TbError.newSQLException(-90637); 
    if (this.table == null || this.table.length() == 0)
      throw TbError.newSQLException(-90638); 
    if (this.columnNames == null)
      throw TbError.newSQLException(-90640); 
    for (byte b = 0; b < this.columnNames.length; b++) {
      if (this.columnNames[b] == null || this.columnNames[b].length() == 0)
        throw TbError.newSQLException(-90640); 
    } 
  }
  
  private void checkParameterIndex(int paramInt) throws SQLException {
    if (paramInt < 1 || paramInt > this.columnCnt)
      throw TbError.newSQLException(-90609); 
  }
  
  public int getBufferSize() {
    return this.bufferSize;
  }
  
  public String getClientCharSet() {
    return this.clientCharset;
  }
  
  public String getColumn(int paramInt) throws SQLException {
    if (this.columnNames == null)
      throw TbError.newSQLException(-90640); 
    checkParameterIndex(paramInt);
    return this.columnNames[paramInt - 1];
  }
  
  public int getColumnCnt() {
    return this.columnNames.length;
  }
  
  public TbColumnDesc[] getColumnMetas() {
    return this.columnMetas;
  }
  
  public int getDataType(int paramInt) throws SQLException {
    checkParameterIndex(paramInt);
    return (this.columnMetas[paramInt - 1]).dataType;
  }
  
  private String getDBString(String paramString) {
    int i = paramString.indexOf('"');
    int j = paramString.lastIndexOf('"');
    if (j > i) {
      paramString = paramString.substring(i + 1, j);
    } else {
      paramString = paramString.toUpperCase();
    } 
    return paramString;
  }
  
  public boolean getLogFlag() {
    return this.logFlag;
  }
  
  public String getSchema() {
    return this.schema;
  }
  
  public String getTable() {
    return this.table;
  }
  
  public void reset() {
    this.schema = null;
    this.table = null;
    this.partition = "";
    this.columnCnt = 0;
    this.logFlag = false;
    this.parallelFlag = false;
    this.clientCharset = null;
    this.columnNames = null;
    this.columnMetas = null;
  }
  
  public void setBufferSize(int paramInt) {
    this.bufferSize = paramInt;
  }
  
  public void setClientCharSet(String paramString) {
    this.clientCharset = paramString;
  }
  
  public void setColumn(int paramInt, String paramString) throws SQLException {
    if (this.columnNames == null)
      throw TbError.newSQLException(-90640); 
    checkParameterIndex(paramInt);
    this.columnNames[paramInt - 1] = getDBString(paramString);
  }
  
  public void setColumnCnt(int paramInt) {
    this.columnCnt = paramInt;
    if (this.columnNames == null) {
      this.columnNames = new String[paramInt];
      for (byte b = 0; b < paramInt; b++)
        this.columnNames[b] = new String(); 
    } 
  }
  
  public void setColumnMetas(TbColumnDesc[] paramArrayOfTbColumnDesc) {
    this.columnMetas = paramArrayOfTbColumnDesc;
  }
  
  public void setLogFlag(boolean paramBoolean) {
    this.logFlag = paramBoolean;
  }
  
  public void setSchema(String paramString) throws SQLException {
    if (paramString == null || paramString.length() == 0)
      throw TbError.newSQLException(-90637); 
    this.schema = getDBString(paramString);
  }
  
  public void setTable(String paramString) throws SQLException {
    if (paramString == null || paramString.length() == 0)
      throw TbError.newSQLException(-90638); 
    this.table = getDBString(paramString);
  }
  
  public String getPartition() {
    return this.partition;
  }
  
  public void setPartition(String paramString) throws SQLException {
    if (paramString == null)
      throw TbError.newSQLException(-90661); 
    this.partition = getDBString(paramString);
  }
  
  public boolean getParallelFlag() {
    return this.parallelFlag;
  }
  
  public void setParallelFlag(boolean paramBoolean) {
    this.parallelFlag = paramBoolean;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\dpl\TbDirPathMetaData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */