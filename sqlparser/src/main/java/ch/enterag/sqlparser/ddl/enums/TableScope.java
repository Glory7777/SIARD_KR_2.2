package ch.enterag.sqlparser.ddl.enums;

import ch.enterag.sqlparser.K;

public enum TableScope {
   GLOBAL(K.GLOBAL.getKeyword() + " " + K.TEMPORARY.getKeyword()),
   LOCAL(K.LOCAL.getKeyword() + " " + K.TEMPORARY.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private TableScope(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
