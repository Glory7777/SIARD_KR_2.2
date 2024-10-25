package com.tmax.tibero.jdbc.data;

import com.tmax.tibero.jdbc.TbLob;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.util.TbCommon;
import com.tmax.tibero.jdbc.util.TbRandom;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

public class ConnectionInfo implements Cloneable {
  public static final String DATABASENAME = "databaseName";
  
  public static final String DATASOURCENAME = "dataSourceName";
  
  public static final String DESCRIPTION = "description";
  
  public static final String DRIVERTYPE = "driverType";
  
  public static final String NETWORKPROTOCOL = "networkProtocol";
  
  public static final String PASSWORD = "password";
  
  public static final String PORTNUMBER = "portNumber";
  
  public static final String SERVERNAME = "serverName";
  
  public static final String USER = "user";
  
  public static final String TDU = "tdu";
  
  public static final String URL = "url";
  
  public static final String LOGIN_TIMEOUT = "login_timeout";
  
  public static final String READ_TIMEOUT = "read_timeout";
  
  public static final String LOAD_BALANCE = "load_balance";
  
  public static final String FAILOVER_RETRY_CNT = "failover_retry_count";
  
  public static final String FAILOVER = "failover";
  
  public static final String BACKUP_SERVER = "backup_server";
  
  public static final String BACKUP_PORT = "backup_port";
  
  public static final String CHARACTERSET = "characterset";
  
  public static final String PROGNAME = "program_name";
  
  public static final String STATEMENT_CACHE = "statement_cache";
  
  public static final String STATEMENT_CACHE_MAX_SIZE = "statement_cache_max_size";
  
  public static final String LOB_CHUNK_MAX_SIZE = "lobChunkMaxSize";
  
  public static final String INCLUDE_SYNONYMS = "includeSynonyms";
  
  public static final String MAP_DATE_TO_TIMESTAMP = "mapDateToTimestamp";
  
  public static final String DEFAULT_NCHAR = "defaultNChar";
  
  public static final String RELOAD_CONFIG = "reload_config";
  
  public static final String SELF_KEEPALIVE = "self_keepalive";
  
  public static final String SELF_KEEPALIVE_IDLE_TIME = "self_keepidle";
  
  public static final String SELF_KEEPALIVE_MAX_TRY_COUNT = "self_keepcnt";
  
  public static final String SELF_KEEPALIVE_INTERVAL_TIME = "self_keepintvl";
  
  public static final String NLS_DATETIME_FORMAT_ENABLED = "nls_datetime_format_enabled";
  
  private static final String NEW_PASSWORD = "new_password";
  
  private static final String DATABASE_PRODUCT_NAME = "databaseProductName";
  
  private static final String DATABASE_PRODUCT_VERSION = "databaseProductVersion";
  
  private static final String DRIVER_NAME = "driverName";
  
  private static final String DRIVER_VERSION = "driverVersion";
  
  private static final int DEFAULT_RETRY_COUNT = 3;
  
  private static final int DEFAULT_STMT_CACHE_MAX_SIZE = 5;
  
  private static final int DEFAULT_SELF_KEEPALIVE_IDLE_TIME = 60;
  
  private static final int DEFAULT_SELF_KEEPALIVE_MAX_TRY_COUNT = 3;
  
  private static final int DEFAULT_SELF_KEEPALIVE_INTERVAL_TIME = 10;
  
  private int failover = 0;
  
  private boolean loadBalance = false;
  
  private boolean connectedToPrimary = false;
  
  private int curNodeIdx = 0;
  
  private int visitedNodeCount = 0;
  
  private boolean[] visitedNodes = null;
  
  private Vector nodeList = null;
  
  private int tryCount = 0;
  
  private int retryCount = 3;
  
  private boolean selfKeepAlive = false;
  
  private int selfKeepIdle = 60;
  
  private int selfKeepCount = 3;
  
  private int selfKeepInterval = 10;
  
  private boolean nlsDTFormatEnabled = false;
  
  private String user;
  
  private String databaseName;
  
  private String datasourceName;
  
  private String description;
  
  private String driverType = "thin";
  
  private String protocol = "tcp";
  
  private String password;
  
  private String newPassword;
  
  private String url;
  
  private String databaseProductName;
  
  private String databaseProductVersion;
  
  private String driverName;
  
  private String driverVersion;
  
  private int tdu = 4096;
  
  private int loginTimeout = 0;
  
  private int readTimeout = 0;
  
  private String charset = null;
  
  private String progname = null;
  
  private boolean isXA = false;
  
  private boolean isInternal = false;
  
  private boolean stmtCache = false;
  
  private int stmtCacheMaxSize = 5;
  
  private boolean includeSynonyms = false;
  
  private boolean mapDateToTimestamp = true;
  
  private boolean defaultNChar = false;
  
  private int lobMaxChunkSize = TbLob.getMaxChunkSize();
  
  public ConnectionInfo() {}
  
  public ConnectionInfo(Properties paramProperties) {
    set(paramProperties);
  }
  
  public ConnectionInfo(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, Properties paramProperties) {
    paramString3 = TbCommon.getEmptyString(paramString3, "localhost");
    if (paramInt <= 0)
      paramInt = 8629; 
    NodeInfo nodeInfo = new NodeInfo(paramString3, paramInt);
    if (this.nodeList == null)
      this.nodeList = new Vector(); 
    this.nodeList.add(nodeInfo);
    setUser(paramString2);
    setDatabaseName(paramString4);
    this.url = paramString1;
    if (paramProperties != null) {
      this.datasourceName = paramProperties.getProperty("dataSourceName", "");
      this.description = paramProperties.getProperty("description", "");
      this.driverType = paramProperties.getProperty("driverType", "thin");
      this.protocol = paramProperties.getProperty("networkProtocol", "tcp");
      this.password = paramProperties.getProperty("password", "");
      this.newPassword = paramProperties.getProperty("new_password", "");
      String str = paramProperties.getProperty("failover");
      if (str == null) {
        this.failover = 0;
      } else if ("ON".equalsIgnoreCase(str) || "SESSION".equalsIgnoreCase(str)) {
        this.failover = 1;
      } else if ("CURSOR".equalsIgnoreCase(str)) {
        this.failover = 3;
      } else {
        this.failover = 0;
      } 
      this.loadBalance = paramProperties.getProperty("load_balance", "").equalsIgnoreCase("ON");
      try {
        this.retryCount = Integer.parseInt(paramProperties.getProperty("failover_retry_count"));
        if (this.retryCount < 1)
          this.retryCount = 3; 
      } catch (NumberFormatException numberFormatException) {
        this.retryCount = 3;
      } 
      try {
        this.loginTimeout = Integer.parseInt(paramProperties.getProperty("login_timeout"));
      } catch (NumberFormatException numberFormatException) {
        this.loginTimeout = 0;
      } 
      try {
        this.readTimeout = Integer.parseInt(paramProperties.getProperty("read_timeout"));
      } catch (NumberFormatException numberFormatException) {
        this.readTimeout = 0;
      } 
      try {
        this.tdu = Integer.parseInt(paramProperties.getProperty("tdu"));
      } catch (NumberFormatException numberFormatException) {
        this.tdu = 4096;
      } 
      this.progname = paramProperties.getProperty("program_name");
      this.charset = paramProperties.getProperty("characterset");
      this.stmtCache = paramProperties.getProperty("statement_cache", "OFF").equalsIgnoreCase("ON");
      try {
        this.stmtCacheMaxSize = Integer.parseInt(paramProperties.getProperty("statement_cache_max_size"));
      } catch (NumberFormatException numberFormatException) {
        this.stmtCacheMaxSize = 5;
      } 
      try {
        this.lobMaxChunkSize = Integer.parseInt(paramProperties.getProperty("lobChunkMaxSize"));
        TbLob.setMaxChunkSize(this.lobMaxChunkSize);
      } catch (NumberFormatException numberFormatException) {}
      this.includeSynonyms = paramProperties.getProperty("includeSynonyms", "false").equalsIgnoreCase("true");
      this.mapDateToTimestamp = paramProperties.getProperty("mapDateToTimestamp", "true").equalsIgnoreCase("true");
      this.defaultNChar = paramProperties.getProperty("defaultNChar", "false").equalsIgnoreCase("true");
      this.databaseProductName = paramProperties.getProperty("databaseProductName", "");
      this.databaseProductVersion = paramProperties.getProperty("databaseProductVersion", "");
      this.driverName = paramProperties.getProperty("driverName", "");
      this.driverVersion = paramProperties.getProperty("driverVersion", "");
      this.selfKeepAlive = paramProperties.getProperty("self_keepalive", "false").equalsIgnoreCase("true");
      try {
        this.selfKeepIdle = Integer.parseInt(paramProperties.getProperty("self_keepidle"));
      } catch (NumberFormatException numberFormatException) {
        this.selfKeepIdle = 60;
      } 
      try {
        this.selfKeepInterval = Integer.parseInt(paramProperties.getProperty("self_keepintvl"));
      } catch (NumberFormatException numberFormatException) {
        this.selfKeepInterval = 10;
      } 
      try {
        this.selfKeepCount = Integer.parseInt(paramProperties.getProperty("self_keepcnt"));
      } catch (NumberFormatException numberFormatException) {
        this.selfKeepCount = 3;
      } 
      this.nlsDTFormatEnabled = paramProperties.getProperty("nls_datetime_format_enabled", "false").equalsIgnoreCase("true");
    } 
  }
  
  public Object clone() {
    try {
      return super.clone();
    } catch (Exception exception) {
      throw new RuntimeException("ConnectionInfo clone() failed:" + exception.getMessage());
    } 
  }
  
  public String getCharacterSet() {
    return this.charset;
  }
  
  public NodeInfo getClusterNode() throws SQLException {
    return this.connectedToPrimary ? getSecondaryNode() : getPrimaryNode();
  }
  
  public String getDatabaseName() {
    return this.databaseName;
  }
  
  public String getDataSourceName() {
    return this.datasourceName;
  }
  
  public String getDescription() {
    return this.description;
  }
  
  public String getDriverType() {
    return this.driverType;
  }
  
  public int getFailover() {
    return this.failover;
  }
  
  public int getLoginTimeout() {
    return this.loginTimeout;
  }
  
  public String getNetworkProtocol() {
    return this.protocol;
  }
  
  public Vector getNodeList() {
    return this.nodeList;
  }
  
  public String getPassword() {
    return this.password;
  }
  
  public String getNewPassword() {
    return this.newPassword;
  }
  
  public int getFailoverRetryCount() {
    return this.retryCount;
  }
  
  public boolean getIncludeSynonyms() {
    return this.includeSynonyms;
  }
  
  public void setIncludeSynonyms(boolean paramBoolean) {
    this.includeSynonyms = paramBoolean;
  }
  
  public boolean getMapDateToTimestamp() {
    return this.mapDateToTimestamp;
  }
  
  public void setMapDateToTimestamp(boolean paramBoolean) {
    this.mapDateToTimestamp = paramBoolean;
  }
  
  public boolean getDefaultNChar() {
    return this.defaultNChar;
  }
  
  public void setDefaultNChar(boolean paramBoolean) {
    this.defaultNChar = paramBoolean;
  }
  
  public void setFailoverRetryCount(int paramInt) {
    if (this.retryCount < 1) {
      this.retryCount = 3;
    } else {
      this.retryCount = paramInt;
    } 
  }
  
  public boolean isStmtCache() {
    return this.stmtCache;
  }
  
  public void setStmtCache(boolean paramBoolean) {
    this.stmtCache = paramBoolean;
  }
  
  public int getStmtCacheMaxSize() {
    return this.stmtCacheMaxSize;
  }
  
  public void setStmtCacheMaxSize(int paramInt) {
    this.stmtCacheMaxSize = paramInt;
  }
  
  private synchronized NodeInfo getPrimaryNode() throws SQLException {
    NodeInfo nodeInfo = null;
    if (this.nodeList == null || this.nodeList.size() == 0)
      throw TbError.newSQLException(-90400, "Node list is empty"); 
    this.visitedNodes = new boolean[this.nodeList.size()];
    this.curNodeIdx = 0;
    if (this.loadBalance) {
      this.curNodeIdx = TbRandom.nextInt(this.nodeList.size());
    } else if (isFailoverSessionEnabled()) {
      for (byte b = 0; b < this.nodeList.size(); b++) {
        nodeInfo = this.nodeList.get(b);
        if (nodeInfo.getNodeType() == 1) {
          this.curNodeIdx = b;
          break;
        } 
      } 
    } 
    this.visitedNodeCount = 1;
    this.visitedNodes[this.curNodeIdx] = true;
    this.connectedToPrimary = true;
    return this.nodeList.get(this.curNodeIdx);
  }
  
  public String getProgramName() {
    return this.progname;
  }
  
  public int getReadTimeout() {
    return this.readTimeout;
  }
  
  public synchronized NodeInfo getSecondaryNode() {
    byte b = 0;
    NodeInfo nodeInfo = null;
    if (this.nodeList == null || this.nodeList.size() <= 1 || !isFailoverSessionEnabled())
      return null; 
    if (this.visitedNodeCount == this.nodeList.size()) {
      if (++this.tryCount == this.retryCount)
        return null; 
      for (b = 0; b < this.nodeList.size(); b++)
        this.visitedNodes[b] = false; 
      this.visitedNodeCount = 0;
    } 
    for (b = 0; b < this.nodeList.size(); b++) {
      nodeInfo = this.nodeList.get(b);
      if (nodeInfo.getNodeType() == 2) {
        this.curNodeIdx = b;
        this.visitedNodeCount++;
        this.visitedNodes[this.curNodeIdx] = true;
        return nodeInfo;
      } 
    } 
    while (true) {
      this.curNodeIdx = (this.curNodeIdx + 1 >= this.nodeList.size()) ? 0 : (this.curNodeIdx + 1);
      if (!this.visitedNodes[this.curNodeIdx]) {
        this.visitedNodeCount++;
        this.visitedNodes[this.curNodeIdx] = true;
        return this.nodeList.get(this.curNodeIdx);
      } 
    } 
  }
  
  public int getTDU() {
    return this.tdu;
  }
  
  public String getURL() {
    return this.url;
  }
  
  public String getUser() {
    return this.user;
  }
  
  private String getUserName(String paramString) {
    return (paramString.length() >= 2 && paramString.startsWith("\"") && paramString.endsWith("\"")) ? paramString.substring(1, paramString.length() - 1) : paramString.toUpperCase();
  }
  
  public boolean isFailoverSessionEnabled() {
    return ((this.failover & 0x1) > 0);
  }
  
  public boolean isFailoverCursorEnabled() {
    return ((this.failover & 0x2) > 0);
  }
  
  public boolean isInternal() {
    return this.isInternal;
  }
  
  public boolean isLoadBalance() {
    return this.loadBalance;
  }
  
  public boolean isXA() {
    return this.isXA;
  }
  
  public void set(Properties paramProperties) {
    char c;
    if (paramProperties == null)
      return; 
    this.databaseName = paramProperties.getProperty("databaseName", "");
    this.datasourceName = paramProperties.getProperty("dataSourceName", "");
    this.description = paramProperties.getProperty("description", "");
    setUser(paramProperties.getProperty("user", ""));
    this.password = paramProperties.getProperty("password", "");
    this.newPassword = paramProperties.getProperty("new_password", "");
    this.url = paramProperties.getProperty("url", "");
    this.driverType = paramProperties.getProperty("driverType", "thin");
    this.protocol = paramProperties.getProperty("networkProtocol", "tcp");
    String str1 = paramProperties.getProperty("serverName", "localhost");
    String str2 = paramProperties.getProperty("portNumber", "");
    try {
      c = Integer.parseInt(str2);
    } catch (Exception exception) {
      c = '↵';
    } 
    if (this.nodeList == null)
      this.nodeList = new Vector(); 
    NodeInfo nodeInfo = new NodeInfo(str1, c);
    this.nodeList.add(nodeInfo);
    String str3 = paramProperties.getProperty("failover");
    if (str3 == null) {
      this.failover = 0;
    } else if ("ON".equalsIgnoreCase(str3) || "SESSION".equalsIgnoreCase(str3)) {
      this.failover = 1;
    } else if ("CURSOR".equalsIgnoreCase(str3)) {
      this.failover = 3;
    } else {
      this.failover = 0;
    } 
    this.loadBalance = paramProperties.getProperty("load_balance", "").equalsIgnoreCase("ON");
    try {
      this.retryCount = Integer.parseInt(paramProperties.getProperty("failover_retry_count"));
      if (this.retryCount < 1)
        this.retryCount = 3; 
    } catch (NumberFormatException numberFormatException) {
      this.retryCount = 3;
    } 
    try {
      this.loginTimeout = Integer.parseInt(paramProperties.getProperty("login_timeout"));
    } catch (NumberFormatException numberFormatException) {
      this.loginTimeout = 0;
    } 
    try {
      this.readTimeout = Integer.parseInt(paramProperties.getProperty("read_timeout"));
    } catch (NumberFormatException numberFormatException) {
      this.readTimeout = 0;
    } 
    try {
      this.tdu = Integer.parseInt(paramProperties.getProperty("tdu"));
    } catch (Exception exception) {
      this.tdu = 4096;
    } 
    this.progname = paramProperties.getProperty("program_name");
    this.charset = paramProperties.getProperty("characterset");
    this.stmtCache = paramProperties.getProperty("statement_cache", "OFF").equalsIgnoreCase("ON");
    try {
      this.stmtCacheMaxSize = Integer.parseInt(paramProperties.getProperty("statement_cache_max_size"));
    } catch (NumberFormatException numberFormatException) {
      this.stmtCacheMaxSize = 5;
    } 
    try {
      this.lobMaxChunkSize = Integer.parseInt(paramProperties.getProperty("lobChunkMaxSize"));
      TbLob.setMaxChunkSize(this.lobMaxChunkSize);
    } catch (NumberFormatException numberFormatException) {}
    this.includeSynonyms = paramProperties.getProperty("includeSynonyms", "false").equalsIgnoreCase("true");
    this.mapDateToTimestamp = paramProperties.getProperty("mapDateToTimestamp", "true").equalsIgnoreCase("true");
    this.defaultNChar = paramProperties.getProperty("defaultNChar", "false").equalsIgnoreCase("true");
    this.databaseProductName = paramProperties.getProperty("databaseProductName", "");
    this.databaseProductVersion = paramProperties.getProperty("databaseProductVersion", "");
    this.driverName = paramProperties.getProperty("driverName", "");
    this.driverVersion = paramProperties.getProperty("driverVersion", "");
    this.selfKeepAlive = paramProperties.getProperty("self_keepalive", "false").equalsIgnoreCase("true");
    try {
      this.selfKeepIdle = Integer.parseInt(paramProperties.getProperty("self_keepidle"));
    } catch (NumberFormatException numberFormatException) {
      this.selfKeepIdle = 60;
    } 
    try {
      this.selfKeepInterval = Integer.parseInt(paramProperties.getProperty("self_keepintvl"));
    } catch (NumberFormatException numberFormatException) {
      this.selfKeepInterval = 10;
    } 
    try {
      this.selfKeepCount = Integer.parseInt(paramProperties.getProperty("self_keepcnt"));
    } catch (NumberFormatException numberFormatException) {
      this.selfKeepCount = 3;
    } 
    this.nlsDTFormatEnabled = paramProperties.getProperty("nls_datetime_format_enabled", "false").equalsIgnoreCase("true");
  }
  
  public void update(Properties paramProperties) {
    if (paramProperties == null)
      return; 
    if (paramProperties.getProperty("databaseName") != null)
      this.databaseName = paramProperties.getProperty("databaseName"); 
    if (paramProperties.getProperty("dataSourceName") != null)
      this.datasourceName = paramProperties.getProperty("dataSourceName"); 
    if (paramProperties.getProperty("description") != null)
      this.description = paramProperties.getProperty("description"); 
    if (paramProperties.getProperty("user") != null)
      setUser(paramProperties.getProperty("user")); 
    if (paramProperties.getProperty("password") != null)
      this.password = paramProperties.getProperty("password"); 
    if (paramProperties.getProperty("new_password") != null)
      this.newPassword = paramProperties.getProperty("new_password"); 
    if (paramProperties.getProperty("url") != null)
      this.url = paramProperties.getProperty("url"); 
    if (paramProperties.getProperty("driverType") != null)
      this.driverType = paramProperties.getProperty("driverType", "thin"); 
    if (paramProperties.getProperty("networkProtocol") != null)
      this.protocol = paramProperties.getProperty("networkProtocol", "tcp"); 
    String str1 = null;
    if (paramProperties.getProperty("serverName") != null)
      str1 = paramProperties.getProperty("serverName", "localhost"); 
    int i = -1;
    if (paramProperties.getProperty("portNumber") != null) {
      String str = paramProperties.getProperty("portNumber");
      try {
        i = Integer.parseInt(str);
      } catch (Exception exception) {
        i = 8629;
      } 
    } 
    if (str1 != null && i > 0) {
      this.nodeList = new Vector();
      NodeInfo nodeInfo = new NodeInfo(str1, i);
      this.nodeList.add(nodeInfo);
    } 
    String str2 = paramProperties.getProperty("failover");
    if (str2 != null)
      if ("ON".equalsIgnoreCase(str2) || "SESSION".equalsIgnoreCase(str2)) {
        this.failover = 1;
      } else if ("CURSOR".equalsIgnoreCase(str2)) {
        this.failover = 3;
      } else {
        this.failover = 0;
      }  
    this.loadBalance = paramProperties.getProperty("load_balance", "").equalsIgnoreCase("ON");
    if (paramProperties.getProperty("load_balance") != null)
      this.loadBalance = paramProperties.getProperty("load_balance", "").equalsIgnoreCase("ON"); 
    if (paramProperties.getProperty("failover_retry_count") != null)
      try {
        this.retryCount = Integer.parseInt(paramProperties.getProperty("failover_retry_count"));
        if (this.retryCount < 1)
          this.retryCount = 3; 
      } catch (NumberFormatException numberFormatException) {
        this.retryCount = 3;
      }  
    if (paramProperties.getProperty("login_timeout") != null)
      try {
        this.loginTimeout = Integer.parseInt(paramProperties.getProperty("login_timeout"));
      } catch (NumberFormatException numberFormatException) {
        this.loginTimeout = 0;
      }  
    if (paramProperties.getProperty("read_timeout") != null)
      try {
        this.readTimeout = Integer.parseInt(paramProperties.getProperty("read_timeout"));
      } catch (NumberFormatException numberFormatException) {
        this.readTimeout = 0;
      }  
    if (paramProperties.getProperty("tdu") != null)
      try {
        this.tdu = Integer.parseInt(paramProperties.getProperty("tdu"));
      } catch (Exception exception) {
        this.tdu = 4096;
      }  
    if (paramProperties.getProperty("program_name") != null)
      this.progname = paramProperties.getProperty("program_name"); 
    if (paramProperties.getProperty("characterset") != null)
      this.charset = paramProperties.getProperty("characterset"); 
    if (paramProperties.getProperty("statement_cache") != null)
      this.stmtCache = paramProperties.getProperty("statement_cache", "OFF").equalsIgnoreCase("ON"); 
    if (paramProperties.getProperty("statement_cache_max_size") != null)
      try {
        this.stmtCacheMaxSize = Integer.parseInt(paramProperties.getProperty("statement_cache_max_size"));
      } catch (NumberFormatException numberFormatException) {
        this.stmtCacheMaxSize = 5;
      }  
    if (paramProperties.getProperty("lobChunkMaxSize") != null)
      try {
        this.lobMaxChunkSize = Integer.parseInt(paramProperties.getProperty("lobChunkMaxSize"));
        TbLob.setMaxChunkSize(this.lobMaxChunkSize);
      } catch (NumberFormatException numberFormatException) {} 
    if (paramProperties.getProperty("includeSynonyms") != null)
      this.includeSynonyms = paramProperties.getProperty("includeSynonyms", "false").equalsIgnoreCase("true"); 
    if (paramProperties.getProperty("mapDateToTimestamp") != null)
      this.mapDateToTimestamp = paramProperties.getProperty("mapDateToTimestamp", "true").equalsIgnoreCase("true"); 
    if (paramProperties.getProperty("defaultNChar") != null)
      this.defaultNChar = paramProperties.getProperty("defaultNChar", "false").equalsIgnoreCase("true"); 
    if (paramProperties.getProperty("databaseProductName") != null)
      this.databaseProductName = paramProperties.getProperty("databaseProductName", ""); 
    if (paramProperties.getProperty("databaseProductVersion") != null)
      this.databaseProductVersion = paramProperties.getProperty("databaseProductVersion", ""); 
    if (paramProperties.getProperty("driverName") != null)
      this.driverName = paramProperties.getProperty("driverName", ""); 
    if (paramProperties.getProperty("driverVersion") != null)
      this.driverVersion = paramProperties.getProperty("driverVersion", ""); 
    if (paramProperties.getProperty("self_keepalive") != null)
      this.selfKeepAlive = paramProperties.getProperty("self_keepalive", "false").equalsIgnoreCase("true"); 
    if (paramProperties.getProperty("self_keepidle") != null)
      try {
        this.selfKeepIdle = Integer.parseInt(paramProperties.getProperty("self_keepidle"));
      } catch (NumberFormatException numberFormatException) {
        this.selfKeepIdle = 60;
      }  
    if (paramProperties.getProperty("self_keepintvl") != null)
      try {
        this.selfKeepInterval = Integer.parseInt(paramProperties.getProperty("self_keepintvl"));
      } catch (NumberFormatException numberFormatException) {
        this.selfKeepInterval = 10;
      }  
    if (paramProperties.getProperty("self_keepcnt") != null)
      try {
        this.selfKeepCount = Integer.parseInt(paramProperties.getProperty("self_keepcnt"));
      } catch (NumberFormatException numberFormatException) {
        this.selfKeepCount = 3;
      }  
    if (paramProperties.getProperty("nls_datetime_format_enabled") != null)
      this.nlsDTFormatEnabled = paramProperties.getProperty("nls_datetime_format_enabled", "false").equalsIgnoreCase("true"); 
  }
  
  public void setDatabaseName(String paramString) {
    this.databaseName = TbCommon.getEmptyString(paramString, "");
  }
  
  public void setDatabaseProductName(String paramString) {
    this.databaseProductName = TbCommon.getEmptyString(paramString, "");
  }
  
  public void setDatabaseProductVersion(String paramString) {
    this.databaseProductVersion = TbCommon.getEmptyString(paramString, "");
  }
  
  public void setDriverName(String paramString) {
    this.driverName = TbCommon.getEmptyString(paramString, "");
  }
  
  public void setDriverVersion(String paramString) {
    this.driverVersion = TbCommon.getEmptyString(paramString, "");
  }
  
  public void setDataSourceName(String paramString) {
    this.datasourceName = TbCommon.getEmptyString(paramString, "");
  }
  
  public void setDescription(String paramString) {
    this.description = TbCommon.getEmptyString(paramString, "");
  }
  
  public void setDriverType(String paramString) {
    this.driverType = TbCommon.getEmptyString(paramString, "thin");
  }
  
  public void setFailover(int paramInt) {
    this.failover = paramInt;
  }
  
  public void setInternal(boolean paramBoolean) {
    this.isInternal = paramBoolean;
  }
  
  public void setLoadBalance(boolean paramBoolean) {
    this.loadBalance = paramBoolean;
  }
  
  public void setLobMaxChunkSize(int paramInt) {
    this.lobMaxChunkSize = paramInt;
  }
  
  public void setLoginTimeout(int paramInt) {
    this.loginTimeout = paramInt;
  }
  
  public void setReadTimeout(int paramInt) {
    this.readTimeout = paramInt;
  }
  
  public void setCharacterSet(String paramString) {
    this.charset = paramString;
  }
  
  public void setProgramName(String paramString) {
    this.progname = TbCommon.getEmptyString(paramString, "");
  }
  
  public void setNetworkProtocol(String paramString) {
    this.protocol = TbCommon.getEmptyString(paramString, "tcp");
  }
  
  public void setNodeList(Vector paramVector) {
    this.nodeList = paramVector;
  }
  
  public void setPassword(String paramString) {
    this.password = TbCommon.getEmptyString(paramString, "");
  }
  
  public void setTDU(int paramInt) {
    this.tdu = (paramInt <= 0) ? 4096 : paramInt;
  }
  
  public void setURL(String paramString) {
    this.url = paramString;
  }
  
  public void setUser(String paramString) {
    this.user = getUserName(TbCommon.getEmptyString(paramString, ""));
  }
  
  public void setUser(String paramString, boolean paramBoolean) {
    if (paramBoolean) {
      setUser(paramString);
    } else {
      this.user = TbCommon.getEmptyString(paramString, "");
    } 
  }
  
  public void setXA(boolean paramBoolean) {
    this.isXA = paramBoolean;
  }
  
  public String getDatabaseProductName() {
    return this.databaseProductName;
  }
  
  public String getDatabaseProductVersion() {
    return this.databaseProductVersion;
  }
  
  public String getDriverName() {
    return this.driverName;
  }
  
  public String getDriverVersion() {
    return this.driverVersion;
  }
  
  public int getLobMaxChunkSize() {
    return this.lobMaxChunkSize;
  }
  
  public boolean useSelfKeepAlive() {
    return this.selfKeepAlive;
  }
  
  public void setSelfKeepAlive(boolean paramBoolean) {
    this.selfKeepAlive = paramBoolean;
  }
  
  public int getSelfKeepIdle() {
    return this.selfKeepIdle;
  }
  
  public void setSelfKeepIdle(int paramInt) {
    this.selfKeepIdle = paramInt;
  }
  
  public int getSelfKeepCount() {
    return this.selfKeepCount;
  }
  
  public void setSelfKeepCount(int paramInt) {
    this.selfKeepCount = paramInt;
  }
  
  public int getSelfKeepInterval() {
    return this.selfKeepInterval;
  }
  
  public void setSelfKeepInterval(int paramInt) {
    this.selfKeepInterval = paramInt;
  }
  
  public boolean getNlsDatetimeFormatEnabled() {
    return this.nlsDTFormatEnabled;
  }
  
  public void setNlsDatetimeFormatEnabled(boolean paramBoolean) {
    this.nlsDTFormatEnabled = paramBoolean;
  }
  
  public String toString() {
    StringBuffer stringBuffer = new StringBuffer(256);
    stringBuffer.append("ConnInfo[").append("User=").append(this.user).append("/Password=").append((this.password != null) ? "********" : null).append("/URL=").append(this.url).append("/DriverType=").append(this.driverType).append("/NetworkProtocol=").append(this.protocol).append("/TDU=").append(this.tdu).append("/DatabaseName=").append(this.databaseName).append("/DataSourceName=").append(this.datasourceName).append("/Failover=").append(this.failover).append("/LoadBalance=").append(this.loadBalance).append("/Description=").append(this.description).append("/ProgramName=").append(this.progname).append("/isXA=").append(this.isXA).append("/LoginTimeout=").append(this.loginTimeout).append("/ReadTimeout=").append(this.readTimeout).append("/SelfKeepAlive=").append(this.selfKeepAlive).append("/SelfKeepIdle=").append(this.selfKeepIdle).append("/SelfKeepInterval=").append(this.selfKeepInterval).append("/SelfKeepCount=").append(this.selfKeepCount).append("/IncludeSynonyms=").append(this.includeSynonyms).append("/MapDateToTimestamp=").append(this.mapDateToTimestamp).append("/NlsDatetimeFormatEnabled=").append(this.nlsDTFormatEnabled).append("]");
    return stringBuffer.toString();
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\ConnectionInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */