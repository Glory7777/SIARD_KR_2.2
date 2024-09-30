package ch.enterag.utils.database;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;












public abstract class SqlTypes
{
  public static final String sUNKNOWN = "UNKNOWN";
  private static Map<Integer, String> _mapTypeNames = null;



  
  private static void initialize() {
    _mapTypeNames = new HashMap<>();
    Field[] afield = Types.class.getDeclaredFields();
    for (int iField = 0; iField < afield.length; iField++) {

      
      try {
        Field field = afield[iField];
        Integer iType = Integer.valueOf(field.getInt(null));
        String sTypeName = afield[iField].getName();
        _mapTypeNames.put(iType, sTypeName);
      }
      catch (IllegalAccessException illegalAccessException) {}
    } 
  }






  
  public static String getTypeName(int iType) {
    if (_mapTypeNames == null)
      initialize(); 
    String sType = _mapTypeNames.get(Integer.valueOf(iType));
    if (sType == null)
      sType = "UNKNOWN"; 
    return sType;
  }





  
  public static List<Integer> getAllTypes() {
    if (_mapTypeNames == null)
      initialize(); 
    List<Integer> listTypes = new ArrayList<>(_mapTypeNames.keySet());
    Collections.sort(listTypes);
    return listTypes;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\utils\database\SqlTypes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */