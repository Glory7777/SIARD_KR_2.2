package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.SqlStatement;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.datatype.PredefinedType;
import ch.enterag.sqlparser.expression.enums.AdditiveOperator;
import ch.enterag.sqlparser.expression.enums.MultiplicativeOperator;
import ch.enterag.sqlparser.expression.enums.Sign;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;
import java.math.BigDecimal;

public class NumericValueExpression extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(NumericValueExpression.class.getName());
   private NumericValueExpression.NveVisitor _visitor = new NumericValueExpression.NveVisitor();
   private AdditiveOperator _ao = null;
   private MultiplicativeOperator _mo = null;
   private NumericValueExpression _nve1 = null;
   private NumericValueExpression _nve2 = null;
   private NumericValueExpression _nve = null;
   private Sign _sign = null;
   private ValueExpressionPrimary _vep = null;
   private NumericValueFunction _nvf = null;

   private NumericValueExpression.NveVisitor getVisitor() {
      return this._visitor;
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

   public NumericValueExpression getFirstOperand() {
      return this._nve1;
   }

   public void setFirstOperand(NumericValueExpression nve1) {
      this._nve1 = nve1;
   }

   public NumericValueExpression getSecondOperand() {
      return this._nve2;
   }

   public void setSecondOperand(NumericValueExpression nve2) {
      this._nve2 = nve2;
   }

   public NumericValueExpression getParenthesizedExpression() {
      return this._nve;
   }

   public void setParenthesizedExpression(NumericValueExpression nve) {
      this._nve = nve;
   }

   public Sign getSign() {
      return this._sign;
   }

   public void setSign(Sign sign) {
      this._sign = sign;
   }

   public ValueExpressionPrimary getValueExpressionPrimary() {
      return this._vep;
   }

   public void setValueExpressionPrimary(ValueExpressionPrimary vep) {
      this._vep = vep;
   }

   public NumericValueFunction getNumericValueFunction() {
      return this._nvf;
   }

   public void setNumericValueFunction(NumericValueFunction nvf) {
      this._nvf = nvf;
   }

   public String format() {
      String sExpression = "";
      if (this.getFirstOperand() != null && this.getSecondOperand() != null) {
         sExpression = this.getFirstOperand().format();
         if (this.getAdditiveOperator() != null) {
            sExpression = sExpression + " " + this.getAdditiveOperator().getKeywords() + " ";
         } else if (this.getMultiplicativeOperator() != null) {
            sExpression = sExpression + this.getMultiplicativeOperator().getKeywords();
         }

         sExpression = sExpression + this.getSecondOperand().format();
      } else if (this.getParenthesizedExpression() != null) {
         sExpression = "(" + this.getParenthesizedExpression().format() + ")";
      } else if (this.getValueExpressionPrimary() != null) {
         if (this.getSign() != null) {
            sExpression = this.getSign().getKeywords();
         }

         sExpression = sExpression + this.getValueExpressionPrimary().format();
      } else if (this.getNumericValueFunction() != null) {
         sExpression = this.getNumericValueFunction().format();
      }

      return sExpression;
   }

   public DataType getDataType(SqlStatement ss) {
      DataType dt = null;
      if (this.getFirstOperand() != null && this.getSecondOperand() != null) {
         DataType dt1 = this.getFirstOperand().getDataType(ss);
         DataType dt2 = this.getSecondOperand().getDataType(ss);
         if (dt1 != null && dt2 != null) {
            PredefinedType pt1 = dt1.getPredefinedType();
            PredefinedType pt2 = dt2.getPredefinedType();
            if (pt1 != null && pt2 != null) {
               int ip1;
               int is1;
               int ip2;
               int is2;
               ip1 = dt1.getPredefinedType().getPrecision();
               is1 = dt1.getPredefinedType().getScale();
               ip2 = dt2.getPredefinedType().getPrecision();
               is2 = dt2.getPredefinedType().getScale();
               dt = this.getSqlFactory().newDataType();
               PredefinedType pt = this.getSqlFactory().newPredefinedType();
               dt.initPredefinedDataType(pt);
               label67:
               switch(pt1.getType()) {
               case DOUBLE:
               case FLOAT:
               case REAL:
                  pt.initDoubleType();
                  break;
               case DECIMAL:
               case NUMERIC:
                  switch(pt2.getType()) {
                  case DOUBLE:
                  case FLOAT:
                  case REAL:
                     pt.initDoubleType();
                     break label67;
                  case DECIMAL:
                  case NUMERIC:
                     pt.initDecimalType(ip2, is2);
                     break label67;
                  case BIGINT:
                     ip2 = 19;
                     is2 = 0;
                     pt.initDecimalType(ip2, is2);
                     break label67;
                  case INTEGER:
                     ip2 = 10;
                     is2 = 0;
                     pt.initDecimalType(ip2, is2);
                     break label67;
                  case SMALLINT:
                     ip2 = 5;
                     is2 = 0;
                     pt.initDecimalType(ip2, is2);
                     break label67;
                  default:
                     throw new IllegalArgumentException("Unexpected data type for numeric value expression: " + pt2.getType().getKeyword() + "!");
                  }
               case BIGINT:
                  switch(pt2.getType()) {
                  case DOUBLE:
                  case FLOAT:
                  case REAL:
                     pt.initDoubleType();
                     break label67;
                  case DECIMAL:
                  case NUMERIC:
                     pt.initDecimalType(ip2, is2);
                     break label67;
                  case BIGINT:
                  case INTEGER:
                  case SMALLINT:
                     pt.initBigIntType();
                     break label67;
                  default:
                     throw new IllegalArgumentException("Unexpected data type for numeric value expression: " + pt2.getType().getKeyword() + "!");
                  }
               case INTEGER:
                  switch(pt2.getType()) {
                  case DOUBLE:
                  case FLOAT:
                  case REAL:
                     pt.initDoubleType();
                     break label67;
                  case DECIMAL:
                  case NUMERIC:
                     pt.initDecimalType(ip2, is2);
                     break label67;
                  case BIGINT:
                     pt.initBigIntType();
                     break label67;
                  case INTEGER:
                  case SMALLINT:
                     pt.initIntegerType();
                     break label67;
                  default:
                     throw new IllegalArgumentException("Unexpected data type for numeric value expression: " + pt2.getType().getKeyword() + "!");
                  }
               case SMALLINT:
                  switch(pt2.getType()) {
                  case DOUBLE:
                  case FLOAT:
                  case REAL:
                     pt.initDoubleType();
                     break label67;
                  case DECIMAL:
                  case NUMERIC:
                     pt.initDecimalType(ip2, is2);
                     break label67;
                  case BIGINT:
                     pt.initBigIntType();
                     break label67;
                  case INTEGER:
                     pt.initIntegerType();
                  case SMALLINT:
                     pt.initSmallIntType();
                     break label67;
                  default:
                     throw new IllegalArgumentException("Unexpected data type for numeric value expression: " + pt2.getType().getKeyword() + "!");
                  }
               default:
                  throw new IllegalArgumentException("Unexpected data type for numeric value expression: " + pt1.getType().getKeyword() + "!");
               }

               if (ip1 > ip2) {
                  dt.getPredefinedType().setPrecision(ip1);
               }

               if (is1 > is2) {
                  dt.getPredefinedType().setScale(is1);
               }
            }
         } else if (dt1.getPredefinedType() != null) {
            dt = dt1;
         } else if (dt2.getPredefinedType() != null) {
            dt = dt2;
         }
      } else if (this.getParenthesizedExpression() != null) {
         dt = this.getParenthesizedExpression().getDataType(ss);
      } else if (this.getValueExpressionPrimary() != null) {
         dt = this.getValueExpressionPrimary().getDataType(ss);
      } else if (this.getNumericValueFunction() != null) {
         dt = this.getNumericValueFunction().getDataType(ss);
      }

      return dt;
   }

   private Object evaluateOperator(AdditiveOperator ao, MultiplicativeOperator mo, Object oValue1, Object oValue2) {
      Object oValue = null;
      BigDecimal bd1 = null;
      BigDecimal bd2 = null;
      Double d1 = null;
      Double d2 = null;
      if (oValue1 != null && oValue2 != null) {
         if (oValue1 instanceof BigDecimal) {
            bd1 = (BigDecimal)oValue1;
         } else {
            if (!(oValue1 instanceof Double)) {
               throw new IllegalArgumentException("Numeric value must be BigDecimal or Double!");
            }

            d1 = (Double)oValue1;
         }

         if (oValue2 instanceof BigDecimal) {
            bd2 = (BigDecimal)oValue2;
         } else {
            if (!(oValue2 instanceof Double)) {
               throw new IllegalArgumentException("Numeric value must be BigDecimal or Double!");
            }

            d2 = (Double)oValue2;
         }

         if (bd1 == null && bd2 == null) {
            if (ao != null) {
               if (ao == AdditiveOperator.PLUS_SIGN) {
                  oValue = d1 + d2;
               } else if (ao == AdditiveOperator.MINUS_SIGN) {
                  oValue = d1 - d2;
               }
            } else if (mo != null) {
               if (mo == MultiplicativeOperator.ASTERISK) {
                  oValue = d1 * d2;
               } else if (mo == MultiplicativeOperator.SOLIDUS) {
                  oValue = d1 / d2;
               }
            }
         } else {
            if (bd1 == null) {
               bd1 = BigDecimal.valueOf(d1);
            }

            if (bd2 == null) {
               bd2 = BigDecimal.valueOf(d2);
            }

            if (ao != null) {
               if (ao == AdditiveOperator.PLUS_SIGN) {
                  oValue = bd1.add(bd2);
               } else if (ao == AdditiveOperator.MINUS_SIGN) {
                  oValue = bd1.subtract(bd2);
               }
            } else if (mo != null) {
               if (mo == MultiplicativeOperator.ASTERISK) {
                  oValue = bd1.multiply(bd2);
               } else if (mo == MultiplicativeOperator.SOLIDUS) {
                  oValue = bd1.divide(bd2);
               }
            }
         }
      }

      return oValue;
   }

   private Object evaluateSign(Sign sign, Object oValue) {
      if (Sign.MINUS_SIGN.equals(sign)) {
         if (oValue instanceof BigDecimal) {
            BigDecimal bdValue = (BigDecimal)oValue;
            oValue = bdValue.negate();
         } else if (oValue instanceof Double) {
            Double dValue = (Double)oValue;
            oValue = -dValue;
         }
      }

      return oValue;
   }

   public int evaluateInteger(Object oValue) {
      int iValue = 1;
       if (oValue instanceof BigDecimal) {
         iValue = ((BigDecimal)oValue).intValueExact();
      } else {
         if (!(oValue instanceof Double)) {
            throw new IllegalArgumentException("Numeric value could not be cast to int!");
         }

         iValue = ((Double)oValue).intValue();
      }

      return iValue;
   }

   private Object evaluate(Object oFirstOperand, Object oSecondOperand, Object oParenthesized, Object oValuePrimary, Object oNumericValue) {
      Object oValue = null;
      if (this.getFirstOperand() != null && this.getSecondOperand() != null) {
         oValue = this.evaluateOperator(this.getAdditiveOperator(), this.getMultiplicativeOperator(), oFirstOperand, oSecondOperand);
      } else if (this.getParenthesizedExpression() != null) {
         oValue = oParenthesized;
      } else if (this.getValueExpressionPrimary() != null) {
         oValue = this.evaluateSign(this.getSign(), oValuePrimary);
      } else if (this.getNumericValueFunction() != null) {
         oValue = oNumericValue;
      }

      return oValue;
   }

   public Object evaluate(SqlStatement ss, boolean bAggregated) {
      Object oFirstOperand = null;
      if (this.getFirstOperand() != null) {
         oFirstOperand = this.getFirstOperand().evaluate(ss, bAggregated);
      }

      Object oSecondOperand = null;
      if (this.getSecondOperand() != null) {
         oSecondOperand = this.getSecondOperand().evaluate(ss, bAggregated);
      }

      Object oParenthesized = null;
      if (this.getParenthesizedExpression() != null) {
         oParenthesized = this.getParenthesizedExpression().evaluate(ss, bAggregated);
      }

      Object oValuePrimary = null;
      if (this.getValueExpressionPrimary() != null) {
         oValuePrimary = this.getValueExpressionPrimary().evaluate(ss, bAggregated);
      }

      Object oNumericValue = null;
      if (this.getNumericValueFunction() != null) {
         oNumericValue = this.getNumericValueFunction().evaluate(ss, bAggregated);
      }

      Object oValue = this.evaluate(oFirstOperand, oSecondOperand, oParenthesized, oValuePrimary, oNumericValue);
      return oValue;
   }

   public Object resetAggregates(SqlStatement ss) {
      Object oFirstOperand = null;
      if (this.getFirstOperand() != null) {
         oFirstOperand = this.getFirstOperand().resetAggregates(ss);
      }

      Object oSecondOperand = null;
      if (this.getSecondOperand() != null) {
         oSecondOperand = this.getSecondOperand().resetAggregates(ss);
      }

      Object oParenthesized = null;
      if (this.getParenthesizedExpression() != null) {
         oParenthesized = this.getParenthesizedExpression().resetAggregates(ss);
      }

      Object oValuePrimary = null;
      if (this.getValueExpressionPrimary() != null) {
         oValuePrimary = this.getValueExpressionPrimary().resetAggregates(ss);
      }

      Object oNumericValue = null;
      if (this.getNumericValueFunction() != null) {
         oNumericValue = this.getNumericValueFunction().resetAggregates(ss);
      }

      Object oValue = this.evaluate(oFirstOperand, oSecondOperand, oParenthesized, oValuePrimary, oNumericValue);
      return oValue;
   }

   public void parse(SqlParser.NumericValueExpressionContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().numericValueExpression());
   }

   public void initialize(AdditiveOperator ao, MultiplicativeOperator mo, NumericValueExpression nve1, NumericValueExpression nve2, NumericValueExpression nve, Sign sign, ValueExpressionPrimary vep, NumericValueFunction nvf) {
      _il.enter(new Object[]{ao, mo, nve1, nve2, sign, vep, nvf});
      this.setAdditiveOperator(ao);
      this.setMultiplicativeOperator(mo);
      this.setFirstOperand(nve1);
      this.setSecondOperand(nve2);
      this.setParenthesizedExpression(nve);
      this.setSign(sign);
      this.setValueExpressionPrimary(vep);
      this.setNumericValueFunction(nvf);
      _il.exit();
   }

   public NumericValueExpression(SqlFactory sf) {
      super(sf);
   }

   private class NveVisitor extends EnhancedSqlBaseVisitor<NumericValueExpression> {
      private NveVisitor() {
      }

      public NumericValueExpression visitNumericValueExpression(SqlParser.NumericValueExpressionContext ctx) {
         NumericValueExpression nveReturn = NumericValueExpression.this;
         if (ctx.numericValueExpression().size() == 2) {
            if (ctx.additiveOperator() != null) {
               NumericValueExpression.this.setAdditiveOperator(this.getAdditiveOperator(ctx.additiveOperator()));
            } else if (ctx.multiplicativeOperator() != null) {
               NumericValueExpression.this.setMultiplicativeOperator(this.getMultiplicativeOperator(ctx.multiplicativeOperator()));
            }

            NumericValueExpression.this.setFirstOperand(NumericValueExpression.this.getSqlFactory().newNumericValueExpression());
            NumericValueExpression.this.getFirstOperand().parse(ctx.numericValueExpression(0));
            NumericValueExpression.this.setSecondOperand(NumericValueExpression.this.getSqlFactory().newNumericValueExpression());
            NumericValueExpression.this.getSecondOperand().parse(ctx.numericValueExpression(1));
         } else if (ctx.LEFT_PAREN() != null && ctx.RIGHT_PAREN() != null) {
            NumericValueExpression.this.setParenthesizedExpression(NumericValueExpression.this.getSqlFactory().newNumericValueExpression());
            NumericValueExpression.this.getParenthesizedExpression().parse(ctx.numericValueExpression(0));
         } else {
            if (ctx.sign() != null) {
               NumericValueExpression.this.setSign(this.getSign(ctx.sign()));
            }

            nveReturn = (NumericValueExpression)this.visitChildren(ctx);
         }

         return nveReturn;
      }

      public NumericValueExpression visitValueExpressionPrimary(SqlParser.ValueExpressionPrimaryContext ctx) {
         NumericValueExpression.this.setValueExpressionPrimary(NumericValueExpression.this.getSqlFactory().newValueExpressionPrimary());
         NumericValueExpression.this.getValueExpressionPrimary().parse(ctx);
         return NumericValueExpression.this;
      }

      public NumericValueExpression visitNumericValueFunction(SqlParser.NumericValueFunctionContext ctx) {
         NumericValueExpression.this.setNumericValueFunction(NumericValueExpression.this.getSqlFactory().newNumericValueFunction());
         NumericValueExpression.this.getNumericValueFunction().parse(ctx);
         return NumericValueExpression.this;
      }

      // $FF: synthetic method
      NveVisitor(Object x1) {
         this();
      }
   }
}
