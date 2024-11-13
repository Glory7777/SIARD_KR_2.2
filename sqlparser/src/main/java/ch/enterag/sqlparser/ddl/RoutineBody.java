package ch.enterag.sqlparser.ddl;

import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.SqlLiterals;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.utils.logging.IndentLogger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.tree.TerminalNode;

public class RoutineBody extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(RoutineBody.class.getName());
   private RoutineBody.RbVisitor _visitor = new RoutineBody.RbVisitor();
   private List<String> _listTokens = new ArrayList();

   private RoutineBody.RbVisitor getVisitor() {
      return this._visitor;
   }

   public List<String> getTokens() {
      return this._listTokens;
   }

   private void setTokens(List<String> listTokens) {
      this._listTokens = listTokens;
   }

   public String format() {
      String s = "";

      for(int i = 0; i < this.getTokens().size(); ++i) {
         if (i > 0) {
            s = s + " ";
         }

         s = s + (String)this.getTokens().get(i);
      }

      return s;
   }

   public void parse(SqlParser.RoutineBodyContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().routineBody());
   }

   public void initialize(List<String> listTokens) {
      _il.enter(new Object[0]);
      this.setTokens(listTokens);
      _il.exit();
   }

   public RoutineBody(SqlFactory sf) {
      super(sf);
   }

   private class RbVisitor extends EnhancedSqlBaseVisitor<RoutineBody> {
      private RbVisitor() {
      }

      public RoutineBody visitTerminal(TerminalNode tn) {
         int iType = tn.getSymbol().getType();
         String sLiteral = SqlParser.VOCABULARY.getLiteralName(iType);
         String sDisplay = SqlParser.VOCABULARY.getDisplayName(iType);
         if (sLiteral != null) {
            sDisplay = sLiteral.substring(1, sLiteral.length() - 1);
         } else if (tn.getSymbol().getType() == 344) {
            try {
               sDisplay = SqlLiterals.formatId(SqlLiterals.parseId(tn.getText()));
            } catch (ParseException var6) {
               throw new IllegalArgumentException("Cannot parse identifier: " + var6.getMessage() + "!");
            }
         } else if (sDisplay.endsWith("_LITERAL") || sDisplay.equals("UNSIGNED_INTEGER")) {
            sDisplay = tn.getText();
         }

         RoutineBody.this.getTokens().add(sDisplay);
         return RoutineBody.this;
      }

      // $FF: synthetic method
      RbVisitor(Object x1) {
         this();
      }
   }
}
