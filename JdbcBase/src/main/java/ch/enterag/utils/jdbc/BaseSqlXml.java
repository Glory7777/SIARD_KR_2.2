package ch.enterag.utils.jdbc;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.SQLException;
import java.sql.SQLXML;


public abstract class BaseSqlXml
        implements SQLXML {
    private SQLXML _sqlxmlWrapped = null;


    public BaseSqlXml(SQLXML sqlxmlWrapped) {
        this._sqlxmlWrapped = sqlxmlWrapped;
    }


    public InputStream getBinaryStream() throws SQLException {
        return this._sqlxmlWrapped.getBinaryStream();
    }


    public OutputStream setBinaryStream() throws SQLException {
        return this._sqlxmlWrapped.setBinaryStream();
    }


    public Reader getCharacterStream() throws SQLException {
        return this._sqlxmlWrapped.getCharacterStream();
    }


    public Writer setCharacterStream() throws SQLException {
        return this._sqlxmlWrapped.setCharacterStream();
    }


    public String getString() throws SQLException {
        return this._sqlxmlWrapped.getString();
    }


    public void setString(String value) throws SQLException {
        this._sqlxmlWrapped.setString(value);
    }


    public <T extends javax.xml.transform.Source> T getSource(Class<T> sourceClass) throws SQLException {
        return this._sqlxmlWrapped.getSource(sourceClass);
    }


    public <T extends javax.xml.transform.Result> T setResult(Class<T> resultClass) throws SQLException {
        return this._sqlxmlWrapped.setResult(resultClass);
    }


    public void free() throws SQLException {
        this._sqlxmlWrapped.free();
    }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\jdbcbase.jar!\ch\entera\\utils\jdbc\BaseSqlXml.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */