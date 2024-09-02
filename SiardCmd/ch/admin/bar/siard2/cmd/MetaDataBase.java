package ch.admin.bar.siard2.cmd;
import ch.admin.bar.siard2.api.MetaData;
import ch.enterag.sqlparser.identifier.QualifiedId;
import java.sql.Array;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Set;

public abstract class MetaDataBase {
  protected DatabaseMetaData _dmd = null;
  protected MetaData _md = null;
  protected int _iQueryTimeoutSeconds = 30; private boolean _bSupportsArrays = false; public void setQueryTimeout(int iQueryTimeoutSeconds) {
    this._iQueryTimeoutSeconds = iQueryTimeoutSeconds;
  } private boolean _bSupportsDistincts = false; public boolean supportsArrays() {
    return this._bSupportsArrays;
  } public boolean supportsDistincts() {
    return this._bSupportsDistincts;
  } private boolean _bSupportsUdts = false; public boolean supportsUdts() {
    return this._bSupportsUdts;
  } private Set<QualifiedId> _setUsedTypes = null;








  
  private boolean isUsedInColumn(QualifiedId qiType) throws SQLException {
    if (this._setUsedTypes == null) {
      
      this._setUsedTypes = new HashSet<>();
      ResultSet rs = this._dmd.getColumns(null, "%", "%", "%");
      while (rs.next()) {
        
        int iDataType = rs.getInt("DATA_TYPE");
        if (iDataType == 2001 || iDataType == 2002) {
          
          String sTypeName = rs.getString("TYPE_NAME");
          QualifiedId qi = null; 
          try { qi = new QualifiedId(sTypeName); }
          catch (ParseException pe) { throw new SQLException(sTypeName + " could not be parsed!", pe); }
           if (qi != null)
            this._setUsedTypes.add(qi); 
        } 
      } 
      rs.close();
    } 
    return this._setUsedTypes.contains(qiType);
  }







  
  protected MetaDataBase(DatabaseMetaData dmd, MetaData md) throws SQLException {
    this._dmd = dmd;
    this._md = md;
    
    ResultSet rs = this._dmd.getUDTs(null, "%", "%", null);
    while (rs.next() && (!this._bSupportsUdts || !this._bSupportsDistincts)) {
      
      String sTypeSchema = rs.getString("TYPE_SCHEM");
      String sTypeName = rs.getString("TYPE_NAME");
      QualifiedId qiType = new QualifiedId(null, sTypeSchema, sTypeName);
      if (isUsedInColumn(qiType)) {
        
        int iDataType = rs.getInt("DATA_TYPE");
        if (iDataType == 2002) {
          this._bSupportsUdts = true; continue;
        }  if (iDataType == 2001)
          this._bSupportsDistincts = true; 
      } 
    } 
    rs.close();
    
    try {
      Array array = this._dmd.getConnection().createArrayOf("INTEGER", (Object[])new Integer[] { Integer.valueOf(1), Integer.valueOf(2) });
      array.free();
      this._bSupportsArrays = true;
    } catch (SQLFeatureNotSupportedException sfnse) {
      this._bSupportsArrays = false;
    } 
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardcmd.jar!\ch\admin\bar\siard2\cmd\MetaDataBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */