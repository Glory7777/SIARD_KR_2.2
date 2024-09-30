package ch.admin.bar.siard2.api.generated.old10;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

























































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "privilegesType", namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", propOrder = {"privilege"})
public class PrivilegesType
{
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", required = true)
  protected List<PrivilegeType> privilege;
  
  public List<PrivilegeType> getPrivilege() {
    if (this.privilege == null) {
      this.privilege = new ArrayList<>();
    }
    return this.privilege;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\generated\old10\PrivilegesType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */