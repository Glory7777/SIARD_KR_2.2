package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class ISO8859P15CharToByteConverter {
    protected boolean subMode = true;

    protected byte[] subBytes = new byte[]{63};

    private static final char[] UNICODE_TO_ISO8859P15_IDX_PAGE00 = new char[]{
            '\u00A0', '\u00A1', '\u00A2', '\u00A3', Character.MIN_VALUE, '\u00A5', Character.MIN_VALUE, '\u00A7', Character.MIN_VALUE, '\u00A9',
            '\u00AA', '\u00AB', '\u00AC', '\u00AD', '\u00AE', '\u00AF', '\u00B0', '\u00B1', '\u00B2', '\u00B3',
            Character.MIN_VALUE, '\u00B5', '\u00B6', '\u00B7', Character.MIN_VALUE, '\u00B9', '\u00BA', '\u00BB', Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, '\u00BF'};

    private static final char[] UNICODE_TO_ISO8859P15_IDX_PAGE01 = new char[]{
            Character.MIN_VALUE, Character.MIN_VALUE, '\u00BC', '\u00BD', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u00A6', '\u00A8', Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            '\u00BE', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u00B4', '\u00B8', Character.MIN_VALUE};

    public int convCharArr(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
        int i = paramInt1;
        int j = paramInt3;
        while (i < paramInt2 && j < paramInt4) {
            int c = paramArrayOfchar[i];
            if (c < '\u00A0') {
                paramArrayOfbyte[j++] = (byte) c;
            } else {
                char b;
                if (c >= '\u00A0' && c < '\u00C0') {
                    b = UNICODE_TO_ISO8859P15_IDX_PAGE00[c - 160];
                } else if (c >= '\u00C0' && c < '\u0100') {
                    b = (char) c;
                } else if (c >= '\u0150' && c < '\u0180') {
                    b = UNICODE_TO_ISO8859P15_IDX_PAGE01[c - 336];
                } else if (c == '\u20AC') {
                    b = 164;
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
