package ch.admin.bar.siard2.api.generated.old21;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;























































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "schemasType", namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", propOrder = {"schema"})
public class SchemasType
{
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", required = true)
  protected List<SchemaType> schema;
  
  public List<SchemaType> getSchema() {
    if (this.schema == null) {
      this.schema = new ArrayList<>();
    }
    return this.schema;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\generated\old21\SchemasType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */