package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;

public class MS1252ByteToCharConverter {
  protected boolean subMode = true;
  
  protected char[] subChars = new char[] { '?' };
  
  private static final char[] MS1252_TO_UNICODE_PAGE = new char[] { 
      '€', '�', '‚', 'ƒ', '„', '…', '†', '‡', 'ˆ', '‰', 
      'Š', '‹', 'Œ', '�', 'Ž', '�', '�', '‘', '’', '“', 
      '”', '•', '–', '—', '˜', '™', 'š', '›', 'œ', '�', 
      'ž', 'Ÿ' };
  
  private int decodeFromUcs(byte paramByte1, byte paramByte2) {
    return (paramByte1 << 8) + (paramByte2 & 0xFF);
  }
  
  private void encodeUCharToUCS2(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
    boolean bool = false;
    byte b = (byte)paramInt2;
    paramArrayOfchar[paramInt1] = (char)bool;
    paramArrayOfchar[paramInt1++] = (char)b;
  }
  
  public int convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
    int i = paramInt1;
    int j = paramInt3;
    while (i < paramInt2) {
      int k = decodeFromUcs((byte)0, paramArrayOfbyte[i]);
      if (k < 128 || k >= 160) {
        paramArrayOfchar[j++] = (char)k;
        i++;
        continue;
      } 
      int m = k - 128;
      k = MS1252_TO_UNICODE_PAGE[m];
      if (k != 65533) {
        paramArrayOfchar[j++] = (char)k;
        i++;
        continue;
      } 
      if (this.subMode) {
        encodeUCharToUCS2(paramArrayOfchar, j, this.subChars[0]);
        j++;
        continue;
      } 
      throw TbError.newSQLException(-590742, k);
    } 
    return j - paramInt3;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\MS1252ByteToCharConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */