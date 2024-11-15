package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.Interval;
import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.SqlStatement;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.datatype.IntervalQualifier;
import ch.enterag.sqlparser.expression.enums.AdditiveOperator;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DatetimeValueExpression extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(DatetimeValueExpression.class.getName());
   private DatetimeValueExpression.DveVisitor _visitor = new DatetimeValueExpression.DveVisitor();
   private boolean _bAddition = false;
   private boolean _bSubtraction = false;
   private DatetimeValueExpression _dve = null;
   private IntervalValueExpression _ive = null;
   private ValueExpressionPrimary _vep = null;
   public DatetimeValueFunction _dvf = null;
   private boolean _bLocalTimeZone = false;
   private ValueExpressionPrimary _vepInterval = null;
   private IntervalQualifier _iq = null;
   private IntervalValueExpression _iveAbsArgument = null;

   private DatetimeValueExpression.DveVisitor getVisitor() {
      return this._visitor;
   }

   public boolean isAddition() {
      return this._bAddition;
   }

   public void setAddition(boolean bAddition) {
      this._bAddition = bAddition;
   }

   public boolean isSubtraction() {
      return this._bSubtraction;
   }

   public void setSubtraction(boolean bSubtraction) {
      this._bSubtraction = bSubtraction;
   }

   public DatetimeValueExpression getDatetimeOperand() {
      return this._dve;
   }

   public void setDatetimeOperand(DatetimeValueExpression dve) {
      this._dve = dve;
   }

   public IntervalValueExpression getIntervalOperand() {
      return this._ive;
   }

   public void setIntervalOperand(IntervalValueExpression ive) {
      this._ive = ive;
   }

   public ValueExpressionPrimary getValueExpressionPrimary() {
      return this._vep;
   }

   public void setValueExpressionPrimary(ValueExpressionPrimary vep) {
      this._vep = vep;
   }

   public DatetimeValueFunction getDatetimeValueFunction() {
      return this._dvf;
   }

   public void setDatetimeValueFunction(DatetimeValueFunction dvf) {
      this._dvf = dvf;
   }

   public boolean isLocalTimeZone() {
      return this._bLocalTimeZone;
   }

   public void setLocalTimeZone(boolean bLocalTimeZone) {
      this._bLocalTimeZone = bLocalTimeZone;
   }

   public ValueExpressionPrimary getInterval() {
      return this._vepInterval;
   }

   public void setInterval(ValueExpressionPrimary vepInterval) {
      this._vepInterval = vepInterval;
   }

   public IntervalQualifier getIntervalQualifier() {
      return this._iq;
   }

   public void setIntervalQualifier(IntervalQualifier iq) {
      this._iq = iq;
   }

   public IntervalValueExpression getAbsArgument() {
      return this._iveAbsArgument;
   }

   public void setAbsArgument(IntervalValueExpression iveAbsArgument) {
      this._iveAbsArgument = iveAbsArgument;
   }

   public String format() {
      String sExpression = null;
      if (this.isAddition()) {
         sExpression = this.getDatetimeOperand().format() + " " + AdditiveOperator.PLUS_SIGN.getKeywords() + " " + this.getIntervalOperand().format();
      } else if (this.isSubtraction()) {
         sExpression = this.getDatetimeOperand().format() + " " + AdditiveOperator.MINUS_SIGN.getKeywords() + " " + this.getIntervalOperand().format();
      } else {
         if (this.getValueExpressionPrimary() != null) {
            sExpression = this.getValueExpressionPrimary().format();
         } else if (this.getDatetimeValueFunction() != null) {
            sExpression = this.getDatetimeValueFunction().format();
         }

         if (this.isLocalTimeZone()) {
            sExpression = sExpression + " " + K.AT.getKeyword() + " " + K.LOCAL.getKeyword();
         }

         if (this.getInterval() != null || this.getAbsArgument() != null) {
            sExpression = sExpression + " " + K.AT.getKeyword() + " " + K.TIME.getKeyword() + " " + K.ZONE.getKeyword() + " ";
            if (this.getInterval() != null) {
               sExpression = sExpression + this.getInterval().format();
               if (this.getIntervalQualifier() != null) {
                  sExpression = sExpression + " " + this.getIntervalQualifier().format();
               }
            } else if (this.getAbsArgument() != null) {
               sExpression = sExpression + K.ABS.getKeyword() + "(" + this.getAbsArgument().format() + ")";
            }
         }
      }

      return sExpression;
   }

   public DataType getDataType(SqlStatement ss) {
      DataType dt = null;
      if (!this.isAddition() && !this.isSubtraction()) {
         if (this.getValueExpressionPrimary() != null) {
            dt = this.getValueExpressionPrimary().getDataType(ss);
         } else if (this.getDatetimeValueFunction() != null) {
            dt = this.getDatetimeValueFunction().getDataType(ss);
         }
      } else {
         dt = this.getDatetimeOperand().getDataType(ss);
      }

      return dt;
   }

   private Object evaluate(Object oDatetimeOperand, Interval ivIntervalOperand, Object oDatetimeValue, Object oValuePrimary) {
      Object oValue = null;
      if (!this.isAddition() && !this.isSubtraction()) {
         if (this.getDatetimeValueFunction() != null) {
            oValue = oDatetimeValue;
         } else if (this.getValueExpressionPrimary() != null) {
            oValue = oValuePrimary;
         }
      } else if (oDatetimeOperand != null && ivIntervalOperand != null) {
         Calendar cal = new GregorianCalendar();
         if (oDatetimeOperand instanceof Date) {
            cal.setTime((Date)oDatetimeOperand);
         } else if (oDatetimeOperand instanceof Time) {
            cal.setTime((Time)oDatetimeOperand);
         } else {
            if (!(oDatetimeOperand instanceof Timestamp)) {
               throw new IllegalArgumentException("Invalid type of datetime operand!");
            }

            cal.setTime((Timestamp)oDatetimeOperand);
         }

         if (this.isSubtraction()) {
            ivIntervalOperand.setSign(-ivIntervalOperand.getSign());
         }

         cal = ivIntervalOperand.addTo(cal);
         if (oDatetimeOperand instanceof Date) {
            oValue = new Date(cal.getTimeInMillis());
         } else if (oDatetimeOperand instanceof Time) {
            oValue = new Time(cal.getTimeInMillis());
         } else if (oDatetimeOperand instanceof Timestamp) {
            Timestamp ts = new Timestamp(cal.getTimeInMillis());
            ts.setNanos(((Timestamp)oDatetimeOperand).getNanos());
            oValue = ts;
         }
      }

      return oValue;
   }

   public Object evaluate(SqlStatement ss, boolean bAggregated) {
      Object oDatetimeOperand = null;
      if (this.getDatetimeOperand() != null) {
         oDatetimeOperand = this.getDatetimeOperand().evaluate(ss, bAggregated);
      }

      Interval ivIntervalOperand = null;
      if (this.getIntervalOperand() != null) {
         ivIntervalOperand = this.getIntervalOperand().evaluate(ss, bAggregated);
      }

      Object oDatetimeValue = null;
      if (this.getDatetimeValueFunction() != null) {
         oDatetimeValue = this.getDatetimeValueFunction().evaluate(ss);
      }

      Object oValuePrimary = null;
      if (this.getValueExpressionPrimary() != null) {
         oValuePrimary = this.getValueExpressionPrimary().evaluate(ss, bAggregated);
      }

      Object oValue = this.evaluate(oDatetimeOperand, ivIntervalOperand, oDatetimeValue, oValuePrimary);
      return oValue;
   }

   public Object resetAggregates(SqlStatement ss) {
      Object oDatetimeOperand = null;
      if (this.getDatetimeOperand() != null) {
         oDatetimeOperand = this.getDatetimeOperand().resetAggregates(ss);
      }

      Interval ivIntervalOperand = null;
      if (this.getIntervalOperand() != null) {
         ivIntervalOperand = this.getIntervalOperand().resetAggregates(ss);
      }

      Object oDatetimeValue = null;
      if (this.getDatetimeValueFunction() != null) {
         oDatetimeValue = this.getDatetimeValueFunction().evaluate(ss);
      }

      Object oValuePrimary = null;
      if (this.getValueExpressionPrimary() != null) {
         oValuePrimary = this.getValueExpressionPrimary().resetAggregates(ss);
      }

      Object oValue = this.evaluate(oDatetimeOperand, ivIntervalOperand, oDatetimeValue, oValuePrimary);
      return oValue;
   }

   public void parse(SqlParser.DatetimeValueExpressionContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().datetimeValueExpression());
   }

   public void initialize(boolean bAddition, boolean bSubtraction, DatetimeValueExpression dve, IntervalValueExpression ive, ValueExpressionPrimary vep, DatetimeValueFunction dvf, boolean bLocalTimeZone, ValueExpressionPrimary vepInterval, IntervalQualifier iq, IntervalValueExpression iveAbsArgument) {
      _il.enter(new Object[]{String.valueOf(bAddition), String.valueOf(bSubtraction), dve, ive, vep, dvf, String.valueOf(bLocalTimeZone), vepInterval, iq, iveAbsArgument});
      this.setAddition(bAddition);
      this.setSubtraction(bSubtraction);
      this.setDatetimeOperand(dve);
      this.setIntervalOperand(ive);
      this.setDatetimeValueFunction(dvf);
      this.setLocalTimeZone(bLocalTimeZone);
      this.setInterval(vepInterval);
      this.setIntervalQualifier(iq);
      this.setAbsArgument(iveAbsArgument);
      _il.exit();
   }

   public DatetimeValueExpression(SqlFactory sf) {
      super(sf);
   }

   private class DveVisitor extends EnhancedSqlBaseVisitor<DatetimeValueExpression> {
      private DveVisitor() {
      }

      public DatetimeValueExpression visitDatetimeValueExpression(SqlParser.DatetimeValueExpressionContext ctx) {
         if (ctx.datetimeValueExpression() != null && ctx.intervalValueExpression() != null) {
            if (ctx.MINUS_SIGN() != null) {
               DatetimeValueExpression.this.setSubtraction(true);
               DatetimeValueExpression.this.setDatetimeOperand(DatetimeValueExpression.this.getSqlFactory().newDatetimeValueExpression());
               DatetimeValueExpression.this.getDatetimeOperand().parse(ctx.datetimeValueExpression());
               DatetimeValueExpression.this.setIntervalOperand(DatetimeValueExpression.this.getSqlFactory().newIntervalValueExpression());
               DatetimeValueExpression.this.getIntervalOperand().parse(ctx.intervalValueExpression());
            } else if (ctx.PLUS_SIGN() != null) {
               DatetimeValueExpression.this.setAddition(true);
               DatetimeValueExpression.this.setIntervalOperand(DatetimeValueExpression.this.getSqlFactory().newIntervalValueExpression());
               DatetimeValueExpression.this.getIntervalOperand().parse(ctx.intervalValueExpression());
               DatetimeValueExpression.this.setDatetimeOperand(DatetimeValueExpression.this.getSqlFactory().newDatetimeValueExpression());
               DatetimeValueExpression.this.getDatetimeOperand().parse(ctx.datetimeValueExpression());
            }
         } else if (ctx.valueExpressionPrimary() != null) {
            DatetimeValueExpression.this.setValueExpressionPrimary(DatetimeValueExpression.this.getSqlFactory().newValueExpressionPrimary());
            DatetimeValueExpression.this.getValueExpressionPrimary().parse(ctx.valueExpressionPrimary());
         } else if (ctx.datetimeValueFunction() != null) {
            DatetimeValueExpression.this.setDatetimeValueFunction(DatetimeValueExpression.this.getSqlFactory().newDatetimeValueFunction());
            DatetimeValueExpression.this.getDatetimeValueFunction().parse(ctx.datetimeValueFunction());
         }

         return (DatetimeValueExpression)this.visitChildren(ctx);
      }

      public DatetimeValueExpression visitTimeZone(SqlParser.TimeZoneContext ctx) {
         if (ctx.LOCAL() != null) {
            DatetimeValueExpression.this.setLocalTimeZone(true);
         }

         return (DatetimeValueExpression)this.visitChildren(ctx);
      }

      public DatetimeValueExpression visitIntervalPrimary(SqlParser.IntervalPrimaryContext ctx) {
         if (ctx.valueExpressionPrimary() != null) {
            DatetimeValueExpression.this.setInterval(DatetimeValueExpression.this.getSqlFactory().newValueExpressionPrimary());
            DatetimeValueExpression.this.getInterval().parse(ctx.valueExpressionPrimary());
            if (ctx.intervalQualifier() != null) {
               DatetimeValueExpression.this.setIntervalQualifier(DatetimeValueExpression.this.getSqlFactory().newIntervalQualifier());
               DatetimeValueExpression.this.getIntervalQualifier().parse(ctx.intervalQualifier());
            }
         } else if (ctx.ABS() != null && ctx.intervalValueExpression() != null) {
            DatetimeValueExpression.this.setAbsArgument(DatetimeValueExpression.this.getSqlFactory().newIntervalValueExpression());
            DatetimeValueExpression.this.getAbsArgument().parse(ctx.intervalValueExpression());
         }

         return DatetimeValueExpression.this;
      }

      // $FF: synthetic method
      DveVisitor(Object x1) {
         this();
      }
   }
}
