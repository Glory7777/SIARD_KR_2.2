package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.ddl.enums.DropBehavior;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;

public class AlterTableStatement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(AlterTableStatement.class.getName());
   private AlterTableStatement.AtsVisitor _visitor = new AlterTableStatement.AtsVisitor();
   private QualifiedId _qTableName = new QualifiedId();
   private ColumnDefinition _cd = null;
   private Identifier _idColumnName = new Identifier();
   private AlterColumnAction _aca = null;
   private TableConstraintDefinition _tcd = null;
   private QualifiedId _qConstraintName = new QualifiedId();
   private DropBehavior _db = null;

   private AlterTableStatement.AtsVisitor getVisitor() {
      return this._visitor;
   }

   public QualifiedId getTableName() {
      return this._qTableName;
   }

   private void setTableName(QualifiedId qTableName) {
      this._qTableName = qTableName;
   }

   public ColumnDefinition getColumnDefinition() {
      return this._cd;
   }

   public void setColumnDefinition(ColumnDefinition cd) {
      this._cd = cd;
   }

   public Identifier getColumnName() {
      return this._idColumnName;
   }

   private void setColumnName(Identifier idColumnName) {
      this._idColumnName = idColumnName;
   }

   public AlterColumnAction getAlterColumnAction() {
      return this._aca;
   }

   public void setAlterColumnAction(AlterColumnAction aca) {
      this._aca = aca;
   }

   public TableConstraintDefinition getTableConstraintDefinition() {
      return this._tcd;
   }

   public void setTableConstraintDefinition(TableConstraintDefinition tcd) {
      this._tcd = tcd;
   }

   public QualifiedId getConstraintName() {
      return this._qConstraintName;
   }

   private void setConstraintName(QualifiedId qConstraintName) {
      this._qConstraintName = qConstraintName;
   }

   public DropBehavior getDropBehavior() {
      return this._db;
   }

   public void setDropBehavior(DropBehavior db) {
      this._db = db;
   }

   public String format() {
      String sStatement = K.ALTER.getKeyword() + " " + K.TABLE.getKeyword() + " " + this.getTableName().format();
      if (this.getColumnDefinition() != null) {
         sStatement = sStatement + " " + K.ADD.getKeyword() + " " + K.COLUMN.getKeyword() + " " + this.getColumnDefinition().format();
      } else if (this.getAlterColumnAction() != null) {
         sStatement = sStatement + " " + K.ALTER.getKeyword() + " " + K.COLUMN.getKeyword() + " " + this.getColumnName().format() + " " + this.getAlterColumnAction().format();
      } else if (this.getColumnName().isSet()) {
         sStatement = sStatement + " " + K.DROP.getKeyword() + " " + K.COLUMN.getKeyword() + " " + this.getColumnName().format();
      } else if (this.getTableConstraintDefinition() != null) {
         sStatement = sStatement + " " + K.ADD.getKeyword() + " " + this.getTableConstraintDefinition().format();
      } else if (this.getConstraintName().isSet()) {
         sStatement = sStatement + " " + K.DROP.getKeyword() + " " + K.CONSTRAINT.getKeyword() + " " + this.getConstraintName().format() + " " + this.getDropBehavior().getKeywords();
      }

      return sStatement;
   }

   public void parse(SqlParser.AlterTableStatementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().alterTableStatement());
   }

   public void initialize(QualifiedId qTableName, ColumnDefinition cd, Identifier idColumnName, AlterColumnAction aca, TableConstraintDefinition tcd, QualifiedId qConstraintName, DropBehavior db) {
      _il.enter(new Object[]{qTableName, cd, idColumnName, aca, tcd, db});
      this.setTableName(qTableName);
      this.setColumnDefinition(cd);
      this.setColumnName(idColumnName);
      this.setAlterColumnAction(aca);
      this.setTableConstraintDefinition(tcd);
      this.setConstraintName(qConstraintName);
      this.setDropBehavior(db);
      _il.exit();
   }

   public AlterTableStatement(SqlFactory sf) {
      super(sf);
   }

   private class AtsVisitor extends EnhancedSqlBaseVisitor<AlterTableStatement> {
      private AtsVisitor() {
      }

      public AlterTableStatement visitTableName(SqlParser.TableNameContext ctx) {
         this.setTableName(ctx, AlterTableStatement.this.getTableName());
         return AlterTableStatement.this;
      }

      public AlterTableStatement visitAddColumnDefinition(SqlParser.AddColumnDefinitionContext ctx) {
         AlterTableStatement.this.setColumnDefinition(AlterTableStatement.this.getSqlFactory().newColumnDefinition());
         AlterTableStatement.this.getColumnDefinition().parse(ctx.columnDefinition());
         return AlterTableStatement.this;
      }

      public AlterTableStatement visitDropColumnDefinition(SqlParser.DropColumnDefinitionContext ctx) {
         this.setColumnName(ctx.columnName(), AlterTableStatement.this.getColumnName());
         return AlterTableStatement.this;
      }

      public AlterTableStatement visitAlterColumnDefinition(SqlParser.AlterColumnDefinitionContext ctx) {
         this.setColumnName(ctx.columnName(), AlterTableStatement.this.getColumnName());
         AlterTableStatement.this.setAlterColumnAction(AlterTableStatement.this.getSqlFactory().newAlterColumnAction());
         AlterTableStatement.this.getAlterColumnAction().parse(ctx.alterColumnAction());
         return AlterTableStatement.this;
      }

      public AlterTableStatement visitAddTableConstraintDefinition(SqlParser.AddTableConstraintDefinitionContext ctx) {
         AlterTableStatement.this.setTableConstraintDefinition(AlterTableStatement.this.getSqlFactory().newTableConstraintDefinition());
         AlterTableStatement.this.getTableConstraintDefinition().parse(ctx.tableConstraintDefinition());
         return AlterTableStatement.this;
      }

      public AlterTableStatement visitDropTableConstraintDefinition(SqlParser.DropTableConstraintDefinitionContext ctx) {
         this.setConstraintName(ctx.constraintName(), AlterTableStatement.this.getConstraintName());
         AlterTableStatement.this.setDropBehavior(this.getDropBehavior(ctx.dropBehavior()));
         return AlterTableStatement.this;
      }

      // $FF: synthetic method
      AtsVisitor(Object x1) {
         this();
      }
   }
}
