package com.tmax.tibero.jdbc.util;

import java.io.IOException;
import java.io.PrintStream;

public class TbDTFormatParserTokenManager implements TbDTFormatParserConstants {
  public PrintStream debugStream = System.out;
  
  static final int[] jjnextStates = new int[] { 
      31, 37, 38, 40, 26, 29, 21, 22, 16, 17, 
      2, 3 };
  
  public static final String[] jjstrLiteralImages = new String[] { 
      "", null, null, null, null, null, null, null, null, null, 
      null, null, null, null, null, null, null, null, null, null, 
      null, null, null, null, null, "CC", "DD", "DDD", "DY", "D", 
      "DAY", "FM", "FX", null, null, "HH24", "MI", "MON", "MM", "RM", 
      "MONTH", "Q", null, "X", "SSSSS", "SS", "SCC", "SYYYY", "SYEAR", "TZD", 
      "TZH", "TZM", "TZR", "W", "WW", null, null, "YEAR" };
  
  public static final String[] lexStateNames = new String[] { "DEFAULT" };
  
  protected SimpleCharStream input_stream;
  
  private final int[] jjrounds = new int[41];
  
  private final int[] jjstateSet = new int[82];
  
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
      case 0:
        if ((paramLong & 0x180000000L) != 0L)
          return 4; 
        if ((paramLong & 0x200000000000000L) != 0L) {
          this.jjmatchedKind = 56;
          return 21;
        } 
        return ((paramLong & 0x8000000000L) != 0L) ? 11 : (((paramLong & 0x800000000L) != 0L) ? 7 : -1);
      case 1:
        if ((paramLong & 0x800000000L) != 0L) {
          if (this.jjmatchedPos != 1) {
            this.jjmatchedKind = 34;
            this.jjmatchedPos = 1;
          } 
          return 9;
        } 
        if ((paramLong & 0x200000000000000L) != 0L) {
          if (this.jjmatchedPos == 0) {
            this.jjmatchedKind = 56;
            this.jjmatchedPos = 0;
          } 
          return -1;
        } 
        return -1;
      case 2:
        if ((paramLong & 0x200000000000000L) != 0L) {
          if (this.jjmatchedPos == 0) {
            this.jjmatchedKind = 56;
            this.jjmatchedPos = 0;
          } 
          return -1;
        } 
        if ((paramLong & 0x800000000L) != 0L) {
          if (this.jjmatchedPos < 1) {
            this.jjmatchedKind = 34;
            this.jjmatchedPos = 1;
          } 
          return -1;
        } 
        return -1;
      case 3:
        if ((paramLong & 0x200000000000000L) != 0L) {
          if (this.jjmatchedPos == 0) {
            this.jjmatchedKind = 56;
            this.jjmatchedPos = 0;
          } 
          return -1;
        } 
        if ((paramLong & 0x800000000L) != 0L) {
          if (this.jjmatchedPos < 1) {
            this.jjmatchedKind = 34;
            this.jjmatchedPos = 1;
          } 
          return -1;
        } 
        return -1;
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
      case 'C':
        return jjMoveStringLiteralDfa1_0(33554432L);
      case 'D':
        this.jjmatchedKind = 29;
        return jjMoveStringLiteralDfa1_0(1543503872L);
      case 'F':
        return jjMoveStringLiteralDfa1_0(6442450944L);
      case 'H':
        return jjMoveStringLiteralDfa1_0(34359738368L);
      case 'M':
        return jjMoveStringLiteralDfa1_0(1580547964928L);
      case 'Q':
        return jjStopAtPos(0, 41);
      case 'R':
        return jjMoveStringLiteralDfa1_0(549755813888L);
      case 'S':
        return jjMoveStringLiteralDfa1_0(545357767376896L);
      case 'T':
        return jjMoveStringLiteralDfa1_0(8444249301319680L);
      case 'W':
        this.jjmatchedKind = 53;
        return jjMoveStringLiteralDfa1_0(18014398509481984L);
      case 'X':
        return jjStopAtPos(0, 43);
      case 'Y':
        return jjMoveStringLiteralDfa1_0(144115188075855872L);
    } 
    return jjMoveNfa_0(6, 0);
  }
  
  private final int jjMoveStringLiteralDfa1_0(long paramLong) {
    try {
      this.curChar = this.input_stream.readChar();
    } catch (IOException iOException) {
      jjStopStringLiteralDfa_0(0, paramLong);
      return 1;
    } 
    switch (this.curChar) {
      case 'A':
        return jjMoveStringLiteralDfa2_0(paramLong, 1073741824L);
      case 'C':
        return ((paramLong & 0x2000000L) != 0L) ? jjStopAtPos(1, 25) : jjMoveStringLiteralDfa2_0(paramLong, 70368744177664L);
      case 'D':
        if ((paramLong & 0x4000000L) != 0L) {
          this.jjmatchedKind = 26;
          this.jjmatchedPos = 1;
        } 
        return jjMoveStringLiteralDfa2_0(paramLong, 134217728L);
      case 'E':
        return jjMoveStringLiteralDfa2_0(paramLong, 144115188075855872L);
      case 'H':
        return jjMoveStringLiteralDfa2_0(paramLong, 34359738368L);
      case 'I':
        if ((paramLong & 0x1000000000L) != 0L)
          return jjStopAtPos(1, 36); 
        break;
      case 'M':
        if ((paramLong & 0x80000000L) != 0L)
          return jjStopAtPos(1, 31); 
        if ((paramLong & 0x4000000000L) != 0L)
          return jjStopAtPos(1, 38); 
        if ((paramLong & 0x8000000000L) != 0L)
          return jjStopAtPos(1, 39); 
        break;
      case 'O':
        return jjMoveStringLiteralDfa2_0(paramLong, 1236950581248L);
      case 'S':
        if ((paramLong & 0x200000000000L) != 0L) {
          this.jjmatchedKind = 45;
          this.jjmatchedPos = 1;
        } 
        return jjMoveStringLiteralDfa2_0(paramLong, 17592186044416L);
      case 'W':
        if ((paramLong & 0x40000000000000L) != 0L)
          return jjStopAtPos(1, 54); 
        break;
      case 'X':
        if ((paramLong & 0x100000000L) != 0L)
          return jjStopAtPos(1, 32); 
        break;
      case 'Y':
        return ((paramLong & 0x10000000L) != 0L) ? jjStopAtPos(1, 28) : jjMoveStringLiteralDfa2_0(paramLong, 422212465065984L);
      case 'Z':
        return jjMoveStringLiteralDfa2_0(paramLong, 8444249301319680L);
    } 
    return jjStartNfa_0(0, paramLong);
  }
  
  private final int jjMoveStringLiteralDfa2_0(long paramLong1, long paramLong2) {
    if ((paramLong2 &= paramLong1) == 0L)
      return jjStartNfa_0(0, paramLong1); 
    try {
      this.curChar = this.input_stream.readChar();
    } catch (IOException iOException) {
      jjStopStringLiteralDfa_0(1, paramLong2);
      return 2;
    } 
    switch (this.curChar) {
      case '2':
        return jjMoveStringLiteralDfa3_0(paramLong2, 34359738368L);
      case 'A':
        return jjMoveStringLiteralDfa3_0(paramLong2, 144115188075855872L);
      case 'C':
        if ((paramLong2 & 0x400000000000L) != 0L)
          return jjStopAtPos(2, 46); 
        break;
      case 'D':
        if ((paramLong2 & 0x8000000L) != 0L)
          return jjStopAtPos(2, 27); 
        if ((paramLong2 & 0x2000000000000L) != 0L)
          return jjStopAtPos(2, 49); 
        break;
      case 'E':
        return jjMoveStringLiteralDfa3_0(paramLong2, 281474976710656L);
      case 'H':
        if ((paramLong2 & 0x4000000000000L) != 0L)
          return jjStopAtPos(2, 50); 
        break;
      case 'M':
        if ((paramLong2 & 0x8000000000000L) != 0L)
          return jjStopAtPos(2, 51); 
        break;
      case 'N':
        if ((paramLong2 & 0x2000000000L) != 0L) {
          this.jjmatchedKind = 37;
          this.jjmatchedPos = 2;
        } 
        return jjMoveStringLiteralDfa3_0(paramLong2, 1099511627776L);
      case 'R':
        if ((paramLong2 & 0x10000000000000L) != 0L)
          return jjStopAtPos(2, 52); 
        break;
      case 'S':
        return jjMoveStringLiteralDfa3_0(paramLong2, 17592186044416L);
      case 'Y':
        return ((paramLong2 & 0x40000000L) != 0L) ? jjStopAtPos(2, 30) : jjMoveStringLiteralDfa3_0(paramLong2, 140737488355328L);
    } 
    return jjStartNfa_0(1, paramLong2);
  }
  
  private final int jjMoveStringLiteralDfa3_0(long paramLong1, long paramLong2) {
    if ((paramLong2 &= paramLong1) == 0L)
      return jjStartNfa_0(1, paramLong1); 
    try {
      this.curChar = this.input_stream.readChar();
    } catch (IOException iOException) {
      jjStopStringLiteralDfa_0(2, paramLong2);
      return 3;
    } 
    switch (this.curChar) {
      case '4':
        if ((paramLong2 & 0x800000000L) != 0L)
          return jjStopAtPos(3, 35); 
        break;
      case 'A':
        return jjMoveStringLiteralDfa4_0(paramLong2, 281474976710656L);
      case 'R':
        if ((paramLong2 & 0x200000000000000L) != 0L)
          return jjStopAtPos(3, 57); 
        break;
      case 'S':
        return jjMoveStringLiteralDfa4_0(paramLong2, 17592186044416L);
      case 'T':
        return jjMoveStringLiteralDfa4_0(paramLong2, 1099511627776L);
      case 'Y':
        return jjMoveStringLiteralDfa4_0(paramLong2, 140737488355328L);
    } 
    return jjStartNfa_0(2, paramLong2);
  }
  
  private final int jjMoveStringLiteralDfa4_0(long paramLong1, long paramLong2) {
    if ((paramLong2 &= paramLong1) == 0L)
      return jjStartNfa_0(2, paramLong1); 
    try {
      this.curChar = this.input_stream.readChar();
    } catch (IOException iOException) {
      jjStopStringLiteralDfa_0(3, paramLong2);
      return 4;
    } 
    switch (this.curChar) {
      case 'H':
        if ((paramLong2 & 0x10000000000L) != 0L)
          return jjStopAtPos(4, 40); 
        break;
      case 'R':
        if ((paramLong2 & 0x1000000000000L) != 0L)
          return jjStopAtPos(4, 48); 
        break;
      case 'S':
        if ((paramLong2 & 0x100000000000L) != 0L)
          return jjStopAtPos(4, 44); 
        break;
      case 'Y':
        if ((paramLong2 & 0x800000000000L) != 0L)
          return jjStopAtPos(4, 47); 
        break;
    } 
    return jjStartNfa_0(3, paramLong2);
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
    this.jjnewStateCnt = 41;
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
            case 6:
              if ((0xC00F00100000000L & l) != 0L) {
                if (k > 15)
                  k = 15; 
                jjCheckNAdd(0);
                break;
              } 
              if (this.curChar == '"')
                jjCheckNAddTwoStates(2, 3); 
              break;
            case 0:
              if ((0xC00F00100000000L & l) == 0L)
                break; 
              if (k > 15)
                k = 15; 
              jjCheckNAdd(0);
              break;
            case 1:
              if (this.curChar == '"')
                jjCheckNAddTwoStates(2, 3); 
              break;
            case 2:
              if ((0xDBFF7CBB00000000L & l) != 0L)
                jjCheckNAddTwoStates(2, 3); 
              break;
            case 3:
              if (this.curChar != '"')
                break; 
              if (k > 16)
                k = 16; 
              this.jjstateSet[this.jjnewStateCnt++] = 1;
              break;
            case 5:
              if ((0x3FE000000000000L & l) != 0L && k > 33)
                k = 33; 
              break;
            case 8:
              if (this.curChar == '2' && k > 34)
                k = 34; 
              break;
            case 9:
              if (this.curChar == '1')
                this.jjstateSet[this.jjnewStateCnt++] = 8; 
              break;
            case 27:
              if (this.curChar == '.' && k > 24)
                k = 24; 
              break;
            case 29:
              if (this.curChar == '.')
                this.jjstateSet[this.jjnewStateCnt++] = 28; 
              break;
            case 32:
              if (this.curChar == '.' && k > 23)
                k = 23; 
              break;
            case 34:
              if (this.curChar == '.')
                this.jjstateSet[this.jjnewStateCnt++] = 33; 
              break;
            case 37:
              if (this.curChar == '.')
                this.jjstateSet[this.jjnewStateCnt++] = 36; 
              break;
            case 40:
              if (this.curChar == '.')
                this.jjstateSet[this.jjnewStateCnt++] = 39; 
              break;
          } 
        } while (j != i);
      } else if (this.curChar < '') {
        long l = 1L << (this.curChar & 0x3F);
        do {
          switch (this.jjstateSet[--j]) {
            case 6:
              if (this.curChar == 'A') {
                jjCheckNAddStates(0, 3);
                break;
              } 
              if (this.curChar == 'P') {
                jjCheckNAddTwoStates(31, 34);
                break;
              } 
              if (this.curChar == 'B') {
                jjAddStates(4, 5);
                break;
              } 
              if (this.curChar == 'Y') {
                if (k > 56)
                  k = 56; 
                jjAddStates(6, 7);
                break;
              } 
              if (this.curChar == 'I') {
                if (k > 55)
                  k = 55; 
                jjAddStates(8, 9);
                break;
              } 
              if (this.curChar == 'R') {
                this.jjstateSet[this.jjnewStateCnt++] = 11;
                break;
              } 
              if (this.curChar == 'H') {
                this.jjstateSet[this.jjnewStateCnt++] = 7;
                break;
              } 
              if (this.curChar == 'F')
                this.jjstateSet[this.jjnewStateCnt++] = 4; 
              break;
            case 21:
              if (this.curChar == 'Y')
                jjCheckNAddTwoStates(23, 24); 
              if (this.curChar == 'Y' && k > 56)
                k = 56; 
              break;
            case 2:
              if ((0x2FFFFFFFAFFFFFFEL & l) != 0L)
                jjAddStates(10, 11); 
              break;
            case 4:
              if (this.curChar != 'F')
                break; 
              if (k > 33)
                k = 33; 
              this.jjstateSet[this.jjnewStateCnt++] = 5;
              break;
            case 7:
              if (this.curChar != 'H')
                break; 
              if (k > 34)
                k = 34; 
              this.jjstateSet[this.jjnewStateCnt++] = 9;
              break;
            case 10:
              if (this.curChar == 'H')
                this.jjstateSet[this.jjnewStateCnt++] = 7; 
              break;
            case 11:
              if (this.curChar != 'R')
                break; 
              if (k > 42)
                k = 42; 
              this.jjstateSet[this.jjnewStateCnt++] = 13;
              break;
            case 12:
              if (this.curChar == 'R' && k > 42)
                k = 42; 
              break;
            case 13:
              if (this.curChar == 'R')
                this.jjstateSet[this.jjnewStateCnt++] = 12; 
              break;
            case 14:
              if (this.curChar == 'R')
                this.jjstateSet[this.jjnewStateCnt++] = 11; 
              break;
            case 15:
              if (this.curChar != 'I')
                break; 
              if (k > 55)
                k = 55; 
              jjAddStates(8, 9);
              break;
            case 16:
            case 18:
              if (this.curChar == 'Y' && k > 55)
                k = 55; 
              break;
            case 17:
              if (this.curChar == 'Y')
                jjCheckNAddTwoStates(18, 19); 
              break;
            case 19:
              if (this.curChar == 'Y')
                jjCheckNAdd(18); 
              break;
            case 20:
              if (this.curChar != 'Y')
                break; 
              if (k > 56)
                k = 56; 
              jjAddStates(6, 7);
              break;
            case 22:
              if (this.curChar == 'Y')
                jjCheckNAddTwoStates(23, 24); 
              break;
            case 23:
              if (this.curChar == 'Y' && k > 56)
                k = 56; 
              break;
            case 24:
              if (this.curChar == 'Y')
                jjCheckNAdd(23); 
              break;
            case 25:
              if (this.curChar == 'B')
                jjAddStates(4, 5); 
              break;
            case 26:
              if (this.curChar == 'C' && k > 24)
                k = 24; 
              break;
            case 28:
              if (this.curChar == 'C')
                jjCheckNAdd(27); 
              break;
            case 30:
              if (this.curChar == 'P')
                jjCheckNAddTwoStates(31, 34); 
              break;
            case 31:
              if (this.curChar == 'M' && k > 23)
                k = 23; 
              break;
            case 33:
            case 36:
              if (this.curChar == 'M')
                jjCheckNAdd(32); 
              break;
            case 35:
              if (this.curChar == 'A')
                jjCheckNAddStates(0, 3); 
              break;
            case 38:
              if (this.curChar == 'D' && k > 24)
                k = 24; 
              break;
            case 39:
              if (this.curChar == 'D')
                jjCheckNAdd(27); 
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
      if ((j = this.jjnewStateCnt) == (i = 41 - (this.jjnewStateCnt = i)))
        return paramInt2; 
      try {
        this.curChar = this.input_stream.readChar();
      } catch (IOException iOException) {
        return paramInt2;
      } 
    } 
  }
  
  public TbDTFormatParserTokenManager(SimpleCharStream paramSimpleCharStream) {
    this.input_stream = paramSimpleCharStream;
  }
  
  public TbDTFormatParserTokenManager(SimpleCharStream paramSimpleCharStream, int paramInt) {
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
    byte b = 41;
    while (b-- > 0)
      this.jjrounds[b] = Integer.MIN_VALUE; 
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
    Object object = null;
    int i = 0;
    try {
      this.curChar = this.input_stream.BeginToken();
    } catch (IOException iOException) {
      this.jjmatchedKind = 0;
      return jjFillToken();
    } 
    this.jjmatchedKind = Integer.MAX_VALUE;
    this.jjmatchedPos = 0;
    i = jjMoveStringLiteralDfa0_0();
    if (this.jjmatchedKind != Integer.MAX_VALUE) {
      if (this.jjmatchedPos + 1 < i)
        this.input_stream.backup(i - this.jjmatchedPos - 1); 
      return jjFillToken();
    } 
    int j = this.input_stream.getEndLine();
    int k = this.input_stream.getEndColumn();
    String str = null;
    boolean bool = false;
    try {
      this.input_stream.readChar();
      this.input_stream.backup(1);
    } catch (IOException iOException) {
      bool = true;
      str = (i <= 1) ? "" : this.input_stream.GetImage();
      if (this.curChar == '\n' || this.curChar == '\r') {
        j++;
        k = 0;
      } else {
        k++;
      } 
    } 
    if (!bool) {
      this.input_stream.backup(1);
      str = (i <= 1) ? "" : this.input_stream.GetImage();
    } 
    throw new TokenMgrError(bool, this.curLexState, j, k, str, this.curChar, 0);
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdb\\util\TbDTFormatParserTokenManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */