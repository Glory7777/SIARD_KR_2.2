package com.tmax.tibero.jdbc.data.charset;

public class MS950ExtByteToCharConverter {
    private static final char[] CP950EXT_TO_UNICODE_PAGEF9 = new char[]{
            '\u7881', '\u92B9', '\u88CF', '\u58BB', '\u6052', '\u7CA7', '\u5AFA', '\u2554', '\u2566', '\u2557',
            '\u2560', '\u256C', '\u2563', '\u255A', '\u2569', '\u255D', '\u2552', '\u2564', '\u2555', '\u255E',
            '\u256A', '\u2561', '\u2558', '\u2567', '\u255B', '\u2553', '\u2565', '\u2556', '\u255F', '\u256B',
            '\u2562', '\u2559', '\u2568', '\u255C', '\u2551', '\u2550', '\u256D', '\u256E', '\u2570', '\u256F',
            '\u2593'};

    protected boolean subMode = true;

    protected char[] subChars = new char[]{'?'};

    public int convert(byte paramByte1, byte paramByte2, char[] paramArrayOfchar, int paramInt) {
        if (paramByte1 == -7 && ((paramByte2 >= 64 && paramByte2 < Byte.MAX_VALUE) || (paramByte2 >= -95 && paramByte2 < -1))) {
            int b = ((paramByte2 & 0xFF) >= 161) ? 98 : 64;
            int i = 157 * ((paramByte1 & 0xFF) - 161) + (paramByte2 & 0xFF) - b;
            char c = '\uFFFD';
            if (i >= 13932 && i < 13973)
                c = CP950EXT_TO_UNICODE_PAGEF9[i - 13932];
            if (c != '\uFFFD') {
                paramArrayOfchar[paramInt] = (char) c;
                return 0;
            }
        }
        return -1;
    }
}