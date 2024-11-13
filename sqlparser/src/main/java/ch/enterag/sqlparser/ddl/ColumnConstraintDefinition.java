package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.ddl.enums.ColumnConstraintType;
import ch.enterag.sqlparser.ddl.enums.ConstraintCheckTime;
import ch.enterag.sqlparser.ddl.enums.Deferrability;
import ch.enterag.sqlparser.ddl.enums.Match;
import ch.enterag.sqlparser.ddl.enums.ReferentialAction;
import ch.enterag.sqlparser.expression.BooleanValueExpression;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;

public class ColumnConstraintDefinition extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(ColumnConstraintDefinition.class.getName());
   private ColumnConstraintDefinition.CcdVisitor _visitor = new ColumnConstraintDefinition.CcdVisitor();
   private QualifiedId _qConstraint = new QualifiedId();
   private ColumnConstraintType _type = null;
   private QualifiedId _qReferencedTable = new QualifiedId();
   private Identifier _idReferencedColumn = new Identifier();
   private Match _match = null;
   private ReferentialAction _raDelete = null;
   private ReferentialAction _raUpdate = null;
   private BooleanValueExpression _bve = null;
   private Deferrability _def = null;
   private ConstraintCheckTime _cct = null;

   private ColumnConstraintDefinition.CcdVisitor getVisitor() {
      return this._visitor;
   }

   public QualifiedId getConstraint() {
      return this._qConstraint;
   }

   private void setConstraint(QualifiedId qConstraint) {
      this._qConstraint = qConstraint;
   }

   public ColumnConstraintType getType() {
      return this._type;
   }

   public void setType(ColumnConstraintType type) {
      this._type = type;
   }

   public QualifiedId getReferencedTable() {
      return this._qReferencedTable;
   }

   private void setReferencedTable(QualifiedId qReferencedTable) {
      this._qReferencedTable = qReferencedTable;
   }

   public Identifier getReferencedColumn() {
      return this._idReferencedColumn;
   }

   private void setReferencedColumn(Identifier idReferencedColumn) {
      this._idReferencedColumn = idReferencedColumn;
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
      if (this.getType() == ColumnConstraintType.REFERENCES) {
         sDefinition = sDefinition + " " + this.getReferencedTable().format();
         if (this.getReferencedColumn().isSet()) {
            sDefinition = sDefinition + "(" + this.getReferencedColumn().format() + ")";
         }

         if (this.getMatch() != null) {
            sDefinition = sDefinition + " " + this.getMatch().getKeywords();
         }

         if (this.getDeleteAction() != null) {
            sDefinition = sDefinition + " " + K.ON.getKeyword() + " " + K.DELETE.getKeyword() + " " + this.getDeleteAction().getKeywords();
         }

         if (this.getUpdateAction() != null) {
            sDefinition = sDefinition + " " + K.ON.getKeyword() + " " + K.UPDATE.getKeyword() + " " + this.getUpdateAction().getKeywords();
         }
      } else if (this.getType() == ColumnConstraintType.CHECK) {
         sDefinition = sDefinition + "(" + this.getBooleanValueExpression().format() + ")";
      }

      if (this.getDeferrability() != null) {
         sDefinition = sDefinition + " " + this.getDeferrability().getKeywords();
      }

      if (this.getConstraintCheckTime() != null) {
         sDefinition = sDefinition + " " + this.getConstraintCheckTime().getKeywords();
      }

      return sDefinition;
   }

   public void parse(SqlParser.ColumnConstraintDefinitionContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().columnConstraintDefinition());
   }

   public void initialize(QualifiedId qConstraint, ColumnConstraintType type, QualifiedId qReferencedTable, Identifier idReferencedColumn, Match match, ReferentialAction raDelete, ReferentialAction raUpdate, BooleanValueExpression bve, Deferrability def, ConstraintCheckTime cct) {
      _il.enter(new Object[]{qConstraint, type, qReferencedTable, idReferencedColumn, match, raDelete, raUpdate, bve, def, cct});
      this.setConstraint(qConstraint);
      this.setType(type);
      this.setReferencedTable(qReferencedTable);
      this.setReferencedColumn(idReferencedColumn);
      this.setMatch(match);
      this.setDeleteAction(raDelete);
      this.setUpdateAction(raUpdate);
      this.setBooleanValueExpression(bve);
      this.setDeferrability(def);
      this.setConstraintCheckTime(cct);
      _il.exit();
   }

   public ColumnConstraintDefinition(SqlFactory sf) {
      super(sf);
   }

   private class CcdVisitor extends EnhancedSqlBaseVisitor<ColumnConstraintDefinition> {
      private CcdVisitor() {
      }

      public ColumnConstraintDefinition visitColumnConstraint(SqlParser.ColumnConstraintContext ctx) {
         ColumnConstraintDefinition.this.setType(this.getColumnConstraint(ctx));
         return (ColumnConstraintDefinition)this.visitChildren(ctx);
      }

      public ColumnConstraintDefinition visitConstraintName(SqlParser.ConstraintNameContext ctx) {
         this.setConstraintName(ctx, ColumnConstraintDefinition.this.getConstraint());
         return ColumnConstraintDefinition.this;
      }

      public ColumnConstraintDefinition visitTableName(SqlParser.TableNameContext ctx) {
         this.setTableName(ctx, ColumnConstraintDefinition.this.getReferencedTable());
         return ColumnConstraintDefinition.this;
      }

      public ColumnConstraintDefinition visitColumnName(SqlParser.ColumnNameContext ctx) {
         this.setColumnName(ctx, ColumnConstraintDefinition.this.getReferencedColumn());
         return ColumnConstraintDefinition.this;
      }

      public ColumnConstraintDefinition visitUpdateAction(SqlParser.UpdateActionContext ctx) {
         ColumnConstraintDefinition.this.setUpdateAction(this.getReferentialAction(ctx.referentialAction()));
         return ColumnConstraintDefinition.this;
      }

      public ColumnConstraintDefinition visitDeleteAction(SqlParser.DeleteActionContext ctx) {
         ColumnConstraintDefinition.this.setDeleteAction(this.getReferentialAction(ctx.referentialAction()));
         return ColumnConstraintDefinition.this;
      }

      public ColumnConstraintDefinition visitMatch(SqlParser.MatchContext ctx) {
         ColumnConstraintDefinition.this.setMatch(this.getMatch(ctx));
         return ColumnConstraintDefinition.this;
      }

      public ColumnConstraintDefinition visitBooleanValueExpression(SqlParser.BooleanValueExpressionContext ctx) {
         ColumnConstraintDefinition.this.setBooleanValueExpression(ColumnConstraintDefinition.this.getSqlFactory().newBooleanValueExpression());
         ColumnConstraintDefinition.this.getBooleanValueExpression().parse(ctx);
         return ColumnConstraintDefinition.this;
      }

      public ColumnConstraintDefinition visitDeferrability(SqlParser.DeferrabilityContext ctx) {
         ColumnConstraintDefinition.this.setDeferrability(this.getDeferrability(ctx));
         return ColumnConstraintDefinition.this;
      }

      public ColumnConstraintDefinition visitConstraintCheckTime(SqlParser.ConstraintCheckTimeContext ctx) {
         ColumnConstraintDefinition.this.setConstraintCheckTime(this.getConstraintCheckTime(ctx));
         return ColumnConstraintDefinition.this;
      }

      // $FF: synthetic method
      CcdVisitor(Object x1) {
         this();
      }
   }
}
