package ch.admin.bar.siard2.cmd;

import ch.admin.bar.siard2.api.Archive;
import ch.admin.bar.siard2.api.MetaTable;
import ch.admin.bar.siard2.api.Table;
import ch.enterag.sqlparser.SqlLiterals;
import ch.enterag.sqlparser.identifier.QualifiedId;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PrimaryDataTransfer {
    private static final Logger LOG = LoggerFactory.getLogger(PrimaryDataTransfer.class);


    protected Connection _conn = null;
    protected Archive _archive = null;
    protected ArchiveMapping _am = null;
    protected int _iQueryTimeoutSeconds = 30;
    protected boolean _bSupportsArrays = false;

    public void setQueryTimeout(int iQueryTimeoutSeconds) {
        this._iQueryTimeoutSeconds = iQueryTimeoutSeconds;
    }

    protected boolean _bSupportsDistincts = false;

    public boolean supportsArrays() {
        return this._bSupportsArrays;
    }

    public boolean supportsDistincts() {
        return this._bSupportsDistincts;
    }

    protected boolean _bSupportsUdts = false;

    public boolean supportsUdts() {
        return this._bSupportsUdts;
    }


    protected ResultSet openTable(Table table, SchemaMapping sm) throws IOException, SQLException {
        MetaTable mt = table.getMetaTable();

        TableMapping tm = null;
        if (sm != null) {
            tm = sm.getTableMapping(mt.getName());
        }
        StringBuilder sbSql = new StringBuilder("SELECT\r\n");
        List<List<String>> llColumnNames = mt.getColumnNames(supportsArrays(), supportsUdts());
        for (int iColumn = 0; iColumn < llColumnNames.size(); iColumn++) {

            if (iColumn > 0)
                sbSql.append(",\r\n");
            sbSql.append("  ");
            List<String> listColumnName = llColumnNames.get(iColumn);
            StringBuilder sbColumnName = new StringBuilder();
            for (int i = 0; i < listColumnName.size(); i++) {

                if (i > 0)
                    sbColumnName.append(".");
                sbColumnName.append(listColumnName.get(i));
            }
            String sExtendedColumnName = sbColumnName.toString();
            if (tm != null)
                sExtendedColumnName = tm.getMappedExtendedColumnName(sExtendedColumnName);
            sbSql.append(SqlLiterals.formatId(sExtendedColumnName));
        }
        String sSchemaName = mt.getParentMetaSchema().getName();
        if (sm != null)
            sSchemaName = sm.getMappedSchemaName();
        String sTableName = mt.getName();
        if (tm != null)
            sTableName = tm.getMappedTableName();
        QualifiedId qiTable = new QualifiedId(null, sSchemaName, sTableName);
        sbSql.append("\r\n FROM " + qiTable.format());

        String sqlStatement = sbSql.toString();
        LOG.trace("SQL statement: '{}'", sqlStatement);

        int iHoldability = 1;
        if (sm == null)
            iHoldability = 1;
        int iConcurrency = 1008;
        if (sm == null)
            iConcurrency = 1007;
        int iType = 1003;
        if (sm == null)
            iType = 1003;
        this._conn.setHoldability(iHoldability);
        Statement stmt = this._conn.createStatement(iType, iConcurrency, iHoldability);
        stmt.setQueryTimeout(this._iQueryTimeoutSeconds);
        ResultSet rs = stmt.executeQuery(sbSql.toString());

        LOG.debug("Data from table '{}.{}' successfully loaded", qiTable.getSchema(), qiTable.getName());

        return rs;
    }


    protected PrimaryDataTransfer(Connection conn, Archive archive, ArchiveMapping am, boolean bSupportsArrays, boolean bSupportsDistincts, boolean bSupportsUdts) {
        this._conn = conn;
        this._archive = archive;
        this._am = am;
        this._bSupportsArrays = bSupportsArrays;
        this._bSupportsDistincts = bSupportsDistincts;
        this._bSupportsUdts = bSupportsUdts;
    }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardcmd.jar!\ch\admin\bar\siard2\cmd\PrimaryDataTransfer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */