package ch.admin.bar.siard2.api.meta;

import ch.admin.bar.siard2.api.MetaAttribute;
import ch.admin.bar.siard2.api.MetaSchema;
import ch.admin.bar.siard2.api.MetaSearch;
import ch.admin.bar.siard2.api.MetaType;
import ch.admin.bar.siard2.api.generated.*;
import ch.admin.bar.siard2.api.primary.ArchiveImpl;
import ch.enterag.sqlparser.BaseSqlFactory;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.datatype.PredefinedType;
import ch.enterag.sqlparser.datatype.enums.PreType;
import ch.enterag.sqlparser.ddl.enums.Multiplier;
import ch.enterag.utils.xml.XU;

import java.io.IOException;

public class MetaTypeImpl extends MetaSearchImpl implements MetaType {
  private static final long lKILO = 1024L;
  private static ObjectFactory _of = new ObjectFactory(); private static final long lMEGA = 1048576L; private static final long lGIGA = 1073741824L;
  private Map<String, MetaAttribute> _mapMetaAttributes = new HashMap<>();
  
  private MetaSchema _msParent = null;
  
  public MetaSchema getParentMetaSchema() {
    return this._msParent;
  }


  
  public boolean isValid() {
    boolean bValid = true;
    CategoryType cat = getCategoryType();
    if (cat != CategoryType.DISTINCT)
    {
      if (bValid && getMetaAttributes() < 1)
        bValid = false; 
    }
    for (int iAttribute = 0; bValid && iAttribute < getMetaAttributes(); iAttribute++) {
      
      if (!getMetaAttribute(iAttribute).isValid())
        bValid = false; 
    } 
    return bValid;
  }
  
  private TypeType _tt = null;

  
  public TypeType getTypeType() throws IOException {
    for (int iAttribute = 0; iAttribute < getMetaAttributes(); iAttribute++) {
      
      MetaAttribute ma = getMetaAttribute(iAttribute);
      ((MetaAttributeImpl)ma).getAttributeType();
    } 
    return this._tt;
  }





  
  private ArchiveImpl getArchiveImpl() {
    return (ArchiveImpl)getParentMetaSchema().getSchema().getParentArchive();
  }
  
  private TypeType _ttTemplate = null;





  
  public void setTemplate(TypeType ttTemplate) throws IOException {
    this._ttTemplate = ttTemplate;
    if (!SU.isNotEmpty(getDescription()))
      setDescription(XU.fromXml(this._ttTemplate.getDescription())); 
    AttributesType ats = this._ttTemplate.getAttributes();
    if (ats != null)
    {
      for (int iAttribute = 0; iAttribute < ats.getAttribute().size(); iAttribute++) {
        
        AttributeType atTemplate = ats.getAttribute().get(iAttribute);
        String sName = XU.fromXml(atTemplate.getName());
        MetaAttribute ma = getMetaAttribute(sName);
        if (ma != null) {
          
          MetaAttributeImpl mai = (MetaAttributeImpl)ma;
          mai.setTemplate(atTemplate);
        } 
      } 
    }
  }








  
  private MetaTypeImpl(MetaSchema msParent, TypeType tt) throws IOException {
    this._msParent = msParent;
    this._tt = tt;
    
    AttributesType ats = this._tt.getAttributes();
    if (ats != null)
    {
      for (int iAttribute = 0; iAttribute < ats.getAttribute().size(); iAttribute++) {
        
        AttributeType at = ats.getAttribute().get(iAttribute);
        MetaAttribute ma = MetaAttributeImpl.newInstance(this, at, iAttribute + 1);
        this._mapMetaAttributes.put(XU.fromXml(at.getName()), ma);
      } 
    }
  }









  
  public static MetaType newInstance(MetaSchema msParent, TypeType tt) throws IOException {
    return new MetaTypeImpl(msParent, tt);
  }


  
  public String getName() {
    return XU.fromXml(this._tt.getName());
  }





  
  public void setCategory(String sCategory) throws IOException {
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      this._tt.setBase(null);
      this._mapMetaAttributes.clear();
      
      try {
        CategoryType ct = CategoryType.fromValue(sCategory.toLowerCase().trim());
        if (getArchiveImpl().isMetaDataDifferent(this._tt.getCategory(), ct))
          this._tt.setCategory(ct); 
      } catch (IllegalArgumentException iae) {
        throw new IllegalArgumentException("Category must be \"distinct\" or \"udt\"!");
      } 
    } else {
      throw new IOException("Category cannot be set!");
    } 
  }
  public String getCategory() {
    return this._tt.getCategory().value();
  }
  public CategoryType getCategoryType() {
    return CategoryType.fromValue(this._tt.getCategory().value());
  }





  
  public void setUnderSchema(String sUnderSchema) throws IOException {
    if (getArchiveImpl().canModifyPrimaryData() && getArchiveImpl().isEmpty()) {
      
      if (getArchiveImpl().isMetaDataDifferent(getUnderSchema(), sUnderSchema)) {
        this._tt.setUnderSchema(XU.toXml(sUnderSchema));
      }
    } else {
      throw new IOException("Schema of supertype cannot be set!");
    } 
  }
  public String getUnderSchema() {
    return XU.fromXml(this._tt.getUnderSchema());
  }





  
  public void setUnderType(String sUnderType) throws IOException {
    if (getArchiveImpl().canModifyPrimaryData() && getArchiveImpl().isEmpty()) {
      
      if (getArchiveImpl().isMetaDataDifferent(getUnderType(), sUnderType)) {
        
        this._tt.setUnderType(XU.toXml(sUnderType));
        if (getUnderSchema() == null) {
          setUnderSchema(getParentMetaSchema().getName());
        }
      } 
    } else {
      throw new IOException("Supertype cannot be set!");
    } 
  }
  public String getUnderType() {
    return XU.fromXml(this._tt.getUnderType());
  }





  
  public void setInstantiable(boolean bInstantiable) throws IOException {
    if (getArchiveImpl().canModifyPrimaryData() && getArchiveImpl().isEmpty()) {
      
      if (getArchiveImpl().isMetaDataDifferent(Boolean.valueOf(isInstantiable()), Boolean.valueOf(bInstantiable))) {
        this._tt.setInstantiable(bInstantiable);
      }
    } else {
      throw new IOException("Instantiability cannot be set!");
    } 
  }
  public boolean isInstantiable() {
    return this._tt.isInstantiable();
  }





  
  public void setFinal(boolean bFinal) throws IOException {
    if (getArchiveImpl().canModifyPrimaryData() && getArchiveImpl().isEmpty()) {
      
      if (getArchiveImpl().isMetaDataDifferent(Boolean.valueOf(isFinal()), Boolean.valueOf(bFinal))) {
        this._tt.setFinal(bFinal);
      }
    } else {
      throw new IOException("Finality cannot be set!");
    } 
  }
  public boolean isFinal() {
    return this._tt.isFinal();
  }





  
  public void setBase(String sBase) throws IOException {
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      if (sBase != null) {
        
        CategoryType cat = getCategoryType();
        if (cat == CategoryType.DISTINCT) {
          
          BaseSqlFactory baseSqlFactory = new BaseSqlFactory();
          PredefinedType pt = new PredefinedType((SqlFactory)baseSqlFactory);
          pt.parse(sBase);
          sBase = pt.format();
        } else {
          
          throw new IOException("Base type can only be set for \"distinct\" or \"array\" types!");
        }  if (getArchiveImpl().isMetaDataDifferent(getBase(), sBase)) {
          this._tt.setBase(XU.toXml(sBase));
        }
      } 
    } else {
      throw new IOException("Base type cannot be set!");
    } 
  }


  
  public void setBasePreType(int iBaseType, long lPrecision, int iScale) throws IOException {
    BaseSqlFactory baseSqlFactory = new BaseSqlFactory();
    PredefinedType prt = baseSqlFactory.newPredefinedType();
    prt.initialize(iBaseType, lPrecision, iScale);
    String sBase = prt.format();
    setBase(sBase);
  }
  
  public String getBase() {
    return XU.fromXml(this._tt.getBase());
  }


  
  public int getBasePreType() {
    int iBaseType = 0;
    String sType = getBase();
    if (sType != null) {
      
      BaseSqlFactory baseSqlFactory = new BaseSqlFactory();
      PredefinedType pt = baseSqlFactory.newPredefinedType();
      pt.parse(sType);
      PreType ptBase = pt.getType();
      iBaseType = ptBase.getSqlType();
    } 
    return iBaseType;
  }



  
  public long getBaseLength() {
    long lLength = -1L;
    String sType = getBase();
    if (sType != null) {
      
      BaseSqlFactory baseSqlFactory = new BaseSqlFactory();
      PredefinedType prt = baseSqlFactory.newPredefinedType();
      prt.parse(sType);
      lLength = prt.getLength();
      if (lLength != -1L) {
        
        Multiplier mult = prt.getMultiplier();
        if (mult != null)
        {
          switch (prt.getMultiplier()) {
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
    }  return lLength;
  }



  
  public int getBaseScale() {
    int iScale = -1;
    String sType = getBase();
    if (sType != null) {
      
      BaseSqlFactory baseSqlFactory = new BaseSqlFactory();
      PredefinedType prt = baseSqlFactory.newPredefinedType();
      prt.parse(sType);
      iScale = prt.getScale();
    } 
    return iScale;
  }




  
  public int getMetaAttributes() {
    return this._mapMetaAttributes.size();
  }



  
  public MetaAttribute getMetaAttribute(int iAttribute) {
    MetaAttribute ma = null;
    AttributesType ats = this._tt.getAttributes();
    if (ats != null) {
      
      AttributeType at = ats.getAttribute().get(iAttribute);
      String sName = XU.fromXml(at.getName());
      ma = getMetaAttribute(sName);
    } 
    return ma;
  }



  
  public MetaAttribute getMetaAttribute(String sName) {
    return this._mapMetaAttributes.get(sName);
  }




  
  public MetaAttribute createMetaAttribute(String sName) throws IOException {
    MetaAttribute ma = null;
    CategoryType cat = getCategoryType();
    if (getArchiveImpl().canModifyPrimaryData() && cat != CategoryType.DISTINCT) {

      
      if (getMetaAttribute(sName) == null) {
        
        AttributesType ats = this._tt.getAttributes();
        if (ats == null) {
          
          ats = _of.createAttributesType();
          this._tt.setAttributes(ats);
        } 
        AttributeType at = _of.createAttributeType();
        at.setName(XU.toXml(sName));
        ats.getAttribute().add(at);
        ma = MetaAttributeImpl.newInstance(this, at, this._mapMetaAttributes.size() + 1);
        this._mapMetaAttributes.put(sName, ma);
        getArchiveImpl().isMetaDataDifferent(null, ma);
        if (this._ttTemplate != null) {
          
          AttributesType atsTemplate = this._ttTemplate.getAttributes();
          if (atsTemplate != null) {
            
            AttributeType atTemplate = null;
            for (int iAttribute = 0; atTemplate == null && iAttribute < atsTemplate.getAttribute().size(); iAttribute++) {
              
              AttributeType atTry = atsTemplate.getAttribute().get(iAttribute);
              if (sName.equals(XU.fromXml(atTry.getName())))
                atTemplate = atTry; 
            } 
            if (atTemplate != null && ma instanceof MetaAttributeImpl) {
              
              MetaAttributeImpl mai = (MetaAttributeImpl)ma;
              mai.setTemplate(atTemplate);
            } 
          } 
        } 
      } else {
        
        throw new IOException("Only one attribute with the same name allowed!");
      } 
    } else {
      throw new IOException("Attribute cannot be created!");
    }  return ma;
  }





  
  public void setDescription(String sDescription) {
    if (getArchiveImpl().isMetaDataDifferent(getDescription(), sDescription))
      this._tt.setDescription(XU.toXml(sDescription)); 
  }
  
  public String getDescription() {
    return XU.fromXml(this._tt.getDescription());
  }




  
  protected MetaSearch[] getSubMetaSearches() throws IOException {
    MetaSearch[] ams = new MetaSearch[getMetaAttributes()];
    for (int iAttribute = 0; iAttribute < getMetaAttributes(); iAttribute++)
      ams[iAttribute] = (MetaSearch)getMetaAttribute(iAttribute); 
    return ams;
  }




  
  public String[] getSearchElements(DU du) throws IOException {
    return new String[] {
        
        getName(), 
        getCategory(), 



        
        String.valueOf(isInstantiable()), 
        String.valueOf(isFinal()), 
        getBase(), 
        getDescription()
      };
  }







  
  public String toString() {
    return getName();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\meta\MetaTypeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */