package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.SqlStatement;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.datatype.PredefinedType;
import ch.enterag.sqlparser.ddl.enums.WithOrWithoutTimeZone;
import ch.enterag.sqlparser.expression.enums.DatetimeFunction;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

public class DatetimeValueFunction extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(DatetimeValueFunction.class.getName());
   public static final int iUNDEFINED = -1;
   public static final int iTIME_PRECISION_DEFAULT = 0;
   public static final int iTIMESTAMP_PRECISION_DEFAULT = 6;
   private DatetimeValueFunction.DvfVisitor _visitor = new DatetimeValueFunction.DvfVisitor();
   private DatetimeFunction _df = null;
   private int _iSecondsDecimals = -1;

   private DatetimeValueFunction.DvfVisitor getVisitor() {
      return this._visitor;
   }

   public DatetimeFunction getDatetimeFunction() {
      return this._df;
   }

   public void setDatetimeFunction(DatetimeFunction df) {
      this._df = df;
   }

   public int getSecondsDecimals() {
      return this._iSecondsDecimals;
   }

   public void setSecondsDecimals(int iSecondsDecimals) {
      this._iSecondsDecimals = iSecondsDecimals;
   }

   private String formatSecondsDecimals(int iDefaultDecimals) {
      String sSecondsDecimals = "";
      if (this.getSecondsDecimals() != -1 && this.getSecondsDecimals() != iDefaultDecimals) {
         sSecondsDecimals = sSecondsDecimals + "(" + this.getSecondsDecimals() + ")";
      }

      return sSecondsDecimals;
   }

   public String format() {
      String sFunction = this.getDatetimeFunction().getKeywords();
      if (this.getSecondsDecimals() != -1) {
         if (this.getDatetimeFunction() != DatetimeFunction.CURRENT_TIME && this.getDatetimeFunction() != DatetimeFunction.LOCALTIME) {
            if (this.getDatetimeFunction() == DatetimeFunction.CURRENT_TIMESTAMP || this.getDatetimeFunction() == DatetimeFunction.LOCALTIMESTAMP) {
               sFunction = sFunction + this.formatSecondsDecimals(6);
            }
         } else {
            sFunction = sFunction + this.formatSecondsDecimals(0);
         }
      }

      return sFunction;
   }

   public DataType getDataType(SqlStatement ss) {
      DataType dt = this.getSqlFactory().newDataType();
      PredefinedType pt = this.getSqlFactory().newPredefinedType();
      dt.initPredefinedDataType(pt);
      switch(this.getDatetimeFunction()) {
      case CURRENT_DATE:
         pt.initDateType();
         break;
      case CURRENT_TIME:
      case LOCALTIME:
         pt.initTimeType(-1, (WithOrWithoutTimeZone)null);
         break;
      case CURRENT_TIMESTAMP:
      case LOCALTIMESTAMP:
         pt.initTimestampType(-1, (WithOrWithoutTimeZone)null);
      }

      return dt;
   }

   public Object evaluate(SqlStatement ss) {
      Object oValue = null;
      long lNow = (new GregorianCalendar()).getTime().getTime();
      switch(this.getDatetimeFunction()) {
      case CURRENT_DATE:
         oValue = new Date(lNow);
         break;
      case CURRENT_TIME:
      case LOCALTIME:
         oValue = new Time(lNow % 1000L * 60L * 60L * 24L);
         break;
      case CURRENT_TIMESTAMP:
      case LOCALTIMESTAMP:
         oValue = new Timestamp(lNow);
      }

      return oValue;
   }

   public void parse(SqlParser.DatetimeValueFunctionContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().datetimeValueFunction());
   }

   public void initialize(DatetimeFunction df, int iSecondsDecimals) {
      _il.enter(new Object[]{df, String.valueOf(iSecondsDecimals)});
      this.setDatetimeFunction(df);
      this.setSecondsDecimals(iSecondsDecimals);
      _il.exit();
   }

   public DatetimeValueFunction(SqlFactory sf) {
      super(sf);
   }

   private class DvfVisitor extends EnhancedSqlBaseVisitor<DatetimeValueFunction> {
      private DvfVisitor() {
      }

      public DatetimeValueFunction visitDatetimeValueFunction(SqlParser.DatetimeValueFunctionContext ctx) {
         DatetimeValueFunction.this.setDatetimeFunction(this.getDatetimeFunction(ctx));
         return (DatetimeValueFunction)this.visitChildren(ctx);
      }

      public DatetimeValueFunction visitSecondsDecimals(SqlParser.SecondsDecimalsContext ctx) {
         DatetimeValueFunction.this.setSecondsDecimals(Integer.parseInt(ctx.getText()));
         return DatetimeValueFunction.this;
      }

      // $FF: synthetic method
      DvfVisitor(Object x1) {
         this();
      }
   }
}
