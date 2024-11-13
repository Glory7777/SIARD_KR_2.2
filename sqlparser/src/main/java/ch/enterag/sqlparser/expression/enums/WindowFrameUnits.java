package ch.enterag.sqlparser.expression.enums;

import ch.enterag.sqlparser.K;

public enum WindowFrameUnits {
   ROWS(K.ROWS.getKeyword()),
   RANGE(K.RANGE.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private WindowFrameUnits(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
