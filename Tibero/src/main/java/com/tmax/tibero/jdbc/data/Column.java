package com.tmax.tibero.jdbc.data;

import com.tmax.tibero.jdbc.TbTypeDescriptor;
import com.tmax.tibero.jdbc.msg.TbColumnDesc;
import com.tmax.tibero.jdbc.util.TbCommon;
import java.sql.SQLException;

public class Column {
  private int colIdx = 0;
  
  private int dataType = 0;
  
  private int maxSize = 0;
  
  private String name = null;
  
  private int precision = 0;
  
  private int scale = 0;
  
  private int sqlType = 0;
  
  private boolean isNullable = false;
  
  private boolean mapDateToTimestamp;
  
  private TbTypeDescriptor descriptor;
  
  public Column() {
    this.mapDateToTimestamp = true;
  }
  
  public Column(boolean paramBoolean) {
    this.mapDateToTimestamp = paramBoolean;
  }
  
  public Column(Column paramColumn) {
    this.colIdx = paramColumn.colIdx;
    this.dataType = paramColumn.dataType;
    this.maxSize = paramColumn.maxSize;
    this.name = paramColumn.name;
    this.precision = paramColumn.precision;
    this.scale = paramColumn.scale;
    this.sqlType = paramColumn.sqlType;
    this.isNullable = paramColumn.isNullable;
    this.mapDateToTimestamp = paramColumn.mapDateToTimestamp;
    this.descriptor = paramColumn.descriptor;
  }
  
  public final int getDataType() {
    return this.dataType;
  }
  
  public final TbTypeDescriptor getDescriptor() {
    return this.descriptor;
  }
  
  public final int getMaxLength() {
    return this.maxSize;
  }
  
  public final String getName() {
    return (this.name == null || this.name.length() == 0) ? ("column" + this.colIdx) : this.name;
  }
  
  public final int getPrecision() {
    return this.precision;
  }
  
  public final int getScale() {
    return this.scale;
  }
  
  public final int getSqlType() {
    return this.sqlType;
  }
  
  public final boolean isNullable() {
    return this.isNullable;
  }
  
  public void set(int paramInt, TbColumnDesc paramTbColumnDesc) throws SQLException {
    this.colIdx = paramInt;
    this.dataType = paramTbColumnDesc.dataType;
    this.maxSize = paramTbColumnDesc.maxSize;
    this.name = paramTbColumnDesc.name;
    this.precision = paramTbColumnDesc.precision;
    this.scale = paramTbColumnDesc.scale;
    if (paramTbColumnDesc.dataType == 16) {
      this.sqlType = -1;
    } else {
      this.sqlType = DataType.getSqlType(paramTbColumnDesc.dataType, paramTbColumnDesc.precision, this.mapDateToTimestamp);
    } 
    this.isNullable = TbCommon.getBitmapAt(0, paramTbColumnDesc.etcMeta);
  }
  
  public void set(int paramInt, TbColumnDesc paramTbColumnDesc, TbTypeDescriptor paramTbTypeDescriptor) throws SQLException {
    set(paramInt, paramTbColumnDesc);
    this.descriptor = paramTbTypeDescriptor;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\Column.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */