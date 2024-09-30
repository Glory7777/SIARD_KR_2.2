package ch.enterag.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;









public class FU
{
  private static int iBUFSIZ = 8192;











  
  public static long copy(File fileSource, File fileTarget) throws IOException {
    long lCopied = 0L;
    FileInputStream fis = null;
    FileOutputStream fos = null;
    
    try {
      byte[] buf = new byte[iBUFSIZ];
      fis = new FileInputStream(fileSource);
      fos = new FileOutputStream(fileTarget); int iRead;
      for (iRead = fis.read(buf); iRead != -1; iRead = fis.read(buf))
        fos.write(buf, 0, iRead); 
      fos.flush();
    }
    finally {
      
      if (fos != null) fos.close(); 
      if (fis != null) fis.close(); 
    } 
    return lCopied;
  }










  
  public static void copyFiles(File fileSource, File fileTarget, boolean bReplace) throws IOException {
    if (fileSource.isDirectory()) {
      
      if (!fileTarget.exists())
        fileTarget.mkdir(); 
      String[] as = fileSource.list();
      for (int i = 0; i < as.length; i++) {
        copyFiles(new File(fileSource, as[i]), new File(fileTarget, as[i]), bReplace);
      
      }
    }
    else if (bReplace || !fileTarget.exists()) {
      copy(fileSource, fileTarget);
    } 
  }







  
  public static boolean deleteFiles(File file) {
    if (file.isDirectory()) {
      
      File[] afile = file.listFiles();
      for (int i = 0; i < afile.length; i++)
        deleteFiles(afile[i]); 
    } 
    return file.delete();
  }








  
  public static URI toUri(File file) {
    Path path = Paths.get(file.toString(), new String[0]);
    return path.toUri();
  }








  
  public static File fromUri(URI uri) {
    Path path = Paths.get(uri);
    return path.toFile();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\utils\FU.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */