package ch.admin.bar.siard2.cmd;

import ch.admin.bar.siard2.jdbc.AccessDriver;
import ch.admin.bar.siard2.jdbc.Db2Driver;
import ch.admin.bar.siard2.jdbc.MsSqlDriver;
import ch.admin.bar.siard2.jdbc.MySqlDriver;
import ch.admin.bar.siard2.jdbc.OracleDriver;
import ch.admin.bar.siard2.jdbc.PostgresDriver;
import ch.enterag.utils.EU;
import ch.enterag.utils.io.SpecialFolder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;













public class SiardConnection
  extends Properties
{
  private static final Logger LOG = LoggerFactory.getLogger(SiardConnection.class);



  
  private static SiardConnection _sc = null;
  
  public static final int iDEFAULT_QUERY_TIMEOUT_SECONDS = 75;
  
  public static final int iDEFAULT_LOGIN_TIMEOUT_SECONDS = 300;
  
  private static final String sCONFIG_FOLDER = "etc";
  
  private static final String sJDBCDRIVERS_PROPERTIES = "jdbcdrivers.properties";
  
  private static final String sTITLE_SUFFIX = "_title";
  
  private static final String sSAMPLE_SUFFIX = "_sample";
  
  private static final String sOPTION_SUFFIX = "_option";
  
  private static File getJdbcDriversPropertiesFile() {
    File fileDrivers = null;
    String sFileDrivers = System.getProperty("ch.admin.bar.siard2.cmd.drivers");
    if (sFileDrivers != null) {
      fileDrivers = new File(sFileDrivers);
    } else {
      
      fileDrivers = SpecialFolder.getMainJar();
      
      if (fileDrivers.isFile())
        fileDrivers = fileDrivers.getParentFile(); 
      fileDrivers = fileDrivers.getParentFile();
      fileDrivers = new File(fileDrivers.getAbsolutePath() + File.separator + "etc" + File.separator + "jdbcdrivers.properties");
    } 


    
    return fileDrivers;
  }





  
  private static Properties getJdbcDriversDefaultProperties() {
    Properties propDrivers = new Properties();
    propDrivers.put("postgresql", PostgresDriver.class.getName());
    propDrivers.put("postgresql_title", "PostgreSQL");
    propDrivers.put("postgresql_sample", "jdbc:postgresql://dbserver.enterag.ch:5432/testdb");
    propDrivers.put("oracle", OracleDriver.class.getName());
    propDrivers.put("oracle_title", "Oracle");
    propDrivers.put("oracle_sample", "jdbc:oracle:thin:@dbserver.enterag.ch:1521:orcl");
    propDrivers.put("sqlserver", MsSqlDriver.class.getName());
    propDrivers.put("sqlserver_title", "SQL Server");
    propDrivers.put("sqlserver_sample", "jdbc:sqlserver://dbserver.enterag.ch:1433;databaseName=testdb");
    propDrivers.put("mysql", MySqlDriver.class.getName());
    propDrivers.put("mysql_title", "SQL Server");
    propDrivers.put("mysql_sample", "jdbc:mysql://dbserver.enterag.ch:3306/testdb");
    propDrivers.put("db2", Db2Driver.class.getName());
    propDrivers.put("db2_title", "DB/2");
    propDrivers.put("db2_sample", "jdbc:db2:dbserver.enterag.ch:50000/testdb");
    propDrivers.put("access", AccessDriver.class.getName());
    propDrivers.put("access_title", "Microsoft Access");
    propDrivers.put("access_sample", "jdbc:access:D:\\Projekte\\SIARD2\\JdbcAccess\\testfiles\\dbfile.mdb");
    return propDrivers;
  }






  
  public synchronized Enumeration<Object> keys() {
    return Collections.enumeration(new TreeSet(keySet()));
  }





  
  private SiardConnection() {
    File fileJdbcDrivers = getJdbcDriversPropertiesFile();
    
    try {
      if (fileJdbcDrivers.exists()) {
        
        FileReader fr = new FileReader(fileJdbcDrivers);
        load(fr);
        fr.close();
      }
      else {
        
        Properties propDefault = getJdbcDriversDefaultProperties();
        Set<String> setSchemes = propDefault.stringPropertyNames();
        for (Iterator<String> iterScheme = setSchemes.iterator(); iterScheme.hasNext(); ) {
          
          String sScheme = iterScheme.next();
          setProperty(sScheme, propDefault.getProperty(sScheme));
        } 
        FileWriter fw = new FileWriter(fileJdbcDrivers);
        store(fw, "Map from JDBC sub schema to title, sample URL and JDBC Driver class to be loaded.");
        fw.close();
      } 
    } catch (IOException ie) {
      System.err.println(EU.getExceptionMessage(ie));
    } 
  }




  
  public static SiardConnection getSiardConnection() {
    if (_sc == null)
      _sc = new SiardConnection(); 
    return _sc;
  }
  
  static String extractSubSchema(String jdbcUrl) {
    String[] split = jdbcUrl.split(":");
    
    if (split.length < 3 || !split[0].equals("jdbc")) {
      throw new IllegalArgumentException(jdbcUrl + " is not a valid JDBC-Url.");
    }
    
    return split[1];
  }
  
  public Driver loadValidDriver(String jdbcUrl) {
    try {
      String subSchema = extractSubSchema(jdbcUrl);
      
      String driverClassName = getProperty(subSchema);
      if (driverClassName == null) {
        throw new IllegalArgumentException("No driver specified for url " + jdbcUrl);
      }
      Class<?> driverClass = Class.forName(driverClassName);
      Driver driver = (Driver)driverClass.newInstance();
      
      if (!driver.acceptsURL(jdbcUrl)) {
        throw new IllegalStateException(String.format("Driver %s does not accept url %s.", new Object[] { driver
                
                .getClass().getCanonicalName(), jdbcUrl }));
      }

      
      LOG.info("'{}' as driver loaded for url '{}'", driver.getClass().getCanonicalName(), jdbcUrl);
      
      return driver;
    } catch (Throwable $ex) {
      throw $ex;
    } 
  }

  
  public Connection createValidConnection(String jdbcUrl, String user, String password) throws SQLException {
    logDrivers(jdbcUrl);
    
    Properties info = new Properties();
    
    if (user != null) {
      info.put("user", user);
    }
    if (password != null) {
      info.put("password", password);
    }
    
    Driver driver = loadValidDriver(jdbcUrl);
    Connection connection = driver.connect(jdbcUrl, info);
    
    LOG.info("Created connection of type '{}'for url '{}'", connection.getClass().getCanonicalName(), jdbcUrl);
    
    return connection;
  }



  
  private static void logDrivers(String jdbcUrl) {
    try {
      StringBuilder sb = (new StringBuilder()).append("Registered JDBC drivers and its compatibility for url '").append(jdbcUrl).append("':");
      
      Enumeration<Driver> enumeration = DriverManager.getDrivers();
      while (enumeration.hasMoreElements()) {
        Driver driver = enumeration.nextElement();
        sb.append("\n - ")
          .append(driver.getClass().getCanonicalName())
          .append(": ")
          .append(driver.acceptsURL(jdbcUrl));
      } 
      
      LOG.info(sb.toString());
    } catch (Throwable $ex) {
      throw $ex;
    } 
  }




  
  public String loadDriver(String sJdbcUrl) {
    String sError = null;
    
    if (sJdbcUrl == null) {
      sError = "JDBC URL of database must be given!";
    } else if (sJdbcUrl.startsWith("jdbc")) {

      
      String sSubScheme = sJdbcUrl.substring("jdbc".length() + 1);
      int iSubScheme = sSubScheme.indexOf(":");
      if (iSubScheme >= 0) {
        
        sSubScheme = sSubScheme.substring(0, iSubScheme);

        
        String sJdbcDriverClass = getProperty(sSubScheme);
        if (sJdbcDriverClass != null) {
          
          try {
            Class.forName(sJdbcDriverClass);
          } catch (ClassNotFoundException cnfe) {
            
            sError = "Driver " + sJdbcDriverClass + " could not be loaded (" + EU.getExceptionMessage(cnfe) + ")!";
          } 
        } else {
          
          sError = "No driver found for sub scheme \"" + sSubScheme + "\"!";
        } 
      } else {
        sError = "JDBC URL " + sJdbcUrl + " must contain a subs scheme terminated by colon (\":\")!";
      } 
    } else {
      sError = "JDBC URL " + sJdbcUrl + " must start with \"" + "jdbc" + "\"!";
    }  if (sError != null)
      System.err.println(sError); 
    return sError;
  }





  
  public String[] getSchemes() {
    List<String> listSchemes = new ArrayList<>();
    for (Enumeration<Object> enumKeys = keys(); enumKeys.hasMoreElements(); ) {
      
      String sKey = (String)enumKeys.nextElement();
      if (!sKey.endsWith("_title") && !sKey.endsWith("_sample") && !sKey.endsWith("_option"))
        listSchemes.add(sKey); 
    } 
    return listSchemes.<String>toArray(new String[0]);
  }






  
  public String getDriverClass(String sScheme) {
    return getProperty(sScheme);
  }






  
  public String getTitle(String sScheme) {
    return getProperty(sScheme + "_title");
  }






  
  public int getOptions(String sScheme) {
    return (getProperty(sScheme + "_sample").split("\\|")).length;
  }











  
  public String getSampleUrl(String sScheme, String sHost, String sFolder, String sDatabase, int iOption) {
    String sSampleUrl = getProperty(sScheme + "_sample");
    sSampleUrl = sSampleUrl.split("\\|")[iOption];
    return MessageFormat.format(sSampleUrl, new Object[] { sHost, sFolder.replace("\\", "/"), sDatabase });
  }








  
  public String getOption(String sScheme, int iOption) {
    String sOption = getProperty(sScheme + "_option");
    sOption = sOption.split("\\|")[iOption];
    return sOption;
  }










  
  public String getSampleUrl(String sScheme, String sHost, String sFolder, String sDatabase) {
    return getSampleUrl(sScheme, sHost, sFolder, sDatabase, 0);
  }

  
  public boolean isLocal(String sScheme) {
    boolean bLocal = false;
    if (getProperty(sScheme + "_sample").indexOf("{0}") < 0)
      bLocal = true; 
    return bLocal;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardcmd.jar!\ch\admin\bar\siard2\cmd\SiardConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */