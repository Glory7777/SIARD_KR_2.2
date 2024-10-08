package ch.admin.bar.siard2.mysql.expression;

import ch.admin.bar.siard2.mysql.MySqlLiterals;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.expression.Literal;





















public class MySqlLiteral
  extends Literal
{
  public MySqlLiteral(SqlFactory sf) {
    super(sf);
  }







  
  public String format() {
    String sFormatted = "";
    
    if (getBytes() != null) {
      sFormatted = MySqlLiterals.formatBytesLiteral(getBytes());
    }
    
    return sFormatted;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\jdbcmysql-2.2.2.jar!\ch\admin\bar\siard2\mysql\expression\MySqlLiteral.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */