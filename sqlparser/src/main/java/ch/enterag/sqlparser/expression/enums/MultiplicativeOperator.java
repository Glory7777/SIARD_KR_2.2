package ch.enterag.sqlparser.expression.enums;

public enum MultiplicativeOperator {
   ASTERISK("*"),
   SOLIDUS("/");

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private MultiplicativeOperator(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
