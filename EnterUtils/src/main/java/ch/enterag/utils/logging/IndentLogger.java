package ch.enterag.utils.logging;

import ch.enterag.utils.EU;
import java.sql.SQLWarning;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;























public class IndentLogger
  extends Logger
{
  private static final int iINDENT_AMOUNT = 2;
  private static final String sTAG_EVENT = "-- ";
  private static final String sTAG_ENTER = ">> ";
  private static final String sTAG_EXIT = "<< ";
  private static StringBuilder m_sbIndent = new StringBuilder();

  
  public int getIndent() {
    return m_sbIndent.toString().length();
  }


  
  public void setIndent(int iIndent) {
    int iPreviousIndent = getIndent();
    if (iIndent < 0)
      iIndent = 0; 
    m_sbIndent.setLength(iIndent);
    for (int i = iPreviousIndent; i < iIndent; i++) {
      m_sbIndent.setCharAt(i, ' ');
    }
  }



















  
  protected IndentLogger(String sName, String sResources) throws MissingResourceException {
    super(sName, sResources);
  }










  
  private String getCallingMethod(int iDepth) {
    String sCallingMethod = null;
    
    StackTraceElement[] asSte = Thread.currentThread().getStackTrace();
    String sClassName = asSte[iDepth].getClassName();
    String sMethodName = asSte[iDepth].getMethodName();
    sCallingMethod = sClassName + "." + sMethodName;
    return sCallingMethod;
  }









  
  public void log(LogRecord record) {
    if (isLoggable(record.getLevel())) {
      super.log(record);
    }
  }







  
  public synchronized void event(String sMessage) {
    finer(m_sbIndent.toString() + "-- " + sMessage);
  }






  
  public synchronized void enter(Object... aoParm) {
    if (isLoggable(Level.FINEST)) {
      
      StringBuilder sbMethod = new StringBuilder(getCallingMethod(3));
      sbMethod.append("(");
      for (int iParameter = 0; iParameter < aoParm.length; iParameter++) {
        
        if (iParameter > 0)
          sbMethod.append(", "); 
        sbMethod.append(String.valueOf(aoParm[iParameter]));
      } 
      sbMethod.append(")");
      finest(m_sbIndent.toString() + ">> " + sbMethod.toString());
      setIndent(getIndent() + 2);
    } 
  }





  
  public synchronized void exit() {
    if (isLoggable(Level.FINEST)) {
      
      StringBuilder sbMethod = new StringBuilder(getCallingMethod(3));
      setIndent(getIndent() - 2);
      finest(m_sbIndent.toString() + "<< " + sbMethod.toString());
    } 
  }






  
  public synchronized void exit(Object oReturn) {
    if (isLoggable(Level.FINEST)) {
      
      StringBuilder sbMethod = new StringBuilder(getCallingMethod(3));
      setIndent(getIndent() - 2);
      finest(m_sbIndent.toString() + "<< " + sbMethod.toString() + "(" + String.valueOf(oReturn) + ")");
    } 
  }








  
  public final void properties(String sTitle, Properties prop) {
    event(sTitle + ":");
    setIndent(getIndent() + 2);
    String sPropKey = null;
    String sPropValue = null;
    Enumeration<?> enumProperty = prop.propertyNames();
    while (enumProperty.hasMoreElements()) {

      
      sPropKey = (String)enumProperty.nextElement();
      sPropValue = prop.getProperty(sPropKey);
      info("  " + sPropKey + ": " + sPropValue);
    } 
    setIndent(getIndent() - 2);
  }




  
  public final void systemProperties() {
    Runtime rt = Runtime.getRuntime();
    info("free memory: " + String.valueOf(rt.freeMemory()));
    info("total memory: " + String.valueOf(rt.totalMemory()));
    info("maximum memory: " + String.valueOf(rt.maxMemory()));
    Properties propSystem = System.getProperties();
    properties("System properties", propSystem);
  }





  
  public synchronized void error(Error e) {
    if (isLoggable(Level.INFO)) {
      event(EU.getErrorMessage(e));
    }
  }




  
  public synchronized void exception(Exception e) {
    if (isLoggable(Level.FINER)) {
      event(EU.getExceptionMessage(e));
    }
  }




  
  public synchronized void sqlwarning(SQLWarning sw) {
    if (isLoggable(Level.FINER)) {
      
      StringBuilder sbMessage = null;
      for (; sw != null; sw = sw.getNextWarning()) {
        
        if (sbMessage == null) {
          sbMessage = new StringBuilder();
        } else {
          sbMessage = sbMessage.append("\n");
        }  sbMessage.append("Warning " + sw.getMessage() + " / SQL State " + sw.getSQLState() + " / Error Code " + sw.getErrorCode());
      } 
      if (sbMessage != null) {
        event(sbMessage.toString());
      }
    } 
  }









  
  public static IndentLogger getIndentLogger(String sName) {
    IndentLogger il = new IndentLogger(sName, null);
    LogManager lm = LogManager.getLogManager();
    lm.addLogger(il);
    return il;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\utils\logging\IndentLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */