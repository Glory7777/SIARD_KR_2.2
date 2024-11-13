package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.ddl.enums.DropBehavior;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class DropProcedureStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(DropProcedureStatement.class.getName());
   private DropProcedureStatement.DpsVisitor _visitor = new DropProcedureStatement.DpsVisitor();
   private boolean _bSpecific = false;
   private QualifiedId _qiProcedureName = new QualifiedId();
   private boolean _bParameterList = false;
   private List<SqlParameterDeclaration> _listParameters = new ArrayList();
   private DropBehavior _db = null;

   private DropProcedureStatement.DpsVisitor getVisitor() {
      return this._visitor;
   }

   public boolean isSpecific() {
      return this._bSpecific;
   }

   public void setSpecific(boolean bSpecific) {
      this._bSpecific = bSpecific;
   }

   public QualifiedId getProcedureName() {
      return this._qiProcedureName;
   }

   private void setProcedureName(QualifiedId qiProcedureName) {
      this._qiProcedureName = qiProcedureName;
   }

   public boolean hasParameterList() {
      return this._bParameterList;
   }

   public void setParameterList(boolean bParameterList) {
      this._bParameterList = bParameterList;
   }

   public List<SqlParameterDeclaration> getParameters() {
      return this._listParameters;
   }

   private void setParameters(List<SqlParameterDeclaration> listParameters) {
      this._listParameters = listParameters;
   }

   public DropBehavior getDropBehavior() {
      return this._db;
   }

   public void setDropBehavior(DropBehavior db) {
      this._db = db;
   }

   private String formatParameters() {
      String s = "(";

      for(int i = 0; i < this.getParameters().size(); ++i) {
         if (i > 0) {
            s = s + "," + " ";
         }

         s = s + ((SqlParameterDeclaration)this.getParameters().get(i)).format();
      }

      s = s + ")";
      return s;
   }

   public String format() {
      String sStatement = K.DROP.getKeyword();
      if (this.isSpecific()) {
         sStatement = sStatement + " " + K.SPECIFIC.getKeyword();
      }

      sStatement = sStatement + " " + K.PROCEDURE.getKeyword() + " " + this.getProcedureName().format();
      if (this.hasParameterList()) {
         sStatement = sStatement + this.formatParameters();
      }

      sStatement = sStatement + " " + this.getDropBehavior().getKeywords();
      return sStatement;
   }

   public void parse(SqlParser.DropProcedureStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().dropProcedureStatement());
   }

   public void initialize(boolean bSpecific, QualifiedId qiProcedureName, boolean bParameterList, List<SqlParameterDeclaration> listParameters, DropBehavior db) {
      _il.enter(new Object[0]);
      this.setSpecific(bSpecific);
      this.setProcedureName(qiProcedureName);
      this.setParameterList(bParameterList);
      this.setParameters(listParameters);
      this.setDropBehavior(db);
      _il.exit();
   }

   public DropProcedureStatement(SqlFactory sf) {
      super(sf);
   }

   private class DpsVisitor extends EnhancedSqlBaseVisitor<DropProcedureStatement> {
      private DpsVisitor() {
      }

      public DropProcedureStatement visitDropProcedureStatement(SqlParser.DropProcedureStatementContext ctx) {
         if (ctx.SPECIFIC() != null) {
            DropProcedureStatement.this.setSpecific(true);
         }

         if (ctx.LEFT_PAREN() != null && ctx.RIGHT_PAREN() != null) {
            DropProcedureStatement.this.setParameterList(true);
         }

         this.setRoutineName(ctx.routineName(), DropProcedureStatement.this.getProcedureName());
         return (DropProcedureStatement)this.visitChildren(ctx);
      }

      public DropProcedureStatement visitSqlParameterDeclaration(SqlParser.SqlParameterDeclarationContext ctx) {
         SqlParameterDeclaration spd = DropProcedureStatement.this.getSqlFactory().newSqlParameterDeclaration();
         spd.parse(ctx);
         DropProcedureStatement.this.getParameters().add(spd);
         return DropProcedureStatement.this;
      }

      public DropProcedureStatement visitDropBehavior(SqlParser.DropBehaviorContext ctx) {
         DropProcedureStatement.this.setDropBehavior(this.getDropBehavior(ctx));
         return DropProcedureStatement.this;
      }

      // $FF: synthetic method
      DpsVisitor(Object x1) {
         this();
      }
   }
}
