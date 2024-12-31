package ch.admin.bar.siard2.cmd;

import com.tmax.tibero.jdbc.TbClob;

public class TbClobWrapper extends TbClobBaseWrapper {

    private final TbClob delegate;

    public TbClobWrapper(TbClob tbClob) {
        super(new TbClobBaseWrapper(tbClob));
        this.delegate = tbClob;
    }

}
