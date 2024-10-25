package com.tmax.tibero;

import com.tmax.tibero.jdbc.util.TbResourceBundle;

public class DriverConstants {
  private static TbResourceBundle _driverBundle = new TbResourceBundle("com.tmax.tibero.driver");
  
  public static final String JDBC_MAJOR = _driverBundle.getValue("major");
  
  public static final String JDBC_MINOR = _driverBundle.getValue("minor");
  
  public static final String JDBC_REVISION = _driverBundle.getValue("revision");
  
  public static final String JDBC_PACKAGE_NAME = _driverBundle.getValue("package_name");
  
  public static final String JDBC_PRODUCT_NAME = _driverBundle.getValue("product");
  
  public static final String BRAND_NAME = _driverBundle.getValue("brand");
  
  public static final String COPYRIGHT = _driverBundle.getValue("copyright");
  
  public static final String VENDOR_NAME = _driverBundle.getValue("vendor");
  
  public static final String JDBC_URL_BRAND_NAME = _driverBundle.getValue("url_brand");
  
  public static final String BUILD_BRANCH = _driverBundle.getValue("build_branch");
  
  public static final String BUILD_PATCHFILES = _driverBundle.getValue("build_patchfiles");
  
  public static final int KILO = (int)Math.pow(2.0D, 10.0D);
  
  public static final int MEGA = (int)Math.pow(2.0D, 20.0D);
  
  public static final int GIGA = (int)Math.pow(2.0D, 30.0D);
  
  public static final String DEFAULT_SERVER = "localhost";
  
  public static final int DEFAULT_PORT = 8629;
  
  public static final String DEFAULT_PROTOCOL = "tcp";
  
  public static final String DEFAULT_DRIVER = "thin";
  
  public static final String DEFAULT_CONNECTION_CONFIG_FILENAME = "TbJDBCConnection.properties";
  
  public static final int NODE_DEFAULT = 0;
  
  public static final int NODE_PRIMARY = 1;
  
  public static final int NODE_BACKUP = 2;
  
  public static final int PRE_FETCH_SIZE = 64000;
  
  public static final int FETCH_SIZE = 50;
  
  public static final int DEFAULT_ROW_CHUNK_SIZE = 4096;
  
  public static final int ROW_CHUNK_HDR_SIZE = 4;
  
  public static final int ROW_PIECE_HDR_SIZE = 3;
  
  public static final int TDU_SIZE = 4096;
  
  public static final int BATCH_SEND_SIZE = 32 * KILO;
  
  public static final int MAX_FIELD_SIZE = 65535;
  
  public static final int MIN_DEFERRED_BYTE_SIZE = 65532;
  
  public static final int MIN_DEFERRED_CHAR_CNT = KILO * 16;
  
  public static final int MAX_VARCHAR_COLUMN_BYTE_SIZE_V5 = 4000;
  
  public static final int MAX_NVARCHAR_COLUMN_BYTE_SIZE_V5 = 4000;
  
  public static final int SERVER_LITTLE_ENDIAN = 0;
  
  public static final int SERVER_BIG_ENDIAN = 1;
  
  public static final int FO_FLG_SES = 1;
  
  public static final int FO_FLG_CUR = 2;
  
  public static final int FO_FLG_TX = 4;
  
  public static final int FO_MODE_NONE = 0;
  
  public static final int FO_MODE_SESSION = 1;
  
  public static final int FO_MODE_CURSOR = 3;
  
  public static final String FO_STR_NONE = "NONE";
  
  public static final String FO_STR_SESSION = "SESSION";
  
  public static final String FO_STR_CURSOR = "CURSOR";
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\DriverConstants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */