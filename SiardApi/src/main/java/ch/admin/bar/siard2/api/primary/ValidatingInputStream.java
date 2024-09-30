package ch.admin.bar.siard2.api.primary;
import ch.enterag.utils.BU;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import org.w3c.dom.Element;

public class ValidatingInputStream extends InputStream {
  private InputStream _is = null;
  private MessageDigest _md = null;
  private byte[] _bufDigest = null;
  private long _lLength = -1L;
  private long _lRead = 0L;
  
  private boolean _bValidated = false;
  
  private void initialize(Element el, InputStream is, boolean bValidateLength) {
    if (is == null) {
      this._is = new ByteArrayInputStream(BU.fromHex(el.getTextContent()));
    } else {
      
      this._is = is;
      if (bValidateLength) {
        
        String sLength = el.getAttribute("length");
        if (sLength != null && sLength.length() > 0)
          this._lLength = Long.parseLong(sLength); 
      } 
      String sAlgorithm = el.getAttribute("digestType");
      if (sAlgorithm != null && sAlgorithm.length() > 0) {
        
        String sMessageDigest = el.getAttribute("digest");
        if (sMessageDigest != null && sMessageDigest.length() > 0) {
          
          this._bufDigest = BU.fromHex(sMessageDigest);
          
          try {
            this._md = MessageDigest.getInstance(sAlgorithm);
            this._is = new DigestInputStream(is, this._md);
          }
          catch (NoSuchAlgorithmException noSuchAlgorithmException) {}
        } 
      } 
    } 
  }






  
  ValidatingInputStream(Element el, InputStream is, boolean bValidateLength) {
    initialize(el, is, bValidateLength);
  }






  
  public ValidatingInputStream(Element el, InputStream is) {
    initialize(el, is, true);
  }


  
  private void validateAtEof() throws IOException {
    if (!this._bValidated) {
      
      this._bValidated = true;
      if (this._lLength >= 0L) {
        
        if (this._lLength != this._lRead)
          throw new IOException("Unexpected length " + this._lRead + " instead of " + this._lLength + " detected!");
        if (this._md != null)
        {
          if (!Arrays.equals(this._bufDigest, this._md.digest())) {
            throw new IOException("Message digest did not match!");
          }
        }
      } 
    } 
  }


  
  public int read() throws IOException {
    int iRead = this._is.read();
    if (iRead != -1) {
      this._lRead++;
    } else {
      validateAtEof();
    }  return iRead;
  }



  
  public int read(byte[] buf) throws IOException {
    int iRead = this._is.read(buf);
    if (iRead != -1) {
      this._lRead += iRead;
    } else {
      validateAtEof();
    }  return iRead;
  }



  
  public int read(byte[] buf, int iOffset, int iLength) throws IOException {
    int iRead = 0;
    if (iLength > 0) {
      
      iRead = this._is.read(buf, iOffset, iLength);
      if (iRead != -1) {
        this._lRead += iRead;
      } else {
        validateAtEof();
      } 
    }  return iRead;
  }



  
  public long skip(long lSkip) throws IOException {
    long lSkipped = this._is.skip(lSkip);
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
    this._is.close();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\primary\ValidatingInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */