package ch.admin.bar.siard2.api.generated.old10;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

























































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "routinesType", namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", propOrder = {"routine"})
public class RoutinesType
{
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", required = true)
  protected List<RoutineType> routine;
  
  public List<RoutineType> getRoutine() {
    if (this.routine == null) {
      this.routine = new ArrayList<>();
    }
    return this.routine;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\generated\old10\RoutinesType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */