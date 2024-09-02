package ch.admin.bar.siard2.api.meta;

import ch.admin.bar.siard2.api.MetaTable;
import ch.admin.bar.siard2.api.MetaUniqueKey;
import ch.admin.bar.siard2.api.generated.UniqueKeyType;
import ch.admin.bar.siard2.api.primary.ArchiveImpl;
import ch.enterag.utils.DU;
import ch.enterag.utils.SU;
import ch.enterag.utils.xml.XU;
import java.io.IOException;











public class MetaUniqueKeyImpl
  extends MetaSearchImpl
  implements MetaUniqueKey
{
  private MetaTable _mtParent;
  private UniqueKeyType _ukt;
  
  public MetaTable getParentMetaTable() {
    return this._mtParent;
  }
  
  public boolean isValid() {
    return (getColumns() > 0);
  }




  
  private ArchiveImpl getArchive() {
    return (ArchiveImpl)getParentMetaTable().getTable().getParentSchema().getParentArchive();
  }







  
  public void setTemplate(UniqueKeyType uktTemplate) {
    if (!SU.isNotEmpty(getDescription())) {
      setDescription(XU.fromXml(uktTemplate.getDescription()));
    }
  }





  
  private MetaUniqueKeyImpl(MetaTable mtParent, UniqueKeyType ukt) {
    this._mtParent = mtParent;
    this._ukt = ukt;
  }







  
  public static MetaUniqueKey newInstance(MetaTable mtParent, UniqueKeyType ukt) {
    return new MetaUniqueKeyImpl(mtParent, ukt);
  }


  
  public String getName() {
    return XU.fromXml(this._ukt.getName());
  }

  
  public int getColumns() {
    return this._ukt.getColumn().size();
  }
  public String getColumn(int iColumn) {
    return XU.fromXml(this._ukt.getColumn().get(iColumn));
  }



  
  public void addColumn(String sColumn) throws IOException {
    if (getArchive().canModifyPrimaryData()) {
      
      this._ukt.getColumn().add(XU.toXml(sColumn));
      getArchive().isMetaDataDifferent(null, sColumn);
    } else {
      
      throw new IOException("Column cannot be set!");
    } 
  }

  
  public String getColumnsString() {
    StringBuilder sbColumns = new StringBuilder();
    for (int iColumn = 0; iColumn < getColumns(); iColumn++) {
      
      if (iColumn > 0)
        sbColumns.append(", "); 
      sbColumns.append(getColumn(iColumn));
    } 
    return sbColumns.toString();
  }





  
  public void setDescription(String sDescription) {
    if (getArchive().isMetaDataDifferent(getDescription(), sDescription))
      this._ukt.setDescription(XU.toXml(sDescription)); 
  }
  
  public String getDescription() {
    return XU.fromXml(this._ukt.getDescription());
  }



  
  public String[] getSearchElements(DU du) throws IOException {
    return new String[] {
        getName(), 
        getColumnsString(), 
        getDescription()
      };
  }








  
  public String toString() {
    String s = null;
    if (this == getParentMetaTable().getMetaPrimaryKey()) {
      s = "primary key";
    } else {
      s = getName();
    }  return s;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\meta\MetaUniqueKeyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */