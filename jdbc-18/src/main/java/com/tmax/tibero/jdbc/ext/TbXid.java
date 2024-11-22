package com.tmax.tibero.jdbc.ext;

import java.io.Serializable;
import javax.transaction.xa.Xid;

public class TbXid implements Xid, Serializable {
  private static final long serialVersionUID = 8653403137105513841L;
  
  public static int DATA_SIZE = 128;
  
  public static int SERIALIZED_SIZE = 24 + DATA_SIZE;
  
  private int formatId;
  
  private byte[] gtrid;
  
  private byte[] bqual;
  
  private volatile String xidAsString;
  
  public TbXid(int paramInt, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws TbXAException {
    if (paramArrayOfbyte1 != null && paramArrayOfbyte1.length > 64)
      throw new TbXAException(-4, "GTRID is null or greater than 64"); 
    if (paramArrayOfbyte2 != null && paramArrayOfbyte2.length > 64)
      throw new TbXAException(-4, "BQUAL is null or greater than 64"); 
    this.formatId = paramInt;
    this.gtrid = paramArrayOfbyte1;
    this.bqual = paramArrayOfbyte2;
  }
  
  public int getFormatId() {
    return this.formatId;
  }
  
  public byte[] getGlobalTransactionId() {
    return this.gtrid;
  }
  
  public byte[] getBranchQualifier() {
    return this.bqual;
  }
  
  public String toString() {
    String str = this.xidAsString;
    if (str == null) {
      StringBuilder stringBuilder = new StringBuilder(300);
      stringBuilder.append("TbXid@").append(hashCode()).append("[");
      if (this.gtrid != null)
        for (byte b = 0; b < this.gtrid.length; b++) {
          String str1 = Integer.toHexString(this.gtrid[b] & 0xFF);
          if (str1.length() == 1)
            stringBuilder.append('0'); 
          stringBuilder.append(str1);
        }  
      stringBuilder.append('.');
      if (this.bqual != null)
        for (byte b = 0; b < this.bqual.length; b++) {
          String str1 = Integer.toHexString(this.bqual[b] & 0xFF);
          if (str1.length() == 1)
            stringBuilder.append('0'); 
          stringBuilder.append(str1);
        }  
      stringBuilder.append('.');
      stringBuilder.append("0x").append(Integer.toHexString(this.formatId));
      stringBuilder.append("]");
      str = stringBuilder.toString();
      this.xidAsString = str;
    } 
    return str;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\ext\TbXid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */