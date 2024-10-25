package com.tmax.tibero.jdbc.util;

import com.tmax.tibero.jdbc.data.BigLiteral;
import com.tmax.tibero.jdbc.err.TbError;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;

public class TbSQLParser {
  private String originalSql;
  
  private int length;
  
  private StringBuffer parsedSql;
  
  private StringBuffer token;
  
  private char chr;
  
  private int index;
  
  private int openParenCnt;
  
  private boolean isString;
  
  private static final byte[] IDEOGRAPHIC_SPACE = new byte[] { -29, Byte.MIN_VALUE, Byte.MIN_VALUE };
  
  private void appendFunctionName(String paramString) throws SQLException {
    this.parsedSql.append(paramString);
    skipWhitespace();
    processSQL();
  }
  
  private void appendFunctionPrefix(String paramString) throws SQLException {
    skipWhitespace();
    if (this.index < this.length && (this.chr = this.originalSql.charAt(this.index)) == '(') {
      this.index++;
    } else {
      String str = new String(this.index + ": '(' is expected");
      throw TbError.newSQLException(-90632, str);
    } 
    this.parsedSql.append(paramString);
    skipWhitespace();
    processSQL();
  }
  
  private void checkCurrentChar(char paramChar) throws SQLException {
    this.chr = this.originalSql.charAt(this.index - 1);
    if (this.chr != paramChar) {
      String str = new String(this.index + ": '" + paramChar + "' is expected");
      throw TbError.newSQLException(-90632, str);
    } 
  }
  
  public static String getBigLiteral(String paramString, int paramInt, ArrayList<BigLiteral> paramArrayList) {
    StringBuffer stringBuffer = new StringBuffer();
    byte b1 = 0;
    byte b2 = 0;
    char[] arrayOfChar = new char[paramString.length() + 1];
    paramString.getChars(0, paramString.length(), arrayOfChar, 0);
    while (true) {
      if (arrayOfChar[b1] == '-' && arrayOfChar[b1 + 1] == '-') {
        stringBuffer.append(arrayOfChar[b1++]);
        stringBuffer.append(arrayOfChar[b1++]);
        while (arrayOfChar[b1] != '\n') {
          if (arrayOfChar[b1] == '\000')
            return stringBuffer.toString(); 
          stringBuffer.append(arrayOfChar[b1++]);
        } 
        stringBuffer.append(arrayOfChar[b1++]);
        continue;
      } 
      if (arrayOfChar[b1] == '"') {
        stringBuffer.append(arrayOfChar[b1++]);
        while (arrayOfChar[b1] != '"') {
          if (arrayOfChar[b1] == '\000')
            return stringBuffer.toString(); 
          stringBuffer.append(arrayOfChar[b1++]);
        } 
        stringBuffer.append(arrayOfChar[b1++]);
        continue;
      } 
      if (arrayOfChar[b1] == '\'' && arrayOfChar[b1 + 1] == '\'') {
        stringBuffer.append(arrayOfChar[b1++]);
        stringBuffer.append(arrayOfChar[b1++]);
      } else if (arrayOfChar[b1] == '\'') {
        byte b = ++b1;
        StringBuffer stringBuffer1 = new StringBuffer();
        while (arrayOfChar[b1] != '\'') {
          if (arrayOfChar[b1] == '\000') {
            stringBuffer.append('\'');
            stringBuffer.append(stringBuffer1);
            return stringBuffer.toString();
          } 
          stringBuffer1.append(arrayOfChar[b1++]);
          while (arrayOfChar[b1] == '\'' && arrayOfChar[b1 + 1] == '\'') {
            stringBuffer1.append(arrayOfChar[b1++]);
            stringBuffer1.append(arrayOfChar[b1++]);
          } 
        } 
        if (b1 - b > 65535 / paramInt) {
          BigLiteral bigLiteral = new BigLiteral(b2++, paramString.substring(b, b1));
          paramArrayList.add(bigLiteral);
          stringBuffer.append('?');
        } else {
          stringBuffer.append('\'');
          stringBuffer.append(stringBuffer1);
          stringBuffer.append('\'');
        } 
        b1++;
        continue;
      } 
      if (arrayOfChar[b1] == '/' && arrayOfChar[b1 + 1] == '*') {
        stringBuffer.append(arrayOfChar[b1++]);
        stringBuffer.append(arrayOfChar[b1++]);
        while (true) {
          if (arrayOfChar[b1] != '*' || arrayOfChar[b1 + 1] != '/') {
            if (arrayOfChar[b1] == '\000')
              return stringBuffer.toString(); 
            stringBuffer.append(arrayOfChar[b1++]);
            continue;
          } 
          stringBuffer.append(arrayOfChar[b1++]);
          stringBuffer.append(arrayOfChar[b1++]);
        } 
        break;
      } 
      if (arrayOfChar[b1] == '\000')
        return stringBuffer.toString(); 
      if (arrayOfChar[b1] == '?' || (arrayOfChar[b1] == ':' && arrayOfChar[b1 + 1] != '=')) {
        stringBuffer.append(arrayOfChar[b1++]);
        b2++;
        continue;
      } 
      stringBuffer.append(arrayOfChar[b1++]);
    } 
  }
  
  public static int getParamCount(String paramString, int paramInt) {
    if (TbSQLTypeScanner.isDDLStmt(paramInt))
      return 0; 
    byte b1 = 0;
    byte b2 = 0;
    boolean bool = false;
    char[] arrayOfChar = new char[paramString.length() + 1];
    paramString.getChars(0, paramString.length(), arrayOfChar, 0);
    while (true) {
      if (arrayOfChar[b1] == '-' && arrayOfChar[b1 + 1] == '-') {
        for (b1 += 2; arrayOfChar[b1] != '\n'; b1++) {
          if (arrayOfChar[b1] == '\000')
            return b2; 
        } 
        b1++;
        continue;
      } 
      if (arrayOfChar[b1] == '"') {
        while (arrayOfChar[++b1] != '"') {
          if (arrayOfChar[b1] == '\000')
            return b2; 
          b1++;
        } 
        b1++;
        continue;
      } 
      if (arrayOfChar[b1] == '\'') {
        while (arrayOfChar[++b1] != '\'') {
          if (arrayOfChar[b1] == '\000')
            return b2; 
          b1++;
        } 
        b1++;
        continue;
      } 
      if (arrayOfChar[b1] == '/' && arrayOfChar[b1 + 1] == '*') {
        for (b1 += 2;; b1 += 2) {
          if (arrayOfChar[b1] != '*' || arrayOfChar[b1 + 1] != '/') {
            if (arrayOfChar[b1] == '\000')
              return b2; 
            b1++;
            continue;
          } 
        } 
        break;
      } 
      if ((!TbSQLTypeScanner.isPSMStmt(paramInt) && paramString.regionMatches(true, b1, "returning", 0, 9) && b1 > 1 && b1 + 9 < arrayOfChar.length && Character.isWhitespace(arrayOfChar[b1 - 1]) && Character.isWhitespace(arrayOfChar[b1 + 9])) || bool) {
        byte b = b1;
        if (!bool) {
          b1 += 9;
          b = b1;
        } 
        while (arrayOfChar[b1] != ';' && arrayOfChar[b1] != ')') {
          if (arrayOfChar[b1] == '\000')
            return b2; 
          if (!bool && paramString.regionMatches(true, b1, "into", 0, 3)) {
            bool = true;
            b1 = b;
            break;
          } 
          b1++;
        } 
        b1++;
        if (!bool)
          continue; 
      } 
      if (arrayOfChar[b1] == '\000')
        return b2; 
      if (arrayOfChar[b1] == '?' || (arrayOfChar[b1] == ':' && arrayOfChar[b1 + 1] != '='))
        b2++; 
      b1++;
    } 
  }
  
  public static String[] getParamNames(String paramString) {
    byte b = 0;
    char[] arrayOfChar = new char[paramString.length() + 1];
    ArrayList<String> arrayList = new ArrayList();
    StringBuffer stringBuffer = new StringBuffer();
    paramString.getChars(0, paramString.length(), arrayOfChar, 0);
    label69: while (true) {
      if (arrayOfChar[b] == '-' && arrayOfChar[b + 1] == '-') {
        for (b += 2; arrayOfChar[b] != '\n'; b++) {
          if (arrayOfChar[b] == '\000')
            return (String[])arrayList.toArray((Object[])new String[0]); 
        } 
        b++;
        continue;
      } 
      if (arrayOfChar[b] == '"') {
        while (arrayOfChar[++b] != '"') {
          if (arrayOfChar[b] == '\000')
            return (String[])arrayList.toArray((Object[])new String[0]); 
          b++;
        } 
        b++;
        continue;
      } 
      if (arrayOfChar[b] == '\'') {
        while (arrayOfChar[++b] != '\'') {
          if (arrayOfChar[b] == '\000')
            return (String[])arrayList.toArray((Object[])new String[0]); 
          b++;
        } 
        b++;
        continue;
      } 
      if (arrayOfChar[b] == '/' && arrayOfChar[b + 1] == '*') {
        for (b += 2;; b += 2) {
          if (arrayOfChar[b] != '*' || arrayOfChar[b + 1] != '/') {
            if (arrayOfChar[b] == '\000')
              return (String[])arrayList.toArray((Object[])new String[0]); 
            b++;
            continue;
          } 
        } 
        break;
      } 
      if (arrayOfChar[b] == '\000')
        return (String[])arrayList.toArray((Object[])new String[0]); 
      if (arrayOfChar[b] == '?') {
        arrayList.add("?");
      } else if (arrayOfChar[b] == ':' && arrayOfChar[b + 1] != '=') {
        b++;
        stringBuffer.delete(0, stringBuffer.length());
        while (true) {
          if (arrayOfChar[b] == '\000')
            return arrayList.<String>toArray(new String[0]); 
          if (Character.isLetterOrDigit(arrayOfChar[b]) || arrayOfChar[b] == '_') {
            stringBuffer.append(arrayOfChar[b]);
          } else {
            if (stringBuffer.length() > 0) {
              arrayList.add(stringBuffer.toString());
              continue label69;
            } 
            continue label69;
          } 
          b++;
        } 
        break;
      } 
      b++;
    } 
  }
  
  public static String getRowIdAddedRefetchSql(String paramString, int paramInt) throws SQLException {
    if (paramString == null)
      throw TbError.newSQLException(-90608, "null"); 
    if (paramInt <= 0)
      return paramString; 
    StringBuffer stringBuffer = new StringBuffer();
    String str = paramString.toUpperCase();
    int i = str.lastIndexOf("FOR");
    int j = str.lastIndexOf("UPDATE");
    if (str.lastIndexOf("\"") < i && i < j) {
      stringBuffer.append(paramString);
      stringBuffer.delete(i, i + 3);
      stringBuffer.delete(j - 3, j + 3);
      stringBuffer.insert(0, "select * from (");
      stringBuffer.append(") ");
    } else {
      stringBuffer.append("select * from (").append(paramString).append(") ");
    } 
    if (paramInt > 0)
      stringBuffer.append(" WHERE _ROWID_CHAR0_ = ? "); 
    for (byte b = 1; b < paramInt; b++)
      stringBuffer.append(" OR _ROWID_CHAR0_ = ? "); 
    return stringBuffer.toString();
  }
  
  private void init() {
    this.index = 0;
    this.openParenCnt = 0;
    this.isString = false;
  }
  
  private void makeToken() {
    if (this.originalSql.charAt(this.index) == '/') {
      this.index++;
      while (this.originalSql.charAt(this.index) != '/')
        this.index++; 
      this.index++;
    } 
    if (this.originalSql.charAt(this.index) == ' ')
      this.index++; 
    while (this.index < this.length && (Character.isJavaIdentifierPart(this.chr = this.originalSql.charAt(this.index)) || this.chr == '?')) {
      this.token.append(this.chr);
      this.index++;
    } 
  }
  
  public String parse(String paramString) throws SQLException {
    init();
    this.parsedSql = new StringBuffer(paramString.length());
    this.token = new StringBuffer(32);
    this.originalSql = paramString;
    this.length = this.originalSql.length();
    processSQL();
    return this.parsedSql.substring(0, this.parsedSql.length());
  }
  
  private void processCall() throws SQLException {
    this.parsedSql.append("CALL");
    processSQL();
  }
  
  private void processDate() throws SQLException {
    this.parsedSql.append("TO_DATE (");
    skipWhitespace();
    processSQL();
    this.parsedSql.append(", 'YYYY-MM-DD')");
  }
  
  private void processEscape() throws SQLException {
    this.parsedSql.append("ESCAPE ");
    skipWhitespace();
    processSQL();
  }
  
  private void processFunction() throws SQLException {
    this.parsedSql.append("?");
    processSQL();
  }
  
  private void processOuterJoin() throws SQLException {
    this.parsedSql.append(" ( ");
    skipWhitespace();
    processSQL();
    this.parsedSql.append(" ) ");
  }
  
  private void processScalarFunction() throws SQLException {
    this.token.delete(0, this.token.length());
    skipWhitespace();
    makeToken();
    String str = this.token.substring(0, this.token.length()).toUpperCase().intern();
    if ("ABS".equalsIgnoreCase(str) || "ACOS".equalsIgnoreCase(str) || "ASIN".equalsIgnoreCase(str) || "ATAN".equalsIgnoreCase(str) || "ATAN2".equalsIgnoreCase(str) || "COS".equalsIgnoreCase(str) || "EXP".equalsIgnoreCase(str) || "FLOOR".equalsIgnoreCase(str) || "MOD".equalsIgnoreCase(str) || "POWER".equalsIgnoreCase(str) || "ROUND".equalsIgnoreCase(str) || "SIGN".equalsIgnoreCase(str) || "SIN".equalsIgnoreCase(str) || "SQRT".equalsIgnoreCase(str) || "TAN".equalsIgnoreCase(str) || "ASCII".equalsIgnoreCase(str) || "CHAR_LENGTH".equalsIgnoreCase(str) || "CHARACTER_LENGTH".equalsIgnoreCase(str) || "CONCAT".equalsIgnoreCase(str) || "LENGTH".equalsIgnoreCase(str) || "LTRIM".equalsIgnoreCase(str) || "REPLACE".equalsIgnoreCase(str) || "RTRIM".equalsIgnoreCase(str) || "EXTRACT".equalsIgnoreCase(str)) {
      appendFunctionName(str);
    } else if ("CEILING".equalsIgnoreCase(str)) {
      appendFunctionName("CEIL");
    } else if ("LOG".equalsIgnoreCase(str)) {
      appendFunctionName("LN");
    } else if ("LOG10".equalsIgnoreCase(str)) {
      appendFunctionPrefix("LOG ( 10, ");
    } else if ("PI".equalsIgnoreCase(str)) {
      appendFunctionPrefix("( 3.141592653589793238462643383279502884197169399375 ");
    } else if ("TRUNCATE".equalsIgnoreCase(str)) {
      appendFunctionName("TRUNC");
    } else if ("CHAR".equalsIgnoreCase(str)) {
      appendFunctionName("CHR");
    } else if ("INSERT".equalsIgnoreCase(str)) {
      ArrayList<StringBuffer> arrayList = processArguments();
      boolean bool = (arrayList == null) ? false : arrayList.size();
      if (bool != 4) {
        String str1 = new String("The number of arguments is wrong. expected=4, actual=" + bool);
        throw TbError.newSQLException(-90632, str1);
      } 
      this.parsedSql.append("SUBSTR(").append(arrayList.get(0)).append(", 1, (").append(arrayList.get(1)).append(")-1) || ").append(arrayList.get(3)).append(" || SUBSTR(").append(arrayList.get(0)).append(", (").append(arrayList.get(1)).append(")+(").append(arrayList.get(2)).append("))");
      processSQL();
    } else if ("LCASE".equalsIgnoreCase(str)) {
      appendFunctionName("LOWER");
    } else if ("LEFT".equalsIgnoreCase(str)) {
      ArrayList<StringBuffer> arrayList = processArguments();
      boolean bool = (arrayList == null) ? false : arrayList.size();
      if (bool != 2) {
        String str1 = new String("The number of arguments is wrong. expected=2, actual=" + bool);
        throw TbError.newSQLException(-90632, str1);
      } 
      this.parsedSql.append("SUBSTR(").append(arrayList.get(0)).append(", 1,").append(arrayList.get(1)).append(')');
      processSQL();
    } else if ("LOCATE".equalsIgnoreCase(str)) {
      ArrayList<StringBuffer> arrayList = processArguments();
      boolean bool = (arrayList == null) ? false : arrayList.size();
      if (bool != 2) {
        String str1 = new String("The number of arguments is wrong. expected=2, actual=" + bool);
        throw TbError.newSQLException(-90632, str1);
      } 
      this.parsedSql.append("INSTR(").append(arrayList.get(1)).append(", ").append(arrayList.get(0)).append(')');
      processSQL();
    } else if ("REPEAT".equalsIgnoreCase(str)) {
      ArrayList<StringBuffer> arrayList = processArguments();
      boolean bool = (arrayList == null) ? false : arrayList.size();
      if (bool != 2) {
        String str1 = new String("The number of arguments is wrong. expected=2, actual=" + bool);
        throw TbError.newSQLException(-90632, str1);
      } 
      this.parsedSql.append("RPAD(").append(arrayList.get(0)).append(", ").append(arrayList.get(1)).append(" * LENGTH(").append(arrayList.get(0)).append("), ").append(arrayList.get(0)).append(')');
      processSQL();
    } else if ("RIGHT".equalsIgnoreCase(str)) {
      ArrayList<StringBuffer> arrayList = processArguments();
      boolean bool = (arrayList == null) ? false : arrayList.size();
      if (bool != 2) {
        String str1 = new String("The number of arguments is wrong. expected=2, actual=" + bool);
        throw TbError.newSQLException(-90632, str1);
      } 
      this.parsedSql.append("SUBSTR(").append(arrayList.get(0)).append(", - ").append(arrayList.get(1)).append(')');
      processSQL();
    } else if ("SPACE".equalsIgnoreCase(str)) {
      appendFunctionPrefix("LPAD(' ', ");
    } else if ("SUBSTRING".equalsIgnoreCase(str)) {
      appendFunctionName("SUBSTR");
    } else if ("UCASE".equalsIgnoreCase(str)) {
      appendFunctionName("UPPER");
    } else if ("CURDATE".equalsIgnoreCase(str) || "CURRENT_DATE".equalsIgnoreCase(str)) {
      appendFunctionPrefix("(CURRENT_DATE");
    } else if ("CURRENT_TIME".equalsIgnoreCase(str)) {
      appendFunctionPrefix("(CURRENT_TIME");
    } else if ("CURTIME".equalsIgnoreCase(str) || "CURRENT_TIMESTAMP".equalsIgnoreCase(str) || "NOW".equalsIgnoreCase(str)) {
      appendFunctionPrefix("(CURRENT_TIMESTAMP");
    } else if ("DAYOFMONTH".equalsIgnoreCase(str)) {
      appendFunctionPrefix("EXTRACT ( DAY FROM ");
    } else if ("HOUR".equalsIgnoreCase(str)) {
      appendFunctionPrefix("EXTRACT ( HOUR FROM ");
    } else if ("MINUTE".equalsIgnoreCase(str)) {
      appendFunctionPrefix("EXTRACT ( MINUTE FROM ");
    } else if ("MONTH".equalsIgnoreCase(str)) {
      appendFunctionPrefix("EXTRACT ( MONTH FROM ");
    } else if ("SECOND".equalsIgnoreCase(str)) {
      appendFunctionPrefix("EXTRACT ( SECOND FROM ");
    } else if ("YEAR".equalsIgnoreCase(str)) {
      appendFunctionPrefix("EXTRACT ( YEAR FROM ");
    } else if ("DATABASE".equalsIgnoreCase(str)) {
      appendFunctionPrefix("USERENV('DB_NAME'");
    } else if ("IFNULL".equalsIgnoreCase(str)) {
      appendFunctionName("NVL");
    } else if ("USER".equalsIgnoreCase(str)) {
      appendFunctionPrefix("(USER");
    } else if ("CONVERT".equalsIgnoreCase(str)) {
      ArrayList<StringBuffer> arrayList = processArguments();
      boolean bool = (arrayList == null) ? false : arrayList.size();
      if (bool != 2) {
        String str3 = new String("The number of arguments is wrong. expected=2, actual=" + bool);
        throw TbError.newSQLException(-90632, str3);
      } 
      String str1 = ((StringBuffer)arrayList.get(1)).toString().trim();
      String str2 = null;
      if ("SQL_VARCHAR".equalsIgnoreCase(str1) || "SQL_CHAR".equalsIgnoreCase(str1)) {
        str2 = "TO_CHAR";
      } else if ("SQL_INTEGER".equalsIgnoreCase(str1) || "SQL_NUMERIC".equalsIgnoreCase(str1) || "SQL_DECIMAL".equalsIgnoreCase(str1)) {
        str2 = "TO_NUMBER";
      } else if ("SQL_DATE".equalsIgnoreCase(str1)) {
        str2 = "TO_DATE";
      } else if ("SQL_TIME".equalsIgnoreCase(str1)) {
        str2 = "TO_TIME";
      } else if ("SQL_TIMESTAMP".equalsIgnoreCase(str1)) {
        str2 = "TO_TIMESTAMP";
      } else {
        String str3 = new String("NOT implemented option: \"" + str1 + "\"");
        throw TbError.newSQLException(-90632, str3);
      } 
      this.parsedSql.append(str2).append('(').append(arrayList.get(0)).append(')');
      processSQL();
    } else {
      String str1 = new String("unsuppoted SQL92 Token: \"" + str + "\"");
      throw TbError.newSQLException(-90632, str1);
    } 
  }
  
  private void processSQL() throws SQLException {
    while (this.index < this.length) {
      this.chr = this.originalSql.charAt(this.index++);
      char c = Character.MIN_VALUE;
      if (this.isString) {
        this.parsedSql.append(this.chr);
        if (this.chr == '\'')
          this.isString = false; 
        continue;
      } 
      switch (this.chr) {
        case '\'':
          this.parsedSql.append(this.chr);
          this.isString = true;
          continue;
        case '{':
          this.openParenCnt++;
          this.token.delete(0, this.token.length());
          skipWhitespace();
          makeToken();
          processToken();
          checkCurrentChar('}');
          continue;
        case '}':
          this.openParenCnt--;
          if (this.openParenCnt < 0) {
            String str = new String(this.index + ": Unnecessary '" + this.chr + "' exists");
            throw TbError.newSQLException(-90632, str);
          } 
          return;
        case '/':
          if (this.index < this.length && (c = this.originalSql.charAt(this.index)) == '*') {
            this.parsedSql.append(this.chr).append(c);
            this.index++;
            while (this.index < this.length && ((this.chr = this.originalSql.charAt(this.index)) != '*' || (c = this.originalSql.charAt(this.index + 1)) != '/')) {
              this.parsedSql.append(this.chr);
              this.index++;
            } 
            this.parsedSql.append(this.chr = this.originalSql.charAt(this.index++));
            this.parsedSql.append(this.chr = this.originalSql.charAt(this.index++));
            continue;
          } 
          this.parsedSql.append(this.chr);
          continue;
        case '-':
          if (this.index < this.length && (c = this.originalSql.charAt(this.index)) == '-') {
            this.parsedSql.append(this.chr).append(c);
            this.index++;
            while (this.index < this.length && (this.chr = this.originalSql.charAt(this.index)) != '\n') {
              this.parsedSql.append(this.chr);
              this.index++;
            } 
            this.parsedSql.append(this.chr);
            this.index++;
            continue;
          } 
          this.parsedSql.append(this.chr);
          continue;
      } 
      this.parsedSql.append(this.chr);
    } 
  }
  
  private ArrayList<StringBuffer> processArguments() throws SQLException {
    skipWhitespace();
    if (this.index < this.length && (this.chr = this.originalSql.charAt(this.index)) == '(') {
      this.index++;
    } else {
      String str = new String(this.index + ": '(' is expected");
      throw TbError.newSQLException(-90632, str);
    } 
    ArrayList<StringBuffer> arrayList = new ArrayList();
    StringBuffer stringBuffer = this.parsedSql;
    this.parsedSql = new StringBuffer();
    byte b = 0;
    while (this.index < this.length) {
      char c2;
      char c1 = this.originalSql.charAt(this.index++);
      if (this.isString) {
        this.parsedSql.append(c1);
        if (c1 == '\'')
          this.isString = false; 
        continue;
      } 
      switch (c1) {
        case '\'':
          this.parsedSql.append(c1);
          this.isString = true;
          continue;
        case '{':
          this.openParenCnt++;
          this.token.delete(0, this.token.length());
          skipWhitespace();
          makeToken();
          processToken();
          checkCurrentChar('}');
          continue;
        case ',':
          if (b) {
            this.parsedSql.append(c1);
            continue;
          } 
          arrayList.add(this.parsedSql);
          this.parsedSql = new StringBuffer();
          continue;
        case '(':
          b++;
          skipWhitespace();
          makeToken();
          processToken();
          checkCurrentChar(')');
          continue;
        case ')':
          if (b == 0) {
            arrayList.add(this.parsedSql);
            break;
          } 
          if (b > 0)
            b--; 
          continue;
        case '/':
          if (this.index < this.length && (c2 = this.originalSql.charAt(this.index)) == '*') {
            this.parsedSql.append(c1).append(c2);
            this.index++;
            while (this.index < this.length && ((c1 = this.originalSql.charAt(this.index)) != '*' || (c2 = this.originalSql.charAt(this.index + 1)) != '/')) {
              this.parsedSql.append(c1);
              this.index++;
            } 
            this.parsedSql.append(c1 = this.originalSql.charAt(this.index++));
            this.parsedSql.append(c1 = this.originalSql.charAt(this.index++));
            continue;
          } 
          this.parsedSql.append(c1);
          continue;
        case '-':
          if (this.index < this.length && (c2 = this.originalSql.charAt(this.index)) == '-') {
            this.parsedSql.append(c1).append(c2);
            this.index++;
            while (this.index < this.length && (c1 = this.originalSql.charAt(this.index)) != '\n') {
              this.parsedSql.append(c1);
              this.index++;
            } 
            this.parsedSql.append(c1);
            this.index++;
            continue;
          } 
          this.parsedSql.append(c1);
          continue;
      } 
      this.parsedSql.append(c1);
    } 
    this.parsedSql = stringBuffer;
    return arrayList;
  }
  
  private void processTime() throws SQLException {
    this.parsedSql.append("TO_DATE (");
    skipWhitespace();
    processSQL();
    this.parsedSql.append(", 'HH24:MI:SS')");
  }
  
  private void processTimestamp() throws SQLException {
    this.parsedSql.append("TO_TIMESTAMP (");
    skipWhitespace();
    processSQL();
    this.parsedSql.append(", 'YYYY-MM-DD HH24:MI:SS.FF')");
  }
  
  private void processToken() throws SQLException {
    String str = this.token.substring(0, this.token.length());
    if (str.equalsIgnoreCase("?")) {
      processFunction();
    } else if (str.equalsIgnoreCase("CALL")) {
      processCall();
    } else if (str.equalsIgnoreCase("DECLARE")) {
      processSQL();
    } else if (str.equalsIgnoreCase("TS")) {
      processTimestamp();
    } else if (str.equalsIgnoreCase("T")) {
      processTime();
    } else if (str.equalsIgnoreCase("D")) {
      processDate();
    } else if (str.equalsIgnoreCase("ESCAPE")) {
      processEscape();
    } else if (str.equalsIgnoreCase("FN")) {
      processScalarFunction();
    } else if (str.equalsIgnoreCase("OJ")) {
      processOuterJoin();
    } else {
      String str1 = new String(this.index + ": \"" + this.token + "\" token is not supported");
      throw TbError.newSQLException(-90632, str1);
    } 
  }
  
  public static String replace(String paramString) {
    byte b = 0;
    char[] arrayOfChar = new char[paramString.length() + 1];
    StringBuffer stringBuffer = new StringBuffer(paramString.length());
    paramString.getChars(0, paramString.length(), arrayOfChar, 0);
    while (true) {
      if (arrayOfChar[b] == '-' && arrayOfChar[b + 1] == '-') {
        stringBuffer.append(arrayOfChar[b]);
        stringBuffer.append(arrayOfChar[b + 1]);
        for (b += 2; arrayOfChar[b] != '\n'; b++) {
          if (arrayOfChar[b] == '\000')
            return stringBuffer.toString(); 
          stringBuffer.append(arrayOfChar[b]);
        } 
        stringBuffer.append(arrayOfChar[b]);
        b++;
        continue;
      } 
      if (arrayOfChar[b] == '"') {
        stringBuffer.append(arrayOfChar[b]);
        while (arrayOfChar[++b] != '"') {
          if (arrayOfChar[b] == '\000')
            return stringBuffer.toString(); 
          stringBuffer.append(arrayOfChar[b]);
          b++;
        } 
        stringBuffer.append(arrayOfChar[b]);
        b++;
        continue;
      } 
      if (arrayOfChar[b] == '\'') {
        stringBuffer.append(arrayOfChar[b]);
        while (arrayOfChar[++b] != '\'') {
          if (arrayOfChar[b] == '\000')
            return stringBuffer.toString(); 
          stringBuffer.append(arrayOfChar[b]);
          b++;
        } 
        stringBuffer.append(arrayOfChar[b]);
        b++;
        continue;
      } 
      if (arrayOfChar[b] == '/' && arrayOfChar[b + 1] == '*') {
        stringBuffer.append(arrayOfChar[b]);
        stringBuffer.append(arrayOfChar[b + 1]);
        for (b += 2;; b += 2) {
          if (arrayOfChar[b] != '*' || arrayOfChar[b + 1] != '/') {
            if (arrayOfChar[b] == '\000')
              return stringBuffer.toString(); 
            stringBuffer.append(arrayOfChar[b]);
            b++;
            continue;
          } 
          stringBuffer.append(arrayOfChar[b]);
          stringBuffer.append(arrayOfChar[b + 1]);
        } 
        break;
      } 
      if (arrayOfChar[b] == '\000')
        return stringBuffer.toString(); 
      if (arrayOfChar[b] == '?' && Character.isDigit(arrayOfChar[b + 1])) {
        byte b1 = b;
        for (b += 2; Character.isDigit(arrayOfChar[b]); b++);
        if (Character.isLetter(arrayOfChar[b])) {
          stringBuffer.append(paramString.substring(b1, b));
          continue;
        } 
        stringBuffer.append(":");
        stringBuffer.append(paramString.substring(b1 + 1, b));
        continue;
      } 
      stringBuffer.append(arrayOfChar[b]);
      b++;
    } 
  }
  
  public static String replaceIDEOGraphicSpace(String paramString) {
    char c;
    try {
      c = (new String(IDEOGRAPHIC_SPACE, "utf8")).charAt(0);
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      return paramString;
    } 
    if (paramString == null || paramString.indexOf(c) < 0)
      return paramString; 
    byte b = 0;
    char[] arrayOfChar = new char[paramString.length() + 1];
    StringBuffer stringBuffer = new StringBuffer(paramString.length());
    paramString.getChars(0, paramString.length(), arrayOfChar, 0);
    while (true) {
      if (arrayOfChar[b] == '-' && arrayOfChar[b + 1] == '-') {
        stringBuffer.append(arrayOfChar[b]);
        stringBuffer.append(arrayOfChar[b + 1]);
        for (b += 2; arrayOfChar[b] != '\n'; b++) {
          if (arrayOfChar[b] == '\000')
            return stringBuffer.toString(); 
          stringBuffer.append(arrayOfChar[b]);
        } 
        stringBuffer.append(arrayOfChar[b]);
        b++;
        continue;
      } 
      if (arrayOfChar[b] == '"') {
        stringBuffer.append(arrayOfChar[b]);
        while (arrayOfChar[++b] != '"') {
          if (arrayOfChar[b] == '\000')
            return stringBuffer.toString(); 
          stringBuffer.append(arrayOfChar[b]);
          b++;
        } 
        stringBuffer.append(arrayOfChar[b]);
        b++;
        continue;
      } 
      if (arrayOfChar[b] == '\'') {
        stringBuffer.append(arrayOfChar[b]);
        while (arrayOfChar[++b] != '\'') {
          if (arrayOfChar[b] == '\000')
            return stringBuffer.toString(); 
          stringBuffer.append(arrayOfChar[b]);
          b++;
        } 
        stringBuffer.append(arrayOfChar[b]);
        b++;
        continue;
      } 
      if (arrayOfChar[b] == '/' && arrayOfChar[b + 1] == '*') {
        stringBuffer.append(arrayOfChar[b]);
        stringBuffer.append(arrayOfChar[b + 1]);
        for (b += 2;; b += 2) {
          if (arrayOfChar[b] != '*' || arrayOfChar[b + 1] != '/') {
            if (arrayOfChar[b] == '\000')
              return stringBuffer.toString(); 
            stringBuffer.append(arrayOfChar[b]);
            b++;
            continue;
          } 
          stringBuffer.append(arrayOfChar[b]);
          stringBuffer.append(arrayOfChar[b + 1]);
        } 
        break;
      } 
      if (arrayOfChar[b] == '\000')
        return stringBuffer.toString(); 
      if (arrayOfChar[b] == c) {
        stringBuffer.append(" ");
        b++;
        continue;
      } 
      stringBuffer.append(arrayOfChar[b]);
      b++;
    } 
  }
  
  private void skipWhitespace() {
    while (this.index < this.length) {
      this.chr = this.originalSql.charAt(this.index);
      if (this.chr != ' ' && this.chr != '\t' && this.chr != '\r' && this.chr != '\n')
        break; 
      this.parsedSql.append(this.chr);
      this.index++;
    } 
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdb\\util\TbSQLParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */