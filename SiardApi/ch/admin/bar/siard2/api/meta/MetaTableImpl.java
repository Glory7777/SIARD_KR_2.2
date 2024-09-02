package ch.admin.bar.siard2.api.meta;
import ch.admin.bar.siard2.api.MetaCheckConstraint;
import ch.admin.bar.siard2.api.MetaColumn;
import ch.admin.bar.siard2.api.MetaForeignKey;
import ch.admin.bar.siard2.api.MetaSearch;
import ch.admin.bar.siard2.api.MetaTable;
import ch.admin.bar.siard2.api.MetaTrigger;
import ch.admin.bar.siard2.api.MetaUniqueKey;
import ch.admin.bar.siard2.api.Table;
import ch.admin.bar.siard2.api.generated.CandidateKeysType;
import ch.admin.bar.siard2.api.generated.CheckConstraintType;
import ch.admin.bar.siard2.api.generated.CheckConstraintsType;
import ch.admin.bar.siard2.api.generated.ColumnType;
import ch.admin.bar.siard2.api.generated.ColumnsType;
import ch.admin.bar.siard2.api.generated.ForeignKeyType;
import ch.admin.bar.siard2.api.generated.ForeignKeysType;
import ch.admin.bar.siard2.api.generated.TableType;
import ch.admin.bar.siard2.api.generated.TriggerType;
import ch.admin.bar.siard2.api.generated.TriggersType;
import ch.admin.bar.siard2.api.generated.UniqueKeyType;
import ch.enterag.utils.xml.XU;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetaTableImpl extends MetaSearchImpl implements MetaTable {
  private static ObjectFactory _of = new ObjectFactory();
  private MetaUniqueKey _mukPrimaryKey = null;
  private Map<String, MetaColumn> _mapMetaColumns = new HashMap<>();
  private Map<String, MetaForeignKey> _mapMetaForeignKeys = new HashMap<>();
  private Map<String, MetaUniqueKey> _mapMetaCandidateKeys = new HashMap<>();
  private Map<String, MetaCheckConstraint> _mapMetaCheckConstraints = new HashMap<>();
  private Map<String, MetaTrigger> _mapMetaTriggers = new HashMap<>();
  
  private Table _table;

  
  public MetaSchema getParentMetaSchema() {
    return getTable().getParentSchema().getMetaSchema();
  }


  
  public Table getTable() {
    return this._table;
  }



  
  public boolean isValid() {
    boolean bValid = true;
    if (bValid && getMetaColumns() < 1)
      bValid = false; 
    for (int iColumn = 0; bValid && iColumn < getMetaColumns(); iColumn++) {
      
      if (!getMetaColumn(iColumn).isValid())
        bValid = false; 
    } 
    if (bValid && getMetaPrimaryKey() != null)
    {
      if (!getMetaPrimaryKey().isValid())
        bValid = false; 
    }
    for (int iCandidateKey = 0; bValid && iCandidateKey < getMetaCandidateKeys(); iCandidateKey++) {
      
      if (!getMetaCandidateKey(iCandidateKey).isValid())
        bValid = false; 
    } 
    for (int iCheckConstraint = 0; bValid && iCheckConstraint < getMetaCheckConstraints(); iCheckConstraint++) {
      
      if (!getMetaCheckConstraint(iCheckConstraint).isValid())
        bValid = false; 
    } 
    for (int iTrigger = 0; bValid && iTrigger < getMetaTriggers(); iTrigger++) {
      
      if (!getMetaTrigger(iTrigger).isValid())
        bValid = false; 
    } 
    return bValid;
  }
  
  private TableType _tt = null;

  
  public TableType getTableType() throws IOException {
    for (int iColumn = 0; iColumn < getMetaColumns(); iColumn++) {
      
      MetaColumn mc = getMetaColumn(iColumn);
      ((MetaColumnImpl)mc).getColumnType();
    } 
    return this._tt;
  }





  
  private ArchiveImpl getArchiveImpl() {
    return (ArchiveImpl)getTable().getParentSchema().getParentArchive();
  }
  
  private TableType _ttTemplate = null;





  
  public void setTemplate(TableType ttTemplate) throws IOException {
    this._ttTemplate = ttTemplate;
    if (!SU.isNotEmpty(getDescription()))
      setDescription(XU.fromXml(this._ttTemplate.getDescription())); 
    ColumnsType cts = this._ttTemplate.getColumns();
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
    UniqueKeyType uktTemplate = this._ttTemplate.getPrimaryKey();
    if (uktTemplate != null) {
      
      MetaUniqueKey muk = getMetaPrimaryKey();
      if (muk != null) {
        
        MetaUniqueKeyImpl muki = (MetaUniqueKeyImpl)muk;
        muki.setTemplate(uktTemplate);
      } 
    } 
    ForeignKeysType fkts = this._ttTemplate.getForeignKeys();
    if (fkts != null)
    {
      for (int iForeignKey = 0; iForeignKey < fkts.getForeignKey().size(); iForeignKey++) {
        
        ForeignKeyType fktTemplate = fkts.getForeignKey().get(iForeignKey);
        String sName = XU.fromXml(fktTemplate.getName());
        MetaForeignKey mfk = getMetaForeignKey(sName);
        if (mfk != null) {
          
          MetaForeignKeyImpl mfki = (MetaForeignKeyImpl)mfk;
          mfki.setTemplate(fktTemplate);
        } 
      } 
    }
    CandidateKeysType ckts = this._ttTemplate.getCandidateKeys();
    if (ckts != null)
    {
      for (int iCandidateKey = 0; iCandidateKey < ckts.getCandidateKey().size(); iCandidateKey++) {
        
        uktTemplate = ckts.getCandidateKey().get(iCandidateKey);
        String sName = XU.fromXml(uktTemplate.getName());
        MetaUniqueKey muk = getMetaCandidateKey(sName);
        if (muk != null) {
          
          MetaUniqueKeyImpl muki = (MetaUniqueKeyImpl)muk;
          muki.setTemplate(uktTemplate);
        } 
      } 
    }
    CheckConstraintsType ccts = this._ttTemplate.getCheckConstraints();
    if (ccts != null)
    {
      for (int iCheckConstraint = 0; iCheckConstraint < ccts.getCheckConstraint().size(); iCheckConstraint++) {
        
        CheckConstraintType cctTemplate = ccts.getCheckConstraint().get(iCheckConstraint);
        String sName = XU.fromXml(cctTemplate.getName());
        MetaCheckConstraint mcc = getMetaCheckConstraint(sName);
        if (mcc != null) {
          
          MetaCheckConstraintImpl mcci = (MetaCheckConstraintImpl)mcc;
          mcci.setTemplate(cctTemplate);
        } 
      } 
    }
    TriggersType tts = this._ttTemplate.getTriggers();
    if (tts != null)
    {
      for (int iTrigger = 0; iTrigger < tts.getTrigger().size(); iTrigger++) {
        
        TriggerType trtTemplate = tts.getTrigger().get(iTrigger);
        String sName = XU.fromXml(trtTemplate.getName());
        MetaTrigger mt = getMetaTrigger(sName);
        if (mt != null) {
          
          MetaTriggerImpl mti = (MetaTriggerImpl)mt;
          mti.setTemplate(trtTemplate);
        } 
      } 
    }
  }







  
  public static TableType createTableType(String sName, String sFolder) {
    TableType tt = _of.createTableType();
    tt.setName(XU.toXml(sName));
    tt.setFolder(XU.toXml(sFolder));
    tt.setColumns(_of.createColumnsType());
    tt.setRows(BigInteger.ZERO);
    return tt;
  }








  
  private MetaTableImpl(Table table, TableType tt) throws IOException {
    this._table = table;
    this._tt = tt;
    
    ColumnsType cts = this._tt.getColumns();
    for (int iColumn = 0; iColumn < cts.getColumn().size(); iColumn++) {
      
      ColumnType ct = cts.getColumn().get(iColumn);
      MetaColumn mc = MetaColumnImpl.newInstance(this, iColumn + 1, ct);
      this._mapMetaColumns.put(XU.fromXml(ct.getName()), mc);
    } 
    
    UniqueKeyType uktPrimary = this._tt.getPrimaryKey();
    if (uktPrimary != null) {
      this._mukPrimaryKey = MetaUniqueKeyImpl.newInstance(this, uktPrimary);
    }
    ForeignKeysType fkts = this._tt.getForeignKeys();
    if (fkts != null)
    {
      for (int iForeignKey = 0; iForeignKey < fkts.getForeignKey().size(); iForeignKey++) {
        
        ForeignKeyType fkt = fkts.getForeignKey().get(iForeignKey);
        MetaForeignKey mfk = MetaForeignKeyImpl.newInstance(this, fkt);
        this._mapMetaForeignKeys.put(XU.fromXml(fkt.getName()), mfk);
      } 
    }
    
    CandidateKeysType ckts = this._tt.getCandidateKeys();
    if (ckts != null)
    {
      for (int iCandidateKey = 0; iCandidateKey < ckts.getCandidateKey().size(); iCandidateKey++) {
        
        UniqueKeyType ukt = ckts.getCandidateKey().get(iCandidateKey);
        MetaUniqueKey muk = MetaUniqueKeyImpl.newInstance(this, ukt);
        this._mapMetaCandidateKeys.put(XU.fromXml(ukt.getName()), muk);
      } 
    }
    
    CheckConstraintsType ccts = this._tt.getCheckConstraints();
    if (ccts != null)
    {
      for (int iCheckConstraint = 0; iCheckConstraint < ccts.getCheckConstraint().size(); iCheckConstraint++) {
        
        CheckConstraintType cct = ccts.getCheckConstraint().get(iCheckConstraint);
        MetaCheckConstraint mcc = MetaCheckConstraintImpl.newInstance(this, cct);
        this._mapMetaCheckConstraints.put(XU.fromXml(cct.getName()), mcc);
      } 
    }
    
    TriggersType tts = this._tt.getTriggers();
    if (tts != null)
    {
      for (int iTrigger = 0; iTrigger < tts.getTrigger().size(); iTrigger++) {
        
        TriggerType trt = tts.getTrigger().get(iTrigger);
        MetaTrigger mt = MetaTriggerImpl.newInstance(this, trt);
        this._mapMetaTriggers.put(XU.fromXml(trt.getName()), mt);
      } 
    }
  }









  
  public static MetaTable newInstance(Table table, TableType tt) throws IOException {
    return new MetaTableImpl(table, tt);
  }


  
  public String getName() {
    return XU.fromXml(this._tt.getName());
  }

  
  public String getFolder() {
    return XU.fromXml(this._tt.getFolder());
  }




  
  public void setDescription(String sDescription) {
    if (getArchiveImpl().isMetaDataDifferent(getDescription(), sDescription))
      this._tt.setDescription(XU.toXml(sDescription)); 
  }
  public String getDescription() {
    return XU.fromXml(this._tt.getDescription());
  }





  
  public void setRows(long lRows) throws IOException {
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      if (getArchiveImpl().isMetaDataDifferent(this._tt.getRows(), BigInteger.valueOf(lRows))) {
        this._tt.setRows(BigInteger.valueOf(lRows));
      }
    } else {
      throw new IOException("Rows cannot be set!");
    } 
  }
  public long getRows() {
    return this._tt.getRows().longValue();
  }

  
  public int getMetaColumns() {
    return this._mapMetaColumns.size();
  }


  
  public MetaColumn getMetaColumn(int iColumn) {
    MetaColumn mc = null;
    ColumnsType cts = this._tt.getColumns();
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
    if (getArchiveImpl().canModifyPrimaryData() && getTable().isEmpty()) {
      
      if (getMetaColumn(sName) == null) {
        
        ColumnsType cts = this._tt.getColumns();
        if (cts == null) {
          
          cts = _of.createColumnsType();
          this._tt.setColumns(cts);
        } 
        ColumnType ct = _of.createColumnType();
        ct.setName(XU.toXml(sName));
        cts.getColumn().add(ct);
        mc = MetaColumnImpl.newInstance(this, this._mapMetaColumns.size() + 1, ct);
        this._mapMetaColumns.put(sName, mc);
        getArchiveImpl().isMetaDataDifferent(null, mc);
        if (this._ttTemplate != null) {
          
          ColumnsType ctsTemplate = this._ttTemplate.getColumns();
          if (ctsTemplate != null) {
            
            ColumnType ctTemplate = null;
            for (int iColumn = 0; ctTemplate == null && iColumn < ctsTemplate.getColumn().size(); iColumn++) {
              
              ColumnType ctTry = ctsTemplate.getColumn().get(iColumn);
              if (sName.equals(XU.fromXml(ctTry.getName())))
                ctTemplate = ctTry; 
            } 
            if (ctTemplate != null && mc instanceof MetaColumnImpl) {
              
              MetaColumnImpl mci = (MetaColumnImpl)mc;
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





  
  public MetaUniqueKey getMetaPrimaryKey() {
    return this._mukPrimaryKey;
  }



  
  public MetaUniqueKey createMetaPrimaryKey(String sName) {
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      UniqueKeyType uktPrimary = _of.createUniqueKeyType();
      uktPrimary.setName(XU.toXml(sName));
      this._tt.setPrimaryKey(uktPrimary);
      this._mukPrimaryKey = MetaUniqueKeyImpl.newInstance(this, uktPrimary);
      getArchiveImpl().isMetaDataDifferent(null, this._mukPrimaryKey);
    } 
    return this._mukPrimaryKey;
  }


  
  public int getMetaForeignKeys() {
    return this._mapMetaForeignKeys.size();
  }


  
  public MetaForeignKey getMetaForeignKey(int iForeignKey) {
    MetaForeignKey mfk = null;
    ForeignKeysType fkts = this._tt.getForeignKeys();
    if (fkts != null) {
      
      ForeignKeyType fkt = fkts.getForeignKey().get(iForeignKey);
      String sName = XU.fromXml(fkt.getName());
      mfk = getMetaForeignKey(sName);
    } 
    return mfk;
  }



  
  public MetaForeignKey getMetaForeignKey(String sName) {
    return this._mapMetaForeignKeys.get(sName);
  }




  
  public MetaForeignKey createMetaForeignKey(String sName) throws IOException {
    MetaForeignKey mfk = null;
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      if (getMetaForeignKey(sName) == null) {
        
        ForeignKeysType fkts = this._tt.getForeignKeys();
        if (fkts == null) {
          
          fkts = _of.createForeignKeysType();
          this._tt.setForeignKeys(fkts);
        } 
        ForeignKeyType fkt = _of.createForeignKeyType();
        fkt.setName(XU.toXml(sName));
        fkts.getForeignKey().add(fkt);
        mfk = MetaForeignKeyImpl.newInstance(this, fkt);
        this._mapMetaForeignKeys.put(sName, mfk);
        getArchiveImpl().isMetaDataDifferent(null, mfk);
        if (this._ttTemplate != null) {
          
          ForeignKeysType fktsTemplate = this._ttTemplate.getForeignKeys();
          if (fktsTemplate != null) {
            
            ForeignKeyType fktTemplate = null;
            for (int iForeignKey = 0; fktTemplate == null && iForeignKey < fktsTemplate.getForeignKey().size(); iForeignKey++) {
              
              ForeignKeyType fktTry = fktsTemplate.getForeignKey().get(iForeignKey);
              if (sName.equals(XU.fromXml(fktTry.getName())))
                fktTemplate = fktTry; 
            } 
            if (fktTemplate != null && mfk instanceof MetaForeignKeyImpl) {
              
              MetaForeignKeyImpl mfki = (MetaForeignKeyImpl)mfk;
              mfki.setTemplate(fktTemplate);
            } 
          } 
        } 
      } else {
        
        throw new IOException("Only one foreign key with the same name allowed per table!");
      } 
    } else {
      throw new IOException("New foreign keys can only be created if archive is open for modification of primary data.");
    }  return mfk;
  }


  
  public int getMetaCandidateKeys() {
    return this._mapMetaCandidateKeys.size();
  }


  
  public MetaUniqueKey getMetaCandidateKey(int iCandidateKey) {
    MetaUniqueKey muk = null;
    CandidateKeysType ckts = this._tt.getCandidateKeys();
    if (ckts != null) {
      
      UniqueKeyType ukt = ckts.getCandidateKey().get(iCandidateKey);
      String sName = XU.fromXml(ukt.getName());
      muk = getMetaCandidateKey(sName);
    } 
    return muk;
  }



  
  public MetaUniqueKey getMetaCandidateKey(String sName) {
    return this._mapMetaCandidateKeys.get(sName);
  }




  
  public MetaUniqueKey createMetaCandidateKey(String sName) throws IOException {
    MetaUniqueKey muk = null;
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      if (getMetaCandidateKey(sName) == null) {
        
        CandidateKeysType ckts = this._tt.getCandidateKeys();
        if (ckts == null) {
          
          ckts = _of.createCandidateKeysType();
          this._tt.setCandidateKeys(ckts);
        } 
        UniqueKeyType ukt = _of.createUniqueKeyType();
        ukt.setName(XU.toXml(sName));
        ckts.getCandidateKey().add(ukt);
        muk = MetaUniqueKeyImpl.newInstance(this, ukt);
        this._mapMetaCandidateKeys.put(sName, muk);
        getArchiveImpl().isMetaDataDifferent(null, muk);
        if (this._ttTemplate != null) {
          
          CandidateKeysType fktsTemplate = this._ttTemplate.getCandidateKeys();
          if (fktsTemplate != null) {
            
            UniqueKeyType uktTemplate = null;
            for (int iCandidateKey = 0; uktTemplate == null && iCandidateKey < fktsTemplate.getCandidateKey().size(); iCandidateKey++) {
              
              UniqueKeyType uktTry = fktsTemplate.getCandidateKey().get(iCandidateKey);
              if (sName.equals(XU.fromXml(uktTry.getName())))
                uktTemplate = uktTry; 
            } 
            if (uktTemplate != null && muk instanceof MetaUniqueKeyImpl) {
              
              MetaUniqueKeyImpl muki = (MetaUniqueKeyImpl)muk;
              muki.setTemplate(uktTemplate);
            } 
          } 
        } 
      } else {
        
        throw new IOException("Only one candidate key with the same name allowed per table!");
      } 
    } else {
      throw new IOException("New candidaten keys can only be created if archive is open for modification of primary data.");
    }  return muk;
  }


  
  public int getMetaCheckConstraints() {
    return this._mapMetaCheckConstraints.size();
  }


  
  public MetaCheckConstraint getMetaCheckConstraint(int iCheckConstraint) {
    MetaCheckConstraint mcc = null;
    CheckConstraintsType ccts = this._tt.getCheckConstraints();
    if (ccts != null) {
      
      CheckConstraintType cct = ccts.getCheckConstraint().get(iCheckConstraint);
      String sName = XU.fromXml(cct.getName());
      mcc = getMetaCheckConstraint(sName);
    } 
    return mcc;
  }



  
  public MetaCheckConstraint getMetaCheckConstraint(String sName) {
    return this._mapMetaCheckConstraints.get(sName);
  }




  
  public MetaCheckConstraint createMetaCheckConstraint(String sName) throws IOException {
    MetaCheckConstraint mcc = null;
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      if (getMetaCheckConstraint(sName) == null) {
        
        CheckConstraintsType ccts = this._tt.getCheckConstraints();
        if (ccts == null) {
          
          ccts = _of.createCheckConstraintsType();
          this._tt.setCheckConstraints(ccts);
        } 
        CheckConstraintType cct = _of.createCheckConstraintType();
        cct.setName(XU.toXml(sName));
        ccts.getCheckConstraint().add(cct);
        mcc = MetaCheckConstraintImpl.newInstance(this, cct);
        this._mapMetaCheckConstraints.put(sName, mcc);
        getArchiveImpl().isMetaDataDifferent(null, mcc);
        if (this._ttTemplate != null) {
          
          CheckConstraintsType cctsTemplate = this._ttTemplate.getCheckConstraints();
          if (cctsTemplate != null) {
            
            CheckConstraintType cctTemplate = null;
            for (int iCheckConstraint = 0; cctTemplate == null && iCheckConstraint < cctsTemplate.getCheckConstraint().size(); iCheckConstraint++) {
              
              CheckConstraintType cctTry = cctsTemplate.getCheckConstraint().get(iCheckConstraint);
              if (sName.equals(XU.fromXml(cctTry.getName())))
                cctTemplate = cctTry; 
            } 
            if (cctTemplate != null && mcc instanceof MetaCheckConstraintImpl) {
              
              MetaCheckConstraintImpl mcci = (MetaCheckConstraintImpl)mcc;
              mcci.setTemplate(cctTemplate);
            } 
          } 
        } 
      } else {
        
        throw new IOException("Only one check constraint with the same name allowed per table!");
      } 
    } else {
      throw new IOException("New check constraints can only be created if archive is open for modification of primary data.");
    }  return mcc;
  }


  
  public int getMetaTriggers() {
    return this._mapMetaTriggers.size();
  }


  
  public MetaTrigger getMetaTrigger(int iTrigger) {
    MetaTrigger mt = null;
    TriggersType tts = this._tt.getTriggers();
    if (tts != null) {
      
      TriggerType tt = tts.getTrigger().get(iTrigger);
      String sName = XU.fromXml(tt.getName());
      mt = getMetaTrigger(sName);
    } 
    return mt;
  }



  
  public MetaTrigger getMetaTrigger(String sName) {
    return this._mapMetaTriggers.get(sName);
  }




  
  public MetaTrigger createMetaTrigger(String sName) throws IOException {
    MetaTrigger mt = null;
    if (getArchiveImpl().canModifyPrimaryData()) {
      
      if (getMetaTrigger(sName) == null) {
        
        TriggersType tts = this._tt.getTriggers();
        if (tts == null) {
          
          tts = _of.createTriggersType();
          this._tt.setTriggers(tts);
        } 
        TriggerType tt = _of.createTriggerType();
        tt.setName(XU.toXml(sName));
        tts.getTrigger().add(tt);
        mt = MetaTriggerImpl.newInstance(this, tt);
        this._mapMetaTriggers.put(sName, mt);
        getArchiveImpl().isMetaDataDifferent(null, mt);
        if (this._ttTemplate != null) {
          
          TriggersType ttsTemplate = this._ttTemplate.getTriggers();
          if (ttsTemplate != null) {
            
            TriggerType ttTemplate = null;
            for (int iTrigger = 0; ttTemplate == null && iTrigger < ttsTemplate.getTrigger().size(); iTrigger++) {
              
              TriggerType ttTry = ttsTemplate.getTrigger().get(iTrigger);
              if (sName.equals(XU.fromXml(ttTry.getName())))
                ttTemplate = ttTry; 
            } 
            if (ttTemplate != null && mt instanceof MetaTriggerImpl) {
              
              MetaTriggerImpl mti = (MetaTriggerImpl)mt;
              mti.setTemplate(ttTemplate);
            } 
          } 
        } 
      } else {
        
        throw new IOException("Only one trigger with the same name allowed per table!");
      } 
    } else {
      throw new IOException("New triggers can only be created if archive is open for modification of primary data.");
    }  return mt;
  }






  
  public List<List<String>> getColumnNames(boolean bSupportsArrays, boolean bSupportsUdts) throws IOException {
    List<List<String>> llNames = new ArrayList<>();
    for (int iColumn = 0; iColumn < getMetaColumns(); iColumn++) {
      
      MetaColumn mc = getMetaColumn(iColumn);
      llNames.addAll(mc.getNames(bSupportsArrays, bSupportsUdts));
    } 
    return llNames;
  }





  
  public String getType(List<String> listNames) throws IOException {
    MetaColumn mc = getMetaColumn(listNames.get(0));
    return mc.getType(listNames);
  }





  
  protected MetaSearch[] getSubMetaSearches() throws IOException {
    int iPrimaryKeys = 0;
    if (getMetaPrimaryKey() != null) {
      iPrimaryKeys = 1;
    }




    
    MetaSearch[] ams = new MetaSearch[getMetaColumns() + iPrimaryKeys + getMetaCandidateKeys() + getMetaForeignKeys() + getMetaCheckConstraints() + getMetaTriggers()];
    for (int iColumn = 0; iColumn < getMetaColumns(); iColumn++)
      ams[iColumn] = (MetaSearch)getMetaColumn(iColumn); 
    for (int iPrimaryKey = 0; iPrimaryKey < iPrimaryKeys; iPrimaryKey++)
      ams[getMetaColumns() + iPrimaryKey] = (MetaSearch)getMetaPrimaryKey(); 
    for (int iCandidateKey = 0; iCandidateKey < getMetaCandidateKeys(); iCandidateKey++)
      ams[getMetaColumns() + iPrimaryKeys + iCandidateKey] = (MetaSearch)getMetaCandidateKey(iCandidateKey); 
    for (int iForeignKey = 0; iForeignKey < getMetaForeignKeys(); iForeignKey++)
      ams[getMetaColumns() + iPrimaryKeys + getMetaCandidateKeys() + iForeignKey] = (MetaSearch)
        getMetaForeignKey(iForeignKey); 
    for (int iCheckConstraint = 0; iCheckConstraint < getMetaCheckConstraints(); iCheckConstraint++)
      ams[getMetaColumns() + iPrimaryKeys + getMetaCandidateKeys() + getMetaForeignKeys() + iCheckConstraint] = (MetaSearch)
        getMetaCheckConstraint(iCheckConstraint); 
    for (int iTrigger = 0; iTrigger < getMetaTriggers(); iTrigger++)
      ams[getMetaColumns() + iPrimaryKeys + getMetaCandidateKeys() + getMetaForeignKeys() + getMetaCheckConstraints() + iTrigger] = (MetaSearch)
        getMetaTrigger(iTrigger); 
    return ams;
  }




  
  public String[] getSearchElements(DU du) throws IOException {
    return new String[] {
        
        getName(), 
        String.valueOf(getRows()), 
        getDescription()
      };
  }







  
  public String toString() {
    return getName();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\meta\MetaTableImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */