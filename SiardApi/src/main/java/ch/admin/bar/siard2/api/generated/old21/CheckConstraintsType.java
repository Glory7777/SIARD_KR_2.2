package ch.admin.bar.siard2.api.generated.old21;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;























































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checkConstraintsType", namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", propOrder = {"checkConstraint"})
public class CheckConstraintsType
{
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", required = true)
  protected List<CheckConstraintType> checkConstraint;
  
  public List<CheckConstraintType> getCheckConstraint() {
    if (this.checkConstraint == null) {
      this.checkConstraint = new ArrayList<>();
    }
    return this.checkConstraint;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\generated\old21\CheckConstraintsType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */