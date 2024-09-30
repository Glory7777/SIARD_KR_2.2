package ch.enterag.utils;

import java.util.TimeZone;











public abstract class TZ
{
  private static final TimeZone m_tzLocal = TimeZone.getDefault();
  public static void setLocalTimeZone() { TimeZone.setDefault(m_tzLocal); } public static void setUtcTimeZone() {
    TimeZone.setDefault(getUtcTimeZone());
  }




  
  public static TimeZone getUtcTimeZone() {
    TimeZone tz = TimeZone.getTimeZone("GMT");
    tz.setRawOffset(0);
    return tz;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\utils\TZ.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */