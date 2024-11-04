package com.tmax.tibero.jdbc.util;

import com.tmax.tibero.DriverConstants;

public class JDBCVersion {
  public static void main(String[] paramArrayOfString) {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("\n");
    stringBuffer.append(DriverConstants.BRAND_NAME);
    stringBuffer.append(" JDBC Driver ");
    stringBuffer.append(DriverConstants.JDBC_MAJOR);
    stringBuffer.append(".");
    stringBuffer.append(DriverConstants.JDBC_MINOR);
    stringBuffer.append(" ");
    stringBuffer.append(" ");
    stringBuffer.append(" ");
    if (!"UNKNOWN".equals(DriverConstants.BUILD_BRANCH)) {
      stringBuffer.append("(");
      stringBuffer.append(DriverConstants.BUILD_BRANCH);
      stringBuffer.append(") ");
    } 
    stringBuffer.append(" (Rev.");
    stringBuffer.append(DriverConstants.JDBC_REVISION);
    stringBuffer.append(")\n\n");
    if (DriverConstants.COPYRIGHT != null && DriverConstants.COPYRIGHT.length() != 0) {
      stringBuffer.append(DriverConstants.COPYRIGHT);
      stringBuffer.append("\n\n");
    } 
    if (!"UNKNOWN".equals(DriverConstants.BUILD_PATCHFILES)) {
      stringBuffer.append("Patch files (");
      stringBuffer.append(replaceNewLineToComma(DriverConstants.BUILD_PATCHFILES));
      stringBuffer.append(")\n");
    } 
    System.out.println(stringBuffer.toString());
  }
  
  private static String replaceNewLineToComma(String paramString) {
    paramString = paramString.replaceAll("\n", ", ");
    for (paramString = paramString.trim(); paramString.endsWith(","); paramString = paramString.trim())
      paramString = paramString.substring(0, paramString.length() - 1); 
    return paramString;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdb\\util\JDBCVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */