package ch.admin.bar.siard2.api.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;





































































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "columnType", namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", propOrder = {"name", "lobFolder", "type", "mimeType", "typeSchema", "typeName", "typeOriginal", "fields", "nullable", "defaultValue", "cardinality", "description"})
public class ColumnType
{
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", required = true)
  protected String name;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
  @XmlSchemaType(name = "anyURI")
  protected String lobFolder;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
  protected String type;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
  protected String mimeType;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
  protected String typeSchema;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
  protected String typeName;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
  protected String typeOriginal;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
  protected FieldsType fields;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
  protected Boolean nullable;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
  protected String defaultValue;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
  protected BigInteger cardinality;
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








  
  public String getType() {
    return this.type;
  }








  
  public void setType(String value) {
    this.type = value;
  }








  
  public String getMimeType() {
    return this.mimeType;
  }








  
  public void setMimeType(String value) {
    this.mimeType = value;
  }








  
  public String getTypeSchema() {
    return this.typeSchema;
  }








  
  public void setTypeSchema(String value) {
    this.typeSchema = value;
  }








  
  public String getTypeName() {
    return this.typeName;
  }








  
  public void setTypeName(String value) {
    this.typeName = value;
  }








  
  public String getTypeOriginal() {
    return this.typeOriginal;
  }








  
  public void setTypeOriginal(String value) {
    this.typeOriginal = value;
  }








  
  public FieldsType getFields() {
    return this.fields;
  }








  
  public void setFields(FieldsType value) {
    this.fields = value;
  }








  
  public Boolean isNullable() {
    return this.nullable;
  }








  
  public void setNullable(Boolean value) {
    this.nullable = value;
  }








  
  public String getDefaultValue() {
    return this.defaultValue;
  }








  
  public void setDefaultValue(String value) {
    this.defaultValue = value;
  }








  
  public BigInteger getCardinality() {
    return this.cardinality;
  }








  
  public void setCardinality(BigInteger value) {
    this.cardinality = value;
  }








  
  public String getDescription() {
    return this.description;
  }








  
  public void setDescription(String value) {
    this.description = value;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\generated\ColumnType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */