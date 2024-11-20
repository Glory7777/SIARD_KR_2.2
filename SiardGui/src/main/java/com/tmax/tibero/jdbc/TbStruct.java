package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.data.*;
import com.tmax.tibero.jdbc.data.binder.Binder;
import com.tmax.tibero.jdbc.data.binder.StaticBinder;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;

public class TbStruct implements Struct {
    private TbStructDescriptor descriptor;

    private TbConnection conn;

    private Object[] attributes;

    private Params subParams;

    public TbStruct(TbStructDescriptor paramTbStructDescriptor, Connection paramConnection, Object[] paramArrayOfObject) throws SQLException {
        if (paramTbStructDescriptor == null)
            throw TbError.newSQLException(-90608, "desc=null");
        if (paramConnection == null)
            throw TbError.newSQLException(-90608, "conn=null");
        this.descriptor = paramTbStructDescriptor;
        this.conn = (TbConnection) paramConnection;
        this.attributes = paramArrayOfObject;
        int i = paramTbStructDescriptor.getNumOfFields();
        int[] arrayOfInt = paramTbStructDescriptor.getAttributeTypes();
        this.subParams = new Params();
        initSubParams(i, arrayOfInt, paramArrayOfObject);
    }

    private void initSubParams(int paramInt, int[] paramArrayOfint, Object[] paramArrayOfObject) throws SQLException {
        byte b1 = (byte) ((paramArrayOfObject == null) ? 0 : paramArrayOfObject.length);
        this.subParams.attributeBinders = new Binder[paramInt];
        this.subParams.bindData = new BindData(paramInt);
        for (byte b2 = 0; b2 < b1; b2++) {
            if (paramArrayOfObject[b2] == null) {
                this.subParams.attributeBinders[b2] = StaticBinder.getNullBinder();
            } else {
                String str;
                switch (paramArrayOfint[b2]) {
                    case 1:
                        if (this.subParams.paramBigDecimal == null)
                            this.subParams.paramBigDecimal = new BigDecimal[paramInt];
                        this.subParams.attributeBinders[b2] = StaticBinder.getBigDecimalBinder();
                        if (paramArrayOfObject[b2] instanceof Short) {
                            this.subParams.paramBigDecimal[b2] = new BigDecimal(((Short) paramArrayOfObject[b2]).intValue());
                        } else if (paramArrayOfObject[b2] instanceof Integer) {
                            this.subParams.paramBigDecimal[b2] = new BigDecimal(((Integer) paramArrayOfObject[b2]).intValue());
                        } else if (paramArrayOfObject[b2] instanceof Long) {
                            this.subParams.paramBigDecimal[b2] = new BigDecimal(((Long) paramArrayOfObject[b2]).longValue());
                        } else if (paramArrayOfObject[b2] instanceof BigInteger) {
                            this.subParams.paramBigDecimal[b2] = new BigDecimal((BigInteger) paramArrayOfObject[b2]);
                        } else if (paramArrayOfObject[b2] instanceof BigDecimal) {
                            this.subParams.paramBigDecimal[b2] = (BigDecimal) paramArrayOfObject[b2];
                        } else if (paramArrayOfObject[b2] instanceof Float) {
                            this.subParams.paramBigDecimal[b2] = new BigDecimal(((Float) paramArrayOfObject[b2]).floatValue());
                        } else if (paramArrayOfObject[b2] instanceof Double) {
                            this.subParams.paramBigDecimal[b2] = new BigDecimal(((Double) paramArrayOfObject[b2]).doubleValue());
                        } else if (paramArrayOfObject[b2] instanceof String) {
                            this.subParams.paramBigDecimal[b2] = new BigDecimal((String) paramArrayOfObject[b2]);
                        } else if (paramArrayOfObject[b2] instanceof Boolean) {
                            this.subParams.paramBigDecimal[b2] = new BigDecimal(((Boolean) paramArrayOfObject[b2]).booleanValue() ? 1.0D : 0.0D);
                        } else {
                            String str1 = "elementType=" + DataType.getDBTypeName(paramArrayOfint[b2]) + ",attribute=" + paramArrayOfObject[b2];
                            throw TbError.newSQLException(-90651, str1);
                        }
                        this.subParams.bindData.setINParam(b2, paramArrayOfint[b2], 0);
                        break;
                    case 2:
                    case 3:
                    case 10:
                        if (this.subParams.paramString == null)
                            this.subParams.paramString = new String[paramInt];
                        this.subParams.attributeBinders[b2] = StaticBinder.getStringBinder();
                        if (paramArrayOfObject[b2] instanceof Boolean) {
                            this.subParams.paramString[b2] = ((Boolean) paramArrayOfObject[b2]).booleanValue() ? "1" : "0";
                        } else if (paramArrayOfObject[b2] == null) {
                            this.subParams.paramString[b2] = null;
                        } else if (paramArrayOfObject[b2] instanceof Reader) {
                            int i = this.conn.getMaxDFRCharCount() + 1;
                            char[] arrayOfChar = new char[i];
                            int j = 0;
                            try {
                                j = ((Reader) paramArrayOfObject[b2]).read(arrayOfChar, 0, i);
                            } catch (IOException iOException) {
                                throw TbError.newSQLException(-90202, iOException.getMessage());
                            }
                            if (j < 0) {
                                this.subParams.attributeBinders[b2] = StaticBinder.getNullBinder();
                                break;
                            }
                            if (j > this.conn.getMaxDFRCharCount())
                                throw TbError.newSQLException(-90201);
                            this.subParams.paramString[b2] = new String(arrayOfChar, 0, j);
                        } else if (paramArrayOfObject[b2] instanceof InputStream) {
                            char c = '\uFFFD';
                            byte[] arrayOfByte1 = new byte[c];
                            int i = 0;
                            if (this.subParams.paramBytes == null)
                                this.subParams.paramBytes = new byte[paramInt][];
                            try {
                                i = ((InputStream) paramArrayOfObject[b2]).read(arrayOfByte1, 0, c);
                            } catch (IOException iOException) {
                                throw TbError.newSQLException(-90202, iOException.getMessage());
                            }
                            if (i < 0) {
                                this.subParams.attributeBinders[b2] = StaticBinder.getNullBinder();
                                break;
                            }
                            if (i > 65532)
                                throw TbError.newSQLException(-90201);
                            byte[] arrayOfByte2 = new byte[i];
                            System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, i);
                            this.subParams.attributeBinders[b2] = StaticBinder.getBytesBinder();
                            this.subParams.paramBytes[b2] = arrayOfByte2;
                        } else {
                            this.subParams.paramString[b2] = paramArrayOfObject[b2].toString();
                        }
                        this.subParams.bindData.setINParam(b2, paramArrayOfint[b2], 0);
                        break;
                    case 18:
                    case 19:
                        if (this.subParams.paramString == null)
                            this.subParams.paramString = new String[paramInt];
                        this.subParams.attributeBinders[b2] = StaticBinder.getNStringBinder();
                        if (paramArrayOfObject[b2] instanceof Boolean) {
                            this.subParams.paramString[b2] = ((Boolean) paramArrayOfObject[b2]).booleanValue() ? "1" : "0";
                        } else if (paramArrayOfObject[b2] == null) {
                            this.subParams.paramString[b2] = null;
                        } else {
                            this.subParams.paramString[b2] = paramArrayOfObject[b2].toString();
                        }
                        this.subParams.bindData.setINParam(b2, paramArrayOfint[b2], 0);
                        break;
                    case 4:
                        if (this.subParams.paramBytes == null)
                            this.subParams.paramBytes = new byte[paramInt][];
                        this.subParams.attributeBinders[b2] = StaticBinder.getBytesBinder();
                        if (paramArrayOfObject[b2] instanceof byte[]) {
                            this.subParams.paramBytes[b2] = (byte[]) paramArrayOfObject[b2];
                        } else if (paramArrayOfObject[b2] instanceof InputStream) {
                            char c = '\uFFFD';
                            byte[] arrayOfByte1 = new byte[c];
                            int i = 0;
                            try {
                                i = ((InputStream) paramArrayOfObject[b2]).read(arrayOfByte1, 0, c);
                            } catch (IOException iOException) {
                                throw TbError.newSQLException(-90202, iOException.getMessage());
                            }
                            if (i < 0) {
                                this.subParams.attributeBinders[b2] = StaticBinder.getNullBinder();
                                break;
                            }
                            if (i > 65532)
                                throw TbError.newSQLException(-90201);
                            byte[] arrayOfByte2 = new byte[i];
                            System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, i);
                            this.subParams.attributeBinders[b2] = StaticBinder.getBytesBinder();
                            this.subParams.paramBytes[b2] = arrayOfByte2;
                        } else {
                            String str1 = "elementType=" + DataType.getDBTypeName(paramArrayOfint[b2]) + ",attribute=" + paramArrayOfObject[b2];
                            throw TbError.newSQLException(-90651, str1);
                        }
                        this.subParams.bindData.setINParam(b2, paramArrayOfint[b2], 0);
                        break;
                    case 5:
                        if (paramArrayOfObject[b2] instanceof TbTimestampTZ) {
                            if (this.subParams.paramCalendar == null)
                                this.subParams.paramCalendar = new Calendar[paramInt];
                            Calendar calendar = Calendar.getInstance(((TbTimestampTZ) paramArrayOfObject[b2]).getTimeZone());
                            calendar.setTime((Date) paramArrayOfObject[b2]);
                            this.subParams.paramCalendar[b2] = calendar;
                            this.subParams.attributeBinders[b2] = StaticBinder.getDateBinder();
                        } else if (paramArrayOfObject[b2] instanceof TbTimestamp) {
                            if (this.subParams.paramTbDate == null)
                                this.subParams.paramTbDate = new TbDate[paramInt];
                            TbTimestamp tbTimestamp = (TbTimestamp) paramArrayOfObject[b2];
                            this.subParams.paramTbDate[b2] = new TbDate(tbTimestamp.getYear(), tbTimestamp.getMonth(), tbTimestamp.getDayOfMonth(), tbTimestamp.getHourOfDay(), tbTimestamp.getMinutes(), tbTimestamp.getSeconds());
                            this.subParams.attributeBinders[b2] = StaticBinder.getTbDateBinder();
                        } else if (paramArrayOfObject[b2] instanceof TbDate) {
                            if (this.subParams.paramTbDate == null)
                                this.subParams.paramTbDate = new TbDate[paramInt];
                            this.subParams.paramTbDate[b2] = (TbDate) paramArrayOfObject[b2];
                            this.subParams.attributeBinders[b2] = StaticBinder.getTbDateBinder();
                        } else if (paramArrayOfObject[b2] instanceof Date) {
                            if (this.subParams.paramCalendar == null)
                                this.subParams.paramCalendar = new Calendar[paramInt];
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime((Date) paramArrayOfObject[b2]);
                            this.subParams.paramCalendar[b2] = calendar;
                            this.subParams.attributeBinders[b2] = StaticBinder.getDateBinder();
                        } else if (paramArrayOfObject[b2] instanceof Timestamp) {
                            if (this.subParams.paramCalendar == null)
                                this.subParams.paramCalendar = new Calendar[paramInt];
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime((Timestamp) paramArrayOfObject[b2]);
                            this.subParams.paramCalendar[b2] = calendar;
                            this.subParams.attributeBinders[b2] = StaticBinder.getDateBinder();
                        } else if (paramArrayOfObject[b2] instanceof String) {
                            if (this.subParams.paramCalendar == null)
                                this.subParams.paramCalendar = new Calendar[paramInt];
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(Date.valueOf((String) paramArrayOfObject[b2]));
                            this.subParams.paramCalendar[b2] = calendar;
                            this.subParams.attributeBinders[b2] = StaticBinder.getDateBinder();
                        } else {
                            String str1 = "elementType=" + DataType.getDBTypeName(paramArrayOfint[b2]) + ",attribute=" + paramArrayOfObject[b2];
                            throw TbError.newSQLException(-90651, str1);
                        }
                        this.subParams.bindData.setINParam(b2, paramArrayOfint[b2], 0);
                        break;
                    case 6:
                        if (paramArrayOfObject[b2] instanceof Time) {
                            if (this.subParams.paramCalendar == null)
                                this.subParams.paramCalendar = new Calendar[paramInt];
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime((Time) paramArrayOfObject[b2]);
                            this.subParams.paramCalendar[b2] = calendar;
                            this.subParams.attributeBinders[b2] = StaticBinder.getTimeBinder();
                        } else if (paramArrayOfObject[b2] instanceof String) {
                            if (this.subParams.paramCalendar == null)
                                this.subParams.paramCalendar = new Calendar[paramInt];
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(Time.valueOf((String) paramArrayOfObject[b2]));
                            this.subParams.paramCalendar[b2] = calendar;
                            this.subParams.attributeBinders[b2] = StaticBinder.getTimeBinder();
                        } else {
                            String str1 = "elementType=" + DataType.getDBTypeName(paramArrayOfint[b2]) + ",attribute=" + paramArrayOfObject[b2];
                            throw TbError.newSQLException(-90651, str1);
                        }
                        this.subParams.bindData.setINParam(b2, paramArrayOfint[b2], 0);
                        break;
                    case 7:
                        if (paramArrayOfObject[b2] instanceof TbTimestampTZ) {
                            if (this.subParams.paramTimestamp == null)
                                this.subParams.paramTimestamp = new Timestamp[paramInt];
                            this.subParams.paramTimestamp[b2] = (Timestamp) paramArrayOfObject[b2];
                            this.subParams.attributeBinders[b2] = StaticBinder.getTimestampBinder();
                        } else if (paramArrayOfObject[b2] instanceof TbTimestamp) {
                            if (this.subParams.paramTbTimestamp == null)
                                this.subParams.paramTbTimestamp = new TbTimestamp[paramInt];
                            this.subParams.paramTbTimestamp[b2] = (TbTimestamp) paramArrayOfObject[b2];
                            this.subParams.attributeBinders[b2] = StaticBinder.getTbTimestampBinder();
                        } else if (paramArrayOfObject[b2] instanceof TbDate) {
                            if (this.subParams.paramTbTimestamp == null)
                                this.subParams.paramTbTimestamp = new TbTimestamp[paramInt];
                            TbDate tbDate = (TbDate) paramArrayOfObject[b2];
                            this.subParams.paramTbTimestamp[b2] = new TbTimestamp(tbDate.getYear(), tbDate.getMonth(), tbDate.getDayOfMonth(), tbDate.getHourOfDay(), tbDate.getMinutes(), tbDate.getSeconds(), 0);
                            this.subParams.attributeBinders[b2] = StaticBinder.getTbTimestampBinder();
                        } else if (paramArrayOfObject[b2] instanceof Timestamp) {
                            if (this.subParams.paramTimestamp == null)
                                this.subParams.paramTimestamp = new Timestamp[paramInt];
                            this.subParams.paramTimestamp[b2] = (Timestamp) paramArrayOfObject[b2];
                            this.subParams.attributeBinders[b2] = StaticBinder.getTimestampBinder();
                        } else if (paramArrayOfObject[b2] instanceof Date) {
                            if (this.subParams.paramCalendar == null)
                                this.subParams.paramCalendar = new Calendar[paramInt];
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime((Date) paramArrayOfObject[b2]);
                            this.subParams.paramCalendar[b2] = calendar;
                            this.subParams.attributeBinders[b2] = StaticBinder.getDateBinder();
                        } else if (paramArrayOfObject[b2] instanceof String) {
                            if (this.subParams.paramTimestamp == null)
                                this.subParams.paramTimestamp = new Timestamp[paramInt];
                            this.subParams.paramTimestamp[b2] = Timestamp.valueOf((String) paramArrayOfObject[b2]);
                            this.subParams.attributeBinders[b2] = StaticBinder.getTimestampBinder();
                        } else {
                            String str1 = "elementType=" + DataType.getDBTypeName(paramArrayOfint[b2]) + ",attribute=" + paramArrayOfObject[b2];
                            throw TbError.newSQLException(-90651, str1);
                        }
                        this.subParams.bindData.setINParam(b2, paramArrayOfint[b2], 0);
                        break;
                    case 12:
                    case 13:
                    case 20:
                        if (paramArrayOfObject[b2] instanceof TbLob) {
                            if (this.subParams.paramBytes == null)
                                this.subParams.paramBytes = new byte[paramInt][];
                            this.subParams.paramBytes[b2] = ((TbLob) paramArrayOfObject[b2]).getLocator();
                            this.subParams.attributeBinders[b2] = StaticBinder.getBytesBinder();
                        } else {
                            String str1 = "elementType=" + DataType.getDBTypeName(paramArrayOfint[b2]) + ",attribute=" + paramArrayOfObject;
                            throw TbError.newSQLException(-90651, str1);
                        }
                        this.subParams.bindData.setINParam(b2, paramArrayOfint[b2], 0);
                        break;
                    case 15:
                        if (this.subParams.paramBytes == null)
                            this.subParams.paramBytes = new byte[paramInt][];
                        this.subParams.attributeBinders[b2] = StaticBinder.getBytesBinder();
                        if (paramArrayOfObject[b2] instanceof TbRowId) {
                            this.subParams.paramBytes[b2] = ((TbRowId) paramArrayOfObject[b2]).getBytes();
                        } else {
                            String str1 = "elementType=" + DataType.getDBTypeName(paramArrayOfint[b2]) + ",attribute=" + paramArrayOfObject[b2];
                            throw TbError.newSQLException(-90651, str1);
                        }
                        this.subParams.bindData.setINParam(b2, paramArrayOfint[b2], 0);
                        break;
                    case 33:
                        if (this.subParams.paramBytes == null)
                            this.subParams.paramBytes = new byte[paramInt][];
                        this.subParams.attributeBinders[b2] = StaticBinder.getBytesBinder();
                        if (paramArrayOfObject[b2] instanceof TbRef) {
                            this.subParams.paramBytes[b2] = ((TbRef) paramArrayOfObject[b2]).getRawData();
                        } else {
                            String str1 = "elementType=" + DataType.getDBTypeName(paramArrayOfint[b2]) + ",attribute=" + paramArrayOfObject[b2];
                            throw TbError.newSQLException(-90651, str1);
                        }
                        this.subParams.bindData.setINParam(b2, paramArrayOfint[b2], 0);
                        break;
                    case 29:
                    case 30:
                    case 31:
                        if (this.subParams.paramArray == null)
                            this.subParams.paramArray = new Array[paramInt];
                        if (paramArrayOfObject[b2] instanceof Array) {
                            this.subParams.paramArray[b2] = (Array) paramArrayOfObject[b2];
                            this.subParams.attributeBinders[b2] = StaticBinder.getArrayInBinder();
                            this.subParams.getBindData().getBindItem(b2).setTypeDescriptor(((TbArray) paramArrayOfObject[b2]).getDescriptor());
                        } else if (paramArrayOfObject[b2] == null) {
                            this.subParams.paramArray[b2] = null;
                            this.subParams.attributeBinders[b2] = StaticBinder.getNullBinder();
                        } else {
                            String str1 = "elementType=" + DataType.getDBTypeName(paramArrayOfint[b2]) + ",attribute=" + paramArrayOfObject;
                            throw TbError.newSQLException(-90651, str1);
                        }
                        this.subParams.bindData.setINParam(b2, paramArrayOfint[b2], 0);
                        break;
                    case 28:
                    case 32:
                        if (this.subParams.paramStruct == null)
                            this.subParams.paramStruct = new Struct[paramInt];
                        if (paramArrayOfObject[b2] instanceof Struct) {
                            this.subParams.paramStruct[b2] = (Struct) paramArrayOfObject[b2];
                            this.subParams.attributeBinders[b2] = StaticBinder.getStructInBinder();
                            this.subParams.getBindData().getBindItem(b2).setTypeDescriptor(((TbStruct) paramArrayOfObject[b2]).getDescriptor());
                            break;
                        }
                        if (paramArrayOfObject[b2] instanceof SQLData) {
                            Struct struct = (Struct) toStruct(paramArrayOfObject[b2], (Connection) this.conn);
                            this.subParams.paramStruct[b2] = struct;
                            this.subParams.attributeBinders[b2] = StaticBinder.getStructInBinder();
                            this.subParams.getBindData().getBindItem(b2).setTypeDescriptor(((TbStruct) struct).getDescriptor());
                            break;
                        }
                        if (paramArrayOfObject[b2] == null) {
                            this.subParams.paramStruct[b2] = null;
                            this.subParams.attributeBinders[b2] = StaticBinder.getNullBinder();
                            break;
                        }
                        str = "elementType=" + DataType.getDBTypeName(paramArrayOfint[b2]) + ",attribute=" + paramArrayOfObject;
                        throw TbError.newSQLException(-90651, str);
                    default:
                        throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramArrayOfint[b2]));
                }
            }
        }
    }

    public TbStructDescriptor getDescriptor() throws SQLException {
        return this.descriptor;
    }

    public int getNumOfFields() {
        return this.descriptor.getNumOfFields();
    }

    public static Object toStruct(Object paramObject, Connection paramConnection) throws SQLException {
        TbStruct tbStruct = null;
        if (paramObject != null)
            if (paramObject instanceof TbStruct) {
                tbStruct = (TbStruct) paramObject;
            } else if (paramObject instanceof SQLData) {
                SQLData sQLData = (SQLData) paramObject;
                TbStructDescriptor tbStructDescriptor = TbStructDescriptor.createDescriptor(sQLData.getSQLTypeName(), paramConnection);
                SQLOutput sQLOutput = tbStructDescriptor.toSQLOutput();
                sQLData.writeSQL(sQLOutput);
                tbStruct = ((TbSQLOutput) sQLOutput).getStruct();
            } else {
                throw TbError.newSQLException(-90651, String.valueOf(paramObject));
            }
        return tbStruct;
    }

    public ParamContainer getSubParams() {
        return this.subParams;
    }

    public String getSQLTypeName() throws SQLException {
        return this.descriptor.getSQLTypeName();
    }

    public Object[] getAttributes() throws SQLException {
        return this.attributes;
    }

    public Object toClass(Class<?> paramClass, Map<String, Class<?>> paramMap) throws SQLException {
        if (paramClass == null)
            return this;
        Object object = null;
        try {
            object = paramClass.newInstance();
            if (object instanceof SQLData)
                ((SQLData) object).readSQL(this.descriptor.toSQLInput(this, paramMap), this.descriptor.getSQLTypeName());
        } catch (InstantiationException instantiationException) {
            throw TbError.newSQLException(-90651, instantiationException);
        } catch (IllegalAccessException illegalAccessException) {
            throw TbError.newSQLException(-90651, illegalAccessException);
        }
        return object;
    }

    public Object toClass(Map<String, Class<?>> paramMap) throws SQLException {
        Object object = this;
        Map<String, Class<?>> map = paramMap;
        if (map != null) {
            Class<?> clazz = this.descriptor.getClass(map);
            if (clazz != null)
                object = toClass(clazz, map);
        } else if (this.conn != null) {
            map = this.conn.getTypeMap();
            Class<?> clazz = this.descriptor.getClass(map);
            if (clazz != null)
                object = toClass(clazz, map);
        }
        return object;
    }

    public Object[] getAttributes(Map<String, Class<?>> paramMap) throws SQLException {
        Object[] arrayOfObject = new Object[this.attributes.length];
        if (this.attributes.length == 0)
            return null;
        for (byte b = 0; b < arrayOfObject.length; b++)
            arrayOfObject[b] = this.attributes[b];
        Map<String, Class<?>> map = paramMap;
        if (this.conn != null)
            for (byte b1 = 0; b1 < arrayOfObject.length; b1++) {
                if (arrayOfObject[b1] instanceof SQLData)
                    arrayOfObject[b1] = toStruct(arrayOfObject[b1], (Connection) this.conn);
            }
        if (map != null)
            for (byte b1 = 0; b1 < arrayOfObject.length; b1++) {
                if (arrayOfObject[b1] instanceof Struct) {
                    Class<?> clazz = ((TbStruct) arrayOfObject[b1]).getDescriptor().getClassWithExplicitMap(map);
                    if (clazz != null)
                        arrayOfObject[b1] = ((TbStruct) arrayOfObject[b1]).toClass(clazz, map);
                }
            }
        return arrayOfObject;
    }

    class Params implements ParamContainer {
        private Binder[] attributeBinders;

        private BindData bindData;

        private BigDecimal[] paramBigDecimal;

        private String[] paramString;

        private Timestamp[] paramTimestamp;

        private TbTimestampTZ[] paramTbTimestampTZ;

        private TbTimestamp[] paramTbTimestamp;

        private TbDate[] paramTbDate;

        private Calendar[] paramCalendar;

        private byte[][] paramBytes;

        private InputStream[] paramStream;

        private Reader[] paramReader;

        private Struct[] paramStruct;

        private Array[] paramArray;

        public BindData getBindData() {
            return this.bindData;
        }

        public Binder[][] getBinder() {
            return (Binder[][]) null;
        }

        public Binder getBinder(int param1Int1, int param1Int2) {
            return this.attributeBinders[param1Int2];
        }

        public int getParameterCnt() {
            return 1;
        }

        public byte getParamType(int param1Int1, int param1Int2) {
            return 0;
        }

        public byte[][] getParamTypes() {
            return (byte[][]) null;
        }

        public byte[] getParamTypesOfRow(int param1Int) {
            return null;
        }

        public Array getParamArray(int param1Int1, int param1Int2) {
            return this.paramArray[param1Int2];
        }

        public Array[] getParamArrayOfRow(int param1Int) {
            return null;
        }

        public BigDecimal getParamBigDecimal(int param1Int1, int param1Int2) {
            return this.paramBigDecimal[param1Int2];
        }

        public BigDecimal[] getParamBigDecimalOfRow(int param1Int) {
            return null;
        }

        public byte[] getParamBytes(int param1Int1, int param1Int2) {
            return this.paramBytes[param1Int2];
        }

        public byte[][] getParamBytesOfRow(int param1Int) {
            return (byte[][]) null;
        }

        public Calendar getParamCalendar(int param1Int1, int param1Int2) {
            return this.paramCalendar[param1Int2];
        }

        public Calendar[] getParamCalendarOfRow(int param1Int) {
            return null;
        }

        public double getParamDouble(int param1Int1, int param1Int2) {
            return 0.0D;
        }

        public double[] getParamDoubleOfRow(int param1Int) {
            return null;
        }

        public float getParamFloat(int param1Int1, int param1Int2) {
            return 0.0F;
        }

        public float[] getParamFloatOfRow(int param1Int) {
            return null;
        }

        public int getParamInt(int param1Int1, int param1Int2) {
            return 0;
        }

        public int[] getParamIntOfRow(int param1Int) {
            return null;
        }

        public long getParamLong(int param1Int1, int param1Int2) {
            return 0L;
        }

        public long[] getParamLongOfRow(int param1Int) {
            return null;
        }

        public Reader getParamReader(int param1Int1, int param1Int2) {
            return this.paramReader[param1Int2];
        }

        public Reader[] getParamReaderOfRow(int param1Int) {
            return null;
        }

        public InputStream getParamStream(int param1Int1, int param1Int2) {
            return this.paramStream[param1Int2];
        }

        public InputStream[] getParamStreamOfRow(int param1Int) {
            return null;
        }

        public String getParamString(int param1Int1, int param1Int2) {
            return this.paramString[param1Int2];
        }

        public String[] getParamStringOfRow(int param1Int) {
            return null;
        }

        public Struct getParamStruct(int param1Int1, int param1Int2) {
            return this.paramStruct[param1Int2];
        }

        public Struct[] getParamStructOfRow(int param1Int) {
            return null;
        }

        public TbDate getParamTbDate(int param1Int1, int param1Int2) {
            return this.paramTbDate[param1Int2];
        }

        public TbDate[] getParamTbDateOfRow(int param1Int) {
            return null;
        }

        public TbTimestamp getParamTbTimestamp(int param1Int1, int param1Int2) {
            return this.paramTbTimestamp[param1Int2];
        }

        public TbTimestamp[] getParamTbTimestampOfRow(int param1Int) {
            return null;
        }

        public TbTimestampTZ getParamTbTimestampTZ(int param1Int1, int param1Int2) {
            return this.paramTbTimestampTZ[param1Int2];
        }

        public TbTimestampTZ[] getParamTbTimestampTZOfRow(int param1Int) {
            return null;
        }

        public Timestamp getParamTimestamp(int param1Int1, int param1Int2) {
            return this.paramTimestamp[param1Int2];
        }

        public Timestamp[] getParamTimestampOfRow(int param1Int) {
            return null;
        }
    }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\TbStruct.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */