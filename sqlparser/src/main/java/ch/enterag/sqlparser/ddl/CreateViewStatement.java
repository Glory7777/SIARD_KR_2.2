package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.ddl.enums.Levels;
import ch.enterag.sqlparser.expression.QueryExpression;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.IdList;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class CreateViewStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(CreateViewStatement.class.getName());
   private CreateViewStatement.CvsVisitor _visitor = new CreateViewStatement.CvsVisitor();
   private boolean _bRecursive = false;
   private QualifiedId _qViewName = new QualifiedId();
   private IdList _ilColumnNames = new IdList();
   private QualifiedId _qUdtName = new QualifiedId();
   private List<ViewElement> _listVe = new ArrayList();
   private QualifiedId _qSuperTable = new QualifiedId();
   private QueryExpression _qe = null;
   private boolean _bCheckOption = false;
   private Levels _levels = null;

   private CreateViewStatement.CvsVisitor getVisitor() {
      return this._visitor;
   }

   public boolean getRecursive() {
      return this._bRecursive;
   }

   public void setRecursive(boolean bRecursive) {
      this._bRecursive = bRecursive;
   }

   public QualifiedId getViewName() {
      return this._qViewName;
   }

   private void setViewName(QualifiedId qViewName) {
      this._qViewName = qViewName;
   }

   public IdList getColumnNames() {
      return this._ilColumnNames;
   }

   private void setColumnNames(IdList ilColumnNames) {
      this._ilColumnNames = ilColumnNames;
   }

   public QualifiedId getUdtName() {
      return this._qUdtName;
   }

   private void setUdtName(QualifiedId qUdtName) {
      this._qUdtName = qUdtName;
   }

   public List<ViewElement> getViewElements() {
      return this._listVe;
   }

   private void setViewElements(List<ViewElement> listVe) {
      this._listVe = listVe;
   }

   public QualifiedId getSuperTable() {
      return this._qSuperTable;
   }

   private void setSuperTable(QualifiedId qSuperTable) {
      this._qSuperTable = qSuperTable;
   }

   public QueryExpression getQueryExpression() {
      return this._qe;
   }

   public void setQueryExpression(QueryExpression qe) {
      this._qe = qe;
   }

   public boolean getCheckOption() {
      return this._bCheckOption;
   }

   public void setCheckOption(boolean bCheckOption) {
      this._bCheckOption = bCheckOption;
   }

   public Levels getLevels() {
      return this._levels;
   }

   public void setLevels(Levels levels) {
      this._levels = levels;
   }

   public String format() {
      String sStatement = K.CREATE.getKeyword();
      if (this.getRecursive()) {
         sStatement = sStatement + " " + K.RECURSIVE.getKeyword();
      }

      sStatement = sStatement + " " + K.VIEW.getKeyword() + " " + this.getViewName().format();
      if (this.getColumnNames().isSet()) {
         sStatement = sStatement + "(" + this.getColumnNames().format() + ")";
      } else {
         sStatement = sStatement + " " + K.OF.getKeyword() + " " + this.getUdtName().format();
         if (this.getSuperTable() != null) {
            sStatement = sStatement + " " + K.UNDER.getKeyword() + " " + this.getSuperTable().format();
         }

         sStatement = sStatement + "(";

         for(int i = 0; i < this.getViewElements().size(); ++i) {
            if (i > 0) {
               sStatement = sStatement + ",";
            }

            sStatement = sStatement + " " + ((ViewElement)this.getViewElements().get(i)).format();
         }

         sStatement = sStatement + ")";
      }

      sStatement = sStatement + " " + K.AS.getKeyword() + " " + this.getQueryExpression().format();
      if (this.getCheckOption()) {
         sStatement = sStatement + " " + K.WITH.getKeyword();
         if (this.getLevels() != null) {
            sStatement = sStatement + " " + this.getLevels().getKeywords();
         }

         sStatement = sStatement + " " + K.CHECK.getKeyword() + " " + K.OPTION.getKeyword();
      }

      return sStatement;
   }

   public void parse(SqlParser.CreateViewStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().createViewStatement());
   }

   public void initialize(boolean bRecursive, QualifiedId qiViewName, IdList ilColumnNames, QualifiedId qiUdtName, List<ViewElement> listVe, QualifiedId qiSuperTable, QueryExpression qe, boolean bCheckOption, Levels levels) {
      _il.enter(new Object[0]);
      this.setRecursive(bRecursive);
      this.setViewName(qiViewName);
      this.setColumnNames(ilColumnNames);
      this.setUdtName(qiUdtName);
      this.setViewElements(listVe);
      this.setSuperTable(qiSuperTable);
      this.setQueryExpression(qe);
      this.setCheckOption(bCheckOption);
      this.setLevels(levels);
      _il.exit();
   }

   public CreateViewStatement(SqlFactory sf) {
      super(sf);
   }

   private class CvsVisitor extends EnhancedSqlBaseVisitor<CreateViewStatement> {
      private CvsVisitor() {
      }

      public CreateViewStatement visitCreateViewStatement(SqlParser.CreateViewStatementContext ctx) {
         if (ctx.RECURSIVE() != null) {
            CreateViewStatement.this.setRecursive(true);
         }

         if (ctx.CHECK() != null) {
            CreateViewStatement.this.setCheckOption(true);
         }

         return (CreateViewStatement)this.visitChildren(ctx);
      }

      public CreateViewStatement visitTableName(SqlParser.TableNameContext ctx) {
         this.setTableName(ctx, CreateViewStatement.this.getViewName());
         return CreateViewStatement.this;
      }

      public CreateViewStatement visitQueryExpression(SqlParser.QueryExpressionContext ctx) {
         CreateViewStatement.this.setQueryExpression(CreateViewStatement.this.getSqlFactory().newQueryExpression());
         CreateViewStatement.this.getQueryExpression().parse(ctx);
         return CreateViewStatement.this;
      }

      public CreateViewStatement visitColumnName(SqlParser.ColumnNameContext ctx) {
         this.addColumnName(ctx, CreateViewStatement.this.getColumnNames());
         return CreateViewStatement.this;
      }

      public CreateViewStatement visitUdtName(SqlParser.UdtNameContext ctx) {
         this.setUdtName(ctx, CreateViewStatement.this.getUdtName());
         return CreateViewStatement.this;
      }

      public CreateViewStatement visitSubviewClause(SqlParser.SubviewClauseContext ctx) {
         this.setTableName(ctx.tableName(), CreateViewStatement.this.getSuperTable());
         return CreateViewStatement.this;
      }

      public CreateViewStatement visitViewElement(SqlParser.ViewElementContext ctx) {
         ViewElement ve = CreateViewStatement.this.getSqlFactory().newViewElement();
         ve.parse(ctx);
         CreateViewStatement.this.getViewElements().add(ve);
         return CreateViewStatement.this;
      }

      public CreateViewStatement visitLevels(SqlParser.LevelsContext ctx) {
         CreateViewStatement.this.setLevels(this.getLevels(ctx));
         return CreateViewStatement.this;
      }

      // $FF: synthetic method
      CvsVisitor(Object x1) {
         this();
      }
   }
}
