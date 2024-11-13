package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.ddl.enums.ReferenceGeneration;
import ch.enterag.sqlparser.ddl.enums.ViewElementType;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;

public class ViewElement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(ViewElement.class.getName());
   private ViewElement.VeVisitor _visitor = new ViewElement.VeVisitor();
   private ViewElementType _type = null;
   private Identifier _idColumnName = new Identifier();
   private ReferenceGeneration _rg = null;
   private QualifiedId _qTableName = new QualifiedId();

   private ViewElement.VeVisitor getVisitor() {
      return this._visitor;
   }

   public ViewElementType getType() {
      return this._type;
   }

   public void setType(ViewElementType type) {
      this._type = type;
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

   public QualifiedId getTableName() {
      return this._qTableName;
   }

   private void setTableName(QualifiedId qTableName) {
      this._qTableName = qTableName;
   }

   public String format() {
      String sDefinition = null;
      switch(this.getType()) {
      case SELFREF_COLUMN_SPECIFICATION:
         sDefinition = K.REF.getKeyword() + " " + K.IS.getKeyword() + " " + this.getColumnName().format() + " " + this.getReferenceGeneration().getKeywords();
         break;
      case VIEW_COLUMN_OPTION:
         sDefinition = this.getColumnName().format() + " " + K.WITH.getKeyword() + " " + K.OPTIONS.getKeyword() + " " + K.SCOPE.getKeyword() + " " + this.getTableName().format();
      }

      return sDefinition;
   }

   public void parse(SqlParser.ViewElementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().viewElement());
   }

   public void initialize(ViewElementType type, QualifiedId qTableName, Identifier idColumnName, ReferenceGeneration rg) {
      _il.enter(new Object[]{type, qTableName, idColumnName});
      this.setType(type);
      this.setTableName(qTableName);
      this.setColumnName(idColumnName);
      this.setReferenceGeneration(rg);
      _il.exit();
   }

   public ViewElement(SqlFactory sf) {
      super(sf);
   }

   private class VeVisitor extends EnhancedSqlBaseVisitor<ViewElement> {
      private VeVisitor() {
      }

      public ViewElement visitViewElement(SqlParser.ViewElementContext ctx) {
         if (ctx.selfrefColumnSpecification() != null) {
            ViewElement.this.setType(ViewElementType.SELFREF_COLUMN_SPECIFICATION);
         } else {
            ViewElement.this.setType(ViewElementType.VIEW_COLUMN_OPTION);
         }

         return (ViewElement)this.visitChildren(ctx);
      }

      public ViewElement visitTableName(SqlParser.TableNameContext ctx) {
         this.setTableName(ctx, ViewElement.this.getTableName());
         return ViewElement.this;
      }

      public ViewElement visitColumnName(SqlParser.ColumnNameContext ctx) {
         this.setColumnName(ctx, ViewElement.this.getColumnName());
         return ViewElement.this;
      }

      public ViewElement visitReferenceGeneration(SqlParser.ReferenceGenerationContext ctx) {
         ViewElement.this.setReferenceGeneration(this.getReferenceGeneration(ctx));
         return ViewElement.this;
      }

      // $FF: synthetic method
      VeVisitor(Object x1) {
         this();
      }
   }
}
