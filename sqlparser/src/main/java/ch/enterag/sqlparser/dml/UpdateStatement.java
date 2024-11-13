package ch.enterag.sqlparser.dml;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.expression.BooleanValueExpression;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(UpdateStatement.class.getName());
   private UpdateStatement.UsVisitor _visitor = new UpdateStatement.UsVisitor();
   private QualifiedId _qiTableName = new QualifiedId();
   private List<SetClause> _listSetClauses = new ArrayList();
   private BooleanValueExpression _bve = null;
   private List<String> _listColumnNames = new ArrayList();
   private Map<String, DataType> _mapColumnTypes = new HashMap();
   private Map<String, Object> _mapColumnValues = new HashMap();

   private UpdateStatement.UsVisitor getVisitor() {
      return this._visitor;
   }

   public QualifiedId getTableName() {
      return this._qiTableName;
   }

   private void setTableName(QualifiedId qiTableName) {
      this._qiTableName = qiTableName;
   }

   public List<SetClause> getSetClauses() {
      return this._listSetClauses;
   }

   private void setSetClauses(List<SetClause> listSetClauses) {
      this._listSetClauses = listSetClauses;
   }

   public BooleanValueExpression getBooleanValueExpression() {
      return this._bve;
   }

   public void setBooleanValueExpression(BooleanValueExpression bve) {
      this._bve = bve;
   }

   public boolean hasColumn(String sColumn) {
      return this.getColumnValues().keySet().contains(sColumn);
   }

   public List<String> getColumnNames() {
      return this._listColumnNames;
   }

   public void setColumnNames(List<String> listColumnNames) {
      this._listColumnNames = listColumnNames;
      this.getColumnValues().clear();

      for(int iColumn = 0; iColumn < listColumnNames.size(); ++iColumn) {
         this.getColumnValues().put(listColumnNames.get(iColumn), (Object)null);
      }

   }

   private Map<String, DataType> getColumnTypes() {
      return this._mapColumnTypes;
   }

   private Map<String, Object> getColumnValues() {
      return this._mapColumnValues;
   }

   public void setColumnType(String sColumnName, DataType dtColumn) {
      this.getColumnTypes().put(sColumnName, dtColumn);
   }

   public void setColumnType(int iPosition, DataType dtColumn) {
      this.setColumnType((String)this.getColumnNames().get(iPosition - 1), dtColumn);
   }

   public DataType getColumnType(String sColumnName) {
      return (DataType)this.getColumnTypes().get(sColumnName);
   }

   public void setColumnValue(String sColumnName, Object oColumnValue) {
      this.getColumnValues().put(sColumnName, oColumnValue);
   }

   public void setColumnValue(int iPosition, Object oColumnValue) {
      this.setColumnValue((String)this.getColumnNames().get(iPosition - 1), oColumnValue);
   }

   public Object getColumnValue(String sColumnName) {
      return this.getColumnValues().get(sColumnName);
   }

   public String format() {
      String sStatement = K.UPDATE.getKeyword() + " " + this.getTableName().format() + " " + K.SET.getKeyword();

      for(int i = 0; i < this.getSetClauses().size(); ++i) {
         if (i > 0) {
            sStatement = sStatement + ",";
         }

         sStatement = sStatement + "\r\n" + "  " + ((SetClause)this.getSetClauses().get(i)).format();
      }

      if (this.getBooleanValueExpression() != null) {
         sStatement = sStatement + "\r\n" + K.WHERE + " " + this.getBooleanValueExpression().format();
      }

      return sStatement;
   }

   public void parse(SqlParser.UpdateStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().updateStatement());
   }

   public void initialize(QualifiedId qiTableName, List<SetClause> listSetClauses, BooleanValueExpression bve) {
      _il.enter(new Object[0]);
      this.setTableName(qiTableName);
      this.setSetClauses(listSetClauses);
      this.setBooleanValueExpression(bve);
      _il.exit();
   }

   public UpdateStatement(SqlFactory sf) {
      super(sf);
   }

   private class UsVisitor extends EnhancedSqlBaseVisitor<UpdateStatement> {
      private UsVisitor() {
      }

      public UpdateStatement visitTableName(SqlParser.TableNameContext ctx) {
         this.setTableName(ctx, UpdateStatement.this.getTableName());
         return UpdateStatement.this;
      }

      public UpdateStatement visitSetClause(SqlParser.SetClauseContext ctx) {
         SetClause sc = UpdateStatement.this.getSqlFactory().newSetClause();
         sc.parse(ctx);
         UpdateStatement.this.getSetClauses().add(sc);
         return UpdateStatement.this;
      }

      public UpdateStatement visitBooleanValueExpression(SqlParser.BooleanValueExpressionContext ctx) {
         UpdateStatement.this.setBooleanValueExpression(UpdateStatement.this.getSqlFactory().newBooleanValueExpression());
         UpdateStatement.this.getBooleanValueExpression().parse(ctx);
         return UpdateStatement.this;
      }

      // $FF: synthetic method
      UsVisitor(Object x1) {
         this();
      }
   }
}
