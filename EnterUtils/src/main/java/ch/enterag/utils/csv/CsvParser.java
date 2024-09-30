package ch.enterag.utils.csv;

import java.io.IOException;

















public interface CsvParser
{
  public static final char DEFAULT_SEPARATOR = ',';
  public static final int INITIAL_READ_SIZE = 1024;
  public static final int READ_BUFFER_SIZE = 128;
  public static final char DEFAULT_QUOTE_CHARACTER = '"';
  public static final char DEFAULT_ESCAPE_CHARACTER = '\\';
  public static final boolean DEFAULT_STRICT_QUOTES = false;
  public static final boolean DEFAULT_IGNORE_LEADING_WHITESPACE = true;
  public static final boolean DEFAULT_IGNORE_QUOTATIONS = false;
  public static final char NULL_CHARACTER = '\000';
  
  public enum CSVReaderNullFieldIndicator
  {
    EMPTY_SEPARATORS,
    EMPTY_QUOTES,
    BOTH,
    NEITHER;
  }





















































  
  public static final CSVReaderNullFieldIndicator DEFAULT_NULL_FIELD_INDICATOR = CSVReaderNullFieldIndicator.NEITHER;
  
  char getSeparator();
  
  char getQuotechar();
  
  boolean isPending();
  
  String[] parseLineMulti(String paramString) throws IOException;
  
  String[] parseLine(String paramString) throws IOException;
  
  CSVReaderNullFieldIndicator nullFieldIndicator();
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\utils\csv\CsvParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */