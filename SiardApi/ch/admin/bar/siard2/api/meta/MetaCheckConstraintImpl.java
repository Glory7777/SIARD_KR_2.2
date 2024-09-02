package ch.admin.bar.siard2.api.meta;

import ch.admin.bar.siard2.api.MetaCheckConstraint;
import ch.admin.bar.siard2.api.MetaTable;
import ch.admin.bar.siard2.api.generated.CheckConstraintType;
import ch.admin.bar.siard2.api.primary.ArchiveImpl;
import ch.enterag.utils.DU;
import ch.enterag.utils.SU;
import ch.enterag.utils.xml.XU;
import java.io.IOException;











public class MetaCheckConstraintImpl
  extends MetaSearchImpl
  implements MetaCheckConstraint
{
  private MetaTable _mtParent;
  private CheckConstraintType _cct;
  
  public MetaTable getParentMetaTable() {
    return this._mtParent;
  }
  
  public boolean isValid() {
    return (getCondition() != null);
  }




  
  private ArchiveImpl getArchiveImpl() {
    return (ArchiveImpl)getParentMetaTable().getTable().getParentSchema().getParentArchive();
  }







  
  public void setTemplate(CheckConstraintType cctTemplate) {
    if (!SU.isNotEmpty(getDescription())) {
      setDescription(XU.fromXml(cctTemplate.getDescription()));
    }
  }





  
  private MetaCheckConstraintImpl(MetaTable mtParent, CheckConstraintType cct) {
    this._mtParent = mtParent;
    this._cct = cct;
  }







  
  public static MetaCheckConstraint newInstance(MetaTable mtParent, CheckConstraintType cct) {
    return new MetaCheckConstraintImpl(mtParent, cct);
  }


  
  public String getName() {
    return XU.fromXml(this._cct.getName());
  }





  
  public void setCondition(String sCondition) throws IOException {
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      if (getArchiveImpl().isMetaDataDifferent(getCondition(), sCondition)) {
        this._cct.setCondition(XU.toXml(sCondition));
      }
    } else {
      throw new IOException("Condition cannot be set!");
    } 
  }
  public String getCondition() {
    return XU.fromXml(this._cct.getCondition());
  }




  
  public void setDescription(String sDescription) {
    if (getArchiveImpl().isMetaDataDifferent(getDescription(), sDescription))
      this._cct.setDescription(XU.toXml(sDescription)); 
  }
  
  public String getDescription() {
    return XU.fromXml(this._cct.getDescription());
  }



  
  public String[] getSearchElements(DU du) throws IOException {
    return new String[] {
        
        getName(), 
        getCondition(), 
        getDescription()
      };
  }








  
  public String toString() {
    return getName();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\meta\MetaCheckConstraintImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */