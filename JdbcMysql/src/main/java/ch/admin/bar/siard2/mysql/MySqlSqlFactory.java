package ch.admin.bar.siard2.mysql;

import ch.admin.bar.siard2.mysql.datatype.MySqlPredefinedType;
import ch.admin.bar.siard2.mysql.ddl.MySqlDropSchemaStatement;
import ch.admin.bar.siard2.mysql.ddl.MySqlDropTableStatement;
import ch.admin.bar.siard2.mysql.expression.MySqlLiteral;
import ch.admin.bar.siard2.mysql.expression.MySqlUnsignedLiteral;
import ch.admin.bar.siard2.mysql.expression.MySqlValueExpressionPrimary;
import ch.enterag.sqlparser.BaseSqlFactory;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.datatype.PredefinedType;
import ch.enterag.sqlparser.ddl.DropSchemaStatement;
import ch.enterag.sqlparser.ddl.DropTableStatement;
import ch.enterag.sqlparser.expression.Literal;
import ch.enterag.sqlparser.expression.UnsignedLiteral;
import ch.enterag.sqlparser.expression.ValueExpressionPrimary;













public class MySqlSqlFactory
  extends BaseSqlFactory
  implements SqlFactory
{
  public PredefinedType newPredefinedType() {
    return (PredefinedType)new MySqlPredefinedType(this);
  }





  
  public Literal newLiteral() {
    return (Literal)new MySqlLiteral(this);
  }





  
  public UnsignedLiteral newUnsignedLiteral() {
    return (UnsignedLiteral)new MySqlUnsignedLiteral(this);
  }





  
  public ValueExpressionPrimary newValueExpressionPrimary() {
    return (ValueExpressionPrimary)new MySqlValueExpressionPrimary(this);
  }





  
  public DropSchemaStatement newDropSchemaStatement() {
    return (DropSchemaStatement)new MySqlDropSchemaStatement(this);
  }




  
  public DropTableStatement newDropTableStatement() {
    return (DropTableStatement)new MySqlDropTableStatement(this);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\jdbcmysql-2.2.2.jar!\ch\admin\bar\siard2\mysql\MySqlSqlFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */