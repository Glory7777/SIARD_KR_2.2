package ch.enterag.sqlparser.dml;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.dml.enums.OverrideClause;
import ch.enterag.sqlparser.expression.QueryExpression;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.IdList;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class InsertStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(InsertStatement.class.getName());
   private InsertStatement.IsVisitor _visitor = new InsertStatement.IsVisitor();
   private QualifiedId _qiTableName = new QualifiedId();
   private IdList _ilColumnNames = new IdList();
   private List<AssignedRow> _listValues = new ArrayList();
   private QueryExpression _qe = null;
   private OverrideClause _oc = null;
   private boolean _bFromDefault = false;

   private InsertStatement.IsVisitor getVisitor() {
      return this._visitor;
   }

   public QualifiedId getTableName() {
      return this._qiTableName;
   }

   private void setTableName(QualifiedId qiTableName) {
      this._qiTableName = qiTableName;
   }

   public IdList getColumnNames() {
      return this._ilColumnNames;
   }

   private void setColumnNames(IdList ilColumnNames) {
      this._ilColumnNames = ilColumnNames;
   }

   public List<AssignedRow> getValues() {
      return this._listValues;
   }

   private void setValues(List<AssignedRow> listValues) {
      this._listValues = listValues;
   }

   public QueryExpression getQueryExpression() {
      return this._qe;
   }

   public void setQueryExpression(QueryExpression qe) {
      this._qe = qe;
   }

   public OverrideClause getOverrideClause() {
      return this._oc;
   }

   public void setOverrideClause(OverrideClause oc) {
      this._oc = oc;
   }

   public boolean isFromDefault() {
      return this._bFromDefault;
   }

   public void setFromDefault(boolean bFromDefault) {
      this._bFromDefault = bFromDefault;
   }

   public String formatValues() {
      String sValues = K.VALUES.getKeyword();

      for(int i = 0; i < this.getValues().size(); ++i) {
         if (i > 0) {
            sValues = sValues + "," + " ";
         }

         sValues = sValues + "\r\n" + "  " + ((AssignedRow)this.getValues().get(i)).format();
      }

      return sValues;
   }

   public String format() {
      String sStatement = K.INSERT.getKeyword() + " " + K.INTO.getKeyword() + " " + this.getTableName().format();
      if (!this.isFromDefault()) {
         sStatement = sStatement + "(" + this.getColumnNames().format() + ")";
         if (this.getOverrideClause() != null) {
            sStatement = sStatement + " " + this.getOverrideClause().getKeywords();
         }

         if (this.getValues().size() > 0) {
            sStatement = sStatement + " " + this.formatValues();
         } else if (this.getQueryExpression() != null) {
            sStatement = sStatement + " " + this.getQueryExpression().format();
         }
      } else {
         sStatement = sStatement + " " + K.DEFAULT.getKeyword() + " " + K.VALUES.getKeyword();
      }

      return sStatement;
   }

   public void parse(SqlParser.InsertStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().insertStatement());
   }

   public void initialize(QualifiedId qiTableName, IdList ilColumnNames, List<AssignedRow> listValues, QueryExpression qe, OverrideClause oc, boolean bFromDefault) {
      _il.enter(new Object[0]);
      this.setTableName(qiTableName);
      this.setColumnNames(ilColumnNames);
      this.setValues(listValues);
      this.setQueryExpression(qe);
      this.setOverrideClause(oc);
      this.setFromDefault(bFromDefault);
      _il.exit();
   }

   public InsertStatement(SqlFactory sf) {
      super(sf);
   }

   private class IsVisitor extends EnhancedSqlBaseVisitor<InsertStatement> {
      private IsVisitor() {
      }

      public InsertStatement visitAssignedRow(SqlParser.AssignedRowContext ctx) {
         AssignedRow ar = InsertStatement.this.getSqlFactory().newAssignedRow();
         ar.parse(ctx);
         InsertStatement.this.getValues().add(ar);
         return (InsertStatement)this.visitChildren(ctx);
      }

      public InsertStatement visitFromDefault(SqlParser.FromDefaultContext ctx) {
         InsertStatement.this.setFromDefault(true);
         return InsertStatement.this;
      }

      public InsertStatement visitTableName(SqlParser.TableNameContext ctx) {
         this.setTableName(ctx, InsertStatement.this.getTableName());
         return InsertStatement.this;
      }

      public InsertStatement visitColumnName(SqlParser.ColumnNameContext ctx) {
         this.addColumnName(ctx, InsertStatement.this.getColumnNames());
         return InsertStatement.this;
      }

      public InsertStatement visitOverrideClause(SqlParser.OverrideClauseContext ctx) {
         InsertStatement.this.setOverrideClause(this.getOverrideClause(ctx));
         return InsertStatement.this;
      }

      public InsertStatement visitQueryExpression(SqlParser.QueryExpressionContext ctx) {
         InsertStatement.this.setQueryExpression(InsertStatement.this.getSqlFactory().newQueryExpression());
         InsertStatement.this.getQueryExpression().parse(ctx);
         return InsertStatement.this;
      }

      // $FF: synthetic method
      IsVisitor(Object x1) {
         this();
      }
   }
}
