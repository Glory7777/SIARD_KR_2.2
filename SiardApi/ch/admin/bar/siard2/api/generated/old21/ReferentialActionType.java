package ch.admin.bar.siard2.api.generated.old21;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;




























@XmlType(name = "referentialActionType", namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
@XmlEnum
public enum ReferentialActionType
{
  CASCADE("CASCADE"),
  SET_NULL("SET NULL"),
  
  SET_DEFAULT("SET DEFAULT"),
  
  RESTRICT("RESTRICT"),
  NO_ACTION("NO ACTION");
  
  private final String value;
  
  ReferentialActionType(String v) {
    this.value = v;
  }
  
  public String value() {
    return this.value;
  }
  
  public static ReferentialActionType fromValue(String v) {
    for (ReferentialActionType c : values()) {
      if (c.value.equals(v)) {
        return c;
      }
    } 
    throw new IllegalArgumentException(v);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\generated\old21\ReferentialActionType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */