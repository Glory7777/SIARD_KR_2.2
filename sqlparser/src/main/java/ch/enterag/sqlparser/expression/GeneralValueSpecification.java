package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.SqlStatement;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.expression.enums.GeneralValue;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.ColonId;
import ch.enterag.sqlparser.identifier.IdChain;
import ch.enterag.utils.logging.IndentLogger;

public class GeneralValueSpecification extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(GeneralValueSpecification.class.getName());
   private GeneralValueSpecification.GvsVisitor _visitor = new GeneralValueSpecification.GvsVisitor();
   private ColonId _cid = new ColonId();
   private ColonId _cidIndicator = new ColonId();
   private IdChain _idcColumnOrParameter = new IdChain();
   private GeneralValue _gv = null;

   private GeneralValueSpecification.GvsVisitor getVisitor() {
      return this._visitor;
   }

   public ColonId getVariable() {
      return this._cid;
   }

   private void setVariable(ColonId cid) {
      this._cid = cid;
   }

   public ColonId getIndicator() {
      return this._cidIndicator;
   }

   private void setIndicator(ColonId cidIndicator) {
      this._cidIndicator = cidIndicator;
   }

   public IdChain getColumnOrParameter() {
      return this._idcColumnOrParameter;
   }

   private void setColumnOrParameter(IdChain idcParameter) {
      this._idcColumnOrParameter = idcParameter;
   }

   public GeneralValue getGeneralValue() {
      return this._gv;
   }

   public void setGeneralValue(GeneralValue generalValue) {
      this._gv = generalValue;
   }

   public String format() {
      String sSpecification = null;
      if (this.getVariable().isSet()) {
         sSpecification = this.getVariable().format();
         if (this.getIndicator().isSet()) {
            sSpecification = sSpecification + " " + K.INDICATOR.getKeyword() + " " + this.getIndicator().format();
         }
      } else if (this.getColumnOrParameter().isSet()) {
         sSpecification = this.getColumnOrParameter().format();
      } else if (this.getGeneralValue() != null) {
         sSpecification = this.getGeneralValue().getKeywords();
      }

      return sSpecification;
   }

   public DataType getDataType(SqlStatement ss) {
      DataType dt = null;
      if (this.getColumnOrParameter().isSet()) {
         dt = ss.getColumnType(this.getColumnOrParameter());
      } else {
         if (this.getGeneralValue() == null) {
            throw new IllegalArgumentException("Evaluating variables is not supported!");
         }

         dt = ss.getGeneralType(this);
      }

      return dt;
   }

   public Object evaluate(SqlStatement ss, boolean bAggregated) {
      Object oValue = null;
      if (this.getColumnOrParameter().isSet()) {
         oValue = ss.getColumnValue(this.getColumnOrParameter(), bAggregated);
      } else {
         if (this.getGeneralValue() == null) {
            throw new IllegalArgumentException("Evaluating variables is not supported!");
         }

         oValue = ss.getGeneralValue(this);
      }

      return oValue;
   }

   public void parse(SqlParser.GeneralValueSpecificationContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().generalValueSpecification());
   }

   public void initialize(ColonId cidVariable, ColonId cidIndicator, IdChain idcColumnOrParameter, GeneralValue gv) {
      _il.enter(new Object[]{cidVariable, cidIndicator, idcColumnOrParameter, gv});
      this.setVariable(cidVariable);
      this.setIndicator(cidIndicator);
      this.setColumnOrParameter(idcColumnOrParameter);
      this.setGeneralValue(gv);
      _il.exit();
   }

   public void initialize(IdChain idcColumnOrParameter) {
      this.setColumnOrParameter(idcColumnOrParameter);
   }

   public GeneralValueSpecification(SqlFactory sf) {
      super(sf);
   }

   private class GvsVisitor extends EnhancedSqlBaseVisitor<GeneralValueSpecification> {
      private GvsVisitor() {
      }

      public GeneralValueSpecification visitGeneralValueSpecification(SqlParser.GeneralValueSpecificationContext ctx) {
         GeneralValueSpecification vepResult = GeneralValueSpecification.this;
         GeneralValueSpecification.this.setGeneralValue(this.getGeneralValue(ctx));
         if (vepResult.getGeneralValue() == null) {
            vepResult = (GeneralValueSpecification)this.visitChildren(ctx);
         }

         return vepResult;
      }

      public GeneralValueSpecification visitVariableName(SqlParser.VariableNameContext ctx) {
         this.setVariableName(ctx, GeneralValueSpecification.this.getVariable());
         return GeneralValueSpecification.this;
      }

      public GeneralValueSpecification visitIndicatorVariable(SqlParser.IndicatorVariableContext ctx) {
         this.setVariableName(ctx.variableName(), GeneralValueSpecification.this.getIndicator());
         return GeneralValueSpecification.this;
      }

      public GeneralValueSpecification visitIdentifierChain(SqlParser.IdentifierChainContext ctx) {
         this.setIdChain(ctx, GeneralValueSpecification.this.getColumnOrParameter());
         return GeneralValueSpecification.this;
      }

      // $FF: synthetic method
      GvsVisitor(Object x1) {
         this();
      }
   }
}
