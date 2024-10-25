package com.tmax.tibero.jdbc.util;

import com.tmax.tibero.jdbc.err.TbError;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLWarning;
import java.util.Vector;

public class TbDTFormatParser implements TbDTFormatParserConstants {
  public TbDTFormatParserTokenManager token_source;
  
  SimpleCharStream jj_input_stream;
  
  public Token token;
  
  public Token jj_nt;
  
  private int jj_ntk;
  
  private int jj_gen;
  
  private final int[] jj_la1 = new int[4];
  
  private static int[] jj_la1_0;
  
  private static int[] jj_la1_1;
  
  private Vector jj_expentries = new Vector();
  
  private int[] jj_expentry;
  
  private int jj_kind = -1;
  
  public static TbDTFormat parse(String paramString) throws SQLWarning {
    if (paramString == null)
      return null; 
    StringReader stringReader = new StringReader(paramString.toUpperCase());
    try {
      return (new TbDTFormatParser(stringReader)).parse();
    } catch (ParseException parseException) {
      throw TbError.newSQLWarning(-90604, parseException);
    } catch (TokenMgrError tokenMgrError) {
      throw TbError.newSQLWarning(-90604, tokenMgrError);
    } 
  }
  
  private final TbDTFormat parse() throws ParseException {
    TbDTFormat tbDTFormat = new TbDTFormat();
    while (true) {
      switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
        case 23:
        case 24:
        case 25:
        case 26:
        case 27:
        case 28:
        case 29:
        case 30:
        case 31:
        case 32:
        case 33:
        case 34:
        case 35:
        case 36:
        case 37:
        case 38:
        case 39:
        case 40:
        case 41:
        case 42:
        case 43:
        case 44:
        case 45:
        case 46:
        case 47:
        case 48:
        case 49:
        case 50:
        case 51:
        case 52:
        case 53:
        case 54:
        case 55:
        case 56:
        case 57:
          field(tbDTFormat);
          break;
        case 15:
        case 16:
          delimiter(tbDTFormat);
          break;
        default:
          this.jj_la1[0] = this.jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
      } 
      switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
        case 15:
        case 16:
        case 23:
        case 24:
        case 25:
        case 26:
        case 27:
        case 28:
        case 29:
        case 30:
        case 31:
        case 32:
        case 33:
        case 34:
        case 35:
        case 36:
        case 37:
        case 38:
        case 39:
        case 40:
        case 41:
        case 42:
        case 43:
        case 44:
        case 45:
        case 46:
        case 47:
        case 48:
        case 49:
        case 50:
        case 51:
        case 52:
        case 53:
        case 54:
        case 55:
        case 56:
        case 57:
          continue;
      } 
      this.jj_la1[1] = this.jj_gen;
      jj_consume_token(0);
      return tbDTFormat;
    } 
  }
  
  private final void delimiter(TbDTFormat paramTbDTFormat) throws ParseException {
    TbDTFormatElement tbDTFormatElement;
    String str1;
    String str2;
    switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
      case 15:
        jj_consume_token(15);
        tbDTFormatElement = TbDTFormatElement.newInstance(15, this.token.toString());
        paramTbDTFormat.addElement(tbDTFormatElement);
        return;
      case 16:
        jj_consume_token(16);
        str1 = this.token.toString();
        str2 = str1.substring(1, str1.length() - 1);
        tbDTFormatElement = TbDTFormatElement.newInstance(15, str2);
        paramTbDTFormat.addElement(tbDTFormatElement);
        return;
    } 
    this.jj_la1[2] = this.jj_gen;
    jj_consume_token(-1);
    throw new ParseException();
  }
  
  private final void field(TbDTFormat paramTbDTFormat) throws ParseException {
    String str;
    boolean bool;
    switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
      case 23:
        jj_consume_token(23);
        break;
      case 25:
        jj_consume_token(25);
        break;
      case 26:
        jj_consume_token(26);
        break;
      case 28:
        jj_consume_token(28);
        break;
      case 29:
        jj_consume_token(29);
        break;
      case 30:
        jj_consume_token(30);
        break;
      case 27:
        jj_consume_token(27);
        break;
      case 24:
        jj_consume_token(24);
        break;
      case 31:
        jj_consume_token(31);
        break;
      case 32:
        jj_consume_token(32);
        break;
      case 33:
        jj_consume_token(33);
        break;
      case 34:
        jj_consume_token(34);
        break;
      case 35:
        jj_consume_token(35);
        break;
      case 36:
        jj_consume_token(36);
        break;
      case 37:
        jj_consume_token(37);
        break;
      case 38:
        jj_consume_token(38);
        break;
      case 39:
        jj_consume_token(39);
        break;
      case 40:
        jj_consume_token(40);
        break;
      case 41:
        jj_consume_token(41);
        break;
      case 43:
        jj_consume_token(43);
        break;
      case 42:
        jj_consume_token(42);
        break;
      case 44:
        jj_consume_token(44);
        break;
      case 45:
        jj_consume_token(45);
        break;
      case 46:
        jj_consume_token(46);
        break;
      case 47:
        jj_consume_token(47);
        break;
      case 48:
        jj_consume_token(48);
        break;
      case 49:
        jj_consume_token(49);
        break;
      case 50:
        jj_consume_token(50);
        break;
      case 51:
        jj_consume_token(51);
        break;
      case 52:
        jj_consume_token(52);
        break;
      case 54:
        jj_consume_token(54);
        break;
      case 53:
        jj_consume_token(53);
        break;
      case 56:
        jj_consume_token(56);
        break;
      case 55:
        jj_consume_token(55);
        break;
      case 57:
        jj_consume_token(57);
        break;
      default:
        this.jj_la1[3] = this.jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
    } 
    switch (this.token.kind) {
      case 31:
        paramTbDTFormat.setTrimEnabled(true);
        return;
      case 32:
        paramTbDTFormat.setExactFormatEnabled(true);
        return;
      case 33:
        str = this.token.toString();
        bool = (str.length() == 2) ? false : Integer.parseInt(str.substring(2));
        tbDTFormatElement = TbDTFormatElement.newInstance(this.token.kind, this.token.toString(), bool);
        paramTbDTFormat.addElement(tbDTFormatElement);
        return;
      case 42:
      case 55:
      case 56:
        str = this.token.toString();
        tbDTFormatElement = TbDTFormatElement.newInstance(this.token.kind, str, str.length());
        paramTbDTFormat.addElement(tbDTFormatElement);
        return;
    } 
    TbDTFormatElement tbDTFormatElement = TbDTFormatElement.newInstance(this.token.kind, this.token.toString());
    paramTbDTFormat.addElement(tbDTFormatElement);
  }
  
  private static void jj_la1_0() {
    jj_la1_0 = new int[] { -8290304, -8290304, 98304, -8388608 };
  }
  
  private static void jj_la1_1() {
    jj_la1_1 = new int[] { 67108863, 67108863, 0, 67108863 };
  }
  
  public TbDTFormatParser(InputStream paramInputStream) {
    this(paramInputStream, null);
  }
  
  public TbDTFormatParser(InputStream paramInputStream, String paramString) {
    try {
      this.jj_input_stream = new SimpleCharStream(paramInputStream, paramString, 1, 1);
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      throw new RuntimeException(unsupportedEncodingException);
    } 
    this.token_source = new TbDTFormatParserTokenManager(this.jj_input_stream);
    this.token = new Token();
    this.jj_ntk = -1;
    this.jj_gen = 0;
    for (byte b = 0; b < 4; b++)
      this.jj_la1[b] = -1; 
  }
  
  public void ReInit(InputStream paramInputStream) {
    ReInit(paramInputStream, null);
  }
  
  public void ReInit(InputStream paramInputStream, String paramString) {
    try {
      this.jj_input_stream.ReInit(paramInputStream, paramString, 1, 1);
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      throw new RuntimeException(unsupportedEncodingException);
    } 
    this.token_source.ReInit(this.jj_input_stream);
    this.token = new Token();
    this.jj_ntk = -1;
    this.jj_gen = 0;
    for (byte b = 0; b < 4; b++)
      this.jj_la1[b] = -1; 
  }
  
  public TbDTFormatParser(Reader paramReader) {
    this.jj_input_stream = new SimpleCharStream(paramReader, 1, 1);
    this.token_source = new TbDTFormatParserTokenManager(this.jj_input_stream);
    this.token = new Token();
    this.jj_ntk = -1;
    this.jj_gen = 0;
    for (byte b = 0; b < 4; b++)
      this.jj_la1[b] = -1; 
  }
  
  public void ReInit(Reader paramReader) {
    this.jj_input_stream.ReInit(paramReader, 1, 1);
    this.token_source.ReInit(this.jj_input_stream);
    this.token = new Token();
    this.jj_ntk = -1;
    this.jj_gen = 0;
    for (byte b = 0; b < 4; b++)
      this.jj_la1[b] = -1; 
  }
  
  public TbDTFormatParser(TbDTFormatParserTokenManager paramTbDTFormatParserTokenManager) {
    this.token_source = paramTbDTFormatParserTokenManager;
    this.token = new Token();
    this.jj_ntk = -1;
    this.jj_gen = 0;
    for (byte b = 0; b < 4; b++)
      this.jj_la1[b] = -1; 
  }
  
  public void ReInit(TbDTFormatParserTokenManager paramTbDTFormatParserTokenManager) {
    this.token_source = paramTbDTFormatParserTokenManager;
    this.token = new Token();
    this.jj_ntk = -1;
    this.jj_gen = 0;
    for (byte b = 0; b < 4; b++)
      this.jj_la1[b] = -1; 
  }
  
  private final Token jj_consume_token(int paramInt) throws ParseException {
    Token token;
    if ((token = this.token).next != null) {
      this.token = this.token.next;
    } else {
      this.token = this.token.next = this.token_source.getNextToken();
    } 
    this.jj_ntk = -1;
    if (this.token.kind == paramInt) {
      this.jj_gen++;
      return this.token;
    } 
    this.token = token;
    this.jj_kind = paramInt;
    throw generateParseException();
  }
  
  public final Token getNextToken() {
    if (this.token.next != null) {
      this.token = this.token.next;
    } else {
      this.token = this.token.next = this.token_source.getNextToken();
    } 
    this.jj_ntk = -1;
    this.jj_gen++;
    return this.token;
  }
  
  public final Token getToken(int paramInt) {
    Token token = this.token;
    for (byte b = 0; b < paramInt; b++) {
      if (token.next != null) {
        token = token.next;
      } else {
        token = token.next = this.token_source.getNextToken();
      } 
    } 
    return token;
  }
  
  private final int jj_ntk() {
    return ((this.jj_nt = this.token.next) == null) ? (this.jj_ntk = (this.token.next = this.token_source.getNextToken()).kind) : (this.jj_ntk = this.jj_nt.kind);
  }
  
  public ParseException generateParseException() {
    this.jj_expentries.removeAllElements();
    boolean[] arrayOfBoolean = new boolean[58];
    byte b1;
    for (b1 = 0; b1 < 58; b1++)
      arrayOfBoolean[b1] = false; 
    if (this.jj_kind >= 0) {
      arrayOfBoolean[this.jj_kind] = true;
      this.jj_kind = -1;
    } 
    for (b1 = 0; b1 < 4; b1++) {
      if (this.jj_la1[b1] == this.jj_gen)
        for (byte b = 0; b < 32; b++) {
          if ((jj_la1_0[b1] & 1 << b) != 0)
            arrayOfBoolean[b] = true; 
          if ((jj_la1_1[b1] & 1 << b) != 0)
            arrayOfBoolean[32 + b] = true; 
        }  
    } 
    for (b1 = 0; b1 < 58; b1++) {
      if (arrayOfBoolean[b1]) {
        this.jj_expentry = new int[1];
        this.jj_expentry[0] = b1;
        this.jj_expentries.addElement(this.jj_expentry);
      } 
    } 
    int[][] arrayOfInt = new int[this.jj_expentries.size()][];
    for (byte b2 = 0; b2 < this.jj_expentries.size(); b2++)
      arrayOfInt[b2] = this.jj_expentries.elementAt(b2); 
    return new ParseException(this.token, arrayOfInt, tokenImage);
  }
  
  public final void enable_tracing() {}
  
  public final void disable_tracing() {}
  
  static {
    jj_la1_0();
    jj_la1_1();
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdb\\util\TbDTFormatParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */