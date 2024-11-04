package com.tmax.tibero.jdbc.comm;

import java.net.Socket;
import java.util.Hashtable;

public class TbSocketRegistry {
  private static Hashtable cache = new Hashtable<Object, Object>();
  
  public static void putSocket(Socket paramSocket) {
    String str = Thread.currentThread().getName();
    cache.put(str, paramSocket);
  }
  
  public static Socket removeSocket() {
    String str = Thread.currentThread().getName();
    return (Socket)cache.remove(str);
  }
  
  public static Socket getSocket() {
    String str = Thread.currentThread().getName();
    return (Socket)cache.get(str);
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\comm\TbSocketRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */