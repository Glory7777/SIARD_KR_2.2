package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;

public class MS950ByteToCharConverter {
  private HKSCS2001ByteToCharConverter hkscs2001converter = new HKSCS2001ByteToCharConverter();
  
  private Big5ByteToCharConverter big5Converter = new Big5ByteToCharConverter();
  
  private MS950ExtByteToCharConverter ms950ExtConverter = new MS950ExtByteToCharConverter();
  
  protected boolean subMode = true;
  
  protected char[] subChars = new char[] { '?' };
  
  private static final char[] CP950_TO_UNICODE_PAGEA1 = new char[] { 
      '　', '，', '、', '。', '．', '‧', '；', '：', '？', '！', 
      '︰', '…', '‥', '﹐', '﹑', '﹒', '·', '﹔', '﹕', '﹖', 
      '﹗', '｜', '–', '︱', '—', '︳', '╴', '︴', '﹏', '（', 
      '）', '︵', '︶', '｛', '｝', '︷', '︸', '〔', '〕', '︹', 
      '︺', '【', '】', '︻', '︼', '《', '》', '︽', '︾', '〈', 
      '〉', '︿', '﹀', '「', '」', '﹁', '﹂', '『', '』', '﹃', 
      '﹄', '﹙', '﹚', '﹛', '﹜', '﹝', '﹞', '‘', '’', '“', 
      '”', '〝', '〞', '‵', '′', '＃', '＆', '＊', '※', '§', 
      '〃', '○', '●', '△', '▲', '◎', '☆', '★', '◇', '◆', 
      '□', '■', '▽', '▼', '㊣', '℅', '¯', '￣', '＿', 'ˍ', 
      '﹉', '﹊', '﹍', '﹎', '﹋', '﹌', '﹟', '﹠', '﹡', '＋', 
      '－', '×', '÷', '±', '√', '＜', '＞', '＝', '≦', '≧', 
      '≠', '∞', '≒', '≡', '﹢', '﹣', '﹤', '﹥', '﹦', '～', 
      '∩', '∪', '⊥', '∠', '∟', '⊿', '㏒', '㏑', '∫', '∮', 
      '∵', '∴', '♀', '♂', '⊕', '⊙', '↑', '↓', '←', '→', 
      '↖', '↗', '↙', '↘', '∥', '∣', '／', '＼', '∕', '﹨', 
      '＄', '￥', '〒', '￠', '￡', '％', '＠', '℃', '℉', '﹩', 
      '﹪', '﹫', '㏕', '㎜', '㎝', '㎞', '㏎', '㎡', '㎎', '㎏', 
      '㏄', '°', '兙', '兛', '兞', '兝', '兡', '兣', '嗧', '瓩', 
      '糎', '▁', '▂', '▃', '▄', '▅', '▆', '▇', '█', '▏', 
      '▎', '▍', '▌', '▋', '▊', '▉', '┼', '┴', '┬', '┤', 
      '├', '▔', '─', '│', '▕', '┌', '┐', '└', '┘', '╭', 
      '╮', '╰', '╯', '═', '╞', '╪', '╡', '◢', '◣', '◥', 
      '◤', '╱', '╲', '╳', '０', '１', '２', '３', '４', '５', 
      '６', '７', '８', '９', 'Ⅰ', 'Ⅱ', 'Ⅲ', 'Ⅳ', 'Ⅴ', 'Ⅵ', 
      'Ⅶ', 'Ⅷ', 'Ⅸ', 'Ⅹ', '〡', '〢', '〣', '〤', '〥', '〦', 
      '〧', '〨', '〩', '十', '卄', '卅', 'Ａ', 'Ｂ', 'Ｃ', 'Ｄ', 
      'Ｅ', 'Ｆ', 'Ｇ', 'Ｈ', 'Ｉ', 'Ｊ', 'Ｋ', 'Ｌ', 'Ｍ', 'Ｎ', 
      'Ｏ', 'Ｐ', 'Ｑ', 'Ｒ', 'Ｓ', 'Ｔ', 'Ｕ', 'Ｖ', 'Ｗ', 'Ｘ', 
      'Ｙ', 'Ｚ', 'ａ', 'ｂ', 'ｃ', 'ｄ', 'ｅ', 'ｆ', 'ｇ', 'ｈ', 
      'ｉ', 'ｊ', 'ｋ', 'ｌ', 'ｍ', 'ｎ', 'ｏ', 'ｐ', 'ｑ', 'ｒ', 
      'ｓ', 'ｔ', 'ｕ', 'ｖ' };
  
  private void decodeUCharToUCS2(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
    int bool = 0;
    byte b = (byte)paramInt2;
    paramArrayOfchar[paramInt1] = (char)bool;
    paramArrayOfchar[paramInt1++] = (char)b;
  }
  
  private void decodeUShortToUCS2(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
    byte b1 = (byte)(paramInt2 >> 8);
    byte b2 = (byte)(paramInt2 & 0xFF);
    paramArrayOfchar[paramInt1] = (char)b1;
    paramArrayOfchar[paramInt1++] = (char)b2;
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
      if ((byte)b1 == -116) {
        b = paramArrayOfbyte[k + 1];
        int n = this.hkscs2001converter.convert((byte)b1, (byte)b, paramArrayOfchar, m);
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
        int n = this.ms950ExtConverter.convert((byte)b1, (byte)b, paramArrayOfchar, m);
        if (n == 0) {
          k += 2;
          m++;
          continue;
        } 
      } 
      if ((byte)b1 >= -127 && (byte)b1 < -1) {
        b = paramArrayOfbyte[k + 1];
        if (((byte)b >= 64 && (byte)b < Byte.MAX_VALUE) || ((byte)b >= -95 && (byte)b < -1)) {
          if (b1 >= -95) {
            if (b1 < -93) {
              int b3 = ((char)b >= '¡') ? 98 : 64;
              int i1 = 157 * ((byte)b1 - -95) + ((char)b & 0xFF) - (byte)b3;
              char c1 = CP950_TO_UNICODE_PAGEA1[i1];
              if (c1 != '�') {
                paramArrayOfchar[m++] = (char)c1;
                k += 2;
                continue;
              } 
            } 
            if (((byte)b1 != -58 || (char)b < '¡') && (byte)b1 != -57) {
              int i1 = this.big5Converter.convert((byte)b1, (byte)b, paramArrayOfchar, m);
              if (i1 == 0) {
                k += 2;
                m++;
                continue;
              } 
            } 
            if ((byte)b1 == -93 && (byte)b == -31) {
              paramArrayOfchar[m++] = '€';
              k += 2;
              continue;
            } 
            if (b1 >= 250) {
              int b3 = ((char)b >= '¡') ? 98 : 64;
              int i1 = 57344 + 157 * ((byte)b1 - -6) + ((char)b & 0xFF) - (byte)b3;
              paramArrayOfchar[m++] = (char)i1;
              k += 2;
            } 
            continue;
          } 
          char c = ((char)b1 >= '') ? '?' : '';
          int b2 = ((char)b >= '¡') ? 98 : 64;
          int n = c + 157 * ((byte)b1 - -127) + ((char)b & 0xFF) - (byte)b2;
          paramArrayOfchar[m++] = (char)n;
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


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\MS950ByteToCharConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */