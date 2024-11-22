package com.tmax.tibero.jdbc.data.charset;

public class UTF8ByteToCharConverter {
    protected boolean subMode;

    protected char[] subChars = new char[]{'?'};

    private byte[] savedBytes = new byte[5];

    public int convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) {
        int charOff = 0;
        int byteOff = 0;
        int savedSize = 0;
        char[] arrayOfChar = new char[2];
        int b2 = 0;
        if (savedSize != 0) {
            byte[] arrayOfByte = new byte[paramInt2 - paramInt1 + savedSize];
            for (byte b = 0; b < savedSize; b++)
                arrayOfByte[b] = this.savedBytes[b];
            System.arraycopy(paramArrayOfbyte, paramInt1, arrayOfByte, savedSize, paramInt2 - paramInt1);
            paramArrayOfbyte = arrayOfByte;
            paramInt1 = 0;
            paramInt2 = arrayOfByte.length;
            b2 = -savedSize;
            savedSize = 0;
        }
        charOff = paramInt3;
        byteOff = paramInt1;
        while (byteOff < paramInt2) {
            byte b3;
            int k = byteOff;
            int m = paramArrayOfbyte[byteOff++] & 0xFF;
            if ((m & 0x80) == 0) {
                arrayOfChar[0] = (char) m;
                b3 = 1;
            } else if ((m & 0xE0) == 192) {
                if (byteOff >= paramInt2) {
                    savedSize = 1;
                    this.savedBytes[0] = (byte) m;
                    break;
                }
                int n = paramArrayOfbyte[byteOff++] & 0xFF;
                if ((n & 0xC0) != 128) {
                    byteOff += b2;
                    arrayOfChar[0] = this.subChars[0];
                    b3 = 1;
                } else {
                    arrayOfChar[0] = (char) ((m & 0x1F) << 6 | n & 0x3F);
                    b3 = 1;
                }
            } else if ((m & 0xF0) == 224) {
                if (byteOff + 1 >= paramInt2) {
                    this.savedBytes[0] = (byte) m;
                    if (byteOff >= paramInt2) {
                        savedSize = 1;
                        break;
                    }
                    savedSize = 2;
                    this.savedBytes[1] = paramArrayOfbyte[byteOff++];
                    break;
                }
                int n = paramArrayOfbyte[byteOff++] & 0xFF;
                int i1 = paramArrayOfbyte[byteOff++] & 0xFF;
                if ((n & 0xC0) != 128 || (i1 & 0xC0) != 128) {
                    byteOff += b2;
                    arrayOfChar[0] = this.subChars[0];
                    b3 = 1;
                } else {
                    arrayOfChar[0] = (char) ((m & 0xF) << 12 | (n & 0x3F) << 6 | i1 & 0x3F);
                    b3 = 1;
                }
            } else if ((m & 0xF8) == 240) {
                if (byteOff + 2 >= paramInt2) {
                    this.savedBytes[0] = (byte) m;
                    if (byteOff >= paramInt2) {
                        savedSize = 1;
                        break;
                    }
                    if (byteOff + 1 >= paramInt2) {
                        savedSize = 2;
                        this.savedBytes[1] = paramArrayOfbyte[byteOff++];
                        break;
                    }
                    savedSize = 3;
                    this.savedBytes[1] = paramArrayOfbyte[byteOff++];
                    this.savedBytes[2] = paramArrayOfbyte[byteOff++];
                    break;
                }
                int n = paramArrayOfbyte[byteOff++] & 0xFF;
                int i1 = paramArrayOfbyte[byteOff++] & 0xFF;
                int i2 = paramArrayOfbyte[byteOff++] & 0xFF;
                if ((n & 0xC0) != 128 || (i1 & 0xC0) != 128 || (i2 & 0xC0) != 128) {
                    byteOff += b2;
                    arrayOfChar[0] = this.subChars[0];
                    b3 = 1;
                } else {
                    int i3 = (0x7 & m) << 18 | (0x3F & n) << 12 | (0x3F & i1) << 6 | 0x3F & i2;
                    arrayOfChar[0] = (char) ((i3 - 65536) / 1024 + 55296);
                    arrayOfChar[1] = (char) ((i3 - 65536) % 1024 + 56320);
                    b3 = 2;
                }
            } else {
                byteOff += b2;
                arrayOfChar[0] = this.subChars[0];
                b3 = 1;
            }
            if (charOff + b3 > paramInt4) {
                byteOff = k;
                byteOff += b2;
                arrayOfChar[0] = this.subChars[0];
                b3 = 1;
            }
            for (byte b4 = 0; b4 < b3; b4++)
                paramArrayOfchar[charOff + b4] = arrayOfChar[b4];
            charOff += b3;
        }
        byteOff += b2;
        return charOff - paramInt3;
    }
}