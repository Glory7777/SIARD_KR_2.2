package ch.enterag.utils.csv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;





















public class CsvParserImpl
  implements CsvParser
{
  private final char separator;
  private final char quotechar;
  private final char escape;
  private final boolean strictQuotes;
  private final boolean ignoreLeadingWhiteSpace;
  private final boolean ignoreQuotations;
  private final CsvParser.CSVReaderNullFieldIndicator nullFieldIndicator;
  private String pending;
  private boolean inField = false;
  
  public CsvParserImpl() {
    this(',', '"', '\\');
  }





  
  public CsvParserImpl(char separator) {
    this(separator, '"', '\\');
  }







  
  public CsvParserImpl(char separator, char quotechar) {
    this(separator, quotechar, '\\');
  }







  
  public CsvParserImpl(char separator, char quotechar, char escape) {
    this(separator, quotechar, escape, false);
  }









  
  public CsvParserImpl(char separator, char quotechar, char escape, boolean strictQuotes) {
    this(separator, quotechar, escape, strictQuotes, true);
  }










  
  public CsvParserImpl(char separator, char quotechar, char escape, boolean strictQuotes, boolean ignoreLeadingWhiteSpace) {
    this(separator, quotechar, escape, strictQuotes, ignoreLeadingWhiteSpace, false);
  }












  
  public CsvParserImpl(char separator, char quotechar, char escape, boolean strictQuotes, boolean ignoreLeadingWhiteSpace, boolean ignoreQuotations) {
    this(separator, quotechar, escape, strictQuotes, ignoreLeadingWhiteSpace, ignoreQuotations, DEFAULT_NULL_FIELD_INDICATOR);
  }














  
  CsvParserImpl(char separator, char quotechar, char escape, boolean strictQuotes, boolean ignoreLeadingWhiteSpace, boolean ignoreQuotations, CsvParser.CSVReaderNullFieldIndicator nullFieldIndicator) {
    if (anyCharactersAreTheSame(separator, quotechar, escape)) {
      throw new UnsupportedOperationException("The separator, quote, and escape characters must be different!");
    }
    if (separator == '\000') {
      throw new UnsupportedOperationException("The separator character must be defined!");
    }
    this.separator = separator;
    this.quotechar = quotechar;
    this.escape = escape;
    this.strictQuotes = strictQuotes;
    this.ignoreLeadingWhiteSpace = ignoreLeadingWhiteSpace;
    this.ignoreQuotations = ignoreQuotations;
    this.nullFieldIndicator = nullFieldIndicator;
  }





  
  public char getSeparator() {
    return this.separator;
  }




  
  public char getQuotechar() {
    return this.quotechar;
  }



  
  public char getEscape() {
    return this.escape;
  }



  
  public boolean isStrictQuotes() {
    return this.strictQuotes;
  }



  
  public boolean isIgnoreLeadingWhiteSpace() {
    return this.ignoreLeadingWhiteSpace;
  }



  
  public boolean isIgnoreQuotations() {
    return this.ignoreQuotations;
  }










  
  private boolean anyCharactersAreTheSame(char separator, char quotechar, char escape) {
    return (isSameCharacter(separator, quotechar) || isSameCharacter(separator, escape) || isSameCharacter(quotechar, escape));
  }






  
  private boolean isSameCharacter(char c1, char c2) {
    return (c1 != '\000' && c1 == c2);
  }




  
  public boolean isPending() {
    return (this.pending != null);
  }









  
  public String[] parseLineMulti(String nextLine) throws IOException {
    return parseLine(nextLine, true);
  }









  
  public String[] parseLine(String nextLine) throws IOException {
    return parseLine(nextLine, false);
  }

  
  private boolean isWhitespace(StringBuilder sb) {
    boolean bWhite = true;
    for (int i = 0; bWhite && i < sb.length(); i++)
      bWhite = Character.isWhitespace(sb.charAt(i)); 
    return bWhite;
  }









  
  protected String[] parseLine(String nextLine, boolean multi) throws IOException {
    if (!multi && this.pending != null) {
      this.pending = null;
    }
    
    if (nextLine == null) {
      if (this.pending != null) {
        String s = this.pending;
        this.pending = null;
        return new String[] { s };
      } 
      return null;
    } 
    
    List<String> tokensOnThisLine = new ArrayList<>();
    StringBuilder sb = new StringBuilder(nextLine.length() + 128);
    boolean inQuotes = false;
    boolean fromQuotedField = false;
    if (this.pending != null) {
      sb.append(this.pending);
      this.pending = null;
      inQuotes = !this.ignoreQuotations;
    } 
    for (int i = 0; i < nextLine.length(); i++) {
      
      char c = nextLine.charAt(i);
      if (c == this.escape) {
        if (isNextCharacterEscapable(nextLine, inQuotes(inQuotes), i)) {
          i = appendNextCharacterAndAdvanceLoop(nextLine, sb, i);
        }
      } else if (c == this.quotechar) {
        if (isNextCharacterEscapedQuote(nextLine, inQuotes(inQuotes), i)) {
          i = appendNextCharacterAndAdvanceLoop(nextLine, sb, i);
        } else {
          
          inQuotes = !inQuotes;
          if (atStartOfField(sb)) {
            fromQuotedField = true;
          }

          
          if (!this.strictQuotes && 
            i > 2 && nextLine
            .charAt(i - 1) != this.separator && nextLine
            .length() > i + 1 && nextLine
            .charAt(i + 1) != this.separator)
          {
            
            if (this.ignoreLeadingWhiteSpace && sb.length() > 0 && isWhitespace(sb)) {
              sb.setLength(0);
            } else {
              sb.append(c);
            } 
          }
        } 

        
        this.inField = !this.inField;
      } else if (c == this.separator && (!inQuotes || this.ignoreQuotations)) {
        tokensOnThisLine.add(convertEmptyToNullIfNeeded(sb.toString(), fromQuotedField));
        fromQuotedField = false;
        sb.setLength(0);
        this.inField = false;
      }
      else if (!this.strictQuotes || (inQuotes && !this.ignoreQuotations)) {
        sb.append(c);
        this.inField = true;
        fromQuotedField = true;
      } 
    } 


    
    if (inQuotes && !this.ignoreQuotations) {
      if (multi) {
        
        sb.append('\n');
        this.pending = sb.toString();
        sb = null;
      } else {
        throw new IOException("Un-terminated quoted field at end of CSV line");
      } 
      if (this.inField) {
        fromQuotedField = true;
      }
    } else {
      this.inField = false;
    } 
    
    if (sb != null) {
      tokensOnThisLine.add(convertEmptyToNullIfNeeded(sb.toString(), fromQuotedField));
    }
    return tokensOnThisLine.<String>toArray(new String[tokensOnThisLine.size()]);
  }

  
  private boolean atStartOfField(StringBuilder sb) {
    return (sb.length() == 0);
  }
  
  private String convertEmptyToNullIfNeeded(String s, boolean fromQuotedField) {
    if (s.isEmpty() && shouldConvertEmptyToNull(fromQuotedField)) {
      return null;
    }
    return s;
  }
  
  private boolean shouldConvertEmptyToNull(boolean fromQuotedField) {
    switch (this.nullFieldIndicator) {
      case BOTH:
        return true;
      case EMPTY_SEPARATORS:
        return !fromQuotedField;
      case EMPTY_QUOTES:
        return fromQuotedField;
    } 
    return false;
  }









  
  private int appendNextCharacterAndAdvanceLoop(String line, StringBuilder sb, int i) {
    sb.append(line.charAt(i + 1));
    i++;
    return i;
  }






  
  private boolean inQuotes(boolean inQuotes) {
    return ((inQuotes && !this.ignoreQuotations) || this.inField);
  }










  
  private boolean isNextCharacterEscapedQuote(String nextLine, boolean inQuotes, int i) {
    return (inQuotes && nextLine
      .length() > i + 1 && 
      isCharacterQuoteCharacter(nextLine.charAt(i + 1)));
  }






  
  private boolean isCharacterQuoteCharacter(char c) {
    return (c == this.quotechar);
  }






  
  private boolean isCharacterEscapeCharacter(char c) {
    return (c == this.escape);
  }








  
  private boolean isCharacterEscapable(char c) {
    return (isCharacterQuoteCharacter(c) || isCharacterEscapeCharacter(c));
  }













  
  protected boolean isNextCharacterEscapable(String nextLine, boolean inQuotes, int i) {
    return (inQuotes && nextLine
      .length() > i + 1 && 
      isCharacterEscapable(nextLine.charAt(i + 1)));
  }




  
  public CsvParser.CSVReaderNullFieldIndicator nullFieldIndicator() {
    return this.nullFieldIndicator;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\utils\csv\CsvParserImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */