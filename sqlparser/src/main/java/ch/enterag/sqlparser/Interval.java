package ch.enterag.sqlparser;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;

public class Interval implements Serializable {
   private static final long serialVersionUID = -1705264794060459365L;
   private int _iSign = 1;
   private int _iYears = 0;
   private int _iMonths = 0;
   private int _iDays = 0;
   private int _iHours = 0;
   private int _iMinutes = 0;
   private int _iSeconds = 0;
   private long _lNanoSeconds = 0L;

   public int getSign() {
      return this._iSign;
   }

   public void setSign(int iSign) {
      if (iSign != 1 && iSign != -1) {
         throw new IllegalArgumentException("Sign must be 1 or -1!");
      } else {
         this._iSign = iSign;
      }
   }

   public int getYears() {
      return this._iYears;
   }

   public void setYears(int iYears) {
      if (iYears >= 0) {
         this._iYears = iYears;
         this._iDays = 0;
         this._iMinutes = 0;
         this._iSeconds = 0;
         this._lNanoSeconds = 0L;
      } else {
         throw new IllegalArgumentException("Years value must be non-negative. Use setSign() to change the sign of the interval.");
      }
   }

   public int getMonths() {
      return this._iMonths;
   }

   public void setMonths(int iMonths) {
      if (iMonths >= 0 && iMonths < 12) {
         this._iMonths = iMonths;
         this._iDays = 0;
         this._iMinutes = 0;
         this._iSeconds = 0;
         this._lNanoSeconds = 0L;
      } else {
         throw new IllegalArgumentException("Months value must be non-negative and less than 12. Use setSign() to change the sign of the interval.");
      }
   }

   public int getDays() {
      return this._iDays;
   }

   public void setDays(int iDays) {
      if (iDays >= 0) {
         this._iDays = iDays;
         this._iYears = 0;
         this._iMonths = 0;
      } else {
         throw new IllegalArgumentException("Days value must be non-negative. Use setSign() to change the sign of the interval.");
      }
   }

   public int getHours() {
      return this._iHours;
   }

   public void setHours(int iHours) {
      if (iHours >= 0 && iHours < 24) {
         this._iHours = iHours;
         this._iYears = 0;
         this._iMonths = 0;
      } else {
         throw new IllegalArgumentException("Hours value must be non-negative and less than 24. Use setSign() to change the sign of the interval.");
      }
   }

   public int getMinutes() {
      return this._iMinutes;
   }

   public void setMinutes(int iMinutes) {
      if (iMinutes >= 0 && iMinutes < 60) {
         this._iMinutes = iMinutes;
         this._iYears = 0;
         this._iMonths = 0;
      } else {
         throw new IllegalArgumentException("Minutes value must be non-negative and less than 60. Use setSign() to change the sign of the interval.");
      }
   }

   public int getSeconds() {
      return this._iSeconds;
   }

   public void setSeconds(int iSeconds) {
      if (iSeconds >= 0 && iSeconds < 61) {
         this._iSeconds = iSeconds;
         this._iYears = 0;
         this._iMonths = 0;
      } else {
         throw new IllegalArgumentException("Seconds value must be non-negative and less than 61. Use setSign() to change the sign of the interval.");
      }
   }

   public long getNanoSeconds() {
      return this._lNanoSeconds;
   }

   public void setNanoSeconds(long lNanoSeconds) {
      if (lNanoSeconds >= 0L && lNanoSeconds < 1000000000L) {
         this._lNanoSeconds = lNanoSeconds;
      } else {
         throw new IllegalArgumentException("Nano seconds must be non-negative and between 0 and 1'000'000'000!");
      }
   }

   public int getMilliSeconds() {
      return (int)(this.getNanoSeconds() / 1000000L);
   }

   public void setMilliseconds(int iMilliseconds) {
      long lNanoSeconds = 1000000L * (long)iMilliseconds;
      this.setNanoSeconds(lNanoSeconds);
   }

   public boolean equals(Object o) {
      boolean bEquals = false;
      if (o instanceof Interval) {
         Interval iv = (Interval)o;
         bEquals = true;
         if (bEquals && this.getSign() != iv.getSign()) {
            bEquals = false;
         }

         if (bEquals && this.getYears() != iv.getYears()) {
            bEquals = false;
         }

         if (bEquals && this.getMonths() != iv.getMonths()) {
            bEquals = false;
         }

         if (bEquals && this.getDays() != iv.getDays()) {
            bEquals = false;
         }

         if (bEquals && this.getHours() != iv.getHours()) {
            bEquals = false;
         }

         if (bEquals && this.getMinutes() != iv.getMinutes()) {
            bEquals = false;
         }

         if (bEquals && this.getSeconds() != iv.getSeconds()) {
            bEquals = false;
         }

         if (bEquals && this.getNanoSeconds() != iv.getNanoSeconds()) {
            bEquals = false;
         }
      }

      return bEquals;
   }

   public String toString() {
      return SqlLiterals.formatIntervalLiteral(this);
   }

   public Interval(int iSign, int iYears, int iMonths) {
      this.setSign(iSign);
      this.setYears(iYears);
      this.setMonths(iMonths);
   }

   public Interval(int iSign, int iDays, int iHours, int iMinutes, int iSeconds, long lNanoSeconds) {
      this.setSign(iSign);
      this.setDays(iDays);
      this.setHours(iHours);
      this.setMinutes(iMinutes);
      this.setSeconds(iSeconds);
      this.setNanoSeconds(lNanoSeconds);
   }

   public static Interval between(Calendar cal1, Calendar cal2) {
      Interval interval = new Interval(1, 0, 0, 0, 0, 0L);
      int iSign = 1;
      if (cal1.after(cal2)) {
         iSign = -1;
         Calendar cal = cal2;
         cal2 = cal1;
         cal1 = cal;
      }

      int iYears = cal2.get(1) - cal1.get(1);
      int iMonths = cal2.get(2) - cal1.get(2);
      int iDays = cal2.get(5) - cal1.get(5);
      int iHours = cal2.get(11) - cal1.get(11);
      int iMinutes = cal2.get(12) - cal1.get(12);
      int iSeconds = cal2.get(13) - cal1.get(13);
      int iMillis = cal2.get(14) - cal1.get(14);
      boolean bDayToTime = iDays != 0 || iHours != 0 || iMinutes != 0 || iSeconds != 0 || iMillis != 0;
      boolean bYearToMonth = iYears != 0 || iMonths != 0;
      if (bDayToTime && bYearToMonth) {
         long l = cal2.getTimeInMillis() - cal1.getTimeInMillis();
         iMillis = (int)(l % 1000L);
         l /= 1000L;
         iSeconds = (int)(l % 60L);
         l /= 60L;
         iMinutes = (int)(l % 60L);
         l /= 60L;
         iHours = (int)(l % 24L);
         l /= 24L;
         iDays = (int)l;
         bYearToMonth = false;
      }

      if (bYearToMonth) {
         if (iMonths < 0) {
            --iYears;
            iMonths += 12;
         }

         interval = new Interval(iSign, iYears, iMonths);
      } else if (bDayToTime) {
         if (iMillis < 0) {
            --iSeconds;
            iMillis += 1000;
         }

         if (iSeconds < 0) {
            --iMinutes;
            iSeconds += 60;
         }

         if (iMinutes < 0) {
            --iHours;
            iMinutes += 60;
         }

         if (iHours < 0) {
            --iDays;
            iHours += 24;
         }

         interval = new Interval(iSign, iDays, iHours, iMinutes, iSeconds, 0L);
         interval.setMilliseconds(iMillis);
      }

      return interval;
   }

   public Calendar addTo(Calendar cal) {
      Calendar calResult = new GregorianCalendar();
      calResult.setTime(cal.getTime());
      calResult.add(1, this.getSign() * this.getYears());
      calResult.add(2, this.getSign() * this.getMonths());
      calResult.add(5, this.getSign() * this.getDays());
      calResult.add(11, this.getSign() * this.getHours());
      calResult.add(12, this.getSign() * this.getMinutes());
      calResult.add(13, this.getSign() * this.getSeconds());
      calResult.add(14, this.getSign() * this.getMilliSeconds());
      return calResult;
   }

   public Duration toDuration() {
      Duration duration = null;

      try {
         DatatypeFactory df = DatatypeFactory.newInstance();
         if (this.getDays() == 0 && this.getMinutes() == 0 && this.getSeconds() == 0 && this.getMilliSeconds() == 0) {
            duration = df.newDurationYearMonth(this.getSign() > 0, this.getYears(), this.getMonths());
         } else {
            long lMilliSeconds = (long)this.getDays();
            lMilliSeconds = 24L * lMilliSeconds + (long)this.getHours();
            lMilliSeconds = 60L * lMilliSeconds + (long)this.getMinutes();
            lMilliSeconds = 60L * lMilliSeconds + (long)this.getSeconds();
            lMilliSeconds = 1000L * lMilliSeconds + (long)this.getMilliSeconds();
            lMilliSeconds = (long)this.getSign() * lMilliSeconds;
            duration = df.newDurationDayTime(lMilliSeconds);
         }
      } catch (DatatypeConfigurationException var5) {
      }

      return duration;
   }

   public static Interval fromDuration(Duration duration) {
      Interval iv = null;
      int iSign = duration.getSign();
      iSign = iSign == 0 ? 1 : iSign;
      if (duration.getDays() == 0 && duration.getMinutes() == 0 && duration.getSeconds() == 0) {
         iv = new Interval(iSign, duration.getYears(), duration.getMonths());
      } else {
         long lMilliSeconds = duration.getTimeInMillis(new Date(0L));
         if (lMilliSeconds < 0L) {
            lMilliSeconds = -lMilliSeconds;
         }

         long lMillis = 86400000L;
         int iDays = (int)(lMilliSeconds / lMillis);
         lMilliSeconds %= lMillis;
         lMillis = 3600000L;
         int iHours = (int)(lMilliSeconds / lMillis);
         lMilliSeconds %= lMillis;
         lMillis = 60000L;
         int iMinutes = (int)(lMilliSeconds / lMillis);
         lMilliSeconds %= lMillis;
         lMillis = 1000L;
         int iSeconds = (int)(lMilliSeconds / lMillis);
         lMilliSeconds %= lMillis;
         long lNanoSeconds = 1000000L * lMilliSeconds;
         iv = new Interval(iSign, iDays, iHours, iMinutes, iSeconds, lNanoSeconds);
      }

      return iv;
   }
}
