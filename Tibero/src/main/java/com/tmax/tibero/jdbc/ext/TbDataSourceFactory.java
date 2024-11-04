package com.tmax.tibero.jdbc.ext;

import java.util.Hashtable;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.naming.spi.ObjectFactory;

public class TbDataSourceFactory implements ObjectFactory {
  private static String TB_DATASOURCE = "com.tmax.tibero.jdbc.ext.TbDataSource";
  
  private static String TB_CONN_POOL_DATASOURCE = "com.tmax.tibero.jdbc.ext.TbConnectionPoolDataSource";
  
  private static String TB_XA_DATASOURCE = "com.tmax.tibero.jdbc.ext.TbXADataSource";
  
  public Object getObjectInstance(Object paramObject, Name paramName, Context paramContext, Hashtable paramHashtable) throws Exception {
    if (paramObject == null)
      return null; 
    Reference reference = (Reference)paramObject;
    String str = reference.getClassName();
    TbDataSource tbDataSource = null;
    if (TB_DATASOURCE.equals(str)) {
      tbDataSource = new TbDataSource();
    } else if (TB_CONN_POOL_DATASOURCE.equals(str)) {
      tbDataSource = new TbConnectionPoolDataSource();
    } else if (TB_XA_DATASOURCE.equals(str)) {
      tbDataSource = new TbXADataSource();
    } else {
      return null;
    } 
    StringRefAddr stringRefAddr = null;
    if ((stringRefAddr = (StringRefAddr)reference.get("userName")) != null || (stringRefAddr = (StringRefAddr)reference.get("user")) != null || (stringRefAddr = (StringRefAddr)reference.get("u")) != null)
      tbDataSource.setUser((String)stringRefAddr.getContent()); 
    if ((stringRefAddr = (StringRefAddr)reference.get("password")) != null || (stringRefAddr = (StringRefAddr)reference.get("passWord")) != null)
      tbDataSource.setPassword((String)stringRefAddr.getContent()); 
    if ((stringRefAddr = (StringRefAddr)reference.get("tdu")) != null)
      tbDataSource.setTdu(Integer.parseInt((String)stringRefAddr.getContent())); 
    if ((stringRefAddr = (StringRefAddr)reference.get("databaseName")) != null || (stringRefAddr = (StringRefAddr)reference.get("sid")) != null)
      tbDataSource.setDatabaseName((String)stringRefAddr.getContent()); 
    if ((stringRefAddr = (StringRefAddr)reference.get("description")) != null || (stringRefAddr = (StringRefAddr)reference.get("describe")) != null)
      tbDataSource.setDescription((String)stringRefAddr.getContent()); 
    if ((stringRefAddr = (StringRefAddr)reference.get("dataSourceName")) != null)
      tbDataSource.setDataSourceName((String)stringRefAddr.getContent()); 
    if ((stringRefAddr = (StringRefAddr)reference.get("url")) != null || (stringRefAddr = (StringRefAddr)reference.get("Url")) != null) {
      tbDataSource.setURL((String)stringRefAddr.getContent());
    } else {
      if ((stringRefAddr = (StringRefAddr)reference.get("networkProtocol")) != null || (stringRefAddr = (StringRefAddr)reference.get("protocol")) != null)
        tbDataSource.setNetworkProtocol((String)stringRefAddr.getContent()); 
      if ((stringRefAddr = (StringRefAddr)reference.get("serverName")) != null || (stringRefAddr = (StringRefAddr)reference.get("host")) != null)
        tbDataSource.setServerName((String)stringRefAddr.getContent()); 
      if ((stringRefAddr = (StringRefAddr)reference.get("portNumber")) != null || (stringRefAddr = (StringRefAddr)reference.get("port")) != null)
        tbDataSource.setPortNumber(Integer.parseInt((String)stringRefAddr.getContent())); 
    } 
    Properties properties = new Properties();
    if ((stringRefAddr = (StringRefAddr)reference.get("failover")) != null)
      properties.setProperty("failover", (String)stringRefAddr.getContent()); 
    if ((stringRefAddr = (StringRefAddr)reference.get("load_balance")) != null)
      properties.setProperty("load_balance", (String)stringRefAddr.getContent()); 
    if ((stringRefAddr = (StringRefAddr)reference.get("failover_retry_count")) != null)
      properties.setProperty("failover_retry_count", (String)stringRefAddr.getContent()); 
    if ((stringRefAddr = (StringRefAddr)reference.get("characterset")) != null)
      properties.setProperty("characterset", (String)stringRefAddr.getContent()); 
    if ((stringRefAddr = (StringRefAddr)reference.get("statement_cache")) != null)
      properties.setProperty("statement_cache", (String)stringRefAddr.getContent()); 
    if ((stringRefAddr = (StringRefAddr)reference.get("statement_cache_max_size")) != null)
      properties.setProperty("statement_cache_max_size", (String)stringRefAddr.getContent()); 
    if ((stringRefAddr = (StringRefAddr)reference.get("lobChunkMaxSize")) != null)
      properties.setProperty("lobChunkMaxSize", (String)stringRefAddr.getContent()); 
    if ((stringRefAddr = (StringRefAddr)reference.get("includeSynonyms")) != null)
      properties.setProperty("includeSynonyms", (String)stringRefAddr.getContent()); 
    if ((stringRefAddr = (StringRefAddr)reference.get("mapDateToTimestamp")) != null)
      properties.setProperty("mapDateToTimestamp", (String)stringRefAddr.getContent()); 
    if ((stringRefAddr = (StringRefAddr)reference.get("defaultNChar")) != null)
      properties.setProperty("defaultNChar", (String)stringRefAddr.getContent()); 
    if ((stringRefAddr = (StringRefAddr)reference.get("nls_datetime_format_enabled")) != null)
      properties.setProperty("nls_datetime_format_enabled", (String)stringRefAddr.getContent()); 
    tbDataSource.setConnectionProperties(properties);
    return tbDataSource;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\ext\TbDataSourceFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */