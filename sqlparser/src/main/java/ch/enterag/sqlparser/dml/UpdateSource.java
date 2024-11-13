package ch.enterag.sqlparser.dml;

import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.dml.enums.SpecialValue;
import ch.enterag.sqlparser.expression.ValueExpression;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;

public class UpdateSource extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(UpdateSource.class.getName());
   private UpdateSource.UsVisitor _visitor = new UpdateSource.UsVisitor();
   private ValueExpression _ve = null;
   private SpecialValue _sv = null;

   private UpdateSource.UsVisitor getVisitor() {
      return this._visitor;
   }

   public ValueExpression getValueExpression() {
      return this._ve;
   }

   public void setValueExpression(ValueExpression ve) {
      this._ve = ve;
   }

   public SpecialValue getSpecialValue() {
      return this._sv;
   }

   public void setSpecialValue(SpecialValue sv) {
      this._sv = sv;
   }

   public String format() {
      String s = null;
      if (this.getValueExpression() != null) {
         s = this.getValueExpression().format();
      } else if (this.getSpecialValue() != null) {
         s = this.getSpecialValue().getKeywords();
      }

      return s;
   }

   public void parse(SqlParser.UpdateSourceContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().updateSource());
   }

   public void initialize(ValueExpression ve, SpecialValue sv) {
      _il.enter(new Object[0]);
      this.setValueExpression(ve);
      this.setSpecialValue(sv);
      _il.exit();
   }

   public UpdateSource(SqlFactory sf) {
      super(sf);
   }

   private class UsVisitor extends EnhancedSqlBaseVisitor<UpdateSource> {
      private UsVisitor() {
      }

      public UpdateSource visitValueExpression(SqlParser.ValueExpressionContext ctx) {
         UpdateSource.this.setValueExpression(UpdateSource.this.getSqlFactory().newValueExpression());
         UpdateSource.this.getValueExpression().parse(ctx);
         return UpdateSource.this;
      }

      public UpdateSource visitSpecialValue(SqlParser.SpecialValueContext ctx) {
         UpdateSource.this.setSpecialValue(this.getSpecialValue(ctx));
         return UpdateSource.this;
      }

      // $FF: synthetic method
      UsVisitor(Object x1) {
         this();
      }
   }
}
