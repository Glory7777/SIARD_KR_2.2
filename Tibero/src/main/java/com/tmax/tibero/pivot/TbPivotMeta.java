package com.tmax.tibero.pivot;

import com.tmax.tibero.jdbc.TbCallableStatement;
import com.tmax.tibero.jdbc.TbStatement;
import com.tmax.tibero.jdbc.msg.TbPivotInfo;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

public class TbPivotMeta {
  TbPivotInfo[] pivotInfo;
  
  Vector pivotData;
  
  int[] startChunkIdx;
  
  int[] offset;
  
  int[] curChunkPos;
  
  HashMap colIdxMap;
  
  Vector[] metas;
  
  HashMap[] newIdxMap;
  
  int[] curIdx;
  
  public TbPivotMeta(TbStatement paramTbStatement) throws Exception {
    init(paramTbStatement.getPivotInfo(), paramTbStatement.getPivotData());
  }
  
  public TbPivotMeta(TbCallableStatement paramTbCallableStatement, int paramInt) throws Exception {
    init(paramTbCallableStatement.getPivotInfo(paramInt), paramTbCallableStatement.getPivotData(paramInt));
  }
  
  private void init(TbPivotInfo[] paramArrayOfTbPivotInfo, Vector paramVector) throws Exception {
    this.pivotInfo = paramArrayOfTbPivotInfo;
    this.pivotData = paramVector;
    if (paramArrayOfTbPivotInfo == null)
      return; 
    this.startChunkIdx = new int[paramArrayOfTbPivotInfo.length];
    this.offset = new int[paramArrayOfTbPivotInfo.length];
    this.curChunkPos = new int[paramArrayOfTbPivotInfo.length];
    this.colIdxMap = new HashMap<Object, Object>();
    int i = 0;
    byte b;
    for (b = 0; b < paramArrayOfTbPivotInfo.length; b++) {
      this.colIdxMap.put(new Integer((paramArrayOfTbPivotInfo[b]).colIdx), new Integer(b));
      this.startChunkIdx[b] = i;
      this.curChunkPos[b] = i;
      this.offset[b] = 0;
      i += (paramArrayOfTbPivotInfo[b]).chunkCnt;
    } 
    this.metas = new Vector[paramArrayOfTbPivotInfo.length];
    this.newIdxMap = new HashMap[paramArrayOfTbPivotInfo.length];
    this.curIdx = new int[paramArrayOfTbPivotInfo.length];
    for (b = 0; b < paramArrayOfTbPivotInfo.length; b++) {
      this.metas[b] = new Vector();
      this.newIdxMap[b] = new HashMap<Object, Object>();
      for (byte b1 = 0;; b1++) {
        TbPivotColMeta tbPivotColMeta;
        String str = getNextOldColName(b);
        if (str == null) {
          Collections.sort(this.metas[b]);
          for (b1 = 0; b1 < this.metas[b].size(); b1++) {
            tbPivotColMeta = this.metas[b].elementAt(b1);
            this.newIdxMap[b].put(new Integer(tbPivotColMeta.getOldIdx()), new Integer(b1));
          } 
          break;
        } 
        this.metas[b].add(new TbPivotColMeta(b1, (String)tbPivotColMeta, getOldValType(b, b1)));
      } 
      this.curIdx[b] = 0;
    } 
    paramArrayOfTbPivotInfo = null;
    paramVector = null;
    this.startChunkIdx = null;
    this.offset = null;
    this.curChunkPos = null;
  }
  
  private int getPivotIdx(int paramInt) throws Exception {
    Integer integer = (Integer)this.colIdxMap.get(new Integer(paramInt));
    if (integer != null)
      return integer.intValue(); 
    throw new Exception("invalid index for pivot column: " + paramInt);
  }
  
  private String getNextOldColName(int paramInt) throws Exception {
    if (this.curChunkPos[paramInt] == this.startChunkIdx[paramInt] + (this.pivotInfo[paramInt]).chunkCnt)
      return null; 
    byte[] arrayOfByte = this.pivotData.elementAt(this.curChunkPos[paramInt]);
    int i = this.offset[paramInt];
    int j = 0;
    if (arrayOfByte[i] <= 250) {
      j = 0xFF & arrayOfByte[i];
      i++;
      this.offset[paramInt] = this.offset[paramInt] + 1 + j;
    } else if (arrayOfByte[i] == 254) {
      j = (0xFF & arrayOfByte[i + 1]) << 8;
      j &= 0xFF & arrayOfByte[i + 2];
      i += 3;
      this.offset[paramInt] = this.offset[paramInt] + 3 + j;
    } else {
      throw new Exception("rpcol length field curruption");
    } 
    if (this.offset[paramInt] == arrayOfByte.length) {
      this.curChunkPos[paramInt] = this.curChunkPos[paramInt] + 1;
      this.offset[paramInt] = 0;
    } 
    return (j == 0) ? "" : new String(arrayOfByte, i, j);
  }
  
  private int getOldValType(int paramInt1, int paramInt2) throws Exception {
    TbPivotInfo tbPivotInfo = this.pivotInfo[paramInt1];
    if (tbPivotInfo.valType == null)
      throw new Exception("can't provide value type information"); 
    int i = tbPivotInfo.valType.length;
    return (tbPivotInfo.valType[paramInt2 % i]).type;
  }
  
  public String getNextColName(int paramInt) throws Exception {
    if (this.colIdxMap == null)
      return null; 
    int i = getPivotIdx(paramInt);
    if (this.curIdx[i] == this.metas[i].size())
      return null; 
    TbPivotColMeta tbPivotColMeta = this.metas[i].elementAt(this.curIdx[i]);
    this.curIdx[i] = this.curIdx[i] + 1;
    return tbPivotColMeta.getName();
  }
  
  public int getValType(int paramInt1, int paramInt2) throws Exception {
    int i = getPivotIdx(paramInt1);
    TbPivotColMeta tbPivotColMeta = this.metas[i].elementAt(paramInt2);
    return tbPivotColMeta.getType();
  }
  
  public HashMap getNewIdxMap(int paramInt) throws Exception {
    return this.newIdxMap[getPivotIdx(paramInt)];
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\pivot\TbPivotMeta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */