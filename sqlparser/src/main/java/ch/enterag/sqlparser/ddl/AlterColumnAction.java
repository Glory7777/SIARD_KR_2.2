package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.ddl.enums.DropBehavior;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;

public class AlterColumnAction extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(AlterColumnAction.class.getName());
   private AlterColumnAction.AcaVisitor _visitor = new AlterColumnAction.AcaVisitor();
   private String _sDefaultOption = null;
   private boolean _bDropDefault = false;
   private QualifiedId _qScopeTable = new QualifiedId();
   private DropBehavior _dbScope = null;

   private AlterColumnAction.AcaVisitor getVisitor() {
      return this._visitor;
   }

   public String getDefaultOption() {
      return this._sDefaultOption;
   }

   public void setDefaultOption(String sDefaultOption) {
      this._sDefaultOption = sDefaultOption;
   }

   public boolean isDropDefault() {
      return this._bDropDefault;
   }

   public void setDropDefault(boolean bDropDefault) {
      this._bDropDefault = bDropDefault;
   }

   public QualifiedId getScopeTable() {
      return this._qScopeTable;
   }

   private void setScopeTable(QualifiedId qScopeTable) {
      this._qScopeTable = qScopeTable;
   }

   public DropBehavior getScopeDropBehavior() {
      return this._dbScope;
   }

   public void setScopeDropBehavior(DropBehavior dbScope) {
      this._dbScope = dbScope;
   }

   public String format() {
      String sAction = null;
      if (this.getDefaultOption() != null) {
         sAction = K.SET.getKeyword() + " " + K.DEFAULT.getKeyword() + " " + this.getDefaultOption();
      } else if (this.isDropDefault()) {
         sAction = K.DROP.getKeyword() + " " + K.DEFAULT.getKeyword();
      } else if (this.getScopeTable().isSet()) {
         sAction = K.ADD.getKeyword() + " " + K.SCOPE.getKeyword() + " " + this.getScopeTable().format();
      } else if (this.getScopeDropBehavior() != null) {
         sAction = K.DROP.getKeyword() + " " + K.SCOPE.getKeyword() + " " + this.getScopeDropBehavior().getKeywords();
      }

      return sAction;
   }

   public void parse(SqlParser.AlterColumnActionContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().alterColumnAction());
   }

   public void initialize(String sDefaultOption, boolean bDropDefault, QualifiedId qScopeTable, DropBehavior dbScope) {
      _il.enter(new Object[]{sDefaultOption, String.valueOf(bDropDefault), qScopeTable, dbScope});
      this.setDefaultOption(sDefaultOption);
      this.setDropDefault(bDropDefault);
      this.setScopeTable(qScopeTable);
      this.setScopeDropBehavior(dbScope);
      _il.exit();
   }

   public AlterColumnAction(SqlFactory sf) {
      super(sf);
   }

   private class AcaVisitor extends EnhancedSqlBaseVisitor<AlterColumnAction> {
      private AcaVisitor() {
      }

      public AlterColumnAction visitSetColumnDefaultClause(SqlParser.SetColumnDefaultClauseContext ctx) {
         String sDefaultValue = ctx.defaultOption().getText();
         if (!sDefaultValue.startsWith("'")) {
            sDefaultValue = sDefaultValue.toUpperCase();
         }

         AlterColumnAction.this.setDefaultOption(sDefaultValue);
         return AlterColumnAction.this;
      }

      public AlterColumnAction visitDropColumnDefaultClause(SqlParser.DropColumnDefaultClauseContext ctx) {
         AlterColumnAction.this.setDropDefault(true);
         return AlterColumnAction.this;
      }

      public AlterColumnAction visitAddColumnScopeClause(SqlParser.AddColumnScopeClauseContext ctx) {
         this.setTableName(ctx.tableName(), AlterColumnAction.this.getScopeTable());
         return AlterColumnAction.this;
      }

      public AlterColumnAction visitDropColumnScopeClause(SqlParser.DropColumnScopeClauseContext ctx) {
         AlterColumnAction.this.setScopeDropBehavior(this.getDropBehavior(ctx.dropBehavior()));
         return AlterColumnAction.this;
      }

      // $FF: synthetic method
      AcaVisitor(Object x1) {
         this();
      }
   }
}
