package com.tmax.tibero.jdbc.data;

import tibero.jdbc.TbArray;
import com.tmax.tibero.jdbc.TbArrayDescriptor;
import com.tmax.tibero.jdbc.TbBlob;
import com.tmax.tibero.jdbc.TbClob;
import com.tmax.tibero.jdbc.TbIntervalDts;
import com.tmax.tibero.jdbc.TbIntervalYtm;
import com.tmax.tibero.jdbc.TbLob;
import com.tmax.tibero.jdbc.TbNClob;
import com.tmax.tibero.jdbc.TbRef;
import com.tmax.tibero.jdbc.TbRowId;
import com.tmax.tibero.jdbc.TbSQLXML;
import com.tmax.tibero.jdbc.TbStruct;
import com.tmax.tibero.jdbc.TbStructDescriptor;
import com.tmax.tibero.jdbc.TbTypeDescriptor;
import com.tmax.tibero.jdbc.TbXMLInputStream;
import com.tmax.tibero.jdbc.data.charset.CharsetMapper;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.driver.TbResultSet;
import com.tmax.tibero.jdbc.driver.TbResultSetFactory;
import com.tmax.tibero.jdbc.driver.TbStatement;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.msg.TbColumnDesc;
import com.tmax.tibero.jdbc.util.TbCommon;
import com.tmax.tibero.jdbc.util.TbDTFormatter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TimeZone;

public class DataTypeConverter implements Cloneable {
  public static final byte RPCOL_UDT_OBJ = -124;
  
  public static final byte RPCOL_UDT_COLL = -120;
  
  public static final byte RPCOL_UDT_ROOT = 1;
  
  public static final byte RPCOL_UDT_SUBTYPE = 2;
  
  public static final byte[] RPCOL_UDT_NOT_FINAL = new byte[] { -2, 0, 0, 0 };
  
  public static final int RPCOL_UDT_MIN_SIZE = 3;
  
  public static final byte RPCOL_NULLOBJ_W_DEPTH = -4;
  
  public static byte RPCOL_NULLOBJ;
  
  public static byte RPCOL_5BYTE;
  
  public static final int COLLECTION_META_LENGTH = 40;
  
  public static final int STRUCT_META_LENGTH = 40;
  
  public static final String _DESC_OID_PREFIX = "/O";
  
  public static final String _DESC_TOBJ_ID_PREFIX = "/T";
  
  public static final String _DESC_VERSION_NO_PREFIX = "/V";
  
  private TbConnection conn = null;
  
  private CharsetMapper charsetMapper = null;
  
  private CharsetMapper nCharsetMapper = null;
  
  public static final String BUDDHA_CALENDAR = "THAI BUDDHA";
  
  public static final int BUDDHA_YEAR_GAP = 543;
  
  private static final int[] pr2six = new int[] { 
      64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 
      64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 
      64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 
      64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 
      64, 64, 64, 62, 64, 64, 64, 63, 52, 53, 
      54, 55, 56, 57, 58, 59, 60, 61, 64, 64, 
      64, 64, 64, 64, 64, 0, 1, 2, 3, 4, 
      5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 
      15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 
      25, 64, 64, 64, 64, 64, 64, 26, 27, 28, 
      29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 
      39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 
      49, 50, 51, 64, 64, 64, 64, 64, 64, 64, 
      64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 
      64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 
      64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 
      64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 
      64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 
      64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 
      64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 
      64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 
      64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 
      64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 
      64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 
      64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 
      64, 64, 64, 64, 64, 64 };
  
  private static final byte[] d = new byte[] { 
      66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 
      64, 66, 66, 66, 66, 66, 66, 66, 66, 66, 
      66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 
      66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 
      66, 66, 66, 62, 66, 66, 66, 63, 52, 53, 
      54, 55, 56, 57, 58, 59, 60, 61, 66, 66, 
      66, 65, 66, 66, 66, 0, 1, 2, 3, 4, 
      5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 
      15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 
      25, 66, 66, 66, 66, 66, 66, 26, 27, 28, 
      29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 
      39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 
      49, 50, 51, 66, 66, 66, 66, 66, 66, 66, 
      66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 
      66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 
      66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 
      66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 
      66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 
      66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 
      66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 
      66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 
      66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 
      66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 
      66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 
      66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 
      66, 66, 66, 66, 66, 66 };
  
  public DataTypeConverter(TbConnection paramTbConnection) throws SQLException {
    this.charsetMapper = new CharsetMapper(2);
    this.conn = paramTbConnection;
  }
  
  public void buildColumnMetadata(TbColumnDesc[] paramArrayOfTbColumnDesc, TbResultSet paramTbResultSet, int paramInt) throws SQLException {
    buildColumnMetaData(paramArrayOfTbColumnDesc, 0, paramInt, paramTbResultSet.getCols());
    for (byte b = 0; b < paramInt; b++) {
      if (DataType.isLocatorCategory((paramArrayOfTbColumnDesc[b]).dataType))
        paramTbResultSet.setHaveLocator(true); 
    } 
  }
  
  public void buildColumnMetaData(TbColumnDesc[] paramArrayOfTbColumnDesc, int paramInt1, int paramInt2, Column[] paramArrayOfColumn) throws SQLException {
    if (paramArrayOfTbColumnDesc.length > paramInt2) {
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
      try {
        for (int i = paramInt2; i < paramArrayOfTbColumnDesc.length; i++) {
          if ((paramArrayOfTbColumnDesc[i]).name != null && (paramArrayOfTbColumnDesc[i]).name.matches("[0-9a-zA-Z]{32}")) {
            TbStructDescriptor tbStructDescriptor;
            TbArrayDescriptor tbArrayDescriptor;
            switch ((paramArrayOfTbColumnDesc[i]).dataType) {
              case 28:
              case 32:
                tbStructDescriptor = TbStructDescriptor.createDescriptor((paramArrayOfTbColumnDesc[i]).dataType, (paramArrayOfTbColumnDesc[i]).name, (Connection)this.conn);
                descPut(hashMap, Integer.valueOf(i), tbStructDescriptor);
                break;
              case 29:
              case 30:
              case 31:
                tbArrayDescriptor = TbArrayDescriptor.createDescriptor((paramArrayOfTbColumnDesc[i]).dataType, (paramArrayOfTbColumnDesc[i]).name, (Connection)this.conn);
                descPut(hashMap, Integer.valueOf(i), tbArrayDescriptor);
                break;
            } 
          } 
        } 
      } catch (SQLException sQLException) {
        if (sQLException.getErrorCode() <= -90400 && sQLException.getErrorCode() > -90500)
          throw sQLException; 
        this.conn.addWarning(TbError.newSQLWarning(-90664, sQLException));
      } catch (Exception exception) {
        this.conn.addWarning(TbError.newSQLWarning(-90664, exception));
      } 
      for (byte b = 0; b < paramInt2; b++) {
        if (DataType.isUDTCategory((paramArrayOfTbColumnDesc[b]).dataType)) {
          paramArrayOfColumn[b].set(b, paramArrayOfTbColumnDesc[b], (TbTypeDescriptor)descGet(hashMap, Integer.valueOf((paramArrayOfTbColumnDesc[b]).scale)));
        } else {
          paramArrayOfColumn[b].set(b, paramArrayOfTbColumnDesc[b]);
        } 
      } 
    } else {
      for (byte b = 0; b < paramInt2; b++)
        paramArrayOfColumn[b].set(b, paramArrayOfTbColumnDesc[b]); 
    } 
  }
  
  private static String colDesc2Str(TbColumnDesc[] paramArrayOfTbColumnDesc) {
    if (paramArrayOfTbColumnDesc == null)
      return "[(null)]"; 
    StringBuffer stringBuffer = new StringBuffer(paramArrayOfTbColumnDesc.length * 40);
    stringBuffer.append("[#\n");
    for (byte b = 0; b < paramArrayOfTbColumnDesc.length; b++)
      stringBuffer.append("  coldesc idx=").append(b).append("/type=").append((paramArrayOfTbColumnDesc[b]).dataType).append("/size=").append((paramArrayOfTbColumnDesc[b]).maxSize).append("/prec=").append((paramArrayOfTbColumnDesc[b]).precision).append("/scale=").append((paramArrayOfTbColumnDesc[b]).scale).append("/etc=").append((paramArrayOfTbColumnDesc[b]).etcMeta).append("/name=").append((paramArrayOfTbColumnDesc[b]).name).append("\n"); 
    stringBuffer.append("#]");
    return stringBuffer.toString();
  }
  
  private <K, V> V descGet(Map<K, V> paramMap, K paramK) {
    return paramMap.get(paramK);
  }
  
  private <K, V> V descPut(Map<K, V> paramMap, K paramK, V paramV) {
    return paramMap.put(paramK, paramV);
  }
  
  public int bytesToChars(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
    return this.charsetMapper.bytesToChars(paramArrayOfbyte, paramInt1, paramInt2, paramArrayOfchar, paramInt3, paramInt4, false);
  }
  
  public int bytesToNChars(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
    return this.nCharsetMapper.bytesToChars(paramArrayOfbyte, paramInt1, paramInt2, paramArrayOfchar, paramInt3, paramInt4, false);
  }
  
  public byte[] castFromBigDecimal(BigDecimal paramBigDecimal, int paramInt) throws SQLException {
    switch (paramInt) {
      case 2:
      case 3:
      case 10:
        return fromString(paramBigDecimal.toString());
      case 18:
      case 19:
        return fromNString(paramBigDecimal.toString());
      case 1:
        return fromBigDecimal(paramBigDecimal);
      case 4:
        return paramBigDecimal.toString().getBytes();
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt));
  }
  
  public int castFromBigDecimal(byte[] paramArrayOfbyte, int paramInt1, BigDecimal paramBigDecimal, int paramInt2) throws SQLException {
    byte[] arrayOfByte;
    switch (paramInt2) {
      case 2:
      case 3:
      case 10:
        return fromString(paramArrayOfbyte, paramInt1, paramBigDecimal.toString());
      case 18:
      case 19:
        return fromNString(paramArrayOfbyte, paramInt1, paramBigDecimal.toString());
      case 1:
        return fromBigDecimal(paramArrayOfbyte, paramInt1, paramBigDecimal);
      case 4:
        arrayOfByte = paramBigDecimal.toString().getBytes();
        System.arraycopy(arrayOfByte, 0, paramArrayOfbyte, paramInt1, arrayOfByte.length);
        return arrayOfByte.length;
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt2));
  }
  
  public byte[] castFromBoolean(boolean paramBoolean, int paramInt) throws SQLException {
    switch (paramInt) {
      case 1:
        return fromInt(paramBoolean ? 1 : 0);
      case 2:
      case 3:
        return fromString(paramBoolean ? "1" : "0");
      case 18:
      case 19:
        return fromNString(paramBoolean ? "1" : "0");
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt));
  }
  
  public int castFromBoolean(byte[] paramArrayOfbyte, int paramInt1, boolean paramBoolean, int paramInt2) throws SQLException {
    switch (paramInt2) {
      case 1:
        return fromInt(paramArrayOfbyte, paramInt1, paramBoolean ? 1 : 0);
      case 2:
      case 3:
        return fromString(paramArrayOfbyte, paramInt1, paramBoolean ? "1" : "0");
      case 18:
      case 19:
        return fromNString(paramArrayOfbyte, paramInt1, paramBoolean ? "1" : "0");
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt2));
  }
  
  public byte[] castFromByte(byte paramByte, int paramInt) throws SQLException {
    switch (paramInt) {
      case 1:
        return fromInt(paramByte);
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt));
  }
  
  public int castFromByte(byte[] paramArrayOfbyte, int paramInt1, byte paramByte, int paramInt2) throws SQLException {
    switch (paramInt2) {
      case 1:
        return fromInt(paramArrayOfbyte, paramInt1, paramByte);
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt2));
  }
  
  public byte[] castFromBytes(byte[] paramArrayOfbyte, int paramInt) throws SQLException {
    switch (paramInt) {
      case 2:
      case 3:
      case 10:
        return fromString(new String(paramArrayOfbyte));
      case 18:
      case 19:
        return fromNString(new String(paramArrayOfbyte));
      case 4:
        return paramArrayOfbyte;
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt));
  }
  
  public int castFromBytes(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2) throws SQLException {
    switch (paramInt2) {
      case 2:
      case 3:
      case 10:
        return fromString(paramArrayOfbyte1, paramInt1, new String(paramArrayOfbyte2));
      case 18:
      case 19:
        return fromNString(paramArrayOfbyte1, paramInt1, new String(paramArrayOfbyte2));
      case 4:
        System.arraycopy(paramArrayOfbyte2, 0, paramArrayOfbyte1, paramInt1, paramArrayOfbyte2.length);
        return paramArrayOfbyte2.length;
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt2));
  }
  
  public int castFromDate(byte[] paramArrayOfbyte, int paramInt1, Date paramDate, int paramInt2) throws SQLException {
    Calendar calendar;
    byte[] arrayOfByte;
    switch (paramInt2) {
      case 2:
      case 3:
      case 10:
        return fromString(paramArrayOfbyte, paramInt1, paramDate.toString());
      case 18:
      case 19:
        return fromNString(paramArrayOfbyte, paramInt1, paramDate.toString());
      case 5:
        calendar = Calendar.getInstance();
        calendar.setTime(paramDate);
        return fromDate(paramArrayOfbyte, paramInt1, calendar);
      case 7:
        calendar = Calendar.getInstance();
        calendar.setTime(paramDate);
        return fromTimestamp(paramArrayOfbyte, paramInt1, calendar, 0);
      case 6:
        calendar = Calendar.getInstance();
        calendar.setTime(paramDate);
        return fromTime(paramArrayOfbyte, paramInt1, calendar, 0);
      case 4:
        arrayOfByte = paramDate.toString().getBytes();
        System.arraycopy(arrayOfByte, 0, paramArrayOfbyte, paramInt1, arrayOfByte.length);
        return arrayOfByte.length;
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt2));
  }
  
  public int castFromTbDate(byte[] paramArrayOfbyte, int paramInt1, TbDate paramTbDate, int paramInt2) throws SQLException {
    TbTimestamp tbTimestamp;
    byte[] arrayOfByte;
    switch (paramInt2) {
      case 2:
      case 3:
      case 10:
        return fromString(paramArrayOfbyte, paramInt1, paramTbDate.toString());
      case 18:
      case 19:
        return fromNString(paramArrayOfbyte, paramInt1, paramTbDate.toString());
      case 5:
      case 6:
        return fromTbDate(paramArrayOfbyte, paramInt1, paramTbDate);
      case 7:
        tbTimestamp = new TbTimestamp(paramTbDate.getYear(), paramTbDate.getMonth(), paramTbDate.getDayOfMonth(), paramTbDate.getHourOfDay(), paramTbDate.getMinutes(), paramTbDate.getSeconds(), 0);
        return fromTbTimestamp(paramArrayOfbyte, paramInt1, tbTimestamp);
      case 4:
        arrayOfByte = paramTbDate.toString().getBytes();
        System.arraycopy(arrayOfByte, 0, paramArrayOfbyte, paramInt1, arrayOfByte.length);
        return arrayOfByte.length;
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt2));
  }
  
  public byte[] castFromDate(Date paramDate, int paramInt) throws SQLException {
    Calendar calendar;
    switch (paramInt) {
      case 2:
      case 3:
      case 10:
        return fromString(paramDate.toString());
      case 18:
      case 19:
        return fromNString(paramDate.toString());
      case 5:
        calendar = Calendar.getInstance();
        calendar.setTime(paramDate);
        return fromDate(calendar);
      case 7:
        calendar = Calendar.getInstance();
        calendar.setTime(paramDate);
        return fromTimestamp(calendar, 0);
      case 6:
        calendar = Calendar.getInstance();
        calendar.setTime(paramDate);
        return fromTime(calendar, 0);
      case 4:
        return paramDate.toString().getBytes();
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt));
  }
  
  public int castFromDouble(byte[] paramArrayOfbyte, int paramInt1, double paramDouble, int paramInt2) throws SQLException {
    byte[] arrayOfByte;
    switch (paramInt2) {
      case 2:
      case 3:
      case 10:
        return fromString(paramArrayOfbyte, paramInt1, Double.toString(paramDouble));
      case 18:
      case 19:
        return fromNString(paramArrayOfbyte, paramInt1, Double.toString(paramDouble));
      case 1:
        return fromDouble(paramArrayOfbyte, paramInt1, paramDouble);
      case 4:
        arrayOfByte = Double.toString(paramDouble).getBytes();
        System.arraycopy(arrayOfByte, 0, paramArrayOfbyte, paramInt1, arrayOfByte.length);
        return arrayOfByte.length;
      case 24:
        return fromBinaryDouble(paramArrayOfbyte, paramInt1, paramDouble);
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt2));
  }
  
  public byte[] castFromDouble(double paramDouble, int paramInt) throws SQLException {
    switch (paramInt) {
      case 2:
      case 3:
      case 10:
        return fromString(Double.toString(paramDouble));
      case 18:
      case 19:
        return fromNString(Double.toString(paramDouble));
      case 1:
        return fromDouble(paramDouble);
      case 4:
        return Double.toString(paramDouble).getBytes();
      case 24:
        return fromBinaryDouble(paramDouble);
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt));
  }
  
  public int castFromFloat(byte[] paramArrayOfbyte, int paramInt1, float paramFloat, int paramInt2) throws SQLException {
    byte[] arrayOfByte;
    switch (paramInt2) {
      case 2:
      case 3:
      case 10:
        return fromString(paramArrayOfbyte, paramInt1, Float.toString(paramFloat));
      case 18:
      case 19:
        return fromNString(paramArrayOfbyte, paramInt1, Float.toString(paramFloat));
      case 1:
        return fromFloat(paramArrayOfbyte, paramInt1, paramFloat);
      case 4:
        arrayOfByte = Float.toString(paramFloat).getBytes();
        System.arraycopy(arrayOfByte, 0, paramArrayOfbyte, paramInt1, arrayOfByte.length);
        return arrayOfByte.length;
      case 23:
        return fromBinaryFloat(paramArrayOfbyte, paramInt1, paramFloat);
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt2));
  }
  
  public byte[] castFromFloat(float paramFloat, int paramInt) throws SQLException {
    switch (paramInt) {
      case 2:
      case 3:
      case 10:
        return fromString(Float.toString(paramFloat));
      case 18:
      case 19:
        return fromNString(Float.toString(paramFloat));
      case 1:
        return fromFloat(paramFloat);
      case 4:
        return Float.toString(paramFloat).getBytes();
      case 23:
        return fromBinaryFloat(paramFloat);
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt));
  }
  
  public int castFromInt(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    byte[] arrayOfByte;
    switch (paramInt3) {
      case 2:
      case 3:
      case 10:
        return fromString(paramArrayOfbyte, paramInt1, Integer.toString(paramInt2));
      case 18:
      case 19:
        return fromNString(paramArrayOfbyte, paramInt1, Integer.toString(paramInt2));
      case 1:
        return fromInt(paramArrayOfbyte, paramInt1, paramInt2);
      case 4:
        arrayOfByte = Integer.toString(paramInt2).getBytes();
        System.arraycopy(arrayOfByte, 0, paramArrayOfbyte, paramInt1, arrayOfByte.length);
        return arrayOfByte.length;
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt3));
  }
  
  public byte[] castFromInt(int paramInt1, int paramInt2) throws SQLException {
    switch (paramInt2) {
      case 2:
      case 3:
      case 10:
        return fromString(Integer.toString(paramInt1));
      case 18:
      case 19:
        return fromNString(Integer.toString(paramInt1));
      case 1:
        return fromInt(paramInt1);
      case 4:
        return Integer.toString(paramInt1).getBytes();
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt2));
  }
  
  public int castFromLong(byte[] paramArrayOfbyte, int paramInt1, long paramLong, int paramInt2) throws SQLException {
    byte[] arrayOfByte;
    switch (paramInt2) {
      case 2:
      case 3:
      case 10:
        return fromString(paramArrayOfbyte, paramInt1, Long.toString(paramLong));
      case 18:
      case 19:
        return fromNString(paramArrayOfbyte, paramInt1, Long.toString(paramLong));
      case 1:
        return fromLong(paramArrayOfbyte, paramInt1, paramLong);
      case 4:
        arrayOfByte = Long.toString(paramLong).getBytes();
        System.arraycopy(arrayOfByte, 0, paramArrayOfbyte, paramInt1, arrayOfByte.length);
        return arrayOfByte.length;
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt2));
  }
  
  public byte[] castFromLong(long paramLong, int paramInt) throws SQLException {
    switch (paramInt) {
      case 2:
      case 3:
      case 10:
        return fromString(Long.toString(paramLong));
      case 18:
      case 19:
        return fromNString(Long.toString(paramLong));
      case 1:
        return fromLong(paramLong);
      case 4:
        return Long.toString(paramLong).getBytes();
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt));
  }
  
  public byte[] castFromObject(Object paramObject, int paramInt) throws SQLException {
    if (paramObject instanceof String)
      return castFromString((String)paramObject, paramInt); 
    if (paramObject instanceof Boolean)
      return castFromBoolean(((Boolean)paramObject).booleanValue(), paramInt); 
    if (paramObject instanceof Byte)
      return castFromByte(((Byte)paramObject).byteValue(), paramInt); 
    if (paramObject instanceof byte[])
      return castFromBytes((byte[])paramObject, paramInt); 
    if (paramObject instanceof BigDecimal)
      return castFromBigDecimal((BigDecimal)paramObject, paramInt); 
    if (paramObject instanceof Integer)
      return castFromInt(((Integer)paramObject).intValue(), paramInt); 
    if (paramObject instanceof Float)
      return castFromFloat(((Float)paramObject).floatValue(), paramInt); 
    if (paramObject instanceof Double)
      return castFromDouble(((Double)paramObject).doubleValue(), paramInt); 
    if (paramObject instanceof Long)
      return castFromLong(((Long)paramObject).longValue(), paramInt); 
    if (paramObject instanceof Short)
      return castFromShort(((Short)paramObject).shortValue(), paramInt); 
    if (paramObject instanceof Date)
      return castFromDate((Date)paramObject, paramInt); 
    if (paramObject instanceof Time)
      return castFromTime((Time)paramObject, paramInt); 
    if (paramObject instanceof Timestamp)
      return castFromTimestamp((Timestamp)paramObject, paramInt); 
    throw TbError.newSQLException(-90610, paramObject.toString());
  }
  
  public int castFromRowId(byte[] paramArrayOfbyte, int paramInt1, String paramString, int paramInt2) throws SQLException {
    ServerInfo serverInfo = this.conn.getServerInfo();
    int i = serverInfo.getServerEndian();
    switch (paramInt2) {
      case 15:
        return (serverInfo.getServerIsNanobase() == 1) ? (new TbNrowId(this.conn)).getServerBytes(paramArrayOfbyte, paramInt1, i, paramString) : (new TbRowId()).fromString(paramArrayOfbyte, paramInt1, i, paramString);
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt2));
  }
  
  public int castFromShort(byte[] paramArrayOfbyte, int paramInt1, short paramShort, int paramInt2) throws SQLException {
    byte[] arrayOfByte;
    switch (paramInt2) {
      case 2:
      case 3:
      case 10:
        return fromString(paramArrayOfbyte, paramInt1, Short.toString(paramShort));
      case 18:
      case 19:
        return fromNString(paramArrayOfbyte, paramInt1, Short.toString(paramShort));
      case 1:
        return fromShort(paramArrayOfbyte, paramInt1, paramShort);
      case 4:
        arrayOfByte = Short.toString(paramShort).getBytes();
        System.arraycopy(arrayOfByte, 0, paramArrayOfbyte, paramInt1, arrayOfByte.length);
        return arrayOfByte.length;
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt2));
  }
  
  public byte[] castFromShort(short paramShort, int paramInt) throws SQLException {
    switch (paramInt) {
      case 2:
      case 3:
      case 10:
        return fromString(Short.toString(paramShort));
      case 18:
      case 19:
        return fromNString(Short.toString(paramShort));
      case 1:
        return fromShort(paramShort);
      case 4:
        return Short.toString(paramShort).getBytes();
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt));
  }
  
  public int castFromString(byte[] paramArrayOfbyte, int paramInt1, String paramString, int paramInt2) throws SQLException {
    Calendar calendar;
    BigDecimal bigDecimal;
    byte[] arrayOfByte;
    Date date;
    Time time;
    Timestamp timestamp;
    switch (paramInt2) {
      case 2:
      case 3:
      case 10:
        return fromString(paramArrayOfbyte, paramInt1, paramString);
      case 18:
      case 19:
        return fromNString(paramArrayOfbyte, paramInt1, paramString);
      case 1:
        bigDecimal = new BigDecimal(paramString);
        return fromBigDecimal(paramArrayOfbyte, paramInt1, bigDecimal);
      case 4:
        arrayOfByte = paramString.toString().getBytes();
        System.arraycopy(arrayOfByte, 0, paramArrayOfbyte, paramInt1, arrayOfByte.length);
        return arrayOfByte.length;
      case 5:
        date = Date.valueOf(paramString);
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return fromDate(paramArrayOfbyte, paramInt1, calendar);
      case 6:
        time = Time.valueOf(paramString);
        calendar = Calendar.getInstance();
        calendar.setTime(time);
        return fromTime(paramArrayOfbyte, paramInt1, calendar, 0);
      case 7:
        timestamp = Timestamp.valueOf(paramString);
        calendar = Calendar.getInstance();
        calendar.setTime(timestamp);
        return fromTimestamp(paramArrayOfbyte, paramInt1, calendar, timestamp.getNanos());
      case 13:
        return stringToFixedBytes(paramString, 0, paramString.length(), paramArrayOfbyte, paramInt1, paramArrayOfbyte.length - paramInt1);
      case 20:
        return getDBEncodedNBytes(paramArrayOfbyte, paramInt1, paramString);
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt2));
  }
  
  public byte[] castFromString(String paramString, int paramInt) throws SQLException {
    Calendar calendar;
    BigDecimal bigDecimal;
    Date date;
    Time time;
    Timestamp timestamp;
    byte[] arrayOfByte;
    switch (paramInt) {
      case 2:
      case 3:
      case 10:
        return fromString(paramString);
      case 18:
      case 19:
        return fromNString(paramString);
      case 1:
        bigDecimal = new BigDecimal(paramString);
        return fromBigDecimal(bigDecimal);
      case 4:
        return paramString.toString().getBytes();
      case 5:
        date = Date.valueOf(paramString);
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return fromDate(calendar);
      case 6:
        time = Time.valueOf(paramString);
        calendar = Calendar.getInstance();
        calendar.setTime(time);
        return fromTime(calendar, 0);
      case 7:
        timestamp = Timestamp.valueOf(paramString);
        calendar = Calendar.getInstance();
        calendar.setTime(timestamp);
        return fromTimestamp(calendar, timestamp.getNanos());
      case 13:
        arrayOfByte = new byte[paramString.length() * 2];
        stringToFixedBytes(paramString, 0, paramString.length(), arrayOfByte, 0, arrayOfByte.length);
        return arrayOfByte;
      case 20:
        return getDBEncodedNBytes(paramString);
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt));
  }
  
  public int castFromTime(byte[] paramArrayOfbyte, int paramInt1, Time paramTime, int paramInt2) throws SQLException {
    Calendar calendar;
    byte[] arrayOfByte;
    switch (paramInt2) {
      case 2:
      case 3:
      case 10:
        return fromString(paramArrayOfbyte, paramInt1, paramTime.toString());
      case 18:
      case 19:
        return fromNString(paramArrayOfbyte, paramInt1, paramTime.toString());
      case 6:
        calendar = Calendar.getInstance();
        calendar.setTime(paramTime);
        return fromTime(paramArrayOfbyte, paramInt1, calendar, 0);
      case 5:
        calendar = Calendar.getInstance();
        calendar.setTime(paramTime);
        return fromDate(paramArrayOfbyte, paramInt1, calendar);
      case 7:
        calendar = Calendar.getInstance();
        calendar.setTime(paramTime);
        return fromTimestamp(paramArrayOfbyte, paramInt1, calendar, 0);
      case 4:
        arrayOfByte = paramTime.toString().getBytes();
        System.arraycopy(arrayOfByte, 0, paramArrayOfbyte, paramInt1, arrayOfByte.length);
        return arrayOfByte.length;
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt2));
  }
  
  public byte[] castFromTime(Time paramTime, int paramInt) throws SQLException {
    Calendar calendar;
    switch (paramInt) {
      case 2:
      case 3:
      case 10:
        return fromString(paramTime.toString());
      case 18:
      case 19:
        return fromNString(paramTime.toString());
      case 6:
        calendar = Calendar.getInstance();
        calendar.setTime(paramTime);
        return fromTime(calendar, 0);
      case 5:
        calendar = Calendar.getInstance();
        calendar.setTime(paramTime);
        return fromDate(calendar);
      case 7:
        calendar = Calendar.getInstance();
        calendar.setTime(paramTime);
        return fromTimestamp(calendar, 0);
      case 4:
        return paramTime.toString().getBytes();
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt));
  }
  
  public int castFromTimestamp(byte[] paramArrayOfbyte, int paramInt1, Timestamp paramTimestamp, int paramInt2) throws SQLException {
    Calendar calendar;
    byte[] arrayOfByte;
    switch (paramInt2) {
      case 2:
      case 3:
      case 10:
        return fromString(paramArrayOfbyte, paramInt1, paramTimestamp.toString());
      case 18:
      case 19:
        return fromNString(paramArrayOfbyte, paramInt1, paramTimestamp.toString());
      case 5:
        calendar = Calendar.getInstance();
        calendar.setTime(paramTimestamp);
        return fromDate(paramArrayOfbyte, paramInt1, calendar);
      case 6:
        calendar = Calendar.getInstance();
        calendar.setTime(paramTimestamp);
        return fromTime(paramArrayOfbyte, paramInt1, calendar, paramTimestamp.getNanos());
      case 7:
        calendar = Calendar.getInstance();
        calendar.setTime(paramTimestamp);
        return fromTimestamp(paramArrayOfbyte, paramInt1, calendar, paramTimestamp.getNanos());
      case 4:
        arrayOfByte = paramTimestamp.toString().getBytes();
        System.arraycopy(arrayOfByte, 0, paramArrayOfbyte, paramInt1, arrayOfByte.length);
        return arrayOfByte.length;
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt2));
  }
  
  public byte[] castFromTimestamp(Timestamp paramTimestamp, int paramInt) throws SQLException {
    Calendar calendar;
    switch (paramInt) {
      case 2:
      case 3:
      case 10:
        return fromString(paramTimestamp.toString());
      case 18:
      case 19:
        return fromNString(paramTimestamp.toString());
      case 5:
        calendar = Calendar.getInstance();
        calendar.setTime(paramTimestamp);
        return fromDate(calendar);
      case 6:
        calendar = Calendar.getInstance();
        calendar.setTime(paramTimestamp);
        return fromTime(calendar, paramTimestamp.getNanos());
      case 7:
        calendar = Calendar.getInstance();
        calendar.setTime(paramTimestamp);
        return fromTimestamp(calendar, paramTimestamp.getNanos());
      case 4:
        return paramTimestamp.toString().getBytes();
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt));
  }
  
  public int castFromTbTimestamp(byte[] paramArrayOfbyte, int paramInt1, TbTimestamp paramTbTimestamp, int paramInt2) throws SQLException {
    TbDate tbDate;
    byte[] arrayOfByte;
    switch (paramInt2) {
      case 2:
      case 3:
      case 10:
        return fromString(paramArrayOfbyte, paramInt1, paramTbTimestamp.toString());
      case 18:
      case 19:
        return fromNString(paramArrayOfbyte, paramInt1, paramTbTimestamp.toString());
      case 5:
      case 6:
        tbDate = new TbDate(paramTbTimestamp.getYear(), paramTbTimestamp.getMonth(), paramTbTimestamp.getDayOfMonth(), paramTbTimestamp.getHourOfDay(), paramTbTimestamp.getMinutes(), paramTbTimestamp.getSeconds());
        return fromTbDate(paramArrayOfbyte, paramInt1, tbDate);
      case 7:
        return fromTbTimestamp(paramArrayOfbyte, paramInt1, paramTbTimestamp);
      case 4:
        arrayOfByte = paramTbTimestamp.toString().getBytes();
        System.arraycopy(arrayOfByte, 0, paramArrayOfbyte, paramInt1, arrayOfByte.length);
        return arrayOfByte.length;
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt2));
  }
  
  public int castFromTbTimestampTZ(byte[] paramArrayOfbyte, int paramInt1, TbTimestampTZ paramTbTimestampTZ, int paramInt2) throws SQLException {
    Calendar calendar1;
    TbDate tbDate;
    Calendar calendar2;
    TbTimestamp tbTimestamp;
    byte[] arrayOfByte;
    switch (paramInt2) {
      case 2:
      case 3:
      case 10:
        return fromString(paramArrayOfbyte, paramInt1, paramTbTimestampTZ.toString());
      case 18:
      case 19:
        return fromNString(paramArrayOfbyte, paramInt1, paramTbTimestampTZ.toString());
      case 5:
      case 6:
        calendar1 = Calendar.getInstance(paramTbTimestampTZ.getTimeZone());
        calendar1.setTimeInMillis(paramTbTimestampTZ.getTime());
        tbDate = new TbDate(calendar1.get(1), calendar1.get(2), calendar1.get(5), calendar1.get(11), calendar1.get(12), calendar1.get(13));
        return fromTbDate(paramArrayOfbyte, paramInt1, tbDate);
      case 7:
        calendar2 = Calendar.getInstance(paramTbTimestampTZ.getTimeZone());
        calendar2.setTimeInMillis(paramTbTimestampTZ.getTime());
        tbTimestamp = new TbTimestamp(calendar2.getTimeInMillis());
        return fromTbTimestamp(paramArrayOfbyte, paramInt1, tbTimestamp);
      case 21:
        return fromTbTimestampTZ(paramArrayOfbyte, paramInt1, paramTbTimestampTZ);
      case 4:
        arrayOfByte = paramTbTimestampTZ.toString().getBytes();
        System.arraycopy(arrayOfByte, 0, paramArrayOfbyte, paramInt1, arrayOfByte.length);
        return arrayOfByte.length;
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt2));
  }
  
  public int castFromTimestampLTZ(byte[] paramArrayOfbyte, int paramInt1, Timestamp paramTimestamp, int paramInt2) throws SQLException {
    TimeZone timeZone;
    byte[] arrayOfByte;
    Calendar calendar;
    TbDate tbDate;
    TbTimestamp tbTimestamp;
    switch (paramInt2) {
      case 2:
      case 3:
      case 10:
        return fromString(paramArrayOfbyte, paramInt1, paramTimestamp.toString());
      case 18:
      case 19:
        return fromNString(paramArrayOfbyte, paramInt1, paramTimestamp.toString());
      case 5:
      case 6:
        timeZone = TimeZone.getTimeZone("UTC");
        calendar = Calendar.getInstance(timeZone);
        calendar.setTimeInMillis(paramTimestamp.getTime());
        tbDate = new TbDate(calendar.get(1), calendar.get(2), calendar.get(5), calendar.get(11), calendar.get(12), calendar.get(13));
        return fromTbDate(paramArrayOfbyte, paramInt1, tbDate);
      case 22:
        timeZone = TimeZone.getTimeZone("UTC");
        calendar = Calendar.getInstance(timeZone);
        calendar.setTimeInMillis(paramTimestamp.getTime());
        tbTimestamp = new TbTimestamp(calendar.getTimeInMillis(), timeZone);
        return fromTbTimestamp(paramArrayOfbyte, paramInt1, tbTimestamp);
      case 4:
        arrayOfByte = paramTimestamp.toString().getBytes();
        System.arraycopy(arrayOfByte, 0, paramArrayOfbyte, paramInt1, arrayOfByte.length);
        return arrayOfByte.length;
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt2));
  }
  
  public int castFromTbIntervalYtm(byte[] paramArrayOfbyte, int paramInt1, TbIntervalYtm paramTbIntervalYtm, int paramInt2) throws SQLException {
    switch (paramInt2) {
      case 8:
        return fromTbIntervalYtm(paramArrayOfbyte, paramInt1, paramTbIntervalYtm);
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt2));
  }
  
  public int castFromTbIntervalDts(byte[] paramArrayOfbyte, int paramInt1, TbIntervalDts paramTbIntervalDts, int paramInt2) throws SQLException {
    switch (paramInt2) {
      case 9:
        return fromTbIntervalDts(paramArrayOfbyte, paramInt1, paramTbIntervalDts);
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt2));
  }
  
  public int charsToBytes(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    return this.charsetMapper.charsToBytes(paramArrayOfchar, paramInt1, paramInt2, paramArrayOfbyte, paramInt3, paramInt4, false);
  }
  
  public int charsToFixedBytes(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    byte b1 = 0;
    byte b2 = 0;
    int i = paramInt3;
    for (int j = paramInt1; j < paramInt1 + paramInt2; j++) {
      b1 = (byte)(paramArrayOfchar[j] >> 8 & 0xFF);
      b2 = (byte)(paramArrayOfchar[j] & 0xFF);
      if (i + 1 >= paramInt3 + paramInt4)
        throw TbError.newSQLException(-590744, (i + 1) + " >= " + paramInt4); 
      paramArrayOfbyte[i++] = b1;
      paramArrayOfbyte[i++] = b2;
    } 
    return i - paramInt3;
  }
  
  public int fixedBytesToChars(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
    int i = paramInt3;
    if (paramInt2 % 2 != 0)
      throw TbError.newSQLException(-590748, paramInt2); 
    for (int j = paramInt1; j < paramInt1 + paramInt2; j += 2) {
      if (i >= paramInt3 + paramInt4)
        throw TbError.newSQLException(-590744, i + " >= " + paramInt3 + " + " + paramInt4); 
      paramArrayOfchar[i++] = (char)((short)(paramArrayOfbyte[j] << 8 & 0xFFFF) + (short)(paramArrayOfbyte[j + 1] & 0xFF));
    } 
    return i - paramInt3;
  }
  
  private byte[] fromBigDecimal(BigDecimal paramBigDecimal) throws SQLException {
    return TbNumber.fromString(paramBigDecimal.toString());
  }
  
  public int fromBigDecimal(byte[] paramArrayOfbyte, int paramInt, BigDecimal paramBigDecimal) throws SQLException {
    return TbNumber.fromString(paramArrayOfbyte, paramInt, paramBigDecimal.toString());
  }
  
  public int fromDate(byte[] paramArrayOfbyte, int paramInt, Calendar paramCalendar) throws SQLException {
    int i = paramCalendar.get(1);
    paramArrayOfbyte[paramInt] = (byte)(i / 100 + 100);
    paramArrayOfbyte[paramInt + 1] = (byte)(i % 100 + 100);
    paramArrayOfbyte[paramInt + 2] = (byte)(paramCalendar.get(2) + 1);
    paramArrayOfbyte[paramInt + 3] = (byte)paramCalendar.get(5);
    if (paramCalendar.get(9) == 0) {
      paramArrayOfbyte[paramInt + 4] = (byte)paramCalendar.get(10);
    } else {
      paramArrayOfbyte[paramInt + 4] = (byte)(paramCalendar.get(10) + 12);
    } 
    paramArrayOfbyte[paramInt + 5] = (byte)paramCalendar.get(12);
    paramArrayOfbyte[paramInt + 6] = (byte)paramCalendar.get(13);
    paramArrayOfbyte[paramInt + 7] = 0;
    return 8;
  }
  
  private byte[] fromDate(Calendar paramCalendar) throws SQLException {
    byte[] arrayOfByte = new byte[8];
    int i = paramCalendar.get(1);
    arrayOfByte[0] = (byte)(i / 100 + 100);
    arrayOfByte[1] = (byte)(i % 100 + 100);
    arrayOfByte[2] = (byte)(paramCalendar.get(2) + 1);
    arrayOfByte[3] = (byte)paramCalendar.get(5);
    if (paramCalendar.get(9) == 0) {
      arrayOfByte[4] = (byte)paramCalendar.get(10);
    } else {
      arrayOfByte[4] = (byte)(paramCalendar.get(10) + 12);
    } 
    arrayOfByte[5] = (byte)paramCalendar.get(12);
    arrayOfByte[6] = (byte)paramCalendar.get(13);
    return arrayOfByte;
  }
  
  public int fromTbDate(byte[] paramArrayOfbyte, int paramInt, TbDate paramTbDate) throws SQLException {
    System.arraycopy(paramTbDate.getBytes(), 0, paramArrayOfbyte, paramInt, 8);
    return 8;
  }
  
  public int fromDouble(byte[] paramArrayOfbyte, int paramInt, double paramDouble) throws SQLException {
    return TbNumber.fromString(paramArrayOfbyte, paramInt, Double.toString(paramDouble));
  }
  
  private byte[] fromDouble(double paramDouble) throws SQLException {
    return TbNumber.fromString(Double.toString(paramDouble));
  }
  
  public int fromFloat(byte[] paramArrayOfbyte, int paramInt, float paramFloat) throws SQLException {
    return TbNumber.fromString(paramArrayOfbyte, paramInt, Float.toString(paramFloat));
  }
  
  private byte[] fromFloat(float paramFloat) throws SQLException {
    return TbNumber.fromString(Float.toString(paramFloat));
  }
  
  public int fromBinaryDouble(byte[] paramArrayOfbyte, int paramInt, double paramDouble) throws SQLException {
    return convertDouble2Bytes(paramArrayOfbyte, paramInt, paramDouble);
  }
  
  public byte[] fromBinaryDouble(double paramDouble) throws SQLException {
    byte[] arrayOfByte = new byte[8];
    convertDouble2Bytes(arrayOfByte, 0, paramDouble);
    return arrayOfByte;
  }
  
  private int convertDouble2Bytes(byte[] paramArrayOfbyte, int paramInt, double paramDouble) {
    byte b = 8;
    if (Double.compare(paramDouble, Double.NaN) == 0) {
      paramArrayOfbyte[paramInt] = -1;
      paramArrayOfbyte[paramInt + 1] = -8;
      paramArrayOfbyte[paramInt + 2] = 0;
      paramArrayOfbyte[paramInt + 3] = 0;
      paramArrayOfbyte[paramInt + 4] = 0;
      paramArrayOfbyte[paramInt + 5] = 0;
      paramArrayOfbyte[paramInt + 6] = 0;
      paramArrayOfbyte[paramInt + 7] = 0;
    } else if (Double.compare(paramDouble, -0.0D) == 0 || Double.compare(paramDouble, 0.0D) == 0) {
      paramArrayOfbyte[paramInt] = Byte.MIN_VALUE;
      paramArrayOfbyte[paramInt + 1] = 0;
      paramArrayOfbyte[paramInt + 2] = 0;
      paramArrayOfbyte[paramInt + 3] = 0;
      paramArrayOfbyte[paramInt + 4] = 0;
      paramArrayOfbyte[paramInt + 5] = 0;
      paramArrayOfbyte[paramInt + 6] = 0;
      paramArrayOfbyte[paramInt + 7] = 0;
    } else if (paramDouble > 0.0D) {
      paramDouble *= -1.0D;
      long l = Double.doubleToRawLongBits(paramDouble);
      TbCommon.long2Bytes(l, paramArrayOfbyte, paramInt, b);
    } else if (paramDouble < 0.0D) {
      long l = Double.doubleToRawLongBits(paramDouble);
      l ^= 0xFFFFFFFFFFFFFFFFL;
      TbCommon.long2Bytes(l, paramArrayOfbyte, paramInt, b);
    } else {
      long l = Double.doubleToRawLongBits(paramDouble);
      TbCommon.long2Bytes(l, paramArrayOfbyte, paramInt, b);
    } 
    return b;
  }
  
  public int fromBinaryFloat(byte[] paramArrayOfbyte, int paramInt, float paramFloat) throws SQLException {
    return convertFloat2Bytes(paramArrayOfbyte, paramInt, paramFloat);
  }
  
  public byte[] fromBinaryFloat(float paramFloat) throws SQLException {
    byte[] arrayOfByte = new byte[4];
    convertFloat2Bytes(arrayOfByte, 0, paramFloat);
    return arrayOfByte;
  }
  
  private int convertFloat2Bytes(byte[] paramArrayOfbyte, int paramInt, float paramFloat) {
    byte b = 4;
    if (Float.compare(paramFloat, Float.NaN) == 0) {
      paramArrayOfbyte[paramInt] = -1;
      paramArrayOfbyte[paramInt + 1] = -64;
      paramArrayOfbyte[paramInt + 2] = 0;
      paramArrayOfbyte[paramInt + 3] = 0;
    } else if (Float.compare(paramFloat, -0.0F) == 0 || Float.compare(paramFloat, 0.0F) == 0) {
      paramArrayOfbyte[paramInt] = Byte.MIN_VALUE;
      paramArrayOfbyte[paramInt + 1] = 0;
      paramArrayOfbyte[paramInt + 2] = 0;
      paramArrayOfbyte[paramInt + 3] = 0;
    } else if (paramFloat > 0.0F) {
      paramFloat *= -1.0F;
      int i = Float.floatToRawIntBits(paramFloat);
      TbCommon.int2Bytes(i, paramArrayOfbyte, paramInt, b);
    } else if (paramFloat < 0.0F) {
      int i = Float.floatToRawIntBits(paramFloat);
      i ^= 0xFFFFFFFF;
      TbCommon.int2Bytes(i, paramArrayOfbyte, paramInt, b);
    } else {
      int i = Float.floatToRawIntBits(paramFloat);
      TbCommon.int2Bytes(i, paramArrayOfbyte, paramInt, b);
    } 
    return b;
  }
  
  public int fromInt(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    return TbNumber.fromInteger(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  private byte[] fromInt(int paramInt) throws SQLException {
    return TbNumber.fromInteger(paramInt);
  }
  
  public int fromLong(byte[] paramArrayOfbyte, int paramInt, long paramLong) throws SQLException {
    return TbNumber.fromLong(paramArrayOfbyte, paramInt, paramLong);
  }
  
  private byte[] fromLong(long paramLong) throws SQLException {
    return TbNumber.fromLong(paramLong);
  }
  
  public int fromNString(byte[] paramArrayOfbyte, int paramInt, String paramString) throws SQLException {
    return getDBEncodedNBytes(paramArrayOfbyte, paramInt, paramString);
  }
  
  private byte[] fromNString(String paramString) throws SQLException {
    return getDBEncodedNBytes(paramString);
  }
  
  public int fromShort(byte[] paramArrayOfbyte, int paramInt, short paramShort) throws SQLException {
    return fromInt(paramArrayOfbyte, paramInt, paramShort);
  }
  
  private byte[] fromShort(short paramShort) throws SQLException {
    return fromInt(paramShort);
  }
  
  public int fromString(byte[] paramArrayOfbyte, int paramInt, String paramString) throws SQLException {
    return getDBEncodedBytes(paramArrayOfbyte, paramInt, paramString);
  }
  
  public byte[] fromString(String paramString) throws SQLException {
    return getDBEncodedBytes(paramString);
  }
  
  public int fromTime(byte[] paramArrayOfbyte, int paramInt1, Calendar paramCalendar, int paramInt2) throws SQLException {
    int i = paramCalendar.get(10);
    if (paramCalendar.get(9) == 1)
      i += 12; 
    paramArrayOfbyte[paramInt1] = (byte)i;
    paramArrayOfbyte[paramInt1 + 1] = (byte)paramCalendar.get(12);
    paramArrayOfbyte[paramInt1 + 2] = (byte)paramCalendar.get(13);
    paramArrayOfbyte[paramInt1 + 3] = 0;
    if (paramInt2 != 0) {
      TbCommon.int2Bytes(paramInt2, paramArrayOfbyte, paramInt1 + 4, 4);
    } else {
      int j = paramCalendar.get(14) * 1000000;
      TbCommon.int2Bytes(j, paramArrayOfbyte, paramInt1 + 4, 4);
    } 
    return 8;
  }
  
  private byte[] fromTime(Calendar paramCalendar, int paramInt) throws SQLException {
    byte[] arrayOfByte = new byte[8];
    int i = paramCalendar.get(10);
    if (paramCalendar.get(9) == 1)
      i += 12; 
    arrayOfByte[0] = (byte)i;
    arrayOfByte[1] = (byte)paramCalendar.get(12);
    arrayOfByte[2] = (byte)paramCalendar.get(13);
    arrayOfByte[3] = 0;
    TbCommon.int2Bytes(paramInt, arrayOfByte, 4, 4);
    return arrayOfByte;
  }
  
  public int fromTimestamp(byte[] paramArrayOfbyte, int paramInt1, Calendar paramCalendar, int paramInt2) throws SQLException {
    int i = paramCalendar.get(1);
    int j = paramCalendar.get(10);
    if (paramCalendar.get(9) == 1)
      j += 12; 
    paramArrayOfbyte[paramInt1] = (byte)(i / 100 + 100);
    paramArrayOfbyte[paramInt1 + 1] = (byte)(i % 100 + 100);
    paramArrayOfbyte[paramInt1 + 2] = (byte)(paramCalendar.get(2) + 1);
    paramArrayOfbyte[paramInt1 + 3] = (byte)paramCalendar.get(5);
    paramArrayOfbyte[paramInt1 + 4] = (byte)j;
    paramArrayOfbyte[paramInt1 + 5] = (byte)paramCalendar.get(12);
    paramArrayOfbyte[paramInt1 + 6] = (byte)paramCalendar.get(13);
    paramArrayOfbyte[paramInt1 + 7] = 0;
    TbCommon.int2Bytes(paramInt2, paramArrayOfbyte, paramInt1 + 8, 4);
    return 12;
  }
  
  public int fromTimestampTZ(byte[] paramArrayOfbyte, int paramInt1, Calendar paramCalendar, int paramInt2, TimeZone paramTimeZone) throws SQLException {
    int i = paramCalendar.get(1);
    int j = paramCalendar.get(10);
    if (paramCalendar.get(9) == 1)
      j += 12; 
    paramArrayOfbyte[paramInt1] = (byte)(i / 100 + 100);
    paramArrayOfbyte[paramInt1 + 1] = (byte)(i % 100 + 100);
    paramArrayOfbyte[paramInt1 + 2] = (byte)(paramCalendar.get(2) + 1);
    paramArrayOfbyte[paramInt1 + 3] = (byte)paramCalendar.get(5);
    paramArrayOfbyte[paramInt1 + 4] = (byte)j;
    paramArrayOfbyte[paramInt1 + 5] = (byte)paramCalendar.get(12);
    paramArrayOfbyte[paramInt1 + 6] = (byte)paramCalendar.get(13);
    paramArrayOfbyte[paramInt1 + 7] = 0;
    TbCommon.int2Bytes(paramInt2, paramArrayOfbyte, paramInt1 + 8, 4);
    String str = paramTimeZone.getID();
    int k = ZoneInfo.getTimeZoneIdByName(str);
    int m = paramTimeZone.getOffset(paramCalendar.getTimeInMillis());
    m /= 1000;
    int n = m % 60;
    int i1 = m / 60 % 60;
    int i2 = m / 3600;
    paramArrayOfbyte[paramInt1 + 12] = (byte)((0xFF & i2) + 100);
    paramArrayOfbyte[paramInt1 + 13] = (byte)(0xFF & i1);
    paramArrayOfbyte[paramInt1 + 14] = (byte)(0xFF & n);
    TbCommon.int2Bytes(k, paramArrayOfbyte, paramInt1 + 15, 2);
    return 17;
  }
  
  private byte[] fromTimestamp(Calendar paramCalendar, int paramInt) throws SQLException {
    byte[] arrayOfByte = new byte[12];
    int i = paramCalendar.get(1);
    int j = paramCalendar.get(10);
    if (paramCalendar.get(9) == 1)
      j += 12; 
    arrayOfByte[0] = (byte)(i / 100 + 100);
    arrayOfByte[1] = (byte)(i % 100 + 100);
    arrayOfByte[2] = (byte)(paramCalendar.get(2) + 1);
    arrayOfByte[3] = (byte)paramCalendar.get(5);
    arrayOfByte[4] = (byte)j;
    arrayOfByte[5] = (byte)paramCalendar.get(12);
    arrayOfByte[6] = (byte)paramCalendar.get(13);
    TbCommon.int2Bytes(paramInt, arrayOfByte, 8, 4);
    return arrayOfByte;
  }
  
  public int fromTbTimestamp(byte[] paramArrayOfbyte, int paramInt, TbTimestamp paramTbTimestamp) throws SQLException {
    System.arraycopy(paramTbTimestamp.getBytes(), 0, paramArrayOfbyte, paramInt, 12);
    return 12;
  }
  
  public int fromTbTimestampTZ(byte[] paramArrayOfbyte, int paramInt, TbTimestampTZ paramTbTimestampTZ) throws SQLException {
    Calendar calendar = Calendar.getInstance(ZoneInfo.TZ_UTC);
    calendar.setTimeInMillis(paramTbTimestampTZ.getTime());
    return fromTimestampTZ(paramArrayOfbyte, paramInt, calendar, paramTbTimestampTZ.getNanos(), paramTbTimestampTZ.getTimeZone());
  }
  
  public int fromTbIntervalYtm(byte[] paramArrayOfbyte, int paramInt, TbIntervalYtm paramTbIntervalYtm) throws SQLException {
    System.arraycopy(paramTbIntervalYtm.getBytes(), 0, paramArrayOfbyte, paramInt, 5);
    return 5;
  }
  
  public int fromTbIntervalDts(byte[] paramArrayOfbyte, int paramInt, TbIntervalDts paramTbIntervalDts) throws SQLException {
    System.arraycopy(paramTbIntervalDts.getBytes(), 0, paramArrayOfbyte, paramInt, 12);
    return 12;
  }
  
  private String getDBDecodedNString(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    return this.nCharsetMapper.bytesToString(paramArrayOfbyte, paramInt1, paramInt2, false);
  }
  
  public String getDBDecodedString(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    return this.charsetMapper.bytesToString(paramArrayOfbyte, paramInt1, paramInt2, false);
  }
  
  private int getDBEncodedBytes(byte[] paramArrayOfbyte, int paramInt, String paramString) throws SQLException {
    return this.charsetMapper.stringToBytes(paramArrayOfbyte, paramInt, paramString);
  }
  
  public byte[] getDBEncodedBytes(String paramString) throws SQLException {
    return this.charsetMapper.stringToBytes(paramString);
  }
  
  private int getDBEncodedNBytes(byte[] paramArrayOfbyte, int paramInt, String paramString) throws SQLException {
    return this.nCharsetMapper.stringToBytes(paramArrayOfbyte, paramInt, paramString);
  }
  
  private byte[] getDBEncodedNBytes(String paramString) throws SQLException {
    return this.nCharsetMapper.stringToBytes(paramString);
  }
  
  public int getEndingBytePos(byte[] paramArrayOfbyte, int paramInt) {
    return this.charsetMapper.getEndingBytePos(paramArrayOfbyte, paramInt);
  }
  
  public int getEndingBytePosNCharset(byte[] paramArrayOfbyte, int paramInt) {
    return this.nCharsetMapper.getEndingBytePos(paramArrayOfbyte, paramInt);
  }
  
  public int getLeadingBytePos(byte[] paramArrayOfbyte, int paramInt) {
    return this.charsetMapper.getLeadingBytePos(paramArrayOfbyte, paramInt);
  }
  
  public int getLeadingBytePosNCharset(byte[] paramArrayOfbyte, int paramInt) {
    return this.nCharsetMapper.getLeadingBytePos(paramArrayOfbyte, paramInt);
  }
  
  public int getMaxBytesPerChar() {
    return this.charsetMapper.getMaxBytesPerChar();
  }
  
  public int getMaxBytesPerNChar() {
    return this.nCharsetMapper.getMaxBytesPerChar();
  }
  
  public int getUCS2MaxBytesPerChar() {
    return 2;
  }
  
  public boolean isEndingByte(byte[] paramArrayOfbyte, int paramInt) {
    return this.charsetMapper.isEndingByte(paramArrayOfbyte, paramInt);
  }
  
  public boolean isEndingByteNCharset(byte[] paramArrayOfbyte, int paramInt) {
    return this.nCharsetMapper.isEndingByte(paramArrayOfbyte, paramInt);
  }
  
  public boolean isLeadingByte(byte[] paramArrayOfbyte, int paramInt) {
    return this.charsetMapper.isLeadingByte(paramArrayOfbyte, paramInt);
  }
  
  public boolean isLeadingByteNCharset(byte[] paramArrayOfbyte, int paramInt) {
    return this.nCharsetMapper.isLeadingByte(paramArrayOfbyte, paramInt);
  }
  
  public int nCharsToBytes(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    return this.nCharsetMapper.charsToBytes(paramArrayOfchar, paramInt1, paramInt2, paramArrayOfbyte, paramInt3, paramInt4, false);
  }
  
  public void reset() {
    this.charsetMapper = null;
    this.nCharsetMapper = null;
    this.conn = null;
  }
  
  public void setCharset(int paramInt1, int paramInt2) throws SQLException {
    if (paramInt1 == 27)
      paramInt1 = 7; 
    if (paramInt2 == 27)
      paramInt2 = 7; 
    if (paramInt1 == 28 || paramInt1 == 29)
      paramInt1 = 13; 
    if (paramInt2 == 28 || paramInt2 == 29)
      paramInt2 = 13; 
    this.charsetMapper = new CharsetMapper(paramInt1);
    this.nCharsetMapper = new CharsetMapper(paramInt2);
  }
  
  private int stringToFixedBytes(String paramString, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
    byte b1 = 0;
    byte b2 = 0;
    int i = paramInt3;
    for (int j = paramInt1; j < paramInt2; j++) {
      b1 = (byte)(paramString.charAt(j) >> 8 & 0xFF);
      b2 = (byte)(paramString.charAt(j) & 0xFF);
      if (i + 1 >= paramInt4)
        throw TbError.newSQLException(-590744, (i + 1) + " >= " + paramInt4); 
      paramArrayOfbyte[i++] = b1;
      paramArrayOfbyte[i++] = b2;
    } 
    return i - paramInt3;
  }
  
  public Object toStruct(Object paramObject, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, TbStructDescriptor paramTbStructDescriptor, Class<?> paramClass, Map<String, Class<?>> paramMap) throws SQLException {
    int i;
    boolean bool;
    if (paramInt2 <= 0 || paramObject == null)
      return null; 
    if (paramInt3 != 32 && paramInt3 != 28)
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3)); 
    if (paramTbStructDescriptor == null)
      throw TbError.newSQLException(-90612, "obj_desc null"); 
    if (!(paramObject instanceof byte[]))
      throw TbError.newSQLException(-90612, "inst=" + paramObject.toString()); 
    byte[] arrayOfByte = (byte[])paramObject;
    if (arrayOfByte.length > paramInt1 + 3 && arrayOfByte[paramInt1] == -124) {
      i = paramInt1;
    } else if (arrayOfByte.length > paramInt1 + 5 && paramInt2 > 5) {
      byte[] arrayOfByte1 = new byte[paramInt2];
      System.arraycopy(arrayOfByte, paramInt1, arrayOfByte1, 0, paramInt2);
      TbBlob tbBlob = new TbBlob(this.conn, arrayOfByte1, true);
      long l = tbBlob.length();
      if (l > 2147483647L)
        throw TbError.newSQLException(-90612, "rp(lob) len=" + l); 
      arrayOfByte = tbBlob.getBytes(1L, (int)l);
      byte b = (arrayOfByte == null) ? 0 : arrayOfByte.length;
      if (b < 3)
        throw TbError.newSQLException(-90612, "udt_lob_len=" + b); 
      if (arrayOfByte[0] != -124)
        throw TbError.newSQLException(-90612, "udt_rpflag=0x" + Integer.toHexString(0xFF & arrayOfByte[0])); 
      i = 0;
    } else {
      throw TbError.newSQLException(-90612, paramObject.toString());
    } 
    if (arrayOfByte[i + 1] == 1) {
      bool = true;
    } else if (arrayOfByte[i + 1] == 2) {
      bool = false;
    } else {
      throw TbError.newSQLException(-90612, "udt_inherit=0x" + Integer.toHexString(0xFF & arrayOfByte[i + 1]));
    } 
    i += 2;
    TbStructDescriptor tbStructDescriptor = paramTbStructDescriptor;
    if (arrayOfByte[i] == RPCOL_UDT_NOT_FINAL[0] && arrayOfByte.length >= i + 4 && arrayOfByte[i + 1] == RPCOL_UDT_NOT_FINAL[1] && arrayOfByte[i + 2] == RPCOL_UDT_NOT_FINAL[2] && arrayOfByte[i + 3] == RPCOL_UDT_NOT_FINAL[3])
      i += RPCOL_UDT_NOT_FINAL.length; 
    if (this.conn.serverInfo.getProtocolMajorVersion() >= 2 && this.conn.serverInfo.getProtocolMinorVersion() >= 16) {
      RPCOL_NULLOBJ = -5;
      RPCOL_5BYTE = -3;
    } else {
      RPCOL_NULLOBJ = -3;
      RPCOL_5BYTE = -5;
    } 
    int j = arrayOfByte[i] & 0xFF;
    if (j <= 250) {
      i++;
    } else if (j == 254) {
      j = TbCommon.bytes2Int(arrayOfByte, i + 1, 2);
      i += 3;
    } else if ((byte)j == RPCOL_5BYTE) {
      j = TbCommon.bytes2Int(arrayOfByte, i + 1, 4);
      i += 5;
    } else {
      throw TbError.newSQLException(-90612, "udt_rplen=0x" + Integer.toHexString(j));
    } 
    if (!bool) {
      int k = 0xFF & arrayOfByte[i];
      byte[] arrayOfByte1 = toBytes(arrayOfByte, i + 1, k, 4, paramBoolean);
      i += 1 + k;
      char[] arrayOfChar = "0123456789ABCDEF".toCharArray();
      StringBuilder stringBuilder = new StringBuilder(k * 2);
      for (byte b = 0; b < k; b++) {
        stringBuilder.append(arrayOfChar[arrayOfByte1[b] >> 4 & 0xF]);
        stringBuilder.append(arrayOfChar[arrayOfByte1[b] & 0xF]);
      } 
      String str = stringBuilder.toString();
      try {
        tbStructDescriptor = TbStructDescriptor.createDescriptor(paramInt3, str, (Connection)this.conn);
      } catch (SQLException sQLException) {
        throw TbError.newSQLException(-90612, sQLException);
      } 
    } 
    LinkedList<ObjInstBldCtx> linkedList = new LinkedList();
    ObjInstBldCtx objInstBldCtx = new ObjInstBldCtx(tbStructDescriptor);
    while (true) {
      byte b = 0;
      int k = 0;
      int m = 0;
      switch (arrayOfByte[i]) {
        case -4:
          k = 0xFF & arrayOfByte[i + 1];
          i += 2;
          break;
        case 0:
          k = Integer.MAX_VALUE;
          i++;
          break;
        default:
          if (arrayOfByte[i] == RPCOL_NULLOBJ) {
            i++;
            break;
          } 
          m = arrayOfByte[i] & 0xFF;
          if (m <= 250) {
            i++;
            break;
          } 
          if (m == 254) {
            m = TbCommon.bytes2Int(arrayOfByte, i + 1, 2);
            i += 3;
            break;
          } 
          if ((byte)m == RPCOL_5BYTE) {
            m = TbCommon.bytes2Int(arrayOfByte, i + 1, 4);
            i += 5;
            break;
          } 
          throw TbError.newSQLException(-90612, "subRpLen=" + m);
      } 
      while (objInstBldCtx.desc.getAttributeTypes()[objInstBldCtx.attrProcessed] == 32 && (m != 0 || b < k)) {
        String str3 = objInstBldCtx.desc.getAttributeTypeNames()[objInstBldCtx.attrProcessed];
        String str4 = objInstBldCtx.desc.getAttributeOIDs()[objInstBldCtx.attrProcessed];
        for (ObjInstBldCtx objInstBldCtx1 : linkedList) {
          if (objInstBldCtx1.attrProcessed > 0)
            break; 
          if ((str3 != null && str3.equalsIgnoreCase(objInstBldCtx1.desc.getSQLTypeName())) || (str4 != null && str4.equalsIgnoreCase(objInstBldCtx1.desc.getOID())))
            throw TbError.newSQLException(-90612, arrayOfByte.toString()); 
        } 
        Object object1 = null;
        if (str4 != null) {
          object1 = this.conn.getDescriptor("/O" + str4);
          if (object1 == null)
            object1 = TbStructDescriptor.createDescriptor(32, str4, (Connection)this.conn); 
        } 
        if (object1 == null && str3 != null) {
          object1 = this.conn.getDescriptor(str3);
          if (object1 == null)
            object1 = TbStructDescriptor.createDescriptor(str3, (Connection)this.conn); 
        } 
        if (object1 == null)
          throw TbError.newSQLException(-90665, "parent=" + objInstBldCtx.desc.getSQLTypeName() + ", attrIdx=" + objInstBldCtx.attrProcessed + ", attr=" + str3 + ", attrOID=" + str4); 
        if (object1 instanceof TbStructDescriptor && ((TbStructDescriptor)object1).isFinal()) {
          TbStructDescriptor tbStructDescriptor1 = (TbStructDescriptor)object1;
          ObjInstBldCtx objInstBldCtx1 = new ObjInstBldCtx(tbStructDescriptor1);
          linkedList.addFirst(objInstBldCtx);
          objInstBldCtx = objInstBldCtx1;
          b++;
        } 
      } 
      int n = objInstBldCtx.desc.getAttributeTypes()[objInstBldCtx.attrProcessed];
      int i1 = DataType.getSqlType(n);
      String str1 = objInstBldCtx.desc.getAttributeTypeNames()[objInstBldCtx.attrProcessed];
      String str2 = objInstBldCtx.desc.getAttributeOIDs()[objInstBldCtx.attrProcessed];
      Object object = null;
      switch (n) {
        case 29:
        case 30:
        case 31:
          if (str2 != null && (object = this.conn.getDescriptor(str2)) == null)
            object = TbArrayDescriptor.createDescriptor(n, str2, (Connection)this.conn); 
          if (object == null && str1 != null && (object = this.conn.getDescriptor(str1)) == null)
            object = TbArrayDescriptor.createDescriptor(str1, (Connection)this.conn); 
          if (object == null)
            throw TbError.newSQLException(-90665, "parent=" + objInstBldCtx.desc.getSQLTypeName() + ", attrIdx=" + objInstBldCtx.attrProcessed + ", attr=" + str1 + ", attrOID=" + str2); 
          break;
        case 28:
        case 32:
          if (str2 != null && (object = this.conn.getDescriptor(str2)) == null)
            object = TbStructDescriptor.createDescriptor(n, str2, (Connection)this.conn); 
          if (object == null && str1 != null && (object = this.conn.getDescriptor(str1)) == null)
            object = TbStructDescriptor.createDescriptor(str1, (Connection)this.conn); 
          if (object == null)
            throw TbError.newSQLException(-90665, "parent=" + objInstBldCtx.desc.getSQLTypeName() + ", attrIdx=" + objInstBldCtx.attrProcessed + ", attr=" + str1 + ", attrOID=" + str2); 
          break;
      } 
      if (object instanceof TbArrayDescriptor) {
        TbArrayDescriptor tbArrayDescriptor = (TbArrayDescriptor)object;
        objInstBldCtx.values[objInstBldCtx.attrProcessed] = toArray(arrayOfByte, i, m, n, paramBoolean, tbArrayDescriptor, paramMap);
      } else if (object instanceof TbStructDescriptor) {
        TbStructDescriptor tbStructDescriptor1 = (TbStructDescriptor)object;
        objInstBldCtx.values[objInstBldCtx.attrProcessed] = toStruct(arrayOfByte, i, m, n, paramBoolean, tbStructDescriptor1, null, paramMap);
      } else {
        if (objInstBldCtx.desc.getAttributeTypes()[objInstBldCtx.attrProcessed] == 1 && m > 0) {
          i--;
          m++;
        } 
        objInstBldCtx.values[objInstBldCtx.attrProcessed] = toObject(arrayOfByte, i, m, n, i1, paramBoolean);
      } 
      objInstBldCtx.attrProcessed++;
      i += m;
      b = 0;
      while (objInstBldCtx.values.length <= objInstBldCtx.attrProcessed) {
        TbStruct tbStruct = new TbStruct(objInstBldCtx.desc, (Connection)this.conn, objInstBldCtx.values);
        Object object1 = null;
        boolean bool1 = linkedList.isEmpty();
        if (tbStruct != null)
          if (bool1 && paramClass != null) {
            object1 = tbStruct.toClass(paramClass, paramMap);
          } else {
            object1 = tbStruct.toClass(paramMap);
          }  
        if (bool1)
          return object1; 
        ObjInstBldCtx objInstBldCtx1 = linkedList.removeFirst();
        objInstBldCtx1.values[objInstBldCtx1.attrProcessed++] = object1;
        objInstBldCtx = objInstBldCtx1;
      } 
    } 
  }
  
  public Array toArray(Object paramObject, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, TbArrayDescriptor paramTbArrayDescriptor, Map<String, Class<?>> paramMap) throws SQLException {
    int i;
    if (paramInt2 <= 0 || paramObject == null)
      return null; 
    if (!(paramObject instanceof byte[]))
      throw TbError.newSQLException(-90612, "inst=" + paramObject.toString()); 
    if (paramInt3 != 29 && paramInt3 != 30 && paramInt3 != 31)
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3)); 
    if (paramTbArrayDescriptor == null)
      throw TbError.newSQLException(-90612, "arr_desc=null"); 
    byte[] arrayOfByte = (byte[])paramObject;
    if (arrayOfByte.length > paramInt1 + 3 && arrayOfByte[paramInt1] == -120 && arrayOfByte[paramInt1 + 1] == 1) {
      i = paramInt1 + 2;
    } else if (arrayOfByte.length > paramInt1 + 5) {
      byte[] arrayOfByte1 = new byte[paramInt2];
      System.arraycopy(arrayOfByte, paramInt1, arrayOfByte1, 0, paramInt2);
      TbBlob tbBlob = new TbBlob(this.conn, arrayOfByte1, true);
      long l = tbBlob.length();
      if (l > 2147483647L)
        throw TbError.newSQLException(-90612, "rp(lob) len=" + l); 
      arrayOfByte = tbBlob.getBytes(1L, (int)l);
      byte b1 = (arrayOfByte == null) ? 0 : arrayOfByte.length;
      if (b1 < 3)
        throw TbError.newSQLException(-90612, "udt_lob_len=" + b1); 
      if (arrayOfByte[0] != -120 || arrayOfByte[1] != 1)
        throw TbError.newSQLException(-90612, "udt_rpflag=0x" + Integer.toHexString(0xFF & arrayOfByte[0]) + ",0x" + Integer.toHexString(0xFF & arrayOfByte[1])); 
      i = 2;
    } else {
      throw TbError.newSQLException(-90612, paramObject.toString());
    } 
    int j = paramTbArrayDescriptor.getElementType();
    int k = paramTbArrayDescriptor.getBaseType();
    if (this.conn.serverInfo.getProtocolMajorVersion() >= 2 && this.conn.serverInfo.getProtocolMinorVersion() >= 16) {
      RPCOL_NULLOBJ = -5;
      RPCOL_5BYTE = -3;
    } else {
      RPCOL_NULLOBJ = -3;
      RPCOL_5BYTE = -5;
    } 
    int m = arrayOfByte[i] & 0xFF;
    if (m <= 250) {
      i++;
    } else if (m == 254) {
      m = TbCommon.bytes2Int(arrayOfByte, i + 1, 2);
      i += 3;
    } else if ((byte)m == RPCOL_5BYTE) {
      m = TbCommon.bytes2Int(arrayOfByte, i + 1, 4);
      i += 5;
    } else {
      throw TbError.newSQLException(-90612, "rplen=" + m);
    } 
    if (arrayOfByte[i] != 0 || arrayOfByte[i + 1] != 0 || arrayOfByte[i + 2] != 0)
      throw TbError.newSQLException(-90612, "illegal_input"); 
    i += 3;
    int n = arrayOfByte[i] & 0xFF;
    if (n <= 250) {
      i++;
    } else if (n == 254) {
      n = TbCommon.bytes2Int(arrayOfByte, i + 1, 2);
      i += 3;
    } else if ((byte)n == RPCOL_5BYTE) {
      n = TbCommon.bytes2Int(arrayOfByte, i + 1, 4);
      i += 5;
    } else {
      throw TbError.newSQLException(-90612, "arrayElemCnt=" + n);
    } 
    Object[] arrayOfObject = new Object[n];
    for (byte b = 0; b < n; b++) {
      Object object1;
      Object object2;
      int i1 = arrayOfByte[i] & 0xFF;
      if (i1 <= 250) {
        i++;
      } else if (i1 == 254) {
        i1 = TbCommon.bytes2Int(arrayOfByte, i + 1, 2);
        i += 3;
      } else if ((byte)i1 == RPCOL_5BYTE) {
        i1 = TbCommon.bytes2Int(arrayOfByte, i + 1, 4);
        i += 5;
      } else {
        throw TbError.newSQLException(-90612, "subRpLen=" + i1);
      } 
      switch (j) {
        case 29:
        case 30:
        case 31:
          object1 = this.conn.getDescriptor(paramTbArrayDescriptor.getElementTypeName());
          if (object1 == null)
            object1 = TbArrayDescriptor.createDescriptor(j, paramTbArrayDescriptor.getElementTypeName(), (Connection)this.conn); 
          if (object1 instanceof TbArrayDescriptor) {
            TbArrayDescriptor tbArrayDescriptor = (TbArrayDescriptor)object1;
            arrayOfObject[b] = toArray(arrayOfByte, i, i1, j, paramBoolean, tbArrayDescriptor, paramMap);
            break;
          } 
          TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
          break;
        case 28:
        case 32:
          object2 = this.conn.getDescriptor(paramTbArrayDescriptor.getElementTypeName());
          if (object2 == null)
            object2 = TbStructDescriptor.createDescriptor(j, paramTbArrayDescriptor.getElementTypeName(), (Connection)this.conn); 
          if (object2 instanceof TbStructDescriptor) {
            TbStructDescriptor tbStructDescriptor = (TbStructDescriptor)object2;
            arrayOfObject[b] = toStruct(arrayOfByte, i, i1, j, paramBoolean, tbStructDescriptor, null, paramMap);
            break;
          } 
          TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
          break;
        case 1:
          if (i1 == 0) {
            arrayOfObject[b] = null;
            break;
          } 
          arrayOfObject[b] = toObject(arrayOfByte, i - 1, i1 + 1, j, k, paramBoolean);
          break;
        default:
          arrayOfObject[b] = toObject(arrayOfByte, i, i1, j, k, paramBoolean);
          break;
      } 
      i += i1;
    } 
    return (Array)new TbArray(paramTbArrayDescriptor, (Connection)this.conn, arrayOfObject);
  }
  
  public Ref toRef(Object paramObject, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return null; 
    if (paramObject instanceof byte[]) {
      byte[] arrayOfByte = (byte[])paramObject;
      switch (paramInt3) {
        case 33:
          return (Ref)new TbRef(arrayOfByte, paramInt1, paramInt2, this.conn);
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public InputStream toAsciiStream(Object paramObject, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    if (paramInt2 == 0 || paramObject == null)
      return null; 
    if (paramObject instanceof byte[]) {
      byte[] arrayOfByte2;
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = (byte[])paramObject;
      switch (paramInt3) {
        case 2:
        case 3:
          return new ByteArrayInputStream(arrayOfByte1, paramInt1, paramInt2);
        case 10:
          arrayOfByte2 = new byte[paramInt2];
          System.arraycopy(arrayOfByte1, paramInt1, arrayOfByte2, 0, paramInt2);
          synchronized (this.conn) {
            arrayOfByte3 = this.conn.getTbComm().readLongRaw(arrayOfByte2);
          } 
          return new ByteArrayInputStream(arrayOfByte3, 0, arrayOfByte3.length);
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public BigDecimal toBigDecimal(Object paramObject, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    if (paramInt2 == 0 || paramObject == null)
      return null; 
    if (paramObject instanceof byte[]) {
      Double double_;
      Float float_;
      byte[] arrayOfByte = (byte[])paramObject;
      switch (paramInt3) {
        case 2:
        case 3:
          try {
            String str = getDBDecodedString(arrayOfByte, paramInt1, paramInt2);
            return new BigDecimal(str.trim());
          } catch (NumberFormatException numberFormatException) {
            return null;
          } 
        case 1:
          return TbNumber.toBigDecimal(arrayOfByte, paramInt1, paramInt2);
        case 24:
          double_ = new Double(TbNumber.toBinaryDouble(arrayOfByte, paramInt1, paramInt2));
          return new BigDecimal(double_.toString());
        case 23:
          float_ = new Float(TbNumber.toBinaryFloat(arrayOfByte, paramInt1, paramInt2));
          return new BigDecimal(float_.toString());
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public InputStream toBinaryStream(Object paramObject, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    if (paramInt2 == 0 || paramObject == null)
      return null; 
    if (paramObject instanceof InputStream)
      try {
        ((InputStream)paramObject).reset();
        return (InputStream)paramObject;
      } catch (IOException iOException) {
        throw TbError.newSQLException(-90616, iOException);
      }  
    if (paramObject instanceof Blob)
      return ((TbBlob)paramObject).getBinaryStream(); 
    if (paramObject instanceof byte[]) {
      byte[] arrayOfByte2;
      byte[] arrayOfByte3;
      byte[] arrayOfByte4;
      byte[] arrayOfByte1 = (byte[])paramObject;
      switch (paramInt3) {
        case 2:
        case 3:
          return new ByteArrayInputStream(arrayOfByte1, paramInt1, paramInt2);
        case 11:
          arrayOfByte2 = new byte[paramInt2];
          System.arraycopy(arrayOfByte1, paramInt1, arrayOfByte2, 0, paramInt2);
          synchronized (this.conn) {
            arrayOfByte3 = this.conn.getTbComm().readLongRaw(arrayOfByte2);
          } 
          return new ByteArrayInputStream(arrayOfByte3, 0, arrayOfByte3.length);
        case 12:
          arrayOfByte4 = new byte[paramInt2];
          System.arraycopy(arrayOfByte1, paramInt1, arrayOfByte4, 0, paramInt2);
          return (new TbBlob(this.conn, arrayOfByte4, false)).getBinaryStream();
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public Blob toBlob(Object paramObject, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) throws SQLException {
    if (paramInt2 == 0 || paramObject == null)
      return null; 
    if (paramObject instanceof Blob)
      return (Blob)paramObject; 
    if (paramObject instanceof byte[]) {
      byte[] arrayOfByte2;
      byte[] arrayOfByte1 = (byte[])paramObject;
      switch (paramInt3) {
        case 12:
          arrayOfByte2 = new byte[paramInt2];
          System.arraycopy(arrayOfByte1, paramInt1, arrayOfByte2, 0, paramInt2);
          return (Blob)new TbBlob(this.conn, arrayOfByte2, paramBoolean);
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public boolean toBoolean(Object paramObject, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    if (paramInt2 == 0 || paramObject == null)
      return false; 
    if (paramObject instanceof byte[]) {
      String str1;
      String str2;
      byte[] arrayOfByte = (byte[])paramObject;
      switch (paramInt3) {
        case 2:
        case 3:
          str1 = getDBDecodedString(arrayOfByte, paramInt1, paramInt2).trim();
          return (str1 != null && (str1.equals("t") || str1.equals("1") || str1.equalsIgnoreCase("true")));
        case 18:
        case 19:
          str2 = getDBDecodedNString(arrayOfByte, paramInt1, paramInt2).trim();
          return (str2 != null && (str2.equals("t") || str2.equals("1") || str2.equalsIgnoreCase("true")));
        case 1:
          return (TbNumber.toInteger(arrayOfByte, paramInt1, paramInt2) != 0);
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public byte toByte(Object paramObject, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    int i = toIntInternal(paramObject, paramInt1, paramInt2, paramInt3);
    if (i > 127 || i < -128)
      throw TbError.newSQLException(-590749, i); 
    return (byte)i;
  }
  
  public byte[] toBytes(Object paramObject, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) throws SQLException {
    if (paramInt2 == 0 || paramObject == null)
      return null; 
    if (paramObject instanceof byte[]) {
      byte[] arrayOfByte2;
      byte[] arrayOfByte3;
      TbBlob tbBlob;
      byte[] arrayOfByte4;
      byte[] arrayOfByte1 = (byte[])paramObject;
      switch (paramInt3) {
        case 10:
        case 11:
          arrayOfByte2 = new byte[paramInt2];
          System.arraycopy(arrayOfByte1, paramInt1, arrayOfByte2, 0, paramInt2);
          synchronized (this.conn) {
            return this.conn.getTbComm().readLongRaw(arrayOfByte2);
          } 
        case 12:
          arrayOfByte3 = new byte[paramInt2];
          System.arraycopy(arrayOfByte1, paramInt1, arrayOfByte3, 0, paramInt2);
          tbBlob = new TbBlob(this.conn, arrayOfByte3, paramBoolean);
          arrayOfByte4 = tbBlob.getBytes(1L, (int)tbBlob.length());
          if (tbBlob.getIsTempLob() && paramBoolean)
            tbBlob.free(); 
          return arrayOfByte4;
        case 13:
        case 20:
          throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
      } 
      byte[] arrayOfByte5 = new byte[paramInt2];
      System.arraycopy(arrayOfByte1, paramInt1, arrayOfByte5, 0, paramInt2);
      return arrayOfByte5;
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public int toBytes(Object paramObject, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfbyte) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return 0; 
    if (paramObject instanceof byte[]) {
      switch (paramInt3) {
        case 10:
        case 11:
        case 12:
        case 13:
        case 20:
          throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
      } 
      if (paramInt2 > paramArrayOfbyte.length)
        throw TbError.newSQLException(-90650); 
      System.arraycopy(paramObject, paramInt1, paramArrayOfbyte, 0, paramInt2);
      return paramInt2;
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public Reader toCharacterStream(Object paramObject, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return null; 
    if (paramObject instanceof Reader)
      try {
        ((Reader)paramObject).reset();
        return (Reader)paramObject;
      } catch (IOException iOException) {
        throw TbError.newSQLException(-90616, iOException);
      }  
    if (paramObject instanceof Clob)
      return ((TbClob)paramObject).getCharacterStream(); 
    if (paramObject instanceof byte[]) {
      byte[] arrayOfByte2;
      String str;
      byte[] arrayOfByte3;
      byte[] arrayOfByte4;
      byte[] arrayOfByte1 = (byte[])paramObject;
      switch (paramInt3) {
        case 2:
        case 3:
          return new StringReader(getDBDecodedString(arrayOfByte1, paramInt1, paramInt2));
        case 18:
        case 19:
          return new StringReader(getDBDecodedNString(arrayOfByte1, paramInt1, paramInt2));
        case 10:
          arrayOfByte2 = new byte[paramInt2];
          System.arraycopy(arrayOfByte1, paramInt1, arrayOfByte2, 0, paramInt2);
          synchronized (this.conn) {
            str = this.conn.getTbComm().readLong(arrayOfByte2);
          } 
          return new StringReader(str);
        case 13:
          arrayOfByte3 = new byte[paramInt2];
          System.arraycopy(arrayOfByte1, paramInt1, arrayOfByte3, 0, paramInt2);
          return (new TbClob(this.conn, arrayOfByte3, false)).getCharacterStream();
        case 20:
          arrayOfByte4 = new byte[paramInt2];
          System.arraycopy(arrayOfByte1, paramInt1, arrayOfByte4, 0, paramInt2);
          return (new TbNClob(this.conn, arrayOfByte4, false)).getCharacterStream();
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public Clob toClob(Object paramObject, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return null; 
    if (paramObject instanceof Clob)
      return (Clob)paramObject; 
    if (paramObject instanceof byte[]) {
      byte[] arrayOfByte2;
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = (byte[])paramObject;
      switch (paramInt3) {
        case 13:
          arrayOfByte2 = new byte[paramInt2];
          System.arraycopy(arrayOfByte1, paramInt1, arrayOfByte2, 0, paramInt2);
          return (Clob)new TbClob(this.conn, arrayOfByte2, paramBoolean);
        case 20:
          arrayOfByte3 = new byte[paramInt2];
          System.arraycopy(arrayOfByte1, paramInt1, arrayOfByte3, 0, paramInt2);
          return (Clob)new TbNClob(this.conn, arrayOfByte3, paramBoolean);
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public Date toDate(Object paramObject, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return null; 
    if (paramObject instanceof byte[]) {
      Date date;
      String str;
      byte[] arrayOfByte = (byte[])paramObject;
      switch (paramInt3) {
        case 2:
        case 3:
          str = getDBDecodedString(arrayOfByte, paramInt1, paramInt2);
          date = Date.valueOf(str.trim());
          if (this.conn.getNlsCalendar().equals("THAI BUDDHA")) {
            long l = date.getTime();
            Date date1 = new Date(l);
            int i = date1.getYear();
            date1.setYear(i + 543);
            date = new Date(date1.getTime());
          } 
          return date;
        case 5:
          if (paramInt2 != 8)
            throw TbError.newSQLException(-590750, paramInt2); 
          date = toDateFromBytes(arrayOfByte, paramInt1);
          if (this.conn.getNlsCalendar().equals("THAI BUDDHA")) {
            long l = date.getTime();
            Date date1 = new Date(l);
            int i = date1.getYear();
            date1.setYear(i + 543);
            date = new Date(date1.getTime());
          } 
          return date;
        case 7:
          if (paramInt2 != 12)
            throw TbError.newSQLException(-590751, paramInt2); 
          date = toDateFromBytes(arrayOfByte, paramInt1);
          if (this.conn.getNlsCalendar().equals("THAI BUDDHA")) {
            long l = date.getTime();
            Date date1 = new Date(l);
            int i = date1.getYear();
            date1.setYear(i + 543);
            date = new Date(date1.getTime());
          } 
          return date;
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  private Date toDateFromBytes(byte[] paramArrayOfbyte, int paramInt) {
    int i = 0xFF & paramArrayOfbyte[paramInt];
    int j = 0xFF & paramArrayOfbyte[paramInt + 1];
    int k = 0xFF & paramArrayOfbyte[paramInt + 2];
    int m = 0xFF & paramArrayOfbyte[paramInt + 3];
    int n = 0xFF & paramArrayOfbyte[paramInt + 4];
    int i1 = 0xFF & paramArrayOfbyte[paramInt + 5];
    int i2 = 0xFF & paramArrayOfbyte[paramInt + 6];
    int i3 = (i - 100) * 100 + j - 100;
    int i4 = k - 1;
    Calendar calendar = Calendar.getInstance();
    calendar.set(i3, i4, m, n, i1, i2);
    calendar.clear(14);
    return new Date(calendar.getTimeInMillis());
  }
  
  public TbDate toTbDate(Object paramObject, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return null; 
    if (paramObject instanceof byte[]) {
      byte[] arrayOfByte2;
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = (byte[])paramObject;
      switch (paramInt3) {
        case 5:
          if (paramInt2 != 8)
            throw TbError.newSQLException(-590750, paramInt2); 
          arrayOfByte2 = new byte[8];
          System.arraycopy(arrayOfByte1, paramInt1, arrayOfByte2, 0, 8);
          return new TbDate(arrayOfByte2);
        case 7:
          if (paramInt2 != 12)
            throw TbError.newSQLException(-590751, paramInt2); 
          arrayOfByte3 = new byte[8];
          System.arraycopy(arrayOfByte1, paramInt1, arrayOfByte3, 0, 8);
          return new TbDate(arrayOfByte3);
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public double toDouble(Object paramObject, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return 0.0D; 
    if (paramObject instanceof byte[]) {
      byte[] arrayOfByte = (byte[])paramObject;
      switch (paramInt3) {
        case 2:
        case 3:
          try {
            String str = getDBDecodedString(arrayOfByte, paramInt1, paramInt2);
            return Double.parseDouble(str.trim());
          } catch (NumberFormatException numberFormatException) {
            return 0.0D;
          } 
        case 18:
        case 19:
          try {
            String str = getDBDecodedNString(arrayOfByte, paramInt1, paramInt2);
            return Double.parseDouble(str.trim());
          } catch (NumberFormatException numberFormatException) {
            return 0.0D;
          } 
        case 1:
          return TbNumber.toDouble(arrayOfByte, paramInt1, paramInt2);
        case 24:
          return TbNumber.toBinaryDouble(arrayOfByte, paramInt1, paramInt2);
        case 23:
          return TbNumber.toBinaryFloat(arrayOfByte, paramInt1, paramInt2);
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public float toFloat(Object paramObject, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return 0.0F; 
    if (paramObject instanceof byte[]) {
      byte[] arrayOfByte = (byte[])paramObject;
      switch (paramInt3) {
        case 2:
        case 3:
          try {
            String str = getDBDecodedString(arrayOfByte, paramInt1, paramInt2);
            return Float.parseFloat(str.trim());
          } catch (NumberFormatException numberFormatException) {
            return 0.0F;
          } 
        case 18:
        case 19:
          try {
            String str = getDBDecodedNString(arrayOfByte, paramInt1, paramInt2);
            return Float.parseFloat(str.trim());
          } catch (NumberFormatException numberFormatException) {
            return 0.0F;
          } 
        case 1:
          return (float)TbNumber.toDouble(arrayOfByte, paramInt1, paramInt2);
        case 24:
          return (float)TbNumber.toBinaryDouble(arrayOfByte, paramInt1, paramInt2);
        case 23:
          return TbNumber.toBinaryFloat(arrayOfByte, paramInt1, paramInt2);
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public int toInt(Object paramObject, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    return toIntInternal(paramObject, paramInt1, paramInt2, paramInt3);
  }
  
  public TbIntervalDts toIntervalDts(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return null; 
    if (paramObject instanceof byte[]) {
      byte[] arrayOfByte;
      switch (paramInt3) {
        case 9:
          arrayOfByte = new byte[paramInt2];
          System.arraycopy(paramObject, paramInt1, arrayOfByte, 0, paramInt2);
          return new TbIntervalDts(arrayOfByte, paramInt4, paramInt5);
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public TbIntervalYtm toIntervalYtm(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return null; 
    if (paramObject instanceof byte[]) {
      byte[] arrayOfByte;
      switch (paramInt3) {
        case 8:
          arrayOfByte = new byte[paramInt2];
          System.arraycopy(paramObject, paramInt1, arrayOfByte, 0, paramInt2);
          return new TbIntervalYtm(arrayOfByte, paramInt4);
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public int toIntInternal(Object paramObject, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return 0; 
    if (paramObject instanceof byte[]) {
      byte[] arrayOfByte = (byte[])paramObject;
      switch (paramInt3) {
        case 2:
        case 3:
          try {
            String str = getDBDecodedString(arrayOfByte, paramInt1, paramInt2);
            return Integer.parseInt(str.trim());
          } catch (NumberFormatException numberFormatException) {
            return TbCommon.bytes2Int(arrayOfByte, paramInt1, paramInt2);
          } 
        case 18:
        case 19:
          try {
            String str = getDBDecodedString(arrayOfByte, paramInt1, paramInt2);
            return Integer.parseInt(str.trim());
          } catch (NumberFormatException numberFormatException) {
            return TbCommon.bytes2Int(arrayOfByte, paramInt1, paramInt2);
          } 
        case 1:
          return TbNumber.toInteger(arrayOfByte, paramInt1, paramInt2);
        case 24:
          return (int)TbNumber.toBinaryDouble(arrayOfByte, paramInt1, paramInt2);
        case 23:
          return (int)TbNumber.toBinaryFloat(arrayOfByte, paramInt1, paramInt2);
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public long toLong(Object paramObject, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return 0L; 
    if (paramObject instanceof byte[]) {
      byte[] arrayOfByte = (byte[])paramObject;
      switch (paramInt3) {
        case 2:
        case 3:
          try {
            String str = getDBDecodedString(arrayOfByte, paramInt1, paramInt2);
            return Long.parseLong(str.trim());
          } catch (NumberFormatException numberFormatException) {
            return TbCommon.bytes2Long(arrayOfByte, paramInt1, paramInt2);
          } 
        case 18:
        case 19:
          try {
            String str = getDBDecodedNString(arrayOfByte, paramInt1, paramInt2);
            return Long.parseLong(str.trim());
          } catch (NumberFormatException numberFormatException) {
            return TbCommon.bytes2Long(arrayOfByte, paramInt1, paramInt2);
          } 
        case 1:
          return TbNumber.toLong(arrayOfByte, paramInt1, paramInt2);
        case 24:
          return (long)TbNumber.toBinaryDouble(arrayOfByte, paramInt1, paramInt2);
        case 23:
          return (long)TbNumber.toBinaryFloat(arrayOfByte, paramInt1, paramInt2);
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  private byte[] toLongRawBytes(Object paramObject, int paramInt1, int paramInt2) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return null; 
    if (paramObject instanceof byte[]) {
      byte[] arrayOfByte = new byte[paramInt2];
      System.arraycopy(paramObject, paramInt1, arrayOfByte, 0, paramInt2);
      synchronized (this.conn) {
        return this.conn.getTbComm().readLongRaw(arrayOfByte);
      } 
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public InputStream toLongRawStream(Object paramObject, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return null; 
    if (paramObject instanceof byte[]) {
      byte[] arrayOfByte2;
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = (byte[])paramObject;
      switch (paramInt3) {
        case 10:
        case 11:
          arrayOfByte2 = new byte[paramInt2];
          System.arraycopy(arrayOfByte1, paramInt1, arrayOfByte2, 0, paramInt2);
          synchronized (this.conn) {
            arrayOfByte3 = this.conn.getTbComm().readLongRaw(arrayOfByte2);
          } 
          return new ByteArrayInputStream(arrayOfByte3, 0, arrayOfByte3.length);
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  private String toLongString(Object paramObject, int paramInt1, int paramInt2) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return null; 
    if (paramObject instanceof byte[]) {
      byte[] arrayOfByte = new byte[paramInt2];
      System.arraycopy(paramObject, paramInt1, arrayOfByte, 0, paramInt2);
      synchronized (this.conn) {
        return this.conn.getTbComm().readLong(arrayOfByte);
      } 
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public NClob toNClob(Object paramObject, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    return (NClob)toTbNClob(paramObject, paramInt1, paramInt2, paramInt3);
  }
  
  public TbNClob toTbNClob(Object paramObject, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return null; 
    if (paramObject instanceof TbNClob || paramObject instanceof NClob)
      return (TbNClob)paramObject; 
    if (paramObject instanceof byte[]) {
      byte[] arrayOfByte2;
      byte[] arrayOfByte1 = (byte[])paramObject;
      switch (paramInt3) {
        case 20:
          arrayOfByte2 = new byte[paramInt2];
          System.arraycopy(arrayOfByte1, paramInt1, arrayOfByte2, 0, paramInt2);
          return new TbNClob(this.conn, arrayOfByte2, false);
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public TbNrowId toNrowId(Object paramObject, int paramInt1, int paramInt2) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return null; 
    if (paramObject instanceof byte[]) {
      TbNrowId tbNrowId = new TbNrowId(this.conn);
      System.arraycopy(paramObject, paramInt1, tbNrowId.nrowid, 0, 8);
      return tbNrowId;
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public Object toObject(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) throws SQLException {
    return toObject(paramObject, paramInt1, paramInt2, paramInt3, paramInt4, -1, -1, paramBoolean, null, null, null, null);
  }
  
  public Object toObject(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean, TbTypeDescriptor paramTbTypeDescriptor, Class<?> paramClass, Map<String, Class<?>> paramMap, Statement paramStatement) throws SQLException {
    BigDecimal bigDecimal;
    switch (paramInt3) {
      case 2:
      case 3:
      case 18:
      case 19:
        return toString(paramObject, paramInt1, paramInt2, paramInt3, paramBoolean);
      case 10:
        return toLongString(paramObject, paramInt1, paramInt2);
      case 11:
        return toLongRawBytes(paramObject, paramInt1, paramInt2);
      case 1:
        bigDecimal = toBigDecimal(paramObject, paramInt1, paramInt2, paramInt3);
        switch (paramInt4) {
          case -6:
          case 4:
          case 5:
            return new Integer(bigDecimal.intValue());
          case -5:
            return new Long(bigDecimal.longValue());
          case 7:
            return new Float(bigDecimal.floatValue());
          case 6:
          case 8:
            return new Double(bigDecimal.doubleValue());
        } 
        return bigDecimal;
      case 24:
        return new Double(toDouble(paramObject, paramInt1, paramInt2, paramInt3));
      case 23:
        return new Float(toFloat(paramObject, paramInt1, paramInt2, paramInt3));
      case 5:
        switch (paramInt4) {
          case 91:
            return toDate(paramObject, paramInt1, paramInt2, paramInt3);
          case 93:
            return toTimestamp(paramObject, paramInt1, paramInt2, paramInt3);
          case 92:
            return toTime(paramObject, paramInt1, paramInt2, paramInt3);
        } 
        throw TbError.newSQLException(-590703, Integer.toString(paramInt4));
      case 6:
        return toTime(paramObject, paramInt1, paramInt2, paramInt3);
      case 7:
      case 21:
      case 22:
        return toTimestamp(paramObject, paramInt1, paramInt2, paramInt3);
      case 4:
        return toBytes(paramObject, paramInt1, paramInt2, paramInt3, paramBoolean);
      case 15:
        return (this.conn.getServerInfo().getServerIsNanobase() == 1) ? toNrowId(paramObject, paramInt1, paramInt2) : toRowId(paramObject, paramInt1, paramInt2, paramInt3);
      case 12:
        return toBlob(paramObject, paramInt1, paramInt2, paramInt3, paramBoolean);
      case 13:
      case 20:
        return (paramInt4 == 2009) ? toSQLXML(paramObject, paramInt1, paramInt2, paramInt3, paramBoolean) : toClob(paramObject, paramInt1, paramInt2, paramInt3, paramBoolean);
      case 9:
        return toIntervalDts(paramObject, paramInt1, paramInt2, paramInt3, paramInt5, paramInt6);
      case 8:
        return toIntervalYtm(paramObject, paramInt1, paramInt2, paramInt3, paramInt5);
      case 29:
      case 30:
      case 31:
        if (paramTbTypeDescriptor instanceof TbArrayDescriptor)
          return toArray(paramObject, paramInt1, paramInt2, paramInt3, paramBoolean, (TbArrayDescriptor)paramTbTypeDescriptor, paramMap); 
        if (paramInt2 == 0)
          return null; 
        break;
      case 28:
      case 32:
        if (paramTbTypeDescriptor instanceof TbStructDescriptor)
          return (paramTbTypeDescriptor.getOID().compareTo("00000000000000000000000000000001") == 0 && paramClass == null && (paramMap == null || paramMap.get(paramTbTypeDescriptor.getSQLTypeName()) == null)) ? toStruct(paramObject, paramInt1, paramInt2, paramInt3, paramBoolean, (TbStructDescriptor)paramTbTypeDescriptor, TbSQLXML.class, paramMap) : toStruct(paramObject, paramInt1, paramInt2, paramInt3, paramBoolean, (TbStructDescriptor)paramTbTypeDescriptor, paramClass, paramMap); 
        if (paramInt2 == 0)
          return null; 
        break;
      case 16:
        return toResultSet(paramObject, paramInt1, 4, (TbStatement)paramStatement);
      case 33:
        return toRef(paramObject, paramInt1, paramInt2, paramInt3);
    } 
    throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt3));
  }
  
  static short swap(short paramShort) {
    return (short)(paramShort << 8 | paramShort >> 8 & 0xFF);
  }
  
  static int swap(int paramInt) {
    return swap((short)paramInt) << 16 | swap((short)(paramInt >> 16)) & 0xFFFF;
  }
  
  int fromByteArray(byte[] paramArrayOfbyte) {
    return paramArrayOfbyte[0] << 24 | (paramArrayOfbyte[1] & 0xFF) << 16 | (paramArrayOfbyte[2] & 0xFF) << 8 | paramArrayOfbyte[3] & 0xFF;
  }
  
  public TbResultSet toResultSet(Object paramObject, int paramInt1, int paramInt2, TbStatement paramTbStatement) throws SQLException {
    TbResultSet tbResultSet;
    int i = TbCommon.bytes2Int((byte[])paramObject, paramInt1, paramInt2);
    synchronized (this.conn) {
      tbResultSet = this.conn.getTbComm().describeCSRReply(paramTbStatement, i);
    } 
    return tbResultSet;
  }
  
  public TbResultSet toResultSet(int paramInt1, int paramInt2, int paramInt3, TbColumnDesc[] paramArrayOfTbColumnDesc, TbStatement paramTbStatement, byte[] paramArrayOfbyte) throws SQLException {
    int i = paramInt1 - paramInt2;
    TbResultSet tbResultSet = TbResultSetFactory.buildResultSet(paramTbStatement, paramInt3, i, paramInt2, paramArrayOfbyte);
    buildColumnMetadata(paramArrayOfTbColumnDesc, tbResultSet, paramInt1);
    tbResultSet.setFetchCompleted(0);
    return tbResultSet;
  }
  
  public TbResultSet toResultSet(TbStatement paramTbStatement, byte[] paramArrayOfbyte, BindItem paramBindItem) throws SQLException {
    int i = paramBindItem.getLength();
    int j = TbCommon.bytes2Int(paramArrayOfbyte, 0, i);
    TbColumnDesc[] arrayOfTbColumnDesc = paramBindItem.getColMeta();
    if (arrayOfTbColumnDesc == null)
      throw TbError.newSQLException(-90644); 
    TbResultSet tbResultSet = TbResultSetFactory.buildResultSet(paramTbStatement, j, arrayOfTbColumnDesc.length, 0, null);
    buildColumnMetadata(arrayOfTbColumnDesc, tbResultSet, arrayOfTbColumnDesc.length);
    tbResultSet.setFetchCompleted(0);
    return tbResultSet;
  }
  
  public TbRowId toRowId(Object paramObject, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return null; 
    if (paramObject instanceof byte[]) {
      TbRowId tbRowId;
      switch (paramInt3) {
        case 15:
          tbRowId = new TbRowId();
          tbRowId.fromBytes(this.conn.getServerInfo().getServerEndian(), (byte[])paramObject, paramInt1);
          return tbRowId;
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public short toShort(Object paramObject, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    int i = toIntInternal(paramObject, paramInt1, paramInt2, paramInt3);
    if (i > 32767 || i < -32768)
      throw TbError.newSQLException(-590749, i); 
    return (short)i;
  }
  
  public SQLXML toSQLXML(Object paramObject, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return null; 
    if (paramObject instanceof byte[]) {
      byte[] arrayOfByte;
      TbClob tbClob;
      TbXMLInputStream tbXMLInputStream;
      Object object1;
      Object object2;
      Object object3;
      switch (paramInt3) {
        case 13:
          arrayOfByte = new byte[paramInt2];
          System.arraycopy(paramObject, paramInt1, arrayOfByte, 0, paramInt2);
          tbClob = new TbClob(this.conn, arrayOfByte, paramBoolean);
          tbXMLInputStream = new TbXMLInputStream(tbClob);
          return (SQLXML)new TbSQLXML(this.conn, tbXMLInputStream);
        case 28:
        case 32:
          object1 = this.conn.getDescriptor("/O00000000000000000000000000000001");
          object2 = null;
          if (object1 instanceof TbStructDescriptor)
            object2 = toStruct(paramObject, paramInt1, paramInt2, paramInt3, paramBoolean, (TbStructDescriptor)object1, null, null); 
          object3 = null;
          if (object2 instanceof TbStruct && ((TbStruct)object2).getNumOfFields() > 1)
            object3 = ((TbStruct)object2).getAttributes()[0]; 
          if (object3 instanceof TbClob) {
            TbXMLInputStream tbXMLInputStream1 = new TbXMLInputStream((TbClob)object3);
            return (SQLXML)new TbSQLXML(this.conn, tbXMLInputStream1);
          } 
          break;
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public String toString(Object paramObject, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) throws SQLException {
    return toString(paramObject, paramInt1, paramInt2, paramInt3, -1, -1, paramBoolean);
  }
  
  public String toString(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return null; 
    if (paramObject instanceof byte[]) {
      Clob clob;
      String str1;
      TbNClob tbNClob;
      String str2;
      Blob blob;
      byte[] arrayOfByte2;
      byte[] arrayOfByte3;
      TbDate tbDate;
      TbTimestamp tbTimestamp1;
      TimeZone timeZone;
      TbTimestamp tbTimestamp2;
      String str3;
      byte[] arrayOfByte1 = (byte[])paramObject;
      switch (paramInt3) {
        case 2:
        case 3:
          return getDBDecodedString(arrayOfByte1, paramInt1, paramInt2);
        case 18:
        case 19:
          return getDBDecodedNString(arrayOfByte1, paramInt1, paramInt2);
        case 4:
          return TbCommon.bytes2String(arrayOfByte1, paramInt1, paramInt2);
        case 13:
          clob = toClob(paramObject, paramInt1, paramInt2, paramInt3, paramBoolean);
          str1 = clob.getSubString(1L, (int)clob.length());
          if (((TbLob)clob).getIsTempLob() && paramBoolean)
            ((TbLob)clob).free(); 
          return str1;
        case 20:
          tbNClob = (TbNClob)toClob(paramObject, paramInt1, paramInt2, paramInt3, paramBoolean);
          str2 = tbNClob.getSubString(1L, (int)tbNClob.length());
          if (tbNClob.getIsTempLob() && paramBoolean)
            tbNClob.free(); 
          return str2;
        case 12:
          blob = toBlob(paramObject, paramInt1, paramInt2, paramInt3, paramBoolean);
          arrayOfByte2 = blob.getBytes(1L, (int)blob.length());
          if (((TbLob)blob).getIsTempLob() && paramBoolean)
            ((TbLob)blob).free(); 
          return TbCommon.bytes2String(arrayOfByte2, 0, arrayOfByte2.length);
        case 10:
          return toLongString(paramObject, paramInt1, paramInt2);
        case 11:
          arrayOfByte3 = toLongRawBytes(paramObject, paramInt1, paramInt2);
          return TbCommon.bytes2String(arrayOfByte3, 0, arrayOfByte3.length);
        case 1:
          return TbNumber.getNormalForm(arrayOfByte1, paramInt1, paramInt2);
        case 24:
          return Double.toString(toDouble(paramObject, paramInt1, paramInt2, paramInt3));
        case 23:
          return Float.toString(toFloat(paramObject, paramInt1, paramInt2, paramInt3));
        case 5:
          tbDate = toTbDate(paramObject, paramInt1, paramInt2, paramInt3);
          return (tbDate == null) ? null : (this.conn.info.getNlsDatetimeFormatEnabled() ? TbDTFormatter.format(this.conn.getParsedNlsDateFormat(), tbDate) : tbDate.toString());
        case 7:
          tbTimestamp1 = toTbTimestamp(paramObject, paramInt1, paramInt2, paramInt3);
          return (tbTimestamp1 == null) ? null : (this.conn.info.getNlsDatetimeFormatEnabled() ? TbDTFormatter.format(this.conn.getParsedNlsTimestampFormat(), tbTimestamp1) : tbTimestamp1.toString());
        case 21:
          timeZone = toTimeZoneFromBytes(arrayOfByte1, paramInt1, paramInt2);
          tbTimestamp2 = getTbTimestampTZFromBytes(arrayOfByte1, paramInt1, paramInt2, timeZone);
          if (tbTimestamp2 == null)
            return null; 
          str3 = timeZone.getID();
          if (getTimeZoneIdFromBytes(arrayOfByte1, paramInt1, paramInt2) == ZoneInfo.TZ_ID_OFFSET.getId().intValue() && str3.startsWith("GMT"))
            str3 = str3.substring("GMT".length()); 
          return tbTimestamp2.toString() + " " + str3;
        case 22:
          timeZone = TimeZone.getDefault();
          tbTimestamp2 = getTbTimestampLTZFromBytes(arrayOfByte1, paramInt1, paramInt2, timeZone);
          return (tbTimestamp2 == null) ? null : tbTimestamp2.toString();
      } 
      Object object = toObject(paramObject, paramInt1, paramInt2, paramInt3, DataType.getSqlType(paramInt3, 0, this.conn.getMapDateToTimestamp()), paramInt4, paramInt5, paramBoolean, null, null, null, null);
      return (object == null) ? null : object.toString();
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public Time toTime(Object paramObject, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return null; 
    if (paramObject instanceof byte[]) {
      String str1;
      String str2;
      byte[] arrayOfByte = (byte[])paramObject;
      switch (paramInt3) {
        case 2:
        case 3:
          str1 = getDBDecodedString(arrayOfByte, paramInt1, paramInt2);
          return Time.valueOf(str1.trim());
        case 18:
        case 19:
          str2 = getDBDecodedNString(arrayOfByte, paramInt1, paramInt2);
          return Time.valueOf(str2.trim());
        case 5:
          if (paramInt2 != 8)
            throw TbError.newSQLException(-590750, paramInt2); 
          return toTimeFromDate(arrayOfByte, paramInt1);
        case 6:
          if (paramInt2 != 8)
            throw TbError.newSQLException(-590752, paramInt2); 
          return toTimeFromBytes(arrayOfByte, paramInt1);
        case 7:
          if (paramInt2 != 12)
            throw TbError.newSQLException(-590751, paramInt2); 
          return toTimeFromTimestamp(arrayOfByte, paramInt1);
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  private Time toTimeFromBytes(byte[] paramArrayOfbyte, int paramInt) {
    int i = 0xFF & paramArrayOfbyte[paramInt];
    int j = 0xFF & paramArrayOfbyte[paramInt + 1];
    int k = 0xFF & paramArrayOfbyte[paramInt + 2];
    Calendar calendar = Calendar.getInstance();
    calendar.set(1970, 0, 1, i, j, k);
    calendar.clear(14);
    return new Time(calendar.getTimeInMillis());
  }
  
  private Time toTimeFromDate(byte[] paramArrayOfbyte, int paramInt) {
    int i = 0xFF & paramArrayOfbyte[paramInt + 4];
    int j = 0xFF & paramArrayOfbyte[paramInt + 5];
    int k = 0xFF & paramArrayOfbyte[paramInt + 6];
    Calendar calendar = Calendar.getInstance();
    calendar.set(1970, 0, 1, i, j, k);
    calendar.clear(14);
    return new Time(calendar.getTimeInMillis());
  }
  
  private Time toTimeFromTimestamp(byte[] paramArrayOfbyte, int paramInt) {
    int i = 0xFF & paramArrayOfbyte[paramInt + 4];
    int j = 0xFF & paramArrayOfbyte[paramInt + 5];
    int k = 0xFF & paramArrayOfbyte[paramInt + 6];
    Calendar calendar = Calendar.getInstance();
    calendar.set(1970, 0, 1, i, j, k);
    calendar.clear(14);
    Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
    return new Time(timestamp.getTime());
  }
  
  public Timestamp toTimestamp(Object paramObject, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    return toTimestamp(paramObject, paramInt1, paramInt2, paramInt3, null);
  }
  
  public Timestamp toTimestamp(Object paramObject, int paramInt1, int paramInt2, int paramInt3, Calendar paramCalendar) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return null; 
    if (paramObject instanceof byte[]) {
      String str1;
      String str2;
      byte[] arrayOfByte = (byte[])paramObject;
      switch (paramInt3) {
        case 2:
        case 3:
          str1 = getDBDecodedString(arrayOfByte, paramInt1, paramInt2);
          return Timestamp.valueOf(str1.trim());
        case 18:
        case 19:
          str2 = getDBDecodedNString(arrayOfByte, paramInt1, paramInt2);
          return Timestamp.valueOf(str2.trim());
        case 5:
          return toTimestampFromDate(arrayOfByte, paramInt1, paramInt2);
        case 6:
          return toTimestampFromTime(arrayOfByte, paramInt1, paramInt2);
        case 7:
          return (paramCalendar == null) ? toTimestampFromBytes(arrayOfByte, paramInt1, paramInt2) : toTimestampFromBytes(arrayOfByte, paramInt1, paramInt2, paramCalendar);
        case 21:
          return toTimestampTZFromBytes(arrayOfByte, paramInt1, paramInt2);
        case 22:
          return toTimestampLTZFromBytes(arrayOfByte, paramInt1, paramInt2);
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public TbTimestamp toTbTimestamp(Object paramObject, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return null; 
    if (paramObject instanceof byte[]) {
      byte[] arrayOfByte2;
      byte b;
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = (byte[])paramObject;
      switch (paramInt3) {
        case 5:
          if (paramInt2 != 8)
            throw TbError.newSQLException(-590750, paramInt2); 
          arrayOfByte2 = new byte[12];
          System.arraycopy(arrayOfByte1, paramInt1, arrayOfByte2, 0, 8);
          for (b = 8; b < arrayOfByte2.length; b++)
            arrayOfByte2[b] = 0; 
          return new TbTimestamp(arrayOfByte2);
        case 7:
          if (paramInt2 != 12)
            throw TbError.newSQLException(-590751, paramInt2); 
          arrayOfByte3 = new byte[12];
          System.arraycopy(arrayOfByte1, paramInt1, arrayOfByte3, 0, 12);
          return new TbTimestamp(arrayOfByte3);
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  private Timestamp toTimestampFromBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    if (paramInt2 != 12)
      throw TbError.newSQLException(-590751, paramInt2); 
    int i = 0xFF & paramArrayOfbyte[paramInt1 + 0];
    int j = 0xFF & paramArrayOfbyte[paramInt1 + 1];
    int k = 0xFF & paramArrayOfbyte[paramInt1 + 2];
    int m = 0xFF & paramArrayOfbyte[paramInt1 + 3];
    int n = 0xFF & paramArrayOfbyte[paramInt1 + 4];
    int i1 = 0xFF & paramArrayOfbyte[paramInt1 + 5];
    int i2 = 0xFF & paramArrayOfbyte[paramInt1 + 6];
    int i3 = (i - 100) * 100 + j - 100;
    int i4 = k - 1;
    int i5 = TbCommon.bytes2Int(paramArrayOfbyte, paramInt1 + 8, 4);
    Calendar calendar = Calendar.getInstance();
    calendar.set(i3, i4, m, n, i1, i2);
    Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
    timestamp.setNanos(i5);
    return timestamp;
  }
  
  private Timestamp toTimestampFromBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, Calendar paramCalendar) throws SQLException {
    if (paramInt2 != 12)
      throw TbError.newSQLException(-590751, paramInt2); 
    int i = 0xFF & paramArrayOfbyte[paramInt1 + 0];
    int j = 0xFF & paramArrayOfbyte[paramInt1 + 1];
    int k = 0xFF & paramArrayOfbyte[paramInt1 + 2];
    int m = 0xFF & paramArrayOfbyte[paramInt1 + 3];
    int n = 0xFF & paramArrayOfbyte[paramInt1 + 4];
    int i1 = 0xFF & paramArrayOfbyte[paramInt1 + 5];
    int i2 = 0xFF & paramArrayOfbyte[paramInt1 + 6];
    int i3 = (i - 100) * 100 + j - 100;
    int i4 = k - 1;
    int i5 = TbCommon.bytes2Int(paramArrayOfbyte, paramInt1 + 8, 4);
    paramCalendar.set(i3, i4, m, n, i1, i2);
    Timestamp timestamp = new Timestamp(paramCalendar.getTimeInMillis());
    timestamp.setNanos(i5);
    return timestamp;
  }
  
  private Timestamp toTimestampFromTime(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    if (paramInt2 != 8)
      throw TbError.newSQLException(-590752, paramInt2); 
    int i = 0xFF & paramArrayOfbyte[paramInt1];
    int j = 0xFF & paramArrayOfbyte[paramInt1 + 1];
    int k = 0xFF & paramArrayOfbyte[paramInt1 + 2];
    int m = TbCommon.bytes2Int(paramArrayOfbyte, paramInt1 + 4, 4);
    Calendar calendar = Calendar.getInstance();
    calendar.set(1970, 0, 1, i, j, k);
    Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
    timestamp.setNanos(m);
    return timestamp;
  }
  
  private Timestamp toTimestampFromDate(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    if (paramInt2 != 8)
      throw TbError.newSQLException(-590750, paramInt2); 
    int i = 0xFF & paramArrayOfbyte[paramInt1 + 0];
    int j = 0xFF & paramArrayOfbyte[paramInt1 + 1];
    int k = 0xFF & paramArrayOfbyte[paramInt1 + 2];
    int m = 0xFF & paramArrayOfbyte[paramInt1 + 3];
    int n = 0xFF & paramArrayOfbyte[paramInt1 + 4];
    int i1 = 0xFF & paramArrayOfbyte[paramInt1 + 5];
    int i2 = 0xFF & paramArrayOfbyte[paramInt1 + 6];
    int i3 = (i - 100) * 100 + j - 100;
    int i4 = k - 1;
    boolean bool = false;
    Calendar calendar = Calendar.getInstance();
    calendar.set(i3, i4, m, n, i1, i2);
    Timestamp timestamp = new Timestamp(calendar.getTime().getTime());
    timestamp.setNanos(bool);
    return timestamp;
  }
  
  private int getTimeZoneIdFromBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    if (paramInt2 != 17)
      throw TbError.newSQLException(-590751, paramInt2); 
    return TbCommon.bytes2Int(paramArrayOfbyte, paramInt1 + 15, 2);
  }
  
  public TimeZone toTimeZoneFromBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    if (paramInt2 != 17)
      throw TbError.newSQLException(-590751, paramInt2); 
    int i = TbCommon.bytes2Int(paramArrayOfbyte, paramInt1 + 15, 2);
    if (i == ZoneInfo.TZ_ID_OFFSET.getId().intValue()) {
      int j = (0xFF & paramArrayOfbyte[paramInt1 + 12]) - 100;
      int k = 0xFF & paramArrayOfbyte[paramInt1 + 13];
      NumberFormat numberFormat = NumberFormat.getInstance();
      numberFormat.setMinimumIntegerDigits(2);
      StringBuffer stringBuffer = new StringBuffer();
      stringBuffer.append("GMT");
      if (j >= 0)
        stringBuffer.append("+"); 
      stringBuffer.append(numberFormat.format(j));
      stringBuffer.append(":");
      stringBuffer.append(numberFormat.format(k));
      return TimeZone.getTimeZone(stringBuffer.toString());
    } 
    String str = ZoneInfo.getZoneNameById(i);
    return TimeZone.getTimeZone(str);
  }
  
  private TbTimestamp getTbTimestampTZFromBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, TimeZone paramTimeZone) throws SQLException {
    if (paramInt2 != 17)
      throw TbError.newSQLException(-590751, paramInt2); 
    int i = 0xFF & paramArrayOfbyte[paramInt1 + 0];
    int j = 0xFF & paramArrayOfbyte[paramInt1 + 1];
    int k = 0xFF & paramArrayOfbyte[paramInt1 + 2];
    int m = 0xFF & paramArrayOfbyte[paramInt1 + 3];
    int n = 0xFF & paramArrayOfbyte[paramInt1 + 4];
    int i1 = 0xFF & paramArrayOfbyte[paramInt1 + 5];
    int i2 = 0xFF & paramArrayOfbyte[paramInt1 + 6];
    int i3 = (i - 100) * 100 + j - 100;
    int i4 = k - 1;
    int i5 = TbCommon.bytes2Int(paramArrayOfbyte, paramInt1 + 8, 4);
    Calendar calendar = Calendar.getInstance(ZoneInfo.TZ_UTC);
    calendar.set(i3, i4, m, n, i1, i2);
    calendar.clear(14);
    long l = calendar.getTimeInMillis();
    calendar.setTimeZone(paramTimeZone);
    calendar.setTimeInMillis(l);
    return new TbTimestamp(calendar.get(1), calendar.get(2) + 1, calendar.get(5), calendar.get(11), calendar.get(12), calendar.get(13), i5);
  }
  
  private TbTimestamp getTbTimestampLTZFromBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, TimeZone paramTimeZone) throws SQLException {
    if (paramInt2 != 12)
      throw TbError.newSQLException(-590751, paramInt2); 
    int i = 0xFF & paramArrayOfbyte[paramInt1 + 0];
    int j = 0xFF & paramArrayOfbyte[paramInt1 + 1];
    int k = 0xFF & paramArrayOfbyte[paramInt1 + 2];
    int m = 0xFF & paramArrayOfbyte[paramInt1 + 3];
    int n = 0xFF & paramArrayOfbyte[paramInt1 + 4];
    int i1 = 0xFF & paramArrayOfbyte[paramInt1 + 5];
    int i2 = 0xFF & paramArrayOfbyte[paramInt1 + 6];
    int i3 = (i - 100) * 100 + j - 100;
    int i4 = k - 1;
    int i5 = TbCommon.bytes2Int(paramArrayOfbyte, paramInt1 + 8, 4);
    Calendar calendar = Calendar.getInstance(ZoneInfo.TZ_UTC);
    calendar.set(i3, i4, m, n, i1, i2);
    calendar.clear(14);
    long l = calendar.getTimeInMillis();
    calendar.setTimeZone(paramTimeZone);
    calendar.setTimeInMillis(l);
    paramArrayOfbyte = new byte[12];
    int i6 = calendar.get(1);
    paramArrayOfbyte[0] = (byte)(i6 / 100 + 100);
    paramArrayOfbyte[1] = (byte)(i6 % 100 + 100);
    paramArrayOfbyte[2] = (byte)(calendar.get(2) + 1);
    paramArrayOfbyte[3] = (byte)calendar.get(5);
    paramArrayOfbyte[4] = (byte)calendar.get(11);
    paramArrayOfbyte[5] = (byte)calendar.get(12);
    paramArrayOfbyte[6] = (byte)calendar.get(13);
    paramArrayOfbyte[7] = 0;
    TbCommon.int2Bytes(i5, paramArrayOfbyte, 8, 4);
    return new TbTimestamp(paramArrayOfbyte);
  }
  
  private Timestamp toTimestampTZFromBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    if (paramInt2 != 17)
      throw TbError.newSQLException(-590751, paramInt2); 
    int i = 0xFF & paramArrayOfbyte[paramInt1 + 0];
    int j = 0xFF & paramArrayOfbyte[paramInt1 + 1];
    int k = 0xFF & paramArrayOfbyte[paramInt1 + 2];
    int m = 0xFF & paramArrayOfbyte[paramInt1 + 3];
    int n = 0xFF & paramArrayOfbyte[paramInt1 + 4];
    int i1 = 0xFF & paramArrayOfbyte[paramInt1 + 5];
    int i2 = 0xFF & paramArrayOfbyte[paramInt1 + 6];
    int i3 = (i - 100) * 100 + j - 100;
    int i4 = k - 1;
    int i5 = TbCommon.bytes2Int(paramArrayOfbyte, paramInt1 + 8, 4);
    Calendar calendar = Calendar.getInstance(ZoneInfo.TZ_UTC);
    calendar.set(i3, i4, m, n, i1, i2);
    calendar.clear(14);
    Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
    timestamp.setNanos(i5);
    return timestamp;
  }
  
  private Timestamp toTimestampLTZFromBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SQLException {
    if (paramInt2 != 12)
      throw TbError.newSQLException(-590751, "" + paramInt2); 
    int i = 0xFF & paramArrayOfbyte[paramInt1 + 0];
    int j = 0xFF & paramArrayOfbyte[paramInt1 + 1];
    int k = 0xFF & paramArrayOfbyte[paramInt1 + 2];
    int m = 0xFF & paramArrayOfbyte[paramInt1 + 3];
    int n = 0xFF & paramArrayOfbyte[paramInt1 + 4];
    int i1 = 0xFF & paramArrayOfbyte[paramInt1 + 5];
    int i2 = 0xFF & paramArrayOfbyte[paramInt1 + 6];
    int i3 = (i - 100) * 100 + j - 100;
    int i4 = k - 1;
    int i5 = TbCommon.bytes2Int(paramArrayOfbyte, paramInt1 + 8, 4);
    Calendar calendar = Calendar.getInstance(ZoneInfo.TZ_UTC);
    calendar.set(i3, i4, m, n, i1, i2);
    long l = calendar.getTimeInMillis();
    calendar.setTimeInMillis(l);
    calendar.clear(14);
    Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
    timestamp.setNanos(i5);
    return timestamp;
  }
  
  public InputStream toUnicodeStream(Object paramObject, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) throws SQLException {
    if (paramInt2 <= 0 || paramObject == null)
      return null; 
    if (DataType.isCharacterCategory(paramInt3)) {
      String str = toString(paramObject, paramInt1, paramInt2, paramInt3, paramBoolean);
      return new ByteArrayInputStream(str.getBytes());
    } 
    if (paramObject instanceof byte[]) {
      byte[] arrayOfByte;
      String str;
      switch (paramInt3) {
        case 10:
          arrayOfByte = new byte[paramInt2];
          System.arraycopy(paramObject, paramInt1, arrayOfByte, 0, paramInt2);
          synchronized (this.conn) {
            str = this.conn.getTbComm().readLong(arrayOfByte);
          } 
          return new ByteArrayInputStream(str.getBytes());
      } 
      throw TbError.newSQLException(-90612, DataType.getDBTypeName(paramInt3));
    } 
    throw TbError.newSQLException(-90612, paramObject.toString());
  }
  
  public static String tbBase64Encode(byte[] paramArrayOfbyte) {
    StringBuilder stringBuilder = new StringBuilder();
    byte b;
    for (b = 0; b < paramArrayOfbyte.length - 2; b += 3) {
      stringBuilder.append(encode(paramArrayOfbyte[b] >> 2 & 0x3F));
      stringBuilder.append(encode((paramArrayOfbyte[b] & 0x3) << 4 | (paramArrayOfbyte[b + 1] & 0xF0) >> 4));
      stringBuilder.append(encode((paramArrayOfbyte[b + 1] & 0xF) << 2 | (paramArrayOfbyte[b + 2] & 0xC0) >> 6));
      stringBuilder.append(encode(paramArrayOfbyte[b + 2] & 0x3F));
    } 
    if (b < paramArrayOfbyte.length) {
      stringBuilder.append(encode(paramArrayOfbyte[b] >> 2 & 0x3F));
      if (b == paramArrayOfbyte.length - 1) {
        stringBuilder.append(encode((paramArrayOfbyte[b] & 0x3) << 4));
        stringBuilder.append('=');
      } else {
        stringBuilder.append(encode((paramArrayOfbyte[b] & 0x3) << 4 | (paramArrayOfbyte[b + 1] & 0xF0) >> 4));
        stringBuilder.append(encode((paramArrayOfbyte[b + 1] & 0xF) << 2));
      } 
      stringBuilder.append('=');
    } 
    return stringBuilder.toString();
  }
  
  private static char encode(int paramInt) {
    char[] arrayOfChar = { 
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
        'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 
        'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 
        'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', 
        '8', '9', '+', '/' };
    return arrayOfChar[paramInt];
  }
  
  public static byte[] tbBase64Decode(byte[] paramArrayOfbyte) {
    byte b = 0;
    while (b < paramArrayOfbyte.length && pr2six[paramArrayOfbyte[b++]] <= 63);
    int i = b - 1;
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    b = 0;
    while (i > 4) {
      byteArrayOutputStream.write(pr2six[paramArrayOfbyte[b]] << 2 | pr2six[paramArrayOfbyte[b + 1]] >> 4);
      byteArrayOutputStream.write(pr2six[paramArrayOfbyte[b + 1]] << 4 | pr2six[paramArrayOfbyte[b + 2]] >> 2);
      byteArrayOutputStream.write(pr2six[paramArrayOfbyte[b + 2]] << 6 | pr2six[paramArrayOfbyte[b + 3]]);
      b += 4;
      i -= 4;
    } 
    if (i > 1)
      byteArrayOutputStream.write(pr2six[paramArrayOfbyte[b]] << 2 | pr2six[paramArrayOfbyte[b + 1]] >> 4); 
    if (i > 2)
      byteArrayOutputStream.write(pr2six[paramArrayOfbyte[b + 1]] << 4 | pr2six[paramArrayOfbyte[b + 2]] >> 2); 
    if (i > 3)
      byteArrayOutputStream.write(pr2six[paramArrayOfbyte[b + 2]] << 6 | pr2six[paramArrayOfbyte[b + 3]]); 
    return byteArrayOutputStream.toByteArray();
  }
  
  public static byte[] base64Decode2(String paramString) throws Exception {
    int[] arrayOfInt = { 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 
        54, 55, 56, 57, 58, 59, 60, 61, -1, -1, 
        -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 
        5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 
        15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 
        25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 
        29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 
        39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 
        49, 50, 51, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1 };
    byte[] arrayOfByte = paramString.getBytes("ASCII");
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    for (byte b = 0; b < arrayOfByte.length; b += 4) {
      int i = 0;
      if (arrayOfInt[arrayOfByte[b]] != -1) {
        i = (arrayOfInt[arrayOfByte[b]] & 0xFF) << 18;
      } else {
        b++;
        continue;
      } 
      byte b1 = 0;
      if (b + 1 < arrayOfByte.length && arrayOfInt[arrayOfByte[b + 1]] != -1) {
        i |= (arrayOfInt[arrayOfByte[b + 1]] & 0xFF) << 12;
        b1++;
      } 
      if (b + 2 < arrayOfByte.length && arrayOfInt[arrayOfByte[b + 2]] != -1) {
        i |= (arrayOfInt[arrayOfByte[b + 2]] & 0xFF) << 6;
        b1++;
      } 
      if (b + 3 < arrayOfByte.length && arrayOfInt[arrayOfByte[b + 3]] != -1) {
        i |= arrayOfInt[arrayOfByte[b + 3]] & 0xFF;
        b1++;
      } 
      while (b1 > 0) {
        int j = (i & 0xFF0000) >> 16;
        byteArrayOutputStream.write((char)j);
        i <<= 8;
        b1--;
      } 
    } 
    return byteArrayOutputStream.toByteArray();
  }
  
  public static byte[] base64Decode(byte[] paramArrayOfbyte) throws Exception {
    int i = 0;
    byte b1 = 0;
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    for (byte b2 = 0; b2 < paramArrayOfbyte.length; b2++) {
      byte b = d[paramArrayOfbyte[b2]];
      switch (b) {
        case 64:
          break;
        case 66:
          return null;
        case 65:
          break;
        default:
          i = i << 6 | b;
          if (++b1 == 4) {
            byteArrayOutputStream.write((byte)(i >> 16 & 0xFF));
            byteArrayOutputStream.write((byte)(i >> 8 & 0xFF));
            byteArrayOutputStream.write((byte)(i & 0xFF));
            i = 0;
            b1 = 0;
          } 
          break;
      } 
    } 
    if (b1 == 3) {
      byteArrayOutputStream.write((byte)(i >> 10 & 0xFF));
      byteArrayOutputStream.write((byte)(i >> 2 & 0xFF));
    } else if (b1 == 2) {
      byteArrayOutputStream.write((byte)(i >> 4 & 0xFF));
    } 
    return byteArrayOutputStream.toByteArray();
  }
  
  class ObjInstBldCtx {
    TbStructDescriptor desc;
    
    Object[] values;
    
    int attrProcessed;
    
    ObjInstBldCtx(TbStructDescriptor param1TbStructDescriptor) {
      this.desc = param1TbStructDescriptor;
      this.values = new Object[param1TbStructDescriptor.getNumOfFields()];
      this.attrProcessed = 0;
    }
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\DataTypeConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */