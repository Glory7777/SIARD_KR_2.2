package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;

import java.sql.SQLException;

public class EUCJPCharToByteConverter extends JIS0208CharToByteConverter {
    private JIS0201CharToByteConverter JIS0201cb = new JIS0201CharToByteConverter();

    private JIS0212CharToByteConverter JIS0212cb = new JIS0212CharToByteConverter();

    short[] JIS0208Index1 = getIndex1();

    String[] JIS0208Index2 = getIndex2();

    final int MAX_BYTE_SIZE = 3;

    static final int[][] EUC_JISX0213__COMP_TABLE_DATA = new int[][]{
            {44004, 44005}, {44000, 44006}, {43484, 43972}, {43960, 43976}, {43959, 43978}, {43952, 43980}, {43971, 43982}, {43960, 43977}, {43959, 43979}, {43952, 43981},
            {43971, 43983}, {42155, 42231}, {42157, 42232}, {42159, 42233}, {42161, 42234}, {42163, 42235}, {42411, 42487}, {42413, 42488}, {42415, 42489}, {42417, 42490},
            {42419, 42491}, {42427, 42492}, {42436, 42493}, {42440, 42494}, {42741, 42744}};

    static final int[] EUC_JISX0213__COMBINED_UNICODE_TABLE = new int[]{
            42231, 42232, 42233, 42234, 42235, 42487, 42488, 42489, 42490, 42491,
            42492, 42493, 42494, 42744, 43972, 43976, 43977, 43978, 43979, 43980,
            43981, 43982, 43983, 44005, 44006};

    static final int[] EUC_JISX0213__CJK_EXT_B_TABLE = new int[]{
            44706, 44994, 45004, 45024, 45051, 53204, 53219, 53230, 62906, 62962,
            63145, 63154, 63200, 63468, 63742, 63913, 63943, 63956, 63982, 64221,
            64435, 64457, 64492, 64713, 64721, 65254, 9413025, 9413035, 9413038, 9413046,
            9413062, 9413104, 9413111, 9413113, 9413538, 9413541, 9413543, 9413553, 9413554, 9413560,
            9413567, 9413569, 9413578, 9413586, 9413587, 9413593, 9413596, 9413623, 9413802, 9413809,
            9413810, 9413818, 9413821, 9413849, 9413852, 9413854, 9413859, 9413866, 9413867, 9413874,
            9413876, 9413877, 9414053, 9414066, 9414078, 9414084, 9414087, 9414101, 9414102, 9414142,
            9414832, 9414839, 9414840, 9414842, 9414843, 9414847, 9414848, 9414853, 9414856, 9414858,
            9414859, 9414875, 9414886, 9414892, 9415842, 9415851, 9415856, 9415888, 9415909, 9415917,
            9415922, 9416100, 9416105, 9416106, 9416114, 9416116, 9416117, 9416121, 9416150, 9416189,
            9416355, 9416356, 9416378, 9416380, 9416381, 9416386, 9416387, 9416388, 9416391, 9416393,
            9416405, 9416406, 9416407, 9416411, 9416439, 9416440, 9416618, 9416639, 9416640, 9416642,
            9416643, 9416654, 9416665, 9416673, 9416681, 9416682, 9416688, 9416693, 9432739, 9432756,
            9432777, 9432796, 9432798, 9432799, 9432800, 9433010, 9433031, 9433037, 9433057, 9433060,
            9433250, 9433267, 9433273, 9433299, 9433339, 9433518, 9433520, 9433525, 9433540, 9433565,
            9433569, 9433574, 9433577, 9433589, 9433591, 9433594, 9433761, 9433763, 9433764, 9433768,
            9433772, 9433789, 9433800, 9433819, 9433845, 9433846, 9434034, 9434045, 9434046, 9434048,
            9434066, 9434077, 9434078, 9434099, 9434100, 9434101, 9434103, 9434107, 9434109, 9434274,
            9434276, 9434279, 9434286, 9434287, 9434292, 9434293, 9434301, 9434306, 9434319, 9434345,
            9434347, 9434354, 9434357, 9434361, 9434549, 9434554, 9434566, 9434582, 9434584, 9434586,
            9434589, 9434591, 9434595, 9434602, 9434608, 9434611, 9434820, 9434830, 9434845, 9434869,
            9434878, 9435041, 9435042, 9435059, 9435062, 9435108, 9435109, 9435115, 9435118, 9435123,
            9435305, 9435306, 9435308, 9435316, 9435324, 9435326, 9435330, 9435350, 9435363, 9435383,
            9435385, 9435386, 9435557, 9435567, 9435570, 9435577, 9435586, 9435592, 9435609, 9435614,
            9435622, 9435627, 9435642, 9435646, 9435809, 9435820, 9435823, 9435855, 9435856, 9435863,
            9435877, 9435878, 9435889, 9435890, 9435902, 9436065, 9436076, 9436077, 9436086, 9436087,
            9436093, 9436094, 9436110, 9436111, 9436119, 9436122, 9436124, 9436125, 9436129, 9436133,
            9436135, 9436137, 9436145, 9436322, 9436323, 9436344, 9436354, 9436364, 9436374, 9436377,
            9436381, 9436406, 9436588, 9436619, 9436620, 9436633, 9436635, 9436637, 9436647, 9436653,
            9436656, 9436837, 9436841, 9436843, 9436850, 9436853, 9436883, 9436888, 9436890, 9436910,
            9436912, 9436914, 9436918};

    public boolean canConvert(char paramChar) throws SQLException {
        byte[] arrayOfByte = new byte[3];
        return !(convSingleByte(paramChar, arrayOfByte) == 0 && convDoubleByte(paramChar) == 0);
    }

    protected int convDoubleByte(char paramChar) throws SQLException {
        char c = paramChar;
        if (c == '\u301C' || c == '\uFF5E') {
            char c1 = '\u00A1';
            char c2 = '\u00C1';
            return c1 << 8 | c2;
        }
        if (c == '\u2015')
            c = '\u2014';
        try {
            int i = this.JIS0208Index1[(c & 0xFF00) >> 8] << 8;
            char c1 = this.JIS0208Index2[i >> 12].charAt((i & 0xFFF) + (c & 0xFF));
            if (c1 != '\000')
                return c1 + 32896;
            int j = this.JIS0212cb.convDoubleByte(c);
            return (j == 0) ? j : (j + 9404544);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw TbError.newSQLException(-590743, arrayIndexOutOfBoundsException.getMessage());
        }
    }

    public int getMaxBytesPerChar() {
        return 3;
    }

    protected int convSingleByte(char paramChar, byte[] paramArrayOfbyte) {
        if (paramChar == '\000') {
            paramArrayOfbyte[0] = 0;
            return 1;
        }
        byte b;
        if ((b = this.JIS0201cb.getNative(paramChar)) == 0)
            return 0;
        if (b > 0 && b < 128) {
            paramArrayOfbyte[0] = b;
            return 1;
        }
        paramArrayOfbyte[0] = -114;
        paramArrayOfbyte[1] = b;
        return 2;
    }

    public int convCharArr(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
        int i = 0;
        int j = 0;
        byte[] arrayOfByte = new byte[3];
        j = paramInt3;
        i = paramInt1;
        int k = 0;
        int m = 0;
        int n = 0;
        int[] arrayOfInt = JIS0213CharToByteConverter.JISX0213__CJK_EXT_B_DATA;
        while (i < paramInt2) {
            char c = paramArrayOfchar[i];
            int i1 = convSingleByte(c, arrayOfByte);
            m = k;
            k = parse(c, paramArrayOfchar, i, paramInt2);
            if ((k & 0xFFFF0000) >> 16 == 2)
                for (byte b1 = 0; b1 < arrayOfInt.length; b1++) {
                    if (arrayOfInt[b1] == k) {
                        int i2 = EUC_JISX0213__CJK_EXT_B_TABLE[b1];
                        if (i2 > 65535) {
                            arrayOfByte[0] = (byte) ((i2 & 0xFF0000) >> 16);
                            arrayOfByte[1] = (byte) ((i2 & 0xFF00) >> 8);
                            arrayOfByte[2] = (byte) (i2 & 0xFF);
                            i1 = 3;
                        } else {
                            arrayOfByte[0] = (byte) ((i2 & 0xFF00) >> 8);
                            arrayOfByte[1] = (byte) (i2 & 0xFF);
                            i1 = 2;
                        }
                        i++;
                        break;
                    }
                }
            if (i1 == 0) {
                int i2 = convDoubleByte(c);
                if (i2 != 0) {
                    if (i2 == 9415105 && paramInt2 - i > 1 && paramArrayOfchar[i + 1] == '\u0300') {
                        arrayOfByte[0] = -85;
                        arrayOfByte[1] = -60;
                        i1 = 2;
                        i++;
                    } else if ((i2 & 0xFF0000) == 0) {
                        arrayOfByte[0] = (byte) ((i2 & 0xFF00) >> 8);
                        arrayOfByte[1] = (byte) (i2 & 0xFF);
                        i1 = 2;
                    } else {
                        arrayOfByte[0] = -113;
                        arrayOfByte[1] = (byte) ((i2 & 0xFF00) >> 8);
                        arrayOfByte[2] = (byte) (i2 & 0xFF);
                        i1 = 3;
                    }
                } else {
                    if (m > 0) {
                        int i3 = 0;
                        int i4 = m << 16 | k;
                        int[][] arrayOfInt1 = JIS0213ByteToCharConverter.JISX0213_TO_UCS__COMBINING;
                        for (byte b1 = 0; b1 < arrayOfInt1.length; b1++) {
                            if (i4 == (arrayOfInt1[b1][0] << 16 | arrayOfInt1[b1][1])) {
                                i3 = EUC_JISX0213__COMBINED_UNICODE_TABLE[b1];
                                break;
                            }
                        }
                        if (i3 > 0) {
                            paramArrayOfbyte[j - n] = (byte) (i3 >> 8 & 0xFF | 0x80);
                            paramArrayOfbyte[j - n + 1] = (byte) (i3 & 0xFF | 0x80);
                            j -= n - 2;
                            continue;
                        }
                    }
                    i2 = JIS0213CharToByteConverter.convFromUCS4(k);
                    if (i2 != 0) {
                        byte b1;
                        byte b2;
                        if (i2 == 741) {
                            b1 = 1;
                            b2 = 0;
                        } else if (i2 == 745) {
                            b1 = 1;
                            b2 = 1;
                        } else if (i2 == 768) {
                            b1 = 2;
                            b2 = 5;
                        } else if (i2 == 769) {
                            b1 = 7;
                            b2 = 4;
                        } else if (i2 == 12442) {
                            b1 = 11;
                            b2 = 14;
                        } else {
                            b1 = 0;
                            b2 = -1;
                        }
                        int i3 = i2 | 0x8080;
                        while (b2 > 0 && EUC_JISX0213__COMP_TABLE_DATA[b1][0] != i3) {
                            b1++;
                            b2--;
                        }
                        if (b2 > 0) {
                            i3 = EUC_JISX0213__COMP_TABLE_DATA[b1][1];
                            arrayOfByte[0] = (byte) (i3 >> 8 & 0xFF);
                            arrayOfByte[1] = (byte) (i3 & 0xFF);
                            i1 += 2;
                        } else if ((i2 & 0x8000) != 0) {
                            arrayOfByte[0] = -113;
                            arrayOfByte[1] = (byte) (i2 >> 8 & 0xFF | 0x80);
                            arrayOfByte[2] = (byte) (i2 & 0xFF | 0x80);
                            i1 = 3;
                        } else {
                            arrayOfByte[0] = (byte) (i2 >> 8 & 0xFF | 0x80);
                            arrayOfByte[1] = (byte) (i2 & 0xFF | 0x80);
                            i1 = 2;
                        }
                    } else {
                        throw TbError.newSQLException(-590714, "unknown character");
                    }
                }
            }
            n = i1;
            if (paramInt4 - j < i1)
                throw TbError.newSQLException(-590714, "Conversion buffer overflow");
            for (byte b = 0; b < i1; b++)
                paramArrayOfbyte[j++] = arrayOfByte[b];
            continue;
        }
        return j - paramInt3;
    }
}