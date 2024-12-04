package ch.admin.bar.siard2.cmd.utils.db;

import ch.admin.bar.siard2.cmd.utils.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void givenDataTypeWithSpecifier_whenSubstr_thenSuccess() {
        String dataTypeWithSpecifier = "VARCHAR(100)";
        String dataType = "VARCHAR";
        String specifier = "(100)";

        String actualDataType = StringUtils.subStringDataType(dataTypeWithSpecifier);
        String actualSpecifier = StringUtils.subStringDataTypeSpecifier(dataTypeWithSpecifier);

        assertEquals(dataType, actualDataType);
        assertEquals(specifier, actualSpecifier);
    }

    @Test
    void givenDataTypeWithoutSpecifier_whenSubStr_thenSuccess() {
        String dataType = "INT";
        String specifier = "";

        String actualDataType = StringUtils.subStringDataType(dataType);
        String actualSpecifier = StringUtils.subStringDataTypeSpecifier(dataType);

        assertEquals(dataType, actualDataType);
        assertEquals(specifier, actualSpecifier);

    }

}