package ch.admin.bar.siard2.api.generated.old10;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

























































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "candidateKeysType", namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", propOrder = {"candidateKey"})
public class CandidateKeysType
{
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", required = true)
  protected List<CandidateKeyType> candidateKey;
  
  public List<CandidateKeyType> getCandidateKey() {
    if (this.candidateKey == null) {
      this.candidateKey = new ArrayList<>();
    }
    return this.candidateKey;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\generated\old10\CandidateKeysType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */