package ch.admin.bar.siardsuite.ui.component.rendering.utils;

import ch.admin.bar.siardsuite.ui.component.rendering.model.LazyLoadingDataSource;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 페이지네이션을 관리하는 클래스
 * 기존 LazyLoadingDataSource를 활용하여 페이지 단위로 데이터를 로드합니다.
 */
@Slf4j
public class PaginationManager<T> {

    private static final int DEFAULT_PAGE_SIZE = 100;
    private static final int[] AVAILABLE_PAGE_SIZES = {100, 1000, 10000};

    @Getter
    private final ObservableList<T> currentPageData = FXCollections.observableArrayList();
    
    private final LazyLoadingDataSource<T> dataSource;
    private final IntegerProperty currentPage = new SimpleIntegerProperty(0);
    private final IntegerProperty pageSize = new SimpleIntegerProperty(DEFAULT_PAGE_SIZE);
    private final IntegerProperty totalPages = new SimpleIntegerProperty(0);
    private final IntegerProperty totalItems = new SimpleIntegerProperty(0);

    public PaginationManager(LazyLoadingDataSource<T> dataSource) {
        this.dataSource = dataSource;
        this.totalItems.set((int) dataSource.getNumberOfItems());
        calculateTotalPages();
        loadCurrentPage();
        
        // 페이지 크기 변경 시 리스너 등록
        pageSize.addListener((observable, oldValue, newValue) -> {
            currentPage.set(0); // 첫 페이지로 리셋
            calculateTotalPages();
            loadCurrentPage();
        });
    }

    /**
     * 현재 페이지의 데이터를 로드합니다.
     */
    public void loadCurrentPage() {
        int startIndex = currentPage.get() * pageSize.get();
        int itemsToLoad = Math.min(pageSize.get(), totalItems.get() - startIndex);
        
        if (itemsToLoad <= 0) {
            currentPageData.clear();
            return;
        }

        log.debug("Loading page {} with startIndex {} and itemsToLoad {}", 
                 currentPage.get(), startIndex, itemsToLoad);

        try {
            List<T> data = dataSource.load(startIndex, itemsToLoad);
            currentPageData.clear();
            currentPageData.addAll(data);
        } catch (Exception e) {
            log.error("Failed to load page data: {}", e.getMessage());
            currentPageData.clear();
        }
    }

    /**
     * 지정된 페이지로 이동합니다.
     */
    public void goToPage(int page) {
        if (page >= 0 && page < totalPages.get()) {
            currentPage.set(page);
            loadCurrentPage();
        }
    }

    /**
     * 다음 페이지로 이동합니다.
     */
    public void nextPage() {
        if (currentPage.get() < totalPages.get() - 1) {
            goToPage(currentPage.get() + 1);
        }
    }

    /**
     * 이전 페이지로 이동합니다.
     */
    public void previousPage() {
        if (currentPage.get() > 0) {
            goToPage(currentPage.get() - 1);
        }
    }

    /**
     * 페이지 크기를 설정합니다.
     */
    public void setPageSize(int pageSize) {
        this.pageSize.set(pageSize);
    }

    /**
     * 총 페이지 수를 계산합니다.
     */
    private void calculateTotalPages() {
        int pages = (int) Math.ceil((double) totalItems.get() / pageSize.get());
        totalPages.set(Math.max(1, pages));
    }

    /**
     * 페이지 정보를 문자열로 반환합니다.
     */
    public String getPageInfo() {
        return String.format("%d / %d 페이지", 
            currentPage.get() + 1, totalPages.get());
    }

    /**
     * 사용 가능한 페이지 크기 목록을 반환합니다.
     */
    public static int[] getAvailablePageSizes() {
        return AVAILABLE_PAGE_SIZES.clone();
    }

    // Properties for JavaFX binding
    public IntegerProperty currentPageProperty() { return currentPage; }
    public IntegerProperty pageSizeProperty() { return pageSize; }
    public IntegerProperty totalPagesProperty() { return totalPages; }
    public IntegerProperty totalItemsProperty() { return totalItems; }

    public int getCurrentPage() { return currentPage.get(); }
    public int getPageSize() { return pageSize.get(); }
    public int getTotalPages() { return totalPages.get(); }
    public int getTotalItems() { return totalItems.get(); }
}
