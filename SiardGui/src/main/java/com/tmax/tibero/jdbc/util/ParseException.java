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

    public ParseException() {
    }

    public ParseException(String paramString) {
        super(paramString);
    }

    public String getMessage() {
        if (!this.specialConstructor)
            return super.getMessage();
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        for (byte b1 = 0; b1 < this.expectedTokenSequences.length; b1++) {
            if (i < (this.expectedTokenSequences[b1]).length)
                i = (this.expectedTokenSequences[b1]).length;
            for (byte b = 0; b < (this.expectedTokenSequences[b1]).length; b++)
                stringBuffer.append(this.tokenImage[this.expectedTokenSequences[b1][b]]).append(" ");
            if (this.expectedTokenSequences[b1][(this.expectedTokenSequences[b1]).length - 1] != 0)
                stringBuffer.append("...");
            stringBuffer.append(this.eol).append("    ");
        }
        String retVal = "Encountered \"";
        Token token = this.currentToken.next;
        for (byte b2 = 0; b2 < i; b2++) {
            if (b2 != 0)
                retVal = retVal + " ";
            if (token.kind == 0) {
                retVal = retVal + this.tokenImage[0];
                break;
            }
            retVal = retVal + add_escapes(token.image);
            token = token.next;
        }
        retVal = retVal + "\" at line " + this.currentToken.next.beginLine + ", column " + this.currentToken.next.beginColumn;
        retVal = retVal + "." + this.eol;
        if (this.expectedTokenSequences.length == 1) {
            retVal = retVal + "Was expecting:" + this.eol + "    ";
        } else {
            retVal = retVal + "Was expecting one of:" + this.eol + "    ";
        }
        return retVal + stringBuffer.toString();
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


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdb\\util\ParseException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */