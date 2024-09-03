package ch.admin.bar.siard2.api.generated.old10;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

























































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "parametersType", namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", propOrder = {"parameter"})
public class ParametersType
{
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", required = true)
  protected List<ParameterType> parameter;
  
  public List<ParameterType> getParameter() {
    if (this.parameter == null) {
      this.parameter = new ArrayList<>();
    }
    return this.parameter;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\generated\old10\ParametersType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */