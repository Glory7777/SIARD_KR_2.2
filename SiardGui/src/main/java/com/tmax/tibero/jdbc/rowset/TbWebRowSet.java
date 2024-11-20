package com.tmax.tibero.jdbc.rowset;

import com.tmax.tibero.jdbc.err.TbError;

import javax.sql.rowset.WebRowSet;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TbWebRowSet extends TbCachedRowSet implements WebRowSet {
    private static final long serialVersionUID = -173088871660903546L;

    public TbWebRowSet() throws SQLException {
    }

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