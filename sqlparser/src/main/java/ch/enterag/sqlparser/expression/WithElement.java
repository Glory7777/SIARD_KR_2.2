package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.expression.enums.CycleFunction;
import ch.enterag.sqlparser.expression.enums.SearchFunction;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class WithElement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(WithElement.class.getName());
   private WithElement.WeVisitor _visitor = new WithElement.WeVisitor();
   private Identifier _idQueryName = new Identifier();
   private List<Identifier> _listColumnNames = new ArrayList();
   private QueryExpression _qe = null;
   private SearchFunction _sf = null;
   private List<SortSpecification> _listSortSpecifications = new ArrayList();
   private Identifier _idSearchColumnName = new Identifier();
   private CycleFunction _cf = null;
   private List<Identifier> _listCycleColumnNames = new ArrayList();
   private ValueExpression _ve = null;
   private Identifier _idCycleColumnName = new Identifier();

   private WithElement.WeVisitor getVisitor() {
      return this._visitor;
   }

   public Identifier getQueryName() {
      return this._idQueryName;
   }

   private void setQueryName(Identifier idQueryName) {
      this._idQueryName = idQueryName;
   }

   public List<Identifier> getColumnNames() {
      return this._listColumnNames;
   }

   private void setColumnNames(List<Identifier> listColumnNames) {
      this._listColumnNames = listColumnNames;
   }

   public QueryExpression getQueryExpression() {
      return this._qe;
   }

   public void setQueryExpression(QueryExpression qe) {
      this._qe = qe;
   }

   public SearchFunction getSearchFunction() {
      return this._sf;
   }

   public void setSearchFunction(SearchFunction sf) {
      this._sf = sf;
   }

   public List<SortSpecification> getSortSpecifications() {
      return this._listSortSpecifications;
   }

   private void setSortSpecifications(List<SortSpecification> listSortSpecifications) {
      this._listSortSpecifications = listSortSpecifications;
   }

   public Identifier getSearchColumnName() {
      return this._idSearchColumnName;
   }

   public void setSearchColumnName(Identifier idSearchColumnName) {
      this._idSearchColumnName = idSearchColumnName;
   }

   public CycleFunction getCycleFunction() {
      return this._cf;
   }

   public void setCycleFunction(CycleFunction cf) {
      this._cf = cf;
   }

   public List<Identifier> getCycleColumnNames() {
      return this._listCycleColumnNames;
   }

   private void setCycleColumnNames(List<Identifier> listCycleColumnNames) {
      this._listCycleColumnNames = listCycleColumnNames;
   }

   public ValueExpression getValueExpression() {
      return this._ve;
   }

   public void setValueExpression(ValueExpression ve) {
      this._ve = ve;
   }

   public Identifier getCycleColumnName() {
      return this._idCycleColumnName;
   }

   public void setCycleColumnName(Identifier idCycleColumnName) {
      this._idCycleColumnName = idCycleColumnName;
   }

   public String format() {
      String s = this.getQueryName().format();
      int i;
      if (this.getColumnNames().size() > 0) {
         s = s + "(";

         for(i = 0; i < this.getColumnNames().size(); ++i) {
            if (i > 0) {
               s = s + "," + " ";
            }

            s = s + ((Identifier)this.getColumnNames().get(i)).format();
         }

         s = s + ")";
      }

      if (this.getQueryExpression() != null) {
         s = s + " " + K.AS.getKeyword() + "(" + this.getQueryExpression().format() + ")";
         if (this.getSearchFunction() != null) {
            s = s + " " + this.getSearchFunction().getKeywords();

            for(i = 0; i < this.getSortSpecifications().size(); ++i) {
               if (i > 0) {
                  s = s + "," + " ";
               }

               s = s + ((SortSpecification)this.getSortSpecifications().get(i)).format();
            }

            s = s + " " + K.SET.getKeyword() + " " + this.getSearchColumnName().format();
         }

         if (this.getCycleFunction() != null) {
            s = s + " " + this.getCycleFunction().getKeywords() + " ";
            switch(this.getCycleFunction()) {
            case CYCLE:
               for(i = 0; i < this.getCycleColumnNames().size(); ++i) {
                  if (i > 0) {
                     s = s + "," + " ";
                  }

                  s = s + ((Identifier)this.getCycleColumnNames().get(i)).format();
               }

               return s;
            case SET:
               s = s + this.getCycleColumnName().format() + " " + K.TO.getKeyword() + " " + this.getValueExpression().format();
               break;
            case DEFAULT:
               s = s + this.getValueExpression().format();
               break;
            case USING:
               s = s + this.getCycleColumnName().format();
            }
         }
      }

      return s;
   }

   public void parse(SqlParser.WithElementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().withElement());
   }

   public void initialize(Identifier idQueryName, List<Identifier> listColumnNames, QueryExpression qe, SearchFunction sf, List<SortSpecification> listSortSpecifications, Identifier idSearchColumnName, CycleFunction cf, List<Identifier> listCycleColumnNames, ValueExpression ve, Identifier idCycleColumnName) {
      _il.enter(new Object[0]);
      this.setQueryName(idQueryName);
      this.setColumnNames(listColumnNames);
      this.setQueryExpression(qe);
      this.setSearchFunction(sf);
      this.setSortSpecifications(listSortSpecifications);
      this.setSearchColumnName(idSearchColumnName);
      this.setCycleFunction(cf);
      this.setCycleColumnNames(listCycleColumnNames);
      this.setValueExpression(ve);
      this.setCycleColumnName(idCycleColumnName);
      _il.exit();
   }

   public WithElement(SqlFactory sf) {
      super(sf);
   }

   private class WeVisitor extends EnhancedSqlBaseVisitor<WithElement> {
      private WeVisitor() {
      }

      public WithElement visitQueryName(SqlParser.QueryNameContext ctx) {
         this.setIdentifier(ctx.IDENTIFIER(), WithElement.this.getQueryName());
         return WithElement.this;
      }

      public WithElement visitColumnName(SqlParser.ColumnNameContext ctx) {
         Identifier idColumnName = new Identifier();
         this.setColumnName(ctx, idColumnName);
         WithElement.this.getColumnNames().add(idColumnName);
         return WithElement.this;
      }

      public WithElement visitQueryExpression(SqlParser.QueryExpressionContext ctx) {
         WithElement.this.setQueryExpression(WithElement.this.getSqlFactory().newQueryExpression());
         WithElement.this.getQueryExpression().parse(ctx);
         return WithElement.this;
      }

      public WithElement visitSearchClause(SqlParser.SearchClauseContext ctx) {
         if (ctx.DEPTH() != null) {
            WithElement.this.setSearchFunction(SearchFunction.SEARCH_DEPTH_FIRST_BY);
         } else if (ctx.BREADTH() != null) {
            WithElement.this.setSearchFunction(SearchFunction.SEARCH_BREADTH_FIRST_BY);
         }

         for(int i = 0; i < ctx.sortSpecificationList().sortSpecification().size(); ++i) {
            SortSpecification ss = WithElement.this.getSqlFactory().newSortSpecification();
            ss.parse(ctx.sortSpecificationList().sortSpecification(i));
            WithElement.this.getSortSpecifications().add(ss);
         }

         this.setColumnName(ctx.columnName(), WithElement.this.getSearchColumnName());
         return WithElement.this;
      }

      public WithElement visitCycleClause(SqlParser.CycleClauseContext ctx) {
         if (ctx.CYCLE() != null) {
            WithElement.this.setCycleFunction(CycleFunction.CYCLE);

            for(int i = 0; i < ctx.columnName().size(); ++i) {
               Identifier idColumnName = new Identifier();
               this.setColumnName(ctx.columnName(i), idColumnName);
               WithElement.this.getCycleColumnNames().add(idColumnName);
            }
         } else if (ctx.SET() != null) {
            WithElement.this.setCycleFunction(CycleFunction.SET);
            this.setColumnName(ctx.columnName(0), WithElement.this.getCycleColumnName());
            WithElement.this.setValueExpression(WithElement.this.getSqlFactory().newValueExpression());
            WithElement.this.getValueExpression().parse(ctx.valueExpression());
         } else if (ctx.DEFAULT() != null) {
            WithElement.this.setCycleFunction(CycleFunction.DEFAULT);
            WithElement.this.setValueExpression(WithElement.this.getSqlFactory().newValueExpression());
            WithElement.this.getValueExpression().parse(ctx.valueExpression());
         } else if (ctx.USING() != null) {
            WithElement.this.setCycleFunction(CycleFunction.USING);
            this.setColumnName(ctx.columnName(0), WithElement.this.getCycleColumnName());
         }

         return WithElement.this;
      }

      // $FF: synthetic method
      WeVisitor(Object x1) {
         this();
      }
   }
}
