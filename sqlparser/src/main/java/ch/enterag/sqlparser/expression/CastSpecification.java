package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;

public class CastSpecification extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(CastSpecification.class.getName());
   private CastSpecification.CeVisitor _visitor = new CastSpecification.CeVisitor();
   private ValueExpression _veCastOperand = null;
   private boolean _bNullCast = false;
   private boolean _bEmptyArrayCast = false;
   private boolean _bEmptyMultisetCast = false;
   private DataType _dt = null;

   private CastSpecification.CeVisitor getVisitor() {
      return this._visitor;
   }

   public ValueExpression getCastOperand() {
      return this._veCastOperand;
   }

   public void setCastOperand(ValueExpression veCastOperand) {
      this._veCastOperand = veCastOperand;
   }

   public boolean isNullCast() {
      return this._bNullCast;
   }

   public void setNullCast(boolean bNullCast) {
      this._bNullCast = bNullCast;
   }

   public boolean isEmptyArrayCast() {
      return this._bEmptyArrayCast;
   }

   public void setEmptyArrayCast(boolean bEmptyArrayCast) {
      this._bEmptyArrayCast = bEmptyArrayCast;
   }

   public boolean isEmptyMultisetCast() {
      return this._bEmptyMultisetCast;
   }

   public void setEmptyMultisetCast(boolean bEmptyMultisetCast) {
      this._bEmptyMultisetCast = bEmptyMultisetCast;
   }

   public DataType getDataType() {
      return this._dt;
   }

   public void setDataType(DataType dt) {
      this._dt = dt;
   }

   public String format() {
      String sSpecification = K.CAST.getKeyword() + "(";
      if (this.isNullCast()) {
         sSpecification = sSpecification + K.NULL.getKeyword();
      } else if (this.isEmptyArrayCast()) {
         sSpecification = sSpecification + K.ARRAY.getKeyword() + "[" + "]";
      } else if (this.isEmptyMultisetCast()) {
         sSpecification = sSpecification + K.MULTISET.getKeyword() + "[" + "]";
      } else {
         sSpecification = sSpecification + this.getCastOperand().format();
      }

      sSpecification = sSpecification + " " + K.AS.getKeyword() + " " + this.getDataType().format() + ")";
      return sSpecification;
   }

   public void parse(SqlParser.CastSpecificationContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().castSpecification());
   }

   public void initialize(ValueExpression veCastOperand, boolean bNullCast, boolean bEmptyArrayCast, boolean bEmptyMultisetCast, DataType dt) {
      _il.enter(new Object[0]);
      this.setCastOperand(veCastOperand);
      this.setNullCast(bNullCast);
      this.setEmptyArrayCast(bEmptyArrayCast);
      this.setEmptyMultisetCast(bEmptyMultisetCast);
      this.setDataType(dt);
      _il.exit();
   }

   public CastSpecification(SqlFactory sf) {
      super(sf);
   }

   private class CeVisitor extends EnhancedSqlBaseVisitor<CastSpecification> {
      private CeVisitor() {
      }

      public CastSpecification visitCastOperand(SqlParser.CastOperandContext ctx) {
         if (ctx.NULL() != null) {
            CastSpecification.this.setNullCast(true);
         } else if (ctx.ARRAY() != null) {
            CastSpecification.this.setEmptyArrayCast(true);
         } else if (ctx.MULTISET() != null) {
            CastSpecification.this.setEmptyMultisetCast(true);
         } else if (ctx.valueExpression() != null) {
            CastSpecification.this.setCastOperand(CastSpecification.this.getSqlFactory().newValueExpression());
            CastSpecification.this.getCastOperand().parse(ctx.valueExpression());
         }

         return CastSpecification.this;
      }

      public CastSpecification visitDataType(SqlParser.DataTypeContext ctx) {
         CastSpecification.this.setDataType(CastSpecification.this.getSqlFactory().newDataType());
         CastSpecification.this.getDataType().parse(ctx);
         return CastSpecification.this;
      }

      // $FF: synthetic method
      CeVisitor(Object x1) {
         this();
      }
   }
}
