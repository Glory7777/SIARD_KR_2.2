package ch.enterag.utils.jdbc;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public abstract class BaseResultSetMetaData
        implements ResultSetMetaData {
    private ResultSetMetaData _rsmdWrapped = null;


    public BaseResultSetMetaData(ResultSetMetaData rsmdWrapped) {
        this._rsmdWrapped = rsmdWrapped;
    }


    public int getColumnCount() throws SQLException {
        return this._rsmdWrapped.getColumnCount();
    }


    public String getCatalogName(int column) throws SQLException {
        return this._rsmdWrapped.getCatalogName(column);
    }


    public String getSchemaName(int column) throws SQLException {
        return this._rsmdWrapped.getSchemaName(column);
    }


    public String getTableName(int column) throws SQLException {
        return this._rsmdWrapped.getTableName(column);
    }


    public String getColumnName(int column) throws SQLException {
        return this._rsmdWrapped.getColumnName(column);
    }


    public String getColumnLabel(int column) throws SQLException {
        return this._rsmdWrapped.getColumnLabel(column);
    }


    public String getColumnClassName(int column) throws SQLException {
        return this._rsmdWrapped.getColumnClassName(column);
    }


    public int getColumnType(int column) throws SQLException {
        return this._rsmdWrapped.getColumnType(column);
    }


    public String getColumnTypeName(int column) throws SQLException {
        return this._rsmdWrapped.getColumnTypeName(column);
    }


    public int getColumnDisplaySize(int column) throws SQLException {
        return this._rsmdWrapped.getColumnDisplaySize(column);
    }


    public int getPrecision(int column) throws SQLException {
        return this._rsmdWrapped.getPrecision(column);
    }


    public int getScale(int column) throws SQLException {
        return this._rsmdWrapped.getScale(column);
    }


    public boolean isAutoIncrement(int column) throws SQLException {
        return this._rsmdWrapped.isAutoIncrement(column);
    }


    public boolean isCaseSensitive(int column) throws SQLException {
        return this._rsmdWrapped.isCaseSensitive(column);
    }


    public boolean isSearchable(int column) throws SQLException {
        return this._rsmdWrapped.isSearchable(column);
    }


    public boolean isCurrency(int column) throws SQLException {
        return this._rsmdWrapped.isCurrency(column);
    }


    public int isNullable(int column) throws SQLException {
        return this._rsmdWrapped.isNullable(column);
    }


    public boolean isSigned(int column) throws SQLException {
        return this._rsmdWrapped.isSigned(column);
    }


    public boolean isReadOnly(int column) throws SQLException {
        return this._rsmdWrapped.isReadOnly(column);
    }


    public boolean isWritable(int column) throws SQLException {
        return this._rsmdWrapped.isWritable(column);
    }


    public boolean isDefinitelyWritable(int column) throws SQLException {
        return this._rsmdWrapped.isDefinitelyWritable(column);
    }


    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (iface == ResultSetMetaData.class);
    }


    public <T> T unwrap(Class<T> iface) throws SQLException {
        ResultSetMetaData resultSetMetaData = null;
        T wrapped = null;
        if (isWrapperFor(iface))
            resultSetMetaData = this._rsmdWrapped;
        return (T) resultSetMetaData;
    }
}
