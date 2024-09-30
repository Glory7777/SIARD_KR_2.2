package ch.enterag.utils;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

public class DU
{
  private static final String _sZ = "Z";
  private static final SimpleDateFormat _sdfXS_DATE = new SimpleDateFormat("yyyy-MM-dd");
  private static final SimpleDateFormat _sdfXS_TIME = new SimpleDateFormat("HH:mm:ss");
  private static final SimpleDateFormat _sdfXS_DATE_TIME = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
  private static final DecimalFormat _dfMILLIS = new DecimalFormat("000");
  private static final DecimalFormat _dfNANOS = new DecimalFormat("000000000");
  public static Date dateMINIMUM_SQL = null;
  public static Date dateMAXIMUM_SQL = null;
  public static Timestamp tsMINIMUM_SQL = null;
  public static Timestamp tsMAXIMUM_SQL = null;

  
  static {
    _sdfXS_TIME.setTimeZone(TZ.getUtcTimeZone());
    _sdfXS_DATE_TIME.setTimeZone(TZ.getUtcTimeZone());
    
    try {
      SimpleDateFormat sdfInternal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z");
      Date dateMinimum = (Date) sdfInternal.parse("0001-01-01 00:00:00.000 UTC");
      Date dateMaximum = (Date) sdfInternal.parse("9999-12-31 00:00:00.000 UTC");
      dateMINIMUM_SQL = new Date(dateMinimum.getTime());
      dateMAXIMUM_SQL = new Date(dateMaximum.getTime());
      dateMaximum = (Date) sdfInternal.parse("9999-12-31 23:59:59.999 UTC");
      tsMINIMUM_SQL = new Timestamp(dateMinimum.getTime());
      tsMAXIMUM_SQL = new Timestamp(dateMaximum.getTime());
      tsMAXIMUM_SQL.setNanos(999999999);
    } catch (ParseException pe) {
      System.err.println(pe.getClass().getName() + ": " + pe.getMessage());
    } 
  }
  
  private static Map<String, DU> _map = new HashMap<>();
  
  private SimpleDateFormat _sdf = null; public String getDateFormat() {
    return this._sdf.toPattern();
  } private SimpleDateFormat _sdfDate = null; public String getDateOnlyFormat() {
    return (this._sdfDate == null) ? null : this._sdfDate.toPattern();
  } private SimpleDateFormat _sdfTime = null; public String getTimeOnlyFormat() {
    return (this._sdfTime == null) ? null : this._sdfTime.toPattern();
  } private SimpleDateFormat _sdfTimestamp = null; public String getDateAndTimeFormat() {
    return (this._sdfTimestamp == null) ? null : this._sdfTimestamp.toPattern();
  }






  
  private DU(String sLanguage, String sDateFormat) {
    int iFirstDateSymbol = -1;
    int iLastDateSymbol = -1;
    int iFirstTimeSymbol = -1;
    int iLastTimeSymbol = -1;
    for (int i = 0; i < sDateFormat.length(); i++) {
      
      char c = sDateFormat.charAt(i);
      if ("GyYMwWDdFEu".indexOf(c) >= 0) {
        
        if (iFirstDateSymbol < 0)
          iFirstDateSymbol = i; 
        iLastDateSymbol = i;
      }
      else if ("aHkKhmsSzZX".indexOf(c) >= 0) {
        
        if (iFirstTimeSymbol < 0)
          iFirstTimeSymbol = i; 
        iLastTimeSymbol = i;
      } 
    } 
    if (iFirstDateSymbol >= 0)
      this._sdfDate = new SimpleDateFormat(sDateFormat.substring(iFirstDateSymbol, iLastDateSymbol + 1)); 
    if (iFirstTimeSymbol >= 0)
      this._sdfTime = new SimpleDateFormat(sDateFormat.substring(iFirstTimeSymbol, iLastTimeSymbol + 1)); 
    this._sdf = new SimpleDateFormat(sDateFormat);
    this._sdfTimestamp = new SimpleDateFormat(sDateFormat);
    _map.put(sLanguage, this);
  }








  
  public static DU getInstance(String sLanguage, String sDateFormat) {
    DU du = _map.get(sLanguage);
    if (du == null || !du.getDateFormat().equals(sDateFormat))
      du = new DU(sLanguage, sDateFormat); 
    return du;
  }






  
  public String fromDate(Date date) {
    String s = "";
    if (date != null)
      s = this._sdf.format(date); 
    return s;
  }









  
  public Date toDate(String s) throws ParseException {
    Date date = null;
    if (s.length() > 0)
      date = (Date) this._sdf.parse(s);
    return date;
  }






  
  public String fromSqlDate(Date date) {
    String s = "";
    if (date != null && this._sdfDate != null)
      s = this._sdfDate.format(date); 
    return s;
  }






  
  public String fromSqlTime(Time time) {
    String s = "";
    if (time != null && this._sdfTime != null)
      s = this._sdfTime.format(time); 
    return s;
  }








  
  public String fromSqlTimestamp(Timestamp ts) {
    String s = "";
    if (ts != null && this._sdfTimestamp != null)
      s = this._sdfTimestamp.format(ts); 
    return s;
  }






  
  public Date toDate(GregorianCalendar gc) {
    Date date = null;
    if (gc != null)
      date = (Date) gc.getTime();
    return date;
  }






  
  public Date toDate(XMLGregorianCalendar xgc) {
    Date date = null;
    if (xgc != null)
      date = toDate(xgc.toGregorianCalendar()); 
    return date;
  }






  
  public String fromGregorianCalendar(GregorianCalendar gc) {
    String s = "";
    if (gc != null)
      s = this._sdf.format(gc.getTime()); 
    return s;
  }










  
  public GregorianCalendar toGregorianCalendar(String s) throws ParseException {
    GregorianCalendar gc = null;
    Date date = toDate(s);
    if (date != null) {
      
      gc = new GregorianCalendar();
      gc.setTime(date);
    } 
    return gc;
  }






  
  public GregorianCalendar toGregorianCalendar(Date date) {
    GregorianCalendar gc = null;
    if (date != null) {
      
      gc = new GregorianCalendar();
      gc.setTime(date);
    } 
    return gc;
  }








  
  public GregorianCalendar toGregorianCalendar(XMLGregorianCalendar xgc) {
    GregorianCalendar gc = null;
    if (xgc != null)
      gc = xgc.toGregorianCalendar(); 
    return gc;
  }






  
  public String fromXmlGregorianCalendar(XMLGregorianCalendar xgc) {
    String s = "";
    if (xgc != null)
      s = this._sdf.format(xgc.toGregorianCalendar().getTime()); 
    return s;
  }










  
  public XMLGregorianCalendar toXmlGregorianCalendar(String s) throws ParseException {
    XMLGregorianCalendar xgc = null;
    GregorianCalendar gc = toGregorianCalendar(s);
    if (gc != null)
      
      try { xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc); }
      catch (DatatypeConfigurationException dtce) { System.err.println(dtce.getClass().getName() + ": " + dtce.getMessage()); }
       
    return xgc;
  }






  
  public XMLGregorianCalendar toXmlGregorianCalendar(Date date) {
    XMLGregorianCalendar xgc = null;
    if (date != null) {
      
      GregorianCalendar gc = new GregorianCalendar();
      gc.setTime(date); 
      try { xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc); }
      catch (DatatypeConfigurationException dtce) { System.err.println(dtce.getClass().getName() + ": " + dtce.getMessage()); }
    
    }  return xgc;
  }








  
  public XMLGregorianCalendar toXmlGregorianCalendar(GregorianCalendar gc) {
    XMLGregorianCalendar xgc = null;
    if (gc != null)
      
      try { xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc); }
      catch (DatatypeConfigurationException dtce) { System.err.println(dtce.getClass().getName() + ": " + dtce.getMessage()); }
       
    return xgc;
  }






  
  public String toXsDate(Date date) {
    if (date.after(dateMAXIMUM_SQL)) {
      
      System.err.println("Converted illegal date " + this._sdf.format(date) + " to " + this._sdf.format(dateMAXIMUM_SQL));
      date = dateMAXIMUM_SQL;
    } 
    if (date.before(dateMINIMUM_SQL)) {
      
      System.err.println("Converted illegal date " + this._sdf.format(date) + " to " + this._sdf.format(dateMINIMUM_SQL));
      date = dateMINIMUM_SQL;
    } 
    String s = _sdfXS_DATE.format(date);
    return s;
  }
  
  public Date fromXsDate(String s) throws ParseException {
    Date d = null;
    Date date = (Date) _sdfXS_DATE.parse(s);
    d = new Date(date.getTime());
    if (d.after(dateMAXIMUM_SQL)) {
      
      System.err.println("Converted illegal date " + this._sdf.format(d) + " to " + this._sdf.format(dateMAXIMUM_SQL));
      d = dateMAXIMUM_SQL;
    } 
    if (d.before(dateMINIMUM_SQL)) {
      
      System.err.println("Converted illegal date " + this._sdf.format(d) + " to " + this._sdf.format(dateMINIMUM_SQL));
      d = dateMINIMUM_SQL;
    } 
    return d;
  }






  
  public String toXsTime(Time time) {
    StringBuilder sbTime = new StringBuilder();
    int iMillis = (int)(time.getTime() % 1000L);
    sbTime.append(_sdfXS_TIME.format(time));
    if (iMillis > 0) {
      
      sbTime.append(".");
      
      String sDecimals = _dfMILLIS.format(iMillis);
      
      sDecimals = sDecimals.replaceAll("0*$", "");
      sbTime.append(sDecimals);
    } 
    sbTime.append("Z");
    return sbTime.toString();
  }









  
  public Time fromXsTime(String s) throws ParseException {
    Time time = null;
    if (s.endsWith("Z"))
      s = s.substring(0, s.length() - 1); 
    int iMillis = 0;
    int iDecimalPoint = s.lastIndexOf('.');
    if (iDecimalPoint >= 0) {
      
      String sDecimals = s.substring(iDecimalPoint + 1);
      s = s.substring(0, iDecimalPoint);
      
      if (sDecimals.length() > 3) {
        sDecimals = sDecimals.substring(0, 3);
      }
      if (sDecimals.length() < 3)
        sDecimals = String.format("%1$-3s", new Object[] { sDecimals }).replace(" ", "0"); 
      iMillis = Integer.parseInt(sDecimals);
    } 
    time = new Time(_sdfXS_TIME.parse(s).getTime() + iMillis);
    return time;
  }






  
  public String toXsDateTime(Timestamp ts) {
    if (ts.after(tsMAXIMUM_SQL)) {
      
      System.err.println("Converted illegal date/time " + this._sdf.format(ts) + " to " + this._sdf.format(tsMAXIMUM_SQL));
      ts = tsMAXIMUM_SQL;
    } 
    if (ts.before(tsMINIMUM_SQL)) {
      
      System.err.println("Converted illegal date/time " + this._sdf.format(ts) + " to " + this._sdf.format(tsMINIMUM_SQL));
      ts = tsMINIMUM_SQL;
    } 
    StringBuilder sbDateTime = new StringBuilder();
    sbDateTime.append(_sdfXS_DATE_TIME.format(ts));
    if (ts.getNanos() > 0) {
      
      sbDateTime.append(".");
      
      String sDecimals = _dfNANOS.format(ts.getNanos());
      
      sDecimals = sDecimals.replaceAll("0*$", "");
      sbDateTime.append(_dfNANOS.format(ts.getNanos()));
    } 
    sbDateTime.append("Z");
    return sbDateTime.toString();
  }


  
  public Timestamp fromXsDateTime(String s) throws ParseException {
    Timestamp ts = null;
    if (s.endsWith("Z"))
      s = s.substring(0, s.length() - 1); 
    int iNanos = 0;
    int iDecimalPoint = s.lastIndexOf('.');
    if (iDecimalPoint >= 0) {
      
      String sDecimals = s.substring(iDecimalPoint + 1);
      s = s.substring(0, iDecimalPoint);
      
      if (sDecimals.length() > 9) {
        sDecimals = sDecimals.substring(0, 9);
      }
      sDecimals = String.format("%1$-9s", new Object[] { sDecimals }).replace(" ", "0");
      iNanos = Integer.parseInt(sDecimals);
    } 
    Date date = (Date) _sdfXS_DATE_TIME.parse(s);
    ts = new Timestamp(date.getTime());
    ts.setNanos(iNanos);
    if (ts.after(tsMAXIMUM_SQL)) {
      
      System.err.println("Converted illegal date/time " + this._sdf.format(ts) + " to " + this._sdf.format(tsMAXIMUM_SQL));
      ts = tsMAXIMUM_SQL;
    } 
    if (ts.before(tsMINIMUM_SQL)) {
      
      System.err.println("Converted illegal date/time " + this._sdf.format(ts) + " to " + this._sdf.format(tsMINIMUM_SQL));
      ts = tsMINIMUM_SQL;
    } 
    return ts;
  }






  
  public String toXsDuration(Duration duration) {
    String s = duration.toString();
    return s;
  }








  
  public Duration fromXsDuration(String s) throws ParseException {
    Duration duration = null; 
    try { duration = DatatypeFactory.newInstance().newDuration(s); }
    catch (DatatypeConfigurationException dtce) { System.err.println(dtce.getClass().getName() + ": " + dtce.getMessage()); }
     return duration;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\utils\DU.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */