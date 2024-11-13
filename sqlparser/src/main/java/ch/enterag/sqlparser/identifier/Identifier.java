package ch.enterag.sqlparser.identifier;

import ch.enterag.sqlparser.SqlLiterals;
import java.text.ParseException;

public class Identifier {
   private String _sId = null;

   public String get() {
      return this._sId;
   }

   public void set(String sId) {
      this._sId = sId;
   }

   public String quote() {
      return SqlLiterals.quoteId(this.get());
   }

   public String format() {
      return SqlLiterals.formatId(this.get());
   }

   public void parse(String sDelimited) throws ParseException {
      this.set(SqlLiterals.parseId(sDelimited));
   }

   public boolean isSet() {
      return this._sId != null;
   }

   public Identifier() {
   }

   public Identifier(String sIdentifier) {
      this.set(sIdentifier);
   }
}
