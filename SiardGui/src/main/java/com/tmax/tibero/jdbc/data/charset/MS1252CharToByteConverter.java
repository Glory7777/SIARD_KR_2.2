package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class MS1252CharToByteConverter {
    private static final short[] UNICODE_TO_MS1252_IDX_PAGE1 = new short[]{
            0, 0, 140, 156, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 138, 154, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            159, 0, 0, 0, 0, 142, 158, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 131, 0, 0, 0,
            0, 0};

    private static final char[] UNICODE_TO_MS1252_IDX_PAGE2 = new char[]{
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u0088', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u0098', Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE};

    private static final char[] UNICODE_TO_MS1252_IDX_PAGE20 = new char[]{
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u0096', '\u0097', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u0091', '\u0092',
            '\u0082', Character.MIN_VALUE, '\u0093', '\u0094', '\u0084', Character.MIN_VALUE, '\u0086', '\u0087', '\u0095', Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, '\u0085', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, '\u0089', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, '\u008B', '\u009B', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE};

    protected boolean subMode = true;

    protected byte[] subBytes = new byte[]{63};

    public boolean canConvert(char paramChar) {
        return (paramChar < '\u0080' || (paramChar >= '\u00C0' && paramChar < '\u00FF') || (paramChar >= '\u0100' && paramChar < '\u01BF') || (paramChar >= '\u1EA0' && paramChar < '\u1EFF'));
    }

    protected int convSingleByte(char paramChar, byte[] paramArrayOfbyte) {
        if (paramChar < '\u0080') {
            paramArrayOfbyte[0] = (byte) (paramChar & 0x7F);
            return 1;
        }
        return 0;
    }

    public int convCharArr(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
        int i = paramInt1;
        int j = paramInt3;
        while (i < paramInt2 && j < paramInt4) {
            char c = paramArrayOfchar[i];
            if (c < '\u0080') {
                paramArrayOfbyte[j++] = (byte) c;
            } else {
                char b;
                if (c >= '\u00A0' && c < '\u0100') {
                    b = (char) c;
                } else if ((short) c >= 336 && c < '\u0198') {
                    b = (char) UNICODE_TO_MS1252_IDX_PAGE1[c - 336];
                } else if (c >= '\u02C0' && c < '\u02E0') {
                    b = UNICODE_TO_MS1252_IDX_PAGE2[c - 704];
                } else if (c >= '\u2010' && c < '\u2040') {
                    b = UNICODE_TO_MS1252_IDX_PAGE20[c - 8208];
                } else if (c == '\u20AC') {
                    b = 128;
                } else if (c == '\u2122') {
                    b = 153;
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