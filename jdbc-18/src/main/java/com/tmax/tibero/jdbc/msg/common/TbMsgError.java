package com.tmax.tibero.jdbc.msg.common;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.err.TbError;

import java.sql.SQLException;

public abstract class TbMsgError extends TbMsg {
    public boolean haveError = true;

    private SQLException prevException;

    private SQLException rootException;

    protected void readErrorStackInfo(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
        int i = paramTbStreamDataReader.readInt32();
        if (i == 0) {
            this.haveError = false;
            paramTbStreamDataReader.moveReadOffset(4);
            return;
        }
        paramTbStreamDataReader.moveReadOffset(4);
        paramTbStreamDataReader.moveReadOffset(4);
        int j = paramTbStreamDataReader.readInt32();
        if (j <= 0) {
            this.haveError = false;
            return;
        }
        paramTbStreamDataReader.moveReadOffset(4);
        for (byte b = 0; b < j; b++) {
            paramTbStreamDataReader.moveReadOffset(8);
            paramTbStreamDataReader.moveReadOffset(4);
            int k = paramTbStreamDataReader.readInt32();
            paramTbStreamDataReader.moveReadOffset(4);
            paramTbStreamDataReader.moveReadOffset(4);
            paramTbStreamDataReader.moveReadOffset(1);
            String str1 = paramTbStreamDataReader.readDBDecodedString(6).trim();
            if (null != str1) {
                int i1 = str1.indexOf(0);
                if (i1 > 0)
                    str1 = str1.substring(0, i1);
            }
            String str2 = paramTbStreamDataReader.readDBDecodedString(712).trim();
            if (null != str2) {
                int i1 = str2.indexOf(0);
                if (i1 > 0)
                    str2 = str2.substring(0, i1);
            }
            paramTbStreamDataReader.moveReadOffset(1);
            paramTbStreamDataReader.moveReadOffset(4);
            int m = paramTbStreamDataReader.readInt32();
            int n = paramTbStreamDataReader.readInt32();
            paramTbStreamDataReader.moveReadOffset(4);
            paramTbStreamDataReader.moveReadOffset(12);
            paramTbStreamDataReader.moveReadOffset(80);
            paramTbStreamDataReader.moveReadOffset(4);
            paramTbStreamDataReader.moveReadOffset(80);
            SQLException sQLException = TbError.newSQLException(str2, str1, k);
            if (this.prevException == null) {
                this.rootException = sQLException;
            } else {
                this.prevException.setNextException(sQLException);
            }
            this.prevException = sQLException;
        }
    }

    public SQLException getException(int paramInt) {
        return !this.haveError ? TbError.newSQLException(paramInt) : this.rootException;
    }

    public void changeRootException(SQLException paramSQLException) {
        paramSQLException.setNextException(this.rootException.getNextException());
        this.rootException = paramSQLException;
    }
}