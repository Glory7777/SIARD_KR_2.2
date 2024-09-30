package ch.enterag.utils.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

























public class DiskFile
  extends RandomAccessFile
{
  private static final String sMODE_READ = "r";
  private static final String sMODE_READ_WRITE = "rw";
  private static final int iBUFFER_SIZE = 4096;
  private String m_sFileName = null;

  
  private boolean m_bReadOnly = false;


  
  public String getFileName() {
    return this.m_sFileName;
  } public boolean isReadOnly() {
    return this.m_bReadOnly;
  }














  
  public DiskFile(String sFileName, boolean bReadOnly) throws FileNotFoundException {
    super(sFileName, bReadOnly ? "r" : "rw");
    File file = new File(sFileName);
    this.m_sFileName = file.getAbsolutePath();
    this.m_bReadOnly = bReadOnly;
  }












  
  public DiskFile(File file, boolean bReadOnly) throws FileNotFoundException {
    super(file, bReadOnly ? "r" : "rw");
    this.m_sFileName = file.getAbsolutePath();
    this.m_bReadOnly = bReadOnly;
  }








  
  public DiskFile(String sFileName) throws FileNotFoundException {
    super(sFileName, "rw");
    File file = new File(sFileName);
    this.m_sFileName = file.getAbsolutePath();
  }









  
  public DiskFile(File file) throws FileNotFoundException {
    super(file, "rw");
    this.m_sFileName = file.getAbsolutePath();
  }












  
  public byte[] digest(String sAlgorithm, long lStart, long lEnd) throws IOException {
    if (!sAlgorithm.equals("MD5") && !sAlgorithm.equals("SHA-1")) {
      throw new IllegalArgumentException("Digest algorithm must be MD5 or SHA-1!");
    }
    long lFilePointer = getFilePointer();
    byte[] bufDigest = null;
    
    try {
      MessageDigest md = MessageDigest.getInstance(sAlgorithm);
      byte[] buf = new byte[4096];
      int iRead = 0; long lPosition;
      for (lPosition = lStart; lPosition < lEnd; lPosition += iRead) {
        
        seek(lPosition);
        int iLength = buf.length;
        if (lEnd - lPosition < iLength)
          iLength = (int)(lEnd - lPosition); 
        iRead = read(buf, 0, iLength);
        if (iRead != iLength)
          throw new IOException("Could not read " + String.valueOf(iLength) + " bytes at position " + String.valueOf(lPosition)); 
        md.update(buf, 0, iRead);
      } 
      bufDigest = md.digest();
    } catch (NoSuchAlgorithmException nsae) {
      System.err.println(nsae.getClass().getName() + ": " + nsae.getMessage());
    }  seek(lFilePointer);
    return bufDigest;
  }










  
  public long move(long lSource, long lDestination) throws IOException {
    long lMoved = 0L;
    byte[] buf = new byte[4096];
    if (lSource < lDestination) {

      
      setLength(length() + lDestination - lSource);
      
      int iLength = buf.length;
      long lPos = length() - iLength;
      if (lPos < lSource) {
        
        iLength -= (int)(lSource - lPos);
        lPos = lSource;
      } 
      seek(lPos); while (iLength > 0) {
        
        seek(lPos);
        lMoved += iLength;
        readFully(buf, 0, iLength);
        seek(lPos + lDestination - lSource);
        write(buf, 0, iLength);
        lPos -= iLength;
        if (lPos < lSource)
        {
          iLength -= (int)(lSource - lPos);
          lPos = lSource;
        }
      
      } 
    } else if (lDestination < lSource) {

      
      long lLength = length();
      int iLength = buf.length;
      long lPos = lSource;
      if (lPos + iLength > lLength)
        iLength = (int)(lLength - lPos); 
      while (iLength > 0) {
        
        seek(lPos);
        lMoved += iLength;
        readFully(buf, 0, iLength);
        seek(lPos - lSource + lDestination);
        write(buf, 0, iLength);
        lPos += iLength;
        if (lPos + iLength > lLength)
          iLength = (int)(lLength - lPos); 
      } 
      seek(lLength - lSource + lDestination);
    } 
    setLength(getFilePointer());
    return lMoved;
  }











  
  public long lastIndexOf(byte[] bufPattern) throws IOException {
    long lLastIndex = -1L;
    int iBufferSize = 2 * bufPattern.length;
    if (iBufferSize < 4096)
      iBufferSize = 4096; 
    byte[] buf = new byte[iBufferSize];
    long lFilePointer = getFilePointer();
    if (lFilePointer >= bufPattern.length) {
      
      long lPos = lFilePointer;
      int iStart = 0;
      int iEnd = 0;
      int iReadSize = iBufferSize / 2;
      if (lPos < iReadSize) {
        
        iStart = iReadSize - (int)lPos;
        iReadSize = (int)lPos;
      } 
      lPos -= iReadSize;
      seek(lPos);
      readFully(buf, iStart, iReadSize);
      while (lLastIndex < 0L && iReadSize > 0) {

        
        System.arraycopy(buf, iStart, buf, iBufferSize / 2, iReadSize);
        iEnd = iBufferSize / 2 + iReadSize;
        
        if (lPos < iReadSize) {
          
          iStart = iBufferSize / 2 - (int)lPos;
          iReadSize = (int)lPos;
        } 
        
        lPos -= iReadSize;
        seek(lPos);
        readFully(buf, iStart, iReadSize);
        
        for (int iPos = iEnd - bufPattern.length; iPos >= iStart && lLastIndex < 0L; iPos--) {
          
          boolean bMatch = true;
          for (int i = 0; bMatch && i < bufPattern.length; i++)
            bMatch = (buf[iPos + i] == bufPattern[i]); 
          if (bMatch)
            lLastIndex = lPos + iPos - iStart; 
        } 
      } 
      seek(lFilePointer);
    } 
    return lLastIndex;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\utils\io\DiskFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */