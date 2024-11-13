package ch.enterag.sqlparser.ddl.enums;

import ch.enterag.sqlparser.K;

public enum Instantiability {
   INSTANTIABLE(K.INSTANTIABLE.getKeyword()),
   NOT_INSTANTIABLE(K.NOT.getKeyword() + " " + K.INSTANTIABLE.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private Instantiability(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
