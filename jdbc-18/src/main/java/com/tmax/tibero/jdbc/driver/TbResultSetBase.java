package com.tmax.tibero.jdbc.driver;

import com.tmax.tibero.jdbc.*;
import com.tmax.tibero.jdbc.data.*;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.util.TbCommon;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public abstract class TbResultSetBase extends TbResultSet {
    public static final int EMPTY_CURSOR_ID = -1;

    public static final int EMPTY_RSET = -2;

    private static final long CRC_POLY = 79764919L;

    protected TbStatement stmt;

    protected int csrID = -1;

    protected DataTypeConverter typeConverter;

    protected int columnCount = -1;

    protected Column[] cols;

    protected HashMap<String, Integer> columnNameMap;

    protected int beginColumnIndex = 0;

    protected int currentRowIndex = -1;

    protected int currentFetchCount = 0;

    protected int fetchDirection = 1000;

    protected int fetchSize = 50;

    protected boolean lastColumnWasNull = false;

    protected byte[] rowChunk;

    protected boolean fetchComplete = false;

    protected boolean isRsetClosed = false;

    protected boolean isRsetComplete = false;

    protected boolean isSvrCsrClosed = false;

    private boolean closeStatementOnClose = false;

    private int preparedFetchCnt = 0;

    private boolean mapDateToTimestamp = true;

    private boolean foCsrEnabled = false;

    private int foECode = 0;

    protected static final long[] CRC_TBL = new long[256];

    protected TbResultSetBase(TbStatement paramTbStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfbyte) throws SQLException {
        super(paramTbStatement.getRealRsetType());
        this.stmt = paramTbStatement;
        this.csrID = paramInt1;
        this.typeConverter = paramTbStatement.conn.getTypeConverter();
        this.columnCount = paramInt2 + paramInt3;
        this.fetchDirection = paramTbStatement.getFetchDirection();
        this.fetchSize = paramTbStatement.getFetchSize();
        this.closeStatementOnClose = false;
        this.foCsrEnabled = paramTbStatement.conn.info.isFailoverCursorEnabled();
        if (paramTbStatement.getRealRsetType() != null)
            this.beginColumnIndex = paramInt3 + ((paramTbStatement.getRealRsetType().useRowId() == true) ? 1 : 0);
        makeColumnMetaArray(this.columnCount);
        this.rowChunk = paramArrayOfbyte;
    }

    private void buildColumnNameMap() {
        this.columnNameMap = new HashMap<>();
        for (int i = getColumnCount() - 1; i >= 0; i--)
            this.columnNameMap.put(this.cols[i + this.beginColumnIndex].getName().toUpperCase(), new Integer(i + 1));
    }

    public void cancelRowUpdates() throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    void checkColumnIndex(int paramInt) throws SQLException {
        int i = getColumnCount();
        if (i < 0)
            throw TbError.newSQLException(-90607);
        if (paramInt <= 0 || paramInt > i)
            throw TbError.newSQLException(-90609);
    }

    protected void checkRsetAndConnClosed() throws SQLException {
        if (this.csrID == -1)
            throw TbError.newSQLException(-90607);
        if (this.isRsetClosed)
            throw TbError.newSQLException(-90646);
        if (this.isSvrCsrClosed)
            throw TbError.newSQLException(-90647);
        if (this.stmt.conn == null || this.stmt.conn.isClosed())
            throw TbError.newSQLException(-90603);
        if (this.foECode != 0)
            if (isFoCsrEnabled() && this.foECode == -90700) {
                recover();
                addWarning(TbError.newSQLWarning(-590786));
            } else {
                int i = this.foECode;
                reset();
                this.isRsetClosed = true;
                throw TbError.newSQLException(-90646, TbError.getMsg(i));
            }
    }

    public synchronized void close() throws SQLException {
        if (this.isRsetClosed || this.isRsetComplete)
            return;
        try {
            closeCursor();
            if (!this.isRsetComplete && this.stmt.isCloseOnCompletion()) {
                this.isRsetComplete = true;
                this.stmt.close();
            }
        } catch (SQLException sQLException) {

        } finally {
            reset();
            this.isRsetClosed = true;
            if (this.closeStatementOnClose)
                try {
                    this.stmt.close();
                } catch (SQLException sQLException) {
                }
        }
    }

    public synchronized void closeCursor() throws SQLException {
        if (this.isSvrCsrClosed)
            return;
        TbConnection tbConnection = (TbConnection) this.stmt.getConnection();
        ServerInfo serverInfo = tbConnection.getServerInfo();
        if (serverInfo != null) {
            int i = 0;
            int autoClosed = this.fetchComplete ? 1 : 0;
            int j = serverInfo.getProtocolMajorVersion();
            int k = serverInfo.getProtocolMinorVersion();
            if (j > 2 || (j == 2 && k < 3))
                i = autoClosed & (!this.haveLocator ? 1 : 0);
            if (this.stmt != null && i == 0 && (tbConnection.isPooledConnection || !tbConnection.isSessionClosed()))
                tbConnection.closeCursor(this, this.csrID);
        }
        tbConnection.removeFOActiveResultSet(this);
        this.isSvrCsrClosed = true;
    }

    public void closeStatementOnClose() {
        this.closeStatementOnClose = true;
    }

    public void deleteRow() throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    protected void fetchRowsChunk() throws SQLException {
        try {
            if (isFoCsrEnabled() && this.foECode == -90700) {
                recover();
                addWarning(TbError.newSQLWarning(-590786));
            } else if (this.foECode != 0) {
                int i = this.foECode;
                reset();
                this.isRsetClosed = true;
                throw TbError.newSQLException(-90646, TbError.getMsg(i));
            }
        } catch (SQLException sQLException) {
            if (sQLException.getErrorCode() == -90700) {
                recover();
                addWarning(TbError.newSQLWarning(-590786));
            } else {
                throw sQLException;
            }
        }
        try {
            this.stmt.fetch(this);
        } catch (SQLException sQLException) {
            if (isFoCsrEnabled() && sQLException.getErrorCode() == -90700) {
                recover();
                addWarning(TbError.newSQLWarning(-590786));
                this.stmt.fetch(this);
            } else {
                throw sQLException;
            }
        }
    }

    public synchronized int findColumn(String paramString) throws SQLException {
        return getColumnPosByName(paramString);
    }

    public Array getArray(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        if (setLastColumnIsNull(row, paramInt))
            return null;
        TbTypeDescriptor tbTypeDescriptor = getColumnTypeDescriptor(paramInt);
        TbArrayDescriptor tbArrayDescriptor = (tbTypeDescriptor instanceof TbArrayDescriptor) ? (TbArrayDescriptor) tbTypeDescriptor : null;
        return this.typeConverter.toArray(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt), !getRsetType().isScrollable(), tbArrayDescriptor, this.stmt.conn.getTypeMap());
    }

    public Array getArray(String paramString) throws SQLException {
        return getArray(findColumn(paramString));
    }

    public synchronized InputStream getAsciiStream(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? null : this.typeConverter.toAsciiStream(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt));
    }

    public InputStream getAsciiStream(String paramString) throws SQLException {
        return getAsciiStream(findColumn(paramString));
    }

    public int getBeginColumnIndex() {
        return this.beginColumnIndex;
    }

    public BigDecimal getBigDecimal(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? null : this.typeConverter.toBigDecimal(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt));
    }

    @Deprecated
    public synchronized BigDecimal getBigDecimal(int paramInt1, int paramInt2) throws SQLException {
        return getBigDecimal(paramInt1);
    }

    public BigDecimal getBigDecimal(String paramString) throws SQLException {
        return getBigDecimal(findColumn(paramString));
    }

    @Deprecated
    public BigDecimal getBigDecimal(String paramString, int paramInt) throws SQLException {
        return getBigDecimal(findColumn(paramString), paramInt);
    }

    public synchronized InputStream getBinaryStream(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? null : this.typeConverter.toBinaryStream(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt));
    }

    public InputStream getBinaryStream(String paramString) throws SQLException {
        return getBinaryStream(findColumn(paramString));
    }

    public Blob getBlob(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? null : this.typeConverter.toBlob(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt), !this.stmt.getRealRsetType().isScrollable());
    }

    public Blob getBlob(String paramString) throws SQLException {
        return getBlob(findColumn(paramString));
    }

    public synchronized boolean getBoolean(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? false : this.typeConverter.toBoolean(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt));
    }

    public boolean getBoolean(String paramString) throws SQLException {
        return getBoolean(findColumn(paramString));
    }

    public synchronized byte getByte(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? 0 : this.typeConverter.toByte(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt));
    }

    public byte getByte(String paramString) throws SQLException {
        return getByte(findColumn(paramString));
    }

    public synchronized byte[] getBytes(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? null : this.typeConverter.toBytes(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt), !this.stmt.getRealRsetType().isScrollable());
    }

    public synchronized int getBytes(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        if (setLastColumnIsNull(row, paramInt)) {
            paramArrayOfbyte[0] = 0;
            return 0;
        }
        return this.typeConverter.toBytes(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt), paramArrayOfbyte);
    }

    public byte[] getBytes(String paramString) throws SQLException {
        return getBytes(findColumn(paramString));
    }

    public TbRAW getRAW(int paramInt) throws SQLException {
        return new TbRAW(getBytes(paramInt));
    }

    public TbRAW getRAW(String paramString) throws SQLException {
        return new TbRAW(getBytes(paramString));
    }

    public synchronized Reader getCharacterStream(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? null : this.typeConverter.toCharacterStream(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt));
    }

    public Reader getCharacterStream(String paramString) throws SQLException {
        return getCharacterStream(findColumn(paramString));
    }

    public Clob getClob(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        if (setLastColumnIsNull(row, paramInt))
            return null;
        TbTypeDescriptor tbTypeDescriptor = getColumnTypeDescriptor(paramInt);
        if (tbTypeDescriptor instanceof TbStructDescriptor && tbTypeDescriptor.getOID().compareTo("00000000000000000000000000000001") == 0) {
            Object object1 = this.typeConverter.toStruct(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt), !this.stmt.getRealRsetType().isScrollable(), (TbStructDescriptor) tbTypeDescriptor, null, null);
            Object object2 = null;
            if (object1 instanceof TbStruct && ((TbStruct) object1).getNumOfFields() > 1) {
                object2 = ((TbStruct) object1).getAttributes()[0];
                return (Clob) object2;
            }
        }
        return this.typeConverter.toClob(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt), !this.stmt.getRealRsetType().isScrollable());
    }

    public Clob getClob(String paramString) throws SQLException {
        return getClob(findColumn(paramString));
    }

    public Column[] getCols() {
        return this.cols;
    }

    int getColumnCount() {
        return this.columnCount - this.beginColumnIndex;
    }

    protected int getColumnDataType(int paramInt) throws SQLException {
        checkColumnIndex(paramInt);
        return this.cols[paramInt + this.beginColumnIndex - 1].getDataType();
    }

    protected TbTypeDescriptor getColumnTypeDescriptor(int paramInt) throws SQLException {
        checkColumnIndex(paramInt);
        return this.cols[paramInt + this.beginColumnIndex - 1].getDescriptor();
    }

    private int getColumnLength(Row paramRow, int paramInt) {
        return paramRow.getColumnLength(paramInt + this.beginColumnIndex);
    }

    protected int getColumnMaxLength(int paramInt) throws SQLException {
        checkColumnIndex(paramInt);
        return this.cols[paramInt + this.beginColumnIndex - 1].getMaxLength();
    }

    protected String getColumnName(int paramInt) throws SQLException {
        checkColumnIndex(paramInt);
        return this.cols[paramInt + this.beginColumnIndex - 1].getName();
    }

    protected boolean getColumnNullable(int paramInt) throws SQLException {
        checkColumnIndex(paramInt);
        return this.cols[paramInt + this.beginColumnIndex - 1].isNullable();
    }

    private int getColumnOffset(Row paramRow, int paramInt) {
        return paramRow.getColumnOffset(paramInt + this.beginColumnIndex);
    }

    private int getColumnPosByName(String paramString) throws SQLException {
        if (this.cols == null || getColumnCount() == 0)
            throw TbError.newSQLException(-90607);
        if (this.columnNameMap == null)
            buildColumnNameMap();
        Integer integer = this.columnNameMap.get((paramString != null) ? paramString.toUpperCase() : "");
        if (integer == null)
            throw TbError.newSQLException(-90611, paramString);
        return integer.intValue();
    }

    protected int getColumnPrecision(int paramInt) throws SQLException {
        checkColumnIndex(paramInt);
        return this.cols[paramInt + this.beginColumnIndex - 1].getPrecision();
    }

    protected byte[] getColumnRawData(int paramInt) throws SQLException {
        return getCurrentRow().getRawBytes(paramInt + this.beginColumnIndex);
    }

    protected int getColumnScale(int paramInt) throws SQLException {
        checkColumnIndex(paramInt);
        return this.cols[paramInt + this.beginColumnIndex - 1].getScale();
    }

    protected int getColumnSqlType(int paramInt) throws SQLException {
        checkColumnIndex(paramInt);
        return this.cols[paramInt + this.beginColumnIndex - 1].getSqlType();
    }

    protected long getCurrentChunkCRC(byte[] paramArrayOfbyte, int paramInt, long paramLong) {
        long l = 0L;
        for (byte b = 1; b < paramInt; b++) {
            int i = ((int) (l >> 24L) ^ paramArrayOfbyte[b]) & 0xFF;
            l = l << 8L ^ CRC_TBL[i];
        }
        if (paramLong != 0L) {
            byte[] arrayOfByte = new byte[4];
            arrayOfByte[0] = (byte) (int) (paramLong >> 24L & 0xFFL);
            arrayOfByte[1] = (byte) (int) (paramLong >> 16L & 0xFFL);
            arrayOfByte[2] = (byte) (int) (paramLong >> 8L & 0xFFL);
            arrayOfByte[3] = (byte) (int) (paramLong & 0xFFL);
            for (byte b1 = 0; b1 < 4; b1++) {
                int i = ((int) (l >> 24L) ^ arrayOfByte[b1]) & 0xFF;
                l = l << 8L ^ CRC_TBL[i];
            }
        }
        return l & 0xFFFFFFFFL;
    }

    protected abstract Row getCurrentRow() throws SQLException;

    public int getCursorId() {
        return this.csrID;
    }

    public String getCursorName() throws SQLException {
        throw TbError.newSQLException(-90201);
    }

    public synchronized Date getDate(int paramInt) throws SQLException {
        return getDateInternal(paramInt);
    }

    public Date getDate(int paramInt, Calendar paramCalendar) throws SQLException {
        Date date = getDateInternal(paramInt, paramCalendar);
        if (paramCalendar != null) {
            paramCalendar.setTime(date);
            date = new Date(paramCalendar.getTimeInMillis());
        }
        return date;
    }

    public Date getDate(String paramString) throws SQLException {
        return getDate(findColumn(paramString));
    }

    public Date getDate(String paramString, Calendar paramCalendar) throws SQLException {
        return getDate(findColumn(paramString), paramCalendar);
    }

    private Date getDateInternal(int paramInt, Calendar paramCalendar) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? null : this.typeConverter.toDate(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt), paramCalendar);
    }

    private Date getDateInternal(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? null : this.typeConverter.toDate(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt));
    }

    public TbDate getTbDate(int paramInt) throws SQLException {
        return getTbDateInternal(paramInt);
    }

    private TbDate getTbDateInternal(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? null : this.typeConverter.toTbDate(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt));
    }

    public synchronized double getDouble(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? 0.0D : this.typeConverter.toDouble(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt));
    }

    public double getDouble(String paramString) throws SQLException {
        return getDouble(findColumn(paramString));
    }

    public synchronized int getFetchDirection() throws SQLException {
        return this.fetchDirection;
    }

    public synchronized int getFetchSize() throws SQLException {
        return this.fetchSize;
    }

    public synchronized float getFloat(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? 0.0F : this.typeConverter.toFloat(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt));
    }

    public float getFloat(String paramString) throws SQLException {
        return getFloat(findColumn(paramString));
    }

    public synchronized int getInt(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? 0 : this.typeConverter.toInt(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt));
    }

    public int getInt(String paramString) throws SQLException {
        return getInt(findColumn(paramString));
    }

    public synchronized long getLong(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? 0L : this.typeConverter.toLong(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt));
    }

    public long getLong(String paramString) throws SQLException {
        return getLong(findColumn(paramString));
    }

    public synchronized InputStream getLongByteStream(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? null : this.typeConverter.toLongRawStream(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt));
    }

    public boolean getMapDateToTimestamp() {
        return this.mapDateToTimestamp;
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        return (ResultSetMetaData) new TbResultSetMetaData(this.cols, this.beginColumnIndex);
    }

    public Reader getNCharacterStream(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? null : this.typeConverter.toCharacterStream(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt));
    }

    public Reader getNCharacterStream(String paramString) throws SQLException {
        return getNCharacterStream(findColumn(paramString));
    }

    public NClob getNClob(int paramInt) throws SQLException {
        return (NClob) getTbNClob(paramInt);
    }

    public TbNClob getTbNClob(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? null : this.typeConverter.toTbNClob(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt));
    }

    public NClob getNClob(String paramString) throws SQLException {
        return (NClob) getTbNClob(findColumn(paramString));
    }

    public TbNClob getTbNClob(String paramString) throws SQLException {
        return getTbNClob(findColumn(paramString));
    }

    public String getNString(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? null : this.typeConverter.toString(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt), getColumnPrecision(paramInt), getColumnScale(paramInt), !getRsetType().isScrollable());
    }

    public String getNString(String paramString) throws SQLException {
        return getNString(findColumn(paramString));
    }

    public synchronized Object getObject(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? null : this.typeConverter.toObject(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt), getColumnSqlType(paramInt), getColumnPrecision(paramInt), getColumnScale(paramInt), !getRsetType().isScrollable(), getColumnTypeDescriptor(paramInt), null, this.stmt.conn.getTypeMap(), (Statement) this.stmt);
    }

    public Object getObject(int paramInt, Map<String, Class<?>> paramMap) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        if (setLastColumnIsNull(row, paramInt))
            return null;
        TbTypeDescriptor tbTypeDescriptor = getColumnTypeDescriptor(paramInt);
        String str = null;
        Class clazz = null;
        if (tbTypeDescriptor != null) {
            str = tbTypeDescriptor.getSQLTypeName();
            if (str != null && paramMap != null)
                clazz = paramMap.get(str);
        }
        return this.typeConverter.toObject(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt), getColumnSqlType(paramInt), getColumnPrecision(paramInt), getColumnScale(paramInt), !getRsetType().isScrollable(), tbTypeDescriptor, clazz, paramMap, (Statement) this.stmt);
    }

    public Object getObject(String paramString) throws SQLException {
        return getObject(findColumn(paramString));
    }

    public Object getObject(String paramString, Map<String, Class<?>> paramMap) throws SQLException {
        return getObject(findColumn(paramString), paramMap);
    }

    public <T> T getObject(String paramString, Class<T> paramClass) throws SQLException {
        return getObject(findColumn(paramString), paramClass);
    }

    public <T> T getObject(int paramInt, Class<T> paramClass) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        if (setLastColumnIsNull(row, paramInt))
            return null;
        String str = paramClass.getName();
        switch (str) {
            case "java.sql.Array":
                return (T) getObject(paramInt);
            case "java.io.InputStream":
                return (T) getBinaryStream(paramInt);
            case "java.math.BigDecimal":
                return (T) getBigDecimal(paramInt);
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
        return (T) this.typeConverter.toObject(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt), getColumnSqlType(paramInt), getColumnPrecision(paramInt), getColumnScale(paramInt), !getRsetType().isScrollable(), getColumnTypeDescriptor(paramInt), paramClass, this.stmt.conn.getTypeMap(), (Statement) this.stmt);
    }

    private OffsetTime getOffsetTime(int paramInt) throws SQLException {
        Row row = getCurrentRow();
        int i = getColumnLength(row, paramInt);
        if (i == 17) {
            TimeZone timeZone = this.typeConverter.toTimeZoneFromBytes((byte[]) getRowChunk(row, paramInt), getColumnOffset(row, paramInt), i);
            ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(TimeZone.getDefault().getRawOffset() / 1000);
            return OffsetTime.of(getTimestamp(paramInt).toLocalDateTime().toLocalTime(), zoneOffset).withOffsetSameInstant(ZoneOffset.ofTotalSeconds(timeZone.getRawOffset() / 1000));
        }
        return OffsetTime.of(getTime(paramInt).toLocalTime(), ZoneOffset.UTC);
    }

    private OffsetDateTime getOffsetDateTime(int paramInt) throws SQLException {
        Row row = getCurrentRow();
        int i = getColumnLength(row, paramInt);
        if (i == 17) {
            TimeZone timeZone = this.typeConverter.toTimeZoneFromBytes((byte[]) getRowChunk(row, paramInt), getColumnOffset(row, paramInt), i);
            ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(TimeZone.getDefault().getRawOffset() / 1000);
            return OffsetDateTime.of(getTimestamp(paramInt).toLocalDateTime(), zoneOffset).withOffsetSameInstant(ZoneOffset.ofTotalSeconds(timeZone.getRawOffset() / 1000));
        }
        return OffsetDateTime.of(getTimestamp(paramInt).toLocalDateTime(), ZoneOffset.UTC);
    }

    public int getPreparedFetchCnt() {
        return this.preparedFetchCnt;
    }

    public Ref getRef(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? null : this.typeConverter.toRef(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt));
    }

    public Ref getRef(String paramString) throws SQLException {
        return getRef(findColumn(paramString));
    }

    public synchronized int getRow() throws SQLException {
        return (this.rowsFetchedCnt == 0 || this.currentRowIndex < 0 || this.currentRowIndex >= this.rowsFetchedCnt) ? 0 : (this.currentRowIndex + 1);
    }

    public byte[] getRowChunk(int paramInt) {
        if (this.rowChunk == null || this.rowChunk.length < paramInt)
            this.rowChunk = new byte[paramInt];
        return this.rowChunk;
    }

    private Object getRowChunk(Row paramRow, int paramInt) {
        return paramRow.getRowChunk(paramInt + this.beginColumnIndex);
    }

    public RowId getRowId(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return (RowId) (setLastColumnIsNull(row, paramInt) ? null : this.typeConverter.toRowId(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt)));
    }

    public RowId getRowId(String paramString) throws SQLException {
        return getRowId(findColumn(paramString));
    }

    public synchronized short getShort(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? 0 : this.typeConverter.toShort(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt));
    }

    public short getShort(String paramString) throws SQLException {
        return getShort(findColumn(paramString));
    }

    public SQLXML getSQLXML(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? null : this.typeConverter.toSQLXML(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt), !this.stmt.getRealRsetType().isScrollable());
    }

    public SQLXML getSQLXML(String paramString) throws SQLException {
        return getSQLXML(findColumn(paramString));
    }

    public Statement getStatement() throws SQLException {
        return (Statement) this.stmt;
    }

    public synchronized String getString(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        if (setLastColumnIsNull(row, paramInt))
            return null;
        Object object = getRowChunk(row, paramInt);
        int i = getColumnOffset(row, paramInt);
        int j = getColumnLength(row, paramInt);
        int k = getColumnDataType(paramInt);
        int m = getColumnPrecision(paramInt);
        int n = getColumnScale(paramInt);
        boolean bool = !getRsetType().isScrollable() ? true : false;
        TbTypeDescriptor tbTypeDescriptor = getColumnTypeDescriptor(paramInt);
        if (k == 32 && tbTypeDescriptor != null && "00000000000000000000000000000001".equals(tbTypeDescriptor.getOID())) {
            Object object1 = this.typeConverter.toObject(object, i, j, k, DataType.getSqlType(k), m, n, bool, tbTypeDescriptor, TbSQLXML.class, null, (Statement) this.stmt);
            if (object1 instanceof TbSQLXML)
                return ((TbSQLXML) object1).getString();
        }
        return this.typeConverter.toString(object, i, j, k, m, n, bool);
    }

    public String getString(String paramString) throws SQLException {
        return getString(findColumn(paramString));
    }

    public synchronized Time getTime(int paramInt) throws SQLException {
        return getTimeInternal(paramInt);
    }

    public Time getTime(int paramInt, Calendar paramCalendar) throws SQLException {
        Time time = getTimeInternal(paramInt);
        if (paramCalendar != null) {
            paramCalendar.setTime(time);
            time = new Time(paramCalendar.getTimeInMillis());
        }
        return time;
    }

    public Time getTime(String paramString) throws SQLException {
        return getTime(findColumn(paramString));
    }

    public Time getTime(String paramString, Calendar paramCalendar) throws SQLException {
        return getTime(findColumn(paramString), paramCalendar);
    }

    private Time getTimeInternal(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? null : this.typeConverter.toTime(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt));
    }

    public synchronized Timestamp getTimestamp(int paramInt) throws SQLException {
        return getTimestampInternal(paramInt);
    }

    public Timestamp getTimestamp(int paramInt, Calendar paramCalendar) throws SQLException {
        return (paramCalendar == null) ? getTimestampInternal(paramInt) : getTimestampInternal(paramInt, paramCalendar);
    }

    public Timestamp getTimestamp(String paramString) throws SQLException {
        return getTimestamp(findColumn(paramString));
    }

    public Timestamp getTimestamp(String paramString, Calendar paramCalendar) throws SQLException {
        return getTimestamp(findColumn(paramString), paramCalendar);
    }

    private Timestamp getTimestampInternal(int paramInt) throws SQLException {
        return getTimestampInternal(paramInt, (Calendar) null);
    }

    private Timestamp getTimestampInternal(int paramInt, Calendar paramCalendar) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? null : this.typeConverter.toTimestamp(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt), paramCalendar);
    }

    public TbTimestamp getTbTimestamp(int paramInt) throws SQLException {
        return getTbTimestampInternal(paramInt);
    }

    private TbTimestamp getTbTimestampInternal(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? null : this.typeConverter.toTbTimestamp(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt));
    }

    @Deprecated
    public synchronized InputStream getUnicodeStream(int paramInt) throws SQLException {
        checkRsetAndConnClosed();
        checkColumnIndex(paramInt);
        Row row = getCurrentRow();
        return setLastColumnIsNull(row, paramInt) ? null : this.typeConverter.toUnicodeStream(getRowChunk(row, paramInt), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt), !getRsetType().isScrollable());
    }

    @Deprecated
    public InputStream getUnicodeStream(String paramString) throws SQLException {
        return getUnicodeStream(findColumn(paramString));
    }

    public URL getURL(int paramInt) throws SQLException {
        throw TbError.newSQLException(-90201);
    }

    public URL getURL(String paramString) throws SQLException {
        return getURL(findColumn(paramString));
    }

    protected boolean hasNoResultReturned() throws SQLException {
        return (this.rowsFetchedCnt > 0) ? false : this.fetchComplete;
    }

    public void insertRow() throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public synchronized boolean isAfterLast() throws SQLException {
        return (this.stmt.getMaxRows() != 0 && this.currentRowIndex + 1 > this.stmt.getMaxRows()) ? true : ((!hasNoResultReturned() && this.fetchComplete && this.currentRowIndex + 1 > this.rowsFetchedCnt));
    }

    public synchronized boolean isBeforeFirst() throws SQLException {
        return (!hasNoResultReturned() && this.currentRowIndex < 0);
    }

    public synchronized boolean isFirst() throws SQLException {
        return (!hasNoResultReturned() && this.currentRowIndex == 0);
    }

    public boolean isClosed() throws SQLException {
        return this.isRsetClosed;
    }

    public boolean isFetchComplete() {
        return this.fetchComplete;
    }

    private void makeColumnMetaArray(int paramInt) {
        this.mapDateToTimestamp = this.stmt.conn.getMapDateToTimestamp();
        this.cols = new Column[paramInt];
        for (byte b = 0; b < paramInt; b++)
            this.cols[b] = new Column(this.mapDateToTimestamp);
    }

    public void moveToCurrentRow() throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void moveToInsertRow() throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public synchronized boolean next() throws SQLException {
        if (this.csrID == -1)
            throw TbError.newSQLException(-90607);
        if (this.isRsetClosed)
            throw TbError.newSQLException(-90646);
        if (this.stmt.conn == null || this.stmt.conn.isClosed())
            throw TbError.newSQLException(-90603);
        if (this.isSvrCsrClosed || this.csrID == -2)
            return false;
        if (this.currentRowIndex < 0) {
            this.currentRowIndex = 0;
        } else {
            this.currentRowIndex++;
        }
        if (this.stmt.getMaxRows() != 0 && this.currentRowIndex >= this.stmt.getMaxRows()) {
            closeCursor();
            return false;
        }
        while (this.currentRowIndex >= this.rowsFetchedCnt) {
            if (this.fetchComplete)
                return false;
            fetchRowsChunk();
        }
        return true;
    }

    protected abstract void recover() throws SQLException;

    public void refreshRow() throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    protected abstract void removeCurrentRow() throws SQLException;

    protected void reset() {
        super.reset();
        if (this.columnNameMap != null) {
            this.columnNameMap.clear();
            this.columnNameMap = null;
        }
        this.stmt = null;
        this.csrID = -1;
        this.lastColumnWasNull = false;
        this.typeConverter = null;
        this.cols = null;
        this.rowChunk = null;
        this.fetchComplete = false;
        this.currentRowIndex = -1;
        this.rowsFetchedCnt = 0;
        this.fetchDirection = 1000;
        this.fetchSize = 50;
        this.isRsetClosed = false;
        this.isRsetComplete = false;
        this.closeStatementOnClose = false;
        this.foECode = 0;
    }

    public boolean rowDeleted() throws SQLException {
        return false;
    }

    public boolean rowInserted() throws SQLException {
        return false;
    }

    public boolean rowUpdated() throws SQLException {
        return false;
    }

    protected boolean isFoCsrEnabled() {
        return this.foCsrEnabled;
    }

    public void setFOECode(int paramInt) {
        this.foECode = paramInt;
    }

    public synchronized void setFetchCompleted(int paramInt) {
        this.fetchComplete = TbCommon.getBitmapAt(0, paramInt);
    }

    public synchronized void setFetchDirection(int paramInt) throws SQLException {
        switch (paramInt) {
            case 1000:
            case 1002:
                this.fetchDirection = 1000;
                return;
            case 1001:
                this.fetchDirection = 1001;
                return;
        }
        throw TbError.newSQLException(-90608);
    }

    public synchronized void setFetchSize(int paramInt) throws SQLException {
        if (paramInt == 0) {
            this.fetchSize = 50;
        } else if (paramInt > 0) {
            this.fetchSize = paramInt;
        } else {
            throw TbError.newSQLException(-590735);
        }
    }

    protected boolean setLastColumnIsNull(Row paramRow, int paramInt) throws SQLException {
        return this.lastColumnWasNull = paramRow.isNull(paramInt + this.beginColumnIndex);
    }

    public void setPreparedFetchCnt(int paramInt) {
        this.preparedFetchCnt = paramInt;
    }

    protected void setSaveCRCEnabled(boolean paramBoolean) {
        this.foCsrEnabled = paramBoolean;
    }

    public void updateArray(int paramInt, Array paramArray) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateArray(String paramString, Array paramArray) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateAsciiStream(int paramInt, InputStream paramInputStream) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateAsciiStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateAsciiStream(String paramString, InputStream paramInputStream) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateAsciiStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateAsciiStream(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateBigDecimal(int paramInt, BigDecimal paramBigDecimal) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateBigDecimal(String paramString, BigDecimal paramBigDecimal) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateBinaryStream(int paramInt, InputStream paramInputStream) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateBinaryStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateBinaryStream(String paramString, InputStream paramInputStream) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateBinaryStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateBinaryStream(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateBlob(int paramInt, Blob paramBlob) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateBlob(int paramInt, InputStream paramInputStream) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateBlob(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateBlob(String paramString, Blob paramBlob) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateBlob(String paramString, InputStream paramInputStream) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateBlob(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateBoolean(int paramInt, boolean paramBoolean) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateBoolean(String paramString, boolean paramBoolean) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateByte(int paramInt, byte paramByte) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateByte(String paramString, byte paramByte) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateBytes(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateBytes(String paramString, byte[] paramArrayOfbyte) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateRAW(int paramInt, TbRAW paramTbRAW) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateRAW(String paramString, TbRAW paramTbRAW) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateCharacterStream(int paramInt, Reader paramReader) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateCharacterStream(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateCharacterStream(String paramString, Reader paramReader) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateCharacterStream(String paramString, Reader paramReader, int paramInt) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateClob(int paramInt, Clob paramClob) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateClob(int paramInt, Reader paramReader) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateClob(String paramString, Clob paramClob) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateClob(String paramString, Reader paramReader) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateDate(int paramInt, Date paramDate) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateDate(String paramString, Date paramDate) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateDouble(int paramInt, double paramDouble) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateDouble(String paramString, double paramDouble) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateFloat(int paramInt, float paramFloat) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateFloat(String paramString, float paramFloat) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateInt(int paramInt1, int paramInt2) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateInt(String paramString, int paramInt) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateLong(int paramInt, long paramLong) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateLong(String paramString, long paramLong) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateNCharacterStream(int paramInt, Reader paramReader) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateNCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateNCharacterStream(String paramString, Reader paramReader) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateNCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateNClob(int paramInt, NClob paramNClob) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateNClob(int paramInt, Reader paramReader) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateNClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateNClob(String paramString, NClob paramNClob) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateNClob(String paramString, Reader paramReader) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateNClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateNString(int paramInt, String paramString) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateNString(String paramString1, String paramString2) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateNull(int paramInt) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateNull(String paramString) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateObject(int paramInt, Object paramObject) throws SQLException {
        updateObject(paramInt, paramObject, 0);
    }

    public void updateObject(int paramInt1, Object paramObject, int paramInt2) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateObject(String paramString, Object paramObject) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateObject(String paramString, Object paramObject, int paramInt) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateRef(int paramInt, Ref paramRef) throws SQLException {
        throw TbError.newSQLException(-90201);
    }

    public void updateRef(String paramString, Ref paramRef) throws SQLException {
        throw TbError.newSQLException(-90201);
    }

    public void updateRow() throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateRowId(int paramInt, RowId paramRowId) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateRowId(String paramString, RowId paramRowId) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateShort(int paramInt, short paramShort) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateShort(String paramString, short paramShort) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateSQLXML(int paramInt, SQLXML paramSQLXML) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateSQLXML(String paramString, SQLXML paramSQLXML) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateString(int paramInt, String paramString) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateString(String paramString1, String paramString2) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateTime(int paramInt, Time paramTime) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateTime(String paramString, Time paramTime) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateTimestamp(int paramInt, Timestamp paramTimestamp) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateTimestamp(String paramString, Timestamp paramTimestamp) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateTbTimestamp(int paramInt, TbTimestamp paramTbTimestamp) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public void updateTbTimestamp(String paramString, TbTimestamp paramTbTimestamp) throws SQLException {
        throw TbError.newSQLException(-90621);
    }

    public synchronized boolean wasNull() throws SQLException {
        return this.lastColumnWasNull;
    }

    static {
        for (byte b = 0; b < '\u0100'; b++) {
            long l = (b << 24);
            for (byte b1 = 0; b1 < 8; b1++) {
                if ((l & 0x80000000L) > 0L) {
                    l = l << 1L ^ 0x4C11DB7L;
                } else {
                    l <<= 1L;
                }
            }
            CRC_TBL[b] = l;
        }
    }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\driver\TbResultSetBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */