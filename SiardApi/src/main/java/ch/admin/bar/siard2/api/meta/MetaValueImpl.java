package ch.admin.bar.siard2.api.meta;

import ch.admin.bar.siard2.api.MetaField;
import ch.admin.bar.siard2.api.MetaSearch;
import ch.admin.bar.siard2.api.MetaType;
import ch.admin.bar.siard2.api.MetaValue;
import ch.admin.bar.siard2.api.generated.CategoryType;
import ch.enterag.sqlparser.identifier.QualifiedId;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;










public abstract class MetaValueImpl
  extends MetaSearchImpl
  implements MetaValue
{
  protected static final Pattern _patARRAY_INDEX = Pattern.compile("^.*?\\[\\s*(\\d+)\\s*\\]$");
  private int _iPosition = -1;
  
  public int getPosition() {
    return this._iPosition;
  }
  
  public MetaValueImpl(int iPosition) {
    this._iPosition = iPosition;
  }






  
  public List<List<String>> getNames(boolean bSupportsArrays, boolean bSupportsUdts) throws IOException {
    List<String> listNames = new ArrayList<>();
    List<List<String>> llNames = new ArrayList<>();
    CategoryType cat = null;
    MetaType mt = getMetaType();
    if (mt != null)
      cat = mt.getCategoryType(); 
    if ((getCardinality() < 0 && getMetaFields() == 0) || (
      getCardinality() >= 0 && bSupportsArrays) || (cat == CategoryType.UDT && bSupportsUdts)) {

      
      listNames.add(getName());
      llNames.add(listNames);
    }
    else {
      
      for (int iField = 0; iField < getMetaFields(); iField++) {
        
        MetaField mf = getMetaField(iField);
        List<List<String>> llField = mf.getNames(bSupportsArrays, bSupportsUdts);
        
        for (int i = 0; i < llField.size(); i++) {
          
          List<String> listField = llField.get(i);
          listField.add(0, getName());
          llNames.add(listField);
        } 
      } 
    } 
    return llNames;
  }





  
  public String getType(List<String> listNames) throws IOException {
    String sType = null;
    CategoryType cat = null;
    MetaType mt = getMetaType();
    if (mt != null)
      cat = mt.getCategoryType(); 
    if (listNames.size() == 1) {
      
      sType = getType();
      if (sType == null)
      {
        if (cat == CategoryType.DISTINCT) {
          sType = mt.getBase();
        }
        else {
          
          QualifiedId qiType = new QualifiedId(null, mt.getParentMetaSchema().getName(), mt.getName());
          sType = qiType.format();
        } 
      }
      if (getCardinality() >= 0) {
        sType = sType + " ARRAY[" + getCardinality() + "]";
      }
    } else {
      
      MetaField mf = getMetaField(listNames.get(1));
      sType = mf.getType(listNames.subList(1, listNames.size()));
    } 
    return sType;
  }




  
  public long getMaxLength() throws IOException {
    long l = getLength();
    if (l == -1L) {
      
      int iDataType = getPreType();
      if (iDataType == 1 || iDataType == -15 || iDataType == -2) {

        
        l = 1L;
      } else {
        l = Long.MAX_VALUE;
      } 
    }  return l;
  }





  
  protected MetaSearch[] getSubMetaSearches() throws IOException {
    MetaSearch[] ams = new MetaSearch[getMetaFields()];
    for (int iField = 0; iField < getMetaFields(); iField++)
      ams[iField] = getMetaField(iField);
    return ams;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\meta\MetaValueImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */