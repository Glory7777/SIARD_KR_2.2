package ch.admin.bar.siard2.api.meta;

import ch.admin.bar.siard2.api.*;
import ch.admin.bar.siard2.api.generated.*;
import ch.admin.bar.siard2.api.primary.ArchiveImpl;
import ch.enterag.sqlparser.BaseSqlFactory;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.datatype.PredefinedType;
import ch.enterag.sqlparser.datatype.enums.PreType;
import ch.enterag.sqlparser.ddl.enums.Multiplier;
import ch.enterag.utils.DU;
import ch.enterag.utils.SU;
import ch.enterag.utils.xml.XU;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;

public class MetaColumnImpl extends MetaValueImpl implements MetaColumn {
  private static final long lKILO = 1024L;
  private static final long lMEGA = 1048576L;
  private static ObjectFactory _of = new ObjectFactory(); private static final long lGIGA = 1073741824L; public static final String _sLOB_FOLDER_PREFIX = "lob";
  private Map<String, MetaField> _mapMetaFields = new HashMap<>();

  
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
  
  private MetaTable _mtParent = null;
  
  public MetaTable getParentMetaTable() {
    return this._mtParent;
  }
  private MetaView _mvParent = null;
  
  public MetaView getParentMetaView() {
    return this._mvParent;
  }
  
  public MetaColumn getAncestorMetaColumn() {
    return this;
  }
  
  public boolean isValid() {
    return (getType() != null || getTypeName() != null);
  }
  private ColumnType _ct = null;

  
  public ColumnType getColumnType() throws IOException {
    for (int iField = 0; iField < getMetaFields(); iField++) {
      
      MetaField mf = getMetaField(iField);
      ((MetaFieldImpl)mf).getFieldType();
    } 
    return this._ct;
  }





  
  private Table getTable() {
    Table table = null;
    if (getParentMetaTable() != null)
      table = getParentMetaTable().getTable(); 
    return table;
  }





  
  private ArchiveImpl getArchiveImpl() {
    Archive archive = null;
    if (getParentMetaTable() != null) {
      archive = getParentMetaTable().getTable().getParentSchema().getParentArchive();
    } else if (getParentMetaView() != null) {
      archive = getParentMetaView().getParentMetaSchema().getSchema().getParentArchive();
    }  return (ArchiveImpl)archive;
  }





  
  private MetaSchema getMetaSchema() {
    MetaSchema ms = null;
    if (getParentMetaTable() != null) {
      ms = getParentMetaTable().getParentMetaSchema();
    } else if (getParentMetaView() != null) {
      ms = getParentMetaView().getParentMetaSchema();
    }  return ms;
  }
  
  private ColumnType _ctTemplate = null;





  
  public void setTemplate(ColumnType ctTemplate) throws IOException {
    this._ctTemplate = ctTemplate;
    if (!SU.isNotEmpty(getDescription()))
      setDescription(XU.fromXml(this._ctTemplate.getDescription())); 
    if (getParentMetaTable() != null)
    {
      if (getArchiveImpl().canModifyPrimaryData()) {
        
        if (getLobFolder() == null && SU.isNotEmpty(this._ctTemplate.getLobFolder()))
          setLobFolder(URI.create(XU.fromXml(this._ctTemplate.getLobFolder()))); 
        if (getMimeType() == null && SU.isNotEmpty(this._ctTemplate.getMimeType()))
          setMimeType(XU.fromXml(this._ctTemplate.getMimeType())); 
      } 
    }
    FieldsType fts = this._ctTemplate.getFields();
    if (fts != null)
    {
      
      for (int iField = 0; iField < fts.getField().size(); iField++) {
        
        FieldType ftTemplate = fts.getField().get(iField);
        String sName = XU.fromXml(ftTemplate.getName());
        MetaField mf = getMetaField(sName);
        if (mf != null) {
          
          MetaFieldImpl mfi = (MetaFieldImpl)mf;
          mfi.setTemplate(ftTemplate);
        } 
      } 
    }
  }






  
  private void openMetaFields() throws IOException {
    FieldsType fts = this._ct.getFields();
    if (fts != null)
    {
      for (int iField = 0; iField < fts.getField().size(); iField++) {
        
        FieldType ft = fts.getField().get(iField);
        MetaField mf = MetaFieldImpl.newInstance(this, ft, this._sFolder, iField + 1);
        this._mapMetaFields.put(mf.getName(), mf);
      } 
    }
  }










  
  private MetaColumnImpl(MetaTable mtParent, ColumnType ct, int iPosition) throws IOException {
    super(iPosition);
    this._mtParent = mtParent;
    this._ct = ct;
    this._sFolder = "lob" + String.valueOf(iPosition - 1) + "/";
    openMetaFields();
  }










  
  private MetaColumnImpl(MetaView mvParent, ColumnType ct, int iPosition) throws IOException {
    super(iPosition);
    this._mvParent = mvParent;
    this._ct = ct;
    openMetaFields();
  }











  
  public static MetaColumn newInstance(MetaTable mtParent, int iPosition, ColumnType ct) throws IOException {
    return new MetaColumnImpl(mtParent, ct, iPosition);
  }











  
  public static MetaColumn newInstance(MetaView mvParent, int iPosition, ColumnType ct) throws IOException {
    return new MetaColumnImpl(mvParent, ct, iPosition);
  }


  
  public String getName() {
    return XU.fromXml(this._ct.getName());
  }





  
  public String getFolder() {
    String sFolder = null;
    if (getLobFolder() == null)
      sFolder = this._sFolder; 
    return sFolder;
  }






  
  public void setLobFolder(URI uriLobFolder) throws IOException {
    boolean bMayBeSet = false;
    if (getLobFolder() == null) {
      
      if (getTable() != null && getTable().isEmpty()) {
        bMayBeSet = true;
      }
    } else {
      bMayBeSet = true;
    }  if (bMayBeSet) {
      
      if (getArchiveImpl().isMetaDataDifferent(getLobFolder(), uriLobFolder))
      {
        if (uriLobFolder != null) {
          
          MetaDataImpl mdi = (MetaDataImpl)getParentMetaTable().getTable().getParentSchema().getParentArchive().getMetaData();
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
            this._ct.setLobFolder(XU.toXml(uriLobFolder.toString()));
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
    if (this._ct.getLobFolder() != null) {
      try {
        uriLobFolder = new URI(XU.fromXml(this._ct.getLobFolder()));
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






  
  public void setType(String sType) throws IOException {
    ArchiveImpl ai = getArchiveImpl();
    if (ai.canModifyPrimaryData()) {
      
      if (ai.isMetaDataDifferent(getTypeSchema(), null))
        this._ct.setTypeSchema(null); 
      if (ai.isMetaDataDifferent(getTypeName(), null))
        this._ct.setTypeName(null); 
      BaseSqlFactory baseSqlFactory = new BaseSqlFactory();
      PredefinedType pt = new PredefinedType((SqlFactory)baseSqlFactory);
      pt.parse(sType);
      sType = pt.format();
      if (ai.isMetaDataDifferent(getType(), sType)) {
        this._ct.setType(XU.toXml(pt.format()));
      }
    } else {
      throw new IOException("Type cannot be set!");
    } 
  }



  
  public void setPreType(int iDataType, long lPrecision, int iScale) throws IOException {
    BaseSqlFactory baseSqlFactory = new BaseSqlFactory();
    PredefinedType prt = baseSqlFactory.newPredefinedType();
    prt.initialize(iDataType, lPrecision, iScale);
    String sType = prt.format();
    setType(sType);
  }
  
  public String getType() {
    return XU.fromXml(this._ct.getType());
  }



  
  public int getPreType() throws IOException {
    int iDataType = 0;
    MetaType mt = getMetaType();
    CategoryType cat = null;
    if (mt != null)
      cat = mt.getCategoryType(); 
    String sType = getType();
    if (sType != null) {
      
      BaseSqlFactory baseSqlFactory = new BaseSqlFactory();
      PredefinedType prt = baseSqlFactory.newPredefinedType();
      prt.parse(sType);
      PreType pt = prt.getType();
      iDataType = pt.getSqlType();
    }
    else if (cat == CategoryType.DISTINCT) {
      iDataType = mt.getBasePreType();
    }  return iDataType;
  }




  
  public long getLength() throws IOException {
    long lLength = -1L;
    MetaType mt = getMetaType();
    CategoryType cat = null;
    if (mt != null)
      cat = mt.getCategoryType(); 
    String sType = getType();
    if (sType != null) {
      
      BaseSqlFactory baseSqlFactory = new BaseSqlFactory();
      PredefinedType prt = baseSqlFactory.newPredefinedType();
      prt.parse(sType);
      lLength = prt.getLength();
      if (lLength != -1L) {
        
        Multiplier mult = prt.getMultiplier();
        if (mult != null)
        {
          switch (mult) {
            case K:
              lLength *= 1024L; break;
            case M: lLength *= 1048576L; break;
            case G: lLength *= 1073741824L;
              break;
          } 
        }
      } else {
        lLength = prt.getPrecision();
      } 
    } else if (cat == CategoryType.DISTINCT) {
      lLength = mt.getBaseLength();
    }  return lLength;
  }




  
  public int getScale() throws IOException {
    int iScale = -1;
    MetaType mt = getMetaType();
    CategoryType cat = null;
    if (mt != null)
      cat = mt.getCategoryType(); 
    String sType = getType();
    if (sType != null) {
      
      BaseSqlFactory baseSqlFactory = new BaseSqlFactory();
      PredefinedType prt = baseSqlFactory.newPredefinedType();
      prt.parse(sType);
      iScale = prt.getScale();
    }
    else if (cat == CategoryType.DISTINCT) {
      iScale = mt.getBaseScale();
    }  return iScale;
  }






  
  public void setTypeOriginal(String sTypeOriginal) throws IOException {
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      if (getArchiveImpl().isMetaDataDifferent(getTypeOriginal(), sTypeOriginal)) {
        this._ct.setTypeOriginal(XU.toXml(sTypeOriginal));
      }
    } else {
      throw new IOException("Original type cannot be set!");
    } 
  }
  public String getTypeOriginal() {
    return XU.fromXml(this._ct.getTypeOriginal());
  }





  
  public void setNullable(boolean bNullable) throws IOException {
    if (getArchiveImpl().canModifyPrimaryData() && getTable() != null && getTable().isEmpty()) {
      
      if (getArchiveImpl().isMetaDataDifferent(Boolean.valueOf(isNullable()), Boolean.valueOf(bNullable))) {
        this._ct.setNullable(Boolean.valueOf(bNullable));
      }
    } else {
      throw new IOException("Nullability cannot be set!");
    } 
  }

  
  public boolean isNullable() {
    boolean bNullable = true;
    if (this._ct.isNullable() != null)
      bNullable = this._ct.isNullable().booleanValue(); 
    return bNullable;
  }






  
  public void setDefaultValue(String sDefaultValue) throws IOException {
    if (getArchiveImpl().canModifyPrimaryData() && getTable() != null && getTable().isEmpty()) {
      
      if (getArchiveImpl().isMetaDataDifferent(getDefaultValue(), sDefaultValue)) {
        this._ct.setDefaultValue(XU.toXml(sDefaultValue));
      }
    } else {
      throw new IOException("Default value cannot be set!");
    } 
  }
  public String getDefaultValue() {
    return XU.fromXml(this._ct.getDefaultValue());
  }


  
  public void setMimeType(String mimeType) throws IOException {
    if (getArchiveImpl().isMetaDataDifferent(getMimeType(), mimeType))
      this._ct.setMimeType(XU.toXml(mimeType)); 
  }
  
  public String getMimeType() {
    return XU.fromXml(this._ct.getMimeType());
  }





  
  public void setTypeSchema(String sTypeSchema) throws IOException {
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      if (getArchiveImpl().isMetaDataDifferent(getType(), null))
        this._ct.setType(null); 
      if (getArchiveImpl().isMetaDataDifferent(getTypeSchema(), sTypeSchema)) {
        this._ct.setTypeSchema(XU.toXml(sTypeSchema));
      }
    } else {
      throw new IOException("Type schema cannot be set!");
    } 
  }
  public String getTypeSchema() {
    return XU.fromXml(this._ct.getTypeSchema());
  }





  
  public void setTypeName(String sTypeName) throws IOException {
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      if (getArchiveImpl().isMetaDataDifferent(getType(), null))
        this._ct.setType(null); 
      if (getArchiveImpl().isMetaDataDifferent(getTypeName(), sTypeName)) {
        
        this._ct.setTypeName(XU.toXml(sTypeName));
        if (getTypeSchema() == null) {
          setTypeSchema(getMetaSchema().getName());
        }
      } 
    } else {
      throw new IOException("Type name cannot be set!");
    } 
  }
  public String getTypeName() {
    return XU.fromXml(this._ct.getTypeName());
  }


  
  public MetaType getMetaType() {
    MetaType mt = null;
    if (getTypeName() != null) {
      
      Schema schema = getArchiveImpl().getSchema(getTypeSchema());
      if (schema != null)
        mt = schema.getMetaSchema().getMetaType(getTypeName()); 
    } 
    return mt;
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
      if (this._ct.getFields().getField().get(iField) == mfi._ft)
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
      
      FieldsType fts = this._ct.getFields();
      if (fts == null) {
        
        fts = _of.createFieldsType();
        this._ct.setFields(fts);
      } 
      FieldType ft = _of.createFieldType();
      fts.getField().add(ft);
      int iPosition = this._mapMetaFields.size() + 1;
      String sName = getName() + "[" + String.valueOf(iPosition) + "]";
      if (getCardinality() < 0) {
        
        MetaType mt = getMetaType();
        MetaAttribute ma = mt.getMetaAttribute(iPosition - 1);
        if (ma != null)
          sName = ma.getName(); 
      } 
      ft.setName(XU.toXml(sName));
      mf = MetaFieldImpl.newInstance(this, ft, this._sFolder, iPosition);
      this._mapMetaFields.put(mf.getName(), mf);
      getArchiveImpl().isMetaDataDifferent(null, mf);
      if (this._ctTemplate != null) {
        
        FieldsType ftsTemplate = this._ctTemplate.getFields();
        if (ftsTemplate != null) {
          
          FieldType ftTemplate = null;
          for (int iField = 0; iField < ftsTemplate.getField().size(); iField++) {
            
            FieldType ftTry = ftsTemplate.getField().get(iField);
            if (sName.equals(ftTry.getName()))
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






  
  public void setCardinality(int iCardinality) throws IOException {
    if (getArchiveImpl().canModifyPrimaryData() && (getTable() == null || getTable().isEmpty())) {
      
      if (getArchiveImpl().isMetaDataDifferent(Integer.valueOf(getCardinality()), Integer.valueOf(iCardinality))) {
        this._ct.setCardinality(BigInteger.valueOf(iCardinality));
      }
    } else {
      throw new IOException("Cardinality cannot be set!");
    } 
  }




  
  public int getCardinality() throws IOException {
    int iCardinality = -1;
    BigInteger bi = this._ct.getCardinality();
    if (bi != null)
      iCardinality = bi.intValue(); 
    return iCardinality;
  }





  
  public void setDescription(String sDescription) {
    if (getArchiveImpl().isMetaDataDifferent(getDescription(), sDescription))
      this._ct.setDescription(XU.toXml(sDescription)); 
  }
  
  public String getDescription() {
    return XU.fromXml(this._ct.getDescription());
  }



  
  public String[] getSearchElements(DU du) throws IOException {
    return new String[] {
        
        getName(), 
        String.valueOf(getPosition()), 
        (getLobFolder() == null) ? "" : getLobFolder().toString(), 
        getMimeType(), 
        getType(), 
        getTypeSchema(), 
        getTypeName(), 
        getTypeOriginal(), 
        String.valueOf(isNullable()), 
        getDefaultValue(), 
        (getCardinality() <= 0) ? "" : String.valueOf(getCardinality()), 
        getDescription()
      };
  }







  
  public String toString() {
    return getName();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\meta\MetaColumnImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */