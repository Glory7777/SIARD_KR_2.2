package ch.admin.bar.siard2.mysql;

import ch.enterag.sqlparser.Interval;
import ch.enterag.sqlparser.SqlLiterals;
import ch.enterag.utils.BU;














public abstract class MySqlLiterals
  extends SqlLiterals
{
  private static final String sBYTE_LITERAL_PREFIX = "x";
  
  public static String formatStringLiteral(String sValue) {
    String sQuoted = sNULL;
    if (sValue != null) {
      sQuoted = "'" + sValue.replaceAll("'", "''") + "'";
    }
    return sQuoted;
  }








  
  public static String formatBytesLiteral(byte[] bufValue) {
    String sFormatted = sNULL;
    if (bufValue != null) {
      sFormatted = "x" + formatStringLiteral(BU.toHex(bufValue));
    }
    return sFormatted;
  }









  
  public static String formatIntervalLiteral(Interval intervalValue) {
    String sFormatted = sNULL;
    if (intervalValue != null) {
      sFormatted = formatBytesLiteral(serialize(intervalValue));
    }
    return sFormatted;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\jdbcmysql-2.2.2.jar!\ch\admin\bar\siard2\mysql\MySqlLiterals.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */