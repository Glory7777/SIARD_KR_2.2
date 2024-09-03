package ch.admin.bar.siard2.api.meta;

import ch.admin.bar.siard2.api.MetaData;
import ch.admin.bar.siard2.api.MetaPrivilege;
import ch.admin.bar.siard2.api.generated.PrivOptionType;
import ch.admin.bar.siard2.api.generated.PrivilegeType;
import ch.admin.bar.siard2.api.primary.ArchiveImpl;
import ch.enterag.utils.DU;
import ch.enterag.utils.SU;
import ch.enterag.utils.xml.XU;

import java.io.IOException;











public class MetaPrivilegeImpl
  extends MetaSearchImpl
  implements MetaPrivilege
{
  private MetaData _mdParent = null;
  
  public MetaData getParentMetaData() {
    return this._mdParent;
  }
  private PrivilegeType _pt = null;





  
  private ArchiveImpl getArchive() {
    return (ArchiveImpl)getParentMetaData().getArchive();
  }





  
  public void setTemplate(PrivilegeType ptTemplate) {
    if (!SU.isNotEmpty(getDescription())) {
      setDescription(XU.fromXml(ptTemplate.getDescription()));
    }
  }





  
  private MetaPrivilegeImpl(MetaData mdParent, PrivilegeType pt) {
    this._mdParent = mdParent;
    this._pt = pt;
  }







  
  public static MetaPrivilege newInstance(MetaData mdParent, PrivilegeType pt) {
    return new MetaPrivilegeImpl(mdParent, pt);
  }


  
  public String getType() {
    return XU.fromXml(this._pt.getType());
  }

  
  public String getObject() {
    return XU.fromXml(this._pt.getObject());
  }

  
  public String getGrantor() {
    return XU.fromXml(this._pt.getGrantor());
  }

  
  public String getGrantee() {
    return XU.fromXml(this._pt.getGrantee());
  }





  
  public void setOption(String sOption) throws IOException {
    if (getArchive().canModifyPrimaryData()) {
      
      PrivOptionType pot = PrivOptionType.fromValue(sOption.toUpperCase().trim());
      if (pot != null) {
        
        if (getArchive().isMetaDataDifferent(this._pt.getOption(), pot)) {
          this._pt.setOption(pot);
        }
      } else {
        throw new IOException("Invalid privilege option value!");
      } 
    } else {
      throw new IOException("Privilege option value cannot be set!");
    } 
  }

  
  public String getOption() {
    String sOption = null;
    if (this._pt.getOption() != null)
      sOption = this._pt.getOption().value(); 
    return sOption;
  }





  
  public void setDescription(String sDescription) {
    if (getArchive().isMetaDataDifferent(getDescription(), sDescription))
      this._pt.setDescription(XU.toXml(sDescription)); 
  }
  
  public String getDescription() {
    return XU.fromXml(this._pt.getDescription());
  }



  
  public String[] getSearchElements(DU du) throws IOException {
    return new String[] {
        
        getType(), 
        getObject(), 
        getGrantor(), 
        getGrantee(), 
        getOption(), 
        getDescription()
      };
  }








  
  public String toString() {
    StringBuffer sb = new StringBuffer();
    if (getGrantor() != null) {
      sb.append(getGrantor());
    } else {
      sb.append("*");
    }  if (getGrantee() != null) {
      
      sb.append(" ");
      sb.append(getGrantee());
    } else {
      
      sb.append("*");
    }  if (getType() != null) {
      
      sb.append(" ");
      sb.append(getType());
    } 
    if (getObject() != null) {
      
      sb.append(" ");
      sb.append(getObject());
    } 
    return sb.toString();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\meta\MetaPrivilegeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */