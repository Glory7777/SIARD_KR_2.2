package ch.admin.bar.siard2.cmd;
import ch.admin.bar.siard2.api.MetaColumn;
import ch.admin.bar.siard2.api.MetaTable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableMapping extends Mapping {
  private String _sMappedTableName = null;
  public String getMappedTableName() { return this._sMappedTableName; } public void setMappedTableName(String sMappedTableName) {
    this._sMappedTableName = sMappedTableName;
  } private Map<String, String> _mapColumns = new HashMap<>();
  public String getMappedColumnName(String sColumnName) {
    return this._mapColumns.get(sColumnName);
  } private Map<String, String> _mapExtendedColumns = new HashMap<>();
  String getMappedExtendedColumnName(String sExtendedColumnName) {
    return this._mapExtendedColumns.get(sExtendedColumnName);
  } public void putMappedExtendedColumnName(String sExtendedColumnName, String sMappedColumnName) {
    this._mapExtendedColumns.put(sExtendedColumnName, sMappedColumnName);
  }


  
  private TableMapping(boolean bSupportsArrays, boolean bSupportsUdts, String sMappedTableName, MetaTable mt, int iMaxColumnNameLength) throws IOException {
    this._sMappedTableName = sMappedTableName;
    List<String> listColumns = new ArrayList<>();
    for (int iColumn = 0; iColumn < mt.getMetaColumns(); iColumn++) {
      
      MetaColumn mc = mt.getMetaColumn(iColumn);
      listColumns.add(mc.getName());
    } 
    this._mapColumns = getDisambiguated(listColumns, iMaxColumnNameLength);
    List<List<String>> llColumnNames = mt.getColumnNames(bSupportsArrays, bSupportsUdts);
    List<String> listExtendedColumnNames = new ArrayList<>();
    for (int i = 0; i < llColumnNames.size(); i++) {
      
      List<String> listColumn = llColumnNames.get(i);
      StringBuilder sbColumn = new StringBuilder();
      for (int j = 0; j < listColumn.size(); j++) {
        
        if (j > 0)
          sbColumn.append("."); 
        sbColumn.append(listColumn.get(j));
      } 
      listExtendedColumnNames.add(sbColumn.toString());
    } 
    this._mapExtendedColumns = getDisambiguated(listExtendedColumnNames, iMaxColumnNameLength);
  }



  
  public static TableMapping newInstance(boolean bSupportsArrays, boolean bSupportsUdts, String sMappedTableName, MetaTable mt, int iMaxColumnNameLength) throws IOException {
    return new TableMapping(bSupportsArrays, bSupportsUdts, sMappedTableName, mt, iMaxColumnNameLength);
  }
}