package com.tmax.tibero.jdbc.rowset;

import com.tmax.tibero.jdbc.TbDriver;
import com.tmax.tibero.jdbc.err.TbError;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.RowSet;
import javax.sql.RowSetInternal;
import javax.sql.RowSetReader;

public class TbCachedRowSetReader implements RowSetReader, Serializable {
  private static final long serialVersionUID = 3627327166239927438L;
  
  private static boolean isInitializedDriver = false;
  
  Connection getConnection(RowSetInternal paramRowSetInternal) throws SQLException {
    Connection connection = paramRowSetInternal.getConnection();
    String str1 = ((RowSet)paramRowSetInternal).getUrl();
    String str2 = ((RowSet)paramRowSetInternal).getUsername();
    String str3 = ((RowSet)paramRowSetInternal).getPassword();
    String str4 = ((RowSet)paramRowSetInternal).getDataSourceName();
    if (connection != null && !connection.isClosed())
      return connection; 
    if (str4 != null && !str4.equals(""))
      try {
        InitialContext initialContext = null;
        try {
          Properties properties = System.getProperties();
          initialContext = new InitialContext(properties);
        } catch (SecurityException securityException) {}
        if (initialContext == null)
          initialContext = new InitialContext(); 
        DataSource dataSource = (DataSource)initialContext.lookup(str4);
        return (str2 == null && str3 == null) ? dataSource.getConnection() : dataSource.getConnection(str2, str3);
      } catch (NamingException namingException) {
        throw TbError.newSQLException(-90828, namingException.getMessage());
      }  
    if (str1 != null && !str1.equals("")) {
      if (!isInitializedDriver) {
        DriverManager.registerDriver((Driver)new TbDriver());
        isInitializedDriver = true;
      } 
      if (str2.equals("") || str3.equals(""))
        throw TbError.newSQLException(-90854); 
      return DriverManager.getConnection(str1, str2, str3);
    } 
    throw TbError.newSQLException(-90852);
  }
  
  public synchronized void readData(RowSetInternal paramRowSetInternal) throws SQLException {
    TbCachedRowSet tbCachedRowSet = (TbCachedRowSet)paramRowSetInternal;
    Connection connection = getConnection(paramRowSetInternal);
    if (tbCachedRowSet.getCommand() == null)
      throw TbError.newSQLException(-90826); 
    try {
      connection.setTransactionIsolation(tbCachedRowSet.getTransactionIsolation());
    } catch (Exception exception) {}
    PreparedStatement preparedStatement = connection.prepareStatement(tbCachedRowSet.getCommand());
    setParams(paramRowSetInternal.getParams(), preparedStatement);
    try {
      preparedStatement.setMaxRows(tbCachedRowSet.getMaxRows());
      preparedStatement.setMaxFieldSize(tbCachedRowSet.getMaxFieldSize());
      preparedStatement.setEscapeProcessing(tbCachedRowSet.getEscapeProcessing());
      preparedStatement.setQueryTimeout(tbCachedRowSet.getQueryTimeout());
    } catch (Exception exception) {}
    ResultSet resultSet = preparedStatement.executeQuery();
    tbCachedRowSet.populate(resultSet);
    resultSet.close();
    preparedStatement.close();
    try {
      connection.commit();
    } catch (SQLException sQLException) {}
    if (!tbCachedRowSet.isConnectionOpened())
      connection.close(); 
  }
  
  private void setParams(Object[] paramArrayOfObject, PreparedStatement paramPreparedStatement) throws SQLException {
    for (byte b = 0; b < paramArrayOfObject.length; b++) {
      if (!(paramArrayOfObject[b] instanceof Object[])) {
        paramPreparedStatement.setObject(b + 1, paramArrayOfObject[b]);
      } else {
        Object[] arrayOfObject = (Object[])paramArrayOfObject[b];
        if (arrayOfObject.length == 2) {
          setParams2Arg(b, arrayOfObject, paramPreparedStatement);
        } else if (arrayOfObject.length == 3) {
          setParams3Arg(b, arrayOfObject, paramPreparedStatement);
        } 
      } 
    } 
  }
  
  private void setParams2Arg(int paramInt, Object[] paramArrayOfObject, PreparedStatement paramPreparedStatement) throws SQLException {
    if (paramArrayOfObject[0] == null) {
      paramPreparedStatement.setNull(paramInt + 1, ((Integer)paramArrayOfObject[1]).intValue());
    } else if (paramArrayOfObject[0] instanceof RowId) {
      paramPreparedStatement.setRowId(paramInt + 1, (RowId)paramArrayOfObject[0]);
    } else if (paramArrayOfObject[0] instanceof NClob) {
      paramPreparedStatement.setNClob(paramInt + 1, (NClob)paramArrayOfObject[0]);
    } else if (paramArrayOfObject[0] instanceof Date) {
      if (paramArrayOfObject[1] instanceof Calendar) {
        paramPreparedStatement.setDate(paramInt + 1, (Date)paramArrayOfObject[0], (Calendar)paramArrayOfObject[1]);
      } else {
        throw TbError.newSQLException(-90846, paramArrayOfObject[1].toString());
      } 
    } else if (paramArrayOfObject[0] instanceof Time) {
      if (paramArrayOfObject[1] instanceof Calendar) {
        paramPreparedStatement.setTime(paramInt + 1, (Time)paramArrayOfObject[0], (Calendar)paramArrayOfObject[1]);
      } else {
        throw TbError.newSQLException(-90846, paramArrayOfObject[1].toString());
      } 
    } else if (paramArrayOfObject[0] instanceof Timestamp) {
      if (paramArrayOfObject[1] instanceof Calendar) {
        paramPreparedStatement.setTimestamp(paramInt + 1, (Timestamp)paramArrayOfObject[0], (Calendar)paramArrayOfObject[1]);
      } else {
        throw TbError.newSQLException(-90846, paramArrayOfObject[1].toString());
      } 
    } else if (paramArrayOfObject[0] instanceof Reader) {
      paramPreparedStatement.setCharacterStream(paramInt + 1, (Reader)paramArrayOfObject[0], ((Integer)paramArrayOfObject[1]).intValue());
    } else if (paramArrayOfObject[1] instanceof Integer) {
      if (((Integer)paramArrayOfObject[1]).intValue() == 0) {
        paramPreparedStatement.setNString(paramInt + 1, (String)paramArrayOfObject[0]);
      } else {
        paramPreparedStatement.setObject(paramInt + 1, paramArrayOfObject[0], ((Integer)paramArrayOfObject[1]).intValue());
      } 
    } 
  }
  
  private void setParams3Arg(int paramInt, Object[] paramArrayOfObject, PreparedStatement paramPreparedStatement) throws SQLException {
    if (paramArrayOfObject[0] == null) {
      paramPreparedStatement.setNull(paramInt + 1, ((Integer)paramArrayOfObject[1]).intValue(), (String)paramArrayOfObject[2]);
    } else {
      if (paramArrayOfObject[0] instanceof Reader) {
        switch (((Integer)paramArrayOfObject[2]).intValue()) {
          case 6:
            if (paramArrayOfObject[1] instanceof Integer) {
              paramPreparedStatement.setClob(paramInt + 1, (Reader)paramArrayOfObject[0], ((Integer)paramArrayOfObject[1]).intValue());
            } else {
              paramPreparedStatement.setClob(paramInt + 1, (Reader)paramArrayOfObject[0], ((Long)paramArrayOfObject[1]).longValue());
            } 
            return;
          case 3:
            if (paramArrayOfObject[1] instanceof Integer) {
              paramPreparedStatement.setCharacterStream(paramInt + 1, (Reader)paramArrayOfObject[0], ((Integer)paramArrayOfObject[1]).intValue());
            } else {
              paramPreparedStatement.setCharacterStream(paramInt + 1, (Reader)paramArrayOfObject[0], ((Long)paramArrayOfObject[1]).longValue());
            } 
            return;
          case 4:
            if (paramArrayOfObject[1] instanceof Integer) {
              paramPreparedStatement.setNCharacterStream(paramInt + 1, (Reader)paramArrayOfObject[0], ((Integer)paramArrayOfObject[1]).intValue());
            } else {
              paramPreparedStatement.setNCharacterStream(paramInt + 1, (Reader)paramArrayOfObject[0], ((Long)paramArrayOfObject[1]).longValue());
            } 
            return;
        } 
        throw TbError.newSQLException(-90846, ((Integer)paramArrayOfObject[2]).toString());
      } 
      if (paramArrayOfObject[0] instanceof InputStream) {
        switch (((Integer)paramArrayOfObject[2]).intValue()) {
          case 2:
            if (paramArrayOfObject[1] instanceof Integer) {
              paramPreparedStatement.setBinaryStream(paramInt + 1, (InputStream)paramArrayOfObject[0], ((Integer)paramArrayOfObject[1]).intValue());
            } else {
              paramPreparedStatement.setBinaryStream(paramInt + 1, (InputStream)paramArrayOfObject[0], ((Long)paramArrayOfObject[1]).longValue());
            } 
            return;
          case 1:
            paramPreparedStatement.setAsciiStream(paramInt + 1, (InputStream)paramArrayOfObject[0], ((Integer)paramArrayOfObject[1]).intValue());
            return;
          case 5:
            if (paramArrayOfObject[1] instanceof Integer) {
              paramPreparedStatement.setBlob(paramInt + 1, (InputStream)paramArrayOfObject[0], ((Integer)paramArrayOfObject[1]).intValue());
            } else {
              paramPreparedStatement.setBlob(paramInt + 1, (InputStream)paramArrayOfObject[0], ((Long)paramArrayOfObject[1]).longValue());
            } 
            return;
        } 
        throw TbError.newSQLException(-90846, ((Integer)paramArrayOfObject[2]).toString());
      } 
      if (paramArrayOfObject[1] instanceof Integer && paramArrayOfObject[2] instanceof Integer) {
        paramPreparedStatement.setObject(paramInt + 1, paramArrayOfObject[0], ((Integer)paramArrayOfObject[1]).intValue(), ((Integer)paramArrayOfObject[2]).intValue());
      } else {
        throw TbError.newSQLException(-90846, paramArrayOfObject[0].toString());
      } 
    } 
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\rowset\TbCachedRowSetReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */