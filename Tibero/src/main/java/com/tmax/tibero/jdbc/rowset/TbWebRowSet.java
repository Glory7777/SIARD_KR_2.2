package com.tmax.tibero.jdbc.rowset;

import com.tmax.tibero.jdbc.err.TbError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.rowset.WebRowSet;

public class TbWebRowSet extends TbCachedRowSet implements WebRowSet {
  private static final long serialVersionUID = -173088871660903546L;
  
  public void readXml(Reader paramReader) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public void readXml(InputStream paramInputStream) throws SQLException, IOException {
    throw TbError.newSQLException(-90201);
  }
  
  public void writeXml(Writer paramWriter) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public void writeXml(OutputStream paramOutputStream) throws SQLException, IOException {
    throw TbError.newSQLException(-90201);
  }
  
  public void writeXml(ResultSet paramResultSet, Writer paramWriter) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public void writeXml(ResultSet paramResultSet, OutputStream paramOutputStream) throws SQLException, IOException {
    throw TbError.newSQLException(-90201);
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\rowset\TbWebRowSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */