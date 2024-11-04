package com.tmax.tibero.jdbc.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TbDTFormat {
  private List<TbDTFormatElement> elements = null;
  
  private boolean trim;
  
  private boolean exactFormat;
  
  private boolean[] hasField;
  
  private boolean[] fieldWithAdditionalOption;
  
  TbDTFormat() {
    this.elements = new ArrayList<TbDTFormatElement>();
    this.trim = false;
    this.exactFormat = false;
    this.hasField = new boolean[12];
    Arrays.fill(this.hasField, false);
    this.fieldWithAdditionalOption = new boolean[12];
    Arrays.fill(this.fieldWithAdditionalOption, false);
  }
  
  List<TbDTFormatElement> getElements() {
    return this.elements;
  }
  
  void addElement(TbDTFormatElement paramTbDTFormatElement) {
    switch (paramTbDTFormatElement.getType()) {
      case 24:
        this.hasField[0] = true;
        break;
      case 25:
      case 46:
        this.hasField[1] = true;
        break;
      case 48:
      case 57:
        this.fieldWithAdditionalOption[2] = true;
      case 42:
      case 47:
      case 55:
      case 56:
        this.hasField[2] = true;
        break;
      case 41:
        this.hasField[3] = true;
        break;
      case 37:
      case 40:
        this.fieldWithAdditionalOption[4] = true;
      case 38:
      case 39:
        this.hasField[4] = true;
        break;
      case 53:
      case 54:
        this.hasField[5] = true;
        break;
      case 27:
      case 28:
      case 29:
      case 30:
        this.fieldWithAdditionalOption[6] = true;
      case 26:
        this.hasField[6] = true;
        break;
      case 23:
        this.hasField[7] = true;
        break;
      case 34:
      case 35:
        this.hasField[8] = true;
        break;
      case 36:
        this.hasField[9] = true;
        break;
      case 44:
      case 45:
        this.hasField[10] = true;
        break;
      case 33:
        this.hasField[11] = true;
        break;
    } 
    this.elements.add(paramTbDTFormatElement);
  }
  
  boolean getTrimEnabled() {
    return this.trim;
  }
  
  boolean getExactFormatEnabled() {
    return this.exactFormat;
  }
  
  boolean hasField(int paramInt) {
    return (paramInt < 0 || paramInt >= 12) ? false : this.hasField[paramInt];
  }
  
  boolean isFieldWithAdditionalOption(int paramInt) {
    return (paramInt < 0 || paramInt >= 12) ? false : this.fieldWithAdditionalOption[paramInt];
  }
  
  void setTrimEnabled(boolean paramBoolean) {
    this.trim = paramBoolean;
  }
  
  void setExactFormatEnabled(boolean paramBoolean) {
    this.exactFormat = paramBoolean;
  }
  
  public String toString() {
    StringBuffer stringBuffer = new StringBuffer(128);
    stringBuffer.append("TbDTFormat=[elements=");
    if (this.elements == null) {
      stringBuffer.append(this.elements);
    } else {
      Iterator<TbDTFormatElement> iterator = this.elements.iterator();
      stringBuffer.append('\n');
      while (iterator.hasNext())
        stringBuffer.append("  ").append(iterator.next()).append('\n'); 
    } 
    stringBuffer.append("/trim=").append(this.trim).append("/exactFormat=").append(this.exactFormat).append("]@").append(Integer.toHexString(hashCode()));
    return stringBuffer.toString();
  }
  
  public class Field {
    public static final int ERA = 0;
    
    public static final int CENTURY = 1;
    
    public static final int YEAR = 2;
    
    public static final int QUATER = 3;
    
    public static final int MONTH = 4;
    
    public static final int WEEK = 5;
    
    public static final int DAY = 6;
    
    public static final int AMPM = 7;
    
    public static final int HOUR = 8;
    
    public static final int MINUTE = 9;
    
    public static final int SECOND = 10;
    
    public static final int FRACTION = 11;
    
    public static final int MAX = 12;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdb\\util\TbDTFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */