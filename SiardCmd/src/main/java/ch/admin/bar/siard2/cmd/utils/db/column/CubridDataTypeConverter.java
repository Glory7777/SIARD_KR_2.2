package ch.admin.bar.siard2.cmd.utils.db.column;

import ch.admin.bar.siard2.api.MetaColumn;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class CubridDataTypeConverter extends AbstractDataTypeConverter {

    private static final Map<String, String> DEFAULT_COLUMN_SPECIFIER = Map.of(
            "VARCHAR", "1073741823",
            "CHAR", "1073741823",
            "NUMBER", "38, 0",
            "DECIMAL", "38, 0",
            "BIT", "1073741823",
            "BIT VARYING", "1073741823",
            "STRING", "1073741823"
    );

    private static final Set<String> SUPPORTED_DATA_TYPE = Set.of(
            "INT", "INTEGER", "SHORT", "SMALLINT", "BIGINT", "NUMERIC",
            "DECIMAL", "FLOAT", "REAL", "DOUBLE", "DOUBLE PRECISION",
            "MONETARY", "DATE", "TIME","TIMESTAMP", "DATETIME",
            "BIT", "BIT VARYING", "CHAR", "VARCHAR", "CHAR VARYING",
            "STRING", "BLOB", "CLOB"
    );

    private static final Map<String, String> COMPATIBLE_COLUMN_TYPE = Map.of(
            "VARBINARY", "VARCHAR",
            "BINARY", "CHAR",
            "NVARCHAR2", "VARCHAR",
            "NCHAR", "CHAR",
            "NCHAR VARYING", "VARCHAR",
            "RAW", "VARCHAR",
            "DEC", "DECIMAL"
    );

    public CubridDataTypeConverter(String databaseProductName, MetaColumn metaColumn) throws IOException {
        super(databaseProductName, metaColumn);
    }

    @Override
    protected boolean isSupported(String dataType) {
        return SUPPORTED_DATA_TYPE.contains(dataType);
    }

    @Override
    protected String getAlternativeType(String dataType) {
        return COMPATIBLE_COLUMN_TYPE.get(dataType);
    }

    @Override
    protected String getDefaultSpecifier(String dataType) {
        return DEFAULT_COLUMN_SPECIFIER.get(dataType);
    }

    @Override
    protected boolean requiresSpecifier(String dataType) {
        return DEFAULT_COLUMN_SPECIFIER.containsKey(dataType);
    }
}
