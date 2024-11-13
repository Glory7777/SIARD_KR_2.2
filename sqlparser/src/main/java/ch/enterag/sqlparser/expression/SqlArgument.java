package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;

public class SqlArgument extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(SqlArgument.class.getName());
   private SqlArgument.SaVisitor _visitor = new SqlArgument.SaVisitor();
   private ValueExpression _ve = null;
   private QualifiedId _qiUdtName = new QualifiedId();
   private TargetSpecification _ts = null;
   private boolean _bDefaultArgument = false;
   private boolean _bNullArgument = false;
   private boolean _bEmptyArrayArgument = false;
   private boolean _bEmptyMultisetArgument = false;

   private SqlArgument.SaVisitor getVisitor() {
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

   public void setUdtName(QualifiedId qiUdtName) {
      this._qiUdtName = qiUdtName;
   }

   public TargetSpecification getTargetSpecification() {
      return this._ts;
   }

   public void setTargetSpecification(TargetSpecification ts) {
      this._ts = ts;
   }

   public boolean isDefaultArgument() {
      return this._bDefaultArgument;
   }

   public void setDefaultArgument(boolean bDefaultArgument) {
      this._bDefaultArgument = bDefaultArgument;
   }

   public boolean isNullArgument() {
      return this._bNullArgument;
   }

   public void setNullArgument(boolean bNullArgument) {
      this._bNullArgument = bNullArgument;
   }

   public boolean isEmptyArrayArgument() {
      return this._bEmptyArrayArgument;
   }

   public void setEmptyArrayArgument(boolean bEmptyArrayArgument) {
      this._bEmptyArrayArgument = bEmptyArrayArgument;
   }

   public boolean isEmptyMultisetArgument() {
      return this._bEmptyMultisetArgument;
   }

   public void setEmptyMultisetArgument(boolean bEmptyMultisetArgument) {
      this._bEmptyMultisetArgument = bEmptyMultisetArgument;
   }

   public String format() {
      String s = null;
      if (this.isDefaultArgument()) {
         s = K.DEFAULT.getKeyword();
      } else if (this.isNullArgument()) {
         s = K.NULL.getKeyword();
      } else if (this.isEmptyArrayArgument()) {
         s = K.ARRAY.getKeyword() + "[" + "]";
      } else if (this.isEmptyMultisetArgument()) {
         s = K.MULTISET.getKeyword() + "[" + "]";
      } else if (this.getUdtName().isSet() && this.getValueExpression() != null) {
         s = this.getValueExpression().format() + " " + K.AS.getKeyword() + " " + this.getUdtName().format();
      } else if (this.getTargetSpecification() != null) {
         s = this.getTargetSpecification().format();
      } else if (this.getValueExpression() != null) {
         s = this.getValueExpression().format();
      }

      return s;
   }

   public void parse(SqlParser.SqlArgumentContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().sqlArgument());
   }

   public void initialize(ValueExpression ve, QualifiedId qiUdtName, TargetSpecification ts) {
      _il.enter(new Object[]{ve, qiUdtName, ts});
      this.setValueExpression(ve);
      this.setUdtName(qiUdtName);
      this.setTargetSpecification(ts);
      _il.exit();
   }

   public SqlArgument(SqlFactory sf) {
      super(sf);
   }

   private class SaVisitor extends EnhancedSqlBaseVisitor<SqlArgument> {
      private SaVisitor() {
      }

      public SqlArgument visitSqlArgument(SqlParser.SqlArgumentContext ctx) {
         if (ctx.DEFAULT() != null) {
            SqlArgument.this.setDefaultArgument(true);
         } else if (ctx.NULL() != null) {
            SqlArgument.this.setNullArgument(true);
         } else if (ctx.ARRAY() != null) {
            SqlArgument.this.setEmptyArrayArgument(true);
         } else if (ctx.MULTISET() != null) {
            SqlArgument.this.setEmptyMultisetArgument(true);
         } else {
            this.visitChildren(ctx);
         }

         return SqlArgument.this;
      }

      public SqlArgument visitValueExpression(SqlParser.ValueExpressionContext ctx) {
         SqlArgument.this.setValueExpression(SqlArgument.this.getSqlFactory().newValueExpression());
         SqlArgument.this.getValueExpression().parse(ctx);
         return SqlArgument.this;
      }

      public SqlArgument visitGeneralizedExpression(SqlParser.GeneralizedExpressionContext ctx) {
         SqlArgument.this.setValueExpression(SqlArgument.this.getSqlFactory().newValueExpression());
         SqlArgument.this.getValueExpression().parse(ctx.valueExpression());
         this.setUdtName(ctx.udtName(), SqlArgument.this.getUdtName());
         return SqlArgument.this;
      }

      public SqlArgument visitTargetSpecification(SqlParser.TargetSpecificationContext ctx) {
         SqlArgument.this.setTargetSpecification(SqlArgument.this.getSqlFactory().newTargetSpecification());
         SqlArgument.this.getTargetSpecification().parse(ctx);
         return SqlArgument.this;
      }

      // $FF: synthetic method
      SaVisitor(Object x1) {
         this();
      }
   }
}
