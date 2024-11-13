package ch.enterag.sqlparser.datatype;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.ddl.enums.ReferenceScopeCheck;
import ch.enterag.sqlparser.ddl.enums.ReferentialAction;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.utils.logging.IndentLogger;

public class FieldDefinition extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(FieldDefinition.class.getName());
   protected FieldDefinition.FdVisitor _visitor = new FieldDefinition.FdVisitor();
   private Identifier _idFieldName = new Identifier();
   private DataType _dt = null;
   private ReferenceScopeCheck _rc = null;
   private ReferentialAction _raDelete = null;

   protected FieldDefinition.FdVisitor getVisitor() {
      return this._visitor;
   }

   public Identifier getFieldName() {
      return this._idFieldName;
   }

   private void setFieldName(Identifier idFieldName) {
      this._idFieldName = idFieldName;
   }

   public DataType getDataType() {
      return this._dt;
   }

   public void setDataType(DataType dt) {
      this._dt = dt;
   }

   public ReferenceScopeCheck getReferenceScopeCheck() {
      return this._rc;
   }

   public void setReferenceScopeCheck(ReferenceScopeCheck rc) {
      this._rc = rc;
   }

   public ReferentialAction getDeleteAction() {
      return this._raDelete;
   }

   public void setDeleteAction(ReferentialAction raDelete) {
      this._raDelete = raDelete;
   }

   public String format() {
      String sDefinition = this.getFieldName().format() + " " + this.getDataType().format();
      if (this.getReferenceScopeCheck() != null) {
         sDefinition = sDefinition + " " + this.getReferenceScopeCheck().getKeywords();
      }

      if (this.getDeleteAction() != null) {
         sDefinition = sDefinition + " " + K.ON + " " + K.DELETE + " " + this.getDeleteAction().getKeywords();
      }

      return sDefinition;
   }

   public void parse(SqlParser.FieldDefinitionContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().fieldDefinition());
   }

   public void initialize(Identifier idFieldName, DataType dt, ReferenceScopeCheck rc, ReferentialAction raDelete) {
      _il.enter(new Object[]{idFieldName, dt, rc, raDelete});
      this.setFieldName(idFieldName);
      this.setDataType(dt);
      this.setReferenceScopeCheck(rc);
      this.setDeleteAction(raDelete);
      _il.exit();
   }

   public FieldDefinition(SqlFactory sf) {
      super(sf);
   }

   protected class FdVisitor extends EnhancedSqlBaseVisitor<FieldDefinition> {
      public FieldDefinition visitFieldName(SqlParser.FieldNameContext ctx) {
         this.setIdentifier(ctx.IDENTIFIER(), FieldDefinition.this.getFieldName());
         return FieldDefinition.this;
      }

      public FieldDefinition visitDataType(SqlParser.DataTypeContext ctx) {
         FieldDefinition.this.setDataType(FieldDefinition.this.getSqlFactory().newDataType());
         FieldDefinition.this.getDataType().parse(ctx);
         return FieldDefinition.this;
      }

      public FieldDefinition visitReferenceScopeCheck(SqlParser.ReferenceScopeCheckContext ctx) {
         FieldDefinition.this.setReferenceScopeCheck(this.getReferenceScopeCheck(ctx));
         return FieldDefinition.this;
      }

      public FieldDefinition visitReferentialAction(SqlParser.ReferentialActionContext ctx) {
         FieldDefinition.this.setDeleteAction(this.getReferentialAction(ctx));
         return FieldDefinition.this;
      }
   }
}
