package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.SqlStatement;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class RowValueExpression extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(RowValueExpression.class.getName());
   private RowValueExpression.RveVisitor _visitor = new RowValueExpression.RveVisitor();
   private ValueExpressionPrimary _vep = null;
   private List<ValueExpression> _listVe = new ArrayList();
   private boolean _bRow = false;
   private QueryExpression _qe = null;

   private RowValueExpression.RveVisitor getVisitor() {
      return this._visitor;
   }

   public ValueExpressionPrimary getValueExpressionPrimary() {
      return this._vep;
   }

   public void setValueExpressionPrimary(ValueExpressionPrimary vep) {
      this._vep = vep;
   }

   public List<ValueExpression> getValueExpressions() {
      return this._listVe;
   }

   private void setValueExpressions(List<ValueExpression> listVe) {
      this._listVe = listVe;
   }

   public boolean isRow() {
      return this._bRow;
   }

   public void setRow(boolean bRow) {
      this._bRow = bRow;
   }

   public QueryExpression getQueryExpression() {
      return this._qe;
   }

   public void setQueryExpression(QueryExpression qe) {
      this._qe = qe;
   }

   public String format() {
      String sExpression = "";
      if (this.getValueExpressionPrimary() != null) {
         sExpression = this.getValueExpressionPrimary().format();
      } else if (this.getQueryExpression() != null) {
         sExpression = this.getQueryExpression().format();
      } else {
         if (this.isRow()) {
            sExpression = K.ROW.getKeyword();
         }

         if (this.getValueExpressions().size() > 0) {
            sExpression = sExpression + "(";

            for(int i = 0; i < this.getValueExpressions().size(); ++i) {
               if (i > 0) {
                  sExpression = sExpression + "," + " ";
               }

               sExpression = sExpression + ((ValueExpression)this.getValueExpressions().get(i)).format();
            }

            sExpression = sExpression + ")";
         }
      }

      return sExpression;
   }

   public DataType getDataType(SqlStatement ss) {
      DataType dt = null;
      if (this.getValueExpressionPrimary() != null) {
         dt = this.getValueExpressionPrimary().getDataType(ss);
      } else {
         if (this.getQueryExpression() != null) {
            throw new IllegalArgumentException("Query expression not supported for evaluation!");
         }

         if (this.isRow()) {
            throw new IllegalArgumentException("ROW expression not supported for evaluation!");
         }

         if (this.getValueExpressions().size() > 0) {
            throw new IllegalArgumentException("List of expressions not supported for evaluation!");
         }
      }

      return dt;
   }

   private Object evaluate(Object oValuePrimary) {
      Object oValue = null;
      if (this.getValueExpressionPrimary() != null) {
         oValue = oValuePrimary;
      } else {
         if (this.getQueryExpression() != null) {
            throw new IllegalArgumentException("Query expression not supported for evaluation!");
         }

         if (this.isRow()) {
            throw new IllegalArgumentException("ROW expression not supported for evaluation!");
         }

         if (this.getValueExpressions().size() > 0) {
            throw new IllegalArgumentException("List of expressions not supported for evaluation!");
         }
      }

      return oValue;
   }

   public Object evaluate(SqlStatement ss, boolean bAggregated) {
      Object oValuePrimary = null;
      if (this.getValueExpressionPrimary() != null) {
         oValuePrimary = this.getValueExpressionPrimary().evaluate(ss, bAggregated);
      }

      Object oValue = this.evaluate(oValuePrimary);
      return oValue;
   }

   public Object resetAggregates(SqlStatement ss) {
      Object oValuePrimary = null;
      if (this.getValueExpressionPrimary() != null) {
         oValuePrimary = this.getValueExpressionPrimary().resetAggregates(ss);
      }

      Object oValue = this.evaluate(oValuePrimary);
      return oValue;
   }

   public void parse(SqlParser.RowValueExpressionContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().rowValueExpression());
   }

   public void initValue(ValueExpressionPrimary vep) {
      _il.enter(new Object[]{vep});
      this.setValueExpressionPrimary(vep);
      _il.exit();
   }

   public void initialize(ValueExpressionPrimary vep, List<ValueExpression> listVe, boolean bRow, QueryExpression qe) {
      _il.enter(new Object[]{vep, listVe, String.valueOf(bRow), qe});
      this.setValueExpressionPrimary(vep);
      this.setValueExpressions(listVe);
      this.setRow(bRow);
      this.setQueryExpression(qe);
      _il.exit();
   }

   public RowValueExpression(SqlFactory sf) {
      super(sf);
   }

   private class RveVisitor extends EnhancedSqlBaseVisitor<RowValueExpression> {
      private RveVisitor() {
      }

      public RowValueExpression visitRowValueExpression(SqlParser.RowValueExpressionContext ctx) {
         if (ctx.ROW() != null) {
            RowValueExpression.this.setRow(true);
         }

         return (RowValueExpression)this.visitChildren(ctx);
      }

      public RowValueExpression visitValueExpressionPrimary(SqlParser.ValueExpressionPrimaryContext ctx) {
         RowValueExpression.this.setValueExpressionPrimary(RowValueExpression.this.getSqlFactory().newValueExpressionPrimary());
         RowValueExpression.this.getValueExpressionPrimary().parse(ctx);
         return RowValueExpression.this;
      }

      public RowValueExpression visitValueExpression(SqlParser.ValueExpressionContext ctx) {
         ValueExpression ve = RowValueExpression.this.getSqlFactory().newValueExpression();
         ve.parse(ctx);
         RowValueExpression.this.getValueExpressions().add(ve);
         return RowValueExpression.this;
      }

      public RowValueExpression visitQueryExpression(SqlParser.QueryExpressionContext ctx) {
         RowValueExpression.this.setQueryExpression(RowValueExpression.this.getSqlFactory().newQueryExpression());
         RowValueExpression.this.getQueryExpression().parse(ctx);
         return RowValueExpression.this;
      }

      // $FF: synthetic method
      RveVisitor(Object x1) {
         this();
      }
   }
}
