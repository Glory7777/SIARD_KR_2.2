package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.data.DataType;
import com.tmax.tibero.jdbc.data.UdtAttrMeta;
import com.tmax.tibero.jdbc.data.UdtInfo;
import com.tmax.tibero.jdbc.data.UdtMeta;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.driver.TbResultSetBase;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.util.TbDatabaseMetaQuery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TbArrayDescriptor extends TbTypeDescriptor {
  public static final String _DESC_OID_PREFIX = "/O";
  
  public static final String _DESC_TOBJ_ID_PREFIX = "/T";
  
  public static final String _DESC_VERSION_NO_PREFIX = "/V";
  
  private TbConnection conn;
  
  private int elementType;
  
  private String elementTypeName;
  
  private int elementsLimit;
  
  protected TbArrayDescriptor(String paramString, Connection paramConnection) throws SQLException {
    init(29, null, paramString.toUpperCase());
    this.conn = (TbConnection)paramConnection;
  }
  
  protected TbArrayDescriptor(int paramInt1, String paramString1, String paramString2, int paramInt2, String paramString3, int paramInt3, TbConnection paramTbConnection) throws SQLException {
    init(paramInt1, paramString1, paramString2);
    this.elementType = paramInt2;
    this.elementTypeName = paramString3;
    this.elementsLimit = paramInt3;
    this.conn = paramTbConnection;
  }
  
  public static TbArrayDescriptor createDescriptor(String paramString, Connection paramConnection) throws SQLException {
    if (paramString == null || paramString.length() == 0)
      throw TbError.newSQLException(-90608, "sqlTypeName[" + paramString + "]"); 
    if (!(paramConnection instanceof TbConnection))
      throw TbError.newSQLException(-90608, "conn[" + paramConnection + "]"); 
    TbConnection tbConnection = (TbConnection)paramConnection;
    if (!tbConnection.getExtFeatureInfo().supports(2))
      throw TbError.newSQLException(-90203); 
    String str = paramString.toUpperCase();
    Object object = tbConnection.getDescriptor(str);
    if (object instanceof TbArrayDescriptor)
      return (TbArrayDescriptor)object; 
    if (object != null)
      throw TbError.newSQLException(-90666); 
    String[] arrayOfString = TbTypeDescriptor.splitSQLTypeName(str);
    if (arrayOfString.length != 2)
      throw TbError.newSQLException(-90608, "sqlTypeName[" + paramString + "]"); 
    return lookupUdtMeta(arrayOfString[0], arrayOfString[1], tbConnection);
  }
  
  public static TbArrayDescriptor createDescriptor(int paramInt, String paramString, Connection paramConnection) throws SQLException {
    if (paramString == null || paramString.length() == 0)
      throw TbError.newSQLException(-90608, "OID[" + paramString + "]"); 
    if (!(paramConnection instanceof TbConnection))
      throw TbError.newSQLException(-90608, "conn[" + paramConnection + "]"); 
    TbConnection tbConnection = (TbConnection)paramConnection;
    Object object = tbConnection.getDescriptor("/O" + paramString);
    if (object instanceof TbArrayDescriptor)
      return (TbArrayDescriptor)object; 
    if (object != null)
      throw TbError.newSQLException(-90666); 
    return lookupUdtMeta(paramString, tbConnection);
  }
  
  public static TbArrayDescriptor createDescriptor(int paramInt1, String paramString, int paramInt2, int paramInt3, Connection paramConnection) throws SQLException {
    if (paramString == null || paramString.length() == 0)
      throw TbError.newSQLException(-90608, "OID[" + paramString + "]"); 
    if (!(paramConnection instanceof TbConnection))
      throw TbError.newSQLException(-90608, "conn[" + paramConnection + "]"); 
    TbConnection tbConnection = (TbConnection)paramConnection;
    Object object = tbConnection.getDescriptor("/O" + paramString + "/T" + paramInt2 + "/V" + paramInt3);
    if (object instanceof TbArrayDescriptor)
      return (TbArrayDescriptor)object; 
    if (object != null)
      throw TbError.newSQLException(-90666); 
    return lookupUdtMeta(paramString, tbConnection);
  }
  
  public int getBaseType() throws SQLException {
    return DataType.getSqlType(this.elementType);
  }
  
  public int getElementType() {
    return this.elementType;
  }
  
  public String getElementTypeName() {
    return this.elementTypeName;
  }
  
  public int getElementsLimit() {
    return this.elementsLimit;
  }
  
  public static TbArrayDescriptor lookupUdtMeta(String paramString1, String paramString2, TbConnection paramTbConnection) throws SQLException {
    if ("SYS.UDT_META_T".equalsIgnoreCase(paramString1 + "." + paramString2))
      return null; 
    if (paramString2 == null || paramString2.length() == 0)
      throw TbError.newSQLException(-90608, "typeName[" + paramString2 + "]"); 
    PreparedStatement preparedStatement = paramTbConnection.prepareStatement(TbDatabaseMetaQuery.QUERY_UDTMETA__BY_TYPENAME);
    preparedStatement.setString(1, paramString1);
    preparedStatement.setString(2, paramString2);
    ResultSet resultSet = preparedStatement.executeQuery();
    return lookupUdtMetaInternal((TbResultSetBase)resultSet, paramTbConnection);
  }
  
  public static TbArrayDescriptor lookupUdtMeta(String paramString, TbConnection paramTbConnection) throws SQLException {
    if ("00000000000000000000000000000009".equalsIgnoreCase(paramString))
      return null; 
    if (paramString == null || paramString.length() == 0)
      throw TbError.newSQLException(-90608, "OID(hex)[" + paramString + "]"); 
    PreparedStatement preparedStatement = paramTbConnection.prepareStatement(TbDatabaseMetaQuery.QUERY_UDTMETA__BY_OID);
    preparedStatement.setString(1, paramString);
    ResultSet resultSet = preparedStatement.executeQuery();
    return lookupUdtMetaInternal((TbResultSetBase)resultSet, paramTbConnection);
  }
  
  private static TbArrayDescriptor lookupUdtMetaInternal(TbResultSetBase paramTbResultSetBase, TbConnection paramTbConnection) throws SQLException {
    if (!paramTbResultSetBase.next())
      throw TbError.newSQLException(-90665); 
    try {
      Object object = paramTbResultSetBase.getObject(1, TbTypeDescriptor.getUdtMeta2ClsMap());
      if (!(object instanceof UdtMeta))
        throw TbError.newSQLException(-90664); 
      UdtMeta udtMeta = (UdtMeta)object;
      UdtInfo udtInfo = udtMeta.getUdtId();
      int i = udtInfo.getVersionNo();
      int j = udtInfo.getTobjID();
      Object[] arrayOfObject = (Object[])udtMeta.getAttrMetaArr().getArray();
      UdtAttrMeta udtAttrMeta = (UdtAttrMeta)arrayOfObject[0];
      String str1 = udtInfo.getSchemaName() + "." + udtInfo.getUdtName();
      String str2 = udtAttrMeta.getOid();
      TbArrayDescriptor tbArrayDescriptor = new TbArrayDescriptor(udtInfo.getTypeNo(), udtInfo.getOid(), str1, udtAttrMeta.getTypeNo(), str2, udtMeta.getElemOrAttrCnt(), paramTbConnection);
      tbArrayDescriptor.setVersionNo(i);
      tbArrayDescriptor.setTobjID(j);
      paramTbConnection.putDescriptor(str1, tbArrayDescriptor);
      paramTbConnection.putDescriptor("/O" + udtInfo.getOid(), tbArrayDescriptor);
      return tbArrayDescriptor;
    } catch (SQLException sQLException) {
      int i = sQLException.getErrorCode();
      if ((i <= -90400 && i > -90500) || i == -90664)
        throw sQLException; 
      throw TbError.newSQLException(-90664, sQLException);
    } catch (Exception exception) {
      throw TbError.newSQLException(-90664, exception);
    } 
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\TbArrayDescriptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */