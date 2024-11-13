package ch.enterag.sqlparser.ddl.enums;

import ch.enterag.sqlparser.K;

public enum NullCallClause {
   RETURNS_NULL(K.RETURNS.getKeyword() + " " + K.NULL.getKeyword() + " " + K.ON.getKeyword() + " " + K.NULL.getKeyword() + " " + K.INPUT.getKeyword()),
   CALLED(K.CALLED.getKeyword() + " " + K.ON.getKeyword() + " " + K.NULL.getKeyword() + " " + K.INPUT.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private NullCallClause(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
