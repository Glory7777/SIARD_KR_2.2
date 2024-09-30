package ch.admin.bar.siard2.api.primary;
import ch.enterag.utils.xml.XU;

import java.io.*;
import java.nio.charset.StandardCharsets;

import org.w3c.dom.Element;

public class ValidatingWriter extends Writer {
  private Element _el = null;
  private Writer _wr = null;
  private long _lWritten = 0L;

  
  public ValidatingWriter(Element el, OutputStream os) {
    XU.clearElement(el);
    this._el = el;
    if (os == null) {
      this._wr = new StringWriter();
    } else {


        this._wr = new OutputStreamWriter(new ValidatingOutputStream(el, os), StandardCharsets.UTF_8);

    }
  }



  
  public void write(int iChar) throws IOException {
    this._wr.write(iChar);
    this._lWritten++;
  }



  
  public void write(char[] cbuf) throws IOException {
    this._wr.write(cbuf);
    this._lWritten += cbuf.length;
  }



  
  public void write(char[] cbuf, int iOffset, int iLength) throws IOException {
    this._wr.write(cbuf, iOffset, iLength);
    this._lWritten += iLength;
  }



  
  public void write(String s) throws IOException {
    this._wr.write(s);
    this._lWritten += s.length();
  }



  
  public void write(String s, int iOffset, int iLength) throws IOException {
    this._wr.write(s, iOffset, iLength);
    this._lWritten += iLength;
  }


  
  public void flush() throws IOException {
    this._wr.flush();
  }


  
  public void close() throws IOException {
    this._wr.close();
    if (this._wr instanceof StringWriter swr) {

        XU.toXml(swr.toString(), this._el);
    } 
    this._el.setAttribute("length", String.valueOf(this._lWritten));
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\primary\ValidatingWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */