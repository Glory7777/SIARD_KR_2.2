package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.expression.enums.NullOrdering;
import ch.enterag.sqlparser.expression.enums.OrderingSpecification;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;

public class SortSpecification extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(SortSpecification.class.getName());
   private SortSpecification.SsVisitor _visitor = new SortSpecification.SsVisitor();
   private ValueExpression _ve = null;
   private OrderingSpecification _os = null;
   private NullOrdering _no = null;

   private SortSpecification.SsVisitor getVisitor() {
      return this._visitor;
   }

   public ValueExpression getValueExpression() {
      return this._ve;
   }

   public void setValueExpression(ValueExpression ve) {
      this._ve = ve;
   }

   public OrderingSpecification getOrderingSpecification() {
      return this._os;
   }

   public void setOrderingSpecification(OrderingSpecification os) {
      this._os = os;
   }

   public NullOrdering getNullOrdering() {
      return this._no;
   }

   public void setNullOrdering(NullOrdering no) {
      this._no = no;
   }

   public String format() {
      String sSpecification = this.getValueExpression().format();
      if (this.getOrderingSpecification() != null) {
         sSpecification = sSpecification + " " + this.getOrderingSpecification().getKeywords();
      }

      if (this.getNullOrdering() != null) {
         sSpecification = sSpecification + " " + this.getNullOrdering().getKeywords();
      }

      return sSpecification;
   }

   public void parse(SqlParser.SortSpecificationContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().sortSpecification());
   }

   public void initialize(ValueExpression ve, OrderingSpecification os, NullOrdering no) {
      _il.enter(new Object[0]);
      this.setValueExpression(ve);
      this.setOrderingSpecification(os);
      this.setNullOrdering(no);
      _il.exit();
   }

   public SortSpecification(SqlFactory sf) {
      super(sf);
   }

   private class SsVisitor extends EnhancedSqlBaseVisitor<SortSpecification> {
      private SsVisitor() {
      }

      public SortSpecification visitValueExpression(SqlParser.ValueExpressionContext ctx) {
         SortSpecification.this.setValueExpression(SortSpecification.this.getSqlFactory().newValueExpression());
         SortSpecification.this.getValueExpression().parse(ctx);
         return SortSpecification.this;
      }

      public SortSpecification visitOrderingSpecification(SqlParser.OrderingSpecificationContext ctx) {
         SortSpecification.this.setOrderingSpecification(this.getOrderingSpecification(ctx));
         return SortSpecification.this;
      }

      public SortSpecification visitNullOrdering(SqlParser.NullOrderingContext ctx) {
         SortSpecification.this.setNullOrdering(this.getNullOrdering(ctx));
         return SortSpecification.this;
      }

      // $FF: synthetic method
      SsVisitor(Object x1) {
         this();
      }
   }
}
