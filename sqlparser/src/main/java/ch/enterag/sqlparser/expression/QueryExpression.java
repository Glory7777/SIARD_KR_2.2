package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class QueryExpression extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(QueryExpression.class.getName());
   private QueryExpression.QeVisitor _visitor = new QueryExpression.QeVisitor();
   private boolean _bRecursive = false;
   private List<WithElement> _listWithElements = new ArrayList();
   private QueryExpressionBody _qeb = null;

   private QueryExpression.QeVisitor getVisitor() {
      return this._visitor;
   }

   public boolean isRecursive() {
      return this._bRecursive;
   }

   public void setRecursive(boolean bRecursive) {
      this._bRecursive = bRecursive;
   }

   public List<WithElement> getWithElements() {
      return this._listWithElements;
   }

   private void setWithElements(List<WithElement> listWithElements) {
      this._listWithElements = listWithElements;
   }

   public QueryExpressionBody getQueryExpressionBody() {
      return this._qeb;
   }

   public void setQueryExpressionBody(QueryExpressionBody qeb) {
      this._qeb = qeb;
   }

   public String format() {
      String sExpression = "";
      if (this.getWithElements().size() > 0) {
         sExpression = K.WITH.getKeyword() + " ";
         if (this.isRecursive()) {
            sExpression = sExpression + K.RECURSIVE.getKeyword() + " ";
         }

         for(int i = 0; i < this.getWithElements().size(); ++i) {
            if (i > 0) {
               sExpression = sExpression + "," + " ";
            }

            sExpression = sExpression + ((WithElement)this.getWithElements().get(i)).format();
         }

         sExpression = sExpression + " ";
      }

      sExpression = sExpression + this.getQueryExpressionBody().format();
      return sExpression;
   }

   public void parse(SqlParser.QueryExpressionContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().queryExpression());
   }

   public void initialize(boolean bRecursive, List<WithElement> listWithElements, QueryExpressionBody qeb) {
      _il.enter(new Object[]{String.valueOf(bRecursive), listWithElements, qeb});
      this.setRecursive(bRecursive);
      this.setWithElements(listWithElements);
      this.setQueryExpressionBody(qeb);
      _il.exit();
   }

   public QueryExpression(SqlFactory sf) {
      super(sf);
   }

   private class QeVisitor extends EnhancedSqlBaseVisitor<QueryExpression> {
      private QeVisitor() {
      }

      public QueryExpression visitWithClause(SqlParser.WithClauseContext ctx) {
         if (ctx.RECURSIVE() != null) {
            QueryExpression.this.setRecursive(true);
         }

         for(int i = 0; i < ctx.withElement().size(); ++i) {
            WithElement we = QueryExpression.this.getSqlFactory().newWithElement();
            we.parse(ctx.withElement(i));
            QueryExpression.this.getWithElements().add(we);
         }

         return QueryExpression.this;
      }

      public QueryExpression visitQueryExpressionBody(SqlParser.QueryExpressionBodyContext ctx) {
         QueryExpression.this.setQueryExpressionBody(QueryExpression.this.getSqlFactory().newQueryExpressionBody());
         QueryExpression.this.getQueryExpressionBody().parse(ctx);
         return QueryExpression.this;
      }

      // $FF: synthetic method
      QeVisitor(Object x1) {
         this();
      }
   }
}
