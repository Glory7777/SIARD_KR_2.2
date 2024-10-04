package ch.admin.bar.siard2.cmd.utils;

import lombok.Getter;

@Getter
public class ByteFormatter {

    private final long bytes;
    private static final long KILOBYTE = 1024;
    private static final long MEGABYTE = KILOBYTE * 1024;
    private static final long GIGABYTE = MEGABYTE * 1024;
    private static final long TERABYTE = GIGABYTE * 1024;

    public static String convertToBestFitUnit(long bytes) {
        if (bytes >= TERABYTE) {
            return convertToTB(bytes);
        } else if (bytes >= GIGABYTE) {
            return convertToGB(bytes);
        } else if (bytes >= MEGABYTE) {
            return convertToMB(bytes);
        } else if (bytes >= KILOBYTE) {
            return convertToKB(bytes);
        } else {
            return convertToByte(bytes);
        }
    }

    // Rounds the given value to 2 decimal places
    private static double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private ByteFormatter(long bytes) {
        this.bytes = bytes;
    }

    public static ByteFormatter getInstance(long bytes) {
        return new ByteFormatter(bytes);
    }

    public static String convertToByte(long bytes) {
        return String.format(bytes + " Bytes");
    }

    public static String convertToKB(long bytes) {
        return String.format("%.2f KB", (double) bytes / KILOBYTE);
    }

    public static String convertToMB(long bytes) {
        return java.lang.String.format("%.2f MB", (double) bytes / MEGABYTE);
    }

    public static String convertToGB(long bytes) {
        return java.lang.String.format("%.2f GB", (double) bytes / GIGABYTE);
    }

    public static String convertToTB(long bytes) {
        return java.lang.String.format("%.2f GB", (double) bytes / GIGABYTE);
    }

}
