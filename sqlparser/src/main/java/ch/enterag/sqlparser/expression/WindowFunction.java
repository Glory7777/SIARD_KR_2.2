package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.expression.enums.RankFunction;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.utils.logging.IndentLogger;

public class WindowFunction extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(WindowFunction.class.getName());
   private WindowFunction.WfVisitor _visitor = new WindowFunction.WfVisitor();
   private RankFunction _rf = null;
   private boolean _bRowNumber = false;
   private AggregateFunction _af = null;
   private Identifier _idWindowName = new Identifier();
   private WindowSpecification _ws = null;

   private WindowFunction.WfVisitor getVisitor() {
      return this._visitor;
   }

   public RankFunction getRankFunction() {
      return this._rf;
   }

   public void setRankFunction(RankFunction rf) {
      this._rf = rf;
   }

   public boolean isRowNumber() {
      return this._bRowNumber;
   }

   public void setRowNumber(boolean bRowNumber) {
      this._bRowNumber = bRowNumber;
   }

   public AggregateFunction getAggregateFunction() {
      return this._af;
   }

   public void setAggregateFunction(AggregateFunction af) {
      this._af = af;
   }

   public Identifier getWindowName() {
      return this._idWindowName;
   }

   private void setWindowName(Identifier idWindowName) {
      this._idWindowName = idWindowName;
   }

   public WindowSpecification getWindowSpecification() {
      return this._ws;
   }

   public void setWindowSpecification(WindowSpecification ws) {
      this._ws = ws;
   }

   public String format() {
      String sFunction = null;
      if (this.getRankFunction() != null) {
         sFunction = this.getRankFunction().getKeywords() + "(" + ")";
      } else if (this.isRowNumber()) {
         sFunction = K.ROW_NUMBER.getKeyword() + "(" + ")";
      } else if (this.getAggregateFunction() != null) {
         sFunction = this.getAggregateFunction().format();
      }

      sFunction = sFunction + " " + K.OVER.getKeyword();
      if (this.getWindowName().isSet()) {
         sFunction = sFunction + " " + this.getWindowName().format();
      } else if (this.getWindowSpecification() != null) {
         sFunction = sFunction + " " + "(" + this.getWindowSpecification().format() + ")";
      }

      return sFunction;
   }

   public void parse(SqlParser.WindowFunctionContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().windowFunction());
   }

   public void initialize(RankFunction rf, boolean bRowNumber, AggregateFunction af, Identifier idWindowName, WindowSpecification ws) {
      _il.enter(new Object[]{rf, String.valueOf(bRowNumber), af, idWindowName, ws});
      this.setRankFunction(rf);
      this.setRowNumber(bRowNumber);
      this.setAggregateFunction(af);
      this.setWindowName(idWindowName);
      this.setWindowSpecification(ws);
      _il.exit();
   }

   public WindowFunction(SqlFactory sf) {
      super(sf);
   }

   private class WfVisitor extends EnhancedSqlBaseVisitor<WindowFunction> {
      private WfVisitor() {
      }

      public WindowFunction visitWindowFunctionType(SqlParser.WindowFunctionTypeContext ctx) {
         if (ctx.ROW_NUMBER() != null) {
            WindowFunction.this.setRowNumber(true);
         }

         return (WindowFunction)this.visitChildren(ctx);
      }

      public WindowFunction visitRankFunction(SqlParser.RankFunctionContext ctx) {
         WindowFunction.this.setRankFunction(this.getRankFunction(ctx));
         return WindowFunction.this;
      }

      public WindowFunction visitAggregateFunction(SqlParser.AggregateFunctionContext ctx) {
         WindowFunction.this.setAggregateFunction(WindowFunction.this.getSqlFactory().newAggregateFunction());
         WindowFunction.this.getAggregateFunction().parse(ctx);
         return WindowFunction.this;
      }

      public WindowFunction visitWindowName(SqlParser.WindowNameContext ctx) {
         this.setIdentifier(ctx.IDENTIFIER(), WindowFunction.this.getWindowName());
         return WindowFunction.this;
      }

      public WindowFunction visitWindowSpecification(SqlParser.WindowSpecificationContext ctx) {
         WindowFunction.this.setWindowSpecification(WindowFunction.this.getSqlFactory().newWindowSpecification());
         WindowFunction.this.getWindowSpecification().parse(ctx);
         return WindowFunction.this;
      }

      // $FF: synthetic method
      WfVisitor(Object x1) {
         this();
      }
   }
}
