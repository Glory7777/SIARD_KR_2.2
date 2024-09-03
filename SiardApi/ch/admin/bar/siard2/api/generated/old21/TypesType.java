package ch.admin.bar.siard2.api.generated.old21;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;























































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typesType", namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", propOrder = {"type"})
public class TypesType
{
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", required = true)
  protected List<TypeType> type;
  
  public List<TypeType> getType() {
    if (this.type == null) {
      this.type = new ArrayList<>();
    }
    return this.type;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\generated\old21\TypesType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */