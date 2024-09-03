package ch.admin.bar.siard2.api.primary;

import ch.admin.bar.siard2.api.*;
import ch.admin.bar.siard2.api.generated.SchemaType;
import ch.admin.bar.siard2.api.generated.SchemasType;
import ch.admin.bar.siard2.api.generated.TableType;
import ch.admin.bar.siard2.api.generated.TablesType;
import ch.admin.bar.siard2.api.meta.MetaDataImpl;
import ch.admin.bar.siard2.api.meta.MetaSchemaImpl;
import ch.admin.bar.siard2.api.meta.MetaTableImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class SchemaImpl
  implements Schema
{
  public static final String _sSCHEMA_FOLDER_PREFIX = "schema";
  private Archive _archiveParent = null;
  
  public Archive getParentArchive() {
    return this._archiveParent;
  }
  private MetaSchema _ms = null;
  
  public MetaSchema getMetaSchema() {
    return this._ms;
  }
  private Map<String, Table> _mapTables = new HashMap<>();
  
  public void registerTable(String sName, Table table) {
    this._mapTables.put(sName, table);
  }



  
  public boolean isEmpty() {
    boolean bEmpty = true;
    for (Iterator<String> iterTable = this._mapTables.keySet().iterator(); iterTable.hasNext(); ) {
      
      String sTable = iterTable.next();
      Table table = this._mapTables.get(sTable);
      if (!table.isEmpty())
        bEmpty = false; 
    } 
    return bEmpty;
  }




  
  public boolean isValid() {
    boolean bValid = getMetaSchema().isValid();
    for (int iTable = 0; bValid && iTable < getTables(); iTable++) {
      
      if (!getTable(iTable).isValid())
        bValid = false; 
    } 
    return bValid;
  }





  
  String getSchemaFolder() {
    return ArchiveImpl.getContentFolder() + getMetaSchema().getFolder() + "/";
  }








  
  private SchemaImpl(Archive archiveParent, String sName) throws IOException {
    this._archiveParent = archiveParent;
    MetaDataImpl mdi = (MetaDataImpl)getParentArchive().getMetaData();
    SchemasType sts = mdi.getSiardArchive().getSchemas();
    SchemaType st = null;
    for (int iSchema = 0; st == null && iSchema < sts.getSchema().size(); iSchema++) {
      
      SchemaType stTry = sts.getSchema().get(iSchema);
      if (sName.equals(stTry.getName()))
        st = stTry; 
    } 
    ArchiveImpl ai = (ArchiveImpl)getParentArchive();
    if (st == null) {
      
      String sFolder = "schema" + String.valueOf(sts.getSchema().size());
      ai.createFolderEntry(ArchiveImpl.getContentFolder() + sFolder + "/");
      st = MetaSchemaImpl.createSchemaType(sName, sFolder);
      sts.getSchema().add(st);
    } 
    this._ms = MetaSchemaImpl.newInstance(this, st);
    ai.registerSchema(sName, this);
    
    TablesType tts = st.getTables();
    if (tts != null)
    {
      for (int iTable = 0; iTable < tts.getTable().size(); iTable++) {
        
        TableType tt = tts.getTable().get(iTable);
        TableImpl.newInstance(this, tt.getName());
      } 
    }
  }








  
  public static Schema newInstance(Archive archiveParent, String sName) throws IOException {
    return new SchemaImpl(archiveParent, sName);
  }







  
  public int getTables() {
    return getMetaSchema().getMetaTables();
  }



  
  public Table getTable(int iTable) {
    Table table = null;
    MetaTable mt = getMetaSchema().getMetaTable(iTable);
    if (mt != null)
      table = getTable(mt.getName()); 
    return table;
  }


  
  public Table getTable(String sName) {
    return this._mapTables.get(sName);
  }



  
  public Table createTable(String sName) throws IOException {
    Table table = null;
    if (getParentArchive().canModifyPrimaryData()) {
      
      if (this._mapTables.get(sName) == null) {

        
        table = TableImpl.newInstance(this, sName);
        
        MetaSchemaImpl msi = (MetaSchemaImpl)getMetaSchema();
        SchemaType stTemplate = msi.getTemplate();
        if (stTemplate != null) {
          
          TablesType tts = stTemplate.getTables();
          if (tts != null) {
            
            TableType ttTemplate = null;
            for (int iTable = 0; iTable < tts.getTable().size(); iTable++) {
              
              TableType ttTry = tts.getTable().get(iTable);
              if (sName.equals(ttTry.getName()))
                ttTemplate = ttTry; 
            } 
            if (ttTemplate != null) {
              
              MetaTableImpl mti = (MetaTableImpl)table.getMetaTable();
              mti.setTemplate(ttTemplate);
            } 
          } 
        } 
      } else {
        
        throw new IOException("Table name must be unique within schema!");
      } 
    } else {
      throw new IOException("Table cannot be created!\r\nSIARD archive is not open for modification of primary data!");
    } 
    return table;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\primary\SchemaImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */