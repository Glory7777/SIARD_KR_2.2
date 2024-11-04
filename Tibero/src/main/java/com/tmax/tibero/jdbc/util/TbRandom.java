package com.tmax.tibero.jdbc.util;

import java.util.Random;

public class TbRandom {
  private static final Random INTANCE = new Random();
  
  public static int nextInt(int paramInt) {
    return INTANCE.nextInt(paramInt);
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdb\\util\TbRandom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */