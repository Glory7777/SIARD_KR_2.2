package ch.admin.bar.siard2.mysql.expression;

import ch.admin.bar.siard2.mysql.MySqlLiterals;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.expression.UnsignedLiteral;





















public class MySqlUnsignedLiteral
  extends UnsignedLiteral
{
  public MySqlUnsignedLiteral(SqlFactory sf) {
    super(sf);
  }






  
  public String format() {
    String sExpression = null;
    
    if (getInterval() != null) {
      sExpression = MySqlLiterals.formatIntervalLiteral(getInterval());
    } else {
      sExpression = super.format();
    } 
    
    return sExpression;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\jdbcmysql-2.2.2.jar!\ch\admin\bar\siard2\mysql\expression\MySqlUnsignedLiteral.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */