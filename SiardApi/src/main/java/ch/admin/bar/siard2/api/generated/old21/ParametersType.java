package ch.admin.bar.siard2.api.generated.old21;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;























































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "parametersType", namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", propOrder = {"parameter"})
public class ParametersType
{
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", required = true)
  protected List<ParameterType> parameter;
  
  public List<ParameterType> getParameter() {
    if (this.parameter == null) {
      this.parameter = new ArrayList<>();
    }
    return this.parameter;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\generated\old21\ParametersType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */