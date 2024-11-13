package ch.enterag.sqlparser.ddl.enums;

import ch.enterag.sqlparser.K;

public enum Multiplier {
   K(ch.enterag.sqlparser.K.K.getKeyword(), getK()),
   M(ch.enterag.sqlparser.K.M.getKeyword(), getM()),
   G(ch.enterag.sqlparser.K.G.getKeyword(), getG());

   private static final int _iK = 1024;
   private static final int _iM = 1048576;
   private static final int _iG = 1073741824;
   private String _sKeyword = null;
   private int _iValue = -1;

   private static int getK() {
      return 1024;
   }

   private static int getM() {
      return 1048576;
   }

   private static int getG() {
      return 1073741824;
   }

   public String getKeyword() {
      return this._sKeyword;
   }

   public int getValue() {
      return this._iValue;
   }

   private Multiplier(String sKeyword, int iValue) {
      this._sKeyword = sKeyword;
      this._iValue = iValue;
   }
}
