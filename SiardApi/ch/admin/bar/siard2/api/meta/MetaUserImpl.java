package ch.admin.bar.siard2.api.meta;

import ch.admin.bar.siard2.api.MetaData;
import ch.admin.bar.siard2.api.MetaUser;
import ch.admin.bar.siard2.api.generated.UserType;
import ch.admin.bar.siard2.api.primary.ArchiveImpl;
import ch.enterag.utils.DU;
import ch.enterag.utils.SU;
import ch.enterag.utils.xml.XU;
import java.io.IOException;












public class MetaUserImpl
  extends MetaSearchImpl
  implements MetaUser
{
  private MetaData _mdParent = null;
  
  public MetaData getParentMetaData() {
    return this._mdParent;
  }
  private UserType _ut = null;





  
  private ArchiveImpl getArchive() {
    return (ArchiveImpl)getParentMetaData().getArchive();
  }





  
  public void setTemplate(UserType utTemplate) {
    if (!SU.isNotEmpty(getDescription())) {
      setDescription(XU.fromXml(utTemplate.getDescription()));
    }
  }





  
  private MetaUserImpl(MetaData mdParent, UserType ut) {
    this._mdParent = mdParent;
    this._ut = ut;
  }







  
  public static MetaUser newInstance(MetaData mdParent, UserType ut) {
    return new MetaUserImpl(mdParent, ut);
  }

  
  public String getName() {
    return XU.fromXml(this._ut.getName());
  }




  
  public void setDescription(String sDescription) {
    if (getArchive().isMetaDataDifferent(getDescription(), sDescription))
      this._ut.setDescription(XU.toXml(sDescription)); 
  }
  
  public String getDescription() {
    return XU.fromXml(this._ut.getDescription());
  }



  
  public String[] getSearchElements(DU du) throws IOException {
    return new String[] {
        
        getName(), 
        getDescription()
      };
  }







  
  public String toString() {
    return getName();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\meta\MetaUserImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */