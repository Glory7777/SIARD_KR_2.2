package com.tmax.tibero.jdbc.util;

public class TokenMgrError extends Error {
  static final int LEXICAL_ERROR = 0;
  
  static final int STATIC_LEXER_ERROR = 1;
  
  static final int INVALID_LEXICAL_STATE = 2;
  
  static final int LOOP_DETECTED = 3;
  
  int errorCode;
  
  protected static final String addEscapes(String paramString) {
    StringBuffer stringBuffer = new StringBuffer();
    for (byte b = 0; b < paramString.length(); b++) {
      char c;
      switch (paramString.charAt(b)) {
        case '\000':
          break;
        case '\b':
          stringBuffer.append("\\b");
          break;
        case '\t':
          stringBuffer.append("\\t");
          break;
        case '\n':
          stringBuffer.append("\\n");
          break;
        case '\f':
          stringBuffer.append("\\f");
          break;
        case '\r':
          stringBuffer.append("\\r");
          break;
        case '"':
          stringBuffer.append("\\\"");
          break;
        case '\'':
          stringBuffer.append("\\'");
          break;
        case '\\':
          stringBuffer.append("\\\\");
          break;
        default:
          if ((c = paramString.charAt(b)) < ' ' || c > '~') {
            String str = "0000" + Integer.toString(c, 16);
            stringBuffer.append("\\u" + str.substring(str.length() - 4, str.length()));
            break;
          } 
          stringBuffer.append(c);
          break;
      } 
    } 
    return stringBuffer.toString();
  }
  
  protected static String LexicalError(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, String paramString, char paramChar) {
    return "Lexical error at line " + paramInt2 + ", column " + paramInt3 + ".  Encountered: " + (paramBoolean ? "<EOF> " : ("\"" + addEscapes(String.valueOf(paramChar)) + "\"" + " (" + paramChar + "), ")) + "after : \"" + addEscapes(paramString) + "\"";
  }
  
  public String getMessage() {
    return super.getMessage();
  }
  
  public TokenMgrError() {}
  
  public TokenMgrError(String paramString, int paramInt) {
    super(paramString);
    this.errorCode = paramInt;
  }
  
  public TokenMgrError(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, String paramString, char paramChar, int paramInt4) {
    this(LexicalError(paramBoolean, paramInt1, paramInt2, paramInt3, paramString, paramChar), paramInt4);
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdb\\util\TokenMgrError.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */