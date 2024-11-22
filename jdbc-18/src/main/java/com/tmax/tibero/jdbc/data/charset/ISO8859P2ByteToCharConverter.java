package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class ISO8859P2ByteToCharConverter {
    protected boolean subMode = true;

    protected char[] subChars = new char[]{'?'};

    private static final char[] ISO8859P2_TO_UNICODE_PAGE = new char[]{
            '\u00A0', '\u0104', '\u02D8', '\u0141', '\u00A4', '\u013D', '\u015A', '\u00A7', '\u00A8', '\u0160',
            '\u015E', '\u0164', '\u0179', '\u00AD', '\u017D', '\u017B', '\u00B0', '\u0105', '\u02DB', '\u0142',
            '\u00B4', '\u013E', '\u015B', '\u02C7', '\u00B8', '\u0161', '\u015F', '\u0165', '\u017A', '\u02DD',
            '\u017E', '\u017C', '\u0154', '\u00C1', '\u00C2', '\u0102', '\u00C4', '\u0139', '\u0106', '\u00C7',
            '\u010C', '\u00C9', '\u0118', '\u00CB', '\u011A', '\u00CD', '\u00CE', '\u010E', '\u0110', '\u0143',
            '\u0147', '\u00D3', '\u00D4', '\u0150', '\u00D6', '\u00D7', '\u0158', '\u016E', '\u00DA', '\u0170',
            '\u00DC', '\u00DD', '\u0162', '\u00DF', '\u0155', '\u00E1', '\u00E2', '\u0103', '\u00E4', '\u013A',
            '\u0107', '\u00E7', '\u010D', '\u00E9', '\u0119', '\u00EB', '\u011B', '\u00ED', '\u00EE', '\u010F',
            '\u0111', '\u0144', '\u0148', '\u00F3', '\u00F4', '\u0151', '\u00F6', '\u00F7', '\u0159', '\u016F',
            '\u00FA', '\u0171', '\u00FC', '\u00FD', '\u0163', '\u02D9'};

    private int decodeFromUcs(byte paramByte1, byte paramByte2) {
        return (paramByte1 << 8) + (paramByte2 & 0xFF);
    }

    private void encodeUCharToUCS2(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
        byte high = 0;
        byte b = (byte) paramInt2;
        paramArrayOfchar[paramInt1] = (char) high;
        paramArrayOfchar[paramInt1++] = (char) b;
    }

    public int convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
        int i = paramInt1;
        int j = paramInt3;
        while (i < paramInt2) {
            int k = decodeFromUcs((byte) 0, paramArrayOfbyte[i]);
            if (k < 160) {
                paramArrayOfchar[j++] = (char) k;
                i++;
                continue;
            }
            k = ISO8859P2_TO_UNICODE_PAGE[k - 160];
            paramArrayOfchar[j++] = (char) k;
            i++;
        }
        return j - paramInt3;
    }
}