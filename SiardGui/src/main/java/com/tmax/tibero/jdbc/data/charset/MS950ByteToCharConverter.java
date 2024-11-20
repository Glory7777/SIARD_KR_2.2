package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;

import java.sql.SQLException;

public class MS950ByteToCharConverter {
    private HKSCS2001ByteToCharConverter hkscs2001converter = new HKSCS2001ByteToCharConverter();

    private Big5ByteToCharConverter big5Converter = new Big5ByteToCharConverter();

    private MS950ExtByteToCharConverter ms950ExtConverter = new MS950ExtByteToCharConverter();

    protected boolean subMode = true;

    protected char[] subChars = new char[]{'?'};

    private static final char[] CP950_TO_UNICODE_PAGEA1 = new char[]{
            '\u3000', '\uFF0C', '\u3001', '\u3002', '\uFF0E', '\u2027', '\uFF1B', '\uFF1A', '\uFF1F', '\uFF01',
            '\uFE30', '\u2026', '\u2025', '\uFE50', '\uFE51', '\uFE52', '\u00B7', '\uFE54', '\uFE55', '\uFE56',
            '\uFE57', '\uFF5C', '\u2013', '\uFE31', '\u2014', '\uFE33', '\u2574', '\uFE34', '\uFE4F', '\uFF08',
            '\uFF09', '\uFE35', '\uFE36', '\uFF5B', '\uFF5D', '\uFE37', '\uFE38', '\u3014', '\u3015', '\uFE39',
            '\uFE3A', '\u3010', '\u3011', '\uFE3B', '\uFE3C', '\u300A', '\u300B', '\uFE3D', '\uFE3E', '\u3008',
            '\u3009', '\uFE3F', '\uFE40', '\u300C', '\u300D', '\uFE41', '\uFE42', '\u300E', '\u300F', '\uFE43',
            '\uFE44', '\uFE59', '\uFE5A', '\uFE5B', '\uFE5C', '\uFE5D', '\uFE5E', '\u2018', '\u2019', '\u201C',
            '\u201D', '\u301D', '\u301E', '\u2035', '\u2032', '\uFF03', '\uFF06', '\uFF0A', '\u203B', '\u00A7',
            '\u3003', '\u25CB', '\u25CF', '\u25B3', '\u25B2', '\u25CE', '\u2606', '\u2605', '\u25C7', '\u25C6',
            '\u25A1', '\u25A0', '\u25BD', '\u25BC', '\u32A3', '\u2105', '\u00AF', '\uFFE3', '\uFF3F', '\u02CD',
            '\uFE49', '\uFE4A', '\uFE4D', '\uFE4E', '\uFE4B', '\uFE4C', '\uFE5F', '\uFE60', '\uFE61', '\uFF0B',
            '\uFF0D', '\u00D7', '\u00F7', '\u00B1', '\u221A', '\uFF1C', '\uFF1E', '\uFF1D', '\u2266', '\u2267',
            '\u2260', '\u221E', '\u2252', '\u2261', '\uFE62', '\uFE63', '\uFE64', '\uFE65', '\uFE66', '\uFF5E',
            '\u2229', '\u222A', '\u22A5', '\u2220', '\u221F', '\u22BF', '\u33D2', '\u33D1', '\u222B', '\u222E',
            '\u2235', '\u2234', '\u2640', '\u2642', '\u2295', '\u2299', '\u2191', '\u2193', '\u2190', '\u2192',
            '\u2196', '\u2197', '\u2199', '\u2198', '\u2225', '\u2223', '\uFF0F', '\uFF3C', '\u2215', '\uFE68',
            '\uFF04', '\uFFE5', '\u3012', '\uFFE0', '\uFFE1', '\uFF05', '\uFF20', '\u2103', '\u2109', '\uFE69',
            '\uFE6A', '\uFE6B', '\u33D5', '\u339C', '\u339D', '\u339E', '\u33CE', '\u33A1', '\u338E', '\u338F',
            '\u33C4', '\u00B0', '\u5159', '\u515B', '\u515E', '\u515D', '\u5161', '\u5163', '\u55E7', '\u74E9',
            '\u7CCE', '\u2581', '\u2582', '\u2583', '\u2584', '\u2585', '\u2586', '\u2587', '\u2588', '\u258F',
            '\u258E', '\u258D', '\u258C', '\u258B', '\u258A', '\u2589', '\u253C', '\u2534', '\u252C', '\u2524',
            '\u251C', '\u2594', '\u2500', '\u2502', '\u2595', '\u250C', '\u2510', '\u2514', '\u2518', '\u256D',
            '\u256E', '\u2570', '\u256F', '\u2550', '\u255E', '\u256A', '\u2561', '\u25E2', '\u25E3', '\u25E5',
            '\u25E4', '\u2571', '\u2572', '\u2573', '\uFF10', '\uFF11', '\uFF12', '\uFF13', '\uFF14', '\uFF15',
            '\uFF16', '\uFF17', '\uFF18', '\uFF19', '\u2160', '\u2161', '\u2162', '\u2163', '\u2164', '\u2165',
            '\u2166', '\u2167', '\u2168', '\u2169', '\u3021', '\u3022', '\u3023', '\u3024', '\u3025', '\u3026',
            '\u3027', '\u3028', '\u3029', '\u5341', '\u5344', '\u5345', '\uFF21', '\uFF22', '\uFF23', '\uFF24',
            '\uFF25', '\uFF26', '\uFF27', '\uFF28', '\uFF29', '\uFF2A', '\uFF2B', '\uFF2C', '\uFF2D', '\uFF2E',
            '\uFF2F', '\uFF30', '\uFF31', '\uFF32', '\uFF33', '\uFF34', '\uFF35', '\uFF36', '\uFF37', '\uFF38',
            '\uFF39', '\uFF3A', '\uFF41', '\uFF42', '\uFF43', '\uFF44', '\uFF45', '\uFF46', '\uFF47', '\uFF48',
            '\uFF49', '\uFF4A', '\uFF4B', '\uFF4C', '\uFF4D', '\uFF4E', '\uFF4F', '\uFF50', '\uFF51', '\uFF52',
            '\uFF53', '\uFF54', '\uFF55', '\uFF56'};

    private void decodeUCharToUCS2(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
        byte high = 0;
        byte low = (byte) paramInt2;
        paramArrayOfchar[paramInt1] = (char) high;
        paramArrayOfchar[paramInt1++] = (char) low;
    }

    private void decodeUShortToUCS2(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
        byte b1 = (byte) (paramInt2 >> 8);
        byte b2 = (byte) (paramInt2 & 0xFF);
        paramArrayOfchar[paramInt1] = (char) b1;
        paramArrayOfchar[paramInt1++] = (char) b2;
    }

    public int convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
        int i = paramInt2 - paramInt1;
        int j = paramInt4 - paramInt3;
        byte b = 0;
        int k = paramInt1;
        int m = paramInt3;
        while (k < paramInt1 + i && m < paramInt3 + j) {
            byte b1 = paramArrayOfbyte[k];
            if ((b1 & 0xFF) < 128) {
                decodeUCharToUCS2(paramArrayOfchar, m, b1);
                m++;
                k++;
                continue;
            }
            if ((byte) b1 == -116) {
                b = paramArrayOfbyte[k + 1];
                int n = this.hkscs2001converter.convert((byte) b1, (byte) b, paramArrayOfchar, m);
                if (n == 0) {
                    k += 2;
                    m++;
                    continue;
                }
                if (n == 2) {
                    k += 2;
                    m += 2;
                    continue;
                }
            }
            if ((b1 & 0xFF) == 249) {
                b = paramArrayOfbyte[k + 1];
                int n = this.ms950ExtConverter.convert((byte) b1, (byte) b, paramArrayOfchar, m);
                if (n == 0) {
                    k += 2;
                    m++;
                    continue;
                }
            }
            if ((byte) b1 >= -127 && (byte) b1 < -1) {
                b = paramArrayOfbyte[k + 1];
                if (((byte) b >= 64 && (byte) b < Byte.MAX_VALUE) || ((byte) b >= -95 && (byte) b < -1)) {
                    if (b1 >= -95) {
                        if (b1 < -93) {
                            int b3 = ((char) b >= '\u00A1') ? 98 : 64;
                            int i1 = 157 * ((byte) b1 - -95) + ((char) b & 0xFF) - (byte) b3;
                            char c1 = CP950_TO_UNICODE_PAGEA1[i1];
                            if (c1 != '\uFFFD') {
                                paramArrayOfchar[m++] = (char) c1;
                                k += 2;
                                continue;
                            }
                        }
                        if (((byte) b1 != -58 || (char) b < '\u00A1') && (byte) b1 != -57) {
                            int i1 = this.big5Converter.convert((byte) b1, (byte) b, paramArrayOfchar, m);
                            if (i1 == 0) {
                                k += 2;
                                m++;
                                continue;
                            }
                        }
                        if ((byte) b1 == -93 && (byte) b == -31) {
                            paramArrayOfchar[m++] = '\u20AC';
                            k += 2;
                            continue;
                        }
                        if (b1 >= 250) {
                            int b3 = ((char) b >= '\u00A1') ? 98 : 64;
                            int i1 = 57344 + 157 * ((byte) b1 - -6) + ((char) b & 0xFF) - (byte) b3;
                            paramArrayOfchar[m++] = (char) i1;
                            k += 2;
                        }
                        continue;
                    }
                    char c = ((char) b1 >= '\u008E') ? '\uDB18' : '\uEEB8';
                    int b2 = ((char) b >= '\u00A1') ? 98 : 64;
                    int n = c + 157 * ((byte) b1 - -127) + ((char) b & 0xFF) - (byte) b2;
                    paramArrayOfchar[m++] = (char) n;
                    k += 2;
                }
                continue;
            }
            if (this.subMode) {
                paramArrayOfchar[m++] = this.subChars[0];
                continue;
            }
            throw TbError.newSQLException(-590742, b1);
        }
        return m - paramInt3;
    }
}