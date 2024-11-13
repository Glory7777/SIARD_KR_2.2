package ch.enterag.sqlparser.dml;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class AssignedRow extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(AssignedRow.class.getName());
   private AssignedRow.ArVisitor _visitor = new AssignedRow.ArVisitor();
   private List<UpdateSource> _listUpdateSources = new ArrayList();
   private boolean _bRowExpression = false;
   private UpdateSource _us = null;

   private AssignedRow.ArVisitor getVisitor() {
      return this._visitor;
   }

   public List<UpdateSource> getUpdateSources() {
      return this._listUpdateSources;
   }

   private void setUpdateSources(List<UpdateSource> listUpdateSources) {
      this._listUpdateSources = listUpdateSources;
   }

   public boolean isRowExpression() {
      return this._bRowExpression;
   }

   public void setRowExpression(boolean bRowExpression) {
      this._bRowExpression = bRowExpression;
   }

   public UpdateSource getUpdateSource() {
      return this._us;
   }

   public void setUpdateSource(UpdateSource us) {
      this._us = us;
   }

   public String format() {
      String s = "";
      if (this.getUpdateSource() != null) {
         s = this.getUpdateSource().format();
      } else {
         if (this.isRowExpression()) {
            s = K.ROW.getKeyword() + " ";
         }

         s = s + "(";

         for(int i = 0; i < this.getUpdateSources().size(); ++i) {
            if (i > 0) {
               s = s + "," + " ";
            }

            s = s + ((UpdateSource)this.getUpdateSources().get(i)).format();
         }

         s = s + ")";
      }

      return s;
   }

   public void parse(SqlParser.AssignedRowContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().assignedRow());
   }

   public void initUpdateSource(UpdateSource us) {
      _il.enter(new Object[0]);
      this.setUpdateSource(us);
      _il.exit();
   }

   public void initialize(List<UpdateSource> listUpdateSources, boolean bRowExpression, UpdateSource us) {
      _il.enter(new Object[0]);
      this.setUpdateSources(listUpdateSources);
      this.setRowExpression(bRowExpression);
      this.setUpdateSource(us);
      _il.exit();
   }

   public AssignedRow(SqlFactory sf) {
      super(sf);
   }

   private class ArVisitor extends EnhancedSqlBaseVisitor<AssignedRow> {
      private ArVisitor() {
      }

      public AssignedRow visitAssignedRow(SqlParser.AssignedRowContext ctx) {
         AssignedRow arReturn = AssignedRow.this;
         if (ctx.LEFT_PAREN() == null && ctx.RIGHT_PAREN() == null && ctx.updateSource().size() == 1) {
            AssignedRow.this.setUpdateSource(AssignedRow.this.getSqlFactory().newUpdateSource());
            AssignedRow.this.getUpdateSource().parse(ctx.updateSource(0));
         } else {
            if (ctx.ROW() != null) {
               AssignedRow.this.setRowExpression(true);
            }

            arReturn = (AssignedRow)this.visitChildren(ctx);
         }

         return arReturn;
      }

      public AssignedRow visitUpdateSource(SqlParser.UpdateSourceContext ctx) {
         UpdateSource us = AssignedRow.this.getSqlFactory().newUpdateSource();
         us.parse(ctx);
         AssignedRow.this.getUpdateSources().add(us);
         return AssignedRow.this;
      }

      // $FF: synthetic method
      ArVisitor(Object x1) {
         this();
      }
   }
}
