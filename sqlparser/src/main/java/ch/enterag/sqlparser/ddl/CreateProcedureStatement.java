package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class CreateProcedureStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(CreateProcedureStatement.class.getName());
   private CreateProcedureStatement.CpsVisitor _visitor = new CreateProcedureStatement.CpsVisitor();
   private QualifiedId _qiProcedureName = new QualifiedId();
   private List<SqlParameterDeclaration> _listParameters = new ArrayList();
   private RoutineCharacteristics _rc = null;
   private RoutineBody _rb = null;

   private CreateProcedureStatement.CpsVisitor getVisitor() {
      return this._visitor;
   }

   public QualifiedId getProcedureName() {
      return this._qiProcedureName;
   }

   private void setProcedureName(QualifiedId qiProcedureName) {
      this._qiProcedureName = qiProcedureName;
   }

   public List<SqlParameterDeclaration> getParameters() {
      return this._listParameters;
   }

   private void setParameters(List<SqlParameterDeclaration> listParameters) {
      this._listParameters = listParameters;
   }

   public RoutineCharacteristics getRoutineCharacteristics() {
      return this._rc;
   }

   public void setRoutineCharacteristics(RoutineCharacteristics rc) {
      this._rc = rc;
   }

   public RoutineBody getRoutineBody() {
      return this._rb;
   }

   public void setRoutineBody(RoutineBody rb) {
      this._rb = rb;
   }

   protected String formatParameters() {
      String s = "(";

      for(int i = 0; i < this.getParameters().size(); ++i) {
         if (i > 0) {
            s = s + ",";
         }

         s = s + "\r\n" + "  " + ((SqlParameterDeclaration)this.getParameters().get(i)).format();
      }

      s = s + "\r\n" + ")";
      return s;
   }

   public String format() {
      String sStatement = K.CREATE.getKeyword() + " " + K.PROCEDURE.getKeyword() + " " + this.getProcedureName().format() + this.formatParameters();
      if (this.getRoutineCharacteristics() != null) {
         sStatement = sStatement + this.getRoutineCharacteristics().format();
      }

      if (this.getRoutineBody() != null) {
         sStatement = sStatement + "\r\n" + this.getRoutineBody().format();
      }

      return sStatement;
   }

   public void parse(SqlParser.CreateProcedureStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().createProcedureStatement());
   }

   public void initialize(QualifiedId qiProcedureName, List<SqlParameterDeclaration> listParameters, RoutineCharacteristics rc, RoutineBody rb) {
      _il.enter(new Object[0]);
      this.setProcedureName(qiProcedureName);
      this.setParameters(listParameters);
      this.setRoutineCharacteristics(rc);
      this.setRoutineBody(rb);
      _il.exit();
   }

   public CreateProcedureStatement(SqlFactory sf) {
      super(sf);
   }

   private class CpsVisitor extends EnhancedSqlBaseVisitor<CreateProcedureStatement> {
      private CpsVisitor() {
      }

      public CreateProcedureStatement visitRoutineName(SqlParser.RoutineNameContext ctx) {
         this.setRoutineName(ctx, CreateProcedureStatement.this.getProcedureName());
         return CreateProcedureStatement.this;
      }

      public CreateProcedureStatement visitSqlParameterDeclaration(SqlParser.SqlParameterDeclarationContext ctx) {
         SqlParameterDeclaration spd = CreateProcedureStatement.this.getSqlFactory().newSqlParameterDeclaration();
         spd.parse(ctx);
         CreateProcedureStatement.this.getParameters().add(spd);
         return CreateProcedureStatement.this;
      }

      public CreateProcedureStatement visitRoutineCharacteristics(SqlParser.RoutineCharacteristicsContext ctx) {
         CreateProcedureStatement.this.setRoutineCharacteristics(CreateProcedureStatement.this.getSqlFactory().newRoutineCharacteristics());
         CreateProcedureStatement.this.getRoutineCharacteristics().parse(ctx);
         return CreateProcedureStatement.this;
      }

      public CreateProcedureStatement visitRoutineBody(SqlParser.RoutineBodyContext ctx) {
         CreateProcedureStatement.this.setRoutineBody(CreateProcedureStatement.this.getSqlFactory().newRoutineBody());
         CreateProcedureStatement.this.getRoutineBody().parse(ctx);
         return CreateProcedureStatement.this;
      }

      // $FF: synthetic method
      CpsVisitor(Object x1) {
         this();
      }
   }
}
