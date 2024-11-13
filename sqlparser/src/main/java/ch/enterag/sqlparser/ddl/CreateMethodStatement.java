package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.ddl.enums.MethodType;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class CreateMethodStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(CreateMethodStatement.class.getName());
   private CreateMethodStatement.CmsVisitor _visitor = new CreateMethodStatement.CmsVisitor();
   private MethodType _mt = null;
   private QualifiedId _qiMethodName = new QualifiedId();
   private List<SqlParameterDeclaration> _listParameters = new ArrayList();
   private ReturnsClause _rc = null;
   private QualifiedId _qiForTypeName = new QualifiedId();
   private RoutineCharacteristics _rcs = null;
   private RoutineBody _rb = null;

   private CreateMethodStatement.CmsVisitor getVisitor() {
      return this._visitor;
   }

   public MethodType getMethodType() {
      return this._mt;
   }

   public void setMethodType(MethodType mt) {
      this._mt = mt;
   }

   public QualifiedId getMethodName() {
      return this._qiMethodName;
   }

   private void setMethodName(QualifiedId qiMethodName) {
      this._qiMethodName = qiMethodName;
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

   public QualifiedId getForTypeName() {
      return this._qiForTypeName;
   }

   private void setForTypeName(QualifiedId qiForTypeName) {
      this._qiForTypeName = qiForTypeName;
   }

   public RoutineCharacteristics getRoutineCharacteristics() {
      return this._rcs;
   }

   public void setRoutineCharacteristics(RoutineCharacteristics rcs) {
      this._rcs = rcs;
   }

   public RoutineBody getRoutineBody() {
      return this._rb;
   }

   public void setRoutineBody(RoutineBody rb) {
      this._rb = rb;
   }

   private String formatParameters() {
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
      String sStatement = K.CREATE.getKeyword();
      if (this.getMethodType() != null) {
         sStatement = sStatement + " " + this.getMethodType().getKeywords();
      }

      sStatement = sStatement + " " + K.METHOD.getKeyword() + " " + this.getMethodName().format() + this.formatParameters();
      if (this.getReturnsClause() != null) {
         sStatement = sStatement + "\r\n" + this.getReturnsClause().format();
      }

      sStatement = sStatement + " " + K.FOR.getKeyword() + " " + this.getForTypeName().format();
      if (this.getRoutineCharacteristics() != null) {
         sStatement = sStatement + " " + this.getRoutineCharacteristics().format();
      }

      if (this.getRoutineBody() != null) {
         sStatement = sStatement + "\r\n" + this.getRoutineBody().format();
      }

      return sStatement;
   }

   public void parse(SqlParser.CreateMethodStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().createMethodStatement());
   }

   public void initialize(MethodType mt, QualifiedId qiMethodName, List<SqlParameterDeclaration> listParameters, ReturnsClause rc, QualifiedId qiForTypeName, RoutineCharacteristics rcs, RoutineBody rb) {
      _il.enter(new Object[0]);
      this.setMethodType(mt);
      this.setMethodName(qiMethodName);
      this.setParameters(listParameters);
      this.setReturnsClause(rc);
      this.setForTypeName(qiForTypeName);
      this.setRoutineCharacteristics(rcs);
      this.setRoutineBody(rb);
      _il.exit();
   }

   public CreateMethodStatement(SqlFactory sf) {
      super(sf);
   }

   private class CmsVisitor extends EnhancedSqlBaseVisitor<CreateMethodStatement> {
      private CmsVisitor() {
      }

      public CreateMethodStatement visitMethodType(SqlParser.MethodTypeContext ctx) {
         CreateMethodStatement.this.setMethodType(this.getMethodType(ctx));
         return CreateMethodStatement.this;
      }

      public CreateMethodStatement visitRoutineName(SqlParser.RoutineNameContext ctx) {
         this.setRoutineName(ctx, CreateMethodStatement.this.getMethodName());
         return CreateMethodStatement.this;
      }

      public CreateMethodStatement visitSqlParameterDeclaration(SqlParser.SqlParameterDeclarationContext ctx) {
         SqlParameterDeclaration spd = CreateMethodStatement.this.getSqlFactory().newSqlParameterDeclaration();
         spd.parse(ctx);
         CreateMethodStatement.this.getParameters().add(spd);
         return CreateMethodStatement.this;
      }

      public CreateMethodStatement visitUdtName(SqlParser.UdtNameContext ctx) {
         this.setUdtName(ctx, CreateMethodStatement.this.getForTypeName());
         return CreateMethodStatement.this;
      }

      public CreateMethodStatement visitReturnsClause(SqlParser.ReturnsClauseContext ctx) {
         CreateMethodStatement.this.setReturnsClause(CreateMethodStatement.this.getSqlFactory().newReturnsClause());
         CreateMethodStatement.this.getReturnsClause().parse(ctx);
         return CreateMethodStatement.this;
      }

      public CreateMethodStatement visitRoutineCharacteristics(SqlParser.RoutineCharacteristicsContext ctx) {
         CreateMethodStatement.this.setRoutineCharacteristics(CreateMethodStatement.this.getSqlFactory().newRoutineCharacteristics());
         CreateMethodStatement.this.getRoutineCharacteristics().parse(ctx);
         return CreateMethodStatement.this;
      }

      public CreateMethodStatement visitRoutineBody(SqlParser.RoutineBodyContext ctx) {
         CreateMethodStatement.this.setRoutineBody(CreateMethodStatement.this.getSqlFactory().newRoutineBody());
         CreateMethodStatement.this.getRoutineBody().parse(ctx);
         return CreateMethodStatement.this;
      }

      // $FF: synthetic method
      CmsVisitor(Object x1) {
         this();
      }
   }
}
