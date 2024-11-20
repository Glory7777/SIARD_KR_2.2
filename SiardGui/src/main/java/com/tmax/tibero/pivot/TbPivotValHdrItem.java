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


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\pivot\TbPivotValHdrItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */