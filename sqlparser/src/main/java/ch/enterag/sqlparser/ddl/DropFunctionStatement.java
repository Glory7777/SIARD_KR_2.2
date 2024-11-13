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

public class DropFunctionStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(DropFunctionStatement.class.getName());
   private DropFunctionStatement.DfsVisitor _visitor = new DropFunctionStatement.DfsVisitor();
   private boolean _bSpecific = false;
   private QualifiedId _qiFunctionName = new QualifiedId();
   private boolean _bParameterList = false;
   private List<SqlParameterDeclaration> _listParameters = new ArrayList();
   private DropBehavior _db = null;

   private DropFunctionStatement.DfsVisitor getVisitor() {
      return this._visitor;
   }

   public boolean isSpecific() {
      return this._bSpecific;
   }

   public void setSpecific(boolean bSpecific) {
      this._bSpecific = bSpecific;
   }

   public QualifiedId getFunctionName() {
      return this._qiFunctionName;
   }

   private void setFunctionName(QualifiedId qiFunctionName) {
      this._qiFunctionName = qiFunctionName;
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

      sStatement = sStatement + " " + K.FUNCTION.getKeyword() + " " + this.getFunctionName().format();
      if (this.hasParameterList()) {
         sStatement = sStatement + this.formatParameters();
      }

      sStatement = sStatement + " " + this.getDropBehavior().getKeywords();
      return sStatement;
   }

   public void parse(SqlParser.DropFunctionStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().dropFunctionStatement());
   }

   public void initialize(boolean bSpecific, QualifiedId qiFunctionName, boolean bParameterList, List<SqlParameterDeclaration> listParameters, DropBehavior db) {
      _il.enter(new Object[0]);
      this.setSpecific(bSpecific);
      this.setFunctionName(qiFunctionName);
      this.setParameterList(bParameterList);
      this.setParameters(listParameters);
      this.setDropBehavior(db);
      _il.exit();
   }

   public DropFunctionStatement(SqlFactory sf) {
      super(sf);
   }

   private class DfsVisitor extends EnhancedSqlBaseVisitor<DropFunctionStatement> {
      private DfsVisitor() {
      }

      public DropFunctionStatement visitDropFunctionStatement(SqlParser.DropFunctionStatementContext ctx) {
         if (ctx.SPECIFIC() != null) {
            DropFunctionStatement.this.setSpecific(true);
         }

         if (ctx.LEFT_PAREN() != null && ctx.RIGHT_PAREN() != null) {
            DropFunctionStatement.this.setParameterList(true);
         }

         this.setRoutineName(ctx.routineName(), DropFunctionStatement.this.getFunctionName());
         return (DropFunctionStatement)this.visitChildren(ctx);
      }

      public DropFunctionStatement visitSqlParameterDeclaration(SqlParser.SqlParameterDeclarationContext ctx) {
         SqlParameterDeclaration spd = DropFunctionStatement.this.getSqlFactory().newSqlParameterDeclaration();
         spd.parse(ctx);
         DropFunctionStatement.this.getParameters().add(spd);
         return DropFunctionStatement.this;
      }

      public DropFunctionStatement visitDropBehavior(SqlParser.DropBehaviorContext ctx) {
         DropFunctionStatement.this.setDropBehavior(this.getDropBehavior(ctx));
         return DropFunctionStatement.this;
      }

      // $FF: synthetic method
      DfsVisitor(Object x1) {
         this();
      }
   }
}
