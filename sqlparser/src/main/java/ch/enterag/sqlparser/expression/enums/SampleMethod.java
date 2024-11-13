package ch.enterag.sqlparser.expression.enums;

import ch.enterag.sqlparser.K;

public enum SampleMethod {
   BERNOULLI(K.BERNOULLI.getKeyword()),
   SYSTEM(K.SYSTEM.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private SampleMethod(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
