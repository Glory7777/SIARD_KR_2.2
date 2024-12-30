package ch.admin.bar.siard2.cmd;

import com.tmax.tibero.jdbc.TbClobBase;
import com.tmax.tibero.jdbc.TbClobReader;

import java.io.Reader;
import java.sql.SQLException;

public class TbClobBaseWrapper extends TbClobBase {

    private final TbClobBase delegate;

    public TbClobBaseWrapper(TbClobBase delegate) {
        super(delegate.getConnection(), delegate.getLocator(), delegate.freeTmpLobOnEOF());
        this.delegate = delegate;
    }

    @Override
    public Reader getCharacterStream() throws SQLException {
        this.checkInvalidActionOnEmpty();
        TbClobReader tbClobReader = new TbClobReader(delegate, 1L, Long.MAX_VALUE);
        return new TbClobReaderWrapper(delegate, 1L, Long.MAX_VALUE, tbClobReader);
    }

}
