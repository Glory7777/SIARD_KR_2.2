package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.Interval;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.SqlStatement;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.datatype.IntervalQualifier;
import ch.enterag.sqlparser.datatype.enums.PreType;
import ch.enterag.sqlparser.expression.enums.AdditiveOperator;
import ch.enterag.sqlparser.expression.enums.MultiplicativeOperator;
import ch.enterag.sqlparser.expression.enums.NumericFunction;
import ch.enterag.sqlparser.expression.enums.Sign;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;

public class IntervalValueExpression extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(IntervalValueExpression.class.getName());
   private IntervalValueExpression.IveVisitor _visitor = new IntervalValueExpression.IveVisitor();
   private Sign _sign = null;
   private AdditiveOperator _ao = null;
   private MultiplicativeOperator _mo = null;
   private IntervalValueExpression _ive1 = null;
   private IntervalValueExpression _ive2 = null;
   private DatetimeValueExpression _dve1 = null;
   private DatetimeValueExpression _dve2 = null;
   private NumericValueExpression _nveFactor = null;
   private ValueExpressionPrimary _vep = null;
   private IntervalQualifier _iq = null;
   private IntervalValueExpression _iveAbsArgument = null;

   private IntervalValueExpression.IveVisitor getVisitor() {
      return this._visitor;
   }

   public Sign getSign() {
      return this._sign;
   }

   public void setSign(Sign sign) {
      this._sign = sign;
   }

   public AdditiveOperator getAdditiveOperator() {
      return this._ao;
   }

   public void setAdditiveOperator(AdditiveOperator ao) {
      this._ao = ao;
   }

   public MultiplicativeOperator getMultiplicativeOperator() {
      return this._mo;
   }

   public void setMultiplicativeOperator(MultiplicativeOperator mo) {
      this._mo = mo;
   }

   public IntervalValueExpression getFirstOperand() {
      return this._ive1;
   }

   public void setFirstOperand(IntervalValueExpression nve1) {
      this._ive1 = nve1;
   }

   public IntervalValueExpression getSecondOperand() {
      return this._ive2;
   }

   public void setSecondOperand(IntervalValueExpression nve2) {
      this._ive2 = nve2;
   }

   public DatetimeValueExpression getMinuend() {
      return this._dve1;
   }

   public void setMinuend(DatetimeValueExpression dve1) {
      this._dve1 = dve1;
   }

   public DatetimeValueExpression getSubtrahend() {
      return this._dve2;
   }

   public void setSubtrahend(DatetimeValueExpression dve2) {
      this._dve2 = dve2;
   }

   public NumericValueExpression getFactor() {
      return this._nveFactor;
   }

   public void setFactor(NumericValueExpression nveFactor) {
      this._nveFactor = nveFactor;
   }

   public ValueExpressionPrimary getValueExpressionPrimary() {
      return this._vep;
   }

   public void setValueExpressionPrimary(ValueExpressionPrimary vep) {
      this._vep = vep;
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
      String sExpression = "";
      if (this.getValueExpressionPrimary() != null) {
         if (this.getSign() != null) {
            sExpression = this.getSign().getKeywords();
         }

         sExpression = sExpression + this.getValueExpressionPrimary().format();
         if (this.getIntervalQualifier() != null) {
            sExpression = sExpression + " " + this.getIntervalQualifier().format();
         }
      } else if (this.getAbsArgument() != null) {
         if (this.getSign() != null) {
            sExpression = this.getSign().getKeywords();
         }

         sExpression = sExpression + NumericFunction.ABS.getKeywords() + "(" + this.getAbsArgument().format() + ")";
      } else if (this.getFactor() != null && this.getSecondOperand() != null) {
         sExpression = this.getFactor().format() + "*" + this.getSecondOperand().format();
      } else if (this.getFirstOperand() != null && this.getFactor() != null) {
         sExpression = sExpression + this.getFirstOperand().format();
         if (this.getAdditiveOperator() != null) {
            sExpression = sExpression + " " + this.getAdditiveOperator().getKeywords() + " ";
         } else if (this.getMultiplicativeOperator() != null) {
            sExpression = sExpression + this.getMultiplicativeOperator().getKeywords();
         }

         sExpression = sExpression + this.getFactor().format();
      } else if (this.getAdditiveOperator() != null && this.getFirstOperand() != null && this.getSecondOperand() != null) {
         sExpression = this.getFirstOperand().format() + " " + this.getAdditiveOperator().getKeywords() + " " + this.getSecondOperand().format();
      } else if (this.getMinuend() != null && this.getSubtrahend() != null && this.getIntervalQualifier() != null) {
         sExpression = this.getMinuend().format() + " " + AdditiveOperator.MINUS_SIGN.getKeywords() + " " + this.getSubtrahend().format() + " " + this.getIntervalQualifier().format();
      }

      return sExpression;
   }

   public DataType getDataType(SqlStatement ss) {
      DataType dt = null;
      if (this.getValueExpressionPrimary() != null) {
         dt = this.getValueExpressionPrimary().getDataType(ss);
         if (dt != null && dt.getPredefinedType().getType() != PreType.INTERVAL) {
            throw new IllegalArgumentException("Interval expression must be of INTERVAL type!");
         }
      } else if (this.getAbsArgument() != null) {
         dt = this.getAbsArgument().getDataType(ss);
         if (dt != null && dt.getPredefinedType().getType() != PreType.INTERVAL) {
            throw new IllegalArgumentException("Interval expression must be of INTERVAL type!");
         }
      } else {
         if (this.getFactor() != null && this.getSecondOperand() != null) {
            throw new IllegalArgumentException("Multiplication of intervals not supported for evaluation!");
         }

         if (this.getFirstOperand() != null && this.getFactor() != null) {
            throw new IllegalArgumentException("Adding or multiplying an interval and a number not supported for evaluation!");
         }

         if (this.getAdditiveOperator() != null && this.getFirstOperand() != null && this.getSecondOperand() != null) {
            throw new IllegalArgumentException("Addition of intervals not supported for evaluation!");
         }

         if (this.getMinuend() != null && this.getSubtrahend() != null && this.getIntervalQualifier() != null) {
            throw new IllegalArgumentException("Subtraction of intervals not supported for evaluation!");
         }
      }

      return dt;
   }

   private Interval evaluate(Object oValuePrimary, Interval ivAbsArgument) {
      Interval ivValue = null;
      if (this.getValueExpressionPrimary() != null) {
         if (oValuePrimary != null) {
            if (!(oValuePrimary instanceof Interval)) {
               throw new IllegalArgumentException("Cannot cast to interval!");
            }

            ivValue = (Interval)oValuePrimary;
            if (Sign.MINUS_SIGN == this.getSign()) {
               ivValue.setSign(-ivValue.getSign());
            }
         }
      } else if (this.getAbsArgument() != null) {
         ivValue = ivAbsArgument;
         if (ivAbsArgument != null) {
            ivAbsArgument.setSign(1);
            if (Sign.MINUS_SIGN == this.getSign()) {
               ivAbsArgument.setSign(-1);
            }
         }
      } else {
         if (this.getFactor() != null && this.getSecondOperand() != null) {
            throw new IllegalArgumentException("Multiplication of intervals not supported for evaluation!");
         }

         if (this.getFirstOperand() != null && this.getFactor() != null) {
            throw new IllegalArgumentException("Adding or multiplying an interval and a number not supported for evaluation!");
         }

         if (this.getAdditiveOperator() != null && this.getFirstOperand() != null && this.getSecondOperand() != null) {
            throw new IllegalArgumentException("Addition of intervals not supported for evaluation!");
         }

         if (this.getMinuend() != null && this.getSubtrahend() != null && this.getIntervalQualifier() != null) {
            throw new IllegalArgumentException("Subtraction of intervals not supported for evaluation!");
         }
      }

      return ivValue;
   }

   public Interval evaluate(SqlStatement ss, boolean bAggregated) {
      Object oValuePrimary = null;
      if (this.getValueExpressionPrimary() != null) {
         oValuePrimary = this.getValueExpressionPrimary().evaluate(ss, bAggregated);
      }

      Interval ivAbsArgument = null;
      if (this.getAbsArgument() != null) {
         ivAbsArgument = this.getAbsArgument().evaluate(ss, bAggregated);
      }

      Interval ivValue = this.evaluate(oValuePrimary, ivAbsArgument);
      return ivValue;
   }

   public Interval resetAggregates(SqlStatement ss) {
      Object oValuePrimary = null;
      if (this.getValueExpressionPrimary() != null) {
         oValuePrimary = this.getValueExpressionPrimary().resetAggregates(ss);
      }

      Interval ivAbsArgument = null;
      if (this.getAbsArgument() != null) {
         ivAbsArgument = this.getAbsArgument().resetAggregates(ss);
      }

      Interval ivValue = this.evaluate(oValuePrimary, ivAbsArgument);
      return ivValue;
   }

   public void parse(SqlParser.IntervalValueExpressionContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      SqlParser.IntervalValueExpressionContext ctx = null;

      try {
         ctx = this.getParser().intervalValueExpression();
      } catch (Exception var4) {
         this.setParser(newSqlParser2(sSql));
         ctx = this.getParser().intervalValueExpression();
      }

      this.parse(ctx);
   }

   public void initialize(AdditiveOperator ao, MultiplicativeOperator mo, IntervalValueExpression ive1, IntervalValueExpression ive2, DatetimeValueExpression dve1, DatetimeValueExpression dve2, NumericValueExpression nveFactor, Sign sign, ValueExpressionPrimary vep, IntervalQualifier iq, IntervalValueExpression iveAbsArgument) {
      _il.enter(new Object[]{ao, mo, ive1, ive2, dve1, dve2, nveFactor, vep, iq, iveAbsArgument});
      this.setAdditiveOperator(ao);
      this.setMultiplicativeOperator(mo);
      this.setFirstOperand(ive1);
      this.setSecondOperand(ive2);
      this.setMinuend(dve1);
      this.setSubtrahend(dve2);
      this.setFactor(nveFactor);
      this.setSign(sign);
      this.setValueExpressionPrimary(vep);
      this.setIntervalQualifier(iq);
      this.setAbsArgument(iveAbsArgument);
      _il.exit();
   }

   public IntervalValueExpression(SqlFactory sf) {
      super(sf);
   }

   private class IveVisitor extends EnhancedSqlBaseVisitor<IntervalValueExpression> {
      private IveVisitor() {
      }

      public IntervalValueExpression visitIntervalValueExpression(SqlParser.IntervalValueExpressionContext ctx) {
         IntervalValueExpression iveReturn = IntervalValueExpression.this;
         if (ctx.sign() != null) {
            IntervalValueExpression.this.setSign(this.getSign(ctx.sign()));
         }

         if (ctx.valueExpressionPrimary() != null) {
            IntervalValueExpression.this.setValueExpressionPrimary(IntervalValueExpression.this.getSqlFactory().newValueExpressionPrimary());
            IntervalValueExpression.this.getValueExpressionPrimary().parse(ctx.valueExpressionPrimary());
            if (ctx.intervalQualifier() != null) {
               IntervalValueExpression.this.setIntervalQualifier(IntervalValueExpression.this.getSqlFactory().newIntervalQualifier());
               IntervalValueExpression.this.getIntervalQualifier().parse(ctx.intervalQualifier());
            }
         } else if (ctx.ABS() != null && ctx.intervalValueExpression().size() == 1) {
            IntervalValueExpression.this.setAbsArgument(IntervalValueExpression.this.getSqlFactory().newIntervalValueExpression());
            IntervalValueExpression.this.getAbsArgument().parse(ctx.intervalValueExpression(0));
         } else if (ctx.ASTERISK() != null && ctx.numericValueExpression() != null && ctx.intervalValueExpression().size() == 1) {
            IntervalValueExpression.this.setFactor(IntervalValueExpression.this.getSqlFactory().newNumericValueExpression());
            IntervalValueExpression.this.getFactor().parse(ctx.numericValueExpression());
            IntervalValueExpression.this.setSecondOperand(IntervalValueExpression.this.getSqlFactory().newIntervalValueExpression());
            IntervalValueExpression.this.getSecondOperand().parse(ctx.intervalValueExpression(0));
         } else if (ctx.numericValueExpression() != null && ctx.intervalValueExpression().size() == 1) {
            if (ctx.additiveOperator() != null) {
               IntervalValueExpression.this.setAdditiveOperator(this.getAdditiveOperator(ctx.additiveOperator()));
            } else if (ctx.multiplicativeOperator() != null) {
               IntervalValueExpression.this.setMultiplicativeOperator(this.getMultiplicativeOperator(ctx.multiplicativeOperator()));
            }

            IntervalValueExpression.this.setFirstOperand(IntervalValueExpression.this.getSqlFactory().newIntervalValueExpression());
            IntervalValueExpression.this.getFirstOperand().parse(ctx.intervalValueExpression(0));
            IntervalValueExpression.this.setFactor(IntervalValueExpression.this.getSqlFactory().newNumericValueExpression());
            IntervalValueExpression.this.getFactor().parse(ctx.numericValueExpression());
         } else if (ctx.intervalValueExpression().size() == 2) {
            if (ctx.additiveOperator() != null) {
               IntervalValueExpression.this.setAdditiveOperator(this.getAdditiveOperator(ctx.additiveOperator()));
            }

            IntervalValueExpression.this.setFirstOperand(IntervalValueExpression.this.getSqlFactory().newIntervalValueExpression());
            IntervalValueExpression.this.getFirstOperand().parse(ctx.intervalValueExpression(0));
            IntervalValueExpression.this.setSecondOperand(IntervalValueExpression.this.getSqlFactory().newIntervalValueExpression());
            IntervalValueExpression.this.getSecondOperand().parse(ctx.intervalValueExpression(1));
         } else if (ctx.datetimeValueExpression().size() == 2 && ctx.MINUS_SIGN() != null) {
            IntervalValueExpression.this.setMinuend(IntervalValueExpression.this.getSqlFactory().newDatetimeValueExpression());
            IntervalValueExpression.this.getMinuend().parse(ctx.datetimeValueExpression(0));
            IntervalValueExpression.this.setSubtrahend(IntervalValueExpression.this.getSqlFactory().newDatetimeValueExpression());
            IntervalValueExpression.this.getSubtrahend().parse(ctx.datetimeValueExpression(1));
            IntervalValueExpression.this.setIntervalQualifier(IntervalValueExpression.this.getSqlFactory().newIntervalQualifier());
            IntervalValueExpression.this.getIntervalQualifier().parse(ctx.intervalQualifier());
         } else {
            iveReturn = (IntervalValueExpression)this.visitChildren(ctx);
         }

         return iveReturn;
      }

      // $FF: synthetic method
      IveVisitor(Object x1) {
         this();
      }
   }
}
