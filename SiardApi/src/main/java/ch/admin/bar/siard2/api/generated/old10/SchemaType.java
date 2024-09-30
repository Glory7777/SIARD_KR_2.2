package ch.admin.bar.siard2.api.generated.old10;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;





















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "schemaType", namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", propOrder = {"name", "folder", "description", "tables", "views", "routines"})
public class SchemaType
{
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", required = true)
  protected String name;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", required = true)
  protected String folder;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd")
  protected String description;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", required = true)
  protected TablesType tables;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd")
  protected ViewsType views;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd")
  protected RoutinesType routines;
  
  public String getName() {
    return this.name;
  }








  
  public void setName(String value) {
    this.name = value;
  }








  
  public String getFolder() {
    return this.folder;
  }








  
  public void setFolder(String value) {
    this.folder = value;
  }








  
  public String getDescription() {
    return this.description;
  }








  
  public void setDescription(String value) {
    this.description = value;
  }








  
  public TablesType getTables() {
    return this.tables;
  }








  
  public void setTables(TablesType value) {
    this.tables = value;
  }








  
  public ViewsType getViews() {
    return this.views;
  }








  
  public void setViews(ViewsType value) {
    this.views = value;
  }








  
  public RoutinesType getRoutines() {
    return this.routines;
  }








  
  public void setRoutines(RoutinesType value) {
    this.routines = value;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\generated\old10\SchemaType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */