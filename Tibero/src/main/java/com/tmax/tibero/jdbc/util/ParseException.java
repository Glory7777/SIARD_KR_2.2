package com.tmax.tibero.jdbc.util;

public class ParseException extends Exception {
  protected boolean specialConstructor = true;
  
  public Token currentToken;
  
  public int[][] expectedTokenSequences;
  
  public String[] tokenImage;
  
  protected String eol = System.getProperty("line.separator", "\n");
  
  public ParseException(Token paramToken, int[][] paramArrayOfint, String[] paramArrayOfString) {
    super("");
    this.currentToken = paramToken;
    this.expectedTokenSequences = paramArrayOfint;
    this.tokenImage = paramArrayOfString;
  }
  
  public ParseException() {}
  
  public ParseException(String paramString) {
    super(paramString);
  }

  public String getMessage() {
    if (!this.specialConstructor) {
      return super.getMessage();
    }

    StringBuilder stringBuffer = new StringBuilder();
    int maxLength = 0;

      for (int[] expectedTokenSequence : this.expectedTokenSequences) {
          if (maxLength < expectedTokenSequence.length) {
              maxLength = expectedTokenSequence.length;
          }

          for (int i : expectedTokenSequence) {
              stringBuffer.append(this.tokenImage[i]).append(" ");
          }

          if (expectedTokenSequence[expectedTokenSequence.length - 1] != 0) {
              stringBuffer.append("...");
          }
          stringBuffer.append(this.eol).append("    ");
      }

    String message = "Encountered \"";
    Token token = this.currentToken.next;

    for (int index = 0; index < maxLength; index++) {
      if (index != 0) {
        message += " ";
      }

      if (token.kind == 0) {
        message += this.tokenImage[0];
        break;
      }

      message += add_escapes(token.image);
      token = token.next;
    }

    message += "\" at line " + this.currentToken.next.beginLine + ", column " + this.currentToken.next.beginColumn + "." + this.eol;

    if (this.expectedTokenSequences.length == 1) {
      message += "Was expecting:" + this.eol + "    ";
    } else {
      message += "Was expecting one of:" + this.eol + "    ";
    }

    message += stringBuffer.toString();
    return message;
  }


  protected String add_escapes(String paramString) {
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
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdb\\util\ParseException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */