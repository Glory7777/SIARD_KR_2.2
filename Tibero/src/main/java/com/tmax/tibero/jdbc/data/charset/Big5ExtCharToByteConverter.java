package com.tmax.tibero.jdbc.data.charset;

public class Big5ExtCharToByteConverter {
  protected boolean subMode = true;
  
  protected byte[] subBytes = new byte[] { 63 };
  
  public int getMaxBytesPerChar() {
    return 2;
  }
  
  public int convert(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
    int i;
    switch (paramInt1 >> 8) {
      case 0:
        if (paramInt1 == 175) {
          paramArrayOfbyte[paramInt2] = -95;
          paramArrayOfbyte[paramInt2 + 1] = -62;
          return 0;
        } 
        if (paramInt1 == 162 || paramInt1 == 163 || paramInt1 == 164)
          return -1; 
        break;
      case 2:
        if (paramInt1 == 717) {
          paramArrayOfbyte[paramInt2] = -95;
          paramArrayOfbyte[paramInt2 + 1] = -59;
          return 0;
        } 
        break;
      case 32:
        if (paramInt1 == 8231) {
          paramArrayOfbyte[paramInt2] = -95;
          paramArrayOfbyte[paramInt2 + 1] = 69;
          return 0;
        } 
        if (paramInt1 == 8364) {
          paramArrayOfbyte[paramInt2] = -93;
          paramArrayOfbyte[paramInt2 + 1] = -31;
          return 0;
        } 
        if (paramInt1 == 8226 || paramInt1 == 8254)
          return -1; 
        break;
      case 34:
        if (paramInt1 == 8725) {
          paramArrayOfbyte[paramInt2] = -94;
          paramArrayOfbyte[paramInt2 + 1] = 65;
          return 0;
        } 
        if (paramInt1 == 8853) {
          paramArrayOfbyte[paramInt2] = -95;
          paramArrayOfbyte[paramInt2 + 1] = -14;
          return 0;
        } 
        if (paramInt1 == 8857) {
          paramArrayOfbyte[paramInt2] = -95;
          paramArrayOfbyte[paramInt2 + 1] = -13;
          return 0;
        } 
        if (paramInt1 == 8764)
          return -1; 
        break;
      case 37:
        if (paramInt1 == 9588) {
          paramArrayOfbyte[paramInt2] = -95;
          paramArrayOfbyte[paramInt2 + 1] = 90;
          return 0;
        } 
        break;
      case 38:
        if (paramInt1 == 9737 || paramInt1 == 9793)
          return -1; 
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
        if (paramInt1 == 65105) {
          paramArrayOfbyte[paramInt2] = -95;
          paramArrayOfbyte[paramInt2 + 1] = 78;
          return 0;
        } 
        if (paramInt1 == 65128) {
          paramArrayOfbyte[paramInt2] = -94;
          paramArrayOfbyte[paramInt2 + 1] = 66;
          return 0;
        } 
        break;
      case 255:
        if (paramInt1 == 65295) {
          paramArrayOfbyte[paramInt2] = -95;
          paramArrayOfbyte[paramInt2 + 1] = -2;
          return 0;
        } 
        if (paramInt1 == 65340) {
          paramArrayOfbyte[paramInt2] = -94;
          paramArrayOfbyte[paramInt2 + 1] = 64;
          return 0;
        } 
        if (paramInt1 == 65374) {
          paramArrayOfbyte[paramInt2] = -95;
          paramArrayOfbyte[paramInt2 + 1] = -29;
          return 0;
        } 
        if (paramInt1 == 65504) {
          paramArrayOfbyte[paramInt2] = -94;
          paramArrayOfbyte[paramInt2 + 1] = 70;
          return 0;
        } 
        if (paramInt1 == 65505) {
          paramArrayOfbyte[paramInt2] = -94;
          paramArrayOfbyte[paramInt2 + 1] = 71;
          return 0;
        } 
        if (paramInt1 == 65507) {
          paramArrayOfbyte[paramInt2] = -95;
          paramArrayOfbyte[paramInt2 + 1] = -61;
          return 0;
        } 
        if (paramInt1 == 65509) {
          paramArrayOfbyte[paramInt2] = -94;
          paramArrayOfbyte[paramInt2 + 1] = 68;
          return 0;
        } 
        if (paramInt1 == 65380)
          return -1; 
        break;
    } 
    return -1;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\Big5ExtCharToByteConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */