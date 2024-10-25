package com.tmax.tibero.pivot;

public class TbPivotValHdrItem implements Comparable {
  int nameIdx;
  
  int offset;
  
  public TbPivotValHdrItem(int paramInt1, int paramInt2) {
    this.nameIdx = paramInt1;
    this.offset = paramInt2;
  }
  
  public int compareTo(Object paramObject) {
    return this.nameIdx - ((TbPivotValHdrItem)paramObject).nameIdx;
  }
  
  public int getOffset() {
    return this.offset;
  }
  
  public int getNameIdx() {
    return this.nameIdx;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\pivot\TbPivotValHdrItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */