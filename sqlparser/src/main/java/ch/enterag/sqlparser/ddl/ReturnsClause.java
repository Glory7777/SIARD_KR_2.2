package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class ReturnsClause extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(ReturnsClause.class.getName());
   private ReturnsClause.RcVisitor _visitor = new ReturnsClause.RcVisitor();
   private DataType _dt = null;
   private boolean _bAsLocator = false;
   private DataType _dtCast = null;
   private boolean _bCastAsLocator = false;
   private List<TableColumn> _listTableColumns = new ArrayList();

   private ReturnsClause.RcVisitor getVisitor() {
      return this._visitor;
   }

   public DataType getDataType() {
      return this._dt;
   }

   public void setDataType(DataType dt) {
      this._dt = dt;
   }

   public boolean isAsLocator() {
      return this._bAsLocator;
   }

   public void setAsLocator(boolean bAsLocator) {
      this._bAsLocator = bAsLocator;
   }

   public DataType getCastDataType() {
      return this._dtCast;
   }

   public void setCastDataType(DataType dtCast) {
      this._dtCast = dtCast;
   }

   public boolean isCastAsLocator() {
      return this._bCastAsLocator;
   }

   public void setCastAsLocator(boolean bCastAsLocator) {
      this._bCastAsLocator = bCastAsLocator;
   }

   public List<TableColumn> getTableColumns() {
      return this._listTableColumns;
   }

   private void setTableColumns(List<TableColumn> listTableColumns) {
      this._listTableColumns = listTableColumns;
   }

   private String formatTableColumns() {
      String s = "(";

      for(int i = 0; i < this.getTableColumns().size(); ++i) {
         if (i > 0) {
            s = s + "," + " ";
         }

         s = s + ((TableColumn)this.getTableColumns().get(i)).format();
      }

      s = s + ")";
      return s;
   }

   public String format() {
      String s = K.RETURNS.getKeyword();
      if (this.getDataType() == null && !this.isAsLocator()) {
         if (this.getTableColumns().size() > 0) {
            s = s + K.TABLE.getKeyword() + this.formatTableColumns();
         }
      } else {
         if (this.getDataType() != null) {
            s = s + " " + this.getDataType().format();
         } else if (this.isAsLocator()) {
            s = s + " " + K.AS.getKeyword() + " " + K.LOCATOR.getKeyword();
         }

         if (this.getCastDataType() != null || this.isCastAsLocator()) {
            s = s + " " + K.CAST.getKeyword() + " " + K.FROM.getKeyword();
            if (this.getCastDataType() != null) {
               s = s + " " + this.getCastDataType().format();
            } else if (this.isCastAsLocator()) {
               s = s + " " + K.AS.getKeyword() + " " + K.LOCATOR.getKeyword();
            }
         }
      }

      return s;
   }

   public void parse(SqlParser.ReturnsClauseContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().returnsClause());
   }

   public void initialize(DataType dt, boolean bAsLocator, DataType dtCast, boolean bCastAsLocator, List<TableColumn> listTableColumns) {
      _il.enter(new Object[0]);
      this.setDataType(dt);
      this.setAsLocator(bAsLocator);
      this.setCastDataType(dtCast);
      this.setCastAsLocator(bCastAsLocator);
      this.setTableColumns(listTableColumns);
      _il.exit();
   }

   public ReturnsClause(SqlFactory sf) {
      super(sf);
   }

   private class RcVisitor extends EnhancedSqlBaseVisitor<ReturnsClause> {
      private RcVisitor() {
      }

      public ReturnsClause visitReturnsDataType(SqlParser.ReturnsDataTypeContext ctx) {
         if (ctx.LOCATOR() != null) {
            ReturnsClause.this.setAsLocator(true);
         } else {
            ReturnsClause.this.setDataType(ReturnsClause.this.getSqlFactory().newDataType());
            ReturnsClause.this.getDataType().parse(ctx.dataType());
         }

         return ReturnsClause.this;
      }

      public ReturnsClause visitResultCast(SqlParser.ResultCastContext ctx) {
         if (ctx.LOCATOR() != null) {
            ReturnsClause.this.setCastAsLocator(true);
         } else {
            ReturnsClause.this.setCastDataType(ReturnsClause.this.getSqlFactory().newDataType());
            ReturnsClause.this.getCastDataType().parse(ctx.dataType());
         }

         return ReturnsClause.this;
      }

      public ReturnsClause visitTableColumn(SqlParser.TableColumnContext ctx) {
         TableColumn tc = ReturnsClause.this.getSqlFactory().newTableColumn();
         tc.parse(ctx);
         ReturnsClause.this.getTableColumns().add(tc);
         return ReturnsClause.this;
      }

      // $FF: synthetic method
      RcVisitor(Object x1) {
         this();
      }
   }
}
