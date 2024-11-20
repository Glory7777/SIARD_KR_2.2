package ch.admin.bar.siard2.cmd;

import ch.admin.bar.dbexception.DatabaseExceptionHandlerHelper;
import ch.admin.bar.siard2.api.*;
import ch.admin.bar.siard2.api.generated.CategoryType;
import ch.admin.bar.siard2.api.generated.ReferentialActionType;
import ch.admin.bar.siard2.api.meta.*;
import ch.admin.bar.siard2.cmd.utils.ByteFormatter;
import ch.enterag.sqlparser.BaseSqlFactory;
import ch.enterag.sqlparser.SqlLiterals;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.datatype.PredefinedType;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.EU;
import ch.enterag.utils.ProgramInfo;
import ch.enterag.utils.background.Progress;
import ch.enterag.utils.jdbc.BaseDatabaseMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ch.admin.bar.dbexception.DatabaseExceptionHandlerHelper.doHandleSqlException;

public class MetaDataFromDb extends MetaDataBase {
    private static final Logger LOG = LoggerFactory.getLogger(MetaDataFromDb.class);

    static final Pattern _patARRAY_CONSTRUCTOR = Pattern.compile("^\\s*(.*?)\\s+ARRAY\\s*\\[\\s*(\\d+)\\s*\\]$");
    private boolean _bMaxLobNeeded = false;
    private MetaColumn _mcMaxLob = null;
    private long _lMaxLobSize;
    private Progress _progress;
    private boolean _bViewsAsTables;
    private int _iTablesAnalyzed;
    private int _iTables;
    private int _iTablesPercent;

    private MetaDataFromDb(DatabaseMetaData dmd, MetaData md) throws SQLException {
        super(dmd, md);
        this._lMaxLobSize = -1L;
        this._progress = null;
        this._bViewsAsTables = false;
        this._iTablesAnalyzed = -1;
        this._iTables = -1;
        this._iTablesPercent = -1;
    }

    public static MetaDataFromDb newInstance(DatabaseMetaData dmd, MetaData md) throws SQLException {
        return new MetaDataFromDb(dmd, md);
    }

    public MetaColumn getMaxLobColumn() {
        return this._mcMaxLob;
    }

    private boolean checkMetaTable(MetaTable mt) {
        boolean bMetaDataOk = true;
        System.out.println("    Check Meta Table " + mt.getName());
        try {
            for (int iColumn = 0; bMetaDataOk && iColumn < mt.getMetaColumns(); iColumn++) {
                MetaColumn mc = mt.getMetaColumn(iColumn);
                System.out.println("      Check Meta Column " + mc.getName());
                String sTypeName = mc.getTypeName();
                String sTypeSchema = mc.getTypeSchema();
                if (sTypeName != null && sTypeSchema == null) {
                    System.err.println("Error in column " + mc.getName() + " of table " + mt.getName() + "!");
                    bMetaDataOk = false;
                }
                ((MetaColumnImpl) mc).getColumnType();
            }
            ((MetaTableImpl) mt).getTableType();
        } catch (Exception e) {
            System.err.println(EU.getExceptionMessage(e));
            bMetaDataOk = false;
        }
        return bMetaDataOk;
    }

    private boolean checkMetaView(MetaView mv) {
        boolean bMetaDataOk = true;
        System.out.println("    Check Meta View " + mv.getName());
        try {
            for (int iColumn = 0; bMetaDataOk && iColumn < mv.getMetaColumns(); iColumn++) {
                MetaColumn mc = mv.getMetaColumn(iColumn);
                System.out.println("      Check Meta Column " + mc.getName());
                String sTypeName = mc.getTypeName();
                String sTypeSchema = mc.getTypeSchema();
                if (sTypeName != null && sTypeSchema == null) {
                    System.err.println("Error in column " + mc.getName() + " of view " + mv.getName() + "!");
                    bMetaDataOk = false;
                }
                ((MetaColumnImpl) mc).getColumnType();
            }
            ((MetaViewImpl) mv).getViewType();
        } catch (Exception e) {
            System.err.println(EU.getExceptionMessage(e));
            bMetaDataOk = false;
        }
        return bMetaDataOk;
    }

    private boolean checkMetaType(MetaType mt) {
        boolean bMetaDataOk = true;
        System.out.println("    Check Meta Type " + mt.getName());
        try {
            for (int iAttribute = 0; bMetaDataOk && iAttribute < mt.getMetaAttributes(); iAttribute++) {
                MetaAttribute ma = mt.getMetaAttribute(iAttribute);
                System.out.println("      Check Meta Attribute " + ma.getName());
                String sTypeName = ma.getTypeName();
                String sTypeSchema = ma.getTypeSchema();
                if (sTypeName != null && sTypeSchema == null) {
                    System.err.println("Error in attribute " + ma.getName() + " of type " + mt.getName() + "!");
                    bMetaDataOk = false;
                }
                ((MetaAttributeImpl) ma).getAttributeType();
            }
            ((MetaTypeImpl) mt).getTypeType();
        } catch (Exception e) {
            System.err.println(EU.getExceptionMessage(e));
            bMetaDataOk = false;
        }
        return bMetaDataOk;
    }

    private boolean checkMetaRoutine(MetaRoutine mr) {
        boolean bMetaDataOk = true;
        System.out.println("    Check Meta Routine " + mr.getSpecificName());
        try {
            for (int iParameter = 0; bMetaDataOk && iParameter < mr.getMetaParameters(); iParameter++) {
                MetaParameter mp = mr.getMetaParameter(iParameter);
                System.out.println("      Check Meta Parameter " + mp.getName());
                String sTypeName = mp.getTypeName();
                String sTypeSchema = mp.getTypeSchema();
                if (sTypeName != null && sTypeSchema == null) {
                    System.err.println("Error in parameter " + mr.getName() + " of routine " + mr.getName() + "!");
                    bMetaDataOk = false;
                }
                ((MetaParameterImpl) mp).getParameterType();
            }
            ((MetaRoutineImpl) mr).getRoutineType();
        } catch (Exception e) {
            System.err.println(EU.getExceptionMessage(e));
            bMetaDataOk = false;
        }
        return bMetaDataOk;
    }

    private boolean checkMetaSchema(MetaSchema ms) {
        boolean bMetaDataOk = true;
        System.out.println("  Check Meta Schema " + ms.getName());
        try {
            if (ms.getSchema().getTables() == ms.getMetaTables()) {
                for (int iTable = 0; bMetaDataOk && iTable < ms.getMetaTables(); iTable++)
                    bMetaDataOk = checkMetaTable(ms.getMetaTable(iTable));
            } else {
                System.err.println("Invalid number of tables in schema " + ms.getName() + "!");
                bMetaDataOk = false;
            }
            for (int iView = 0; bMetaDataOk && iView < ms.getMetaViews(); iView++)
                bMetaDataOk = checkMetaView(ms.getMetaView(iView));
            for (int iType = 0; bMetaDataOk && iType < ms.getMetaTypes(); iType++)
                bMetaDataOk = checkMetaType(ms.getMetaType(iType));
            for (int iRoutine = 0; bMetaDataOk && iRoutine < ms.getMetaRoutines(); iRoutine++)
                bMetaDataOk = checkMetaRoutine(ms.getMetaRoutine(iRoutine));
            ((MetaSchemaImpl) ms).getSchemaType();
        } catch (Exception e) {
            System.err.println(EU.getExceptionMessage(e));
            bMetaDataOk = false;
        }
        return bMetaDataOk;
    }

    public boolean checkMetaData() {
        boolean bMetaDataOk = true;
        System.out.println("Check Meta Data");
        try {
            if (this._md.getArchive().getSchemas() == this._md.getMetaSchemas()) {
                for (int iSchema = 0; bMetaDataOk && iSchema < this._md.getMetaSchemas(); iSchema++)
                    bMetaDataOk = checkMetaSchema(this._md.getMetaSchema(iSchema));
                ((MetaDataImpl) this._md).getSiardArchive();
            } else {
                System.err.println("Invalid number of schema!");
                bMetaDataOk = false;
            }
        } catch (Exception e) {
            System.err.println(EU.getExceptionMessage(e));
            bMetaDataOk = false;
        }
        return bMetaDataOk;
    }


    private void incTablesAnalyzed() {
        this._iTablesAnalyzed++;
        if (this._progress != null && this._iTables > 0 && this._iTablesAnalyzed % this._iTablesPercent == 0) {
            int iPercent = 100 * this._iTablesAnalyzed / this._iTables;
            this._progress.notifyProgress(iPercent);
        }
    }


    private boolean cancelRequested() {
        if (this._progress != null && this._progress.cancelRequested()) {
            LOG.info("Cancel downloading of meta data because of request");
            return true;
        }
        return false;
    }


    private String getReferentialAction(int iReferentialAction) {
        ReferentialActionType rat = null;
        switch (iReferentialAction) {
            case 0:
                rat = ReferentialActionType.CASCADE;
                break;
            case 2:
                rat = ReferentialActionType.SET_NULL;
                break;
            case 4:
                rat = ReferentialActionType.SET_DEFAULT;
                break;
            case 1:
                rat = ReferentialActionType.RESTRICT;
                break;
            case 3:
                rat = ReferentialActionType.NO_ACTION;
                break;
        }
        return rat.value();
    }


    private void getAttributes(MetaType mt) throws IOException, SQLException {
        int iPosition = 0;
        CategoryType cat = mt.getCategoryType();
        ResultSet rs = this._dmd.getAttributes(null, ((BaseDatabaseMetaData) this._dmd)
                .toPattern(mt.getParentMetaSchema().getName()), ((BaseDatabaseMetaData) this._dmd)
                .toPattern(mt.getName()), "%");

        while (rs.next()) {
            iPosition++;
            String sTypeSchema = rs.getString("TYPE_SCHEM");
            if (!sTypeSchema.equals(mt.getParentMetaSchema().getName()))
                throw new IOException("Attribute with unexpected type schema found!");
            String sTypeName = rs.getString("TYPE_NAME");
            if (!sTypeName.equals(mt.getName())) throw new IOException("Attribute with unexpected type name found");
            String sAttributeName = rs.getString("ATTR_NAME");
            int iDataType = rs.getInt("DATA_TYPE");
            String sAttrTypeName = rs.getString("ATTR_TYPE_NAME");
            int iAttrSize = rs.getInt("ATTR_SIZE");
            int iDecimalDigits = rs.getInt("DECIMAL_DIGITS");
            MetaType mtAttr = null;
            if (iDataType == 2001 || iDataType == 2002)
                mtAttr = createType(sAttrTypeName, mt.getParentMetaSchema().getName(), iAttrSize, iDecimalDigits);
            if (cat == CategoryType.DISTINCT) {
                mt.setBase(sTypeName);
                if (iDataType != 2001 && iDataType != 2003 && iDataType != 2002)
                    mt.setBasePreType(iDataType, iAttrSize, iDecimalDigits);
                continue;
            }
            MetaAttribute ma = mt.createMetaAttribute(sAttributeName);
            if (iDataType != 2001 && iDataType != 2003 && iDataType != 2002) {
                if (iDataType == 1111) {
                    ma.setType(sAttrTypeName);
                } else {
                    ma.setPreType(iDataType, iAttrSize, iDecimalDigits);
                }
            } else if (iDataType == 2003) {
                Matcher m = _patARRAY_CONSTRUCTOR.matcher(sAttrTypeName);
                if (m.matches()) {
                    String sBaseType = m.group(1);

                    BaseSqlFactory bsf = new BaseSqlFactory();
                    DataType dt = bsf.newDataType();
                    dt.parse(sBaseType);
                    if (dt.getPredefinedType() != null) {
                        ma.setType(dt.format());
                    } else {
                        MetaType mtyBase = createType(sBaseType, sTypeSchema, -1, -1);


                        QualifiedId qiTypeBase = new QualifiedId(null, mtyBase.getParentMetaSchema().getName(), mtyBase.getName());
                        ma.setTypeName(qiTypeBase.getName());
                        ma.setTypeSchema(qiTypeBase.getSchema());
                    }

                    ma.setCardinality(Integer.parseInt(m.group(2)));
                } else {
                    throw new SQLException("Invalid ARRAY constructor for attribute " + ma.getName() + " of type " + mt.getName() + "!");
                }
            } else {
                ma.setTypeName(mtAttr.getName());
                ma.setTypeSchema(mtAttr.getParentMetaSchema().getName());
            }

            int iNullable = rs.getInt("NULLABLE");
            if (iNullable == 0) {
                ma.setNullable(false);
            } else if (iNullable == 1) {
                ma.setNullable(true);
            }
            String sAttributeDefault = rs.getString("ATTR_DEF");
            if (sAttributeDefault != null) ma.setDefaultValue(sAttributeDefault);
            String sRemarks = rs.getString("REMARKS");
            if (sRemarks != null) ma.setDescription(sRemarks);
            int iOrdinalPosition = rs.getInt("ORDINAL_POSITION");
            if (iOrdinalPosition != iPosition) throw new IOException("Invalid ordinal position of attribute!");

        }
        rs.close();
    }


    private MetaType createType(String sTypeName, String sDefaultSchema, int iPrecision, int iScale) throws IOException, SQLException {
        MetaType mt = null;
        try {
            QualifiedId qiType = new QualifiedId(sTypeName);
            String sTypeSchema = qiType.getSchema();
            if (sTypeSchema == null) {
                sTypeSchema = sDefaultSchema;
                qiType.setSchema(sTypeSchema);
                qiType.parseName(sTypeName);
            }
            Schema schema = this._md.getArchive().getSchema(qiType.getSchema());
            if (schema == null) schema = this._md.getArchive().createSchema(qiType.getSchema());
            MetaSchema ms = schema.getMetaSchema();
            mt = ms.getMetaType(qiType.getName());
            if (mt == null) {
                System.out.println("  Type: " + qiType.format());
                mt = ms.createMetaType(qiType.getName());
                ResultSet rs = this._dmd.getUDTs(null, ((BaseDatabaseMetaData) this._dmd)
                        .toPattern(qiType.getSchema()), ((BaseDatabaseMetaData) this._dmd)
                        .toPattern(qiType.getName()), new int[]{2001, 2002});

                rs.next();
                if (qiType.getName().equals(rs.getString("TYPE_NAME")) && qiType.getSchema()
                        .equals(rs.getString("TYPE_SCHEM"))) {
                    String sRemarks = rs.getString("REMARKS");
                    if (sRemarks != null) mt.setDescription(sRemarks);
                    BaseSqlFactory bsf = new BaseSqlFactory();
                    PredefinedType pt = bsf.newPredefinedType();
                    int iBaseType = rs.getInt("BASE_TYPE");
                    if (iBaseType != 0) {
                        mt.setCategory(CategoryType.DISTINCT.value());
                        pt.initialize(iBaseType, iPrecision, iScale);
                        mt.setBase(pt.format());
                    } else {
                        mt.setCategory(CategoryType.UDT.value());
                        getAttributes(mt);
                    }
                } else {
                    throw new SQLException("Invalid type meta data found!");
                }
                rs.close();
            }
        } catch (ParseException pe) {
            throw new SQLException("Type name " + sTypeName + " could not be parsed!", pe);
        }
        return mt;
    }


    private void getColumns(MetaView mv) throws IOException, SQLException {
        ResultSet rs = this._dmd.getColumns(null, ((BaseDatabaseMetaData) this._dmd)
                .toPattern(mv.getParentMetaSchema().getName()), ((BaseDatabaseMetaData) this._dmd)
                .toPattern(mv.getName()), "%");

        while (rs.next()) {
            String sTableSchema = rs.getString("TABLE_SCHEM");
            if (!sTableSchema.equals(mv.getParentMetaSchema().getName()))
                throw new IOException("Invalid view schema for column found!");
            String sTableName = rs.getString("TABLE_NAME");
            if (!sTableName.equals(mv.getName())) throw new IOException("Invalid view name for column found!");
            String sColumnName = rs.getString("COLUMN_NAME");
            MetaColumn mc = mv.createMetaColumn(sColumnName);
            getColumnData(rs, mc);
        }
        rs.close();
    }


    private void getProcedureParameters(MetaRoutine mr) throws IOException, SQLException {
        ResultSet rs = this._dmd.getProcedureColumns(null, ((BaseDatabaseMetaData) this._dmd)
                .toPattern(mr.getParentMetaSchema()
                        .getName()), ((BaseDatabaseMetaData) this._dmd)
                .toPattern(mr.getName()), "%");

        while (rs.next()) {
            String sProcedureSchema = rs.getString("PROCEDURE_SCHEM");
            if (!sProcedureSchema.equals(mr.getParentMetaSchema().getName()))
                throw new IOException("Invalid procedure parameter schema encountered!");
            String sProcedureName = rs.getString("PROCEDURE_NAME");
            if (!sProcedureName.equals(mr.getName()))
                throw new IOException("Invalid procedure parameter name encountered!");
            String sParameterName = rs.getString("COLUMN_NAME");
            int iColumnType = rs.getInt("COLUMN_TYPE");
            int iDataType = rs.getInt("DATA_TYPE");
            String sTypeName = rs.getString("TYPE_NAME");
            MetaType mt = null;
            long lPrecision = rs.getLong("PRECISION");
            int iScale = rs.getInt("SCALE");
            if (iDataType == 2001 || iDataType == 2002) {
                mt = createType(sTypeName, mr.getParentMetaSchema().getName(), (int) lPrecision, iScale);
            }
            String sRemarks = rs.getString("REMARKS");
            int iOrdinalPosition = rs.getInt("ORDINAL_POSITION");
            String sSpecificName = rs.getString("SPECIFIC_NAME");
            if (sSpecificName == null) sSpecificName = sProcedureName;

            if (sSpecificName.equals(mr.getSpecificName())) {
                if (iColumnType == 5 || iColumnType == 3) {
                    mr.setReturnType(sTypeName);
                    if (iDataType != 2001 && iDataType != 2003 && iDataType != 2002)
                        mr.setReturnPreType(iDataType, lPrecision, iScale);
                    continue;
                }
                MetaParameter mp = mr.createMetaParameter(sParameterName);
                if (iOrdinalPosition != mr.getMetaParameters())
                    throw new IOException("Invalid order of procedure columns!");
                switch (iColumnType) {
                    case 1:
                        mp.setMode("IN");
                        break;
                    case 4:
                        mp.setMode("OUT");
                        break;
                    case 2:
                        mp.setMode("INOUT");
                        break;
                }


                if (iDataType != 2001 && iDataType != 2003 && iDataType != 2002) {
                    mp.setPreType(iDataType, lPrecision, iScale);
                    mp.setTypeOriginal(sTypeName);
                } else if (iDataType == 2003) {
                    Matcher m = _patARRAY_CONSTRUCTOR.matcher(sTypeName);
                    if (m.matches()) {
                        String sBaseType = m.group(1);

                        BaseSqlFactory bsf = new BaseSqlFactory();
                        DataType dt = bsf.newDataType();
                        dt.parse(sBaseType);
                        if (dt.getPredefinedType() != null) {
                            mp.setType(dt.format());
                        } else {
                            MetaType mtyBase = createType(sBaseType, sProcedureSchema, -1, -1);


                            QualifiedId qiTypeBase = new QualifiedId(null, mtyBase.getParentMetaSchema().getName(), mtyBase.getName());
                            mp.setTypeName(qiTypeBase.getName());
                            mp.setTypeSchema(qiTypeBase.getSchema());
                        }

                        mp.setCardinality(Integer.parseInt(m.group(2)));
                    } else {
                        throw new SQLException("Invalid ARRAY constructor for parameter " + mp.getName() + " of routine " + mr.getName() + "!");
                    }
                } else {
                    mp.setTypeName(mt.getName());
                    mp.setTypeSchema(mt.getParentMetaSchema().getName());
                }

                if (sRemarks != null) mp.setDescription(sRemarks);

            }
        }
        rs.close();
    }


    private void getFunctionParameters(MetaRoutine mr) throws IOException, SQLException {
        ResultSet rs = this._dmd.getFunctionColumns(null, ((BaseDatabaseMetaData) this._dmd)
                .toPattern(mr.getParentMetaSchema()
                        .getName()), ((BaseDatabaseMetaData) this._dmd)
                .toPattern(mr.getName()), "%");

        while (rs.next()) {
            String sFunctionSchema = rs.getString("FUNCTION_SCHEM");
            if (!mr.getParentMetaSchema().getName().equals(sFunctionSchema))
                throw new IOException("Invalid function parameter schema encountered!");
            String sFunctionName = rs.getString("FUNCTION_NAME");
            if (!mr.getName().equals(sFunctionName))
                throw new IOException("Invalid function parameter name encountered!");
            String sParameterName = rs.getString("COLUMN_NAME");
            int iColumnType = rs.getInt("COLUMN_TYPE");
            int iDataType = rs.getInt("DATA_TYPE");
            String sTypeName = rs.getString("TYPE_NAME");
            long lPrecision = rs.getLong("PRECISION");
            int iScale = rs.getInt("SCALE");
            MetaType mt = null;
            if (iDataType == 2001 || iDataType == 2002)
                mt = createType(sTypeName, mr.getParentMetaSchema().getName(), (int) lPrecision, iScale);
            String sRemarks = rs.getString("REMARKS");
            int iOrdinalPosition = rs.getInt("ORDINAL_POSITION");
            String sSpecificName = rs.getString("SPECIFIC_NAME");
            if (sSpecificName == null) sSpecificName = sFunctionName;

            if (sSpecificName.equals(mr.getSpecificName())) {


                if (iColumnType == 5 || iColumnType == 3) {
                    mr.setReturnType(sTypeName);
                    if (iDataType != 2001 && iDataType != 2003 && iDataType != 2002)
                        mr.setReturnPreType(iDataType, lPrecision, iScale);
                    continue;
                }
                MetaParameter mp = mr.createMetaParameter(sParameterName);
                if (iOrdinalPosition != mr.getMetaParameters())
                    throw new IOException("Invalid order of function columns!");
                switch (iColumnType) {
                    case 1:
                        mp.setMode("IN");
                        break;
                    case 4:
                        mp.setMode("OUT");
                        break;
                    case 2:
                        mp.setMode("INOUT");
                        break;
                }


                if (iDataType != 2001 && iDataType != 2003 && iDataType != 2002) {
                    mp.setPreType(iDataType, lPrecision, iScale);
                    mp.setTypeOriginal(sTypeName);
                } else if (iDataType == 2003) {
                    Matcher m = _patARRAY_CONSTRUCTOR.matcher(sTypeName);
                    if (m.matches()) {
                        String sBaseType = m.group(1);

                        BaseSqlFactory bsf = new BaseSqlFactory();
                        DataType dt = bsf.newDataType();
                        dt.parse(sBaseType);
                        if (dt.getPredefinedType() != null) {
                            mp.setType(dt.format());
                        } else {
                            MetaType mtyBase = createType(sBaseType, sFunctionSchema, -1, -1);


                            QualifiedId qiTypeBase = new QualifiedId(null, mtyBase.getParentMetaSchema().getName(), mtyBase.getName());
                            mp.setTypeName(qiTypeBase.getName());
                            mp.setTypeSchema(qiTypeBase.getSchema());
                        }

                        mp.setCardinality(Integer.parseInt(m.group(2)));
                    } else {
                        throw new SQLException("Invalid ARRAY constructor for parameter " + mp.getName() + " of routine " + mr.getName() + "!");
                    }
                } else {
                    mp.setTypeName(mt.getName());
                    mp.setTypeSchema(mt.getParentMetaSchema().getName());
                }

                if (sRemarks != null) mp.setDescription(sRemarks);

            }
        }
        rs.close();
    }


    private void addReferences(MetaTable mt, String sForeignKeyName, Map<Integer, String> mapFkColumns, Map<String, String> mapPkColumns) throws IOException {
        if (sForeignKeyName != null) {

            MetaForeignKey mfk = mt.getMetaForeignKey(sForeignKeyName);
            for (int iColumn = 0; iColumn < mapFkColumns.size(); iColumn++) {
                String sFkColumnName = mapFkColumns.get(Integer.valueOf(iColumn + 1));
                mfk.addReference(sFkColumnName, mapPkColumns.get(sFkColumnName));
            }
            mapFkColumns.clear();
            mapPkColumns.clear();
        }
    }


    private void addColumns(MetaTable mt, String sUniqueKeyName, Map<Integer, String> mapUniqueColumns) throws IOException {
        if (sUniqueKeyName != null) {
            MetaUniqueKey muk = mt.getMetaCandidateKey(sUniqueKeyName);
            for (int iColumn = 0; iColumn < mapUniqueColumns.size(); iColumn++) {
                String sColumnName = mapUniqueColumns.get(Integer.valueOf(iColumn + 1));
                muk.addColumn(sColumnName);
            }
            mapUniqueColumns.clear();
        }
    }


    private void getColumnData(ResultSet rs, MetaColumn mc) throws IOException, SQLException {
        int iDataType = rs.getInt("DATA_TYPE");
        String sTypeName = rs.getString("TYPE_NAME");
        long lColumnSize = rs.getLong("COLUMN_SIZE");
        int iDecimalDigits = rs.getInt("DECIMAL_DIGITS");
        MetaSchema ms = null;
        QualifiedId qiParent = null;
        if (mc.getParentMetaTable() != null) {
            MetaTable mt = mc.getParentMetaTable();
            ms = mt.getParentMetaSchema();
            qiParent = new QualifiedId(null, ms.getName(), mt.getName());
        } else {
            MetaView mv = mc.getParentMetaView();
            ms = mv.getParentMetaSchema();
            qiParent = new QualifiedId(null, ms.getName(), mv.getName());
        }
        MetaType mty = null;
        if (iDataType == 2001 || iDataType == 2002)
            mty = createType(sTypeName, ms.getName(), (int) lColumnSize, iDecimalDigits);
        if (iDataType != 2001 && iDataType != 2003 && iDataType != 2002) {
            if (iDataType != 1111) {
                mc.setPreType(iDataType, lColumnSize, iDecimalDigits);
            } else {
                mc.setType(sTypeName);
            }
            mc.setTypeOriginal(sTypeName);
        } else if (iDataType == 2003) {
            Matcher m = _patARRAY_CONSTRUCTOR.matcher(sTypeName);
            if (m.matches()) {
                String sBaseType = m.group(1);

                BaseSqlFactory bsf = new BaseSqlFactory();
                DataType dt = bsf.newDataType();
                dt.parse(sBaseType);
                if (dt.getPredefinedType() != null) {
                    mc.setType(dt.format());
                } else {
                    MetaType mtyBase = createType(sBaseType, ms.getName(), -1, -1);


                    QualifiedId qiTypeBase = new QualifiedId(null, mtyBase.getParentMetaSchema().getName(), mtyBase.getName());
                    mc.setTypeName(qiTypeBase.getName());
                    mc.setTypeSchema(qiTypeBase.getSchema());
                }

                mc.setCardinality(Integer.parseInt(m.group(2)));
                mc.setTypeOriginal(sTypeName);
            } else {
                throw new SQLException("Invalid ARRAY constructor for column " + mc.getName() + " of table " + qiParent.format() + "!");
            }
        } else {
            mc.setTypeName(mty.getName());
            mc.setTypeSchema(mty.getParentMetaSchema().getName());
        }

        int iNullable = rs.getInt("NULLABLE");
        if (mc.getParentMetaTable() != null)
            if (iNullable == 0) {
                mc.setNullable(false);
            } else if (iNullable == 1) {
                mc.setNullable(true);
            }

        String sRemarks = rs.getString("REMARKS");
        if (sRemarks != null) mc.setDescription(sRemarks);
        String sColumnDefault = rs.getString("COLUMN_DEF");
        if (mc.getParentMetaTable() != null &&
                sColumnDefault != null) mc.setDefaultValue(sColumnDefault);

        int iOrdinalPosition = rs.getInt("ORDINAL_POSITION");
        if (iOrdinalPosition != mc.getPosition()) throw new IOException("Invalid column position found!");

    }


    private void getRoles() throws IOException, SQLException {
        for (int iPrivilege = 0; iPrivilege < this._md.getMetaPrivileges(); iPrivilege++) {
            MetaPrivilege mp = this._md.getMetaPrivilege(iPrivilege);
            String sGrantee = mp.getGrantee();
            MetaRole mrGrantee = this._md.getMetaRole(sGrantee);
            MetaUser muGrantee = this._md.getMetaUser(sGrantee);
            if (mrGrantee == null && muGrantee == null) {
                mrGrantee = this._md.createMetaRole(sGrantee, null);
                mrGrantee.setAdmin(mp.getGrantor());
            }
        }
    }


    private void getUsers() throws IOException, SQLException {
        this._md.createMetaUser(this._dmd.getUserName());

        for (int iPrivilege = 0; iPrivilege < this._md.getMetaPrivileges(); iPrivilege++) {
            MetaPrivilege mp = this._md.getMetaPrivilege(iPrivilege);
            String sGrantor = mp.getGrantor();
            MetaUser muGrantor = this._md.getMetaUser(sGrantor);
            if (muGrantor == null) muGrantor = this._md.createMetaUser(sGrantor);

        }
    }


    private void getPrivileges() throws IOException, SQLException {
        for (int iSchema = 0; iSchema < this._md.getMetaSchemas(); iSchema++) {
            MetaSchema ms = this._md.getMetaSchema(iSchema);
            for (int iTable = 0; iTable < ms.getMetaTables(); iTable++) {
                MetaTable mt = ms.getMetaTable(iTable);
                ResultSet rs = this._dmd.getTablePrivileges(null, ((BaseDatabaseMetaData) this._dmd)
                        .toPattern(ms.getName()), ((BaseDatabaseMetaData) this._dmd)
                        .toPattern(mt.getName()));
                while (rs.next()) {
                    String sTableSchema = rs.getString("TABLE_SCHEM");
                    String sTableName = rs.getString("TABLE_NAME");
                    String sGrantor = rs.getString("GRANTOR");
                    String sGrantee = rs.getString("GRANTEE");
                    String sPrivilege = rs.getString("PRIVILEGE");
                    String sIsGrantable = rs.getString("IS_GRANTABLE");
                    MetaPrivilege mp = this._md.createMetaPrivilege(sPrivilege, (new QualifiedId(null, sTableSchema, sTableName))


                            .format(), sGrantor, sGrantee);


                    if (!sIsGrantable.equalsIgnoreCase("NO")) mp.setOption("GRANT");
                }
                rs.close();
            }
        }
    }


    private String getQuery(String sViewDefinition) {
        String sQuery = sViewDefinition;
        String[] asParts = sQuery.split("(A|a)(S|s)\\s+(\\-\\-[^\\n]*\\s+)?(S|s)(E|e)(L|l)(E|e)(C|c)(T|t)");
        if (asParts.length > 1) sQuery = "SELECT" + asParts[1];
        return sQuery;
    }


    private void getViews(MetaSchema ms) throws IOException, SQLException {
        ResultSet rs = this._dmd.getTables(null, ((BaseDatabaseMetaData) this._dmd)
                .toPattern(ms.getName()), "%", new String[]{"VIEW"});


        while (rs.next()) {
            String sTableSchema = rs.getString("TABLE_SCHEM");
            if (!sTableSchema.equals(ms.getName())) throw new IOException("Invalid view schema found!");
            String sViewName = rs.getString("TABLE_NAME");
            QualifiedId qiView = new QualifiedId(null, sTableSchema, sViewName);
            System.out.println("  View: " + qiView.format());
            MetaView mv = ms.createMetaView(sViewName);
            try {
                String sTableType = rs.getString("TABLE_TYPE");
                if (!sTableType.equals("VIEW")) throw new IOException("Invalid table type for view found!");
                String sRemarks = rs.getString("REMARKS");
                if (sRemarks != null) mv.setDescription(sRemarks);
                String sQueryText = null;
                try {
                    sQueryText = getQuery(rs.getString("QUERY_TEXT"));
                } catch (SQLException sQLException) {
                }

                if (sQueryText != null) mv.setQueryOriginal(sQueryText);
                getColumns(mv);
            } catch (SQLException se) {
                System.err.println("View " + qiView.format() + " could not be archived (" + EU.getExceptionMessage(se) + ")!");
                ms.removeMetaView(mv);
            }
        }
        rs.close();
    }


    private void getRoutines(MetaSchema ms) throws IOException, SQLException {
        ResultSet rs = this._dmd.getProcedures(null, ((BaseDatabaseMetaData) this._dmd).toPattern(ms.getName()), "%");
        while (rs.next()) {
            String sProcedureSchema = rs.getString("PROCEDURE_SCHEM");
            if (!sProcedureSchema.equals(ms.getName())) throw new IOException("Invalid procedure schema found!");
            String sProcedureName = rs.getString("PROCEDURE_NAME");
            QualifiedId qiRoutine = new QualifiedId(null, sProcedureSchema, sProcedureName);
            System.out.println("  Routine: " + qiRoutine.format());
            String sRemarks = rs.getString("REMARKS");
            String sSpecificName = rs.getString("SPECIFIC_NAME");
            if (sSpecificName == null) sSpecificName = sProcedureName;
            MetaRoutine mr = ms.createMetaRoutine(sSpecificName);
            mr.setName(sProcedureName);
            if (sRemarks != null) mr.setDescription(sRemarks);
            getProcedureParameters(mr);
        }
        rs.close();
        try {
            rs = this._dmd.getFunctions(null, ((BaseDatabaseMetaData) this._dmd).toPattern(ms.getName()), "%");
            while (rs.next()) {
                String sFunctionSchema = rs.getString("FUNCTION_SCHEM");
                if (!sFunctionSchema.equals(ms.getName())) throw new IOException("Invalid function schema found!");
                String sFunctionName = rs.getString("FUNCTION_NAME");
                String sRemarks = rs.getString("REMARKS");
                String sSpecificName = rs.getString("SPECIFIC_NAME");
                if (sSpecificName == null) sSpecificName = sFunctionName;
                MetaRoutine mr = ms.getMetaRoutine(sSpecificName);
                if (mr == null) {

                    QualifiedId qiFunction = new QualifiedId(null, sFunctionSchema, sFunctionName);
                    System.out.println("  Function: " + qiFunction.format());
                    mr = ms.createMetaRoutine(sSpecificName);
                    mr.setName(sFunctionName);
                    if (sRemarks != null) mr.setDescription(sRemarks);
                    getFunctionParameters(mr);
                }
            }
            rs.close();
        } catch (SQLFeatureNotSupportedException sQLFeatureNotSupportedException) {
        }
    }


    private void getRows(MetaTable mt) throws IOException, SQLException {
        int iLobs = 0;
        String sQuery = "SELECT COUNT(*) AS RECORDS";
        if (this._bMaxLobNeeded) {
            for (int iColumn = 0; iColumn < mt.getMetaColumns(); iColumn++) {
                MetaColumn mc = mt.getMetaColumn(iColumn);
                int iPreType = mc.getPreType();
                if (mc.getCardinality() < 0 && (iPreType == 2005 || iPreType == 2011 || iPreType == 2004)) {

                    if (!mc.getTypeOriginal().equals("\"LONG\"")) {

                        sQuery = sQuery + ",\r\n SUM(OCTET_LENGTH(" + SqlLiterals.formatId(mc.getName()) + ")) AS " + SqlLiterals.formatId(mc
                                .getName() + "_SIZE");
                        iLobs++;
                    }
                }
            }
        }
        QualifiedId qiTable = new QualifiedId(null, mt.getParentMetaSchema().getName(), mt.getName());
        sQuery = sQuery + "\r\nFROM " + qiTable.format();
        Statement stmtSizes = this._dmd.getConnection().createStatement();
        stmtSizes.setQueryTimeout(this._iQueryTimeoutSeconds);
        ResultSet rsSizes = stmtSizes.executeQuery(sQuery);
        ResultSetMetaData rsmd = rsSizes.getMetaData();
        if (rsSizes.next()) {
            long lRows = rsSizes.getLong("RECORDS");
            mt.setRows(lRows);
            if (this._bMaxLobNeeded)
                for (int iLob = 0; iLob < iLobs; iLob++) {
                    String sLobName = rsmd.getColumnLabel(iLob + 2);
                    long lLobSize = rsSizes.getLong(sLobName);
                    if (this._lMaxLobSize < lLobSize) {
                        this._lMaxLobSize = lLobSize;
                        this._mcMaxLob = mt.getMetaColumn(sLobName.substring(0, sLobName.length() - "_SIZE".length()));
                    }
                }
        } else {
            throw new IOException("Size of table " + mt.getName() + " could not be determined!");
        }
        LOG.debug("Size of table '{}.{}' successfully determined", qiTable
                .getSchema(), qiTable
                .getName());

        rsSizes.close();
        stmtSizes.close();
    }


    private void getUniqueKeys(MetaTable mt) throws IOException, SQLException {
        String sUniqueKeyName = null;
        Map<Integer, String> mapUniqueColumns = new HashMap<>();
        ResultSet rs = this._dmd.getIndexInfo(null, mt.getParentMetaSchema().getName(), mt.getName(), true, false);
        while (rs.next()) {
            String sTableSchema = rs.getString("TABLE_SCHEM");
            if (!sTableSchema.equals(mt.getParentMetaSchema().getName()))
                throw new IOException("Invalid unique key table schema found!");
            String sTableName = rs.getString("TABLE_NAME");
            if (!sTableName.equals(mt.getName())) throw new IOException("Invalid unique key table name found!");
            boolean bNonUnique = rs.getBoolean("NON_UNIQUE");
            if (bNonUnique) throw new IOException("Invalid non-unique unique index found!");
            String sIndexName = rs.getString("INDEX_NAME");
            int iIndexType = rs.getInt("TYPE");

            boolean bPrimary = mt.getMetaPrimaryKey() != null && mt.getMetaPrimaryKey().getName().equals(sIndexName);
            if (iIndexType != 0 && iIndexType != 3 && !bPrimary) {
                MetaUniqueKey muk = mt.getMetaCandidateKey(sIndexName);
                if (muk == null) {
                    addColumns(mt, sUniqueKeyName, mapUniqueColumns);
                    sUniqueKeyName = sIndexName;
                    muk = mt.createMetaCandidateKey(sUniqueKeyName);
                }
                int iOrdinalPosition = rs.getInt("ORDINAL_POSITION");
                String sColumnName = rs.getString("COLUMN_NAME");
                mapUniqueColumns.put(Integer.valueOf(iOrdinalPosition), sColumnName);
            }

            LOG.debug("Metadata for unique key '{}' (table '{}.{}') loaded", sUniqueKeyName, sTableSchema, sTableName);
        }


        rs.close();
        addColumns(mt, sUniqueKeyName, mapUniqueColumns);
    }


    private void getForeignKeys(MetaTable mt) throws IOException, SQLException {
        String sForeignKeyName = null;
        Map<Integer, String> mapFkColumns = new HashMap<>();
        Map<String, String> mapPkColumns = new HashMap<>();
        ResultSet rs = this._dmd.getImportedKeys(null, mt.getParentMetaSchema().getName(), mt.getName());
        while (rs.next()) {
            String sPkTableSchema = rs.getString("PKTABLE_SCHEM");
            String sPkTableName = rs.getString("PKTABLE_NAME");
            String sPkColumnName = rs.getString("PKCOLUMN_NAME");
            String sFkTableSchema = rs.getString("FKTABLE_SCHEM");
            if (!sFkTableSchema.equals(mt.getParentMetaSchema().getName()))
                throw new IOException("Invalid foreign key table schema found!");
            String sFkTableName = rs.getString("FKTABLE_NAME");
            if (!sFkTableName.equals(mt.getName())) throw new IOException("Invalid foreign key table name found!");
            String sFkColumnName = rs.getString("FKCOLUMN_NAME");
            MetaColumn mc = mt.getMetaColumn(sFkColumnName);
            if (mc == null) throw new IOException("Invalid foreign key column name found!");
            int iKeySeq = rs.getInt("KEY_SEQ");
            int iUpdateRule = rs.getInt("UPDATE_RULE");
            int iDeleteRule = rs.getInt("DELETE_RULE");
            String sFkName = rs.getString("FK_NAME");
            MetaForeignKey mfk = mt.getMetaForeignKey(sFkName);
            if (mfk == null) {

                addReferences(mt, sForeignKeyName, mapFkColumns, mapPkColumns);

                sForeignKeyName = sFkName;
                mfk = mt.createMetaForeignKey(sForeignKeyName);
            }
            mapPkColumns.put(sFkColumnName, sPkColumnName);
            mapFkColumns.put(Integer.valueOf(iKeySeq), sFkColumnName);
            mfk.setReferencedSchema(sPkTableSchema);
            mfk.setReferencedTable(sPkTableName);
            mfk.setDeleteAction(getReferentialAction(iDeleteRule));
            mfk.setUpdateAction(getReferentialAction(iUpdateRule));

            LOG.debug("Metadata for foreign key '{}' (table '{}.{}') loaded", sForeignKeyName, sPkTableSchema, sPkTableName);
        }


        rs.close();

        addReferences(mt, sForeignKeyName, mapFkColumns, mapPkColumns);
    }


    private void getPrimaryKey(MetaTable mt) throws IOException, SQLException {
        String sPkName = "PK_" + mt.getName();
        Map<Integer, String> mapPkColumns = new HashMap<>();
        ResultSet rs = this._dmd.getPrimaryKeys(null, mt.getParentMetaSchema().getName(), mt.getName());
        while (rs.next()) {
            String sTableSchema = rs.getString("TABLE_SCHEM");
            if (!sTableSchema.equals(mt.getParentMetaSchema().getName()))
                throw new IOException("Invalid table schema for primary key found!");
            String sTableName = rs.getString("TABLE_NAME");
            if (!mt.getName().equals(sTableName)) throw new IOException("Invalid table name for primary key found!");
            String sColumnName = rs.getString("COLUMN_NAME");
            int iKeySeq = rs.getInt("KEY_SEQ");
            mapPkColumns.put(Integer.valueOf(iKeySeq), sColumnName);
            String s = rs.getString("PK_NAME");
            if (s != null) {
                sPkName = s;
            } else {
                LOG.info("No name for primary key of column '{}.{}.{}' available. Used '{}' instead.", sTableSchema, sTableName, sColumnName, sPkName);
            }


            LOG.debug("Metadata for primary key '{}' (column '{}.{}.{}') loaded", sPkName, sTableSchema, sTableName, sColumnName);
        }


        rs.close();
        if (mapPkColumns.size() > 0) {
            MetaUniqueKey muk = mt.createMetaPrimaryKey(sPkName);
            for (int iColumn = 0; iColumn < mapPkColumns.size(); iColumn++) {
                String sColumnName = mapPkColumns.get(Integer.valueOf(iColumn + 1));
                muk.addColumn(sColumnName);
            }
        }
    }


    private void getColumns(MetaTable mt) throws IOException, SQLException {

        ResultSet rs = this._dmd.getColumns(null,
                ((BaseDatabaseMetaData) this._dmd).toPattern(mt.getParentMetaSchema().getName()),
                ((BaseDatabaseMetaData) this._dmd).toPattern(mt.getName()),
                "%");


        while (rs.next()) {
            String sTableSchema = rs.getString("TABLE_SCHEM");
            if (!sTableSchema.equals(mt.getParentMetaSchema().getName()))
                throw new IOException("Invalid table schema for column found!");
            String sTableName = rs.getString("TABLE_NAME");
            if (!sTableName.equals(mt.getName())) throw new IOException("Invalid table name for column found!");
            String sColumnName = rs.getString("COLUMN_NAME");
            MetaColumn mc = mt.createMetaColumn(sColumnName);
            getColumnData(rs, mc);

            LOG.debug("Metadata for column '{}.{}.{}' loaded", sTableSchema, sTableName, sColumnName);
        }

        if (mt.getMetaColumns() == 0) throw new SQLException("Table " + mt.getName() + " has no columns!");
        rs.close();
    }

    private void getGlobalMetaData() throws IOException, SQLException {
        getPrivileges();

        getUsers();

        getRoles();
    }


    private void getSchemaMetaData() throws IOException, SQLException {
        for (int iSchema = 0; iSchema < this._md.getMetaSchemas(); iSchema++) {
            MetaSchema ms = this._md.getMetaSchema(iSchema);
            if (ms.getMetaTables() > 0) {
                if (!this._bViewsAsTables) getViews(ms);
                getRoutines(ms);
            }
        }
    }

    private void getTables(Map<String, List<String>> selectedSchemaTableMap) throws IOException, SQLException {
        String[] asTypes = {"TABLE"};
        if (this._bViewsAsTables) asTypes = new String[]{"TABLE", "VIEW"};
        ResultSet rs = this._dmd.getTables(null, "%", "%", asTypes);
        this._iTables = 0;
        for (; rs.next(); this._iTables++) ;
        rs.close();
        this._iTablesPercent = (this._iTables + 99) / 100;
        this._iTablesAnalyzed = 0;
        rs = this._dmd.getTables(null, "%", "%", asTypes);

        boolean hasSelected = !selectedSchemaTableMap.entrySet().isEmpty();

        while (rs.next() && !cancelRequested()) {
            String sTableSchema = rs.getString("TABLE_SCHEM");
            String sTableName = rs.getString("TABLE_NAME");
            String sTableType = rs.getString("TABLE_TYPE");
            if (!Arrays.asList(asTypes).contains(sTableType))
                throw new IOException("Invalid table type found!");
            String sRemarks = rs.getString("REMARKS");

            // 특정 엔티티를 선택한 경우 선택되지 않은 엔티티는 메타 정보 조회 무시 1
            if (hasSelected) {
                boolean selected = selectedSchemaTableMap.entrySet()
                        .stream()
                        .anyMatch(entry -> {
                                    String schemaName = entry.getKey();
                                    HashSet<String> tableNameSet = new HashSet<>(entry.getValue());

                                    return sTableSchema.equals(schemaName) && tableNameSet.contains(sTableName);
                                }
                        );
                if (!selected) continue;
            }

            Schema schema = this._md.getArchive().getSchema(sTableSchema);
            if (schema == null) schema = this._md.getArchive().createSchema(sTableSchema);
            Table table = schema.getTable(sTableName);
            if (table == null) table = schema.createTable(sTableName);
            MetaTable mt = table.getMetaTable();
            QualifiedId qiTable = new QualifiedId(null, sTableSchema, sTableName);
            System.out.println("  Table: " + qiTable.format());
            if (sRemarks != null && sRemarks.length() > 0) mt.setDescription(sRemarks);

            LOG.debug("Load metadata for table '{}.{}'", sTableSchema, sTableName);

            getColumns(mt);
            getPrimaryKey(mt);
            getForeignKeys(mt);
            getUniqueKeys(mt);
            getRows(mt);
            incTablesAnalyzed();
        }
        rs.close();
    }

    private void getTables() throws IOException, SQLException {
        String[] asTypes = {"TABLE"};
        if (this._bViewsAsTables) asTypes = new String[]{"TABLE", "VIEW"};
        ResultSet rs = this._dmd.getTables(null, "%", "%", asTypes);
        this._iTables = 0;
        for (; rs.next(); this._iTables++) ;
        rs.close();
        this._iTablesPercent = (this._iTables + 99) / 100;
        this._iTablesAnalyzed = 0;
        rs = this._dmd.getTables(null, "%", "%", asTypes);
        while (rs.next() && !cancelRequested()) {
            String sTableSchema = rs.getString("TABLE_SCHEM");
            String sTableName = rs.getString("TABLE_NAME");
            String sTableType = rs.getString("TABLE_TYPE");
            if (!Arrays.asList(asTypes).contains(sTableType))
                throw new IOException("Invalid table type found!");
            String sRemarks = rs.getString("REMARKS");
            Schema schema = this._md.getArchive().getSchema(sTableSchema);
            if (schema == null) schema = this._md.getArchive().createSchema(sTableSchema);
            Table table = schema.getTable(sTableName);
            if (table == null) table = schema.createTable(sTableName);
            MetaTable mt = table.getMetaTable();
            QualifiedId qiTable = new QualifiedId(null, sTableSchema, sTableName);
            System.out.println("  Table: " + qiTable.format());
            if (sRemarks != null && sRemarks.length() > 0) mt.setDescription(sRemarks);

            LOG.debug("Load metadata for table '{}.{}'", sTableSchema, sTableName);

            // 테이블 사이즈 세팅
            getTableSize(table, mt);
            getColumns(mt);
            getPrimaryKey(mt);
            getForeignKeys(mt);
            getUniqueKeys(mt);
            getRows(mt);
            incTablesAnalyzed();
        }
        rs.close();
    }

    private void logDownload() throws IOException, SQLException {
        if (this._md.getDataOwner() == null) this._md.setDataOwner("(...)");
        if (this._md.getDataOriginTimespan() == null) this._md.setDataOriginTimespan("(...)");
        if (this._md.getDbName() == null) this._md.setDbName("(...)");
        ProgramInfo pi = ProgramInfo.getProgramInfo();
        this._md.setProducerApplication(pi.getProgram() + " " + pi.getVersion() + " " + pi.getCopyright());

        try {
            InetAddress ia = InetAddress.getLocalHost();
            this._md.setClientMachine(ia.getCanonicalHostName());
        } catch (UnknownHostException uhe) {
            LOG.error("Can not determine host", uhe);
        }

        this._md.setDatabaseProduct(this._dmd.getDatabaseProductName() + " " + this._dmd.getDatabaseProductVersion());

        this._md.setConnection(this._dmd.getURL());

        this._md.setDatabaseUser(this._dmd.getUserName());
    }

    public void download(boolean bViewsAsTables, boolean bMaxLobNeeded, Progress progress) throws IOException, SQLException {
        downloadInternal(bViewsAsTables, bMaxLobNeeded, progress, null);
    }

    /**
     * 선택한 엔티티의 메타 정보 추출
     *
     * @param bViewsAsTables
     * @param bMaxLobNeeded
     * @param progress
     * @param selectedSchemaTableMap
     * @throws IOException
     * @throws SQLException
     */
    public void download(boolean bViewsAsTables,
                         boolean bMaxLobNeeded,
                         Progress progress,
                         Map<String, List<String>> selectedSchemaTableMap) throws IOException, SQLException {
        downloadInternal(bViewsAsTables, bMaxLobNeeded, progress, selectedSchemaTableMap);
    }

    private void downloadInternal(boolean bViewsAsTables,
                                  boolean bMaxLobNeeded,
                                  Progress progress,
                                  Map<String, List<String>> selectedSchemaTableMap) throws IOException, SQLException {

        LOG.info("Start meta data download to archive {} (view-as-tables: {}, max-lob-needed: {})",
                this._md.getArchive().getFile().getAbsoluteFile(),
                Boolean.valueOf(bViewsAsTables),
                Boolean.valueOf(bMaxLobNeeded));

        System.out.println("Meta Data");

        this._progress = progress;
        this._bViewsAsTables = bViewsAsTables;
        this._bMaxLobNeeded = bMaxLobNeeded;

        logDownload();

        if (selectedSchemaTableMap != null && !selectedSchemaTableMap.isEmpty()) {
            getTables(selectedSchemaTableMap);
        } else {
            getTables();
        }

        if (!cancelRequested()) getSchemaMetaData();
        if (!cancelRequested()) getGlobalMetaData();

        if (cancelRequested()) throw new IOException("Meta data download cancelled!");

        LOG.info("Meta data download finished");

    }

    /**
     * 데이터베이스 테이블 별 사이즈를 구하여 세팅
     *
     * @param table
     * @param metaTable
     * @throws SQLException
     */
    private void getTableSize(Table table, MetaTable metaTable) {
        String dbName = null;
        try {
            String databaseProductName = this._dmd.getConnection().getMetaData().getDatabaseProductName().toLowerCase();
            dbName = databaseProductName;
            DataBase dataBase = DataBase.findByName(databaseProductName);

            String tableName = metaTable.getName();
            String schemaName = metaTable.getParentMetaSchema().getName();

            if (dataBase.equals(DataBase.CUBRID)) {
                return;
            }

            String query = switch (dataBase) {
                case MYSQL -> "SELECT (data_length + index_length) AS size " +
                        "FROM information_schema.tables " +
                        "WHERE table_schema = ? " +
                        "AND table_name = ?";
                case ORACLE -> getOracleQuery();
                case POSTGRESQL -> "SELECT pg_size_pretty(pg_total_relation_size(?)) AS size";
                case MSSQL -> "SELECT SUM(a.total_pages) AS size_mb " +
                        "FROM sys.tables t ...";
                case CUBRID -> ";info stats " + tableName;
                case TIBERO -> "";
            };

            long size = 0;
            try (PreparedStatement stmt = _dmd.getConnection().prepareStatement(query)) {
                switch (dataBase) {
                    case MYSQL -> {
                        stmt.setString(1, schemaName);
                        stmt.setString(2, tableName);
                    }
                    case ORACLE -> {
                        stmt.setString(1, tableName);
                        stmt.setString(2, tableName);
                    }
                    case POSTGRESQL -> stmt.setString(1, schemaName + "." + tableName);
                    // TODO :: more db
                }

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    size = rs.getLong(1);  // Assuming the size is in the first column
                }
            }

            Schema parentSchema = table.getParentSchema();
            parentSchema.addTableSize(size);

            table.setFormattedTableSize(ByteFormatter.convertToBestFitUnit(size));
            table.setTableSize(size);
        } catch (Exception e) {
            if (e instanceof SQLException) {
                SQLException sqlException = (SQLException) e;
                doHandleSqlException(dbName, "getTableSize", sqlException);
            }
        }

    }

    private String getOracleQuery() {
        String databaseUser = this._md.getArchive().getMetaData().getDatabaseUser();
        if (isSysdba(databaseUser)) {
            return "SELECT bytes AS table_size " +
                    "FROM dba_segments " +
                    "WHERE (segment_name = UPPER(?) OR segment_name = LOWER(?)) " +
                    "AND segment_type = 'TABLE'";
        } else {
            return "SELECT bytes AS table_size " +
                    "FROM USER_segments " +
                    "WHERE (segment_name = UPPER(?) OR segment_name = LOWER(?)) " +
                    "AND segment_type = 'TABLE'";
        }
    }

    private boolean isSysdba(String databaseUser) {
//        return true;
        Optional.ofNullable(databaseUser).orElseThrow(() -> new IllegalArgumentException("database user is not specified!!"));
        return !databaseUser.startsWith("C##") && databaseUser.startsWith("SYS");
    }

}