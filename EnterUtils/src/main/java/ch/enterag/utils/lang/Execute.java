package ch.enterag.utils.lang;

import ch.enterag.utils.logging.IndentLogger;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;










public class Execute
{
  private static IndentLogger _il = IndentLogger.getIndentLogger(Execute.class.getName());
  
  private String _sStdOut = null;
  public String getStdOut() {
    return this._sStdOut;
  }
  private String _sStdErr = null;
  public String getStdErr() {
    return this._sStdErr;
  }
  private int _iResult = -1;
  public int getResult() {
    return this._iResult;
  }






  
  private static List<String> splitVersion(String sVersion) {
    List<String> listVersion = new ArrayList<>();
    String sPart = "";
    for (int i = 0; i < sVersion.length(); i++) {
      
      char c = sVersion.charAt(i);
      if (Character.isLetterOrDigit(c)) {
        sPart = sPart + c;
      } else {
        
        if (sPart.length() > 0)
          listVersion.add(sPart); 
        sPart = "";
      } 
    } 
    if (sPart.length() > 0)
      listVersion.add(sPart); 
    return listVersion;
  }









  
  public static boolean ltVersion(String sVersion1, String sVersion2) {
    _il.enter(new Object[] { sVersion1, sVersion2 });
    boolean bEqual = true;
    boolean bLess = false;
    List<String> listVersion1 = splitVersion(sVersion1);
    List<String> listVersion2 = splitVersion(sVersion2);
    for (int i = 0; bEqual && i < listVersion1.size() && i < listVersion2.size(); i++) {
      
      String s1 = listVersion1.get(i);
      String s2 = listVersion2.get(i);
      int comp = s1.compareTo(s2);
      
      try {
        int i1 = Integer.parseInt(s1);
        int i2 = Integer.parseInt(s2);
        comp = Integer.compare(i1, i2);
      }
      catch (NumberFormatException numberFormatException) {}
      if (comp < 0) {
        
        bLess = true;
        bEqual = false;
      }
      else {
        
        bLess = false;
        if (comp != 0)
          bEqual = false; 
      } 
    } 
    _il.exit(Boolean.valueOf(bLess));
    return bLess;
  }







  
  public static boolean isJavaVersionLessThan(String sVersion) {
    String sJavaVersion = System.getProperty("java.version");
    if (sJavaVersion.startsWith("1."))
      sJavaVersion = sJavaVersion.substring(2); 
    if (sVersion.startsWith("1."))
      sVersion = sVersion.substring(2); 
    return ltVersion(sJavaVersion, sVersion);
  }




  
  public static boolean isOsWindows() {
    boolean bIsOsWindows = false;
    String sOsName = System.getProperty("os.name");
    if (sOsName != null)
      bIsOsWindows = sOsName.startsWith("Windows"); 
    return bIsOsWindows;
  }




  
  public static boolean isOsLinux() {
    boolean bIsOsLinux = false;
    String sOsName = System.getProperty("os.name");
    if (sOsName != null)
      bIsOsLinux = sOsName.startsWith("Linux"); 
    return bIsOsLinux;
  }









  
  private String captureOutput(InputStream is) throws IOException {
    InputStreamReader isrdrOut = new InputStreamReader(is);
    StringWriter swOut = new StringWriter(); int i;
    for (i = isrdrOut.read(); i != -1; i = isrdrOut.read())
      swOut.write(i); 
    swOut.close();
    return swOut.toString();
  }









  
  private void redirectInput(Reader rdr, OutputStream os) throws IOException {
    _il.event("stdin redirected");
    OutputStreamWriter oswr = new OutputStreamWriter(os); int i;
    for (i = rdr.read(); i != -1; i = rdr.read())
      oswr.write(i); 
    oswr.close();
  }






  
  private int run(String sCommand) {
    _il.enter(new Object[] { sCommand });
    int iReturn = -1;

    
    try { Process proc = Runtime.getRuntime().exec(sCommand);
      this._sStdOut = captureOutput(proc.getInputStream());
      _il.event("stdout: " + this._sStdOut.toString());
      this._sStdErr = captureOutput(proc.getErrorStream());
      _il.event("stderr: " + this._sStdErr.toString());
      iReturn = proc.waitFor(); }
    catch (IOException ie)
    { _il.exception(ie); }
    catch (InterruptedException ie) { _il.exception(ie); }
     _il.exit(String.valueOf(iReturn));
    return iReturn;
  }






  
  public static Execute execute(String sCommand) {
    Execute ex = new Execute();
    ex._iResult = ex.run(sCommand);
    return ex;
  }







  
  private int run(String sCommand, Reader rdrInput) {
    _il.enter(new Object[] { sCommand, rdrInput });
    int iReturn = -1;

    
    try { Process proc = Runtime.getRuntime().exec(sCommand);
      redirectInput(rdrInput, proc.getOutputStream());
      this._sStdOut = captureOutput(proc.getInputStream());
      _il.event("stdout: " + this._sStdOut.toString());
      this._sStdErr = captureOutput(proc.getErrorStream());
      _il.event("stderr: " + this._sStdErr.toString());
      iReturn = proc.waitFor(); }
    catch (InterruptedException ie)
    { _il.exception(ie); }
    catch (IOException ie) { _il.exception(ie); }
     _il.exit(String.valueOf(iReturn));
    return iReturn;
  }







  
  public static Execute execute(String sCommand, Reader rdrInput) {
    Execute ex = new Execute();
    ex._iResult = ex.run(sCommand, rdrInput);
    return ex;
  }






  
  private int run(String[] asCommand) {
    _il.enter((Object[])asCommand);
    int iReturn = -1;

    
    try { Process proc = Runtime.getRuntime().exec(asCommand);
      this._sStdOut = captureOutput(proc.getInputStream());
      _il.event("stdout: " + this._sStdOut.toString());
      this._sStdErr = captureOutput(proc.getErrorStream());
      _il.event("stderr: " + this._sStdErr.toString());
      iReturn = proc.waitFor(); }
    catch (InterruptedException ie)
    { _il.exception(ie); }
    catch (IOException ie) { _il.exception(ie); }
     _il.exit(String.valueOf(iReturn));
    return iReturn;
  }






  
  public static Execute execute(String[] asCommand) {
    Execute ex = new Execute();
    ex._iResult = ex.run(asCommand);
    return ex;
  }







  
  private int run(String[] asCommand, File fileWorkingDirectory) {
    _il.enter((Object[])asCommand);
    int iReturn = -1;

    
    try { Process proc = Runtime.getRuntime().exec(asCommand, (String[])null, fileWorkingDirectory);
      this._sStdOut = captureOutput(proc.getInputStream());
      _il.event("stdout: " + this._sStdOut.toString());
      this._sStdErr = captureOutput(proc.getErrorStream());
      _il.event("stderr: " + this._sStdErr.toString());
      iReturn = proc.waitFor(); }
    catch (InterruptedException ie)
    { _il.exception(ie); }
    catch (IOException ie) { _il.exception(ie); }
     _il.exit(String.valueOf(iReturn));
    return iReturn;
  }







  
  public static Execute execute(String[] asCommand, File fileWorkingDirectory) {
    Execute ex = new Execute();
    ex._iResult = ex.run(asCommand, fileWorkingDirectory);
    return ex;
  }








  
  private int run(String[] asCommand, File fileWorkingDirectory, Reader rdrInput) {
    _il.enter((Object[])asCommand);
    int iReturn = -1;

    
    try { Process proc = Runtime.getRuntime().exec(asCommand, (String[])null, fileWorkingDirectory);
      redirectInput(rdrInput, proc.getOutputStream());
      this._sStdOut = captureOutput(proc.getInputStream());
      _il.event("stdout: " + this._sStdOut.toString());
      this._sStdErr = captureOutput(proc.getErrorStream());
      _il.event("stderr: " + this._sStdErr.toString());
      iReturn = proc.waitFor(); }
    catch (InterruptedException ie)
    { _il.exception(ie); }
    catch (IOException ie) { _il.exception(ie); }
     _il.exit(String.valueOf(iReturn));
    return iReturn;
  }








  
  public static Execute execute(String[] asCommand, File fileWorkingDirectory, Reader rdrInput) {
    Execute ex = new Execute();
    ex._iResult = ex.run(asCommand, fileWorkingDirectory, rdrInput);
    return ex;
  }







  
  private int run(String[] asCommand, Reader rdrInput) {
    _il.enter(new Object[] { asCommand, rdrInput });
    int iReturn = -1;

    
    try { Process proc = Runtime.getRuntime().exec(asCommand);
      redirectInput(rdrInput, proc.getOutputStream());
      this._sStdOut = captureOutput(proc.getInputStream());
      _il.event("stdout: " + this._sStdOut.toString());
      this._sStdErr = captureOutput(proc.getErrorStream());
      _il.event("stderr: " + this._sStdErr.toString());
      iReturn = proc.waitFor(); }
    catch (InterruptedException ie)
    { _il.exception(ie); }
    catch (IOException ie) { _il.exception(ie); }
     _il.exit(String.valueOf(iReturn));
    return iReturn;
  }








  
  public static Execute execute(String[] asCommand, Reader rdrInput) {
    Execute ex = new Execute();
    ex._iResult = ex.run(asCommand, rdrInput);
    return ex;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\utils\lang\Execute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */