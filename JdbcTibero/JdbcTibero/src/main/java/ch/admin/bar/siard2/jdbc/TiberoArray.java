package ch.admin.bar.siard2.jdbc;

import ch.enterag.sqlparser.SqlLiterals;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.logging.IndentLogger;
import oracle.sql.ARRAY;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Map;

public class TiberoArray implements Array {
    private static IndentLogger _il = IndentLogger.getIndentLogger(TiberoArray.class.getName());
    private ARRAY _tarray = null;
    private long _lMaxLength = -1L;

}
