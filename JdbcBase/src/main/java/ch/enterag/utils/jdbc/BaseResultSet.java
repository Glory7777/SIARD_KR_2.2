package ch.enterag.utils.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import javax.xml.datatype.Duration;


public abstract class BaseResultSet
        implements ResultSet {
    private ResultSet _rsWrapped = null;
    protected boolean _bWasNull = false;


    private void throwUndefinedMethod(AbstractMethodError ame) throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("Undefined JDBC method!", ame);
    }


    public BaseResultSet(ResultSet rsWrapped) {
        this._rsWrapped = rsWrapped;
    }


    public Statement getStatement() throws SQLException {
        return this._rsWrapped.getStatement();
    }


    public SQLWarning getWarnings() throws SQLException {
        return this._rsWrapped.getWarnings();
    }


    public void clearWarnings() throws SQLException {
        this._rsWrapped.clearWarnings();
    }


    public String getCursorName() throws SQLException {
        return this._rsWrapped.getCursorName();
    }


    public void close() throws SQLException {
        this._rsWrapped.close();
    }


    public boolean isClosed() throws SQLException {
        return this._rsWrapped.isClosed();
    }


    public boolean isBeforeFirst() throws SQLException {
        return this._rsWrapped.isBeforeFirst();
    }


    public boolean isAfterLast() throws SQLException {
        return this._rsWrapped.isAfterLast();
    }


    public boolean isFirst() throws SQLException {
        return this._rsWrapped.isFirst();
    }


    public boolean isLast() throws SQLException {
        return this._rsWrapped.isLast();
    }


    public void beforeFirst() throws SQLException {
        this._rsWrapped.beforeFirst();
    }


    public void afterLast() throws SQLException {
        this._rsWrapped.afterLast();
    }


    public int getRow() throws SQLException {
        return this._rsWrapped.getRow();
    }


    public boolean absolute(int row) throws SQLException {
        return this._rsWrapped.absolute(row);
    }


    public boolean relative(int rows) throws SQLException {
        return this._rsWrapped.relative(rows);
    }


    public boolean previous() throws SQLException {
        return this._rsWrapped.previous();
    }


    public boolean first() throws SQLException {
        return this._rsWrapped.first();
    }


    public boolean last() throws SQLException {
        return this._rsWrapped.last();
    }


    public boolean next() throws SQLException {
        return this._rsWrapped.next();
    }


    public ResultSetMetaData getMetaData() throws SQLException {
        return this._rsWrapped.getMetaData();
    }


    public int getHoldability() throws SQLException {
        return this._rsWrapped.getHoldability();
    }


    public void setFetchDirection(int direction) throws SQLException {
        this._rsWrapped.setFetchDirection(direction);
    }


    public int getFetchDirection() throws SQLException {
        return this._rsWrapped.getFetchDirection();
    }


    public void setFetchSize(int rows) throws SQLException {
        this._rsWrapped.setFetchSize(rows);
    }


    public int getFetchSize() throws SQLException {
        return this._rsWrapped.getFetchSize();
    }


    public int getType() throws SQLException {
        return this._rsWrapped.getType();
    }


    public int getConcurrency() throws SQLException {
        return this._rsWrapped.getConcurrency();
    }


    public int findColumn(String columnLabel) throws SQLException {
        return this._rsWrapped.findColumn(columnLabel);
    }


    public boolean wasNull() throws SQLException {
        return this._bWasNull;
    }


    public void updateNull(int columnIndex) throws SQLException {
        this._rsWrapped.updateNull(columnIndex);
    }


    public void updateNull(String columnLabel) throws SQLException {
        updateNull(findColumn(columnLabel));
    }


    public String getString(int columnIndex) throws SQLException {
        String s = this._rsWrapped.getString(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return s;
    }


    public void updateString(int columnIndex, String x) throws SQLException {
        this._rsWrapped.updateString(columnIndex, x);
    }


    public String getString(String columnLabel) throws SQLException {
        return getString(findColumn(columnLabel));
    }


    public void updateString(String columnLabel, String x) throws SQLException {
        updateString(findColumn(columnLabel), x);
    }


    public String getNString(int columnIndex) throws SQLException {
        String s = this._rsWrapped.getNString(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return s;
    }


    public void updateNString(int columnIndex, String nString) throws SQLException {
        this._rsWrapped.updateNString(columnIndex, nString);
    }


    public String getNString(String columnLabel) throws SQLException {
        return getNString(findColumn(columnLabel));
    }


    public void updateNString(String columnLabel, String nString) throws SQLException {
        updateNString(findColumn(columnLabel), nString);
    }


    public boolean getBoolean(int columnIndex) throws SQLException {
        boolean b = this._rsWrapped.getBoolean(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return b;
    }


    public void updateBoolean(int columnIndex, boolean x) throws SQLException {
        this._rsWrapped.updateBoolean(columnIndex, x);
    }


    public boolean getBoolean(String columnLabel) throws SQLException {
        return getBoolean(findColumn(columnLabel));
    }


    public void updateBoolean(String columnLabel, boolean x) throws SQLException {
        updateBoolean(findColumn(columnLabel), x);
    }


    public byte getByte(int columnIndex) throws SQLException {
        byte by = this._rsWrapped.getByte(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return by;
    }


    public void updateByte(int columnIndex, byte x) throws SQLException {
        this._rsWrapped.updateByte(columnIndex, x);
    }


    public byte getByte(String columnLabel) throws SQLException {
        return getByte(findColumn(columnLabel));
    }


    public void updateByte(String columnLabel, byte x) throws SQLException {
        updateByte(findColumn(columnLabel), x);
    }


    public short getShort(int columnIndex) throws SQLException {
        Short sh = Short.valueOf(this._rsWrapped.getShort(columnIndex));
        this._bWasNull = this._rsWrapped.wasNull();
        return sh.shortValue();
    }


    public void updateShort(int columnIndex, short x) throws SQLException {
        this._rsWrapped.updateShort(columnIndex, x);
    }


    public short getShort(String columnLabel) throws SQLException {
        return getShort(findColumn(columnLabel));
    }


    public void updateShort(String columnLabel, short x) throws SQLException {
        updateShort(findColumn(columnLabel), x);
    }


    public int getInt(int columnIndex) throws SQLException {
        int i = this._rsWrapped.getInt(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return i;
    }


    public void updateInt(int columnIndex, int x) throws SQLException {
        this._rsWrapped.updateInt(columnIndex, x);
    }


    public int getInt(String columnLabel) throws SQLException {
        return getInt(findColumn(columnLabel));
    }


    public void updateInt(String columnLabel, int x) throws SQLException {
        updateInt(findColumn(columnLabel), x);
    }


    public long getLong(int columnIndex) throws SQLException {
        long l = this._rsWrapped.getLong(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return l;
    }


    public void updateLong(int columnIndex, long x) throws SQLException {
        this._rsWrapped.updateLong(columnIndex, x);
    }


    public long getLong(String columnLabel) throws SQLException {
        return getLong(findColumn(columnLabel));
    }


    public void updateLong(String columnLabel, long x) throws SQLException {
        updateLong(findColumn(columnLabel), x);
    }


    public float getFloat(int columnIndex) throws SQLException {
        float f = this._rsWrapped.getFloat(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return f;
    }


    public void updateFloat(int columnIndex, float x) throws SQLException {
        this._rsWrapped.updateFloat(columnIndex, x);
    }


    public float getFloat(String columnLabel) throws SQLException {
        return getFloat(findColumn(columnLabel));
    }


    public void updateFloat(String columnLabel, float x) throws SQLException {
        updateFloat(findColumn(columnLabel), x);
    }


    public double getDouble(int columnIndex) throws SQLException {
        double d = this._rsWrapped.getDouble(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return d;
    }


    public void updateDouble(int columnIndex, double x) throws SQLException {
        this._rsWrapped.updateDouble(columnIndex, x);
    }


    public double getDouble(String columnLabel) throws SQLException {
        return getDouble(findColumn(columnLabel));
    }


    public void updateDouble(String columnLabel, double x) throws SQLException {
        updateDouble(findColumn(columnLabel), x);
    }


    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        BigDecimal bd = this._rsWrapped.getBigDecimal(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return bd;
    }


    public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
        this._rsWrapped.updateBigDecimal(columnIndex, x);
    }


    public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
        return getBigDecimal(findColumn(columnLabel));
    }


    public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
        updateBigDecimal(findColumn(columnLabel), x);
    }


    @Deprecated
    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
        BigDecimal bd = this._rsWrapped.getBigDecimal(columnIndex, scale);
        this._bWasNull = this._rsWrapped.wasNull();
        return bd;
    }


    @Deprecated
    public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
        return getBigDecimal(findColumn(columnLabel), scale);
    }


    public byte[] getBytes(int columnIndex) throws SQLException {
        byte[] buf = this._rsWrapped.getBytes(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return buf;
    }


    public void updateBytes(int columnIndex, byte[] x) throws SQLException {
        this._rsWrapped.updateBytes(columnIndex, x);
    }


    public byte[] getBytes(String columnLabel) throws SQLException {
        return getBytes(findColumn(columnLabel));
    }


    public void updateBytes(String columnLabel, byte[] x) throws SQLException {
        updateBytes(findColumn(columnLabel), x);
    }


    public Date getDate(int columnIndex) throws SQLException {
        Date date = this._rsWrapped.getDate(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return date;
    }


    public void updateDate(int columnIndex, Date x) throws SQLException {
        this._rsWrapped.updateDate(columnIndex, x);
    }


    public Date getDate(String columnLabel) throws SQLException {
        return getDate(findColumn(columnLabel));
    }


    public void updateDate(String columnLabel, Date x) throws SQLException {
        updateDate(findColumn(columnLabel), x);
    }


    public Date getDate(int columnIndex, Calendar cal) throws SQLException {
        Date date = this._rsWrapped.getDate(columnIndex, cal);
        this._bWasNull = this._rsWrapped.wasNull();
        return date;
    }


    public Date getDate(String columnLabel, Calendar cal) throws SQLException {
        return getDate(findColumn(columnLabel), cal);
    }


    public Time getTime(int columnIndex) throws SQLException {
        Time time = this._rsWrapped.getTime(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return time;
    }


    public void updateTime(int columnIndex, Time x) throws SQLException {
        this._rsWrapped.updateTime(columnIndex, x);
    }


    public Time getTime(String columnLabel) throws SQLException {
        return getTime(findColumn(columnLabel));
    }


    public void updateTime(String columnLabel, Time x) throws SQLException {
        updateTime(findColumn(columnLabel), x);
    }


    public Time getTime(int columnIndex, Calendar cal) throws SQLException {
        Time time = this._rsWrapped.getTime(columnIndex, cal);
        this._bWasNull = this._rsWrapped.wasNull();
        return time;
    }


    public Time getTime(String columnLabel, Calendar cal) throws SQLException {
        return getTime(findColumn(columnLabel), cal);
    }


    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        Timestamp ts = this._rsWrapped.getTimestamp(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return ts;
    }


    public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
        this._rsWrapped.updateTimestamp(columnIndex, x);
    }


    public Timestamp getTimestamp(String columnLabel) throws SQLException {
        return getTimestamp(findColumn(columnLabel));
    }


    public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
        updateTimestamp(findColumn(columnLabel), x);
    }


    public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
        Timestamp ts = this._rsWrapped.getTimestamp(columnIndex, cal);
        this._bWasNull = this._rsWrapped.wasNull();
        return ts;
    }


    public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
        return getTimestamp(findColumn(columnLabel), cal);
    }


    public InputStream getAsciiStream(int columnIndex) throws SQLException {
        InputStream is = this._rsWrapped.getAsciiStream(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return is;
    }


    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
        this._rsWrapped.updateAsciiStream(columnIndex, x);
    }


    public InputStream getAsciiStream(String columnLabel) throws SQLException {
        return getAsciiStream(findColumn(columnLabel));
    }


    public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
        updateAsciiStream(findColumn(columnLabel), x);
    }


    public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
        this._rsWrapped.updateAsciiStream(columnIndex, x, length);
    }


    public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
        updateAsciiStream(findColumn(columnLabel), x, length);
    }


    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
        this._rsWrapped.updateAsciiStream(columnIndex, x, length);
    }


    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
        updateAsciiStream(findColumn(columnLabel), x, length);
    }


    @Deprecated
    public InputStream getUnicodeStream(int columnIndex) throws SQLException {
        InputStream is = this._rsWrapped.getUnicodeStream(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return is;
    }


    @Deprecated
    public InputStream getUnicodeStream(String columnLabel) throws SQLException {
        return getUnicodeStream(findColumn(columnLabel));
    }


    public Reader getCharacterStream(int columnIndex) throws SQLException {
        Reader rdr = this._rsWrapped.getCharacterStream(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return rdr;
    }


    public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
        this._rsWrapped.updateCharacterStream(columnIndex, x);
    }


    public Reader getCharacterStream(String columnLabel) throws SQLException {
        return getCharacterStream(findColumn(columnLabel));
    }


    public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
        updateCharacterStream(findColumn(columnLabel), reader);
    }


    public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
        this._rsWrapped.updateCharacterStream(columnIndex, x, length);
    }


    public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
        updateCharacterStream(findColumn(columnLabel), reader, length);
    }


    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        this._rsWrapped.updateCharacterStream(columnIndex, x, length);
    }


    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        updateCharacterStream(findColumn(columnLabel), reader, length);
    }


    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        Reader rdr = this._rsWrapped.getNCharacterStream(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return rdr;
    }


    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        this._rsWrapped.updateNCharacterStream(columnIndex, x, length);
    }


    public Reader getNCharacterStream(String columnLabel) throws SQLException {
        return getNCharacterStream(findColumn(columnLabel));
    }


    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        updateNCharacterStream(findColumn(columnLabel), reader, length);
    }


    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
        this._rsWrapped.updateNCharacterStream(columnIndex, x);
    }


    public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
        updateNCharacterStream(findColumn(columnLabel), reader);
    }


    public InputStream getBinaryStream(int columnIndex) throws SQLException {
        InputStream is = this._rsWrapped.getBinaryStream(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return is;
    }


    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
        this._rsWrapped.updateBinaryStream(columnIndex, x);
    }


    public InputStream getBinaryStream(String columnLabel) throws SQLException {
        return getBinaryStream(findColumn(columnLabel));
    }


    public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
        updateBinaryStream(findColumn(columnLabel), x);
    }


    public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
        this._rsWrapped.updateBinaryStream(columnIndex, x, length);
    }


    public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
        updateBinaryStream(findColumn(columnLabel), x, length);
    }


    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
        this._rsWrapped.updateBinaryStream(columnIndex, x, length);
    }


    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
        updateBinaryStream(findColumn(columnLabel), x, length);
    }


    public Object getObject(int columnIndex) throws SQLException {
        Object o = this._rsWrapped.getObject(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return o;
    }


    public void updateObject(int columnIndex, Object x) throws SQLException {
        this._rsWrapped.updateObject(columnIndex, x);
    }


    public Object getObject(String columnLabel) throws SQLException {
        return getObject(findColumn(columnLabel));
    }


    public void updateObject(String columnLabel, Object x) throws SQLException {
        updateObject(findColumn(columnLabel), x);
    }


    public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
        this._rsWrapped.updateObject(columnIndex, x, scaleOrLength);
    }


    public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
        updateObject(findColumn(columnLabel), x, scaleOrLength);
    }


    public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
        Object o = this._rsWrapped.getObject(columnIndex, map);
        this._bWasNull = this._rsWrapped.wasNull();
        return o;
    }


    public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
        return getObject(findColumn(columnLabel), map);
    }


    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        T o = null;

        try {
            o = this._rsWrapped.getObject(columnIndex, type);
            this._bWasNull = this._rsWrapped.wasNull();
        } catch (AbstractMethodError ame) {
            throwUndefinedMethod(ame);
        }
        return o;
    }


    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        return getObject(findColumn(columnLabel), type);
    }


    public Ref getRef(int columnIndex) throws SQLException {
        Ref ref = this._rsWrapped.getRef(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return ref;
    }


    public void updateRef(int columnIndex, Ref x) throws SQLException {
        this._rsWrapped.updateRef(columnIndex, x);
    }


    public Ref getRef(String columnLabel) throws SQLException {
        return getRef(findColumn(columnLabel));
    }


    public void updateRef(String columnLabel, Ref x) throws SQLException {
        updateRef(findColumn(columnLabel), x);
    }


    public Blob getBlob(int columnIndex) throws SQLException {
        Blob blob = this._rsWrapped.getBlob(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return blob;
    }


    public void updateBlob(int columnIndex, Blob x) throws SQLException {
        this._rsWrapped.updateBlob(columnIndex, x);
    }


    public Blob getBlob(String columnLabel) throws SQLException {
        return getBlob(findColumn(columnLabel));
    }


    public void updateBlob(String columnLabel, Blob x) throws SQLException {
        updateBlob(findColumn(columnLabel), x);
    }


    public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
        this._rsWrapped.updateBlob(columnIndex, inputStream);
    }


    public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
        updateBlob(findColumn(columnLabel), inputStream);
    }


    public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
        this._rsWrapped.updateBlob(columnIndex, inputStream, length);
    }


    public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
        updateBlob(findColumn(columnLabel), inputStream, length);
    }


    public Clob getClob(int columnIndex) throws SQLException {
        Clob clob = this._rsWrapped.getClob(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return clob;
    }


    public void updateClob(int columnIndex, Clob x) throws SQLException {
        this._rsWrapped.updateClob(columnIndex, x);
    }


    public Clob getClob(String columnLabel) throws SQLException {
        return getClob(findColumn(columnLabel));
    }


    public void updateClob(String columnLabel, Clob x) throws SQLException {
        updateClob(findColumn(columnLabel), x);
    }


    public void updateClob(int columnIndex, Reader reader) throws SQLException {
        this._rsWrapped.updateClob(columnIndex, reader);
    }


    public void updateClob(String columnLabel, Reader reader) throws SQLException {
        updateClob(findColumn(columnLabel), reader);
    }


    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
        this._rsWrapped.updateClob(columnIndex, reader, length);
    }


    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
        updateClob(findColumn(columnLabel), reader, length);
    }


    public NClob getNClob(int columnIndex) throws SQLException {
        NClob nclob = this._rsWrapped.getNClob(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return nclob;
    }


    public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
        this._rsWrapped.updateNClob(columnIndex, nClob);
    }


    public NClob getNClob(String columnLabel) throws SQLException {
        return getNClob(findColumn(columnLabel));
    }


    public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
        updateNClob(findColumn(columnLabel), nClob);
    }


    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
        this._rsWrapped.updateNClob(columnIndex, reader);
    }


    public void updateNClob(String columnLabel, Reader reader) throws SQLException {
        updateNClob(findColumn(columnLabel), reader);
    }


    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
        this._rsWrapped.updateNClob(columnIndex, reader, length);
    }


    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
        updateNClob(findColumn(columnLabel), reader, length);
    }


    public Array getArray(int columnIndex) throws SQLException {
        Array array = this._rsWrapped.getArray(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return array;
    }


    public void updateArray(int columnIndex, Array x) throws SQLException {
        this._rsWrapped.updateArray(columnIndex, x);
    }


    public Array getArray(String columnLabel) throws SQLException {
        return getArray(findColumn(columnLabel));
    }


    public void updateArray(String columnLabel, Array x) throws SQLException {
        updateArray(findColumn(columnLabel), x);
    }


    public RowId getRowId(int columnIndex) throws SQLException {
        RowId rowid = this._rsWrapped.getRowId(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return rowid;
    }


    public void updateRowId(int columnIndex, RowId x) throws SQLException {
        this._rsWrapped.updateRowId(columnIndex, x);
    }


    public RowId getRowId(String columnLabel) throws SQLException {
        return getRowId(findColumn(columnLabel));
    }


    public void updateRowId(String columnLabel, RowId x) throws SQLException {
        updateRowId(findColumn(columnLabel), x);
    }


    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        SQLXML sqlxml = this._rsWrapped.getSQLXML(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return sqlxml;
    }


    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
        this._rsWrapped.updateSQLXML(columnIndex, xmlObject);
    }


    public SQLXML getSQLXML(String columnLabel) throws SQLException {
        return getSQLXML(findColumn(columnLabel));
    }


    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
        updateSQLXML(findColumn(columnLabel), xmlObject);
    }


    public Duration getDuration(int columnIndex) throws SQLException, SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("getDuration() not supported!");
    }


    public Duration getDuration(String columnLabel) throws SQLException, SQLFeatureNotSupportedException {
        return getDuration(findColumn(columnLabel));
    }


    public void updateDuration(int columnIndex, Duration x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateDuration() not supported!");
    }


    public void updateDuration(String columnLabel, Duration x) throws SQLException {
        updateDuration(findColumn(columnLabel), x);
    }


    public URL getURL(int columnIndex) throws SQLException {
        URL url = this._rsWrapped.getURL(columnIndex);
        this._bWasNull = this._rsWrapped.wasNull();
        return url;
    }


    public URL getURL(String columnLabel) throws SQLException {
        return getURL(findColumn(columnLabel));
    }


    public void insertRow() throws SQLException {
        this._rsWrapped.insertRow();
    }


    public void updateRow() throws SQLException {
        this._rsWrapped.updateRow();
    }


    public void deleteRow() throws SQLException {
        this._rsWrapped.deleteRow();
    }


    public void refreshRow() throws SQLException {
        this._rsWrapped.refreshRow();
    }


    public boolean rowUpdated() throws SQLException {
        return this._rsWrapped.rowUpdated();
    }


    public boolean rowInserted() throws SQLException {
        return this._rsWrapped.rowInserted();
    }


    public boolean rowDeleted() throws SQLException {
        return this._rsWrapped.rowDeleted();
    }


    public void cancelRowUpdates() throws SQLException {
        this._rsWrapped.cancelRowUpdates();
    }


    public void moveToInsertRow() throws SQLException {
        this._rsWrapped.moveToInsertRow();
    }


    public void moveToCurrentRow() throws SQLException {
        this._rsWrapped.moveToCurrentRow();
    }


    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (iface == ResultSet.class);
    }


    public <T> T unwrap(Class<T> iface) throws SQLException {
        ResultSet resultSet = null;
        T wrapped = null;
        if (isWrapperFor(iface))
            resultSet = this._rsWrapped;
        return (T) resultSet;
    }
}
