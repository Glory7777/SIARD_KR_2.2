package com.tmax.tibero.jdbc.ext;

import com.tmax.tibero.jdbc.TbPreparedStatement;
import com.tmax.tibero.jdbc.TbStatement;
import com.tmax.tibero.jdbc.data.RsetType;

import java.sql.SQLException;

public class TbStatementCache {
    public static final int ENTRY_TYPE_TBSTATEMENT = 0;

    public static final int ENTRY_TYPE_TBPREPAREDSTATEMENT = 1;

    public static final int ENTRY_TYPE_TBCALLABLESTATEMENT = 2;

    private TbStatementCacheEntry cacheEntryHead = null;

    private TbStatementCacheEntry cacheEntryTail;

    private int cacheSize;

    private int numEntry;

    public TbStatementCache(int paramInt) {
        this.cacheSize = paramInt;
        this.numEntry = 0;
    }

    public boolean add(TbStatement paramTbStatement, int paramInt) throws SQLException {
        if (!paramTbStatement.isPoolable() || contains(paramTbStatement))
            return false;
        if (paramTbStatement instanceof TbPreparedStatement && ((TbPreparedStatement) paramTbStatement).getPPID() == null)
            return false;
        if (this.numEntry == this.cacheSize)
            purgeLastEntry();
        paramTbStatement.resetForCache();
        TbStatementCacheEntry tbStatementCacheEntry = new TbStatementCacheEntry(paramInt);
        tbStatementCacheEntry.stmt = paramTbStatement;
        tbStatementCacheEntry.next = this.cacheEntryHead;
        if (this.cacheEntryHead != null)
            this.cacheEntryHead.prev = tbStatementCacheEntry;
        this.cacheEntryHead = tbStatementCacheEntry;
        if (this.cacheEntryTail == null)
            this.cacheEntryTail = tbStatementCacheEntry;
        this.numEntry++;
        return true;
    }

    public void clear() {
        for (TbStatementCacheEntry tbStatementCacheEntry = this.cacheEntryHead; tbStatementCacheEntry != null; tbStatementCacheEntry = tbStatementCacheEntry.next) {
            try {
                tbStatementCacheEntry.clear();
            } catch (SQLException sQLException) {
            }
        }
        this.cacheEntryHead = null;
        this.numEntry = 0;
    }

    private void purgeLastEntry() throws SQLException {
        TbStatementCacheEntry tbStatementCacheEntry = this.cacheEntryTail;
        tbStatementCacheEntry.prev.next = tbStatementCacheEntry.next;
        tbStatementCacheEntry.clear();
        this.cacheEntryTail = tbStatementCacheEntry.prev;
        this.numEntry--;
    }

    private TbStatementCacheEntry find(String paramString, int paramInt, RsetType paramRsetType) {
        for (TbStatementCacheEntry tbStatementCacheEntry = this.cacheEntryHead; tbStatementCacheEntry != null; tbStatementCacheEntry = tbStatementCacheEntry.next) {
            String str = tbStatementCacheEntry.stmt.getOriginalSql();
            try {
                if (tbStatementCacheEntry.stmtType == paramInt && paramRsetType != null && tbStatementCacheEntry.stmt.getResultSetType() == paramRsetType.getType() && tbStatementCacheEntry.stmt.getResultSetConcurrency() == paramRsetType.getConcurrency() && tbStatementCacheEntry.stmt.getResultSetHoldability() == paramRsetType.getHoldability() && (paramString == null || paramString.equals(str)))
                    return tbStatementCacheEntry;
            } catch (SQLException sQLException) {
            }
        }
        return null;
    }

    public boolean contains(String paramString, int paramInt, RsetType paramRsetType) {
        return (find(paramString, paramInt, paramRsetType) != null);
    }

    public boolean contains(TbStatement paramTbStatement) {
        String str;
        int bool;
        if (paramTbStatement instanceof com.tmax.tibero.jdbc.TbCallableStatement) {
            str = paramTbStatement.getOriginalSql();
            bool = 1;
        } else if (paramTbStatement instanceof TbPreparedStatement) {
            str = paramTbStatement.getOriginalSql();
            bool = 1;
        } else {
            str = null;
            bool = 0;
        }
        try {
            RsetType rsetType = RsetType.getRsetType(paramTbStatement.getResultSetType(), paramTbStatement.getResultSetConcurrency());
            return contains(str, bool, rsetType);
        } catch (SQLException sQLException) {
            return false;
        }
    }

    public TbStatement get(String paramString, int paramInt, RsetType paramRsetType) throws SQLException {
        TbStatementCacheEntry tbStatementCacheEntry = find(paramString, paramInt, paramRsetType);
        if (tbStatementCacheEntry != null) {
            if (tbStatementCacheEntry.prev != null)
                tbStatementCacheEntry.prev.next = tbStatementCacheEntry.next;
            if (tbStatementCacheEntry.next != null)
                tbStatementCacheEntry.next.prev = tbStatementCacheEntry.prev;
            if (tbStatementCacheEntry == this.cacheEntryHead)
                this.cacheEntryHead = tbStatementCacheEntry.next;
            if (tbStatementCacheEntry == this.cacheEntryTail)
                this.cacheEntryTail = tbStatementCacheEntry.prev;
            if (tbStatementCacheEntry.stmt instanceof TbPreparedStatement)
                ((TbPreparedStatement) tbStatementCacheEntry.stmt).clearParameters();
            this.numEntry--;
            return tbStatementCacheEntry.stmt;
        }
        return null;
    }
}