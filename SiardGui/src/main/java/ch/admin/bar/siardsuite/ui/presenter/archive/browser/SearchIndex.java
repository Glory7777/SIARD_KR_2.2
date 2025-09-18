package ch.admin.bar.siardsuite.ui.presenter.archive.browser;

import ch.admin.bar.siardsuite.model.database.DatabaseTable;
import lombok.Getter;
import lombok.NonNull;

import java.util.*;

/**
 * 검색 인덱스: 한 번의 전수 스캔으로 생성한 결과를 UI 전역에서 재사용한다.
 * - perTableMatchedPositions: 테이블별 매치된 레코드 인덱스(Record.getRecord()) 목록
 * - perTableMatchedCount: 테이블별 매치 개수(positions.size())
 * - unifiedPositions: 통합 결과를 위한 (table, recordIndex) 쌍의 리스트
 * - totalMatched: 모든 테이블 매치 합계
 */
@Getter
public class SearchIndex {
    private final Map<DatabaseTable, List<Long>> perTableMatchedPositions = new HashMap<>();
    private final Map<DatabaseTable, Long> perTableMatchedCount = new HashMap<>();
    private final List<TablePosition> unifiedPositions = new ArrayList<>();
    private long totalMatched = 0L;
    // 테이블 내 절대 인덱스(0-based) -> 전역 순번(1-based) 매핑
    private final Map<DatabaseTable, Map<Long, Long>> tableAbsToGlobalIndex = new HashMap<>();

    public void addMatch(@NonNull DatabaseTable table, long recordIndex) {
        perTableMatchedPositions.computeIfAbsent(table, k -> new ArrayList<>()).add(recordIndex);
        perTableMatchedCount.put(table, (perTableMatchedCount.getOrDefault(table, 0L) + 1L));
        // 전역 순번은 1부터 시작
        long globalIndex = totalMatched + 1;
        unifiedPositions.add(new TablePosition(table, recordIndex));
        tableAbsToGlobalIndex.computeIfAbsent(table, k -> new HashMap<>()).put(recordIndex, globalIndex);
        totalMatched++;
    }

    public long getMatchedCount(@NonNull DatabaseTable table) {
        return perTableMatchedCount.getOrDefault(table, 0L);
    }

    /**
     * 테이블의 절대 레코드 인덱스(0-based)에 해당하는 전역 순번(1-based)을 반환.
     * 없으면 -1 반환.
     */
    public long getGlobalIndex(@NonNull DatabaseTable table, long absoluteZeroBasedIndex) {
        return tableAbsToGlobalIndex.getOrDefault(table, Collections.emptyMap())
                .getOrDefault(absoluteZeroBasedIndex, -1L);
    }

    @Getter
    public static class TablePosition {
        private final DatabaseTable table;
        private final long recordIndex;

        public TablePosition(DatabaseTable table, long recordIndex) {
            this.table = table;
            this.recordIndex = recordIndex;
        }
    }
}