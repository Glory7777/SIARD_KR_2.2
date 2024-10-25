package com.tmax.tibero.pivot;

import com.tmax.tibero.jdbc.TbBlob;
import com.tmax.tibero.jdbc.data.TbNumber;
import com.tmax.tibero.jdbc.util.TbCommon;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

public class TbPivotValReader {
  private static final int FETCH_SIZE = 65536;
  
  Blob blobData;
  
  byte[] byteData;
  
  int itemCnt;
  
  int offsetIdx;
  
  boolean hasByteData;
  
  int blobDataLen;
  
  int prevOffset = 0;
  
  int blobDataLocalEndPos;
  
  int localOffset = 0;
  
  Vector hdr;
  
  HashMap newIdxMap;
  
  private byte[] getNextRpcolData() throws Exception {
    byte b;
    int i;
    int j;
    if (this.offsetIdx >= this.itemCnt)
      throw new Exception("offset index out of bound (itemCnt: " + this.itemCnt + ", offsetIdx: " + this.offsetIdx); 
    int k = ((TbPivotValHdrItem)this.hdr.elementAt(this.offsetIdx)).getOffset();
    if (!this.hasByteData)
      this.localOffset += k - this.prevOffset; 
    if (this.localOffset <= 0 && !this.hasByteData) {
      byte[] arrayOfByte2 = this.blobData.getBytes((k + 1), 1);
      i = 0xFF & arrayOfByte2[0];
      if (0 <= i && i <= 250) {
        j = i;
        b = 1;
      } else if (i == 254) {
        byte[] arrayOfByte3 = this.blobData.getBytes((k + 2), 2);
        int m = 0xFF & arrayOfByte3[0];
        int n = 0xFF & arrayOfByte3[1];
        j = 0xFF & m << 8;
        j &= 0xFF & n;
        b = 3;
      } else {
        throw new Exception("invalid rpcol first length byte: " + i);
      } 
      if (j == 0) {
        this.offsetIdx++;
        return null;
      } 
      if (j < 0)
        throw new Exception("invalid rpcol length: " + j); 
      byte[] arrayOfByte1 = this.blobData.getBytes((k + b + 1), j);
      this.prevOffset = k;
      this.offsetIdx++;
      return arrayOfByte1;
    } 
    if (k >= this.blobDataLocalEndPos)
      getNextBlobDataChunk(k); 
    if (!this.hasByteData) {
      i = 0xFF & this.byteData[this.localOffset];
    } else {
      i = 0xFF & this.byteData[k];
    } 
    if (0 <= i && i <= 250) {
      j = i;
      b = 1;
    } else if (i == 254) {
      int m;
      int n;
      if (k + 2 >= this.blobDataLocalEndPos)
        getNextBlobDataChunk(k); 
      if (!this.hasByteData) {
        m = 0xFF & this.byteData[this.localOffset + 1];
        n = 0xFF & this.byteData[this.localOffset + 2];
      } else {
        m = 0xFF & this.byteData[k + 1];
        n = 0xFF & this.byteData[k + 2];
      } 
      j = 0xFF & m << 8;
      j &= 0xFF & n;
      b = 3;
    } else {
      throw new Exception("invalid rpcol first length byte: " + i);
    } 
    if (j == 0) {
      this.offsetIdx++;
      return null;
    } 
    if (j < 0)
      throw new Exception("invalid rpcol length: " + j); 
    byte[] arrayOfByte = new byte[j];
    while (k + b + j - 1 > this.blobDataLocalEndPos)
      getNextBlobDataChunk(k); 
    if (!this.hasByteData) {
      for (byte b1 = 0; b1 < j; b1++)
        arrayOfByte[b1] = this.byteData[this.localOffset + b + b1]; 
    } else {
      for (byte b1 = 0; b1 < j; b1++)
        arrayOfByte[b1] = this.byteData[k + b + b1]; 
    } 
    this.prevOffset = k;
    this.offsetIdx++;
    return arrayOfByte;
  }
  
  private void getNextBlobDataChunk(int paramInt) throws Exception {
    if (this.blobDataLen > paramInt + 65536 - 1) {
      this.byteData = this.blobData.getBytes(paramInt, paramInt + 65536 - 1);
      this.blobDataLocalEndPos = paramInt + 65536 - 1;
    } else {
      this.byteData = this.blobData.getBytes(paramInt, this.blobDataLen);
      this.blobDataLocalEndPos = this.blobDataLen;
    } 
    this.localOffset = 1;
  }
  
  private void getAllBlobDataChunk() throws Exception {
    this.byteData = this.blobData.getBytes(1L, this.blobDataLen);
    this.blobDataLocalEndPos = this.blobDataLen;
    this.hasByteData = true;
  }
  
  private byte[] getNextRpcolForNumber() throws Exception {
    byte[] arrayOfByte1 = getNextRpcolData();
    if (arrayOfByte1 == null)
      return null; 
    if (arrayOfByte1.length > 250)
      throw new Exception("invalid rpcol length for number: " + arrayOfByte1.length); 
    byte[] arrayOfByte2 = new byte[arrayOfByte1.length + 1];
    arrayOfByte2[0] = (byte)arrayOfByte1.length;
    for (byte b = 0; b < arrayOfByte1.length; b++)
      arrayOfByte2[b + 1] = arrayOfByte1[b]; 
    return arrayOfByte2;
  }
  
  private int getNextIntForHdr(InputStream paramInputStream) throws Exception {
    int i = 0;
    byte b1 = 24;
    for (byte b2 = 0; b2 < 4; b2++) {
      int j = paramInputStream.read();
      i += j << b1;
      b1 -= 8;
    } 
    return i;
  }
  
  private int getNextIntFrom4ByteArr(int paramInt) {
    int i = 0;
    byte b1 = 0;
    for (byte b2 = 24; b1 < 4; b2 -= 8) {
      int j = 0xFF & this.byteData[paramInt + b1];
      i += j << b2;
      b1++;
    } 
    return i;
  }
  
  private int getNextIntFrom2ByteArr(int paramInt) {
    return ((0xFF & this.byteData[paramInt]) << 8) + (0xFF & this.byteData[paramInt + 1]);
  }
  
  private int getNewIdx(int paramInt) {
    return ((Integer)this.newIdxMap.get(new Integer(paramInt))).intValue();
  }
  
  public TbPivotValReader(Blob paramBlob, int paramInt, TbPivotMeta paramTbPivotMeta) throws Exception {
    this.blobData = paramBlob;
    this.offsetIdx = 0;
    this.byteData = ((TbBlob)paramBlob).getLobData();
    this.hdr = new Vector();
    this.newIdxMap = paramTbPivotMeta.getNewIdxMap(paramInt);
    if (this.byteData == null) {
      InputStream inputStream = paramBlob.getBinaryStream();
      this.itemCnt = getNextIntForHdr(inputStream);
      for (byte b = 0; b < this.itemCnt; b++) {
        int i = getNewIdx(getNextIntForHdr(inputStream));
        int j = getNextIntForHdr(inputStream);
        this.hdr.add(new TbPivotValHdrItem(i, j));
      } 
      inputStream.close();
      this.blobDataLen = (int)paramBlob.length();
      if (this.blobDataLen < 65536) {
        getAllBlobDataChunk();
        this.hasByteData = true;
      } else {
        this.hasByteData = false;
      } 
    } else {
      byte b1 = 0;
      this.itemCnt = getNextIntFrom4ByteArr(b1);
      b1 += true;
      for (byte b2 = 0; b2 < this.itemCnt; b2++) {
        int i = getNewIdx(getNextIntFrom4ByteArr(b1));
        int j = getNextIntFrom2ByteArr(b1 + 4);
        this.hdr.add(new TbPivotValHdrItem(i, j));
        b1 += 6;
      } 
      this.blobDataLocalEndPos = (int)paramBlob.length();
      this.hasByteData = true;
    } 
    Collections.sort(this.hdr);
  }
  
  public int getColNameIdx(int paramInt) throws Exception {
    return ((TbPivotValHdrItem)this.hdr.elementAt(paramInt)).getNameIdx();
  }
  
  public int getCnt() {
    return this.itemCnt;
  }
  
  public Long getNextLong() throws Exception {
    byte[] arrayOfByte = getNextRpcolForNumber();
    return (arrayOfByte == null) ? null : new Long(TbNumber.toLong(arrayOfByte, 0, arrayOfByte.length));
  }
  
  public Integer getNextInt() throws Exception {
    byte[] arrayOfByte = getNextRpcolForNumber();
    return (arrayOfByte == null) ? null : new Integer(TbNumber.toInteger(arrayOfByte, 0, arrayOfByte.length));
  }
  
  public Double getNextDouble() throws Exception {
    byte[] arrayOfByte = getNextRpcolForNumber();
    return (arrayOfByte == null) ? null : new Double(TbNumber.toDouble(arrayOfByte, 0, arrayOfByte.length));
  }
  
  public BigDecimal getNextBigDecimal() throws Exception {
    byte[] arrayOfByte = getNextRpcolForNumber();
    return (arrayOfByte == null) ? null : TbNumber.toBigDecimal(arrayOfByte, 0, arrayOfByte.length);
  }
  
  public String getNextString() throws Exception {
    byte[] arrayOfByte = getNextRpcolData();
    return (arrayOfByte == null) ? null : new String(arrayOfByte);
  }
  
  public Date getNextDate() throws Exception {
    byte[] arrayOfByte = getNextRpcolData();
    if (arrayOfByte == null)
      return null; 
    if (arrayOfByte.length != 8)
      throw new Exception("invalid rpcol length for date: " + arrayOfByte.length); 
    int i = 0xFF & arrayOfByte[0];
    int j = 0xFF & arrayOfByte[1];
    int k = 0xFF & arrayOfByte[2];
    int m = 0xFF & arrayOfByte[3];
    int n = 0xFF & arrayOfByte[4];
    int i1 = 0xFF & arrayOfByte[5];
    int i2 = 0xFF & arrayOfByte[6];
    int i3 = (i - 100) * 100 + j - 100;
    int i4 = k - 1;
    Calendar calendar = Calendar.getInstance();
    calendar.set(i3, i4, m, n, i1, i2);
    return new Date(calendar.getTime().getTime());
  }
  
  public Timestamp getNextTimestamp() throws Exception {
    byte[] arrayOfByte = getNextRpcolData();
    if (arrayOfByte == null)
      return null; 
    if (arrayOfByte.length != 12)
      throw new Exception("invalid rpcol length for timestamp: " + arrayOfByte.length); 
    int i = 0xFF & arrayOfByte[0];
    int j = 0xFF & arrayOfByte[1];
    int k = 0xFF & arrayOfByte[2];
    int m = 0xFF & arrayOfByte[3];
    int n = 0xFF & arrayOfByte[4];
    int i1 = 0xFF & arrayOfByte[5];
    int i2 = 0xFF & arrayOfByte[6];
    int i3 = (i - 100) * 100 + j - 100;
    int i4 = k - 1;
    int i5 = TbCommon.bytes2Int(arrayOfByte, 8, 4);
    Calendar calendar = Calendar.getInstance();
    calendar.set(i3, i4, m, n, i1, i2);
    Timestamp timestamp = new Timestamp(calendar.getTime().getTime());
    timestamp.setNanos(i5);
    return timestamp;
  }
  
  public Time getNextTime() throws Exception {
    byte[] arrayOfByte = getNextRpcolData();
    if (arrayOfByte == null)
      return null; 
    if (arrayOfByte.length != 8)
      throw new Exception("invalid rpcol length for time: " + arrayOfByte.length); 
    int i = 0xFF & arrayOfByte[0];
    int j = 0xFF & arrayOfByte[1];
    int k = 0xFF & arrayOfByte[2];
    int m = TbCommon.bytes2Int(arrayOfByte, 4, 4);
    Calendar calendar = Calendar.getInstance();
    calendar.set(10, i);
    calendar.set(12, j);
    calendar.set(13, k);
    Timestamp timestamp = new Timestamp(calendar.getTime().getTime());
    timestamp.setNanos(m);
    return new Time(timestamp.getTime());
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\pivot\TbPivotValReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */