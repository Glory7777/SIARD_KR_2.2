package ch.admin.bar.siard2.cmd.utils;

public class StringUtils {

    private final static String LEFT_PARENTHESIS = "(";
    private final static String DOT = ".";

    private StringUtils() {
    }

    public static String removeFirstDot(String str) {
        return subStringByDelimiter(str, DOT, false, 1);
    }

    public static String subStringDataType(String str) {
        return subStringByDelimiter(str, LEFT_PARENTHESIS, true);
    }

    public static String subStringDataTypeSpecifier(String str) {
        return subStringByDelimiter(str, LEFT_PARENTHESIS, false);
    }

    private static String subStringByDelimiter(String str, String delimiter, boolean trimBeforeDelimiter) {
        return subStringByDelimiter(str, delimiter, trimBeforeDelimiter, 0);
    }

    private static String subStringByDelimiter(String str, String delimiter, boolean trimBeforeDelimiter, int offset) {
        throwIfNullOrEmpty(str);

        str = str.toUpperCase();

        int i = str.indexOf(delimiter);
        if (i > 0) {
            return trimBeforeDelimiter ? str.substring(0, i) : str.substring(i + offset);
        }
        return trimBeforeDelimiter ? str : "";
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static void throwIfNullOrEmpty(String str) {
        if (isNullOrEmpty(str)) throw new RuntimeException("cannot be null");
    }

}
