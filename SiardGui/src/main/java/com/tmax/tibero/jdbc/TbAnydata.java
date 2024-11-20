package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.data.DataType;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.util.TbCommon;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Struct;

public class TbAnydata {
  boolean isNull;
  
  Blob largeData;
  
  byte[] data;
  
  byte[] type;
  
  private Struct struct;
  
  Connection conn = null;
  
  public TbAnydata(Struct paramStruct) throws SQLException {
    this.largeData = (Blob)paramStruct.getAttributes()[0];
    this.data = (byte[])paramStruct.getAttributes()[1];
    this.type = (byte[])paramStruct.getAttributes()[2];
    this.struct = paramStruct;
  }
  
  public static TbAnydata convertObject(Object paramObject, Connection paramConnection) throws SQLException {
    if (paramObject instanceof Blob || paramObject instanceof java.sql.Clob) {
      byte[] arrayOfByte = { 
          0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 
          0, Byte.MIN_VALUE, 0, 0, 0, 0, 1, 0, 0, 0, 
          0, 0, 0, 0, 0, -54, -54, -54, -54, -54, 
          -54, -54, -54, -54, -54, -54, -54, -54, -54, -54 };
      arrayOfByte[16] = (byte)DataType.getDataType(DataType.getSqlType(paramObject));
      if (paramObject instanceof TbClobBase) {
        TbClobBase tbClobBase = (TbClobBase)paramObject;
        TbConnection tbConnection = (TbConnection)paramConnection;
        TbBlob tbBlob = new TbBlob(tbConnection, tbClobBase.getLocator(), tbClobBase.freeTmpLobOnEOF());
        Object[] arrayOfObject1 = { tbBlob, null, arrayOfByte };
        Struct struct1 = paramConnection.createStruct("SYS.ANYDATA", arrayOfObject1);
        return new TbAnydata(struct1);
      } 
      Object[] arrayOfObject = { paramObject, null, arrayOfByte };
      Struct struct = paramConnection.createStruct("SYS.ANYDATA", arrayOfObject);
      return new TbAnydata(struct);
    } 
    String str = getTypeName(DataType.getDataType(DataType.getSqlType(paramObject)));
    CallableStatement callableStatement = paramConnection.prepareCall("BEGIN ? := SYS.ANYDATA.CONVERT" + str + "(?); END;");
    callableStatement.registerOutParameter(1, 2002, "SYS.ANYDATA");
    callableStatement.setObject(2, paramObject);
    callableStatement.execute();
    return new TbAnydata((Struct)callableStatement.getObject(1));
  }
  
  public static TbAnydata convertTimestampTZ(Object paramObject, Connection paramConnection) throws SQLException {
    CallableStatement callableStatement = paramConnection.prepareCall("BEGIN ? := SYS.ANYDATA.CONVERTTIMESTAMPTZ(?); END;");
    callableStatement.registerOutParameter(1, 2002, "SYS.ANYDATA");
    callableStatement.setObject(2, paramObject);
    callableStatement.execute();
    return new TbAnydata((Struct)callableStatement.getObject(1));
  }
  
  public static TbAnydata convertTimestampLTZ(Object paramObject, Connection paramConnection) throws SQLException {
    CallableStatement callableStatement = paramConnection.prepareCall("BEGIN ? := SYS.ANYDATA.CONVERTTIMESTAMPLTZ(?); END;");
    callableStatement.registerOutParameter(1, 2002, "SYS.ANYDATA");
    callableStatement.setObject(2, paramObject);
    callableStatement.execute();
    return new TbAnydata((Struct)callableStatement.getObject(1));
  }
  
  public static TbAnydata convertChar(Object paramObject, Connection paramConnection) throws SQLException {
    CallableStatement callableStatement = paramConnection.prepareCall("BEGIN ? := SYS.ANYDATA.CONVERTCHAR(?); END;");
    callableStatement.registerOutParameter(1, 2002, "SYS.ANYDATA");
    callableStatement.setObject(2, paramObject, 1);
    callableStatement.execute();
    return new TbAnydata((Struct)callableStatement.getObject(1));
  }
  
  public static TbAnydata convertNChar(Object paramObject, Connection paramConnection) throws SQLException {
    CallableStatement callableStatement = paramConnection.prepareCall("BEGIN ? := SYS.ANYDATA.CONVERTNCHAR(?); END;");
    callableStatement.registerOutParameter(1, 2002, "SYS.ANYDATA");
    callableStatement.setObject(2, paramObject, -15);
    callableStatement.execute();
    return new TbAnydata((Struct)callableStatement.getObject(1));
  }
  
  public static TbAnydata convertNVarchar(Object paramObject, Connection paramConnection) throws SQLException {
    CallableStatement callableStatement = paramConnection.prepareCall("BEGIN ? := SYS.ANYDATA.CONVERTNVARCHAR2(?); END;");
    callableStatement.registerOutParameter(1, 2002, "SYS.ANYDATA");
    callableStatement.setObject(2, paramObject, -9);
    callableStatement.execute();
    return new TbAnydata((Struct)callableStatement.getObject(1));
  }
  
  public static TbAnydata convertBFloat(Object paramObject, Connection paramConnection) throws SQLException {
    CallableStatement callableStatement = paramConnection.prepareCall("BEGIN ? := SYS.ANYDATA.CONVERTBFLOAT(?); END;");
    callableStatement.registerOutParameter(1, 2002, "SYS.ANYDATA");
    callableStatement.setObject(2, paramObject, 100);
    callableStatement.execute();
    return new TbAnydata((Struct)callableStatement.getObject(1));
  }
  
  public static TbAnydata convertBDouble(Object paramObject, Connection paramConnection) throws SQLException {
    CallableStatement callableStatement = paramConnection.prepareCall("BEGIN ? := SYS.ANYDATA.CONVERTBDOUBLE(?); END;");
    callableStatement.registerOutParameter(1, 2002, "SYS.ANYDATA");
    callableStatement.setObject(2, paramObject, 101);
    callableStatement.execute();
    return new TbAnydata((Struct)callableStatement.getObject(1));
  }
  
  public static TbAnydata convertIntervalYM(Object paramObject, Connection paramConnection) throws SQLException {
    CallableStatement callableStatement = paramConnection.prepareCall("BEGIN ? := SYS.ANYDATA.CONVERTINTERVALYM(?); END;");
    callableStatement.registerOutParameter(1, 2002, "SYS.ANYDATA");
    callableStatement.setObject(2, paramObject);
    callableStatement.execute();
    return new TbAnydata((Struct)callableStatement.getObject(1));
  }
  
  public static TbAnydata convertIntervalDS(Object paramObject, Connection paramConnection) throws SQLException {
    CallableStatement callableStatement = paramConnection.prepareCall("BEGIN ? := SYS.ANYDATA.CONVERTINTERVALDS(?); END;");
    callableStatement.registerOutParameter(1, 2002, "SYS.ANYDATA");
    callableStatement.setObject(2, paramObject);
    callableStatement.execute();
    return new TbAnydata((Struct)callableStatement.getObject(1));
  }
  
  public Object toJDBCObject(Connection paramConnection) throws SQLException {
    switch (TbCommon.bytes2Int(this.type, 13, 4)) {
      case 13:
        return new TbClob((TbConnection)paramConnection, ((TbBlob)this.largeData).getLocator(), ((TbBlob)this.largeData).freeTmpLobOnEOF());
      case 20:
        return new TbNClob((TbConnection)paramConnection, ((TbBlob)this.largeData).getLocator(), ((TbBlob)this.largeData).freeTmpLobOnEOF());
      case 12:
        return this.largeData;
    } 
    String str = getTypeName(TbCommon.bytes2Int(this.type, 13, 4));
    CallableStatement callableStatement = paramConnection.prepareCall("DECLARE L_ANY SYS.ANYDATA; BEGIN L_ANY := ?;  ? := L_ANY.GET" + str + "(?); END;");
    callableStatement.registerOutParameter(2, 4);
    if (str.equalsIgnoreCase("INTERVALYM") || str.equalsIgnoreCase("INTERVALDS")) {
      callableStatement.registerOutParameter(3, 12);
      callableStatement.setObject(1, this.struct);
      callableStatement.execute();
      return callableStatement.getObject(3);
    } 
    if (str.equalsIgnoreCase("OBJECT") || str.equalsIgnoreCase("COLLECTION")) {
      PreparedStatement preparedStatement = paramConnection.prepareStatement("SELECT OWNER, OBJECT_NAME FROM ALL_OBJECTS WHERE OBJECT_ID=?");
      preparedStatement.setInt(1, TbCommon.bytes2Int(this.type, 17, 4));
      ResultSet resultSet = preparedStatement.executeQuery();
      resultSet.next();
      String str1 = resultSet.getString(1) + "." + resultSet.getString(2);
      resultSet.close();
      callableStatement.registerOutParameter(3, DataType.getSqlType(TbCommon.bytes2Int(this.type, 13, 4)), str1);
      callableStatement.setObject(1, this.struct);
      callableStatement.execute();
      return (TbCommon.bytes2Int(this.type, 13, 4) == 32) ? callableStatement.getObject(3) : callableStatement.getArray(3);
    } 
    callableStatement.registerOutParameter(3, DataType.getSqlType(TbCommon.bytes2Int(this.type, 13, 4)));
    callableStatement.setObject(1, this.struct);
    callableStatement.execute();
    return callableStatement.getObject(3);
  }
  
  private static String getTypeName(int paramInt) throws SQLException {
    switch (paramInt) {
      case 2:
        return "CHAR";
      case 3:
        return "VARCHAR2";
      case 1:
        return "NUMBER";
      case 5:
        return "DATE";
      case 6:
        return "TIME";
      case 21:
        return "TIMESTAMPTZ";
      case 22:
        return "TIMESTAMPLTZ";
      case 7:
        return "TIMESTAMP";
      case 8:
        return "INTERVALYM";
      case 9:
        return "INTERVALDS";
      case 4:
        return "RAW";
      case 12:
        return "BLOB";
      case 13:
        return "CLOB";
      case 24:
        return "BDOUBLE";
      case 23:
        return "BFLOAT";
      case 18:
        return "NCHAR";
      case 19:
        return "NVARCHAR2";
      case 20:
        return "NCLOB";
      case 32:
        return "OBJECT";
      case 29:
      case 30:
        return "COLLECTION";
    } 
    throw TbError.newSQLException(-590703, Integer.toString(paramInt));
  }
  
  public Struct getStruct() {
    return this.struct;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\TbAnydata.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */