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

public class DeleteStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(DeleteStatement.class.getName());
   private DeleteStatement.DsVisitor _visitor = new DeleteStatement.DsVisitor();
   private QualifiedId _qiTableName = new QualifiedId();
   private BooleanValueExpression _bve = null;
   private List<String> _listColumnNames = new ArrayList();
   private Map<String, DataType> _mapColumnTypes = new HashMap();
   private Map<String, Object> _mapColumnValues = new HashMap();

   private DeleteStatement.DsVisitor getVisitor() {
      return this._visitor;
   }

   public QualifiedId getTableName() {
      return this._qiTableName;
   }

   private void setTableName(QualifiedId qiTableName) {
      this._qiTableName = qiTableName;
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
      String sStatement = K.DELETE.getKeyword() + " " + K.FROM.getKeyword() + " " + this.getTableName().format();
      if (this.getBooleanValueExpression() != null) {
         sStatement = sStatement + " " + K.WHERE + " " + this.getBooleanValueExpression().format();
      }

      return sStatement;
   }

   public void parse(SqlParser.DeleteStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().deleteStatement());
   }

   public void initialize(QualifiedId qiTableName, BooleanValueExpression bve) {
      _il.enter(new Object[0]);
      this.setTableName(qiTableName);
      this.setBooleanValueExpression(bve);
      _il.exit();
   }

   public DeleteStatement(SqlFactory sf) {
      super(sf);
   }

   private class DsVisitor extends EnhancedSqlBaseVisitor<DeleteStatement> {
      private DsVisitor() {
      }

      public DeleteStatement visitTableName(SqlParser.TableNameContext ctx) {
         this.setTableName(ctx, DeleteStatement.this.getTableName());
         return DeleteStatement.this;
      }

      public DeleteStatement visitBooleanValueExpression(SqlParser.BooleanValueExpressionContext ctx) {
         DeleteStatement.this.setBooleanValueExpression(DeleteStatement.this.getSqlFactory().newBooleanValueExpression());
         DeleteStatement.this.getBooleanValueExpression().parse(ctx);
         return DeleteStatement.this;
      }

      // $FF: synthetic method
      DsVisitor(Object x1) {
         this();
      }
   }
}
