package com.tmax.tibero.jdbc.driver;

import com.tmax.tibero.jdbc.data.DataType;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.msg.TbBindParamDesc;
import java.sql.ParameterMetaData;
import java.sql.SQLException;

public class TbParameterMetaData implements ParameterMetaData {
  private int paramCnt = 0;
  
  private TbBindParamDesc[] paramMeta = null;
  
  private boolean mapDateToTimestamp = false;
  
  TbParameterMetaData(int paramInt, TbBindParamDesc[] paramArrayOfTbBindParamDesc, boolean paramBoolean) {
    this.paramCnt = paramInt;
    this.paramMeta = paramArrayOfTbBindParamDesc;
    this.mapDateToTimestamp = paramBoolean;
  }
  
  public String getParameterClassName(int paramInt) throws SQLException {
    checkParameterIndex(paramInt);
    return DataType.getDataTypeClassName(getParamDataType(paramInt));
  }
  
  public int getParameterCount() throws SQLException {
    return this.paramCnt;
  }
  
  public int getParameterMode(int paramInt) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public int getParameterType(int paramInt) throws SQLException {
    int k;
    checkParameterIndex(paramInt);
    int i = getParamDataType(paramInt);
    int j = getParamPrecision(paramInt);
    if (i == 16) {
      k = -1;
    } else {
      k = DataType.getSqlType(i, j, this.mapDateToTimestamp);
    } 
    return k;
  }
  
  public String getParameterTypeName(int paramInt) throws SQLException {
    checkParameterIndex(paramInt);
    int i = getParamDataType(paramInt);
    return DataType.getDBTypeName(i, getParamPrecision(paramInt));
  }
  
  public int getPrecision(int paramInt) throws SQLException {
    checkParameterIndex(paramInt);
    int i = getParamPrecision(paramInt);
    int j = getParamDataType(paramInt);
    switch (j) {
      case 2:
      case 3:
        return getParamMaxLength(paramInt);
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
    checkParameterIndex(paramInt);
    int i = 0;
    int j = getParamDataType(paramInt);
    switch (j) {
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
        return 0;
    } 
    i = getParamScale(paramInt);
    return (i < 0) ? 0 : i;
  }
  
  public int isNullable(int paramInt) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public boolean isSigned(int paramInt) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  private void checkParameterIndex(int paramInt) throws SQLException {
    if (this.paramMeta == null)
      throw TbError.newSQLException(-90201); 
    if (paramInt <= 0 || paramInt > this.paramCnt)
      throw TbError.newSQLException(-90609, "(0 <= index[" + paramInt + "] < paramCount[" + this.paramCnt + "])"); 
  }
  
  private int getParamDataType(int paramInt) throws SQLException {
    checkParameterIndex(paramInt);
    return (this.paramMeta[paramInt - 1]).type;
  }
  
  private int getParamPrecision(int paramInt) throws SQLException {
    checkParameterIndex(paramInt);
    String str = (this.paramMeta[paramInt - 1]).placeHolderName;
    String[] arrayOfString = str.split("@");
    return Integer.parseInt(arrayOfString[0]);
  }
  
  private int getParamScale(int paramInt) throws SQLException {
    checkParameterIndex(paramInt);
    String str = (this.paramMeta[paramInt - 1]).placeHolderName;
    String[] arrayOfString = str.split("@");
    return Integer.parseInt(arrayOfString[1]);
  }
  
  private int getParamMaxLength(int paramInt) throws SQLException {
    checkParameterIndex(paramInt);
    String str = (this.paramMeta[paramInt - 1]).placeHolderName;
    String[] arrayOfString = str.split("@");
    return Integer.parseInt(arrayOfString[2]);
  }
  
  public boolean isWrapperFor(Class<?> paramClass) throws SQLException {
    return paramClass.isInstance(this);
  }
  
  public <T> T unwrap(Class<T> paramClass) throws SQLException {
    try {
      return paramClass.cast(this);
    } catch (ClassCastException classCastException) {
      throw TbError.newSQLException(-90657);
    } 
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\driver\TbParameterMetaData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */