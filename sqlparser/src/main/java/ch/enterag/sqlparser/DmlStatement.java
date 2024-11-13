package ch.enterag.sqlparser;

import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.dml.DeleteStatement;
import ch.enterag.sqlparser.dml.InsertStatement;
import ch.enterag.sqlparser.dml.UpdateStatement;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;

public class DmlStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(DmlStatement.class.getName());
   private DmlStatement.DsVisitor _visitor = new DmlStatement.DsVisitor();
   private InsertStatement _is = null;
   private DeleteStatement _ds = null;
   private UpdateStatement _us = null;

   private DmlStatement.DsVisitor getVisitor() {
      return this._visitor;
   }

   public InsertStatement getInsertStatement() {
      return this._is;
   }

   public void setInsertStatement(InsertStatement is) {
      this._is = is;
   }

   public DeleteStatement getDeleteStatement() {
      return this._ds;
   }

   public void setDeleteStatement(DeleteStatement ds) {
      this._ds = ds;
   }

   public UpdateStatement getUpdateStatement() {
      return this._us;
   }

   public void setUpdateStatement(UpdateStatement us) {
      this._us = us;
   }

   public String format() {
      String sStatement = null;
      if (this.getInsertStatement() != null) {
         sStatement = this.getInsertStatement().format();
      } else if (this.getDeleteStatement() != null) {
         sStatement = this.getDeleteStatement().format();
      } else if (this.getUpdateStatement() != null) {
         sStatement = this.getUpdateStatement().format();
      }

      return sStatement;
   }

   public void parse(SqlParser.DmlStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().dmlStatement());
   }

   public void initialize(InsertStatement is, DeleteStatement ds, UpdateStatement us) {
      _il.enter(new Object[0]);
      this.setInsertStatement(is);
      this.setDeleteStatement(ds);
      this.setUpdateStatement(us);
      _il.exit();
   }

   public DmlStatement(SqlFactory sf) {
      super(sf);
   }

   private class DsVisitor extends EnhancedSqlBaseVisitor<DmlStatement> {
      private DsVisitor() {
      }

      public DmlStatement visitInsertStatement(SqlParser.InsertStatementContext ctx) {
         DmlStatement.this.setInsertStatement(DmlStatement.this.getSqlFactory().newInsertStatement());
         DmlStatement.this.getInsertStatement().parse(ctx);
         return DmlStatement.this;
      }

      public DmlStatement visitDeleteStatement(SqlParser.DeleteStatementContext ctx) {
         DmlStatement.this.setDeleteStatement(DmlStatement.this.getSqlFactory().newDeleteStatement());
         DmlStatement.this.getDeleteStatement().parse(ctx);
         return DmlStatement.this;
      }

      public DmlStatement visitUpdateStatement(SqlParser.UpdateStatementContext ctx) {
         DmlStatement.this.setUpdateStatement(DmlStatement.this.getSqlFactory().newUpdateStatement());
         DmlStatement.this.getUpdateStatement().parse(ctx);
         return DmlStatement.this;
      }

      // $FF: synthetic method
      DsVisitor(Object x1) {
         this();
      }
   }
}
