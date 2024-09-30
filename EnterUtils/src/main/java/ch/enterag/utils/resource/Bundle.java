package ch.enterag.utils.resource;

import ch.enterag.utils.logging.IndentLogger;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
















public class Bundle
{
  private static final String sPROPERTIES_EXTENSION = ".properties";
  private static IndentLogger _il = IndentLogger.getIndentLogger(Bundle.class.getName());
  
  private Class<?> _clsResource = null;









  
  private String _sResource = null;
  private String _sLanguage = Locale.getDefault().getLanguage();
  private Properties _propDefaults = null;
  private Properties _prop = null;






  
  private Properties load(String sResource) {
    _il.enter(new Object[] { sResource });
    Properties prop = new Properties();
    InputStream is = this._clsResource.getResourceAsStream(sResource + ".properties");
    if (is != null) {
      
      InputStreamReader isr = null;

      
      try { isr = new InputStreamReader(is, "UTF-8");
        prop.load(isr);
        isr.close(); }
      catch (UnsupportedEncodingException usee)
      { _il.exception(usee); }
      catch (IOException ie) { _il.exception(ie); }
    
    }  if (this._propDefaults != null)
    {
      for (Iterator<Object> iterKey = this._propDefaults.keySet().iterator(); iterKey.hasNext(); ) {
        
        String sKey = (String)iterKey.next();
        if (prop.getProperty(sKey) == null)
          prop.setProperty(sKey, this._propDefaults.getProperty(sKey)); 
      } 
    }
    _il.exit(prop);
    return prop;
  }




  
  private void load() {
    String sResource = this._sResource + "_" + this._sLanguage;
    this._prop = load(sResource);
  }



  
  public String getLanguage() {
    return this._sLanguage;
  }




  
  public void setLanguage(String sLanguage) {
    _il.enter(new Object[] { sLanguage });
    if (!sLanguage.equals(getLanguage())) {
      
      this._sLanguage = sLanguage;
      load();
    } 
    _il.exit();
  }











  
  protected Bundle(Class<?> clsResource, String sResource) {
    _il.enter(new Object[] { clsResource, sResource });
    this._clsResource = clsResource;
    this._sResource = sResource;
    
    this._propDefaults = load(sResource);
    if (this._propDefaults.size() == 0) {
      throw new IllegalArgumentException("Resource " + sResource + ".properties" + " not found!");
    }
    load();
    _il.exit();
  }











  
  public static Bundle getBundle(Class<?> clsResource, String sResource) {
    return new Bundle(clsResource, sResource);
  }










  
  public String getProperty(String sKey, String sDefaultValue) {
    return this._prop.getProperty(sKey, sDefaultValue).trim();
  }










  
  public String getProperty(String sKey) {
    String sValue = this._prop.getProperty(sKey);
    if (sValue == null)
      throw new IllegalArgumentException("Bundle " + this._sResource + " does not define property " + sKey + "!"); 
    return sValue.trim();
  }










  
  public Set<String> stringPropertyNames() {
    return this._prop.stringPropertyNames();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\utils\resource\Bundle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */