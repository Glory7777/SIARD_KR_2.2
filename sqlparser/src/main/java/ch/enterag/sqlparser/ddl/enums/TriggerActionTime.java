package ch.enterag.sqlparser.ddl.enums;

import ch.enterag.sqlparser.K;

public enum TriggerActionTime {
   BEFORE(K.BEFORE.getKeyword()),
   AFTER(K.AFTER.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private TriggerActionTime(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
