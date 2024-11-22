package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;

import java.sql.SQLException;

public class TCVN3ByteToCharConverter {
    protected boolean subMode = true;

    protected char[] subChars = new char[]{'?'};

    private static final char[] TCVN3_TO_UNICODE_PAGE = new char[]{
            '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD',
            '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD',
            '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD',
            '\uFFFD', '\uFFFD', '\uFFFD', '\u0102', '\u00C2', '\u00CA', '\u00D4', '\u01A0', '\u01AF', '\u0110',
            '\u0103', '\u00E2', '\u00EA', '\u00F4', '\u01A1', '\u01B0', '\u0111', '\uFFFD', '\uFFFD', '\uFFFD',
            '\uFFFD', '\uFFFD', '\uFFFD', '\u00E0', '\u1EA3', '\u00E3', '\u00E1', '\u1EA1', '\uFFFD', '\u1EB1',
            '\u1EB3', '\u1EB5', '\u1EAF', '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD', '\uFFFD',
            '\u1EB7', '\u1EA7', '\u1EA9', '\u1EAB', '\u1EA5', '\u1EAD', '\u00E8', '\uFFFD', '\u1EBB', '\u1EBD',
            '\u00E9', '\u1EB9', '\u1EC1', '\u1EC3', '\u1EC5', '\u1EBF', '\u1EC7', '\u00EC', '\u1EC9', '\uFFFD',
            '\uFFFD', '\uFFFD', '\u0129', '\u00ED', '\u1ECB', '\u00F2', '\uFFFD', '\u1ECF', '\u00F5', '\u00F3',
            '\u1ECD', '\u1ED3', '\u1ED5', '\u1ED7', '\u1ED1', '\u1ED9', '\u1EDD', '\u1EDF', '\u1EE1', '\u1EDB',
            '\u1EE3', '\u00F9', '\uFFFD', '\u1EE7', '\u0169', '\u00FA', '\u1EE5', '\u1EEB', '\u1EED', '\u1EEF',
            '\u1EE9', '\u1EF1', '\u1EF3', '\u1EF7', '\u1EF9', '\u00FD', '\u1EF5', '\uFFFD'};

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
        int j;
        for (j = paramInt3; i < paramInt2 && j < paramInt4; j++) {
            int k = decodeFromUcs((byte) 0, paramArrayOfbyte[i]);
            if (k < 128) {
                paramArrayOfchar[j] = (char) k;
            } else {
                int m = k - 128;
                k = TCVN3_TO_UNICODE_PAGE[m];
                if (k != 65533) {
                    paramArrayOfchar[j] = (char) k;
                } else if (this.subMode) {
                    encodeUCharToUCS2(paramArrayOfchar, j, this.subChars[0]);
                } else {
                    throw TbError.newSQLException(-590742, k);
                }
            }
            i++;
        }
        return j - paramInt3;
    }
}