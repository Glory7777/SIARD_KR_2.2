package ch.enterag.utils.jaxb;

import ch.enterag.utils.logging.IndentLogger;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.bind.Validator;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;





public class ValidatingJAXBContext
  extends JAXBContext
{
  private static IndentLogger m_il = IndentLogger.getIndentLogger(ValidatingJAXBContext.class.getName());
  private JAXBContext m_jc = null;
  private URL m_urlSchema = null;



  
  static {
    System.setProperty("com.sun.xml.bind.v2.bytecode.ClassTailor.noOptimize", "true");
  }

  
  private class ContextValidationEventHandler
    implements ValidationEventHandler
  {
    private ContextValidationEventHandler() {}

    
    public boolean handleEvent(ValidationEvent ve) {
      boolean bContinue = false;
      ValidatingJAXBContext.m_il.enter(new Object[] { ve });
      if (ve.getSeverity() == 1 || ve
        .getSeverity() == 2) {
        
        ValidationEventLocator vel = ve.getLocator();
        ValidatingJAXBContext.m_il.severe("XML validation for " + String.valueOf(vel.getURL()) + " failed at line " + 
            String.valueOf(vel.getLineNumber()) + " and column " + 
            String.valueOf(vel.getColumnNumber()) + " with message " + ve
            .getMessage());
      } else {
        
        bContinue = true;
      }  ValidatingJAXBContext.m_il.exit(String.valueOf(bContinue));
      return bContinue;
    }
  }








  
  private ValidatingJAXBContext(JAXBContext jc, URL urlSchema) {
    this.m_jc = jc;
    this.m_urlSchema = urlSchema;
  }








  
  public static ValidatingJAXBContext newInstance(URL urlSchema, Class<?>... classesToBeBound) throws JAXBException {
    JAXBContext jc = JAXBContext.newInstance(classesToBeBound);
    ValidatingJAXBContext vjc = new ValidatingJAXBContext(jc, urlSchema);
    return vjc;
  }








  
  public static ValidatingJAXBContext newInstance(URL urlSchema, String sPackage) throws JAXBException {
    JAXBContext jc = JAXBContext.newInstance(sPackage);
    ValidatingJAXBContext vjc = new ValidatingJAXBContext(jc, urlSchema);
    return vjc;
  }







  
  public Unmarshaller createUnmarshaller() throws JAXBException {
    m_il.enter(new Object[0]);
    Unmarshaller u = this.m_jc.createUnmarshaller();
    if (this.m_urlSchema != null) {
      
      m_il.event("Creating validating Unmarshaller ...");
      SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

      
      try { InputStream is = this.m_urlSchema.openStream();
        Source source = new StreamSource(is);
        Schema schema = sf.newSchema(source);
        is.close();
        u.setSchema(schema); }
      catch (SAXException se)
      { throw new RuntimeException("Unable to create Schema " + String.valueOf(this.m_urlSchema) + "!", se); }
      catch (IOException ie) { throw new RuntimeException("Unable to read Schema " + String.valueOf(this.m_urlSchema) + "!", ie); }
    
    }  u.setEventHandler(new ContextValidationEventHandler());
    m_il.exit(u);
    return u;
  }







  
  public Marshaller createMarshaller() throws JAXBException {
    m_il.enter(new Object[0]);
    Marshaller m = this.m_jc.createMarshaller();
    if (this.m_urlSchema != null) {
      
      m_il.event("Creating validating Unmarshaller ...");
      SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

      
      try { InputStream is = this.m_urlSchema.openStream();
        Source source = new StreamSource(is);
        Schema schema = sf.newSchema(source);
        is.close();
        m.setSchema(schema); }
      catch (SAXException se)
      { throw new RuntimeException("Unable to create Schema " + String.valueOf(this.m_urlSchema) + "!", se); }
      catch (IOException ie) { throw new RuntimeException("Unable to read Schema " + String.valueOf(this.m_urlSchema) + "!", ie); }
    
    }  m.setEventHandler(new ContextValidationEventHandler());
    m.setProperty("jaxb.formatted.output", Boolean.TRUE);
    m_il.exit(m);
    return m;
  }







  
  public Validator createValidator() throws JAXBException {
    throw new RuntimeException("createValidator is deprecated!");
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\entera\\utils\jaxb\ValidatingJAXBContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */