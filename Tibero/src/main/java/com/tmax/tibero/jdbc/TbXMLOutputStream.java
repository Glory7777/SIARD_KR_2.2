package com.tmax.tibero.jdbc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class TbXMLOutputStream extends ByteArrayOutputStream {
  public ByteArrayInputStream getInputStream() {
    return new ByteArrayInputStream(this.buf, 0, this.count);
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\TbXMLOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */