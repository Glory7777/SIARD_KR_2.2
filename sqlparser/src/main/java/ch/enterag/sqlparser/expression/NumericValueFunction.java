package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.SqlStatement;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.datatype.PredefinedType;
import ch.enterag.sqlparser.expression.enums.DatetimeField;
import ch.enterag.sqlparser.expression.enums.NumericFunction;
import ch.enterag.sqlparser.expression.enums.TimeZoneField;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.BU;
import ch.enterag.utils.SU;
import ch.enterag.utils.logging.IndentLogger;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumericValueFunction extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(NumericValueFunction.class.getName());
   private NumericValueFunction.NvfVisitor _visitor = new NumericValueFunction.NvfVisitor();
   private NumericFunction _nf = null;
   private StringValueExpression _sve1 = null;
   private StringValueExpression _sve2 = null;
   private DatetimeField _df = null;
   private TimeZoneField _tf = null;
   private DatetimeValueExpression _dve = null;
   private IntervalValueExpression _ive = null;
   private StringValueExpression _sve = null;
   private ArrayValueExpression _ave = null;
   private MultisetValueExpression _mve = null;
   private NumericValueExpression _nve = null;
   private NumericValueExpression _nve2 = null;
   private NumericValueExpression _nveWidthBucketOperand = null;
   private NumericValueExpression _nveWidthBucketBound1 = null;
   private NumericValueExpression _nveWidthBucketBound2 = null;
   private NumericValueExpression _nveWidthBucketCount = null;

   private NumericValueFunction.NvfVisitor getVisitor() {
      return this._visitor;
   }

   public NumericFunction getNumericFunction() {
      return this._nf;
   }

   public void setNumericFunction(NumericFunction nf) {
      this._nf = nf;
   }

   public StringValueExpression getFirstStringValueExpression() {
      return this._sve1;
   }

   public void setFirstStringValueExpression(StringValueExpression sve1) {
      this._sve1 = sve1;
   }

   public StringValueExpression getSecondStringValueExpression() {
      return this._sve2;
   }

   public void setSecondStringValueExpression(StringValueExpression sve2) {
      this._sve2 = sve2;
   }

   public DatetimeField getDatetimeField() {
      return this._df;
   }

   public void setDatetimeField(DatetimeField df) {
      this._df = df;
   }

   public TimeZoneField getTimeZoneField() {
      return this._tf;
   }

   public void setTimeZoneField(TimeZoneField tf) {
      this._tf = tf;
   }

   public DatetimeValueExpression getDatetimeValueExpression() {
      return this._dve;
   }

   public void setDatetimeValueExpression(DatetimeValueExpression dve) {
      this._dve = dve;
   }

   public IntervalValueExpression getIntervalValueExpression() {
      return this._ive;
   }

   public void setIntervalValueExpression(IntervalValueExpression ive) {
      this._ive = ive;
   }

   public StringValueExpression getStringValueExpression() {
      return this._sve;
   }

   public void setStringValueExpression(StringValueExpression sve) {
      this._sve = sve;
   }

   public ArrayValueExpression getArrayValueExpression() {
      return this._ave;
   }

   public void setArrayValueExpression(ArrayValueExpression ave) {
      this._ave = ave;
   }

   public MultisetValueExpression getMultisetValueExpression() {
      return this._mve;
   }

   public void setMultisetValueExpression(MultisetValueExpression mve) {
      this._mve = mve;
   }

   public NumericValueExpression getNumericValueExpression() {
      return this._nve;
   }

   public void setNumericValueExpression(NumericValueExpression nve) {
      this._nve = nve;
   }

   public NumericValueExpression getSecondNumericValueExpression() {
      return this._nve2;
   }

   public void setSecondNumericValueExpression(NumericValueExpression nve2) {
      this._nve2 = nve2;
   }

   public NumericValueExpression getWidthBucketOperand() {
      return this._nveWidthBucketOperand;
   }

   public void setWidthBucketOperand(NumericValueExpression nveWidthBucketOperand) {
      this._nveWidthBucketOperand = nveWidthBucketOperand;
   }

   public NumericValueExpression getWidthBucketBound1() {
      return this._nveWidthBucketBound1;
   }

   public void setWidthBucketBound1(NumericValueExpression nveWidthBucketBound1) {
      this._nveWidthBucketBound1 = nveWidthBucketBound1;
   }

   public NumericValueExpression getWidthBucketBound2() {
      return this._nveWidthBucketBound2;
   }

   public void setWidthBucketBound2(NumericValueExpression nveWidthBucketBound2) {
      this._nveWidthBucketBound2 = nveWidthBucketBound2;
   }

   public NumericValueExpression getWidthBucketCount() {
      return this._nveWidthBucketCount;
   }

   public void setWidthBucketCount(NumericValueExpression nveWidthBucketCount) {
      this._nveWidthBucketCount = nveWidthBucketCount;
   }

   public String format() {
      String sFunction = this.getNumericFunction().getKeywords() + "(";
      switch(this.getNumericFunction()) {
      case POSITION:
         sFunction = sFunction + this.getFirstStringValueExpression().format() + " " + K.IN.getKeyword() + " " + this.getSecondStringValueExpression().format();
         break;
      case EXTRACT:
         if (this.getDatetimeField() != null) {
            sFunction = sFunction + this.getDatetimeField().getKeywords();
         } else if (this.getTimeZoneField() != null) {
            sFunction = sFunction + this.getTimeZoneField().getKeywords();
         }

         sFunction = sFunction + " " + K.FROM.getKeyword();
         if (this.getDatetimeValueExpression() != null) {
            sFunction = sFunction + " " + this.getDatetimeValueExpression().format();
         } else if (this.getIntervalValueExpression() != null) {
            sFunction = sFunction + " " + this.getIntervalValueExpression().format();
         }
         break;
      case CHARACTER_LENGTH:
      case OCTET_LENGTH:
         sFunction = sFunction + this.getStringValueExpression().format();
         break;
      case CARDINALITY:
         if (this.getArrayValueExpression() != null) {
            sFunction = sFunction + this.getArrayValueExpression().format();
         } else if (this.getMultisetValueExpression() != null) {
            sFunction = sFunction + this.getMultisetValueExpression().format();
         }
         break;
      case ABS:
      case LN:
      case EXP:
      case SQRT:
      case FLOOR:
      case CEILING:
         sFunction = sFunction + this.getNumericValueExpression().format();
         break;
      case MOD:
      case POWER:
         sFunction = sFunction + this.getNumericValueExpression().format() + "," + " " + this.getSecondNumericValueExpression().format();
         break;
      case WIDTH_BUCKET:
         sFunction = sFunction + this.getWidthBucketOperand().format() + "," + " " + this.getWidthBucketBound1().format() + "," + " " + this.getWidthBucketBound2().format() + "," + " " + this.getWidthBucketCount().format();
      }

      sFunction = sFunction + ")";
      return sFunction;
   }

   public DataType getDataType(SqlStatement ss) {
      DataType dt = null;
      DataType dtArgument = null;
      if (this.getNumericValueExpression() != null) {
         dtArgument = this.getNumericValueExpression().getDataType(ss);
      }

      dt = this.getSqlFactory().newDataType();
      PredefinedType pt = this.getSqlFactory().newPredefinedType();
      dt.initPredefinedDataType(pt);
      switch(this.getNumericFunction()) {
      case POSITION:
      case CHARACTER_LENGTH:
      case OCTET_LENGTH:
      case FLOOR:
      case CEILING:
         pt.initIntegerType();
         break;
      case EXTRACT:
         throw new IllegalArgumentException("DateTime function EXTRACT is not yet supported for evaluation!");
      case CARDINALITY:
         throw new IllegalArgumentException("Array value function CARDINALITY not supported!");
      case ABS:
      case MOD:
      case POWER:
         dt = dtArgument;
         break;
      case LN:
      case EXP:
      case SQRT:
         pt.initDoubleType();
         break;
      case WIDTH_BUCKET:
         throw new IllegalArgumentException("Numeric function WIDTH_BUCKET not supported for evaluation!");
      }

      return dt;
   }

   private Object evaluate(Object oNumericValue, Object oSecondNumericValue, Object oStringValue, Object oSecondStringValue) {
      Object oValue = null;
      BigDecimal bd = null;
      Double d = null;
      String s = null;
      byte[] buf = null;
      if (this.getNumericValueExpression() != null) {
         if (oNumericValue != null) {
            if (oNumericValue instanceof BigDecimal) {
               bd = (BigDecimal)oNumericValue;
            } else {
               if (!(oNumericValue instanceof Double)) {
                  throw new IllegalArgumentException("Numeric values must be exact or approximate!");
               }

               d = (Double)oNumericValue;
            }
         }
      } else if (this.getStringValueExpression() != null && oStringValue != null) {
         if (oStringValue instanceof String) {
            s = (String)oStringValue;
         } else {
            if (!(oStringValue instanceof byte[])) {
               throw new IllegalArgumentException("String values must be character or binary strings!");
            }

            buf = (byte[])((byte[])oStringValue);
         }
      }

      switch(this.getNumericFunction()) {
      case POSITION:
         int iIndex = -1;
         if (s != null) {
            String sContainer = (String)oSecondStringValue;
            iIndex = sContainer.indexOf(s);
         } else if (buf != null) {
            byte[] bufContainer = (byte[])((byte[])oSecondStringValue);
            iIndex = BU.indexOf(bufContainer, buf);
         }

         oValue = BigDecimal.valueOf((long)(iIndex + 1));
         break;
      case EXTRACT:
         throw new IllegalArgumentException("DateTime function EXTRACT is not yet supported for evaluation!");
      case CHARACTER_LENGTH:
         if (s != null) {
            oValue = BigDecimal.valueOf((long)s.length());
         }
         break;
      case OCTET_LENGTH:
         if (s != null) {
            oValue = BigDecimal.valueOf((long)SU.putUtf8String(s).length);
         } else if (buf != null) {
            oValue = BigDecimal.valueOf((long)buf.length);
         }
         break;
      case CARDINALITY:
         throw new IllegalArgumentException("Array value function CARDINALITY not supported!");
      case ABS:
         if (bd != null) {
            oValue = bd.abs();
         } else if (d != null) {
            oValue = Math.abs(d);
         }
         break;
      case LN:
         if (bd != null) {
            oValue = BigDecimal.valueOf(Math.log(bd.doubleValue()));
         } else if (d != null) {
            oValue = Math.log(d);
         }
         break;
      case EXP:
         if (bd != null) {
            oValue = BigDecimal.valueOf(Math.exp(bd.doubleValue()));
         } else if (d != null) {
            oValue = Math.exp(d);
         }
         break;
      case SQRT:
         if (bd != null) {
            oValue = BigDecimal.valueOf(Math.sqrt(bd.doubleValue()));
         } else if (d != null) {
            oValue = Math.sqrt(d);
         }
         break;
      case FLOOR:
         if (bd != null) {
            oValue = bd.setScale(0, RoundingMode.DOWN);
         } else if (d != null) {
            oValue = Math.floor(d);
         }
         break;
      case CEILING:
         if (bd != null) {
            oValue = bd.setScale(0, RoundingMode.UP);
         } else if (d != null) {
            oValue = Math.ceil(d);
         }
         break;
      case MOD:
         BigDecimal bdDivisor = (BigDecimal)oSecondNumericValue;
         if (bd != null && bdDivisor != null) {
            oValue = bd.remainder(bdDivisor);
         }
         break;
      case POWER:
         int iPower = this.getSecondNumericValueExpression().evaluateInteger(oSecondNumericValue);
         if (bd != null) {
            oValue = bd.pow(iPower);
         } else {
            if (d == null) {
               throw new IllegalArgumentException("Numeric values must be exact or approximate!");
            }

            double dValue = 1.0D;

            for(int i = 0; i < iPower; ++i) {
               dValue *= d;
            }

            oValue = dValue;
         }
         break;
      case WIDTH_BUCKET:
         throw new IllegalArgumentException("Numeric function WIDTH_BUCKET not supported for evaluation!");
      }

      return oValue;
   }

   public Object evaluate(SqlStatement ss, boolean bAggregated) {
      Object oNumericValue = null;
      if (this.getNumericValueExpression() != null) {
         oNumericValue = this.getNumericValueExpression().evaluate(ss, bAggregated);
      }

      Object oSecondNumericValue = null;
      if (this.getSecondNumericValueExpression() != null) {
         oSecondNumericValue = this.getSecondNumericValueExpression().evaluate(ss, bAggregated);
      }

      Object oStringValue = null;
      if (this.getStringValueExpression() != null) {
         oStringValue = this.getStringValueExpression().evaluate(ss, bAggregated);
      }

      Object oSecondStringValue = null;
      if (this.getSecondStringValueExpression() != null) {
         oSecondStringValue = this.getSecondStringValueExpression().evaluate(ss, bAggregated);
      }

      Object oValue = this.evaluate(oNumericValue, oSecondNumericValue, oStringValue, oSecondStringValue);
      return oValue;
   }

   public Object resetAggregates(SqlStatement ss) {
      Object oNumericValue = null;
      if (this.getNumericValueExpression() != null) {
         oNumericValue = this.getNumericValueExpression().resetAggregates(ss);
      }

      Object oSecondNumericValue = null;
      if (this.getSecondNumericValueExpression() != null) {
         oSecondNumericValue = this.getSecondNumericValueExpression().resetAggregates(ss);
      }

      Object oStringValue = null;
      if (this.getStringValueExpression() != null) {
         oStringValue = this.getStringValueExpression().resetAggregates(ss);
      }

      Object oSecondStringValue = null;
      if (this.getSecondStringValueExpression() != null) {
         oSecondStringValue = this.getSecondStringValueExpression().resetAggregates(ss);
      }

      Object oValue = this.evaluate(oNumericValue, oSecondNumericValue, oStringValue, oSecondStringValue);
      return oValue;
   }

   public void parse(SqlParser.NumericValueFunctionContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().numericValueFunction());
   }

   public void initialize(NumericFunction nf, StringValueExpression sve1, StringValueExpression sve2, DatetimeField df, TimeZoneField tzf, DatetimeValueExpression dve, IntervalValueExpression ive, StringValueExpression sve, ArrayValueExpression ave, MultisetValueExpression mve, NumericValueExpression nve, NumericValueExpression nve2, NumericValueExpression nveWidthBucketOperand, NumericValueExpression nveWidthBucketBound1, NumericValueExpression nveWidthBucketBound2, NumericValueExpression nveWidthBucketCount) {
      _il.enter(new Object[]{nf, sve1, sve2, df, tzf, dve, ive, sve, ave, mve, nve, nve2, nveWidthBucketOperand, nveWidthBucketBound1, nveWidthBucketBound2, nveWidthBucketCount});
      this.setNumericFunction(nf);
      this.setFirstStringValueExpression(sve1);
      this.setSecondStringValueExpression(sve2);
      this.setDatetimeField(df);
      this.setTimeZoneField(tzf);
      this.setDatetimeValueExpression(dve);
      this.setIntervalValueExpression(ive);
      this.setStringValueExpression(sve);
      this.setArrayValueExpression(ave);
      this.setMultisetValueExpression(mve);
      this.setNumericValueExpression(nve);
      this.setSecondNumericValueExpression(nve2);
      this.setWidthBucketOperand(nveWidthBucketOperand);
      this.setWidthBucketBound1(nveWidthBucketBound1);
      this.setWidthBucketBound2(nveWidthBucketBound2);
      this.setWidthBucketCount(nveWidthBucketCount);
      _il.exit();
   }

   public NumericValueFunction(SqlFactory sf) {
      super(sf);
   }

   private class NvfVisitor extends EnhancedSqlBaseVisitor<NumericValueFunction> {
      private NvfVisitor() {
      }

      public NumericValueFunction visitPositionExpression(SqlParser.PositionExpressionContext ctx) {
         NumericValueFunction.this.setNumericFunction(NumericFunction.POSITION);
         NumericValueFunction.this.setFirstStringValueExpression(NumericValueFunction.this.getSqlFactory().newStringValueExpression());
         NumericValueFunction.this.getFirstStringValueExpression().parse(ctx.stringValueExpression(0));
         NumericValueFunction.this.setSecondStringValueExpression(NumericValueFunction.this.getSqlFactory().newStringValueExpression());
         NumericValueFunction.this.getSecondStringValueExpression().parse(ctx.stringValueExpression(1));
         return NumericValueFunction.this;
      }

      public NumericValueFunction visitExtractExpression(SqlParser.ExtractExpressionContext ctx) {
         NumericValueFunction.this.setNumericFunction(NumericFunction.EXTRACT);
         return (NumericValueFunction)this.visitChildren(ctx);
      }

      public NumericValueFunction visitLengthExpression(SqlParser.LengthExpressionContext ctx) {
         if (ctx.CHAR_LENGTH() == null && ctx.CHARACTER_LENGTH() == null) {
            if (ctx.OCTET_LENGTH() != null) {
               NumericValueFunction.this.setNumericFunction(NumericFunction.OCTET_LENGTH);
            }
         } else {
            NumericValueFunction.this.setNumericFunction(NumericFunction.CHARACTER_LENGTH);
         }

         NumericValueFunction.this.setStringValueExpression(NumericValueFunction.this.getSqlFactory().newStringValueExpression());
         NumericValueFunction.this.getStringValueExpression().parse(ctx.stringValueExpression());
         return NumericValueFunction.this;
      }

      public NumericValueFunction visitCardinalityExpression(SqlParser.CardinalityExpressionContext ctx) {
         NumericValueFunction.this.setNumericFunction(NumericFunction.CARDINALITY);
         if (ctx.arrayValueExpression() != null) {
            NumericValueFunction.this.setArrayValueExpression(NumericValueFunction.this.getSqlFactory().newArrayValueExpression());
            NumericValueFunction.this.getArrayValueExpression().parse(ctx.arrayValueExpression());
         } else if (ctx.multisetValueExpression() != null) {
            NumericValueFunction.this.setMultisetValueExpression(NumericValueFunction.this.getSqlFactory().newMultisetValueExpression());
            NumericValueFunction.this.getMultisetValueExpression().parse(ctx.multisetValueExpression());
         }

         return NumericValueFunction.this;
      }

      public NumericValueFunction visitAbsoluteValueExpression(SqlParser.AbsoluteValueExpressionContext ctx) {
         NumericValueFunction.this.setNumericFunction(NumericFunction.ABS);
         NumericValueFunction.this.setNumericValueExpression(NumericValueFunction.this.getSqlFactory().newNumericValueExpression());
         NumericValueFunction.this.getNumericValueExpression().parse(ctx.numericValueExpression());
         return NumericValueFunction.this;
      }

      public NumericValueFunction visitModulusExpression(SqlParser.ModulusExpressionContext ctx) {
         NumericValueFunction.this.setNumericFunction(NumericFunction.MOD);
         NumericValueFunction.this.setNumericValueExpression(NumericValueFunction.this.getSqlFactory().newNumericValueExpression());
         NumericValueFunction.this.getNumericValueExpression().parse(ctx.numericValueExpression(0));
         NumericValueFunction.this.setSecondNumericValueExpression(NumericValueFunction.this.getSqlFactory().newNumericValueExpression());
         NumericValueFunction.this.getSecondNumericValueExpression().parse(ctx.numericValueExpression(1));
         return NumericValueFunction.this;
      }

      public NumericValueFunction visitNaturalLogarithm(SqlParser.NaturalLogarithmContext ctx) {
         NumericValueFunction.this.setNumericFunction(NumericFunction.LN);
         NumericValueFunction.this.setNumericValueExpression(NumericValueFunction.this.getSqlFactory().newNumericValueExpression());
         NumericValueFunction.this.getNumericValueExpression().parse(ctx.numericValueExpression());
         return NumericValueFunction.this;
      }

      public NumericValueFunction visitExponentialFunction(SqlParser.ExponentialFunctionContext ctx) {
         NumericValueFunction.this.setNumericFunction(NumericFunction.EXP);
         NumericValueFunction.this.setNumericValueExpression(NumericValueFunction.this.getSqlFactory().newNumericValueExpression());
         NumericValueFunction.this.getNumericValueExpression().parse(ctx.numericValueExpression());
         return NumericValueFunction.this;
      }

      public NumericValueFunction visitPowerFunction(SqlParser.PowerFunctionContext ctx) {
         NumericValueFunction.this.setNumericFunction(NumericFunction.POWER);
         NumericValueFunction.this.setNumericValueExpression(NumericValueFunction.this.getSqlFactory().newNumericValueExpression());
         NumericValueFunction.this.getNumericValueExpression().parse(ctx.numericValueExpression(0));
         NumericValueFunction.this.setSecondNumericValueExpression(NumericValueFunction.this.getSqlFactory().newNumericValueExpression());
         NumericValueFunction.this.getSecondNumericValueExpression().parse(ctx.numericValueExpression(1));
         return NumericValueFunction.this;
      }

      public NumericValueFunction visitSquareRoot(SqlParser.SquareRootContext ctx) {
         NumericValueFunction.this.setNumericFunction(NumericFunction.SQRT);
         NumericValueFunction.this.setNumericValueExpression(NumericValueFunction.this.getSqlFactory().newNumericValueExpression());
         NumericValueFunction.this.getNumericValueExpression().parse(ctx.numericValueExpression());
         return NumericValueFunction.this;
      }

      public NumericValueFunction visitFloorFunction(SqlParser.FloorFunctionContext ctx) {
         NumericValueFunction.this.setNumericFunction(NumericFunction.FLOOR);
         NumericValueFunction.this.setNumericValueExpression(NumericValueFunction.this.getSqlFactory().newNumericValueExpression());
         NumericValueFunction.this.getNumericValueExpression().parse(ctx.numericValueExpression());
         return NumericValueFunction.this;
      }

      public NumericValueFunction visitCeilingFunction(SqlParser.CeilingFunctionContext ctx) {
         NumericValueFunction.this.setNumericFunction(NumericFunction.CEILING);
         NumericValueFunction.this.setNumericValueExpression(NumericValueFunction.this.getSqlFactory().newNumericValueExpression());
         NumericValueFunction.this.getNumericValueExpression().parse(ctx.numericValueExpression());
         return NumericValueFunction.this;
      }

      public NumericValueFunction visitWidthBucketFunction(SqlParser.WidthBucketFunctionContext ctx) {
         NumericValueFunction.this.setNumericFunction(NumericFunction.WIDTH_BUCKET);
         NumericValueFunction.this.setWidthBucketOperand(NumericValueFunction.this.getSqlFactory().newNumericValueExpression());
         NumericValueFunction.this.getWidthBucketOperand().parse(ctx.widthBucketOperand().numericValueExpression());
         NumericValueFunction.this.setWidthBucketBound1(NumericValueFunction.this.getSqlFactory().newNumericValueExpression());
         NumericValueFunction.this.getWidthBucketBound1().parse(ctx.widthBucketBound1().numericValueExpression());
         NumericValueFunction.this.setWidthBucketBound2(NumericValueFunction.this.getSqlFactory().newNumericValueExpression());
         NumericValueFunction.this.getWidthBucketBound2().parse(ctx.widthBucketBound2().numericValueExpression());
         NumericValueFunction.this.setWidthBucketCount(NumericValueFunction.this.getSqlFactory().newNumericValueExpression());
         NumericValueFunction.this.getWidthBucketCount().parse(ctx.widthBucketCount().numericValueExpression());
         return NumericValueFunction.this;
      }

      public NumericValueFunction visitExtractField(SqlParser.ExtractFieldContext ctx) {
         if (ctx.primaryDatetimeField() != null) {
            NumericValueFunction.this.setDatetimeField(this.getDatetimeField(ctx.primaryDatetimeField()));
         } else if (ctx.timeZoneField() != null) {
            NumericValueFunction.this.setTimeZoneField(this.getTimeZoneField(ctx.timeZoneField()));
         }

         return NumericValueFunction.this;
      }

      public NumericValueFunction visitDatetimeValueExpression(SqlParser.DatetimeValueExpressionContext ctx) {
         NumericValueFunction.this.setDatetimeValueExpression(NumericValueFunction.this.getSqlFactory().newDatetimeValueExpression());
         NumericValueFunction.this.getDatetimeValueExpression().parse(ctx);
         return NumericValueFunction.this;
      }

      public NumericValueFunction visitIntervalValueExpression(SqlParser.IntervalValueExpressionContext ctx) {
         NumericValueFunction.this.setIntervalValueExpression(NumericValueFunction.this.getSqlFactory().newIntervalValueExpression());
         NumericValueFunction.this.getIntervalValueExpression().parse(ctx);
         return NumericValueFunction.this;
      }

      // $FF: synthetic method
      NvfVisitor(Object x1) {
         this();
      }
   }
}
