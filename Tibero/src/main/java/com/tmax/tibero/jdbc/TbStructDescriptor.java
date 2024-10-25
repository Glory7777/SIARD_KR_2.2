package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.data.UdtAttrMeta;
import com.tmax.tibero.jdbc.data.UdtHierarchyInfo;
import com.tmax.tibero.jdbc.data.UdtInfo;
import com.tmax.tibero.jdbc.data.UdtMeta;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.driver.TbResultSetBase;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.util.TbDatabaseMetaQuery;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Map;

public class TbStructDescriptor extends TbTypeDescriptor {
  private TbConnection conn;
  
  private int memberNo;
  
  private int numOfFields;
  
  private int[] attributeTypes;
  
  private String[] attributeTypeNames;
  
  private String[] attributeOIDs;
  
  private boolean isFinal = true;
  
  public static final String _DESC_OID_PREFIX = "/O";
  
  public static final String _DESC_TOBJ_ID_PREFIX = "/T";
  
  public static final String _DESC_VERSION_NO_PREFIX = "/V";
  
  protected TbStructDescriptor(String paramString, Connection paramConnection) throws SQLException {
    init(32, null, paramString.toUpperCase());
    this.conn = (TbConnection)paramConnection;
  }
  
  protected TbStructDescriptor(int paramInt, String paramString1, String paramString2, int[] paramArrayOfint, String[] paramArrayOfString1, String[] paramArrayOfString2, TbConnection paramTbConnection) throws SQLException {
    init(paramInt, paramString1, paramString2);
    this.attributeTypes = paramArrayOfint;
    this.numOfFields = paramArrayOfint.length;
    this.attributeTypeNames = paramArrayOfString1;
    this.attributeOIDs = paramArrayOfString2;
    this.conn = paramTbConnection;
  }
  
  public static TbStructDescriptor createDescriptor(TbRef paramTbRef, Connection paramConnection) throws SQLException {
    if (!(paramConnection instanceof TbConnection))
      throw TbError.newSQLException(-90608, "conn[" + paramConnection + "]"); 
    TbConnection tbConnection = (TbConnection)paramConnection;
    String str = paramTbRef.getHexTOID();
    Object object = tbConnection.getDescriptor(str);
    if (object instanceof TbStructDescriptor)
      return (TbStructDescriptor)object; 
    if (object != null)
      throw TbError.newSQLException(-90666); 
    return lookupUdtMetaWithTOID(paramTbRef.getHexTOID(), tbConnection);
  }
  
  public static TbStructDescriptor createDescriptor(String paramString, Connection paramConnection) throws SQLException {
    if (paramString == null || paramString.length() == 0)
      throw TbError.newSQLException(-90608, "sqlTypeName[" + paramString + "]"); 
    TbConnection tbConnection = (TbConnection)paramConnection;
    if (!tbConnection.getExtFeatureInfo().supports(2))
      throw TbError.newSQLException(-90203); 
    String str = paramString.toUpperCase();
    Object object = tbConnection.getDescriptor(str);
    if (object instanceof TbStructDescriptor)
      return (TbStructDescriptor)object; 
    if (object != null)
      throw TbError.newSQLException(-90666); 
    String[] arrayOfString = TbTypeDescriptor.splitSQLTypeName(str);
    if (arrayOfString.length != 2)
      throw TbError.newSQLException(-90608, "sqlTypeName[" + paramString + "]"); 
    return lookupUdtMeta(arrayOfString[0], arrayOfString[1], tbConnection);
  }
  
  public static TbStructDescriptor createDescriptor(int paramInt, String paramString, Connection paramConnection) throws SQLException {
    if (paramString == null || paramString.length() == 0)
      throw TbError.newSQLException(-90608, "OID(hex)[" + paramString + "]"); 
    TbConnection tbConnection = (TbConnection)paramConnection;
    Object object = tbConnection.getDescriptor("/O" + paramString);
    if (object instanceof TbStructDescriptor)
      return (TbStructDescriptor)object; 
    if (object != null)
      throw TbError.newSQLException(-90666); 
    return lookupUdtMeta(paramString, tbConnection);
  }
  
  public static TbStructDescriptor createDescriptor(int paramInt1, String paramString, int paramInt2, int paramInt3, Connection paramConnection) throws SQLException {
    if (paramString == null || paramString.length() == 0)
      throw TbError.newSQLException(-90608, "OID(hex)[" + paramString + "]"); 
    if (!(paramConnection instanceof TbConnection))
      throw TbError.newSQLException(-90608, "conn[" + paramConnection + "]"); 
    TbConnection tbConnection = (TbConnection)paramConnection;
    Object object = tbConnection.getDescriptor("/O" + paramString + "/T" + paramInt2 + "/V" + paramInt3);
    if (object instanceof TbStructDescriptor)
      return (TbStructDescriptor)object; 
    if (object != null)
      throw TbError.newSQLException(-90666); 
    return lookupUdtMeta(paramString, tbConnection);
  }
  
  public static TbStructDescriptor lookupUdtMetaWithTOID(String paramString, TbConnection paramTbConnection) throws SQLException {
    if (paramString == null || paramString.length() == 0)
      throw TbError.newSQLException(-90608, "OID(hex)[" + paramString + "]"); 
    PreparedStatement preparedStatement = paramTbConnection.prepareStatement(TbDatabaseMetaQuery.QUERY_OBJUDTMETA__BY_OBJTBLID);
    preparedStatement.setString(1, paramString);
    ResultSet resultSet = preparedStatement.executeQuery();
    return lookupUdtMetaInternal((TbResultSetBase)resultSet, paramTbConnection);
  }
  
  public static TbStructDescriptor lookupUdtMeta(String paramString, TbConnection paramTbConnection) throws SQLException {
    if ("00000000000000000000000000000009".equalsIgnoreCase(paramString))
      return null; 
    if (paramString == null || paramString.length() == 0)
      throw TbError.newSQLException(-90608, "OID(hex)[" + paramString + "]"); 
    PreparedStatement preparedStatement = paramTbConnection.prepareStatement(TbDatabaseMetaQuery.QUERY_UDTMETA__BY_OID);
    preparedStatement.setString(1, paramString);
    ResultSet resultSet = preparedStatement.executeQuery();
    return lookupUdtMetaInternal((TbResultSetBase)resultSet, paramTbConnection);
  }
  
  public static TbStructDescriptor lookupUdtMeta(String paramString1, String paramString2, TbConnection paramTbConnection) throws SQLException {
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
  
  private static TbStructDescriptor lookupUdtMetaInternal(TbResultSetBase paramTbResultSetBase, TbConnection paramTbConnection) throws SQLException {
    if (!paramTbResultSetBase.next())
      throw TbError.newSQLException(-90665); 
    try {
      Object object = paramTbResultSetBase.getObject(1, TbTypeDescriptor.getUdtMeta2ClsMap());
      if (!(object instanceof UdtMeta))
        throw TbError.newSQLException(-90664); 
      UdtMeta udtMeta = (UdtMeta)object;
      int i = udtMeta.getUdtId().getVersionNo();
      int j = udtMeta.getUdtId().getTobjID();
      Object[] arrayOfObject1 = (Object[])udtMeta.getAttrMetaArr().getArray();
      Object[] arrayOfObject2 = (Object[])udtMeta.getHierarchyInfoArr().getArray();
      TbStructDescriptor tbStructDescriptor = null;
      for (byte b = 0; b < udtMeta.getHierarchyCnt(); b++) {
        UdtHierarchyInfo udtHierarchyInfo = (UdtHierarchyInfo)arrayOfObject2[b];
        byte[] arrayOfByte = udtHierarchyInfo.getProperties();
        UdtInfo udtInfo = udtHierarchyInfo.getUdtId();
        BigDecimal[] arrayOfBigDecimal = (BigDecimal[])udtHierarchyInfo.getAttrIdxArr().getArray();
        int[] arrayOfInt = new int[arrayOfBigDecimal.length];
        String[] arrayOfString1 = new String[arrayOfBigDecimal.length];
        String[] arrayOfString2 = new String[arrayOfBigDecimal.length];
        String str = udtInfo.getSchemaName() + "." + udtInfo.getUdtName();
        for (byte b1 = 0; b1 < arrayOfBigDecimal.length; b1++) {
          UdtAttrMeta udtAttrMeta = (UdtAttrMeta)arrayOfObject1[arrayOfBigDecimal[b1].intValue() - 1];
          arrayOfInt[b1] = udtAttrMeta.getTypeNo();
          arrayOfString2[b1] = udtAttrMeta.getOid();
        } 
        TbStructDescriptor tbStructDescriptor1 = new TbStructDescriptor(udtInfo.getTypeNo(), udtInfo.getOid(), str, arrayOfInt, arrayOfString1, arrayOfString2, paramTbConnection);
        tbStructDescriptor1.setVersionNo(i);
        tbStructDescriptor1.setTobjID(j);
        tbStructDescriptor1.setIsFinal(arrayOfByte);
        if (b == 0)
          tbStructDescriptor = tbStructDescriptor1; 
        paramTbConnection.putDescriptor(str, tbStructDescriptor1);
        paramTbConnection.putDescriptor("/O" + udtInfo.getOid(), tbStructDescriptor1);
      } 
      return tbStructDescriptor;
    } catch (SQLException sQLException) {
      int i = sQLException.getErrorCode();
      if ((i <= -90400 && i > -90500) || i == -90664)
        throw sQLException; 
      throw TbError.newSQLException(-90664, sQLException);
    } catch (Exception exception) {
      throw TbError.newSQLException(-90664, exception);
    } 
  }
  
  public SQLInput toSQLInput(TbStruct paramTbStruct, Map<String, Class<?>> paramMap) throws SQLException {
    return new TbSQLInput(paramTbStruct, this, paramMap);
  }
  
  public SQLOutput toSQLOutput() {
    return new TbSQLOutput(this, (TbConnection)this.conn);
  }
  
  public int getNumOfFields() {
    return this.numOfFields;
  }
  
  public String[] getAttributeOIDs() {
    return this.attributeOIDs;
  }
  
  public int[] getAttributeTypes() {
    return this.attributeTypes;
  }
  
  public String[] getAttributeTypeNames() {
    return this.attributeTypeNames;
  }
  
  public int getMemberNo() {
    return this.memberNo;
  }
  
  public Class<?> getClass(Map<String, Class<?>> paramMap) throws SQLException {
    String str = getSQLTypeName();
    Class<?> clazz = paramMap.get(str);
    if (clazz == null && this.conn != null) {
      Object object = this.conn.getTypeMap().get(str);
      if (object instanceof Class)
        clazz = (Class)object; 
    } 
    return clazz;
  }
  
  public Class<?> getClassWithExplicitMap(Map<String, Class<?>> paramMap) throws SQLException {
    String str = getSQLTypeName();
    return paramMap.get(str);
  }
  
  public boolean isFinal() {
    return this.isFinal;
  }
  
  public void setIsFinal(byte[] paramArrayOfbyte) throws SQLException {
    if (paramArrayOfbyte[0] == 1) {
      this.isFinal = true;
    } else if (paramArrayOfbyte[0] == 0) {
      this.isFinal = false;
    } else {
      throw TbError.newSQLException(-90664);
    } 
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\TbStructDescriptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */