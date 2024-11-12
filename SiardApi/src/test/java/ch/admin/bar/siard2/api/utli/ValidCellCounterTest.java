package ch.admin.bar.siard2.api.utli;

import ch.admin.bar.siard2.api.Record;
import ch.admin.bar.siard2.api.Schema;
import ch.admin.bar.siard2.api.Table;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidCellCounterTest {

    ValidCellCounter validCellCounter = new ValidCellCounter();

    @Mock
    Schema schema;

    @Mock
    Table table;

    @Mock
    Record record;

    @Test
    void addCount() {
        when(record.getParentTable()).thenReturn(table);
        when(table.getParentSchema()).thenReturn(schema);
        assertDoesNotThrow(() -> validCellCounter.addCountAndUpdateTable(record));
    }

    @Test
    void getCount(){
        when(record.getParentTable()).thenReturn(table);
        when(table.getParentSchema()).thenReturn(schema);
        assertDoesNotThrow(() -> validCellCounter.addCountAndUpdateTable(record));
        long count = assertDoesNotThrow(() -> validCellCounter.getCount(record));
        assertEquals(count, 1);
    }

    @Test
    void getDefaultCount() {
        when(record.getParentTable()).thenReturn(table);
        when(table.getParentSchema()).thenReturn(schema);
        long count = assertDoesNotThrow(() -> validCellCounter.getCount(record));
        assertEquals(count, 0);
    }
}