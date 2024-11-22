package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;

import java.sql.SQLException;

public class CharToByteDoubleByte {
    protected static final char MIN_HIGH = '\uD800';

    protected static final char MAX_HIGH = '\uDBFF';

    protected static final char MIN_LOW = '\uDC00';

    protected static final char MAX_LOW = '\uDFFF';

    protected static final char MIN = '\uD800';

    protected static final char MAX = '\uDFFF';

    protected boolean subMode = true;

    protected byte[] subBytes = new byte[]{63};

    protected short[] index1;

    protected String[] index2;

    protected final int MAX_BYTE_SIZE = 2;

    public boolean canConvert(char paramChar) throws SQLException {
        byte[] arrayOfByte = new byte[2];
        return (paramChar == '\000' || convSingleByte(paramChar, arrayOfByte) != 0) ? true : ((convDoubleByte(paramChar) != 0));
    }

    private void checkOverflow(int paramInt1, int paramInt2, int paramInt3) throws SQLException {
        if (paramInt1 + paramInt2 > paramInt3)
            throw TbError.newSQLException(-590744, paramInt1 + " + " + paramInt2 + " > " + paramInt3);
    }

    public int convCharArr(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
        int charOff = 0;
        int byteOff = 0;
        char highHalfZoneCode = Character.MIN_VALUE;
        byte[] arrayOfByte = new byte[2];
        charOff = paramInt1;
        byteOff = paramInt3;
        if (highHalfZoneCode != '\000') {
            highHalfZoneCode = Character.MIN_VALUE;
            if (this.subMode) {
                int k = this.subBytes.length;
                checkOverflow(byteOff, k, paramInt4);
                for (byte b = 0; b < this.subBytes.length; b++)
                    paramArrayOfbyte[byteOff++] = this.subBytes[b];
                charOff++;
            } else {
                if (paramArrayOfchar[paramInt1] >= '\uDC00' && paramArrayOfchar[paramInt1] <= '\uDFFF')
                    throw TbError.newSQLException(-590742, paramArrayOfchar[paramInt1]);
                throw TbError.newSQLException(-590743, paramArrayOfchar[paramInt1]);
            }
        }
        while (charOff < paramInt2) {
            int k;
            byte b1 = 1;
            byte[] arrayOfByte1 = arrayOfByte;
            char c1 = paramArrayOfchar[charOff];
            if (c1 >= '\uD800' && c1 <= '\uDBFF') {
                if (charOff + 1 >= paramInt2) {
                    highHalfZoneCode = c1;
                    break;
                }
                c1 = paramArrayOfchar[charOff + 1];
                if (this.subMode) {
                    arrayOfByte1 = this.subBytes;
                    k = this.subBytes.length;
                    b1 = 2;
                } else {
                    if (c1 >= '\uDC00' && c1 <= '\uDFFF')
                        throw TbError.newSQLException(-590742, c1);
                    throw TbError.newSQLException(-590743, c1);
                }
            } else if (c1 >= '\uDC00' && c1 <= '\uDFFF') {
                if (this.subMode) {
                    arrayOfByte1 = this.subBytes;
                    k = this.subBytes.length;
                } else {
                    throw TbError.newSQLException(-590743, c1);
                }
            } else {
                k = convSingleByte(c1, arrayOfByte1);
                if (k == 0) {
                    int m = convDoubleByte(c1);
                    if (m != 0) {
                        arrayOfByte1[0] = (byte) ((m & 0xFF00) >> 8);
                        arrayOfByte1[1] = (byte) (m & 0xFF);
                        k = 2;
                    } else if (this.subMode) {
                        arrayOfByte1 = this.subBytes;
                        k = this.subBytes.length;
                    } else {
                        throw TbError.newSQLException(-590742, c1);
                    }
                }
            }
            checkOverflow(byteOff, k, paramInt4);
            for (byte b2 = 0; b2 < k; b2++)
                paramArrayOfbyte[byteOff++] = arrayOfByte1[b2];
            charOff += b1;
        }
        return byteOff - paramInt3;
    }

    protected int convSingleByte(char paramChar, byte[] paramArrayOfbyte) {
        if (paramChar < '\u0080') {
            paramArrayOfbyte[0] = (byte) (paramChar & 0x7F);
            return 1;
        }
        return 0;
    }

    protected int convSingleByte(char paramChar) {
        return (paramChar < '\u0080') ? 1 : 0;
    }

    public int convString(String paramString, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
        return convCharArr(paramString.toCharArray(), paramInt1, paramInt2, paramArrayOfbyte, paramInt3, paramInt4);
    }

    public short[] getIndex1() {
        return this.index1;
    }

    public String[] getIndex2() {
        return this.index2;
    }

    public int getMaxBytesPerChar() {
        return 2;
    }

    protected int convDoubleByte(char paramChar) throws SQLException {
        try {
            int i = this.index1[(paramChar & 0xFF00) >> 8] << 8;
            return this.index2[i >> 12].charAt((i & 0xFFF) + (paramChar & 0xFF));
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw TbError.newSQLException(-590743, arrayIndexOutOfBoundsException.getMessage());
        }
    }

    protected int parse(char paramChar, char[] paramArrayOfchar, int paramInt1, int paramInt2) throws SQLException {
        if (isHigh(paramChar)) {
            if (paramInt2 - paramInt1 < 2)
                throw TbError.newSQLException(-590714, "Malformed input");
            char c = paramArrayOfchar[paramInt1 + 1];
            if (isLow(c))
                return toUCS4(paramChar, c);
            throw TbError.newSQLException(-590714, "Malformed input");
        }
        if (isLow(paramChar))
            throw TbError.newSQLException(-590714, "Malformed input");
        return paramChar;
    }

    private boolean isHigh(int paramInt) {
        return (55296 <= paramInt && paramInt <= 56319);
    }

    private boolean isLow(int paramInt) {
        return (56320 <= paramInt && paramInt <= 57343);
    }

    public int toUCS4(char paramChar1, char paramChar2) {
        return ((paramChar1 & 0x3FF) << 10 | paramChar2 & 0x3FF) + 65536;
    }
}