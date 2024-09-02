package ch.admin.bar.siard2.api.primary;

import ch.admin.bar.siard2.api.Cell;
import ch.admin.bar.siard2.api.MetaValue;
import ch.admin.bar.siard2.api.Record;
import ch.admin.bar.siard2.api.Table;
import ch.admin.bar.siard2.api.Value;
import ch.enterag.utils.background.Progress;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.text.Collator;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;


public class SortedTableImpl
  implements SortedTable
{
  private TableImpl _ti = null;
  private RecordDispenserImpl _rdi = null;
  private File _fileSorted = null;
  private boolean _bAscending = true;
  private Progress _progress = null;
  private long _lWritten = -1L;
  private long _lWrites = -1L;
  private long _lWritesPercent = -1L;





  
  private void incWritten() {
    this._lWritten++;
    if (this._progress != null && this._lWritten % this._lWritesPercent == 0L) {
      
      int iPercent = (int)(100L * this._lWritten / this._lWrites);
      this._progress.notifyProgress(iPercent);
    } 
  }





  
  private boolean cancelRequested() {
    boolean bCancelRequested = false;
    if (this._progress != null)
      bCancelRequested = this._progress.cancelRequested(); 
    return bCancelRequested;
  }


  
  public boolean getAscending() {
    return this._bAscending;
  }
  private int _iSortColumn = -1;

  
  public int getSortColumn() {
    return this._iSortColumn;
  }




  
  public InputStream open() throws IOException {
    return new FileInputStream(this._fileSorted);
  }









  
  private int compareStrings(String sLeft, String sRight) {
    return Collator.getInstance().compare(sLeft, sRight);
  }







  
  private int compareBytes(byte[] bufLeft, byte[] bufRight) {
    int iCompare = 0;
    int i = 0;
    while (iCompare == 0 && i < bufLeft.length && i < bufRight.length) {
      
      int iLeft = bufLeft[i];
      if (iLeft < 0)
        iLeft += 256; 
      int iRight = bufRight[i];
      if (iRight < 0)
        iRight += 256; 
      if (iLeft < iRight) {
        iCompare = -1; continue;
      }  if (iLeft > iRight) {
        iCompare = 1; continue;
      } 
      i++;
    } 
    if (iCompare == 0 && (i < bufLeft.length || i < bufRight.length))
    {
      if (i < bufRight.length) {
        iCompare = -1;
      } else {
        iCompare = 1;
      }  } 
    return iCompare;
  }










  
  private int compareInputStreams(InputStream isLeft, InputStream isRight) throws IOException {
    int iCompare = 0;
    int iReadLeft = isLeft.read();
    int iReadRight = isRight.read();
    while (iCompare == 0 && iReadLeft != -1 && iReadRight != -1) {
      
      iCompare = compareBytes(new byte[] { (byte)iReadLeft }, new byte[] { (byte)iReadRight });
      iReadLeft = isLeft.read();
      iReadRight = isRight.read();
    } 
    if (iCompare == 0 && (iReadLeft != -1 || iReadRight != -1))
    {
      if (iReadLeft == -1) {
        iCompare = -1;
      } else {
        iCompare = 1;
      }  } 
    isLeft.close();
    isRight.close();
    return iCompare;
  }











  
  private int compareReaders(Reader rdrLeft, Reader rdrRight) throws IOException {
    int iCompare = 0;
    int iReadLeft = rdrLeft.read();
    int iReadRight = rdrRight.read();
    while (iCompare == 0 && iReadLeft != -1 && iReadRight != -1) {
      
      iCompare = compareStrings(String.valueOf((char)iReadLeft), String.valueOf((char)iReadRight));
      iReadLeft = rdrLeft.read();
      iReadRight = rdrRight.read();
    } 
    if (iCompare == 0 && (iReadLeft != -1 || iReadRight != -1))
    {
      if (iReadLeft == -1) {
        iCompare = -1;
      } else {
        iCompare = 1;
      }  } 
    rdrLeft.close();
    rdrRight.close();
    return iCompare;
  }










  
  private int compare(Value valueLeft, Value valueRight) throws IOException {
    int iCompare = 0;
    MetaValue mv = valueLeft.getMetaValue();
    if (!valueLeft.isNull()) {
      
      if (!valueRight.isNull()) {
        
        if (mv.getCardinality() < 0) {
          
          int iAttribute, iType = mv.getPreType();
          switch (iType) {
            
            case -15:
            case -9:
            case 1:
            case 12:
              iCompare = compareStrings(valueLeft.getString(), valueRight.getString());
              break;
            case 2005:
            case 2009:
            case 2011:
              iCompare = compareReaders(valueLeft.getReader(), valueRight.getReader());
              break;
            case -3:
            case -2:
              iCompare = compareBytes(valueLeft.getBytes(), valueRight.getBytes());
              break;
            case 70:
            case 2004:
              iCompare = compareInputStreams(valueLeft.getInputStream(), valueRight.getInputStream());
              break;
            case 16:
              iCompare = valueLeft.getBoolean().compareTo(valueRight.getBoolean());
              break;
            case 4:
            case 5:
              iCompare = valueLeft.getLong().compareTo(valueRight.getLong());
              break;
            case -5:
              iCompare = valueLeft.getBigInteger().compareTo(valueRight.getBigInteger());
              break;
            case 2:
            case 3:
              iCompare = valueLeft.getBigDecimal().compareTo(valueRight.getBigDecimal());
              break;
            case 6:
            case 7:
            case 8:
              iCompare = valueLeft.getDouble().compareTo(valueRight.getDouble());
              break;
            case 91:
              iCompare = valueLeft.getDate().compareTo(valueRight.getDate());
              break;
            case 92:
              iCompare = valueLeft.getTime().compareTo(valueRight.getTime());
              break;
            case 93:
              iCompare = valueLeft.getTimestamp().compareTo(valueRight.getTimestamp());
              break;
            case 1111:
              iCompare = valueLeft.getDuration().compare(valueRight.getDuration());
              break;
            
            case 0:
              for (iAttribute = 0; iCompare == 0 && iAttribute < valueLeft.getAttributes(); iAttribute++) {
                iCompare = compare((Value)valueLeft.getAttribute(iAttribute), (Value)valueRight.getAttribute(iAttribute));
              }
              break;
          } 

        
        } else {
          for (int iElement = 0; iCompare == 0 && iElement < valueLeft.getElements(); iElement++) {
            iCompare = compare((Value)valueLeft.getElement(iElement), (Value)valueRight.getElement(iElement));
          }
        } 
      } else {
        iCompare = 1;
      }
    
    }
    else if (!valueRight.isNull()) {
      iCompare = -1;
    } 
    return iCompare;
  }










  
  private boolean lessEqual(Record recordLeft, Record recordRight) throws IOException {
    boolean bLessEqual = true;
    Cell cellLeft = recordLeft.getCell(this._iSortColumn);
    Cell cellRight = recordRight.getCell(this._iSortColumn);
    int iCompare = compare((Value)cellLeft, (Value)cellRight);
    if (this._bAscending) {
      bLessEqual = (iCompare <= 0);
    } else {
      bLessEqual = (iCompare >= 0);
    }  return bLessEqual;
  }











  
  private void merge(XMLStreamReader xsrLeft, XMLStreamReader xsrRight, XMLStreamWriter xsw) throws IOException, XMLStreamException {
    Record recordLeft = null;
    Record recordRight = null;
    if (xsrLeft.isStartElement())
      recordLeft = this._rdi.readRecord(xsrLeft); 
    if (xsrRight.isStartElement())
      recordRight = this._rdi.readRecord(xsrRight); 
    while (recordLeft != null && recordRight != null && !cancelRequested()) {
      
      if (lessEqual(recordLeft, recordRight)) {
        
        RecordRetainerImpl.writeRecord(recordLeft, xsw);
        if (xsrLeft.isStartElement()) {
          recordLeft = this._rdi.readRecord(xsrLeft);
        } else {
          recordLeft = null;
        } 
      } else {
        
        RecordRetainerImpl.writeRecord(recordRight, xsw);
        if (xsrRight.isStartElement()) {
          recordRight = this._rdi.readRecord(xsrRight);
        } else {
          recordRight = null;
        } 
      }  incWritten();
    } 
    while (recordLeft != null && !cancelRequested()) {
      
      RecordRetainerImpl.writeRecord(recordLeft, xsw);
      incWritten();
      if (xsrLeft.isStartElement()) {
        recordLeft = this._rdi.readRecord(xsrLeft); continue;
      } 
      recordLeft = null;
    } 
    while (recordRight != null && !cancelRequested()) {
      
      RecordRetainerImpl.writeRecord(recordRight, xsw);
      incWritten();
      if (xsrRight.isStartElement()) {
        recordRight = this._rdi.readRecord(xsrRight); continue;
      } 
      recordRight = null;
    } 
  }











  
  private void sort(XMLStreamReader xsr, XMLStreamWriter xsw, long lRecords) throws IOException, XMLStreamException {
    if (lRecords > 1L) {
      
      long lRecordsLeft = lRecords / 2L;
      File fileLeft = File.createTempFile("sort", ".xml");
      OutputStream osLeft = new FileOutputStream(fileLeft);
      XMLStreamWriter xswLeft = RecordRetainerImpl.writeHeader(osLeft, this._ti);
      sort(xsr, xswLeft, lRecordsLeft);
      RecordRetainerImpl.writeFooter(xswLeft);
      xswLeft.close();
      osLeft.close();
      
      long lRecordsRight = lRecords - lRecordsLeft;
      File fileRight = File.createTempFile("sort", ".xml");
      OutputStream osRight = new FileOutputStream(fileRight);
      XMLStreamWriter xswRight = RecordRetainerImpl.writeHeader(osRight, this._ti);
      sort(xsr, xswRight, lRecordsRight);
      RecordRetainerImpl.writeFooter(xswRight);
      xswRight.close();
      osRight.close();
      
      InputStream isLeft = new FileInputStream(fileLeft);
      XMLStreamReader xsrLeft = this._rdi.readHeader(null, isLeft);
      InputStream isRight = new FileInputStream(fileRight);
      XMLStreamReader xsrRight = this._rdi.readHeader(null, isRight);
      merge(xsrLeft, xsrRight, xsw);
      xsrLeft.close();
      isLeft.close();
      fileLeft.delete();
      xsrRight.close();
      isRight.close();
      fileRight.delete();
    }
    else {
      
      RecordRetainerImpl.writeRecord(this._rdi.readRecord(xsr), xsw);
      incWritten();
    } 
  }





  
  public void sort(Table table, boolean bAscending, int iSortColumn, Progress progress) throws IOException {
    if (this._fileSorted == null || bAscending != this._bAscending || iSortColumn != this._iSortColumn) {


      
      this._progress = progress;
      this._ti = (TableImpl)table;
      long lRecords = table.getMetaTable().getRows();
      if (lRecords > 0L) {

        
        this._lWrites = lRecords;
        int iLog2 = 0;
        while (lRecords > 1L) {
          
          lRecords = (lRecords + 1L) / 2L;
          iLog2++;
        } 
        this._lWrites = iLog2 * this._lWrites;
        this._lWritesPercent = (this._lWrites + 99L) / 100L;
        this._lWritten = 0L;
        lRecords = table.getMetaTable().getRows();
        this._bAscending = bAscending;
        this._iSortColumn = iSortColumn;
        
        try {
          File fileOutput = File.createTempFile("tab", ".xml");
          OutputStream osXml = new FileOutputStream(fileOutput);
          XMLStreamWriter xsw = RecordRetainerImpl.writeHeader(osXml, this._ti);
          
          this._rdi = (RecordDispenserImpl)table.openRecords();
          InputStream isXml = this._rdi.getXmlInputStream();
          XMLStreamReader xsr = this._rdi.getXmlStreamReader();
          
          sort(xsr, xsw, lRecords);
          
          xsr.close();
          isXml.close();
          
          RecordRetainerImpl.writeFooter(xsw);
          xsw.close();
          osXml.close();

          
          if (cancelRequested()) {
            throw new IOException("Table sort cancelled!");
          }
          if (this._fileSorted != null)
            this._fileSorted.delete(); 
          this._fileSorted = fileOutput;
          this._fileSorted.deleteOnExit();
        } catch (XMLStreamException xse) {
          throw new IOException("Table could not be sorted!", xse);
        } 
      } else {
        throw new IllegalArgumentException("Cannot sort 0 records!");
      } 
    }  this._progress = null;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\primary\SortedTableImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */