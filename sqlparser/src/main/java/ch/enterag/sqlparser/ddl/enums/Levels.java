package ch.enterag.sqlparser.ddl.enums;

import ch.enterag.sqlparser.K;

public enum Levels {
   CASCADED(K.CASCADED.getKeyword()),
   LOCAL(K.LOCAL.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private Levels(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
