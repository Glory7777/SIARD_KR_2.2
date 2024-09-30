package ch.admin.bar.siard2.api.meta;

import ch.admin.bar.siard2.api.MetaTable;
import ch.admin.bar.siard2.api.MetaTrigger;
import ch.admin.bar.siard2.api.generated.ActionTimeType;
import ch.admin.bar.siard2.api.generated.TriggerType;
import ch.admin.bar.siard2.api.primary.ArchiveImpl;
import ch.enterag.utils.DU;
import ch.enterag.utils.SU;
import ch.enterag.utils.xml.XU;
import java.io.IOException;











public class MetaTriggerImpl
  extends MetaSearchImpl
  implements MetaTrigger
{
  private MetaTable _mtParent = null;
  
  public MetaTable getParentMetaTable() {
    return this._mtParent;
  }

  
  private final TriggerType _tt;

  
  public boolean isValid() {
    boolean bValid = getActionTime() != null;
      if (getTriggerEvent() == null)
      bValid = false; 
    if (getTriggeredAction() == null)
      bValid = false; 
    return bValid;
  }





  
  private ArchiveImpl getArchive() {
    return (ArchiveImpl)getParentMetaTable().getTable().getParentSchema().getParentArchive();
  }





  
  public void setTemplate(TriggerType ttTemplate) {
    if (!SU.isNotEmpty(getDescription())) {
      setDescription(XU.fromXml(ttTemplate.getDescription()));
    }
  }





  
  private MetaTriggerImpl(MetaTable mtParent, TriggerType tt) {
    this._mtParent = mtParent;
    this._tt = tt;
  }







  
  public static MetaTrigger newInstance(MetaTable mtParent, TriggerType tt) {
    return new MetaTriggerImpl(mtParent, tt);
  }


  
  public String getName() {
    return XU.fromXml(this._tt.getName());
  }





  
  public void setActionTime(String sActionTime) throws IOException {
    if (getArchive().canModifyPrimaryData()) {

      
      try {
        ActionTimeType att = ActionTimeType.fromValue(sActionTime.toUpperCase().trim());
        if (getArchive().isMetaDataDifferent(getActionTime(), sActionTime)) {
          this._tt.setActionTime(att);
        }
      } catch (IllegalArgumentException iae) {
        
        throw new IllegalArgumentException("Invalid action time value! (Must be \"BEFORE\", \"INSTEAD OF\" or \"AFTER\".)");
      } 
    } else {
      
      throw new IOException("Action time cannot be set!");
    } 
  }

  
  public String getActionTime() {
    String sActionTime = null;
    ActionTimeType att = this._tt.getActionTime();
    if (att != null)
      sActionTime = XU.fromXml(att.value()); 
    return sActionTime;
  }






  
  public void setTriggerEvent(String sTriggerEvent) throws IOException {
    if (getArchive().canModifyPrimaryData()) {
      
      if (getArchive().isMetaDataDifferent(getTriggerEvent(), sTriggerEvent)) {
        this._tt.setTriggerEvent(XU.toXml(sTriggerEvent));
      }
    } else {
      throw new IOException("Trigger event cannot be set!");
    } 
  }
  public String getTriggerEvent() {
    return XU.fromXml(this._tt.getTriggerEvent());
  }





  
  public void setAliasList(String sAliasList) throws IOException {
    if (getArchive().canModifyPrimaryData()) {
      
      if (getArchive().isMetaDataDifferent(getAliasList(), sAliasList)) {
        this._tt.setAliasList(XU.toXml(sAliasList));
      }
    } else {
      throw new IOException("Alias list cannot be set!");
    } 
  }
  public String getAliasList() {
    return XU.fromXml(this._tt.getAliasList());
  }





  
  public void setTriggeredAction(String sTriggeredAction) throws IOException {
    if (getArchive().canModifyPrimaryData()) {
      
      if (getArchive().isMetaDataDifferent(getTriggeredAction(), sTriggeredAction)) {
        this._tt.setTriggeredAction(XU.toXml(sTriggeredAction));
      }
    } else {
      throw new IOException("Triggered action cannot be set!");
    } 
  }
  public String getTriggeredAction() {
    return XU.fromXml(this._tt.getTriggeredAction());
  }




  
  public void setDescription(String sDescription) {
    if (getArchive().isMetaDataDifferent(getDescription(), sDescription))
      this._tt.setDescription(XU.toXml(sDescription)); 
  }
  
  public String getDescription() {
    return XU.fromXml(this._tt.getDescription());
  }



  
  public String[] getSearchElements(DU du) throws IOException {
    return new String[] {
        
        getName(), 
        getActionTime(), 
        getTriggerEvent(), 
        getAliasList(), 
        getTriggeredAction(), 
        getDescription()
      };
  }







  
  public String toString() {
    return getName();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\meta\MetaTriggerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */