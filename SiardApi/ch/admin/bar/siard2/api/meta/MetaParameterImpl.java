package ch.admin.bar.siard2.api.meta;

import ch.admin.bar.siard2.api.MetaParameter;
import ch.admin.bar.siard2.api.MetaRoutine;
import ch.admin.bar.siard2.api.MetaType;
import ch.admin.bar.siard2.api.Schema;
import ch.admin.bar.siard2.api.generated.CategoryType;
import ch.admin.bar.siard2.api.generated.ParameterType;
import ch.admin.bar.siard2.api.primary.ArchiveImpl;
import ch.enterag.sqlparser.BaseSqlFactory;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.datatype.PredefinedType;
import ch.enterag.sqlparser.datatype.enums.PreType;
import ch.enterag.sqlparser.ddl.enums.ParameterMode;
import ch.enterag.utils.DU;
import ch.enterag.utils.SU;
import ch.enterag.utils.xml.XU;
import java.io.IOException;
import java.math.BigInteger;







public class MetaParameterImpl
  extends MetaSearchImpl
  implements MetaParameter
{
  private MetaRoutine _mr = null;
  
  public MetaRoutine getParentMetaRoutine() {
    return this._mr;
  }
  ParameterType _pt = null;

  
  public ParameterType getParameterType() throws IOException {
    return this._pt;
  }
  
  private int _iPosition = -1;
  
  public int getPosition() {
    return this._iPosition;
  }
  
  public boolean isValid() {
    return (getType() != null || getTypeName() != null);
  }




  
  private ArchiveImpl getArchiveImpl() {
    return (ArchiveImpl)getParentMetaRoutine().getParentMetaSchema().getSchema().getParentArchive();
  }
  
  private ParameterType _ptTemplate = null;





  
  public void setTemplate(ParameterType ptTemplate) throws IOException {
    this._ptTemplate = ptTemplate;
    if (!SU.isNotEmpty(getDescription())) {
      setDescription(XU.fromXml(this._ptTemplate.getDescription()));
    }
  }








  
  private MetaParameterImpl(MetaRoutine mr, ParameterType pt, int iPosition) throws IOException {
    this._mr = mr;
    this._pt = pt;
    this._iPosition = iPosition;
  }










  
  public static MetaParameter newInstance(MetaRoutine mr, ParameterType pt, int iPosition) throws IOException {
    return new MetaParameterImpl(mr, pt, iPosition);
  }


  
  public String getName() {
    return XU.fromXml(this._pt.getName());
  }





  
  public void setMode(String sMode) throws IOException {
    ArchiveImpl archiveImpl = getArchiveImpl();
    if (archiveImpl.canModifyPrimaryData()) {
      
      sMode = sMode.toUpperCase();
      ParameterMode pm = ParameterMode.getByKeywords(sMode);
      if (pm != null) {
        
        if (getArchiveImpl().isMetaDataDifferent(getMode(), pm.getKeywords())) {
          this._pt.setMode(sMode);
        }
      } else {
        throw new IllegalArgumentException("Mode must be IN, OUT or INOUT!");
      } 
    } else {
      throw new IOException("Mode cannot be set!");
    } 
  }
  public String getMode() {
    return this._pt.getMode();
  }





  
  public void setType(String sType) throws IOException {
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      if (getArchiveImpl().isMetaDataDifferent(getTypeSchema(), null))
        this._pt.setTypeSchema(null); 
      if (getArchiveImpl().isMetaDataDifferent(getTypeName(), null))
        this._pt.setTypeName(null); 
      BaseSqlFactory baseSqlFactory = new BaseSqlFactory();
      PredefinedType pt = new PredefinedType((SqlFactory)baseSqlFactory);
      pt.parse(sType);
      sType = pt.format();
      if (getArchiveImpl().isMetaDataDifferent(getType(), sType)) {
        this._pt.setType(XU.toXml(sType));
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
    return XU.fromXml(this._pt.getType());
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






  
  public void setTypeOriginal(String sTypeOriginal) throws IOException {
    ArchiveImpl archiveImpl = getArchiveImpl();
    if (archiveImpl.canModifyPrimaryData() && getTypeName() == null) {
      
      if (getArchiveImpl().isMetaDataDifferent(getTypeOriginal(), sTypeOriginal)) {
        this._pt.setTypeOriginal(XU.toXml(sTypeOriginal));
      }
    } else {
      throw new IOException("Original type cannot be set!");
    } 
  }
  public String getTypeOriginal() {
    return XU.fromXml(this._pt.getTypeOriginal());
  }





  
  public void setTypeSchema(String sTypeSchema) throws IOException {
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      if (getArchiveImpl().isMetaDataDifferent(getType(), null))
        this._pt.setType(null); 
      if (getArchiveImpl().isMetaDataDifferent(getTypeSchema(), sTypeSchema)) {
        this._pt.setTypeSchema(XU.toXml(sTypeSchema));
      }
    } else {
      throw new IOException("Type schema cannot be set!");
    } 
  }
  public String getTypeSchema() {
    return XU.fromXml(this._pt.getTypeSchema());
  }





  
  public void setTypeName(String sTypeName) throws IOException {
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      if (getArchiveImpl().isMetaDataDifferent(getType(), null))
        this._pt.setType(null); 
      if (getArchiveImpl().isMetaDataDifferent(getTypeName(), sTypeName)) {
        
        this._pt.setTypeName(XU.toXml(sTypeName));
        if (getTypeSchema() == null) {
          setTypeSchema(getParentMetaRoutine().getParentMetaSchema().getName());
        }
      } 
    } else {
      throw new IOException("Type name cannot be set!");
    } 
  }
  public String getTypeName() {
    return XU.fromXml(this._pt.getTypeName());
  }


  
  public MetaType getMetaType() {
    MetaType mt = null;
    if (getTypeName() != null) {
      
      Schema schema = getArchiveImpl().getSchema(getTypeSchema());
      mt = schema.getMetaSchema().getMetaType(getTypeName());
    } 
    return mt;
  }






  
  public void setCardinality(int iCardinality) throws IOException {
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      if (getArchiveImpl().isMetaDataDifferent(Integer.valueOf(getCardinality()), Integer.valueOf(iCardinality))) {
        this._pt.setCardinality(BigInteger.valueOf(iCardinality));
      }
    } else {
      throw new IOException("Cardinality cannot be set!");
    } 
  }



  
  public int getCardinality() {
    int iCardinality = -1;
    BigInteger bi = this._pt.getCardinality();
    if (bi != null)
      iCardinality = bi.intValue(); 
    return iCardinality;
  }





  
  public void setDescription(String sDescription) {
    if (getArchiveImpl().isMetaDataDifferent(getDescription(), sDescription))
      this._pt.setDescription(XU.toXml(sDescription)); 
  }
  
  public String getDescription() {
    return XU.fromXml(this._pt.getDescription());
  }



  
  public String[] getSearchElements(DU du) throws IOException {
    return new String[] {
        
        getName(), 
        String.valueOf(getPosition()), 
        getMode(), 
        getType(), 
        getTypeSchema(), 
        getTypeName(), 
        getTypeOriginal(), 
        (getCardinality() <= 0) ? "" : String.valueOf(getCardinality()), 
        getDescription()
      };
  }







  
  public String toString() {
    return getName();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\meta\MetaParameterImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */