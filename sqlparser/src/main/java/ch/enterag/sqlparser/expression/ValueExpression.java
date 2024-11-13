package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.SqlStatement;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;

public class ValueExpression extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(ValueExpression.class.getName());
   private ValueExpression.VeVisitor _visitor = new ValueExpression.VeVisitor();
   private CommonValueExpression _cve = null;
   private BooleanValueExpression _bve = null;
   private RowValueExpression _rve = null;

   private ValueExpression.VeVisitor getVisitor() {
      return this._visitor;
   }

   public CommonValueExpression getCommonValueExpression() {
      return this._cve;
   }

   public void setCommonValueExpression(CommonValueExpression cve) {
      this._cve = cve;
   }

   public BooleanValueExpression getBooleanValueExpression() {
      return this._bve;
   }

   public void setBooleanValueExpression(BooleanValueExpression bve) {
      this._bve = bve;
   }

   public RowValueExpression getRowValueExpression() {
      return this._rve;
   }

   public void setRowValueExpression(RowValueExpression rve) {
      this._rve = rve;
   }

   public String format() {
      String sExpression = null;
      if (this.getCommonValueExpression() != null) {
         sExpression = this.getCommonValueExpression().format();
      } else if (this.getBooleanValueExpression() != null) {
         sExpression = this.getBooleanValueExpression().format();
      } else if (this.getRowValueExpression() != null) {
         sExpression = this.getRowValueExpression().format();
      }

      return sExpression;
   }

   public DataType getDataType(SqlStatement ss) {
      DataType dt = null;
      if (this.getCommonValueExpression() != null) {
         dt = this.getCommonValueExpression().getDataType(ss);
      } else if (this.getBooleanValueExpression() != null) {
         dt = this.getBooleanValueExpression().getDataType(ss);
      } else if (this.getRowValueExpression() != null) {
         dt = this.getRowValueExpression().getDataType(ss);
      }

      return dt;
   }

   private Object evaluate(Object oCommonValue, Boolean bBooleanValue, Object oRowValue) {
      Object oValue = null;
      if (this.getCommonValueExpression() != null) {
         oValue = oCommonValue;
      } else if (this.getBooleanValueExpression() != null) {
         oValue = bBooleanValue;
      } else if (this.getRowValueExpression() != null) {
         oValue = oRowValue;
      }

      return oValue;
   }

   public Object evaluate(SqlStatement ss, boolean bAggregated) {
      Object oCommonValue = null;
      if (this.getCommonValueExpression() != null) {
         oCommonValue = this.getCommonValueExpression().evaluate(ss, bAggregated);
      }

      Boolean bBooleanValue = null;
      if (this.getBooleanValueExpression() != null) {
         bBooleanValue = this.getBooleanValueExpression().evaluate(ss, bAggregated);
      }

      Object oRowValue = null;
      if (this.getRowValueExpression() != null) {
         oRowValue = this.getRowValueExpression().evaluate(ss, bAggregated);
      }

      Object oValue = this.evaluate(oCommonValue, bBooleanValue, oRowValue);
      return oValue;
   }

   public Object resetAggregates(SqlStatement ss) {
      Object oCommonValue = null;
      if (this.getCommonValueExpression() != null) {
         oCommonValue = this.getCommonValueExpression().resetAggregates(ss);
      }

      Boolean bBooleanValue = null;
      if (this.getBooleanValueExpression() != null) {
         bBooleanValue = this.getBooleanValueExpression().resetAggregates(ss);
      }

      Object oRowValue = null;
      if (this.getRowValueExpression() != null) {
         oRowValue = this.getRowValueExpression().resetAggregates(ss);
      }

      Object oValue = this.evaluate(oCommonValue, bBooleanValue, oRowValue);
      return oValue;
   }

   public void parse(SqlParser.ValueExpressionContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().valueExpression());
   }

   public void initialize(CommonValueExpression cve, BooleanValueExpression bve, RowValueExpression rve) {
      _il.enter(new Object[]{cve, bve, rve});
      this.setCommonValueExpression(cve);
      this.setBooleanValueExpression(bve);
      this.setRowValueExpression(rve);
      _il.exit();
   }

   public ValueExpression(SqlFactory sf) {
      super(sf);
   }

   private class VeVisitor extends EnhancedSqlBaseVisitor<ValueExpression> {
      private VeVisitor() {
      }

      public ValueExpression visitCommonValueExpression(SqlParser.CommonValueExpressionContext ctx) {
         ValueExpression.this.setCommonValueExpression(ValueExpression.this.getSqlFactory().newCommonValueExpression());
         ValueExpression.this.getCommonValueExpression().parse(ctx);
         return ValueExpression.this;
      }

      public ValueExpression visitBooleanValueExpression(SqlParser.BooleanValueExpressionContext ctx) {
         ValueExpression.this.setBooleanValueExpression(ValueExpression.this.getSqlFactory().newBooleanValueExpression());
         ValueExpression.this.getBooleanValueExpression().parse(ctx);
         return ValueExpression.this;
      }

      public ValueExpression visitRowValueExpression(SqlParser.RowValueExpressionContext ctx) {
         ValueExpression.this.setRowValueExpression(ValueExpression.this.getSqlFactory().newRowValueExpression());
         ValueExpression.this.getRowValueExpression().parse(ctx);
         return ValueExpression.this;
      }

      // $FF: synthetic method
      VeVisitor(Object x1) {
         this();
      }
   }
}
