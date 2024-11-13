package ch.enterag.sqlparser.ddl.enums;

import ch.enterag.sqlparser.K;

public enum ParameterStyle {
   SQL(K.SQL.getKeyword()),
   GENERAL(K.GENERAL.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private ParameterStyle(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
