package ch.admin.bar.siard2.mysql.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.ddl.DropSchemaStatement;
import ch.enterag.sqlparser.ddl.enums.DropBehavior;



















public class MySqlDropSchemaStatement
  extends DropSchemaStatement
{
  public MySqlDropSchemaStatement(SqlFactory sf) {
    super(sf);
  }



  
  public String format() {
    if (getDropBehavior() == DropBehavior.CASCADE)
      throw new IllegalArgumentException("Schema drop behavior CASCADE not supported by MSSQL!"); 
    String sStatement = K.DROP.getKeyword() + " " + K.SCHEMA.getKeyword() + " " + getSchemaName().format();
    return sStatement;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\jdbcmysql-2.2.2.jar!\ch\admin\bar\siard2\mysql\ddl\MySqlDropSchemaStatement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */