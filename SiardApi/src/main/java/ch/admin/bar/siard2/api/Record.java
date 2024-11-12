package ch.admin.bar.siard2.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Record {
    Table getParentTable();

    long getRecord();

    int getCells() throws IOException;

    Cell getCell(int paramInt) throws IOException;

    List<Value> getValues(boolean paramBoolean1, boolean paramBoolean2) throws IOException;

    boolean containsInCell(final String searchTerm);
}