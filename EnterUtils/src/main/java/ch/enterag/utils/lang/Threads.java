package ch.enterag.utils.lang;

import ch.enterag.utils.EU;
import ch.enterag.utils.logging.IndentLogger;














public abstract class Threads
{
  private static IndentLogger _il = IndentLogger.getIndentLogger(Threads.class.getName());





  
  public static Thread getMainThread() {
    _il.enter(new Object[0]);
    Thread threadMain = null;
    int iActive = Thread.activeCount();
    Thread[] athread = new Thread[2 * iActive];
    int iEnumerated = Thread.enumerate(athread);
    if (iEnumerated < athread.length) {
      
      long lMinId = Long.MAX_VALUE;
      for (int i = 0; i < iEnumerated; i++) {
        
        Thread thread = athread[i];
        if (thread.getId() < lMinId) {
          
          threadMain = thread;
          lMinId = thread.getId();
        } 
      } 
    } 
    
    _il.exit(threadMain.getName());
    return threadMain;
  }








  
  public static Class<?> getMainClass(Thread thread) {
    Class<?> clsMain = null;
    StackTraceElement[] aste = thread.getStackTrace();
    for (int i = 0; clsMain == null && i < aste.length; i++) {
      
      String sClassName = aste[i].getClassName();
      String sMethodName = aste[i].getMethodName();
      
      if (!sClassName.startsWith("java") && 
        !sClassName.startsWith("com.sun") && 
        !sClassName.startsWith("sun") && sMethodName
        .equals("main"))
        
        try { clsMain = Class.forName(sClassName); }
        catch (ClassNotFoundException cnfe) { System.err.println(EU.getExceptionMessage(cnfe)); }
         
    } 
    return clsMain;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\utils\lang\Threads.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */