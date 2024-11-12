package ch.admin.bar.siard2.api.utli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchUtilTest {

    String searchTerm;
    SearchUtil searchUtil;

    @BeforeEach
    void setup() {
        searchTerm = "Downloads";
        searchUtil = new SearchUtil("Downloads");
    }

    @Test
    void givenSearchKeyThenReturnsTrue() {
        String searchTerm = "Downloads/tesdt";
        assertTrue(searchUtil.matches(searchTerm));
    }

}