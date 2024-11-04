package ch.admin.bar.siard2.jdbc;

import com.tmax.tibero.jdbc.TbArray;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class TiberoArray implements java.sql.Array {
    private static ch.enterag.utils.logging.IndentLogger _il;
    private TbArray _tArray;
    private long _lMaxLength;
    private ch.enterag.sqlparser.identifier.QualifiedId _qiVarray;
    private String _sBaseTypeName;
    private Object[] _aoElements;
    private com.tmax.tibero.jdbc.TbConnection _conn;


    public TiberoArray(TbArray tArray) throws SQLException {
        this._tArray = tArray;
    }

    public TiberoArray(Connection conn, String sBaseTypeName, Object[] aoElements) throws SQLException {
        this._conn = (com.tmax.tibero.jdbc.TbConnection) conn;
        this._sBaseTypeName = sBaseTypeName;
        this._aoElements = aoElements;
    }

    public String getBaseTypeName() throws SQLException {
        return this._sBaseTypeName;
    }

    public int getBaseType() throws java.sql.SQLException {
        return 0;
    }

    public Object getArray() throws SQLException {
        return this._tArray.getArray();
    }

    public Object getArray(Map<String, Class<?>> map) throws SQLException {
        return this._tArray.getArray(map);
    }

    public Object getArray(long index, int count) throws SQLException {
        return this._tArray.getArray(index, count);
    }

    public Object getArray(long index, int count, Map<String, Class<?>> map) throws SQLException {
        return this._tArray.getArray(index, count, map);
    }

    public ResultSet getResultSet() throws SQLException {
        return this._tArray.getResultSet();
    }

    public ResultSet getResultSet(Map<String, Class<?>> map) throws SQLException {
        return this._tArray.getResultSet(map);
    }

    public ResultSet getResultSet(long index, int count) throws SQLException {
        return this._tArray.getResultSet(index, count);
    }

    public ResultSet getResultSet(long index, int count, Map<String, Class<?>> map) throws SQLException {
        return this._tArray.getResultSet(index, count, map);
    }

    public void free() throws SQLException {
        if (this._tArray != null) {
            this._tArray.free();
        }
    }


}
