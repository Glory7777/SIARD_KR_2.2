package ch.enterag.sqlparser.ddl.enums;

import ch.enterag.sqlparser.K;

public enum ConstraintCheckTime {
   INITIALLY_DEFERRED(K.INITIALLY.getKeyword() + " " + K.DEFERRED.getKeyword()),
   INITIALLY_IMMEDIATE(K.INITIALLY.getKeyword() + " " + K.IMMEDIATE.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private ConstraintCheckTime(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
