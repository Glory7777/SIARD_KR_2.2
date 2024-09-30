package ch.admin.bar.siard2.api.primary;

import ch.enterag.utils.BU;
import ch.enterag.utils.xml.XU;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.w3c.dom.Element;

public class ValidatingOutputStream extends OutputStream {
  private OutputStream _os;
  private long _lWritten = 0L;
  private MessageDigest _md;
  
  public ValidatingOutputStream(Element el, OutputStream os) {
    XU.clearElement(el);
    this._el = el;
    if (os == null) {
      this._os = new ByteArrayOutputStream();
    } else {
      
      this._os = os;
      
      try {
        this._md = MessageDigest.getInstance(ArchiveImpl._dttDEFAULT_DIGEST_ALGORITHM.value());
        this._os = new DigestOutputStream(os, this._md);
      }
      catch (NoSuchAlgorithmException noSuchAlgorithmException) {}
    } 
  }

  
  private final Element _el;
  
  public void write(int b) throws IOException {
    this._os.write(b);
    this._lWritten++;
  }



  
  public void write(byte[] buf) throws IOException {
    this._os.write(buf);
    this._lWritten += buf.length;
  }



  
  public void write(byte[] buf, int iOffset, int iLength) throws IOException {
    this._os.write(buf, iOffset, iLength);
    this._lWritten += iLength;
  }



  
  public void close() throws IOException {
    this._os.close();
    if (this._os instanceof ByteArrayOutputStream baos) {

        this._el.setTextContent(BU.toHex(baos.toByteArray()));
    }
    else {
      
      this._el.setAttribute("length", String.valueOf(this._lWritten));
      if (this._md != null) {
        
        this._el.setAttribute("digestType", this._md.getAlgorithm());
        this._el.setAttribute("digest", BU.toHex(this._md.digest()));
      } 
    } 
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\primary\ValidatingOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */