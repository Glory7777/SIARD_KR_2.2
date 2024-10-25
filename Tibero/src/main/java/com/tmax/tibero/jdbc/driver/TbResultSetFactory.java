package com.tmax.tibero.jdbc.driver;

import com.tmax.tibero.jdbc.data.RsetType;
import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;

public class TbResultSetFactory {
  public static TbResultSet buildResultSet(TbStatement paramTbStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfbyte) throws SQLException {
    TbRSFwOnly tbRSFwOnly;
    TbRSScrollable tbRSScrollable;
    TbRSSensitive tbRSSensitive;
    if (paramTbStatement == null)
      throw TbError.newSQLException(-90608); 
    RsetType rsetType = paramTbStatement.getRealRsetType();
    switch (rsetType.getRank()) {
      case 2:
        tbRSFwOnly = new TbRSFwOnly(paramTbStatement, paramInt1, paramInt2, paramInt3, paramArrayOfbyte);
        return new TbRSUpdatable(tbRSFwOnly, RsetType.FWUP);
      case 3:
        return new TbRSScrollable(paramTbStatement, paramInt1, paramInt2, paramInt3, paramArrayOfbyte);
      case 4:
        tbRSScrollable = new TbRSScrollable(paramTbStatement, paramInt1, paramInt2, paramInt3, paramArrayOfbyte);
        return new TbRSUpdatable(tbRSScrollable, RsetType.SIUP);
      case 5:
        return new TbRSSensitive(paramTbStatement, paramInt1, paramInt2, paramInt3, paramArrayOfbyte);
      case 6:
        tbRSSensitive = new TbRSSensitive(paramTbStatement, paramInt1, paramInt2, paramInt3, paramArrayOfbyte);
        return new TbRSUpdatable(tbRSSensitive, RsetType.SSUP);
    } 
    return new TbRSFwOnly(paramTbStatement, paramInt1, paramInt2, paramInt3, paramArrayOfbyte);
  }
  
  public static TbResultSet buildEmptyResultSet(TbStatement paramTbStatement, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    return new TbRSFwOnly(paramTbStatement, paramInt1, paramInt2, paramInt3, null);
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\driver\TbResultSetFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */