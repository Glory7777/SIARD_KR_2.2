package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.expression.enums.WindowFrameExclusion;
import ch.enterag.sqlparser.expression.enums.WindowFrameOrder;
import ch.enterag.sqlparser.expression.enums.WindowFrameUnits;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.IdChain;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class WindowSpecification extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(WindowSpecification.class.getName());
   private WindowSpecification.WsVisitor _visitor = new WindowSpecification.WsVisitor();
   private Identifier _idWindowName = new Identifier();
   private List<IdChain> _listPartitionColumns = new ArrayList();
   private List<SortSpecification> _listSortSpecifications = new ArrayList();
   private WindowFrameUnits _wfu = null;
   private boolean _bCurrentRow = false;
   private boolean _bUnbounded = false;
   private UnsignedLiteral _ul = null;
   private GeneralValueSpecification _gvs = null;
   private WindowFrameBound _wfb1 = null;
   private WindowFrameBound _wfb2 = null;
   private WindowFrameExclusion _wfe = null;

   private WindowSpecification.WsVisitor getVisitor() {
      return this._visitor;
   }

   public Identifier getWindowName() {
      return this._idWindowName;
   }

   private void setWindowName(Identifier idWindowName) {
      this._idWindowName = idWindowName;
   }

   public List<IdChain> getPartitionColumns() {
      return this._listPartitionColumns;
   }

   private void setPartitionColumns(List<IdChain> listPartition) {
      this._listPartitionColumns = listPartition;
   }

   public List<SortSpecification> getSortSpecifications() {
      return this._listSortSpecifications;
   }

   private void setSortSpecifications(List<SortSpecification> listSortSpecifications) {
      this._listSortSpecifications = listSortSpecifications;
   }

   public WindowFrameUnits getWindowFrameUnits() {
      return this._wfu;
   }

   public void setWindowFrameUnits(WindowFrameUnits wfu) {
      this._wfu = wfu;
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

   public WindowFrameBound getBound1() {
      return this._wfb1;
   }

   public void setBound1(WindowFrameBound wfb1) {
      this._wfb1 = wfb1;
   }

   public WindowFrameBound getBound2() {
      return this._wfb2;
   }

   public void setBound2(WindowFrameBound wfb2) {
      this._wfb2 = wfb2;
   }

   public WindowFrameExclusion getWindowFrameExclusion() {
      return this._wfe;
   }

   public void setWindowFrameExclusion(WindowFrameExclusion wfe) {
      this._wfe = wfe;
   }

   public String format() {
      String sSpecification = "";
      if (this.getWindowName() != null) {
         sSpecification = sSpecification + this.getWindowName().format();
      }

      int i;
      if (this.getPartitionColumns().size() > 0) {
         sSpecification = sSpecification + " " + K.PARTITION.getKeyword() + " " + K.BY.getKeyword();

         for(i = 0; i < this.getPartitionColumns().size(); ++i) {
            if (i > 0) {
               sSpecification = sSpecification + ",";
            }

            sSpecification = sSpecification + " " + ((IdChain)this.getPartitionColumns().get(i)).format();
         }
      }

      if (this.getSortSpecifications().size() > 0) {
         sSpecification = sSpecification + " " + K.ORDER.getKeyword() + " " + K.BY.getKeyword();

         for(i = 0; i < this.getSortSpecifications().size(); ++i) {
            if (i > 0) {
               sSpecification = sSpecification + ",";
            }

            sSpecification = sSpecification + " " + ((SortSpecification)this.getSortSpecifications().get(i)).format();
         }
      }

      if (this.getWindowFrameUnits() != null) {
         sSpecification = sSpecification + " " + this.getWindowFrameUnits().getKeywords();
         if (!this.isUnbounded() && this.getUnsignedLiteral() == null && this.getGeneralValueSpecification() == null) {
            if (this.getBound1() != null && this.getBound2() != null) {
               sSpecification = sSpecification + " " + K.BETWEEN.getKeyword() + " " + this.getBound1().format() + " " + K.AND.getKeyword() + " " + this.getBound2().format();
            }
         } else {
            if (this.isUnbounded()) {
               sSpecification = sSpecification + " " + K.UNBOUNDED.getKeyword();
            } else if (this.getUnsignedLiteral() != null) {
               sSpecification = sSpecification + " " + this.getUnsignedLiteral().format();
            } else if (this.getGeneralValueSpecification() != null) {
               sSpecification = sSpecification + " " + this.getGeneralValueSpecification().format();
            }

            sSpecification = sSpecification + " " + WindowFrameOrder.PRECEDING.getKeywords();
         }

         if (this.getWindowFrameExclusion() != null) {
            sSpecification = sSpecification + " " + this.getWindowFrameExclusion().getKeywords();
         }
      }

      return sSpecification;
   }

   public void parse(SqlParser.WindowSpecificationContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().windowSpecification());
   }

   public void initialize(Identifier idWindowName, List<IdChain> listPartitionColumns, List<SortSpecification> listSortSpecifications, WindowFrameUnits wfu, boolean bCurrentRow, boolean bUnbounded, UnsignedLiteral ul, GeneralValueSpecification gvs, WindowFrameBound wfb1, WindowFrameBound wfb2, WindowFrameExclusion wfe) {
      _il.enter(new Object[]{idWindowName, listPartitionColumns, listSortSpecifications, wfu, String.valueOf(bCurrentRow), String.valueOf(bUnbounded), ul, gvs, wfb1, wfb2, wfe});
      this.setWindowName(idWindowName);
      this.setPartitionColumns(listPartitionColumns);
      this.setSortSpecifications(listSortSpecifications);
      this.setWindowFrameUnits(wfu);
      this.setCurrentRow(bCurrentRow);
      this.setUnsignedLiteral(ul);
      this.setGeneralValueSpecification(gvs);
      this.setBound1(wfb1);
      this.setBound2(wfb2);
      this.setWindowFrameExclusion(wfe);
      _il.exit();
   }

   public WindowSpecification(SqlFactory sf) {
      super(sf);
   }

   private class WsVisitor extends EnhancedSqlBaseVisitor<WindowSpecification> {
      private WsVisitor() {
      }

      public WindowSpecification visitWindowPartitionClause(SqlParser.WindowPartitionClauseContext ctx) {
         for(int i = 0; i < ctx.columnReference().size(); ++i) {
            IdChain idcColumnReference = new IdChain();
            this.setIdChain(ctx.columnReference(i).identifierChain(), idcColumnReference);
            WindowSpecification.this.getPartitionColumns().add(idcColumnReference);
         }

         return WindowSpecification.this;
      }

      public WindowSpecification visitSortSpecificationList(SqlParser.SortSpecificationListContext ctx) {
         for(int i = 0; i < ctx.sortSpecification().size(); ++i) {
            SortSpecification ss = WindowSpecification.this.getSqlFactory().newSortSpecification();
            ss.parse(ctx.sortSpecification(i));
            WindowSpecification.this.getSortSpecifications().add(ss);
         }

         return WindowSpecification.this;
      }

      public WindowSpecification visitWindowName(SqlParser.WindowNameContext ctx) {
         this.setIdentifier(ctx.IDENTIFIER(), WindowSpecification.this.getWindowName());
         return WindowSpecification.this;
      }

      public WindowSpecification visitWindowFrameUnits(SqlParser.WindowFrameUnitsContext ctx) {
         WindowSpecification.this.setWindowFrameUnits(this.getWindowFrameUnits(ctx));
         return WindowSpecification.this;
      }

      public WindowSpecification visitWindowFrameStart(SqlParser.WindowFrameStartContext ctx) {
         if (ctx.CURRENT() != null && ctx.ROW() != null) {
            WindowSpecification.this.setCurrentRow(true);
         } else if (ctx.UNBOUNDED() != null) {
            WindowSpecification.this.setUnbounded(true);
         }

         return (WindowSpecification)this.visitChildren(ctx);
      }

      public WindowSpecification visitUnsignedValueSpecification(SqlParser.UnsignedValueSpecificationContext ctx) {
         if (ctx.unsignedLiteral() != null) {
            WindowSpecification.this.setUnsignedLiteral(WindowSpecification.this.getSqlFactory().newUnsignedLiteral());
            WindowSpecification.this.getUnsignedLiteral().parse(ctx.unsignedLiteral());
         } else if (ctx.generalValueSpecification() != null) {
            WindowSpecification.this.setGeneralValueSpecification(WindowSpecification.this.getSqlFactory().newGeneralValueSpecification());
            WindowSpecification.this.getGeneralValueSpecification().parse(ctx.generalValueSpecification());
         }

         return WindowSpecification.this;
      }

      public WindowSpecification visitWindowFrameBetween(SqlParser.WindowFrameBetweenContext ctx) {
         WindowSpecification.this.setBound1(WindowSpecification.this.getSqlFactory().newWindowFrameBound());
         WindowSpecification.this.getBound1().parse(ctx.windowFrameBound1().windowFrameBound());
         WindowSpecification.this.setBound2(WindowSpecification.this.getSqlFactory().newWindowFrameBound());
         WindowSpecification.this.getBound2().parse(ctx.windowFrameBound2().windowFrameBound());
         return WindowSpecification.this;
      }

      public WindowSpecification visitWindowFrameExclusion(SqlParser.WindowFrameExclusionContext ctx) {
         WindowSpecification.this.setWindowFrameExclusion(this.getWindowFrameExclusion(ctx));
         return WindowSpecification.this;
      }

      // $FF: synthetic method
      WsVisitor(Object x1) {
         this();
      }
   }
}
