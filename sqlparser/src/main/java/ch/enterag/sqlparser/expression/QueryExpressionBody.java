package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.expression.enums.QueryOperator;
import ch.enterag.sqlparser.expression.enums.SetQuantifier;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class QueryExpressionBody extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(QueryExpressionBody.class.getName());
   private QueryExpressionBody.QebVisitor _visitor = new QueryExpressionBody.QebVisitor();
   private QueryOperator _qo = null;
   private QueryExpressionBody _qeb = null;
   private QueryExpressionBody _qeb2 = null;
   private SetQuantifier _sq = null;
   private boolean _bCorresponding = false;
   private List<Identifier> _listCorrespondingColumnNames = new ArrayList();
   private TableReference _tr = null;
   private QuerySpecification _qs = null;
   private QualifiedId _qiTableName = new QualifiedId();
   private List<TableRowValueExpression> _listTrve = new ArrayList();

   private QueryExpressionBody.QebVisitor getVisitor() {
      return this._visitor;
   }

   public QueryOperator getQueryOperator() {
      return this._qo;
   }

   public void setQueryOperator(QueryOperator qo) {
      this._qo = qo;
   }

   public QueryExpressionBody getQueryExpressionBody() {
      return this._qeb;
   }

   public void setQueryExpressionBody(QueryExpressionBody qeb) {
      this._qeb = qeb;
   }

   public QueryExpressionBody getSecondQueryExpressionBody() {
      return this._qeb2;
   }

   public void setSecondQueryExpressionBody(QueryExpressionBody qeb2) {
      this._qeb2 = qeb2;
   }

   public SetQuantifier getSetQuantifier() {
      return this._sq;
   }

   public void setSetQuantifier(SetQuantifier sq) {
      this._sq = sq;
   }

   public boolean isCorresponding() {
      return this._bCorresponding;
   }

   public void setCorresponding(boolean bCorresponding) {
      this._bCorresponding = bCorresponding;
   }

   public List<Identifier> getCorrespondingColumnNames() {
      return this._listCorrespondingColumnNames;
   }

   private void setCorrespondingColumnNames(List<Identifier> listCorrespondingColumnNames) {
      this._listCorrespondingColumnNames = listCorrespondingColumnNames;
   }

   public TableReference getTableReference() {
      return this._tr;
   }

   public void setTableReference(TableReference tr) {
      this._tr = tr;
   }

   public QuerySpecification getQuerySpecification() {
      return this._qs;
   }

   public void setQuerySpecification(QuerySpecification qs) {
      this._qs = qs;
   }

   public QualifiedId getTableName() {
      return this._qiTableName;
   }

   private void setTableName(QualifiedId qiTableName) {
      this._qiTableName = qiTableName;
   }

   public List<TableRowValueExpression> getTableRowValueExpressions() {
      return this._listTrve;
   }

   private void setTableRowValueExpressions(List<TableRowValueExpression> listTrve) {
      this._listTrve = listTrve;
   }

   public String formatValues() {
      String s = K.VALUES.getKeyword() + "(";

      for(int i = 0; i < this.getTableRowValueExpressions().size(); ++i) {
         if (i > 0) {
            s = s + "," + " ";
         }

         s = s + ((TableRowValueExpression)this.getTableRowValueExpressions().get(i)).format();
      }

      s = s + ")";
      return s;
   }

   public String format() {
      String s = null;
      if (this.getQueryOperator() != null) {
         s = this.getQueryExpressionBody().format() + " " + this.getQueryOperator().getKeywords();
         if (this.getSetQuantifier() != null) {
            s = s + " " + this.getSetQuantifier().getKeywords();
         }

         if (this.isCorresponding()) {
            s = s + " " + K.CORRESPONDING.getKeyword();
            if (this.getCorrespondingColumnNames().size() > 0) {
               s = s + " " + K.BY.getKeyword() + "(";

               for(int i = 0; i < this.getCorrespondingColumnNames().size(); ++i) {
                  if (i > 0) {
                     s = s + "," + " ";
                  }

                  s = s + ((Identifier)this.getCorrespondingColumnNames().get(i)).format();
               }

               s = s + ")";
            }
         }

         s = s + " " + this.getSecondQueryExpressionBody().format();
      } else if (this.getTableReference() != null) {
         s = this.getTableReference().format();
      } else if (this.getQueryExpressionBody() != null) {
         s = s + "(" + this.getQueryExpressionBody().format() + ")";
      } else if (this.getQuerySpecification() != null) {
         s = this.getQuerySpecification().format();
      } else if (this.getTableName().isSet()) {
         s = K.TABLE.getKeyword() + " " + this.getTableName().format();
      } else if (this.getTableRowValueExpressions().size() > 0) {
         s = this.formatValues();
      }

      return s;
   }

   public void parse(SqlParser.QueryExpressionBodyContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().queryExpressionBody());
   }

   public void initialize(QueryOperator qo, QueryExpressionBody qeb, QueryExpressionBody qeb2, SetQuantifier sq, boolean bCorresponding, List<Identifier> listCorrespondingColumnNames, TableReference tr, QuerySpecification qs, QualifiedId qiTableName, List<TableRowValueExpression> listTrve) {
      _il.enter(new Object[0]);
      this.setQueryOperator(qo);
      this.setQueryExpressionBody(qeb);
      this.setSecondQueryExpressionBody(qeb2);
      this.setSetQuantifier(sq);
      this.setCorresponding(bCorresponding);
      this.setCorrespondingColumnNames(listCorrespondingColumnNames);
      this.setTableReference(tr);
      this.setQuerySpecification(qs);
      this.setTableName(qiTableName);
      this.setTableRowValueExpressions(listTrve);
      _il.exit();
   }

   public QueryExpressionBody(SqlFactory sf) {
      super(sf);
   }

   private class QebVisitor extends EnhancedSqlBaseVisitor<QueryExpressionBody> {
      private QebVisitor() {
      }

      public QueryExpressionBody visitQueryExpressionBody(SqlParser.QueryExpressionBodyContext ctx) {
         int i;
         if (ctx.queryOperator() != null && ctx.queryExpressionBody().size() == 2) {
            QueryExpressionBody.this.setQueryOperator(this.getQueryOperator(ctx.queryOperator()));
            QueryExpressionBody.this.setQueryExpressionBody(QueryExpressionBody.this.getSqlFactory().newQueryExpressionBody());
            QueryExpressionBody.this.getQueryExpressionBody().parse(ctx.queryExpressionBody(0));
            QueryExpressionBody.this.setSecondQueryExpressionBody(QueryExpressionBody.this.getSqlFactory().newQueryExpressionBody());
            QueryExpressionBody.this.getSecondQueryExpressionBody().parse(ctx.queryExpressionBody(1));
            if (ctx.setQuantifier() != null) {
               QueryExpressionBody.this.setSetQuantifier(this.getSetQuantifier(ctx.setQuantifier()));
            }

            if (ctx.correspondingSpecification() != null) {
               QueryExpressionBody.this.setCorresponding(true);

               for(i = 0; i < ctx.correspondingSpecification().columnName().size(); ++i) {
                  Identifier idColumnName = new Identifier();
                  this.setColumnName(ctx.correspondingSpecification().columnName(i), idColumnName);
                  QueryExpressionBody.this.getCorrespondingColumnNames().add(idColumnName);
               }
            }
         } else if (ctx.tableReference() != null) {
            QueryExpressionBody.this.setTableReference(QueryExpressionBody.this.getSqlFactory().newTableReference());
            QueryExpressionBody.this.getTableReference().parse(ctx.tableReference());
         } else if (ctx.querySpecification() != null) {
            QueryExpressionBody.this.setQuerySpecification(QueryExpressionBody.this.getSqlFactory().newQuerySpecification());
            QueryExpressionBody.this.getQuerySpecification().parse(ctx.querySpecification());
         } else if (ctx.queryExpressionBody().size() == 1) {
            QueryExpressionBody.this.setQueryExpressionBody(QueryExpressionBody.this.getSqlFactory().newQueryExpressionBody());
            QueryExpressionBody.this.getQueryExpressionBody().parse(ctx.queryExpressionBody(0));
         } else if (ctx.TABLE() != null && ctx.tableName() != null) {
            this.setTableName(ctx.tableName(), QueryExpressionBody.this.getTableName());
         } else if (ctx.VALUES() != null) {
            for(i = 0; i < ctx.tableRowValueExpression().size(); ++i) {
               TableRowValueExpression trve = QueryExpressionBody.this.getSqlFactory().newTableRowValueExpression();
               trve.parse(ctx.tableRowValueExpression(i));
               QueryExpressionBody.this.getTableRowValueExpressions().add(trve);
            }
         }

         return QueryExpressionBody.this;
      }

      // $FF: synthetic method
      QebVisitor(Object x1) {
         this();
      }
   }
}
