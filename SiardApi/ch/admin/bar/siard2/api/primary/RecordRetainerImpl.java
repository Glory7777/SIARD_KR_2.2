package ch.admin.bar.siard2.api.primary;

import ch.admin.bar.siard2.api.Record;
import ch.admin.bar.siard2.api.RecordRetainer;
import ch.admin.bar.siard2.api.Table;
import ch.admin.bar.siard2.api.generated.table.RecordType;
import ch.enterag.utils.FU;
import ch.enterag.utils.jaxb.XMLStreamFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;







public class RecordRetainerImpl
  implements RecordRetainer
{
  private static final int iBUFFER_SIZE = 8192;
  private Table _table = null;
  private CountingOutputStream _osXml = null;
  private XMLStreamWriter _xsw = null;
  private long _lRecord = -1L;
  private File _fileTemporaryLobFolder = null; private URI getTemporaryLobFolder() {
    return FU.toUri(this._fileTemporaryLobFolder);
  }
  
  private class CountingOutputStream
    extends OutputStream
  {
    private OutputStream _os = null;
    private long _lCount = 0L;
    
    public CountingOutputStream(OutputStream os) {
      this._os = os;
    }


    
    public void write(int b) throws IOException {
      this._os.write(b);
      this._lCount++;
    }


    
    public void write(byte[] buf) throws IOException {
      this._os.write(buf);
      this._lCount += buf.length;
    }


    
    public void write(byte[] buf, int iOffset, int iLength) throws IOException {
      this._os.write(buf, iOffset, iLength);
      this._lCount += iLength;
    }


    
    public void close() throws IOException {
      this._os.close();
    }
    
    public long getByteCount() {
      return this._lCount;
    }
  }






  
  private ArchiveImpl getArchiveImpl() {
    return (ArchiveImpl)this._table.getParentSchema().getParentArchive();
  }








  
  static XMLStreamWriter writeHeader(OutputStream osXml, Table table) throws IOException {
    XMLStreamWriter xsw = null;
    
    try {
      xsw = XMLStreamFactory.createStreamWriter(osXml);
      xsw.setDefaultNamespace("http://www.bar.admin.ch/xmlns/siard/2/table.xsd");
      xsw.setPrefix("xsi", "http://www.w3.org/2001/XMLSchema-instance");
      xsw.writeStartDocument("UTF-8", "1.0");
      xsw.writeCharacters("\n");
      
      xsw.writeStartElement("table");
      xsw.writeNamespace(null, "http://www.bar.admin.ch/xmlns/siard/2/table.xsd");
      xsw.writeAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
      xsw.writeAttribute("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation", "http://www.bar.admin.ch/xmlns/siard/2/table.xsd " + table
          .getMetaTable().getFolder() + ".xsd");
      xsw.writeAttribute("version", "2.2");
    } catch (XMLStreamException xse) {
      throw new IOException("Start of document could not be written!", xse);
    }  return xsw;
  }








  
  public RecordRetainerImpl(Table table) throws IOException {
    TableImpl ti = (TableImpl)table;
    ti.setCreating(true);
    this._table = table;
    
    if (getArchiveImpl().canModifyPrimaryData()) {

      
      if (!getArchiveImpl().existsFolderEntry(ti.getTableFolder())) {
        getArchiveImpl().createFolderEntry(ti.getTableFolder());
      }
      this._fileTemporaryLobFolder = File.createTempFile("siard" + ti.getTableFolder()
          .substring(0, ti.getTableFolder().length() - 1).replace("/", "_") + "_", "");
      this._fileTemporaryLobFolder.delete();
      this._fileTemporaryLobFolder.mkdir();
      this._fileTemporaryLobFolder.deleteOnExit();
      
      this._lRecord = 0L;
      this._osXml = new CountingOutputStream(getArchiveImpl().createFileEntry(ti.getTableXml()));
      this._xsw = writeHeader(this._osXml, this._table);
    } else {
      
      throw new IOException("Table cannot be opened for writing!");
    } 
  }







  
  private static void putRowElement(Element el, XMLStreamWriter xsw) throws XMLStreamException {
    xsw.writeStartElement(el.getLocalName());
    int i;
    for (i = 0; i < el.getAttributes().getLength(); i++) {
      
      Attr attr = (Attr)el.getAttributes().item(i);
      xsw.writeAttribute(attr.getName(), attr.getValue());
    } 
    for (i = 0; i < el.getChildNodes().getLength(); i++) {
      
      Node node = el.getChildNodes().item(i);
      if (node.getNodeType() == 1) {
        putRowElement((Element)node, xsw);
      } else if (node.getNodeType() == 3) {
        
        xsw.writeCharacters(node.getTextContent());
      } 
    } 

    
    xsw.writeEndElement();
  }









  
  private static void putRecordType(RecordType rt, XMLStreamWriter xsw) throws XMLStreamException {
    xsw.writeStartElement("row");
    for (int i = 0; i < rt.getAny().size(); i++)
      putRowElement(rt.getAny().get(i), xsw); 
    xsw.writeEndElement();
  }









  
  static void writeRecord(Record record, XMLStreamWriter xsw) throws IOException, XMLStreamException {
    putRecordType(((RecordImpl)record).getRecordType(), xsw);
  }






  
  public void put(Record record) throws IOException {
    try {
      writeRecord(record, this._xsw);
      this._lRecord++;
    } catch (XMLStreamException xse) {
      
      throw new IOException("Error writing record " + String.valueOf(this._lRecord) + "!", xse);
    } 
  }









  
  private void copyLobFiles(File fileTempLobFolder, String sInternalFolder) throws IOException {
    byte[] buffer = new byte[8192];
    if (fileTempLobFolder.exists()) {
      
      File[] afile = fileTempLobFolder.listFiles();
      if (afile != null)
      {
        for (int i = 0; i < afile.length; i++) {
          
          File file = afile[i];
          if (file.isDirectory()) {
            
            String sInternalSubFolder = sInternalFolder + file.getName() + "/";
            copyLobFiles(file, sInternalSubFolder);
          
          }
          else {
            
            if (!getArchiveImpl().existsFolderEntry(sInternalFolder)) {
              getArchiveImpl().createFolderEntry(sInternalFolder);
            }
            String sInternalFile = sInternalFolder + file.getName();
            OutputStream osLob = getArchiveImpl().createFileEntry(sInternalFile);
            InputStream isLob = new FileInputStream(file); int iRead;
            for (iRead = isLob.read(buffer); iRead != -1; iRead = isLob.read(buffer))
              osLob.write(buffer, 0, iRead); 
            isLob.close();
            osLob.close();
            file.delete();
          } 
        } 
      }
      fileTempLobFolder.delete();
    } 
  }








  
  static void writeFooter(XMLStreamWriter xsw) throws IOException {
    try {
      xsw.writeCharacters("\n");
      xsw.writeEndDocument();
    } catch (XMLStreamException xse) {
      throw new IOException("End of document could not be written!", xse);
    } 
  }





  
  public void close() throws IOException {
    
    try { if (this._xsw != null) {
        
        TableImpl ti = (TableImpl)this._table;
        writeFooter(this._xsw);
        this._xsw.close();
        this._osXml.close();
        this._xsw = null;
        this._table.getMetaTable().setRows(this._lRecord);
        
        OutputStream osXsd = getArchiveImpl().createFileEntry(ti.getTableXsd());
        this._table.exportTableSchema(osXsd);
        osXsd.close();
        
        copyLobFiles(FU.fromUri(getTemporaryLobFolder()), ti.getTableFolder());
        FU.deleteFiles(FU.fromUri(getTemporaryLobFolder()));
      } else {
        
        throw new IOException("Table records have not been created!");
      }  }
    catch (XMLStreamException xse) { throw new IOException("XMLStreamWriter could not be closed!", xse); }
     ((TableImpl)this._table).setCreating(false);
    this._lRecord = -1L;
  }





  
  public Record create() throws IOException {
    Record record = RecordImpl.newInstance(this._table, getPosition(), getTemporaryLobFolder());
    return record;
  }




  
  public long getPosition() {
    return this._lRecord;
  }




  
  public long getByteCount() {
    return this._osXml.getByteCount();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\primary\RecordRetainerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */