package ch.enterag.utils;














public abstract class EU
{
  public static String getThrowableMessage(Throwable t) {
    String sMessage = "";
    Throwable tException = t;
    for (; tException != null; tException = tException.getCause()) {
      
      if (tException == t) {
        sMessage = sMessage + tException.getClass().getName() + ": ";
      } else {
        sMessage = sMessage + "< " + tException.getClass().getName() + ": ";
      }  if (tException.getMessage() != null)
        sMessage = sMessage + tException.getMessage(); 
    } 
    return "  " + sMessage;
  }






  
  public static String getErrorMessage(Error e) {
    return getThrowableMessage(e);
  }






  
  public static String getExceptionMessage(Exception e) {
    return getThrowableMessage(e);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\utils\EU.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */