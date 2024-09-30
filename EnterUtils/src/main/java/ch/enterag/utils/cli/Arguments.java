package ch.enterag.utils.cli;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;






















public class Arguments
{
  private Map<String, String> m_mapOptions = null;

  
  public String getOption(String sName) {
    return this.m_mapOptions.get(sName);
  }
  private String[] m_asArgument = null;

  
  public String getArgument(int iPosition) {
    return this.m_asArgument[iPosition];
  } public int getArguments() {
    return this.m_asArgument.length;
  }
  private String m_sError = null;
  public String getError() {
    return this.m_sError;
  }




  
  private Arguments(String[] args) {
    List<String> listArgument = new ArrayList<>();
    this.m_mapOptions = new HashMap<>();
    int iArgument = 0;
    while (iArgument < args.length) {
      
      String sArgument = args[iArgument];
      if (sArgument.startsWith("-") || (!File.separator.equals("/") && sArgument.startsWith("/"))) {

        
        int iPosition = 1;
        
        while (iPosition < sArgument.length() && 
          Character.isLetterOrDigit(sArgument.charAt(iPosition)))
          iPosition++; 
        if (iPosition > 1) {
          
          String sName = sArgument.substring(1, iPosition);
          String sValue = "";
          if (iPosition < sArgument.length())
          {
            if (sArgument.charAt(iPosition) == ':' || sArgument
              .charAt(iPosition) == '=') {
              sValue = sArgument.substring(iPosition + 1);
            } else {
              this.m_sError = "Option " + sName + " must be terminated by colon, equals or blank!";
            }  } 
          this.m_mapOptions.put(sName, sValue);
        } else {
          
          this.m_sError = "Empty option encountered!";
        } 
      } else {
        listArgument.add(args[iArgument]);
      }  iArgument++;
    } 
    this.m_asArgument = listArgument.<String>toArray(new String[0]);
  }






  
  public static Arguments newInstance(String[] args) {
    return new Arguments(args);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\utils\cli\Arguments.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */