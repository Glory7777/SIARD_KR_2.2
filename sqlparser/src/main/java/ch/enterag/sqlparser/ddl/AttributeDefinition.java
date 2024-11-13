package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.ddl.enums.ReferenceScopeCheck;
import ch.enterag.sqlparser.ddl.enums.ReferentialAction;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.utils.logging.IndentLogger;

public class AttributeDefinition extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(AttributeDefinition.class.getName());
   private AttributeDefinition.AdVisitor _visitor = new AttributeDefinition.AdVisitor();
   private Identifier _idAttributeName = new Identifier();
   private DataType _dt = null;
   private ReferenceScopeCheck _rsc = null;
   private ReferentialAction _raDelete = null;
   private String _sDefaultOption = null;

   private AttributeDefinition.AdVisitor getVisitor() {
      return this._visitor;
   }

   public Identifier getAttributeName() {
      return this._idAttributeName;
   }

   private void setAttributeName(Identifier idAttributeName) {
      this._idAttributeName = idAttributeName;
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

   public String format() {
      String sDefinition = this.getAttributeName().format() + " " + this.getDataType().format();
      if (this.getReferenceScopeCheck() != null) {
         sDefinition = sDefinition + " " + this.getReferenceScopeCheck().getKeywords();
         if (this.getDeleteAction() != null) {
            sDefinition = sDefinition + " " + this.getDeleteAction().getKeywords();
         }
      }

      if (this.getDefaultOption() != null) {
         sDefinition = sDefinition + " " + K.DEFAULT.getKeyword() + " " + this.getDefaultOption();
      }

      return sDefinition;
   }

   public void parse(SqlParser.AttributeDefinitionContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().attributeDefinition());
   }

   public void initialize(Identifier idAttributeName, DataType dt, ReferenceScopeCheck rsc, ReferentialAction raDelete, String sDefaultOption) {
      _il.enter(new Object[]{idAttributeName, dt, rsc, sDefaultOption});
      this.setAttributeName(idAttributeName);
      this.setDataType(dt);
      this.setReferenceScopeCheck(rsc);
      this.setDeleteAction(raDelete);
      this.setDefaultOption(sDefaultOption);
      _il.exit();
   }

   public void initNameType(Identifier idAttributeName, DataType dt) {
      _il.enter(new Object[]{idAttributeName, dt});
      this.setAttributeName(idAttributeName);
      this.setDataType(dt);
      _il.exit();
   }

   public AttributeDefinition(SqlFactory sf) {
      super(sf);
   }

   private class AdVisitor extends EnhancedSqlBaseVisitor<AttributeDefinition> {
      private AdVisitor() {
      }

      public AttributeDefinition visitReferenceScopeCheck(SqlParser.ReferenceScopeCheckContext ctx) {
         AttributeDefinition.this.setReferenceScopeCheck(this.getReferenceScopeCheck(ctx));
         return AttributeDefinition.this;
      }

      public AttributeDefinition visitAttributeName(SqlParser.AttributeNameContext ctx) {
         this.setAttributeName(ctx, AttributeDefinition.this.getAttributeName());
         return AttributeDefinition.this;
      }

      public AttributeDefinition visitDataType(SqlParser.DataTypeContext ctx) {
         AttributeDefinition.this.setDataType(AttributeDefinition.this.getSqlFactory().newDataType());
         AttributeDefinition.this.getDataType().parse(ctx);
         return AttributeDefinition.this;
      }

      public AttributeDefinition visitDeleteAction(SqlParser.DeleteActionContext ctx) {
         AttributeDefinition.this.setDeleteAction(this.getReferentialAction(ctx.referentialAction()));
         return AttributeDefinition.this;
      }

      public AttributeDefinition visitDefaultOption(SqlParser.DefaultOptionContext ctx) {
         String sDefaultValue = ctx.getText();
         if (!sDefaultValue.startsWith("'")) {
            sDefaultValue = sDefaultValue.toUpperCase();
         }

         AttributeDefinition.this.setDefaultOption(sDefaultValue);
         return AttributeDefinition.this;
      }

      // $FF: synthetic method
      AdVisitor(Object x1) {
         this();
      }
   }
}
