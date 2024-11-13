package ch.enterag.sqlparser.ddl.enums;

import ch.enterag.sqlparser.K;

public enum ReferenceScopeCheck {
   CHECKED(K.REFERENCES.getKeyword() + " " + K.ARE.getKeyword() + " " + K.CHECKED.getKeyword()),
   NOT_CHECKED(K.REFERENCES.getKeyword() + " " + K.ARE.getKeyword() + " " + K.NOT.getKeyword() + " " + K.CHECKED.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private ReferenceScopeCheck(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
