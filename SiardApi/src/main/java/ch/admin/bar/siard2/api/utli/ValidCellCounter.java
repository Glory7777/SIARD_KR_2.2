package ch.admin.bar.siard2.api.utli;

import ch.admin.bar.siard2.api.Record;
import ch.admin.bar.siard2.api.Schema;
import ch.admin.bar.siard2.api.Table;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ValidCellCounter {

    private final Map<CellTuple<Schema, Table, Record>, Long> cellCounter = new HashMap<>();

    public void addCountAndUpdateTable(Record record) {
        CellTuple<Schema, Table, Record> key = keyFrom(record);
        cellCounter.merge(key, 1L, Long::sum);
        updateTableMatchedCount(record);
    }

    public long getCount(Record record) {
        CellTuple<Schema, Table, Record> key = keyFrom(record);
        return cellCounter.getOrDefault(key, 0L);
    }

    private static CellTuple<Schema, Table, Record> keyFrom(Record record) {
        Table table = record.getParentTable();
        Schema schema = table.getParentSchema();
        return CellTuple.of(schema, table, record);
    }

    public long getTotalSum() {
        return cellCounter.values().stream().mapToLong(x -> x).sum();
    }

    public boolean hasValue() {
        return !cellCounter.isEmpty();
    }

    public void setToDefault() {
        cellCounter.clear();
    }

    private void updateTableMatchedCount(Record record) {
        record.getParentTable().updateMatchedRows();
    }
}
