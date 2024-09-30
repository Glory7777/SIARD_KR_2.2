package ch.admin.bar.siard2.cmd;
import ch.admin.bar.siard2.api.MetaData;
import ch.admin.bar.siard2.api.MetaSchema;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ArchiveMapping {
  private final Map<String, SchemaMapping> _mapSchemas = new HashMap<>();
  public SchemaMapping getSchemaMapping(String sSchemaName) { return this._mapSchemas.get(sSchemaName); } public String getMappedSchemaName(String sSchemaName) {
    return getSchemaMapping(sSchemaName).getMappedSchemaName();
  }



  
  private ArchiveMapping(boolean bSupportsArrays, boolean bSupportsUdts, Map<String, String> mapSchemas, MetaData md, int iMaxTableNameLength, int iMaxColumnNameLength) throws IOException {
    for (int iSchema = 0; iSchema < md.getMetaSchemas(); iSchema++) {
      
      MetaSchema ms = md.getMetaSchema(iSchema);
      String sMappedSchemaName = mapSchemas.get(ms.getName());
      if (sMappedSchemaName == null)
        sMappedSchemaName = ms.getName(); 
      this._mapSchemas.put(ms.getName(), 
          SchemaMapping.newInstance(bSupportsArrays, bSupportsUdts, sMappedSchemaName, ms, iMaxTableNameLength, iMaxColumnNameLength));
    } 
  }





  
  public static ArchiveMapping newInstance(boolean bSupportsArrays, boolean bSupportsUdts, Map<String, String> mapSchemas, MetaData md, int iMaxTableNameLength, int iMaxColumnNameLength) throws IOException {
    return new ArchiveMapping(bSupportsArrays, bSupportsUdts, mapSchemas, md, iMaxTableNameLength, iMaxColumnNameLength);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardcmd.jar!\ch\admin\bar\siard2\cmd\ArchiveMapping.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */