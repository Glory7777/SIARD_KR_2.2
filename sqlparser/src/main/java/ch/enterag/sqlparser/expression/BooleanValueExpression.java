package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.SqlStatement;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.datatype.PredefinedType;
import ch.enterag.sqlparser.expression.enums.BooleanLiteral;
import ch.enterag.sqlparser.expression.enums.BooleanOperator;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;

public class BooleanValueExpression extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(BooleanValueExpression.class.getName());
   private BooleanValueExpression.BveVisitor _visitor = new BooleanValueExpression.BveVisitor();
   private boolean _bNot = false;
   private BooleanOperator _bo = null;
   private BooleanValueExpression _bve = null;
   private BooleanValueExpression _bve2 = null;
   private BooleanPrimary _bp = null;
   private BooleanLiteral _bl = null;

   private BooleanValueExpression.BveVisitor getVisitor() {
      return this._visitor;
   }

   public boolean isNot() {
      return this._bNot;
   }

   public void setNot(boolean bNot) {
      this._bNot = bNot;
   }

   public BooleanOperator getBooleanOperator() {
      return this._bo;
   }

   public void setBooleanOperator(BooleanOperator bo) {
      this._bo = bo;
   }

   public BooleanValueExpression getBooleanValueExpression() {
      return this._bve;
   }

   public void setBooleanValueExpression(BooleanValueExpression bve) {
      this._bve = bve;
   }

   public BooleanValueExpression getSecondBooleanValueExpression() {
      return this._bve2;
   }

   public void setSecondBooleanValueExpression(BooleanValueExpression bve2) {
      this._bve2 = bve2;
   }

   public BooleanPrimary getBooleanPrimary() {
      return this._bp;
   }

   public void setBooleanPrimary(BooleanPrimary bp) {
      this._bp = bp;
   }

   public BooleanLiteral getBooleanLiteral() {
      return this._bl;
   }

   public void setBooleanLiteral(BooleanLiteral bl) {
      this._bl = bl;
   }

   public String format() {
      String sExpression = "";
      if (this.isNot()) {
         sExpression = K.NOT.getKeyword() + " ";
      }

      if (this.getBooleanValueExpression() != null) {
         sExpression = sExpression + this.getBooleanValueExpression().format();
         if (this.getBooleanOperator() != null) {
            sExpression = sExpression + " " + this.getBooleanOperator().getKeywords();
         }

         if (this.getSecondBooleanValueExpression() != null) {
            sExpression = sExpression + " " + this.getSecondBooleanValueExpression().format();
         }
      } else if (this.getBooleanPrimary() != null) {
         sExpression = this.getBooleanPrimary().format();
         if (this.getBooleanLiteral() != null) {
            sExpression = sExpression + " " + K.IS.getKeyword();
            if (this.isNot()) {
               sExpression = sExpression + " " + K.NOT.getKeyword();
            }

            sExpression = sExpression + " " + this.getBooleanLiteral().getKeywords();
         }
      }

      return sExpression;
   }

   public DataType getDataType(SqlStatement ss) {
      DataType dt = this.getSqlFactory().newDataType();
      PredefinedType pt = this.getSqlFactory().newPredefinedType();
      dt.initPredefinedDataType(pt);
      pt.initBooleanType();
      return dt;
   }

   private Boolean evaluate(Boolean bBooleanPrimary, Boolean bBooleanValue, Boolean bSecondBooleanValue) {
      Boolean bValue = null;
      if (this.getBooleanPrimary() != null) {
         bValue = bBooleanPrimary;
         BooleanLiteral bl = this.getBooleanLiteral();
         if (bl != null) {
            if ((bBooleanPrimary != null || bl != BooleanLiteral.UNKNOWN) && (!Boolean.FALSE.equals(bBooleanPrimary) || bl != BooleanLiteral.FALSE) && (!Boolean.TRUE.equals(bBooleanPrimary) || bl != BooleanLiteral.TRUE)) {
               bValue = Boolean.FALSE;
            } else {
               bValue = Boolean.TRUE;
            }

            if (this.isNot()) {
               bValue = !bValue;
            }
         }
      } else if (this.getBooleanOperator() != null) {
         BooleanOperator bo = this.getBooleanOperator();
         bValue = bBooleanValue;
         switch(bo) {
         case AND:
            if (Boolean.TRUE.equals(bBooleanValue) && Boolean.TRUE.equals(bSecondBooleanValue)) {
               bValue = Boolean.TRUE;
            } else if (!Boolean.FALSE.equals(bBooleanValue) && !Boolean.FALSE.equals(bSecondBooleanValue)) {
               bValue = null;
            } else {
               bValue = Boolean.FALSE;
            }
            break;
         case OR:
            if (Boolean.FALSE.equals(bBooleanValue) && Boolean.FALSE.equals(bSecondBooleanValue)) {
               bValue = Boolean.FALSE;
            } else if (!Boolean.TRUE.equals(bBooleanValue) && !Boolean.TRUE.equals(bSecondBooleanValue)) {
               bValue = null;
            } else {
               bValue = Boolean.TRUE;
            }
         }
      }

      return bValue;
   }

   public Boolean evaluate(SqlStatement ss, boolean bAggregated) {
      Boolean bBooleanPrimary = null;
      if (this.getBooleanPrimary() != null) {
         bBooleanPrimary = this.getBooleanPrimary().evaluate(ss, bAggregated);
      }

      Boolean bBooleanValue = null;
      if (this.getBooleanValueExpression() != null) {
         bBooleanValue = this.getBooleanValueExpression().evaluate(ss, bAggregated);
      }

      Boolean bSecondBooleanValue = null;
      if (this.getSecondBooleanValueExpression() != null) {
         bSecondBooleanValue = this.getSecondBooleanValueExpression().evaluate(ss, bAggregated);
      }

      Boolean bValue = this.evaluate(bBooleanPrimary, bBooleanValue, bSecondBooleanValue);
      return bValue;
   }

   public Boolean resetAggregates(SqlStatement ss) {
      Boolean bBooleanPrimary = null;
      if (this.getBooleanPrimary() != null) {
         bBooleanPrimary = this.getBooleanPrimary().resetAggregates(ss);
      }

      Boolean bBooleanValue = null;
      if (this.getBooleanValueExpression() != null) {
         bBooleanValue = this.getBooleanValueExpression().resetAggregates(ss);
      }

      Boolean bSecondBooleanValue = null;
      if (this.getSecondBooleanValueExpression() != null) {
         bSecondBooleanValue = this.getSecondBooleanValueExpression().resetAggregates(ss);
      }

      Boolean bValue = this.evaluate(bBooleanPrimary, bBooleanValue, bSecondBooleanValue);
      return bValue;
   }

   public void parse(SqlParser.BooleanValueExpressionContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().booleanValueExpression());
   }

   public void initialize(boolean bNot, BooleanOperator bo, BooleanValueExpression bve, BooleanValueExpression bve2, BooleanPrimary bp, BooleanLiteral bl) {
      _il.enter(new Object[]{bNot, bo, bve, bve2, bp, bl});
      this.setNot(bNot);
      this.setBooleanOperator(bo);
      this.setBooleanValueExpression(bve);
      this.setSecondBooleanValueExpression(bve2);
      this.setBooleanPrimary(bp);
      this.setBooleanLiteral(bl);
      _il.exit();
   }

   public BooleanValueExpression(SqlFactory sf) {
      super(sf);
   }

   private class BveVisitor extends EnhancedSqlBaseVisitor<BooleanValueExpression> {
      private BveVisitor() {
      }

      public BooleanValueExpression visitBooleanValueExpression(SqlParser.BooleanValueExpressionContext ctx) {
         if (ctx.NOT() != null) {
            BooleanValueExpression.this.setNot(true);
         }

         if (ctx.booleanValueExpression().size() > 0) {
            BooleanValueExpression.this.setBooleanValueExpression(BooleanValueExpression.this.getSqlFactory().newBooleanValueExpression());
            BooleanValueExpression.this.getBooleanValueExpression().parse(ctx.booleanValueExpression(0));
         }

         if (ctx.booleanOperator() != null) {
            BooleanValueExpression.this.setBooleanOperator(this.getBooleanOperator(ctx.booleanOperator()));
         }

         if (ctx.booleanValueExpression().size() > 1) {
            BooleanValueExpression.this.setSecondBooleanValueExpression(BooleanValueExpression.this.getSqlFactory().newBooleanValueExpression());
            BooleanValueExpression.this.getSecondBooleanValueExpression().parse(ctx.booleanValueExpression(1));
         }

         if (ctx.booleanPrimary() != null) {
            BooleanValueExpression.this.setBooleanPrimary(BooleanValueExpression.this.getSqlFactory().newBooleanPrimary());
            BooleanValueExpression.this.getBooleanPrimary().parse(ctx.booleanPrimary());
         }

         if (ctx.BOOLEAN_LITERAL() != null) {
            BooleanValueExpression.this.setBooleanLiteral(this.getBooleanLiteral(ctx.BOOLEAN_LITERAL()));
         }

         return BooleanValueExpression.this;
      }

      // $FF: synthetic method
      BveVisitor(Object x1) {
         this();
      }
   }
}
