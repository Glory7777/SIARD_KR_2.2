package com.tmax.tibero.jdbc.util;

import java.util.Random;

public class TbRandom {
  private static final Random INTANCE = new Random();
  
  public static int nextInt(int paramInt) {
    return INTANCE.nextInt(paramInt);
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdb\\util\TbRandom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */