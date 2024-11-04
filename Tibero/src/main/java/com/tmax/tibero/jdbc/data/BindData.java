package com.tmax.tibero.jdbc.data;

import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;

public class BindData {
  protected BindItem[] bindItems = null;
  
  protected int deferredParamCnt = 0;
  
  public BindData() {}
  
  public BindData(int paramInt) throws SQLException {
    if (paramInt < 0)
      throw TbError.newSQLException(-590731); 
    if (this.bindItems == null)
      initialize(paramInt); 
  }
  
  private void checkParameterIndex(int paramInt) throws SQLException {
    if (this.bindItems == null || paramInt < 0 || paramInt >= this.bindItems.length)
      throw TbError.newSQLException(-90609); 
  }
  
  public void clearDFRParameter() throws SQLException {
    this.deferredParamCnt = 0;
  }
  
  public void clone(BindData paramBindData) throws SQLException {
    int i = this.bindItems.length;
    paramBindData.initialize(i);
    for (byte b = 0; b < i; b++)
      this.bindItems[b].clone(paramBindData.bindItems[b]); 
    paramBindData.deferredParamCnt = this.deferredParamCnt;
  }
  
  public BindItem[] getBindItems() {
    return this.bindItems;
  }
  
  public BindItem getBindItem(int paramInt) throws SQLException {
    checkParameterIndex(paramInt);
    return this.bindItems[paramInt];
  }
  
  public int getDFRParameterCnt() {
    return this.deferredParamCnt;
  }
  
  public int getInOutParameterCnt() {
    byte b1 = 0;
    for (byte b2 = 0; b2 < this.bindItems.length; b2++) {
      if (this.bindItems[b2].isOUTParameter() && this.bindItems[b2].isINParameter())
        b1++; 
    } 
    return b1;
  }
  
  public int getInParameterCnt() {
    byte b1 = 0;
    for (byte b2 = 0; b2 < this.bindItems.length; b2++) {
      if (this.bindItems[b2].isINParameter())
        b1++; 
    } 
    return b1;
  }
  
  public int getOutParameterCnt() {
    byte b1 = 0;
    for (byte b2 = 0; b2 < this.bindItems.length; b2++) {
      if (this.bindItems[b2].isOUTParameter())
        b1++; 
    } 
    return b1;
  }
  
  public int getParameterCnt() {
    return this.bindItems.length;
  }
  
  private void initialize(int paramInt) {
    this.bindItems = new BindItem[paramInt];
    for (byte b = 0; b < paramInt; b++)
      this.bindItems[b] = new BindItem(); 
  }
  
  public void insertDFRLiteral(int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    int i = this.bindItems.length;
    for (int j = i - 1; j > paramInt1; j--)
      this.bindItems[j] = this.bindItems[j - 1]; 
    this.bindItems[paramInt1] = new BindItem();
    this.bindItems[paramInt1].set(8, paramInt2, paramInt3);
    this.deferredParamCnt++;
  }
  
  public boolean isInParameterOn(int paramInt) throws SQLException {
    checkParameterIndex(paramInt);
    return this.bindItems[paramInt].isINParameter();
  }
  
  public boolean isOutParameterOn(int paramInt) throws SQLException {
    checkParameterIndex(paramInt);
    return this.bindItems[paramInt].isOUTParameter();
  }
  
  public String getOutParameterTypeName(int paramInt) throws SQLException {
    checkParameterIndex(paramInt);
    return this.bindItems[paramInt].getTypeName();
  }
  
  public void removeDFRLiteral(int[] paramArrayOfint) {
    byte b1 = 0;
    int i = this.bindItems.length - paramArrayOfint.length;
    BindItem[] arrayOfBindItem = this.bindItems;
    this.bindItems = new BindItem[i];
    for (byte b2 = 0; b2 < arrayOfBindItem.length; b2++) {
      byte b;
      for (b = 0; b < paramArrayOfint.length && b2 != paramArrayOfint[b]; b++);
      if (b == paramArrayOfint.length)
        this.bindItems[b1++] = arrayOfBindItem[b2]; 
    } 
  }
  
  public void reset() {
    if (this.bindItems == null)
      return; 
    for (byte b = 0; b < this.bindItems.length; b++) {
      this.bindItems[b].reset();
      this.bindItems[b] = null;
    } 
    this.bindItems = null;
  }
  
  public void resize(int paramInt) {
    if (this.bindItems == null) {
      initialize(paramInt);
    } else if (paramInt > this.bindItems.length) {
      BindItem[] arrayOfBindItem = this.bindItems;
      this.bindItems = new BindItem[paramInt];
      System.arraycopy(arrayOfBindItem, 0, this.bindItems, 0, arrayOfBindItem.length);
      for (int i = arrayOfBindItem.length; i < paramInt; i++)
        this.bindItems[i] = new BindItem(); 
    } else if (paramInt < this.bindItems.length) {
      BindItem[] arrayOfBindItem = this.bindItems;
      this.bindItems = new BindItem[paramInt];
      System.arraycopy(arrayOfBindItem, 0, this.bindItems, 0, paramInt);
    } 
  }
  
  public void reuse() {
    if (this.bindItems == null)
      return; 
    for (byte b = 0; b < this.bindItems.length; b++)
      this.bindItems[b].reuse(); 
    this.deferredParamCnt = 0;
  }
  
  public void set(BindData paramBindData) throws SQLException {
    int i = paramBindData.getParameterCnt();
    if (this.bindItems == null) {
      initialize(i);
    } else {
      reuse();
    } 
    System.arraycopy(paramBindData.bindItems, 0, this.bindItems, 0, i);
    this.deferredParamCnt = paramBindData.getDFRParameterCnt();
  }
  
  public void setDFRParam(int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    checkParameterIndex(paramInt1);
    this.bindItems[paramInt1].set(8, paramInt2, paramInt3);
    this.deferredParamCnt++;
  }
  
  public void setINParam(int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    checkParameterIndex(paramInt1);
    this.bindItems[paramInt1].set(1, paramInt2, paramInt3);
  }
  
  public void setOUTParam(int paramInt1, int paramInt2) throws SQLException {
    checkParameterIndex(paramInt1);
    this.bindItems[paramInt1].set(2, paramInt2);
  }
  
  public void setOUTParam(int paramInt1, int paramInt2, String paramString) throws SQLException {
    checkParameterIndex(paramInt1);
    this.bindItems[paramInt1].set(2, paramInt2);
    this.bindItems[paramInt1].setTypeName(paramString);
  }
  
  public void setOUTParam(int paramInt1, int paramInt2, String paramString, TbConnection paramTbConnection) throws SQLException {
    checkParameterIndex(paramInt1);
    this.bindItems[paramInt1].set(2, paramInt2);
    this.bindItems[paramInt1].setTypeName(paramString);
    this.bindItems[paramInt1].requestUdtMeta(paramInt2, paramTbConnection);
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\BindData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */