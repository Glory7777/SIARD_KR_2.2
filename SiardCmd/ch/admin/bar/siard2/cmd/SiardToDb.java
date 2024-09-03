package ch.admin.bar.siard2.cmd;

import ch.admin.bar.siard2.api.Archive;
import ch.admin.bar.siard2.api.MetaData;
import ch.admin.bar.siard2.api.MetaSchema;
import ch.admin.bar.siard2.api.primary.ArchiveImpl;
import ch.admin.bar.siard2.cmd.utils.RuntimeHelper;
import ch.admin.bar.siard2.cmd.utils.VersionsExplorer;
import ch.enterag.utils.EU;
import ch.enterag.utils.ProgramInfo;
import ch.enterag.utils.background.Progress;
import ch.enterag.utils.cli.Arguments;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;













public class SiardToDb
{
  private static final Logger LOG = LoggerFactory.getLogger(SiardToDb.class);

  
  public static final int iRETURN_OK = 0;

  
  public static final int iRETURN_WARNING = 4;

  
  public static final int iRETURN_ERROR = 8;

  
  public static final int iRETURN_FATAL = 12;


  private static ProgramInfo _pi = ProgramInfo.getProgramInfo("SIARD Suite", VersionsExplorer.INSTANCE
                  .getSiardVersion(), "SiardToDb", VersionsExplorer.INSTANCE
                  .getAppVersion(), "Program to load database content from a .siard file", "Swiss Federal Archives, Berne, Switzerland, 2008-2016",
          Arrays.asList("Hartwig Thomas, Enter AG, Rüti ZH, Switzerland", "Andreas Voss, Swiss Federal Archives, Berne, Switzerland", "Anders Bo Nielsen, Danish National Archives, Denmark", "Claire Röthlisberger-Jourdan, KOST, Berne, Switzerland"),
          Arrays.asList("Hartwig Thomas, Enter AG, Rüti ZH, Switzerland", "Simon Jutz, Cytex GmbH, Zurich, Switzerland"),
          Arrays.asList("Claudia Matthys, POOL Computer AG, Zurich, Switzerland", "Marcel Büchler, Swiss Federal Archives, Berne, Switzerland", "Yvan Dutoit, Swiss Federal Archives, Berne, Switzerland"),
          Arrays.asList("Hartwig Thomas, Enter AG, Rüti ZH, Switzerland", "Marcel Büchler, Swiss Federal Archives, Berne, Switzerland", "Alain Mast, Swiss Federal Archives, Berne, Switzerland", "Krystyna Ohnesorge, Swiss Federal Archives, Berne, Switzerland"));
  private int _iLoginTimeoutSeconds = 300;
  private int _iQueryTimeoutSeconds = 75;
  private boolean _bOverwrite = false;
  private String _sJdbcUrl = null;
  private String _sDatabaseUser = null;
  private String _sDatabasePassword = null;
  private File _fileSiard = null;
  private Map<String, String> _mapSchemas = new HashMap<>();
  
  private Archive _archive = null;
  private Connection _conn = null;
  
  private int _iReturn = 4; public int getReturn() {
    return this._iReturn;
  }


  private void printUsage() {
    System.out.println("Usage:");
    System.out.println("java -cp <siardpath>/lib/siardcmd.jar ch.admin.bar.siard2.cmd.SiardToDb [-h]");
    System.out.println("  [-o][-q=<query timeout>][-l=<login timeout>]");
    System.out.println("  -s=<siard file> -j=<JDBC URL> -u=<database user> -p=<database password>");
    System.out.println("  [<schema> <mappedschema>]*");
    System.out.println("where");
    System.out.println("  <siardpath>         installation path of SiardCmd");
    System.out.println("  -h (help)           prints this usage information");
    System.out.println("  -o                  overwrite existing database objects");
    System.out.println("  <login timeout>     login timeout in seconds (default: " + String.valueOf(this._iLoginTimeoutSeconds) + ")");
    System.out.println("  <query timeout>     query timeout in seconds (default: " + String.valueOf(this._iQueryTimeoutSeconds) + ")");
    System.out.println("  <JDBC URL>          JDBC URL of database to be uploaded");
    System.out.print("                      e.g. ");
    SiardConnection sc = SiardConnection.getSiardConnection();
    String[] asSchemes = sc.getSchemes();
    for (int i = 0; i < asSchemes.length; i++) {
      
      if (i > 0)
        System.out.println("                           "); 
      String sScheme = asSchemes[i];
      System.out.println("for " + sc.getTitle(sScheme) + ": " + sc.getSampleUrl(sScheme, "dbserver.enterag.ch", "D:\\dbfolder", "testdb"));
    } 
    System.out.println("  <database user>     database user");
    System.out.println("  <database password> database password");
    System.out.println("  <siard file>        name of .siard file (will be overwritten. if it exists!)");
    System.out.println("  <schema>            schema name in SIARD file");
    System.out.println("  <mappedschema>      schema name to be used in database");
  }




  
  private void getParameters(String[] asArgs) {
    this._iReturn = 0;
    Arguments args = Arguments.newInstance(asArgs);
    if (args.getOption("h") != null) {
      this._iReturn = 4;
    }
    String sLoginTimeoutSeconds = args.getOption("l");
    
    String sQueryTimeoutSeconds = args.getOption("q");
    
    if (args.getOption("o") != null) {
      this._bOverwrite = true;
    }
    this._sJdbcUrl = args.getOption("j");
    
    this._sDatabaseUser = args.getOption("u");
    
    this._sDatabasePassword = args.getOption("p");
    
    String sSiardFile = args.getOption("s");
    
    for (int i = 0; i < args.getArguments() / 2; i++) {
      
      String sSchema = args.getArgument(2 * i);
      String sMappedSchema = args.getArgument(2 * i + 1);
      this._mapSchemas.put(sSchema, sMappedSchema);
    } 

    
    if (this._iReturn == 0) {
      
      if (sLoginTimeoutSeconds != null) {
        try {
          this._iLoginTimeoutSeconds = Integer.parseInt(sLoginTimeoutSeconds);
        } catch (NumberFormatException nfe) {
          
          System.err.println("Invalid login timeout: " + sLoginTimeoutSeconds + "!");
          this._iReturn = 8;
        } 
      }
      if (sQueryTimeoutSeconds != null) {
        try {
          this._iQueryTimeoutSeconds = Integer.parseInt(sQueryTimeoutSeconds);
        } catch (NumberFormatException nfe) {
          
          System.err.println("Invalid query timeout: " + sQueryTimeoutSeconds + "!");
          this._iReturn = 8;
        } 
      }
      String sError = SiardConnection.getSiardConnection().loadDriver(this._sJdbcUrl);
      if (sError != null) {
        
        System.err.println(sError);
        this._iReturn = 8;
      } 
      if (this._sDatabaseUser == null) {
        
        System.err.println("Database user must be given!");
        this._iReturn = 8;
      } 
      if (this._sDatabasePassword == null) {
        
        System.err.println("Database password must be given!");
        this._iReturn = 8;
      } 
      if (sSiardFile != null) {
        this._fileSiard = new File(sSiardFile);
      } else {
        
        System.err.println("SIARD file must be given!");
        this._iReturn = 8;
      } 
      if (args.getArguments() % 2 != 0) {
        
        System.err.println("Dangling schema name on command line!");
        this._iReturn = 8;
      } 
    } 
    
    if (this._iReturn == 0) {






      
      StringBuilder sb = (new StringBuilder()).append("\n").append("Parameters\n").append("  SIARD file             : ").append(this._fileSiard.getAbsolutePath()).append("\n").append("  JDBC URL               : ").append(this._sJdbcUrl).append("\n").append("  Database user          : ").append(this._sDatabaseUser).append("\n").append("  Database password      : ***\n");
      if (sLoginTimeoutSeconds != null)
        sb.append("  Login timeout          : ").append(this._iLoginTimeoutSeconds).append(" seconds\n"); 
      if (sQueryTimeoutSeconds != null)
        sb.append("  Query timeout          : ").append(this._iQueryTimeoutSeconds).append(" seconds\n"); 
      if (this._bOverwrite)
        sb.append("  Overwrite              : Database objects may be overwritten on upload\n"); 
      if (this._mapSchemas.size() > 0)
      {
        for (Iterator<String> iterSchema = this._mapSchemas.keySet().iterator(); iterSchema.hasNext(); ) {
          
          String sSchema = iterSchema.next();
          String sMappedSchema = this._mapSchemas.get(sSchema);
          sb.append("  Mapped Schema          : \"").append(sSchema).append("\" => \"").append(sMappedSchema).append("\"");
        } 
      }
      sb.append("\n");
      
      String message = sb.toString();
      LOG.info(message);
      System.out.println(message);
    } else {
      
      printUsage();
    } 
  }








  
  public SiardToDb(String[] asArgs) throws IOException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
    getParameters(asArgs);
    if (this._iReturn == 0) {

      
      this._archive = ArchiveImpl.newInstance();
      this._archive.open(this._fileSiard);
      
      String sError = null;
      
      SiardConnection siardConnection = SiardConnection.getSiardConnection();
      if (sError == null || sError.length() == 0) {
        
        DriverManager.setLoginTimeout(this._iLoginTimeoutSeconds);
        this._conn = siardConnection.createValidConnection(this._sJdbcUrl, this._sDatabaseUser, this._sDatabasePassword);
        if (this._conn != null && !this._conn.isClosed()) {
          
          System.out.println("Connected to " + this._conn.getMetaData().getURL().toString());
          this._conn.setAutoCommit(false);
          
          MetaData md = this._archive.getMetaData();
          MetaDataToDb mdtd = MetaDataToDb.newInstance(this._conn.getMetaData(), md, this._mapSchemas);
          mdtd.setQueryTimeout(this._iQueryTimeoutSeconds);
          if (this._bOverwrite || (mdtd.tablesDroppedByUpload() == 0 && mdtd.typesDroppedByUpload() == 0)) {
            
            if (!mdtd.supportsUdts()) {
              
              int iTypesInSiard = 0;
              for (int iSchema = 0; iSchema < md.getMetaSchemas(); iSchema++) {
                
                MetaSchema ms = md.getMetaSchema(iSchema);
                iTypesInSiard += ms.getMetaTypes();
              } 
              if (iTypesInSiard > 0) {
                String message = "Target database does not support UDTs. UDTs will be \"flattened\".";
                
                LOG.info("Target database does not support UDTs. UDTs will be \"flattened\".");
                System.out.println("Target database does not support UDTs. UDTs will be \"flattened\".");
              } 
            } 
            mdtd.upload((Progress)null);
            
            PrimaryDataToDb pdtd = PrimaryDataToDb.newInstance(this._conn, this._archive, mdtd
                .getArchiveMapping(), mdtd.supportsArrays(), mdtd.supportsDistincts(), mdtd.supportsUdts());
            pdtd.setQueryTimeout(this._iQueryTimeoutSeconds);
            pdtd.upload((Progress)null);
          }
          else {
            
            System.err.println("Database objects exist which would be overwritten on upload!");
            System.err.println("Backup and delete them first or use -o option for overwriting them.");
            this._iReturn = 4;
          } 
          
          this._conn.close();
        } else {
          
          System.err.println("Connection to " + this._conn.getMetaData().getURL().toString() + " failed!");
        } 
      } else {
        System.err.println("Connection to " + this._sJdbcUrl + " not supported (" + sError + ")!");
      }  this._archive.close();
    } 
  }








  
  public static void main(String[] asArgs) {
    int iReturn = 4;
    
    try {
      _pi.printStart();
      _pi.logStart();
      LOG.info(RuntimeHelper.getRuntimeInformation());
      
      SiardToDb stdb = new SiardToDb(asArgs);
      
      _pi.logTermination();
      
      _pi.printTermination();
      iReturn = stdb.getReturn();
    }
    catch (Exception e) {
      
      LOG.error("Exception while uploading SIARD archive", e);
      System.err.println(EU.getExceptionMessage(e));
      iReturn = 8;
    }
    catch (Error e) {
      
      LOG.error("Error while uploading SIARD archive", e);
      System.err.println(EU.getErrorMessage(e));
      iReturn = 12;
    } 
    System.exit(iReturn);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardcmd.jar!\ch\admin\bar\siard2\cmd\SiardToDb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */