package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.sqlparser.identifier.SchemaId;
import ch.enterag.utils.logging.IndentLogger;

public class CreateSchemaStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(CreateSchemaStatement.class.getName());
   private CreateSchemaStatement.CssVisitor _visitor = new CreateSchemaStatement.CssVisitor();
   private SchemaId _sidSchemaName = new SchemaId();
   private Identifier _idAuthorizationName = new Identifier();

   private CreateSchemaStatement.CssVisitor getVisitor() {
      return this._visitor;
   }

   public SchemaId getSchemaName() {
      return this._sidSchemaName;
   }

   private void setSchemaName(SchemaId sidSchemaName) {
      this._sidSchemaName = sidSchemaName;
   }

   public Identifier getAuthorizationName() {
      return this._idAuthorizationName;
   }

   private void setAuthorizationName(Identifier idAuthorizationName) {
      this._idAuthorizationName = idAuthorizationName;
   }

   public String format() {
      String sStatement = K.CREATE.getKeyword() + " " + K.SCHEMA.getKeyword() + " " + this.getSchemaName().format();
      if (this.getAuthorizationName().isSet()) {
         sStatement = sStatement + " " + K.AUTHORIZATION.getKeyword() + " " + this.getAuthorizationName().format();
      }

      return sStatement;
   }

   public void parse(SqlParser.CreateSchemaStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().createSchemaStatement());
   }

   public void initialize(SchemaId sidSchemaName, Identifier idAuthorizationName) {
      _il.enter(new Object[]{sidSchemaName, idAuthorizationName});
      this.setSchemaName(sidSchemaName);
      this.setAuthorizationName(idAuthorizationName);
      _il.exit();
   }

   public CreateSchemaStatement(SqlFactory sf) {
      super(sf);
   }

   private class CssVisitor extends EnhancedSqlBaseVisitor<CreateSchemaStatement> {
      private CssVisitor() {
      }

      public CreateSchemaStatement visitSchemaName(SqlParser.SchemaNameContext ctx) {
         this.setSchemaName(ctx, CreateSchemaStatement.this.getSchemaName());
         return CreateSchemaStatement.this;
      }

      public CreateSchemaStatement visitAuthorizationName(SqlParser.AuthorizationNameContext ctx) {
         this.setAuthorizationName(ctx, CreateSchemaStatement.this.getAuthorizationName());
         return CreateSchemaStatement.this;
      }

      // $FF: synthetic method
      CssVisitor(Object x1) {
         this();
      }
   }
}
