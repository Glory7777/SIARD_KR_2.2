package com.tmax.tibero.jdbc.data;

import java.util.ArrayList;

public class BatchUpdateInfo {
  private ArrayList<BatchInfo> batchInfos = new ArrayList<BatchInfo>();
  
  private ArrayList<Integer> deferredRowIndices = new ArrayList<Integer>();
  
  public void add(BatchInfo paramBatchInfo) {
    this.batchInfos.add(paramBatchInfo);
    if (paramBatchInfo.getBindData().getDFRParameterCnt() > 0)
      this.deferredRowIndices.add(paramBatchInfo.getCurrentRowIndex());
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


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\BatchUpdateInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */