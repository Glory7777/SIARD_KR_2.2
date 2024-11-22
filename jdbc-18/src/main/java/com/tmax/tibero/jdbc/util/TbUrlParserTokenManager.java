package com.tmax.tibero.jdbc.util;

import java.io.IOException;
import java.io.PrintStream;

public class TbUrlParserTokenManager implements TbUrlParserConstants {
    public PrintStream debugStream = System.out;

    static final int[] jjnextStates = new int[]{
            45, 46, 52, 1, 2, 131, 134, 138, 113, 124,
            109, 110, 98, 105, 83, 92, 54, 60, 70};

    public static final String[] jjstrLiteralImages = new String[]{
            "", null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, ":", "@(", "=", ")", "(", "@", "/"};

    public static final String[] lexStateNames = new String[]{"DEFAULT"};

    static final long[] jjtoToken = new long[]{1121605006655457L};

    static final long[] jjtoSkip = new long[]{30L};

    protected SimpleCharStream input_stream;

    private final int[] jjrounds = new int[141];

    private final int[] jjstateSet = new int[282];

    protected char curChar;

    int curLexState = 0;

    int defaultLexState = 0;

    int jjnewStateCnt;

    int jjround;

    int jjmatchedPos;

    int jjmatchedKind;

    public void setDebugStream(PrintStream paramPrintStream) {
        this.debugStream = paramPrintStream;
    }

    private final int jjStopStringLiteralDfa_0(int paramInt, long paramLong) {
        switch (paramInt) {

        }
        return -1;
    }

    private final int jjStartNfa_0(int paramInt, long paramLong) {
        return jjMoveNfa_0(jjStopStringLiteralDfa_0(paramInt, paramLong), paramInt + 1);
    }

    private final int jjStopAtPos(int paramInt1, int paramInt2) {
        this.jjmatchedKind = paramInt2;
        this.jjmatchedPos = paramInt1;
        return paramInt1 + 1;
    }

    private final int jjStartNfaWithStates_0(int paramInt1, int paramInt2, int paramInt3) {
        this.jjmatchedKind = paramInt2;
        this.jjmatchedPos = paramInt1;
        try {
            this.curChar = this.input_stream.readChar();
        } catch (IOException iOException) {
            return paramInt1 + 1;
        }
        return jjMoveNfa_0(paramInt3, paramInt1 + 1);
    }

    private final int jjMoveStringLiteralDfa0_0() {
        switch (this.curChar) {
            case '(':
                return jjStopAtPos(0, 47);
            case ')':
                return jjStopAtPos(0, 46);
            case '/':
                return jjStopAtPos(0, 49);
            case ':':
                return jjStopAtPos(0, 43);
            case '=':
                return jjStopAtPos(0, 45);
            case '@':
                this.jjmatchedKind = 48;
                return jjMoveStringLiteralDfa1_0(17592186044416L);
        }
        return jjMoveNfa_0(0, 0);
    }

    private final int jjMoveStringLiteralDfa1_0(long paramLong) {
        try {
            this.curChar = this.input_stream.readChar();
        } catch (IOException iOException) {
            jjStopStringLiteralDfa_0(0, paramLong);
            return 1;
        }
        switch (this.curChar) {
            case '(':
                if ((paramLong & 0x100000000000L) != 0L)
                    return jjStopAtPos(1, 44);
                break;
        }
        return jjStartNfa_0(0, paramLong);
    }

    private final void jjCheckNAdd(int paramInt) {
        if (this.jjrounds[paramInt] != this.jjround) {
            this.jjstateSet[this.jjnewStateCnt++] = paramInt;
            this.jjrounds[paramInt] = this.jjround;
        }
    }

    private final void jjAddStates(int paramInt1, int paramInt2) {
        do {
            this.jjstateSet[this.jjnewStateCnt++] = jjnextStates[paramInt1];
        } while (paramInt1++ != paramInt2);
    }

    private final void jjCheckNAddTwoStates(int paramInt1, int paramInt2) {
        jjCheckNAdd(paramInt1);
        jjCheckNAdd(paramInt2);
    }

    private final void jjCheckNAddStates(int paramInt1, int paramInt2) {
        do {
            jjCheckNAdd(jjnextStates[paramInt1]);
        } while (paramInt1++ != paramInt2);
    }

    private final void jjCheckNAddStates(int paramInt) {
        jjCheckNAdd(jjnextStates[paramInt]);
        jjCheckNAdd(jjnextStates[paramInt + 1]);
    }

    private final int jjMoveNfa_0(int paramInt1, int paramInt2) {
        int i = 0;
        this.jjnewStateCnt = 141;
        int j = 1;
        this.jjstateSet[0] = paramInt1;
        int k = Integer.MAX_VALUE;
        while (true) {
            if (++this.jjround == Integer.MAX_VALUE)
                ReInitRounds();
            if (this.curChar < '@') {
                long l = 1L << this.curChar;
                do {
                    switch (this.jjstateSet[--j]) {
                        case 0:
                            if ((0x3FF000000000000L & l) == 0L)
                                break;
                            if (k > 35)
                                k = 35;
                            jjCheckNAddStates(0, 2);
                            break;
                        case 1:
                            if ((0x7FF000000000000L & l) != 0L)
                                jjAddStates(3, 4);
                            break;
                        case 43:
                            if ((0xDBFF7CBB00000000L & l) == 0L)
                                break;
                            if (k > 42)
                                k = 42;
                            this.jjstateSet[this.jjnewStateCnt++] = 43;
                            break;
                        case 45:
                            if ((0x3FF000000000000L & l) != 0L)
                                jjCheckNAddTwoStates(45, 46);
                            break;
                        case 46:
                            if (this.curChar == '.')
                                jjCheckNAdd(47);
                            break;
                        case 47:
                            if ((0x3FF000000000000L & l) != 0L)
                                jjCheckNAddTwoStates(47, 48);
                            break;
                        case 48:
                            if (this.curChar == '.')
                                jjCheckNAdd(49);
                            break;
                        case 49:
                            if ((0x3FF000000000000L & l) != 0L)
                                jjCheckNAddTwoStates(49, 50);
                            break;
                        case 50:
                            if (this.curChar == '.')
                                jjCheckNAdd(51);
                            break;
                        case 51:
                            if ((0x3FF000000000000L & l) == 0L)
                                break;
                            if (k > 5)
                                k = 5;
                            jjCheckNAdd(51);
                            break;
                        case 52:
                            if ((0x3FF000000000000L & l) == 0L)
                                break;
                            if (k > 36)
                                k = 36;
                            jjCheckNAdd(52);
                            break;
                    }
                } while (j != i);
            } else if (this.curChar < '\u0080') {
                long l = 1L << (this.curChar & 0x3F);
                do {
                    switch (this.jjstateSet[--j]) {
                        case 0:
                            if ((0x7FFFFFE87FFFFFEL & l) != 0L) {
                                if (k > 42)
                                    k = 42;
                                jjCheckNAdd(43);
                            } else if (this.curChar == '[') {
                                jjCheckNAddTwoStates(1, 2);
                            }
                            if ((0x10000000100000L & l) != 0L) {
                                jjAddStates(5, 7);
                                break;
                            }
                            if ((0x200000002L & l) != 0L) {
                                jjAddStates(8, 9);
                                break;
                            }
                            if ((0x800000008000L & l) != 0L) {
                                jjAddStates(10, 11);
                                break;
                            }
                            if ((0x1000000010000L & l) != 0L) {
                                jjAddStates(12, 13);
                                break;
                            }
                            if ((0x800000008L & l) != 0L) {
                                jjAddStates(14, 15);
                                break;
                            }
                            if ((0x1000000010L & l) != 0L) {
                                jjAddStates(16, 18);
                                break;
                            }
                            if ((0x8000000080000L & l) != 0L) {
                                this.jjstateSet[this.jjnewStateCnt++] = 36;
                                break;
                            }
                            if ((0x400000004000L & l) != 0L) {
                                this.jjstateSet[this.jjnewStateCnt++] = 32;
                                break;
                            }
                            if ((0x10000000100L & l) != 0L) {
                                this.jjstateSet[this.jjnewStateCnt++] = 28;
                                break;
                            }
                            if ((0x100000001000L & l) != 0L) {
                                this.jjstateSet[this.jjnewStateCnt++] = 16;
                                break;
                            }
                            if ((0x4000000040L & l) != 0L) {
                                this.jjstateSet[this.jjnewStateCnt++] = 8;
                                break;
                            }
                            if ((0x40000000400L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 4;
                            break;
                        case 1:
                            if ((0x7FFFFFE87FFFFFEL & l) != 0L)
                                jjCheckNAddTwoStates(1, 2);
                            break;
                        case 2:
                            if (this.curChar == ']' && k > 6)
                                k = 6;
                            break;
                        case 3:
                            if ((0x40000000400L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 4;
                            break;
                        case 4:
                            if ((0x1000000010L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 5;
                            break;
                        case 5:
                            if ((0x400000004L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 6;
                            break;
                        case 6:
                            if ((0x800000008L & l) != 0L && k > 9)
                                k = 9;
                            break;
                        case 7:
                            if ((0x4000000040L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 8;
                            break;
                        case 8:
                            if ((0x200000002L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 9;
                            break;
                        case 9:
                            if ((0x20000000200L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 10;
                            break;
                        case 10:
                            if ((0x100000001000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 11;
                            break;
                        case 11:
                            if ((0x800000008000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 12;
                            break;
                        case 12:
                            if ((0x40000000400000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 13;
                            break;
                        case 13:
                            if ((0x2000000020L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 14;
                            break;
                        case 14:
                            if ((0x4000000040000L & l) != 0L && k > 12)
                                k = 12;
                            break;
                        case 15:
                            if ((0x100000001000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 16;
                            break;
                        case 16:
                            if ((0x800000008000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 17;
                            break;
                        case 17:
                            if ((0x200000002L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 18;
                            break;
                        case 18:
                            if ((0x1000000010L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 19;
                            break;
                        case 19:
                            if (this.curChar == '_')
                                this.jjstateSet[this.jjnewStateCnt++] = 20;
                            break;
                        case 20:
                            if ((0x400000004L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 21;
                            break;
                        case 21:
                            if ((0x200000002L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 22;
                            break;
                        case 22:
                            if ((0x100000001000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 23;
                            break;
                        case 23:
                            if ((0x200000002L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 24;
                            break;
                        case 24:
                            if ((0x400000004000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 25;
                            break;
                        case 25:
                            if ((0x800000008L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 26;
                            break;
                        case 26:
                            if ((0x2000000020L & l) != 0L && k > 13)
                                k = 13;
                            break;
                        case 27:
                            if ((0x10000000100L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 28;
                            break;
                        case 28:
                            if ((0x800000008000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 29;
                            break;
                        case 29:
                            if ((0x8000000080000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 30;
                            break;
                        case 30:
                            if ((0x10000000100000L & l) != 0L && k > 20)
                                k = 20;
                            break;
                        case 31:
                            if ((0x400000004000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 32;
                            break;
                        case 32:
                            if ((0x800000008000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 33;
                            break;
                        case 33:
                            if ((0x400000004000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 34;
                            break;
                        case 34:
                            if ((0x2000000020L & l) != 0L && k > 23)
                                k = 23;
                            break;
                        case 35:
                            if ((0x8000000080000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 36;
                            break;
                        case 36:
                            if ((0x2000000020L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 37;
                            break;
                        case 37:
                            if ((0x8000000080000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 38;
                            break;
                        case 38:
                            if ((0x8000000080000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 39;
                            break;
                        case 39:
                            if ((0x20000000200L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 40;
                            break;
                        case 40:
                            if ((0x800000008000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 41;
                            break;
                        case 41:
                            if ((0x400000004000L & l) != 0L && k > 24)
                                k = 24;
                            break;
                        case 42:
                            if ((0x7FFFFFE87FFFFFEL & l) == 0L)
                                break;
                            if (k > 42)
                                k = 42;
                            jjCheckNAdd(43);
                            break;
                        case 43:
                            if ((0x2FFFFFFFAFFFFFFEL & l) == 0L)
                                break;
                            if (k > 42)
                                k = 42;
                            jjCheckNAdd(43);
                            break;
                        case 53:
                            if ((0x1000000010L & l) != 0L)
                                jjAddStates(16, 18);
                            break;
                        case 54:
                            if ((0x2000000020L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 55;
                            break;
                        case 55:
                            if ((0x4000000040L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 56;
                            break;
                        case 56:
                            if ((0x200000002L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 57;
                            break;
                        case 57:
                            if ((0x20000000200000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 58;
                            break;
                        case 58:
                            if ((0x100000001000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 59;
                            break;
                        case 59:
                            if ((0x10000000100000L & l) != 0L && k > 7)
                                k = 7;
                            break;
                        case 60:
                            if ((0x2000000020L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 61;
                            break;
                        case 61:
                            if ((0x8000000080000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 62;
                            break;
                        case 62:
                            if ((0x800000008L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 63;
                            break;
                        case 63:
                            if ((0x4000000040000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 64;
                            break;
                        case 64:
                            if ((0x20000000200L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 65;
                            break;
                        case 65:
                            if ((0x1000000010000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 66;
                            break;
                        case 66:
                            if ((0x10000000100000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 67;
                            break;
                        case 67:
                            if ((0x20000000200L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 68;
                            break;
                        case 68:
                            if ((0x800000008000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 69;
                            break;
                        case 69:
                            if ((0x400000004000L & l) != 0L && k > 11)
                                k = 11;
                            break;
                        case 70:
                            if ((0x200000002L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 71;
                            break;
                        case 71:
                            if ((0x10000000100000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 72;
                            break;
                        case 72:
                            if ((0x200000002L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 73;
                            break;
                        case 73:
                            if ((0x400000004L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 74;
                            break;
                        case 74:
                            if ((0x200000002L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 75;
                            break;
                        case 75:
                            if ((0x8000000080000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 76;
                            break;
                        case 76:
                            if ((0x2000000020L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 77;
                            break;
                        case 77:
                            if (this.curChar == '_')
                                this.jjstateSet[this.jjnewStateCnt++] = 78;
                            break;
                        case 78:
                            if ((0x400000004000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 79;
                            break;
                        case 79:
                            if ((0x200000002L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 80;
                            break;
                        case 80:
                            if ((0x200000002000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 81;
                            break;
                        case 81:
                            if ((0x2000000020L & l) != 0L && k > 17)
                                k = 17;
                            break;
                        case 82:
                            if ((0x800000008L & l) != 0L)
                                jjAddStates(14, 15);
                            break;
                        case 83:
                            if ((0x800000008000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 84;
                            break;
                        case 84:
                            if ((0x400000004000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 85;
                            break;
                        case 85:
                            if ((0x400000004000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 86;
                            break;
                        case 86:
                            if ((0x2000000020L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 87;
                            break;
                        case 87:
                            if ((0x800000008L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 88;
                            break;
                        case 88:
                            if ((0x10000000100000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 89;
                            break;
                        case 89:
                            if ((0x20000000200L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 90;
                            break;
                        case 90:
                            if ((0x800000008000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 91;
                            break;
                        case 91:
                            if ((0x400000004000L & l) != 0L && k > 8)
                                k = 8;
                            break;
                        case 92:
                            if ((0x20000000200000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 93;
                            break;
                        case 93:
                            if ((0x4000000040000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 94;
                            break;
                        case 94:
                            if ((0x8000000080000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 95;
                            break;
                        case 95:
                            if ((0x800000008000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 96;
                            break;
                        case 96:
                            if ((0x4000000040000L & l) != 0L && k > 25)
                                k = 25;
                            break;
                        case 97:
                            if ((0x1000000010000L & l) != 0L)
                                jjAddStates(12, 13);
                            break;
                        case 98:
                            if ((0x4000000040000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 99;
                            break;
                        case 99:
                            if ((0x800000008000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 100;
                            break;
                        case 100:
                            if ((0x10000000100000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 101;
                            break;
                        case 101:
                            if ((0x800000008000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 102;
                            break;
                        case 102:
                            if ((0x800000008L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 103;
                            break;
                        case 103:
                            if ((0x800000008000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 104;
                            break;
                        case 104:
                            if ((0x100000001000L & l) != 0L && k > 14)
                                k = 14;
                            break;
                        case 105:
                            if ((0x800000008000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 106;
                            break;
                        case 106:
                            if ((0x4000000040000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 107;
                            break;
                        case 107:
                            if ((0x10000000100000L & l) != 0L && k > 21)
                                k = 21;
                            break;
                        case 108:
                            if ((0x800000008000L & l) != 0L)
                                jjAddStates(10, 11);
                            break;
                        case 109:
                            if ((0x400000004000L & l) != 0L && k > 15)
                                k = 15;
                            break;
                        case 110:
                            if ((0x4000000040L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 111;
                            break;
                        case 111:
                            if ((0x4000000040L & l) != 0L && k > 16)
                                k = 16;
                            break;
                        case 112:
                            if ((0x200000002L & l) != 0L)
                                jjAddStates(8, 9);
                            break;
                        case 113:
                            if ((0x1000000010L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 114;
                            break;
                        case 114:
                            if ((0x1000000010L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 115;
                            break;
                        case 115:
                            if ((0x4000000040000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 116;
                            break;
                        case 116:
                            if ((0x2000000020L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 117;
                            break;
                        case 117:
                            if ((0x8000000080000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 118;
                            break;
                        case 118:
                            if ((0x8000000080000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 119;
                            break;
                        case 119:
                            if (this.curChar == '_')
                                this.jjstateSet[this.jjnewStateCnt++] = 120;
                            break;
                        case 120:
                            if ((0x100000001000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 121;
                            break;
                        case 121:
                            if ((0x20000000200L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 122;
                            break;
                        case 122:
                            if ((0x8000000080000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 123;
                            break;
                        case 123:
                            if ((0x10000000100000L & l) != 0L && k > 18)
                                k = 18;
                            break;
                        case 124:
                            if ((0x1000000010L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 125;
                            break;
                        case 125:
                            if ((0x1000000010L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 126;
                            break;
                        case 126:
                            if ((0x4000000040000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 127;
                            break;
                        case 127:
                            if ((0x2000000020L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 128;
                            break;
                        case 128:
                            if ((0x8000000080000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 129;
                            break;
                        case 129:
                            if ((0x8000000080000L & l) != 0L && k > 19)
                                k = 19;
                            break;
                        case 130:
                            if ((0x10000000100000L & l) != 0L)
                                jjAddStates(5, 7);
                            break;
                        case 131:
                            if ((0x10000000100L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 132;
                            break;
                        case 132:
                            if ((0x20000000200L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 133;
                            break;
                        case 133:
                            if ((0x400000004000L & l) != 0L && k > 10)
                                k = 10;
                            break;
                        case 134:
                            if ((0x400000004L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 135;
                            break;
                        case 135:
                            if ((0x800000008L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 136;
                            break;
                        case 136:
                            if ((0x100000001000L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 137;
                            break;
                        case 137:
                            if ((0x20000000200L & l) != 0L && k > 10)
                                k = 10;
                            break;
                        case 138:
                            if ((0x800000008L & l) != 0L)
                                this.jjstateSet[this.jjnewStateCnt++] = 139;
                            break;
                        case 139:
                            if ((0x1000000010000L & l) == 0L)
                                break;
                            if (k > 22)
                                k = 22;
                            this.jjstateSet[this.jjnewStateCnt++] = 140;
                            break;
                        case 140:
                            if ((0x8000000080000L & l) != 0L && k > 22)
                                k = 22;
                            break;
                    }
                } while (j != i);
            } else {
                int m = (this.curChar & 0xFF) >> 6;
                long l = 1L << (this.curChar & 0x3F);
                do {
                    switch (this.jjstateSet[--j]) {

                    }
                } while (j != i);
            }
            if (k != Integer.MAX_VALUE) {
                this.jjmatchedKind = k;
                this.jjmatchedPos = paramInt2;
                k = Integer.MAX_VALUE;
            }
            paramInt2++;
            if ((j = this.jjnewStateCnt) == (i = 141 - (this.jjnewStateCnt = i)))
                return paramInt2;
            try {
                this.curChar = this.input_stream.readChar();
            } catch (IOException iOException) {
                return paramInt2;
            }
        }
    }

    public TbUrlParserTokenManager(SimpleCharStream paramSimpleCharStream) {
        this.input_stream = paramSimpleCharStream;
    }

    public TbUrlParserTokenManager(SimpleCharStream paramSimpleCharStream, int paramInt) {
        this(paramSimpleCharStream);
        SwitchTo(paramInt);
    }

    public void ReInit(SimpleCharStream paramSimpleCharStream) {
        this.jjmatchedPos = this.jjnewStateCnt = 0;
        this.curLexState = this.defaultLexState;
        this.input_stream = paramSimpleCharStream;
        ReInitRounds();
    }

    private final void ReInitRounds() {
        this.jjround = -2147483647;
        char c = '\u008D';
        while (c-- > '\000')
            this.jjrounds[c] = Integer.MIN_VALUE;
    }

    public void ReInit(SimpleCharStream paramSimpleCharStream, int paramInt) {
        ReInit(paramSimpleCharStream);
        SwitchTo(paramInt);
    }

    public void SwitchTo(int paramInt) {
        if (paramInt >= 1 || paramInt < 0)
            throw new TokenMgrError("Error: Ignoring invalid lexical state : " + paramInt + ". State unchanged.", 2);
        this.curLexState = paramInt;
    }

    protected Token jjFillToken() {
        Token token = Token.newToken(this.jjmatchedKind);
        token.kind = this.jjmatchedKind;
        String str = jjstrLiteralImages[this.jjmatchedKind];
        token.image = (str == null) ? this.input_stream.GetImage() : str;
        token.beginLine = this.input_stream.getBeginLine();
        token.beginColumn = this.input_stream.getBeginColumn();
        token.endLine = this.input_stream.getEndLine();
        token.endColumn = this.input_stream.getEndColumn();
        return token;
    }

    public Token getNextToken() {
        Token specialToken = null;

        int curPos = 0;

        while (true) {
            try {
                this.curChar = this.input_stream.BeginToken();
            } catch (IOException e) {

                this.jjmatchedKind = 0;
                Token matchedToken = jjFillToken();
                return matchedToken;
            }

            try {
                this.input_stream.backup(0);
                while (this.curChar <= ' ' && (0x100002600L & 1L << this.curChar) != 0L) {
                    this.curChar = this.input_stream.BeginToken();
                }
            } catch (IOException e1) {
                continue;
            }
            this.jjmatchedKind = Integer.MAX_VALUE;
            this.jjmatchedPos = 0;
            curPos = jjMoveStringLiteralDfa0_0();
            if (this.jjmatchedKind != Integer.MAX_VALUE) {

                if (this.jjmatchedPos + 1 < curPos)
                    this.input_stream.backup(curPos - this.jjmatchedPos - 1);
                if ((jjtoToken[this.jjmatchedKind >> 6] & 1L << (this.jjmatchedKind & 0x3F)) != 0L) {

                    Token matchedToken = jjFillToken();
                    return matchedToken;
                }

                continue;
            }
            break;
        }
        int error_line = this.input_stream.getEndLine();
        int error_column = this.input_stream.getEndColumn();
        String error_after = null;
        boolean EOFSeen = false;
        try {
            this.input_stream.readChar();
            this.input_stream.backup(1);
        } catch (IOException e1) {
            EOFSeen = true;
            error_after = (curPos <= 1) ? "" : this.input_stream.GetImage();
            if (this.curChar == '\n' || this.curChar == '\r') {
                error_line++;
                error_column = 0;
            } else {

                error_column++;
            }
        }
        if (!EOFSeen) {
            this.input_stream.backup(1);
            error_after = (curPos <= 1) ? "" : this.input_stream.GetImage();
        }
        throw new TokenMgrError(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
    }
}
