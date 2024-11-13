package ch.enterag.sqlparser.ddl.enums;

import ch.enterag.sqlparser.K;

public enum WithOrWithoutData {
   WITH_DATA(K.WITH.getKeyword() + " " + K.DATA.getKeyword()),
   WITH_NO_DATA(K.WITH.getKeyword() + " " + K.NO.getKeyword() + " " + K.DATA.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private WithOrWithoutData(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
