package ch.admin.bar.siard2.cmd.utils.db.column;

import ch.admin.bar.siard2.api.MetaColumn;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class TiberoDataTypeConverter extends AbstractDataTypeConverter {

    public TiberoDataTypeConverter(String databaseProductName, MetaColumn metaColumn) throws IOException {
        super(databaseProductName, metaColumn);
    }

    /**
     * 길이가 주어져야 하는 타입
     */
    private static final Map<String, String> DEFAULT_COLUMN_SPECIFIER = Map.of(
            "VARCHAR", "65532",
            "NCHAR", "2000",
            "NVARCHAR", "65532",
            "RAW", "2000"
    );

    private static final Map<String, SupportedDataType> COMPATIBLE_COLUMN_TYPE_MAP = Map.of(
            "BIGINT", SupportedDataType.INTEGER,
            "SMALLINT", SupportedDataType.INTEGER,
            "DOUBLE PRECISION", SupportedDataType.NUMBER,
            "BINARY", SupportedDataType.RAW,
            "VARBINARY", SupportedDataType.RAW,
            "NCHAR VARYING", SupportedDataType.CHAR
    );

    protected boolean requiresSpecifier(String type) {
        return DEFAULT_COLUMN_SPECIFIER.containsKey(type);
    }

    @Override
    protected String getAlternativeType(String type) {
        return Optional.ofNullable(COMPATIBLE_COLUMN_TYPE_MAP.get(type.toUpperCase()))
                .map(SupportedDataType::name)
                .orElse(null);
    }

    @Override
    protected String getDefaultSpecifier(String dataType) {
        return DEFAULT_COLUMN_SPECIFIER.get(dataType);
    }

    @Override
    protected boolean isSupported(String dataType) {
        return SupportedDataType.isSupported(dataType);
    }

    @Getter
    @RequiredArgsConstructor
    private enum SupportedDataType {

        NUMBER(OriginalDataType.NUMBER, "(38, 0)"),
        NCHAR(OriginalDataType.NUMBER, "(2000)"),
        INT(OriginalDataType.NUMBER, "(38, 0)"),
        INTEGER(OriginalDataType.NUMBER, "(38, 0)"),
        DECIMAL(OriginalDataType.NUMBER, "(38, 0)"),
        NUMERIC(OriginalDataType.NUMBER, "(38, 0)"),
        FLOAT(OriginalDataType.NUMBER, "(38, 0)"),
        DATE(OriginalDataType.DATE, ""),
        TIMESTAMP(OriginalDataType.TIMESTAMP, "(23)"),
        TIME(OriginalDataType.TIME, "(6)"),
        CHAR(OriginalDataType.CHARACTER, "2000"),
        VARCHAR(OriginalDataType.VARCHAR, "65532"),
        VARCHAR2(OriginalDataType.VARCHAR, "65532"),
        NVARCHAR(OriginalDataType.VARCHAR, "65532"),
        CLOB(OriginalDataType.LOB, ""),
        BLOB(OriginalDataType.LOB, ""),
        NCLOB(OriginalDataType.LOB, ""),
        ROWID(OriginalDataType.ROWID, ""),
        RAW(OriginalDataType.RAW, "2000"),
        ;

        final OriginalDataType originalType;
        final String defaultSpecifier;

        private static boolean isSupported(String type) {
            return Arrays.stream(values()).anyMatch(supportedType -> supportedType.name().equalsIgnoreCase(type));
        }

    }

}
