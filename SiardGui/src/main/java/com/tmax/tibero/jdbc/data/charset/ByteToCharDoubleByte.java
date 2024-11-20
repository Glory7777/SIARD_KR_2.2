package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;

import java.sql.SQLException;

public class ByteToCharDoubleByte {
    protected boolean subMode = true;

    protected char[] subChars = new char[]{'?'};

    protected short[] index1;

    protected String[] index2;

    protected int start;

    protected int end;

    protected static final char REPLACE_CHAR = '\uFFFD';

    public int convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
        int charOff = 0;
        int byteOff = 0;
        byte savedByte = 0;
        charOff = paramInt3;
        for (byteOff = paramInt1; byteOff < paramInt2;) {
            byte b1;
            int k;
            if (savedByte == 0) {
                k = paramArrayOfbyte[byteOff];
                b1 = 1;
            } else {
                k = savedByte;
                savedByte = 0;
                b1 = 0;
            }
            char c = convSingleByte(k);
            if (c == '\uFFFD') {
                if (byteOff + b1 >= paramInt2) {
                    savedByte = (byte) k;
                    byteOff += b1;
                    break;
                }
                k &= 0xFF;
                int m = paramArrayOfbyte[byteOff + b1] & 0xFF;
                b1++;
                c = getUnicode(k, m);
            }
            if (c == '\uFFFD')
                if (this.subMode) {
                    c = this.subChars[0];
                } else {
                    c = this.subChars[0];
                }
            if (charOff >= paramInt4)
                c = this.subChars[0];
            paramArrayOfchar[charOff++] = c;
            byteOff += b1;
        }
        return charOff - paramInt3;
    }

    protected char convSingleByte(int paramInt) {
        return (paramInt >= 0) ? (char) paramInt : '\uFFFD';
    }

    public short[] getIndex1() {
        return this.index1;
    }

    public String[] getIndex2() {
        return this.index2;
    }

    protected char getUnicode(int paramInt1, int paramInt2) throws SQLException {
        if (paramInt1 < 0 || paramInt1 > this.index1.length || paramInt2 < this.start || paramInt2 > this.end)
            return '\uFFFD';
        try {
            int i = (this.index1[paramInt1] & 0xF) * (this.end - this.start + 1) + paramInt2 - this.start;
            return this.index2[this.index1[paramInt1] >> 4].charAt(i);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw TbError.newSQLException(-590743, arrayIndexOutOfBoundsException.getMessage());
        }
    }
}