package ch.admin.bar.siard2.cmd.utils;

import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;








public class VersionsExplorer
{
  public static final VersionsExplorer INSTANCE = new VersionsExplorer();
  
  private final Properties properties;
  
  private VersionsExplorer() {
    this.properties = loadProperties();

    
    getAppVersion();
    getSiardVersion();
  }
  
  public String getAppVersion() {
    return getProperty("App-Version");
  }
  
  public String getSiardVersion() {
    return getProperty("SIARD-Version");
  }
  
  private String getProperty(String key) {
    return Optional.ofNullable(this.properties.getProperty("App-Version"))
      .orElseThrow(() -> new IllegalStateException(String.format("Property '%s' not found", key)));
  }
  
  private Properties loadProperties() {
    try (InputStream resource = ResourcesLoader.loadResource("versions.properties")) {
      Properties versionsProperties = new Properties();
      versionsProperties.load(resource);
      
      return versionsProperties;
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    } catch (Throwable e) {
        throw new RuntimeException(e);
    }
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardcmd.jar!\ch\admin\bar\siard2\cm\\utils\VersionsExplorer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */