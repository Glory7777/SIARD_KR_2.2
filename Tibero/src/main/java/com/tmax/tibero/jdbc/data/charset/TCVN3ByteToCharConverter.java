package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;

public class TCVN3ByteToCharConverter {
  protected boolean subMode = true;
  
  protected char[] subChars = new char[] { '?' };
  
  private static final char[] TCVN3_TO_UNICODE_PAGE = new char[] { 
      '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', 
      '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', 
      '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', 
      '�', '�', '�', 'Ă', 'Â', 'Ê', 'Ô', 'Ơ', 'Ư', 'Đ', 
      'ă', 'â', 'ê', 'ô', 'ơ', 'ư', 'đ', '�', '�', '�', 
      '�', '�', '�', 'à', 'ả', 'ã', 'á', 'ạ', '�', 'ằ', 
      'ẳ', 'ẵ', 'ắ', '�', '�', '�', '�', '�', '�', '�', 
      'ặ', 'ầ', 'ẩ', 'ẫ', 'ấ', 'ậ', 'è', '�', 'ẻ', 'ẽ', 
      'é', 'ẹ', 'ề', 'ể', 'ễ', 'ế', 'ệ', 'ì', 'ỉ', '�', 
      '�', '�', 'ĩ', 'í', 'ị', 'ò', '�', 'ỏ', 'õ', 'ó', 
      'ọ', 'ồ', 'ổ', 'ỗ', 'ố', 'ộ', 'ờ', 'ở', 'ỡ', 'ớ', 
      'ợ', 'ù', '�', 'ủ', 'ũ', 'ú', 'ụ', 'ừ', 'ử', 'ữ', 
      'ứ', 'ự', 'ỳ', 'ỷ', 'ỹ', 'ý', 'ỵ', '�' };
  
  private int decodeFromUcs(byte paramByte1, byte paramByte2) {
    return (paramByte1 << 8) + (paramByte2 & 0xFF);
  }
  
  private void encodeUCharToUCS2(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
    int bool = 0;
    byte b = (byte)paramInt2;
    paramArrayOfchar[paramInt1] = (char)bool;
    paramArrayOfchar[paramInt1++] = (char)b;
  }
  
  public int convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
    int i = paramInt1;
    int j;
    for (j = paramInt3; i < paramInt2 && j < paramInt4; j++) {
      int k = decodeFromUcs((byte)0, paramArrayOfbyte[i]);
      if (k < 128) {
        paramArrayOfchar[j] = (char)k;
      } else {
        int m = k - 128;
        k = TCVN3_TO_UNICODE_PAGE[m];
        if (k != 65533) {
          paramArrayOfchar[j] = (char)k;
        } else if (this.subMode) {
          encodeUCharToUCS2(paramArrayOfchar, j, this.subChars[0]);
        } else {
          throw TbError.newSQLException(-590742, k);
        } 
      } 
      i++;
    } 
    return j - paramInt3;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\TCVN3ByteToCharConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */