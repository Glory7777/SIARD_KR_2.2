package ch.admin.bar.siardsuite.ui.presenter.archive.browser.forms;

import ch.admin.bar.dbexception.DatabaseExceptionHandlerHelper;
import ch.admin.bar.dbexception.DbOutOfMemoryException;
import ch.admin.bar.siard2.api.Cell;
import ch.admin.bar.siard2.api.Record;
import ch.admin.bar.siard2.api.Table;
import ch.admin.bar.siard2.api.primary.TableImpl;
import ch.admin.bar.siard2.api.MetaColumn;
import ch.admin.bar.siardsuite.framework.i18n.DisplayableText;
import ch.admin.bar.siardsuite.framework.i18n.keys.I18nKey;
import ch.admin.bar.siardsuite.model.database.DatabaseColumn;
import ch.admin.bar.siardsuite.model.database.DatabaseSchema;
import ch.admin.bar.siardsuite.model.database.DatabaseTable;
import ch.admin.bar.siardsuite.model.database.SiardArchive;
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

    public static RenderableForm<DatabaseTable> createAndUpdateWithSearchResult(@NonNull final DatabaseTable table, String searchTerm, ch.admin.bar.siardsuite.ui.presenter.archive.browser.SearchIndex searchIndex) {
        val tableProperties = table.getColumns().stream()
                .map(column -> new TableColumnProperty<>(
                        DisplayableText.of(column.getName()),
                        row -> row.findCellValue(column.getName()),
                        createCellClickListener(column)))
                .collect(Collectors.toList());

        // No. 컬럼: 단순히 데이터 개수 순번 (1, 2, 3...)
        tableProperties.add(0, new TableColumnProperty<>(
                DisplayableText.of("No."),
                row -> String.valueOf(row.getViewIndex()), // 항상 1부터 시작하는 순번
                Optional.empty()
        ));

        // 검색인 경우 매치된 테이블과 값의 스니펫을 별도 컬럼으로 추가해 가독성을 높인다
        if (searchTerm != null && !searchTerm.isBlank()) {
            // 통합검색결과의 No.와 일치하는 Matched No. 컬럼 추가 (No. 바로 다음)
            tableProperties.add(1, new TableColumnProperty<>(
                    DisplayableText.of("Matched No."),
                    row -> {
                        try {
                            long recordZeroBased = row.getRecord().getRecord();
                            Long global = (searchIndex != null) ?
                                    searchIndex.getGlobalIndex(table, recordZeroBased) : null;
                            return global != null ? String.valueOf(global) : "";
                        } catch (Exception e) {
                            return "";
                        }
                    },
                    Optional.empty()
            ));
            tableProperties.add(2, new TableColumnProperty<>(
                    DisplayableText.of("Matched Table"),
                    row -> table.getName(),
                    Optional.empty()
            ));
            tableProperties.add(3, new TableColumnProperty<>(
                    DisplayableText.of("Matched Value"),
                    row -> row.findFirstMatchedSnippet(searchTerm),
                    Optional.empty()
            ));
        }

        return RenderableForm.<DatabaseTable>builder()
                .dataSupplier(() -> table)
                .group(RenderableFormGroup.<DatabaseTable>builder()
                        .property(RenderableLazyLoadingTable.<DatabaseTable, RecordWrapper>builder()
                                .dataExtractor(databaseTable -> new RecordDataSource(table, searchTerm))
                                .properties(tableProperties)
                                .build())

                                
                        .property(new ReadOnlyStringProperty<>(
                                LABEL_TABLE,
                                DatabaseTable::getName))
                        .property(new ReadOnlyStringProperty<>(
                                LABEL_NUMBER_OF_ROWS,
                                Converter.longToString(t -> {
                                    if (searchTerm != null && !searchTerm.isBlank() && searchIndex != null) {
                                        return searchIndex.getMatchedCount(table);
                                    }
                                    return table.getNumberOfRows();
                                }))
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
            this.absoluteIndex = record.getRecord() + 1; // 기본은 절대 인덱스 기반
            this.viewIndex = -1; // 검색 시에만 설정

            val cells = new ListAssembler<>(
                    Converter.catchExceptions(record::getCells),
                    Converter.catchExceptions(record::getCell)
            ).assemble();

            this.cellsByName = cells.stream()
                    .collect(Collectors.toMap(cell -> cell.getMetaColumn().getName(), cell -> cell));
        }

        // 검색 시 사용되는 생성자: 뷰 내 순번과 절대 인덱스를 함께 설정
        public RecordWrapper(@NonNull Record record, long viewIndex, long absoluteIndex) {
            this.record = record;
            this.viewIndex = viewIndex;
            this.absoluteIndex = absoluteIndex;

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

        // 뷰 내 순번(검색 결과 내 1..N)
        @Getter
        private long viewIndex;
        // 절대 인덱스(Record.getRecord()+1)
        @Getter
        private long absoluteIndex;

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

        public String findFirstMatchedSnippet(String searchTerm) {
            if (searchTerm == null || searchTerm.isBlank()) return "";
            final String lowered = searchTerm.toLowerCase();
            for (Cell cell : cellsByName.values()) {
                try {
                    if (cell == null || cell.isNull()) continue;
                    String text = extractText(cell);
                    String hay = text == null ? "" : text;
                    int idx = hay.toLowerCase().indexOf(lowered);
                    if (idx >= 0) {
                        int start = Math.max(0, idx - 15);
                        int end = Math.min(hay.length(), idx + lowered.length() + 15);
                        String prefix = start > 0 ? "…" : "";
                        String suffix = end < hay.length() ? "…" : "";
                        String matched = hay.substring(idx, idx + lowered.length());
                        return prefix + hay.substring(start, idx) + "[" + matched + "]" + hay.substring(idx + lowered.length(), end) + suffix;
                    }
                } catch (Exception ignore) {
                }
            }
            return "";
        }
    }

    /**
     * 주어진 테이블에서 검색어에 매치되는 레코드로부터 스니펫을 최대 limit 개수까지 수집한다.
     */
    public static List<String> collectMatchedSnippets(Table table, String searchTerm, int limit) {
        final List<String> snippets = new ArrayList<>();
        if (searchTerm == null || searchTerm.isBlank() || limit <= 0) return snippets;
        try {
            val dispenser = table.openRecords();
            while (snippets.size() < limit) {
                val rec = dispenser.getWithSearchTerm(searchTerm);
                if (rec == null) break;
                if (dispenser.anyMatches()) {
                    val wrapper = new RecordWrapper(rec);
                    String s = wrapper.findFirstMatchedSnippet(searchTerm);
                    if (s != null && !s.isBlank()) snippets.add(s);
                }
            }
        } catch (Exception ignore) {
        }
        return snippets;
    }

    /**
     * 통합 검색 결과를 위한 폼 생성 - 모든 테이블의 매치된 레코드를 하나의 표로 표시
     */
    public static RenderableForm<Object> createUnifiedSearchResult(@NonNull final SiardArchive archive, String searchTerm, @NonNull final ch.admin.bar.siardsuite.ui.presenter.archive.browser.SearchIndex searchIndex) {
       
        final List<UnifiedRecordWrapper> placeholder = new ArrayList<>();

        val tableProperties = new ArrayList<TableColumnProperty<UnifiedRecordWrapper>>();
        
        // No. 컬럼 (1부터 시작) - 순차 번호 표시
        tableProperties.add(new TableColumnProperty<>(
                DisplayableText.of("No."),
                row -> String.valueOf(row.getGlobalIndex()),
                Optional.empty()
        ));
        
        // Matched Table 컬럼
        tableProperties.add(new TableColumnProperty<>(
                DisplayableText.of("Matched Table"),
                UnifiedRecordWrapper::getTableName,
                Optional.empty()
        ));
        
        // Matched Value 컬럼
        tableProperties.add(new TableColumnProperty<>(
                DisplayableText.of("Matched Value"),
                row -> row.findFirstMatchedSnippet(searchTerm),
                Optional.empty()
        ));
        
        // 모든 셀 값을 표시하는 컬럼 추가
        tableProperties.add(new TableColumnProperty<>(
                DisplayableText.of("All Values"),
                row -> row.getAllCellValues(),
                Optional.empty()
        ));

        return RenderableForm.<Object>builder()
                .dataSupplier(() -> archive)
                .group(RenderableFormGroup.<Object>builder()
                        .property(RenderableLazyLoadingTable.<Object, UnifiedRecordWrapper>builder()
                                .dataExtractor(data -> new UnifiedStreamingDataSource(archive, searchTerm, searchIndex))
                                .properties(tableProperties)
                                .build())
                        .build())
                .build();
    }

    /**
     * 통합 검색 결과용 레코드 래퍼
     */
    public static class UnifiedRecordWrapper {
        private final Record record;
        private final String tableName;
        private final Map<String, Cell> cellsByName;
        private long globalIndex = 0; // 전역 순차 번호

        public UnifiedRecordWrapper(@NonNull Record record, @NonNull String tableName) {
            this.record = record;
            this.tableName = tableName;

            val cells = new ListAssembler<>(
                    Converter.catchExceptions(record::getCells),
                    Converter.catchExceptions(record::getCell)
            ).assemble();

            this.cellsByName = cells.stream()
                    .collect(Collectors.toMap(cell -> cell.getMetaColumn().getName(), cell -> cell));
        }

        public void setGlobalIndex(long globalIndex) {
            this.globalIndex = globalIndex;
        }

        public long getGlobalIndex() {
            return globalIndex;
        }

        public String getTableName() {
            return tableName;
        }

        public String getAllCellValues() {
            return cellsByName.values().stream()
                    .map(this::extractText)
                    .filter(s -> s != null && !s.isBlank())
                    .collect(Collectors.joining(" | "));
        }

        public String findFirstMatchedSnippet(String searchTerm) {
            if (searchTerm == null || searchTerm.isBlank()) return "";
            final String lowered = searchTerm.toLowerCase();
            for (Cell cell : cellsByName.values()) {
                try {
                    if (cell == null || cell.isNull()) continue;
                    String text = extractText(cell);
                    String hay = text == null ? "" : text;
                    int idx = hay.toLowerCase().indexOf(lowered);
                    if (idx >= 0) {
                        int start = Math.max(0, idx - 15);
                        int end = Math.min(hay.length(), idx + lowered.length() + 15);
                        String prefix = start > 0 ? "…" : "";
                        String suffix = end < hay.length() ? "…" : "";
                        String matched = hay.substring(idx, idx + lowered.length());
                        return prefix + hay.substring(start, idx) + "[" + matched + "]" + hay.substring(idx + lowered.length(), end) + suffix;
                    }
                } catch (Exception ignore) {
                }
            }
            return "";
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

    /**
     * 통합 검색 결과용 데이터 소스
     */
    // 통합 검색 결과를 제공 - SIARD 기존 로직 활용하여 제한 없이 모든 데이터 검색
    public static class UnifiedStreamingDataSource implements LazyLoadingDataSource<UnifiedRecordWrapper> {
        private final List<DatabaseTable> tables;
        private final String searchTerm;
        // 각 테이블에서 "읽은 전체 레코드 수"를 추적(매치 여부와 무관)
        private final long[] readRowsPerTable;
        private final ch.admin.bar.siardsuite.ui.presenter.archive.browser.SearchIndex searchIndex;
        private long globalIndex = 0; // 전역 순차 번호
        private boolean allDataLoaded = false; // 모든 데이터 로드 완료 여부
        private final List<UnifiedRecordWrapper> allLoadedRecords = new ArrayList<>(); // 모든 로드된 레코드

        public UnifiedStreamingDataSource(@NonNull SiardArchive archive, String searchTerm, ch.admin.bar.siardsuite.ui.presenter.archive.browser.SearchIndex searchIndex) {
            val list = new ArrayList<DatabaseTable>();
            for (val schema : archive.getSchemas()) {
                list.addAll(schema.getTables());
            }
            this.tables = list;
            this.searchTerm = searchTerm;
            this.searchIndex = searchIndex;
            this.readRowsPerTable = new long[list.size()];
        }

        @Override
        public List<UnifiedRecordWrapper> load(int startIndex, int nrOfItems) {
            // 이미 로드된 데이터가 있으면 그것을 반환
            if (startIndex < allLoadedRecords.size()) {
                int endIndex = Math.min(startIndex + nrOfItems, allLoadedRecords.size());
                return allLoadedRecords.subList(startIndex, endIndex);
            }

            // 모든 데이터가 이미 로드되었으면 빈 리스트 반환
            if (allDataLoaded) {
                return new ArrayList<>();
            }

            // 새로운 데이터 로드: 인덱스가 있으면 인덱스 기반으로, 없으면 순차 스캔
            final List<UnifiedRecordWrapper> out = new ArrayList<>();
            int need = nrOfItems;
            boolean foundAnyData = false;
            
            // 각 테이블에서 순차적으로 검색
            for (int t = 0; t < tables.size() && need > 0; t++) {
                val table = tables.get(t);
                try {
                    if (searchIndex != null) {
                        // 인덱스 기반: 이미 계산된 매치 포지션에서 필요한 구간만 로드
                        val positions = searchIndex.getPerTableMatchedPositions().getOrDefault(table, List.of());
                        long already = allLoadedRecords.stream().filter(r -> r.getTableName().equals(table.getName())).count();
                        for (int i = (int) already; i < positions.size() && need > 0; i++) {
                            long target = positions.get(i);
                            val dispenser = table.getTable().openRecords();
                            dispenser.skip(target);
                            val rec = dispenser.get();
                            if (rec == null) break;
                            val wrapper = new UnifiedRecordWrapper(rec, table.getName());
                            wrapper.setGlobalIndex(++globalIndex);
                            out.add(wrapper);
                            allLoadedRecords.add(wrapper);
                            need--;
                            foundAnyData = true;
                        }
                    } else {
                        val dispenser = table.getTable().openRecords();
                        dispenser.skip((int) readRowsPerTable[t]);
                        while (need > 0) {
                            val rec = dispenser.getWithSearchTerm(searchTerm);
                            if (rec == null) break;
                            readRowsPerTable[t]++;
                            if (dispenser.anyMatches()) {
                                val wrapper = new UnifiedRecordWrapper(rec, table.getName());
                                wrapper.setGlobalIndex(++globalIndex);
                                out.add(wrapper);
                                allLoadedRecords.add(wrapper);
                                need--;
                                foundAnyData = true;
                            }
                        }
                    }
                } catch (Exception ignore) {
                    // 테이블 접근 실패 시 다음 테이블로
                }
            }
            
            // 더 이상 로드할 데이터가 없으면 완료 표시
            if (!foundAnyData) {
                allDataLoaded = true;
            }
            
            return out;
        }

        @Override
        public long findIndexOf(UnifiedRecordWrapper item) {
            return item.getGlobalIndex(); // 전역 순차 번호 반환
        }

        @Override
        public long getNumberOfItems() {
            return allLoadedRecords.size();
        }
    }

// 검색 결과 표시의 핵심: DataSource가 매칭 레코드만 즉시 공급하고,

    public static class RecordDataSource implements LazyLoadingDataSource<RecordWrapper> {
        // DatabaseTable을 보유하면 '전체 행 수'를 정확히 얻을 수 있습니다.
        private final DatabaseTable databaseTable;
        private final Table table;
        private final String searchTerm;
        // 매치 총개수 캐시(검색어가 있을 때 한 번만 계산)
        private Long cachedMatchedCount = null;

    public RecordDataSource(DatabaseTable databaseTable, String searchTerm) {
        this.databaseTable = databaseTable;
        this.table = databaseTable.getTable();
            this.searchTerm = searchTerm;
        }

    private boolean isSearchTermBlank() {
        return searchTerm == null || searchTerm.isBlank();
    }

        @SneakyThrows
        @Override
        public List<RecordWrapper> load(int startIndex, int nrOfItems) {
            try {
            val dispenser = table.openRecords();
            dispenser.skip(startIndex);

            final List<RecordWrapper> collected = new ArrayList<>();
            long viewSeq = startIndex + 1L; // No. 컬럼용 순번 (1, 2, 3...)
            for (int i = 0; i < nrOfItems; i++) {
                val record = dispenser.getWithSearchTerm(searchTerm);
                if (record == null) break;

                if (isSearchTermBlank()) {
                    // 비검색 모드: No. = 절대 인덱스
                    collected.add(new RecordWrapper(record, viewSeq, record.getRecord() + 1));
                } else if (dispenser.anyMatches()) {
                    // 검색 모드: No. = 순번, Matched No. = 절대 인덱스
                    long abs1Based = record.getRecord() + 1;
                    collected.add(new RecordWrapper(record, viewSeq, abs1Based));
                }
                viewSeq++;
            }

            return collected;
            } catch (OutOfMemoryError e) {
                DatabaseExceptionHandlerHelper.doHandleOutOfMemoryException(e);
                throw e;
            } catch (Exception e) {
                // ZIP 파일이 일시적으로 준비되지 않았거나(Reset 타이밍), 암호화 미지원 예외 등인 경우 빈 결과로 안전 복귀
                log.warn("RecordDataSource.load skipped due to transient error: {}", e.getMessage());
                return new ArrayList<>();
            }
        }

        @Override
        public long findIndexOf(RecordWrapper item) {
            return item.getRecord().getRecord();
        }

        @Override
        public long getNumberOfItems() {
            if (isSearchTermBlank()) {
                return databaseTable.getNumberOfRows();
            }
            if (cachedMatchedCount != null) {
                return cachedMatchedCount;
            }
            // 전체 스캔으로 매치 수 산출(한 번만)
            long count = 0;
            try {
                val dispenser = table.openRecords();
                while (true) {
                    val rec = dispenser.getWithSearchTerm(searchTerm);
                    if (rec == null) break;
                    if (dispenser.anyMatches()) count++;
                }
            } catch (Exception ignore) {
            }
            cachedMatchedCount = count;
            return count;
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
