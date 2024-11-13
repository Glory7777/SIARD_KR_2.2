package ch.enterag.sqlparser.ddl.enums;

import ch.enterag.sqlparser.K;

public enum ColumnConstraintType {
   NOT_NULL(K.NOT.getKeyword() + " " + K.NULL.getKeyword()),
   UNIQUE(K.UNIQUE.getKeyword()),
   PRIMARY_KEY(K.PRIMARY.getKeyword() + " " + K.KEY.getKeyword()),
   REFERENCES(K.REFERENCES.getKeyword()),
   CHECK(K.CHECK.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private ColumnConstraintType(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
