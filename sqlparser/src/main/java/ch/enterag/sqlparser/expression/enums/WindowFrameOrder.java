package ch.enterag.sqlparser.expression.enums;

import ch.enterag.sqlparser.K;

public enum WindowFrameOrder {
   PRECEDING(K.PRECEDING.getKeyword()),
   FOLLOWING(K.FOLLOWING.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private WindowFrameOrder(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
