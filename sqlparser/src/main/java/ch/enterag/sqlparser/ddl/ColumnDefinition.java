package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.ddl.enums.ReferenceScopeCheck;
import ch.enterag.sqlparser.ddl.enums.ReferentialAction;
import ch.enterag.sqlparser.expression.ValueExpression;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.utils.logging.IndentLogger;

public class ColumnDefinition extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(ColumnDefinition.class.getName());
   public static final int iUNDEFINED = -1;
   private ColumnDefinition.CdVisitor _visitor = new ColumnDefinition.CdVisitor();
   private Identifier _idColumnName = new Identifier();
   private DataType _dt = null;
   private ReferenceScopeCheck _rsc = null;
   private ReferentialAction _raDelete = null;
   private String _sDefaultOption = null;
   private ValueExpression _ve = null;
   private ColumnConstraintDefinition _ccd = null;

   private ColumnDefinition.CdVisitor getVisitor() {
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

   public ReferenceScopeCheck getReferenceScopeCheck() {
      return this._rsc;
   }

   public void setReferenceScopeCheck(ReferenceScopeCheck rc) {
      this._rsc = rc;
   }

   public ReferentialAction getDeleteAction() {
      return this._raDelete;
   }

   public void setDeleteAction(ReferentialAction raDelete) {
      this._raDelete = raDelete;
   }

   public String getDefaultOption() {
      return this._sDefaultOption;
   }

   public void setDefaultOption(String sDefaultOption) {
      this._sDefaultOption = sDefaultOption;
   }

   public ValueExpression getValueExpression() {
      return this._ve;
   }

   public void setValueExpression(ValueExpression ve) {
      this._ve = ve;
   }

   public ColumnConstraintDefinition getColumnConstraintDefinition() {
      return this._ccd;
   }

   public void setColumnConstraintDefinition(ColumnConstraintDefinition ccd) {
      this._ccd = ccd;
   }

   public String format() {
      String sDefinition = this.getColumnName().format() + " " + this.getDataType().format();
      if (this.getReferenceScopeCheck() != null) {
         sDefinition = sDefinition + " " + this.getReferenceScopeCheck().getKeywords();
         if (this.getDeleteAction() != null) {
            sDefinition = sDefinition + " " + this.getDeleteAction().getKeywords();
         }
      }

      if (this.getDefaultOption() != null) {
         sDefinition = sDefinition + " " + K.DEFAULT.getKeyword() + " " + this.getDefaultOption();
      }

      if (this.getValueExpression() != null) {
         sDefinition = sDefinition + " " + K.GENERATED.getKeyword() + " " + K.ALWAYS.getKeyword() + " " + K.AS.getKeyword() + "(" + this.getValueExpression().format() + ")";
      }

      if (this.getColumnConstraintDefinition() != null) {
         sDefinition = sDefinition + " " + this.getColumnConstraintDefinition().format();
      }

      return sDefinition;
   }

   public void parse(SqlParser.ColumnDefinitionContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().columnDefinition());
   }

   public void initialize(Identifier idColumnName, DataType dt, ReferenceScopeCheck rsc, ReferentialAction raDelete, String sDefaultOption, ColumnConstraintDefinition ccd) {
      _il.enter(new Object[]{idColumnName, dt, rsc, sDefaultOption, ccd});
      this.setColumnName(idColumnName);
      this.setDataType(dt);
      this.setReferenceScopeCheck(rsc);
      this.setDeleteAction(raDelete);
      this.setDefaultOption(sDefaultOption);
      this.setColumnConstraintDefinition(ccd);
      _il.exit();
   }

   public void initNameType(Identifier idColumnName, DataType dt) {
      _il.enter(new Object[]{idColumnName, dt});
      this.setColumnName(idColumnName);
      this.setDataType(dt);
      _il.exit();
   }

   public ColumnDefinition(SqlFactory sf) {
      super(sf);
   }

   private class CdVisitor extends EnhancedSqlBaseVisitor<ColumnDefinition> {
      private CdVisitor() {
      }

      public ColumnDefinition visitReferenceScopeCheck(SqlParser.ReferenceScopeCheckContext ctx) {
         ColumnDefinition.this.setReferenceScopeCheck(this.getReferenceScopeCheck(ctx));
         return ColumnDefinition.this;
      }

      public ColumnDefinition visitColumnName(SqlParser.ColumnNameContext ctx) {
         this.setColumnName(ctx, ColumnDefinition.this.getColumnName());
         return ColumnDefinition.this;
      }

      public ColumnDefinition visitDataType(SqlParser.DataTypeContext ctx) {
         ColumnDefinition.this.setDataType(ColumnDefinition.this.getSqlFactory().newDataType());
         ColumnDefinition.this.getDataType().parse(ctx);
         return ColumnDefinition.this;
      }

      public ColumnDefinition visitDeleteAction(SqlParser.DeleteActionContext ctx) {
         ColumnDefinition.this.setDeleteAction(this.getReferentialAction(ctx.referentialAction()));
         return ColumnDefinition.this;
      }

      public ColumnDefinition visitDefaultOption(SqlParser.DefaultOptionContext ctx) {
         String sDefaultValue = ctx.getText();
         if (!sDefaultValue.startsWith("'")) {
            sDefaultValue = sDefaultValue.toUpperCase();
         }

         ColumnDefinition.this.setDefaultOption(sDefaultValue);
         return ColumnDefinition.this;
      }

      public ColumnDefinition visitValueExpression(SqlParser.ValueExpressionContext ctx) {
         ColumnDefinition.this.setValueExpression(ColumnDefinition.this.getSqlFactory().newValueExpression());
         ColumnDefinition.this.getValueExpression().parse(ctx);
         return ColumnDefinition.this;
      }

      public ColumnDefinition visitColumnConstraintDefinition(SqlParser.ColumnConstraintDefinitionContext ctx) {
         ColumnDefinition.this.setColumnConstraintDefinition(ColumnDefinition.this.getSqlFactory().newColumnConstraintDefinition());
         ColumnDefinition.this.getColumnConstraintDefinition().parse(ctx);
         return ColumnDefinition.this;
      }

      // $FF: synthetic method
      CdVisitor(Object x1) {
         this();
      }
   }
}
