package com.tmax.tibero.jdbc.util;

public class Token {
  public int kind;
  
  public int beginLine;
  
  public int beginColumn;
  
  public int endLine;
  
  public int endColumn;
  
  public String image;
  
  public Token next;
  
  public Token specialToken;
  
  public String toString() {
    return this.image;
  }
  
  public static final Token newToken(int paramInt) {
    switch (paramInt) {
    
    } 
    return new Token();
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdb\\util\Token.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */