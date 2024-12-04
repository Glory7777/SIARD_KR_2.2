package ch.admin.bar.siard2.cmd.utils.tibero;

import ch.enterag.utils.jdbc.BaseConnection;

import java.sql.Connection;

public class TiberoConnectionAdapter extends BaseConnection {

    public TiberoConnectionAdapter(Connection connWrapped) {
        super(connWrapped);
    }

}
