package ch.admin.bar.siard2.api.generated.old10;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "viewType", namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", propOrder = {"name", "query", "queryOriginal", "description", "columns"})
public class ViewType
{
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", required = true)
  protected String name;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd")
  protected String query;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd")
  protected String queryOriginal;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd")
  protected String description;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", required = true)
  protected ColumnsType columns;
  
  public String getName() {
    return this.name;
  }








  
  public void setName(String value) {
    this.name = value;
  }








  
  public String getQuery() {
    return this.query;
  }








  
  public void setQuery(String value) {
    this.query = value;
  }








  
  public String getQueryOriginal() {
    return this.queryOriginal;
  }








  
  public void setQueryOriginal(String value) {
    this.queryOriginal = value;
  }








  
  public String getDescription() {
    return this.description;
  }








  
  public void setDescription(String value) {
    this.description = value;
  }








  
  public ColumnsType getColumns() {
    return this.columns;
  }








  
  public void setColumns(ColumnsType value) {
    this.columns = value;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\generated\old10\ViewType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */