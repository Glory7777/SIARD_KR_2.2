package ch.admin.bar.siard2.api.generated.old21;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;























































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "foreignKeysType", namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", propOrder = {"foreignKey"})
public class ForeignKeysType
{
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", required = true)
  protected List<ForeignKeyType> foreignKey;
  
  public List<ForeignKeyType> getForeignKey() {
    if (this.foreignKey == null) {
      this.foreignKey = new ArrayList<>();
    }
    return this.foreignKey;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\generated\old21\ForeignKeysType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */