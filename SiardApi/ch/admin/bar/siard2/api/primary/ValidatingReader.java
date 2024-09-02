package ch.admin.bar.siard2.api.primary;
import ch.enterag.utils.xml.XU;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.w3c.dom.Element;

public class ValidatingReader extends Reader {
  private Reader _rdr = null;
  private long _lLength = -1L;
  private long _lRead = 0L;
  
  private boolean _bValidated = false;
  
  public ValidatingReader(Element el, InputStream is) {
    if (is == null) {
      this._rdr = new StringReader(XU.fromXml(el));
    } else {

      
      try {
        String sLength = el.getAttribute("length");
        if (sLength != null)
          this._lLength = Long.parseLong(sLength); 
        this._rdr = new InputStreamReader(new ValidatingInputStream(el, is, false), "UTF-8");
      
      }
      catch (UnsupportedEncodingException unsupportedEncodingException) {}
    } 
  }


  
  private void validateAtEof() throws IOException {
    if (!this._bValidated) {
      
      this._bValidated = true;
      if (this._lLength >= 0L)
      {
        if (this._lLength != this._lRead) {
          throw new IOException("Unexpected length " + String.valueOf(this._lRead) + " instead of " + String.valueOf(this._lLength) + " detected!");
        }
      }
    } 
  }


  
  public int read() throws IOException {
    int iRead = this._rdr.read();
    if (iRead != -1) {
      this._lRead++;
    } else {
      validateAtEof();
    }  return iRead;
  }



  
  public int read(char[] cbuf) throws IOException {
    int iRead = this._rdr.read(cbuf);
    if (iRead != -1) {
      this._lRead += iRead;
    } else {
      validateAtEof();
    }  return iRead;
  }



  
  public int read(char[] cbuf, int iOffset, int iLength) throws IOException {
    int iRead = 0;
    if (iLength > 0) {
      
      iRead = this._rdr.read(cbuf, iOffset, iLength);
      if (iRead != -1) {
        this._lRead += iRead;
      } else {
        validateAtEof();
      } 
    }  return iRead;
  }



  
  public long skip(long lSkip) throws IOException {
    long lSkipped = this._rdr.skip(lSkip);
    this._lRead += lSkipped;
    return lSkipped;
  }


  
  public void close() throws IOException {
    if (!this._bValidated) {
      
      if (this._lLength > this._lRead)
        skip(this._lLength - this._lRead); 
      while (!this._bValidated)
        read(); 
    } 
    this._rdr.close();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\primary\ValidatingReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */