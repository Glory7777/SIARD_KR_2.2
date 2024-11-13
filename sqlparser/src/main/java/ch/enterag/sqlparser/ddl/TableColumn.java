package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.utils.logging.IndentLogger;

public class TableColumn extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(TableColumn.class.getName());
   private TableColumn.TcVisitor _visitor = new TableColumn.TcVisitor();
   private Identifier _idColumnName = new Identifier();
   private DataType _dt = null;

   private TableColumn.TcVisitor getVisitor() {
      return this._visitor;
   }

   public Identifier getColumnName() {
      return this._idColumnName;
   }

   private void setColumnName(Identifier idColumnName) {
      this._idColumnName = idColumnName;
   }

   public DataType getDataType() {
      return this._dt;
   }

   public void setDataType(DataType dt) {
      this._dt = dt;
   }

   public String format() {
      String sTableColumn = this.getColumnName().format() + " " + this.getDataType().format();
      return sTableColumn;
   }

   public void parse(SqlParser.TableColumnContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().tableColumn());
   }

   public void initialize(Identifier idColumnName, DataType dt) {
      _il.enter(new Object[]{idColumnName, dt});
      this.setColumnName(idColumnName);
      this.setDataType(dt);
      _il.exit();
   }

   public TableColumn(SqlFactory sf) {
      super(sf);
   }

   private class TcVisitor extends EnhancedSqlBaseVisitor<TableColumn> {
      private TcVisitor() {
      }

      public TableColumn visitColumnName(SqlParser.ColumnNameContext ctx) {
         this.setColumnName(ctx, TableColumn.this.getColumnName());
         return TableColumn.this;
      }

      public TableColumn visitDataType(SqlParser.DataTypeContext ctx) {
         TableColumn.this.setDataType(TableColumn.this.getSqlFactory().newDataType());
         TableColumn.this.getDataType().parse(ctx);
         return TableColumn.this;
      }

      // $FF: synthetic method
      TcVisitor(Object x1) {
         this();
      }
   }
}
