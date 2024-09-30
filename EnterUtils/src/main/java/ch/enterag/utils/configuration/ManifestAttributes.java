package ch.enterag.utils.configuration;

import ch.enterag.utils.io.SpecialFolder;
import ch.enterag.utils.logging.IndentLogger;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.jar.Manifest;






public class ManifestAttributes
  extends Manifest
{
  private static IndentLogger _il = IndentLogger.getIndentLogger(ManifestAttributes.class.getName());



  
  private static final String sMANIFEST_RESOURCE = "/META-INF/MANIFEST.MF";



  
  public ManifestAttributes(InputStream is) throws IOException {
    super(is);
    _il.enter(new Object[0]);
    _il.exit();
  }





  
  public String getImplementationVersion() {
    _il.enter(new Object[0]);
    String sImplementationVersion = getMainAttributes().getValue("Implementation-Version");
    _il.exit(sImplementationVersion);
    return sImplementationVersion;
  }





  
  public String getImplementationTitle() {
    _il.enter(new Object[0]);
    String sImplementationTitle = getMainAttributes().getValue("Implementation-Title");
    _il.exit(sImplementationTitle);
    return sImplementationTitle;
  }





  
  public String getImplementationVendor() {
    _il.enter(new Object[0]);
    String sImplementationVendor = getMainAttributes().getValue("Implementation-Vendor");
    _il.exit(sImplementationVendor);
    return sImplementationVendor;
  }





  
  public String getSpecificationVersion() {
    _il.enter(new Object[0]);
    String sSpecificationVersion = getMainAttributes().getValue("Specification-Version");
    _il.exit(sSpecificationVersion);
    return sSpecificationVersion;
  }





  
  public String getSpecificationTitle() {
    _il.enter(new Object[0]);
    String sSpecificationTitle = getMainAttributes().getValue("Specification-Title");
    _il.exit(sSpecificationTitle);
    return sSpecificationTitle;
  }





  
  public String getSpecificationVendor() {
    _il.enter(new Object[0]);
    String sSpecificationVendor = getMainAttributes().getValue("Specification-Vendor");
    _il.exit(sSpecificationVendor);
    return sSpecificationVendor;
  }





  
  public Calendar getBuiltDate() {
    _il.enter(new Object[0]);
    Calendar calBuiltDate = null;
    String sBuiltDate = getMainAttributes().getValue("Built-Date");
    if (sBuiltDate != null) {
      
      SimpleDateFormat sdf = new SimpleDateFormat("dd. MMMM yyyy", Locale.US);
      
      try {
        Date date = sdf.parse(sBuiltDate);
        calBuiltDate = new GregorianCalendar();
        calBuiltDate.setTime(date);
      } catch (ParseException pe) {
        _il.exception(pe);
      } 
    }  _il.exit(calBuiltDate);
    return calBuiltDate;
  }






  
  public static ManifestAttributes getInstance(InputStream is) {
    _il.enter(new Object[] { is });
    ManifestAttributes mfa = null;
    if (is != null)
      
      try { mfa = new ManifestAttributes(is); }
      catch (IOException ie) { _il.exception(ie); }
       
    _il.exit(mfa);
    return mfa;
  }






  
  public static ManifestAttributes getInstance(Class<?> clazz) {
    _il.enter(new Object[] { clazz });
    ManifestAttributes mfa = null;

    
    try {
      URL urlManifest = clazz.getResource("/META-INF/MANIFEST.MF");
      _il.event("Initial manifest: " + String.valueOf(urlManifest));
      File fileJar = SpecialFolder.getJarFromClass(clazz, false);
      if (fileJar != null)
      {
        
        if (fileJar.isFile()) {
          
          URL urlJarFile = fileJar.toURI().toURL();
          _il.event("JAR file URL: " + String.valueOf(urlJarFile));
          String sJarUrl = "jar:" + urlJarFile.toString() + "!" + "/META-INF/MANIFEST.MF";
          urlManifest = new URL(sJarUrl);
          _il.event("Manifest in JAR: " + urlManifest.toString());
        }
        else {
          
          File fileManifest = null;
          if (fileJar.getAbsolutePath().contains("/out/production/")) {
            fileManifest = new File(fileJar.getAbsolutePath() + "/META-INF/MANIFEST.MF");
          } else {
            fileManifest = new File(fileJar.getParentFile().getParentFile().getParentFile().getAbsolutePath() + "/tmp/jar/MANIFEST.MF");
          } 
          urlManifest = fileManifest.toURI().toURL();
          System.out.println(urlManifest);
        } 
      }
      _il.event("Using " + String.valueOf(urlManifest));
      InputStream is = urlManifest.openStream();
      if (is != null)
        mfa = new ManifestAttributes(is); 
    } catch (IOException ie) {
      _il.exception(ie);
    }  _il.exit(mfa);
    return mfa;
  }





  
  public static ManifestAttributes getInstance() {
    return getInstance(ManifestAttributes.class);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\utils\configuration\ManifestAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */