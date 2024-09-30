package ch.enterag.utils.uuid;

import java.nio.ByteBuffer;
import java.util.UUID;



public class UUIDLE
{
  public static byte[] uuidToBytes(UUID uuid) {
    ByteBuffer bb = ByteBuffer.allocate(16);
    long lHigh = uuid.getMostSignificantBits();
    bb.putLong(lHigh);
    long lLow = uuid.getLeastSignificantBits();
    bb.putLong(lLow);
    byte[] buf = bb.array();
    byte[] bufOutput = new byte[16];
    int i;
    for (i = 0; i < 4; i++) {
      bufOutput[i] = buf[3 - i];
    }
    for (i = 0; i < 2; i++) {
      bufOutput[4 + i] = buf[5 - i];
    }
    for (i = 0; i < 2; i++) {
      bufOutput[6 + i] = buf[7 - i];
    }
    for (i = 8; i < 16; i++)
      bufOutput[i] = buf[i]; 
    return bufOutput;
  }


  
  public static UUID uuidFromBytes(byte[] bufInput, int iPos) {
    byte[] buf = new byte[16];
    int i;
    for (i = 0; i < 4; i++) {
      buf[i] = bufInput[iPos + 3 - i];
    }
    for (i = 0; i < 2; i++) {
      buf[4 + i] = bufInput[iPos + 4 + 1 - i];
    }
    for (i = 0; i < 2; i++) {
      buf[6 + i] = bufInput[iPos + 6 + 1 - i];
    }
    for (i = 8; i < 16; i++)
      buf[i] = bufInput[iPos + i]; 
    ByteBuffer bb = ByteBuffer.wrap(buf);
    long high = bb.getLong();
    long low = bb.getLong();
    UUID uuid = new UUID(high, low);
    return uuid;
  }

  
  public static UUID uuidFromBytes(byte[] bufInput) {
    return uuidFromBytes(bufInput, 0);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\util\\uuid\UUIDLE.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */