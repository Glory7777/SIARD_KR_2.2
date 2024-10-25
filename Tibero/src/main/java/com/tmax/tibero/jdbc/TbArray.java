package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.data.BindData;
import com.tmax.tibero.jdbc.data.DataType;
import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.data.TbDate;
import com.tmax.tibero.jdbc.data.TbTimestamp;
import com.tmax.tibero.jdbc.data.TbTimestampTZ;
import com.tmax.tibero.jdbc.data.binder.Binder;
import com.tmax.tibero.jdbc.data.binder.StaticBinder;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TbArray implements Array {
  private TbConnection conn;

  private TbArrayDescriptor descriptor;

  private int baseType;

  private Object[] elements;

  private Params subParams;

  public TbArray(TbArrayDescriptor paramTbArrayDescriptor, Connection paramConnection, Object[] paramArrayOfObject) throws SQLException {
    if (paramTbArrayDescriptor == null)
      throw TbError.newSQLException(-90608, "desc=null");
    if (paramConnection == null)
      throw TbError.newSQLException(-90608, "conn=null");
    this.descriptor = paramTbArrayDescriptor;
    this.baseType = paramTbArrayDescriptor.getBaseType();
    this.conn = (TbConnection)paramConnection;
    this.elements = paramArrayOfObject;
    this.subParams = new Params();
    initSubParams(paramTbArrayDescriptor.getElementType(), paramArrayOfObject);
  }

  private void initSubParams(int paramInt, Object[] paramArrayOfObject) throws SQLException {
    byte b2;
    byte b3;
    byte b1 = (byte) ((paramArrayOfObject == null) ? 0 : paramArrayOfObject.length);
    this.subParams.elementBinders = new Binder[b1];
    this.subParams.bindData = new BindData(b1);
    switch (paramInt) {
      case 1:
        this.subParams.paramBigDecimal = new BigDecimal[b1];
        for (b2 = 0; b2 < b1; b2++) {
          if (paramArrayOfObject[b2] == null) {
            this.subParams.paramBigDecimal[b2] = null;
            this.subParams.elementBinders[b2] = StaticBinder.getNullBinder();
          } else if (paramArrayOfObject[b2] instanceof Short) {
            this.subParams.paramBigDecimal[b2] = new BigDecimal(((Short)paramArrayOfObject[b2]).intValue());
            this.subParams.elementBinders[b2] = StaticBinder.getBigDecimalBinder();
          } else if (paramArrayOfObject[b2] instanceof Integer) {
            this.subParams.paramBigDecimal[b2] = new BigDecimal(((Integer)paramArrayOfObject[b2]).intValue());
            this.subParams.elementBinders[b2] = StaticBinder.getBigDecimalBinder();
          } else if (paramArrayOfObject[b2] instanceof Long) {
            this.subParams.paramBigDecimal[b2] = new BigDecimal(((Long)paramArrayOfObject[b2]).longValue());
            this.subParams.elementBinders[b2] = StaticBinder.getBigDecimalBinder();
          } else if (paramArrayOfObject[b2] instanceof BigInteger) {
            this.subParams.paramBigDecimal[b2] = new BigDecimal((BigInteger)paramArrayOfObject[b2]);
            this.subParams.elementBinders[b2] = StaticBinder.getBigDecimalBinder();
          } else if (paramArrayOfObject[b2] instanceof BigDecimal) {
            this.subParams.paramBigDecimal[b2] = (BigDecimal)paramArrayOfObject[b2];
            this.subParams.elementBinders[b2] = StaticBinder.getBigDecimalBinder();
          } else if (paramArrayOfObject[b2] instanceof Float) {
            this.subParams.paramBigDecimal[b2] = new BigDecimal(((Float)paramArrayOfObject[b2]).floatValue());
            this.subParams.elementBinders[b2] = StaticBinder.getBigDecimalBinder();
          } else if (paramArrayOfObject[b2] instanceof Double) {
            this.subParams.paramBigDecimal[b2] = new BigDecimal(((Double)paramArrayOfObject[b2]).doubleValue());
            this.subParams.elementBinders[b2] = StaticBinder.getBigDecimalBinder();
          } else if (paramArrayOfObject[b2] instanceof String) {
            this.subParams.paramBigDecimal[b2] = new BigDecimal((String)paramArrayOfObject[b2]);
            this.subParams.elementBinders[b2] = StaticBinder.getBigDecimalBinder();
          } else if (paramArrayOfObject[b2] instanceof Boolean) {
            this.subParams.paramBigDecimal[b2] = new BigDecimal(((Boolean)paramArrayOfObject[b2]).booleanValue() ? 1.0D : 0.0D);
            this.subParams.elementBinders[b2] = StaticBinder.getBigDecimalBinder();
          } else {
            String str = "elementType=" + DataType.getDBTypeName(paramInt) + ",element=" + paramArrayOfObject[b2];
            throw TbError.newSQLException(-90651, str);
          }
          this.subParams.bindData.setINParam(b2, paramInt, 0);
        }
        return;
      case 2:
      case 3:
      case 10:
        this.subParams.paramString = new String[b1];
        for (b2 = 0; b2 < b1; b2++) {
          if (paramArrayOfObject[b2] instanceof Boolean) {
            this.subParams.paramString[b2] = ((Boolean)paramArrayOfObject[b2]).booleanValue() ? "1" : "0";
            this.subParams.elementBinders[b2] = StaticBinder.getStringBinder();
          } else if (paramArrayOfObject[b2] == null) {
            this.subParams.paramString[b2] = null;
            this.subParams.elementBinders[b2] = StaticBinder.getNullBinder();
          } else {
            this.subParams.paramString[b2] = paramArrayOfObject[b2].toString();
            this.subParams.elementBinders[b2] = StaticBinder.getStringBinder();
          }
          this.subParams.bindData.setINParam(b2, paramInt, 0);
        }
        return;
      case 18:
      case 19:
        this.subParams.paramString = new String[b1];
        for (b2 = 0; b2 < b1; b2++) {
          if (paramArrayOfObject[b2] instanceof Boolean) {
            this.subParams.paramString[b2] = ((Boolean)paramArrayOfObject[b2]).booleanValue() ? "1" : "0";
            this.subParams.elementBinders[b2] = StaticBinder.getNStringBinder();
          } else if (paramArrayOfObject[b2] == null) {
            this.subParams.paramString[b2] = null;
            this.subParams.elementBinders[b2] = StaticBinder.getNullBinder();
          } else {
            this.subParams.paramString[b2] = paramArrayOfObject[b2].toString();
            this.subParams.elementBinders[b2] = StaticBinder.getNStringBinder();
          }
          this.subParams.bindData.setINParam(b2, paramInt, 0);
        }
        return;
      case 4:
        this.subParams.paramBytes = new byte[b1][];
        for (b2 = 0; b2 < b1; b2++) {
          if (paramArrayOfObject[b2] instanceof byte[]) {
            this.subParams.paramBytes[b2] = (byte[])paramArrayOfObject[b2];
            this.subParams.elementBinders[b2] = StaticBinder.getBytesBinder();
          } else if (paramArrayOfObject[b2] == null) {
            this.subParams.paramBytes[b2] = null;
            this.subParams.elementBinders[b2] = StaticBinder.getNullBinder();
          } else {
            String str = "elementType=" + DataType.getDBTypeName(paramInt) + ",element=" + paramArrayOfObject[b2];
            throw TbError.newSQLException(-90651, str);
          }
          this.subParams.bindData.setINParam(b2, paramInt, 0);
        }
        return;
      case 5:
        for (b2 = 0; b2 < b1; b2++) {
          if (paramArrayOfObject[b2] instanceof TbTimestampTZ) {
            if (this.subParams.paramCalendar == null)
              this.subParams.paramCalendar = new Calendar[b1];
            Calendar calendar = Calendar.getInstance(((TbTimestampTZ)paramArrayOfObject[b2]).getTimeZone());
            calendar.setTime((Date)paramArrayOfObject[b2]);
            this.subParams.paramCalendar[b2] = calendar;
            this.subParams.elementBinders[b2] = StaticBinder.getDateBinder();
          } else if (paramArrayOfObject[b2] instanceof TbTimestamp) {
            if (this.subParams.paramTbDate == null)
              this.subParams.paramTbDate = new TbDate[b1];
            TbTimestamp tbTimestamp = (TbTimestamp)paramArrayOfObject[b2];
            this.subParams.paramTbDate[b2] = new TbDate(tbTimestamp.getYear(), tbTimestamp.getMonth(), tbTimestamp.getDayOfMonth(), tbTimestamp.getHourOfDay(), tbTimestamp.getMinutes(), tbTimestamp.getSeconds());
            this.subParams.elementBinders[b2] = StaticBinder.getTbDateBinder();
          } else if (paramArrayOfObject[b2] instanceof TbDate) {
            if (this.subParams.paramTbDate == null)
              this.subParams.paramTbDate = new TbDate[b1];
            this.subParams.paramTbDate[b2] = (TbDate)paramArrayOfObject[b2];
            this.subParams.elementBinders[b2] = StaticBinder.getTbDateBinder();
          } else if (paramArrayOfObject[b2] instanceof Timestamp || paramArrayOfObject[b2] instanceof Date) {
            if (this.subParams.paramCalendar == null)
              this.subParams.paramCalendar = new Calendar[b1];
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((Date)paramArrayOfObject[b2]);
            this.subParams.paramCalendar[b2] = calendar;
            this.subParams.elementBinders[b2] = StaticBinder.getDateBinder();
          } else if (paramArrayOfObject[b2] instanceof String) {
            if (this.subParams.paramCalendar == null)
              this.subParams.paramCalendar = new Calendar[b1];
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Date.valueOf((String)paramArrayOfObject[b2]));
            this.subParams.paramCalendar[b2] = calendar;
            this.subParams.elementBinders[b2] = StaticBinder.getDateBinder();
          } else {
            String str = "elementType=" + DataType.getDBTypeName(paramInt) + ",element=" + paramArrayOfObject[b2];
            throw TbError.newSQLException(-90651, str);
          }
          this.subParams.bindData.setINParam(b2, paramInt, 0);
        }
        return;
      case 7:
        for (b2 = 0; b2 < b1; b2++) {
          if (paramArrayOfObject[b2] instanceof TbTimestampTZ) {
            if (this.subParams.paramTimestamp == null)
              this.subParams.paramTimestamp = new Timestamp[b1];
            this.subParams.paramTimestamp[b2] = (Timestamp)paramArrayOfObject[b2];
            this.subParams.elementBinders[b2] = StaticBinder.getTimestampBinder();
          } else if (paramArrayOfObject[b2] instanceof TbTimestamp) {
            if (this.subParams.paramTbTimestamp == null)
              this.subParams.paramTbTimestamp = new TbTimestamp[b1];
            this.subParams.paramTbTimestamp[b2] = (TbTimestamp)paramArrayOfObject[b2];
            this.subParams.elementBinders[b2] = StaticBinder.getTbTimestampBinder();
          } else if (paramArrayOfObject[b2] instanceof TbDate) {
            if (this.subParams.paramTbTimestamp == null)
              this.subParams.paramTbTimestamp = new TbTimestamp[b1];
            TbDate tbDate = (TbDate)paramArrayOfObject[b2];
            this.subParams.paramTbTimestamp[b2] = new TbTimestamp(tbDate.getYear(), tbDate.getMonth(), tbDate.getDayOfMonth(), tbDate.getHourOfDay(), tbDate.getMinutes(), tbDate.getSeconds(), 0);
            this.subParams.elementBinders[b2] = StaticBinder.getTbTimestampBinder();
          } else if (paramArrayOfObject[b2] instanceof Timestamp) {
            if (this.subParams.paramTimestamp == null)
              this.subParams.paramTimestamp = new Timestamp[b1];
            this.subParams.paramTimestamp[b2] = (Timestamp)paramArrayOfObject[b2];
            this.subParams.elementBinders[b2] = StaticBinder.getTimestampBinder();
          } else if (paramArrayOfObject[b2] instanceof Date) {
            if (this.subParams.paramCalendar == null)
              this.subParams.paramCalendar = new Calendar[b1];
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((Date)paramArrayOfObject[b2]);
            this.subParams.paramCalendar[b2] = calendar;
            this.subParams.elementBinders[b2] = StaticBinder.getDateBinder();
          } else if (paramArrayOfObject[b2] instanceof String) {
            if (this.subParams.paramTimestamp == null)
              this.subParams.paramTimestamp = new Timestamp[b1];
            this.subParams.paramTimestamp[b2] = Timestamp.valueOf((String)paramArrayOfObject[b2]);
            this.subParams.elementBinders[b2] = StaticBinder.getTimestampBinder();
          } else {
            String str = "elementType=" + DataType.getDBTypeName(paramInt) + ",element=" + paramArrayOfObject[b2];
            throw TbError.newSQLException(-90651, str);
          }
          this.subParams.bindData.setINParam(b2, paramInt, 0);
        }
        return;
      case 12:
      case 13:
      case 20:
        this.subParams.paramBytes = new byte[b1][];
        for (b2 = 0; b2 < b1; b2++) {
          if (paramArrayOfObject[b2] instanceof TbLob) {
            this.subParams.paramBytes[b2] = ((TbLob)paramArrayOfObject[b2]).getLocator();
            this.subParams.elementBinders[b2] = StaticBinder.getBytesBinder();
          } else if (paramArrayOfObject[b2] == null) {
            this.subParams.paramBytes[b2] = null;
            this.subParams.elementBinders[b2] = StaticBinder.getNullBinder();
          } else {
            String str = "elementType=" + DataType.getDBTypeName(paramInt) + ",element=" + paramArrayOfObject;
            throw TbError.newSQLException(-90651, str);
          }
          this.subParams.bindData.setINParam(b2, paramInt, 0);
        }
        return;
      case 29:
      case 30:
      case 31:
        if (this.subParams.paramArray == null)
          this.subParams.paramArray = new Array[b1];
        for (b2 = 0; b2 < b1; b2++) {
          if (paramArrayOfObject[b2] instanceof Array) {
            this.subParams.paramArray[b2] = (Array)paramArrayOfObject[b2];
            this.subParams.elementBinders[b2] = StaticBinder.getArrayInBinder();
            this.subParams.getBindData().getBindItem(b2).setTypeDescriptor(((TbArray)paramArrayOfObject[b2]).getDescriptor());
          } else if (paramArrayOfObject[b2] == null) {
            this.subParams.paramArray[b2] = null;
            this.subParams.elementBinders[b2] = StaticBinder.getNullBinder();
          } else {
            String str = "elementType=" + DataType.getDBTypeName(paramInt) + ",element=" + paramArrayOfObject;
            throw TbError.newSQLException(-90651, str);
          }
          this.subParams.bindData.setINParam(b2, paramInt, 0);
        }
        return;
      case 28:
      case 32:
        if (this.subParams.paramStruct == null)
          this.subParams.paramStruct = new Struct[b1];
        for (b3 = 0; b3 < b1; b3++) {
          if (paramArrayOfObject[b3] instanceof Struct) {
            this.subParams.paramStruct[b3] = (Struct)paramArrayOfObject[b3];
            this.subParams.elementBinders[b3] = StaticBinder.getStructInBinder();
            this.subParams.getBindData().getBindItem(b3).setTypeDescriptor(((TbStruct)paramArrayOfObject[b3]).getDescriptor());
          } else if (paramArrayOfObject[b3] instanceof java.sql.SQLData) {
            Struct struct = (Struct)TbStruct.toStruct(paramArrayOfObject[b3], (Connection)this.conn);
            this.subParams.paramStruct[b3] = struct;
            this.subParams.elementBinders[b3] = StaticBinder.getStructInBinder();
            this.subParams.getBindData().getBindItem(b3).setTypeDescriptor(((TbStruct)struct).getDescriptor());
          } else if (paramArrayOfObject[b3] == null) {
            this.subParams.paramStruct[b3] = null;
            this.subParams.elementBinders[b3] = StaticBinder.getNullBinder();
          } else {
            String str = "elementType=" + DataType.getDBTypeName(paramInt) + ",element=" + paramArrayOfObject;
            throw TbError.newSQLException(-90651, str);
          }
        }
        return;
    }
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt));
  }

  public TbArrayDescriptor getDescriptor() {
    return this.descriptor;
  }

  public void free() throws SQLException {
    this.conn = null;
    this.descriptor = null;
  }

  public Object getArray() throws SQLException {
    Serializable bool = (this.elements == null) ? false : this.elements.length;
    return getArray(0L, (Integer) bool);
  }

  public Object getArray(long paramLong, int paramInt) throws SQLException {
    BigDecimal[] arrayOfBigDecimal;
    byte b1;
    String[] arrayOfString;
    byte b2;
    Timestamp[] arrayOfTimestamp;
    byte b3;
    byte[][] arrayOfByte;
    byte b4;
    Blob[] arrayOfBlob;
    byte b5;
    NClob[] arrayOfNClob;
    byte b6;
    Clob[] arrayOfClob;
    byte b7;
    Ref[] arrayOfRef;
    byte b8;
    TbArray[] arrayOfTbArray;
    byte b9;
    int i = this.elements.length;
    if (paramInt == 0 || i == 0)
      return null;
    if (paramInt < 0)
      throw TbError.newSQLException(-590731);
    if (paramLong < 0L || paramLong >= i)
      throw TbError.newSQLException(-90609);
    if (paramLong + paramInt > i)
      throw TbError.newSQLException(-90608);
    int j = this.descriptor.getElementType();
    switch (j) {
      case 1:
        arrayOfBigDecimal = new BigDecimal[paramInt];
        for (b1 = 0; b1 < paramInt; b1++)
          arrayOfBigDecimal[b1] = (BigDecimal)this.elements[(int)paramLong + b1];
        return arrayOfBigDecimal;
      case 2:
      case 3:
      case 10:
      case 18:
      case 19:
        arrayOfString = new String[paramInt];
        for (b2 = 0; b2 < paramInt; b2++)
          arrayOfString[b2] = (String)this.elements[(int)paramLong + b2];
        return arrayOfString;
      case 5:
        if (!this.conn.info.getMapDateToTimestamp()) {
          Date[] arrayOfDate = new Date[paramInt];
          for (byte b = 0; b < paramInt; b++)
            arrayOfDate[b] = (Date)this.elements[(int)paramLong + b];
          return arrayOfDate;
        }
      case 7:
      case 21:
      case 22:
        arrayOfTimestamp = new Timestamp[paramInt];
        for (b3 = 0; b3 < paramInt; b3++)
          arrayOfTimestamp[b3] = (Timestamp)this.elements[(int)paramLong + b3];
        return arrayOfTimestamp;
      case 4:
        arrayOfByte = new byte[paramInt][];
        for (b4 = 0; b4 < paramInt; b4++)
          arrayOfByte[b4] = (byte[])this.elements[(int)paramLong + b4];
        return arrayOfByte;
      case 12:
        arrayOfBlob = new Blob[paramInt];
        for (b5 = 0; b5 < paramInt; b5++)
          arrayOfBlob[b5] = (Blob)this.elements[(int)paramLong + b5];
        return arrayOfBlob;
      case 20:
        arrayOfNClob = new NClob[paramInt];
        for (b6 = 0; b6 < paramInt; b6++)
          arrayOfNClob[b6] = (NClob)this.elements[(int)paramLong + b6];
        return arrayOfNClob;
      case 13:
        arrayOfClob = new Clob[paramInt];
        for (b7 = 0; b7 < paramInt; b7++)
          arrayOfClob[b7] = (Clob)this.elements[(int)paramLong + b7];
        return arrayOfClob;
      case 33:
        arrayOfRef = new Ref[paramInt];
        for (b8 = 0; b8 < paramInt; b8++)
          arrayOfRef[b8] = (Ref)this.elements[(int)paramLong + b8];
        return arrayOfRef;
      case 29:
      case 30:
      case 31:
        arrayOfTbArray = new TbArray[paramInt];
        for (b9 = 0; b9 < paramInt; b9++)
          arrayOfTbArray[b9] = (TbArray)this.elements[(int)paramLong + b9];
        return arrayOfTbArray;
    }
    Object[] arrayOfObject = new Object[paramInt];
    for (byte b10 = 0; b10 < paramInt; b10++)
      arrayOfObject[b10] = this.elements[(int)paramLong + b10];
    return arrayOfObject;
  }

  public Object getArray(long paramLong, int paramInt, Map<String, Class<?>> paramMap) throws SQLException {
    Object[] arrayOfObject = (Object[])getArray(paramLong, paramInt);
    Map<String, Class<?>> map = paramMap;
    if (arrayOfObject == null)
      return null;
    if (this.conn != null)
      for (byte b = 0; b < arrayOfObject.length && arrayOfObject[b] instanceof java.sql.SQLData; b++)
        arrayOfObject[b] = TbStruct.toStruct(arrayOfObject[b], (Connection)this.conn);
    if (map != null) {
      byte b = 0;
      while (b < arrayOfObject.length && arrayOfObject[b] instanceof Struct) {
        Class<?> clazz = ((TbStruct)arrayOfObject[b]).getDescriptor().getClassWithExplicitMap(map);
        if (clazz != null) {
          arrayOfObject[b] = ((TbStruct)arrayOfObject[b]).toClass(clazz, map);
          b++;
        }
      }
    }
    return arrayOfObject;
  }

  public Object getArray(Map<String, Class<?>> paramMap) throws SQLException {
    Serializable bool = (this.elements == null) ? false : this.elements.length;
    return getArray(0L, (Integer) bool, paramMap);
  }

  public int getBaseType() throws SQLException {
    return this.baseType;
  }

  public String getBaseTypeName() throws SQLException {
    return DataType.getDBTypeName(this.descriptor.getElementType());
  }

  public ResultSet getResultSet() throws SQLException {
    return getResultSet(0L, (this.elements == null) ? 0 : this.elements.length);
  }

  public ResultSet getResultSet(long paramLong, int paramInt) throws SQLException {
    return new TbArrayDataResultSet(this.conn, this, paramLong, paramInt, null);
  }

  public ResultSet getResultSet(long paramLong, int paramInt, Map<String, Class<?>> paramMap) throws SQLException {
    Map<String, Class<?>> map = paramMap;
    if (map == null)
      map = new HashMap<String, Class<?>>();
    return new TbArrayDataResultSet(this.conn, this, paramLong, paramInt, map);
  }

  public ResultSet getResultSet(Map<String, Class<?>> paramMap) throws SQLException {
    return getResultSet(0L, (this.elements == null) ? 0 : this.elements.length, paramMap);
  }

  public Params getSubParams() {
    return this.subParams;
  }

  public String getSQLTypeName() {
    return this.descriptor.getSQLTypeName();
  }

  public int length() {
    return (this.elements == null) ? 0 : this.elements.length;
  }

  class Params implements ParamContainer {
    private Binder[] elementBinders;

    private BindData bindData;

    private BigDecimal[] paramBigDecimal;

    private String[] paramString;

    private Timestamp[] paramTimestamp;

    private TbTimestampTZ[] paramTbTimestampTZ;

    private TbTimestamp[] paramTbTimestamp;

    private TbDate[] paramTbDate;

    private Calendar[] paramCalendar;

    private byte[][] paramBytes;

    private InputStream[] paramStream;

    private Reader[] paramReader;

    private Struct[] paramStruct;

    private Array[] paramArray;

    public BindData getBindData() {
      return this.bindData;
    }

    public Binder[][] getBinder() {
      return (Binder[][])null;
    }

    public Binder getBinder(int param1Int1, int param1Int2) {
      return this.elementBinders[param1Int2];
    }

    public int getParameterCnt() {
      return 1;
    }

    public byte getParamType(int param1Int1, int param1Int2) {
      return 0;
    }

    public byte[][] getParamTypes() {
      return (byte[][])null;
    }

    public byte[] getParamTypesOfRow(int param1Int) {
      return null;
    }

    public Array getParamArray(int param1Int1, int param1Int2) {
      return this.paramArray[param1Int2];
    }

    public Array[] getParamArrayOfRow(int param1Int) {
      return null;
    }

    public BigDecimal getParamBigDecimal(int param1Int1, int param1Int2) {
      return this.paramBigDecimal[param1Int2];
    }

    public BigDecimal[] getParamBigDecimalOfRow(int param1Int) {
      return null;
    }

    public byte[] getParamBytes(int param1Int1, int param1Int2) {
      return this.paramBytes[param1Int2];
    }

    public byte[][] getParamBytesOfRow(int param1Int) {
      return (byte[][])null;
    }

    public Calendar getParamCalendar(int param1Int1, int param1Int2) {
      return this.paramCalendar[param1Int2];
    }

    public Calendar[] getParamCalendarOfRow(int param1Int) {
      return null;
    }

    public double getParamDouble(int param1Int1, int param1Int2) {
      return 0.0D;
    }

    public double[] getParamDoubleOfRow(int param1Int) {
      return null;
    }

    public float getParamFloat(int param1Int1, int param1Int2) {
      return 0.0F;
    }

    public float[] getParamFloatOfRow(int param1Int) {
      return null;
    }

    public int getParamInt(int param1Int1, int param1Int2) {
      return 0;
    }

    public int[] getParamIntOfRow(int param1Int) {
      return null;
    }

    public long getParamLong(int param1Int1, int param1Int2) {
      return 0L;
    }

    public long[] getParamLongOfRow(int param1Int) {
      return null;
    }

    public Reader getParamReader(int param1Int1, int param1Int2) {
      return this.paramReader[param1Int2];
    }

    public Reader[] getParamReaderOfRow(int param1Int) {
      return null;
    }

    public InputStream getParamStream(int param1Int1, int param1Int2) {
      return this.paramStream[param1Int2];
    }

    public InputStream[] getParamStreamOfRow(int param1Int) {
      return null;
    }

    public String getParamString(int param1Int1, int param1Int2) {
      return this.paramString[param1Int2];
    }

    public String[] getParamStringOfRow(int param1Int) {
      return null;
    }

    public Struct getParamStruct(int param1Int1, int param1Int2) {
      return this.paramStruct[param1Int2];
    }

    public Struct[] getParamStructOfRow(int param1Int) {
      return null;
    }

    public TbDate getParamTbDate(int param1Int1, int param1Int2) {
      return this.paramTbDate[param1Int2];
    }

    public TbDate[] getParamTbDateOfRow(int param1Int) {
      return null;
    }

    public TbTimestamp getParamTbTimestamp(int param1Int1, int param1Int2) {
      return this.paramTbTimestamp[param1Int2];
    }

    public TbTimestamp[] getParamTbTimestampOfRow(int param1Int) {
      return null;
    }

    public TbTimestampTZ getParamTbTimestampTZ(int param1Int1, int param1Int2) {
      return this.paramTbTimestampTZ[param1Int2];
    }

    public TbTimestampTZ[] getParamTbTimestampTZOfRow(int param1Int) {
      return null;
    }

    public Timestamp getParamTimestamp(int param1Int1, int param1Int2) {
      return this.paramTimestamp[param1Int2];
    }

    public Timestamp[] getParamTimestampOfRow(int param1Int) {
      return null;
    }
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\TbArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */