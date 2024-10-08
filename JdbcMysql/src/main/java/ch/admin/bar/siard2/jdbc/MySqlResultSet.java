package ch.admin.bar.siard2.jdbc;

import ch.enterag.sqlparser.Interval;
import ch.enterag.sqlparser.SqlLiterals;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.jdbc.BaseResultSet;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKBReader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.NClob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.xml.datatype.Duration;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class MySqlResultSet
        extends BaseResultSet
        implements ResultSet {
    private MySqlConnection _conn = null;
    private QualifiedId _qiTable = null;
    private String _sPrimaryColumn = null;

    public void setPrimaryColumn(QualifiedId qiTable, String sPrimaryColumn) {
        this._qiTable = qiTable;
        this._sPrimaryColumn = sPrimaryColumn;
    }


    public MySqlResultSet(ResultSet rsWrapped, MySqlConnection conn) throws SQLException {
        super(rsWrapped);
        this._conn = conn;
    }


    public Statement getStatement() throws SQLException {
        return new MySqlStatement(super.getStatement(), this._conn);
    }


    public ResultSetMetaData getMetaData() throws SQLException {
        return new MySqlResultSetMetaData(super.getMetaData(), this._sPrimaryColumn);
    }


    private Object mapObject(Object o, int iType) throws SQLException {
        if (o instanceof String && iType == -1) {
            Clob clob = getStatement().getConnection().createClob();
            clob.setString(1L, (String) o);
            o = clob;
        } else if (o instanceof String && iType == -16) {
            NClob nclob = getStatement().getConnection().createNClob();
            nclob.setString(1L, (String) o);
            o = nclob;
        } else if (o instanceof String && iType == 70) {
            try {
                o = new URL((String) o);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        } else if (o instanceof byte[] && iType == -4) {
            Blob blob = getStatement().getConnection().createBlob();
            blob.setBytes(1L, (byte[]) o);
            o = blob;
        } else if (o instanceof Integer && iType == 5) {
            Integer i = (Integer) o;
            Short sh = Short.valueOf(i.shortValue());
            o = sh;
        } else if (o instanceof Integer && iType == -6) {
            Integer i = (Integer) o;
            Short sh = Short.valueOf(i.shortValue());
            o = sh;
        }
        return o;
    }


    public Object getObject(int columnIndex) throws SQLException {
        Object o = super.getObject(columnIndex);
        int iType = ((ResultSetMetaData) getMetaData().<ResultSetMetaData>unwrap(ResultSetMetaData.class)).getColumnType(columnIndex);
        String sColumnType = getMetaData().getColumnTypeName(columnIndex);
        int iDisplaySize = getMetaData().getColumnDisplaySize(columnIndex);
        if (sColumnType.equals("GEOMETRY") || sColumnType
                .equals("POINT") || sColumnType
                .equals("LINESTRING") || sColumnType
                .equals("POLYGON") || sColumnType
                .equals("MULTIPOINT") || sColumnType
                .equals("MULTILINESTRING") || sColumnType
                .equals("MULTIPOLYGON") || sColumnType
                .equals("GEOMETRYCOLLECTION")) {
            try {
                o = getGeometryFromInputStream(new ByteArrayInputStream((byte[]) o)).toText();
            } catch (Exception e) {
                throw new SQLException("Parsing of Geometry failed!", e);
            }
        } else if (sColumnType.equals("SMALLINT UNSIGNED")) {
            o = mapObject(o, 4);
        } else if (sColumnType.equals("VARCHAR")) {
            if (iDisplaySize < 256) {
                o = mapObject(o, 12);
            } else {
                o = mapObject(o, iType);
            }
        } else {
            if (o instanceof LocalDateTime) {
                return Timestamp.valueOf((LocalDateTime) o);
            }
            o = mapObject(o, iType);
        }
        return o;
    }


    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        Object o = null;
        T oMapped = null;
        int iType = 1111;
        o = super.getObject(columnIndex);
        String sColumnType = getMetaData().getColumnTypeName(columnIndex);
        if (sColumnType.equals("GEOMETRY") || sColumnType
                .equals("POINT") || sColumnType
                .equals("LINESTRING") || sColumnType
                .equals("POLYGON") || sColumnType
                .equals("MULTIPOINT") || sColumnType
                .equals("MULTILINESTRING") || sColumnType
                .equals("MULTIPOLYGON") || sColumnType
                .equals("GEOMETRYCOLLECTION"))

            try {
                o = getGeometryFromInputStream(new ByteArrayInputStream((byte[]) o)).toText();
            } catch (Exception e) {
                throw new SQLException("Parsing of Geometry failed!", e);
            }

        if (type.isInstance(o)) {
            oMapped = (T) o;
        } else {
            oMapped = mapObject(o, iType, type);
        }
        return oMapped;
    }


    private <T> T mapObject(Object o, int iType, Class<T> type) throws SQLException {
        T oMapped = null;
        oMapped = type.cast(mapObject(o, iType));
        return oMapped;
    }


    public String getString(int columnIndex) throws SQLException {
        String result = null;
        String sColumnType = getMetaData().getColumnTypeName(columnIndex);
        if (sColumnType.equals("GEOMETRY") || sColumnType
                .equals("POINT") || sColumnType
                .equals("LINESTRING") || sColumnType
                .equals("POLYGON") || sColumnType
                .equals("MULTIPOINT") || sColumnType
                .equals("MULTILINESTRING") || sColumnType
                .equals("MULTIPOLYGON") || sColumnType
                .equals("GEOMETRYCOLLECTION")) {

            try {
                result = getGeometryFromInputStream(new ByteArrayInputStream(getBytes(columnIndex))).toText();
            } catch (Exception e) {
                throw new SQLException("Parsing of Geometry failed!", e);
            }

        } else {
            result = super.getString(columnIndex);
        }
        return result;
    }


    public String getNString(int columnIndex) throws SQLException {
        return getString(columnIndex);
    }


    public Duration getDuration(int columnIndex) throws SQLException, SQLFeatureNotSupportedException {
        byte[] buf = getBytes(columnIndex);
        Interval iv = (Interval) SqlLiterals.deserialize(buf, Interval.class);
        return iv.toDuration();
    }


    public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
        byte[] buf = readByteArray(inputStream);
        Blob b = this._conn.createBlob();
        b.setBytes(1L, buf);
        updateBlob(columnIndex, b);
    }


    public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
        byte[] buf = readByteArray(inputStream);
        Blob b = this._conn.createBlob();
        b.setBytes(1L, buf);
        updateBlob(columnIndex, b);
    }


    public void updateClob(int columnIndex, Reader reader) throws SQLException {
        String s = readString(reader);
        updateString(columnIndex, s);
    }


    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
        String s = readString(reader);
        updateString(columnIndex, s);
    }


    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
        updateBytes(columnIndex, readByteArray(x));
    }


    public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
        updateBytes(columnIndex, readByteArray(x));
    }


    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
        updateBytes(columnIndex, readByteArray(x));
    }


    public void updateCharacterStream(int columnIndex, Reader reader) throws SQLException {
        updateString(columnIndex, readString(reader));
    }


    public void updateCharacterStream(int columnIndex, Reader reader, int length) throws SQLException {
        updateString(columnIndex, readString(reader));
    }


    public void updateCharacterStream(int columnIndex, Reader reader, long length) throws SQLException {
        updateString(columnIndex, readString(reader));
    }


    public NClob getNClob(int columnIndex) throws SQLException {
        Clob clob = getClob(columnIndex);
        NClob nclob = this._conn.createNClob();
        nclob.setString(1L, clob.getSubString(1L, (int) clob.length()));
        clob.free();
        return nclob;
    }


    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        return getCharacterStream(columnIndex);
    }


    public void updateNClob(int columnIndex, NClob nclob) throws SQLException {
        Clob clob = this._conn.createClob();
        clob.setString(1L, nclob.getSubString(1L, (int) nclob.length()));
        updateClob(columnIndex, clob);
        clob.free();
    }


    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
        updateCharacterStream(columnIndex, x);
    }


    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        updateCharacterStream(columnIndex, x, length);
    }


    public void updateNClob(int columnIndex, Reader x) throws SQLException {
        updateClob(columnIndex, x);
    }


    public void updateNClob(int columnIndex, Reader x, long length) throws SQLException {
        updateClob(columnIndex, x, length);
    }


    private void setNoBackslashEscapes(boolean bNoBackslashEscapes) throws SQLException {
        String sSql = "ANSI";
        if (bNoBackslashEscapes)
            sSql = sSql + ",NO_BACKSLASH_ESCAPES";
        sSql = "SET SESSION sql_mode = '" + sSql + "';";
        Connection connNative = this._conn.<Connection>unwrap(Connection.class);
        Statement stmt = connNative.createStatement();
        stmt.executeUpdate(sSql);
        stmt.close();
    }


    public void insertRow() throws SQLException {
        setNoBackslashEscapes(false);
        super.insertRow();
        setNoBackslashEscapes(true);
    }


    public void updateRow() throws SQLException {
        setNoBackslashEscapes(false);
        super.updateRow();
        setNoBackslashEscapes(true);
    }


    public void updateSQLXML(int columnIndex, SQLXML xml) throws SQLException {
        String sXML = xml.getString();
        xml.free();


        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        Document doc = null;
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(sXML.getBytes("UTF-8"))));
        } catch (ParserConfigurationException e) {
            throw new SQLException(e);
        } catch (SAXException e) {
            throw new SQLException(e);
        } catch (IOException e) {
            throw new SQLException(e);
        }
        if (doc != null) {
            updateString(columnIndex, sXML);
        }
    }


    public void updateObject(int columnIndex, Object x) throws SQLException {
        if (x instanceof SQLXML) {
            updateSQLXML(columnIndex, (SQLXML) x);
        } else if (x instanceof NClob) {
            updateNClob(columnIndex, (NClob) x);
        } else {
            super.updateObject(columnIndex, x);
        }
    }


    public void updateDuration(int columnIndex, Duration x) throws SQLException {
        Interval iv = Interval.fromDuration(x);
        byte[] buf = SqlLiterals.serialize(iv);
        updateBytes(columnIndex, buf);
    }


    private Geometry getGeometryFromInputStream(InputStream inputStream) throws Exception {
        Geometry geometry = null;
        if (inputStream != null) {
            byte[] buffer = new byte[255];
            int bytesRead = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            byte[] geometryAsBytes = baos.toByteArray();


            byte[] sridBytes = new byte[4];
            System.arraycopy(geometryAsBytes, 0, sridBytes, 0, 4);
            boolean bigEndian = (geometryAsBytes[4] == 0);

            int srid = 0;
            if (bigEndian) {
                for (int i = 0; i < sridBytes.length; i++) {
                    srid = (srid << 8) + (sridBytes[i] & 0xFF);
                }
            } else {
                for (int i = 0; i < sridBytes.length; i++) {
                    srid += (sridBytes[i] & 0xFF) << 8 * i;
                }
            }

            WKBReader wkbReader = new WKBReader();
            byte[] wkb = new byte[geometryAsBytes.length - 4];
            System.arraycopy(geometryAsBytes, 4, wkb, 0, wkb.length);
            geometry = wkbReader.read(wkb);
            geometry.setSRID(srid);
        }

        return geometry;
    }


    private byte[] readByteArray(InputStream inputStream) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buf = new byte[65535];
        try {
            int len;
            while ((len = inputStream.read(buf)) != -1) {
                os.write(buf, 0, len);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os.toByteArray();
    }


    private String readString(Reader reader) {
        StringBuilder builder = new StringBuilder();
        try {
            int c = -1;
            char[] chars = new char[65535];
            do {
                c = reader.read(chars, 0, chars.length);
                if (c <= 0)
                    continue;
                builder.append(chars, 0, c);
            }
            while (c > 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }


    public String getCursorName() throws SQLException {
        String s = null;
        try {
            s = super.getCursorName();
        } catch (SQLFeatureNotSupportedException sfnse) {
            throw sfnse;
        } catch (SQLException se) {
            throw new SQLFeatureNotSupportedException("getCursorName not supported!", se);
        }
        return s;
    }


    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {

        try {
            super.updateAsciiStream(columnIndex, x);
        } catch (SQLFeatureNotSupportedException sfnse) {
            throw sfnse;
        } catch (SQLException se) {
            throw new SQLFeatureNotSupportedException("updateAsciiStream not supported!", se);
        }

    }


    public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {

        try {
            super.updateAsciiStream(columnIndex, x, length);
        } catch (SQLFeatureNotSupportedException sfnse) {
            throw sfnse;
        } catch (SQLException se) {
            throw new SQLFeatureNotSupportedException("updateAsciiStream not supported!", se);
        }

    }


    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {

        try {
            super.updateAsciiStream(columnIndex, x, length);
        } catch (SQLFeatureNotSupportedException sfnse) {
            throw sfnse;
        } catch (SQLException se) {
            throw new SQLFeatureNotSupportedException("updateAsciiStream not supported!", se);
        }

    }

    public URL updateURL(int columnIndex, URL url) throws SQLException {
        super.updateObject(columnIndex, url.getPath());
        return url;
    }

    public URL updateURL(String columnLabel, URL url) throws SQLException {
        updateURL(findColumn(columnLabel), url);
        return url;
    }


    public void close() throws SQLException {
        MySqlStatement stmt = (MySqlStatement) getStatement();
        super.close();
        stmt.removePrimaryColumn(this._qiTable, this._sPrimaryColumn);
    }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\jdbcmysql-2.2.2.jar!\ch\admin\bar\siard2\jdbc\MySqlResultSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */