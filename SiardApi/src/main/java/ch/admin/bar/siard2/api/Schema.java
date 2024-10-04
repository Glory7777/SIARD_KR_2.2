package ch.admin.bar.siard2.api;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface Schema {

    Archive getParentArchive();

    MetaSchema getMetaSchema();

    boolean isValid();

    boolean isEmpty();

    int getTables();

    Table getTable(int paramInt);

    Table getTable(String paramString);

    Table createTable(String paramString) throws IOException;

    void replaceWithSelectedTables(List<String> selectedTableNameList);

    long getRecordCount();

    Collection<Table> getSelectedTables();

    long getSchemaSize();

    void addTableSize(long tableSize);

}
