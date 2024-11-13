package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;

public class TableRowValueExpression extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(TableRowValueExpression.class.getName());
   private TableRowValueExpression.TrveVisitor _visitor = new TableRowValueExpression.TrveVisitor();
   private CommonValueExpression _cve = null;
   private BooleanValueExpression _bve = null;
   private RowValueExpression _rve = null;
   private ValueExpressionPrimary _vep = null;

   private TableRowValueExpression.TrveVisitor getVisitor() {
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

   public ValueExpressionPrimary getValueExpressionPrimary() {
      return this._vep;
   }

   public void setValueExpressionPrimary(ValueExpressionPrimary vep) {
      this._vep = vep;
   }

   public String format() {
      String sExpression = null;
      if (this.getCommonValueExpression() != null) {
         sExpression = this.getCommonValueExpression().format();
      } else if (this.getBooleanValueExpression() != null) {
         sExpression = this.getBooleanValueExpression().format();
      } else if (this.getRowValueExpression() != null) {
         sExpression = this.getRowValueExpression().format();
      } else if (this.getValueExpressionPrimary() != null) {
         sExpression = this.getValueExpressionPrimary().format();
      }

      return sExpression;
   }

   public void parse(SqlParser.TableRowValueExpressionContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().tableRowValueExpression());
   }

   public void initialize(CommonValueExpression cve, BooleanValueExpression bve, RowValueExpression rve, ValueExpressionPrimary vep) {
      _il.enter(new Object[]{cve, bve, rve});
      this.setCommonValueExpression(cve);
      this.setBooleanValueExpression(bve);
      this.setRowValueExpression(rve);
      this.setValueExpressionPrimary(vep);
      _il.exit();
   }

   public TableRowValueExpression(SqlFactory sf) {
      super(sf);
   }

   private class TrveVisitor extends EnhancedSqlBaseVisitor<TableRowValueExpression> {
      private TrveVisitor() {
      }

      public TableRowValueExpression visitCommonValueExpression(SqlParser.CommonValueExpressionContext ctx) {
         TableRowValueExpression.this.setCommonValueExpression(TableRowValueExpression.this.getSqlFactory().newCommonValueExpression());
         TableRowValueExpression.this.getCommonValueExpression().parse(ctx);
         return TableRowValueExpression.this;
      }

      public TableRowValueExpression visitBooleanValueExpression(SqlParser.BooleanValueExpressionContext ctx) {
         TableRowValueExpression.this.setBooleanValueExpression(TableRowValueExpression.this.getSqlFactory().newBooleanValueExpression());
         TableRowValueExpression.this.getBooleanValueExpression().parse(ctx);
         return TableRowValueExpression.this;
      }

      public TableRowValueExpression visitRowValueExpression(SqlParser.RowValueExpressionContext ctx) {
         TableRowValueExpression.this.setRowValueExpression(TableRowValueExpression.this.getSqlFactory().newRowValueExpression());
         TableRowValueExpression.this.getRowValueExpression().parse(ctx);
         return TableRowValueExpression.this;
      }

      public TableRowValueExpression visitValueExpressionPrimary(SqlParser.ValueExpressionPrimaryContext ctx) {
         TableRowValueExpression.this.setValueExpressionPrimary(TableRowValueExpression.this.getSqlFactory().newValueExpressionPrimary());
         TableRowValueExpression.this.getValueExpressionPrimary().parse(ctx);
         return TableRowValueExpression.this;
      }

      // $FF: synthetic method
      TrveVisitor(Object x1) {
         this();
      }
   }
}
