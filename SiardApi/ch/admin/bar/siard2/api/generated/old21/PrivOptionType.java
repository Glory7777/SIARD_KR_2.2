package ch.admin.bar.siard2.api.generated.old21;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


























@XmlType(name = "privOptionType", namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
@XmlEnum
public enum PrivOptionType
{
  ADMIN,
  GRANT;
  
  public String value() {
    return name();
  }
  
  public static PrivOptionType fromValue(String v) {
    return valueOf(v);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\generated\old21\PrivOptionType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */