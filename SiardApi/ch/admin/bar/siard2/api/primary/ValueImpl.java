package ch.admin.bar.siard2.api.primary;
import ch.admin.bar.siard2.api.Field;
import ch.admin.bar.siard2.api.MetaField;
import ch.admin.bar.siard2.api.MetaType;
import ch.admin.bar.siard2.api.MetaValue;
import ch.admin.bar.siard2.api.Value;
import ch.admin.bar.siard2.api.generated.CategoryType;
import ch.enterag.utils.BU;
import ch.enterag.utils.DU;
import ch.enterag.utils.FU;
import ch.enterag.utils.database.SqlTypes;
import ch.enterag.utils.xml.XU;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.datatype.Duration;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class ValueImpl implements Value {
  private static final String _sSEQUENCE_PREFIX = "seq";
  private static final DU _du = DU.getInstance("en", "yyyy-MM-dd HH:mm:ss.S"); private static final String _sRECORD_PREFIX = "record"; private static final String _sEXTENSION_TEXT = "txt"; private static final String _sEXTENSION_XML = "xml";
  private static final String _sEXTENSION_BIN = "bin";
  private URI _uriTemporaryLobFolder = null; public URI getTemporaryLobFolder() {
    return this._uriTemporaryLobFolder;
  }
  private long _lRecord = -1L;

  
  protected long getRecord() {
    return this._lRecord;
  }
  private int _iIndex = -1;

  
  protected int getIndex() {
    return this._iIndex;
  }
  Element _elValue = null;





  
  private Element getValueElement() throws IOException {
    if (this._elValue == null)
    {
      if (this instanceof Cell) {
        this._elValue = RecordImpl.getDocument().createElementNS("http://www.bar.admin.ch/xmlns/siard/2/table.xsd", getColumnTag(getIndex()));
      } else {
        
        ValueImpl viParent = (ValueImpl)((Field)this).getParent();
        int iCardinalityParent = viParent.getCardinality();
        if (iCardinalityParent >= 0) {
          this._elValue = RecordImpl.getDocument().createElementNS("http://www.bar.admin.ch/xmlns/siard/2/table.xsd", getElementTag(getIndex()));
        } else {
          
          MetaType mtParent = viParent.getMetaType();
          CategoryType catParent = mtParent.getCategoryType();
          if (catParent == CategoryType.UDT)
            this._elValue = RecordImpl.getDocument().createElementNS("http://www.bar.admin.ch/xmlns/siard/2/table.xsd", getAttributeTag(getIndex())); 
        } 
        viParent.getValueElement().appendChild(this._elValue);
      } 
    }
    return this._elValue;
  }
  
  private MetaValue _mv = null;

  
  public MetaValue getMetaValue() {
    return this._mv;
  }








  
  protected void extendArray(int iSize, int iCardinality) throws IOException {
    for (int iField = 0; iField < iSize; iField++) {
      
      String sTag = getElementTag(iField);
      Field field = getFieldMap().get(sTag);
      if (field == null) {
        
        MetaField mfNull = getMetaField(iField);
        getFieldMap().put(sTag, createField(iField, mfNull, null));
      } 
    } 
  }







  
  protected abstract Field createField(int paramInt, MetaField paramMetaField, Element paramElement) throws IOException;







  
  protected abstract String getInternalLobFolder() throws IOException;







  
  public abstract Cell getAncestorCell();






  
  private int getMetaFields() throws IOException {
    return getMetaValue().getMetaFields();
  }









  
  private MetaField getMetaField(int iField) throws IOException {
    return getMetaValue().getMetaField(iField);
  }







  
  private URI getAbsoluteLobFolder() {
    return getMetaValue().getAbsoluteLobFolder();
  }





  
  private TableImpl getTableImpl() {
    return (TableImpl)getAncestorCell().getParentRecord().getParentTable();
  }





  
  private ArchiveImpl getArchiveImpl() {
    return (ArchiveImpl)getTableImpl().getParentSchema().getParentArchive();
  }








  
  private int getCardinality() throws IOException {
    return getMetaValue().getCardinality();
  }
































  
  private int getPreType() throws IOException {
    return getMetaValue().getPreType();
  }







  
  private MetaType getMetaType() throws IOException {
    return getMetaValue().getMetaType();
  }







  
  private String getMimeType() {
    return getMetaValue().getMimeType();
  }






  
  public static String getColumnTag(int iColumn) {
    return "c" + String.valueOf(iColumn + 1);
  }





  
  public static String getElementTag(int iIndex) {
    return "a" + String.valueOf(iIndex + 1);
  }





  
  public static String getAttributeTag(int iIndex) {
    return "u" + String.valueOf(iIndex + 1);
  }





  
  public static String getFieldTag(int iIndex) {
    return "r" + String.valueOf(iIndex + 1);
  }






  
  public static int getIndex(String sTag) {
    return Integer.parseInt(sTag.substring(1)) - 1;
  }








  
  private String getTag(MetaType mtParent, int iCardinalityParent, int iField) {
    String sTag = null;
    if (iCardinalityParent >= 0) {
      sTag = getElementTag(iField);
    } else {
      
      CategoryType catParent = mtParent.getCategoryType();
      if (catParent == CategoryType.UDT) {
        sTag = getAttributeTag(iField);
      } else {
        sTag = getFieldTag(iField);
      } 
    }  return sTag;
  }







  
  public String getLobFilename() throws IOException {
    int iPreType = getPreType();
    String sExtension = "txt";
    if (iPreType == 2009) {
      sExtension = "xml";
    } else if (iPreType == -2 || iPreType == -3 || iPreType == 2004 || iPreType == 70) {



      
      sExtension = "bin";
      String sMimeType = getMimeType();
      if (sMimeType != null)
        sExtension = MimeTypes.getExtension(sMimeType); 
    } 
    String sFilename = "record" + String.valueOf(getRecord());
    return sFilename + "." + sExtension;
  }

  
  private Map<String, Field> _mapFields = null;

  
  private Map<String, Field> getFieldMap() throws IOException {
    if (this._mapFields == null) {
      
      this._mapFields = new HashMap<>();
      int iCardinality = getCardinality();
      if (iCardinality < 0) {
        
        MetaType mt = getMetaType();
        if (mt != null)
        {
          
          for (int iField = 0; iField < getMetaFields(); iField++) {
            
            MetaField mf = getMetaField(iField);
            String sTag = getTag(mt, iCardinality, iField);
            this._mapFields.put(sTag, createField(iField, mf, null));
          } 
        }
      } 
    } 
    return this._mapFields;
  }







  
  private void setValue(Element elValue) throws IOException {
    if (elValue != null) {
      
      int iField = 0;
      
      for (int iChild = 0; iChild < elValue.getChildNodes().getLength(); iChild++) {
        
        Node node = elValue.getChildNodes().item(iChild);
        if (node.getNodeType() == 1) {
          
          Element elChild = (Element)node;
          MetaField mf = getMetaField(getIndex(elChild.getLocalName()));
          String sFieldTag = elChild.getLocalName();
          Field field = createField(iField, mf, elChild);
          getFieldMap().put(sFieldTag, field);
          iField++;
        } 
      } 
    } 
    this._elValue = elValue;
  }







  
  public Element getValue() throws IOException {
    if (getFieldMap() != null) {
      
      if (getMetaFields() > 0) {
        XU.clearElement(getValueElement());
      }
      int iCardinality = getCardinality();
      MetaType mt = getMetaType();
      for (int iField = 0; iField < getMetaFields(); iField++) {
        
        String sTag = getTag(mt, iCardinality, iField);
        Field field = getFieldMap().get(sTag);
        if (field != null && !field.isNull()) {
          
          Element elField = ((FieldImpl)field).getValue();
          if (elField != null)
            getValueElement().appendChild(elField); 
        } 
      } 
    } 
    return this._elValue;
  }













  
  protected void initialize(long lRecord, URI uriTemporaryLobFolder, int iIndex, Element elValue, MetaValue mv) throws IOException {
    this._lRecord = lRecord;
    this._uriTemporaryLobFolder = uriTemporaryLobFolder;
    this._iIndex = iIndex;
    this._mv = mv;
    setValue(elValue);
    if (elValue != null) {
      
      int iCardinality = mv.getCardinality();
      if (iCardinality > 0) {

        
        Element elChild = null;
        for (Node nodeChild = elValue.getLastChild(); elChild == null && nodeChild != null; nodeChild = nodeChild.getPreviousSibling()) {
          
          if (nodeChild.getNodeType() == 1)
            elChild = (Element)nodeChild; 
        } 
        if (elChild != null) {

          
          int iArrayIndex = getIndex(elChild.getTagName());
          
          extendArray(iArrayIndex + 1, iCardinality);
        } 
      } 
    } 
  }




  
  public boolean isNull() {
    return (this._elValue == null);
  }





  
  public String getString() throws IOException {
    String s = null;
    if (!getValueElement().hasAttribute("file")) {
      s = XU.fromXml(getValueElement());
    } else {
      
      Reader rdrClob = getReader();
      if (rdrClob != null) {
        
        StringWriter swr = new StringWriter();
        char[] cbufTransfer = new char[8192]; int iRead;
        for (iRead = rdrClob.read(cbufTransfer); iRead != -1; iRead = rdrClob.read(cbufTransfer))
          swr.write(cbufTransfer, 0, iRead); 
        rdrClob.close();
        s = swr.getBuffer().toString();
      } 
    } 
    return s;
  }





  
  public void setString(String s) throws IOException {
    boolean bShort = (this._mv.getMaxLength() <= getArchiveImpl().getMaxInlineSize());
    if (bShort) {
      XU.toXml(s, getValueElement());
    } else {
      
      StringReader srdr = new StringReader(s);
      setReader(srdr);
    } 
  }





  
  public byte[] getBytes() throws IOException {
    byte[] buf = null;
    if (!getValueElement().hasAttribute("file")) {
      buf = BU.fromHex(getValueElement().getTextContent());
    } else {
      
      InputStream isBlob = getInputStream();
      if (isBlob != null) {
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bufTransfer = new byte[8192]; int iRead;
        for (iRead = isBlob.read(bufTransfer); iRead != -1; iRead = isBlob.read(bufTransfer))
          baos.write(bufTransfer, 0, iRead); 
        isBlob.close();
        buf = baos.toByteArray();
      } 
    } 
    return buf;
  }





  
  public void setBytes(byte[] buf) throws IOException {
    boolean bShort = (this._mv.getMaxLength() <= getArchiveImpl().getMaxInlineSize());
    if (bShort) {
      getValueElement().appendChild(getValueElement().getOwnerDocument().createTextNode(BU.toHex(buf)));
    } else {
      
      ByteArrayInputStream bais = new ByteArrayInputStream(buf);
      setInputStream(bais);
    } 
  }





  
  public Boolean getBoolean() throws IOException {
    Boolean b = null;
    if (!isNull()) {
      
      int iPreType = getPreType();
      if (iPreType == 16)
      { b = Boolean.valueOf(Boolean.parseBoolean(getValueElement().getTextContent())); }
      else { if (iPreType != 0) {
          throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be converted to boolean!");
        }
        throw new IllegalArgumentException("Value of cell of complex type cannot be converted to boolean!"); }
    
    }  return b;
  }





  
  public void setBoolean(boolean b) throws IOException {
    int iPreType = getPreType();
    if (iPreType == 16)
    { getValueElement().setTextContent(String.valueOf(b)); }
    else { if (iPreType != 0) {
        throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be set to boolean value!");
      }
      throw new IllegalArgumentException("Value of cell of complex type cannot be set to boolean!"); }
  
  }




  
  public Short getShort() throws IOException {
    Short sh = null;
    if (!isNull()) {
      
      int iPreType = getPreType();
      if (iPreType == 5 || iPreType == 4 || iPreType == -5)
      
      { 
        sh = Short.valueOf(Short.parseShort(getValueElement().getTextContent())); }
      else { if (iPreType != 0) {
          throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be converted to short!");
        }
        throw new IllegalArgumentException("Value of cell of complex type cannot be converted to short!"); }
    
    }  return sh;
  }





  
  public void setShort(short sh) throws IOException {
    int iPreType = getPreType();
    if (iPreType == 5 || iPreType == 4 || iPreType == -5)
    
    { 
      getValueElement().setTextContent(String.valueOf(sh)); }
    else { if (iPreType != 0) {
        throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be set to short value!");
      }
      throw new IllegalArgumentException("Value of cell of complex type cannot be set to short!"); }
  
  }




  
  public Integer getInt() throws IOException {
    Integer i = null;
    if (!isNull()) {
      
      int iPreType = getPreType();
      if (iPreType == 5 || iPreType == 4 || iPreType == -5)
      
      { 
        i = Integer.valueOf(Integer.parseInt(getValueElement().getTextContent())); }
      else { if (iPreType != 0) {
          throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be converted to int!");
        }
        throw new IllegalArgumentException("Value of cell of complex type cannot be converted to int!"); }
    
    }  return i;
  }





  
  public void setInt(int i) throws IOException {
    int iPreType = getPreType();
    if (iPreType == 5 || iPreType == 4 || iPreType == -5)
    
    { 
      getValueElement().setTextContent(String.valueOf(i)); }
    else { if (iPreType != 0) {
        throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be set to int value!");
      }
      throw new IllegalArgumentException("Value of cell of complex type cannot be set to int!"); }
  
  }




  
  public Long getLong() throws IOException {
    Long l = null;
    if (!isNull()) {
      
      int iPreType = getPreType();
      if (iPreType == 5 || iPreType == 4 || iPreType == -5)
      
      { 
        l = Long.valueOf(Long.parseLong(getValueElement().getTextContent())); }
      else { if (iPreType != 0) {
          throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be converted to int!");
        }
        throw new IllegalArgumentException("Value of cell of complex type cannot be converted to int!"); }
    
    }  return l;
  }





  
  public void setLong(long l) throws IOException {
    int iPreType = getPreType();
    if (iPreType == 5 || iPreType == 4 || iPreType == -5)
    
    { 
      getValueElement().setTextContent(String.valueOf(l)); }
    else { if (iPreType != 0) {
        throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be set to int value!");
      }
      throw new IllegalArgumentException("Value of cell of complex type cannot be set to int!"); }
  
  }




  
  public BigInteger getBigInteger() throws IOException {
    BigInteger bi = null;
    if (!isNull()) {
      
      int iPreType = getPreType();
      if (iPreType == 5 || iPreType == 4 || iPreType == -5)
      
      { 
        bi = new BigInteger(getValueElement().getTextContent()); }
      else { if (iPreType != 0) {
          throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be converted to BigInteger!");
        }
        throw new IllegalArgumentException("Value of cell of complex type cannot be converted to BigInteger!"); }
    
    }  return bi;
  }





  
  public void setBigInteger(BigInteger bi) throws IOException {
    int iPreType = getPreType();
    if (iPreType == 5 || iPreType == 4 || iPreType == -5)
    
    { 
      getValueElement().setTextContent(bi.toString()); }
    else { if (iPreType != 0) {
        throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be set to BigInteger value!");
      }
      throw new IllegalArgumentException("Value of cell of complex type cannot be set to BigInteger!"); }
  
  }




  
  public BigDecimal getBigDecimal() throws IOException {
    BigDecimal bd = null;
    if (!isNull()) {
      
      int iPreType = getPreType();
      if (iPreType == 3 || iPreType == 2 || iPreType == 5 || iPreType == 4 || iPreType == -5 || iPreType == 6 || iPreType == 7 || iPreType == 8)
      
      { 




        
        bd = new BigDecimal(getValueElement().getTextContent()); }
      else { if (iPreType != 0) {
          throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be converted to BigDecimal!");
        }
        throw new IllegalArgumentException("Value of cell of complex type cannot be converted to BigDecimal!"); }
    
    }  return bd;
  }





  
  public void setBigDecimal(BigDecimal bd) throws IOException {
    int iPreType = getPreType();
    if (iPreType == 3 || iPreType == 2 || iPreType == 5 || iPreType == 4 || iPreType == -5 || iPreType == 6 || iPreType == 7 || iPreType == 8) {








      
      String s = bd.toPlainString();
      
      try {
        long l = bd.longValueExact();
        s = String.valueOf(l);
      }
      catch (ArithmeticException arithmeticException) {}
      getValueElement().setTextContent(s);
    } else {
      if (iPreType != 0) {
        throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be set to BigDecimal value!");
      }
      throw new IllegalArgumentException("Value of cell of complex type cannot be set to BigDecimal!");
    } 
  }




  
  public Float getFloat() throws IOException {
    Float f = null;
    if (!isNull()) {
      
      int iPreType = getPreType();
      if (iPreType == 7 || iPreType == 8 || iPreType == 6)
      
      { 
        f = Float.valueOf(Float.parseFloat(getValueElement().getTextContent())); }
      else { if (iPreType != 0) {
          throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be converted to float!");
        }
        throw new IllegalArgumentException("Value of cell of complex type cannot be converted to float!"); }
    
    }  return f;
  }





  
  public void setFloat(float f) throws IOException {
    int iPreType = getPreType();
    if (iPreType == 7 || iPreType == 8 || iPreType == 6)
    
    { 
      getValueElement().setTextContent(String.valueOf(f)); }
    else { if (iPreType != 0) {
        throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be set to float value!");
      }
      throw new IllegalArgumentException("Value of cell of complex type cannot be set to float!"); }
  
  }




  
  public Double getDouble() throws IOException {
    Double d = null;
    if (!isNull()) {
      
      int iPreType = getPreType();
      if (iPreType == 7 || iPreType == 6 || iPreType == 8)
      
      { 
        d = Double.valueOf(Double.parseDouble(getValueElement().getTextContent())); }
      else { if (iPreType != 0) {
          throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be converted to double!");
        }
        throw new IllegalArgumentException("Value of cell of complex type cannot be converted to double!"); }
    
    }  return d;
  }





  
  public void setDouble(double d) throws IOException {
    int iPreType = getPreType();
    if (iPreType == 7 || iPreType == 8 || iPreType == 6)
    
    { 
      getValueElement().setTextContent(String.valueOf(d)); }
    else { if (iPreType != 0) {
        throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be set to double value!");
      }
      throw new IllegalArgumentException("Value of cell of complex type cannot be set to double!"); }
  
  }




  
  public Date getDate() throws IOException {
    Date date = null;
    if (!isNull()) {
      
      int iPreType = getPreType();
      if (iPreType == 91)
      { 
        try { date = _du.fromXsDate(getValueElement().getTextContent()); }
        catch (ParseException pe) { throw new IllegalArgumentException("Cell value " + getValueElement().getTextContent() + " could not be parsed as xs:date!", pe); }
         }
      else { if (iPreType != 0) {
          throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be converted to date!");
        }
        throw new IllegalArgumentException("Value of cell of complex type cannot be converted to date!"); }
    
    }  return date;
  }





  
  public void setDate(Date date) throws IOException {
    int iPreType = getPreType();
    if (iPreType == 91)
    { getValueElement().setTextContent(_du.toXsDate(date)); }
    else { if (iPreType != 0) {
        throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be set to date value!");
      }
      throw new IllegalArgumentException("Value of cell of complex type cannot be set to date!"); }
  
  }




  
  public Time getTime() throws IOException {
    Time time = null;
    if (!isNull()) {
      
      int iPreType = getPreType();
      if (iPreType == 92)
      { 
        try { time = _du.fromXsTime(getValueElement().getTextContent()); }
        catch (ParseException pe) { throw new IllegalArgumentException("Cell value " + getValueElement().getTextContent() + " could not be parsed as xs:time!", pe); }
         }
      else { if (iPreType != 0) {
          throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be converted to time!");
        }
        throw new IllegalArgumentException("Value of cell of complex type cannot be converted to time!"); }
    
    }  return time;
  }





  
  public void setTime(Time time) throws IOException {
    int iPreType = getPreType();
    if (iPreType == 92)
    { getValueElement().setTextContent(_du.toXsTime(time)); }
    else { if (iPreType != 0) {
        throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be set to time value!");
      }
      throw new IllegalArgumentException("Value of cell of complex type cannot be set to time!"); }
  
  }




  
  public Timestamp getTimestamp() throws IOException {
    Timestamp ts = null;
    if (!isNull()) {
      
      int iPreType = getPreType();
      if (iPreType == 93)
      { 
        try { ts = _du.fromXsDateTime(getValueElement().getTextContent()); }
        catch (ParseException pe) { throw new IllegalArgumentException("Cell value " + getValueElement().getTextContent() + " could not be parsed as xs:dateTime!", pe); }
         }
      else { if (iPreType != 0) {
          throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be converted to timestamp!");
        }
        throw new IllegalArgumentException("Value of cell of complex type cannot be converted to timestamp!"); }
    
    }  return ts;
  }





  
  public void setTimestamp(Timestamp ts) throws IOException {
    int iPreType = getPreType();
    if (iPreType == 93)
    { getValueElement().setTextContent(_du.toXsDateTime(ts)); }
    else { if (iPreType != 0) {
        throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be set to timestamp value!");
      }
      throw new IllegalArgumentException("Value of cell of complex type cannot be set to timestamp!"); }
  
  }




  
  public Duration getDuration() throws IOException {
    Duration duration = null;
    if (!isNull()) {
      
      int iPreType = getPreType();
      if (iPreType == 1111)
      { 
        try { duration = _du.fromXsDuration(getValueElement().getTextContent()); }
        catch (ParseException pe) { throw new IllegalArgumentException("Cell value " + getValueElement().getTextContent() + " could not be parsed as xs:duration!", pe); }
         }
      else { if (iPreType != 0) {
          throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be converted to duration!");
        }
        throw new IllegalArgumentException("Value of cell of complex type cannot be converted to duration!"); }
    
    }  return duration;
  }





  
  public void setDuration(Duration duration) throws IOException {
    int iPreType = getPreType();
    if (iPreType == 1111)
    { getValueElement().setTextContent(_du.toXsDuration(duration)); }
    else { if (iPreType != 0) {
        throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be set to duration value!");
      }
      throw new IllegalArgumentException("Value of cell of complex type cannot be set to duration!"); }
  
  }


  
  public Reader getReader() throws IOException {
    Reader reader = null;
    if (!isNull()) {
      int iPreType = getPreType();
      if (iPreType == 1 || iPreType == 12 || iPreType == 2005 || iPreType == -15 || iPreType == -9 || iPreType == 2011 || iPreType == 2009 || iPreType == 70) {







        
        InputStream inputStream = getInputStreamFromLobFile();
        reader = new ValidatingReader(getValueElement(), inputStream);
      } else {
        if (iPreType != 0) {
          throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be read from input stream!");
        }
        throw new IllegalArgumentException("Value of cell of complex type cannot be read from input stream!");
      } 
    }  return reader;
  }





  
  public long getCharLength() throws IOException {
    long lCharLength = -1L;
    if (!isNull()) {
      
      int iPreType = getPreType();
      if (iPreType == 1 || iPreType == 12 || iPreType == 2005 || iPreType == -15 || iPreType == -9 || iPreType == 2011 || iPreType == 2009 || iPreType == 70) {







        
        String sLength = getValueElement().getAttribute("length");
        if (sLength != null && sLength.length() > 0) {
          lCharLength = Long.parseLong(sLength);
        } else {
          lCharLength = getString().length();
        } 
      } else {
        lCharLength = Long.MIN_VALUE;
      } 
    }  return lCharLength;
  }



  
  public void setReader(Reader reader) throws IOException {
    int iPreType = getPreType();
    if (iPreType == 1 || iPreType == 12 || iPreType == 2005 || iPreType == -15 || iPreType == -9 || iPreType == 2011 || iPreType == 2009 || iPreType == 70)
    
    { 





      
      int iMaxInlineSize = getArchiveImpl().getMaxInlineSize();
      
      char[] bufferPrefix = new char[iMaxInlineSize + 1];
      int iOffset = 0;
      int iRead = -1;
      iRead = reader.read(bufferPrefix);
      for (; iOffset < bufferPrefix.length && iRead != -1; 
        iRead = reader.read(bufferPrefix, iOffset, bufferPrefix.length - iOffset)) {
        iOffset += iRead;
      }
      
      String lobFilename = getLobFilename();
      URI externalLobFolder = getAbsoluteLobFolder();
      
      AbstractMap.SimpleEntry<File, String> lobFileAndLobFilename = getLobFileAndUpdateLobFilename(lobFilename, externalLobFolder);
      File lobFile = lobFileAndLobFilename.getKey();
      lobFilename = lobFileAndLobFilename.getValue();
      
      OutputStream outputStream = new FileOutputStream(lobFile);
      getValueElement().setAttribute("file", lobFilename);
      Writer writer = new ValidatingWriter(getValueElement(), outputStream);
      writer.write(bufferPrefix, 0, iOffset);
      if (iRead != -1) {
        char[] bufferTransfer = new char[8192];
        for (iRead = reader.read(bufferTransfer); iRead != -1; iRead = reader.read(bufferTransfer))
          writer.write(bufferTransfer, 0, iRead); 
      } 
      writer.close();
      reader.close(); }
    else { if (iPreType != 0) {
        throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(iPreType) + " cannot be set using a reader!");
      }
      throw new IllegalArgumentException("Value of cell of complex type cannot be set using a reader!"); }
  
  }





  
  public String getFilename() throws IOException {
    String sFilename = null;
    if (getValueElement().hasAttribute("file"))
      sFilename = getValueElement().getAttribute("file"); 
    return sFilename;
  }



  
  public InputStream getInputStream() throws IOException {
    InputStream inputStream = null;
    if (!isNull())
      if (getPreType() == -2 || 
        getPreType() == -3 || 
        getPreType() == 2004 || 
        getPreType() == 70) {
        
        inputStream = getInputStreamFromLobFile();
        inputStream = new ValidatingInputStream(getValueElement(), inputStream);
      } else {
        if (getPreType() != 0) {
          throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(getPreType()) + " cannot be read from input stream!");
        }
        throw new IllegalArgumentException("Value of cell of complex type cannot be read from input stream!");
      }  
    return inputStream;
  }
  
  private InputStream getInputStreamFromLobFile() throws IOException {
    InputStream inputStream = null;
    
    if (getValueElement().hasAttribute("dlurlpathonly")) {
      String lobFileName = getValueElement().getAttribute("dlurlpathonly");
      URL dlURL = new URL(lobFileName);
      inputStream = dlURL.openStream();
    } else if (getValueElement().hasAttribute("file")) {
      String lobFileName = getValueElement().getAttribute("file");
      URI externalLobFolderUri = getAbsoluteLobFolder();
      if (externalLobFolderUri == null) {
        inputStream = getArchiveImpl().openFileEntry(lobFileName);
      } else {
        URI uriExternal = externalLobFolderUri.resolve(lobFileName);
        inputStream = new FileInputStream(FU.fromUri(uriExternal));
      } 
    } 
    return inputStream;
  }





  
  public long getByteLength() throws IOException {
    long lByteLength = -1L;
    if (!isNull()) {
      
      int iPreType = getPreType();
      if (iPreType == -2 || iPreType == -3 || iPreType == 2004 || iPreType == 70) {



        
        String sLength = getValueElement().getAttribute("length");
        if (sLength != null && sLength.length() > 0) {
          lByteLength = Long.parseLong(sLength);
        } else {
          lByteLength = (getBytes()).length;
        } 
      } else {
        lByteLength = Long.MIN_VALUE;
      } 
    }  return lByteLength;
  }



  
  public void setInputStream(InputStream inputStream) throws IOException {
    if (getPreType() == -2 || 
      getPreType() == -3 || 
      getPreType() == 2004 || 
      getPreType() == 70) {
      
      int iMaxInlineSize = getArchiveImpl().getMaxInlineSize();
      
      byte[] bufferPrefix = new byte[1 + iMaxInlineSize];
      int iOffset = 0;
      int iRead = -1;
      iRead = inputStream.read(bufferPrefix);
      for (; iOffset < bufferPrefix.length && iRead != -1; 
        iRead = inputStream.read(bufferPrefix, iOffset, bufferPrefix.length - iOffset)) {
        iOffset += iRead;
      }
      
      String lobFilename = getLobFilename();
      URI externalLobFolder = getAbsoluteLobFolder();
      
      AbstractMap.SimpleEntry<File, String> lobFileAndLobFilename = getLobFileAndUpdateLobFilename(lobFilename, externalLobFolder);
      File lobFile = lobFileAndLobFilename.getKey();
      lobFilename = lobFileAndLobFilename.getValue();
      
      OutputStream outputStream = Files.newOutputStream(lobFile.toPath(), new java.nio.file.OpenOption[0]);
      getValueElement().setAttribute("file", lobFilename);
      outputStream = new ValidatingOutputStream(getValueElement(), outputStream);
      outputStream.write(bufferPrefix, 0, iOffset);
      
      if (iRead != -1) {
        byte[] bufferTransfer = new byte[8192];
        for (iRead = inputStream.read(bufferTransfer); iRead != -1; iRead = inputStream.read(bufferTransfer)) {
          outputStream.write(bufferTransfer, 0, iRead);
        }
      } 
      outputStream.close();
      inputStream.close();
    } else {
      if (getPreType() != 0) {
        throw new IllegalArgumentException("Cell of type " + SqlTypes.getTypeName(getPreType()) + " cannot be set using an input stream!");
      }
      
      throw new IllegalArgumentException("Value of cell of complex type cannot be set using an input stream!");
    } 
  }
  
  private AbstractMap.SimpleEntry<File, String> getLobFileAndUpdateLobFilename(String lobFilename, URI externalLobFolderUri) throws IOException {
    File lobFile;
    if (externalLobFolderUri == null) {
      lobFilename = getInternalLobFolder() + lobFilename;
      URI uriTemporaryLobFolder = getTemporaryLobFolder();
      lobFile = FU.fromUri(uriTemporaryLobFolder.resolve(lobFilename));
      lobFilename = getTableImpl().getTableFolder() + lobFilename;
    } else {
      int iMaxLobsPerFolder = getArchiveImpl().getMaxLobsPerFolder();
      if (iMaxLobsPerFolder > 0 && getTableImpl().getMetaTable().getRows() > iMaxLobsPerFolder) {
        long lSequence = getRecord() / getArchiveImpl().getMaxLobsPerFolder();
        lobFilename = "seq" + lSequence + File.separator + lobFilename;
      } 
      URI uriExternal = externalLobFolderUri.resolve(lobFilename);
      lobFile = FU.fromUri(uriExternal);
    } 
    if (!lobFile.getParentFile().exists()) {
      lobFile.getParentFile().mkdirs();
    }
    return new AbstractMap.SimpleEntry<>(lobFile, lobFilename);
  }

  
  public void setInputStream(InputStream inputStream, String filePath) throws IOException {
    if (getPreType() == 70) {
      setInputStream(inputStream);
      if (getValueElement().hasAttribute("dlurlpathonly")) {
        getValueElement().setAttribute("dlurlpathonly", filePath);
      }
    } 
  }





  
  public int getElements() throws IOException {
    int iElements = 0;
    int iCardinality = getCardinality();
    if (iCardinality >= 0)
      iElements = getMetaValue().getMetaFields(); 
    return iElements;
  }





  
  public Field getElement(int iElement) throws IOException {
    Field field = null;
    int iCardinality = getCardinality();
    if (iCardinality >= 0) {

      
      extendArray(iElement + 1, iCardinality);
      field = getFieldMap().get(getElementTag(iElement));
    } else {
      
      throw new IllegalArgumentException("Cell or field is not an ARRAY!");
    }  return field;
  }





  
  public int getAttributes() throws IOException {
    int iAttributes = 0;
    MetaType mt = getMetaType();
    CategoryType cat = null;
    if (mt != null)
      cat = mt.getCategoryType(); 
    if (cat == CategoryType.UDT)
      iAttributes = getFieldMap().size(); 
    return iAttributes;
  }





  
  public Field getAttribute(int iAttribute) throws IOException {
    Field field = null;
    MetaType mt = getMetaType();
    CategoryType cat = null;
    if (mt != null)
      cat = mt.getCategoryType(); 
    if (cat == CategoryType.UDT) {
      field = getFieldMap().get(getAttributeTag(iAttribute));
    } else {
      throw new IllegalArgumentException("Cell or field is not a UDT!");
    }  return field;
  }





  
  public Object getObject() throws IOException {
    Object o = null;
    if (!isNull()) {
      
      int iPreType = getPreType();
      if (iPreType != 0) {
        
        switch (iPreType) {
          
          case -15:
          case -9:
          case 1:
          case 12:
            o = getString();
            break;
          case 2005:
          case 2009:
          case 2011:
            o = getReader();
            break;
          case -3:
          case -2:
            o = getBytes();
            break;
          case 70:
          case 2004:
            o = getInputStream();
            break;
          case 2:
          case 3:
            o = getBigDecimal();
            break;
          case 5:
            o = getInt();
            break;
          case 4:
            o = getLong();
            break;
          case -5:
            o = getBigInteger();
            break;
          case 6:
          case 8:
            o = getDouble();
            break;
          case 7:
            o = getFloat();
            break;
          case 16:
            o = getBoolean();
            break;
          case 91:
            o = getDate();
            break;
          case 92:
            o = getTime();
            break;
          case 93:
            o = getTimestamp();
            break;
          case 1111:
            o = getDuration();
            break;
        } 
      
      } else {
        throw new IllegalArgumentException("Cell is a structured type!");
      } 
    }  return o;
  }





  
  public List<Value> getValues(boolean bSupportsArrays, boolean bSupportsUdts) throws IOException {
    List<Value> listValues = new ArrayList<>();
    if (!bSupportsArrays)
    {
      for (int iElement = 0; iElement < getElements(); iElement++)
        listValues.addAll(getElement(iElement).getValues(bSupportsArrays, bSupportsUdts)); 
    }
    if (!bSupportsUdts)
    {
      for (int iAttribute = 0; iAttribute < getAttributes(); iAttribute++)
        listValues.addAll(getAttribute(iAttribute).getValues(bSupportsArrays, bSupportsUdts)); 
    }
    if (listValues.size() == 0)
      listValues.add(this); 
    return listValues;
  }

  
  private void dumpElement(Element el, String sIndent) {
    System.out.print("\r\n" + sIndent + el.getTagName() + ":");
    int iElements = 0;
    for (int i = 0; i < el.getChildNodes().getLength(); i++) {
      
      Node node = el.getChildNodes().item(i);
      if (node.getNodeType() == 1) {
        
        Element elChild = (Element)node;
        dumpElement(elChild, sIndent + "  ");
        iElements++;
      } 
    } 
    if (iElements == 0)
      System.out.println(" " + el.getTextContent()); 
  }
  
  public void dumpDom() {
    if (this._elValue != null) {
      dumpElement(this._elValue, "");
    } else {
      System.out.println("null");
    } 
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\primary\ValueImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */