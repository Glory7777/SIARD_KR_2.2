package com.tmax.tibero.jdbc.util;

public class TbSQLTypeScanner {
  private static final char NULL = '\000';
  
  private static final int STATE_START = 0;
  
  private static final int STATE_1 = 1;
  
  private static final int STATE_2 = 2;
  
  private static final int STATE_3 = 3;
  
  private static final int STATE_4 = 4;
  
  private static final int STATE_5 = 5;
  
  private static final int STATE_6 = 6;
  
  private static final int STATE_DONE = 10;
  
  public static final int STMT_UNKNOWN = 0;
  
  public static final int STMT_QUERY = 16;
  
  public static final int STMT_DML = 32;
  
  public static final int STMT_DML_DELETE = 33;
  
  public static final int STMT_DML_UPDATE = 34;
  
  public static final int STMT_DML_INSERT = 36;
  
  public static final int STMT_DML_MERGE = 40;
  
  public static final int STMT_PSM = 64;
  
  public static final int STMT_PSM_BEGIN = 65;
  
  public static final int STMT_PSM_CALL = 66;
  
  public static final int STMT_PSM_DECLARE = 68;
  
  public static final int STMT_DDL = 128;
  
  public static final int STMT_DDL_JAVA_SRC = 136;
  
  private int state;
  
  private int index;
  
  private int textLen;
  
  private String text;
  
  public int getCurrentIndex() {
    return this.index;
  }
  
  private char getNextChar() {
    return (this.index < this.textLen) ? this.text.charAt(this.index++) : Character.MIN_VALUE;
  }
  
  public synchronized String callDeclareSyntax(String paramString) {
    this.index = 0;
    this.textLen = paramString.length();
    this.text = paramString;
    String str = getToken();
    String[] arrayOfString = { "{", "CALL", "DECLARE", "<<", ">>" };
    if (str.startsWith(arrayOfString[0])) {
      str = (str.length() == arrayOfString[0].length()) ? getToken() : str.substring(arrayOfString[0].length());
      if (str.equals(arrayOfString[1])) {
        str = getToken();
        if (str.equals(arrayOfString[2])) {
          this.text = this.text.replaceFirst("CALL", "").replaceFirst("call", "").replaceFirst("\\{", "");
          this.text = replaceLast(this.text, "}", ";");
        } else if (str.startsWith(arrayOfString[3])) {
          while (!str.equals(arrayOfString[4]))
            str = (str.length() == arrayOfString[3].length()) ? getToken() : str.substring(arrayOfString[3].length()); 
          str = getToken();
          if (str.equals(arrayOfString[2])) {
            this.text = this.text.replaceFirst("CALL", "").replaceFirst("call", "").replaceFirst("\\{", "");
            this.text = replaceLast(this.text, "}", ";");
          } 
        } 
      } 
    } 
    return this.text;
  }
  
  private static String replaceLast(String paramString1, String paramString2, String paramString3) {
    return paramString1.replaceFirst("(?s)(.*)" + paramString2, "$1" + paramString3);
  }
  
  public synchronized int getSQLType(String paramString) {
    this.index = 0;
    this.textLen = paramString.length();
    this.text = paramString;
    String str = getToken();
    if (str.equals("("))
      str = getToken(); 
    if (str.equals("SELECT") || str.equals("WITH"))
      return 16; 
    if (str.equals("DELETE"))
      return 33; 
    if (str.equals("INSERT"))
      return 36; 
    if (str.equals("UPDATE"))
      return 34; 
    if (str.equals("MERGE"))
      return 40; 
    if (str.equals("BEGIN"))
      return 65; 
    if (str.equals("DECLARE"))
      return 68; 
    if (str.equals("CALL"))
      return 66; 
    String[] arrayOfString1 = { "CREATE", "OR", "REPLACE", "AND", "RESOLVE", "JAVA", "SOURCE" };
    if (str.equals(arrayOfString1[0])) {
      int i = 1;
      int j = 1;
      while (j == 1) {
        String str1 = getToken();
        int k;
        for (k = i; k < arrayOfString1.length; k++) {
          if (arrayOfString1[k].equals(str1)) {
            i = k + 1;
            j |= 1 << k;
            break;
          } 
        } 
        if ((j & 0x1) > 0 && (j & 0x20) > 0 && (j & 0x40) > 0)
          return 136; 
        if (k == arrayOfString1.length)
          j = 0; 
      } 
      return 128;
    } 
    String[] arrayOfString2 = { "{", "?", "=", "CALL" };
    if (str.startsWith(arrayOfString2[0])) {
      int i = 1;
      int j = 1;
      String str1 = str;
      int k = arrayOfString2[0].length();
      while (j == 1) {
        str1 = (str1.length() == k) ? getToken() : str1.substring(k);
        int m;
        for (m = i; m < arrayOfString2.length; m++) {
          if (str1.startsWith(arrayOfString2[m])) {
            i = m + 1;
            k = arrayOfString2[m].length();
            j |= 1 << m;
            break;
          } 
          if (m == 1 && str1.length() > 1 && str1.charAt(0) == ':') {
            i = m + 1;
            int n = str1.indexOf('=');
            k = (n > 0) ? n : str1.length();
            j |= 1 << m;
            break;
          } 
        } 
        if ((j & 0x1) > 0 && (j & 0x8) > 0)
          return 66; 
        if (m == arrayOfString2.length)
          j = 0; 
      } 
    } 
    return 0;
  }
  
  private String getToken() {
    this.state = 0;
    StringBuffer stringBuffer = new StringBuffer(32);
    while (this.state != 10) {
      char c = getNextChar();
      boolean bool = true;
      switch (this.state) {
        case 0:
          switch (c) {
            case '/':
              this.state = 1;
              break;
            case '-':
              this.state = 4;
              break;
            case '\'':
              this.state = 6;
              break;
            case '(':
            case ')':
              if (stringBuffer.length() > 0) {
                this.state = 10;
                bool = false;
                ungetNextChar();
                break;
              } 
              this.state = 10;
              bool = true;
              break;
            case '\t':
            case '\n':
            case '\r':
            case ' ':
              if (stringBuffer.length() > 0)
                this.state = 10; 
              bool = false;
              break;
            case '\000':
              this.state = 10;
              bool = false;
              break;
          } 
          break;
        case 1:
          switch (c) {
            case '*':
              this.state = 2;
              bool = false;
              stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
              break;
            case '(':
            case ')':
              this.state = 10;
              bool = false;
              ungetNextChar();
              break;
            case '\000':
              this.state = 10;
              bool = false;
              break;
          } 
          this.state = 0;
          break;
        case 2:
          switch (c) {
            case '*':
              this.state = 3;
              bool = false;
              break;
            case '\000':
              this.state = 10;
              bool = false;
              break;
          } 
          bool = false;
          break;
        case 3:
          switch (c) {
            case '*':
              bool = false;
              break;
            case '/':
              this.state = 0;
              bool = false;
              break;
            case '\000':
              this.state = 10;
              bool = false;
              break;
          } 
          this.state = 2;
          bool = false;
          break;
        case 4:
          switch (c) {
            case '-':
              this.state = 5;
              bool = false;
              stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
              break;
            case '(':
            case ')':
              this.state = 10;
              bool = false;
              ungetNextChar();
              break;
            case '\000':
              this.state = 10;
              bool = false;
              break;
          } 
          this.state = 0;
          break;
        case 5:
          switch (c) {
            case '\n':
            case '\r':
              this.state = 0;
              ungetNextChar();
              bool = false;
              break;
            case '\000':
              this.state = 10;
              bool = false;
              break;
          } 
          bool = false;
          break;
        case 6:
          switch (c) {
            case '\'':
              this.state = 0;
              break;
            case '\000':
              this.state = 10;
              bool = false;
              break;
          } 
          break;
      } 
      if (bool)
        stringBuffer.append(c); 
    } 
    return stringBuffer.substring(0, stringBuffer.length()).toUpperCase();
  }
  
  private void ungetNextChar() {
    this.index--;
  }
  
  public static boolean isDMLStmt(int paramInt) {
    return ((paramInt & 0x20) > 0);
  }
  
  public static boolean isPSMStmt(int paramInt) {
    return ((paramInt & 0x40) > 0);
  }
  
  public static boolean isQueryStmt(int paramInt) {
    return ((paramInt & 0x10) > 0);
  }
  
  public static boolean isDDLStmt(int paramInt) {
    return ((paramInt & 0x80) > 0);
  }
  
  public static boolean isAnonBlockStmt(int paramInt) {
    return (paramInt == 65 || paramInt == 68);
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdb\\util\TbSQLTypeScanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */