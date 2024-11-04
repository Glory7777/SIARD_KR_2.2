package com.tmax.tibero.jdbc.data;

import com.tmax.tibero.jdbc.driver.TbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExtFeatureInfo {
  public static final short UNKNOWN = 0;
  
  public static final short SUPPORT = 1;
  
  public static final short UNSUPPORT = 2;
  
  public static final int ID_64K_VARCHAR = 0;
  
  public static final int ID_STATIC_VIEW_FOR_UDT = 1;
  
  public static final int ID_UDT_OWNER_OF_COLUMN = 2;
  
  public static final int ID_MAX = 3;
  
  private static final String DB_T;
  
  private static final String DB_I;
  
  private static final String DB_U;
  
  private static final String UDT_OWNER_OF_COL_VIEWNAME = new String(new char[] { 
        'A', 'L', 'L', '_', 'T', 'B', 'L', '_', 'C', 'O', 
        'L', 'U', 'M', 'N', 'S' });
  
  private static final String UDT_OWNER_OF_COL_COLNAME = "DATA_TYPE_OWNER";
  
  private TbConnection conn;
  
  private short[] info = new short[3];
  
  public ExtFeatureInfo(TbConnection paramTbConnection) {
    this.conn = paramTbConnection;
  }
  
  public boolean supports(int paramInt) {
    return supports(paramInt, true);
  }
  
  public boolean supports(int paramInt, boolean paramBoolean) {
    if (paramInt < 0 && paramInt >= 3)
      return false; 
    if (paramBoolean && this.info[paramInt] == 0) {
      String str;
      int i;
      boolean bool;
      boolean bool1;
      boolean bool2;
      PreparedStatement preparedStatement = null;
      switch (paramInt) {
        case 0:
          str = this.conn.serverInfo.getTbProductName();
          i = this.conn.serverInfo.getTbMajor();
          bool = ((i >= 6 && DB_T.equalsIgnoreCase(str)) || (i >= 11 && DB_I.equalsIgnoreCase(str)) || (i >= 6 && DB_U.equalsIgnoreCase(str))) ? true : false;
          this.info[0] = (short) (bool ? 1 : 2);
          return bool;
        case 1:
          bool1 = false;
          try {
            preparedStatement = this.conn.prepareStatement(" SELECT OBJECT_NAME FROM ALL_OBJECTS  WHERE OWNER = 'SYSCAT' AND OBJECT_TYPE IN ('TABLE', 'VIEW') AND OBJECT_NAME = ?");
            preparedStatement.setString(1, "ALL_TYPES");
            ResultSet resultSet = preparedStatement.executeQuery();
            bool1 = resultSet.next();
            this.info[1] = (short) (bool1 ? 1 : 2);
            return bool1;
          } catch (Exception exception) {
            break;
          } finally {
            try {
              if (preparedStatement != null)
                preparedStatement.close(); 
            } catch (Exception exception) {}
          } 
        case 2:
          bool2 = false;
          try {
            preparedStatement = this.conn.prepareStatement(" SELECT COLUMN_NAME FROM ALL_TAB_COLUMNS  WHERE OWNER = 'SYSCAT' AND TABLE_NAME = ? AND COLUMN_NAME = ?");
            preparedStatement.setString(1, UDT_OWNER_OF_COL_VIEWNAME);
            preparedStatement.setString(2, "DATA_TYPE_OWNER");
            ResultSet resultSet = preparedStatement.executeQuery();
            bool2 = resultSet.next();
            this.info[2] = (short) (bool2 ? 1 : 2);
            return bool2;
          } catch (Exception exception) {
            break;
          } finally {
            try {
              if (preparedStatement != null)
                preparedStatement.close(); 
            } catch (Exception exception) {}
          } 
      } 
    } 
    return (this.info[paramInt] == 1);
  }
  
  static {
    char[][] arrayOfChar = { { 'T', 'i', 'b', 'e', 'r', 'o' }, { 
          'I', 'n', 's', 'p', 'u', 'r', ' ', 'K', '-', 'D', 
          'B' }, { 'U', 'p', 'R', 'i', 'g', 'h', 't' } };
    DB_T = new String(arrayOfChar[0]);
    DB_I = new String(arrayOfChar[1]);
    DB_U = new String(arrayOfChar[2]);
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\ExtFeatureInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */