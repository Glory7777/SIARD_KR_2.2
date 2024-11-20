package com.tmax.tibero.jdbc.ext;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TbPropertyFileReloadThread implements Runnable {
  private String fileName;
  
  private Properties props;
  
  private int reloadTime;
  
  private boolean stop;
  
  public TbPropertyFileReloadThread(String paramString, int paramInt) {
    this.fileName = paramString;
    this.reloadTime = paramInt;
    this.stop = false;
  }
  
  public void run() {
    try {
      while (!this.stop) {
        this.props = loadProperties(this.fileName);
        Thread.sleep(this.reloadTime);
      } 
    } catch (InterruptedException interruptedException) {
    
    } catch (IOException iOException) {}
  }
  
  public Properties loadProperties(String paramString) throws IOException {
    File file = null;
    FileInputStream fileInputStream = null;
    Properties properties = null;
    try {
      file = new File(paramString);
      properties = new Properties();
      fileInputStream = new FileInputStream(file);
      properties.load(new BufferedInputStream(fileInputStream));
    } finally {
      if (file != null)
        try {
          fileInputStream.close();
        } catch (IOException iOException) {} 
    } 
    return properties;
  }
  
  public Properties getProperties() {
    return this.props;
  }
  
  public void stop() {
    this.stop = true;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\ext\TbPropertyFileReloadThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */