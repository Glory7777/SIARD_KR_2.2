package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.ColonId;
import ch.enterag.sqlparser.identifier.IdChain;
import ch.enterag.utils.logging.IndentLogger;

public class SimpleValueSpecification extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(SimpleValueSpecification.class.getName());
   private SimpleValueSpecification.SvsVisitor _visitor = new SimpleValueSpecification.SvsVisitor();
   private Literal _literal = null;
   private ColonId _ciVariableName = new ColonId();
   private ColonId _ciIndicatorVariable = new ColonId();
   private IdChain _icSqlParameter = null;

   private SimpleValueSpecification.SvsVisitor getVisitor() {
      return this._visitor;
   }

   public Literal getLiteral() {
      return this._literal;
   }

   public void setLiteral(Literal literal) {
      this._literal = literal;
   }

   public ColonId getVariableName() {
      return this._ciVariableName;
   }

   private void setVariableName(ColonId ciVariableName) {
      this._ciVariableName = ciVariableName;
   }

   public ColonId getIndicatorVariable() {
      return this._ciIndicatorVariable;
   }

   private void setIndicatorVariable(ColonId ciIndicatorVariable) {
      this._ciIndicatorVariable = ciIndicatorVariable;
   }

   public IdChain getSqlParameterSpecification() {
      return this._icSqlParameter;
   }

   private void setSqlParameterSpecification(IdChain icSqlParameter) {
      this._icSqlParameter = icSqlParameter;
   }

   public String format() {
      String sSpecification = null;
      if (this.getLiteral() != null) {
         sSpecification = this.getLiteral().format();
      } else if (this.getVariableName().isSet()) {
         sSpecification = this.getVariableName().format();
         if (this.getIndicatorVariable().isSet()) {
            sSpecification = sSpecification + " " + K.INDICATOR.getKeyword() + " " + this.getIndicatorVariable().format();
         }
      } else if (this.getSqlParameterSpecification().isSet()) {
         sSpecification = this.getSqlParameterSpecification().format();
      }

      return sSpecification;
   }

   public void parse(SqlParser.SimpleValueSpecificationContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().simpleValueSpecification());
   }

   public void initialize(Literal literal, ColonId ciVariableName, ColonId ciIndicatorVariable, IdChain icSqlParameter) {
      _il.enter(new Object[]{literal, ciVariableName, ciIndicatorVariable, icSqlParameter});
      this.setLiteral(literal);
      this.setVariableName(ciVariableName);
      this.setIndicatorVariable(ciIndicatorVariable);
      this.setSqlParameterSpecification(icSqlParameter);
      _il.exit();
   }

   public SimpleValueSpecification(SqlFactory sf) {
      super(sf);
   }

   private class SvsVisitor extends EnhancedSqlBaseVisitor<SimpleValueSpecification> {
      private SvsVisitor() {
      }

      public SimpleValueSpecification visitLiteral(SqlParser.LiteralContext ctx) {
         SimpleValueSpecification.this.setLiteral(SimpleValueSpecification.this.getSqlFactory().newLiteral());
         SimpleValueSpecification.this.getLiteral().parse(ctx);
         return SimpleValueSpecification.this;
      }

      public SimpleValueSpecification visitVariableName(SqlParser.VariableNameContext ctx) {
         this.setVariableName(ctx, SimpleValueSpecification.this.getVariableName());
         return SimpleValueSpecification.this;
      }

      public SimpleValueSpecification visitIndicatorVariable(SqlParser.IndicatorVariableContext ctx) {
         this.setVariableName(ctx.variableName(), SimpleValueSpecification.this.getIndicatorVariable());
         return SimpleValueSpecification.this;
      }

      public SimpleValueSpecification visitIdentifierChain(SqlParser.IdentifierChainContext ctx) {
         this.setIdChain(ctx, SimpleValueSpecification.this.getSqlParameterSpecification());
         return SimpleValueSpecification.this;
      }

      // $FF: synthetic method
      SvsVisitor(Object x1) {
         this();
      }
   }
}
