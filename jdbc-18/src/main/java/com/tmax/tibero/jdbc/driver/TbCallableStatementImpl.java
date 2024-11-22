package com.tmax.tibero.jdbc.driver;

import com.tmax.tibero.jdbc.TbResultSet;
import com.tmax.tibero.jdbc.*;
import com.tmax.tibero.jdbc.data.*;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.msg.TbPivotInfo;
import com.tmax.tibero.jdbc.util.TbCommon;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.*;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.*;

public class TbCallableStatementImpl extends TbPreparedStatementImpl implements CallableStatement {
    private static final int DFLT_NAMED_PARAM_CNT = 10;

    private static final int OID_BYTE_LENGTH = 32;

    private static final int TOBJID_BYTE_LENGTH = 4;

    private static final int VERSION_NO_BYTE_LENGTH = 4;

    private String[] namedParamArr = new String[10];

    private int namedParamCnt;

    private boolean haveNamedParameter;

    private boolean haveOrdinalParameter;

    private boolean lastColumnWasNull;

    private byte[][] outParamBytes = new byte[this.bindParamCnt][];

    private byte[] outParamTypes = new byte[this.bindParamCnt];

    private TbResultSet[] outPrefetchedRs = new TbResultSet[this.bindParamCnt];

    private BindItem[] outItems = new BindItem[this.bindParamCnt];

    protected TbPivotInfo[][] pivotInfoArr;

    protected ArrayList<Vector<byte[]>> pivotDataArr;

    public TbCallableStatementImpl(TbConnection paramTbConnection, String paramString) throws SQLException {
        this(paramTbConnection, paramString, 1003, 1007, 64000);
    }

    public TbCallableStatementImpl(TbConnection paramTbConnection, String paramString, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
        super(paramTbConnection, paramString, paramInt1, paramInt2, paramInt3, false);
        for (byte b = 0; b < this.bindParamCnt; b++)
            this.outItems[b] = new BindItem();
    }

    private int addNamedParam(String paramString) {
        for (byte b = 0; b < this.namedParamCnt; b++) {
            if (this.namedParamArr[b].equals(paramString))
                return b + 1;
        }
        if (this.namedParamCnt >= this.namedParamArr.length) {
            String[] arrayOfString = new String[this.namedParamArr.length * 2];
            System.arraycopy(this.namedParamArr, 0, arrayOfString, 0, this.namedParamArr.length);
            this.namedParamArr = arrayOfString;
        }
        this.namedParamArr[this.namedParamCnt++] = paramString;
        this.haveNamedParameter = true;
        return this.namedParamCnt;
    }

    public void addPivotData(int paramInt, byte[] paramArrayOfbyte) {
        if (this.pivotDataArr == null) {
            this.pivotDataArr = new ArrayList<>(this.bindParamCnt);
            for (byte b = 0; b < this.bindParamCnt; b++)
                this.pivotDataArr.add(null);
        }
        Vector<byte[]> vector = this.pivotDataArr.get(paramInt);
        if (vector == null) {
            vector = new Vector();
            this.pivotDataArr.set(paramInt, vector);
        }
        vector.add(paramArrayOfbyte);
    }

    private void checkOutParamExists(int paramInt) throws SQLException {
        if (this.outParamBytes[paramInt] == null)
            throw TbError.newSQLException(-90609);
    }

    public synchronized boolean execute() throws SQLException {
        if (this.haveNamedParameter && this.haveOrdinalParameter)
            throw TbError.newSQLException(-90655);
        if (this.namedParamCnt > 0)
            rewriteQuestionToNamedParam();
        return super.execute();
    }

    public synchronized int executeUpdate() throws SQLException {
        if (this.haveNamedParameter && this.haveOrdinalParameter)
            throw TbError.newSQLException(-90655);
        if (this.namedParamCnt > 0)
            rewriteQuestionToNamedParam();
        return super.executeUpdate();
    }

    public Array getArray(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getArrayInternal(paramInt);
    }

    public Array getArray(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getArrayInternal(i);
    }

    private Array getArrayInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        byte[] arrayOfByte = this.outParamBytes[paramInt - 1];
        if (setLastColumnWasNull(bindItem))
            return null;
        String str = this.typeConverter.toString(arrayOfByte, 0, 32, 3, false);
        int i = TbCommon.bytes2Int(arrayOfByte, 32, 4);
        int j = TbCommon.bytes2Int(arrayOfByte, 36, 4);
        TbArrayDescriptor tbArrayDescriptor = TbArrayDescriptor.createDescriptor(29, str, i, j, (Connection) this.conn);
        bindItem.setTypeDescriptor((TbTypeDescriptor) tbArrayDescriptor);
        byte b = 40;
        return this.typeConverter.toArray(arrayOfByte, b, bindItem.getLength() - b, DataType.getDataType(bindItem.getSQLType()), !getRealRsetType().isScrollable(), tbArrayDescriptor, this.conn.getTypeMap());
    }

    public Struct getStruct(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getStructInternal(paramInt);
    }

    private Struct getStructInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        return setLastColumnWasNull(bindItem) ? null : (Struct) this.typeConverter.toStruct(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), bindItem.getSQLType(), !getRealRsetType().isScrollable(), null, null, this.conn.getTypeMap());
    }

    public BigDecimal getBigDecimal(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getBigDecimalInternal(paramInt);
    }

    @Deprecated
    public BigDecimal getBigDecimal(int paramInt1, int paramInt2) throws SQLException {
        return getBigDecimal(paramInt1);
    }

    public BigDecimal getBigDecimal(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getBigDecimalInternal(i);
    }

    private BigDecimal getBigDecimalInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        return setLastColumnWasNull(bindItem) ? null : this.typeConverter.toBigDecimal(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), this.outParamTypes[paramInt - 1]);
    }

    public Blob getBlob(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getBlobInternal(paramInt);
    }

    public Blob getBlob(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getBlobInternal(i);
    }

    private Blob getBlobInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        return setLastColumnWasNull(bindItem) ? null : this.typeConverter.toBlob(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), this.outParamTypes[paramInt - 1], false);
    }

    public boolean getBoolean(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getBooleanInternal(paramInt);
    }

    public boolean getBoolean(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getBooleanInternal(i);
    }

    private boolean getBooleanInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        return setLastColumnWasNull(bindItem) ? false : this.typeConverter.toBoolean(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), this.outParamTypes[paramInt - 1]);
    }

    public byte getByte(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getByteInternal(paramInt);
    }

    public byte getByte(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getByteInternal(i);
    }

    private byte getByteInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        return setLastColumnWasNull(bindItem) ? 0 : this.typeConverter.toByte(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), this.outParamTypes[paramInt - 1]);
    }

    public byte[] getBytes(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getBytesInternal(paramInt);
    }

    public byte[] getBytes(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getBytesInternal(i);
    }

    public TbRAW getRAW(int paramInt) throws SQLException {
        return new TbRAW(getBytes(paramInt));
    }

    public TbRAW getRAW(String paramString) throws SQLException {
        return new TbRAW(getBytes(paramString));
    }

    private byte[] getBytesInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        return setLastColumnWasNull(bindItem) ? null : this.typeConverter.toBytes(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), this.outParamTypes[paramInt - 1], false);
    }

    public Reader getCharacterStream(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getCharacterStreamInternal(paramInt);
    }

    public Reader getCharacterStream(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getCharacterStreamInternal(i);
    }

    private Reader getCharacterStreamInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        return setLastColumnWasNull(bindItem) ? null : this.typeConverter.toCharacterStream(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), this.outParamTypes[paramInt - 1]);
    }

    public Clob getClob(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getClobInternal(paramInt);
    }

    public Clob getClob(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getClobInternal(i);
    }

    private Clob getClobInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        return setLastColumnWasNull(bindItem) ? null : this.typeConverter.toClob(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), this.outParamTypes[paramInt - 1], false);
    }

    public Date getDate(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getDateInternal(paramInt);
    }

    public Date getDate(int paramInt, Calendar paramCalendar) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        Date date = getDateInternal(paramInt);
        if (paramCalendar != null) {
            paramCalendar.setTime(date);
            date = (Date) paramCalendar.getTime();
        }
        return date;
    }

    public Date getDate(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getDateInternal(i);
    }

    public Date getDate(String paramString, Calendar paramCalendar) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getDate(i, paramCalendar);
    }

    private Date getDateInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        return setLastColumnWasNull(bindItem) ? null : this.typeConverter.toDate(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), this.outParamTypes[paramInt - 1]);
    }

    public TbDate getTbDate(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getTbDateInternal(paramInt);
    }

    public TbDate getTbDate(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getTbDateInternal(i);
    }

    private TbDate getTbDateInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        return setLastColumnWasNull(bindItem) ? null : this.typeConverter.toTbDate(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), this.outParamTypes[paramInt - 1]);
    }

    public double getDouble(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getDoubleInternal(paramInt);
    }

    public double getDouble(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getDoubleInternal(i);
    }

    private double getDoubleInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        return setLastColumnWasNull(bindItem) ? 0.0D : this.typeConverter.toDouble(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), this.outParamTypes[paramInt - 1]);
    }

    public float getFloat(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getFloatInternal(paramInt);
    }

    public float getFloat(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getFloatInternal(i);
    }

    private float getFloatInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        return setLastColumnWasNull(bindItem) ? 0.0F : this.typeConverter.toFloat(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), this.outParamTypes[paramInt - 1]);
    }

    public int getInt(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getIntInternal(paramInt);
    }

    public int getInt(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getIntInternal(i);
    }

    private int getIntInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        return setLastColumnWasNull(bindItem) ? 0 : this.typeConverter.toInt(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), this.outParamTypes[paramInt - 1]);
    }

    public long getLong(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getLongInternal(paramInt);
    }

    public long getLong(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getLongInternal(i);
    }

    private long getLongInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        return setLastColumnWasNull(bindItem) ? 0L : this.typeConverter.toLong(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), this.outParamTypes[paramInt - 1]);
    }

    public Reader getNCharacterStream(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getCharacterStreamInternal(paramInt);
    }

    public Reader getNCharacterStream(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getCharacterStreamInternal(i);
    }

    public NClob getNClob(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getNClobInternal(paramInt);
    }

    public NClob getNClob(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getNClobInternal(i);
    }

    private NClob getNClobInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        return setLastColumnWasNull(bindItem) ? null : this.typeConverter.toNClob(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), this.outParamTypes[paramInt - 1]);
    }

    public String getNString(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getStringInternal(paramInt);
    }

    public String getNString(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getStringInternal(i);
    }

    public Object getObject(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getObjectInternal(paramInt);
    }

    public Object getObject(int paramInt, Map paramMap) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        TbStruct tbStruct = (TbStruct) getObjectInternal(paramInt);
        if (paramMap == null)
            return tbStruct;
        String str = tbStruct.getSQLTypeName();
        Class clazz = (Class) paramMap.get(str);
        return tbStruct.toClass(clazz, paramMap);
    }

    public Object getObject(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getObjectInternal(i);
    }

    public Object getObject(String paramString, Map paramMap) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getObject(i, paramMap);
    }

    private Object getObjectInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        byte[] arrayOfByte = this.outParamBytes[paramInt - 1];
        if (bindItem.getParamMode() == 1)
            throw TbError.newSQLException(-590772);
        if (this.outParamTypes[paramInt - 1] == 16)
            return (this.outPrefetchedRs[paramInt - 1] == null) ? this.typeConverter.toResultSet(this, this.outParamBytes[paramInt - 1], bindItem) : this.outPrefetchedRs[paramInt - 1];
        if (this.outParamTypes[paramInt - 1] == 32 || this.outParamTypes[paramInt - 1] == 28) {
            if (setLastColumnWasNull(bindItem))
                return null;
            String str = this.typeConverter.toString(arrayOfByte, 0, 32, 3, false);
            int i = TbCommon.bytes2Int(arrayOfByte, 32, 4);
            int j = TbCommon.bytes2Int(arrayOfByte, 36, 4);
            TbStructDescriptor tbStructDescriptor = TbStructDescriptor.createDescriptor(32, str, i, j, (Connection) this.conn);
            bindItem.setTypeDescriptor((TbTypeDescriptor) tbStructDescriptor);
            return this.typeConverter.toStruct(arrayOfByte, 40, bindItem.getLength(), DataType.getDataType(bindItem.getSQLType()), !getRealRsetType().isScrollable(), tbStructDescriptor, null, null);
        }
        return (this.outParamTypes[paramInt - 1] == 29 || this.outParamTypes[paramInt - 1] == 30) ? getArrayInternal(paramInt) : (setLastColumnWasNull(bindItem) ? null : this.typeConverter.toObject(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), this.outParamTypes[paramInt - 1], bindItem.getSQLType(), true));
    }

    public BindItem getOutItems(int paramInt) {
        return this.outItems[paramInt];
    }

    public Vector<byte[]> getPivotData(int paramInt) throws SQLException {
        if (this.outParamTypes[paramInt - 1] == 16) {
            if (this.pivotDataArr == null || this.pivotDataArr.get(paramInt - 1) == null) {
                int i = TbCommon.bytes2Int(this.outParamBytes[paramInt - 1], 0, this.outItems[paramInt - 1].getLength());
                this.conn.getTbComm().executePivot(this, paramInt - 1, i);
            }
            if (this.pivotDataArr != null)
                return this.pivotDataArr.get(paramInt - 1);
        }
        return null;
    }

    public TbPivotInfo[] getPivotInfo(int paramInt) throws SQLException {
        if (this.outParamTypes[paramInt - 1] == 16) {
            if (this.pivotInfoArr == null || this.pivotInfoArr[paramInt - 1] == null) {
                int i = TbCommon.bytes2Int(this.outParamBytes[paramInt - 1], 0, this.outItems[paramInt - 1].getLength());
                this.conn.getTbComm().executePivot(this, paramInt - 1, i);
            }
            if (this.pivotInfoArr != null)
                return this.pivotInfoArr[paramInt - 1];
        }
        return null;
    }

    public Ref getRef(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getRefInternal(paramInt);
    }

    public Ref getRef(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getRefInternal(i);
    }

    private Ref getRefInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        return setLastColumnWasNull(bindItem) ? null : this.typeConverter.toRef(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), this.outParamTypes[paramInt - 1]);
    }

    public RowId getRowId(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getRowIdInternal(paramInt);
    }

    public RowId getRowId(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getRowIdInternal(i);
    }

    private RowId getRowIdInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        return (RowId) (setLastColumnWasNull(bindItem) ? null : this.typeConverter.toRowId(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), this.outParamTypes[paramInt - 1]));
    }

    public short getShort(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getShortInternal(paramInt);
    }

    public short getShort(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getShortInternal(i);
    }

    private short getShortInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        return setLastColumnWasNull(bindItem) ? 0 : this.typeConverter.toShort(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), this.outParamTypes[paramInt - 1]);
    }

    public SQLXML getSQLXML(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getSQLXMLInternal(paramInt);
    }

    public SQLXML getSQLXML(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getSQLXMLInternal(i);
    }

    private SQLXML getSQLXMLInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        return setLastColumnWasNull(bindItem) ? null : this.typeConverter.toSQLXML(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), 13, false);
    }

    public String getString(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getStringInternal(paramInt);
    }

    public String getString(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getStringInternal(i);
    }

    private String getStringInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        return setLastColumnWasNull(bindItem) ? null : this.typeConverter.toString(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), this.outParamTypes[paramInt - 1], true);
    }

    public Time getTime(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getTimeInternal(paramInt);
    }

    public Time getTime(int paramInt, Calendar paramCalendar) throws SQLException {
        Time time = getTimeInternal(paramInt);
        if (paramCalendar != null) {
            paramCalendar.setTime(time);
            time = (Time) paramCalendar.getTime();
        }
        return time;
    }

    public Time getTime(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getTimeInternal(i);
    }

    public Time getTime(String paramString, Calendar paramCalendar) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getTime(i, paramCalendar);
    }

    private Time getTimeInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        return setLastColumnWasNull(bindItem) ? null : this.typeConverter.toTime(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), this.outParamTypes[paramInt - 1]);
    }

    public Timestamp getTimestamp(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getTimestampInternal(paramInt);
    }

    public Timestamp getTimestamp(int paramInt, Calendar paramCalendar) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        Timestamp timestamp = getTimestampInternal(paramInt);
        if (paramCalendar != null) {
            paramCalendar.setTime(timestamp);
            timestamp = (Timestamp) paramCalendar.getTime();
        }
        return timestamp;
    }

    public Timestamp getTimestamp(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getTimestampInternal(i);
    }

    public Timestamp getTimestamp(String paramString, Calendar paramCalendar) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getTimestamp(i, paramCalendar);
    }

    private Timestamp getTimestampInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        return setLastColumnWasNull(bindItem) ? null : this.typeConverter.toTimestamp(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), this.outParamTypes[paramInt - 1]);
    }

    public TbTimestamp getTbTimestamp(int paramInt) throws SQLException {
        if (this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        return getTbTimestampInternal(paramInt);
    }

    public TbTimestamp getTbTimestamp(String paramString) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getTbTimestampInternal(i);
    }

    private TbTimestamp getTbTimestampInternal(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        return setLastColumnWasNull(bindItem) ? null : this.typeConverter.toTbTimestamp(this.outParamBytes[paramInt - 1], 0, bindItem.getLength(), this.outParamTypes[paramInt - 1]);
    }

    public URL getURL(int paramInt) throws SQLException {
        throw TbError.newSQLException(-90201);
    }

    public URL getURL(String paramString) throws SQLException {
        throw TbError.newSQLException(-90201);
    }

    public synchronized void registerOutParameter(int paramInt1, int paramInt2) throws SQLException {
        registerOutParameter(paramInt1, paramInt2, 0);
    }

    public synchronized void registerOutParameter(int paramInt1, int paramInt2, int paramInt3) throws SQLException {
        registerOutParameterInternal(paramInt1, paramInt2, paramInt3, (String) null);
    }

    public void registerOutParameter(int paramInt1, int paramInt2, String paramString) throws SQLException {
        registerOutParameterInternal(paramInt1, paramInt2, 0, paramString);
    }

    public synchronized void registerOutParameter(String paramString, int paramInt) throws SQLException {
        registerOutParameter(paramString, paramInt, 0);
    }

    public synchronized void registerOutParameter(String paramString, int paramInt1, int paramInt2) throws SQLException {
        int i = addNamedParam(paramString);
        registerOutParameterInternal(i, paramInt1, paramInt2, (String) null);
    }

    public void registerOutParameter(String paramString1, int paramInt, String paramString2) throws SQLException {
        int i = addNamedParam(paramString1);
        registerOutParameterInternal(i, paramInt, 0, paramString2);
    }

    public void registerOutParameter(int paramInt, SQLType paramSQLType) throws SQLException {
        registerOutParameter(paramInt, paramSQLType.getVendorTypeNumber().intValue());
    }

    public void registerOutParameter(int paramInt1, SQLType paramSQLType, int paramInt2) throws SQLException {
        registerOutParameter(paramInt1, paramSQLType.getVendorTypeNumber().intValue(), paramInt2);
    }

    public void registerOutParameter(int paramInt, SQLType paramSQLType, String paramString) throws SQLException {
        registerOutParameter(paramInt, paramSQLType.getVendorTypeNumber().intValue(), paramString);
    }

    public void registerOutParameter(String paramString, SQLType paramSQLType) throws SQLException {
        registerOutParameter(paramString, paramSQLType.getVendorTypeNumber().intValue());
    }

    public void registerOutParameter(String paramString, SQLType paramSQLType, int paramInt) throws SQLException {
        registerOutParameter(paramString, paramSQLType.getVendorTypeNumber().intValue(), paramInt);
    }

    public void registerOutParameter(String paramString1, SQLType paramSQLType, String paramString2) throws SQLException {
        registerOutParameter(paramString1, paramSQLType.getVendorTypeNumber().intValue(), paramString2);
    }

    private void registerOutParameterInternal(int paramInt1, int paramInt2, int paramInt3, String paramString) throws SQLException {
        switch (paramInt2) {
            case -2003:
            case 2003:
                this.bindData.setOUTParam(paramInt1 - 1, paramInt2, paramString, this.conn);
                if (this.binder[this.currentRowIndex][paramInt1 - 1] == null)
                    this.binder[this.currentRowIndex][paramInt1 - 1] = this.staticArrayOutBinder;
                int i = this.bindData.getBindItem(paramInt1 - 1).getTypeDescriptor().getDataType();
                setParamTypes(paramInt1 - 1, (byte) i);
                return;
            case 2002:
                this.bindData.setOUTParam(paramInt1 - 1, paramInt2, paramString, this.conn);
                if (this.binder[this.currentRowIndex][paramInt1 - 1] == null)
                    this.binder[this.currentRowIndex][paramInt1 - 1] = this.staticStructOutBinder;
                i = this.bindData.getBindItem(paramInt1 - 1).getTypeDescriptor().getDataType();
                setParamTypes(paramInt1 - 1, (byte) i);
                return;
        }
        this.bindData.setOUTParam(paramInt1 - 1, paramInt2, paramString);
        if (this.binder[this.currentRowIndex][paramInt1 - 1] == null)
            this.binder[this.currentRowIndex][paramInt1 - 1] = this.staticNullBinder;
        int i = DataType.getDataType(paramInt2);
        setParamTypes(paramInt1 - 1, (byte) i);
    }

    public synchronized void close() throws SQLException {
        for (byte b = 0; this.outPrefetchedRs != null && b < this.outPrefetchedRs.length; b++) {
            if (this.outPrefetchedRs[b] != null)
                this.outPrefetchedRs[b].close();
        }
        super.close();
    }

    protected void reset() {
        super.reset();
        this.namedParamArr = null;
        this.outParamBytes = (byte[][]) null;
        this.outParamTypes = null;
        this.outPrefetchedRs = null;
        this.outItems = null;
    }

    public synchronized void resetForCache() {
        super.resetForCache();
        this.namedParamArr = null;
        this.outParamBytes = (byte[][]) null;
        this.outParamTypes = null;
        this.outPrefetchedRs = null;
        this.outItems = null;
    }

    private void rewriteQuestionToNamedParam() throws SQLException {
        byte b1 = 0;
        char[] arrayOfChar = this.originalSql.toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        if (getParameterCnt() != this.namedParamCnt)
            throw TbError.newSQLException(-90627);
        for (byte b2 = 0; b2 < arrayOfChar.length; b2++) {
            if (arrayOfChar[b2] == '?') {
                stringBuffer.append(this.namedParamArr[b1++]);
                stringBuffer.append("=>?");
            } else {
                stringBuffer.append(arrayOfChar[b2]);
            }
        }
        if (b1 != this.namedParamCnt)
            throw TbError.newSQLException(-90627);
        this.originalSql = new String(stringBuffer);
    }

    public void setAsciiStream(int paramInt, InputStream paramInputStream) throws SQLException {
        this.haveOrdinalParameter = true;
        setBinaryStreamInternal(paramInt, paramInputStream, 2147483647);
    }

    public void setAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
        this.haveOrdinalParameter = true;
        setBinaryStreamInternal(paramInt1, paramInputStream, paramInt2);
    }

    public void setAsciiStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
        if (paramLong > 2147483647L)
            throw TbError.newSQLException(-90656, Long.toString(paramLong));
        this.haveOrdinalParameter = true;
        setBinaryStreamInternal(paramInt, paramInputStream, (int) paramLong);
    }

    public void setAsciiStream(String paramString, InputStream paramInputStream) throws SQLException {
        int i = addNamedParam(paramString);
        setBinaryStreamInternal(i, paramInputStream, 2147483647);
    }

    public void setAsciiStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
        int i = addNamedParam(paramString);
        setBinaryStreamInternal(i, paramInputStream, paramInt);
    }

    public void setAsciiStream(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
        if (paramLong > 2147483647L)
            throw TbError.newSQLException(-90656, Long.toString(paramLong));
        int i = addNamedParam(paramString);
        setBinaryStreamInternal(i, paramInputStream, (int) paramLong);
    }

    public void setBigDecimal(int paramInt, BigDecimal paramBigDecimal) throws SQLException {
        this.haveOrdinalParameter = true;
        setBigDecimalInternal(paramInt, paramBigDecimal);
    }

    public void setBigDecimal(String paramString, BigDecimal paramBigDecimal) throws SQLException {
        int i = addNamedParam(paramString);
        setBigDecimalInternal(i, paramBigDecimal);
    }

    public void setBinaryStream(int paramInt, InputStream paramInputStream) throws SQLException {
        this.haveOrdinalParameter = true;
        setBinaryStreamInternal(paramInt, paramInputStream, 2147483647);
    }

    public void setBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
        this.haveOrdinalParameter = true;
        setBinaryStreamInternal(paramInt1, paramInputStream, paramInt2);
    }

    public void setBinaryStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
        if (paramLong > 2147483647L)
            throw TbError.newSQLException(-90656, Long.toString(paramLong));
        this.haveOrdinalParameter = true;
        setBinaryStreamInternal(paramInt, paramInputStream, (int) paramLong);
    }

    public void setBinaryStream(String paramString, InputStream paramInputStream) throws SQLException {
        int i = addNamedParam(paramString);
        setBinaryStreamInternal(i, paramInputStream, 2147483647);
    }

    public void setBinaryStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
        int i = addNamedParam(paramString);
        setBinaryStreamInternal(i, paramInputStream, paramInt);
    }

    public void setBinaryStream(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
        if (paramLong > 2147483647L)
            throw TbError.newSQLException(-90656, Long.toString(paramLong));
        int i = addNamedParam(paramString);
        setBinaryStreamInternal(i, paramInputStream, (int) paramLong);
    }

    public void setBlob(int paramInt, Blob paramBlob) throws SQLException {
        this.haveOrdinalParameter = true;
        setBlobInternal(paramInt, paramBlob);
    }

    public void setBlob(int paramInt, InputStream paramInputStream) throws SQLException {
        this.haveOrdinalParameter = true;
        setBlobInternal(paramInt, paramInputStream, 2147483647L);
    }

    public void setBlob(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
        this.haveOrdinalParameter = true;
        setBlobInternal(paramInt, paramInputStream, paramLong);
    }

    public void setBlob(String paramString, Blob paramBlob) throws SQLException {
        int i = addNamedParam(paramString);
        setBlobInternal(i, paramBlob);
    }

    public void setBlob(String paramString, InputStream paramInputStream) throws SQLException {
        int i = addNamedParam(paramString);
        setBlobInternal(i, paramInputStream, 2147483647L);
    }

    public void setBlob(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
        int i = addNamedParam(paramString);
        setBlobInternal(i, paramInputStream, paramLong);
    }

    public void setBoolean(int paramInt, boolean paramBoolean) throws SQLException {
        this.haveOrdinalParameter = true;
        setBooleanInternal(paramInt, paramBoolean);
    }

    public void setBoolean(String paramString, boolean paramBoolean) throws SQLException {
        int i = addNamedParam(paramString);
        setBooleanInternal(i, paramBoolean);
    }

    public void setByte(int paramInt, byte paramByte) throws SQLException {
        this.haveOrdinalParameter = true;
        setByteInternal(paramInt, paramByte);
    }

    public void setByte(String paramString, byte paramByte) throws SQLException {
        int i = addNamedParam(paramString);
        setByteInternal(i, paramByte);
    }

    public void setBytes(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
        this.haveOrdinalParameter = true;
        setBytesInternal(paramInt, 4, paramArrayOfbyte);
    }

    public void setBytes(int paramInt1, int paramInt2, byte[] paramArrayOfbyte) throws SQLException {
        this.haveOrdinalParameter = true;
        setBytesInternal(paramInt1, paramInt2, paramArrayOfbyte);
    }

    public void setBytes(String paramString, byte[] paramArrayOfbyte) throws SQLException {
        int i = addNamedParam(paramString);
        setBytesInternal(i, 4, paramArrayOfbyte);
    }

    public void setRAW(int paramInt, TbRAW paramTbRAW) throws SQLException {
        setBytes(paramInt, paramTbRAW.getBytes());
    }

    public void setRAW(int paramInt1, int paramInt2, TbRAW paramTbRAW) throws SQLException {
        setBytes(paramInt1, paramInt2, paramTbRAW.getBytes());
    }

    public void setRAW(String paramString, TbRAW paramTbRAW) throws SQLException {
        setBytes(paramString, paramTbRAW.getBytes());
    }

    public void setCharacterStream(int paramInt, Reader paramReader) throws SQLException {
        this.haveOrdinalParameter = true;
        setCharacterStreamInternal(paramInt, paramReader, 2147483647);
    }

    public void setCharacterStream(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
        this.haveOrdinalParameter = true;
        setCharacterStreamInternal(paramInt1, paramReader, paramInt2);
    }

    public void setCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
        if (paramLong > 2147483647L)
            throw TbError.newSQLException(-90656, Long.toString(paramLong));
        this.haveOrdinalParameter = true;
        setCharacterStreamInternal(paramInt, paramReader, (int) paramLong);
    }

    public void setCharacterStream(String paramString, Reader paramReader) throws SQLException {
        int i = addNamedParam(paramString);
        setCharacterStreamInternal(i, paramReader, 2147483647);
    }

    public void setCharacterStream(String paramString, Reader paramReader, int paramInt) throws SQLException {
        int i = addNamedParam(paramString);
        setCharacterStreamInternal(i, paramReader, paramInt);
    }

    public void setCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
        if (paramLong > 2147483647L)
            throw TbError.newSQLException(-90656, Long.toString(paramLong));
        int i = addNamedParam(paramString);
        setCharacterStreamInternal(i, paramReader, (int) paramLong);
    }

    public void setClob(int paramInt, Clob paramClob) throws SQLException {
        this.haveOrdinalParameter = true;
        setClobInternal(paramInt, 13, paramClob);
    }

    public void setClob(int paramInt, Reader paramReader) throws SQLException {
        this.haveOrdinalParameter = true;
        setClobInternal(paramInt, paramReader, 2147483647L);
    }

    public void setClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
        this.haveOrdinalParameter = true;
        setClobInternal(paramInt, paramReader, paramLong);
    }

    public void setClob(String paramString, Clob paramClob) throws SQLException {
        int i = addNamedParam(paramString);
        setClobInternal(i, 13, paramClob);
    }

    public void setClob(String paramString, Reader paramReader) throws SQLException {
        int i = addNamedParam(paramString);
        setClobInternal(i, paramReader, 2147483647L);
    }

    public void setClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
        int i = addNamedParam(paramString);
        setClobInternal(i, paramReader, paramLong);
    }

    public void setDate(int paramInt, Date paramDate) throws SQLException {
        this.haveOrdinalParameter = true;
        setDateInternal(paramInt, paramDate);
    }

    public void setDate(int paramInt, Date paramDate, Calendar paramCalendar) throws SQLException {
        this.haveOrdinalParameter = true;
        setDateInternal(paramInt, paramDate);
    }

    public void setDate(String paramString, Date paramDate) throws SQLException {
        int i = addNamedParam(paramString);
        setDateInternal(i, paramDate);
    }

    public void setDate(String paramString, Date paramDate, Calendar paramCalendar) throws SQLException {
        int i = addNamedParam(paramString);
        setDateInternal(i, paramDate);
    }

    public void setTbDate(int paramInt, TbDate paramTbDate) throws SQLException {
        this.haveOrdinalParameter = true;
        setTbDateInternal(paramInt, paramTbDate);
    }

    public void setTbDate(String paramString, TbDate paramTbDate) throws SQLException {
        int i = addNamedParam(paramString);
        setTbDateInternal(i, paramTbDate);
    }

    public void setDouble(int paramInt, double paramDouble) throws SQLException {
        this.haveOrdinalParameter = true;
        setDoubleInternal(paramInt, paramDouble);
    }

    public void setDouble(String paramString, double paramDouble) throws SQLException {
        int i = addNamedParam(paramString);
        setDoubleInternal(i, paramDouble);
    }

    public void setFloat(int paramInt, float paramFloat) throws SQLException {
        this.haveOrdinalParameter = true;
        setFloatInternal(paramInt, paramFloat);
    }

    public void setFloat(String paramString, float paramFloat) throws SQLException {
        int i = addNamedParam(paramString);
        setFloatInternal(i, paramFloat);
    }

    public void setInt(int paramInt1, int paramInt2) throws SQLException {
        this.haveOrdinalParameter = true;
        setIntInternal(paramInt1, paramInt2);
    }

    public void setInt(String paramString, int paramInt) throws SQLException {
        int i = addNamedParam(paramString);
        setIntInternal(i, paramInt);
    }

    private synchronized boolean setLastColumnWasNull(BindItem paramBindItem) {
        return this.lastColumnWasNull = (paramBindItem.getLength() == 0);
    }

    public void setLong(int paramInt, long paramLong) throws SQLException {
        this.haveOrdinalParameter = true;
        setLongInternal(paramInt, paramLong);
    }

    public void setLong(String paramString, long paramLong) throws SQLException {
        int i = addNamedParam(paramString);
        setLongInternal(i, paramLong);
    }

    public void setNCharacterStream(int paramInt, Reader paramReader) throws SQLException {
        this.haveOrdinalParameter = true;
        setNCharacterStreamInternal(paramInt, paramReader, 2147483647);
    }

    public void setNCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
        if (paramLong > 2147483647L)
            throw TbError.newSQLException(-90656, Long.toString(paramLong));
        this.haveOrdinalParameter = true;
        setNCharacterStreamInternal(paramInt, paramReader, (int) paramLong);
    }

    public void setNCharacterStream(String paramString, Reader paramReader) throws SQLException {
        int i = addNamedParam(paramString);
        setNCharacterStreamInternal(i, paramReader, 2147483647);
    }

    public void setNCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
        if (paramLong > 2147483647L)
            throw TbError.newSQLException(-90656, Long.toString(paramLong));
        int i = addNamedParam(paramString);
        setNCharacterStreamInternal(i, paramReader, (int) paramLong);
    }

    public void setNClob(int paramInt, Clob paramClob) throws SQLException {
        this.haveOrdinalParameter = true;
        setClobInternal(paramInt, 20, paramClob);
    }

    public void setNClob(int paramInt, NClob paramNClob) throws SQLException {
        this.haveOrdinalParameter = true;
        setClobInternal(paramInt, 20, paramNClob);
    }

    public void setNClob(int paramInt, Reader paramReader) throws SQLException {
        this.haveOrdinalParameter = true;
        setNClobInternal(paramInt, paramReader, 2147483647);
    }

    public void setNClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
        if (paramLong > 2147483647L)
            throw TbError.newSQLException(-90656, Long.toString(paramLong));
        this.haveOrdinalParameter = true;
        setNClobInternal(paramInt, paramReader, (int) paramLong);
    }

    public void setNClob(String paramString, NClob paramNClob) throws SQLException {
        int i = addNamedParam(paramString);
        setClobInternal(i, 20, paramNClob);
    }

    public void setNClob(String paramString, Reader paramReader) throws SQLException {
        int i = addNamedParam(paramString);
        setNClobInternal(i, paramReader, 2147483647);
    }

    public void setNClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
        if (paramLong > 2147483647L)
            throw TbError.newSQLException(-90656, Long.toString(paramLong));
        int i = addNamedParam(paramString);
        setNClobInternal(i, paramReader, (int) paramLong);
    }

    public void setNString(int paramInt, String paramString) throws SQLException {
        this.haveOrdinalParameter = true;
        setNStringInternal(paramInt, paramString);
    }

    public void setNString(String paramString1, String paramString2) throws SQLException {
        int i = addNamedParam(paramString1);
        setNStringInternal(i, paramString2);
    }

    public void setNull(int paramInt1, int paramInt2) throws SQLException {
        this.haveOrdinalParameter = true;
        setNullInternal(paramInt1, DataType.getDataType(paramInt2));
    }

    public void setNull(int paramInt1, int paramInt2, String paramString) throws SQLException {
        this.haveOrdinalParameter = true;
        setNullInternal(paramInt1, DataType.getDataType(paramInt2));
    }

    public void setNull(String paramString, int paramInt) throws SQLException {
        int i = addNamedParam(paramString);
        setNullInternal(i, paramInt);
    }

    public void setNull(String paramString1, int paramInt, String paramString2) throws SQLException {
        int i = addNamedParam(paramString1);
        setNullInternal(i, paramInt);
    }

    public void setObject(int paramInt, Object paramObject) throws SQLException {
        setObject(paramInt, paramObject, DataType.getSqlType(paramObject), 0);
    }

    public void setObject(int paramInt1, Object paramObject, int paramInt2) throws SQLException {
        setObject(paramInt1, paramObject, paramInt2, 0);
    }

    public void setObject(int paramInt1, Object paramObject, int paramInt2, int paramInt3) throws SQLException {
        this.haveOrdinalParameter = true;
        setObjectInternal(paramInt1, paramObject, paramInt2, paramInt3);
    }

    public void setObject(String paramString, Object paramObject) throws SQLException {
        setObject(paramString, paramObject, DataType.getSqlType(paramObject), 0);
    }

    public void setObject(String paramString, Object paramObject, int paramInt) throws SQLException {
        setObject(paramString, paramObject, paramInt, 0);
    }

    public void setObject(String paramString, Object paramObject, int paramInt1, int paramInt2) throws SQLException {
        int i = addNamedParam(paramString);
        setObjectInternal(i, paramObject, paramInt1, paramInt2);
    }

    public void setOutParam(int paramInt1, int paramInt2, byte[] paramArrayOfbyte, TbResultSet paramTbResultSet) throws SQLException {
        DataType.checkValidDataType(paramInt2);
        this.outParamTypes[paramInt1] = (byte) paramInt2;
        this.outParamBytes[paramInt1] = paramArrayOfbyte;
        this.outPrefetchedRs[paramInt1] = paramTbResultSet;
    }

    public void setPivotInfo(int paramInt, TbPivotInfo[] paramArrayOfTbPivotInfo) {
        if (this.pivotInfoArr == null)
            this.pivotInfoArr = new TbPivotInfo[this.bindParamCnt][];
        this.pivotInfoArr[paramInt] = paramArrayOfTbPivotInfo;
    }

    public void setRef(int paramInt, Ref paramRef) throws SQLException {
        this.haveOrdinalParameter = true;
        setRefInternal(paramInt, 33, paramRef);
    }

    public void setRowId(int paramInt, RowId paramRowId) throws SQLException {
        this.haveOrdinalParameter = true;
        setRowIdInternal(paramInt, paramRowId);
    }

    public void setRowId(String paramString, RowId paramRowId) throws SQLException {
        int i = addNamedParam(paramString);
        setRowIdInternal(i, paramRowId);
    }

    public void setShort(int paramInt, short paramShort) throws SQLException {
        this.haveOrdinalParameter = true;
        setShortInternal(paramInt, paramShort);
    }

    public void setShort(String paramString, short paramShort) throws SQLException {
        int i = addNamedParam(paramString);
        setShortInternal(i, paramShort);
    }

    public void setSQLXML(int paramInt, SQLXML paramSQLXML) throws SQLException {
        this.haveOrdinalParameter = true;
        setSQLXMLInternal(paramInt, paramSQLXML);
    }

    public void setSQLXML(String paramString, SQLXML paramSQLXML) throws SQLException {
        int i = addNamedParam(paramString);
        setSQLXMLInternal(i, paramSQLXML);
    }

    public void setString(int paramInt, String paramString) throws SQLException {
        this.haveOrdinalParameter = true;
        setStringInternal(paramInt, paramString);
    }

    public void setString(String paramString1, String paramString2) throws SQLException {
        int i = addNamedParam(paramString1);
        setStringInternal(i, paramString2);
    }

    public void setTime(int paramInt, Time paramTime) throws SQLException {
        this.haveOrdinalParameter = true;
        setTimeInternal(paramInt, paramTime);
    }

    public void setTime(int paramInt, Time paramTime, Calendar paramCalendar) throws SQLException {
        this.haveOrdinalParameter = true;
        setTimeInternal(paramInt, paramTime);
    }

    public void setTime(String paramString, Time paramTime) throws SQLException {
        int i = addNamedParam(paramString);
        setTimeInternal(i, paramTime);
    }

    public void setTime(String paramString, Time paramTime, Calendar paramCalendar) throws SQLException {
        int i = addNamedParam(paramString);
        setTimeInternal(i, paramTime);
    }

    public void setTimestamp(int paramInt, Timestamp paramTimestamp) throws SQLException {
        this.haveOrdinalParameter = true;
        setTimestampInternal(paramInt, paramTimestamp);
    }

    public void setTimestamp(int paramInt, Timestamp paramTimestamp, Calendar paramCalendar) throws SQLException {
        this.haveOrdinalParameter = true;
        setTimestampInternal(paramInt, paramTimestamp);
    }

    public void setTimestamp(String paramString, Timestamp paramTimestamp) throws SQLException {
        int i = addNamedParam(paramString);
        setTimestampInternal(i, paramTimestamp);
    }

    public void setTimestamp(String paramString, Timestamp paramTimestamp, Calendar paramCalendar) throws SQLException {
        int i = addNamedParam(paramString);
        setTimestampInternal(i, paramTimestamp);
    }

    public void setTbTimestamp(int paramInt, TbTimestamp paramTbTimestamp) throws SQLException {
        this.haveOrdinalParameter = true;
        setTbTimestampInternal(paramInt, paramTbTimestamp);
    }

    public void setTbTimestamp(String paramString, TbTimestamp paramTbTimestamp) throws SQLException {
        int i = addNamedParam(paramString);
        setTbTimestampInternal(i, paramTbTimestamp);
    }

    @Deprecated
    public void setUnicodeStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
        this.haveOrdinalParameter = true;
        setUnicodeStreamInternal(paramInt1, paramInputStream, paramInt2);
    }

    @Deprecated
    public void setUnicodeStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
        int i = addNamedParam(paramString);
        setUnicodeStreamInternal(i, paramInputStream, paramInt);
    }

    public void setURL(int paramInt, URL paramURL) throws SQLException {
        throw TbError.newSQLException(-90201);
    }

    public void setURL(String paramString, URL paramURL) throws SQLException {
        throw TbError.newSQLException(-90201);
    }

    public boolean wasNull() throws SQLException {
        return this.lastColumnWasNull;
    }

    public boolean isWrapperFor(Class<?> paramClass) throws SQLException {
        return paramClass.isInstance(this);
    }

    public <T> T unwrap(Class<T> paramClass) throws SQLException {
        try {
            return paramClass.cast(this);
        } catch (ClassCastException classCastException) {
            throw TbError.newSQLException(-90657);
        }
    }

    public <T> T getObject(int paramInt, Class<T> paramClass) throws SQLException {
        String str = paramClass.getName();
        switch (str) {
            case "java.sql.Array":
                return (T) getObject(paramInt);
            case "java.math.BigDecimal":
                return (T) getBigDecimal(paramInt);
            case "java.math.BigInteger":
                return (T) getBigDecimal(paramInt).toBigInteger();
            case "java.sql.Blob":
                return (T) getBlob(paramInt);
            case "java.lang.Byte":
                return (T) new Byte(getByte(paramInt));
            case "byte[]":
                return (T) getBytes(paramInt);
            case "java.io.Reader":
                return (T) getCharacterStream(paramInt);
            case "java.sql.Clob":
                return (T) getClob(paramInt);
            case "java.sql.Date":
                return (T) getDate(paramInt);
            case "java.lang.Double":
                return (T) new Double(getDouble(paramInt));
            case "java.lang.Float":
                return (T) new Float(getFloat(paramInt));
            case "java.lang.Integer":
                return (T) new Integer(getInt(paramInt));
            case "java.lang.Long":
                return (T) new Long(getLong(paramInt));
            case "java.sql.NClob":
                return (T) getNClob(paramInt);
            case "java.sql.Ref":
                return (T) getRef(paramInt);
            case "java.sql.RowId":
                return (T) getRowId(paramInt);
            case "java.lang.Short":
                return (T) new Short(getShort(paramInt));
            case "java.sql.SQLXML":
                return (T) getSQLXML(paramInt);
            case "java.lang.String":
                return (T) getString(paramInt);
            case "java.sql.Time":
                return (T) getTime(paramInt);
            case "java.sql.Timestamp":
                return (T) getTimestamp(paramInt);
            case "java.time.LocalDate":
                return (T) getDate(paramInt).toLocalDate();
            case "java.time.LocalTime":
                return (T) getTimestamp(paramInt).toLocalDateTime().toLocalTime();
            case "java.time.LocalDateTime":
                return (T) getTimestamp(paramInt).toLocalDateTime();
            case "java.time.OffsetTime":
                return (T) getOffsetTime(paramInt);
            case "java.time.OffsetDateTime":
                return (T) getOffsetDateTime(paramInt);
            case "java.net.URL":
                return (T) getURL(paramInt);
        }
        return (T) getObject(paramInt);
    }

    public <T> T getObject(String paramString, Class<T> paramClass) throws SQLException {
        if (!this.haveNamedParameter)
            throw TbError.newSQLException(-90655);
        int i = addNamedParam(paramString);
        return getObject(i, paramClass);
    }

    private OffsetTime getOffsetTime(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        if (setLastColumnWasNull(bindItem))
            return null;
        if (bindItem.getLength() == 17) {
            TimeZone timeZone = this.typeConverter.toTimeZoneFromBytes(this.outParamBytes[paramInt - 1], 0, bindItem.getLength());
            ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(TimeZone.getDefault().getRawOffset() / 1000);
            return OffsetTime.of(getTimestamp(paramInt).toLocalDateTime().toLocalTime(), zoneOffset).withOffsetSameInstant(ZoneOffset.ofTotalSeconds(timeZone.getRawOffset() / 1000));
        }
        return OffsetTime.of(getTime(paramInt).toLocalTime(), ZoneOffset.UTC);
    }

    private OffsetDateTime getOffsetDateTime(int paramInt) throws SQLException {
        checkConnectionOpen();
        checkOutParamExists(paramInt - 1);
        BindItem bindItem = this.outItems[paramInt - 1];
        if (setLastColumnWasNull(bindItem))
            return null;
        if (bindItem.getLength() == 17) {
            TimeZone timeZone = this.typeConverter.toTimeZoneFromBytes(this.outParamBytes[paramInt - 1], 0, bindItem.getLength());
            ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(TimeZone.getDefault().getRawOffset() / 1000);
            return OffsetDateTime.of(getTimestamp(paramInt).toLocalDateTime(), zoneOffset).withOffsetSameInstant(ZoneOffset.ofTotalSeconds(timeZone.getRawOffset() / 1000));
        }
        return OffsetDateTime.of(getTimestamp(paramInt).toLocalDateTime(), ZoneOffset.UTC);
    }
}