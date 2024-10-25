package ch.admin.bar.siard2.tibero.datatype;

import ch.admin.bar.siard2.jdbc.TiberoConnection;
import ch.admin.bar.siard2.tibero.TiberoSqlFactory;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.identifier.QualifiedId;
import com.tmax.tibero.jdbc.TbArray;

import java.sql.Connection;
import java.sql.SQLException;

public class TiberoDataType extends DataType {
    protected String formatArrayType() {
        String sDataType = null;
        String sBaseType = this.getDataType().format();
        int iLength = this.getLength();
        if (iLength == -1) {
            throw new IllegalArgumentException("Tibero cannot handle arrays of undefined length!");
        } else {
            try {
                TiberoSqlFactory tsf = (TiberoSqlFactory)this.getSqlFactory();
                TiberoConnection tconn = tsf.getConnection();
                com.tmax.tibero.jdbc.TbConnection conn = (com.tmax.tibero.jdbc.TbConnection)tconn.unwrap(Connection.class);
                QualifiedId qiVarray = TbArray.findOrCreateVarray(conn, sBaseType, iLength);
                sDataType = qiVarray.quote();
                return sDataType;
            } catch (SQLException var8) {
                SQLException se = var8;
                throw new IllegalArgumentException(se);
            }
        }
    }

    public TiberoDataType(SqlFactory sf) {
        super(sf);
    }
}
