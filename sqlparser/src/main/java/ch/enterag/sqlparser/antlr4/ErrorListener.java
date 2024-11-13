package ch.enterag.sqlparser.antlr4;

import ch.enterag.utils.logging.IndentLogger;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

public class ErrorListener implements ANTLRErrorListener {
   private static ErrorListener _en = null;
   private static IndentLogger _il = IndentLogger.getIndentLogger(ErrorListener.class.getName());
   private boolean bExceptionSuppressed = true;

   private boolean isExceptionSuppressed() {
      return this.bExceptionSuppressed;
   }

   public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {
      _il.enter(new Object[]{recognizer, dfa, String.valueOf(startIndex), String.valueOf(stopIndex), String.valueOf(exact), ambigAlts, configs});
      String sInput = "";

      for(int i = startIndex; i <= stopIndex; ++i) {
         sInput = sInput + recognizer.getTokenStream().get(i).getText();
      }

      if (ambigAlts == null) {
         ambigAlts = configs.getAlts();
      }

      _il.exit();
      if (!this.isExceptionSuppressed()) {
         throw new IllegalArgumentException("SQL ambiguity for input \"" + sInput + "\" " + ambigAlts.toString());
      }
   }

   public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, ATNConfigSet configs) {
      _il.enter(new Object[]{recognizer, dfa, String.valueOf(startIndex), String.valueOf(stopIndex), conflictingAlts, configs});
      _il.exit();
      if (!this.isExceptionSuppressed()) {
         throw new IllegalArgumentException("SQL attempting full context for substring " + String.valueOf(startIndex) + " to " + stopIndex);
      }
   }

   public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, ATNConfigSet configs) {
      _il.enter(new Object[]{recognizer, dfa, String.valueOf(startIndex), String.valueOf(stopIndex), String.valueOf(prediction), configs});
      _il.exit();
      if (!this.isExceptionSuppressed()) {
         throw new IllegalArgumentException("SQL context sensitivity for substring " + String.valueOf(startIndex) + " to " + stopIndex);
      }
   }

   public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
      _il.enter(new Object[]{recognizer, offendingSymbol, String.valueOf(line), String.valueOf(charPositionInLine), msg, e});
      List<String> listRuleStack = ((Parser)recognizer).getRuleInvocationStack();
      Collections.reverse(listRuleStack);
      _il.exit();
      throw new IllegalArgumentException("SQL parsing error at position " + String.valueOf(line) + "/" + charPositionInLine + " at " + offendingSymbol + ": " + msg + "\r\nRule stack: " + listRuleStack);
   }

   private ErrorListener() {
   }

   public static ErrorListener getInstance() {
      if (_en == null) {
         _en = new ErrorListener();
      }

      return _en;
   }
}
