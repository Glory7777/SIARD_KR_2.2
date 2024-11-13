package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.SqlStatement;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.expression.enums.SetQuantifier;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.IdChain;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class QuerySpecification extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(QuerySpecification.class.getName());
   private QuerySpecification.QsVisitor _visitor = new QuerySpecification.QsVisitor();
   private SetQuantifier _sq = null;
   private boolean _bAsterisk = false;
   private List<SelectSublist> _listSelectSublists = new ArrayList();
   private List<TableReference> _listTableReferences = new ArrayList();
   private BooleanValueExpression _bveWhere = null;
   private SetQuantifier _sqGroupBy = null;
   private List<GroupingElement> _listGroupingElements = new ArrayList();
   private BooleanValueExpression _bveHaving = null;
   private List<Identifier> _listWindowNames = new ArrayList();
   private List<WindowSpecification> _listWindowSpecifications = new ArrayList();
   private boolean _bCount = false;
   private boolean _bAggregates = false;

   private QuerySpecification.QsVisitor getVisitor() {
      return this._visitor;
   }

   public SetQuantifier getSetQuantifier() {
      return this._sq;
   }

   public void setSetQuantifier(SetQuantifier sq) {
      this._sq = sq;
   }

   public boolean isAsterisk() {
      return this._bAsterisk;
   }

   public void setAsterisk(boolean bAsterisk) {
      this._bAsterisk = bAsterisk;
   }

   public List<SelectSublist> getSelectSublists() {
      return this._listSelectSublists;
   }

   private void setSelectSublists(List<SelectSublist> listSelectSublists) {
      this._listSelectSublists = listSelectSublists;
   }

   public void addSelectSublist(SelectSublist ss) {
      this._listSelectSublists.add(ss);
   }

   public List<TableReference> getTableReferences() {
      return this._listTableReferences;
   }

   private void setTableReferences(List<TableReference> listTableReferences) {
      this._listTableReferences = listTableReferences;
   }

   public BooleanValueExpression getWhereCondition() {
      return this._bveWhere;
   }

   public void setWhereCondition(BooleanValueExpression bveWhere) {
      this._bveWhere = bveWhere;
   }

   public SetQuantifier getSetQuantifierGroupBy() {
      return this._sqGroupBy;
   }

   public void setSetQuantifierGroupBy(SetQuantifier sqGroupBy) {
      this._sqGroupBy = sqGroupBy;
   }

   public List<GroupingElement> getGroupingElements() {
      return this._listGroupingElements;
   }

   private void setGroupingElements(List<GroupingElement> listGroupingElements) {
      this._listGroupingElements = listGroupingElements;
   }

   public BooleanValueExpression getHavingCondition() {
      return this._bveHaving;
   }

   public void setHavingCondition(BooleanValueExpression bveHaving) {
      this._bveHaving = bveHaving;
   }

   public List<Identifier> getWindowNames() {
      return this._listWindowNames;
   }

   private void setWindowNames(List<Identifier> listWindowNames) {
      this._listWindowNames = listWindowNames;
   }

   public List<WindowSpecification> getWindowSpecifications() {
      return this._listWindowSpecifications;
   }

   private void setWindowSpecifications(List<WindowSpecification> listWindowSpecifications) {
      this._listWindowSpecifications = listWindowSpecifications;
   }

   public boolean hasCount() {
      return this._bCount;
   }

   public void setCount(boolean bCount) {
      this._bCount = bCount;
   }

   public boolean hasAggregates() {
      return this._bAggregates;
   }

   public void setAggregates(boolean bAggregates) {
      this._bAggregates = bAggregates;
   }

   public boolean isCount() {
      boolean bCount = this.hasCount() && this.getSelectSublists().size() == 1 && this.getWhereCondition() == null;
      return bCount;
   }

   public boolean isGrouped() {
      boolean bGrouped = this.hasAggregates() || this.getGroupingElements().size() > 0;
      return bGrouped;
   }

   public boolean isGroupedOk(IdChain idcColumn) {
      boolean bGroupedOk = true;
      if (this.getGroupingElements().size() > 0 || this.hasAggregates()) {
         for(int i = 0; bGroupedOk && i < this.getGroupingElements().size(); ++i) {
            GroupingElement ge = (GroupingElement)this.getGroupingElements().get(i);
            if (!ge.getOrdinaryGroupingSets().contains(idcColumn)) {
               bGroupedOk = false;
            }
         }
      }

      return bGroupedOk;
   }

   protected boolean equalTables(QualifiedId qiTable1, QualifiedId qiTable2) {
      return qiTable1.equals(qiTable2);
   }

   private TablePrimary getTablePrimary(SqlStatement sqlstmt, QualifiedId qiTable, TablePrimary tp, String sColumnName) {
      TablePrimary tpFound = null;
      if (tp.getTableName().getName() != null) {
         QualifiedId qiName = new QualifiedId(tp.getTableName().getCatalog() != null ? tp.getTableName().getCatalog() : sqlstmt.getDefaultCatalog(), tp.getTableName().getSchema() != null ? tp.getTableName().getSchema() : sqlstmt.getDefaultSchema(), tp.getTableName().getName());
         QualifiedId qiCorrelation = new QualifiedId(tp.getTableName().getCatalog() != null ? tp.getTableName().getCatalog() : sqlstmt.getDefaultCatalog(), tp.getTableName().getSchema() != null ? tp.getTableName().getSchema() : sqlstmt.getDefaultSchema(), tp.getCorrelationName().get());
         if ((qiTable.getName() == null || this.equalTables(qiTable, qiName) || this.equalTables(qiTable, qiCorrelation)) && tp.hasColumn(sColumnName)) {
            tpFound = tp;
         }
      }

      if (tpFound == null && tp.getTableReference() != null) {
         tpFound = this.getTablePrimary(sqlstmt, qiTable, tp.getTableReference(), sColumnName);
      }

      return tpFound;
   }

   private TablePrimary getTablePrimary(SqlStatement sqlstmt, QualifiedId qiTable, TableReference tr, String sColumnName) {
      TablePrimary tp = null;
      if (tr.getTablePrimary() != null) {
         tp = this.getTablePrimary(sqlstmt, qiTable, tr.getTablePrimary(), sColumnName);
      }

      if (tp == null && tr.getTableReference() != null) {
         tp = this.getTablePrimary(sqlstmt, qiTable, tr.getTableReference(), sColumnName);
      }

      if (tp == null && tr.getSecondTableReference() != null) {
         tp = this.getTablePrimary(sqlstmt, qiTable, tr.getSecondTableReference(), sColumnName);
      }

      return tp;
   }

   public TablePrimary getTablePrimary(SqlStatement sqlstmt, IdChain idcColumn) {
      TablePrimary tpFound = null;
      List<String> list = idcColumn.get();
      int iSize = list.size();
      if (iSize > 0 && iSize <= 4) {
         String sColumnName = (String)list.get(iSize - 1);
         QualifiedId qiTable = new QualifiedId();
         if (iSize > 1) {
            qiTable.setName((String)list.get(iSize - 2));
         }

         if (iSize > 2) {
            qiTable.setSchema((String)list.get(iSize - 3));
         } else {
            qiTable.setSchema(sqlstmt.getDefaultSchema());
         }

         if (iSize > 3) {
            qiTable.setCatalog((String)list.get(iSize - 4));
         } else {
            qiTable.setCatalog(sqlstmt.getDefaultCatalog());
         }

         for(int iTable = 0; tpFound == null && iTable < this.getTableReferences().size(); ++iTable) {
            TablePrimary tp = this.getTablePrimary(sqlstmt, qiTable, (TableReference)this.getTableReferences().get(iTable), sColumnName);
            tpFound = tp;
         }

         return tpFound;
      } else {
         throw new IllegalArgumentException("Identifier chain is invalid for column!");
      }
   }

   public String format() {
      String sSpecification = K.SELECT.getKeyword();
      if (this.getSetQuantifier() != null) {
         sSpecification = sSpecification + " " + this.getSetQuantifier().getKeywords();
      }

      int i;
      if (this.isAsterisk()) {
         sSpecification = sSpecification + " " + "*";
      } else {
         for(i = 0; i < this.getSelectSublists().size(); ++i) {
            if (i > 0) {
               sSpecification = sSpecification + ",";
            }

            sSpecification = sSpecification + "\r\n" + "  " + ((SelectSublist)this.getSelectSublists().get(i)).format();
         }
      }

      sSpecification = sSpecification + "\r\n" + K.FROM.getKeyword();

      for(i = 0; i < this.getTableReferences().size(); ++i) {
         if (i > 0) {
            sSpecification = sSpecification + ",";
         }

         sSpecification = sSpecification + " " + ((TableReference)this.getTableReferences().get(i)).format();
      }

      if (this.getWhereCondition() != null) {
         sSpecification = sSpecification + "\r\n" + K.WHERE.getKeyword() + " " + this.getWhereCondition().format();
      }

      if (this.getGroupingElements().size() > 0) {
         sSpecification = sSpecification + "\r\n" + K.GROUP.getKeyword() + " " + K.BY.getKeyword();
         if (this.getSetQuantifierGroupBy() != null) {
            sSpecification = sSpecification + " " + this.getSetQuantifierGroupBy().getKeywords();
         }

         for(i = 0; i < this.getGroupingElements().size(); ++i) {
            if (i > 0) {
               sSpecification = sSpecification + ",";
            }

            sSpecification = sSpecification + " " + ((GroupingElement)this.getGroupingElements().get(i)).format();
         }
      }

      if (this.getHavingCondition() != null) {
         sSpecification = sSpecification + "\r\n" + K.HAVING.getKeyword() + " " + this.getHavingCondition().format();
      }

      if (this.getWindowNames().size() > 0) {
         sSpecification = sSpecification + "\r\n" + K.WINDOW.getKeyword();

         for(i = 0; i < this.getWindowNames().size(); ++i) {
            if (i > 0) {
               sSpecification = sSpecification + ",";
            }

            sSpecification = sSpecification + " " + ((Identifier)this.getWindowNames().get(i)).format() + " " + K.AS.getKeyword() + " " + ((WindowSpecification)this.getWindowSpecifications().get(i)).format();
         }
      }

      return sSpecification;
   }

   public void parse(SqlParser.QuerySpecificationContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public boolean evaluateWhere(SqlStatement ss) {
      boolean bWhere = true;
      if (this.getWhereCondition() != null) {
         bWhere = this.getWhereCondition().evaluate(ss, true);
      }

      return bWhere;
   }

   public boolean evaluateHaving(SqlStatement ss) {
      boolean bHaving = true;
      if (this.getHavingCondition() != null) {
         bHaving = this.getHavingCondition().evaluate(ss, false);
      }

      return bHaving;
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      SqlParser.QuerySpecificationContext ctx = null;

      try {
         ctx = this.getParser().querySpecification();
      } catch (Exception var4) {
         this.setParser(newSqlParser2(sSql));
         ctx = this.getParser().querySpecification();
      }

      this.parse(ctx);
   }

   public void initialize(SetQuantifier sq, boolean bAsterisk, List<SelectSublist> listSelectSublists, List<TableReference> listTableReferences, BooleanValueExpression bveWhere, boolean bAggregates, SetQuantifier sqGroupBy, List<GroupingElement> listGroupingElements, BooleanValueExpression bveHaving, List<Identifier> listWindowNames, List<WindowSpecification> listWindowSpecifications) {
      _il.enter(new Object[0]);
      this.setSetQuantifier(sq);
      this.setAsterisk(bAsterisk);
      this.setSelectSublists(listSelectSublists);
      this.setTableReferences(listTableReferences);
      this.setWhereCondition(bveWhere);
      this.setAggregates(bAggregates);
      this.setSetQuantifierGroupBy(sqGroupBy);
      this.setGroupingElements(listGroupingElements);
      this.setHavingCondition(bveHaving);
      this.setWindowNames(listWindowNames);
      this.setWindowSpecifications(listWindowSpecifications);
      _il.exit();
   }

   public void initialize(boolean bAsterisk, List<SelectSublist> listSelectSublists, List<TableReference> listTableReferences, BooleanValueExpression bveWhere) {
      _il.enter(new Object[0]);
      this.setAsterisk(bAsterisk);
      this.setSelectSublists(listSelectSublists);
      this.setTableReferences(listTableReferences);
      this.setWhereCondition(bveWhere);
      _il.exit();
   }

   public QuerySpecification(SqlFactory sf) {
      super(sf);
   }

   private class QsVisitor extends EnhancedSqlBaseVisitor<QuerySpecification> {
      private QsVisitor() {
      }

      public QuerySpecification visitQuerySpecification(SqlParser.QuerySpecificationContext ctx) {
         if (ctx.setQuantifier() != null) {
            QuerySpecification.this.setSetQuantifier(this.getSetQuantifier(ctx.setQuantifier()));
         }

         return (QuerySpecification)this.visitChildren(ctx);
      }

      public QuerySpecification visitSelectList(SqlParser.SelectListContext ctx) {
         if (ctx.ASTERISK() != null) {
            QuerySpecification.this.setAsterisk(true);
         } else {
            QuerySpecification.this.getSqlFactory().setCount(false);
            QuerySpecification.this.getSqlFactory().setAggregates(false);

            for(int i = 0; i < ctx.selectSublist().size(); ++i) {
               SelectSublist ss = QuerySpecification.this.getSqlFactory().newSelectSublist();
               ss.parse(ctx.selectSublist(i));
               QuerySpecification.this.getSelectSublists().add(ss);
            }

            if (QuerySpecification.this.getSqlFactory().hasCount()) {
               QuerySpecification.this.setCount(true);
            }

            if (QuerySpecification.this.getSqlFactory().hasAggregates()) {
               QuerySpecification.this.setAggregates(true);
            }
         }

         return QuerySpecification.this;
      }

      public QuerySpecification visitFromClause(SqlParser.FromClauseContext ctx) {
         for(int i = 0; i < ctx.tableReference().size(); ++i) {
            TableReference tr = QuerySpecification.this.getSqlFactory().newTableReference();
            tr.parse(ctx.tableReference(i));
            QuerySpecification.this.getTableReferences().add(tr);
         }

         return QuerySpecification.this;
      }

      public QuerySpecification visitWhereClause(SqlParser.WhereClauseContext ctx) {
         QuerySpecification.this.getSqlFactory().setAggregates(false);
         QuerySpecification.this.setWhereCondition(QuerySpecification.this.getSqlFactory().newBooleanValueExpression());
         QuerySpecification.this.getWhereCondition().parse(ctx.booleanValueExpression());
         return QuerySpecification.this;
      }

      public QuerySpecification visitGroupByClause(SqlParser.GroupByClauseContext ctx) {
         if (ctx.setQuantifier() != null) {
            QuerySpecification.this.setSetQuantifierGroupBy(this.getSetQuantifier(ctx.setQuantifier()));
         }

         for(int i = 0; i < ctx.groupingElement().size(); ++i) {
            GroupingElement ge = QuerySpecification.this.getSqlFactory().newGroupingElement();
            ge.parse(ctx.groupingElement(i));
            QuerySpecification.this.getGroupingElements().add(ge);
         }

         return QuerySpecification.this;
      }

      public QuerySpecification visitHavingClause(SqlParser.HavingClauseContext ctx) {
         QuerySpecification.this.getSqlFactory().setAggregates(false);
         QuerySpecification.this.setHavingCondition(QuerySpecification.this.getSqlFactory().newBooleanValueExpression());
         QuerySpecification.this.getHavingCondition().parse(ctx.booleanValueExpression());
         return QuerySpecification.this;
      }

      public QuerySpecification visitWindowClause(SqlParser.WindowClauseContext ctx) {
         for(int i = 0; i < ctx.windowDefinition().size(); ++i) {
            Identifier idWindowName = new Identifier();
            this.setIdentifier(ctx.windowDefinition(i).windowName().IDENTIFIER(), idWindowName);
            WindowSpecification ws = QuerySpecification.this.getSqlFactory().newWindowSpecification();
            ws.parse(ctx.windowDefinition(i).windowSpecification());
            QuerySpecification.this.getWindowSpecifications().add(ws);
         }

         return QuerySpecification.this;
      }

      // $FF: synthetic method
      QsVisitor(Object x1) {
         this();
      }
   }
}
