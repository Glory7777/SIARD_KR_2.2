package com.tmax.tibero.jdbc.data;

import com.tmax.tibero.jdbc.TbArrayDescriptor;
import com.tmax.tibero.jdbc.TbStructDescriptor;
import com.tmax.tibero.jdbc.TbTypeDescriptor;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.msg.TbColumnDesc;
import java.sql.SQLException;

public class BindItem {
  private int paramMode = 0;
  
  private int sqlType = 0;
  
  private int length;
  
  private TbColumnDesc[] colMeta;
  
  private TbTypeDescriptor typeDesc;
  
  private String typeName;
  
  public void clone(BindItem paramBindItem) {
    paramBindItem.paramMode = this.paramMode;
    paramBindItem.sqlType = this.sqlType;
    paramBindItem.length = this.length;
    paramBindItem.typeName = this.typeName;
    paramBindItem.typeDesc = this.typeDesc;
  }
  
  public TbColumnDesc[] getColMeta() {
    return this.colMeta;
  }
  
  public int getSQLType() {
    return this.sqlType;
  }
  
  public int getLength() {
    return this.length;
  }
  
  public int getParamMode() {
    return this.paramMode;
  }
  
  public TbTypeDescriptor getTypeDescriptor() {
    return this.typeDesc;
  }
  
  public boolean isDFRParameter() {
    return (this.paramMode == 8);
  }
  
  public boolean isINOUTParameter() {
    return (this.paramMode == 4);
  }
  
  public boolean isINParameter() {
    return (this.paramMode == 1 || this.paramMode == 4);
  }
  
  public boolean isOUTParameter() {
    return (this.paramMode == 2 || this.paramMode == 4);
  }
  
  public String getTypeName() {
    return this.typeName;
  }
  
  public void reset() {
    if (this.colMeta != null) {
      for (byte b = 0; b < this.colMeta.length; b++)
        this.colMeta[b] = null; 
      this.colMeta = null;
    } 
    if (this.typeDesc != null)
      this.typeDesc = null; 
  }
  
  public void reuse() {
    this.paramMode = 0;
    this.sqlType = 0;
    this.typeName = null;
    if (this.colMeta != null) {
      for (byte b = 0; b < this.colMeta.length; b++)
        this.colMeta[b] = null; 
      this.colMeta = null;
    } 
    if (this.typeDesc != null)
      this.typeDesc = null; 
  }
  
  public void set(int paramInt1, int paramInt2) {
    setParamMode(paramInt1);
    this.sqlType = paramInt2;
  }
  
  public void set(int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    setParamMode(paramInt1);
    this.sqlType = paramInt2;
    this.length = paramInt3;
  }
  
  public void set(int paramInt1, int paramInt2, TbColumnDesc[] paramArrayOfTbColumnDesc, TbTypeDescriptor paramTbTypeDescriptor) throws SQLException {
    this.paramMode = 2;
    this.sqlType = paramInt1;
    this.length = paramInt2;
    this.colMeta = paramArrayOfTbColumnDesc;
    this.typeDesc = paramTbTypeDescriptor;
  }
  
  public void set(int paramInt1, int paramInt2, TbColumnDesc[] paramArrayOfTbColumnDesc, TbTypeDescriptor paramTbTypeDescriptor, String paramString) throws SQLException {
    this.paramMode = 2;
    this.sqlType = paramInt1;
    this.length = paramInt2;
    this.colMeta = paramArrayOfTbColumnDesc;
    this.typeDesc = paramTbTypeDescriptor;
    this.typeName = paramString;
  }
  
  private void setParamMode(int paramInt) {
    if ((this.paramMode == 1 && paramInt == 2) || (this.paramMode == 2 && paramInt == 1)) {
      this.paramMode = 4;
    } else {
      this.paramMode = paramInt;
    } 
  }
  
  public void setTypeName(String paramString) {
    this.typeName = paramString;
  }
  
  public void requestUdtMeta(int paramInt, TbConnection paramTbConnection) throws SQLException {
    String str = paramTbConnection.info.getUser();
    String[] arrayOfString = this.typeName.split("[.]{1}");
    if (arrayOfString.length != 1)
      if (arrayOfString.length == 2) {
        str = arrayOfString[0];
        this.typeName = arrayOfString[1];
      } else {
        throw TbError.newSQLException(-90649);
      }  
    switch (paramInt) {
      case 2002:
        this.typeDesc = (TbTypeDescriptor)TbStructDescriptor.lookupUdtMeta(str, this.typeName, paramTbConnection);
        break;
      case -2003:
      case 2003:
        this.typeDesc = (TbTypeDescriptor)TbArrayDescriptor.lookupUdtMeta(str, this.typeName, paramTbConnection);
        break;
    } 
  }
  
  public void setTypeDescriptor(TbTypeDescriptor paramTbTypeDescriptor) {
    this.typeDesc = paramTbTypeDescriptor;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(96);
    stringBuilder.append(super.toString()).append("[mode=").append(this.paramMode).append("/sqlType=").append(this.sqlType).append("/length=").append(this.length).append("/colMeta=").append(this.colMeta).append("/typeDescriptor=").append(this.typeDesc).append("/typeName=").append(this.typeName).append(']');
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\BindItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */