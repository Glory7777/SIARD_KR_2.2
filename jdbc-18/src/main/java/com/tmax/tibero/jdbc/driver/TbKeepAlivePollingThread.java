package com.tmax.tibero.jdbc.driver;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TbKeepAlivePollingThread extends Thread {
  private HashMap<String, HashSet<TbKeepAlive>> keepAliveSetMap;
  
  private long sleepMillis;
  
  public TbKeepAlivePollingThread() {
    super("TbKeepAlivePollingThread");
    setDaemon(true);
    this.sleepMillis = 1000L;
    this.keepAliveSetMap = new HashMap<>();
    start();
  }
  
  public void run() {
    while (true) {
      try {
        Thread.sleep(this.sleepMillis);
      } catch (InterruptedException interruptedException) {}
      try {
        poll();
      } catch (Exception exception) {}
    } 
  }
  
  private void poll() {
    for (Map.Entry<String, HashSet<TbKeepAlive>> entry : this.keepAliveSetMap.entrySet()) {
      HashSet hashSet = (HashSet)entry.getValue();
      if (hashSet == null || hashSet.isEmpty())
        continue; 
      for (TbKeepAlive tbKeepAlive : entry.getValue()) {
        long l = System.currentTimeMillis();
        try {
          SocketChannel socketChannel = tbKeepAlive.getChannel();
          if (socketChannel != null) {
            if (socketChannel.finishConnect()) {
              int i = tbKeepAlive.checkReply();
              switch (i) {
                case 1:
                  tbKeepAlive.closeCheckChannel(i);
                  tbKeepAlive.resetBaseTime();
                  continue;
                case 4:
                  for (TbKeepAlive tbKeepAlive1 : this.keepAliveSetMap.get(entry.getKey())) {
                    tbKeepAlive1.closeCheckChannel(i);
                    tbKeepAlive1.closeOrgConnSocket(i);
                  } 
                  break;
              } 
              continue;
            } 
            if (l - tbKeepAlive.getBaseTime() > tbKeepAlive.getIntervalMillis()) {
              byte b = 3;
              if (tbKeepAlive.getTryCount() >= tbKeepAlive.getMaxRetryCount()) {
                for (TbKeepAlive tbKeepAlive1 : this.keepAliveSetMap.get(entry.getKey())) {
                  tbKeepAlive1.closeCheckChannel(b);
                  tbKeepAlive1.closeOrgConnSocket(b);
                } 
                break;
              } 
              tbKeepAlive.closeCheckChannel(b);
              tbKeepAlive.tryConnect();
            } 
            continue;
          } 
          if (l - tbKeepAlive.getBaseTime() > tbKeepAlive.getIdleMillis())
            tbKeepAlive.tryConnect(); 
        } catch (Throwable throwable) {
          tbKeepAlive.closeCheckChannel(5);
        } 
      } 
    } 
  }
  
  void add(TbKeepAlive paramTbKeepAlive) {
    synchronized (this.keepAliveSetMap) {
      String str = paramTbKeepAlive.getTargetAddressWithSvrInstId();
      HashSet<TbKeepAlive> hashSet = this.keepAliveSetMap.get(str);
      if (hashSet == null) {
        hashSet = new HashSet();
        this.keepAliveSetMap.put(str, hashSet);
      } 
      hashSet.add(paramTbKeepAlive);
    } 
  }
  
  void remove(TbKeepAlive paramTbKeepAlive) {
    synchronized (this.keepAliveSetMap) {
      String str = paramTbKeepAlive.getTargetAddressWithSvrInstId();
      if (str == null)
        return; 
      HashSet hashSet = this.keepAliveSetMap.get(str);
      if (hashSet != null)
        hashSet.remove(paramTbKeepAlive); 
    } 
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\driver\TbKeepAlivePollingThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */