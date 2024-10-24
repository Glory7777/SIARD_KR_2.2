package ch.enterag.utils.jdbc;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;


public abstract class BaseArray
        implements Array {
    private Array _arrayWrapped = null;


    public BaseArray(Array arrayWrapped) {
        this._arrayWrapped = arrayWrapped;
    }


    public String getBaseTypeName() throws SQLException {
        return this._arrayWrapped.getBaseTypeName();
    }


    public int getBaseType() throws SQLException {
        return this._arrayWrapped.getBaseType();
    }


    public Object getArray() throws SQLException {
        return this._arrayWrapped.getArray();
    }


    public Object getArray(Map<String, Class<?>> map) throws SQLException {
        return this._arrayWrapped.getArray(map);
    }


    public Object getArray(long index, int count) throws SQLException {
        return this._arrayWrapped.getArray(index, count);
    }


    public Object getArray(long index, int count, Map<String, Class<?>> map) throws SQLException {
        return this._arrayWrapped.getArray(index, count, map);
    }


    public ResultSet getResultSet() throws SQLException {
        return this._arrayWrapped.getResultSet();
    }


    public ResultSet getResultSet(Map<String, Class<?>> map) throws SQLException {
        return this._arrayWrapped.getResultSet(map);
    }


    public ResultSet getResultSet(long index, int count) throws SQLException {
        return this._arrayWrapped.getResultSet(index, count);
    }


    public ResultSet getResultSet(long index, int count, Map<String, Class<?>> map) throws SQLException {
        return this._arrayWrapped.getResultSet(index, count, map);
    }


    public void free() throws SQLException {
        this._arrayWrapped.free();
    }


    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (iface == Array.class);
    }


    public <T> T unwrap(Class<T> iface) throws SQLException {
        Array array = null;
        T wrapped = null;
        if (isWrapperFor(iface))
            array = this._arrayWrapped;
        return (T) array;
    }
}
