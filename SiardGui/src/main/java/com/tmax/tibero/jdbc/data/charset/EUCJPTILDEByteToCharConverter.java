package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;

import java.sql.SQLException;

public class EUCJPTILDEByteToCharConverter extends JIS0208ByteToCharConverter {
    private JIS0201ByteToCharConverter JIS0201bc = new JIS0201ByteToCharConverter();

    private JIS0212ByteToCharConverter JIS0212bc = new JIS0212ByteToCharConverter();

    short[] JIS0208Index1 = getIndex1();

    String[] JIS0208Index2 = getIndex2();

    protected char decode0212(int paramInt1, int paramInt2) throws SQLException {
        return (paramInt1 == -126 && paramInt2 == 55) ? '\uFF5E' : this.JIS0212bc.getUnicode(paramInt1, paramInt2);
    }

    protected char getUnicode(int paramInt1, int paramInt2) throws SQLException {
        if (paramInt1 == 161 && paramInt2 == 193)
            return '\uFF5E';
        if ((paramInt1 & 0xFF) == 161 && (paramInt2 & 0xFF) == 189)
            return '\u2015';
        if (paramInt1 == 142)
            return this.JIS0201bc.getUnicode(paramInt2 - 256);
        if (paramInt1 < 0 || paramInt1 > (getIndex1()).length || paramInt2 < this.start || paramInt2 > this.end)
            return '\uFFFD';
        try {
            int i = (this.JIS0208Index1[paramInt1 - 128] & 0xF) * (this.end - this.start + 1) + paramInt2 - this.start;
            return this.JIS0208Index2[this.JIS0208Index1[paramInt1 - 128] >> 4].charAt(i);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw TbError.newSQLException(-590743, arrayIndexOutOfBoundsException.getMessage());
        }
    }

    public int convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
        int charOff = 0;
        int byteOff = 0;
        charOff = paramInt3;
        char outputChar = '\uFFFD';
        for (byteOff = paramInt1; ; ) {
            byte inputCharByteSize;
            if (byteOff < paramInt2) {
                boolean bool = false;
                int k = paramArrayOfbyte[byteOff] & 0xFF;
                inputCharByteSize = 1;
                if ((k & 0x80) == 0) {
                    outputChar = (char) k;
                } else {
                    int m = paramArrayOfbyte[byteOff + 1] & 0xFF;
                    if ((k & 0xFF) == 143) {
                        if (m >= 161 && m < 255) {
                            if (byteOff + 3 > paramInt2)
                                return charOff;
                            int n = paramArrayOfbyte[byteOff + 2] & 0xFF;
                            inputCharByteSize += 2;
                            outputChar = decode0212(m - 128, n - 128);
                        }
                    } else {
                        if (byteOff + 2 > paramInt2)
                            return charOff;
                        if (m >= 161 && m < 255) {
                            inputCharByteSize++;
                            outputChar = getUnicode(k, m);
                        }
                    }
                    bool = (outputChar == '\uFFFD') ? true : false;
                }
                if (bool) {
                    char[] arrayOfChar;
                    if (byteOff + 2 > paramInt2)
                        return charOff;
                    int m = paramArrayOfbyte[byteOff + 1] & 0xFF;
                    if (k == 143) {
                        if (byteOff + 3 > paramInt2)
                            return charOff;
                        int n = paramArrayOfbyte[byteOff + 2] & 0xFF;
                        arrayOfChar = JIS0213ByteToCharConverter.decode(384 + m, n ^ 0x80);
                    } else {
                        arrayOfChar = JIS0213ByteToCharConverter.decode(128 + k, m ^ 0x80);
                    }
                    if (arrayOfChar.length == 1) {
                        outputChar = arrayOfChar[0];
                    } else {
                        if (charOff + arrayOfChar.length > paramInt4)
                            return charOff - paramInt3;
                        System.arraycopy(arrayOfChar, 0, paramArrayOfchar, charOff, arrayOfChar.length);
                        charOff += arrayOfChar.length;
                        byteOff += inputCharByteSize;
                    }
                }
                if (charOff + 1 > paramInt4)
                    return charOff - paramInt3;
                paramArrayOfchar[charOff++] = outputChar;
            } else {
                break;
            }
            byteOff += inputCharByteSize;
        }
        return charOff - paramInt3;
    }
}