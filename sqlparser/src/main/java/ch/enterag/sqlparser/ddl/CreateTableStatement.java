package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.ddl.enums.CommitAction;
import ch.enterag.sqlparser.ddl.enums.TableScope;
import ch.enterag.sqlparser.ddl.enums.WithOrWithoutData;
import ch.enterag.sqlparser.expression.QueryExpression;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.IdList;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class CreateTableStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(CreateTableStatement.class.getName());
   private CreateTableStatement.CtsVisitor _visitor = new CreateTableStatement.CtsVisitor();
   private TableScope _ts = null;
   private QualifiedId _qTableName = new QualifiedId();
   private List<TableElement> _listTe = new ArrayList();
   private QualifiedId _qUdtName = new QualifiedId();
   private QualifiedId _qSuperTable = new QualifiedId();
   private CommitAction _ca = null;
   private IdList _ilColumnNames = new IdList();
   private QueryExpression _qe = null;
   private WithOrWithoutData _wowd = null;

   private CreateTableStatement.CtsVisitor getVisitor() {
      return this._visitor;
   }

   public TableScope getTableScope() {
      return this._ts;
   }

   public void setTableScope(TableScope ts) {
      this._ts = ts;
   }

   public QualifiedId getTableName() {
      return this._qTableName;
   }

   private void setTableName(QualifiedId qTableName) {
      this._qTableName = qTableName;
   }

   public List<TableElement> getTableElements() {
      return this._listTe;
   }

   private void setTableElements(List<TableElement> listTe) {
      this._listTe = listTe;
   }

   public QualifiedId getUdtName() {
      return this._qUdtName;
   }

   private void setUdtName(QualifiedId qUdtName) {
      this._qUdtName = qUdtName;
   }

   public QualifiedId getSuperTable() {
      return this._qSuperTable;
   }

   private void setSuperTable(QualifiedId qSuperTable) {
      this._qSuperTable = qSuperTable;
   }

   public CommitAction getCommitAction() {
      return this._ca;
   }

   public void setCommitAction(CommitAction ca) {
      this._ca = ca;
   }

   public IdList getColumnNames() {
      return this._ilColumnNames;
   }

   private void setColumnNames(IdList ilColumnNames) {
      this._ilColumnNames = ilColumnNames;
   }

   public QueryExpression getQueryExpression() {
      return this._qe;
   }

   public void setQueryExpression(QueryExpression qe) {
      this._qe = qe;
   }

   public WithOrWithoutData getWithOrWithoutData() {
      return this._wowd;
   }

   public void setWithOrWithoutData(WithOrWithoutData wowd) {
      this._wowd = wowd;
   }

   private String formatTableElements() {
      String sElements = "(";

      for(int iElement = 0; iElement < this.getTableElements().size(); ++iElement) {
         if (iElement > 0) {
            sElements = sElements + ",";
         }

         sElements = sElements + "\r\n" + "  " + ((TableElement)this.getTableElements().get(iElement)).format();
      }

      sElements = sElements + "\r\n" + ")";
      return sElements;
   }

   public String format() {
      String sStatement = K.CREATE.getKeyword();
      if (this.getTableScope() != null) {
         sStatement = sStatement + " " + this.getTableScope().getKeywords();
      }

      sStatement = sStatement + " " + K.TABLE.getKeyword() + " " + this.getTableName().format();
      if (this.getUdtName().isSet()) {
         sStatement = sStatement + " " + K.OF.getKeyword() + " " + this.getUdtName().format();
         if (this.getSuperTable().isSet()) {
            sStatement = sStatement + " " + K.UNDER.getKeyword() + " " + this.getSuperTable().format();
         }
      }

      if (this.getQueryExpression() == null) {
         sStatement = sStatement + this.formatTableElements();
      } else {
         if (this.getColumnNames().isSet()) {
            sStatement = sStatement + "(" + this.getColumnNames().format() + ")";
         }

         sStatement = sStatement + " " + K.AS.getKeyword() + "(" + this.getQueryExpression().format() + ")";
         if (this.getWithOrWithoutData() != null) {
            sStatement = sStatement + " " + this.getWithOrWithoutData().getKeywords();
         }
      }

      if (this.getCommitAction() != null) {
         sStatement = sStatement + " " + this.getCommitAction().getKeywords();
      }

      return sStatement;
   }

   public void parse(SqlParser.CreateTableStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().createTableStatement());
   }

   public void initialize(TableScope ts, QualifiedId qTableName, List<TableElement> listElements, QualifiedId qUdtName, QualifiedId qSuperTable, CommitAction ca, IdList ilColumnNames, QueryExpression qe, WithOrWithoutData wowd) {
      _il.enter(new Object[]{ts, qTableName, listElements, qUdtName, qSuperTable, ca, ilColumnNames, qe, wowd});
      this.setTableScope(ts);
      this.setTableName(qTableName);
      this.setTableElements(listElements);
      this.setUdtName(qUdtName);
      this.setSuperTable(qSuperTable);
      this.setCommitAction(ca);
      this.setColumnNames(ilColumnNames);
      this.setQueryExpression(qe);
      this.setWithOrWithoutData(wowd);
      _il.exit();
   }

   public void initTableElements(TableScope ts, QualifiedId qTableName, List<TableElement> listElements, CommitAction ca) {
      _il.enter(new Object[]{ts, qTableName, listElements, ca});
      this.setTableScope(ts);
      this.setTableName(qTableName);
      this.setTableElements(listElements);
      this.setCommitAction(ca);
      _il.exit();
   }

   public CreateTableStatement(SqlFactory sf) {
      super(sf);
   }

   private class CtsVisitor extends EnhancedSqlBaseVisitor<CreateTableStatement> {
      private CtsVisitor() {
      }

      public CreateTableStatement visitTableScope(SqlParser.TableScopeContext ctx) {
         CreateTableStatement.this.setTableScope(this.getTableScope(ctx));
         return CreateTableStatement.this;
      }

      public CreateTableStatement visitSubtableClause(SqlParser.SubtableClauseContext ctx) {
         this.setTableName(ctx.tableName(), CreateTableStatement.this.getSuperTable());
         return CreateTableStatement.this;
      }

      public CreateTableStatement visitTableName(SqlParser.TableNameContext ctx) {
         this.setTableName(ctx, CreateTableStatement.this.getTableName());
         return CreateTableStatement.this;
      }

      public CreateTableStatement visitUdtName(SqlParser.UdtNameContext ctx) {
         this.setUdtName(ctx, CreateTableStatement.this.getUdtName());
         return CreateTableStatement.this;
      }

      public CreateTableStatement visitTableElement(SqlParser.TableElementContext ctx) {
         TableElement te = CreateTableStatement.this.getSqlFactory().newTableElement();
         te.parse(ctx);
         CreateTableStatement.this.getTableElements().add(te);
         return CreateTableStatement.this;
      }

      public CreateTableStatement visitCommitAction(SqlParser.CommitActionContext ctx) {
         CreateTableStatement.this.setCommitAction(this.getCommitAction(ctx));
         return CreateTableStatement.this;
      }

      public CreateTableStatement visitColumnName(SqlParser.ColumnNameContext ctx) {
         this.addColumnName(ctx, CreateTableStatement.this.getColumnNames());
         return CreateTableStatement.this;
      }

      public CreateTableStatement visitQueryExpression(SqlParser.QueryExpressionContext ctx) {
         CreateTableStatement.this.setQueryExpression(CreateTableStatement.this.getSqlFactory().newQueryExpression());
         CreateTableStatement.this.getQueryExpression().parse(ctx);
         return CreateTableStatement.this;
      }

      public CreateTableStatement visitWithOrWithoutData(SqlParser.WithOrWithoutDataContext ctx) {
         CreateTableStatement.this.setWithOrWithoutData(this.getWithOrWithoutData(ctx));
         return CreateTableStatement.this;
      }

      // $FF: synthetic method
      CtsVisitor(Object x1) {
         this();
      }
   }
}
