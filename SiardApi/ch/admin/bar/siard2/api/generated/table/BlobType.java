package ch.admin.bar.siard2.api.generated.table;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigInteger;










































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "blobType", namespace = "http://www.bar.admin.ch/xmlns/siard/2/table.xsd", propOrder = {"value"})
public class BlobType
{
  @XmlValue
  @XmlJavaTypeAdapter(HexBinaryAdapter.class)
  @XmlSchemaType(name = "hexBinary")
  protected byte[] value;
  @XmlAttribute(name = "file")
  @XmlSchemaType(name = "anyURI")
  protected String file;
  @XmlAttribute(name = "length")
  protected BigInteger length;
  @XmlAttribute(name = "digestType")
  protected DigestTypeType digestType;
  @XmlAttribute(name = "digest")
  protected String digest;
  
  public byte[] getValue() {
    return this.value;
  }








  
  public void setValue(byte[] value) {
    this.value = value;
  }








  
  public String getFile() {
    return this.file;
  }








  
  public void setFile(String value) {
    this.file = value;
  }








  
  public BigInteger getLength() {
    return this.length;
  }








  
  public void setLength(BigInteger value) {
    this.length = value;
  }








  
  public DigestTypeType getDigestType() {
    return this.digestType;
  }








  
  public void setDigestType(DigestTypeType value) {
    this.digestType = value;
  }








  
  public String getDigest() {
    return this.digest;
  }








  
  public void setDigest(String value) {
    this.digest = value;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\generated\table\BlobType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */