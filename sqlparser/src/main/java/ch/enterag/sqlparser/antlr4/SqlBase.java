package ch.enterag.sqlparser.antlr4;

import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.generated.SqlBaseVisitor;
import ch.enterag.sqlparser.generated.SqlLexer;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public abstract class SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(SqlBase.class.getName());
   public static final String sIDENTIFIER = "IDENTIFIER";
   public static final String sNEW_LINE = "\r\n";
   public static final String sINDENT = "  ";
   public static final String sSP = " ";
   public static final String sPERIOD = ".";
   public static final String sCOMMA = ",";
   public static final String sLEFT_PAREN = "(";
   public static final String sRIGHT_PAREN = ")";
   public static final String sLEFT_BRACKET = "[";
   public static final String sRIGHT_BRACKET = "]";
   public static final String sCONCATENATION_OPERATOR = "||";
   public static final String sQUESTION_MARK = "?";
   public static final String sDOUBLE_COLON = "::";
   public static final String sDEREFERENCE_OPERATOR = "->";
   public static final String sASTERISK = "*";
   public static final String sEQUALS = "=";
   private static boolean _bDebug = false;
   private SqlFactory _sf = null;
   private SqlParser _parser = null;
   private ParserRuleContext _ctx = null;

   public static boolean isDebug() {
      return _bDebug;
   }

   public static void setDebug(boolean bDebug) {
      _bDebug = bDebug;
   }

   protected SqlFactory getSqlFactory() {
      return this._sf;
   }

   private void setSqlFactory(SqlFactory sf) {
      this._sf = sf;
   }

   protected SqlParser getParser() {
      return this._parser;
   }

   public void setParser(SqlParser parser) {
      this._parser = parser;
   }

   protected ParserRuleContext getContext() {
      return this._ctx;
   }

   public void setContext(ParserRuleContext ctx) {
      this._ctx = ctx;
   }

   public void throwParseCancellationException(String sMessage) {
      throw new ParseCancellationException(sMessage);
   }

   private static void listTokens(CommonTokenStream cts) {
      for(int i = 0; i < cts.getNumberOfOnChannelTokens(); ++i) {
         Token token = cts.get(i);
         System.out.println(i + ": " + token.getText() + " " + SqlParser.VOCABULARY.getSymbolicName(token.getType()));
      }

   }

   public void listTokens() {
      CommonTokenStream cts = (CommonTokenStream)this.getParser().getTokenStream();
      listTokens(cts);
   }

   public void listTree(ParserRuleContext ctx) {
      SqlBase.BaseVisitor bv = new SqlBase.BaseVisitor();
      bv.visit(ctx);
   }

   public static SqlParser newSqlParser(String sSql) {

      SqlParser sp = null;

      try {
         ByteArrayInputStream bis = new ByteArrayInputStream(sSql.getBytes());
         ANTLRInputStream ais = new ANTLRInputStream(bis);
         SqlLexer sqlLexer = new SqlLexer(ais);
         CommonTokenStream cts = new CommonTokenStream(sqlLexer);
         if (isDebug()) {
            listTokens(cts);
         }

         sp = new SqlParser(cts);
         sp.getErrorListeners().clear();
         sp.addErrorListener(ErrorListener.getInstance());
         ((ParserATNSimulator)sp.getInterpreter()).setPredictionMode(PredictionMode.SLL);
      } catch (IOException var6) {
         _il.exception(var6);
      }

      return sp;
   }

   public static SqlParser newSqlParser2(String sSql) {
      SqlParser sp = null;

      try {
         ByteArrayInputStream bis = new ByteArrayInputStream(sSql.getBytes());
         ANTLRInputStream ais = new ANTLRInputStream(bis);
         SqlLexer sqlLexer = new SqlLexer(ais);
         CommonTokenStream cts = new CommonTokenStream(sqlLexer);
         if (isDebug()) {
            listTokens(cts);
         }

         sp = new SqlParser(cts);
         sp.getErrorListeners().clear();
         sp.addErrorListener(ErrorListener.getInstance());
         ((ParserATNSimulator)sp.getInterpreter()).setPredictionMode(PredictionMode.LL_EXACT_AMBIG_DETECTION);
      } catch (IOException var6) {
         _il.exception(var6);
      }

      return sp;
   }

   public SqlBase(SqlFactory sf) {
      this.setSqlFactory(sf);
   }

   public abstract String format();

   public abstract void parse(String var1);

   private class BaseVisitor extends SqlBaseVisitor<SqlBase> {
      private static final String sINDENT = "  ";
      private String _sIndent;

      private BaseVisitor() {
         this._sIndent = "";
      }

      public SqlBase visitChildren(RuleNode ctx) {
         this._sIndent = this._sIndent + "  ";
         SqlBase sbReturn = null;

         for(int i = 0; i < ctx.getChildCount(); ++i) {
            ParseTree pt = ctx.getChild(i);
            if (pt instanceof RuleNode) {
               RuleNode ctxChild = (RuleNode)pt;
               sbReturn = this.visitChildren(ctxChild);
            } else if (pt instanceof TerminalNode) {
               TerminalNode tn = (TerminalNode)pt;
               sbReturn = this.visitTerminal(tn);
            } else if (pt instanceof ErrorNode) {
               ErrorNode en = (ErrorNode)pt;
               sbReturn = this.visitErrorNode(en);
            }
         }

         this._sIndent = this._sIndent.substring(0, this._sIndent.length() - "  ".length());
         return sbReturn;
      }

      public SqlBase visitErrorNode(ErrorNode node) {
         System.out.println("ERROR: " + node.toString());
         return SqlBase.this;
      }

      public SqlBase visitTerminal(TerminalNode node) {
         Token token = node.getSymbol();
         int iType = token.getType();
         String sName = SqlParser.VOCABULARY.getSymbolicName(iType);
         if (sName == null) {
            sName = SqlParser.VOCABULARY.getLiteralName(iType);
         }

         if (sName == null) {
            sName = SqlParser.VOCABULARY.getDisplayName(iType);
         }

         System.out.println(this._sIndent + token.getText() + " " + sName);
         return SqlBase.this;
      }

      // $FF: synthetic method
      BaseVisitor(Object x1) {
         this();
      }
   }
}
