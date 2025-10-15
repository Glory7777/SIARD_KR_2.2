package ch.admin.bar.siardsuite.ui.component.rendering;

import ch.admin.bar.siardsuite.ui.component.rendering.utils.PaginationManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 페이지네이션 컨트롤 UI 컴포넌트
 * 페이지 크기 선택, 이전/다음 버튼, 페이지 정보를 제공
 */
@Slf4j
public class PaginationControls<T> extends HBox {

    @Getter
    private final ComboBox<Integer> pageSizeComboBox;
    @Getter
    private final Button prevButton;
    @Getter
    private final Button nextButton;
    @Getter
    private final Button firstButton;
    @Getter
    private final Button lastButton;
    @Getter
    private final Label pageInfoLabel;
    
    private final PaginationManager<T> paginationManager;
    
    // 중앙에 배치될 컨테이너: < [1 2 3 ...] > 를 담는다
    private final HBox centerBox;
    private final HBox pageButtonsBox; // 페이지 번호 라벨을 담을 컨테이너
    private final List<Label> pageLabels;

    public PaginationControls(PaginationManager<T> paginationManager) {
        this.paginationManager = paginationManager;
        
        // UI 컴포넌트 초기화
        this.pageSizeComboBox = new ComboBox<>();
        this.prevButton = new Button("<");
        this.nextButton = new Button(">");
        this.firstButton = new Button("<<");
        this.lastButton = new Button(">>");
        this.pageInfoLabel = new Label();
        this.centerBox = new HBox();
        this.pageButtonsBox = new HBox(); // 페이지 번호 컨테이너 초기화
        this.pageLabels = new ArrayList<>();
        
        initializeControls();
        setupEventHandlers();
        setupBindings();
    }

    private void initializeControls() {
        // 페이지 크기 선택 박스 설정 (기본값 100으로 설정)
        pageSizeComboBox.getItems().addAll(
            PaginationManager.getAvailablePageSizes()[0],
            PaginationManager.getAvailablePageSizes()[1],
            PaginationManager.getAvailablePageSizes()[2]
        );
        pageSizeComboBox.setValue(100); // 기본값 100으로 설정
        pageSizeComboBox.setPrefWidth(100); // 가로 크기 증가(10000도 잘 보이도록)

        // 버튼 설정 (작고 균일하게) + 패딩/인셋 제거
        int btnW = 16, btnH = 16;
        String arrowBtnStyle =
            "-fx-background-color: transparent;" +
            "-fx-padding: 0;" +
            "-fx-background-insets: 0;" +
            "-fx-border-insets: 0;" +
            "-fx-focus-color: transparent;" +
            "-fx-faint-focus-color: transparent;";

        firstButton.setPrefSize(btnW, btnH);
        prevButton.setPrefSize(btnW, btnH);
        nextButton.setPrefSize(btnW, btnH);
        lastButton.setPrefSize(btnW, btnH);

        firstButton.setMinSize(btnW, btnH);
        prevButton.setMinSize(btnW, btnH);
        nextButton.setMinSize(btnW, btnH);
        lastButton.setMinSize(btnW, btnH);

        firstButton.setStyle(arrowBtnStyle);
        prevButton.setStyle(arrowBtnStyle);
        nextButton.setStyle(arrowBtnStyle);
        lastButton.setStyle(arrowBtnStyle);
        
        // 페이지 정보 라벨 설정
        pageInfoLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 11px;");
        
        // centerBox는 요소 간 기본 간격 0, 간격은 margin으로만 제어
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(0);

        // 페이지 번호 컨테이너 설정
        pageButtonsBox.setAlignment(Pos.CENTER);
        pageButtonsBox.setSpacing(0);
        HBox.setMargin(pageButtonsBox, new Insets(0, 0, 0, 0));

        // 화살표 버튼 좌우 여백
        HBox.setMargin(prevButton,  new Insets(0, 5, 0, 5));
        HBox.setMargin(nextButton,  new Insets(0, 5, 0, 5));

        // 레이아웃 설정 (컴팩트하게)
        setAlignment(Pos.CENTER);
        setSpacing(5);
        setPadding(new Insets(3, 5, 3, 5));
        
        // 페이지네이션 컨트롤(페이지 번호 + 화살표)을 담는 컨테이너
        HBox paginationBox = new HBox(0, firstButton, prevButton, pageButtonsBox, nextButton, lastButton);
        paginationBox.setAlignment(Pos.CENTER);

        // 중앙 컨테이너에 페이지네이션 컨트롤을 추가
        centerBox.getChildren().add(paginationBox);

        // 오른쪽 정보 묶음: [pageInfoLabel][pageSizeComboBox]
        HBox rightBox = new HBox(6, pageInfoLabel, pageSizeComboBox);
        rightBox.setAlignment(Pos.CENTER_RIGHT);
        // 라벨이 줄지 않도록 고정
        pageInfoLabel.setMinWidth(Region.USE_PREF_SIZE);
        pageInfoLabel.setMaxWidth(Region.USE_PREF_SIZE);
        HBox.setHgrow(pageInfoLabel, Priority.NEVER);
        HBox.setHgrow(pageSizeComboBox, Priority.NEVER);

        // 컴포넌트들을 HBox에 추가
        getChildren().addAll(centerBox, rightBox);

        // centerBox가 남은 공간을 모두 차지하도록 설정
        HBox.setHgrow(centerBox, Priority.ALWAYS);

        // 페이지 버튼들 초기화 (컨테이너가 구성된 이후 호출해야 올바른 위치에 삽입됨)
        updatePageButtons();
    }

    private void setupEventHandlers() {
        // 페이지 크기 변경 이벤트
        pageSizeComboBox.setOnAction(event -> {
            Integer newPageSize = pageSizeComboBox.getValue();
            if (newPageSize != null) {
                log.debug("Page size changed to: {}", newPageSize);
                paginationManager.setPageSize(newPageSize);
                updatePageButtons(); // 페이지 크기 변경 시 페이지 버튼 업데이트
            }
        });

        // 이전 페이지 버튼 이벤트
        prevButton.setOnAction(event -> {
            log.debug("Previous page clicked");
            paginationManager.previousPage();
            this.requestFocus();
        });

        // 다음 페이지 버튼 이벤트
        nextButton.setOnAction(event -> {
            log.debug("Next page clicked");
            paginationManager.nextPage();
            this.requestFocus();
        });

        // 첫 페이지 버튼 이벤트
        firstButton.setOnAction(event -> {
            log.debug("First page clicked");
            paginationManager.goToPage(0);
            this.requestFocus();
        });

        // 마지막 페이지 버튼 이벤트
        lastButton.setOnAction(event -> {
            log.debug("Last page clicked");
            int last = Math.max(0, paginationManager.getTotalPages() - 1);
            paginationManager.goToPage(last);
            this.requestFocus();
        });
    }

    private void setupBindings() {
        // 페이지 정보 라벨 바인딩 (1-based로 표시)
        pageInfoLabel.textProperty().bind(
            paginationManager.currentPageProperty().add(1).asString()
                .concat(" / ")
                .concat(paginationManager.totalPagesProperty().asString())
                .concat(" 페이지")
        );

        // 이전 버튼 활성화/비활성화
        firstButton.disableProperty().bind(
            paginationManager.currentPageProperty().isEqualTo(0)
        );
        prevButton.disableProperty().bind(
            paginationManager.currentPageProperty().isEqualTo(0)
        );

        // 다음 버튼 활성화/비활성화
        lastButton.disableProperty().bind(
            paginationManager.currentPageProperty()
                .isEqualTo(paginationManager.totalPagesProperty().subtract(1))
        );
        nextButton.disableProperty().bind(
            paginationManager.currentPageProperty()
                .isEqualTo(paginationManager.totalPagesProperty().subtract(1))
        );

        // 현재 페이지가 변경될 때마다 페이지 버튼 업데이트
        paginationManager.currentPageProperty().addListener((observable, oldValue, newValue) -> {
            updatePageButtons();
        });

        // 총 페이지 수가 변경될 때마다 페이지 버튼 업데이트
        paginationManager.totalPagesProperty().addListener((observable, oldValue, newValue) -> {
            updatePageButtons();
        });
    }

    /**
     * 페이지 버튼들을 업데이트
     */
    private void updatePageButtons() {
        // 기존 라벨들 제거
        pageButtonsBox.getChildren().clear();
        pageLabels.clear();
        
        int currentPage = paginationManager.getCurrentPage() + 1; // 1-based로 변환
        int totalPages = paginationManager.getTotalPages();
        
        // 표시할 페이지가 없으면 아무것도 하지 않음
        if (totalPages <= 0) {
            return;
        }

        // 최대 10개 페이지 라벨만 표시
        int startPage = Math.max(1, currentPage - 4);
        int endPage = Math.min(totalPages, startPage + 9);
        
        // 시작 페이지 조정 (끝에서 10개가 되도록)
        if (endPage - startPage < 9) {
            startPage = Math.max(1, endPage - 9);
        }
        
        for (int i = startPage; i <= endPage; i++) {
            Label pageLabel = new Label(String.valueOf(i));
            
            // 하이퍼링크 스타일 - 정렬 및 크기 개선
            if (i == currentPage) {
                // 선택된 페이지: 검은색 + 굵은 글씨
                pageLabel.setStyle("-fx-text-fill: #000000; -fx-font-weight: bold; -fx-underline: false; -fx-font-size: 12px; -fx-cursor: default; -fx-alignment: center;");
            } else {
                // 클릭 가능한 페이지: 파란색 + hand 커서
                pageLabel.setStyle("-fx-text-fill: #0078d4; -fx-font-weight: normal; -fx-underline: false; -fx-font-size: 12px; -fx-cursor: hand; -fx-alignment: center;");
            }
            
            // 라벨 크기 설정으로 정렬 개선(모두 동일 크기)
            pageLabel.setPrefWidth(28); // 4자리(1000)도 잘 보이도록
            pageLabel.setPrefHeight(18);
            pageLabel.setMinSize(28, 18);
            
            // 라벨 간격은 margin으로 2px
            HBox.setMargin(pageLabel, new Insets(0, 0, 0, 0));

            final int pageNumber = i;
            pageLabel.setOnMouseClicked(event -> {
                if (pageNumber != currentPage) {
                    log.debug("Page {} clicked", pageNumber);
                    paginationManager.goToPage(pageNumber - 1); // 0-based로 변환
                }
            });
            
            pageLabels.add(pageLabel);
            pageButtonsBox.getChildren().add(pageLabel);
        }
    }

    /**
     * 페이지 정보를 수동으로 업데이트
     * (바인딩이 제대로 작동하지 않을 경우를 위한 백업)
     */
    public void updatePageInfo() {
        pageInfoLabel.setText(paginationManager.getPageInfo());
    }
}
