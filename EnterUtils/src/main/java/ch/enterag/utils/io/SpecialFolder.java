package ch.enterag.utils.io;

import ch.enterag.utils.EU;
import ch.enterag.utils.lang.Threads;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;






























public abstract class SpecialFolder
{
  private static final String sJAVA_SETTINGS_DIRECTORY = ".java";
  private static final String sDATA_DIRECTORY = "data";
  private static final String sAPPLICATIONS_DIRECTORY = "applications";
  private static final String sTEMP_DIRECTORY = "temp";
  
  public static File getJarFromClass(String sClassName, boolean bReport) {
    if (bReport)
      System.out.println("getJarFromClass(" + sClassName + ")"); 
    File fileJar = new File(".");


    
    try {
      URL url = SpecialFolder.class.getResource(sClassName);
      if (url == null) {
        
        if (bReport) {
          System.err.println("Class " + sClassName + " not found!");
        }
      } else if (url.getProtocol().equals("jar")) {
        
        if (bReport)
          System.out.println("URL: " + url.toString()); 
        String sJar = URLDecoder.decode(url.getPath(), "UTF-8");
        if (bReport)
          System.out.println("JAR: " + sJar); 
        int iExclamation = sJar.indexOf('!');
        if (iExclamation < 0)
          iExclamation = sJar.length(); 
        if (sJar.startsWith("file:"))
          sJar = sJar.substring(5, iExclamation); 
        if (sJar.length() > 2 && sJar.charAt(0) == '/' && sJar.charAt(2) == ':')
          sJar = sJar.substring(1); 
        fileJar = new File(sJar);
        if (bReport) {
          System.out.println("fileJar: " + fileJar.getAbsolutePath());
        }
      } else {
        
        System.err.println("Not in a JAR!");
        if (url.getProtocol().equals("file")) {
          
          String sFile = URLDecoder.decode(url.getPath(), "UTF-8");
          if (sFile.endsWith(sClassName))
            sFile = sFile.substring(0, sFile.length() - sClassName.length()); 
          if (sFile.length() > 2 && sFile.charAt(0) == '/' && sFile.charAt(2) == ':')
            sFile = sFile.substring(1); 
          fileJar = new File(sFile);
        } 
      } 
    } catch (UnsupportedEncodingException uee) {
      System.err.println(EU.getExceptionMessage(uee));
    } 
    return fileJar;
  }









  
  public static File getJarFromClass(Class<?> classAnchor, boolean bReport) {
    return getJarFromClass("/" + classAnchor.getName().replaceAll("\\.", "/") + ".class", bReport);
  }







  
  public static File getMainJar() {
    return getJarFromClass(Threads.getMainClass(Threads.getMainThread()), false);
  }






  
  public static File getCurrentMainJar() {
    return getJarFromClass(Threads.getMainClass(Thread.currentThread()), false);
  }






  
  public static File findInPath(String sFile) {
    File fileFound = null;
    File file = new File(sFile);
    if (file.exists()) {
      fileFound = file;
    } else {
      
      String[] asPath = System.getenv("PATH").split(File.pathSeparator);
      for (int i = 0; fileFound == null && i < asPath.length; i++) {
        
        String sDirectory = asPath[i];
        file = new File(sDirectory, sFile);
        if (file.isFile() && file.exists())
          fileFound = file; 
      } 
    } 
    return fileFound;
  }







  
  public static String getUserHome() {
    return (String)System.getProperties().get("user.home");
  }







  
  public static String getDesktopFolder() {
    File fileHome = new File(getUserHome());
    return fileHome.getAbsolutePath() + File.separator + "Desktop";
  }










  
  public static String getUserDataHome(String sApplicationName) {
    String sAppData = System.getenv("APPDATA");
    if (sAppData == null)
      sAppData = getUserHome() + File.separator + "data"; 
    String sUserAppHome = sAppData + File.separator + sApplicationName;
    
    File fileUserAppHome = new File(sUserAppHome);
    if (!fileUserAppHome.exists())
      fileUserAppHome.mkdirs(); 
    return sUserAppHome;
  }









  
  public static void removeUserDataHome(String sApplicationName) {
    Path pathUserAppHome = Paths.get(getUserDataHome(sApplicationName), new String[0]); 
    try { Files.deleteIfExists(pathUserAppHome); }
    catch (IOException ie) { System.err.println(ie.getClass().getName() + ": " + ie.getMessage()); }
  
  }









  
  public static String getUserLocalHome(String sApplicationName) {
    String sLocalAppData = System.getenv("LOCALAPPDATA");
    if (sLocalAppData == null)
      sLocalAppData = getUserHome() + File.separator + "applications"; 
    String sUserLocalHome = sLocalAppData + File.separator + sApplicationName;
    
    File fileUserLocalHome = new File(sUserLocalHome);
    if (!fileUserLocalHome.exists())
      fileUserLocalHome.mkdirs(); 
    return sUserLocalHome;
  }









  
  public static void removeUserLocalHome(String sApplicationName) {
    Path pathUserLocalHome = Paths.get(getUserLocalHome(sApplicationName), new String[0]); 
    try { Files.deleteIfExists(pathUserLocalHome); }
    catch (IOException ie) { System.err.println(ie.getClass().getName() + ": " + ie.getMessage()); }
  
  }







  
  public static String getUserTemp() {
    String sTemp = System.getenv("TEMP");
    if (sTemp == null) {
      sTemp = getUserHome() + File.separator + "temp";
    }
    File fileTemp = new File(sTemp);
    if (!fileTemp.exists())
      fileTemp.mkdirs(); 
    return sTemp;
  }








  
  public static String getUserJavaSettings() {
    String sUserJavaSettings = getUserHome() + File.separator + ".java";
    
    File fileUserJavaSettings = new File(sUserJavaSettings);
    if (!fileUserJavaSettings.exists())
      fileUserJavaSettings.mkdir(); 
    return sUserJavaSettings;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\utils\io\SpecialFolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */