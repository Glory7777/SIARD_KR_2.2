package ch.enterag.sqlparser.expression.enums;

import ch.enterag.sqlparser.K;

public enum SetCondition {
   IS_A_SET(K.IS.getKeyword() + " " + K.A.getKeyword() + " " + K.SET.getKeyword()),
   IS_NOT_A_SET(K.IS.getKeyword() + " " + K.NOT.getKeyword() + " " + K.A.getKeyword() + " " + K.SET.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private SetCondition(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
