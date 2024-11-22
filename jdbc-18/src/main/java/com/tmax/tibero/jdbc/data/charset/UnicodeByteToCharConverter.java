package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;

import java.sql.SQLException;

public class UnicodeByteToCharConverter {
    protected boolean subMode = true;

    protected char[] subChars = new char[]{'\uFFFD'};

    int originalByteOrder = 0;

    boolean usesMark = true;

    public UnicodeByteToCharConverter() {
    }

    protected UnicodeByteToCharConverter(int paramInt, boolean paramBoolean) {
    }

    public int convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
        int m;
        int byteOrder = 1;
        boolean bool1 = false;
        boolean bool2 = false;
        int i = 0;
        boolean bool3 = false;
        if (paramInt1 >= paramInt2)
            return 0;
        byte b2 = 0;
        int j = paramInt1;
        int k = paramInt3;
        if (bool3) {
            m = i & 0xFF;
            bool3 = false;
        } else {
            m = paramArrayOfbyte[j++] & 0xFF;
        }
        b2 = 1;
        if (bool1 && !bool2 && j < paramInt2) {
            int n = paramArrayOfbyte[j++] & 0xFF;
            b2 = 2;
            char c = (char) (m << 8 | n);
            byte b = 0;
            if (c == '\uFEFF') {
                b = 1;
            } else if (c == '\uFFFE') {
                b = 2;
            }
            if (byteOrder == 0) {
                if (b == 0)
                    throw TbError.newSQLException(-590745);
                byteOrder = b;
                if (j < paramInt2) {
                    m = paramArrayOfbyte[j++] & 0xFF;
                    b2 = 1;
                }
            } else if (b == 0) {
                j--;
                b2 = 1;
            } else if (byteOrder == b) {
                if (j < paramInt2) {
                    m = paramArrayOfbyte[j++] & 0xFF;
                    b2 = 1;
                }
            } else {
                throw TbError.newSQLException(-590746);
            }
            bool2 = true;
        }
        while (j < paramInt2) {
            char c;
            int n = paramArrayOfbyte[j++] & 0xFF;
            b2 = 2;
            if (byteOrder == 1) {
                c = (char) (m << 8 | n);
            } else {
                c = (char) (n << 8 | m);
            }
            if (c == '\uFFFE')
                throw TbError.newSQLException(-590747);
            if (k >= paramInt4)
                throw TbError.newSQLException(-590744, k + " >= " + paramInt4);
            paramArrayOfchar[k++] = c;
            if (j < paramInt2) {
                m = paramArrayOfbyte[j++] & 0xFF;
                b2 = 1;
            }
        }
        if (b2 == 1) {
            i = m;
            bool3 = true;
        }
        return k - paramInt3;
    }
}