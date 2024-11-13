package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.ddl.enums.ParameterMode;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.Identifier;
import ch.enterag.utils.logging.IndentLogger;

public class SqlParameterDeclaration extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(SqlParameterDeclaration.class.getName());
   private SqlParameterDeclaration.SpdVisitor _visitor = new SqlParameterDeclaration.SpdVisitor();
   private ParameterMode _pm = null;
   private Identifier _idParameterName = new Identifier();
   private DataType _dt = null;
   private boolean _bResult = false;

   private SqlParameterDeclaration.SpdVisitor getVisitor() {
      return this._visitor;
   }

   public ParameterMode getParameterMode() {
      return this._pm;
   }

   public void setParameterMode(ParameterMode pm) {
      this._pm = pm;
   }

   public Identifier getParameterName() {
      return this._idParameterName;
   }

   private void setParameterName(Identifier idParameterName) {
      this._idParameterName = idParameterName;
   }

   public DataType getDataType() {
      return this._dt;
   }

   public void setDataType(DataType dt) {
      this._dt = dt;
   }

   public boolean getResult() {
      return this._bResult;
   }

   public void setResult(boolean bResult) {
      this._bResult = bResult;
   }

   public String format() {
      String sDeclaration = "";
      if (this.getParameterMode() != null) {
         sDeclaration = this.getParameterMode().getKeywords() + " ";
      }

      if (this.getParameterName().isSet()) {
         sDeclaration = sDeclaration + this.getParameterName().format();
      }

      if (this.getDataType() != null) {
         sDeclaration = sDeclaration + " " + this.getDataType().format();
      } else {
         sDeclaration = sDeclaration + " " + K.AS.getKeyword() + " " + K.LOCATOR.getKeyword();
      }

      if (this.getResult()) {
         sDeclaration = sDeclaration + " " + K.RESULT.getKeyword();
      }

      return sDeclaration;
   }

   public void parse(SqlParser.SqlParameterDeclarationContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().sqlParameterDeclaration());
   }

   public void initialize(ParameterMode pm, Identifier idParameterName) {
      _il.enter(new Object[]{pm, idParameterName});
      this.setParameterMode(pm);
      this.setParameterName(idParameterName);
      _il.exit();
   }

   public SqlParameterDeclaration(SqlFactory sf) {
      super(sf);
   }

   private class SpdVisitor extends EnhancedSqlBaseVisitor<SqlParameterDeclaration> {
      private SpdVisitor() {
      }

      public SqlParameterDeclaration visitParameterMode(SqlParser.ParameterModeContext ctx) {
         SqlParameterDeclaration.this.setParameterMode(this.getParameterMode(ctx));
         return SqlParameterDeclaration.this;
      }

      public SqlParameterDeclaration visitParameterName(SqlParser.ParameterNameContext ctx) {
         this.setParameterName(ctx, SqlParameterDeclaration.this.getParameterName());
         return SqlParameterDeclaration.this;
      }

      public SqlParameterDeclaration visitParameterType(SqlParser.ParameterTypeContext ctx) {
         if (ctx.LOCATOR() != null) {
            SqlParameterDeclaration.this.setDataType((DataType)null);
         } else {
            SqlParameterDeclaration.this.setDataType(SqlParameterDeclaration.this.getSqlFactory().newDataType());
            SqlParameterDeclaration.this.getDataType().parse(ctx.dataType());
         }

         return SqlParameterDeclaration.this;
      }

      public SqlParameterDeclaration visitSqlParameterDeclaration(SqlParser.SqlParameterDeclarationContext ctx) {
         if (ctx.RESULT() != null) {
            SqlParameterDeclaration.this.setResult(true);
         } else {
            SqlParameterDeclaration.this.setResult(false);
         }

         return (SqlParameterDeclaration)this.visitChildren(ctx);
      }

      // $FF: synthetic method
      SpdVisitor(Object x1) {
         this();
      }
   }
}
