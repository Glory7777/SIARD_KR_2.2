package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.ddl.enums.DataAccess;
import ch.enterag.sqlparser.ddl.enums.Deterministic;
import ch.enterag.sqlparser.ddl.enums.LanguageName;
import ch.enterag.sqlparser.ddl.enums.NullCallClause;
import ch.enterag.sqlparser.ddl.enums.ParameterStyle;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;

public class MethodSpecification extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(MethodSpecification.class.getName());
   private MethodSpecification.MsVisitor _visitor = new MethodSpecification.MsVisitor();
   private boolean _bOverriding = false;
   private PartialMethodSpecification _pms = null;
   private boolean _bSelfAsResult = true;
   private boolean _bSelfAsLocator = false;
   private LanguageName _ln = null;
   private ParameterStyle _ps = null;
   private Deterministic _deterministic = null;
   private DataAccess _da = null;
   private NullCallClause _ncc = null;

   private MethodSpecification.MsVisitor getVisitor() {
      return this._visitor;
   }

   public boolean getOverriding() {
      return this._bOverriding;
   }

   public void setOverriding(boolean bOverriding) {
      this._bOverriding = bOverriding;
   }

   public PartialMethodSpecification getPartialMethodSpecification() {
      return this._pms;
   }

   public void setPartialMethodSpecification(PartialMethodSpecification pms) {
      this._pms = pms;
   }

   public boolean getSelfAsResult() {
      return this._bSelfAsResult;
   }

   public void setSelfAsResult(boolean bSelfAsResult) {
      this._bSelfAsResult = bSelfAsResult;
   }

   public boolean getSelfAsLocator() {
      return this._bSelfAsLocator;
   }

   public void setSelfAsLocator(boolean bSelfAsLocator) {
      this._bSelfAsLocator = bSelfAsLocator;
   }

   public LanguageName getLanguageName() {
      return this._ln;
   }

   public void setLanguageName(LanguageName ln) {
      this._ln = ln;
   }

   public ParameterStyle getParameterStyle() {
      return this._ps;
   }

   public void setParameterStyle(ParameterStyle ps) {
      this._ps = ps;
   }

   public Deterministic getDeterministic() {
      return this._deterministic;
   }

   public void setDeterministic(Deterministic deterministic) {
      this._deterministic = deterministic;
   }

   public DataAccess getDataAccess() {
      return this._da;
   }

   public void setDataAccess(DataAccess da) {
      this._da = da;
   }

   public NullCallClause getNullCallClause() {
      return this._ncc;
   }

   public void setNullCallClause(NullCallClause ncc) {
      this._ncc = ncc;
   }

   public String format() {
      String sSpecification = "";
      if (this.getOverriding()) {
         sSpecification = K.OVERRIDING.getKeyword() + " ";
      }

      sSpecification = sSpecification + this.getPartialMethodSpecification().format();
      if (!this.getOverriding()) {
         if (this.getSelfAsResult()) {
            sSpecification = sSpecification + " " + K.SELF.getKeyword() + " " + K.AS.getKeyword() + " " + K.RESULT.getKeyword();
         }

         if (this.getSelfAsLocator()) {
            sSpecification = sSpecification + " " + K.SELF.getKeyword() + " " + K.AS.getKeyword() + " " + K.LOCATOR.getKeyword();
         }

         if (this.getLanguageName() != null) {
            sSpecification = sSpecification + " " + K.LANGUAGE.getKeyword() + " " + this.getLanguageName().getKeywords();
         }

         if (this.getParameterStyle() != null) {
            sSpecification = sSpecification + " " + K.PARAMETER.getKeyword() + " " + K.STYLE.getKeyword() + " " + this.getParameterStyle().getKeywords();
         }

         if (this.getDeterministic() != null) {
            sSpecification = sSpecification + " " + this.getDeterministic().getKeywords();
         }

         if (this.getDataAccess() != null) {
            sSpecification = sSpecification + " " + this.getDataAccess().getKeywords();
         }

         if (this.getNullCallClause() != null) {
            sSpecification = sSpecification + " " + this.getNullCallClause().getKeywords();
         }
      }

      return sSpecification;
   }

   public void parse(SqlParser.MethodSpecificationContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().methodSpecification());
   }

   public void initialize(boolean bOverriding, PartialMethodSpecification pms, boolean bSelfAsResult, boolean bSelfAsLocator, LanguageName ln, ParameterStyle ps, Deterministic deterministic, DataAccess da, NullCallClause ncc) {
      _il.enter(new Object[]{String.valueOf(bOverriding), pms, String.valueOf(bSelfAsResult), String.valueOf(bSelfAsLocator), ln, ps, deterministic, da, ncc});
      this.setOverriding(bOverriding);
      this.setPartialMethodSpecification(pms);
      this.setSelfAsResult(bSelfAsResult);
      this.setSelfAsLocator(bSelfAsLocator);
      this.setLanguageName(ln);
      this.setParameterStyle(ps);
      this.setDeterministic(deterministic);
      this.setDataAccess(da);
      this.setNullCallClause(ncc);
      _il.exit();
   }

   public MethodSpecification(SqlFactory sf) {
      super(sf);
   }

   private class MsVisitor extends EnhancedSqlBaseVisitor<MethodSpecification> {
      private MsVisitor() {
      }

      public MethodSpecification visitOverridingMethodSpecification(SqlParser.OverridingMethodSpecificationContext ctx) {
         MethodSpecification.this.setOverriding(true);
         return (MethodSpecification)this.visitChildren(ctx);
      }

      public MethodSpecification visitOriginalMethodSpecification(SqlParser.OriginalMethodSpecificationContext ctx) {
         MethodSpecification.this.setOverriding(false);
         if (ctx.LOCATOR() != null) {
            MethodSpecification.this.setSelfAsLocator(true);
         } else {
            MethodSpecification.this.setSelfAsLocator(false);
         }

         if (ctx.RESULT() != null) {
            MethodSpecification.this.setSelfAsResult(true);
         } else {
            MethodSpecification.this.setSelfAsResult(false);
         }

         return (MethodSpecification)this.visitChildren(ctx);
      }

      public MethodSpecification visitPartialMethodSpecification(SqlParser.PartialMethodSpecificationContext ctx) {
         MethodSpecification.this.setPartialMethodSpecification(MethodSpecification.this.getSqlFactory().newPartialMethodSpecification());
         MethodSpecification.this.getPartialMethodSpecification().parse(ctx);
         return MethodSpecification.this;
      }

      public MethodSpecification visitLanguageName(SqlParser.LanguageNameContext ctx) {
         MethodSpecification.this.setLanguageName(this.getLanguageName(ctx));
         return MethodSpecification.this;
      }

      public MethodSpecification visitParameterStyle(SqlParser.ParameterStyleContext ctx) {
         MethodSpecification.this.setParameterStyle(this.getParameterStyle(ctx));
         return MethodSpecification.this;
      }

      public MethodSpecification visitDeterministic(SqlParser.DeterministicContext ctx) {
         MethodSpecification.this.setDeterministic(this.getDeterministic(ctx));
         return MethodSpecification.this;
      }

      public MethodSpecification visitDataAccess(SqlParser.DataAccessContext ctx) {
         MethodSpecification.this.setDataAccess(this.getDataAccess(ctx));
         return MethodSpecification.this;
      }

      public MethodSpecification visitNullCallClause(SqlParser.NullCallClauseContext ctx) {
         MethodSpecification.this.setNullCallClause(this.getNullCallClause(ctx));
         return MethodSpecification.this;
      }

      // $FF: synthetic method
      MsVisitor(Object x1) {
         this();
      }
   }
}
