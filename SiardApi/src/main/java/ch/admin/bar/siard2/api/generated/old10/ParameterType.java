package ch.admin.bar.siard2.api.generated.old10;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "parameterType", namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", propOrder = {"name", "mode", "type", "typeOriginal", "description"})
public class ParameterType
{
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", required = true)
  protected String name;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", required = true)
  protected String mode;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", required = true)
  protected String type;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd")
  protected String typeOriginal;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd")
  protected String description;
  
  public String getName() {
    return this.name;
  }








  
  public void setName(String value) {
    this.name = value;
  }








  
  public String getMode() {
    return this.mode;
  }








  
  public void setMode(String value) {
    this.mode = value;
  }








  
  public String getType() {
    return this.type;
  }








  
  public void setType(String value) {
    this.type = value;
  }








  
  public String getTypeOriginal() {
    return this.typeOriginal;
  }








  
  public void setTypeOriginal(String value) {
    this.typeOriginal = value;
  }








  
  public String getDescription() {
    return this.description;
  }








  
  public void setDescription(String value) {
    this.description = value;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\generated\old10\ParameterType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */