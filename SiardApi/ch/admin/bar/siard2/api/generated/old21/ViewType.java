package ch.admin.bar.siard2.api.generated.old21;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "viewType", namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", propOrder = {"name", "query", "queryOriginal", "description", "columns", "rows"})
public class ViewType
{
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", required = true)
  protected String name;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
  protected String query;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
  protected String queryOriginal;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
  protected String description;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", required = true)
  protected ColumnsType columns;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
  protected BigInteger rows;
  
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








  
  public BigInteger getRows() {
    return this.rows;
  }








  
  public void setRows(BigInteger value) {
    this.rows = value;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\generated\old21\ViewType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */