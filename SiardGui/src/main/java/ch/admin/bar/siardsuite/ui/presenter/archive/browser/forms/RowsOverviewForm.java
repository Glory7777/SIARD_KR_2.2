package ch.admin.bar.siardsuite.ui.presenter.archive.browser.forms;

import ch.admin.bar.dbexception.DatabaseExceptionHandlerHelper;
import ch.admin.bar.dbexception.DbOutOfMemoryException;
import ch.admin.bar.siard2.api.Cell;
import ch.admin.bar.siard2.api.Record;
import ch.admin.bar.siard2.api.Table;
import ch.admin.bar.siard2.api.primary.LobReader;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
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
                                .usePagination(true) // 페이지네이션 사용
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
                                .usePagination(true) // 페이지네이션 사용
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
     * 통합 검색 결과용 데이터 소스 (성능 최적화 버전)
     * - SearchIndex의 전역 매치 목록(unifiedPositions)을 직접 사용하여
     * 페이지네이션 요청 시 반복적인 테이블 스캔을 제거.
     */

    public static class UnifiedStreamingDataSource implements LazyLoadingDataSource<UnifiedRecordWrapper> {

        private final String searchTerm;
        // SearchIndex가 미리 계산한 '전역 매치 목록'
        private final List<ch.admin.bar.siardsuite.ui.presenter.archive.browser.SearchIndex.TablePosition> globalMatches;
        private final long totalItems;
        private final SiardArchive archive;
        
        // 이미 로드한 레코드를 캐싱하여 페이지를 앞뒤로 이동할 때 I/O를 방지
        private final Map<Integer, UnifiedRecordWrapper> recordCache = new HashMap<>();

        public UnifiedStreamingDataSource(@NonNull SiardArchive archive, String searchTerm, @NonNull ch.admin.bar.siardsuite.ui.presenter.archive.browser.SearchIndex searchIndex) {
            this.archive = archive;
            this.searchTerm = searchTerm;
            
            // 생성 시점에 '전역 매치 목록'을 한 번만 가져옴
            this.globalMatches = searchIndex.getUnifiedPositions();
            this.totalItems = this.globalMatches.size();
        }

        @Override
        public List<UnifiedRecordWrapper> load(int startIndex, int nrOfItems) {
            final List<UnifiedRecordWrapper> pageRecords = new ArrayList<>();
            // 로드할 정확한 범위 계산
            int endIndex = Math.min(startIndex + nrOfItems, (int) this.totalItems);

            // 이 페이지 로드를 위한 LobReader를 한 번만 열기
            try (LobReader lobReader = new LobReader(new File(archive.getArchive().getFile().getPath()))) {
                
                for (int i = startIndex; i < endIndex; i++) {
                    
                    // 캐시 확인: 이미 로드한 레코드는 즉시 반환
                    if (recordCache.containsKey(i)) {
                        pageRecords.add(recordCache.get(i));
                        continue;
                    }

                    // 캐시에 없는 항목: '전역 매치 목록'에서 직접 위치를 찾아 로드
                    try {
                        // 'i'번째 매치 정보(테이블, 레코드 인덱스)를 직접 가져옴
                        val match = this.globalMatches.get(i);
                        DatabaseTable table = match.getTable();
                        long recordIndex = match.getRecordIndex(); // 0-based

                        // 해당 테이블을 열고 'skip'으로 레코드로 바로 Jump
                        val dispenser = table.getTable().openRecords(lobReader); 
                        dispenser.skip(recordIndex); // 반복 스캔 없이 정확한 위치로 이동
                        val rec = dispenser.get();

                        if (rec != null) {
                            val wrapper = new UnifiedRecordWrapper(rec, table.getName());
                            wrapper.setGlobalIndex(i + 1); // 전역 인덱스(1-based)
                            pageRecords.add(wrapper);
                            recordCache.put(i, wrapper); // 다음을 위해 캐시에 저장
                        }
                    } catch (Exception e) {
                        log.error("Failed to load record at global index {}", i, e);
                    }
                }
            } catch (IOException e) {
                 log.error("Failed to create LobReader for unified search loading", e);
            }
            
            return pageRecords;
        }

        @Override
        public long findIndexOf(UnifiedRecordWrapper item) {
            // globalIndex는 1-based, 리스트 인덱스는 0-based
            return item.getGlobalIndex() - 1;
        }

        @Override
        public long getNumberOfItems() {
            // 전체 아이템 개수를 즉시 반환 (기존의 전체 스캔 로직 제거)
            return this.totalItems;
        }
    }

    /**
     * 
     */
    public static class RecordDataSource implements LazyLoadingDataSource<RecordWrapper> {
        // DatabaseTable을 보유하면 '전체 행 수'를 정확히 얻을 수 있음
        private final DatabaseTable databaseTable;
        private final Table table;
        private final String searchTerm;
        private final LobReader lobReader; // LobReader 인스턴스 추가   
        // 매치 총개수 캐시(검색어가 있을 때 한 번만 계산)
        private Long cachedMatchedCount = null;

    public RecordDataSource(DatabaseTable databaseTable, String searchTerm) {
        this(databaseTable, searchTerm, null);
    }

    public RecordDataSource(DatabaseTable databaseTable, String searchTerm, LobReader lobReader) {
        this.databaseTable = databaseTable;
        this.table = databaseTable.getTable();
        this.searchTerm = searchTerm;
        this.lobReader = lobReader;
        // 리셋 시 캐시 초기화를 위해 생성자에서 캐시 클리어
        this.cachedMatchedCount = null;
    }

        private boolean isSearchTermBlank() {
            return searchTerm == null || searchTerm.isBlank();
        }

        @SneakyThrows
        @Override
        public List<RecordWrapper> load(int startIndex, int nrOfItems) {
            try {
            val dispenser = table.openRecords(this.lobReader); // LobReader 전달

            final List<RecordWrapper> collected = new ArrayList<>();
            long currentSearchResultIndex = 0; // 검색 결과의 현재 인덱스 (0부터 시작)

            // 메모리 사용량 최적화: 최대 로드 개수 제한
            int actualItems = Math.min(nrOfItems, 10000); // 최대 10000개로 제한

            if (isSearchTermBlank()) {
                // 비검색 모드: 단순 페이지네이션
                dispenser.skip(startIndex);
                long viewSeq = startIndex + 1L; // No. 컬럼용 순번 (1, 2, 3...)

                for (int i = 0; i < actualItems; i++) {
                    val record = dispenser.get();
                    if (record == null) break;

                    collected.add(new RecordWrapper(record, viewSeq, record.getRecord() + 1));
                    viewSeq++;
                }
            } else {
                // 검색 모드: 검색 결과만 대상으로 페이지네이션
                while (collected.size() < actualItems) {
                    val record = dispenser.getWithSearchTerm(searchTerm);
                    if (record == null) break;

                    if (dispenser.anyMatches()) {
                        // 검색 결과가 맞으면 현재 인덱스가 요청 범위에 있는지 확인
                        if (currentSearchResultIndex >= startIndex &&
                            currentSearchResultIndex < startIndex + actualItems) {

                            long viewSeq = currentSearchResultIndex + 1; // 검색 결과 내 순번 (1, 2, 3...)
                            long abs1Based = record.getRecord() + 1; // 절대 인덱스
                            collected.add(new RecordWrapper(record, viewSeq, abs1Based));
                        }
                        currentSearchResultIndex++;
                    }
                }
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
            // 검색어가 있을 때만 캐시 사용, 리셋 시에는 캐시 무시
            if (cachedMatchedCount != null && searchTerm != null && !searchTerm.isBlank()) {
                return cachedMatchedCount;
            }
            // 전체 스캔으로 매치 수 산출(한 번만)
            long count = 0;
            try {
                val dispenser = table.openRecords(this.lobReader); // LobReader 전달
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
