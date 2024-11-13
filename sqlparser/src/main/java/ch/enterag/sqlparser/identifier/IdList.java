package ch.enterag.sqlparser.identifier;

import ch.enterag.sqlparser.SqlLiterals;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class IdList {
   private List<String> _listId = new ArrayList();

   protected int size() {
      return this._listId.size();
   }

   protected String get(int i) {
      return (String)this._listId.get(i);
   }

   public List<String> get() {
      return this._listId;
   }

   public void parseAdd(String sDelimited) throws ParseException {
      this._listId.add(SqlLiterals.parseId(sDelimited));
   }

   public String quote() {
      return SqlLiterals.quoteIdentifierCommaList(this._listId);
   }

   public String format() {
      return SqlLiterals.formatIdentifierCommaList(this._listId);
   }

   public boolean isSet() {
      return this._listId.size() > 0;
   }
}
