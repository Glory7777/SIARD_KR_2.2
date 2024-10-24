package ch.admin.bar.siard2.mysql.ddl;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.ddl.DropTableStatement;


























public class MySqlDropTableStatement
  extends DropTableStatement
{
  public String format() {
    String sStatement = K.DROP.getKeyword() + " " + K.TABLE.getKeyword() + " " + getTableName().format();
    return sStatement;
  }





  
  public MySqlDropTableStatement(SqlFactory sf) {
    super(sf);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\jdbcmysql-2.2.2.jar!\ch\admin\bar\siard2\mysql\ddl\MySqlDropTableStatement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */