package ch.enterag.utils;

import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


















public class ProgramInfo
{
  private static final String sSPECIFIED_BY = "Specified by : ";
  private static final String sDEVELOPED_BY = "Developed by : ";
  private static final String sTESTED_BY = "Tested by    : ";
  private static final String sMANAGED_BY = "Managed by   : ";
  private static final String sTRANSLATED_BY = "Translated by: ";
  private static final String sJAVA = "JAVA         : ";
  protected static ProgramInfo _info = null;
  
  private static IndentLogger _il = IndentLogger.getIndentLogger(ProgramInfo.class.getName());

  
  private String _sApplication = null;
  
  private String _sAppVersion = null;
  
  private String _sProgram = null;
  
  private String _sVersion = null;
  
  private String _sDescription = null;
  
  private String _sCopyright = null;
  
  private List<String> _listSpecifiers = new ArrayList<>();
  
  private List<String> _listDevelopers = new ArrayList<>();
  
  private List<String> _listTesters = new ArrayList<>();
  
  private List<String> _listManagers = new ArrayList<>();
  
  private Map<String, String> _mapTranslators = new HashMap<>();




  
  public String toString(List<String> list) {
    StringBuffer sb = new StringBuffer();
    for (Iterator<String> iterator = list.iterator(); iterator.hasNext(); ) {
      
      String s = iterator.next();
      if (sb.length() > 0)
        sb.append("; "); 
      sb.append(s);
    } 
    return sb.toString();
  }








  
  public String getOs() {
    return System.getProperty("os.name");
  }





  
  public String getOsVersion() {
    return System.getProperty("os.version");
  }





  
  public String getOsArchitecture() {
    return System.getProperty("os.arch");
  }





  
  public String getJavaVersion() {
    return System.getProperty("java.version");
  }





  
  public String getJavaArchitecture() {
    return System.getProperty("sun.arch.data.model");
  }





  
  public String getApplication() {
    return this._sApplication;
  }





  
  public String getAppVersion() {
    return this._sAppVersion;
  }





  
  public String getProgram() {
    return this._sProgram;
  }





  
  public String getVersion() {
    return this._sVersion;
  }





  
  public String getDescription() {
    return this._sDescription;
  }





  
  public void setDescription(String sDescription) {
    this._sDescription = sDescription;
  }





  
  public String getCopyright() {
    return this._sCopyright;
  }





  
  public List<String> getSpecifierList() {
    return this._listSpecifiers;
  }





  
  public String getSpecifiers() {
    return toString(this._listSpecifiers);
  }





  
  public void addSpecifier(String sSpecifier) {
    this._listSpecifiers.add(sSpecifier);
  }





  
  public List<String> getDeveloperList() {
    return this._listDevelopers;
  }





  
  public String getDevelopers() {
    return toString(this._listDevelopers);
  }





  
  public void addDeveloper(String sDeveloper) {
    this._listDevelopers.add(sDeveloper);
  }





  
  public List<String> getTesterList() {
    return this._listTesters;
  }





  
  public String getTesters() {
    return toString(this._listTesters);
  }





  
  public void addTester(String sTester) {
    this._listTesters.add(sTester);
  }





  
  public List<String> getManagerList() {
    return this._listManagers;
  }





  
  public String getManagers() {
    return toString(this._listManagers);
  }





  
  public void addManager(String sManager) {
    this._listManagers.add(sManager);
  }





  
  public Map<String, String> getTranslatorMap() {
    return this._mapTranslators;
  }






  
  public void addTranslator(String sLanguage, String sTranslator) {
    this._mapTranslators.put(sLanguage, sTranslator);
  }
























  
  public static ProgramInfo getProgramInfo(String sApplication, String sAppVersion, String sProgram, String sVersion, String sDescription, String sCopyright, List<String> listSpecifiers, List<String> listDevelopers, List<String> listTesters, List<String> listManagers) {
    _info = new ProgramInfo(sApplication, sAppVersion, sProgram, sVersion, sDescription, sCopyright, listSpecifiers, listDevelopers, listTesters, listManagers);





    
    return _info;
  }












  
  public static ProgramInfo getProgramInfo(String sApplication, String sAppVersion, String sProgram, String sVersion, String sDescription, String sCopyright) {
    _info = new ProgramInfo(sApplication, sAppVersion, sProgram, sVersion, sDescription, sCopyright);
    
    return _info;
  }





  
  public static ProgramInfo getProgramInfo() {
    if (_info == null)
      throw new RuntimeException("ProgramInfo must be initialized first!"); 
    return _info;
  }




  
  private void printList(String sLabel, List<String> list) {
    int iItem = 0;
    if (iItem < list.size()) {
      
      System.out.println(sLabel + (String)list.get(iItem));
      String sIndent = SU.repeat(sLabel.length(), ' ');
      for (; ++iItem < list.size(); iItem++) {
        System.out.println(sIndent + (String)list.get(iItem));
      }
    } 
  }
  
  private void printMap(String sLabel, Map<String, String> map) {
    int iItem = 0;
    if (iItem < map.size()) {
      
      String[] asLanguage = (String[])map.keySet().toArray((Object[])new String[0]);
      System.out.println(sLabel + (String)map.get(asLanguage[iItem]) + " (" + asLanguage[iItem] + ")");
      String sIndent = SU.repeat(sLabel.length(), ' ');
      for (; ++iItem < asLanguage.length; iItem++) {
        System.out.println(sIndent + (String)map.get(asLanguage[iItem]) + " (" + asLanguage[iItem] + ")");
      }
    } 
  }



  
  public void printStart() {
    String sDescription = "";
    if (getDescription() != null)
      sDescription = " - " + getDescription(); 
    System.out.println(getProgram() + " " + getVersion() + sDescription);
    System.out.println(getApplication() + " " + getAppVersion() + ": (c) " + getCopyright());
    printList("Specified by : ", this._listSpecifiers);
    printList("Developed by : ", this._listDevelopers);
    printList("Tested by    : ", this._listTesters);
    printList("Managed by   : ", this._listManagers);
    printMap("Translated by: ", this._mapTranslators);
    System.out.println("JAVA         : Version " + getJavaVersion() + " (" + getJavaArchitecture() + ")");
    StringBuilder sbOs = new StringBuilder(getOs());
    while (sbOs.length() < "JAVA         : ".length() - 2)
      sbOs.append(" "); 
    sbOs.append(": ");
    System.out.println(sbOs.toString() + "Version " + getOsVersion() + " (" + getOsArchitecture() + ")");
  }




  
  public void printTermination() {
    System.out.println(getProgram() + " " + getVersion() + " terminates.");
  }




  
  private void logList(String sLabel, List<String> list) {
    int iItem = 0;
    if (iItem < list.size()) {
      
      _il.info(sLabel + (String)list.get(iItem));
      String sIndent = SU.repeat(sLabel.length(), ' ');
      for (; ++iItem < list.size(); iItem++) {
        _il.info(sIndent + (String)list.get(iItem));
      }
    } 
  }



  
  private void logMap(String sLabel, Map<String, String> map) {
    int iItem = 0;
    if (iItem < map.size()) {
      
      String[] asLanguage = (String[])map.keySet().toArray((Object[])new String[0]);
      _il.info(sLabel + (String)map.get(asLanguage[iItem]) + " (" + asLanguage[iItem] + ")");
      String sIndent = SU.repeat(sLabel.length(), ' ');
      for (; ++iItem < asLanguage.length; iItem++) {
        _il.info(sIndent + (String)map.get(asLanguage[iItem]) + " (" + asLanguage[iItem] + ")");
      }
    } 
  }



  
  public void logStart() {
    String sDescription = "";
    if (getDescription() != null)
      sDescription = " - " + getDescription(); 
    _il.info(getProgram() + " " + getVersion() + sDescription);
    _il.info(getApplication() + " " + getAppVersion() + ": " + getCopyright());
    logList("Specified by : ", this._listSpecifiers);
    logList("Developed by : ", this._listDevelopers);
    logList("Tested by    : ", this._listTesters);
    logList("Managed by   : ", this._listManagers);
    logMap("Translated by: ", this._mapTranslators);
    _il.info("JAVA         : Version " + getJavaVersion() + " (" + getJavaArchitecture() + ")");
    StringBuilder sbOs = new StringBuilder(getOs());
    while (sbOs.length() < "JAVA         : ".length() - 2)
      sbOs.append(" "); 
    sbOs.append(": ");
    _il.info(sbOs.toString() + "Version " + getOsVersion() + " (" + getOsArchitecture() + ")");
  }




  
  public void logTermination() {
    _il.info(getProgram() + " " + getVersion() + " terminates.");
  }














  
  protected ProgramInfo(String sApplication, String sAppVersion, String sProgram, String sVersion, String sDescription, String sCopyright) {
    _info = this;
    this._sApplication = sApplication;
    this._sAppVersion = sAppVersion;
    this._sProgram = sProgram;
    this._sVersion = sVersion;
    this._sDescription = sDescription;
    this._sCopyright = sCopyright;
  }




















  
  protected ProgramInfo(String sApplication, String sAppVersion, String sProgram, String sVersion, String sDescription, String sCopyright, List<String> listSpecifiers, List<String> listDevelopers, List<String> listTesters, List<String> listManagers) {
    _info = this;
    this._sApplication = sApplication;
    this._sAppVersion = sAppVersion;
    this._sProgram = sProgram;
    this._sVersion = sVersion;
    this._sDescription = sDescription;
    this._sCopyright = sCopyright;
    if (listSpecifiers != null)
      this._listSpecifiers = listSpecifiers; 
    if (listDevelopers != null)
      this._listDevelopers = listDevelopers; 
    if (listTesters != null)
      this._listTesters = listTesters; 
    if (listManagers != null) {
      this._listManagers = listManagers;
    }
  }











  
  public int compareVersion(String sVersion) {
    int iCompare = 1;
    if (sVersion != null) {
      
      iCompare = 0;
      String[] asInternal = getVersion().split("\\.");
      String[] asExternal = sVersion.split("\\.");
      int iIndex = 0;
      
      for (; iCompare == 0 && iIndex < asInternal.length && iIndex < asExternal.length; 
        iIndex++) {
        
        int iInternal = Integer.parseInt(asInternal[iIndex]);
        int iExternal = Integer.parseInt(asExternal[iIndex]);
        iCompare = Integer.compare(iInternal, iExternal);
      } 
      if (iCompare == 0)
      {
        if (iIndex < asInternal.length) {
          iCompare = 1;
        } else if (iIndex < asExternal.length) {
          iCompare = -1;
        }  } 
    } 
    return iCompare;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\utils\ProgramInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */