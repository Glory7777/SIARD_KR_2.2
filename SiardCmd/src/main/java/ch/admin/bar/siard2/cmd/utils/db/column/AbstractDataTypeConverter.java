package ch.admin.bar.siard2.cmd.utils.db.column;

import ch.admin.bar.siard2.api.MetaColumn;
import ch.admin.bar.siard2.cmd.MetaDataBase;
import sqlj.runtime.profile.util.CustomizerHarnessBeanInfo;

import javax.activation.UnsupportedDataTypeException;
import java.io.IOException;

import static ch.admin.bar.siard2.cmd.utils.StringUtils.subStringDataType;
import static ch.admin.bar.siard2.cmd.utils.StringUtils.subStringDataTypeSpecifier;

public abstract class AbstractDataTypeConverter implements DataTypeConverter {

    protected final String databaseProductName;
    protected final MetaDataBase.DataBase dataBase;
    protected final String dataType;
    protected final String originalDataType;

    protected AbstractDataTypeConverter(String databaseProductName, MetaColumn metaColumn) throws IOException {
        this.databaseProductName = databaseProductName;
        this.dataBase = MetaDataBase.DataBase.findByName(databaseProductName);
        this.dataType = metaColumn.getType().toUpperCase();
        this.originalDataType = metaColumn.getTypeOriginal().toUpperCase();
    }

    protected boolean isSupported(String dataType) {return false;}; // cubrid, tibero만 재정의

    protected abstract String getAlternativeType(String dataType);
    protected abstract String getDefaultSpecifier(String dataType);
    protected abstract boolean requiresSpecifier(String dataType);

    protected String appendSpecifier(String dataType, String specifier) {
        if (isLob(dataType)) return dataType;
        return dataType + getDefaultTypeSpecifierIfNecessary(dataType, specifier);
    }

    protected String getDefaultTypeSpecifierIfNecessary(String dataType, String specifier){
        if (requiresSpecifier(dataType) && specifier.isEmpty()) {
            return "(" + getDefaultSpecifier(dataType) + ")";
        }
        return specifier;
    };

    protected boolean isLob(String type) {
        return type.contains("LOB");
    }

    private boolean isOpenSourceDb() {
        return dataBase != MetaDataBase.DataBase.TIBERO && dataBase != MetaDataBase.DataBase.CUBRID;
    }

    @Override
    public String getColumnType() throws IOException {
        String fullType = this.dataType;
        String lengthSpecifier = subStringDataTypeSpecifier(fullType);
        fullType = subStringDataType(fullType);

        if (isSupported(fullType)) {
            return appendSpecifier(fullType, lengthSpecifier);
        }

        String alternativeType = getAlternativeType(fullType);

        if (alternativeType != null && !alternativeType.isEmpty()) {
            return appendSpecifier(alternativeType, lengthSpecifier);
        }

        String originalType = originalDataType.replaceAll("\"", "");

        if (isSupported(originalType)) {
            return appendSpecifier(originalType, lengthSpecifier);
        }

        // 오픈소스 db 인 경우 대부분 타입 conversion이 구현되어 있으므로 호환되는 데이터가 명시적으로 제공되지 않은 경우 해당 jdbc에 위임
        if (isOpenSourceDb()) {
            return appendSpecifier(fullType, lengthSpecifier);
        }

        String msg = String.format("type %s is not supported in %s", fullType, databaseProductName);
        throw new UnsupportedDataTypeException(msg);
    }

}
