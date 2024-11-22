package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class KOI8RCharToByteConverter {
    protected boolean subMode = true;

    protected byte[] subBytes = new byte[]{63};

    private static final char[] UNICODE_TO_KOI8R_IDX_PAGE00 = new char[]{
            '\u009A', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u00BF',
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u009C', Character.MIN_VALUE, '\u009D', Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u009E', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u009F'};

    private static final char[] UNICODE_TO_KOI8R_IDX_PAGE04 = new char[]{
            Character.MIN_VALUE, '\u00B3', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u00E1', '\u00E2', '\u00F7', '\u00E7',
            '\u00E4', '\u00E5', '\u00F6', '\u00FA', '\u00E9', '\u00EA', '\u00EB', '\u00EC', '\u00ED', '\u00EE',
            '\u00EF', '\u00F0', '\u00F2', '\u00F3', '\u00F4', '\u00F5', '\u00E6', '\u00E8', '\u00E3', '\u00FE',
            '\u00FB', '\u00FD', '\u00FF', '\u00F9', '\u00F8', '\u00FC', '\u00E0', '\u00F1', '\u00C1', '\u00C2',
            '\u00D7', '\u00C7', '\u00C4', '\u00C5', '\u00D6', '\u00DA', '\u00C9', '\u00CA', '\u00CB', '\u00CC',
            '\u00CD', '\u00CE', '\u00CF', '\u00D0', '\u00D2', '\u00D3', '\u00D4', '\u00D5', '\u00C6', '\u00C8',
            '\u00C3', '\u00DE', '\u00DB', '\u00DD', '\u00DF', '\u00D9', '\u00D8', '\u00DC', '\u00C0', '\u00D1',
            Character.MIN_VALUE, '\u00A3', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE};

    private static final char[] UNICODE_TO_KOI8R_IDX_PAGE22 = new char[]{
            Character.MIN_VALUE, '\u0095', '\u0096', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u0097', Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u0098', '\u0099', Character.MIN_VALUE, Character.MIN_VALUE};

    private static final char[] UNICODE_TO_KOI8R_IDX_PAGE23 = new char[]{'\u0093', '\u009B', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE};

    private static final char[] UNICODE_TO_KOI8R_IDX_PAGE25 = new char[]{
            '\u0080', Character.MIN_VALUE, '\u0081', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, '\u0082', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u0083', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            '\u0084', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u0085', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u0086', Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u0087', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u0088', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, '\u0089', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            '\u008A', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            '\u00A0', '\u00A1', '\u00A2', '\u00A4', '\u00A5', '\u00A6', '\u00A7', '\u00A8', '\u00A9', '\u00AA',
            '\u00AB', '\u00AC', '\u00AD', '\u00AE', '\u00AF', '\u00B0', '\u00B1', '\u00B2', '\u00B4', '\u00B5',
            '\u00B6', '\u00B7', '\u00B8', '\u00B9', '\u00BA', '\u00BB', '\u00BC', '\u00BD', '\u00BE', Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u008B', Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, '\u008C', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u008D', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            '\u008E', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u008F', '\u0090', '\u0091', '\u0092', Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            '\u0094', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE};

    public int convCharArr(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
        int i = paramInt1;
        int j = paramInt3;
        while (i < paramInt2 && j < paramInt4) {
            char c = paramArrayOfchar[i];
            if (c < '\u0080') {
                paramArrayOfbyte[j++] = (byte) c;
            } else {
                char b;
                if (c >= '\u00A0' && c < '\u00F8') {
                    b = UNICODE_TO_KOI8R_IDX_PAGE00[c - 160];
                } else if (c >= '\u0400' && c < '\u0458') {
                    b = UNICODE_TO_KOI8R_IDX_PAGE04[c - 1024];
                } else if (c >= '\u2218' && c < '\u2268') {
                    b = UNICODE_TO_KOI8R_IDX_PAGE22[c - 8728];
                } else if (c >= '\u2320' && c < '\u2328') {
                    b = UNICODE_TO_KOI8R_IDX_PAGE23[c - 8992];
                } else if (c >= '\u2500' && c < '\u25A8') {
                    b = UNICODE_TO_KOI8R_IDX_PAGE25[c - 9472];
                } else {
                    b = 63;
                }
                if (b == 0)
                    b = 63;
                paramArrayOfbyte[j++] = (byte) b;
            }
            i++;
        }
        return j - paramInt3;
    }

    public int convString(String paramString, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
        return convCharArr(paramString.toCharArray(), paramInt1, paramInt2, paramArrayOfbyte, paramInt3, paramInt4);
    }

    public int getMaxBytesPerChar() {
        return 1;
    }
}