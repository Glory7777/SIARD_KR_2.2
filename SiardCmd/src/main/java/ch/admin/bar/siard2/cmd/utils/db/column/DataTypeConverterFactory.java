package ch.admin.bar.siard2.cmd.utils.db.column;

import ch.admin.bar.siard2.api.MetaColumn;
import ch.admin.bar.siard2.cmd.utils.ThrowingBiFunction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataTypeConverterFactory {

    private static final Map<String, ThrowingBiFunction<String, MetaColumn, DataTypeConverter>> CONVERTER_MAP = new HashMap<>();

    static {
        CONVERTER_MAP.put("TIBERO", TiberoDataTypeConverter::new);
        CONVERTER_MAP.put("CUBRID", CubridDataTypeConverter::new);
        CONVERTER_MAP.put("MYSQL", MySQLDataTypeConverter::new);
        CONVERTER_MAP.put("ORACLE", OracleDataTypeConverter::new);
    }

    public static DataTypeConverter getInstance(String databaseProductName, MetaColumn metaColumn) throws IOException {
        ThrowingBiFunction<String, MetaColumn, DataTypeConverter> constructor =
                CONVERTER_MAP.get(databaseProductName.toUpperCase());

        if (constructor == null) {
            return null;
//            throw new UnsupportedOperationException("No DataTypeConverter for database: " + databaseProductName);
        }

        return constructor.apply(databaseProductName, metaColumn);
    }

}
