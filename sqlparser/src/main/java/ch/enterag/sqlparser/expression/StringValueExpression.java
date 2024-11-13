package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.SqlStatement;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.datatype.PredefinedType;
import ch.enterag.sqlparser.ddl.enums.Multiplier;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;

public class StringValueExpression extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(StringValueExpression.class.getName());
   private StringValueExpression.SveVisitor _visitor = new StringValueExpression.SveVisitor();
   private boolean _bConcatenation = false;
   private StringValueExpression _sve1 = null;
   private StringValueExpression _sve2 = null;
   private ValueExpressionPrimary _vep = null;
   private StringValueFunction _svf = null;

   private StringValueExpression.SveVisitor getVisitor() {
      return this._visitor;
   }

   public boolean isConcatenation() {
      return this._bConcatenation;
   }

   public void setConcatenation(boolean bConcatenation) {
      this._bConcatenation = bConcatenation;
   }

   public StringValueExpression getFirstOperand() {
      return this._sve1;
   }

   public void setFirstOperand(StringValueExpression sve1) {
      this._sve1 = sve1;
   }

   public StringValueExpression getSecondOperand() {
      return this._sve2;
   }

   public void setSecondOperand(StringValueExpression sve2) {
      this._sve2 = sve2;
   }

   public ValueExpressionPrimary getValueExpressionPrimary() {
      return this._vep;
   }

   public void setValueExpressionPrimary(ValueExpressionPrimary vep) {
      this._vep = vep;
   }

   public StringValueFunction getStringValueFunction() {
      return this._svf;
   }

   public void setStringValueFunction(StringValueFunction svf) {
      this._svf = svf;
   }

   public String format() {
      String sExpression = null;
      if (this.isConcatenation()) {
         sExpression = this.getFirstOperand().format() + " " + "||" + " " + this.getSecondOperand().format();
      } else if (this.getValueExpressionPrimary() != null) {
         sExpression = this.getValueExpressionPrimary().format();
      } else if (this.getStringValueFunction() != null) {
         sExpression = this.getStringValueFunction().format();
      }

      return sExpression;
   }

   public DataType getDataType(SqlStatement ss) {
      DataType dt = null;
      if (this.isConcatenation()) {
         DataType dt1 = this.getFirstOperand().getDataType(ss);
         DataType dt2 = this.getSecondOperand().getDataType(ss);
         if (dt1 != null && dt2 != null) {
            PredefinedType pt1 = dt1.getPredefinedType();
            PredefinedType pt2 = dt2.getPredefinedType();
            if (pt1 != null && pt2 != null) {
               long lLength1 = (long)pt1.getLength();
               if (pt1.getMultiplier() != null) {
                  lLength1 *= (long)pt1.getMultiplier().getValue();
               }

               long lLength2 = (long)pt2.getLength();
               if (pt2.getMultiplier() != null) {
                  lLength2 *= (long)pt2.getMultiplier().getValue();
               }

               long lLength = lLength1 + lLength2;
               Multiplier m = null;
               if (lLength > 2147483647L) {
                  if (lLength < (long)Multiplier.K.getValue() * 2147483647L) {
                     m = Multiplier.K;
                     lLength = (lLength + (long)Multiplier.K.getValue() - 1L) / (long)Multiplier.K.getValue();
                  } else if (lLength < (long)Multiplier.M.getValue() * 2147483647L) {
                     m = Multiplier.M;
                     lLength = (lLength + (long)Multiplier.M.getValue() - 1L) / (long)Multiplier.M.getValue();
                  } else if (lLength < (long)Multiplier.G.getValue() * 2147483647L) {
                     m = Multiplier.G;
                     lLength = (lLength + (long)Multiplier.G.getValue() - 1L) / (long)Multiplier.G.getValue();
                  }
               }

               dt = this.getSqlFactory().newDataType();
               PredefinedType pt = this.getSqlFactory().newPredefinedType();
               dt.initPredefinedDataType(pt);
               switch(pt1.getType()) {
               case CHAR:
               case NCHAR:
                  switch(pt2.getType()) {
                  case CHAR:
                  case NCHAR:
                     if (m != null) {
                        throw new IllegalArgumentException("CHAR length overflow!");
                     }

                     pt.initCharType((int)lLength);
                     return dt;
                  case VARCHAR:
                  case NVARCHAR:
                     if (m != null) {
                        throw new IllegalArgumentException("VARCHAR length overflow!");
                     }

                     pt.initVarCharType((int)lLength);
                     return dt;
                  case CLOB:
                  case NCLOB:
                  case XML:
                     pt.initClobType((int)lLength, m);
                     return dt;
                  default:
                     throw new IllegalArgumentException("Unexpected data type for string value expression: " + pt2.getType().getKeyword() + "!");
                  }
               case VARCHAR:
               case NVARCHAR:
                  switch(pt2.getType()) {
                  case CHAR:
                  case NCHAR:
                  case VARCHAR:
                  case NVARCHAR:
                     if (m != null) {
                        throw new IllegalArgumentException("VARCHAR length overflow!");
                     }

                     pt.initVarCharType((int)lLength);
                     return dt;
                  case CLOB:
                  case NCLOB:
                  case XML:
                     pt.initClobType((int)lLength, m);
                     return dt;
                  default:
                     throw new IllegalArgumentException("Unexpected data type for string value expression: " + pt2.getType().getKeyword() + "!");
                  }
               case CLOB:
               case NCLOB:
                  switch(pt2.getType()) {
                  case CHAR:
                  case NCHAR:
                  case VARCHAR:
                  case NVARCHAR:
                  case CLOB:
                  case NCLOB:
                  case XML:
                     pt.initClobType((int)lLength, m);
                     return dt;
                  default:
                     throw new IllegalArgumentException("Unexpected data type for string value expression: " + pt2.getType().getKeyword() + "!");
                  }
               case XML:
                  switch(pt2.getType()) {
                  case CHAR:
                  case NCHAR:
                  case VARCHAR:
                  case NVARCHAR:
                  case CLOB:
                  case NCLOB:
                     pt.initClobType((int)lLength, m);
                     return dt;
                  case XML:
                     pt.initXmlType();
                     pt.setLength((int)lLength);
                     pt.setMultiplier(m);
                     return dt;
                  default:
                     throw new IllegalArgumentException("Unexpected data type for string value expression: " + pt2.getType().getKeyword() + "!");
                  }
               default:
                  throw new IllegalArgumentException("Unexpected data type for string value expresseion: " + pt1.getType().getKeyword() + "!");
               }
            }
         }
      } else if (this.getValueExpressionPrimary() != null) {
         dt = this.getValueExpressionPrimary().getDataType(ss);
      } else if (this.getStringValueFunction() != null) {
         dt = this.getStringValueFunction().getDataType(ss);
      }

      return dt;
   }

   private Object evaluate(Object oFirstOperand, Object oSecondOperand, Object oValuePrimary, Object oStringValue) {
      Object oValue = null;
      if (this.isConcatenation()) {
         if (oFirstOperand instanceof String && oSecondOperand instanceof String) {
            String s1 = (String)oFirstOperand;
            String s2 = (String)oSecondOperand;
            oValue = s1 + s2;
         } else if (oFirstOperand instanceof byte[] && oSecondOperand instanceof byte[]) {
            byte[] buf1 = (byte[])((byte[])oFirstOperand);
            byte[] buf2 = (byte[])((byte[])oSecondOperand);
            byte[] buf = new byte[buf1.length + buf2.length];
            System.arraycopy(buf1, 0, buf, 0, buf1.length);
            System.arraycopy(buf2, 0, buf, buf1.length, buf2.length);
            oValue = buf;
         }
      } else if (this.getValueExpressionPrimary() != null) {
         oValue = oValuePrimary;
      } else if (this.getStringValueFunction() != null) {
         oValue = oStringValue;
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

      Object oValuePrimary = null;
      if (this.getValueExpressionPrimary() != null) {
         oValuePrimary = this.getValueExpressionPrimary().evaluate(ss, bAggregated);
      }

      Object oStringValue = null;
      if (this.getStringValueFunction() != null) {
         oStringValue = this.getStringValueFunction().evaluate(ss, bAggregated);
      }

      Object oValue = this.evaluate(oFirstOperand, oSecondOperand, oValuePrimary, oStringValue);
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

      Object oValuePrimary = null;
      if (this.getValueExpressionPrimary() != null) {
         oValuePrimary = this.getValueExpressionPrimary().resetAggregates(ss);
      }

      Object oStringValue = null;
      if (this.getStringValueFunction() != null) {
         oStringValue = this.getStringValueFunction().resetAggregates(ss);
      }

      Object oValue = this.evaluate(oFirstOperand, oSecondOperand, oValuePrimary, oStringValue);
      return oValue;
   }

   public void parse(SqlParser.StringValueExpressionContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().stringValueExpression());
   }

   public void initialize(boolean bConcatenation, StringValueExpression sve1, StringValueExpression sve2, ValueExpressionPrimary vep, StringValueFunction svf) {
      _il.enter(new Object[]{String.valueOf(bConcatenation), sve1, sve2, vep, svf});
      this.setConcatenation(bConcatenation);
      this.setFirstOperand(sve1);
      this.setSecondOperand(sve2);
      this.setValueExpressionPrimary(vep);
      this.setStringValueFunction(svf);
      _il.exit();
   }

   public StringValueExpression(SqlFactory sf) {
      super(sf);
   }

   private class SveVisitor extends EnhancedSqlBaseVisitor<StringValueExpression> {
      private SveVisitor() {
      }

      public StringValueExpression visitStringValueExpression(SqlParser.StringValueExpressionContext ctx) {
         if (ctx.CONCATENATION_OPERATOR() != null && ctx.stringValueExpression().size() == 2) {
            StringValueExpression.this.setConcatenation(true);
            StringValueExpression.this.setFirstOperand(StringValueExpression.this.getSqlFactory().newStringValueExpression());
            StringValueExpression.this.getFirstOperand().parse(ctx.stringValueExpression(0));
            StringValueExpression.this.setSecondOperand(StringValueExpression.this.getSqlFactory().newStringValueExpression());
            StringValueExpression.this.getSecondOperand().parse(ctx.stringValueExpression(1));
         } else if (ctx.valueExpressionPrimary() != null) {
            StringValueExpression.this.setValueExpressionPrimary(StringValueExpression.this.getSqlFactory().newValueExpressionPrimary());
            StringValueExpression.this.getValueExpressionPrimary().parse(ctx.valueExpressionPrimary());
         } else if (ctx.stringValueFunction() != null) {
            StringValueExpression.this.setStringValueFunction(StringValueExpression.this.getSqlFactory().newStringValueFunction());
            StringValueExpression.this.getStringValueFunction().parse(ctx.stringValueFunction());
         }

         return StringValueExpression.this;
      }

      // $FF: synthetic method
      SveVisitor(Object x1) {
         this();
      }
   }
}
