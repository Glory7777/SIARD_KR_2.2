package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.ddl.enums.DefaultsOption;
import ch.enterag.sqlparser.ddl.enums.IdentityOption;
import ch.enterag.sqlparser.ddl.enums.ReferenceGeneration;
import ch.enterag.sqlparser.ddl.enums.TableElementType;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;

public class TableElement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(TableElement.class.getName());
   private TableElement.TeVisitor _visitor = new TableElement.TeVisitor();
   private TableElementType _type = null;
   private ColumnDefinition _cd = null;
   private TableConstraintDefinition _tcd = null;
   private QualifiedId _qTableName = new QualifiedId();
   private IdentityOption _io = null;
   private DefaultsOption _do = null;
   private Identifier _idColumnName = new Identifier();
   private ReferenceGeneration _rg = null;
   private String _sDefaultOption = null;
   private ColumnConstraintDefinition _ccd = null;

   private TableElement.TeVisitor getVisitor() {
      return this._visitor;
   }

   public TableElementType getType() {
      return this._type;
   }

   public void setType(TableElementType type) {
      this._type = type;
   }

   public ColumnDefinition getColumnDefinition() {
      return this._cd;
   }

   public void setColumnDefinition(ColumnDefinition cd) {
      this._cd = cd;
   }

   public TableConstraintDefinition getTableConstraintDefinition() {
      return this._tcd;
   }

   public void setTableConstraintDefinition(TableConstraintDefinition tcd) {
      this._tcd = tcd;
   }

   public QualifiedId getTableName() {
      return this._qTableName;
   }

   private void setTableName(QualifiedId qTableName) {
      this._qTableName = qTableName;
   }

   public IdentityOption getIdentityOption() {
      return this._io;
   }

   public void setIdentityOption(IdentityOption io) {
      this._io = io;
   }

   public DefaultsOption getDefaultsOption() {
      return this._do;
   }

   public void setDefaultsOption(DefaultsOption defOption) {
      this._do = defOption;
   }

   public Identifier getColumnName() {
      return this._idColumnName;
   }

   private void setColumnName(Identifier idColumnName) {
      this._idColumnName = idColumnName;
   }

   public ReferenceGeneration getReferenceGeneration() {
      return this._rg;
   }

   public void setReferenceGeneration(ReferenceGeneration rg) {
      this._rg = rg;
   }

   public String getDefaultOption() {
      return this._sDefaultOption;
   }

   public void setDefaultOption(String sDefaultOption) {
      this._sDefaultOption = sDefaultOption;
   }

   public ColumnConstraintDefinition getColumnConstraintDefinition() {
      return this._ccd;
   }

   public void setColumnConstraintDefinition(ColumnConstraintDefinition ccd) {
      this._ccd = ccd;
   }

   public String format() {
      String sDefinition = null;
      switch(this.getType()) {
      case COLUMN_DEFINITION:
         sDefinition = this.getColumnDefinition().format();
         break;
      case TABLE_CONSTRAINT_DEFINITION:
         sDefinition = this.getTableConstraintDefinition().format();
         break;
      case LIKE_CLAUSE:
         sDefinition = K.LIKE.getKeyword() + " " + this.getTableName().format();
         if (this.getIdentityOption() != null) {
            sDefinition = sDefinition + " " + this.getIdentityOption().getKeywords();
         }

         if (this.getDefaultsOption() != null) {
            sDefinition = sDefinition + " " + this.getDefaultsOption().getKeywords();
         }
         break;
      case SELFREF_COLUMN_SPECIFICATION:
         sDefinition = K.REF.getKeyword() + " " + K.IS.getKeyword() + " " + this.getColumnName().format() + " " + this.getReferenceGeneration().getKeywords();
         break;
      case COLUMN_OPTIONS:
         sDefinition = this.getColumnName().format() + " " + K.WITH.getKeyword() + " " + K.OPTIONS.getKeyword() + " ";
         if (this.getTableName().isSet()) {
            sDefinition = sDefinition + K.SCOPE.getKeyword() + " " + this.getTableName().format();
         } else if (this.getDefaultOption() != null) {
            sDefinition = sDefinition + K.DEFAULT.getKeyword() + " " + this.getDefaultOption();
         } else {
            sDefinition = sDefinition + this.getColumnConstraintDefinition().format();
         }
      }

      return sDefinition;
   }

   public void parse(SqlParser.TableElementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().tableElement());
   }

   public void initialize(TableElementType type, ColumnDefinition cd, TableConstraintDefinition tcd, QualifiedId qTableName, IdentityOption io, DefaultsOption dop, Identifier idColumnName, ReferenceGeneration rg, String sDefaultOption, ColumnConstraintDefinition ccd) {
      _il.enter(new Object[]{type, cd, tcd, qTableName, io, dop, idColumnName, sDefaultOption, ccd});
      this.setType(type);
      this.setColumnDefinition(cd);
      this.setTableConstraintDefinition(tcd);
      this.setTableName(qTableName);
      this.setIdentityOption(io);
      this.setDefaultsOption(dop);
      this.setColumnName(idColumnName);
      this.setReferenceGeneration(rg);
      this.setDefaultOption(sDefaultOption);
      this.setColumnConstraintDefinition(ccd);
      _il.exit();
   }

   public void initColumnDefinition(ColumnDefinition cd) {
      _il.enter(new Object[0]);
      this.setType(TableElementType.COLUMN_DEFINITION);
      this.setColumnDefinition(cd);
      _il.exit();
   }

   public void initTableConstraintDefinition(TableConstraintDefinition tcd) {
      _il.enter(new Object[0]);
      this.setType(TableElementType.TABLE_CONSTRAINT_DEFINITION);
      this.setTableConstraintDefinition(tcd);
      _il.exit();
   }

   public TableElement(SqlFactory sf) {
      super(sf);
   }

   private class TeVisitor extends EnhancedSqlBaseVisitor<TableElement> {
      private TeVisitor() {
      }

      public TableElement visitTableElement(SqlParser.TableElementContext ctx) {
         if (ctx.columnDefinition() != null) {
            TableElement.this.setType(TableElementType.COLUMN_DEFINITION);
         } else if (ctx.tableConstraintDefinition() != null) {
            TableElement.this.setType(TableElementType.TABLE_CONSTRAINT_DEFINITION);
         } else if (ctx.likeClause() != null) {
            TableElement.this.setType(TableElementType.LIKE_CLAUSE);
         } else if (ctx.selfrefColumnSpecification() != null) {
            TableElement.this.setType(TableElementType.SELFREF_COLUMN_SPECIFICATION);
         } else if (ctx.columnOptions() != null) {
            TableElement.this.setType(TableElementType.COLUMN_OPTIONS);
         }

         return (TableElement)this.visitChildren(ctx);
      }

      public TableElement visitColumnDefinition(SqlParser.ColumnDefinitionContext ctx) {
         TableElement.this.setColumnDefinition(TableElement.this.getSqlFactory().newColumnDefinition());
         TableElement.this.getColumnDefinition().parse(ctx);
         return (TableElement)this.visitChildren(ctx);
      }

      public TableElement visitTableConstraintDefinition(SqlParser.TableConstraintDefinitionContext ctx) {
         TableElement.this.setTableConstraintDefinition(TableElement.this.getSqlFactory().newTableConstraintDefinition());
         TableElement.this.getTableConstraintDefinition().parse(ctx);
         return TableElement.this;
      }

      public TableElement visitTableName(SqlParser.TableNameContext ctx) {
         this.setTableName(ctx, TableElement.this.getTableName());
         return TableElement.this;
      }

      public TableElement visitIdentityOption(SqlParser.IdentityOptionContext ctx) {
         TableElement.this.setIdentityOption(this.getIdentityOption(ctx));
         return TableElement.this;
      }

      public TableElement visitDefaultsOption(SqlParser.DefaultsOptionContext ctx) {
         TableElement.this.setDefaultsOption(this.getDefaultsOption(ctx));
         return TableElement.this;
      }

      public TableElement visitColumnName(SqlParser.ColumnNameContext ctx) {
         this.setColumnName(ctx, TableElement.this.getColumnName());
         return TableElement.this;
      }

      public TableElement visitDefaultOption(SqlParser.DefaultOptionContext ctx) {
         String sDefaultValue = ctx.getText();
         if (!sDefaultValue.startsWith("'")) {
            sDefaultValue = sDefaultValue.toUpperCase();
         }

         TableElement.this.setDefaultOption(sDefaultValue);
         return TableElement.this;
      }

      public TableElement visitColumnConstraintDefinition(SqlParser.ColumnConstraintDefinitionContext ctx) {
         TableElement.this.setColumnConstraintDefinition(TableElement.this.getSqlFactory().newColumnConstraintDefinition());
         TableElement.this.getColumnConstraintDefinition().parse(ctx);
         return TableElement.this;
      }

      public TableElement visitReferenceGeneration(SqlParser.ReferenceGenerationContext ctx) {
         TableElement.this.setReferenceGeneration(this.getReferenceGeneration(ctx));
         return TableElement.this;
      }

      // $FF: synthetic method
      TeVisitor(Object x1) {
         this();
      }
   }
}
