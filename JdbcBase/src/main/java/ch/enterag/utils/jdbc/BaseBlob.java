package ch.enterag.utils.jdbc;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;


public abstract class BaseBlob
        implements Blob {
    private Blob _blobWrapped = null;


    public BaseBlob(Blob blobWrapped) {
        this._blobWrapped = blobWrapped;
    }


    public long length() throws SQLException {
        return this._blobWrapped.length();
    }


    public long position(byte[] pattern, long start) throws SQLException {
        return this._blobWrapped.position(pattern, start);
    }


    public long position(Blob pattern, long start) throws SQLException {
        return this._blobWrapped.position(pattern, start);
    }


    public byte[] getBytes(long pos, int length) throws SQLException {
        return this._blobWrapped.getBytes(pos, length);
    }


    public int setBytes(long pos, byte[] bytes) throws SQLException {
        return this._blobWrapped.setBytes(pos, bytes);
    }


    public int setBytes(long pos, byte[] bytes, int offset, int len) throws SQLException {
        return this._blobWrapped.setBytes(pos, bytes, offset, len);
    }


    public InputStream getBinaryStream() throws SQLException {
        return this._blobWrapped.getBinaryStream();
    }


    public InputStream getBinaryStream(long pos, long length) throws SQLException {
        return this._blobWrapped.getBinaryStream();
    }


    public OutputStream setBinaryStream(long pos) throws SQLException {
        return this._blobWrapped.setBinaryStream(pos);
    }


    public void truncate(long len) throws SQLException {
        this._blobWrapped.truncate(len);
    }


    public void free() throws SQLException {
        this._blobWrapped.free();
    }


    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (iface == Blob.class);
    }


    public <T> T unwrap(Class<T> iface) throws SQLException {
        Blob blob = null;
        T wrapped = null;
        if (isWrapperFor(iface))
            blob = this._blobWrapped;
        return (T) blob;
    }
}
