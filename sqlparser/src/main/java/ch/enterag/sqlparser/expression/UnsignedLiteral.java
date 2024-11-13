package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.Interval;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.SqlLiterals;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.datatype.PredefinedType;
import ch.enterag.sqlparser.datatype.enums.IntervalField;
import ch.enterag.sqlparser.ddl.enums.WithOrWithoutTimeZone;
import ch.enterag.sqlparser.expression.enums.BooleanLiteral;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;

public class UnsignedLiteral extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(UnsignedLiteral.class.getName());
   private UnsignedLiteral.UlVisitor _visitor = new UnsignedLiteral.UlVisitor();
   private Double _d = null;
   private BigDecimal _bd = null;
   private String _sCharacterString = null;
   private String _sNationalCharacterString = null;
   private String _sBitString = null;
   private byte[] _buf = null;
   private Date _date = null;
   private Time _time = null;
   private Timestamp _ts = null;
   private Interval _interval = null;
   private BooleanLiteral _bl = null;

   private UnsignedLiteral.UlVisitor getVisitor() {
      return this._visitor;
   }

   public Double getApproximate() {
      return this._d;
   }

   public void setApproximate(Double d) {
      this._d = d;
   }

   public BigDecimal getExact() {
      return this._bd;
   }

   public void setExact(BigDecimal bd) {
      this._bd = bd;
   }

   public String getCharacterString() {
      return this._sCharacterString;
   }

   public void setCharacterString(String sCharacterStringLiteral) {
      this._sCharacterString = sCharacterStringLiteral;
   }

   public String getNationalCharacterString() {
      return this._sNationalCharacterString;
   }

   public void setNationalCharacterString(String sNationalCharacterString) {
      this._sNationalCharacterString = sNationalCharacterString;
   }

   public String getBitString() {
      return this._sBitString;
   }

   public void setBitString(String sBitString) {
      this._sBitString = sBitString;
   }

   public byte[] getBytes() {
      return this._buf;
   }

   public void setBytes(byte[] buf) {
      this._buf = buf;
   }

   public Date getDate() {
      return this._date;
   }

   public void setDate(Date date) {
      this._date = date;
   }

   public Time getTime() {
      return this._time;
   }

   public void setTime(Time time) {
      this._time = time;
   }

   public Timestamp getTimestamp() {
      return this._ts;
   }

   public void setTimestamp(Timestamp ts) {
      this._ts = ts;
   }

   public Interval getInterval() {
      return this._interval;
   }

   public void setInterval(Interval interval) {
      this._interval = interval;
   }

   public BooleanLiteral getBoolean() {
      return this._bl;
   }

   public void setBooleanLiteral(BooleanLiteral bl) {
      this._bl = bl;
   }

   public String format() {
      String sFormatted = null;
      if (this.getApproximate() != null) {
         sFormatted = SqlLiterals.formatApproximateLiteral(this.getApproximate());
      } else if (this.getExact() != null) {
         sFormatted = SqlLiterals.formatExactLiteral(this.getExact());
      } else if (this.getCharacterString() != null) {
         sFormatted = SqlLiterals.formatStringLiteral(this.getCharacterString());
      } else if (this.getNationalCharacterString() != null) {
         sFormatted = SqlLiterals.formatNationalStringLiteral(this.getNationalCharacterString());
      } else if (this.getBitString() != null) {
         sFormatted = SqlLiterals.formatBitStringLiteral(this.getBitString());
      } else if (this.getBytes() != null) {
         sFormatted = SqlLiterals.formatBytesLiteral(this.getBytes());
      } else if (this.getDate() != null) {
         sFormatted = SqlLiterals.formatDateLiteral(this.getDate());
      } else if (this.getTime() != null) {
         sFormatted = SqlLiterals.formatTimeLiteral(this.getTime());
      } else if (this.getTimestamp() != null) {
         sFormatted = SqlLiterals.formatTimestampLiteral(this.getTimestamp());
      } else if (this.getInterval() != null) {
         sFormatted = SqlLiterals.formatIntervalLiteral(this.getInterval());
      } else if (this.getBoolean() != null) {
         sFormatted = SqlLiterals.formatBooleanLiteral(this.getBoolean());
      }

      return sFormatted;
   }

   public Object evaluate() {
      Object oValue = null;
      if (this.getApproximate() != null) {
         oValue = this.getApproximate();
      } else if (this.getExact() != null) {
         oValue = this.getExact();
      } else if (this.getCharacterString() != null) {
         oValue = this.getCharacterString();
      } else if (this.getNationalCharacterString() != null) {
         oValue = this.getNationalCharacterString();
      } else if (this.getBitString() != null) {
         int iLength = (this.getBitString().length() + 7) / 8;
         byte[] bufBinary = new byte[iLength];
         int iIndex = 0;

         for(int iByte = 0; iByte < iLength; ++iByte) {
            int i = 0;

            for(int iBit = 0; iIndex < this.getBitString().length() && iBit < 8; ++iBit) {
               i >>>= 0;
               if (this.getBitString().charAt(iIndex) != '0') {
                  i &= 128;
               }
            }

            if (i >= 128) {
               i -= 256;
            }

            bufBinary[iByte] = (byte)i;
         }

         oValue = bufBinary;
      } else if (this.getBytes() != null) {
         oValue = this.getBytes();
      } else if (this.getDate() != null) {
         oValue = this.getDate();
      } else if (this.getTime() != null) {
         oValue = this.getTime();
      } else if (this.getTimestamp() != null) {
         oValue = this.getTimestamp();
      } else if (this.getInterval() != null) {
         oValue = this.getInterval();
      } else if (this.getBoolean() != null) {
         oValue = this.getBoolean();
      }

      return oValue;
   }

   public DataType getDataType() {
      PredefinedType pt = this.getSqlFactory().newPredefinedType();
      int iSecondsDecimals = -1;
      long lMillis = -1L;
      if (this.getApproximate() != null) {
         pt.initDoubleType();
      } else {
         int i;
         if (this.getExact() != null) {
            BigDecimal bd = this.getExact();
            i = bd.scale();
            int iPrecision = bd.toPlainString().length();
            if (i == 0 && bd.compareTo(BigDecimal.valueOf(32767L)) <= 0) {
               pt.initSmallIntType();
            } else if (i == 0 && bd.compareTo(BigDecimal.valueOf(2147483647L)) <= 0) {
               pt.initIntegerType();
            } else if (i == 0 && bd.compareTo(BigDecimal.valueOf(Long.MAX_VALUE)) <= 0) {
               pt.initBigIntType();
            } else {
               pt.initDecimalType(iPrecision, i);
            }
         } else if (this.getCharacterString() != null) {
            pt.initVarCharType(this.getCharacterString().length());
         } else if (this.getNationalCharacterString() != null) {
            pt.initNVarCharType(this.getNationalCharacterString().length());
         } else if (this.getBitString() != null) {
            pt.initBinaryType((this.getBitString().length() + 7) / 8);
         } else if (this.getBytes() != null) {
            pt.initBinaryType(this.getBytes().length);
         } else if (this.getDate() != null) {
            pt.initDateType();
         } else {
            int iNanos;
            if (this.getTime() != null) {
               lMillis = this.getTime().getTime();
               iSecondsDecimals = 3;

               for(iNanos = 10; iNanos < 1000; iNanos = 10 * iNanos) {
                  if (lMillis % (long)iNanos == 0L) {
                     --iSecondsDecimals;
                  }
               }

               pt.initTimeType(iSecondsDecimals, (WithOrWithoutTimeZone)null);
            } else if (this.getTimestamp() != null) {
               iNanos = this.getTimestamp().getNanos();
               if (iNanos != 0) {
                  iSecondsDecimals = 9;

                  for(i = 10; i < 1000000000; i = 10 * i) {
                     if (iNanos % i == 0) {
                        --iSecondsDecimals;
                     }
                  }
               } else {
                  lMillis = this.getTimestamp().getTime();
                  iSecondsDecimals = 3;

                  for(i = 10; i < 1000; i = 10 * i) {
                     if (lMillis % (long)i == 0L) {
                        --iSecondsDecimals;
                     }
                  }
               }

               pt.initTimestampType(iSecondsDecimals, (WithOrWithoutTimeZone)null);
            } else if (this.getInterval() != null) {
               Interval iv = this.getInterval();
               IntervalField ifStart = null;
               IntervalField ifEnd = null;
               int iPrecision = 0;
               if (iv.getYears() != 0) {
                  ifStart = IntervalField.YEAR;
                  ifEnd = ifStart;
                  iPrecision = String.valueOf(iv.getYears()).length();
                  iSecondsDecimals = 0;
                  if (iv.getMonths() != 0) {
                     ifEnd = IntervalField.MONTH;
                  }
               } else if (iv.getDays() != 0) {
                  ifStart = IntervalField.DAY;
                  ifEnd = ifStart;
                  if (iv.getHours() != 0) {
                     ifEnd = IntervalField.HOUR;
                  }

                  if (iv.getMinutes() != 0) {
                     ifEnd = IntervalField.MINUTE;
                  }

                  if (iv.getSeconds() != 0 || iv.getNanoSeconds() != 0L) {
                     ifEnd = IntervalField.SECOND;
                  }
               } else if (iv.getHours() != 0) {
                  ifStart = IntervalField.HOUR;
                  ifEnd = ifStart;
                  if (iv.getMinutes() != 0) {
                     ifEnd = IntervalField.MINUTE;
                  }

                  if (iv.getSeconds() != 0 || iv.getNanoSeconds() != 0L) {
                     ifEnd = IntervalField.SECOND;
                  }
               } else if (iv.getMinutes() != 0) {
                  ifStart = IntervalField.MINUTE;
                  ifEnd = ifStart;
                  if (iv.getSeconds() != 0 || iv.getNanoSeconds() != 0L) {
                     ifEnd = IntervalField.SECOND;
                  }
               } else {
                  ifStart = IntervalField.SECOND;
                  ifEnd = ifStart;
                  long lNanos = iv.getNanoSeconds();
                  iSecondsDecimals = 9;

                  for(int i = 10; i < 1000000000; i = 10 * i) {
                     if (lNanos % (long)i == 0L) {
                        --iSecondsDecimals;
                     }
                  }
               }

               pt.initIntervalType(ifStart, ifEnd, iPrecision, iSecondsDecimals);
            } else if (this.getBoolean() != null) {
               pt.initBooleanType();
            }
         }
      }

      DataType dt = this.getSqlFactory().newDataType();
      dt.initPredefinedDataType(pt);
      return dt;
   }

   public void parse(SqlParser.UnsignedLiteralContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().unsignedLiteral());
   }

   public void initCharacterString(String sCharacterString) {
      _il.enter(new Object[]{sCharacterString});
      this.setCharacterString(sCharacterString);
      _il.exit();
   }

   public void initNationalCharacterString(String sNationalCharacterString) {
      _il.enter(new Object[]{sNationalCharacterString});
      this.setNationalCharacterString(sNationalCharacterString);
      _il.exit();
   }

   public void initBitString(String sBitString) {
      _il.enter(new Object[]{sBitString});
      this.setBitString(sBitString);
      _il.exit();
   }

   public void initBytes(byte[] bufBytes) {
      _il.enter(new Object[]{bufBytes});
      this.setBytes(bufBytes);
      _il.exit();
   }

   public void initBigDecimal(BigDecimal bdExact) {
      _il.enter(new Object[]{bdExact});
      this.setExact(bdExact);
      _il.exit();
   }

   public void initLong(Long lExact) {
      _il.enter(new Object[]{String.valueOf(lExact)});
      BigDecimal bd = BigDecimal.valueOf(lExact);
      this.setExact(bd);
      _il.exit();
   }

   public void initInteger(int iExact) {
      _il.enter(new Object[]{String.valueOf(iExact)});
      BigDecimal bd = BigDecimal.valueOf((long)iExact);
      this.setExact(bd);
      _il.exit();
   }

   public void initDouble(Double dApproximate) {
      _il.enter(new Object[]{dApproximate});
      this.setApproximate(dApproximate);
      _il.exit();
   }

   public void initBoolean(BooleanLiteral bl) {
      _il.enter(new Object[]{bl});
      this.setBooleanLiteral(bl);
      _il.exit();
   }

   public void initDate(Date date) {
      _il.enter(new Object[]{date});
      this.setDate(date);
      _il.exit();
   }

   public void initTime(Time time) {
      _il.enter(new Object[]{time});
      this.setTime(time);
      _il.exit();
   }

   public void initTimestamp(Timestamp ts) {
      _il.enter(new Object[]{ts});
      this.setTimestamp(ts);
      _il.exit();
   }

   public void initInterval(Interval iv) {
      _il.enter(new Object[]{iv});
      this.setInterval(iv);
      _il.exit();
   }

   public void initialize(Double dApproximate, BigDecimal bdExact, String sCharacterString, String sNationalCharacterString, String sBitString, byte[] bufBytes, Date date, Time time, Timestamp ts, Interval iv, BooleanLiteral bl) {
      _il.enter(new Object[]{dApproximate, bdExact, sCharacterString, sNationalCharacterString, sBitString, bufBytes, date, time, ts, bl});
      this.setApproximate(dApproximate);
      this.setExact(bdExact);
      this.setCharacterString(sCharacterString);
      this.setNationalCharacterString(sNationalCharacterString);
      this.setBitString(sBitString);
      this.setBytes(bufBytes);
      this.setDate(date);
      this.setTime(time);
      this.setTimestamp(ts);
      this.setInterval(iv);
      this.setBooleanLiteral(bl);
      _il.exit();
   }

   public UnsignedLiteral(SqlFactory sf) {
      super(sf);
   }

   private class UlVisitor extends EnhancedSqlBaseVisitor<UnsignedLiteral> {
      private UlVisitor() {
      }

      public UnsignedLiteral visitGeneralLiteral(SqlParser.GeneralLiteralContext ctx) {
         try {
            if (ctx.CHARACTER_STRING_LITERAL() != null) {
               UnsignedLiteral.this.setCharacterString(SqlLiterals.parseStringLiteral(ctx.CHARACTER_STRING_LITERAL().getText()));
            } else if (ctx.NATIONAL_CHARACTER_STRING_LITERAL() != null) {
               UnsignedLiteral.this.setNationalCharacterString(SqlLiterals.parseNationalStringLiteral(ctx.NATIONAL_CHARACTER_STRING_LITERAL().getText()));
            } else if (ctx.BIT_STRING_LITERAL() != null) {
               UnsignedLiteral.this.setBitString(SqlLiterals.parseBitStringLiteral(ctx.BIT_STRING_LITERAL().getText()));
            } else if (ctx.BYTE_STRING_LITERAL() != null) {
               UnsignedLiteral.this.setBytes(SqlLiterals.parseBytesLiteral(ctx.BYTE_STRING_LITERAL().getText()));
            } else if (ctx.DATE_LITERAL() != null) {
               UnsignedLiteral.this.setDate(SqlLiterals.parseDateLiteral(ctx.DATE_LITERAL().getText()));
            } else if (ctx.TIME_LITERAL() != null) {
               UnsignedLiteral.this.setTime(SqlLiterals.parseTimeLiteral(ctx.TIME_LITERAL().getText()));
            } else if (ctx.TIMESTAMP_LITERAL() != null) {
               UnsignedLiteral.this.setTimestamp(SqlLiterals.parseTimestampLiteral(ctx.TIMESTAMP_LITERAL().getText()));
            } else if (ctx.intervalLiteral() != null) {
               UnsignedLiteral.this.setInterval(SqlLiterals.parseInterval(ctx.intervalLiteral().getText()));
            } else if (ctx.BOOLEAN_LITERAL() != null) {
               UnsignedLiteral.this.setBooleanLiteral(SqlLiterals.parseBooleanLiteral(ctx.BOOLEAN_LITERAL().getText()));
            }
         } catch (ParseException var3) {
            throw new IllegalArgumentException("Error visiting general literal!", var3);
         }

         return UnsignedLiteral.this;
      }

      public UnsignedLiteral visitUnsignedNumericLiteral(SqlParser.UnsignedNumericLiteralContext ctx) {
         if (ctx.UNSIGNED_APPROXIMATE() != null) {
            try {
               UnsignedLiteral.this.setApproximate(SqlLiterals.parseApproximateLiteral(ctx.getText()));
            } catch (ParseException var3) {
               throw new IllegalArgumentException("Error visiting approximate numeric literal!", var3);
            }
         }

         return (UnsignedLiteral)this.visitChildren(ctx);
      }

      public UnsignedLiteral visitExactNumericLiteral(SqlParser.ExactNumericLiteralContext ctx) {
         try {
            UnsignedLiteral.this.setExact(SqlLiterals.parseExactLiteral(ctx.getText()));
         } catch (ParseException var3) {
            throw new IllegalArgumentException("Error visiting exact numeric literal!", var3);
         }

         return UnsignedLiteral.this;
      }

      // $FF: synthetic method
      UlVisitor(Object x1) {
         this();
      }
   }
}
