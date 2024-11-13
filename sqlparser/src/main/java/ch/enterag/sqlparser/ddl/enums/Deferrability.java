package ch.enterag.sqlparser.ddl.enums;

import ch.enterag.sqlparser.K;

public enum Deferrability {
   DEFERRABLE(K.DEFERRABLE.getKeyword()),
   NOT_DEFERRABLE(K.NOT.getKeyword() + " " + K.DEFERRABLE.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private Deferrability(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
