package ch.enterag.sqlparser.identifier;

import ch.enterag.sqlparser.SqlLiterals;
import java.text.ParseException;

public class ColonId extends Identifier {
   private static final String sCOLON = ":";

   public ColonId() {
   }

   public ColonId(String sIdentifier) {
      super(sIdentifier);
   }

   public void parse(String sDelimited) throws ParseException {
      if (sDelimited.startsWith(":")) {
         this.set(SqlLiterals.parseId(sDelimited.substring(1).trim()));
      } else {
         throw new ParseException("Colon expected!", 0);
      }
   }

   public String format() {
      return ":" + super.format();
   }

   public String quote() {
      return ":" + super.quote();
   }
}
