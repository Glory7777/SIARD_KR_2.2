package com.tmax.tibero.jdbc.data;

import java.sql.Timestamp;
import java.util.TimeZone;

public class TbTimestampTZ extends Timestamp {
  private static final long serialVersionUID = 8847981183019458535L;
  
  private TimeZone timezone;
  
  public TbTimestampTZ(Timestamp paramTimestamp, TimeZone paramTimeZone) {
    super(paramTimestamp.getTime());
    setNanos(paramTimestamp.getNanos());
    this.timezone = paramTimeZone;
  }
  
  public TimeZone getTimeZone() {
    return this.timezone;
  }
  
  public void setTimeZone(TimeZone paramTimeZone) {
    this.timezone = paramTimeZone;
  }
  
  public String toString() {
    return super.toString() + " " + this.timezone.getID();
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\TbTimestampTZ.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */