package com.tmax.tibero.jdbc.ext;

import com.tmax.tibero.Debug;
import com.tmax.tibero.jdbc.TbDriver;
import com.tmax.tibero.jdbc.data.ConnectionInfo;
import com.tmax.tibero.jdbc.data.NodeInfo;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.util.ParseException;
import com.tmax.tibero.jdbc.util.TbUrlParser;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;
import javax.sql.DataSource;

public class TbDataSource implements DataSource, Serializable, Referenceable {
  private static final long serialVersionUID = 6545162651851145814L;
  
  private TbDriver driver = null;
  
  protected ConnectionInfo info = null;
  
  private boolean isExplicitURL;
  
  private int maxStatements;
  
  private int initialPoolSize;
  
  private int minPoolSize;
  
  private int maxPoolSize;
  
  private int maxIdleTime;
  
  private int propertyCycle;
  
  private int loginTimeout;
  
  private Properties connectionProperties;
  
  private String connectionConfigFilePath;
  
  private static boolean reloadConfig;
  
  private static long lastModifiedTime;
  
  public TbDataSource() {
    this.info = new ConnectionInfo();
    this.isExplicitURL = false;
    this.maxStatements = 0;
    this.initialPoolSize = 0;
    this.minPoolSize = 0;
    this.maxPoolSize = 0;
    this.maxIdleTime = 0;
    this.propertyCycle = 0;
    this.loginTimeout = 0;
    this.connectionConfigFilePath = getTbHomeConfigPath() + "TbJDBCConnection.properties";
  }
  
  public Connection getConnection() throws SQLException {
    return getConnection(null, null);
  }
  
  public Connection getConnection(String paramString1, String paramString2) throws SQLException {
    if (reloadConfig)
      reloadConnectionConfigFile(getConnectionConfigFile()); 
    synchronized (this) {
      if (paramString1 != null)
        this.info.setUser(paramString1); 
      if (paramString2 != null)
        this.info.setPassword(paramString2); 
    } 
    if (this.loginTimeout != 0)
      this.info.setLoginTimeout(this.loginTimeout); 
    if (this.driver == null)
      this.driver = new TbDriver(); 
    if (!this.isExplicitURL)
      this.info.setURL(TbUrlParser.makeURL(this.info)); 
    return this.driver.connect(this.info);
  }
  
  public synchronized String getDatabaseName() {
    return this.info.getDatabaseName();
  }
  
  public synchronized String getDataSourceName() {
    return this.info.getDataSourceName();
  }
  
  public synchronized String getDescription() {
    return this.info.getDescription();
  }
  
  public synchronized String getDriverType() {
    return this.info.getDriverType();
  }
  
  public synchronized int getInitialPoolSize() {
    return this.initialPoolSize;
  }
  
  public synchronized int getLoginTimeout() throws SQLException {
    return this.loginTimeout;
  }

  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    throw new SQLFeatureNotSupportedException();
  }

  public synchronized PrintWriter getLogWriter() throws SQLException {
    return Debug.getLogWriter();
  }
  
  public synchronized int getMaxIdleTime() {
    return this.maxIdleTime;
  }
  
  public synchronized int getMaxPoolSize() {
    return this.maxPoolSize;
  }
  
  public synchronized int getMaxStatements() {
    return this.maxStatements;
  }
  
  public synchronized int getMinPoolSize() {
    return this.minPoolSize;
  }
  
  public synchronized String getNetworkProtocol() {
    return this.info.getNetworkProtocol();
  }
  
  public synchronized boolean getNlsDatetimeFormatEnabled() {
    return this.info.getNlsDatetimeFormatEnabled();
  }
  
  public synchronized String getPassword() {
    return this.info.getPassword();
  }
  
  public synchronized int getPortNumber() {
    if (this.info.getNodeList() != null && this.info.getNodeList().size() == 1) {
      NodeInfo nodeInfo = (NodeInfo) this.info.getNodeList().get(0);
      return nodeInfo.getPort();
    } 
    return 8629;
  }
  
  public synchronized String getProgramName() {
    return this.info.getProgramName();
  }
  
  public synchronized int getPropertyCycle() {
    return this.propertyCycle;
  }
  
  public synchronized int getReadTimeout() {
    return this.info.getReadTimeout();
  }
  
  public synchronized Reference getReference() throws NamingException {
    String str10;
    Reference reference = new Reference(getClass().getName(), "com.tmax.tibero.jdbc.ext.TbDataSourceFactory", null);
    String str1 = this.info.getUser();
    if (str1 != null)
      reference.add(new StringRefAddr("user", str1)); 
    String str2 = this.info.getPassword();
    if (str2 != null)
      reference.add(new StringRefAddr("password", str2)); 
    String str3 = this.info.getURL();
    if (str3 != null && !str3.equals(""))
      reference.add(new StringRefAddr("url", str3)); 
    String str4 = this.info.getNetworkProtocol();
    if (str4 != null && !str4.equals(""))
      reference.add(new StringRefAddr("networkProtocol", str4)); 
    int i = 8629;
    String str5 = null;
    if (this.info.getNodeList().size() == 1) {
      NodeInfo nodeInfo = (NodeInfo) this.info.getNodeList().get(0);
      str5 = nodeInfo.getAddress();
      i = nodeInfo.getPort();
    } 
    if (str5 != null && !str5.equals(""))
      reference.add(new StringRefAddr("serverName", str5)); 
    reference.add(new StringRefAddr("tdu", Integer.toString(this.info.getTDU())));
    reference.add(new StringRefAddr("portNumber", Integer.toString(i)));
    String str6 = this.info.getDatabaseName();
    if (str6 != null && !str6.equals(""))
      reference.add(new StringRefAddr("databaseName", this.info.getDatabaseName())); 
    String str7 = this.info.getDescription();
    if (str7 != null && !str7.equals(""))
      reference.add(new StringRefAddr("description", str7)); 
    String str8 = this.info.getDataSourceName();
    if (str8 != null && !str8.equals(""))
      reference.add(new StringRefAddr("dataSourceName", str8)); 
    int j = this.info.getLoginTimeout();
    if (j > 0)
      reference.add(new StringRefAddr("login_timeout", String.valueOf(j))); 
    int k = this.info.getLoginTimeout();
    if (k > 0)
      reference.add(new StringRefAddr("read_timeout", String.valueOf(k))); 
    String str9 = this.info.getProgramName();
    if (str9 != null && !str9.equals(""))
      reference.add(new StringRefAddr("program_name", str9)); 
    switch (this.info.getFailover()) {
      case 1:
        str10 = "SESSION";
        break;
      case 3:
        str10 = "CURSOR";
        break;
      default:
        str10 = "NONE";
        break;
    } 
    reference.add(new StringRefAddr("failover", str10));
    reference.add(new StringRefAddr("load_balance", String.valueOf(this.info.isLoadBalance())));
    reference.add(new StringRefAddr("failover_retry_count", Integer.toString(this.info.getFailoverRetryCount())));
    String str11 = this.info.getCharacterSet();
    if (str11 != null && !str11.equals(""))
      reference.add(new StringRefAddr("characterset", str11)); 
    reference.add(new StringRefAddr("statement_cache", String.valueOf(this.info.isStmtCache())));
    reference.add(new StringRefAddr("statement_cache_max_size", Integer.toString(this.info.getStmtCacheMaxSize())));
    reference.add(new StringRefAddr("lobChunkMaxSize", Integer.toString(this.info.getLobMaxChunkSize())));
    reference.add(new StringRefAddr("includeSynonyms", String.valueOf(this.info.getIncludeSynonyms())));
    reference.add(new StringRefAddr("mapDateToTimestamp", String.valueOf(this.info.getMapDateToTimestamp())));
    reference.add(new StringRefAddr("defaultNChar", String.valueOf(this.info.getDefaultNChar())));
    boolean bool = this.info.useSelfKeepAlive();
    reference.add(new StringRefAddr("self_keepalive", String.valueOf(bool)));
    if (bool) {
      reference.add(new StringRefAddr("self_keepidle", Integer.toString(this.info.getSelfKeepIdle())));
      reference.add(new StringRefAddr("self_keepintvl", Integer.toString(this.info.getSelfKeepInterval())));
      reference.add(new StringRefAddr("self_keepcnt", Integer.toString(this.info.getSelfKeepCount())));
    } 
    reference.add(new StringRefAddr("nls_datetime_format_enabled", String.valueOf(this.info.getNlsDatetimeFormatEnabled())));
    return reference;
  }
  
  public synchronized String getRoleName() {
    return "";
  }
  
  public synchronized String getServerName() {
    if (this.info.getNodeList() != null && this.info.getNodeList().size() == 1) {
      NodeInfo nodeInfo = (NodeInfo) this.info.getNodeList().get(0);
      return nodeInfo.getAddress();
    } 
    return "localhost";
  }
  
  public synchronized boolean getSelfKeepalive() {
    return this.info.useSelfKeepAlive();
  }
  
  public synchronized int getSelfKeepcnt() {
    return this.info.getSelfKeepCount();
  }
  
  public synchronized int getSelfKeepidle() {
    return this.info.getSelfKeepIdle();
  }
  
  public synchronized int getSelfKeepintvl() {
    return this.info.getSelfKeepInterval();
  }
  
  public synchronized int getTdu() {
    return this.info.getTDU();
  }
  
  public synchronized String getURL() throws SQLException {
    if (!this.isExplicitURL)
      this.info.setURL(TbUrlParser.makeURL(this.info)); 
    return this.info.getURL();
  }
  
  public synchronized String getUser() {
    return this.info.getUser();
  }
  
  public synchronized void setDatabaseName(String paramString) {
    this.info.setDatabaseName(paramString);
  }
  
  public synchronized void setDataSourceName(String paramString) {
    this.info.setDataSourceName(paramString);
  }
  
  public synchronized void setDescription(String paramString) {
    this.info.setDescription(paramString);
  }
  
  public synchronized void setDriverType(String paramString) {
    this.info.setDriverType(paramString);
  }
  
  public synchronized void setInitialPoolSize(int paramInt) {
    this.initialPoolSize = paramInt;
  }
  
  public synchronized void setLoginTimeout(int paramInt) throws SQLException {
    this.loginTimeout = paramInt;
  }
  
  public synchronized void setLogWriter(PrintWriter paramPrintWriter) throws SQLException {
    Debug.setLogWriter(paramPrintWriter);
  }
  
  public synchronized void setMapDateToTimestamp(boolean paramBoolean) {
    this.info.setMapDateToTimestamp(paramBoolean);
  }
  
  public synchronized void setMaxIdleTime(int paramInt) {
    this.maxIdleTime = paramInt;
  }
  
  public synchronized void setMaxPoolSize(int paramInt) {
    this.maxPoolSize = paramInt;
  }
  
  public synchronized void setMaxStatements(int paramInt) {
    this.maxStatements = paramInt;
  }
  
  public synchronized void setMinPoolSize(int paramInt) {
    this.minPoolSize = paramInt;
  }
  
  public synchronized void setNetworkProtocol(String paramString) {
    this.info.setNetworkProtocol(paramString);
  }
  
  public synchronized void setNlsDatetimeFormatEnabled(boolean paramBoolean) {
    this.info.setNlsDatetimeFormatEnabled(paramBoolean);
  }
  
  public synchronized void setPassword(String paramString) {
    this.info.setPassword(paramString);
  }
  
  public synchronized void setPortNumber(int paramInt) {
    if (this.info.getNodeList() == null) {
      Vector<NodeInfo> vector = new Vector();
      NodeInfo nodeInfo = new NodeInfo();
      nodeInfo.setPort(paramInt);
      vector.add(nodeInfo);
      this.info.setNodeList(vector);
    } else {
      NodeInfo nodeInfo = (NodeInfo) this.info.getNodeList().get(0);
      nodeInfo.setPort(paramInt);
    } 
  }
  
  public synchronized void setProgramName(String paramString) {
    this.info.setProgramName(paramString);
  }
  
  public synchronized void setPropertyCycle(int paramInt) {
    this.propertyCycle = paramInt;
  }
  
  public synchronized void setReadTimeout(int paramInt) {
    this.info.setReadTimeout(paramInt);
  }
  
  public synchronized void setRoleName(String paramString) {}
  
  public synchronized void setSelfKeepalive(String paramString) throws SQLException {
    try {
      this.info.setSelfKeepAlive("true".equalsIgnoreCase(paramString));
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, "Cannot convert \"" + paramString + "\" to boolean");
    } 
  }
  
  public synchronized void setSelfKeepalive(boolean paramBoolean) {
    this.info.setSelfKeepAlive(paramBoolean);
  }
  
  public synchronized void setSelfKeepidle(String paramString) throws SQLException {
    try {
      this.info.setSelfKeepIdle(Integer.parseInt(paramString));
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, "Cannot convert \"" + paramString + "\" to integer");
    } 
  }
  
  public synchronized void setSelfKeepidle(int paramInt) {
    this.info.setSelfKeepIdle(paramInt);
  }
  
  public synchronized void setSelfKeepintvl(String paramString) throws SQLException {
    try {
      this.info.setSelfKeepInterval(Integer.parseInt(paramString));
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, "Cannot convert \"" + paramString + "\" to integer");
    } 
  }
  
  public synchronized void setSelfKeepintvl(int paramInt) {
    this.info.setSelfKeepInterval(paramInt);
  }
  
  public synchronized void setSelfKeepcnt(String paramString) throws SQLException {
    try {
      this.info.setSelfKeepCount(Integer.parseInt(paramString));
    } catch (Exception exception) {
      throw TbError.newSQLException(-90651, "Cannot convert \"" + paramString + "\" to integer");
    } 
  }
  
  public synchronized void setSelfKeepcnt(int paramInt) {
    this.info.setSelfKeepCount(paramInt);
  }
  
  public synchronized void setServerName(String paramString) {
    if (this.info.getNodeList() == null) {
      Vector<NodeInfo> vector = new Vector();
      NodeInfo nodeInfo = new NodeInfo();
      nodeInfo.setAddress(paramString);
      vector.add(nodeInfo);
      this.info.setNodeList(vector);
    } else {
      NodeInfo nodeInfo = (NodeInfo) this.info.getNodeList().get(0);
      nodeInfo.setAddress(paramString);
    } 
  }
  
  public synchronized void setTdu(int paramInt) {
    this.info.setTDU(paramInt);
  }
  
  public synchronized void setURL(String paramString) throws SQLException {
    ConnectionInfo connectionInfo;
    try {
      connectionInfo = TbUrlParser.parseUrl(paramString, null);
    } catch (ParseException parseException) {
      throw TbError.newSQLException(-90605, parseException);
    } 
    if (connectionInfo == null)
      throw TbError.newSQLException(-90605); 
    this.info.setURL(paramString);
    this.info.setDriverType(connectionInfo.getDriverType());
    this.info.setNodeList(connectionInfo.getNodeList());
    this.info.setDatabaseName(connectionInfo.getDatabaseName());
    this.info.setFailover(connectionInfo.getFailover());
    this.info.setLoadBalance(connectionInfo.isLoadBalance());
    this.info.setNetworkProtocol(connectionInfo.getNetworkProtocol());
    connectionInfo = null;
    this.isExplicitURL = true;
  }
  
  public synchronized void setUser(String paramString) {
    this.info.setUser(paramString);
  }
  
  private String getTbHomeConfigPath() {
    StringBuffer stringBuffer = new StringBuffer();
    try {
      String str1 = System.getenv("TB_HOME");
      if (str1 != null && !str1.equals("")) {
        stringBuffer.append(str1);
        if (!stringBuffer.toString().endsWith(File.separator))
          stringBuffer.append(File.separator); 
        return stringBuffer.append("client").append(File.separator).append("config").append(File.separator).toString();
      } 
    } catch (Throwable throwable) {}
    String str = System.getProperty("TB_HOME");
    if (str != null && !str.equals("")) {
      stringBuffer.append(str);
      if (!stringBuffer.toString().endsWith(File.separator))
        stringBuffer.append(File.separator); 
      return stringBuffer.append("client").append(File.separator).append("config").append(File.separator).toString();
    } 
    return stringBuffer.toString();
  }
  
  public void setConnectionConfigFile(String paramString) throws SQLException {
    File file;
    if (paramString == null || paramString.equals("")) {
      file = new File(this.connectionConfigFilePath);
    } else {
      file = new File(paramString);
      this.connectionConfigFilePath = paramString;
    } 
    Properties properties = new Properties();
    FileInputStream fileInputStream = null;
    try {
      fileInputStream = new FileInputStream(file);
      properties.load(new BufferedInputStream(fileInputStream));
      setConfigProperties(properties);
    } catch (IOException iOException) {
      throw TbError.newSQLException(-590707, iOException.getMessage());
    } finally {
      if (fileInputStream != null)
        try {
          fileInputStream.close();
        } catch (IOException iOException) {} 
    } 
  }
  
  private void setConfigProperties(Properties paramProperties) throws SQLException {
    reloadConfig = paramProperties.getProperty("reload_config", "false").equalsIgnoreCase("yes");
    String str1 = getUser();
    if (str1 != null && !str1.equals(""))
      paramProperties.put("user", str1); 
    String str2 = getPassword();
    if (str2 != null && !str2.equals(""))
      paramProperties.put("password", str2); 
    String str3 = this.info.getURL();
    if (str3 != null && !str3.equals(""))
      paramProperties.put("url", str3); 
    String str4 = getServerName();
    if (str4 != null && !str4.equals("localhost"))
      paramProperties.put("serverName", str4); 
    int i = getPortNumber();
    if (i != 8629)
      paramProperties.put("portNumber", String.valueOf(i)); 
    this.info.set(paramProperties);
    str3 = paramProperties.getProperty("url");
    if (str3 != null && !str3.equals(""))
      setURL(str3); 
  }
  
  private void reloadConnectionConfigFile(String paramString) throws SQLException {
    File file = null;
    FileInputStream fileInputStream = null;
    Properties properties = null;
    try {
      file = new File(paramString);
      long l = file.lastModified();
      if (lastModifiedTime >= l)
        return; 
      properties = new Properties();
      fileInputStream = new FileInputStream(file);
      properties.load(new BufferedInputStream(fileInputStream));
      this.info.update(properties);
      String str = properties.getProperty("url");
      if (str != null && !str.equals(""))
        setURL(str); 
      lastModifiedTime = l;
    } catch (IOException iOException) {
      TbError.newSQLException(-590700, paramString);
    } finally {
      if (fileInputStream != null)
        try {
          fileInputStream.close();
        } catch (IOException iOException) {} 
    } 
  }
  
  public String getConnectionConfigFile() {
    return this.connectionConfigFilePath;
  }
  
  public void setConnectionProperties(Properties paramProperties) {
    ConnectionInfo connectionInfo = new ConnectionInfo(paramProperties);
    if (paramProperties.containsKey("driverType"))
      this.info.setDriverType(connectionInfo.getDriverType()); 
    if (paramProperties.containsKey("serverName"))
      this.info.setNodeList(connectionInfo.getNodeList()); 
    if (paramProperties.containsKey("databaseName"))
      this.info.setDatabaseName(connectionInfo.getDatabaseName()); 
    if (paramProperties.containsKey("failover"))
      this.info.setFailover(connectionInfo.getFailover()); 
    if (paramProperties.containsKey("failover_retry_count"))
      this.info.setFailoverRetryCount(connectionInfo.getFailoverRetryCount()); 
    if (paramProperties.containsKey("load_balance"))
      this.info.setLoadBalance(connectionInfo.isLoadBalance()); 
    if (paramProperties.containsKey("networkProtocol"))
      this.info.setNetworkProtocol(connectionInfo.getNetworkProtocol()); 
    if (paramProperties.containsKey("description"))
      this.info.setDescription(connectionInfo.getDescription()); 
    if (paramProperties.containsKey("user"))
      this.info.setUser(connectionInfo.getUser(), false); 
    if (paramProperties.containsKey("password"))
      this.info.setPassword(connectionInfo.getPassword()); 
    if (paramProperties.containsKey("url"))
      this.info.setURL(connectionInfo.getURL()); 
    if (paramProperties.containsKey("login_timeout"))
      this.info.setLoginTimeout(connectionInfo.getLoginTimeout()); 
    if (paramProperties.containsKey("read_timeout"))
      this.info.setReadTimeout(connectionInfo.getReadTimeout()); 
    if (paramProperties.containsKey("statement_cache"))
      this.info.setStmtCache(connectionInfo.isStmtCache()); 
    if (paramProperties.containsKey("statement_cache_max_size"))
      this.info.setStmtCacheMaxSize(connectionInfo.getStmtCacheMaxSize()); 
    if (paramProperties.containsKey("program_name"))
      this.info.setProgramName(connectionInfo.getProgramName()); 
    if (paramProperties.containsKey("characterset"))
      this.info.setCharacterSet(connectionInfo.getCharacterSet()); 
    if (paramProperties.containsKey("includeSynonyms"))
      this.info.setIncludeSynonyms(connectionInfo.getIncludeSynonyms()); 
    if (paramProperties.containsKey("mapDateToTimestamp"))
      this.info.setMapDateToTimestamp(connectionInfo.getMapDateToTimestamp()); 
    if (paramProperties.containsKey("defaultNChar"))
      this.info.setDefaultNChar(connectionInfo.getDefaultNChar()); 
    if (paramProperties.containsKey("databaseProductName"))
      this.info.setDatabaseProductName(connectionInfo.getDatabaseProductName()); 
    if (paramProperties.containsKey("databaseProductVersion"))
      this.info.setDatabaseProductVersion(connectionInfo.getDatabaseProductVersion()); 
    if (paramProperties.containsKey("driverName"))
      this.info.setDriverName(connectionInfo.getDriverName()); 
    if (paramProperties.containsKey("driverVersion"))
      this.info.setDriverVersion(connectionInfo.getDriverVersion()); 
    if (paramProperties.containsKey("lobChunkMaxSize"))
      this.info.setLobMaxChunkSize(connectionInfo.getLobMaxChunkSize()); 
    if (paramProperties.containsKey("self_keepalive"))
      this.info.setSelfKeepAlive(connectionInfo.useSelfKeepAlive()); 
    if (paramProperties.containsKey("self_keepidle"))
      this.info.setSelfKeepIdle(connectionInfo.getSelfKeepIdle()); 
    if (paramProperties.containsKey("self_keepintvl"))
      this.info.setSelfKeepInterval(connectionInfo.getSelfKeepInterval()); 
    if (paramProperties.containsKey("self_keepcnt"))
      this.info.setSelfKeepCount(connectionInfo.getSelfKeepCount()); 
    if (paramProperties.containsKey("nls_datetime_format_enabled"))
      this.info.setNlsDatetimeFormatEnabled(connectionInfo.getNlsDatetimeFormatEnabled()); 
    this.connectionProperties = paramProperties;
  }
  
  public Properties getConnectionProperties() {
    return this.connectionProperties;
  }
  
  public boolean isWrapperFor(Class<?> paramClass) throws SQLException {
    return paramClass.isInstance(this);
  }
  
  public <T> T unwrap(Class<T> paramClass) throws SQLException {
    try {
      return paramClass.cast(this);
    } catch (ClassCastException classCastException) {
      throw TbError.newSQLException(-90657);
    } 
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\ext\TbDataSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */