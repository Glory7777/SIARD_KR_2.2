package com.tmax.tibero.jdbc.data;

public class RsetType {
  public static final RsetType NULL = new RsetType(0, -1, -1);
  
  public static final RsetType FWRD = new RsetType(1, 1003, 1007);
  
  public static final RsetType FWUP = new RsetType(2, 1003, 1008);
  
  public static final RsetType SIRD = new RsetType(3, 1004, 1007);
  
  public static final RsetType SIUP = new RsetType(4, 1004, 1008);
  
  public static final RsetType SSRD = new RsetType(5, 1005, 1007);
  
  public static final RsetType SSUP = new RsetType(6, 1005, 1008);
  
  public static final int DEFAULT_TYPE = 1003;
  
  public static final int DEFAULT_CONCURRENCY = 1007;
  
  private int rank;
  
  private int type;
  
  private int concurrency;
  
  private RsetType(int paramInt1, int paramInt2, int paramInt3) {
    this.type = paramInt2;
    this.concurrency = paramInt3;
    this.rank = paramInt1;
  }
  
  public boolean equals(Object paramObject) {
    return (paramObject == null || !(paramObject instanceof RsetType)) ? false : ((((RsetType)paramObject).rank == this.rank && ((RsetType)paramObject).type == this.type && ((RsetType)paramObject).concurrency == this.concurrency));
  }
  
  public static RsetType getDownGradedRsetType(int paramInt) {
    switch (paramInt) {
      case 0:
        return NULL;
      case 1:
        return FWRD;
      case 2:
        return FWRD;
      case 3:
        return SIRD;
      case 4:
        return SIRD;
      case 5:
        return SIRD;
      case 6:
        return SIRD;
    } 
    return FWRD;
  }
  
  public int getConcurrency() {
    return this.concurrency;
  }
  
  public RsetType getCopy() {
    switch (this.rank) {
      case 0:
        return NULL;
      case 1:
        return FWRD;
      case 2:
        return FWUP;
      case 3:
        return SIRD;
      case 4:
        return SIUP;
      case 5:
        return SSRD;
      case 6:
        return SSUP;
    } 
    return FWRD;
  }
  
  public int getRank() {
    return this.rank;
  }
  
  public static RsetType getRsetType(int paramInt1, int paramInt2) {
    return (paramInt1 == 1003 && paramInt2 == 1007) ? FWRD : ((paramInt1 == 1003 && paramInt2 == 1008) ? FWUP : ((paramInt1 == 1004 && paramInt2 == 1007) ? SIRD : ((paramInt1 == 1004 && paramInt2 == 1008) ? SIUP : ((paramInt1 == 1005 && paramInt2 == 1007) ? SSRD : ((paramInt1 == 1005 && paramInt2 == 1008) ? SSUP : ((paramInt1 < 1004) ? FWRD : SIRD))))));
  }
  
  public int getType() {
    return this.type;
  }
  
  public boolean isScrollable() {
    return (this.rank > 2);
  }
  
  public boolean isSensitive() {
    return (this.rank == 5 || this.rank == 6);
  }
  
  public boolean isUpdatable() {
    return (this.rank == 2 || this.rank == 4 || this.rank == 6);
  }
  
  public boolean useRowId() {
    return (this.rank != 0 && this.rank != 1 && this.rank != 3);
  }
  
  public int getHoldability() {
    return 1;
  }
  
  public String toString() {
    switch (this.rank) {
      case 1:
        return "RsetType@FWRD";
      case 2:
        return "RsetType@FWUP";
      case 3:
        return "RsetType@SIRD";
      case 4:
        return "RsetType@SIUP";
      case 5:
        return "RsetType@SSRD";
      case 6:
        return "RsetType@SSUP";
    } 
    return "RsetType@NULL";
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\RsetType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */