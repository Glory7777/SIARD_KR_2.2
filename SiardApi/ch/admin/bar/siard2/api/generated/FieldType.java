package ch.admin.bar.siard2.api.generated;

import javax.xml.bind.annotation.*;

















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fieldType", namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", propOrder = {"name", "lobFolder", "fields", "mimeType", "description"})
public class FieldType
{
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", required = true)
  protected String name;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
  @XmlSchemaType(name = "anyURI")
  protected String lobFolder;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
  protected FieldsType fields;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
  protected String mimeType;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
  protected String description;
  
  public String getName() {
    return this.name;
  }








  
  public void setName(String value) {
    this.name = value;
  }








  
  public String getLobFolder() {
    return this.lobFolder;
  }








  
  public void setLobFolder(String value) {
    this.lobFolder = value;
  }








  
  public FieldsType getFields() {
    return this.fields;
  }








  
  public void setFields(FieldsType value) {
    this.fields = value;
  }








  
  public String getMimeType() {
    return this.mimeType;
  }








  
  public void setMimeType(String value) {
    this.mimeType = value;
  }








  
  public String getDescription() {
    return this.description;
  }








  
  public void setDescription(String value) {
    this.description = value;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\generated\FieldType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */