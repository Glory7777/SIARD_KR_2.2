package ch.admin.bar.siard2.api.meta;

import ch.admin.bar.siard2.api.MetaColumn;
import ch.admin.bar.siard2.api.MetaSchema;
import ch.admin.bar.siard2.api.MetaSearch;
import ch.admin.bar.siard2.api.MetaView;
import ch.admin.bar.siard2.api.generated.ColumnType;
import ch.admin.bar.siard2.api.generated.ColumnsType;
import ch.admin.bar.siard2.api.generated.ObjectFactory;
import ch.admin.bar.siard2.api.generated.ViewType;
import ch.admin.bar.siard2.api.primary.ArchiveImpl;
import ch.enterag.utils.DU;
import ch.enterag.utils.SU;
import ch.enterag.utils.xml.XU;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;





public class MetaViewImpl
  extends MetaSearchImpl
  implements MetaView
{
  private static final ObjectFactory _of = new ObjectFactory();
  private final Map<String, MetaColumn> _mapMetaColumns = new HashMap<>();
  
  private MetaSchema _msParent = null;
  
  public MetaSchema getParentMetaSchema() {
    return this._msParent;
  }
  private ViewType _vt = null;

  
  public ViewType getViewType() throws IOException {
    for (int iColumn = 0; iColumn < getMetaColumns(); iColumn++) {
      
      MetaColumn mc = getMetaColumn(iColumn);
      ((MetaColumnImpl)mc).getColumnType();
    } 
    return this._vt;
  }



  
  public boolean isValid() {
    boolean bValid = true;
    if (bValid && getMetaColumns() < 1)
      bValid = false; 
    for (int iColumn = 0; bValid && iColumn < getMetaColumns(); iColumn++) {
      
      if (!getMetaColumn(iColumn).isValid())
        bValid = false; 
    } 
    return bValid;
  }





  
  private ArchiveImpl getArchiveImpl() {
    return (ArchiveImpl)getParentMetaSchema().getSchema().getParentArchive();
  }
  
  private ViewType _vtTemplate = null;





  
  public void setTemplate(ViewType vtTemplate) throws IOException {
    this._vtTemplate = vtTemplate;
    if (!SU.isNotEmpty(getQuery()))
      setQuery(XU.fromXml(this._vtTemplate.getQuery())); 
    if (!SU.isNotEmpty(getDescription()))
      setDescription(XU.fromXml(this._vtTemplate.getDescription())); 
    ColumnsType cts = this._vtTemplate.getColumns();
    if (cts != null)
    {
      for (int iColumn = 0; iColumn < cts.getColumn().size(); iColumn++) {
        
        ColumnType ctTemplate = cts.getColumn().get(iColumn);
        String sName = XU.fromXml(ctTemplate.getName());
        MetaColumn mc = getMetaColumn(sName);
        if (mc != null) {
          
          MetaColumnImpl mci = (MetaColumnImpl)mc;
          mci.setTemplate(ctTemplate);
        } 
      } 
    }
  }








  
  private MetaViewImpl(MetaSchema msParent, ViewType vt) throws IOException {
    this._msParent = msParent;
    this._vt = vt;
    
    ColumnsType cts = this._vt.getColumns();
    if (cts != null)
    {
      for (int iColumn = 0; iColumn < cts.getColumn().size(); iColumn++) {
        
        ColumnType ct = cts.getColumn().get(iColumn);
        MetaColumn mc = MetaColumnImpl.newInstance(this, iColumn + 1, ct);
        this._mapMetaColumns.put(XU.fromXml(ct.getName()), mc);
      } 
    }
  }









  
  public static MetaView newInstance(MetaSchema msParent, ViewType vt) throws IOException {
    return new MetaViewImpl(msParent, vt);
  }


  
  public String getName() {
    return XU.fromXml(this._vt.getName());
  }




  
  public void setQuery(String sQuery) {
    if (getArchiveImpl().isMetaDataDifferent(getQuery(), sQuery))
      this._vt.setQuery(XU.toXml(sQuery)); 
  }
  
  public String getQuery() {
    return XU.fromXml(this._vt.getQuery());
  }





  
  public void setQueryOriginal(String sQueryOriginal) throws IOException {
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      if (getArchiveImpl().isMetaDataDifferent(getQueryOriginal(), sQueryOriginal)) {
        this._vt.setQueryOriginal(XU.toXml(sQueryOriginal));
      }
    } else {
      throw new IOException("Original query cannot be set!");
    } 
  }
  public String getQueryOriginal() {
    return XU.fromXml(this._vt.getQueryOriginal());
  }




  
  public void setDescription(String sDescription) {
    if (getArchiveImpl().isMetaDataDifferent(getDescription(), sDescription))
      this._vt.setDescription(XU.toXml(sDescription)); 
  }
  public String getDescription() {
    return XU.fromXml(this._vt.getDescription());
  }





  
  public void setRows(long lRows) throws IOException {
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      if (getArchiveImpl().isMetaDataDifferent(Long.valueOf(getRows()), Long.valueOf(lRows))) {
        this._vt.setRows(BigInteger.valueOf(lRows));
      }
    } else {
      throw new IOException("Rows cannot be set!");
    } 
  }

  
  public long getRows() {
    long lRows = -1L;
    if (this._vt.getRows() != null)
      lRows = this._vt.getRows().intValue(); 
    return lRows;
  }


  
  public int getMetaColumns() {
    return this._mapMetaColumns.size();
  }


  
  public MetaColumn getMetaColumn(int iColumn) {
    MetaColumn mc = null;
    ColumnsType cts = this._vt.getColumns();
    if (cts != null) {
      
      ColumnType ct = cts.getColumn().get(iColumn);
      String sName = XU.fromXml(ct.getName());
      mc = getMetaColumn(sName);
    } 
    return mc;
  }



  
  public MetaColumn getMetaColumn(String sName) {
    return this._mapMetaColumns.get(sName);
  }




  
  public MetaColumn createMetaColumn(String sName) throws IOException {
    MetaColumn mc = null;
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      if (getMetaColumn(sName) == null) {
        
        ColumnsType cts = this._vt.getColumns();
        if (cts == null) {
          
          cts = _of.createColumnsType();
          this._vt.setColumns(cts);
        } 
        ColumnType ct = _of.createColumnType();
        ct.setName(XU.toXml(sName));
        cts.getColumn().add(ct);
        mc = MetaColumnImpl.newInstance(this, this._mapMetaColumns.size() + 1, ct);
        this._mapMetaColumns.put(sName, mc);
        getArchiveImpl().isMetaDataDifferent(null, mc);
        if (this._vtTemplate != null) {
          
          ColumnsType ctsTemplate = this._vtTemplate.getColumns();
          if (ctsTemplate != null) {
            
            ColumnType ctTemplate = null;
            for (int iColumn = 0; ctTemplate == null && iColumn < ctsTemplate.getColumn().size(); iColumn++) {
              
              ColumnType ctTry = ctsTemplate.getColumn().get(iColumn);
              if (sName.equals(XU.fromXml(ctTry.getName())))
                ctTemplate = ctTry; 
            } 
            if (ctTemplate != null && mc instanceof MetaColumnImpl mci) {

                mci.setTemplate(ctTemplate);
            } 
          } 
        } 
      } else {
        
        throw new IOException("Only one column with the same name allowed per table!");
      } 
    } else {
      throw new IOException("New columns can only be created if archive is open for modification of primary data and table is empty.");
    }  return mc;
  }





  
  protected MetaSearch[] getSubMetaSearches() throws IOException {
    MetaSearch[] ams = new MetaSearch[getMetaColumns()];
    for (int iColumn = 0; iColumn < getMetaColumns(); iColumn++)
      ams[iColumn] = getMetaColumn(iColumn);
    return ams;
  }




  
  public String[] getSearchElements(DU du) throws IOException {
    return new String[] {
        
        getName(), 
        getQueryOriginal(), 
        getQuery(), 
        String.valueOf(getRows()), 
        getDescription()
      };
  }







  
  public String toString() {
    return getName();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\meta\MetaViewImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */