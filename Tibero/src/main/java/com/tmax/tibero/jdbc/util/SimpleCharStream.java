package com.tmax.tibero.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class SimpleCharStream {
  public static final boolean staticFlag = false;
  
  int bufsize;
  
  int available;
  
  int tokenBegin;
  
  public int bufpos = -1;
  
  protected int[] bufline;
  
  protected int[] bufcolumn;
  
  protected int column = 0;
  
  protected int line = 1;
  
  protected boolean prevCharIsCR = false;
  
  protected boolean prevCharIsLF = false;
  
  protected Reader inputStream;
  
  protected char[] buffer;
  
  protected int maxNextCharInd = 0;
  
  protected int inBuf = 0;
  
  protected int tabSize = 8;
  
  protected void setTabSize(int paramInt) {
    this.tabSize = paramInt;
  }
  
  protected int getTabSize(int paramInt) {
    return this.tabSize;
  }
  
  protected void ExpandBuff(boolean paramBoolean) {
    char[] arrayOfChar = new char[this.bufsize + 2048];
    int[] arrayOfInt1 = new int[this.bufsize + 2048];
    int[] arrayOfInt2 = new int[this.bufsize + 2048];
    try {
      if (paramBoolean) {
        System.arraycopy(this.buffer, this.tokenBegin, arrayOfChar, 0, this.bufsize - this.tokenBegin);
        System.arraycopy(this.buffer, 0, arrayOfChar, this.bufsize - this.tokenBegin, this.bufpos);
        this.buffer = arrayOfChar;
        System.arraycopy(this.bufline, this.tokenBegin, arrayOfInt1, 0, this.bufsize - this.tokenBegin);
        System.arraycopy(this.bufline, 0, arrayOfInt1, this.bufsize - this.tokenBegin, this.bufpos);
        this.bufline = arrayOfInt1;
        System.arraycopy(this.bufcolumn, this.tokenBegin, arrayOfInt2, 0, this.bufsize - this.tokenBegin);
        System.arraycopy(this.bufcolumn, 0, arrayOfInt2, this.bufsize - this.tokenBegin, this.bufpos);
        this.bufcolumn = arrayOfInt2;
        this.maxNextCharInd = this.bufpos += this.bufsize - this.tokenBegin;
      } else {
        System.arraycopy(this.buffer, this.tokenBegin, arrayOfChar, 0, this.bufsize - this.tokenBegin);
        this.buffer = arrayOfChar;
        System.arraycopy(this.bufline, this.tokenBegin, arrayOfInt1, 0, this.bufsize - this.tokenBegin);
        this.bufline = arrayOfInt1;
        System.arraycopy(this.bufcolumn, this.tokenBegin, arrayOfInt2, 0, this.bufsize - this.tokenBegin);
        this.bufcolumn = arrayOfInt2;
        this.maxNextCharInd = this.bufpos -= this.tokenBegin;
      } 
    } catch (Throwable throwable) {
      throw new Error(throwable.getMessage());
    } 
    this.bufsize += 2048;
    this.available = this.bufsize;
    this.tokenBegin = 0;
  }
  
  protected void FillBuff() throws IOException {
    if (this.maxNextCharInd == this.available)
      if (this.available == this.bufsize) {
        if (this.tokenBegin > 2048) {
          this.bufpos = this.maxNextCharInd = 0;
          this.available = this.tokenBegin;
        } else if (this.tokenBegin < 0) {
          this.bufpos = this.maxNextCharInd = 0;
        } else {
          ExpandBuff(false);
        } 
      } else if (this.available > this.tokenBegin) {
        this.available = this.bufsize;
      } else if (this.tokenBegin - this.available < 2048) {
        ExpandBuff(true);
      } else {
        this.available = this.tokenBegin;
      }  
    try {
      int i;
      if ((i = this.inputStream.read(this.buffer, this.maxNextCharInd, this.available - this.maxNextCharInd)) == -1) {
        this.inputStream.close();
        throw new IOException();
      } 
      this.maxNextCharInd += i;
      return;
    } catch (IOException iOException) {
      this.bufpos--;
      backup(0);
      if (this.tokenBegin == -1)
        this.tokenBegin = this.bufpos; 
      throw iOException;
    } 
  }
  
  public char BeginToken() throws IOException {
    this.tokenBegin = -1;
    char c = readChar();
    this.tokenBegin = this.bufpos;
    return c;
  }
  
  protected void UpdateLineColumn(char paramChar) {
    this.column++;
    if (this.prevCharIsLF) {
      this.prevCharIsLF = false;
      this.line += this.column = 1;
    } else if (this.prevCharIsCR) {
      this.prevCharIsCR = false;
      if (paramChar == '\n') {
        this.prevCharIsLF = true;
      } else {
        this.line += this.column = 1;
      } 
    } 
    switch (paramChar) {
      case '\r':
        this.prevCharIsCR = true;
        break;
      case '\n':
        this.prevCharIsLF = true;
        break;
      case '\t':
        this.column--;
        this.column += this.tabSize - this.column % this.tabSize;
        break;
    } 
    this.bufline[this.bufpos] = this.line;
    this.bufcolumn[this.bufpos] = this.column;
  }
  
  public char readChar() throws IOException {
    if (this.inBuf > 0) {
      this.inBuf--;
      if (++this.bufpos == this.bufsize)
        this.bufpos = 0; 
      return this.buffer[this.bufpos];
    } 
    if (++this.bufpos >= this.maxNextCharInd)
      FillBuff(); 
    char c = this.buffer[this.bufpos];
    UpdateLineColumn(c);
    return c;
  }
  
  public int getColumn() {
    return this.bufcolumn[this.bufpos];
  }
  
  public int getLine() {
    return this.bufline[this.bufpos];
  }
  
  public int getEndColumn() {
    return this.bufcolumn[this.bufpos];
  }
  
  public int getEndLine() {
    return this.bufline[this.bufpos];
  }
  
  public int getBeginColumn() {
    return this.bufcolumn[this.tokenBegin];
  }
  
  public int getBeginLine() {
    return this.bufline[this.tokenBegin];
  }
  
  public void backup(int paramInt) {
    this.inBuf += paramInt;
    if ((this.bufpos -= paramInt) < 0)
      this.bufpos += this.bufsize; 
  }
  
  public SimpleCharStream(Reader paramReader, int paramInt1, int paramInt2, int paramInt3) {
    this.inputStream = paramReader;
    this.line = paramInt1;
    this.column = paramInt2 - 1;
    this.available = this.bufsize = paramInt3;
    this.buffer = new char[paramInt3];
    this.bufline = new int[paramInt3];
    this.bufcolumn = new int[paramInt3];
  }
  
  public SimpleCharStream(Reader paramReader, int paramInt1, int paramInt2) {
    this(paramReader, paramInt1, paramInt2, 4096);
  }
  
  public SimpleCharStream(Reader paramReader) {
    this(paramReader, 1, 1, 4096);
  }
  
  public void ReInit(Reader paramReader, int paramInt1, int paramInt2, int paramInt3) {
    this.inputStream = paramReader;
    this.line = paramInt1;
    this.column = paramInt2 - 1;
    if (this.buffer == null || paramInt3 != this.buffer.length) {
      this.available = this.bufsize = paramInt3;
      this.buffer = new char[paramInt3];
      this.bufline = new int[paramInt3];
      this.bufcolumn = new int[paramInt3];
    } 
    this.prevCharIsLF = this.prevCharIsCR = false;
    this.tokenBegin = this.inBuf = this.maxNextCharInd = 0;
    this.bufpos = -1;
  }
  
  public void ReInit(Reader paramReader, int paramInt1, int paramInt2) {
    ReInit(paramReader, paramInt1, paramInt2, 4096);
  }
  
  public void ReInit(Reader paramReader) {
    ReInit(paramReader, 1, 1, 4096);
  }
  
  public SimpleCharStream(InputStream paramInputStream, String paramString, int paramInt1, int paramInt2, int paramInt3) throws UnsupportedEncodingException {
    this((paramString == null) ? new InputStreamReader(paramInputStream) : new InputStreamReader(paramInputStream, paramString), paramInt1, paramInt2, paramInt3);
  }
  
  public SimpleCharStream(InputStream paramInputStream, int paramInt1, int paramInt2, int paramInt3) {
    this(new InputStreamReader(paramInputStream), paramInt1, paramInt2, paramInt3);
  }
  
  public SimpleCharStream(InputStream paramInputStream, String paramString, int paramInt1, int paramInt2) throws UnsupportedEncodingException {
    this(paramInputStream, paramString, paramInt1, paramInt2, 4096);
  }
  
  public SimpleCharStream(InputStream paramInputStream, int paramInt1, int paramInt2) {
    this(paramInputStream, paramInt1, paramInt2, 4096);
  }
  
  public SimpleCharStream(InputStream paramInputStream, String paramString) throws UnsupportedEncodingException {
    this(paramInputStream, paramString, 1, 1, 4096);
  }
  
  public SimpleCharStream(InputStream paramInputStream) {
    this(paramInputStream, 1, 1, 4096);
  }
  
  public void ReInit(InputStream paramInputStream, String paramString, int paramInt1, int paramInt2, int paramInt3) throws UnsupportedEncodingException {
    ReInit((paramString == null) ? new InputStreamReader(paramInputStream) : new InputStreamReader(paramInputStream, paramString), paramInt1, paramInt2, paramInt3);
  }
  
  public void ReInit(InputStream paramInputStream, int paramInt1, int paramInt2, int paramInt3) {
    ReInit(new InputStreamReader(paramInputStream), paramInt1, paramInt2, paramInt3);
  }
  
  public void ReInit(InputStream paramInputStream, String paramString) throws UnsupportedEncodingException {
    ReInit(paramInputStream, paramString, 1, 1, 4096);
  }
  
  public void ReInit(InputStream paramInputStream) {
    ReInit(paramInputStream, 1, 1, 4096);
  }
  
  public void ReInit(InputStream paramInputStream, String paramString, int paramInt1, int paramInt2) throws UnsupportedEncodingException {
    ReInit(paramInputStream, paramString, paramInt1, paramInt2, 4096);
  }
  
  public void ReInit(InputStream paramInputStream, int paramInt1, int paramInt2) {
    ReInit(paramInputStream, paramInt1, paramInt2, 4096);
  }
  
  public String GetImage() {
    return (this.bufpos >= this.tokenBegin) ? new String(this.buffer, this.tokenBegin, this.bufpos - this.tokenBegin + 1) : (new String(this.buffer, this.tokenBegin, this.bufsize - this.tokenBegin) + new String(this.buffer, 0, this.bufpos + 1));
  }
  
  public char[] GetSuffix(int paramInt) {
    char[] arrayOfChar = new char[paramInt];
    if (this.bufpos + 1 >= paramInt) {
      System.arraycopy(this.buffer, this.bufpos - paramInt + 1, arrayOfChar, 0, paramInt);
    } else {
      System.arraycopy(this.buffer, this.bufsize - paramInt - this.bufpos - 1, arrayOfChar, 0, paramInt - this.bufpos - 1);
      System.arraycopy(this.buffer, 0, arrayOfChar, paramInt - this.bufpos - 1, this.bufpos + 1);
    } 
    return arrayOfChar;
  }
  
  public void Done() {
    this.buffer = null;
    this.bufline = null;
    this.bufcolumn = null;
  }
  
  public void adjustBeginLineColumn(int paramInt1, int paramInt2) {
    int j;
    int i = this.tokenBegin;
    if (this.bufpos >= this.tokenBegin) {
      j = this.bufpos - this.tokenBegin + this.inBuf + 1;
    } else {
      j = this.bufsize - this.tokenBegin + this.bufpos + 1 + this.inBuf;
    } 
    byte b = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 0;
    while (b < j && this.bufline[k = i % this.bufsize] == this.bufline[m = ++i % this.bufsize]) {
      this.bufline[k] = paramInt1;
      n = i1 + this.bufcolumn[m] - this.bufcolumn[k];
      this.bufcolumn[k] = paramInt2 + i1;
      i1 = n;
      b++;
    } 
    if (b < j) {
      this.bufline[k] = paramInt1++;
      this.bufcolumn[k] = paramInt2 + i1;
      while (b++ < j) {
        if (this.bufline[k = i % this.bufsize] != this.bufline[++i % this.bufsize]) {
          this.bufline[k] = paramInt1++;
          continue;
        } 
        this.bufline[k] = paramInt1;
      } 
    } 
    this.line = this.bufline[k];
    this.column = this.bufcolumn[k];
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdb\\util\SimpleCharStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */