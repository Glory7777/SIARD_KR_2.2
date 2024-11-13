package ch.enterag.sqlparser.dml;

import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.expression.SimpleValueSpecification;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.utils.logging.IndentLogger;

public class UpdateTarget extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(UpdateTarget.class.getName());
   private UpdateTarget.UtVisitor _visitor = new UpdateTarget.UtVisitor();
   private Identifier _idColumnName = new Identifier();
   private SimpleValueSpecification _svs = null;

   private UpdateTarget.UtVisitor getVisitor() {
      return this._visitor;
   }

   public Identifier getColumnName() {
      return this._idColumnName;
   }

   private void setColumnName(Identifier idColumnName) {
      this._idColumnName = idColumnName;
   }

   public SimpleValueSpecification getSimpleValueSpecification() {
      return this._svs;
   }

   public void setSimpleValueSpecification(SimpleValueSpecification svs) {
      this._svs = svs;
   }

   public String format() {
      String s = this.getColumnName().format();
      if (this.getSimpleValueSpecification() != null) {
         s = s + "[" + this.getSimpleValueSpecification().format() + "]";
      }

      return s;
   }

   public void parse(SqlParser.UpdateTargetContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().updateTarget());
   }

   public void initialize(Identifier idColumnName, SimpleValueSpecification svs) {
      _il.enter(new Object[0]);
      this.setColumnName(idColumnName);
      this.setSimpleValueSpecification(svs);
      _il.exit();
   }

   public UpdateTarget(SqlFactory sf) {
      super(sf);
   }

   private class UtVisitor extends EnhancedSqlBaseVisitor<UpdateTarget> {
      private UtVisitor() {
      }

      public UpdateTarget visitColumnName(SqlParser.ColumnNameContext ctx) {
         this.setColumnName(ctx, UpdateTarget.this.getColumnName());
         return UpdateTarget.this;
      }

      public UpdateTarget visitSimpleValueSpecification(SqlParser.SimpleValueSpecificationContext ctx) {
         UpdateTarget.this.setSimpleValueSpecification(UpdateTarget.this.getSqlFactory().newSimpleValueSpecification());
         UpdateTarget.this.getSimpleValueSpecification().parse(ctx);
         return UpdateTarget.this;
      }

      // $FF: synthetic method
      UtVisitor(Object x1) {
         this();
      }
   }
}
