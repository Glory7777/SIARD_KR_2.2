package ch.enterag.utils.jdbc;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Clob;
import java.sql.SQLException;


public abstract class BaseClob
        implements Clob {
    private Clob _clobWrapped = null;


    public BaseClob(Clob clobWrapped) {
        this._clobWrapped = clobWrapped;
    }


    public long length() throws SQLException {
        return this._clobWrapped.length();
    }


    public String getSubString(long pos, int length) throws SQLException {
        return this._clobWrapped.getSubString(pos, length);
    }


    public long position(String searchstr, long start) throws SQLException {
        return this._clobWrapped.position(searchstr, start);
    }


    public long position(Clob searchclob, long start) throws SQLException {
        return this._clobWrapped.position(searchclob, start);
    }


    public int setString(long pos, String str) throws SQLException {
        return this._clobWrapped.setString(pos, str);
    }


    public int setString(long pos, String str, int offset, int len) throws SQLException {
        return this._clobWrapped.setString(pos, str, offset, len);
    }


    public InputStream getAsciiStream() throws SQLException {
        return this._clobWrapped.getAsciiStream();
    }


    public OutputStream setAsciiStream(long pos) throws SQLException {
        return this._clobWrapped.setAsciiStream(pos);
    }


    public Reader getCharacterStream() throws SQLException {
        return this._clobWrapped.getCharacterStream();
    }


    public Reader getCharacterStream(long pos, long length) throws SQLException {
        return this._clobWrapped.getCharacterStream(pos, length);
    }


    public Writer setCharacterStream(long pos) throws SQLException {
        return this._clobWrapped.setCharacterStream(pos);
    }


    public void truncate(long len) throws SQLException {
        this._clobWrapped.truncate(len);
    }


    public void free() throws SQLException {
        this._clobWrapped.free();
    }


    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (iface == Clob.class);
    }


    public <T> T unwrap(Class<T> iface) throws SQLException {
        Clob clob = null;
        T wrapped = null;
        if (isWrapperFor(iface))
            clob = this._clobWrapped;
        return (T) clob;
    }
}
