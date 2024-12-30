package ch.admin.bar.siard2.cmd;

import com.tmax.tibero.jdbc.TbClobBase;
import com.tmax.tibero.jdbc.TbClobReader;
import com.tmax.tibero.jdbc.TbLob;
import com.tmax.tibero.jdbc.err.TbError;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.CharBuffer;
import java.sql.SQLException;

public class TbClobReaderWrapper extends TbClobReader {

    private TbClobBase clob = null;
    private int bufOffset = 0;
    private long lobOffset = 0L;
    private int fetchedSize = 0;
    private long totalSize = 0L;
    private long totalFetchedSize = 0L;
    private char[] buf = null;
    private boolean opened = false;
    private final TbClobReader delegate;

    public TbClobReaderWrapper(TbClobBase var1, long var2, long var4, TbClobReader delegate) throws SQLException {
        super(var1, var2, var4);
        if (var1 == null) {
            throw TbError.newSQLException(-590767);
        } else {
            this.clob = var1;
            this.bufOffset = 0;
            this.lobOffset = var2 - 1L;
            this.fetchedSize = 0;
            this.totalSize = var4;
            this.buf = new char[TbLob.getMaxChunkSize()];
            this.opened = true;
            this.delegate = delegate;
        }
    }

    private void checkClosed() throws IOException {
        if (!this.opened) {
            throw new IOException(TbError.getMsg(-90900));
        }
    }

    private int getRemainedInBuffer() {
        return this.fetchedSize - this.bufOffset;
    }

    private void readNextBuffer() throws SQLException {
        long var1 = 0L;
        long var3 = this.totalSize - this.totalFetchedSize;
        if ((long)this.buf.length < var3) {
            var1 = this.clob.getChars(this.lobOffset + 1L, this.buf);
        } else {
            var1 = this.clob.getChars(this.lobOffset + 1L, this.buf, 0L, var3);
            this.clob.setEndOfStream(true);
        }

        this.totalFetchedSize += var1;
        this.fetchedSize = (int)var1;
        this.lobOffset += var1;
        this.bufOffset = 0;
    }

    @Override
    public int read(@NotNull CharBuffer target) throws IOException {
        return super.read(target);
    }

    @Override
    public int read(char[] chars, int i, int i1) throws IOException {
        if (chars == null) {
            throw new NullPointerException();
        } else if (i >= 0 && i <= chars.length && i1 >= 0 && i + i1 <= chars.length && i + i1 >= 0) {
            if (i1 == 0) {
                return 0;
            } else {
                this.checkClosed();
                if (this.getRemainedInBuffer() == 0) {
                    if (this.clob.isEndOfStream()) {
                        if (this.clob.getIsTempLob() && this.clob.freeTmpLobOnEOF()) {
                            try {
                                this.clob.free();
                            } catch (SQLException var7) {
                            }

                            return -1;
                        }

                        return -1;
                    }

                    try {
                        this.readNextBuffer();
                    } catch (SQLException var9) {
                        throw new IOException(var9.getMessage());
                    }
                }

                if (this.getRemainedInBuffer() >= i1) {
                    System.arraycopy(this.buf, this.bufOffset, chars, i, i1);
                    this.bufOffset += i1;
                    return i1;
                } else {
                    int var4 = 0;

                    while(i1 > var4) {
                        int var5 = this.getRemainedInBuffer();
                        if (var5 > i1 - var4) {
                            var5 = i1 - var4;
                        }

                        System.arraycopy(this.buf, this.bufOffset, chars, i + var4, var5);
                        var4 += var5;
                        this.bufOffset += var5;
                        if (this.getRemainedInBuffer() == 0) {
                            if (this.clob.isEndOfStream()) {
                                return var4;
                            }

                            try {
                                this.readNextBuffer();
                            } catch (SQLException var8) {
                                throw new IOException(var8.getMessage());
                            }
                        }
                    }

                    return var4;
                }
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }
}