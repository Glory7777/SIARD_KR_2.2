package ch.enterag.sqlparser.expression.enums;

import ch.enterag.sqlparser.K;

public enum BooleanOperator {
   AND(K.AND.getKeyword()),
   OR(K.OR.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private BooleanOperator(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
