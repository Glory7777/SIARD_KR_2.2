package com.tmax.tibero.jdbc.data.binder;

public class StaticBinder {
  private static Binder nullBinder = new NullBinder();
  
  private static Binder stringBinder = new StringBinder();
  
  private static Binder readerBinder = new ReaderBinder();
  
  private static Binder intBinder = new IntBinder();
  
  private static Binder longBinder = new LongBinder();
  
  private static Binder floatBinder = new FloatBinder();
  
  private static Binder doubleBinder = new DoubleBinder();
  
  private static Binder binaryFloatBinder;
  
  private static Binder binaryDoubleBinder;
  
  private static Binder bigDecimalBinder = new BigDecimalBinder();
  
  private static Binder dateBinder = new DateBinder();
  
  private static Binder timeBinder = new TimeBinder();
  
  private static Binder timestampBinder = new TimestampBinder();
  
  private static Binder tbDateBinder = new TbDateBinder();
  
  private static Binder tbTimestampBinder;
  
  private static Binder tbTimestampTZBinder = new TbTimestampTZBinder();
  
  private static Binder bytesBinder = new BytesBinder();
  
  private static Binder streamBinder = new StreamBinder();
  
  private static Binder nStringBinder = new NStringBinder();
  
  private static Binder nReaderBinder = new NReaderBinder();
  
  private static Binder structInBinder;
  
  private static Binder structOutBinder;
  
  private static Binder arrayInBinder;
  
  private static Binder arrayOutBinder;
  
  public static Binder getNullBinder() {
    return nullBinder;
  }
  
  public static Binder getStringBinder() {
    return stringBinder;
  }
  
  public static Binder getReaderBinder() {
    return readerBinder;
  }
  
  public static Binder getIntBinder() {
    return intBinder;
  }
  
  public static Binder getLongBinder() {
    return longBinder;
  }
  
  public static Binder getFloatBinder() {
    return floatBinder;
  }
  
  public static Binder getArrayInBinder() {
    return arrayInBinder;
  }
  
  public static Binder getArrayOutBinder() {
    return arrayOutBinder;
  }
  
  public static Binder getDoubleBinder() {
    return doubleBinder;
  }
  
  public static Binder getBinaryFloatBinder(boolean paramBoolean) {
    if (paramBoolean)
      binaryFloatBinder = new BinaryFloatBinder(); 
    return binaryFloatBinder;
  }
  
  public static Binder getBinaryDoubleBinder(boolean paramBoolean) {
    if (paramBoolean)
      binaryDoubleBinder = new BinaryDoubleBinder(); 
    return binaryDoubleBinder;
  }
  
  public static Binder getBigDecimalBinder() {
    return bigDecimalBinder;
  }
  
  public static Binder getDateBinder() {
    return dateBinder;
  }
  
  public static Binder getTimeBinder() {
    return timeBinder;
  }
  
  public static Binder getTimestampBinder() {
    return timestampBinder;
  }
  
  public static Binder getTbTimestampTZBinder() {
    return tbTimestampTZBinder;
  }
  
  public static Binder getTbDateBinder() {
    return tbDateBinder;
  }
  
  public static Binder getTbTimestampBinder() {
    return tbTimestampBinder;
  }
  
  public static Binder getBytesBinder() {
    return bytesBinder;
  }
  
  public static Binder getStreamBinder() {
    return streamBinder;
  }
  
  public static Binder getNStringBinder() {
    return nStringBinder;
  }
  
  public static Binder getNReaderBinder() {
    return nReaderBinder;
  }
  
  public static Binder getStructInBinder() {
    return structInBinder;
  }
  
  public static Binder getStructOutBinder() {
    return structOutBinder;
  }
  
  static {
    tbTimestampBinder = new TbTimestampBinder();
    binaryFloatBinder = new FloatBinder();
    binaryDoubleBinder = new DoubleBinder();
    structInBinder = new StructInBinder();
    structOutBinder = new StructOutBinder();
    arrayInBinder = new ArrayInBinder();
    arrayOutBinder = new ArrayOutBinder();
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\binder\StaticBinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */