package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;

public class ArrayValueExpression extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(ArrayValueExpression.class.getName());
   private ArrayValueExpression.AveVisitor _visitor = new ArrayValueExpression.AveVisitor();
   private boolean _bConcatenation = false;
   private ArrayValueExpression _ave1 = null;
   private ArrayValueExpression _ave2 = null;
   private ValueExpressionPrimary _vep = null;

   private ArrayValueExpression.AveVisitor getVisitor() {
      return this._visitor;
   }

   public boolean isConcatenation() {
      return this._bConcatenation;
   }

   public void setConcatenation(boolean bConcatenation) {
      this._bConcatenation = bConcatenation;
   }

   public ArrayValueExpression getFirstOperand() {
      return this._ave1;
   }

   public void setFirstOperand(ArrayValueExpression ave1) {
      this._ave1 = ave1;
   }

   public ArrayValueExpression getSecondOperand() {
      return this._ave2;
   }

   public void setSecondOperand(ArrayValueExpression ave2) {
      this._ave2 = ave2;
   }

   public ValueExpressionPrimary getValueExpressionPrimary() {
      return this._vep;
   }

   public void setValueExpressionPrimary(ValueExpressionPrimary vep) {
      this._vep = vep;
   }

   public String format() {
      String sExpression = null;
      if (this.isConcatenation()) {
         sExpression = this.getFirstOperand().format() + " " + "||" + " " + this.getSecondOperand().format();
      } else if (this.getValueExpressionPrimary() != null) {
         sExpression = this.getValueExpressionPrimary().format();
      }

      return sExpression;
   }

   public void parse(SqlParser.ArrayValueExpressionContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().arrayValueExpression());
   }

   public void initialize(boolean bConcatenation, ArrayValueExpression ave1, ArrayValueExpression ave2, ValueExpressionPrimary vep) {
      _il.enter(new Object[]{String.valueOf(bConcatenation), ave1, ave2, vep});
      this.setConcatenation(bConcatenation);
      this.setFirstOperand(ave1);
      this.setSecondOperand(ave2);
      this.setValueExpressionPrimary(vep);
      _il.exit();
   }

   public ArrayValueExpression(SqlFactory sf) {
      super(sf);
   }

   private class AveVisitor extends EnhancedSqlBaseVisitor<ArrayValueExpression> {
      private AveVisitor() {
      }

      public ArrayValueExpression visitArrayValueExpression(SqlParser.ArrayValueExpressionContext ctx) {
         if (ctx.CONCATENATION_OPERATOR() != null && ctx.arrayValueExpression().size() == 2) {
            ArrayValueExpression.this.setConcatenation(true);
            ArrayValueExpression.this.setFirstOperand(ArrayValueExpression.this.getSqlFactory().newArrayValueExpression());
            ArrayValueExpression.this.getFirstOperand().parse(ctx.arrayValueExpression(0));
            ArrayValueExpression.this.setSecondOperand(ArrayValueExpression.this.getSqlFactory().newArrayValueExpression());
            ArrayValueExpression.this.getSecondOperand().parse(ctx.arrayValueExpression(1));
         } else if (ctx.valueExpressionPrimary() != null) {
            ArrayValueExpression.this.setValueExpressionPrimary(ArrayValueExpression.this.getSqlFactory().newValueExpressionPrimary());
            ArrayValueExpression.this.getValueExpressionPrimary().parse(ctx.valueExpressionPrimary());
         }

         return ArrayValueExpression.this;
      }

      // $FF: synthetic method
      AveVisitor(Object x1) {
         this();
      }
   }
}
