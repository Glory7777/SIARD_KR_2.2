package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.expression.enums.WindowFrameOrder;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;

public class WindowFrameBound extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(WindowFrameBound.class.getName());
   private WindowFrameBound.WfbVisitor _visitor = new WindowFrameBound.WfbVisitor();
   private boolean _bCurrentRow = false;
   private boolean _bUnbounded = false;
   private UnsignedLiteral _ul = null;
   private GeneralValueSpecification _gvs = null;
   private WindowFrameOrder _wfo = null;

   private WindowFrameBound.WfbVisitor getVisitor() {
      return this._visitor;
   }

   public boolean isCurrentRow() {
      return this._bCurrentRow;
   }

   public void setCurrentRow(boolean bCurrentRow) {
      this._bCurrentRow = bCurrentRow;
   }

   public boolean isUnbounded() {
      return this._bUnbounded;
   }

   public void setUnbounded(boolean bUnbounded) {
      this._bUnbounded = bUnbounded;
   }

   public UnsignedLiteral getUnsignedLiteral() {
      return this._ul;
   }

   public void setUnsignedLiteral(UnsignedLiteral ul) {
      this._ul = ul;
   }

   public GeneralValueSpecification getGeneralValueSpecification() {
      return this._gvs;
   }

   public void setGeneralValueSpecification(GeneralValueSpecification gvs) {
      this._gvs = gvs;
   }

   public WindowFrameOrder getWindowFrameOrder() {
      return this._wfo;
   }

   public void setWindowFrameOrder(WindowFrameOrder wfo) {
      this._wfo = wfo;
   }

   public String format() {
      String sBound = null;
      if (this.isCurrentRow()) {
         sBound = K.CURRENT.getKeyword() + " " + K.ROW.getKeyword();
      } else {
         if (this.isUnbounded()) {
            sBound = K.UNBOUNDED.getKeyword();
         } else if (this.getUnsignedLiteral() != null) {
            sBound = this.getUnsignedLiteral().format();
         } else if (this.getGeneralValueSpecification() != null) {
            sBound = this.getGeneralValueSpecification().format();
         }

         sBound = sBound + " " + this.getWindowFrameOrder().getKeywords();
      }

      return sBound;
   }

   public void parse(SqlParser.WindowFrameBoundContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().windowFrameBound());
   }

   public void initialize(boolean bCurrentRow, boolean bUnbounded, UnsignedLiteral ul, GeneralValueSpecification gv, WindowFrameOrder wfo) {
      _il.enter(new Object[]{String.valueOf(bCurrentRow), String.valueOf(bUnbounded), ul, gv, wfo});
      this.setCurrentRow(bCurrentRow);
      this.setUnbounded(bUnbounded);
      this.setUnsignedLiteral(ul);
      this.setGeneralValueSpecification(gv);
      this.setWindowFrameOrder(wfo);
      _il.exit();
   }

   public WindowFrameBound(SqlFactory sf) {
      super(sf);
   }

   private class WfbVisitor extends EnhancedSqlBaseVisitor<WindowFrameBound> {
      private WfbVisitor() {
      }

      public WindowFrameBound visitWindowFrameStart(SqlParser.WindowFrameStartContext ctx) {
         if (ctx.CURRENT() != null && ctx.ROW() != null) {
            WindowFrameBound.this.setCurrentRow(true);
         } else {
            if (ctx.UNBOUNDED() != null) {
               WindowFrameBound.this.setUnbounded(true);
            }

            if (ctx.PRECEDING() != null) {
               WindowFrameBound.this.setWindowFrameOrder(WindowFrameOrder.PRECEDING);
            }
         }

         return (WindowFrameBound)this.visitChildren(ctx);
      }

      public WindowFrameBound visitWindowFrameBound(SqlParser.WindowFrameBoundContext ctx) {
         if (ctx.UNBOUNDED() != null) {
            WindowFrameBound.this.setUnbounded(true);
         }

         if (ctx.FOLLOWING() != null) {
            WindowFrameBound.this.setWindowFrameOrder(WindowFrameOrder.FOLLOWING);
         }

         return (WindowFrameBound)this.visitChildren(ctx);
      }

      public WindowFrameBound visitUnsignedValueSpecification(SqlParser.UnsignedValueSpecificationContext ctx) {
         if (ctx.unsignedLiteral() != null) {
            WindowFrameBound.this.setUnsignedLiteral(WindowFrameBound.this.getSqlFactory().newUnsignedLiteral());
            WindowFrameBound.this.getUnsignedLiteral().parse(ctx.unsignedLiteral());
         } else if (ctx.generalValueSpecification() != null) {
            WindowFrameBound.this.setGeneralValueSpecification(WindowFrameBound.this.getSqlFactory().newGeneralValueSpecification());
            WindowFrameBound.this.getGeneralValueSpecification().parse(ctx.generalValueSpecification());
         }

         return WindowFrameBound.this;
      }

      // $FF: synthetic method
      WfbVisitor(Object x1) {
         this();
      }
   }
}
