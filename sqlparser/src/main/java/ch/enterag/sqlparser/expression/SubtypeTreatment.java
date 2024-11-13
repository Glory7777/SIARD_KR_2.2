package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;

public class SubtypeTreatment extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(SubtypeTreatment.class.getName());
   private SubtypeTreatment.StVisitor _visitor = new SubtypeTreatment.StVisitor();
   private ValueExpression _ve = null;
   private QualifiedId _qiUdtName = new QualifiedId();
   private QualifiedId _qiScopeTable = new QualifiedId();

   private SubtypeTreatment.StVisitor getVisitor() {
      return this._visitor;
   }

   public ValueExpression getValueExpression() {
      return this._ve;
   }

   public void setValueExpression(ValueExpression ve) {
      this._ve = ve;
   }

   public QualifiedId getUdtName() {
      return this._qiUdtName;
   }

   private void setUdtName(QualifiedId qiUdtName) {
      this._qiUdtName = qiUdtName;
   }

   public QualifiedId getScopeTable() {
      return this._qiScopeTable;
   }

   private void setScopeTable(QualifiedId qiScopeTable) {
      this._qiScopeTable = qiScopeTable;
   }

   public String format() {
      String s = K.TREAT.getKeyword() + "(" + this.getValueExpression().format() + " " + K.AS.getKeyword();
      if (this.getScopeTable().isSet()) {
         s = s + "(" + this.getUdtName().format() + ")" + " " + K.SCOPE.getKeyword() + " " + this.getScopeTable().format();
      } else {
         s = s + " " + this.getUdtName().format();
      }

      s = s + ")";
      return s;
   }

   public void parse(SqlParser.SubtypeTreatmentContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().subtypeTreatment());
   }

   public void initialize(ValueExpression ve, QualifiedId qiUdtName, QualifiedId qiScopeTable) {
      _il.enter(new Object[]{ve, qiUdtName, qiScopeTable});
      this.setValueExpression(ve);
      this.setUdtName(qiUdtName);
      this.setScopeTable(qiScopeTable);
      _il.exit();
   }

   public SubtypeTreatment(SqlFactory sf) {
      super(sf);
   }

   private class StVisitor extends EnhancedSqlBaseVisitor<SubtypeTreatment> {
      private StVisitor() {
      }

      public SubtypeTreatment visitSubtypeTreatment(SqlParser.SubtypeTreatmentContext ctx) {
         SubtypeTreatment.this.setValueExpression(SubtypeTreatment.this.getSqlFactory().newValueExpression());
         SubtypeTreatment.this.getValueExpression().parse(ctx.valueExpression());
         if (ctx.udtName() != null) {
            this.setUdtName(ctx.udtName(), SubtypeTreatment.this.getUdtName());
         } else if (ctx.referenceType() != null) {
            this.setUdtName(ctx.referenceType().udtName(), SubtypeTreatment.this.getUdtName());
            this.setTableName(ctx.referenceType().scopeDefinition().tableName(), SubtypeTreatment.this.getScopeTable());
         }

         return SubtypeTreatment.this;
      }

      // $FF: synthetic method
      StVisitor(Object x1) {
         this();
      }
   }
}
