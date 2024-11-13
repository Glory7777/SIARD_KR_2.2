package ch.enterag.sqlparser.dml;

import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class SetClause extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(SetClause.class.getName());
   private SetClause.ScVisitor _visitor = new SetClause.ScVisitor();
   private SetTarget _st = null;
   private UpdateSource _us = null;
   private List<SetTarget> _listSetTargets = new ArrayList();
   private AssignedRow _as = null;

   private SetClause.ScVisitor getVisitor() {
      return this._visitor;
   }

   public SetTarget getSetTarget() {
      return this._st;
   }

   public void setSetTarget(SetTarget st) {
      this._st = st;
   }

   public UpdateSource getUpdateSource() {
      return this._us;
   }

   public void setUpdateSource(UpdateSource us) {
      this._us = us;
   }

   public List<SetTarget> getSetTargets() {
      return this._listSetTargets;
   }

   private void setSetTargets(List<SetTarget> listSetTargets) {
      this._listSetTargets = listSetTargets;
   }

   public AssignedRow getAssignedRow() {
      return this._as;
   }

   public void setAssignedRow(AssignedRow as) {
      this._as = as;
   }

   private String formatSetTargets() {
      String s = "";

      for(int i = 0; i < this.getSetTargets().size(); ++i) {
         if (i > 0) {
            s = s + "," + " ";
         }

         s = s + ((SetTarget)this.getSetTargets().get(i)).format();
      }

      return s;
   }

   public String format() {
      String s = "";
      if (this.getSetTarget() != null && this.getUpdateSource() != null) {
         s = this.getSetTarget().format() + " " + "=" + " " + this.getUpdateSource().format();
      } else {
         s = "(" + this.formatSetTargets() + ")" + " " + "=" + " " + this.getAssignedRow().format();
      }

      return s;
   }

   public void parse(SqlParser.SetClauseContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().setClause());
   }

   public void initialize(SetTarget st, UpdateSource us, List<SetTarget> listSetTargets, AssignedRow as) {
      _il.enter(new Object[0]);
      this.setSetTarget(st);
      this.setUpdateSource(us);
      this.setSetTargets(listSetTargets);
      this.setAssignedRow(as);
      _il.exit();
   }

   public SetClause(SqlFactory sf) {
      super(sf);
   }

   private class ScVisitor extends EnhancedSqlBaseVisitor<SetClause> {
      private ScVisitor() {
      }

      public SetClause visitSetClause(SqlParser.SetClauseContext ctx) {
         SetClause scResult = SetClause.this;
         if (ctx.updateSource() != null && ctx.setTarget().size() == 1) {
            SetClause.this.setUpdateSource(SetClause.this.getSqlFactory().newUpdateSource());
            SetClause.this.getUpdateSource().parse(ctx.updateSource());
            SetClause.this.setSetTarget(SetClause.this.getSqlFactory().newSetTarget());
            SetClause.this.getSetTarget().parse(ctx.setTarget(0));
         } else if (ctx.assignedRow() != null) {
            SetClause.this.setAssignedRow(SetClause.this.getSqlFactory().newAssignedRow());
            SetClause.this.getAssignedRow().parse(ctx.assignedRow());
            scResult = (SetClause)this.visitChildren(ctx);
         }

         return scResult;
      }

      public SetClause visitSetTarget(SqlParser.SetTargetContext ctx) {
         SetTarget st = SetClause.this.getSqlFactory().newSetTarget();
         st.parse(ctx);
         SetClause.this.getSetTargets().add(st);
         return SetClause.this;
      }

      // $FF: synthetic method
      ScVisitor(Object x1) {
         this();
      }
   }
}
