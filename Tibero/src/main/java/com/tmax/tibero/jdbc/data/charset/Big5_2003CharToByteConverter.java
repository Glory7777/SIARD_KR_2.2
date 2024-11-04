package com.tmax.tibero.jdbc.data.charset;

public class Big5_2003CharToByteConverter {
  private static final char[] BIG5_2003_2CHARSET_PAGE25 = new char[] { 
      'ù', 'ø', 'æ', 'ï', 'Ý', 'è', 'ñ', 'ß', 'ì', 'õ', 
      'ã', 'î', '÷', 'å', 'é', 'ò', 'à', 'ë', 'ô', 'â', 
      'ç', 'ð', 'Þ', 'í', 'ö', 'ä', 'ê', 'ó', 'á' };
  
  public int convert(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
    int i;
    switch (paramInt1 >> 8) {
      case 0:
        if (paramInt1 == -88) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -40;
          return 0;
        } 
        if (paramInt1 == -94 || paramInt1 == -93 || paramInt1 == -91)
          return -1; 
        break;
      case 2:
        if (paramInt1 == -51) {
          paramArrayOfbyte[paramInt2] = -95;
          paramArrayOfbyte[paramInt2 + 1] = -59;
          return 0;
        } 
        break;
      case 4:
        return -1;
      case 32:
        if (paramInt1 == 21) {
          paramArrayOfbyte[paramInt2] = -95;
          paramArrayOfbyte[paramInt2 + 1] = 86;
          return 0;
        } 
        if (paramInt1 == 39) {
          paramArrayOfbyte[paramInt2] = -95;
          paramArrayOfbyte[paramInt2 + 1] = 69;
          return 0;
        } 
        if (paramInt1 == -84) {
          paramArrayOfbyte[paramInt2] = -93;
          paramArrayOfbyte[paramInt2 + 1] = -31;
          return 0;
        } 
        if (paramInt1 == 19 || paramInt1 == 34)
          return -1; 
        break;
      case 33:
        if (paramInt1 >= 112 && paramInt1 <= 121) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = (byte)(paramInt1 - 8379);
          return 0;
        } 
        break;
      case 34:
        if (paramInt1 == 21) {
          paramArrayOfbyte[paramInt2] = -94;
          paramArrayOfbyte[paramInt2 + 1] = 65;
          return 0;
        } 
        if (paramInt1 == -107) {
          paramArrayOfbyte[paramInt2] = -95;
          paramArrayOfbyte[paramInt2 + 1] = -14;
          return 0;
        } 
        if (paramInt1 == -103) {
          paramArrayOfbyte[paramInt2] = -95;
          paramArrayOfbyte[paramInt2 + 1] = -13;
          return 0;
        } 
        if (paramInt1 == 60)
          return -1; 
        break;
      case 36:
        if (paramInt1 <= 31) {
          paramArrayOfbyte[paramInt2] = -93;
          paramArrayOfbyte[paramInt2 + 1] = (byte)(paramInt1 - 9024);
          return 0;
        } 
        if (paramInt1 == 33) {
          paramArrayOfbyte[paramInt2] = -93;
          paramArrayOfbyte[paramInt2 + 1] = -32;
          return 0;
        } 
        if (paramInt1 >= 96 && paramInt1 <= 105) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = (byte)(paramInt1 - 9151);
          return 0;
        } 
        if (paramInt1 >= 116 && paramInt1 <= 125) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = (byte)(paramInt1 - 9161);
          return 0;
        } 
        break;
      case 37:
        if (paramInt1 == 1) {
          paramArrayOfbyte[paramInt2] = -94;
          paramArrayOfbyte[paramInt2 + 1] = -92;
          return 0;
        } 
        if (paramInt1 == 29) {
          paramArrayOfbyte[paramInt2] = -94;
          paramArrayOfbyte[paramInt2 + 1] = -91;
          return 0;
        } 
        if (paramInt1 == 37) {
          paramArrayOfbyte[paramInt2] = -94;
          paramArrayOfbyte[paramInt2 + 1] = -89;
          return 0;
        } 
        if (paramInt1 == 63) {
          paramArrayOfbyte[paramInt2] = -94;
          paramArrayOfbyte[paramInt2 + 1] = -90;
          return 0;
        } 
        if (paramInt1 >= 80 && paramInt1 <= 108) {
          paramArrayOfbyte[paramInt2] = -7;
          paramArrayOfbyte[paramInt2 + 1] = (byte)BIG5_2003_2CHARSET_PAGE25[paramInt1 - 9552];
          return 0;
        } 
        if (paramInt1 == 116) {
          paramArrayOfbyte[paramInt2] = -95;
          paramArrayOfbyte[paramInt2 + 1] = 90;
          return 0;
        } 
        if (paramInt1 == -109) {
          paramArrayOfbyte[paramInt2] = -7;
          paramArrayOfbyte[paramInt2 + 1] = -2;
          return 0;
        } 
        break;
      case 38:
        if (paramInt1 == 9 || paramInt1 == 65)
          return -1; 
        break;
      case 39:
        if (paramInt1 == 61) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -26;
          return 0;
        } 
        break;
      case 47:
        if (paramInt1 == 2) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -65;
          return 0;
        } 
        if (paramInt1 == 3) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -64;
          return 0;
        } 
        if (paramInt1 == 5) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -63;
          return 0;
        } 
        if (paramInt1 == 7) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -62;
          return 0;
        } 
        if (paramInt1 == 12) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -61;
          return 0;
        } 
        if (paramInt1 == 13) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -60;
          return 0;
        } 
        if (paramInt1 == 14) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -59;
          return 0;
        } 
        if (paramInt1 == 19) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -58;
          return 0;
        } 
        if (paramInt1 == 22) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -57;
          return 0;
        } 
        if (paramInt1 == 25) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -56;
          return 0;
        } 
        if (paramInt1 == 27) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -55;
          return 0;
        } 
        if (paramInt1 == 34) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -54;
          return 0;
        } 
        if (paramInt1 == 39) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -53;
          return 0;
        } 
        if (paramInt1 == 46) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -52;
          return 0;
        } 
        if (paramInt1 == 51) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -51;
          return 0;
        } 
        if (paramInt1 == 52) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -50;
          return 0;
        } 
        if (paramInt1 == 53) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -49;
          return 0;
        } 
        if (paramInt1 == 57) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -48;
          return 0;
        } 
        if (paramInt1 == 58) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -47;
          return 0;
        } 
        if (paramInt1 == 65) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -46;
          return 0;
        } 
        if (paramInt1 == 70) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -45;
          return 0;
        } 
        if (paramInt1 == 103) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -44;
          return 0;
        } 
        if (paramInt1 == 104) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -43;
          return 0;
        } 
        if (paramInt1 == -95) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -42;
          return 0;
        } 
        if (paramInt1 == -86) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -41;
          return 0;
        } 
        break;
      case 48:
        if (paramInt1 >= 5 && paramInt1 <= 7) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = (byte)(paramInt1 - 12069);
          return 0;
        } 
        if (paramInt1 >= 56 && paramInt1 <= 58) {
          paramArrayOfbyte[paramInt2] = -94;
          paramArrayOfbyte[paramInt2 + 1] = (byte)(paramInt1 - 12140);
          return 0;
        } 
        if (paramInt1 >= 65 && paramInt1 <= -109) {
          if (paramInt1 < 12377) {
            paramArrayOfbyte[paramInt2] = -58;
            paramArrayOfbyte[paramInt2 + 1] = (byte)(paramInt1 - 12122);
          } else {
            paramArrayOfbyte[paramInt2] = -57;
            paramArrayOfbyte[paramInt2 + 1] = (byte)(paramInt1 - 12313);
          } 
          return 0;
        } 
        if (paramInt1 == -99) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -36;
          return 0;
        } 
        if (paramInt1 == -98) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -35;
          return 0;
        } 
        if (paramInt1 >= -95 && paramInt1 <= -10) {
          paramArrayOfbyte[paramInt2] = -57;
          paramArrayOfbyte[paramInt2 + 1] = (byte)(paramInt1 - ((paramInt1 < 12453) ? 12326 : 12292));
          return 0;
        } 
        if (paramInt1 == -4) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -29;
          return 0;
        } 
        if (paramInt1 == -3) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -38;
          return 0;
        } 
        if (paramInt1 == -2) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -37;
          return 0;
        } 
        break;
      case 83:
        if (paramInt1 == 68)
          return -1; 
        break;
      case 88:
        if (paramInt1 == -69) {
          paramArrayOfbyte[paramInt2] = -7;
          paramArrayOfbyte[paramInt2 + 1] = -39;
          return 0;
        } 
        break;
      case 90:
        if (paramInt1 == -6) {
          paramArrayOfbyte[paramInt2] = -7;
          paramArrayOfbyte[paramInt2 + 1] = -36;
          return 0;
        } 
        break;
      case 95:
        if (paramInt1 == 94) {
          paramArrayOfbyte[paramInt2] = -62;
          paramArrayOfbyte[paramInt2 + 1] = 85;
          return 0;
        } 
        if (paramInt1 == 93)
          return -1; 
        break;
      case 96:
        if (paramInt1 == 82) {
          paramArrayOfbyte[paramInt2] = -7;
          paramArrayOfbyte[paramInt2 + 1] = -38;
          return 0;
        } 
        break;
      case 120:
        if (paramInt1 == -127) {
          paramArrayOfbyte[paramInt2] = -7;
          paramArrayOfbyte[paramInt2 + 1] = -42;
          return 0;
        } 
        break;
      case 124:
        if (paramInt1 == -89) {
          paramArrayOfbyte[paramInt2] = -7;
          paramArrayOfbyte[paramInt2 + 1] = -37;
          return 0;
        } 
        break;
      case 136:
        if (paramInt1 == -49) {
          paramArrayOfbyte[paramInt2] = -7;
          paramArrayOfbyte[paramInt2 + 1] = -40;
          return 0;
        } 
        break;
      case 146:
        if (paramInt1 == -71) {
          paramArrayOfbyte[paramInt2] = -7;
          paramArrayOfbyte[paramInt2 + 1] = -41;
          return 0;
        } 
        break;
      case 224:
      case 225:
      case 226:
      case 227:
      case 228:
      case 229:
      case 230:
      case 231:
      case 232:
      case 233:
      case 234:
      case 235:
      case 236:
      case 237:
      case 238:
      case 239:
      case 240:
      case 241:
      case 242:
      case 243:
      case 244:
      case 245:
      case 246:
        i = paramInt1 - 57344;
        if (i < 5809) {
          int j = i / 157;
          int k = i % 157;
          paramArrayOfbyte[paramInt2] = (byte)(j + ((j < 5) ? 250 : ((j < 24) ? 137 : 105)));
          paramArrayOfbyte[paramInt2 + 1] = (byte)(k + ((k < 63) ? 64 : 98));
          return 0;
        } 
        break;
      case 254:
        if (paramInt1 == 81) {
          paramArrayOfbyte[paramInt2] = -95;
          paramArrayOfbyte[paramInt2 + 1] = 78;
          return 0;
        } 
        if (paramInt1 == 104) {
          paramArrayOfbyte[paramInt2] = -94;
          paramArrayOfbyte[paramInt2 + 1] = 66;
          return 0;
        } 
        break;
      case 255:
        if (paramInt1 == 15) {
          paramArrayOfbyte[paramInt2] = -95;
          paramArrayOfbyte[paramInt2 + 1] = -2;
          return 0;
        } 
        if (paramInt1 == 59) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -28;
          return 0;
        } 
        if (paramInt1 == 60) {
          paramArrayOfbyte[paramInt2] = -94;
          paramArrayOfbyte[paramInt2 + 1] = 64;
          return 0;
        } 
        if (paramInt1 == 61) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -27;
          return 0;
        } 
        if (paramInt1 == 62) {
          paramArrayOfbyte[paramInt2] = -58;
          paramArrayOfbyte[paramInt2 + 1] = -39;
          return 0;
        } 
        if (paramInt1 == 94) {
          paramArrayOfbyte[paramInt2] = -95;
          paramArrayOfbyte[paramInt2 + 1] = -29;
          return 0;
        } 
        if (paramInt1 == -32) {
          paramArrayOfbyte[paramInt2] = -94;
          paramArrayOfbyte[paramInt2 + 1] = 70;
          return 0;
        } 
        if (paramInt1 == -31) {
          paramArrayOfbyte[paramInt2] = -94;
          paramArrayOfbyte[paramInt2 + 1] = 71;
          return 0;
        } 
        if (paramInt1 == -29) {
          paramArrayOfbyte[paramInt2] = -95;
          paramArrayOfbyte[paramInt2 + 1] = -61;
          return 0;
        } 
        if (paramInt1 == -27) {
          paramArrayOfbyte[paramInt2] = -94;
          paramArrayOfbyte[paramInt2 + 1] = 68;
          return 0;
        } 
        if (paramInt1 == 100)
          return -1; 
        break;
    } 
    return -1;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\Big5_2003CharToByteConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */