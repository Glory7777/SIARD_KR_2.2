package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.ColonId;
import ch.enterag.sqlparser.identifier.IdChain;
import ch.enterag.utils.logging.IndentLogger;

public class TargetSpecification extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(TargetSpecification.class.getName());
   private TargetSpecification.TsVisitor _visitor = new TargetSpecification.TsVisitor();
   private ColonId _ciVariableName = new ColonId();
   private ColonId _ciIndicatorVariable = new ColonId();
   private IdChain _icSqlOrColumnRef = new IdChain();
   private IdChain _icTargetArrayRef = new IdChain();
   private SimpleValueSpecification _svs = null;
   private boolean _bQuestionMark = false;

   private TargetSpecification.TsVisitor getVisitor() {
      return this._visitor;
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

   public IdChain getSqlParameterOrColumnReference() {
      return this._icSqlOrColumnRef;
   }

   private void setSqlParameterOrColumnReference(IdChain icSqlOrColumnRef) {
      this._icSqlOrColumnRef = icSqlOrColumnRef;
   }

   public IdChain getTargetArrayReference() {
      return this._icTargetArrayRef;
   }

   private void setTargetArrayReference(IdChain icTargetArrayRef) {
      this._icTargetArrayRef = icTargetArrayRef;
   }

   public SimpleValueSpecification getSimpleValueSpecification() {
      return this._svs;
   }

   public void setSimpleValueSpecification(SimpleValueSpecification svs) {
      this._svs = svs;
   }

   public boolean isQuestionMark() {
      return this._bQuestionMark;
   }

   public void setQuestionMark(boolean bQuestionMark) {
      this._bQuestionMark = bQuestionMark;
   }

   public String format() {
      String sSpecification = "";
      if (this.getVariableName().isSet()) {
         sSpecification = this.getVariableName().format();
         if (this.getIndicatorVariable().isSet()) {
            sSpecification = sSpecification + " " + K.INDICATOR.getKeyword() + " " + this.getIndicatorVariable().format();
         }
      } else if (this.getSqlParameterOrColumnReference().isSet()) {
         sSpecification = this.getSqlParameterOrColumnReference().format();
      } else if (this.getTargetArrayReference().isSet() && this.getSimpleValueSpecification() != null) {
         sSpecification = this.getTargetArrayReference().format() + "[" + this.getSimpleValueSpecification().format() + "]";
      } else if (this.isQuestionMark()) {
         sSpecification = "?";
      }

      return sSpecification;
   }

   public void parse(SqlParser.TargetSpecificationContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().targetSpecification());
   }

   public void initialize(ColonId ciVariableName, ColonId ciIndicatorVariable, IdChain icSqlParameterOrColumnReference, IdChain icTargetArrayRef, SimpleValueSpecification svs, boolean bQuestionMark) {
      _il.enter(new Object[]{ciVariableName, ciIndicatorVariable, icSqlParameterOrColumnReference, icTargetArrayRef, svs, String.valueOf(bQuestionMark)});
      this.setVariableName(ciVariableName);
      this.setIndicatorVariable(ciIndicatorVariable);
      this.setSqlParameterOrColumnReference(icSqlParameterOrColumnReference);
      this.setTargetArrayReference(icTargetArrayRef);
      this.setSimpleValueSpecification(svs);
      this.setQuestionMark(bQuestionMark);
      _il.exit();
   }

   public TargetSpecification(SqlFactory sf) {
      super(sf);
   }

   private class TsVisitor extends EnhancedSqlBaseVisitor<TargetSpecification> {
      private TsVisitor() {
      }

      public TargetSpecification visitTargetSpecification(SqlParser.TargetSpecificationContext ctx) {
         TargetSpecification tsReturn = TargetSpecification.this;
         if (ctx.QUESTION_MARK() != null) {
            TargetSpecification.this.setQuestionMark(true);
         } else {
            tsReturn = (TargetSpecification)this.visitChildren(ctx);
         }

         return tsReturn;
      }

      public TargetSpecification visitVariableName(SqlParser.VariableNameContext ctx) {
         this.setVariableName(ctx, TargetSpecification.this.getVariableName());
         return TargetSpecification.this;
      }

      public TargetSpecification visitIndicatorVariable(SqlParser.IndicatorVariableContext ctx) {
         this.setVariableName(ctx.variableName(), TargetSpecification.this.getIndicatorVariable());
         return TargetSpecification.this;
      }

      public TargetSpecification visitIdentifierChain(SqlParser.IdentifierChainContext ctx) {
         this.setIdChain(ctx, TargetSpecification.this.getSqlParameterOrColumnReference());
         return TargetSpecification.this;
      }

      public TargetSpecification visitTargetArrayReference(SqlParser.TargetArrayReferenceContext ctx) {
         this.setIdChain(ctx.identifierChain(), TargetSpecification.this.getTargetArrayReference());
         return TargetSpecification.this;
      }

      public TargetSpecification visitSimpleValueSpecification(SqlParser.SimpleValueSpecificationContext ctx) {
         TargetSpecification.this.setSimpleValueSpecification(TargetSpecification.this.getSqlFactory().newSimpleValueSpecification());
         TargetSpecification.this.getSimpleValueSpecification().parse(ctx);
         return TargetSpecification.this;
      }

      // $FF: synthetic method
      TsVisitor(Object x1) {
         this();
      }
   }
}
