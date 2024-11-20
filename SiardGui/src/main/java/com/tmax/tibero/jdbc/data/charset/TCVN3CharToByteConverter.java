package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class TCVN3CharToByteConverter {
    private static final short[] UNICODE_TO_TCVN3_IDX_PAGE1 = new short[]{
            63, 63, 162, 63, 63, 63, 63, 63, 63, 63,
            163, 63, 63, 63, 63, 63, 63, 63, 63, 63,
            164, 63, 63, 63, 63, 63, 63, 63, 63, 63,
            63, 63, 181, 184, 169, 183, 63, 63, 63, 63,
            204, 208, 170, 63, 215, 221, 63, 63, 63, 63,
            223, 227, 171, 226, 63, 63, 63, 239, 243, 63,
            63, 253, 63, 63};

    private static final char[] UNICODE_TO_TCVN3_IDX_PAGE2 = new char[]{
            '?', '?', '\u00A1', '\u00A8', '?', '?', '?', '?', '?', '?',
            '?', '?', '?', '?', '?', '?', '\u00A7', '\u00AE', '?', '?',
            '?', '?', '?', '?', '?', '?', '?', '?', '?', '?',
            '?', '?', '?', '?', '?', '?', '?', '?', '?', '?',
            '?', '\u00DC', '?', '?', '?', '?', '?', '?', '?', '?',
            '?', '?', '?', '?', '?', '?', '?', '?', '?', '?',
            '?', '?', '?', '?', '?', '?', '?', '?', '?', '?',
            '?', '?', '?', '?', '?', '?', '?', '?', '?', '?',
            '?', '?', '?', '?', '?', '?', '?', '?', '?', '?',
            '?', '?', '?', '?', '?', '?', '?', '?', '?', '?',
            '?', '?', '?', '?', '?', '\u00F2', '?', '?', '?', '?',
            '?', '?', '?', '?', '?', '?', '?', '?', '?', '?',
            '?', '?', '?', '?', '?', '?', '?', '?', '?', '?',
            '?', '?', '?', '?', '?', '?', '?', '?', '?', '?',
            '?', '?', '?', '?', '?', '?', '?', '?', '?', '?',
            '?', '?', '?', '?', '?', '?', '?', '?', '?', '?',
            '\u00A5', '\u00AC', '?', '?', '?', '?', '?', '?', '?', '?',
            '?', '?', '?', '?', '?', '\u00A6', '\u00AD', '?', '?', '?',
            '?', '?', '?', '?', '?', '?', '?', '?', '?', '?',
            '?', '?'};

    private static final char[] UNICODE_TO_TCVN3_IDX_PAGE3 = new char[]{
            '?', '\u00B9', '?', '\u00B6', '?', '\u00CA', '?', '\u00C7', '?', '\u00C8',
            '?', '\u00C9', '?', '\u00CB', '?', '\u00BE', '?', '\u00BB', '?', '\u00BC',
            '?', '\u00BD', '?', '\u00C6', '?', '\u00D1', '?', '\u00CE', '?', '\u00CF',
            '?', '\u00D5', '?', '\u00D2', '?', '\u00D3', '?', '\u00D4', '?', '\u00D6',
            '?', '\u00D8', '?', '\u00DE', '?', '\u00E4', '?', '\u00E1', '?', '\u00E8',
            '?', '\u00E5', '?', '\u00E6', '?', '\u00E7', '?', '\u00E9', '?', '\u00ED',
            '?', '\u00EA', '?', '\u00EB', '?', '\u00EC', '?', '\u00EE', '?', '\u00F4',
            '?', '\u00F1', '?', '\u00F8', '?', '\u00F5', '?', '\u00F6', '?', '\u00F7',
            '?', '\u00F9', '?', '\u00FA', '?', '\u00FE', '?', '\u00FB', '?', '\u00FC',
            '?', '?', '?', '?', '?', '?'};

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
                if ((short) c >= 192 && c < '\u00FF') {
                    b = (char) UNICODE_TO_TCVN3_IDX_PAGE1[c - 192];
                } else if (c >= '\u0100' && c < '\u01BF') {
                    b = UNICODE_TO_TCVN3_IDX_PAGE2[c - 256];
                } else if (c >= '\u1EA0' && c < '\u1EFF') {
                    b = UNICODE_TO_TCVN3_IDX_PAGE3[c - 7840];
                } else {
                    b = 63;
                }
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