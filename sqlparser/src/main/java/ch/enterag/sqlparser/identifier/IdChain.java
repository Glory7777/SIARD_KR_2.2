package ch.enterag.sqlparser.identifier;

import ch.enterag.sqlparser.SqlLiterals;

public class IdChain extends IdList {
   private static final String sDOT = ".";

   public String format() {
      StringBuilder sbFormatted = new StringBuilder();

      for(int i = 0; i < this.size(); ++i) {
         if (i > 0) {
            sbFormatted.append(".");
         }

         sbFormatted.append(SqlLiterals.formatId(this.get(i)));
      }

      return sbFormatted.toString();
   }

   public String quote() {
      StringBuilder sbFormatted = new StringBuilder();

      for(int i = 0; i < this.size(); ++i) {
         if (i > 0) {
            sbFormatted.append(".");
         }

         sbFormatted.append(SqlLiterals.quoteId(this.get(i)));
      }

      return sbFormatted.toString();
   }
}
