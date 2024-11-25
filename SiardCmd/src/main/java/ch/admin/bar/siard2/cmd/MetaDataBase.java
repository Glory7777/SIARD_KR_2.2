package ch.admin.bar.siard2.cmd;

import ch.admin.bar.dbexception.proxy.ConnectionProxy;
import ch.admin.bar.siard2.api.MetaData;
import ch.enterag.sqlparser.identifier.QualifiedId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.text.ParseException;
import java.util.*;

import static ch.admin.bar.siard2.cmd.MetaDataBase.DataBase.isTibero;

public abstract class MetaDataBase {
    protected DatabaseMetaData _dmd = null;
    protected MetaData _md = null;
    protected int _iQueryTimeoutSeconds = 30;
    private boolean _bSupportsArrays = false;
    private boolean _bSupportsDistincts = false;
    private boolean _bSupportsUdts = false;
    private Set<QualifiedId> _setUsedTypes = null;

    public void setQueryTimeout(int iQueryTimeoutSeconds) {
        this._iQueryTimeoutSeconds = iQueryTimeoutSeconds;
    }

    public boolean supportsArrays() {
        return this._bSupportsArrays;
    }

    public boolean supportsDistincts() {
        return this._bSupportsDistincts;
    }

    public boolean supportsUdts() {
        return this._bSupportsUdts;
    }

    private boolean isUsedInColumn(QualifiedId qiType) throws SQLException {
        if (this._setUsedTypes == null) {
            this._setUsedTypes = new HashSet();
            ResultSet rs = this._dmd.getColumns(null, "%", "%", "%");

            while (true) {
                int iDataType;
                do {
                    if (!rs.next()) {
                        rs.close();
                        return this._setUsedTypes.contains(qiType);
                    }

                    iDataType = rs.getInt("DATA_TYPE");
                } while (iDataType != 2001 && iDataType != 2002);

                String sTypeName = rs.getString("TYPE_NAME");
                QualifiedId qi = null;

                try {
                    qi = new QualifiedId(sTypeName);
                } catch (ParseException var7) {
                    ParseException pe = var7;
                    throw new SQLException(sTypeName + " could not be parsed!", pe);
                }

                if (qi != null) {
                    this._setUsedTypes.add(qi);
                }
            }
        } else {
            return this._setUsedTypes.contains(qiType);
        }
    }

    protected MetaDataBase(DatabaseMetaData dmd, MetaData md) throws SQLException {
        this._dmd = dmd;
        this._md = md;
        ResultSet rs = this._dmd.getUDTs(null, "%", "%", null);

        while (rs.next() && (!this._bSupportsUdts || !this._bSupportsDistincts)) {
            String sTypeSchema = rs.getString("TYPE_SCHEM");
            String sTypeName = rs.getString("TYPE_NAME");
            QualifiedId qiType = new QualifiedId(null, sTypeSchema, sTypeName);
            if (this.isUsedInColumn(qiType)) {
                int iDataType = rs.getInt("DATA_TYPE");
                if (iDataType == 2002) {
                    this._bSupportsUdts = true;
                } else if (iDataType == 2001) {
                    this._bSupportsDistincts = true;
                }
            }
        }

        rs.close();

        try {
            Connection connection = this._dmd.getConnection();
            String databaseProductName = connection.getMetaData().getDatabaseProductName();
            // tibero 인 경우 분기
            String typeName = isTibero(databaseProductName) ? "INTEGER_OBJ" : "INTEGER";
            Array array = connection.createArrayOf(typeName, new Integer[]{1, 2});
            array.free();
            this._bSupportsArrays = true;
        } catch (SQLFeatureNotSupportedException var8) {
            this._bSupportsArrays = false;
        }

    }

    @Getter
    @RequiredArgsConstructor
    public enum DataBase {
        MYSQL("mysql"),
        //        MYSQL_80("mysql", new Version("mysql", 8, 0)),
        ORACLE("oracle"),
        POSTGRESQL("postgresql"),
        MSSQL("mssql"),
        CUBRID("cubrid"),
        TIBERO("tibero"),

        ;

        final String name;
//        final Version version;

        public static DataBase findByName(String name) {
            if (name.isBlank()) throw new NoSuchElementException("No enum value is found");
            return Arrays.stream(DataBase.values())
                    .filter(dataBase -> dataBase.getName().equalsIgnoreCase(name))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("No value is found"));
        }

        public static boolean isTibero(String databaseProductName) {
            if (databaseProductName.isBlank()) throw new NoSuchElementException("No enum value is found");
            return Arrays.stream(DataBase.values())
                    .anyMatch(database -> DataBase.TIBERO.getName().equalsIgnoreCase(databaseProductName));
        }

    }
}
