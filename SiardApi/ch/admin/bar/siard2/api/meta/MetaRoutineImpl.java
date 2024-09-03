package ch.admin.bar.siard2.api.meta;

import ch.admin.bar.siard2.api.MetaParameter;
import ch.admin.bar.siard2.api.MetaRoutine;
import ch.admin.bar.siard2.api.MetaSchema;
import ch.admin.bar.siard2.api.MetaSearch;
import ch.admin.bar.siard2.api.generated.ObjectFactory;
import ch.admin.bar.siard2.api.generated.ParameterType;
import ch.admin.bar.siard2.api.generated.ParametersType;
import ch.admin.bar.siard2.api.generated.RoutineType;
import ch.admin.bar.siard2.api.primary.ArchiveImpl;
import ch.enterag.sqlparser.BaseSqlFactory;
import ch.enterag.sqlparser.datatype.PredefinedType;
import ch.enterag.utils.DU;
import ch.enterag.utils.SU;
import ch.enterag.utils.xml.XU;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;






public class MetaRoutineImpl
  extends MetaSearchImpl
  implements MetaRoutine
{
  private static ObjectFactory _of = new ObjectFactory();
  private Map<String, MetaParameter> _mapMetaParameters = new HashMap<>();
  
  private MetaSchema _msParent = null;
  
  public MetaSchema getParentMetaSchema() {
    return this._msParent;
  }


  
  public boolean isValid() {
    boolean bValid = true;
    if (bValid && getMetaParameters() < 0)
      bValid = false; 
    for (int iParameter = 0; bValid && iParameter < getMetaParameters(); iParameter++) {
      
      if (!getMetaParameter(iParameter).isValid())
        bValid = false; 
    } 
    return bValid;
  }
  
  private RoutineType _rt = null;

  
  public RoutineType getRoutineType() throws IOException {
    for (int iParameter = 0; iParameter < getMetaParameters(); iParameter++) {
      
      MetaParameter mp = getMetaParameter(iParameter);
      ((MetaParameterImpl)mp).getParameterType();
    } 
    return this._rt;
  }





  
  private ArchiveImpl getArchive() {
    return (ArchiveImpl)getParentMetaSchema().getSchema().getParentArchive();
  }
  
  private RoutineType _rtTemplate = null;





  
  public void setTemplate(RoutineType rtTemplate) throws IOException {
    this._rtTemplate = rtTemplate;
    if (!SU.isNotEmpty(getBody()))
      setBody(XU.fromXml(this._rtTemplate.getBody())); 
    if (!SU.isNotEmpty(getDescription()))
      setDescription(XU.fromXml(this._rtTemplate.getDescription())); 
    ParametersType pts = this._rtTemplate.getParameters();
    if (pts != null)
    {
      for (int iParameter = 0; iParameter < pts.getParameter().size(); iParameter++) {
        
        ParameterType ptTemplate = pts.getParameter().get(iParameter);
        String sName = XU.fromXml(ptTemplate.getName());
        MetaParameter mp = getMetaParameter(sName);
        if (mp != null) {
          
          MetaParameterImpl mpi = (MetaParameterImpl)mp;
          mpi.setTemplate(ptTemplate);
        } 
      } 
    }
  }








  
  private MetaRoutineImpl(MetaSchema msParent, RoutineType rt) throws IOException {
    this._msParent = msParent;
    this._rt = rt;
    
    ParametersType pts = this._rt.getParameters();
    if (pts != null)
    {
      for (int iParameter = 0; iParameter < pts.getParameter().size(); iParameter++) {
        
        ParameterType pt = pts.getParameter().get(iParameter);
        MetaParameter mp = MetaParameterImpl.newInstance(this, pt, iParameter + 1);
        this._mapMetaParameters.put(XU.fromXml(pt.getName()), mp);
      } 
    }
  }









  
  public static MetaRoutine newInstance(MetaSchema msParent, RoutineType rt) throws IOException {
    return new MetaRoutineImpl(msParent, rt);
  }

  
  public String getSpecificName() {
    return XU.fromXml(this._rt.getSpecificName());
  }




  
  public void setName(String sName) {
    if (getArchive().isMetaDataDifferent(getName(), sName))
      this._rt.setName(XU.toXml(sName)); 
  }
  
  public String getName() {
    return XU.fromXml(this._rt.getName());
  }




  
  public void setBody(String sBody) {
    if (getArchive().isMetaDataDifferent(getBody(), sBody))
      this._rt.setBody(XU.toXml(sBody)); 
  }
  
  public String getBody() {
    return XU.fromXml(this._rt.getBody());
  }





  
  public void setSource(String sSource) throws IOException {
    if (getArchive().canModifyPrimaryData()) {
      
      if (getArchive().isMetaDataDifferent(getSource(), sSource)) {
        this._rt.setSource(XU.toXml(sSource));
      }
    } else {
      throw new IOException("Source cannot be set!");
    } 
  }
  public String getSource() {
    return XU.fromXml(this._rt.getSource());
  }




  
  public void setDescription(String sDescription) {
    if (getArchive().isMetaDataDifferent(getDescription(), sDescription))
      this._rt.setDescription(XU.toXml(sDescription)); 
  }
  public String getDescription() {
    return XU.fromXml(this._rt.getDescription());
  }





  
  public void setCharacteristic(String sCharacteristic) throws IOException {
    if (getArchive().canModifyPrimaryData()) {
      
      if (getArchive().isMetaDataDifferent(getCharacteristic(), sCharacteristic)) {
        this._rt.setCharacteristic(XU.toXml(sCharacteristic));
      }
    } else {
      throw new IOException("Characteristic cannot be set!");
    } 
  }
  public String getCharacteristic() {
    return XU.fromXml(this._rt.getCharacteristic());
  }





  
  public void setReturnType(String sReturnType) throws IOException {
    if (getArchive().canModifyPrimaryData()) {
      
      if (getArchive().isMetaDataDifferent(getReturnType(), sReturnType)) {
        this._rt.setReturnType(XU.toXml(sReturnType));
      }
    } else {
      throw new IOException("ReturnType cannot be set!");
    } 
  }



  
  public void setReturnPreType(int iReturnType, long lPrecision, int iScale) throws IOException {
    BaseSqlFactory baseSqlFactory = new BaseSqlFactory();
    PredefinedType prt = baseSqlFactory.newPredefinedType();
    prt.initialize(iReturnType, lPrecision, iScale);
    String sReturnType = prt.format();
    setReturnType(sReturnType);
  }

  
  public String getReturnType() {
    return XU.fromXml(this._rt.getReturnType());
  }

  
  public int getMetaParameters() {
    return this._mapMetaParameters.size();
  }


  
  public MetaParameter getMetaParameter(int iParameter) {
    MetaParameter mp = null;
    ParametersType pts = this._rt.getParameters();
    if (pts != null) {
      
      ParameterType pt = pts.getParameter().get(iParameter);
      String sName = XU.fromXml(pt.getName());
      mp = getMetaParameter(sName);
    } 
    return mp;
  }



  
  public MetaParameter getMetaParameter(String sName) {
    return this._mapMetaParameters.get(sName);
  }




  
  public MetaParameter createMetaParameter(String sName) throws IOException {
    MetaParameter mp = null;
    if (getArchive().canModifyPrimaryData()) {
      
      if (getMetaParameter(sName) == null) {
        
        ParametersType pts = this._rt.getParameters();
        if (pts == null) {
          
          pts = _of.createParametersType();
          this._rt.setParameters(pts);
        } 
        ParameterType pt = _of.createParameterType();
        pt.setName(XU.toXml(sName));
        pt.setMode("IN");
        pts.getParameter().add(pt);
        mp = MetaParameterImpl.newInstance(this, pt, this._mapMetaParameters.size() + 1);
        this._mapMetaParameters.put(sName, mp);
        getArchive().isMetaDataDifferent(null, mp);
        if (this._rtTemplate != null) {
          
          ParametersType ptsTemplate = this._rtTemplate.getParameters();
          if (ptsTemplate != null) {
            
            ParameterType ptTemplate = null;
            for (int iParameter = 0; ptTemplate == null && iParameter < ptsTemplate.getParameter().size(); iParameter++) {
              
              ParameterType ptTry = ptsTemplate.getParameter().get(iParameter);
              if (sName.equals(XU.fromXml(ptTry.getName())))
                ptTemplate = ptTry; 
            } 
            if (ptTemplate != null && mp instanceof MetaParameterImpl) {
              
              MetaParameterImpl mpi = (MetaParameterImpl)mp;
              mpi.setTemplate(ptTemplate);
            } 
          } 
        } 
      } else {
        
        throw new IOException("Only one parameter with the same name allowed per routine!");
      } 
    } else {
      throw new IOException("New parameters can only be created if archive is open for modification of primary data.");
    }  return mp;
  }





  
  protected MetaSearch[] getSubMetaSearches() throws IOException {
    MetaSearch[] ams = new MetaSearch[getMetaParameters()];
    for (int iParameter = 0; iParameter < getMetaParameters(); iParameter++)
      ams[iParameter] = (MetaSearch)getMetaParameter(iParameter); 
    return ams;
  }




  
  public String[] getSearchElements(DU du) throws IOException {
    return new String[] {
        
        getName(), 
        getSpecificName(), 
        getSource(), 
        getBody(), 
        getCharacteristic(), 
        getReturnType(), 
        getDescription()
      };
  }







  
  public String toString() {
    return getName();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\meta\MetaRoutineImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */