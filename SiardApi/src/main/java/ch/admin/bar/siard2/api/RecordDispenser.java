package ch.admin.bar.siard2.api;

import java.io.IOException;

public interface RecordDispenser {
    Record get() throws IOException;

    Record getWithSearchTerm(final String searchTerm) throws IOException;

    void skip(long paramLong) throws IOException;

    void close() throws IOException;

    long getPosition();

    long getByteCount();

    boolean anyMatches();
}