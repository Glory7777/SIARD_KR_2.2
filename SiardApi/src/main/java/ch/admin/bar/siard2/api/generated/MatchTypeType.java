package ch.admin.bar.siard2.api.generated;

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


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\generated\MatchTypeType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */