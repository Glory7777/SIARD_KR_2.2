package ch.admin.bar.siardsuite.ui.presenter.archive.browser.forms;

import ch.admin.bar.dbexception.DatabaseExceptionHandlerHelper;
import ch.admin.bar.dbexception.DbOutOfMemoryException;
import ch.admin.bar.siard2.api.Cell;
import ch.admin.bar.siard2.api.Record;
import ch.admin.bar.siard2.api.Table;
import ch.admin.bar.siard2.api.primary.TableImpl;
import ch.admin.bar.siardsuite.framework.i18n.DisplayableText;
import ch.admin.bar.siardsuite.framework.i18n.keys.I18nKey;
import ch.admin.bar.siardsuite.model.database.DatabaseColumn;
import ch.admin.bar.siardsuite.model.database.DatabaseTable;
import ch.admin.bar.siardsuite.ui.component.rendering.model.*;
import ch.admin.bar.siardsuite.ui.presenter.archive.browser.forms.utils.Converter;
import ch.admin.bar.siardsuite.ui.presenter.archive.browser.forms.utils.ListAssembler;
import ch.admin.bar.siardsuite.util.FileHelper;
import ch.admin.bar.siardsuite.util.OS;
import ch.enterag.utils.BU;
import ch.enterag.utils.mime.MimeTypes;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.tika.Tika;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class RowsOverviewForm {

    private static final I18nKey LABEL_TABLE = I18nKey.of("tableContainer.labelTable");
    private static final I18nKey LABEL_NUMBER_OF_ROWS = I18nKey.of("tableContainer.labelNumberOfRows");

    public static RenderableForm<DatabaseTable> createAndUpdateWithSearchResult(@NonNull final DatabaseTable table, String searchTerm) {
        val tableProperties = table.getColumns().stream()
                .map(column -> new TableColumnProperty<>(
                        DisplayableText.of(column.getName()),
                        row -> row.findCellValue(column.getName()),
                        createCellClickListener(column)))
                .collect(Collectors.toList());


        return RenderableForm.<DatabaseTable>builder()
                .dataSupplier(() -> table)
                .group(RenderableFormGroup.<DatabaseTable>builder()
                        .property(RenderableLazyLoadingTable.<DatabaseTable, RecordWrapper>builder()
                                .dataExtractor(databaseTable -> new RecordDataSource(table.getTable(), searchTerm))
                                .properties(tableProperties)
                                .build())
                        .property(new ReadOnlyStringProperty<>(
                                LABEL_TABLE,
                                DatabaseTable::getName))
                        .property(new ReadOnlyStringProperty<>(
                                LABEL_NUMBER_OF_ROWS,
                                Converter.longToString(t -> table.getNumberOfRows()))
                        )
                        .build())
                .build();
    }

    public static class RecordWrapper {
        @Getter
        private final Record record;
        private final Map<String, Cell> cellsByName;

        public RecordWrapper(@NonNull Record record) {
            this.record = record;

            val cells = new ListAssembler<>(
                    Converter.catchExceptions(record::getCells),
                    Converter.catchExceptions(record::getCell)
            ).assemble();

            this.cellsByName = cells.stream()
                    .collect(Collectors.toMap(cell -> cell.getMetaColumn().getName(), cell -> cell));
        }

        public Cell findCell(final String name) {
            return Optional.ofNullable(cellsByName.get(name))
                    .orElseThrow(() -> new IllegalArgumentException(
                            String.format("No cell with name %s found", name)));
        }

        private String findCellValue(final String name) {
            val cell = findCell(name);
            return extractText(cell);
        }

        private String extractText(final Cell cell) {
            if (cell == null || cell.isNull()) {
                return "";
            }
            try {
                switch (cell.getMetaValue().getPreType()) {
                    case Types.BINARY:
                    case Types.VARBINARY:
                    case Types.BLOB:
                        val bytes = cell.getBytes();

                        if (bytes.length == 0) {
                            return "";
                        }

                        if (bytes.length < 16) {
                            return "0x" + BU.toHex(cell.getBytes());
                        }

                        return "0x" + BU.toHex(cell.getBytes()).substring(0, 16) + "...";

                    default:
                        return cell.getString();
                }
            } catch (IOException e) {
                return "";
            }
        }
    }

    public static class RecordDataSource implements LazyLoadingDataSource<RecordWrapper> {
        private final Table table;
        private final String searchTerm;

        public RecordDataSource(Table table, String searchTerm) {
            this.table = table;
            this.searchTerm = searchTerm;
        }

        /**
         * ROW 데이터를 읽어들임
         * 검색인 경우 데이터가 검색 키워드를 포함하고 있으면 보여줄 화면으로 추가하고, 그렇지 않으면 생략
         */
        @SneakyThrows
        @Override
        public List<RecordWrapper> load(int startIndex, int nrOfItems) {
            try {
            resetState();

            log.info("data load ::");
            val recordDispenser = table.openRecords();
            recordDispenser.skip(startIndex);

            final List<RecordWrapper> collected = new ArrayList<>();
            for (int x = 0; x < nrOfItems; x++) {
               val record = recordDispenser.getWithSearchTerm(searchTerm);

                if (record == null) {
                    break;
                }

                if (!isSearchTermBlank()) {
                    if (recordDispenser.anyMatches()){
                        collected.add(new RecordWrapper(record));
                    }
                } else {
                    collected.add(new RecordWrapper(record));
                }
            }
            return collected;

            } catch (OutOfMemoryError e) {
                log.error("OutOfMemoryError 발생: 초기화 중 또는 데이터 로드 중 메모리 부족", e);
                DatabaseExceptionHandlerHelper.doHandleOutOfMemoryException(e);
                throw e;
            }
        }

        private void resetState() {
            try {
                // TableImpl 객체의 matchedRows 값을 0으로 설정
                if (table instanceof TableImpl tableImpl) {
                    tableImpl.setMatchedRows(0); // matchedRows 초기화
                    log.info("TableImpl.matchedRows has been reset to 0.");
                } else {
                    log.warn("Table is not an instance of TableImpl. Reset skipped.");
                }
            } catch (Exception e) {
                log.error("Failed to reset matchedRows in TableImpl.", e);
                throw new RuntimeException("Error resetting matchedRows", e);
            }
        }

        private boolean isSearchTermBlank() {
            return searchTerm == null || searchTerm.isBlank();
        }

        @Override
        public long findIndexOf(RecordWrapper item) {
            return item.getRecord().getRecord();
        }

        @Override
        public long getNumberOfItems() {
            return table.getMatchedRows();
        }
    }

    // siard open
    private static Optional<TableColumnProperty.CellClickedListener<RecordWrapper>> createCellClickListener(final DatabaseColumn column) {
        try {
            val type = column.getColumn().getPreType();
            val clickListenerSupported = type == Types.BINARY || type == Types.VARBINARY || type == Types.BLOB;

            if (!clickListenerSupported) {
                return Optional.empty();
            }
        } catch (IOException e) {
            log.error("Can not read pre-type of column {}. Message: {}", column.getName(), e.getMessage());
            return Optional.empty();
        }

        return Optional.of((property, value) -> {
            val absoluteLobFolder = column.getColumn().getAbsoluteLobFolder();
            val cell = value.findCell(column.getName());

            if (absoluteLobFolder == null) {
                Tika tika = new Tika();
                String mimeType = tika.detect(cell.getBytes());
                String extension = "." + MimeTypes.getExtension(mimeType);
                Path tempFilePath = FileHelper.createTempFile(extension, cell.getBytes());
                OS.openFile(String.valueOf(tempFilePath));
            } else {
                OS.openFile(absoluteLobFolder + cell.getFilename());
            }
        });
    }
}
