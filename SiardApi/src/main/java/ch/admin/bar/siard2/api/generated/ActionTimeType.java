package ch.admin.bar.siard2.api.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


























@XmlType(name = "actionTimeType", namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
@XmlEnum
public enum ActionTimeType
{
  BEFORE("BEFORE"),
  INSTEAD_OF("INSTEAD OF"),
  
  AFTER("AFTER");
  private final String value;
  
  ActionTimeType(String v) {
    this.value = v;
  }
  
  public String value() {
    return this.value;
  }
  
  public static ActionTimeType fromValue(String v) {
    for (ActionTimeType c : values()) {
      if (c.value.equals(v)) {
        return c;
      }
    } 
    throw new IllegalArgumentException(v);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\generated\ActionTimeType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */