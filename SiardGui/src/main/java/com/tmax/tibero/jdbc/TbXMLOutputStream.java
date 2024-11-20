package com.tmax.tibero.jdbc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class TbXMLOutputStream extends ByteArrayOutputStream {
  public ByteArrayInputStream getInputStream() {
    return new ByteArrayInputStream(this.buf, 0, this.count);
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\TbXMLOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */