package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.Interval;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.SqlStatement;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;

public class CommonValueExpression extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(CommonValueExpression.class.getName());
   private CommonValueExpression.CveVisitor _visitor = new CommonValueExpression.CveVisitor();
   private NumericValueExpression _nve = null;
   private StringValueExpression _sve = null;
   private DatetimeValueExpression _dve = null;
   private IntervalValueExpression _ive = null;
   private ArrayValueExpression _ave = null;
   private MultisetValueExpression _mve = null;
   private ValueExpressionPrimary _vep = null;

   private CommonValueExpression.CveVisitor getVisitor() {
      return this._visitor;
   }

   public NumericValueExpression getNumericValueExpression() {
      return this._nve;
   }

   public void setNumericValueExpression(NumericValueExpression nve) {
      this._nve = nve;
   }

   public StringValueExpression getStringValueExpression() {
      return this._sve;
   }

   public void setStringValueExpression(StringValueExpression sve) {
      this._sve = sve;
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

   public ValueExpressionPrimary getValueExpressionPrimary() {
      return this._vep;
   }

   public void setValueExpressionPrimary(ValueExpressionPrimary vep) {
      this._vep = vep;
   }

   public String format() {
      String sExpression = null;
      if (this.getNumericValueExpression() != null) {
         sExpression = this.getNumericValueExpression().format();
      } else if (this.getStringValueExpression() != null) {
         sExpression = this.getStringValueExpression().format();
      } else if (this.getDatetimeValueExpression() != null) {
         sExpression = this.getDatetimeValueExpression().format();
      } else if (this.getIntervalValueExpression() != null) {
         sExpression = this.getIntervalValueExpression().format();
      } else if (this.getArrayValueExpression() != null) {
         sExpression = this.getArrayValueExpression().format();
      } else if (this.getMultisetValueExpression() != null) {
         sExpression = this.getMultisetValueExpression().format();
      } else if (this.getValueExpressionPrimary() != null) {
         sExpression = this.getValueExpressionPrimary().format();
      }

      return sExpression;
   }

   public DataType getDataType(SqlStatement ss) {
      DataType dt = null;
      if (this.getNumericValueExpression() != null) {
         dt = this.getNumericValueExpression().getDataType(ss);
      } else if (this.getStringValueExpression() != null) {
         dt = this.getStringValueExpression().getDataType(ss);
      } else if (this.getDatetimeValueExpression() != null) {
         dt = this.getDatetimeValueExpression().getDataType(ss);
      } else if (this.getIntervalValueExpression() != null) {
         dt = this.getIntervalValueExpression().getDataType(ss);
      } else if (this.getArrayValueExpression() != null) {
         new IllegalArgumentException("Array value expressions are not supported for evaluation!");
      } else if (this.getMultisetValueExpression() != null) {
         new IllegalArgumentException("Multiset value expressions are not supported for evaluation!");
      } else if (this.getValueExpressionPrimary() != null) {
         dt = this.getValueExpressionPrimary().getDataType(ss);
      }

      return dt;
   }

   private Object evaluate(Object oNumericValue, Object oStringValue, Object oDatetimeValue, Interval ivIntervalValue, Object oValuePrimary) {
      Object oValue = null;
      if (this.getNumericValueExpression() != null) {
         oValue = oNumericValue;
      } else if (this.getStringValueExpression() != null) {
         oValue = oStringValue;
      } else if (this.getDatetimeValueExpression() != null) {
         oValue = oDatetimeValue;
      } else if (this.getIntervalValueExpression() != null) {
         oValue = ivIntervalValue;
      } else {
         if (this.getArrayValueExpression() != null) {
            throw new IllegalArgumentException("Array value expressions are not supported for evaluation!");
         }

         if (this.getMultisetValueExpression() != null) {
            throw new IllegalArgumentException("Multiset value expressions are not supported for evaluation!");
         }

         if (this.getValueExpressionPrimary() != null) {
            oValue = oValuePrimary;
         }
      }

      return oValue;
   }

   public Object evaluate(SqlStatement ss, boolean bAggregated) {
      Object oNumericValue = null;
      if (this.getNumericValueExpression() != null) {
         oNumericValue = this.getNumericValueExpression().evaluate(ss, bAggregated);
      }

      Object oStringValue = null;
      if (this.getStringValueExpression() != null) {
         oStringValue = this.getStringValueExpression().evaluate(ss, bAggregated);
      }

      Object oDatetimeValue = null;
      if (this.getDatetimeValueExpression() != null) {
         oDatetimeValue = this.getDatetimeValueExpression().evaluate(ss, bAggregated);
      }

      Interval ivIntervalValue = null;
      if (this.getIntervalValueExpression() != null) {
         ivIntervalValue = this.getIntervalValueExpression().evaluate(ss, bAggregated);
      }

      Object oValuePrimary = null;
      if (this.getValueExpressionPrimary() != null) {
         oValuePrimary = this.getValueExpressionPrimary().evaluate(ss, bAggregated);
      }

      Object oValue = this.evaluate(oNumericValue, oStringValue, oDatetimeValue, ivIntervalValue, oValuePrimary);
      return oValue;
   }

   public Object resetAggregates(SqlStatement ss) {
      Object oNumericValue = null;
      if (this.getNumericValueExpression() != null) {
         oNumericValue = this.getNumericValueExpression().resetAggregates(ss);
      }

      Object oStringValue = null;
      if (this.getStringValueExpression() != null) {
         oStringValue = this.getStringValueExpression().resetAggregates(ss);
      }

      Object oDatetimeValue = null;
      if (this.getDatetimeValueExpression() != null) {
         oDatetimeValue = this.getDatetimeValueExpression().resetAggregates(ss);
      }

      Interval ivIntervalValue = null;
      if (this.getIntervalValueExpression() != null) {
         ivIntervalValue = this.getIntervalValueExpression().resetAggregates(ss);
      }

      Object oValuePrimary = null;
      if (this.getValueExpressionPrimary() != null) {
         oValuePrimary = this.getValueExpressionPrimary().resetAggregates(ss);
      }

      Object oValue = this.evaluate(oNumericValue, oStringValue, oDatetimeValue, ivIntervalValue, oValuePrimary);
      return oValue;
   }

   public void parse(SqlParser.CommonValueExpressionContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().commonValueExpression());
   }

   public void initialize(NumericValueExpression nve, StringValueExpression sve, DatetimeValueExpression dve, IntervalValueExpression ive, ArrayValueExpression ave, MultisetValueExpression mve, ValueExpressionPrimary vep) {
      _il.enter(new Object[]{nve, sve, dve, ive, ave, mve, vep});
      this.setNumericValueExpression(nve);
      this.setStringValueExpression(sve);
      this.setDatetimeValueExpression(dve);
      this.setIntervalValueExpression(ive);
      this.setArrayValueExpression(ave);
      this.setMultisetValueExpression(mve);
      this.setValueExpressionPrimary(vep);
      _il.exit();
   }

   public CommonValueExpression(SqlFactory sf) {
      super(sf);
   }

   private class CveVisitor extends EnhancedSqlBaseVisitor<CommonValueExpression> {
      private CveVisitor() {
      }

      public CommonValueExpression visitNumericValueExpression(SqlParser.NumericValueExpressionContext ctx) {
         CommonValueExpression.this.setNumericValueExpression(CommonValueExpression.this.getSqlFactory().newNumericValueExpression());
         CommonValueExpression.this.getNumericValueExpression().parse(ctx);
         return CommonValueExpression.this;
      }

      public CommonValueExpression visitStringValueExpression(SqlParser.StringValueExpressionContext ctx) {
         CommonValueExpression.this.setStringValueExpression(CommonValueExpression.this.getSqlFactory().newStringValueExpression());
         CommonValueExpression.this.getStringValueExpression().parse(ctx);
         return CommonValueExpression.this;
      }

      public CommonValueExpression visitDatetimeValueExpression(SqlParser.DatetimeValueExpressionContext ctx) {
         CommonValueExpression.this.setDatetimeValueExpression(CommonValueExpression.this.getSqlFactory().newDatetimeValueExpression());
         CommonValueExpression.this.getDatetimeValueExpression().parse(ctx);
         return CommonValueExpression.this;
      }

      public CommonValueExpression visitIntervalValueExpression(SqlParser.IntervalValueExpressionContext ctx) {
         CommonValueExpression.this.setIntervalValueExpression(CommonValueExpression.this.getSqlFactory().newIntervalValueExpression());
         CommonValueExpression.this.getIntervalValueExpression().parse(ctx);
         return CommonValueExpression.this;
      }

      public CommonValueExpression visitArrayValueExpression(SqlParser.ArrayValueExpressionContext ctx) {
         CommonValueExpression.this.setArrayValueExpression(CommonValueExpression.this.getSqlFactory().newArrayValueExpression());
         CommonValueExpression.this.getArrayValueExpression().parse(ctx);
         return CommonValueExpression.this;
      }

      public CommonValueExpression visitMultisetValueExpression(SqlParser.MultisetValueExpressionContext ctx) {
         CommonValueExpression.this.setMultisetValueExpression(CommonValueExpression.this.getSqlFactory().newMultisetValueExpression());
         CommonValueExpression.this.getMultisetValueExpression().parse(ctx);
         return CommonValueExpression.this;
      }

      public CommonValueExpression visitValueExpressionPrimary(SqlParser.ValueExpressionPrimaryContext ctx) {
         CommonValueExpression.this.setValueExpressionPrimary(CommonValueExpression.this.getSqlFactory().newValueExpressionPrimary());
         CommonValueExpression.this.getValueExpressionPrimary().parse(ctx);
         return CommonValueExpression.this;
      }

      // $FF: synthetic method
      CveVisitor(Object x1) {
         this();
      }
   }
}
