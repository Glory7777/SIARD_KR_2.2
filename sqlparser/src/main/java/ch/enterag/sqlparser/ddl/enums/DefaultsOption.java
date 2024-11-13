package ch.enterag.sqlparser.ddl.enums;

import ch.enterag.sqlparser.K;

public enum DefaultsOption {
   INCLUDING_DEFAULTS(K.INCLUDING.getKeyword() + " " + K.DEFAULTS.getKeyword()),
   EXCLUDING_DEFAULTS(K.EXCLUDING.getKeyword() + " " + K.DEFAULTS.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private DefaultsOption(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
