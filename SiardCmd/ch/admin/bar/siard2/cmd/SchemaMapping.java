package ch.admin.bar.siard2.cmd;
import ch.admin.bar.siard2.api.MetaSchema;
import ch.admin.bar.siard2.api.MetaTable;
import ch.admin.bar.siard2.api.MetaType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchemaMapping extends Mapping {
  private String _sMappedSchemaName = null;
  public String getMappedSchemaName() { return this._sMappedSchemaName; } public void setMappedSchemaName(String sMappedSchemaName) {
    this._sMappedSchemaName = sMappedSchemaName;
  } private Map<String, TableMapping> _mapTables = new HashMap<>();
  public TableMapping getTableMapping(String sTableName) { return this._mapTables.get(sTableName); } String getMappedTableName(String sTableName) {
    return getTableMapping(sTableName).getMappedTableName();
  } private Map<String, TypeMapping> _mapTypes = new HashMap<>();
  TypeMapping getTypeMapping(String sTypeName) { return this._mapTypes.get(sTypeName); } String getMappedTypeName(String sTypeName) {
    return getTypeMapping(sTypeName).getMappedTypeName();
  }



  
  private SchemaMapping(boolean bSupportsArrays, boolean bSupportsUdts, String sMappedSchemaName, MetaSchema ms, int iMaxTableNameLength, int iMaxColumnNameLength) throws IOException, IOException {
    this._sMappedSchemaName = sMappedSchemaName;
    List<String> listTypes = new ArrayList<>();
    for (int iType = 0; iType < ms.getMetaTypes(); iType++)
      listTypes.add(ms.getMetaType(iType).getName()); 
    Map<String, String> mapTypes = getDisambiguated(listTypes, iMaxTableNameLength);
    for (int i = 0; i < ms.getMetaTypes(); i++) {
      
      MetaType mt = ms.getMetaType(i);
      this._mapTypes.put(mt.getName(), 
          TypeMapping.newInstance(mapTypes.get(mt.getName()), mt, iMaxColumnNameLength));
    } 
    List<String> listTables = new ArrayList<>();
    for (int iTable = 0; iTable < ms.getMetaTables(); iTable++)
      listTables.add(ms.getMetaTable(iTable).getName()); 
    Map<String, String> mapTables = getDisambiguated(listTables, iMaxTableNameLength);
    for (int j = 0; j < ms.getMetaTables(); j++) {
      
      MetaTable mt = ms.getMetaTable(j);
      this._mapTables.put(mt.getName(), 
          TableMapping.newInstance(bSupportsArrays, bSupportsUdts, mapTables
            .get(mt.getName()), mt, iMaxColumnNameLength));
    } 
  }




  
  public static SchemaMapping newInstance(boolean bSupportsArrays, boolean bSupportsUdts, String sMappedSchemaName, MetaSchema ms, int iMaxTableNameLength, int iMaxColumnNameLength) throws IOException {
    return new SchemaMapping(bSupportsArrays, bSupportsUdts, sMappedSchemaName, ms, iMaxTableNameLength, iMaxColumnNameLength);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardcmd.jar!\ch\admin\bar\siard2\cmd\SchemaMapping.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */