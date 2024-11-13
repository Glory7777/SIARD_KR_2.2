package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TablePrimary extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(TablePrimary.class.getName());
   private TablePrimary.TpVisitor _visitor = new TablePrimary.TpVisitor();
   private QualifiedId _qiTableName = new QualifiedId();
   private Identifier _idCorrelationName = new Identifier();
   private List<Identifier> _listAliasColumnNames = new ArrayList();
   private TableReference _tr = null;
   private boolean _bLateral = false;
   private QueryExpression _qe = null;
   private boolean _bUnnest = false;
   private boolean _bWithOrdinality = false;
   private ArrayValueExpression _ave = null;
   private MultisetValueExpression _mve = null;
   private boolean _bTable = false;
   private boolean _bOnly = false;
   private List<String> _listColumnNames = new ArrayList();
   private Map<String, DataType> _mapColumnTypes = new HashMap();
   private Map<String, Object> _mapColumnValues = new HashMap();

   private TablePrimary.TpVisitor getVisitor() {
      return this._visitor;
   }

   public QualifiedId getTableName() {
      return this._qiTableName;
   }

   private void setTableName(QualifiedId qiTableName) {
      this._qiTableName = qiTableName;
   }

   public Identifier getCorrelationName() {
      return this._idCorrelationName;
   }

   private void setCorrelationName(Identifier idCorrelationName) {
      this._idCorrelationName = idCorrelationName;
   }

   public List<Identifier> getAliasColumnNames() {
      return this._listAliasColumnNames;
   }

   private void setAliasColumnNames(List<Identifier> listAliasColumnNames) {
      this._listAliasColumnNames = listAliasColumnNames;
   }

   public TableReference getTableReference() {
      return this._tr;
   }

   public void setTableReference(TableReference tr) {
      this._tr = tr;
   }

   public boolean isLateral() {
      return this._bLateral;
   }

   public void setLateral(boolean bLateral) {
      this._bLateral = bLateral;
   }

   public QueryExpression getQueryExpression() {
      return this._qe;
   }

   public void setQueryExpression(QueryExpression qe) {
      this._qe = qe;
   }

   public boolean isUnnest() {
      return this._bUnnest;
   }

   public void setUnnest(boolean bUnnest) {
      this._bUnnest = bUnnest;
   }

   public boolean isWithOrdinality() {
      return this._bWithOrdinality;
   }

   public void setWithOrdinality(boolean bWithOrdinality) {
      this._bWithOrdinality = bWithOrdinality;
   }

   public ArrayValueExpression getArrayValueExpression() {
      return this._ave;
   }

   public void setArrayValueExpression(ArrayValueExpression ave) {
      this._ave = ave;
   }

   public MultisetValueExpression getMultisetValueExpression() {
      return this._mve;
   }

   public void setMultisetValueExpression(MultisetValueExpression mve) {
      this._mve = mve;
   }

   public boolean isTable() {
      return this._bTable;
   }

   public void setTable(boolean bTable) {
      this._bTable = bTable;
   }

   public boolean isOnly() {
      return this._bOnly;
   }

   public void setOnly(boolean bOnly) {
      this._bOnly = bOnly;
   }

   public boolean hasColumn(String sColumn) {
      return this.getColumnTypes().keySet().contains(sColumn);
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

   protected Map<String, DataType> getColumnTypes() {
      return this._mapColumnTypes;
   }

   protected Map<String, Object> getColumnValues() {
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
      String s = "";
      if (this.isOnly() && this.getTableName() != null) {
         s = K.ONLY.getKeyword() + "(" + this.getTableName().format() + ")";
      } else if (this.isTable()) {
         s = K.TABLE.getKeyword() + "(";
         if (this.getArrayValueExpression() != null) {
            s = s + this.getArrayValueExpression().format();
         } else if (this.getMultisetValueExpression() != null) {
            s = s + this.getMultisetValueExpression().format();
         }

         s = s + ")";
      } else if (this.isUnnest()) {
         s = K.UNNEST.getKeyword() + "(";
         if (this.getArrayValueExpression() != null) {
            s = s + this.getArrayValueExpression().format();
         } else if (this.getMultisetValueExpression() != null) {
            s = s + this.getMultisetValueExpression().format();
         }

         s = s + ")";
         if (this.isWithOrdinality()) {
            s = s + " " + K.WITH.getKeyword() + " " + K.ORDINALITY.getKeyword();
         }
      } else if (this.getQueryExpression() != null) {
         if (this.isLateral()) {
            s = K.LATERAL.getKeyword();
         }

         s = s + "(" + this.getQueryExpression().format() + ")";
      } else if (this.getTableReference() != null) {
         s = s + this.getTableReference().format();
      } else {
         s = s + this.getTableName().format();
      }

      if (this.getCorrelationName().isSet()) {
         s = s + " " + K.AS.getKeyword() + " " + this.getCorrelationName().format();
         if (this.getAliasColumnNames().size() > 0) {
            s = s + "(";

            for(int i = 0; i < this.getAliasColumnNames().size(); ++i) {
               if (i > 0) {
                  s = s + "," + " ";
               }

               s = s + ((Identifier)this.getAliasColumnNames().get(i)).format();
            }

            s = s + ")";
         }
      }

      return s;
   }

   public void parse(SqlParser.TablePrimaryContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().tablePrimary());
   }

   public void initialize(QualifiedId qiTableName, Identifier idCorrelationName, List<Identifier> listAliasColumnNames, TableReference tr, boolean bLateral, QueryExpression qe, boolean bUnnest, boolean bWithOrdinality, ArrayValueExpression ave, MultisetValueExpression mve, boolean bTable, boolean bOnly) {
      _il.enter(new Object[0]);
      this.setTableName(qiTableName);
      this.setCorrelationName(idCorrelationName);
      this.setAliasColumnNames(listAliasColumnNames);
      this.setTableReference(tr);
      this.setLateral(bLateral);
      this.setQueryExpression(qe);
      this.setUnnest(bUnnest);
      this.setWithOrdinality(bWithOrdinality);
      this.setArrayValueExpression(ave);
      this.setMultisetValueExpression(mve);
      this.setTable(bTable);
      this.setOnly(bOnly);
      _il.exit();
   }

   public void initialize(QualifiedId qiTableName, Identifier idCorrelationName) {
      _il.enter(new Object[0]);
      this.setTableName(qiTableName);
      this.setCorrelationName(idCorrelationName);
      _il.exit();
   }

   public void initialize(QualifiedId qiTableName) {
      _il.enter(new Object[0]);
      this.setTableName(qiTableName);
      _il.exit();
   }

   public TablePrimary(SqlFactory sf) {
      super(sf);
   }

   private class TpVisitor extends EnhancedSqlBaseVisitor<TablePrimary> {
      private TpVisitor() {
      }

      public TablePrimary visitTablePrimary(SqlParser.TablePrimaryContext ctx) {
         if (ctx.UNNEST() != null) {
            TablePrimary.this.setUnnest(true);
            if (ctx.ORDINALITY() != null) {
               TablePrimary.this.setWithOrdinality(true);
            }
         } else if (ctx.TABLE() != null) {
            TablePrimary.this.setTable(true);
         } else if (ctx.ONLY() != null) {
            TablePrimary.this.setOnly(true);
         } else if (ctx.LATERAL() != null) {
            TablePrimary.this.setLateral(true);
         }

         return (TablePrimary)this.visitChildren(ctx);
      }

      public TablePrimary visitArrayValueExpression(SqlParser.ArrayValueExpressionContext ctx) {
         TablePrimary.this.setArrayValueExpression(TablePrimary.this.getSqlFactory().newArrayValueExpression());
         TablePrimary.this.getArrayValueExpression().parse(ctx);
         return TablePrimary.this;
      }

      public TablePrimary visitMultisetValueExpression(SqlParser.MultisetValueExpressionContext ctx) {
         TablePrimary.this.setMultisetValueExpression(TablePrimary.this.getSqlFactory().newMultisetValueExpression());
         TablePrimary.this.getMultisetValueExpression().parse(ctx);
         return TablePrimary.this;
      }

      public TablePrimary visitTableName(SqlParser.TableNameContext ctx) {
         this.setTableName(ctx, TablePrimary.this.getTableName());
         return TablePrimary.this;
      }

      public TablePrimary visitTableAlias(SqlParser.TableAliasContext ctx) {
         this.setIdentifier(ctx.correlationName().IDENTIFIER(), TablePrimary.this.getCorrelationName());

         for(int i = 0; i < ctx.columnName().size(); ++i) {
            Identifier idColumnName = new Identifier();
            this.setColumnName(ctx.columnName(i), idColumnName);
            TablePrimary.this.getAliasColumnNames().add(idColumnName);
         }

         return TablePrimary.this;
      }

      public TablePrimary visitTableReference(SqlParser.TableReferenceContext ctx) {
         TablePrimary.this.setTableReference(TablePrimary.this.getSqlFactory().newTableReference());
         TablePrimary.this.getTableReference().parse(ctx);
         return TablePrimary.this;
      }

      public TablePrimary visitQueryExpression(SqlParser.QueryExpressionContext ctx) {
         TablePrimary.this.setQueryExpression(TablePrimary.this.getSqlFactory().newQueryExpression());
         TablePrimary.this.getQueryExpression().parse(ctx);
         return TablePrimary.this;
      }

      // $FF: synthetic method
      TpVisitor(Object x1) {
         this();
      }
   }
}
