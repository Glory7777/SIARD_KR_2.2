package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.expression.enums.MultisetOperator;
import ch.enterag.sqlparser.expression.enums.SetQuantifier;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;

public class MultisetValueExpression extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(MultisetValueExpression.class.getName());
   private MultisetValueExpression.MveVisitor _visitor = new MultisetValueExpression.MveVisitor();
   private ValueExpressionPrimary _vep = null;
   private MultisetOperator _mo = null;
   private SetQuantifier _sq = null;
   private MultisetValueExpression _mve = null;
   private MultisetValueExpression _mveSetArgument = null;

   private MultisetValueExpression.MveVisitor getVisitor() {
      return this._visitor;
   }

   public ValueExpressionPrimary getValueExpressionPrimary() {
      return this._vep;
   }

   public void setValueExpressionPrimary(ValueExpressionPrimary vep) {
      this._vep = vep;
   }

   public MultisetOperator getMultisetOperator() {
      return this._mo;
   }

   public void setMultisetOperator(MultisetOperator mo) {
      this._mo = mo;
   }

   public SetQuantifier getSetQuantifier() {
      return this._sq;
   }

   private void setSetQuantifier(SetQuantifier sq) {
      this._sq = sq;
   }

   public MultisetValueExpression getMultisetValueExpression() {
      return this._mve;
   }

   public void setMultisetValueExpression(MultisetValueExpression mve) {
      this._mve = mve;
   }

   public MultisetValueExpression getSetArgument() {
      return this._mveSetArgument;
   }

   public void setSetArgument(MultisetValueExpression mveSetArgument) {
      this._mveSetArgument = mveSetArgument;
   }

   public String format() {
      String sExpression = "";
      if (this.getMultisetValueExpression() != null && this.getMultisetOperator() != null) {
         sExpression = this.getMultisetValueExpression().format() + " " + this.getMultisetOperator().getKeywords() + " ";
         if (this.getSetQuantifier() != null) {
            sExpression = sExpression + this.getSetQuantifier().getKeywords() + " ";
         }
      }

      if (this.getValueExpressionPrimary() != null) {
         sExpression = sExpression + this.getValueExpressionPrimary().format();
      } else if (this.getSetArgument() != null) {
         sExpression = sExpression + K.SET.getKeyword() + "(" + this.getSetArgument().format() + ")";
      }

      return sExpression;
   }

   public void parse(SqlParser.MultisetValueExpressionContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().multisetValueExpression());
   }

   public void initialize(MultisetValueExpression mve, MultisetOperator mo, SetQuantifier sq, ValueExpressionPrimary vep, MultisetValueExpression mveSetArgument) {
      _il.enter(new Object[]{mve, mo, sq, vep, mveSetArgument});
      this.setMultisetValueExpression(mve);
      this.setMultisetOperator(mo);
      this.setSetQuantifier(sq);
      this.setValueExpressionPrimary(vep);
      this.setSetArgument(mveSetArgument);
      _il.exit();
   }

   public MultisetValueExpression(SqlFactory sf) {
      super(sf);
   }

   private class MveVisitor extends EnhancedSqlBaseVisitor<MultisetValueExpression> {
      private MveVisitor() {
      }

      public MultisetValueExpression visitMultisetValueExpression(SqlParser.MultisetValueExpressionContext ctx) {
         if (ctx.valueExpressionPrimary() != null) {
            MultisetValueExpression.this.setValueExpressionPrimary(MultisetValueExpression.this.getSqlFactory().newValueExpressionPrimary());
            MultisetValueExpression.this.getValueExpressionPrimary().parse(ctx.valueExpressionPrimary());
         }

         if (ctx.multisetOperator() != null && ctx.multisetValueExpression().size() > 0) {
            MultisetValueExpression.this.setMultisetValueExpression(MultisetValueExpression.this.getSqlFactory().newMultisetValueExpression());
            MultisetValueExpression.this.getMultisetValueExpression().parse(ctx.multisetValueExpression(0));
            MultisetValueExpression.this.setMultisetOperator(this.getMultisetOperator(ctx.multisetOperator()));
            if (ctx.setQuantifier() != null) {
               MultisetValueExpression.this.setSetQuantifier(this.getSetQuantifier(ctx.setQuantifier()));
            }

            if (ctx.SET() != null && ctx.multisetValueExpression().size() > 1) {
               MultisetValueExpression.this.setSetArgument(MultisetValueExpression.this.getSqlFactory().newMultisetValueExpression());
               MultisetValueExpression.this.getSetArgument().parse(ctx.multisetValueExpression(1));
            }
         } else if (ctx.SET() != null && ctx.multisetValueExpression().size() > 0) {
            MultisetValueExpression.this.setSetArgument(MultisetValueExpression.this.getSqlFactory().newMultisetValueExpression());
            MultisetValueExpression.this.getSetArgument().parse(ctx.multisetValueExpression(0));
         }

         return MultisetValueExpression.this;
      }

      // $FF: synthetic method
      MveVisitor(Object x1) {
         this();
      }
   }
}
