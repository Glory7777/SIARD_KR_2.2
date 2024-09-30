package ch.admin.bar.siard2.api.meta;

import ch.admin.bar.siard2.api.MetaForeignKey;
import ch.admin.bar.siard2.api.MetaTable;
import ch.admin.bar.siard2.api.generated.ForeignKeyType;
import ch.admin.bar.siard2.api.generated.MatchTypeType;
import ch.admin.bar.siard2.api.generated.ObjectFactory;
import ch.admin.bar.siard2.api.generated.ReferenceType;
import ch.admin.bar.siard2.api.generated.ReferentialActionType;
import ch.admin.bar.siard2.api.primary.ArchiveImpl;
import ch.enterag.utils.DU;
import ch.enterag.utils.SU;
import ch.enterag.utils.xml.XU;
import java.io.IOException;








public class MetaForeignKeyImpl
  extends MetaSearchImpl
  implements MetaForeignKey
{
  private static final ObjectFactory _of = new ObjectFactory();
  private final MetaTable _mtParent;
  private final ForeignKeyType _fkt;
  
  public MetaTable getParentMetaTable() {
    return this._mtParent;
  }
  
  public boolean isValid() {
    return (getReferences() > 0);
  }




  
  private ArchiveImpl getArchive() {
    return (ArchiveImpl)getParentMetaTable().getTable().getParentSchema().getParentArchive();
  }







  
  public void setTemplate(ForeignKeyType fktTemplate) {
    if (!SU.isNotEmpty(getDescription())) {
      setDescription(XU.fromXml(fktTemplate.getDescription()));
    }
  }





  
  private MetaForeignKeyImpl(MetaTable mtParent, ForeignKeyType fkt) {
    this._mtParent = mtParent;
    this._fkt = fkt;
  }







  
  public static MetaForeignKey newInstance(MetaTable mtParent, ForeignKeyType fkt) {
    return new MetaForeignKeyImpl(mtParent, fkt);
  }


  
  public String getName() {
    return XU.fromXml(this._fkt.getName());
  }





  
  public void setReferencedSchema(String sReferencedSchema) throws IOException {
    if (getArchive().canModifyPrimaryData()) {
      
      if (getArchive().isMetaDataDifferent(getReferencedSchema(), sReferencedSchema)) {
        this._fkt.setReferencedSchema(XU.toXml(sReferencedSchema));
      }
    } else {
      throw new IOException("Referenced schema cannot be set!");
    } 
  }
  public String getReferencedSchema() {
    return XU.fromXml(this._fkt.getReferencedSchema());
  }





  
  public void setReferencedTable(String sReferencedTable) throws IOException {
    if (getArchive().canModifyPrimaryData()) {
      
      if (getArchive().isMetaDataDifferent(getReferencedTable(), sReferencedTable)) {
        
        this._fkt.setReferencedTable(XU.toXml(sReferencedTable));
        if (getReferencedSchema() == null) {
          setReferencedSchema(getParentMetaTable().getParentMetaSchema().getName());
        }
      } 
    } else {
      throw new IOException("Referenced table cannot be set!");
    } 
  }
  public String getReferencedTable() {
    return XU.fromXml(this._fkt.getReferencedTable());
  }

  
  public int getReferences() {
    return this._fkt.getReference().size();
  }
  public String getColumn(int iColumn) {
    return XU.fromXml(this._fkt.getReference().get(iColumn).getColumn());
  }
  public String getReferenced(int iColumn) {
    return XU.fromXml(this._fkt.getReference().get(iColumn).getReferenced());
  }



  
  public void addReference(String sColumn, String sReferenced) throws IOException {
    if (getArchive().canModifyPrimaryData()) {
      
      ReferenceType rt = _of.createReferenceType();
      rt.setColumn(XU.toXml(sColumn));
      rt.setReferenced(XU.toXml(sReferenced));
      this._fkt.getReference().add(rt);
      getArchive().isMetaDataDifferent(null, rt);
    } else {
      
      throw new IOException("Reference cannot be set!");
    } 
  }



  
  public String getColumnsString() {
    StringBuffer sbColumns = new StringBuffer();
    for (int iReference = 0; iReference < getReferences(); iReference++) {
      
      if (iReference > 0)
        sbColumns.append(", "); 
      sbColumns.append(getColumn(iReference));
    } 
    return sbColumns.toString();
  }




  
  public String getReferencesString() {
    StringBuffer sbReferenced = new StringBuffer();
    for (int iReference = 0; iReference < getReferences(); iReference++) {
      
      if (iReference > 0)
        sbReferenced.append(", "); 
      sbReferenced.append(getReferenced(iReference));
    } 
    return sbReferenced.toString();
  }






  
  public void setMatchType(String sMatchType) throws IOException {
    if (getArchive().canModifyPrimaryData()) {

      
      try {
        MatchTypeType mtt = MatchTypeType.fromValue(sMatchType.toUpperCase().trim());
        if (getArchive().isMetaDataDifferent(this._fkt.getMatchType(), mtt)) {
          this._fkt.setMatchType(mtt);
        }
      } catch (IllegalArgumentException iae) {
        
        throw new IllegalArgumentException("Invalid match type! (Match type must be \"FULL\", \"PARTIAL\" or \"SIMPLE\"!");
      }
    
    } else {
      
      throw new IOException("Match type cannot be set!");
    } 
  }

  
  public String getMatchType() {
    String sMatchType = null;
    if (this._fkt.getMatchType() != null)
      sMatchType = this._fkt.getMatchType().value(); 
    return sMatchType;
  }






  
  public void setDeleteAction(String sDeleteAction) throws IOException {
    if (getArchive().canModifyPrimaryData()) {

      
      try {
        ReferentialActionType rat = ReferentialActionType.fromValue(sDeleteAction.toUpperCase().trim());
        if (getArchive().isMetaDataDifferent(this._fkt.getDeleteAction(), rat)) {
          this._fkt.setDeleteAction(rat);
        }
      } catch (IllegalArgumentException iae) {
        
        throw new IllegalArgumentException("Invalid referential action! (Referential action must be \"CASCADE\", \"SET NULL\", \"SET DEFAULT\", \"RESTRICT\" or \"NO ACTION\"!");
      }
    
    } else {
      
      throw new IOException("Referential action cannot be set!");
    } 
  }

  
  public String getDeleteAction() {
    String sDeleteAction = null;
    if (this._fkt.getDeleteAction() != null)
      sDeleteAction = this._fkt.getDeleteAction().value(); 
    return sDeleteAction;
  }






  
  public void setUpdateAction(String sUpdateAction) throws IOException {
    if (getArchive().canModifyPrimaryData()) {

      
      try {
        ReferentialActionType rat = ReferentialActionType.fromValue(sUpdateAction.toUpperCase().trim());
        if (getArchive().isMetaDataDifferent(this._fkt.getUpdateAction(), rat)) {
          this._fkt.setUpdateAction(rat);
        }
      } catch (IllegalArgumentException iae) {
        
        throw new IllegalArgumentException("Invalid referential action! (Referential action must be \"CASCADE\", \"SET NULL\", \"SET DEFAULT\", \"RESTRICT\" or \"NO ACTION\"!");
      }
    
    } else {
      
      throw new IOException("Referential action cannot be set!");
    } 
  }

  
  public String getUpdateAction() {
    String sUpdateAction = null;
    if (this._fkt.getUpdateAction() != null)
      sUpdateAction = this._fkt.getUpdateAction().value(); 
    return sUpdateAction;
  }





  
  public void setDescription(String sDescription) {
    if (getArchive().isMetaDataDifferent(getDescription(), sDescription))
      this._fkt.setDescription(XU.toXml(sDescription)); 
  }
  
  public String getDescription() {
    return XU.fromXml(this._fkt.getDescription());
  }



  
  public String[] getSearchElements(DU du) throws IOException {
    return new String[] {
        
        getName(), 
        getColumnsString(), 
        getReferencedSchema(), 
        getReferencedTable(), 
        getReferencesString(), 
        getMatchType(), 
        getDeleteAction(), 
        getUpdateAction(), 
        getDescription()
      };
  }








  
  public String toString() {
    return getName();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\meta\MetaForeignKeyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */