package ch.admin.bar.siard2.api.meta;

import ch.admin.bar.siard2.api.MetaAttribute;
import ch.admin.bar.siard2.api.MetaType;
import ch.admin.bar.siard2.api.Schema;
import ch.admin.bar.siard2.api.generated.AttributeType;
import ch.admin.bar.siard2.api.generated.CategoryType;
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









public class MetaAttributeImpl
  extends MetaSearchImpl
  implements MetaAttribute
{
  private static final long lKILO = 1024L;
  private static final long lMEGA = 1048576L;
  private static final long lGIGA = 1073741824L;
  private MetaType _mtParent = null;
  
  public MetaType getParentMetaType() {
    return this._mtParent;
  }
  private int _iPosition = -1;
  
  public int getPosition() {
    return this._iPosition;
  }
  
  public boolean isValid() {
    return (getType() != null || getTypeName() != null);
  }




  
  private ArchiveImpl getArchiveImpl() {
    MetaAttribute ma = this;
    MetaType mt = ma.getParentMetaType();
    return (ArchiveImpl)mt.getParentMetaSchema().getSchema().getParentArchive();
  }
  
  private AttributeType _at = null;

  
  public AttributeType getAttributeType() throws IOException {
    return this._at;
  }
  
  private AttributeType _atTemplate = null;





  
  public void setTemplate(AttributeType atTemplate) throws IOException {
    this._atTemplate = atTemplate;
    if (!SU.isNotEmpty(getDescription())) {
      setDescription(XU.fromXml(this._atTemplate.getDescription()));
    }
  }








  
  private MetaAttributeImpl(MetaType mtParent, AttributeType at, int iPosition) throws IOException {
    this._mtParent = mtParent;
    this._at = at;
    this._iPosition = iPosition;
  }










  
  public static MetaAttribute newInstance(MetaType mtParent, AttributeType at, int iPosition) throws IOException {
    return new MetaAttributeImpl(mtParent, at, iPosition);
  }


  
  public String getName() {
    return XU.fromXml(this._at.getName());
  }





  
  public void setType(String sType) throws IOException {
    ArchiveImpl ai = getArchiveImpl();
    if (ai.canModifyPrimaryData()) {
      
      if (ai.isMetaDataDifferent(getTypeSchema(), null))
        this._at.setTypeSchema(null); 
      if (ai.isMetaDataDifferent(getTypeName(), null))
        this._at.setTypeName(null); 
      BaseSqlFactory baseSqlFactory = new BaseSqlFactory();
      PredefinedType pt = new PredefinedType((SqlFactory)baseSqlFactory);
      pt.parse(sType);
      sType = pt.format();
      if (ai.isMetaDataDifferent(getType(), sType)) {
        this._at.setType(XU.toXml(sType));
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
    return XU.fromXml(this._at.getType());
  }

  
  public int getPreType() {
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



  
  public long getLength() {
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
    } else if (cat == CategoryType.DISTINCT) {
      lLength = mt.getBaseLength();
    }  return lLength;
  }



  
  public int getScale() {
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
    ArchiveImpl ai = getArchiveImpl();
    if (ai.canModifyPrimaryData()) {
      
      if (ai.isMetaDataDifferent(getTypeOriginal(), sTypeOriginal)) {
        this._at.setTypeOriginal(XU.toXml(sTypeOriginal));
      }
    } else {
      throw new IOException("Original type cannot be set!");
    } 
  }
  public String getTypeOriginal() {
    return XU.fromXml(this._at.getTypeOriginal());
  }





  
  public void setTypeSchema(String sTypeSchema) throws IOException {
    ArchiveImpl ai = getArchiveImpl();
    if (ai.canModifyPrimaryData()) {
      
      if (ai.isMetaDataDifferent(getType(), null))
        this._at.setType(null); 
      if (ai.isMetaDataDifferent(getTypeSchema(), sTypeSchema)) {
        this._at.setTypeSchema(XU.toXml(sTypeSchema));
      }
    } else {
      throw new IOException("Type schema cannot be set!");
    } 
  }
  public String getTypeSchema() {
    return XU.fromXml(this._at.getTypeSchema());
  }





  
  public void setTypeName(String sTypeName) throws IOException {
    ArchiveImpl ai = getArchiveImpl();
    if (ai.canModifyPrimaryData()) {
      
      if (ai.isMetaDataDifferent(getType(), null))
        this._at.setType(null); 
      if (ai.isMetaDataDifferent(getTypeName(), sTypeName)) {
        
        this._at.setTypeName(XU.toXml(sTypeName));
        if (getTypeSchema() == null) {
          setTypeSchema(getParentMetaType().getParentMetaSchema().getName());
        }
      } 
    } else {
      throw new IOException("Type name cannot be set!");
    } 
  }
  public String getTypeName() {
    return XU.fromXml(this._at.getTypeName());
  }


  
  public MetaType getMetaType() {
    MetaType mt = null;
    if (getTypeName() != null) {
      
      Schema schema = getArchiveImpl().getSchema(getTypeSchema());
      if (schema == null)
        System.err.println("Schema null found in MetaAttribute!"); 
      mt = schema.getMetaSchema().getMetaType(getTypeName());
    } 
    return mt;
  }






  
  public void setNullable(boolean bNullable) throws IOException {
    ArchiveImpl ai = getArchiveImpl();
    if (ai.canModifyPrimaryData()) {
      
      if (ai.isMetaDataDifferent(Boolean.valueOf(isNullable()), Boolean.valueOf(bNullable))) {
        this._at.setNullable(Boolean.valueOf(bNullable));
      }
    } else {
      throw new IOException("Nullability cannot be set!");
    } 
  }

  
  public boolean isNullable() {
    boolean bNullable = true;
    if (this._at.isNullable() != null)
      bNullable = this._at.isNullable().booleanValue(); 
    return bNullable;
  }






  
  public void setDefaultValue(String sDefaultValue) throws IOException {
    ArchiveImpl ai = getArchiveImpl();
    if (ai.canModifyPrimaryData() && getMetaType() == null) {
      
      if (ai.isMetaDataDifferent(getDefaultValue(), sDefaultValue)) {
        this._at.setDefaultValue(XU.toXml(sDefaultValue));
      }
    } else {
      throw new IOException("Default value cannot be set!");
    } 
  }
  public String getDefaultValue() {
    return XU.fromXml(this._at.getDefaultValue());
  }





  
  public void setCardinality(int iCardinality) throws IOException {
    ArchiveImpl ai = getArchiveImpl();
    if (ai.canModifyPrimaryData()) {
      
      if (ai.isMetaDataDifferent(Integer.valueOf(getCardinality()), Integer.valueOf(iCardinality))) {
        this._at.setCardinality(BigInteger.valueOf(iCardinality));
      }
    } else {
      throw new IOException("Cardinality cannot be set!");
    } 
  }



  
  public int getCardinality() {
    int iCardinality = -1;
    BigInteger bi = this._at.getCardinality();
    if (bi != null)
      iCardinality = bi.intValue(); 
    return iCardinality;
  }





  
  public void setDescription(String sDescription) {
    ArchiveImpl ai = getArchiveImpl();
    if (ai.isMetaDataDifferent(getDescription(), sDescription))
      this._at.setDescription(XU.toXml(sDescription)); 
  }
  
  public String getDescription() {
    return XU.fromXml(this._at.getDescription());
  }



  
  public String[] getSearchElements(DU du) throws IOException {
    return new String[] {
        
        getName(), 
        String.valueOf(getPosition()), 
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


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\meta\MetaAttributeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */