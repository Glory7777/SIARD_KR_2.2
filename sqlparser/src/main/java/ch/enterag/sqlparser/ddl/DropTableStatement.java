package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.ddl.enums.DropBehavior;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;

public class DropTableStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(DropTableStatement.class.getName());
   private DropTableStatement.DtsVisitor _visitor = new DropTableStatement.DtsVisitor();
   private QualifiedId _qTableName = new QualifiedId();
   private DropBehavior _db = null;

   private DropTableStatement.DtsVisitor getVisitor() {
      return this._visitor;
   }

   public QualifiedId getTableName() {
      return this._qTableName;
   }

   private void setTableName(QualifiedId qTableName) {
      this._qTableName = qTableName;
   }

   public DropBehavior getDropBehavior() {
      return this._db;
   }

   public void setDropBehavior(DropBehavior db) {
      this._db = db;
   }

   public String format() {
      String sStatement = K.DROP.getKeyword() + " " + K.TABLE.getKeyword() + " " + this.getTableName().format() + " " + this.getDropBehavior().getKeywords();
      return sStatement;
   }

   public void parse(SqlParser.DropTableStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().dropTableStatement());
   }

   public void initialize(QualifiedId qTableName, DropBehavior db) {
      _il.enter(new Object[]{qTableName, db});
      this.setTableName(qTableName);
      this.setDropBehavior(db);
      _il.exit();
   }

   public DropTableStatement(SqlFactory sf) {
      super(sf);
   }

   private class DtsVisitor extends EnhancedSqlBaseVisitor<DropTableStatement> {
      private DtsVisitor() {
      }

      public DropTableStatement visitTableName(SqlParser.TableNameContext ctx) {
         this.setTableName(ctx, DropTableStatement.this.getTableName());
         return DropTableStatement.this;
      }

      public DropTableStatement visitDropBehavior(SqlParser.DropBehaviorContext ctx) {
         DropTableStatement.this.setDropBehavior(this.getDropBehavior(ctx));
         return DropTableStatement.this;
      }

      // $FF: synthetic method
      DtsVisitor(Object x1) {
         this();
      }
   }
}
