package ch.admin.bar.siard2.api.meta;

import ch.admin.bar.siard2.api.MetaData;
import ch.admin.bar.siard2.api.MetaRole;
import ch.admin.bar.siard2.api.generated.RoleType;
import ch.admin.bar.siard2.api.primary.ArchiveImpl;
import ch.enterag.utils.DU;
import ch.enterag.utils.SU;
import ch.enterag.utils.xml.XU;

import java.io.IOException;












public class MetaRoleImpl
  extends MetaSearchImpl
  implements MetaRole
{
  private MetaData _mdParent = null;
  
  public MetaData getParentMetaData() {
    return this._mdParent;
  }
  private RoleType _rt = null;





  
  private ArchiveImpl getArchiveImpl() {
    return (ArchiveImpl)getParentMetaData().getArchive();
  }





  
  public void setTemplate(RoleType rtTemplate) {
    if (!SU.isNotEmpty(getDescription())) {
      setDescription(XU.fromXml(rtTemplate.getDescription()));
    }
  }





  
  private MetaRoleImpl(MetaData mdParent, RoleType rt) {
    this._mdParent = mdParent;
    this._rt = rt;
  }







  
  public static MetaRole newInstance(MetaData mdParent, RoleType rt) {
    return new MetaRoleImpl(mdParent, rt);
  }


  
  public String getName() {
    return XU.fromXml(this._rt.getName());
  }





  
  public void setAdmin(String sAdmin) throws IOException {
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      if (getArchiveImpl().isMetaDataDifferent(getAdmin(), sAdmin)) {
        this._rt.setAdmin(XU.toXml(sAdmin));
      }
    } else {
      throw new IOException("Admin name cannot be set!");
    } 
  }
  public String getAdmin() {
    return XU.fromXml(this._rt.getAdmin());
  }




  
  public void setDescription(String sDescription) {
    if (getArchiveImpl().isMetaDataDifferent(getDescription(), sDescription))
      this._rt.setDescription(XU.toXml(sDescription)); 
  }
  
  public String getDescription() {
    return XU.fromXml(this._rt.getDescription());
  }



  
  public String[] getSearchElements(DU du) throws IOException {
    return new String[] {
        
        getName(), 
        getAdmin(), 
        getDescription()
      };
  }







  
  public String toString() {
    return getName();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\meta\MetaRoleImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */