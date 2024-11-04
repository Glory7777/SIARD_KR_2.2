package com.tmax.tibero.jdbc.data;

public class BigLiteral {
  private int index;
  
  private String value;
  
  public BigLiteral(int paramInt, String paramString) {
    this.index = paramInt;
    this.value = paramString;
  }
  
  public int getLiteralIndex() {
    return this.index;
  }
  
  public String getLiteralValue() {
    return this.value;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\BigLiteral.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */