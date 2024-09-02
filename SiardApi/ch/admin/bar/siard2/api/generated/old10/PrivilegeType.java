package ch.admin.bar.siard2.api.generated.old10;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;





















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "privilegeType", namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", propOrder = {"type", "object", "grantor", "grantee", "option", "description"})
public class PrivilegeType
{
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", required = true)
  protected String type;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd")
  protected String object;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", required = true)
  protected String grantor;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", required = true)
  protected String grantee;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd")
  @XmlSchemaType(name = "string")
  protected PrivOptionType option;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd")
  protected String description;
  
  public String getType() {
    return this.type;
  }








  
  public void setType(String value) {
    this.type = value;
  }








  
  public String getObject() {
    return this.object;
  }








  
  public void setObject(String value) {
    this.object = value;
  }








  
  public String getGrantor() {
    return this.grantor;
  }








  
  public void setGrantor(String value) {
    this.grantor = value;
  }








  
  public String getGrantee() {
    return this.grantee;
  }








  
  public void setGrantee(String value) {
    this.grantee = value;
  }








  
  public PrivOptionType getOption() {
    return this.option;
  }








  
  public void setOption(PrivOptionType value) {
    this.option = value;
  }








  
  public String getDescription() {
    return this.description;
  }








  
  public void setDescription(String value) {
    this.description = value;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\generated\old10\PrivilegeType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */