package ch.admin.bar.siard2.mysql.expression;

import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.expression.ValueExpressionPrimary;





















public class MySqlValueExpressionPrimary
  extends ValueExpressionPrimary
{
  public String format() {
    String sExpression = "";
    
    if (isArrayValueConstructor())
      throw new IllegalArgumentException("ARRAY value constructor is not available in MySQL!"); 
    if (isMultisetValueConstructor())
      throw new IllegalArgumentException("MULTISET value constructor is not available in MySQL!"); 
    if (isTableMultisetValueConstructor()) {
      throw new IllegalArgumentException("TABLE MULTISET value constructor is not available in MySQL!");
    }
    sExpression = super.format();

    
    return sExpression;
  }






  
  public MySqlValueExpressionPrimary(SqlFactory sf) {
    super(sf);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\jdbcmysql-2.2.2.jar!\ch\admin\bar\siard2\mysql\expression\MySqlValueExpressionPrimary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */