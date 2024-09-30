package ch.admin.bar.siard2.api.generated.old10;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;























































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "routineType", namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", propOrder = {"name", "description", "source", "body", "characteristic", "returnType", "parameters"})
public class RoutineType
{
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", required = true)
  protected String name;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd")
  protected String description;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd")
  protected String source;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd")
  protected String body;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd")
  protected String characteristic;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd")
  protected String returnType;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd")
  protected ParametersType parameters;
  
  public String getName() {
    return this.name;
  }








  
  public void setName(String value) {
    this.name = value;
  }








  
  public String getDescription() {
    return this.description;
  }








  
  public void setDescription(String value) {
    this.description = value;
  }








  
  public String getSource() {
    return this.source;
  }








  
  public void setSource(String value) {
    this.source = value;
  }








  
  public String getBody() {
    return this.body;
  }








  
  public void setBody(String value) {
    this.body = value;
  }








  
  public String getCharacteristic() {
    return this.characteristic;
  }








  
  public void setCharacteristic(String value) {
    this.characteristic = value;
  }








  
  public String getReturnType() {
    return this.returnType;
  }








  
  public void setReturnType(String value) {
    this.returnType = value;
  }








  
  public ParametersType getParameters() {
    return this.parameters;
  }








  
  public void setParameters(ParametersType value) {
    this.parameters = value;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\generated\old10\RoutineType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */