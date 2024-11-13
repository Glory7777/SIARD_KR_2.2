package ch.enterag.sqlparser.dml.enums;

import ch.enterag.sqlparser.K;

public enum OverrideClause {
   OVERRIDING_USER_VALUE(K.OVERRIDING.getKeyword() + " " + K.USER.getKeyword() + " " + K.VALUE.getKeyword()),
   OVERRIDING_SYSTEM_VALUE(K.OVERRIDING.getKeyword() + " " + K.SYSTEM.getKeyword() + " " + K.VALUE.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private OverrideClause(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
