package ch.enterag.sqlparser.expression.enums;

import ch.enterag.sqlparser.K;

public enum BooleanLiteral {
   FALSE(K.FALSE.getKeyword()),
   TRUE(K.TRUE.getKeyword()),
   UNKNOWN(K.UNKNOWN.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private BooleanLiteral(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
