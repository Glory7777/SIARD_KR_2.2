package ch.enterag.utils.jaxb;

import ch.admin.bar.siard2.api.generated.SiardArchive;
import ch.enterag.utils.reflect.Glue;
import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallingContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;









public abstract class Io
{
  static {
    System.setProperty("com.sun.xml.bind.v2.bytecode.ClassTailor.noOptimize", "true");
  }












  
  public static <T> T readJaxbObject(Class<T> classType, InputStream isXml, URL urlXsd) throws JAXBException {
    Glue.setPrivate(UnmarshallingContext.class, "errorsCounter", Integer.valueOf(10));
    T jo = null;
    JAXBContext ctx = ValidatingJAXBContext.newInstance(urlXsd, new Class[] { classType });
    Unmarshaller u = ctx.createUnmarshaller();
    StreamSource ss = new StreamSource(isXml);
    jo = u.<T>unmarshal(ss, classType).getValue();
    return jo;
  }














  
  public static <T> T readJaxbObject(Class<T> classType, File fileXml, URL urlXsd) throws FileNotFoundException, JAXBException, IOException {
    T jo = null;
    FileInputStream fis = new FileInputStream(fileXml);
    jo = readJaxbObject(classType, fis, urlXsd);
    fis.close();
    return jo;
  }














  
  public static void writeJaxbObject(SiardArchive jo, OutputStream os, QName qname, String sNoNamespaceSchemaLocation, String sSchemaLocation, boolean bFormat, URL urlXsd) throws JAXBException {
    JAXBContext ctx;
    Class<?> aClass = SiardArchive.class;
    
    if (urlXsd == null) {
      ctx = JAXBContext.newInstance(new Class[] { aClass });
    } else {
      
      ctx = ValidatingJAXBContext.newInstance(urlXsd, new Class[] { aClass });
    } 
    Marshaller m = ctx.createMarshaller();
    if (sNoNamespaceSchemaLocation != null)
      m.setProperty("jaxb.noNamespaceSchemaLocation", sNoNamespaceSchemaLocation); 
    if (sSchemaLocation != null)
      m.setProperty("jaxb.schemaLocation", sSchemaLocation); 
    m.setProperty("jaxb.formatted.output", Boolean.valueOf(bFormat));
    if (qname != null) {

      
      JAXBElement jbe = new JAXBElement(qname, aClass, jo);
      m.marshal(jbe, os);
    } else {
      
      m.marshal(jo, os);
    } 
  }










  
  public static void writeJaxbObject(SiardArchive jo, OutputStream os, QName qname, String sNoNamespaceSchemaLocation, boolean bFormat) throws JAXBException {
    writeJaxbObject(jo, os, qname, sNoNamespaceSchemaLocation, null, bFormat, null);
  }














  
  public static void writeJaxbObject(SiardArchive jo, OutputStream os, String sSchemaLocation, boolean bFormat, URL urlXsd) throws JAXBException {
    writeJaxbObject(jo, os, null, null, sSchemaLocation, bFormat, urlXsd);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\entera\\utils\jaxb\Io.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */