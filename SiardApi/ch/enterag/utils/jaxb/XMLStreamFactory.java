package ch.enterag.utils.jaxb;

import ch.enterag.utils.logging.IndentLogger;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLOutputFactory2;
import org.codehaus.stax2.XMLStreamReader2;
import org.codehaus.stax2.validation.XMLValidationSchema;
import org.codehaus.stax2.validation.XMLValidationSchemaFactory;










public abstract class XMLStreamFactory
{
  private static IndentLogger _il = IndentLogger.getIndentLogger(XMLStreamFactory.class.getName());







  
  public static XMLStreamReader createXMLStreamReader(InputStream isXml) throws XMLStreamException {
    _il.enter(new Object[] { isXml });
    
    XMLInputFactory2 xif = (XMLInputFactory2)XMLInputFactory2.newInstance();
    xif.setProperty("javax.xml.stream.isNamespaceAware", Boolean.TRUE);
    xif.setProperty("javax.xml.stream.isReplacingEntityReferences", Boolean.TRUE);
    xif.setProperty("javax.xml.stream.isSupportingExternalEntities", Boolean.FALSE);
    xif.setProperty("com.ctc.wstx.lazyParsing", Boolean.TRUE);
    xif.configureForSpeed();
    
    XMLStreamReader2 xsr = (XMLStreamReader2)xif.createXMLStreamReader(isXml);
    _il.exit(xsr);
    return (XMLStreamReader)xsr;
  }













  
  public static XMLStreamReader createXMLStreamReader(InputStream isXsd, InputStream isXml) throws XMLStreamException {
    _il.enter(new Object[] { isXsd, isXml });
    XMLStreamReader2 xsr = (XMLStreamReader2)createXMLStreamReader(isXml);
    
    try {
      XMLValidationSchemaFactory sf = XMLValidationSchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
      
      XMLValidationSchema xvs = sf.createSchema(isXsd);
      xsr.validateAgainst(xvs);
    } catch (StackOverflowError soe) {
      _il.error(soe);
    }  _il.exit(xsr);
    return (XMLStreamReader)xsr;
  }













  
  public static XMLStreamReader createXMLStreamReader(URL urlXsd, InputStream isXml) throws XMLStreamException {
    _il.enter(new Object[] { urlXsd, isXml });
    XMLStreamReader2 xsr = (XMLStreamReader2)createXMLStreamReader(isXml);
    
    try {
      XMLValidationSchemaFactory sf = XMLValidationSchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
      XMLValidationSchema xvs = sf.createSchema(urlXsd);
      xsr.validateAgainst(xvs);
    } catch (StackOverflowError soe) {
      _il.error(soe);
    }  _il.exit(xsr);
    return (XMLStreamReader)xsr;
  }







  
  public static XMLStreamWriter createStreamWriter(OutputStream osXml) throws XMLStreamException {
    _il.enter(new Object[] { osXml });
    XMLOutputFactory2 xof = (XMLOutputFactory2)XMLOutputFactory2.newInstance();
    xof.setProperty("javax.xml.stream.isNamespaceAware", Boolean.TRUE);
    xof.configureForSpeed();
    XMLStreamWriter xsw = xof.createXMLStreamWriter(osXml, "UTF-8");
    _il.exit(xsw);
    return xsw;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\entera\\utils\jaxb\XMLStreamFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */