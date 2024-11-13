package ch.enterag.sqlparser.dml;

import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class SetTarget extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(SetTarget.class.getName());
   private SetTarget.StVisitor _visitor = new SetTarget.StVisitor();
   private UpdateTarget _ut = null;
   private List<UpdateTarget> _listUpdateTargets = new ArrayList();
   private Identifier _idMethodName = new Identifier();

   private SetTarget.StVisitor getVisitor() {
      return this._visitor;
   }

   public UpdateTarget getUpdateTarget() {
      return this._ut;
   }

   private void setUpdateTarget(UpdateTarget ut) {
      this._ut = ut;
   }

   public List<UpdateTarget> getUpdateTargets() {
      return this._listUpdateTargets;
   }

   private void setUpdateTargets(List<UpdateTarget> listUpdateTargets) {
      this._listUpdateTargets = listUpdateTargets;
   }

   public Identifier getMethodName() {
      return this._idMethodName;
   }

   public void setMethodName(Identifier idMethodName) {
      this._idMethodName = idMethodName;
   }

   public String format() {
      String s = "";
      if (this.getUpdateTarget() != null) {
         s = this.getUpdateTarget().format();
      } else {
         for(int i = 0; i < this.getUpdateTargets().size(); ++i) {
            s = s + ((UpdateTarget)this.getUpdateTargets().get(i)).format() + ".";
         }

         s = s + this.getMethodName();
      }

      return s;
   }

   public void parse(SqlParser.SetTargetContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().setTarget());
   }

   public void initialize(UpdateTarget ut, List<UpdateTarget> listUpdateTargets, Identifier idMethodName) {
      _il.enter(new Object[0]);
      this.setUpdateTarget(ut);
      this.setUpdateTargets(listUpdateTargets);
      this.setMethodName(idMethodName);
      _il.exit();
   }

   public SetTarget(SqlFactory sf) {
      super(sf);
   }

   private class StVisitor extends EnhancedSqlBaseVisitor<SetTarget> {
      private StVisitor() {
      }

      public SetTarget visitSetTarget(SqlParser.SetTargetContext ctx) {
         SetTarget stResult = SetTarget.this;
         if (ctx.methodName() == null && ctx.updateTarget().size() == 1) {
            SetTarget.this.setUpdateTarget(SetTarget.this.getSqlFactory().newUpdateTarget());
            SetTarget.this.getUpdateTarget().parse(ctx.updateTarget(0));
         } else {
            this.setMethodName(ctx.methodName(), SetTarget.this.getMethodName());
            stResult = (SetTarget)this.visitChildren(ctx);
         }

         return stResult;
      }

      public SetTarget visitUpdateTarget(SqlParser.UpdateTargetContext ctx) {
         UpdateTarget ut = SetTarget.this.getSqlFactory().newUpdateTarget();
         ut.parse(ctx);
         SetTarget.this.getUpdateTargets().add(ut);
         return SetTarget.this;
      }

      // $FF: synthetic method
      StVisitor(Object x1) {
         this();
      }
   }
}
