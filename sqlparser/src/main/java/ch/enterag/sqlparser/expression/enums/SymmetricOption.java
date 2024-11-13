package ch.enterag.sqlparser.expression.enums;

import ch.enterag.sqlparser.K;

public enum SymmetricOption {
   ASYMMETRIC(K.ASYMMETRIC.getKeyword()),
   SYMMETRIC(K.SYMMETRIC.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private SymmetricOption(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
