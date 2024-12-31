package ch.admin.bar.siard2.cmd;

import com.tmax.tibero.jdbc.TbLob;
import com.tmax.tibero.jdbc.comm.TbLobAccessor;
import com.tmax.tibero.jdbc.driver.TbConnection;

import java.sql.SQLException;

public class TbLobWrapper extends TbLob {
    private static int maxChunkSize = 32000;
    private TbConnection conn = null;
    private byte[] locator = null;
    private byte[] data = null;
    private boolean endOfStream = false;
    private boolean isEmpty = false;
    private boolean isDataAppendMode = false;
    private boolean isTempLob = false;
    private boolean freeTmpLobOnEOF = false;
    private long length = -1L;
    private final TbLob delegate;

    public TbLobWrapper(TbConnection var1, byte[] var2, boolean var3, TbLob delegate) throws SQLException {
        super(var1, var2, var3);

        if (var2.length == 1) {
            this.locator = var2;
            this.isEmpty = true;
        } else if ((var2[5] & 4) == 4) {
            this.locator = new byte[96];
            System.arraycopy(var2, 0, this.locator, 0, 96);
            int var4 = var2.length - 96;
            this.data = new byte[var4];
            System.arraycopy(var2, 96, this.data, 0, var4);
            this.isDataAppendMode = true;
        } else if ((var2[4] & 4) == 4) {
            this.locator = var2;
            this.freeTmpLobOnEOF = var3;
            this.isTempLob = true;
        } else {
            this.locator = var2;
        }

        this.delegate = delegate;
    }


    @Override
    protected TbLobAccessor getLobAccessor() {
        return null;
    }

    @Override
    protected long getLengthInternal() {
        return this.length;
    }

    private int getFixedSlobLen() {
        int var1 = this.locator[0] << 8;
        var1 += this.locator[1];
        return var1;
    }
}
