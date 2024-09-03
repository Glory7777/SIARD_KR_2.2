package ch.admin.bar.siard2.cmd;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Mapping
{
  protected static Map<String, String> getDisambiguated(List<String> list, int iMaxLength) {
    Map<String, String> map = new HashMap<>();
    for (Iterator<String> iter = list.iterator(); iter.hasNext(); ) {
      
      String sOriginalName = iter.next();
      String sMappedName = sOriginalName;
      if (iMaxLength > 0) {
        
        if (sOriginalName.length() > iMaxLength)
          sMappedName = sOriginalName.substring(0, iMaxLength - 1) + "_"; 
        for (int iCounter = 0; map.containsValue(sMappedName); iCounter++) {
          
          String sSuffix = "_" + String.valueOf(iCounter);
          sMappedName = sOriginalName.substring(0, iMaxLength - sSuffix.length()) + sSuffix;
        } 
      } 
      map.put(sOriginalName, sMappedName);
    } 
    return map;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardcmd.jar!\ch\admin\bar\siard2\cmd\Mapping.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */