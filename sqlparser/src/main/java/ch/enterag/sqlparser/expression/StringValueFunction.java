package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.SqlLiterals;
import ch.enterag.sqlparser.SqlStatement;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.expression.enums.StringFunction;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;
import java.text.ParseException;
import java.util.Arrays;

public class StringValueFunction extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(StringValueFunction.class.getName());
   private StringValueFunction.SvfVisitor _visitor = new StringValueFunction.SvfVisitor();
   private StringFunction _sf = null;
   private StringValueExpression _sve = null;
   private NumericValueExpression _nveStart = null;
   private NumericValueExpression _nveLength = null;
   private StringValueExpression _svePattern = null;
   private String _sEscapeLetter = null;
   private ValueExpressionPrimary _vepSpecific;

   private StringValueFunction.SvfVisitor getVisitor() {
      return this._visitor;
   }

   public StringFunction getStringFunction() {
      return this._sf;
   }

   public void setStringFunction(StringFunction sf) {
      this._sf = sf;
   }

   public StringValueExpression getStringValueExpression() {
      return this._sve;
   }

   public void setStringValueExpression(StringValueExpression sve) {
      this._sve = sve;
   }

   public NumericValueExpression getStartPosition() {
      return this._nveStart;
   }

   public void setStartPosition(NumericValueExpression nveStart) {
      this._nveStart = nveStart;
   }

   public NumericValueExpression getLength() {
      return this._nveLength;
   }

   public void setLength(NumericValueExpression nveLength) {
      this._nveLength = nveLength;
   }

   public StringValueExpression getPattern() {
      return this._svePattern;
   }

   public void setPattern(StringValueExpression svePattern) {
      this._svePattern = svePattern;
   }

   public String getEscapeLetter() {
      return this._sEscapeLetter;
   }

   public void setEscapeLetter(String sEscapeLetter) {
      this._sEscapeLetter = sEscapeLetter;
   }

   public ValueExpressionPrimary getUdtValueSpecific() {
      return this._vepSpecific;
   }

   public void setUdtValueSpecific(ValueExpressionPrimary vepSpecific) {
      this._vepSpecific = vepSpecific;
   }

   public String format() {
      String sFunction = null;
      if (this.getStringFunction() != null && this.getStringValueExpression() != null) {
         sFunction = this.getStringFunction().getKeywords() + "(" + this.getStringValueExpression().format();
         if (this.getStartPosition() != null) {
            sFunction = sFunction + " " + K.FROM.getKeyword() + " " + this.getStartPosition().format();
            if (this.getLength() != null) {
               sFunction = sFunction + " " + K.FOR.getKeyword() + " " + this.getLength().format();
            }
         } else if (this.getPattern() != null) {
            sFunction = sFunction + " " + K.SIMILAR.getKeyword() + " " + this.getPattern().format();
            if (this.getEscapeLetter() != null) {
               sFunction = sFunction + " " + K.ESCAPE.getKeyword() + " " + SqlLiterals.formatStringLiteral(this.getEscapeLetter());
            }
         }

         sFunction = sFunction + ")";
      } else {
         sFunction = this.getUdtValueSpecific().format() + "." + K.SPECIFICTYPE.getKeyword();
      }

      return sFunction;
   }

   public DataType getDataType(SqlStatement ss) {
      DataType dt = null;
      if (this.getStringFunction() != null && this.getStringValueExpression() != null) {
         DataType dtArgument = this.getStringValueExpression().getDataType(ss);
         switch(this.getStringFunction()) {
         case LOWER:
         case UPPER:
         case TRIM:
         case SUBSTRING:
            dt = dtArgument;
         default:
            return dt;
         case NORMALIZE:
            throw new IllegalArgumentException("String function NORMALIZE is not supported for evaluation!");
         }
      } else {
         throw new IllegalArgumentException("Evaluation of UDT methods not yet supported!");
      }
   }

   private Object evaluate(Object oStringValue, Object oStartPosition, Object oLength) {
      Object oValue = null;
      if (this.getStringFunction() != null && this.getStringValueExpression() != null) {
         String s = null;
         if (oStringValue instanceof String) {
            s = (String)oStringValue;
         }

         byte[] buf = null;
         if (oStringValue instanceof byte[]) {
            buf = (byte[])((byte[])oStringValue);
         }

         switch(this.getStringFunction()) {
         case LOWER:
            if (s == null) {
               throw new IllegalArgumentException("LOWER cannot be applid to binary string!");
            }

            oValue = s.toLowerCase();
            break;
         case UPPER:
            if (s == null) {
               throw new IllegalArgumentException("UPPER cannot be applid to binary string!");
            }

            oValue = s.toUpperCase();
            break;
         case TRIM:
            if (s == null) {
               throw new IllegalArgumentException("TRIM cannot be applid to binary string!");
            }

            oValue = s.trim();
            break;
         case SUBSTRING:
            if (this.getStartPosition() == null) {
               throw new IllegalArgumentException("String function SUBSTRING(s SIMILAR p ESCAPE e) is not supported for evaluation!");
            }

            int iStart = this.getStartPosition().evaluateInteger(oStartPosition) - 1;
            int iLength = this.getLength().evaluateInteger(oLength);
            if (buf != null) {
               oValue = Arrays.copyOfRange(buf, iStart, iStart + iLength);
            }

            if (s != null) {
               oValue = s.substring(iStart, iStart + iLength);
            }
            break;
         case NORMALIZE:
            throw new IllegalArgumentException("String function NORMALIZE is not supported for evaluation!");
         }

         return oValue;
      } else {
         throw new IllegalArgumentException("Evaluation of UDT methods not yet supported!");
      }
   }

   public Object evaluate(SqlStatement ss, boolean bAggregated) {
      Object oStringValue = null;
      if (this.getStringValueExpression() != null) {
         oStringValue = this.getStringValueExpression().evaluate(ss, bAggregated);
      }

      Object oStartPosition = null;
      if (this.getStartPosition() != null) {
         oStartPosition = this.getStartPosition().evaluate(ss, bAggregated);
      }

      Object oLength = null;
      if (this.getLength() != null) {
         oLength = this.getLength().evaluate(ss, bAggregated);
      }

      Object oValue = this.evaluate(oStringValue, oStartPosition, oLength);
      return oValue;
   }

   public Object resetAggregates(SqlStatement ss) {
      Object oStringValue = null;
      if (this.getStringValueExpression() != null) {
         oStringValue = this.getStringValueExpression().resetAggregates(ss);
      }

      Object oStartPosition = null;
      if (this.getStartPosition() != null) {
         oStartPosition = this.getStartPosition().resetAggregates(ss);
      }

      Object oLength = null;
      if (this.getLength() != null) {
         oLength = this.getLength().resetAggregates(ss);
      }

      Object oValue = this.evaluate(oStringValue, oStartPosition, oLength);
      return oValue;
   }

   public void parse(SqlParser.StringValueFunctionContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().stringValueFunction());
   }

   public void initialize(StringFunction sf, StringValueExpression sve, NumericValueExpression nveStart, NumericValueExpression nveLength, StringValueExpression svePattern, String sEscapeLetter, ValueExpressionPrimary vepSpecific) {
      _il.enter(new Object[]{sf, sve, nveStart, nveLength, svePattern, sEscapeLetter, vepSpecific});
      this.setStringFunction(sf);
      this.setStringValueExpression(sve);
      this.setStartPosition(nveStart);
      this.setLength(nveLength);
      this.setPattern(svePattern);
      this.setEscapeLetter(sEscapeLetter);
      this.setUdtValueSpecific(vepSpecific);
      _il.exit();
   }

   public StringValueFunction(SqlFactory sf) {
      super(sf);
   }

   private class SvfVisitor extends EnhancedSqlBaseVisitor<StringValueFunction> {
      private SvfVisitor() {
      }

      public StringValueFunction visitStringValueFunction(SqlParser.StringValueFunctionContext ctx) {
         StringValueFunction svfReturn = StringValueFunction.this;
         StringValueFunction.this.setStringFunction(this.getStringFunction(ctx));
         if (svfReturn.getStringFunction() != null) {
            StringValueFunction.this.setStringValueExpression(StringValueFunction.this.getSqlFactory().newStringValueExpression());
            StringValueFunction.this.getStringValueExpression().parse(ctx.stringValueExpression(0));
            if (svfReturn.getStringFunction() == StringFunction.SUBSTRING) {
               if (ctx.SIMILAR() != null) {
                  StringValueFunction.this.setPattern(StringValueFunction.this.getSqlFactory().newStringValueExpression());
                  StringValueFunction.this.getPattern().parse(ctx.stringValueExpression(1));
                  if (ctx.CHARACTER_STRING_LITERAL() != null) {
                     try {
                        StringValueFunction.this.setEscapeLetter(SqlLiterals.parseStringLiteral(ctx.CHARACTER_STRING_LITERAL().getText()));
                     } catch (ParseException var4) {
                        throw new IllegalArgumentException("Error visiting escape letter!", var4);
                     }
                  }
               } else {
                  StringValueFunction.this.setStartPosition(StringValueFunction.this.getSqlFactory().newNumericValueExpression());
                  StringValueFunction.this.getStartPosition().parse(ctx.startPosition().numericValueExpression());
                  StringValueFunction.this.setLength(StringValueFunction.this.getSqlFactory().newNumericValueExpression());
                  StringValueFunction.this.getLength().parse(ctx.stringLength().numericValueExpression());
               }
            }
         } else {
            svfReturn = (StringValueFunction)this.visitChildren(ctx);
         }

         return svfReturn;
      }

      public StringValueFunction visitValueExpressionPrimary(SqlParser.ValueExpressionPrimaryContext ctx) {
         StringValueFunction.this.setUdtValueSpecific(StringValueFunction.this.getSqlFactory().newValueExpressionPrimary());
         StringValueFunction.this.getUdtValueSpecific().parse(ctx);
         return StringValueFunction.this;
      }

      // $FF: synthetic method
      SvfVisitor(Object x1) {
         this();
      }
   }
}
