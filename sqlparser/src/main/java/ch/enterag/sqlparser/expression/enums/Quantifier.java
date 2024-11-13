package ch.enterag.sqlparser.expression.enums;

import ch.enterag.sqlparser.K;

public enum Quantifier {
   ALL(K.ALL.getKeyword()),
   SOME(K.SOME.getKeyword()),
   ANY(K.ANY.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private Quantifier(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
