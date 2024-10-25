package com.tmax.tibero.jdbc.util;

public class TbDTFormatElement {
  private String text;
  
  private int type;
  
  private int etcValue;
  
  static TbDTFormatElement newInstance(int paramInt, String paramString) {
    TbDTFormatElement tbDTFormatElement = new TbDTFormatElement();
    tbDTFormatElement.type = paramInt;
    tbDTFormatElement.text = paramString;
    tbDTFormatElement.etcValue = 0;
    return tbDTFormatElement;
  }
  
  static TbDTFormatElement newInstance(int paramInt1, String paramString, int paramInt2) {
    TbDTFormatElement tbDTFormatElement = new TbDTFormatElement();
    tbDTFormatElement.type = paramInt1;
    tbDTFormatElement.text = paramString;
    tbDTFormatElement.etcValue = paramInt2;
    return tbDTFormatElement;
  }
  
  String getText() {
    return this.text;
  }
  
  int getType() {
    return this.type;
  }
  
  int getEtcValue() {
    return this.etcValue;
  }
  
  public String toString() {
    StringBuffer stringBuffer = new StringBuffer(64);
    stringBuffer.append("TbDTFormatElement=[");
    if (this.type == 15) {
      stringBuffer.append("DELIMITER");
    } else {
      stringBuffer.append("FIELD(").append(this.type).append(')');
    } 
    stringBuffer.append("/text=\"").append(this.text).append("\"/etcValue=").append(this.etcValue).append("]@").append(Integer.toHexString(hashCode()));
    return stringBuffer.toString();
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdb\\util\TbDTFormatElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */