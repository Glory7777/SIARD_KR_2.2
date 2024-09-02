package ch.admin.bar.siard2.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import javax.xml.datatype.Duration;

public interface Value {
  Cell getAncestorCell();
  
  MetaValue getMetaValue();
  
  boolean isNull();
  
  String getString() throws IOException;
  
  void setString(String paramString) throws IOException;
  
  byte[] getBytes() throws IOException;
  
  void setBytes(byte[] paramArrayOfbyte) throws IOException;
  
  Boolean getBoolean() throws IOException;
  
  void setBoolean(boolean paramBoolean) throws IOException;
  
  Short getShort() throws IOException;
  
  void setShort(short paramShort) throws IOException;
  
  Integer getInt() throws IOException;
  
  void setInt(int paramInt) throws IOException;
  
  Long getLong() throws IOException;
  
  void setLong(long paramLong) throws IOException;
  
  BigInteger getBigInteger() throws IOException;
  
  void setBigInteger(BigInteger paramBigInteger) throws IOException;
  
  BigDecimal getBigDecimal() throws IOException;
  
  void setBigDecimal(BigDecimal paramBigDecimal) throws IOException;
  
  Float getFloat() throws IOException;
  
  void setFloat(float paramFloat) throws IOException;
  
  Double getDouble() throws IOException;
  
  void setDouble(double paramDouble) throws IOException;
  
  Date getDate() throws IOException;
  
  void setDate(Date paramDate) throws IOException;
  
  Time getTime() throws IOException;
  
  void setTime(Time paramTime) throws IOException;
  
  Timestamp getTimestamp() throws IOException;
  
  void setTimestamp(Timestamp paramTimestamp) throws IOException;
  
  Duration getDuration() throws IOException;
  
  void setDuration(Duration paramDuration) throws IOException;
  
  Reader getReader() throws IOException;
  
  long getCharLength() throws IOException;
  
  void setReader(Reader paramReader) throws IOException;
  
  String getFilename() throws IOException;
  
  InputStream getInputStream() throws IOException;
  
  long getByteLength() throws IOException;
  
  void setInputStream(InputStream paramInputStream) throws IOException;
  
  void setInputStream(InputStream paramInputStream, String paramString) throws IOException;
  
  Object getObject() throws IOException;
  
  int getElements() throws IOException;
  
  Field getElement(int paramInt) throws IOException;
  
  int getAttributes() throws IOException;
  
  Field getAttribute(int paramInt) throws IOException;
  
  List<Value> getValues(boolean paramBoolean1, boolean paramBoolean2) throws IOException;
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\Value.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */