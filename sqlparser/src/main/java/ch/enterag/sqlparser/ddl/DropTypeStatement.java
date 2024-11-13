package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.ddl.enums.DropBehavior;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;

public class DropTypeStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(DropTypeStatement.class.getName());
   private DropTypeStatement.DtsVisitor _visitor = new DropTypeStatement.DtsVisitor();
   private QualifiedId _qUdtName = new QualifiedId();
   private DropBehavior _db = null;

   private DropTypeStatement.DtsVisitor getVisitor() {
      return this._visitor;
   }

   public QualifiedId getUdtName() {
      return this._qUdtName;
   }

   private void setUdtName(QualifiedId qUdtName) {
      this._qUdtName = qUdtName;
   }

   public DropBehavior getDropBehavior() {
      return this._db;
   }

   public void setDropBehavior(DropBehavior db) {
      this._db = db;
   }

   public String format() {
      String sStatement = K.DROP.getKeyword() + " " + K.TYPE.getKeyword() + " " + this.getUdtName().format() + " " + this.getDropBehavior().getKeywords();
      return sStatement;
   }

   public void parse(SqlParser.DropTypeStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().dropTypeStatement());
   }

   public void initialize(QualifiedId qUdtName, DropBehavior db) {
      _il.enter(new Object[]{qUdtName, db});
      this.setUdtName(qUdtName);
      this.setDropBehavior(db);
      _il.exit();
   }

   public DropTypeStatement(SqlFactory sf) {
      super(sf);
   }

   private class DtsVisitor extends EnhancedSqlBaseVisitor<DropTypeStatement> {
      private DtsVisitor() {
      }

      public DropTypeStatement visitUdtName(SqlParser.UdtNameContext ctx) {
         this.setUdtName(ctx, DropTypeStatement.this.getUdtName());
         return DropTypeStatement.this;
      }

      public DropTypeStatement visitDropBehavior(SqlParser.DropBehaviorContext ctx) {
         DropTypeStatement.this.setDropBehavior(this.getDropBehavior(ctx));
         return DropTypeStatement.this;
      }

      // $FF: synthetic method
      DtsVisitor(Object x1) {
         this();
      }
   }
}
