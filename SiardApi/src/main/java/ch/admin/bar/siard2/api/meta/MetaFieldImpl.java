package ch.admin.bar.siard2.api.meta;

import ch.admin.bar.siard2.api.Archive;
import ch.admin.bar.siard2.api.MetaAttribute;
import ch.admin.bar.siard2.api.MetaColumn;
import ch.admin.bar.siard2.api.MetaField;
import ch.admin.bar.siard2.api.MetaTable;
import ch.admin.bar.siard2.api.MetaType;
import ch.admin.bar.siard2.api.MetaView;
import ch.admin.bar.siard2.api.generated.CategoryType;
import ch.admin.bar.siard2.api.generated.FieldType;
import ch.admin.bar.siard2.api.generated.FieldsType;
import ch.admin.bar.siard2.api.generated.ObjectFactory;
import ch.admin.bar.siard2.api.primary.ArchiveImpl;
import ch.enterag.utils.DU;
import ch.enterag.utils.SU;
import ch.enterag.utils.xml.XU;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;

public class MetaFieldImpl
  extends MetaValueImpl
  implements MetaField
{
  public static final String _sFIELD_FOLDER_PREFIX = "field";
  private static final ObjectFactory _of = new ObjectFactory();
  private final Map<String, MetaField> _mapMetaFields = new HashMap<>();

  
  private Map<String, MetaField> getMetaFieldsMap() throws IOException {
    if (getArchiveImpl().canModifyPrimaryData())
    {
      if (getCardinality() < 0) {
        
        MetaType mt = getMetaType();
        if (mt != null) {
          
          CategoryType cat = mt.getCategoryType();
          if (cat != CategoryType.DISTINCT)
          {
            for (int i = this._mapMetaFields.size(); i < mt.getMetaAttributes(); i++)
              createMetaField(); 
          }
        } 
      } 
    }
    return this._mapMetaFields;
  }
  
  private String _sFolder = null;





  
  public String getFolder() {
    String sFolder = null;
    if (getLobFolder() == null)
      sFolder = this._sFolder; 
    return sFolder;
  }
  
  private MetaColumn _mcAncestor = null;
  
  public MetaColumn getAncestorMetaColumn() {
    return this._mcAncestor;
  }
  private MetaColumn _mcParent = null;
  
  public MetaColumn getParentMetaColumn() {
    return this._mcParent;
  }
  private MetaField _mfParent = null;
  
  public MetaField getParentMetaField() {
    return this._mfParent;
  }




  
  private MetaTable getMetaTable() {
    MetaField mf = this;
    for (; mf.getParentMetaField() != null; mf = getParentMetaField());
    return mf.getParentMetaColumn().getParentMetaTable();
  }





  
  private MetaView getMetaView() {
    MetaField mf = this;
    for (; mf.getParentMetaField() != null; mf = getParentMetaField());
    return mf.getParentMetaColumn().getParentMetaView();
  }





  
  private ArchiveImpl getArchiveImpl() {
    Archive archive = null;
    if (getMetaTable() != null) {
      archive = getMetaTable().getTable().getParentSchema().getParentArchive();
    } else if (getMetaView() != null) {
      archive = getMetaView().getParentMetaSchema().getSchema().getParentArchive();
    }  return (ArchiveImpl)archive;
  }





  
  public MetaAttribute getMetaAttribute() throws IOException {
    MetaAttribute ma = null;
    MetaType mtParent = null;
    
    MetaColumn mcParent = getParentMetaColumn();
    MetaField mfParent = getParentMetaField();
    if (mcParent != null) {
      mtParent = mcParent.getMetaType();
    } else {
      mtParent = mfParent.getMetaType();
    }  if (mtParent != null)
      ma = mtParent.getMetaAttribute(getPosition() - 1); 
    return ma;
  }
  
  FieldType _ft = null;

  
  FieldType getFieldType() throws IOException {
    for (int iField = 0; iField < getMetaFields(); iField++) {
      
      MetaField mf = getMetaField(iField);
      ((MetaFieldImpl)mf).getFieldType();
    } 
    return this._ft;
  }
  
  private FieldType _ftTemplate = null;





  
  public void setTemplate(FieldType ftTemplate) throws IOException {
    this._ftTemplate = ftTemplate;
    if (!SU.isNotEmpty(getDescription()))
      setDescription(XU.fromXml(this._ftTemplate.getDescription())); 
    FieldsType fts = this._ftTemplate.getFields();
    if (fts != null)
    {
      for (int iField = 0; iField < fts.getField().size(); iField++) {
        
        FieldType ftSubTemplate = fts.getField().get(iField);
        String sName = XU.fromXml(ftSubTemplate.getName());
        MetaField mf = getMetaField(sName);
        if (mf != null) {
          
          MetaFieldImpl mfi = (MetaFieldImpl)mf;
          mfi.setTemplate(ftSubTemplate);
        } 
      } 
    }
  }







  
  private void openMetaFields() throws IOException {
    FieldsType fts = this._ft.getFields();
    if (fts != null)
    {
      for (int iField = 0; iField < fts.getField().size(); iField++) {
        
        FieldType ftSub = fts.getField().get(iField);
        MetaField mfSub = newInstance(this, ftSub, this._sFolder, iField + 1);
        this._mapMetaFields.put(XU.fromXml(ftSub.getName()), mfSub);
      } 
    }
  }











  
  private MetaFieldImpl(MetaColumn mcParent, FieldType ft, String sFolder, int iPosition) throws IOException {
    super(iPosition);
    this._mcParent = mcParent;
    this._mcAncestor = mcParent;
    this._ft = ft;
    if (sFolder != null)
      this._sFolder = sFolder + "field" + (iPosition - 1) + "/";
    openMetaFields();
  }











  
  private MetaFieldImpl(MetaField mfParent, FieldType ft, String sFolder, int iPosition) throws IOException {
    super(iPosition);
    this._mfParent = mfParent;
    this._mcAncestor = mfParent.getAncestorMetaColumn();
    this._ft = ft;
    if (sFolder != null)
      this._sFolder = sFolder + "field" + (iPosition - 1) + "/";
    openMetaFields();
  }











  
  public static MetaField newInstance(MetaColumn mcParent, FieldType ft, String sFolder, int iIndex) throws IOException {
    return new MetaFieldImpl(mcParent, ft, sFolder, iIndex);
  }









  
  public static MetaField newInstance(MetaField mfParent, FieldType ft, String sFolder, int iIndex) throws IOException {
    return new MetaFieldImpl(mfParent, ft, sFolder, iIndex);
  }


  
  public String getName() {
    return XU.fromXml(this._ft.getName());
  }




  
  public void setLobFolder(URI uriLobFolder) throws IOException {
    boolean bMayBeSet = false;
    if (getLobFolder() == null) {
      
      if (getMetaTable() != null && getMetaTable().getTable().isEmpty()) {
        bMayBeSet = true;
      }
    } else {
      bMayBeSet = true;
    }  if (bMayBeSet) {
      
      if (getArchiveImpl().isMetaDataDifferent(getLobFolder(), uriLobFolder))
      {
        if (uriLobFolder != null) {
          
          MetaDataImpl mdi = (MetaDataImpl)getMetaTable().getTable().getParentSchema().getParentArchive().getMetaData();
          if (uriLobFolder.getPath().endsWith("/")) {
            
            if (uriLobFolder.isAbsolute()) {
              
              if (uriLobFolder.getScheme() == null) {
                try {
                  uriLobFolder = new URI("file", "", uriLobFolder.getPath(), null);
                } catch (URISyntaxException uRISyntaxException) {}
              }
              if (!uriLobFolder.getScheme().equals("file")) {
                throw new IllegalArgumentException("Only URIs with scheme \"file\" allowed for LOB folder!");
              }
            } else if (mdi.getLobFolder() == null) {
              
              if (!uriLobFolder.getPath().startsWith("../"))
                throw new IllegalArgumentException("Relative LOB folder URIs must start with \"..\"!"); 
            } 
            this._ft.setLobFolder(XU.toXml(uriLobFolder.toString()));
          } else {
            
            throw new IllegalArgumentException("Path of LOB folder URI must denote a folder (end with \"/\")!");
          } 
        } else {
          throw new IllegalArgumentException("LOB folder URI must not be null!");
        } 
      }
    } else {
      throw new IOException("LOB folder value cannot be set!");
    } 
  }


  
  public URI getLobFolder() {
    URI uriLobFolder = null;
    if (this._ft.getLobFolder() != null) {
      try {
        uriLobFolder = new URI(XU.fromXml(this._ft.getLobFolder()));
      } catch (URISyntaxException uRISyntaxException) {}
    }
    return uriLobFolder;
  }




  
  public URI getAbsoluteLobFolder() {
    URI uriLocal = getLobFolder();
    MetaDataImpl mdi = (MetaDataImpl)getArchiveImpl().getMetaData();
    if (uriLocal != null)
    {
      if (!uriLocal.isAbsolute()) {
        
        URI uriGlobal = mdi.getLobFolder();
        if (uriGlobal != null) {
          
          uriGlobal = mdi.getAbsoluteUri(uriGlobal);
          uriLocal = uriGlobal.resolve(uriLocal);
        } else {
          
          uriLocal = mdi.getAbsoluteUri(uriLocal);
        } 
      }  } 
    return uriLocal;
  }





  
  public String getType() throws IOException {
    String sType = null;
    MetaAttribute ma = getMetaAttribute();
    if (ma != null) {
      sType = ma.getType();
    } else {
      
      MetaColumn mcParent = getParentMetaColumn();
      if (mcParent != null) {
        sType = mcParent.getType();
      } else {
        
        MetaField mfParent = getParentMetaField();
        sType = mfParent.getType();
      } 
    } 
    return sType;
  }





  
  public int getPreType() throws IOException {
    int iDataType = 0;
    MetaAttribute ma = getMetaAttribute();
    if (ma != null) {
      iDataType = ma.getPreType();
    } else {
      
      MetaColumn mcParent = getParentMetaColumn();
      if (mcParent != null) {
        iDataType = mcParent.getPreType();
      } else {
        
        MetaField mfParent = getParentMetaField();
        iDataType = mfParent.getPreType();
      } 
    } 
    return iDataType;
  }





  
  public String getTypeOriginal() throws IOException {
    String sTypeOriginal = null;
    MetaAttribute ma = getMetaAttribute();
    if (ma != null) {
      sTypeOriginal = ma.getTypeOriginal();
    } else {
      
      MetaColumn mcParent = getParentMetaColumn();
      if (mcParent != null) {
        sTypeOriginal = mcParent.getTypeOriginal();
      } else {
        
        MetaField mfParent = getParentMetaField();
        sTypeOriginal = mfParent.getTypeOriginal();
      } 
    } 
    return sTypeOriginal;
  }





  
  public String getTypeSchema() throws IOException {
    String sTypeSchema = null;
    MetaAttribute ma = getMetaAttribute();
    if (ma != null) {
      sTypeSchema = ma.getTypeSchema();
    } else {
      
      MetaColumn mcParent = getParentMetaColumn();
      if (mcParent != null) {
        sTypeSchema = mcParent.getTypeSchema();
      } else {
        
        MetaField mfParent = getParentMetaField();
        sTypeSchema = mfParent.getTypeSchema();
      } 
    } 
    return sTypeSchema;
  }





  
  public String getTypeName() throws IOException {
    String sTypeName = null;
    MetaAttribute ma = getMetaAttribute();
    if (ma != null) {
      sTypeName = ma.getTypeName();
    } else {
      
      MetaColumn mcParent = getParentMetaColumn();
      if (mcParent != null) {
        sTypeName = mcParent.getTypeName();
      } else {
        
        MetaField mfParent = getParentMetaField();
        sTypeName = mfParent.getTypeName();
      } 
    } 
    return sTypeName;
  }





  
  public long getLength() throws IOException {
    long lPrecision = -1L;
    MetaAttribute ma = getMetaAttribute();
    if (ma != null) {
      lPrecision = ma.getLength();
    } else {
      
      MetaColumn mcParent = getParentMetaColumn();
      if (mcParent != null) {
        lPrecision = mcParent.getLength();
      } else {
        
        MetaField mfParent = getParentMetaField();
        lPrecision = mfParent.getLength();
      } 
    } 
    return lPrecision;
  }





  
  public int getScale() throws IOException {
    int iScale = -1;
    MetaAttribute ma = getMetaAttribute();
    if (ma != null) {
      iScale = ma.getScale();
    } else {
      
      MetaColumn mcParent = getParentMetaColumn();
      if (mcParent != null) {
        iScale = mcParent.getScale();
      } else {
        
        MetaField mfParent = getParentMetaField();
        iScale = mfParent.getScale();
      } 
    } 
    return iScale;
  }





  
  public MetaType getMetaType() throws IOException {
    MetaType mt = null;
    MetaAttribute ma = getMetaAttribute();
    if (ma != null) {
      mt = ma.getMetaType();
    } else {
      
      MetaColumn mcParent = getParentMetaColumn();
      MetaField mfParent = getParentMetaField();
      if (mcParent != null) {
        mt = mcParent.getMetaType();
      } else {
        mt = mfParent.getMetaType();
      } 
    }  return mt;
  }





  
  public int getCardinality() throws IOException {
    int iCardinality = -1;
    MetaAttribute ma = getMetaAttribute();
    if (ma != null)
      iCardinality = ma.getCardinality(); 
    return iCardinality;
  }





  
  public void setMimeType(String sMimeType) {
    if (getArchiveImpl().isMetaDataDifferent(getMimeType(), sMimeType))
      this._ft.setMimeType(XU.toXml(sMimeType)); 
  }
  
  public String getMimeType() {
    return XU.fromXml(this._ft.getMimeType());
  }




  
  public void setDescription(String sDescription) {
    if (getArchiveImpl().isMetaDataDifferent(getDescription(), sDescription))
      this._ft.setDescription(XU.toXml(sDescription)); 
  }
  
  public String getDescription() {
    return XU.fromXml(this._ft.getDescription());
  }




  
  public int getMetaFields() throws IOException {
    return getMetaFieldsMap().size();
  }




  
  public MetaField getMetaField(int iField) throws IOException {
    MetaField mf = null;
    
    if (getCardinality() > 0)
    {
      for (int i = this._mapMetaFields.size(); i < iField + 1; i++)
        createMetaField(); 
    }
    for (Iterator<String> iterField = getMetaFieldsMap().keySet().iterator(); mf == null && iterField.hasNext(); ) {
      
      String sName = iterField.next();
      MetaFieldImpl mfi = (MetaFieldImpl)getMetaField(sName);
      if (this._ft.getFields().getField().get(iField) == mfi._ft)
        mf = mfi; 
    } 
    return mf;
  }





  
  public MetaField getMetaField(String sName) throws IOException {
    if (getCardinality() > 0) {
      
      Matcher match = _patARRAY_INDEX.matcher(sName);
      if (match.matches()) {
        
        int iIndex = Integer.parseInt(match.group(1));
        for (int i = this._mapMetaFields.size(); i < iIndex; i++)
          createMetaField(); 
      } 
    } 
    return getMetaFieldsMap().get(sName);
  }





  
  public MetaField createMetaField() throws IOException {
    MetaField mf = null;
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      FieldsType fts = this._ft.getFields();
      if (fts == null) {
        
        fts = _of.createFieldsType();
        this._ft.setFields(fts);
      } 
      FieldType ft = _of.createFieldType();
      fts.getField().add(ft);
      int iPosition = this._mapMetaFields.size() + 1;
      String sName = getName() + "[" + iPosition + "]";
      if (getCardinality() < 0) {
        
        MetaType mt = getMetaType();
        MetaAttribute ma = mt.getMetaAttribute(iPosition - 1);
        sName = ma.getName();
      } 
      ft.setName(XU.toXml(sName));
      mf = newInstance(this, ft, this._sFolder, iPosition);
      this._mapMetaFields.put(mf.getName(), mf);
      getArchiveImpl().isMetaDataDifferent(null, mf);
      if (this._ftTemplate != null) {
        
        FieldsType ftsTemplate = this._ftTemplate.getFields();
        if (ftsTemplate != null) {
          
          FieldType ftTemplate = null;
          for (int iField = 0; iField < ftsTemplate.getField().size(); iField++) {
            
            FieldType ftTry = ftsTemplate.getField().get(iField);
            if (sName.equals(XU.fromXml(ftTry.getName())))
              ftTemplate = ftTry; 
          } 
          if (ftTemplate != null) {
            
            MetaFieldImpl mfi = (MetaFieldImpl)mf;
            mfi.setTemplate(ftTemplate);
          } 
        } 
      } 
    } else {
      
      throw new IOException("New field cannot be added!");
    }  return mf;
  }




  
  public String[] getSearchElements(DU du) throws IOException {
    return new String[] {
        
        getName(), 
        String.valueOf(getPosition()), 
        (getLobFolder() == null) ? "" : getLobFolder().toString(), 
        getMimeType(), 
        getType(), 
        (getCardinality() <= 0) ? "" : String.valueOf(getCardinality()), 
        getDescription()
      };
  }







  
  public String toString() {
    return getName();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\meta\MetaFieldImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */