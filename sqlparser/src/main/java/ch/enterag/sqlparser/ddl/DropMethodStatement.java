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

public class DropMethodStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(DropMethodStatement.class.getName());
   private DropMethodStatement.DmsVisitor _visitor = new DropMethodStatement.DmsVisitor();
   private boolean _bSpecific = false;
   private QualifiedId _qiMethodName = new QualifiedId();
   private boolean _bParameterList = false;
   private List<SqlParameterDeclaration> _listParameters = new ArrayList();
   private QualifiedId _qiUdtName = new QualifiedId();
   private DropBehavior _db = null;

   private DropMethodStatement.DmsVisitor getVisitor() {
      return this._visitor;
   }

   public boolean isSpecific() {
      return this._bSpecific;
   }

   public void setSpecific(boolean bSpecific) {
      this._bSpecific = bSpecific;
   }

   public QualifiedId getMethodName() {
      return this._qiMethodName;
   }

   private void setMethodName(QualifiedId qiMethodName) {
      this._qiMethodName = qiMethodName;
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

   public QualifiedId getUdtName() {
      return this._qiUdtName;
   }

   private void setUdtName(QualifiedId qiUdtName) {
      this._qiUdtName = qiUdtName;
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

      sStatement = sStatement + " " + K.METHOD.getKeyword() + " " + this.getMethodName().format();
      if (this.hasParameterList()) {
         sStatement = sStatement + this.formatParameters();
      }

      sStatement = sStatement + " " + K.FOR.getKeyword() + " " + this.getUdtName().format() + " " + this.getDropBehavior().getKeywords();
      return sStatement;
   }

   public void parse(SqlParser.DropMethodStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().dropMethodStatement());
   }

   public void initialize(boolean bSpecific, QualifiedId qiFunctionName, boolean bParameterList, List<SqlParameterDeclaration> listParameters, QualifiedId qiUdtName, DropBehavior db) {
      _il.enter(new Object[0]);
      this.setSpecific(bSpecific);
      this.setMethodName(qiFunctionName);
      this.setParameterList(bParameterList);
      this.setParameters(listParameters);
      this.setUdtName(qiUdtName);
      this.setDropBehavior(db);
      _il.exit();
   }

   public DropMethodStatement(SqlFactory sf) {
      super(sf);
   }

   private class DmsVisitor extends EnhancedSqlBaseVisitor<DropMethodStatement> {
      private DmsVisitor() {
      }

      public DropMethodStatement visitDropMethodStatement(SqlParser.DropMethodStatementContext ctx) {
         if (ctx.SPECIFIC() != null) {
            DropMethodStatement.this.setSpecific(true);
         }

         if (ctx.LEFT_PAREN() != null && ctx.RIGHT_PAREN() != null) {
            DropMethodStatement.this.setParameterList(true);
         }

         this.setRoutineName(ctx.routineName(), DropMethodStatement.this.getMethodName());
         return (DropMethodStatement)this.visitChildren(ctx);
      }

      public DropMethodStatement visitSqlParameterDeclaration(SqlParser.SqlParameterDeclarationContext ctx) {
         SqlParameterDeclaration spd = DropMethodStatement.this.getSqlFactory().newSqlParameterDeclaration();
         spd.parse(ctx);
         DropMethodStatement.this.getParameters().add(spd);
         return DropMethodStatement.this;
      }

      public DropMethodStatement visitUdtName(SqlParser.UdtNameContext ctx) {
         this.setUdtName(ctx, DropMethodStatement.this.getUdtName());
         return DropMethodStatement.this;
      }

      public DropMethodStatement visitDropBehavior(SqlParser.DropBehaviorContext ctx) {
         DropMethodStatement.this.setDropBehavior(this.getDropBehavior(ctx));
         return DropMethodStatement.this;
      }

      // $FF: synthetic method
      DmsVisitor(Object x1) {
         this();
      }
   }
}
