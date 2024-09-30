package ch.enterag.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;




















public abstract class SU
{
  public static final String sUTF8_CHARSET_NAME = "UTF-8";
  
  public static boolean isNotEmpty(String s) {
    return (s != null && s.length() > 0);
  }






  
  public static boolean isNotWhite(String s) {
    return (s != null && s.trim().length() > 0);
  }









  
  @Deprecated
  public static String replace(String s, String sFind, String sReplace) {
    StringBuffer sb = new StringBuffer();
    if (isNotEmpty(s)) {
      
      int iStart = 0; int iMatch;
      for (iMatch = s.indexOf(sFind, iStart); iMatch >= 0; iMatch = s.indexOf(sFind, iStart)) {

        
        sb.append(s.substring(iStart, iMatch));
        
        sb.append(sReplace);
        
        iStart = iMatch + sFind.length();
      } 
      
      sb.append(s.substring(iStart, s.length()));
    } 
    return sb.toString();
  }









  
  public static String format(String sPattern, Object[] aoReplace) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < sPattern.length(); i++) {
      
      char c = sPattern.charAt(i);
      sb.append(c);
      if (sPattern.charAt(i) == '\'')
      {
        
        if (i < sPattern.length() - 1) {
          
          c = sPattern.charAt(i + 1);
          if (c != '{' && c != '}')
            sb.append('\''); 
        } 
      }
    } 
    return MessageFormat.format(sb.toString(), aoReplace);
  }







  
  public static String format(String sPattern, String sReplace) {
    return format(sPattern, new Object[] { sReplace });
  }







  
  public static String repeat(int iRepetitions, char c) {
    StringBuffer sb = new StringBuffer(iRepetitions);
    for (int i = 0; i < iRepetitions; i++)
      sb.append(c); 
    return sb.toString();
  }







  
  public static String repeat(int iRepetitions, String s) {
    StringBuffer sb = new StringBuffer(iRepetitions);
    for (int i = 0; i < iRepetitions; i++)
      sb.append(s); 
    return sb.toString();
  }








  
  public static int getBreakPoint(String s, int iStart, int iEnd) {
    if (iEnd > s.length()) {
      iEnd = s.length();
    }
    int iBreak = s.indexOf('\n', iStart) + 1;
    if (iBreak <= iStart || iBreak > iEnd) {
      
      iBreak = iEnd;
      if (iEnd < s.length() && s.charAt(iEnd) != ' ') {
        
        boolean bBlankFound = false;
        for (int i = iEnd - 1; !bBlankFound && i > iStart; i--) {
          
          char c = s.charAt(i + 1);
          if (c == ' ' || c == '-' || c == '\n' || c == '\r' || c == '\t') {
            
            iBreak = i + 1;
            bBlankFound = true;
          } 
        } 
      } 
    } 
    return iBreak;
  }












  
  private static byte[] putEncodedCharArray(char[] ac, int iOffset, int iLength, String sEncoding) {
    byte[] buf = null;
    ByteArrayOutputStream baos = new ByteArrayOutputStream(iLength);

    
    try { OutputStreamWriter osw = new OutputStreamWriter(baos, sEncoding);
      for (int i = iOffset; i < iOffset + iLength; i++)
        osw.write(ac[i]); 
      osw.close();
      buf = baos.toByteArray(); }
    catch (UnsupportedEncodingException uee)
    { System.out.println(uee.getClass().getName() + ":" + uee.getMessage()); }
    catch (IOException ie) { System.out.println(ie.getClass().getName() + ":" + ie.getMessage()); }
     return buf;
  }








  
  public static byte[] putUtf8CharArray(char[] ac, int iOffset, int iLength) {
    return putEncodedCharArray(ac, iOffset, iLength, "UTF-8");
  }







  
  public static byte[] putEncodedString(String s, String sEncoding) {
    byte[] buf = null;
    ByteArrayOutputStream baos = new ByteArrayOutputStream(s.length());

    
    try { OutputStreamWriter osw = new OutputStreamWriter(baos, sEncoding);
      osw.write(s);
      osw.close();
      buf = baos.toByteArray(); }
    catch (UnsupportedEncodingException uee)
    { System.out.println(uee.getClass().getName() + ":" + uee.getMessage()); }
    catch (IOException ie) { System.out.println(ie.getClass().getName() + ":" + ie.getMessage()); }
     return buf;
  }






  
  public static byte[] putUtf8String(String s) {
    return putEncodedString(s, "UTF-8");
  }






  
  public static byte[] putCp437String(String s) {
    return putEncodedString(s, "Cp437");
  }






  
  public static byte[] putIsoLatin1String(String s) {
    return putEncodedString(s, "ISO-8859-1");
  }






  
  public static byte[] putWindows1252String(String s) {
    return putEncodedString(s, "Windows-1252");
  }







  
  public static String getEncodedString(byte[] buf, String sEncoding) {
    StringBuffer sb = new StringBuffer();
    ByteArrayInputStream bais = new ByteArrayInputStream(buf);

    
    try { InputStreamReader isr = new InputStreamReader(bais, sEncoding); int i;
      for (i = isr.read(); i != -1; i = isr.read())
        sb.append((char)i);  }
    catch (UnsupportedEncodingException uee)
    { System.out.println(uee.getClass().getName() + ":" + uee.getMessage()); }
    catch (IOException ie) { System.out.println(ie.getClass().getName() + ":" + ie.getMessage()); }
     return sb.toString();
  }






  
  public static String getUtf8String(byte[] buf) {
    return getEncodedString(buf, "UTF-8");
  }






  
  public static String getCp437String(byte[] buf) {
    return getEncodedString(buf, "Cp437");
  }






  
  public static String getIsoLatin1String(byte[] buf) {
    return getEncodedString(buf, "ISO-8859-1");
  }






  
  public static String getWindows1252String(byte[] buf) {
    return getEncodedString(buf, "Windows-1252");
  }








  
  public static String toCsv(String sText) {
    sText = replace(sText, "\\", "\\\\");
    sText = replace(sText, "\n", "\\n");
    sText = replace(sText, "\r", "\\r");
    sText = replace(sText, "\t", "\\t");
    
    for (int i = 0; i < 32; i++) {
      
      char c = (char)i;
      String s = String.valueOf(c);
      String sEntity = "\\x" + BU.toHex((byte)i);
      sText = replace(sText, s, sEntity);
    } 
    return sText;
  }






  
  public static String toHtml(String sText) {
    sText = replace(sText, "<", "&lt;");
    sText = replace(sText, ">", "&gt;");
    sText = replace(sText, "&", "&amp;");
    sText = replace(sText, "\"", "&quot;");
    return sText;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\utils\SU.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */