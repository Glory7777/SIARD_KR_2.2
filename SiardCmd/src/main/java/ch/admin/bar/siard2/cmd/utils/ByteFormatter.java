package ch.admin.bar.siard2.cmd.utils;

public class ByteFormatter {

    private final long bytes;
    private static final long KILOBYTE = 1024;
    private static final long MEGABYTE = KILOBYTE * 1024;
    private static final long GIGABYTE = MEGABYTE * 1024;
    private static final long TERABYTE = GIGABYTE * 1024;

    public static double convertBytesToKB(long bytes) {
        return roundToTwoDecimalPlaces((double) bytes / KILOBYTE);
    }

    public static double convertBytesToMB(long bytes) {
        return roundToTwoDecimalPlaces((double) bytes / MEGABYTE);
    }

    public static double convertBytesToGB(long bytes) {
        return roundToTwoDecimalPlaces((double) bytes / GIGABYTE);
    }

    public static double convertBytesToTB(long bytes) {
        return roundToTwoDecimalPlaces((double) bytes / TERABYTE);
    }

    public static double convertToBestFitUnit(long bytes) {
        if (bytes >= TERABYTE) {
            return convertBytesToTB(bytes);
        } else if (bytes >= GIGABYTE) {
            return convertBytesToGB(bytes);
        } else if (bytes >= MEGABYTE) {
            return convertBytesToMB(bytes);
        } else if (bytes >= KILOBYTE) {
            return convertBytesToKB(bytes);
        } else {
            return roundToTwoDecimalPlaces((double) bytes); // If less than 1 KB, keep it in bytes
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

    public String convertToByte() {
        return String.format(this.bytes + " Bytes");
    }

    public String convertToKiloByte() {
        return String.format("%.2f KB", (double) bytes / KILOBYTE);
    }

    public String convertToMegaByte() {
        return String.format("%.2f MB", (double) bytes / MEGABYTE);
    }

    public String convertToGigaByte() {
        return String.format("%.2f GB", (double) bytes / GIGABYTE);
    }

    public String convertToTerraByte() {
        return String.format("%.2f GB", (double) bytes / GIGABYTE);
    }

}
