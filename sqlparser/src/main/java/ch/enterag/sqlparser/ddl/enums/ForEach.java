package ch.enterag.sqlparser.ddl.enums;

import ch.enterag.sqlparser.K;

public enum ForEach {
   FOR_EACH_ROW(K.FOR.getKeyword() + " " + K.EACH.getKeyword() + " " + K.ROW.getKeyword()),
   FOR_EACH_STATEMENT(K.FOR.getKeyword() + " " + K.EACH.getKeyword() + " " + K.STATEMENT.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private ForEach(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
