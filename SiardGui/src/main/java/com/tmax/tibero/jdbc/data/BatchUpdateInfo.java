package com.tmax.tibero.jdbc.data;

import java.util.ArrayList;

public class BatchUpdateInfo {
  private ArrayList<BatchInfo> batchInfos = new ArrayList<>();
  
  private ArrayList<Integer> deferredRowIndices = new ArrayList<>();
  
  public void add(BatchInfo paramBatchInfo) {
    this.batchInfos.add(paramBatchInfo);
    if (paramBatchInfo.getBindData().getDFRParameterCnt() > 0)
      this.deferredRowIndices.add(new Integer(paramBatchInfo.getCurrentRowIndex())); 
  }
  
  public void clear() {
    this.batchInfos.clear();
    this.deferredRowIndices.clear();
  }
  
  public int getDeferredRowCount() {
    return this.deferredRowIndices.size();
  }
  
  public int getDeferredRowIndex(int paramInt) {
    return ((Integer)this.deferredRowIndices.get(paramInt)).intValue();
  }
  
  public BatchInfo get(int paramInt) {
    return this.batchInfos.get(paramInt);
  }
  
  public int size() {
    return this.batchInfos.size();
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\BatchUpdateInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */