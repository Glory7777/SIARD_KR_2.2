package ch.admin.bar.siard2.cmd;
import ch.admin.bar.siard2.api.MetaAttribute;
import ch.admin.bar.siard2.api.MetaType;
import java.util.HashMap;
import java.util.List;

public class TypeMapping extends Mapping {
  private String _sMappedTypeName = null; String getMappedTypeName() {
    return this._sMappedTypeName;
  } private Map<String, String> _mapAttributes = new HashMap<>(); String getMappedAttributeName(String sAttributeName) {
    return this._mapAttributes.get(sAttributeName);
  }
  
  private TypeMapping(String sMappedTypeName, MetaType mt, int iMaxColumnNameLength) {
    this._sMappedTypeName = sMappedTypeName;
    List<String> listAttributes = new ArrayList<>();
    for (int iAttribute = 0; iAttribute < mt.getMetaAttributes(); iAttribute++) {
      
      MetaAttribute ma = mt.getMetaAttribute(iAttribute);
      listAttributes.add(ma.getName());
    } 
    this._mapAttributes = getDisambiguated(listAttributes, iMaxColumnNameLength);
  }

  
  public static TypeMapping newInstance(String sMappedTypeName, MetaType mt, int iMaxColumnNameLength) {
    return new TypeMapping(sMappedTypeName, mt, iMaxColumnNameLength);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardcmd.jar!\ch\admin\bar\siard2\cmd\TypeMapping.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */