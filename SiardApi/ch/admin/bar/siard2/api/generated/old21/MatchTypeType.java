package ch.admin.bar.siard2.api.generated.old21;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


























@XmlType(name = "matchTypeType", namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
@XmlEnum
public enum MatchTypeType
{
  FULL,
  PARTIAL,
  SIMPLE;
  
  public String value() {
    return name();
  }
  
  public static MatchTypeType fromValue(String v) {
    return valueOf(v);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\generated\old21\MatchTypeType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */