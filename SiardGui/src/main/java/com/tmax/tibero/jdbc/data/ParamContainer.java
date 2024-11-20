package com.tmax.tibero.jdbc.data;

import com.tmax.tibero.jdbc.data.binder.Binder;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Struct;
import java.sql.Timestamp;
import java.util.Calendar;

public interface ParamContainer {
  BindData getBindData();
  
  Binder[][] getBinder();
  
  Binder getBinder(int paramInt1, int paramInt2);
  
  int getParameterCnt();
  
  byte getParamType(int paramInt1, int paramInt2);
  
  byte[][] getParamTypes();
  
  byte[] getParamTypesOfRow(int paramInt);
  
  Array getParamArray(int paramInt1, int paramInt2);
  
  Array[] getParamArrayOfRow(int paramInt);
  
  BigDecimal getParamBigDecimal(int paramInt1, int paramInt2);
  
  BigDecimal[] getParamBigDecimalOfRow(int paramInt);
  
  byte[] getParamBytes(int paramInt1, int paramInt2);
  
  byte[][] getParamBytesOfRow(int paramInt);
  
  Calendar getParamCalendar(int paramInt1, int paramInt2);
  
  Calendar[] getParamCalendarOfRow(int paramInt);
  
  double getParamDouble(int paramInt1, int paramInt2);
  
  double[] getParamDoubleOfRow(int paramInt);
  
  float getParamFloat(int paramInt1, int paramInt2);
  
  float[] getParamFloatOfRow(int paramInt);
  
  int getParamInt(int paramInt1, int paramInt2);
  
  int[] getParamIntOfRow(int paramInt);
  
  long getParamLong(int paramInt1, int paramInt2);
  
  long[] getParamLongOfRow(int paramInt);
  
  Reader getParamReader(int paramInt1, int paramInt2);
  
  Reader[] getParamReaderOfRow(int paramInt);
  
  InputStream getParamStream(int paramInt1, int paramInt2);
  
  InputStream[] getParamStreamOfRow(int paramInt);
  
  String getParamString(int paramInt1, int paramInt2);
  
  String[] getParamStringOfRow(int paramInt);
  
  Struct getParamStruct(int paramInt1, int paramInt2);
  
  Struct[] getParamStructOfRow(int paramInt);
  
  TbDate getParamTbDate(int paramInt1, int paramInt2);
  
  TbDate[] getParamTbDateOfRow(int paramInt);
  
  TbTimestamp getParamTbTimestamp(int paramInt1, int paramInt2);
  
  TbTimestamp[] getParamTbTimestampOfRow(int paramInt);
  
  TbTimestampTZ getParamTbTimestampTZ(int paramInt1, int paramInt2);
  
  TbTimestampTZ[] getParamTbTimestampTZOfRow(int paramInt);
  
  Timestamp getParamTimestamp(int paramInt1, int paramInt2);
  
  Timestamp[] getParamTimestampOfRow(int paramInt);
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\ParamContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */