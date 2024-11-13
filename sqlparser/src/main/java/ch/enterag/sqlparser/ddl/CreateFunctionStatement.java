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

public class CreateFunctionStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(CreateFunctionStatement.class.getName());
   private CreateFunctionStatement.CfsVisitor _visitor = new CreateFunctionStatement.CfsVisitor();
   private QualifiedId _qiFunctionName = new QualifiedId();
   private List<SqlParameterDeclaration> _listParameters = new ArrayList();
   private ReturnsClause _rc = null;
   private RoutineCharacteristics _rcs = null;
   private boolean _bStaticDispatch = false;
   private RoutineBody _rb = null;

   private CreateFunctionStatement.CfsVisitor getVisitor() {
      return this._visitor;
   }

   public QualifiedId getFunctionName() {
      return this._qiFunctionName;
   }

   private void setFunctionName(QualifiedId qiFunctionName) {
      this._qiFunctionName = qiFunctionName;
   }

   public List<SqlParameterDeclaration> getParameters() {
      return this._listParameters;
   }

   private void setParameters(List<SqlParameterDeclaration> listParameters) {
      this._listParameters = listParameters;
   }

   public ReturnsClause getReturnsClause() {
      return this._rc;
   }

   public void setReturnsClause(ReturnsClause rc) {
      this._rc = rc;
   }

   public RoutineCharacteristics getRoutineCharacteristics() {
      return this._rcs;
   }

   public void setRoutineCharacteristics(RoutineCharacteristics rcs) {
      this._rcs = rcs;
   }

   public boolean isStaticDispath() {
      return this._bStaticDispatch;
   }

   public void setStaticDispatch(boolean bStaticDispatch) {
      this._bStaticDispatch = bStaticDispatch;
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
      String sStatement = K.CREATE.getKeyword() + " " + K.FUNCTION.getKeyword() + " " + this.getFunctionName().format() + this.formatParameters() + "\r\n" + this.getReturnsClause().format();
      if (this.getRoutineCharacteristics() != null) {
         sStatement = sStatement + this.getRoutineCharacteristics().format();
      }

      if (this.getRoutineBody() != null) {
         sStatement = sStatement + "\r\n" + this.getRoutineBody().format();
      }

      return sStatement;
   }

   public void parse(SqlParser.CreateFunctionStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().createFunctionStatement());
   }

   public void initialize(QualifiedId qiFunctionName, List<SqlParameterDeclaration> listParameters, ReturnsClause rc, RoutineCharacteristics rcs, RoutineBody rb) {
      _il.enter(new Object[0]);
      this.setFunctionName(qiFunctionName);
      this.setParameters(listParameters);
      this.setReturnsClause(rc);
      this.setRoutineCharacteristics(rcs);
      this.setRoutineBody(rb);
      _il.exit();
   }

   public CreateFunctionStatement(SqlFactory sf) {
      super(sf);
   }

   private class CfsVisitor extends EnhancedSqlBaseVisitor<CreateFunctionStatement> {
      private CfsVisitor() {
      }

      public CreateFunctionStatement visitCreateFunctionStatement(SqlParser.CreateFunctionStatementContext ctx) {
         if (ctx.STATIC() != null && ctx.DISPATCH() != null) {
            CreateFunctionStatement.this.setStaticDispatch(true);
         }

         return (CreateFunctionStatement)this.visitChildren(ctx);
      }

      public CreateFunctionStatement visitRoutineName(SqlParser.RoutineNameContext ctx) {
         this.setRoutineName(ctx, CreateFunctionStatement.this.getFunctionName());
         return CreateFunctionStatement.this;
      }

      public CreateFunctionStatement visitSqlParameterDeclaration(SqlParser.SqlParameterDeclarationContext ctx) {
         SqlParameterDeclaration spd = CreateFunctionStatement.this.getSqlFactory().newSqlParameterDeclaration();
         spd.parse(ctx);
         CreateFunctionStatement.this.getParameters().add(spd);
         return CreateFunctionStatement.this;
      }

      public CreateFunctionStatement visitReturnsClause(SqlParser.ReturnsClauseContext ctx) {
         CreateFunctionStatement.this.setReturnsClause(CreateFunctionStatement.this.getSqlFactory().newReturnsClause());
         CreateFunctionStatement.this.getReturnsClause().parse(ctx);
         return CreateFunctionStatement.this;
      }

      public CreateFunctionStatement visitRoutineCharacteristics(SqlParser.RoutineCharacteristicsContext ctx) {
         CreateFunctionStatement.this.setRoutineCharacteristics(CreateFunctionStatement.this.getSqlFactory().newRoutineCharacteristics());
         CreateFunctionStatement.this.getRoutineCharacteristics().parse(ctx);
         return CreateFunctionStatement.this;
      }

      public CreateFunctionStatement visitRoutineBody(SqlParser.RoutineBodyContext ctx) {
         CreateFunctionStatement.this.setRoutineBody(CreateFunctionStatement.this.getSqlFactory().newRoutineBody());
         CreateFunctionStatement.this.getRoutineBody().parse(ctx);
         return CreateFunctionStatement.this;
      }

      // $FF: synthetic method
      CfsVisitor(Object x1) {
         this();
      }
   }
}
