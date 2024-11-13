package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.SqlStatement;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;

public class RowValuePredicand extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(RowValuePredicand.class.getName());
   private RowValuePredicand.RvpVisitor _visitor = new RowValuePredicand.RvpVisitor();
   private RowValueExpression _rve = null;
   private CommonValueExpression _cve = null;

   private RowValuePredicand.RvpVisitor getVisitor() {
      return this._visitor;
   }

   public RowValueExpression getRowValueExpression() {
      return this._rve;
   }

   public void setRowValueExpression(RowValueExpression rve) {
      this._rve = rve;
   }

   public CommonValueExpression getCommonValueExpression() {
      return this._cve;
   }

   public void setCommonValueExpression(CommonValueExpression cve) {
      this._cve = cve;
   }

   public String format() {
      String sPredicand = null;
      if (this.getRowValueExpression() != null) {
         sPredicand = this.getRowValueExpression().format();
      } else if (this.getCommonValueExpression() != null) {
         sPredicand = this.getCommonValueExpression().format();
      }

      return sPredicand;
   }

   private Object evaluate(Object oCommonValue) {
      Object oValue = null;
      if (this.getRowValueExpression() != null) {
         throw new IllegalArgumentException("Row value expression cannot be evaluated!");
      } else {
         if (this.getCommonValueExpression() != null) {
            oValue = oCommonValue;
         }

         return oValue;
      }
   }

   public Object evaluate(SqlStatement ss, boolean bAggregated) {
      Object oCommonValue = null;
      if (this.getCommonValueExpression() != null) {
         oCommonValue = this.getCommonValueExpression().evaluate(ss, bAggregated);
      }

      Object oValue = this.evaluate(oCommonValue);
      return oValue;
   }

   public Object resetAggregates(SqlStatement ss) {
      Object oCommonValue = null;
      if (this.getCommonValueExpression() != null) {
         oCommonValue = this.getCommonValueExpression().resetAggregates(ss);
      }

      Object oValue = this.evaluate(oCommonValue);
      return oValue;
   }

   public void parse(SqlParser.RowValuePredicandContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().rowValuePredicand());
   }

   public void initialize(RowValueExpression rve, CommonValueExpression cve) {
      _il.enter(new Object[]{rve, cve});
      this.setRowValueExpression(rve);
      this.setCommonValueExpression(cve);
      _il.exit();
   }

   public RowValuePredicand(SqlFactory sf) {
      super(sf);
   }

   private class RvpVisitor extends EnhancedSqlBaseVisitor<RowValuePredicand> {
      private RvpVisitor() {
      }

      public RowValuePredicand visitRowValueExpression(SqlParser.RowValueExpressionContext ctx) {
         RowValuePredicand.this.setRowValueExpression(RowValuePredicand.this.getSqlFactory().newRowValueExpression());
         RowValuePredicand.this.getRowValueExpression().parse(ctx);
         return RowValuePredicand.this;
      }

      public RowValuePredicand visitCommonValueExpression(SqlParser.CommonValueExpressionContext ctx) {
         RowValuePredicand.this.setCommonValueExpression(RowValuePredicand.this.getSqlFactory().newCommonValueExpression());
         RowValuePredicand.this.getCommonValueExpression().parse(ctx);
         return RowValuePredicand.this;
      }

      // $FF: synthetic method
      RvpVisitor(Object x1) {
         this();
      }
   }
}
