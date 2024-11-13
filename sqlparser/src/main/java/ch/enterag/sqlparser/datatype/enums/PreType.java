package ch.enterag.sqlparser.datatype.enums;

import ch.enterag.sqlparser.K;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum PreType {
   CHAR(1, K.CHAR.getKeyword(), new String[]{K.CHARACTER.getKeyword()}),
   VARCHAR(12, K.VARCHAR.getKeyword(), new String[]{K.CHAR.getKeyword() + " " + K.VARYING.getKeyword(), K.CHARACTER.getKeyword() + " " + K.VARYING.getKeyword()}),
   CLOB(2005, K.CLOB.getKeyword(), new String[]{K.CHARACTER.getKeyword() + " " + K.LARGE.getKeyword() + " " + K.OBJECT.getKeyword()}),
   NCHAR(-15, K.NCHAR.getKeyword(), new String[]{K.NATIONAL.getKeyword() + " " + K.CHAR.getKeyword(), K.NATIONAL.getKeyword() + " " + K.CHARACTER.getKeyword()}),
   NVARCHAR(-9, K.NCHAR.getKeyword() + " " + K.VARYING.getKeyword(), new String[]{K.NATIONAL.getKeyword() + " " + K.CHAR.getKeyword() + " " + K.VARYING.getKeyword(), K.NATIONAL.getKeyword() + " " + K.CHARACTER.getKeyword() + " " + K.VARYING.getKeyword()}),
   NCLOB(2011, K.NCLOB.getKeyword(), new String[]{K.NCHAR.getKeyword() + " " + K.LARGE.getKeyword() + " " + K.OBJECT.getKeyword(), K.NATIONAL.getKeyword() + " " + K.CHARACTER.getKeyword() + " " + K.LARGE.getKeyword() + " " + K.OBJECT.getKeyword()}),
   BINARY(-2, K.BINARY.getKeyword(), new String[0]),
   VARBINARY(-3, K.VARBINARY.getKeyword(), new String[]{K.BINARY.getKeyword() + " " + K.VARYING.getKeyword()}),
   BLOB(2004, K.BLOB.getKeyword(), new String[]{K.BINARY.getKeyword() + " " + K.LARGE.getKeyword() + " " + K.OBJECT.getKeyword()}),
   NUMERIC(2, K.NUMERIC.getKeyword(), new String[0]),
   DECIMAL(3, K.DEC.getKeyword(), new String[]{K.DECIMAL.getKeyword()}),
   SMALLINT(5, K.SMALLINT.getKeyword(), new String[0]),
   INTEGER(4, K.INT.getKeyword(), new String[]{K.INTEGER.getKeyword()}),
   BIGINT(-5, K.BIGINT.getKeyword(), new String[0]),
   FLOAT(6, K.FLOAT.getKeyword(), new String[0]),
   REAL(7, K.REAL.getKeyword(), new String[0]),
   DOUBLE(8, K.DOUBLE.getKeyword() + " " + K.PRECISION.getKeyword(), new String[0]),
   BOOLEAN(16, K.BOOLEAN.getKeyword(), new String[0]),
   DATE(91, K.DATE.getKeyword(), new String[0]),
   TIME(92, K.TIME.getKeyword(), new String[]{K.TIME.getKeyword() + " " + K.WITH.getKeyword() + " " + K.TIME.getKeyword() + " " + K.ZONE.getKeyword()}),
   TIMESTAMP(93, K.TIMESTAMP.getKeyword(), new String[]{K.TIMESTAMP.getKeyword() + " " + K.WITH.getKeyword() + " " + K.TIME.getKeyword() + " " + K.ZONE.getKeyword()}),
   XML(2009, K.XML.getKeyword(), new String[0]),
   INTERVAL(1111, K.INTERVAL.getKeyword(), new String[0]),
   DATALINK(70, K.DATALINK.getKeyword(), new String[0]);

   private String _sKeyword = null;
   private Set<String> _setAliases = null;
   private int _iSqlType = 0;

   public String getKeyword() {
      return this._sKeyword;
   }

   public Set<String> getAliases() {
      return this._setAliases;
   }

   public int getSqlType() {
      return this._iSqlType;
   }

   private PreType(int iSqlType, String sKeyword, String... asAliases) {
      this._sKeyword = sKeyword;
      this._setAliases = new HashSet(Arrays.asList(asAliases));
      this._iSqlType = iSqlType;
   }

   public static PreType getBySqlType(int iSqlType) {
      PreType datatype = null;

      for(int i = 0; datatype == null && i < values().length; ++i) {
         PreType dt = values()[i];
         if (dt.getSqlType() == iSqlType) {
            datatype = dt;
         }
      }

      return datatype;
   }

   public static PreType getByKeyword(String sKeyword) {
      PreType datatype = null;

      for(int i = 0; datatype == null && i < values().length; ++i) {
         PreType dt = values()[i];
         if (dt.getKeyword().equals(sKeyword) || dt.getAliases().contains(sKeyword)) {
            datatype = dt;
         }
      }

      return datatype;
   }
}
