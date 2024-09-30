package ch.admin.bar.siard2.api.primary;

import ch.admin.bar.siard2.api.Record;
import ch.admin.bar.siard2.api.RecordDispenser;
import ch.admin.bar.siard2.api.RecordExtract;
import ch.admin.bar.siard2.api.Table;
import java.io.IOException;










public class RecordExtractImpl
  implements RecordExtract
{
  public static final String _sLABEL_ROWS = "rows";
  public static final String _sLABEL_ROW = "row";
  private static int _iMAX_RECORDS = 50;
  public static int getMaxRecords() { return _iMAX_RECORDS; } public static void setMaxRecords(int iMaxRecords) {
    _iMAX_RECORDS = iMaxRecords;
  }
  private Table _table = null;

  
  public Table getTable() {
    return this._table;
  }
  private long _lOffset = -1L;

  
  public long getOffset() {
    return this._lOffset;
  }
  private long _lDelta = -1L;

  
  public long getDelta() {
    return this._lDelta;
  }
  private RecordExtract _rsParent = null;

  
  public RecordExtract getParentRecordExtract() {
    return this._rsParent;
  }
  private Record _recordOffset = null;

  
  public Record getRecord() {
    return this._recordOffset;
  }
  private RecordExtract[] _ars = null;





  
  private RecordExtractImpl(Table table) {
    this._table = table;
    this._lDelta = 1L;
    for (long lDelta = this._lDelta; lDelta < table.getMetaTable().getRows(); lDelta *= _iMAX_RECORDS)
      this._lDelta = lDelta; 
    this._lOffset = 0L;
  }







  
  private RecordExtractImpl(RecordExtract rsParent, int iRecordSet, Record recordOffset) {
    this._rsParent = rsParent;
    this._recordOffset = recordOffset;
    this._table = rsParent.getTable();
    this._lOffset = rsParent.getOffset() + iRecordSet * rsParent.getDelta();
    this._lDelta = rsParent.getDelta() / _iMAX_RECORDS;
  }





  
  public static RecordExtract newInstance(Table table) {
    return new RecordExtractImpl(table);
  }






  
  public static RecordExtract newInstance(RecordExtract rsParent, int iRecordSet, Record recordOffset) {
    return new RecordExtractImpl(rsParent, iRecordSet, recordOffset);
  }





  
  private long getRecords() {
    long lRows = this._table.getMetaTable().getRows();
    long lRecords = getRecordExtracts() * this._lDelta;
    if (this._lOffset + lRecords > lRows)
      lRecords = lRows - this._lOffset; 
    return lRecords;
  }




  
  public String getLabel() {
    String sLabel = null;
    if (this._lDelta > 0L) {
      
      sLabel = "rows";
      String sRows = String.valueOf(getTable().getMetaTable().getRows());
      if (getParentRecordExtract() != null) {
        
        String sOffset = String.valueOf(this._lOffset);
        while (sOffset.length() < sRows.length())
          sOffset = "0" + sOffset; 
        sLabel = "row" + sOffset;
      } 
      sLabel = sLabel + " (" + getRecords() + ")";
    } 
    return sLabel;
  }




  
  public int getRecordExtracts() {
    int iRecordExtracts = 0;
    if (this._ars == null) {
      
      if (this._lDelta > 0L) {
        
        long lMaxRows = this._table.getMetaTable().getRows() - this._lOffset;
        iRecordExtracts = _iMAX_RECORDS;
        if (iRecordExtracts * this._lDelta > lMaxRows) {
          iRecordExtracts = (int)((lMaxRows + this._lDelta - 1L) / this._lDelta);
        }
      } 
    } else {
      iRecordExtracts = this._ars.length;
    }  return iRecordExtracts;
  }






  
  private void loadRecordExtract() throws IOException {
    this._ars = new RecordExtract[getRecordExtracts()];
    RecordDispenser rd = this._table.openRecords();
    rd.skip(this._lOffset);
    this._ars[0] = newInstance(this, 0, rd.get());
    for (int iRecordSet = 1; iRecordSet < getRecordExtracts(); iRecordSet++) {
      
      rd.skip(this._lDelta - 1L);
      this._ars[iRecordSet] = newInstance(this, iRecordSet, rd.get());
    } 
    rd.close();
  }





  
  public RecordExtract getRecordExtract(int iRecordExtract) throws IOException {
    if (this._ars == null)
      loadRecordExtract(); 
    return this._ars[iRecordExtract];
  }








  
  public String toString() {
    return getLabel();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\primary\RecordExtractImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */