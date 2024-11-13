package ch.enterag.sqlparser.expression.enums;

import ch.enterag.sqlparser.K;

public enum CycleFunction {
   CYCLE(K.CYCLE.getKeyword()),
   SET(K.SET.getKeyword()),
   DEFAULT(K.DEFAULT.getKeyword()),
   USING(K.USING.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private CycleFunction(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
