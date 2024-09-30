package ch.admin.bar.siard2.api.primary;

import ch.admin.bar.siard2.api.Cell;
import ch.admin.bar.siard2.api.Field;
import ch.admin.bar.siard2.api.MetaColumn;
import ch.admin.bar.siard2.api.MetaField;
import ch.admin.bar.siard2.api.MetaValue;
import ch.admin.bar.siard2.api.Record;
import ch.admin.bar.siard2.api.meta.MetaColumnImpl;
import java.io.IOException;
import org.w3c.dom.Element;








public class CellImpl
  extends ValueImpl
  implements Cell
{
  private Record _record = null;
  
  public Record getParentRecord() {
    return this._record;
  }
  private MetaColumn _mc = null;
  
  public MetaColumn getMetaColumn() {
    return this._mc;
  }









  
  private CellImpl(Record record, int iIndex, MetaColumn mc, Element elCell) throws IOException {
    this._record = record;
    this._mc = mc;
    RecordImpl ri = (RecordImpl)record;
    initialize(ri.getRecord(), ri.getTemporaryLobFolder(), iIndex, elCell, mc);
  }










  
  public static Cell newInstance(Record record, int iIndex, MetaColumn mc, Element elCell) throws IOException {
    return new CellImpl(record, iIndex, mc, elCell);
  }





  
  protected Field createField(int iField, MetaField mf, Element el) throws IOException {
    if (el != null) {
      
      int iCardinality = mf.getCardinality();
      if (iCardinality > 0)
        extendArray(iField, iCardinality); 
    } 
    return FieldImpl.newInstance(iField, this, this, mf, el);
  }




  
  public Cell getAncestorCell() {
    return this;
  }





  
  protected String getInternalLobFolder() throws IOException {
    return ((MetaColumnImpl)getMetaColumn()).getFolder();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\primary\CellImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */