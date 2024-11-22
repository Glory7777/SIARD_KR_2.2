package com.tmax.tibero.jdbc.dpl.binder;

public class StaticDPLBinder {
  public static DPLBinder nullDPLBinder = new DPLNullBinder();
  
  public static DPLBinder stringDPLBinder = new DPLStringBinder();
  
  public static DPLBinder shortDPLBinder;
  
  public static DPLBinder intDPLBinder;
  
  public static DPLBinder longDPLBinder;
  
  public static DPLBinder floatDPLBinder;
  
  public static DPLBinder doubleDPLBinder;
  
  public static DPLBinder bigDecimalDPLBinder;
  
  public static DPLBinder dateDPLBinder;
  
  public static DPLBinder tbDateDPLBinder;
  
  public static DPLBinder timeDPLBinder;
  
  public static DPLBinder timestampDPLBinder;
  
  public static DPLBinder tbTimestampDPLBinder;
  
  public static DPLBinder tbTimestampTZDPLBinder;
  
  public static DPLBinder timestampLTZDPLBinder;
  
  public static DPLBinder bytesDPLBinder;
  
  public static DPLBinder booleanDPLBinder;
  
  public static DPLBinder rowIdDPLBinder = new DPLRowIdBinder();
  
  public static DPLBinder rawDPLBinder;
  
  public static DPLBinder emptyLOBDPLBinder;
  
  public static DPLBinder tbIntervalYtmDPLBinder;
  
  public static DPLBinder tbIntervalDtsDPLBinder;
  
  static {
    bytesDPLBinder = new DPLBytesBinder();
    intDPLBinder = new DPLIntBinder();
    longDPLBinder = new DPLLongBinder();
    floatDPLBinder = new DPLFloatBinder();
    doubleDPLBinder = new DPLDoubleBinder();
    bigDecimalDPLBinder = new DPLBigDecimalBinder();
    dateDPLBinder = new DPLDateBinder();
    tbDateDPLBinder = new DPLTbDateBinder();
    timeDPLBinder = new DPLTimeBinder();
    timestampDPLBinder = new DPLTimestampBinder();
    tbTimestampDPLBinder = new DPLTbTimestampBinder();
    tbTimestampTZDPLBinder = new DPLTbTimestampTZBinder();
    timestampLTZDPLBinder = new DPLTimestampLTZBinder();
    rawDPLBinder = new DPLRawBinder();
    emptyLOBDPLBinder = new DPLEmptyLOBBinder();
    tbIntervalYtmDPLBinder = new DPLTbIntervalYtmBinder();
    tbIntervalDtsDPLBinder = new DPLTbIntervalDtsBinder();
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\dpl\binder\StaticDPLBinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */