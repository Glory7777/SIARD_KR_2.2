package com.tmax.tibero.jdbc.ext;

import javax.transaction.xa.XAException;

public class TbXAException extends XAException {
  private static final long serialVersionUID = 3487039970165361276L;
  
  public TbXAException() {}
  
  public TbXAException(int paramInt, String paramString) {
    super(paramString);
    this.errorCode = paramInt;
  }
  
  public TbXAException(String paramString) {
    super(paramString);
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\ext\TbXAException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */