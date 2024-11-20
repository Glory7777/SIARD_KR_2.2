package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class ISO8859P2CharToByteConverter {
    protected boolean subMode = true;

    protected byte[] subBytes = new byte[]{63};

    private static final char[] UNICODE_TO_ISO8859P2_IDX_PAGE00 = new char[]{
            '\u00A0', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u00A4', Character.MIN_VALUE, Character.MIN_VALUE, '\u00A7', '\u00A8', Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u00AD', Character.MIN_VALUE, Character.MIN_VALUE, '\u00B0', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            '\u00B4', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u00B8', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u00C1', '\u00C2', Character.MIN_VALUE, '\u00C4', Character.MIN_VALUE, Character.MIN_VALUE, '\u00C7',
            Character.MIN_VALUE, '\u00C9', Character.MIN_VALUE, '\u00CB', Character.MIN_VALUE, '\u00CD', '\u00CE', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, '\u00D3', '\u00D4', Character.MIN_VALUE, '\u00D6', '\u00D7', Character.MIN_VALUE, Character.MIN_VALUE, '\u00DA', Character.MIN_VALUE,
            '\u00DC', '\u00DD', Character.MIN_VALUE, '\u00DF', Character.MIN_VALUE, '\u00E1', '\u00E2', Character.MIN_VALUE, '\u00E4', Character.MIN_VALUE,
            Character.MIN_VALUE, '\u00E7', Character.MIN_VALUE, '\u00E9', Character.MIN_VALUE, '\u00EB', Character.MIN_VALUE, '\u00ED', '\u00EE', Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u00F3', '\u00F4', Character.MIN_VALUE, '\u00F6', '\u00F7', Character.MIN_VALUE, Character.MIN_VALUE,
            '\u00FA', Character.MIN_VALUE, '\u00FC', '\u00FD', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u00C3', '\u00E3',
            '\u00A1', '\u00B1', '\u00C6', '\u00E6', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u00C8', '\u00E8',
            '\u00CF', '\u00EF', '\u00D0', '\u00F0', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            '\u00CA', '\u00EA', '\u00CC', '\u00EC', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u00C5', '\u00E5', Character.MIN_VALUE, Character.MIN_VALUE, '\u00A5', '\u00B5', Character.MIN_VALUE,
            Character.MIN_VALUE, '\u00A3', '\u00B3', '\u00D1', '\u00F1', Character.MIN_VALUE, Character.MIN_VALUE, '\u00D2', '\u00F2', Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u00D5', '\u00F5', Character.MIN_VALUE, Character.MIN_VALUE,
            '\u00C0', '\u00E0', Character.MIN_VALUE, Character.MIN_VALUE, '\u00D8', '\u00F8', '\u00A6', '\u00B6', Character.MIN_VALUE, Character.MIN_VALUE,
            '\u00AA', '\u00BA', '\u00A9', '\u00B9', '\u00DE', '\u00FE', '\u00AB', '\u00BB', Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u00D9', '\u00F9', '\u00DB', '\u00FB',
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u00AC', '\u00BC', '\u00AF',
            '\u00BF', '\u00AE', '\u00BE', Character.MIN_VALUE};

    private static final char[] UNICODE_TO_ISO8859P2_IDX_PAGE02 = new char[]{
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u00B7', Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE,
            Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '\u00A2', '\u00FF', Character.MIN_VALUE, '\u00B2', Character.MIN_VALUE, '\u00BD',
            Character.MIN_VALUE, Character.MIN_VALUE};

    public int convCharArr(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
        int i = paramInt1;
        int j = paramInt3;
        while (i < paramInt2 && j < paramInt4) {
            char c = paramArrayOfchar[i];
            if (c < '\u00A0') {
                paramArrayOfbyte[j++] = (byte) c;
            } else {
                char b;
                if (c >= '\u00A0' && c < '\u0180') {
                    b = UNICODE_TO_ISO8859P2_IDX_PAGE00[c - 160];
                } else if (c >= '\u02C0' && c < '\u02E0') {
                    b = UNICODE_TO_ISO8859P2_IDX_PAGE02[c - 704];
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