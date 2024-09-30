package ch.enterag.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;














public class StopWatch
{
  private static DecimalFormatSymbols dfsEUROPE = new DecimalFormatSymbols();
  private static final DecimalFormat dfLONG = new DecimalFormat("#,##0");
  private static final DecimalFormat dfDOUBLE = new DecimalFormat("#,##0.00");



  
  private long m_lMsStart = 0L;
  private long m_lMsAccumulated = 0L; public long getMsAccumulated() {
    return this.m_lMsAccumulated;
  }







  
  private StopWatch() {
    dfsEUROPE.setGroupingSeparator('\'');
    dfsEUROPE.setDecimalSeparator('.');
    dfLONG.setDecimalFormatSymbols(dfsEUROPE);
    dfDOUBLE.setDecimalFormatSymbols(dfsEUROPE);
  }





  
  public static StopWatch getInstance() {
    return new StopWatch();
  }







  
  public void start() {
    this.m_lMsStart = System.currentTimeMillis();
  }






  
  public long stop() {
    long lMsInterval = System.currentTimeMillis() - this.m_lMsStart;
    this.m_lMsAccumulated += lMsInterval;
    return lMsInterval;
  }






  
  public static String formatLong(long lLong) {
    return dfLONG.format(lLong);
  }





  
  public String formatMs() {
    return formatLong(this.m_lMsAccumulated);
  }







  
  public String formatRate(long lUnits, long lMs) {
    double dRate = lUnits;
    if (lMs > 0L) {
      dRate /= lMs;
    } else {
      dRate = 0.0D;
    }  return dfDOUBLE.format(dRate);
  }






  
  public String formatRate(long lUnits) {
    return formatRate(lUnits, this.m_lMsAccumulated);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\utils\StopWatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */