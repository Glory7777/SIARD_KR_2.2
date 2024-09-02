package ch.admin.bar.siard2.api.meta;
import ch.admin.bar.siard2.api.MetaRoutine;
import ch.admin.bar.siard2.api.MetaSchema;
import ch.admin.bar.siard2.api.MetaSearch;
import ch.admin.bar.siard2.api.MetaTable;
import ch.admin.bar.siard2.api.MetaType;
import ch.admin.bar.siard2.api.MetaView;
import ch.admin.bar.siard2.api.Schema;
import ch.admin.bar.siard2.api.Table;
import ch.admin.bar.siard2.api.generated.ObjectFactory;
import ch.admin.bar.siard2.api.generated.RoutineType;
import ch.admin.bar.siard2.api.generated.RoutinesType;
import ch.admin.bar.siard2.api.generated.SchemaType;
import ch.admin.bar.siard2.api.generated.TableType;
import ch.admin.bar.siard2.api.generated.TablesType;
import ch.admin.bar.siard2.api.generated.TypeType;
import ch.admin.bar.siard2.api.generated.TypesType;
import ch.admin.bar.siard2.api.generated.ViewType;
import ch.admin.bar.siard2.api.generated.ViewsType;
import ch.admin.bar.siard2.api.primary.ArchiveImpl;
import ch.enterag.utils.xml.XU;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MetaSchemaImpl extends MetaSearchImpl implements MetaSchema {
  private static ObjectFactory _of = new ObjectFactory();
  private Map<String, MetaType> _mapMetaTypes = new HashMap<>();
  private Map<String, MetaView> _mapMetaViews = new HashMap<>();
  private Map<String, MetaRoutine> _mapMetaRoutines = new HashMap<>();

  
  private Schema _schema;


  
  private ArchiveImpl getArchiveImpl() {
    return (ArchiveImpl)getSchema().getParentArchive();
  }
  
  public MetaData getParentMetaData() {
    return getArchiveImpl().getMetaData();
  }

  
  public Schema getSchema() {
    return this._schema;
  }


  
  public boolean isValid() {
    boolean bValid = true;
    if (bValid && getMetaTables() < 1 && getMetaTypes() < 1)
      bValid = false; 
    for (int iType = 0; bValid && iType < getMetaTypes(); iType++) {
      
      if (!getMetaType(iType).isValid())
        bValid = false; 
    } 
    for (int iTable = 0; bValid && iTable < getMetaTables(); iTable++) {
      
      if (!getMetaTable(iTable).isValid())
        bValid = false; 
    } 
    for (int iView = 0; bValid && iView < getMetaViews(); iView++) {
      
      if (!getMetaView(iView).isValid())
        bValid = false; 
    } 
    for (int iRoutine = 0; bValid && iRoutine < getMetaRoutines(); iRoutine++) {
      
      if (!getMetaRoutine(iRoutine).isValid())
        bValid = false; 
    } 
    return bValid;
  }
  
  private SchemaType _st = null;

  
  public SchemaType getSchemaType() throws IOException {
    for (int iType = 0; iType < getMetaTypes(); iType++) {
      
      MetaType mt = getMetaType(iType);
      ((MetaTypeImpl)mt).getTypeType();
    } 
    for (int iRoutine = 0; iRoutine < getMetaRoutines(); iRoutine++) {
      
      MetaRoutine mr = getMetaRoutine(iRoutine);
      ((MetaRoutineImpl)mr).getRoutineType();
    } 
    for (int iView = 0; iView < getMetaViews(); iView++) {
      
      MetaView mv = getMetaView(iView);
      ((MetaViewImpl)mv).getViewType();
    } 
    for (int iTable = 0; iTable < getMetaTables(); iTable++) {
      
      MetaTable mt = getMetaTable(iTable);
      if (mt != null)
        ((MetaTableImpl)mt).getTableType(); 
    } 
    return this._st;
  }
  
  private SchemaType _stTemplate = null; public SchemaType getTemplate() {
    return this._stTemplate;
  }




  
  public void setTemplate(SchemaType stTemplate) throws IOException {
    this._stTemplate = stTemplate;
    if (!SU.isNotEmpty(getDescription()))
      setDescription(XU.fromXml(this._stTemplate.getDescription())); 
    TypesType ttys = this._stTemplate.getTypes();
    if (ttys != null)
    {
      for (int iType = 0; iType < ttys.getType().size(); iType++) {
        
        TypeType ttTemplate = ttys.getType().get(iType);
        String sName = XU.fromXml(ttTemplate.getName());
        MetaType mt = getMetaType(sName);
        if (mt != null) {
          
          MetaTypeImpl mti = (MetaTypeImpl)mt;
          mti.setTemplate(ttTemplate);
        } 
      } 
    }
    TablesType tts = this._stTemplate.getTables();
    if (tts != null)
    {
      for (int iTable = 0; iTable < tts.getTable().size(); iTable++) {
        
        TableType ttTemplate = tts.getTable().get(iTable);
        String sName = XU.fromXml(ttTemplate.getName());
        MetaTable mt = getMetaTable(sName);
        if (mt != null) {
          
          MetaTableImpl mti = (MetaTableImpl)mt;
          mti.setTemplate(ttTemplate);
        } 
      } 
    }
    ViewsType vts = this._stTemplate.getViews();
    if (vts != null)
    {
      for (int iView = 0; iView < vts.getView().size(); iView++) {
        
        ViewType vtTemplate = vts.getView().get(iView);
        String sName = XU.fromXml(vtTemplate.getName());
        MetaView mv = getMetaView(sName);
        if (mv != null) {
          
          MetaViewImpl mvi = (MetaViewImpl)mv;
          mvi.setTemplate(vtTemplate);
        } 
      } 
    }
    RoutinesType rts = this._stTemplate.getRoutines();
    if (rts != null)
    {
      for (int iRoutine = 0; iRoutine < rts.getRoutine().size(); iRoutine++) {
        
        RoutineType rtTemplate = rts.getRoutine().get(iRoutine);
        String sName = XU.fromXml(rtTemplate.getName());
        MetaRoutine mr = getMetaRoutine(sName);
        if (mr != null && mr instanceof MetaRoutineImpl) {
          
          MetaRoutineImpl mri = (MetaRoutineImpl)mr;
          mri.setTemplate(rtTemplate);
        } 
      } 
    }
  }







  
  public static SchemaType createSchemaType(String sName, String sFolder) {
    SchemaType st = _of.createSchemaType();
    st.setName(XU.toXml(sName));
    st.setFolder(XU.toXml(sFolder));
    return st;
  }








  
  private MetaSchemaImpl(Schema schema, SchemaType st) throws IOException {
    this._schema = schema;
    this._st = st;

    
    TypesType ttys = this._st.getTypes();
    if (ttys != null)
    {
      for (int iType = 0; iType < ttys.getType().size(); iType++) {
        
        TypeType tt = ttys.getType().get(iType);
        MetaType mt = MetaTypeImpl.newInstance(this, tt);
        this._mapMetaTypes.put(XU.fromXml(tt.getName()), mt);
      } 
    }

    
    ViewsType vts = this._st.getViews();
    if (vts != null)
    {
      for (int iView = 0; iView < vts.getView().size(); iView++) {
        
        ViewType vt = vts.getView().get(iView);
        MetaView mv = MetaViewImpl.newInstance(this, vt);
        this._mapMetaViews.put(XU.fromXml(vt.getName()), mv);
      } 
    }

    
    RoutinesType rts = this._st.getRoutines();
    if (rts != null)
    {
      for (int iRoutine = 0; iRoutine < rts.getRoutine().size(); iRoutine++) {
        
        RoutineType rt = rts.getRoutine().get(iRoutine);
        MetaRoutine mr = MetaRoutineImpl.newInstance(this, rt);
        this._mapMetaRoutines.put(XU.fromXml(rt.getSpecificName()), mr);
      } 
    }
  }









  
  public static MetaSchema newInstance(Schema schema, SchemaType st) throws IOException {
    return new MetaSchemaImpl(schema, st);
  }


  
  public String getName() {
    return XU.fromXml(this._st.getName());
  }

  
  public String getFolder() {
    return XU.fromXml(this._st.getFolder());
  }




  
  public void setDescription(String sDescription) {
    if (getArchiveImpl().isMetaDataDifferent(getDescription(), sDescription))
      this._st.setDescription(XU.toXml(sDescription)); 
  }
  public String getDescription() {
    return XU.fromXml(this._st.getDescription());
  }




  
  public int getMetaTables() {
    int iTables = 0;
    TablesType tts = this._st.getTables();
    if (tts != null)
      iTables = tts.getTable().size(); 
    return iTables;
  }


  
  public MetaTable getMetaTable(int iTable) {
    String sName = ((TableType)this._st.getTables().getTable().get(iTable)).getName();
    return getMetaTable(sName);
  }

  
  public MetaTable getMetaTable(String sName) {
    MetaTable mt = null;
    Table table = getSchema().getTable(sName);
    if (table != null)
      mt = table.getMetaTable(); 
    return mt;
  }





  
  public int getMetaViews() {
    return this._mapMetaViews.size();
  }


  
  public MetaView getMetaView(int iView) {
    MetaView mv = null;
    ViewsType vts = this._st.getViews();
    if (vts != null) {
      
      ViewType vt = vts.getView().get(iView);
      String sName = XU.fromXml(vt.getName());
      mv = getMetaView(sName);
    } 
    return mv;
  }


  
  public MetaView getMetaView(String sName) {
    return this._mapMetaViews.get(sName);
  }



  
  public MetaView createMetaView(String sName) throws IOException {
    MetaView mv = null;
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      if (getMetaView(sName) == null) {
        
        ViewsType vts = this._st.getViews();
        if (vts == null) {
          
          vts = _of.createViewsType();
          this._st.setViews(vts);
        } 
        ViewType vt = _of.createViewType();
        vt.setName(XU.toXml(sName));
        vt.setColumns(_of.createColumnsType());
        vt.setRows(BigInteger.ZERO);
        vts.getView().add(vt);
        mv = MetaViewImpl.newInstance(this, vt);
        this._mapMetaViews.put(sName, mv);
        getArchiveImpl().isMetaDataDifferent(null, mv);
        if (this._stTemplate != null) {
          
          ViewsType vtsTemplate = this._stTemplate.getViews();
          if (vtsTemplate != null) {
            
            ViewType vtTemplate = null;
            for (int iView = 0; vtTemplate == null && iView < vtsTemplate.getView().size(); iView++) {
              
              ViewType vtTry = vtsTemplate.getView().get(iView);
              if (sName.equals(XU.fromXml(vtTry.getName())))
                vtTemplate = vtTry; 
            } 
            if (vtTemplate != null) {
              
              MetaViewImpl mvi = (MetaViewImpl)mv;
              mvi.setTemplate(vtTemplate);
            } 
          } 
        } 
      } else {
        
        throw new IOException("Only one view with the same name allowed per schema!");
      } 
    } else {
      throw new IOException("Views can only be created if archive is open for modification of primary data.");
    }  return mv;
  }




  
  public boolean removeMetaView(MetaView mv) throws IOException {
    boolean bRemoved = false;
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      ViewsType vts = this._st.getViews();
      for (Iterator<ViewType> iterViewType = vts.getView().iterator(); iterViewType.hasNext(); ) {
        
        ViewType vt = iterViewType.next();
        if (vt.getName().equals(mv.getName())) {
          
          iterViewType.remove();
          this._mapMetaViews.remove(mv.getName());
          bRemoved = true;
        } 
      } 
      if (vts.getView().size() == 0) {
        this._st.setViews(null);
      }
    } else {
      throw new IOException("Views can only be removed if archive is open for modification of primary data.");
    }  return bRemoved;
  }





  
  public int getMetaRoutines() {
    return this._mapMetaRoutines.size();
  }


  
  public MetaRoutine getMetaRoutine(int iRoutine) {
    MetaRoutine mr = null;
    RoutinesType rts = this._st.getRoutines();
    if (rts != null) {
      
      RoutineType rt = rts.getRoutine().get(iRoutine);
      String sSpecificName = XU.fromXml(rt.getSpecificName());
      mr = getMetaRoutine(sSpecificName);
    } 
    return mr;
  }


  
  public MetaRoutine getMetaRoutine(String sSpecificName) {
    return this._mapMetaRoutines.get(sSpecificName);
  }




  
  public MetaRoutine createMetaRoutine(String sSpecificName) throws IOException {
    MetaRoutine mr = null;
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      if (getMetaRoutine(sSpecificName) == null) {
        
        RoutinesType rts = this._st.getRoutines();
        if (rts == null) {
          
          rts = _of.createRoutinesType();
          this._st.setRoutines(rts);
        } 
        RoutineType rt = _of.createRoutineType();
        rt.setName(XU.toXml(sSpecificName));
        rt.setSpecificName(XU.toXml(sSpecificName));
        rts.getRoutine().add(rt);
        mr = MetaRoutineImpl.newInstance(this, rt);
        this._mapMetaRoutines.put(sSpecificName, mr);
        getArchiveImpl().isMetaDataDifferent(null, mr);
        if (this._stTemplate != null) {
          
          RoutinesType rtsTemplate = this._stTemplate.getRoutines();
          if (rtsTemplate != null) {
            
            RoutineType rtTemplate = null;
            for (int iRoutine = 0; rtTemplate == null && iRoutine < rtsTemplate.getRoutine().size(); iRoutine++) {
              
              RoutineType rtTry = rtsTemplate.getRoutine().get(iRoutine);
              if (sSpecificName.equals(XU.fromXml(rtTry.getSpecificName())))
                rtTemplate = rtTry; 
            } 
            if (rtTemplate != null) {
              
              MetaRoutineImpl mri = (MetaRoutineImpl)mr;
              mri.setTemplate(rtTemplate);
            } 
          } 
        } 
      } else {
        
        throw new IOException("Only one view with the same name allowed per schema!");
      } 
    } else {
      throw new IOException("Views can only be created if archive is open for modification of primary data.");
    }  return mr;
  }





  
  public int getMetaTypes() {
    return this._mapMetaTypes.size();
  }



  
  public MetaType getMetaType(int iType) {
    MetaType mt = null;
    TypesType ttys = this._st.getTypes();
    if (ttys != null) {
      
      TypeType tt = ttys.getType().get(iType);
      String sName = XU.fromXml(tt.getName());
      mt = getMetaType(sName);
    } 
    return mt;
  }



  
  public MetaType getMetaType(String sName) {
    return this._mapMetaTypes.get(sName);
  }



  
  public MetaType createMetaType(String sName) throws IOException {
    MetaType mt = null;
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      if (getMetaType(sName) == null) {
        
        TypesType ttys = this._st.getTypes();
        if (ttys == null) {
          
          ttys = _of.createTypesType();
          this._st.setTypes(ttys);
        } 
        TypeType tt = _of.createTypeType();
        tt.setName(XU.toXml(sName));
        
        tt.setCategory(CategoryType.DISTINCT);
        tt.setInstantiable(true);
        tt.setFinal(true);
        ttys.getType().add(tt);
        mt = MetaTypeImpl.newInstance(this, tt);
        this._mapMetaTypes.put(sName, mt);
        getArchiveImpl().isMetaDataDifferent(null, mt);
        if (this._stTemplate != null) {
          
          TypesType ttysTemplate = this._stTemplate.getTypes();
          if (ttysTemplate != null) {
            
            TypeType ttTemplate = null;
            for (int iType = 0; ttTemplate == null && iType < ttysTemplate.getType().size(); iType++) {
              
              TypeType ttTry = ttysTemplate.getType().get(iType);
              if (sName.equals(XU.fromXml(ttTry.getName())))
                ttTemplate = ttTry; 
            } 
            if (ttTemplate != null) {
              
              MetaTypeImpl mti = (MetaTypeImpl)mt;
              mti.setTemplate(ttTemplate);
            } 
          } 
        } 
      } else {
        
        throw new IOException("Only one type with the same name allowed per schema!");
      } 
    } else {
      throw new IOException("Types can only be created if archive is open for modification of primary data.");
    }  return mt;
  }





  
  protected MetaSearch[] getSubMetaSearches() throws IOException {
    MetaSearch[] ams = new MetaSearch[getMetaTypes() + getMetaTables() + getMetaViews() + getMetaRoutines()];
    for (int iType = 0; iType < getMetaTypes(); iType++)
      ams[iType] = (MetaSearch)getMetaType(iType); 
    for (int iTable = 0; iTable < getMetaTables(); iTable++)
      ams[getMetaTypes() + iTable] = (MetaSearch)getMetaTable(iTable); 
    for (int iView = 0; iView < getMetaViews(); iView++)
      ams[getMetaTypes() + getMetaTables() + iView] = (MetaSearch)getMetaView(iView); 
    for (int iRoutine = 0; iRoutine < getMetaRoutines(); iRoutine++)
      ams[getMetaTypes() + getMetaTables() + getMetaViews() + iRoutine] = (MetaSearch)getMetaRoutine(iRoutine); 
    return ams;
  }




  
  public String[] getSearchElements(DU du) throws IOException {
    return new String[] {
        
        getName(), 
        getDescription()
      };
  }







  
  public String toString() {
    return getName();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\meta\MetaSchemaImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */