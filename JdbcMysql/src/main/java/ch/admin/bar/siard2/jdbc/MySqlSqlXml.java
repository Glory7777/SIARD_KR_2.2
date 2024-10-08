package ch.admin.bar.siard2.jdbc;

import ch.enterag.utils.SU;
import ch.enterag.utils.jdbc.BaseSqlXml;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLXML;

















public class MySqlSqlXml
  extends BaseSqlXml
  implements SQLXML
{
  private String _sXml = null;

  
  private class XmlOutputStream
    extends ByteArrayOutputStream
  {
    private XmlOutputStream() {}

    
    public void close() throws IOException {
      MySqlSqlXml.this._sXml = SU.getUtf8String(toByteArray());
    }
  }

  
  private class XmlWriter
    extends StringWriter
  {
    private XmlWriter() {}

    
    public void close() throws IOException {
      MySqlSqlXml.this._sXml = toString();
    }
  }






  
  public MySqlSqlXml(SQLXML sqlxmlWrapped) {
    super(sqlxmlWrapped);
  }






  
  public static MySqlSqlXml getInstance() {
    return new MySqlSqlXml(null);
  }






  
  public InputStream getBinaryStream() throws SQLException {
    return new ByteArrayInputStream(SU.putUtf8String(this._sXml));
  }






  
  public OutputStream setBinaryStream() throws SQLException {
    return new XmlOutputStream();
  }






  
  public Reader getCharacterStream() throws SQLException {
    return new StringReader(this._sXml);
  }






  
  public Writer setCharacterStream() throws SQLException {
    return new XmlWriter();
  }






  
  public String getString() throws SQLException {
    return this._sXml;
  }






  
  public void setString(String value) throws SQLException {
    this._sXml = value;
  }






  
  public <T extends javax.xml.transform.Source> T getSource(Class<T> sourceClass) throws SQLException {
    throw new SQLFeatureNotSupportedException("getSource() not supported!");
  }






  
  public <T extends javax.xml.transform.Result> T setResult(Class<T> resultClass) throws SQLException {
    throw new SQLFeatureNotSupportedException("setResult() not supported!");
  }






  
  public void free() throws SQLException {
    this._sXml = null;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\jdbcmysql-2.2.2.jar!\ch\admin\bar\siard2\jdbc\MySqlSqlXml.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */