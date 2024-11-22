package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.data.TbDate;
import com.tmax.tibero.jdbc.err.TbError;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class TbSQLInput implements SQLInput {
  private Map map;
  
  private Object[] attributes;
  
  private int[] attributeTypes;
  
  private int index;
  
  private boolean wasNull;
  
  private TbStruct struct;
  
  private TbStructDescriptor descriptor;
  
  public TbSQLInput(TbStruct paramTbStruct, TbStructDescriptor paramTbStructDescriptor, Map paramMap) throws SQLException {
    this.struct = paramTbStruct;
    this.descriptor = paramTbStructDescriptor;
    this.map = paramMap;
    this.attributes = paramTbStruct.getAttributes();
    this.attributeTypes = paramTbStructDescriptor.getAttributeTypes();
    this.wasNull = false;
    this.index = 0;
  }
  
  public String readString() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return null; 
      if (this.attributes[this.index] instanceof String) {
        String str = (String)this.attributes[this.index];
        return str;
      } 
      if (this.attributes[this.index] instanceof Date) {
        Date date = (Date)this.attributes[this.index];
        return date.toString();
      } 
      if (this.attributes[this.index] instanceof Time) {
        Time time = (Time)this.attributes[this.index];
        return time.toString();
      } 
      if (this.attributes[this.index] instanceof Timestamp) {
        Timestamp timestamp = (Timestamp)this.attributes[this.index];
        return timestamp.toString();
      } 
      if (this.attributes[this.index] instanceof Clob) {
        Clob clob = (Clob)this.attributes[this.index];
        return clob.getSubString(1L, (int)clob.length());
      } 
      throw TbError.newSQLException(-90612, "readString():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public boolean readBoolean() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return false; 
      if (this.attributes[this.index] instanceof String) {
        String str = (String)this.attributes[this.index];
        return str.equals("1");
      } 
      if (this.attributes[this.index] instanceof Integer) {
        Integer integer = (Integer)this.attributes[this.index];
        return (integer.intValue() == 1);
      } 
      if (this.attributes[this.index] instanceof Long) {
        Long long_ = (Long)this.attributes[this.index];
        return (long_.intValue() == 1);
      } 
      if (this.attributes[this.index] instanceof Float) {
        Float float_ = (Float)this.attributes[this.index];
        return (float_.intValue() == 1);
      } 
      if (this.attributes[this.index] instanceof Double) {
        Double double_ = (Double)this.attributes[this.index];
        return (double_.intValue() == 1);
      } 
      throw TbError.newSQLException(-90612, "readBoolean():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public byte readByte() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return 0; 
      if (this.attributes[this.index] instanceof BigDecimal) {
        BigDecimal bigDecimal = (BigDecimal)this.attributes[this.index];
        return bigDecimal.byteValue();
      } 
      if (this.attributes[this.index] instanceof Integer) {
        Integer integer = (Integer)this.attributes[this.index];
        return integer.byteValue();
      } 
      if (this.attributes[this.index] instanceof Long) {
        Long long_ = (Long)this.attributes[this.index];
        return long_.byteValue();
      } 
      if (this.attributes[this.index] instanceof Float) {
        Float float_ = (Float)this.attributes[this.index];
        return float_.byteValue();
      } 
      if (this.attributes[this.index] instanceof Double) {
        Double double_ = (Double)this.attributes[this.index];
        return double_.byteValue();
      } 
      if (this.attributes[this.index] instanceof String) {
        String str = (String)this.attributes[this.index];
        return Byte.parseByte(str);
      } 
      throw TbError.newSQLException(-90612, "readByte():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public short readShort() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return 0; 
      if (this.attributes[this.index] instanceof BigDecimal) {
        BigDecimal bigDecimal = (BigDecimal)this.attributes[this.index];
        return bigDecimal.shortValue();
      } 
      if (this.attributes[this.index] instanceof Integer) {
        Integer integer = (Integer)this.attributes[this.index];
        return integer.shortValue();
      } 
      if (this.attributes[this.index] instanceof Long) {
        Long long_ = (Long)this.attributes[this.index];
        return long_.shortValue();
      } 
      if (this.attributes[this.index] instanceof Float) {
        Float float_ = (Float)this.attributes[this.index];
        return float_.shortValue();
      } 
      if (this.attributes[this.index] instanceof Double) {
        Double double_ = (Double)this.attributes[this.index];
        return double_.shortValue();
      } 
      if (this.attributes[this.index] instanceof String) {
        String str = (String)this.attributes[this.index];
        return Short.parseShort(str);
      } 
      throw TbError.newSQLException(-90612, "readShort():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public int readInt() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return 0; 
      if (this.attributes[this.index] instanceof BigDecimal) {
        BigDecimal bigDecimal = (BigDecimal)this.attributes[this.index];
        return bigDecimal.intValue();
      } 
      if (this.attributes[this.index] instanceof Integer) {
        Integer integer = (Integer)this.attributes[this.index];
        return integer.intValue();
      } 
      if (this.attributes[this.index] instanceof Long) {
        Long long_ = (Long)this.attributes[this.index];
        return long_.intValue();
      } 
      if (this.attributes[this.index] instanceof Float) {
        Float float_ = (Float)this.attributes[this.index];
        return float_.intValue();
      } 
      if (this.attributes[this.index] instanceof Double) {
        Double double_ = (Double)this.attributes[this.index];
        return double_.intValue();
      } 
      if (this.attributes[this.index] instanceof String) {
        String str = (String)this.attributes[this.index];
        return Integer.parseInt(str);
      } 
      throw TbError.newSQLException(-90612, "readInt():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public long readLong() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return 0L; 
      if (this.attributes[this.index] instanceof BigDecimal) {
        BigDecimal bigDecimal = (BigDecimal)this.attributes[this.index];
        return bigDecimal.longValue();
      } 
      if (this.attributes[this.index] instanceof Integer) {
        Integer integer = (Integer)this.attributes[this.index];
        return integer.longValue();
      } 
      if (this.attributes[this.index] instanceof Long) {
        Long long_ = (Long)this.attributes[this.index];
        return long_.longValue();
      } 
      if (this.attributes[this.index] instanceof Float) {
        Float float_ = (Float)this.attributes[this.index];
        return float_.longValue();
      } 
      if (this.attributes[this.index] instanceof Double) {
        Double double_ = (Double)this.attributes[this.index];
        return double_.longValue();
      } 
      if (this.attributes[this.index] instanceof String) {
        String str = (String)this.attributes[this.index];
        return Long.parseLong(str);
      } 
      throw TbError.newSQLException(-90612, "readLong():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public float readFloat() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return 0.0F; 
      if (this.attributes[this.index] instanceof BigDecimal) {
        BigDecimal bigDecimal = (BigDecimal)this.attributes[this.index];
        return bigDecimal.floatValue();
      } 
      if (this.attributes[this.index] instanceof Integer) {
        Integer integer = (Integer)this.attributes[this.index];
        return integer.floatValue();
      } 
      if (this.attributes[this.index] instanceof Long) {
        Long long_ = (Long)this.attributes[this.index];
        return long_.floatValue();
      } 
      if (this.attributes[this.index] instanceof Float) {
        Float float_ = (Float)this.attributes[this.index];
        return float_.floatValue();
      } 
      if (this.attributes[this.index] instanceof Double) {
        Double double_ = (Double)this.attributes[this.index];
        return double_.floatValue();
      } 
      if (this.attributes[this.index] instanceof String) {
        String str = (String)this.attributes[this.index];
        return Float.parseFloat(str);
      } 
      throw TbError.newSQLException(-90612, "readFloat():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public double readDouble() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return 0.0D; 
      if (this.attributes[this.index] instanceof BigDecimal) {
        BigDecimal bigDecimal = (BigDecimal)this.attributes[this.index];
        return bigDecimal.doubleValue();
      } 
      if (this.attributes[this.index] instanceof Integer) {
        Integer integer = (Integer)this.attributes[this.index];
        return integer.doubleValue();
      } 
      if (this.attributes[this.index] instanceof Long) {
        Long long_ = (Long)this.attributes[this.index];
        return long_.doubleValue();
      } 
      if (this.attributes[this.index] instanceof Float) {
        Float float_ = (Float)this.attributes[this.index];
        return float_.doubleValue();
      } 
      if (this.attributes[this.index] instanceof Double) {
        Double double_ = (Double)this.attributes[this.index];
        return double_.doubleValue();
      } 
      if (this.attributes[this.index] instanceof String) {
        String str = (String)this.attributes[this.index];
        return Double.parseDouble(str);
      } 
      throw TbError.newSQLException(-90612, "readDouble():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public BigDecimal readBigDecimal() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return null; 
      if (this.attributes[this.index] instanceof BigDecimal) {
        BigDecimal bigDecimal = (BigDecimal)this.attributes[this.index];
        return bigDecimal;
      } 
      if (this.attributes[this.index] instanceof Integer) {
        Integer integer = (Integer)this.attributes[this.index];
        return new BigDecimal(integer.intValue());
      } 
      if (this.attributes[this.index] instanceof Long) {
        Long long_ = (Long)this.attributes[this.index];
        return new BigDecimal(long_.doubleValue());
      } 
      if (this.attributes[this.index] instanceof Float) {
        Float float_ = (Float)this.attributes[this.index];
        return new BigDecimal(float_.doubleValue());
      } 
      if (this.attributes[this.index] instanceof Double) {
        Double double_ = (Double)this.attributes[this.index];
        return new BigDecimal(double_.doubleValue());
      } 
      if (this.attributes[this.index] instanceof String) {
        String str = (String)this.attributes[this.index];
        return new BigDecimal(str);
      } 
      throw TbError.newSQLException(-90612, "readBigDecimal():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public byte[] readBytes() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return null; 
      if (this.attributes[this.index] instanceof byte[])
        return (byte[])this.attributes[this.index]; 
      if (this.attributes[this.index] instanceof String) {
        String str = (String)this.attributes[this.index];
        return str.getBytes();
      } 
      if (this.attributes[this.index] instanceof Blob) {
        Blob blob = (Blob)this.attributes[this.index];
        return blob.getBytes(1L, (int)blob.length());
      } 
      throw TbError.newSQLException(-90612, "readBytes():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public Date readDate() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return null; 
      if (this.attributes[this.index] instanceof Date)
        return (Date)this.attributes[this.index]; 
      if (this.attributes[this.index] instanceof Timestamp) {
        Timestamp timestamp = (Timestamp)this.attributes[this.index];
        return new Date(timestamp.getTime());
      } 
      if (this.attributes[this.index] instanceof TbDate) {
        TbDate tbDate = (TbDate)this.attributes[this.index];
        Calendar calendar = Calendar.getInstance();
        calendar.set(tbDate.getYear(), tbDate.getMonth(), tbDate.getDayOfMonth(), tbDate.getHourOfDay(), tbDate.getMinutes(), tbDate.getSeconds());
        return new Date(calendar.getTimeInMillis());
      } 
      throw TbError.newSQLException(-90612, "readDate():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public Time readTime() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return null; 
      if (this.attributes[this.index] instanceof Time)
        return (Time)this.attributes[this.index]; 
      if (this.attributes[this.index] instanceof Timestamp) {
        Timestamp timestamp = (Timestamp)this.attributes[this.index];
        return new Time(timestamp.getTime());
      } 
      throw TbError.newSQLException(-90612, "readTime():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public Timestamp readTimestamp() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return null; 
      if (this.attributes[this.index] instanceof Timestamp)
        return (Timestamp)this.attributes[this.index]; 
      if (this.attributes[this.index] instanceof Date) {
        Date date = (Date)this.attributes[this.index];
        return new Timestamp(date.getTime());
      } 
      if (this.attributes[this.index] instanceof Time) {
        Time time = (Time)this.attributes[this.index];
        return new Timestamp(time.getTime());
      } 
      throw TbError.newSQLException(-90612, "readTimestamp():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public Reader readCharacterStream() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return null; 
      if (this.attributes[this.index] instanceof Clob) {
        Clob clob = (Clob)this.attributes[this.index];
        return clob.getCharacterStream();
      } 
      if (this.attributes[this.index] instanceof String) {
        String str = (String)this.attributes[this.index];
        return new StringReader(str);
      } 
      throw TbError.newSQLException(-90612, "readCharacterStream():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public InputStream readAsciiStream() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return null; 
      if (this.attributes[this.index] instanceof Clob) {
        Clob clob = (Clob)this.attributes[this.index];
        return clob.getAsciiStream();
      } 
      throw TbError.newSQLException(-90612, "readAsciiStream():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public InputStream readBinaryStream() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return null; 
      if (this.attributes[this.index] instanceof Blob) {
        Blob blob = (Blob)this.attributes[this.index];
        return blob.getBinaryStream();
      } 
      throw TbError.newSQLException(-90612, "readBinaryStream():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public Object readObject() throws SQLException {
    Object object = null;
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return null; 
      if (this.attributes[this.index] instanceof TbStruct) {
        object = ((TbStruct)this.attributes[this.index]).toClass(this.map);
      } else if (this.attributes[this.index] instanceof java.sql.SQLData) {
        object = this.attributes[this.index];
      } else {
        throw TbError.newSQLException(-90612, "readObject():attr=" + this.attributes[this.index]);
      } 
    } finally {
      this.index++;
    } 
    return object;
  }
  
  public Ref readRef() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return null; 
      if (this.attributes[this.index] instanceof Ref)
        return (Ref)this.attributes[this.index]; 
      throw TbError.newSQLException(-90612, "readRef():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public Blob readBlob() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return null; 
      if (this.attributes[this.index] instanceof Blob)
        return (Blob)this.attributes[this.index]; 
      throw TbError.newSQLException(-90612, "readBlob():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public Clob readClob() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return null; 
      if (this.attributes[this.index] instanceof Clob)
        return (Clob)this.attributes[this.index]; 
      throw TbError.newSQLException(-90612, "readClob():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public Array readArray() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return null; 
      if (this.attributes[this.index] instanceof Array)
        return (Array)this.attributes[this.index]; 
      throw TbError.newSQLException(-90612, "readArray():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public boolean wasNull() throws SQLException {
    return this.wasNull;
  }
  
  public URL readURL() throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public NClob readNClob() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return null; 
      if (this.attributes[this.index] instanceof NClob)
        return (NClob)this.attributes[this.index]; 
      throw TbError.newSQLException(-90612, "readNClob():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public String readNString() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return null; 
      if (this.attributes[this.index] instanceof String) {
        String str = (String)this.attributes[this.index];
        return str;
      } 
      throw TbError.newSQLException(-90612, "readNString():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public SQLXML readSQLXML() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return null; 
      if (this.attributes[this.index] instanceof SQLXML) {
        SQLXML sQLXML = (SQLXML)this.attributes[this.index];
        return sQLXML;
      } 
      throw TbError.newSQLException(-90612, "readSQLXML():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public RowId readRowId() throws SQLException {
    try {
      if (this.wasNull = (this.attributes[this.index] == null))
        return null; 
      if (this.attributes[this.index] instanceof RowId) {
        RowId rowId = (RowId)this.attributes[this.index];
        return rowId;
      } 
      throw TbError.newSQLException(-90612, "readRowId():attr=" + this.attributes[this.index]);
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, exception);
    } finally {
      this.index++;
    } 
  }
  
  public <T> T readObject(Class<T> paramClass) throws SQLException {
    String str = paramClass.getName();
    switch (str) {
      case "java.sql.Array":
        return (T)readArray();
      case "java.io.InputStream":
        return (T)readBinaryStream();
      case "java.math.BigDecimal":
        return (T)readBigDecimal();
      case "java.sql.Blob":
        return (T)readBlob();
      case "java.lang.Byte":
        return (T)new Byte(readByte());
      case "byte[]":
        return (T)readBytes();
      case "java.io.Reader":
        return (T)readCharacterStream();
      case "java.sql.Clob":
        return (T)readClob();
      case "java.sql.Date":
        return (T)readDate();
      case "java.lang.Double":
        return (T)new Double(readDouble());
      case "float":
        return (T)new Float(readFloat());
      case "java.lang.Integer":
        return (T)new Integer(readInt());
      case "java.lang.Long":
        return (T)new Long(readLong());
      case "java.sql.NClob":
        return (T)readNClob();
      case "java.sql.Ref":
        return (T)readRef();
      case "java.sql.RowId":
        return (T)readRowId();
      case "java.lang.Short":
        return (T)new Short(readShort());
      case "java.sql.SQLXML":
        return (T)readSQLXML();
      case "java.lang.String":
        return (T)readString();
      case "java.sql.Time":
        return (T)readTime();
      case "java.sql.Timestamp":
        return (T)readTimestamp();
      case "java.net.URL":
        return (T)readURL();
    } 
    throw new SQLException();
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\TbSQLInput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */