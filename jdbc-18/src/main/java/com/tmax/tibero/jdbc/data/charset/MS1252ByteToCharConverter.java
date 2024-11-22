package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;

import java.sql.SQLException;

public class MS1252ByteToCharConverter {
    protected boolean subMode = true;

    protected char[] subChars = new char[]{'?'};

    private static final char[] MS1252_TO_UNICODE_PAGE = new char[]{
            '\u20AC', '\uFFFD', '\u201A', '\u0192', '\u201E', '\u2026', '\u2020', '\u2021', '\u02C6', '\u2030',
            '\u0160', '\u2039', '\u0152', '\uFFFD', '\u017D', '\uFFFD', '\uFFFD', '\u2018', '\u2019', '\u201C',
            '\u201D', '\u2022', '\u2013', '\u2014', '\u02DC', '\u2122', '\u0161', '\u203A', '\u0153', '\uFFFD',
            '\u017E', '\u0178'};

    private int decodeFromUcs(byte paramByte1, byte paramByte2) {
        return (paramByte1 << 8) + (paramByte2 & 0xFF);
    }

    private void encodeUCharToUCS2(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
        byte high = 0;
        byte low = (byte) paramInt2;
        paramArrayOfchar[paramInt1] = (char) high;
        paramArrayOfchar[paramInt1++] = (char) low;
    }

    public int convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
        int i = paramInt1;
        int j = paramInt3;
        while (i < paramInt2) {
            int k = decodeFromUcs((byte) 0, paramArrayOfbyte[i]);
            if (k < 128 || k >= 160) {
                paramArrayOfchar[j++] = (char) k;
                i++;
                continue;
            }
            int m = k - 128;
            k = MS1252_TO_UNICODE_PAGE[m];
            if (k != 65533) {
                paramArrayOfchar[j++] = (char) k;
                i++;
                continue;
            }
            if (this.subMode) {
                encodeUCharToUCS2(paramArrayOfchar, j, this.subChars[0]);
                j++;
                continue;
            }
            throw TbError.newSQLException(-590742, k);
        }
        return j - paramInt3;
    }
}