package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.ddl.enums.DropBehavior;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.SchemaId;
import ch.enterag.utils.logging.IndentLogger;

public class DropSchemaStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(DropSchemaStatement.class.getName());
   private DropSchemaStatement.DssVisitor _visitor = new DropSchemaStatement.DssVisitor();
   private SchemaId _sidSchemaName = new SchemaId();
   private DropBehavior _db = null;

   private DropSchemaStatement.DssVisitor getVisitor() {
      return this._visitor;
   }

   public SchemaId getSchemaName() {
      return this._sidSchemaName;
   }

   private void setSchemaName(SchemaId sidSchemaName) {
      this._sidSchemaName = sidSchemaName;
   }

   public DropBehavior getDropBehavior() {
      return this._db;
   }

   public void setDropBehavior(DropBehavior db) {
      this._db = db;
   }

   public String format() {
      String sStatement = K.DROP.getKeyword() + " " + K.SCHEMA.getKeyword() + " " + this.getSchemaName().format() + " " + this.getDropBehavior().getKeywords();
      return sStatement;
   }

   public void parse(SqlParser.DropSchemaStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().dropSchemaStatement());
   }

   public void initialize(SchemaId sidSchemaName, DropBehavior db) {
      _il.enter(new Object[]{sidSchemaName, db});
      this.setSchemaName(sidSchemaName);
      this.setDropBehavior(db);
      _il.exit();
   }

   public DropSchemaStatement(SqlFactory sf) {
      super(sf);
   }

   private class DssVisitor extends EnhancedSqlBaseVisitor<DropSchemaStatement> {
      private DssVisitor() {
      }

      public DropSchemaStatement visitSchemaName(SqlParser.SchemaNameContext ctx) {
         this.setSchemaName(ctx, DropSchemaStatement.this.getSchemaName());
         return DropSchemaStatement.this;
      }

      public DropSchemaStatement visitDropBehavior(SqlParser.DropBehaviorContext ctx) {
         DropSchemaStatement.this.setDropBehavior(this.getDropBehavior(ctx));
         return DropSchemaStatement.this;
      }

      // $FF: synthetic method
      DssVisitor(Object x1) {
         this();
      }
   }
}
