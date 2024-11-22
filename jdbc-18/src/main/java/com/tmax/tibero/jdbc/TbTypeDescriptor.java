package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.data.UdtAttrMeta;
import com.tmax.tibero.jdbc.data.UdtHierarchyInfo;
import com.tmax.tibero.jdbc.data.UdtInfo;
import com.tmax.tibero.jdbc.data.UdtMeta;
import com.tmax.tibero.jdbc.driver.TbConnection;

import java.sql.SQLException;
import java.util.*;

public abstract class TbTypeDescriptor {
    public static final String UDT_INFO_SQLTYPENAME = "SYS.UDT_INFO_T";

    public static final String ATTR_META_SQLTYPENAME = "SYS.ATTR_META_T";

    public static final String ATTR_IDX_ARRAY_SQLTYPENAME = "SYS.ATTR_IDX_ARR";

    public static final String HIERARCHY_INFO_SQLTYPENAME = "SYS.HIERARCHY_INFO_T";

    public static final String ATTR_META_ARRAY_SQLTYPENAME = "SYS.ATTR_META_ARR";

    public static final String HIERARCHY_INFO_ARRAY_SQLTYPENAME = "SYS.HIERARCHY_INFO_ARR";

    public static final String UDT_META_SQLTYPENAME = "SYS.UDT_META_T";

    public static final String SQLTYPENAME_XMLTYPE = "SYS.XMLTYPE";

    public static final String SQLTYPENAME_ANYDATA = "SYS.ANYDATA";

    public static final String UDT_INFO_OID = "00000000000000000000000000000003";

    public static final String ATTR_META_OID = "00000000000000000000000000000004";

    public static final String ATTR_IDX_ARRAY_OID = "00000000000000000000000000000005";

    public static final String HIERARCHY_INFO_OID = "00000000000000000000000000000006";

    public static final String ATTR_META_ARRAY_OID = "00000000000000000000000000000007";

    public static final String HIERARCHY_INFO_ARRAY_OID = "00000000000000000000000000000008";

    public static final String UDT_META_OID = "00000000000000000000000000000009";

    public static final String OID_XMLTYPE = "00000000000000000000000000000001";

    public static final String OID_ANYDATA = "0000000000000000000000000000000A";

    public static final String _DESC_OID_PREFIX = "/O";

    public static List<String> preDefinedOIDList = new ArrayList<>();

    public static List<String> preDefinedTypeNameList = new ArrayList<>();

    private static Map<String, Class<?>> udtMeta2ClsMap = new HashMap<>();

    private String sqlTypeName;

    private String schemaName;

    private String packageName;

    private String typeName;

    private int dataType;

    private String hexEncodedOID;

    private int tobjID;

    private int versionNo;

    void init(int paramInt, String paramString1, String paramString2) throws SQLException {
        this.dataType = paramInt;
        this.hexEncodedOID = paramString1;
        this.sqlTypeName = paramString2;
        parseSQLTypeName(paramString2);
    }

    public static List<TbTypeDescriptor> genPredefUDTDescs(TbConnection paramTbConnection) {
        List<TbTypeDescriptor> arrayList = new ArrayList<>(7);
        try {
            arrayList.add(new TbStructDescriptor(32, "00000000000000000000000000000003", "SYS.UDT_INFO_T", new int[]{3, 3, 3, 3, 1, 1, 1, 4}, new String[8], new String[8], paramTbConnection));
            arrayList.add(new TbStructDescriptor(32, "00000000000000000000000000000004", "SYS.ATTR_META_T", new int[]{3, 1, 1, 1, 1, 3}, new String[6], new String[6], paramTbConnection));
            arrayList.add(new TbArrayDescriptor(29, "00000000000000000000000000000005", "SYS.ATTR_IDX_ARR", 1, null, 1500, paramTbConnection));
            arrayList.add(new TbStructDescriptor(32, "00000000000000000000000000000006", "SYS.HIERARCHY_INFO_T", new int[]{32, 29, 4}, new String[]{"SYS.UDT_INFO_T", "SYS.ATTR_IDX_ARR", null}, new String[]{"00000000000000000000000000000003", "00000000000000000000000000000005", null}, paramTbConnection));
            arrayList.add(new TbArrayDescriptor(29, "00000000000000000000000000000007", "SYS.ATTR_META_ARR", 32, "SYS.ATTR_META_T", 1500, paramTbConnection));
            arrayList.add(new TbArrayDescriptor(29, "00000000000000000000000000000008", "SYS.HIERARCHY_INFO_ARR", 32, "SYS.HIERARCHY_INFO_T", 1500, paramTbConnection));
            arrayList.add(new TbStructDescriptor(32, "00000000000000000000000000000009", "SYS.UDT_META_T", new int[]{1, 32, 29, 29, 1, 1}, new String[]{null, "SYS.UDT_INFO_T", "SYS.ATTR_META_ARR", "SYS.HIERARCHY_INFO_ARR", null, null}, new String[]{null, "00000000000000000000000000000003", "00000000000000000000000000000007", "00000000000000000000000000000008", null, null}, paramTbConnection));
            if (paramTbConnection != null)
                for (byte b = 0; b < arrayList.size(); b++) {
                    TbTypeDescriptor tbTypeDescriptor = arrayList.get(b);
                    paramTbConnection.putDescriptor("/O" + tbTypeDescriptor.getOID(), tbTypeDescriptor);
                    paramTbConnection.putDescriptor(tbTypeDescriptor.getSQLTypeName(), tbTypeDescriptor);
                }
        } catch (Exception exception) {
            arrayList.clear();
        }
        return arrayList;
    }

    public static Map<String, Class<?>> getUdtMeta2ClsMap() {
        if (udtMeta2ClsMap.size() == 0) {
            udtMeta2ClsMap.put("SYS.UDT_INFO_T", UdtInfo.class);
            udtMeta2ClsMap.put("SYS.ATTR_META_T", UdtAttrMeta.class);
            udtMeta2ClsMap.put("SYS.HIERARCHY_INFO_T", UdtHierarchyInfo.class);
            udtMeta2ClsMap.put("SYS.UDT_META_T", UdtMeta.class);
        }
        return udtMeta2ClsMap;
    }

    public String getSQLTypeName() {
        return this.sqlTypeName;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public int getDataType() {
        return this.dataType;
    }

    public String getOID() {
        return this.hexEncodedOID;
    }

    public int getVersionNo() {
        return this.versionNo;
    }

    public int getTobjID() {
        return this.tobjID;
    }

    public void setVersionNo(int paramInt) {
        this.versionNo = paramInt;
    }

    public void setTobjID(int paramInt) {
        this.tobjID = paramInt;
    }

    private int parseSQLTypeName(String paramString) throws SQLException {
        String str = ".";
        StringTokenizer stringTokenizer = new StringTokenizer(paramString, str);
        int i = stringTokenizer.countTokens();
        if (i == 2) {
            this.schemaName = stringTokenizer.nextToken().toUpperCase();
            this.typeName = stringTokenizer.nextToken().toUpperCase();
            this.packageName = null;
        } else if (i == 3) {
            this.schemaName = stringTokenizer.nextToken().toUpperCase();
            this.packageName = stringTokenizer.nextToken().toUpperCase();
            this.typeName = stringTokenizer.nextToken().toUpperCase();
        } else {
            throw new SQLException("Invalid type name : " + paramString);
        }
        return i;
    }

    public static String[] splitSQLTypeName(String paramString) {
        String str = "[.]{1}";
        return paramString.split(str);
    }

    protected void setOID(String paramString) {
        this.hexEncodedOID = paramString;
    }

    static {
        preDefinedOIDList.add("/O00000000000000000000000000000003");
        preDefinedOIDList.add("/O00000000000000000000000000000004");
        preDefinedOIDList.add("/O00000000000000000000000000000005");
        preDefinedOIDList.add("/O00000000000000000000000000000006");
        preDefinedOIDList.add("/O00000000000000000000000000000007");
        preDefinedOIDList.add("/O00000000000000000000000000000008");
        preDefinedOIDList.add("/O00000000000000000000000000000009");
        preDefinedOIDList.add("/O00000000000000000000000000000001");
        preDefinedTypeNameList.add("SYS.UDT_INFO_T");
        preDefinedTypeNameList.add("SYS.ATTR_META_T");
        preDefinedTypeNameList.add("SYS.ATTR_IDX_ARR");
        preDefinedTypeNameList.add("SYS.HIERARCHY_INFO_T");
        preDefinedTypeNameList.add("SYS.ATTR_META_ARR");
        preDefinedTypeNameList.add("SYS.HIERARCHY_INFO_ARR");
        preDefinedTypeNameList.add("SYS.UDT_META_T");
        preDefinedTypeNameList.add("SYS.XMLTYPE");
    }
}