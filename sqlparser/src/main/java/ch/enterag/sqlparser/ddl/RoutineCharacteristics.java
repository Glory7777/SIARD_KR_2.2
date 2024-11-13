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

public class RoutineCharacteristics extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(RoutineCharacteristics.class.getName());
   private RoutineCharacteristics.RcVisitor _visitor = new RoutineCharacteristics.RcVisitor();
   private LanguageName _ln = null;
   private ParameterStyle _ps = null;
   private Deterministic _deterministic = null;
   private DataAccess _da = null;
   private NullCallClause _ncc = null;

   private RoutineCharacteristics.RcVisitor getVisitor() {
      return this._visitor;
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
      String s = "";
      if (this.getLanguageName() != null) {
         s = s + "\r\n" + "  " + K.LANGUAGE.getKeyword() + " " + this.getLanguageName().getKeywords();
      }

      if (this.getParameterStyle() != null) {
         s = s + "\r\n" + "  " + K.PARAMETER.getKeyword() + " " + K.STYLE.getKeyword() + " " + this.getParameterStyle().getKeywords();
      }

      if (this.getDeterministic() != null) {
         s = s + "\r\n" + "  " + this.getDeterministic().getKeywords();
      }

      if (this.getDataAccess() != null) {
         s = s + "\r\n" + "  " + this.getDataAccess().getKeywords();
      }

      if (this.getNullCallClause() != null) {
         s = s + "\r\n" + "  " + this.getNullCallClause().getKeywords();
      }

      return s;
   }

   public void parse(SqlParser.RoutineCharacteristicsContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().routineCharacteristics());
   }

   public void initialize(LanguageName ln, ParameterStyle ps, Deterministic deterministic, DataAccess da, NullCallClause ncc) {
      _il.enter(new Object[]{ln, ps, deterministic, da, ncc});
      this.setLanguageName(ln);
      this.setParameterStyle(ps);
      this.setDeterministic(deterministic);
      this.setDataAccess(da);
      this.setNullCallClause(ncc);
      _il.exit();
   }

   public RoutineCharacteristics(SqlFactory sf) {
      super(sf);
   }

   private class RcVisitor extends EnhancedSqlBaseVisitor<RoutineCharacteristics> {
      private RcVisitor() {
      }

      public RoutineCharacteristics visitLanguageName(SqlParser.LanguageNameContext ctx) {
         RoutineCharacteristics.this.setLanguageName(this.getLanguageName(ctx));
         return RoutineCharacteristics.this;
      }

      public RoutineCharacteristics visitParameterStyle(SqlParser.ParameterStyleContext ctx) {
         RoutineCharacteristics.this.setParameterStyle(this.getParameterStyle(ctx));
         return RoutineCharacteristics.this;
      }

      public RoutineCharacteristics visitDeterministic(SqlParser.DeterministicContext ctx) {
         RoutineCharacteristics.this.setDeterministic(this.getDeterministic(ctx));
         return RoutineCharacteristics.this;
      }

      public RoutineCharacteristics visitDataAccess(SqlParser.DataAccessContext ctx) {
         RoutineCharacteristics.this.setDataAccess(this.getDataAccess(ctx));
         return RoutineCharacteristics.this;
      }

      public RoutineCharacteristics visitNullCallClause(SqlParser.NullCallClauseContext ctx) {
         RoutineCharacteristics.this.setNullCallClause(this.getNullCallClause(ctx));
         return RoutineCharacteristics.this;
      }

      // $FF: synthetic method
      RcVisitor(Object x1) {
         this();
      }
   }
}
