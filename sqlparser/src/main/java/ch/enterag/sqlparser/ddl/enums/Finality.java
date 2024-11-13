package ch.enterag.sqlparser.ddl.enums;

import ch.enterag.sqlparser.K;

public enum Finality {
   FINAL(K.FINAL.getKeyword()),
   NOT_FINAL(K.NOT.getKeyword() + " " + K.FINAL.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private Finality(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
