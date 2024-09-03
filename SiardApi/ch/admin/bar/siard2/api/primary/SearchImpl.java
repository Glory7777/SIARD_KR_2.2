package ch.admin.bar.siard2.api.primary;

import ch.admin.bar.siard2.api.Record;
import ch.admin.bar.siard2.api.*;
import ch.enterag.utils.DU;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;







public class SearchImpl
  implements Search
{
  private String _sFindString = null;
  private boolean _bMatchCase = false;
  private List<MetaColumn> _listColumns = null;
  private RecordDispenser _rd = null;
  private Record _record = null;
  private Cell _cell = null;
  private int _iFoundOffset = -1;


  
  public String getFindString() {
    return this._sFindString;
  }



  
  public long getFoundRow() {
    return this._record.getRecord();
  }




  
  public int getFoundPosition() {
    return this._cell.getMetaColumn().getPosition();
  }





  
  public String getFoundString(DU du) throws IOException {
    String s = null;
    if (!this._cell.isNull()) {
      
      Object o = this._cell.getObject();
      s = o.toString();
      if (o instanceof BigDecimal) {
        s = ((BigDecimal)o).toPlainString();
      } else if (o instanceof Date) {
        s = du.fromSqlDate((Date)o);
      } else if (o instanceof Time) {
        s = du.fromSqlTime((Time)o);
      } else if (o instanceof Timestamp) {
        s = du.fromSqlTimestamp((Timestamp)o);
      } 
    }  return s;
  }




  
  public int getFoundOffset() {
    return this._iFoundOffset;
  }






  
  public void find(List<MetaColumn> listColumns, String sFindString, boolean bMatchCase) throws IOException {
    if (listColumns.size() > 0) {
      
      Table table = ((MetaColumn)listColumns.get(0)).getParentMetaTable().getTable();
      this._listColumns = listColumns;
      this._sFindString = sFindString;
      this._bMatchCase = bMatchCase;
      if (bMatchCase) {
        this._sFindString = sFindString;
      } else {
        this._sFindString = sFindString.toLowerCase();
      }  this._rd = table.openRecords();
    } else {
      
      throw new IllegalArgumentException("List of columns must not be empty for search!");
    } 
  }







  
  private int findInCell(DU du) throws IOException {
    this._iFoundOffset++;
    String sFoundString = getFoundString(du);
    if (sFoundString != null) {
      
      if (this._bMatchCase) {
        this._iFoundOffset = sFoundString.indexOf(this._sFindString, this._iFoundOffset);
      } else {
        this._iFoundOffset = sFoundString.toLowerCase().indexOf(this._sFindString, this._iFoundOffset);
      } 
    } else {
      this._iFoundOffset = -1;
    }  return this._iFoundOffset;
  }










  
  private Cell findInRecord(int iStartCell, DU du) throws IOException {
    for (int iCell = iStartCell; this._cell == null && iCell < this._record.getCells(); iCell++) {
      
      this._cell = this._record.getCell(iCell);
      if (this._listColumns.contains(this._cell.getMetaColumn())) {
        
        this._iFoundOffset = findInCell(du);
        if (this._iFoundOffset < 0) {
          this._cell = null;
        }
      } else {
        this._cell = null;
      } 
    }  return this._cell;
  }








  
  private Cell findInTable(DU du) throws IOException {
    for (this._record = this._rd.get(); this._cell == null && this._record != null; ) {
      
      this._cell = findInRecord(0, du);
      if (this._cell == null)
        this._record = this._rd.get(); 
    } 
    if (this._record == null) {
      
      this._rd.close();
      this._sFindString = null;
    } 
    return this._cell;
  }





  
  public Cell findNext(DU du) throws IOException {
    if (this._sFindString != null) {

      
      int iStartCell = 0;
      if (this._cell != null) {
        
        iStartCell = this._cell.getMetaColumn().getPosition();
        this._iFoundOffset = findInCell(du);
        if (this._iFoundOffset < 0) {
          this._cell = null;
        }
      } 
      if (this._cell == null && this._record != null) {
        this._cell = findInRecord(iStartCell, du);
      }
      if (this._cell == null)
        this._cell = findInTable(du); 
    } 
    return this._cell;
  }




  
  public boolean canFindNext() {
    return (this._sFindString != null);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\primary\SearchImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */