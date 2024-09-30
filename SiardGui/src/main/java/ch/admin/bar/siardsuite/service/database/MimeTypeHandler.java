package ch.admin.bar.siardsuite.service.database;

import ch.admin.bar.siard2.api.Cell;
import ch.admin.bar.siard2.api.Field;
import ch.admin.bar.siard2.api.MetaValue;
import ch.admin.bar.siard2.api.Value;
import org.apache.tika.Tika;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.*;

public class MimeTypeHandler {

    private final Map<String, Set<String>> mimeTypes = new HashMap();
    private final Tika tika;

    public MimeTypeHandler(Tika tika) {
        this.tika = tika;
    }

    public void add(Value value, Blob blob) throws SQLException, IOException {
        if (value instanceof Cell) {
            this.add((Cell)value, blob);
        }

        if (value instanceof Field) {
            this.add((Field)value, blob);
        }

    }

    public void add(Value value, byte[] bytes) throws IOException {
        if (value instanceof Cell) {
            this.add((Cell)value, bytes);
        }

        if (value instanceof Field) {
            this.add((Field)value, bytes);
        }

    }

    public void add(Value value, Clob clob) throws SQLException, IOException {
        if (value instanceof Cell) {
            this.add((Cell)value, clob);
        }

        if (value instanceof Field) {
            this.add((Field)value, clob);
        }

    }

    public void applyMimeType(Value value) throws IOException {
        if (value instanceof Cell) {
            this.applyMimeType((Cell)value);
        }

        if (value instanceof Field) {
            this.applyMimeType((Field)value);
        }

    }

    private void add(Cell cell, byte[] bytes) {
        this.add(cell, this.tika.detect(bytes));
    }

    private void add(Field field, byte[] bytes) {
        this.add(field, this.tika.detect(bytes));
    }

    private void add(Cell cell, Clob clob) throws SQLException, IOException {
        this.add(cell, this.tika.detect(clob.getAsciiStream()));
    }

    private void add(Field field, Clob clob) throws SQLException, IOException {
        this.add(field, this.tika.detect(clob.getAsciiStream()));
    }

    private void add(Cell cell, Blob blob) throws SQLException, IOException {
        this.add(cell, this.tika.detect(blob.getBinaryStream()));
    }

    private void add(Field field, Blob blob) throws SQLException, IOException {
        this.add(field, this.tika.detect(blob.getBinaryStream()));
    }

    private void add(Cell cell, String mimeType) {
        this.add(mimeType, cell.getMetaColumn().getName());
    }

    private void add(Field field, String mimeType) {
        this.add(mimeType, field.getMetaField().getName());
    }

    private void add(String mimeType, String name) {
        if (!this.mimeTypes.containsKey(name)) {
            this.mimeTypes.put(name, new HashSet(Collections.singletonList(mimeType)));
        } else {
            this.mimeTypes.get(name).add(mimeType);
        }

    }

    private void applyMimeType(Cell cell) throws IOException {
        this.applyMimeType(cell.getMetaColumn());
    }

    private void applyMimeType(Field field) throws IOException {
        this.applyMimeType(field.getMetaField());
    }

    private void applyMimeType(MetaValue metaValue) throws IOException {
        Set<String> types = this.mimeTypes.get(metaValue.getName());
        if (types != null) {
            if (types.size() == 1) {
                metaValue.setMimeType((String)types.toArray()[0]);
            }

            if (types.size() != 1) {
                metaValue.setMimeType("");
            }

        }
    }
}
