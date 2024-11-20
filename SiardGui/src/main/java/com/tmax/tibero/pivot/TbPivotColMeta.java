package com.tmax.tibero.pivot;

public class TbPivotColMeta implements Comparable {
    int oldIdx;

    String name;

    int type;

    public TbPivotColMeta(int paramInt1, String paramString, int paramInt2) {
        this.oldIdx = paramInt1;
        this.name = paramString;
        this.type = paramInt2;
    }

    public int compareTo(Object paramObject) {
        return this.name.compareTo(((TbPivotColMeta) paramObject).name);
    }

    public int getOldIdx() {
        return this.oldIdx;
    }

    public String getName() {
        return this.name;
    }

    public int getType() {
        return this.type;
    }
}