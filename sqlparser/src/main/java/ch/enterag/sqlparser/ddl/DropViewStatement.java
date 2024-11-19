package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.ddl.enums.DropBehavior;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;

public class DropViewStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(DropViewStatement.class.getName());
   private DropViewStatement.DvsVisitor _visitor = new DropViewStatement.DvsVisitor();
   private QualifiedId _qViewName = new QualifiedId();
   private DropBehavior _db = null;

   private DropViewStatement.DvsVisitor getVisitor() {
      return this._visitor;
   }

   public QualifiedId getViewName() {
      return this._qViewName;
   }

   private void setViewName(QualifiedId qViewName) {
      this._qViewName = qViewName;
   }

   public DropBehavior getDropBehavior() {
      return this._db;
   }

   public void setDropBehavior(DropBehavior db) {
      this._db = db;
   }

   public String format() {
      String sStatement = K.DROP.getKeyword() + " " + K.VIEW.getKeyword() + " " + this.getViewName().format() + " " + this.getDropBehavior().getKeywords();
      return sStatement;
   }

   public void parse(SqlParser.DropViewStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().dropViewStatement());
   }

   public void initialize(QualifiedId qViewName, DropBehavior db) {
      _il.enter(new Object[]{qViewName, db});
      this.setViewName(qViewName);
      this.setDropBehavior(db);
      _il.exit();
   }

   public DropViewStatement(SqlFactory sf) {
      super(sf);
   }

   private class DvsVisitor extends EnhancedSqlBaseVisitor<DropViewStatement> {
      private DvsVisitor() {
      }

      public DropViewStatement visitTableName(SqlParser.TableNameContext ctx) {
         this.setTableName(ctx, DropViewStatement.this.getViewName());
         return DropViewStatement.this;
      }

      public DropViewStatement visitDropBehavior(SqlParser.DropBehaviorContext ctx) {
         DropViewStatement.this.setDropBehavior(this.getDropBehavior(ctx));
         return DropViewStatement.this;
      }

      // $FF: synthetic method
      DvsVisitor(Object x1) {
         this();
      }
   }
}
