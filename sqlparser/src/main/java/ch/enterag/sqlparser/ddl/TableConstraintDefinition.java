package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.ddl.enums.ConstraintCheckTime;
import ch.enterag.sqlparser.ddl.enums.Deferrability;
import ch.enterag.sqlparser.ddl.enums.Match;
import ch.enterag.sqlparser.ddl.enums.ReferentialAction;
import ch.enterag.sqlparser.ddl.enums.TableConstraintType;
import ch.enterag.sqlparser.expression.BooleanValueExpression;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.IdList;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;

public class TableConstraintDefinition extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(TableConstraintDefinition.class.getName());
   private TableConstraintDefinition.TcdVisitor _visitor = new TableConstraintDefinition.TcdVisitor();
   private QualifiedId _qConstraint = new QualifiedId();
   private TableConstraintType _type = null;
   private IdList _idlColumns = new IdList();
   private QualifiedId _qReferencedTable = new QualifiedId();
   private IdList _idlReferencedColumns = new IdList();
   private Match _match = null;
   private ReferentialAction _raDelete = null;
   private ReferentialAction _raUpdate = null;
   private BooleanValueExpression _bve = null;
   private Deferrability _def = null;
   private ConstraintCheckTime _cct = null;

   private TableConstraintDefinition.TcdVisitor getVisitor() {
      return this._visitor;
   }

   public QualifiedId getConstraint() {
      return this._qConstraint;
   }

   private void setConstraint(QualifiedId qConstraint) {
      this._qConstraint = qConstraint;
   }

   public TableConstraintType getType() {
      return this._type;
   }

   public void setType(TableConstraintType type) {
      this._type = type;
   }

   public IdList getColumns() {
      return this._idlColumns;
   }

   private void setColumns(IdList idlColumns) {
      this._idlColumns = idlColumns;
   }

   public QualifiedId getReferencedTable() {
      return this._qReferencedTable;
   }

   private void setReferencedTable(QualifiedId qReferencedTable) {
      this._qReferencedTable = qReferencedTable;
   }

   public IdList getReferencedColumns() {
      return this._idlReferencedColumns;
   }

   private void setReferencedColumns(IdList idlReferencedColumns) {
      this._idlReferencedColumns = idlReferencedColumns;
   }

   public Match getMatch() {
      return this._match;
   }

   public void setMatch(Match match) {
      this._match = match;
   }

   public ReferentialAction getDeleteAction() {
      return this._raDelete;
   }

   public void setDeleteAction(ReferentialAction raDelete) {
      this._raDelete = raDelete;
   }

   public ReferentialAction getUpdateAction() {
      return this._raUpdate;
   }

   public void setUpdateAction(ReferentialAction raUpdate) {
      this._raUpdate = raUpdate;
   }

   public BooleanValueExpression getBooleanValueExpression() {
      return this._bve;
   }

   public void setBooleanValueExpression(BooleanValueExpression bve) {
      this._bve = bve;
   }

   public Deferrability getDeferrability() {
      return this._def;
   }

   public void setDeferrability(Deferrability def) {
      this._def = def;
   }

   public ConstraintCheckTime getConstraintCheckTime() {
      return this._cct;
   }

   public void setConstraintCheckTime(ConstraintCheckTime cct) {
      this._cct = cct;
   }

   public String format() {
      String sDefinition = "";
      if (this.getConstraint().isSet()) {
         sDefinition = K.CONSTRAINT.getKeyword() + " " + this.getConstraint().format() + " ";
      }

      sDefinition = sDefinition + this.getType().getKeywords();
      if (this.getType() == TableConstraintType.CHECK) {
         sDefinition = sDefinition + "(" + this.getBooleanValueExpression().format() + ")";
      } else {
         sDefinition = sDefinition + "(" + this.getColumns().format() + ")";
         if (this.getType() == TableConstraintType.FOREIGN_KEY) {
            sDefinition = sDefinition + " " + K.REFERENCES.getKeyword() + " " + this.getReferencedTable().format() + "(" + this.getReferencedColumns().format() + ")";
            if (this.getMatch() != null) {
               sDefinition = sDefinition + " " + this.getMatch().getKeywords();
            }

            if (this.getDeleteAction() != null) {
               sDefinition = sDefinition + " " + K.ON.getKeyword() + " " + K.DELETE.getKeyword() + " " + this.getDeleteAction().getKeywords();
            }

            if (this.getUpdateAction() != null) {
               sDefinition = sDefinition + " " + K.ON.getKeyword() + " " + K.UPDATE.getKeyword() + " " + this.getUpdateAction().getKeywords();
            }
         }
      }

      if (this.getDeferrability() != null) {
         sDefinition = sDefinition + " " + this.getDeferrability().getKeywords();
      }

      if (this.getConstraintCheckTime() != null) {
         sDefinition = sDefinition + " " + this.getConstraintCheckTime().getKeywords();
      }

      return sDefinition;
   }

   public void parse(SqlParser.TableConstraintDefinitionContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().tableConstraintDefinition());
   }

   public void initialize(QualifiedId qConstraint, TableConstraintType type, IdList idlColumns, QualifiedId qReferencedTable, IdList idlReferencedColumns, Match match, ReferentialAction raDelete, ReferentialAction raUpdate, BooleanValueExpression bve, Deferrability def, ConstraintCheckTime cct) {
      _il.enter(new Object[]{qConstraint, type, idlColumns, qReferencedTable, idlReferencedColumns, match, raDelete, raUpdate, bve, def, cct});
      this.setConstraint(qConstraint);
      this.setType(type);
      this.setColumns(idlColumns);
      this.setReferencedTable(qReferencedTable);
      this.setReferencedColumns(idlReferencedColumns);
      this.setMatch(match);
      this.setDeleteAction(raDelete);
      this.setUpdateAction(raUpdate);
      this.setBooleanValueExpression(bve);
      this.setDeferrability(def);
      this.setConstraintCheckTime(cct);
      _il.exit();
   }

   public void initPrimaryKey(QualifiedId qConstraint, IdList idlColumns) {
      _il.enter(new Object[]{qConstraint, idlColumns});
      this.setConstraint(qConstraint);
      this.setType(TableConstraintType.PRIMARY_KEY);
      this.setColumns(idlColumns);
      _il.exit();
   }

   public TableConstraintDefinition(SqlFactory sf) {
      super(sf);
   }

   private class TcdVisitor extends EnhancedSqlBaseVisitor<TableConstraintDefinition> {
      private TcdVisitor() {
      }

      public TableConstraintDefinition visitConstraintName(SqlParser.ConstraintNameContext ctx) {
         this.setConstraintName(ctx, TableConstraintDefinition.this.getConstraint());
         return TableConstraintDefinition.this;
      }

      public TableConstraintDefinition visitTableConstraint(SqlParser.TableConstraintContext ctx) {
         TableConstraintDefinition.this.setType(this.getTableConstraint(ctx));
         return (TableConstraintDefinition)this.visitChildren(ctx);
      }

      public TableConstraintDefinition visitTableName(SqlParser.TableNameContext ctx) {
         this.setTableName(ctx, TableConstraintDefinition.this.getReferencedTable());
         return TableConstraintDefinition.this;
      }

      public TableConstraintDefinition visitColumnName(SqlParser.ColumnNameContext ctx) {
         if (ctx.getParent() instanceof SqlParser.ReferencesSpecificationContext) {
            this.addColumnName(ctx, TableConstraintDefinition.this.getReferencedColumns());
         } else {
            this.addColumnName(ctx, TableConstraintDefinition.this.getColumns());
         }

         return TableConstraintDefinition.this;
      }

      public TableConstraintDefinition visitUpdateAction(SqlParser.UpdateActionContext ctx) {
         TableConstraintDefinition.this.setUpdateAction(this.getReferentialAction(ctx.referentialAction()));
         return TableConstraintDefinition.this;
      }

      public TableConstraintDefinition visitDeleteAction(SqlParser.DeleteActionContext ctx) {
         TableConstraintDefinition.this.setDeleteAction(this.getReferentialAction(ctx.referentialAction()));
         return TableConstraintDefinition.this;
      }

      public TableConstraintDefinition visitMatch(SqlParser.MatchContext ctx) {
         TableConstraintDefinition.this.setMatch(this.getMatch(ctx));
         return TableConstraintDefinition.this;
      }

      public TableConstraintDefinition visitBooleanValueExpression(SqlParser.BooleanValueExpressionContext ctx) {
         TableConstraintDefinition.this.setBooleanValueExpression(TableConstraintDefinition.this.getSqlFactory().newBooleanValueExpression());
         TableConstraintDefinition.this.getBooleanValueExpression().parse(ctx);
         return TableConstraintDefinition.this;
      }

      public TableConstraintDefinition visitDeferrability(SqlParser.DeferrabilityContext ctx) {
         TableConstraintDefinition.this.setDeferrability(this.getDeferrability(ctx));
         return TableConstraintDefinition.this;
      }

      public TableConstraintDefinition visitConstraintCheckTime(SqlParser.ConstraintCheckTimeContext ctx) {
         TableConstraintDefinition.this.setConstraintCheckTime(this.getConstraintCheckTime(ctx));
         return TableConstraintDefinition.this;
      }

      // $FF: synthetic method
      TcdVisitor(Object x1) {
         this();
      }
   }
}
