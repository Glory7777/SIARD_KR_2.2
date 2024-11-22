package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class ISO8859P9CharToByteConverter {
    protected boolean subMode = true;

    protected byte[] subBytes = new byte[]{63};

    private static final char[] UNICODE_TO_ISO8859P9_IDX_PAGE00 = new char[]{
            Character.MIN_VALUE, '\u00D1', '\u00D2', '\u00D3', '\u00D4', '\u00D5', '\u00D6', '\u00D7', '\u00D8', '\u00D9',
            '\u00DA', '\u00DB', '\u00DC', Character.MIN_VALUE, Character.MIN_VALUE, '\u00DF', '\u00E0', '\u00E1', '\u00E2', '\u00E3',
            '\u00E4', '\u00E5', '\u00E6', '\u00E7', '\u00E8', '\u00E9', '\u00EA', '\u00EB', '\u00EC', '\u00ED',
            '\u00EE', '\u00EF', Character.MIN_VALUE, '\u00F1', '\u00F2', '\u00F3', '\u00F4', '\u00F5', '\u00F6', '\u00F7',
            '\u00F8', '\u00F9', '\u00FA', '\u00FB', '\u00FC', Character.MIN_VALUE, Character.MIN_VALUE, '\u00FF'};

    private static final char[] UNICODE_TO_ISO8859P9_IDX_PAGE01 = new char[]{
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u00D0', '\u00F0', Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u00DD', '\u00FD', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            '\u00DE', '\u00FE'};

    public int convCharArr(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
        int i = paramInt1;
        int j = paramInt3;
        while (i < paramInt2 && j < paramInt4) {
            char c = paramArrayOfchar[i];
            if (c < '\u00D0') {
                paramArrayOfbyte[j++] = (byte) c;
            } else {
                char b;
                if (c >= '\u00D0' && c < '\u0100') {
                    b = UNICODE_TO_ISO8859P9_IDX_PAGE00[c - 208];
                } else if (c >= '\u0118' && c < '\u0160') {
                    b = UNICODE_TO_ISO8859P9_IDX_PAGE01[c - 280];
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