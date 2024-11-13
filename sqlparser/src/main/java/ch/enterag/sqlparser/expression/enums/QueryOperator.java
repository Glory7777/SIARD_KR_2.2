package ch.enterag.sqlparser.expression.enums;

import ch.enterag.sqlparser.K;

public enum QueryOperator {
   INTERSECT(K.INTERSECT.getKeyword()),
   UNION(K.UNION.getKeyword()),
   EXCEPT(K.EXCEPT.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private QueryOperator(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
