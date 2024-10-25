package com.tmax.tibero.jdbc.driver;

public class TbTimeoutPollingThread extends Thread {
  private int count;
  
  private long sleepMillis;
  
  private TbTimeout[] registeredTimeouts;
  
  public TbTimeoutPollingThread() {
    super("TbTimeoutPollingThread");
    setDaemon(true);
    setPriority(10);
    this.registeredTimeouts = new TbTimeout[2];
    this.count = 0;
    this.sleepMillis = 1000L;
    start();
  }
  
  public synchronized void add(TbTimeout paramTbTimeout) {
    int i = 0;
    if (this.count >= this.registeredTimeouts.length) {
      TbTimeout[] arrayOfTbTimeout = new TbTimeout[this.registeredTimeouts.length * 4];
      System.arraycopy(this.registeredTimeouts, 0, arrayOfTbTimeout, 0, this.registeredTimeouts.length);
      i = this.registeredTimeouts.length;
      this.registeredTimeouts = arrayOfTbTimeout;
    } 
    while (i < this.registeredTimeouts.length) {
      if (this.registeredTimeouts[i] == null) {
        this.registeredTimeouts[i] = paramTbTimeout;
        this.count++;
        break;
      } 
      i++;
    } 
  }
  
  private void poll() {
    if (this.count > 0) {
      long l = System.currentTimeMillis();
      for (byte b = 0; b < this.registeredTimeouts.length; b++) {
        try {
          if (this.registeredTimeouts[b] != null)
            this.registeredTimeouts[b].cancelStmtOfTimeover(l); 
        } catch (NullPointerException nullPointerException) {}
      } 
    } 
  }
  
  public synchronized void remove(TbTimeout paramTbTimeout) {
    for (byte b = 0; b < this.registeredTimeouts.length; b++) {
      if (this.registeredTimeouts[b] == paramTbTimeout) {
        this.registeredTimeouts[b] = null;
        this.count--;
        break;
      } 
    } 
  }
  
  public void run() {
    while (true) {
      try {
        Thread.sleep(this.sleepMillis);
      } catch (InterruptedException interruptedException) {}
      poll();
    } 
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\driver\TbTimeoutPollingThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */