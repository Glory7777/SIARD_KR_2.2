package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;

import java.sql.SQLException;

public class CharToByteSingleByte {
    protected boolean subMode = true;

    protected byte[] subBytes = new byte[]{63};

    protected short[] index1;

    protected String index2;

    protected int mask1;

    protected int mask2;

    protected int shift;

    public CharToByteSingleByte() {
    }

    public CharToByteSingleByte(short[] paramArrayOfshort, String paramString, int paramInt1, int paramInt2, int paramInt3) {
        this.index1 = paramArrayOfshort;
        this.index2 = paramString;
        this.mask1 = paramInt1;
        this.mask2 = paramInt2;
        this.shift = paramInt3;
    }

    public CharToByteSingleByte(short[] paramArrayOfshort, String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, byte[] paramArrayOfbyte) {
        this.index1 = paramArrayOfshort;
        this.index2 = paramString;
        this.mask1 = paramInt1;
        this.mask2 = paramInt2;
        this.shift = paramInt3;
        this.subMode = paramBoolean;
        this.subBytes = paramArrayOfbyte;
    }

    public boolean canConvert(char paramChar) {
        return (this.index2.charAt(this.index1[(paramChar & this.mask1) >> this.shift] + (paramChar & this.mask2)) != '\000') ? true : ((paramChar == '\000'));
    }

    public int convert(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
        int charOff = 0;
        int byteOff = 0;
        char highHalfZoneCode = Character.MIN_VALUE;
        byte[] arrayOfByte = new byte[1];
        charOff = paramInt1;
        byteOff = paramInt3;
        if (highHalfZoneCode != '\000') {
            highHalfZoneCode = Character.MIN_VALUE;
            if (paramArrayOfchar[paramInt1] >= '\uDC00' && paramArrayOfchar[paramInt1] <= '\uDFFF')
                throw TbError.newSQLException(-590742, paramArrayOfchar[paramInt1]);
            throw TbError.newSQLException(-590743, paramArrayOfchar[paramInt1]);
        }
        while (charOff < paramInt2) {
            byte[] arrayOfByte1 = arrayOfByte;
            char c1 = paramArrayOfchar[charOff];
            int k = 1;
            byte b1 = 1;
            if (c1 >= '\uD800' && c1 <= '\uDBFF') {
                if (charOff + 1 >= paramInt2) {
                    highHalfZoneCode = c1;
                    break;
                }
                c1 = paramArrayOfchar[charOff + 1];
                if (c1 >= '\uDC00' && c1 <= '\uDFFF') {
                    if (this.subMode) {
                        arrayOfByte1 = this.subBytes;
                        k = this.subBytes.length;
                        b1 = 2;
                    } else {
                        throw TbError.newSQLException(-590742, c1);
                    }
                } else {
                    throw TbError.newSQLException(-590743, c1);
                }
            } else {
                if (c1 >= '\uDC00' && c1 <= '\uDFFF')
                    throw TbError.newSQLException(-590743, c1);
                arrayOfByte1[0] = getNative(c1);
                if (arrayOfByte1[0] == 0 && paramArrayOfchar[charOff] != '\000')
                    if (this.subMode) {
                        arrayOfByte1 = this.subBytes;
                        k = this.subBytes.length;
                    } else {
                        throw TbError.newSQLException(-590742, arrayOfByte1[0]);
                    }
            }
            if (byteOff + k > paramInt4)
                throw TbError.newSQLException(-590714, "Conversion buffer overflow");
            for (byte b2 = 0; b2 < k; b2++)
                paramArrayOfbyte[byteOff++] = arrayOfByte1[b2];
            charOff += b1;
        }
        return byteOff - paramInt3;
    }

    public short[] getIndex1() {
        return this.index1;
    }

    public String getIndex2() {
        return this.index2;
    }

    public int getMaxBytesPerChar() {
        return 1;
    }

    public byte getNative(char paramChar) {
        return (byte) this.index2.charAt(this.index1[(paramChar & this.mask1) >> this.shift] + (paramChar & this.mask2));
    }
}