package ch.enterag.sqlparser.expression.enums;

import ch.enterag.sqlparser.K;

public enum SetQuantifier {
   DISTINCT(K.DISTINCT.getKeyword()),
   ALL(K.ALL.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private SetQuantifier(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
