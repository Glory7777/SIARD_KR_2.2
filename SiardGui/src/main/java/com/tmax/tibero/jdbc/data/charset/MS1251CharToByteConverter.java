package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class MS1251CharToByteConverter {
    private static final short[] UNICODE_TO_MS1251_IDX_PAGE00 = new short[]{
            160, 0, 0, 0, 164, 0, 166, 167, 0, 169,
            0, 171, 172, 173, 174, 0, 176, 177, 0, 0,
            0, 181, 182, 183, 0, 0, 0, 187, 0, 0,
            0, 0};

    private static final char[] UNICODE_TO_MS1251_IDX_PAGE04 = new char[]{
            Character.MIN_VALUE, '\u00A8', '\u0080', '\u0081', '\u00AA', '\u00BD', '\u00B2', '\u00AF', '\u00A3', '\u008A',
            '\u008C', '\u008E', '\u008D', Character.MIN_VALUE, '\u00A1', '\u008F', '\u00C0', '\u00C1', '\u00C2', '\u00C3',
            '\u00C4', '\u00C5', '\u00C6', '\u00C7', '\u00C8', '\u00C9', '\u00CA', '\u00CB', '\u00CC', '\u00CD',
            '\u00CE', '\u00CF', '\u00D0', '\u00D1', '\u00D2', '\u00D3', '\u00D4', '\u00D5', '\u00D6', '\u00D7',
            '\u00D8', '\u00D9', '\u00DA', '\u00DB', '\u00DC', '\u00DD', '\u00DE', '\u00DF', '\u00E0', '\u00E1',
            '\u00E2', '\u00E3', '\u00E4', '\u00E5', '\u00E6', '\u00E7', '\u00E8', '\u00E9', '\u00EA', '\u00EB',
            '\u00EC', '\u00ED', '\u00EE', '\u00EF', '\u00F0', '\u00F1', '\u00F2', '\u00F3', '\u00F4', '\u00F5',
            '\u00F6', '\u00F7', '\u00F8', '\u00F9', '\u00FA', '\u00FB', '\u00FC', '\u00FD', '\u00FE', '\u00FF',
            Character.MIN_VALUE, '\u00B8', '\u0090', '\u0083', '\u00BA', '\u00BE', '\u00B3', '\u00BF', '\u00BC', '\u009A',
            '\u009C', '\u009E', '\u009D', Character.MIN_VALUE, '\u00A2', '\u009F', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u00A5', '\u00B4', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE};

    private static final char[] UNICODE_TO_MS1251_IDX_PAGE20 = new char[]{
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u0096', '\u0097', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u0091', '\u0092',
            '\u0082', Character.MIN_VALUE, '\u0093', '\u0094', '\u0084', Character.MIN_VALUE, '\u0086', '\u0087', '\u0095', Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, '\u0085', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, '\u0089', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, '\u008B', '\u009B', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE};

    protected boolean subMode = true;

    protected byte[] subBytes = new byte[]{63};

    public int convCharArr(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
        int i = paramInt1;
        int j = paramInt3;
        while (i < paramInt2 && j < paramInt4) {
            char c = paramArrayOfchar[i];
            if (c < '\u0080') {
                paramArrayOfbyte[j++] = (byte) c;
            } else {
                char b;
                if (c >= '\u00A0' && c < '\u00C0') {
                    b = (char) UNICODE_TO_MS1251_IDX_PAGE00[c - 160];
                } else if ((short) c >= 1024 && c < '\u0498') {
                    b = UNICODE_TO_MS1251_IDX_PAGE04[c - 1024];
                } else if (c >= '\u2010' && c < '\u2040') {
                    b = UNICODE_TO_MS1251_IDX_PAGE20[c - 8208];
                } else if (c == '\u20AC') {
                    b = 136;
                } else if (c == '\u2116') {
                    b = 185;
                } else if (c == '\u2122') {
                    b = 153;
                } else if (c == '\u0098') {
                    b = 152;
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