package ch.enterag.utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

public class BuildProperties
  extends Properties
{
  private static final long serialVersionUID = 1L;
  
  private void readProperties() throws IOException {
    Reader rdr = new FileReader("build.properties");
    load(rdr);
    rdr.close();
  }


  
  public BuildProperties() throws IOException {
    readProperties();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\utils\BuildProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */