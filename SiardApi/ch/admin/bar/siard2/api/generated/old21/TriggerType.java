package ch.admin.bar.siard2.api.generated.old21;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;



















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "triggerType", namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", propOrder = {"name", "actionTime", "triggerEvent", "aliasList", "triggeredAction", "description"})
public class TriggerType
{
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", required = true)
  protected String name;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", required = true)
  @XmlSchemaType(name = "string")
  protected ActionTimeType actionTime;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", required = true)
  protected String triggerEvent;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
  protected String aliasList;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", required = true)
  protected String triggeredAction;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
  protected String description;
  
  public String getName() {
    return this.name;
  }








  
  public void setName(String value) {
    this.name = value;
  }








  
  public ActionTimeType getActionTime() {
    return this.actionTime;
  }








  
  public void setActionTime(ActionTimeType value) {
    this.actionTime = value;
  }








  
  public String getTriggerEvent() {
    return this.triggerEvent;
  }








  
  public void setTriggerEvent(String value) {
    this.triggerEvent = value;
  }








  
  public String getAliasList() {
    return this.aliasList;
  }








  
  public void setAliasList(String value) {
    this.aliasList = value;
  }








  
  public String getTriggeredAction() {
    return this.triggeredAction;
  }








  
  public void setTriggeredAction(String value) {
    this.triggeredAction = value;
  }








  
  public String getDescription() {
    return this.description;
  }








  
  public void setDescription(String value) {
    this.description = value;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\generated\old21\TriggerType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */