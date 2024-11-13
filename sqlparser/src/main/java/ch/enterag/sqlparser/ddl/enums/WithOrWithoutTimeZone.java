package ch.enterag.sqlparser.ddl.enums;

import ch.enterag.sqlparser.K;

public enum WithOrWithoutTimeZone {
   WITH_TIME_ZONE(K.WITH.getKeyword() + " " + K.TIME.getKeyword() + " " + K.ZONE.getKeyword()),
   WITHOUT_TIME_ZONE(K.WITHOUT.getKeyword() + " " + K.TIME.getKeyword() + " " + K.ZONE.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private WithOrWithoutTimeZone(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
