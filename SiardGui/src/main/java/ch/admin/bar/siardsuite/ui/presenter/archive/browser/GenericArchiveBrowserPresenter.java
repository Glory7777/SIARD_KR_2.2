package ch.admin.bar.siardsuite.ui.presenter.archive.browser;

import ch.admin.bar.siardsuite.framework.dialogs.Dialogs;
import ch.admin.bar.siardsuite.framework.errors.ErrorHandler;
import ch.admin.bar.siardsuite.framework.i18n.DisplayableText;
import ch.admin.bar.siardsuite.framework.i18n.keys.I18nKey;
import ch.admin.bar.siardsuite.framework.view.FXMLLoadHelper;
import ch.admin.bar.siardsuite.framework.view.LoadedView;
import ch.admin.bar.siardsuite.model.TreeAttributeWrapper;
import ch.admin.bar.siardsuite.model.Tuple;
import ch.admin.bar.siardsuite.model.database.SiardArchive;
import ch.admin.bar.siardsuite.ui.View;
import ch.admin.bar.siardsuite.ui.common.DeactivatableListener;
import ch.admin.bar.siardsuite.ui.component.IconButton;
import ch.admin.bar.siardsuite.ui.component.TwoStatesButton;
import ch.admin.bar.siardsuite.ui.component.rendering.FormRenderer;
import ch.admin.bar.siardsuite.ui.component.rendering.TreeItemsExplorer;
import ch.admin.bar.siardsuite.ui.component.rendering.model.RenderableForm;
import ch.admin.bar.siardsuite.ui.presenter.archive.browser.forms.RowsOverviewForm;
import ch.admin.bar.siardsuite.util.OptionalHelper;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ch.admin.bar.siardsuite.model.TreeItemUtil.findChildrenTreeItemList;
import static ch.admin.bar.siardsuite.util.I18n.localeProperty;
import static ch.admin.bar.siardsuite.util.OptionalHelper.ifPresentOrElse;

//TODO:: step에 따라서 체크 박스 등 설정

/**
 * Presentes an archive - either when archiving a database (always only metadata) or when a SIARD Archive
 * file was opened to browse the archive content
 */
@Slf4j
public class GenericArchiveBrowserPresenter {

    private static final I18nKey RESET_SEARCH = I18nKey.of("tableContainer.resetSearchButton");
    private static final I18nKey META_SEARCH = I18nKey.of("tableContainer.metaSearchButton");
    private static final I18nKey TABLE_SEARCH = I18nKey.of("tableContainer.tableSearchButton");
    private static final I18nKey RECORD_SEARCH = I18nKey.of("tableContainer.recordSearchButton");
    private static final I18nKey CURRENT_SEARCH = I18nKey.of("search.record.currentSearch");
    private static final I18nKey RESULTS_FOUND = I18nKey.of("search.record.resultsFound");

    private static final String TABLE_STYLE_CLASS = "tree-table-view";

    private ArchiveStep archiveStep;

    @FXML
    public VBox container;
    @FXML
    private BorderPane borderPane;

    @FXML
    private Label title;
    @FXML
    private Text text;

    @FXML
    private TreeView<TreeAttributeWrapper> treeView;
    @FXML
    public VBox leftTreeBox;

    @FXML
    private IconButton saveChangesButton;
    @FXML
    private IconButton dropChangesButton;
    @FXML
    private MFXButton resetSearchButton;
    @FXML
    private TwoStatesButton tableSearchButton;
    @FXML
    private MFXButton metaSearchButton;
    @FXML
    private MFXButton recordSearchButton;
    @FXML
    private Label titleTableContainer;
    @FXML
    private Label errorMessageLabel;
    @FXML
    private AnchorPane contentPane;

    @FXML
    @Getter
    private Label totalSizeLabel;

    @FXML
    private Label currentSearchLabel;

    @FXML
    private Label resultsFound;

    private FormRenderer currentFormRenderer;
    private TreeBuilder treeBuilder;

    private Dialogs dialogs;
    private ErrorHandler errorHandler;

    private final BooleanProperty hasChanged = new SimpleBooleanProperty(false);
    private String currentSearchTerm = null;
    private Map<String, Long> searchResultCache = new HashMap<>();
    private Map<String, TreeItem<TreeAttributeWrapper>> treeCache = new HashMap<>();
    private SearchIndex currentSearchIndex = null; // 검색 인덱스(전역 캐시)
    private boolean rebuildingTree = false; // 트리 재구성 중 선택 이벤트 무시
    private TreeItem<TreeAttributeWrapper> initialRootItem; // 최초 로드된 트리(정확한 카운트 유지)
    private Task<Void> currentSearchTask; // 진행 중인 검색 태스크
    private StackPane currentSearchOverlay; // 진행 중 오버레이

    public void init(
            final Dialogs dialogs,
            final ErrorHandler errorHandler,
            final DisplayableText titleValue,
            final DisplayableText textValue,
            final Node footerNode,
            final TreeItem<TreeAttributeWrapper> rootTreeItem,
            final TreeBuilder treeBuilder,
            final ArchiveStep archiveStep
    ) {
        this.dialogs = dialogs;
        this.errorHandler = errorHandler;
        this.borderPane.setBottom(footerNode);
        this.treeView.setRoot(rootTreeItem);
        this.treeView.setCellFactory(new TableCheckBoxTreeCellFactory(this, treeView));
        this.treeBuilder = treeBuilder;
        this.initialRootItem = rootTreeItem; // 초기 트리 보관

        this.archiveStep = archiveStep;

        this.resetSearchButton.textProperty().bind(DisplayableText.of(RESET_SEARCH).bindable());
        this.metaSearchButton.textProperty().bind(DisplayableText.of(META_SEARCH).bindable());
        this.tableSearchButton.textProperty().bind(DisplayableText.of(TABLE_SEARCH).bindable());
        this.recordSearchButton.textProperty().bind(DisplayableText.of(RECORD_SEARCH).bindable());
        this.currentSearchLabel.setText(DisplayableText.of(CURRENT_SEARCH).getText());
        this.resultsFound.setText(DisplayableText.of(RESULTS_FOUND).getText());
        // 초기 화면에서는 결과 라벨 비노출 (검색 수행 시에만 노출)
        this.resultsFound.setVisible(false);
        this.resultsFound.setManaged(false);
        
        // Current Input 초기 상태: 비활성(검색 전에는 클릭/파랑 표시 금지)
        setCurrentInputInteractive(false);
        this.title.textProperty().bind(titleValue.bindable());
        this.text.textProperty().bind(textValue.bindable());

        this.leftTreeBox.prefHeightProperty().bind(container.heightProperty());

        // add listeners
        localeProperty().addListener((observable, oldValue, newValue) -> this.treeView.refresh());
        this.leftTreeBox.prefHeightProperty().bind(container.heightProperty());
        this.hasChanged.addListener((observable, oldValue, hasChanges) -> {
            if (hasChanges) {
                showSaveAndDropButtons();
            } else {
                hideSaveAndDropButtons();
            }
        });

        this.saveChangesButton.setOnAction(() -> {
            val report = currentFormRenderer.saveChanges();

            OptionalHelper.when(report.getFailedMessage())
                    .isPresent(this::showErrorMessage)
                    .orElse(this::hideErrorMessage);
        });

        this.dropChangesButton.setOnAction(() -> {
            hideErrorMessage();
            currentFormRenderer.dropChanges();
        });

        // 동일 항목 재클릭 시에도 강제 새로고침되도록 처리
        this.treeView.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            try {
                if (rebuildingTree) return; // 재구성 중에는 무시
                final TreeItem<TreeAttributeWrapper> selected = treeView.getSelectionModel().getSelectedItem();
                if (selected != null && selected.getValue() != null) {
                    refreshContentPane(selected.getValue());
                }
            } catch (Exception ignore) {
            }
        });

        resetSearchButton.setOnAction(event -> {
            // 진행 중 검색이 있으면 즉시 취소 및 오버레이 제거
            try {
                if (currentSearchTask != null && currentSearchTask.isRunning()) {
                    currentSearchTask.cancel();
                }
                if (currentSearchOverlay != null) {
                    container.getChildren().remove(currentSearchOverlay);
                    currentSearchOverlay = null;
                }
                if (recordSearchButton != null) recordSearchButton.setDisable(false);
            } catch (Exception ignore) {}

            TreeItem<TreeAttributeWrapper> rootNode = initialRootItem;
            TreeAttributeWrapper wrapper = rootNode.getValue();
            currentSearchLabel.setText(DisplayableText.of(CURRENT_SEARCH).getText());
            currentSearchTerm = null;
            currentSearchIndex = null; // SearchIndex 초기화
            searchResultCache.clear(); // 캐시 클리어
            treeCache.clear();
            setCurrentInputInteractive(false);
            // 결과 라벨 숨김 처리
            this.resultsFound.setVisible(false);
            this.resultsFound.setManaged(false);
            // 컨텐츠를 메타데이터(루트)로 우선 전환
            refreshContentPane(wrapper);
            // 트리를 최초 상태로 즉시 복원(재구성 없이 원본을 사용)
            try {
                rebuildingTree = true;
                treeView.getSelectionModel().clearSelection();
                treeView.setRoot(null);
                treeView.setRoot(initialRootItem);
                treeView.getSelectionModel().select(initialRootItem);
                treeView.refresh();
            } finally {
                rebuildingTree = false;
            }
        });

        tableSearchButton.setNormalStateAction(event ->
                dialogs.open(
                        View.SEARCH_TABLE,
                        optionalSearchTerm -> OptionalHelper.when(optionalSearchTerm)
                                .isPresent(searchTerm -> currentFormRenderer.applySearchTerm(searchTerm))
                                .orElse(() -> tableSearchButton.setState(TwoStatesButton.State.NORMAL))
                ));

        tableSearchButton.setBoldStateAction(event -> currentFormRenderer.clearSearchTerm());

        recordSearchButton.setOnAction(event ->
                        dialogs.open(
                                View.SEARCH_RECORD,
                                optionalSearchTerm -> OptionalHelper.when(optionalSearchTerm)
                                        .isPresent(s -> {
                                            this.refreshTree(s);
                                            currentSearchLabel.setText(DisplayableText.of(CURRENT_SEARCH).getText() + s);
                                            contentPane.setVisible(false);
                                        })
                                        .orElse(() -> log.info("check invoked"))
                        )
        );

        val explorer = TreeItemsExplorer.from(rootTreeItem);
        metaSearchButton.setOnAction(event ->
                dialogs.open(View.SEARCH_METADATA,
                        new Tuple<>(
                                explorer,
                                treeItem -> treeView.getSelectionModel().select(treeItem)
                        )));

        treeView.getSelectionModel()
                .selectedItemProperty()
                .addListener(new DeactivatableListener<>(this::onSelectedTreeItemChanged));

        treeView.getSelectionModel().select(rootTreeItem);

    }

    private void refreshTree(String searchTerm) {
        // 검색 시작 UI 처리
        if (searchTerm != null && !searchTerm.isBlank()) {
            this.resultsFound.setText(DisplayableText.of(RESULTS_FOUND).getText() + " Searching...");
            this.resultsFound.setVisible(true);
            this.resultsFound.setManaged(true);
        } else {
            // 검색어가 없으면 결과 라벨 숨김
            this.resultsFound.setVisible(false);
            this.resultsFound.setManaged(false);
        }
        this.currentSearchLabel.setText(DisplayableText.of(CURRENT_SEARCH).getText() + " " + (searchTerm == null ? "" : searchTerm));
        this.currentSearchTerm = searchTerm;
        // 검색 중에는 Current Input을 비활성(파랑/클릭 해제)
        setCurrentInputInteractive(false);

        // 전체 화면을 덮는 오버레이 생성
        final ProgressIndicator indicator = new ProgressIndicator();
        indicator.setPrefSize(80, 80);
        
        final StackPane fullOverlay = new StackPane(indicator);
        fullOverlay.setStyle("-fx-background-color: rgba(255,255,255,0.9);");
        fullOverlay.setPickOnBounds(true);
        
        // 검색 버튼만 비활성화 (사용자가 다른 작업 가능하도록)
        if (recordSearchButton != null) recordSearchButton.setDisable(true);
        
        //  // 모든 버튼과 트리 비활성화 (검색 중 상호작용 차단)
        // if (metaSearchButton != null) metaSearchButton.setDisable(true);
        // if (tableSearchButton != null) tableSearchButton.setDisable(true);
        // if (resetSearchButton != null) resetSearchButton.setDisable(true);
        // treeView.setDisable(true);
        
        // 컨테이너에 오버레이 추가 (모든 상호작용 차단)
        this.container.getChildren().add(fullOverlay);
        this.currentSearchOverlay = fullOverlay;

        Task<Void> task = new Task<>() {
            TreeItem<TreeAttributeWrapper> newRootItem;

            @Override
            protected Void call() {
                SiardArchive cachedArchive = treeBuilder.getSiardArchive();

                // 1) 검색 인덱스 구축: 전 테이블 전수 스캔(한 번)
                if (searchTerm != null && !searchTerm.isBlank()) {
                    currentSearchIndex = new SearchIndex();
                    cachedArchive.getSchemas().forEach(schema -> {
                        if (isCancelled()) return; // 취소 시 빠른 종료
                        schema.getTables().forEach(dbTable -> {
                            if (isCancelled()) return; // 취소 시 빠른 종료
                            try {
                                val dispenser = dbTable.getTable().openRecords();
                                while (true) {
                                    if (isCancelled()) break; // 취소 체크
                                    val rec = dispenser.getWithSearchTerm(searchTerm);
                                    if (rec == null) break;
                                    if (dispenser.anyMatches()) {
                                        currentSearchIndex.addMatch(dbTable, rec.getRecord());
                                    }
                                }
                            } catch (Exception ignore) {}
                        });
                    });
                } else {
                    currentSearchIndex = null; // 검색 아님
                }

                // 2) 트리 생성(필요 시 인덱스를 참조하도록 동일 searchTerm 전달)
                TreeBuilder newTreeBuilder = TreeBuilder.builder()
                        .siardArchive(cachedArchive)
                        .readonly(true)
                        .columnSelectable(true)
                        .searchTerm(searchTerm)
                        .searchIndex(currentSearchIndex)
                        .build();
                newRootItem = newTreeBuilder.customCreateRootItem();
                // 캐시에 저장 (카운트는 저장하지 않음)
                treeCache.put(searchTerm, newRootItem);
                
                return null;
            }

            @Override
            protected void succeeded() {
                // 검색 완료: 결과 라벨 업데이트/토글
                if (searchTerm != null && !searchTerm.isBlank()) {
                    long totalMatched = (currentSearchIndex != null) ? currentSearchIndex.getTotalMatched() : 0L;
                    resultsFound.setText(DisplayableText.of(RESULTS_FOUND).getText() + " " + totalMatched);
                    resultsFound.setVisible(true);
                    resultsFound.setManaged(true);
                } else {
                    resultsFound.setVisible(false);
                    resultsFound.setManaged(false);
                }
                try {
                    rebuildingTree = true;
                    treeView.getSelectionModel().clearSelection();
                    treeView.setRoot(null);
                    treeView.setRoot(newRootItem);
                    if (searchTerm == null || searchTerm.isBlank()) {
                        treeView.getSelectionModel().select(newRootItem);
                    }
                    treeView.refresh();
                } finally {
                    rebuildingTree = false;
                }
                
                // 오버레이 제거 및 검색 버튼 다시 활성화
                container.getChildren().remove(fullOverlay);
                currentSearchOverlay = null;
                if (recordSearchButton != null) recordSearchButton.setDisable(false);

                // if (metaSearchButton != null) metaSearchButton.setDisable(false);
                // if (tableSearchButton != null) tableSearchButton.setDisable(false);
                // if (resetSearchButton != null) resetSearchButton.setDisable(false);
                // treeView.setDisable(false);
                
                // 우측 컨텐츠 보이기
                contentPane.setVisible(true);
                
                // 검색 완료 후 자동으로 통합 검색 결과 표시
                if (searchTerm != null && !searchTerm.isBlank()) {
                    // 통합 검색 결과 자동 표시
                    try {
                        val unifiedForm = RowsOverviewForm.createUnifiedSearchResult(treeBuilder.getSiardArchive(), searchTerm, currentSearchIndex);
                        currentFormRenderer = FormRenderer.builder()
                                .renderableForm(unifiedForm)
                                .hasChanged(hasChanged)
                                .errorHandler(errorHandler)
                                .build();

                        val vbox = currentFormRenderer.getRendered();
                        AnchorPane.setLeftAnchor(vbox, 0D);
                        AnchorPane.setRightAnchor(vbox, 0D);
                        VBox.setVgrow(vbox, Priority.ALWAYS);
                        vbox.setPadding(new Insets(25));

                        contentPane.getChildren().setAll(vbox);
                        contentPane.setVisible(true);
                    } catch (Exception e) {
                        log.error("Failed to show unified search results", e);
                        // 오류 시 첫 번째 RECORD 노드 선택
                        TreeItem<TreeAttributeWrapper> recordNode = findFirstRecordNode(newRootItem);
                        if (recordNode != null) {
                            treeView.getSelectionModel().select(recordNode);
                        }
                    }
                }
                
                // Current Input은 검색 후에만 활성화
                setCurrentInputInteractive(searchTerm != null && !searchTerm.isBlank());
                currentSearchTask = null;
            }

            @Override
            protected void failed() {
                // 오버레이 제거 및 검색 버튼 다시 활성화
                container.getChildren().remove(fullOverlay);
                currentSearchOverlay = null;
                if (recordSearchButton != null) recordSearchButton.setDisable(false);
                currentSearchTask = null;
            }
        };

        this.currentSearchTask = task;
        new Thread(task).start();
    }

    private void onSelectedTreeItemChanged(final DeactivatableListener.Change<TreeItem<TreeAttributeWrapper>> change) {
        if (rebuildingTree) {
            return; // 재구성 중에는 선택 변경 무시
        }
        if (!hasChanged.get()) {
            contentPane.setVisible(true);
            refreshContentPane(change.getNewValue().getValue());
            return;
        }

        this.dialogs.open(
                View.UNSAVED_CHANGES,
                result -> {
                    switch (result) {
                        case CANCEL:
                            change.getDeactivatableListener().deactivate();
                            change.getOldValue().ifPresent(previousSelectedItem ->
                                    treeView.getSelectionModel()
                                            .select(previousSelectedItem));
                            change.getDeactivatableListener().activate();
                            break;
                        case DROP_CHANGES:
                            currentFormRenderer.dropChanges();
                            refreshContentPane(change.getNewValue().getValue());
                            break;
                        case SAVE_CHANGES:
                            val report = currentFormRenderer.saveChanges();
                            ifPresentOrElse(
                                    report.getFailedMessage(),
                                    errorMessage -> {
                                        showErrorMessage(errorMessage);
                                        change.getDeactivatableListener().deactivate();
                                        change.getOldValue().ifPresent(previousSelectedItem ->
                                                treeView.getSelectionModel()
                                                        .select(previousSelectedItem));
                                        change.getDeactivatableListener().activate();
                                    },
                                    () -> refreshContentPane(change.getNewValue().getValue())
                            );
                            break;
                    }
                });
    }

    private void refreshContentPane(TreeAttributeWrapper wrapper) {
        hideSaveAndDropButtons();
        hideErrorMessage();

        final RenderableForm form = wrapper.getRenderableForm();
        currentFormRenderer = FormRenderer.builder()
                .renderableForm(form)
                .hasChanged(hasChanged)
                .errorHandler(errorHandler)
                .build();

        this.titleTableContainer.textProperty().bind(wrapper.getViewTitle().bindable());
        this.tableSearchButton.setVisible(currentFormRenderer.hasSearchableData());

        val vbox = currentFormRenderer.getRendered();
        AnchorPane.setLeftAnchor(vbox, 0D);
        AnchorPane.setRightAnchor(vbox, 0D);
        VBox.setVgrow(vbox, Priority.ALWAYS);
        vbox.setPadding(new Insets(25));

        this.contentPane.getChildren().setAll(vbox);
    }

    private void showSaveAndDropButtons() {
        this.saveChangesButton.setVisible(true);
        this.saveChangesButton.setManaged(true);
        this.dropChangesButton.setVisible(true);
        this.dropChangesButton.setManaged(true);
    }

    private void hideSaveAndDropButtons() {
        this.saveChangesButton.setVisible(false);
        this.saveChangesButton.setManaged(false);
        this.dropChangesButton.setVisible(false);
        this.dropChangesButton.setManaged(false);
    }

    private void showErrorMessage(final String message) {
        this.errorMessageLabel.setText(message);
        this.errorMessageLabel.setVisible(true);
        this.errorMessageLabel.setManaged(true);
    }

    private void hideErrorMessage() {
        this.errorMessageLabel.setVisible(false);
        this.errorMessageLabel.setManaged(false);
    }

    public static LoadedView<GenericArchiveBrowserPresenter> load(
            final Dialogs dialogs,
            final ErrorHandler errorHandler,
            final DisplayableText title,
            final DisplayableText text,
            final Node footer,
            final TreeItem<TreeAttributeWrapper> rootTreeItem,
            final TreeBuilder treeBuilder,
            final ArchiveStep archiveStep
    ) {
        val loaded = FXMLLoadHelper.<GenericArchiveBrowserPresenter>load("fxml/archive-browser.fxml");
        loaded.getController()
                .init(dialogs,
                        errorHandler,
                        title,
                        text,
                        footer,
                        rootTreeItem,
                        treeBuilder,
                        archiveStep);
        return loaded;
    }

    @Getter
    @RequiredArgsConstructor
    public enum ArchiveStep {
        PREVIEW,
        ENTITY_SELECTION,
        COLUMN_SFTP_SELECTION,
        OPEN_ARCHIVE,
        ;
    }

    //preview 화면에는 recordSearchButton 필요 없음
    public void removeRecordSearchButton() {
        if (recordSearchButton != null) {
            recordSearchButton.setVisible(false);
            recordSearchButton.setManaged(false); // 레이아웃에서 공간도 차지하지 않게 설정
        }
    }

    //preview 화면에는 recordSearchButton 필요 없음
    public void removeResetButton() {
        if (resetSearchButton != null) {
            resetSearchButton.setVisible(false);
            resetSearchButton.setManaged(false); // 레이아웃에서 공간도 차지하지 않게 설정
        }
    }

    //Open 화면에는 테이블 사이즈 크기 필요 없음 - 어차피 0Byte 로 나오고 있음
    public void removeTableSize() {
        if (totalSizeLabel != null) {
            totalSizeLabel.setVisible(false);
            totalSizeLabel.setManaged(false); // 레이아웃에서 공간도 차지하지 않게 설정
        }
    }

    /**
     * Current Input 클릭 시 통합 검색 결과를 표시
     */
    private void onCurrentInputClicked(MouseEvent event) {
        if (currentSearchTerm != null && !currentSearchTerm.isBlank() && currentSearchIndex != null) {
            // 통합 검색 결과 표시 (캐시된 결과 사용)
            try {
                val unifiedForm = RowsOverviewForm.createUnifiedSearchResult(treeBuilder.getSiardArchive(), currentSearchTerm, currentSearchIndex );
                currentFormRenderer = FormRenderer.builder()
                        .renderableForm(unifiedForm)
                        .hasChanged(hasChanged)
                        .errorHandler(errorHandler)
                        .build();

                val vbox = currentFormRenderer.getRendered();
                AnchorPane.setLeftAnchor(vbox, 0D);
                AnchorPane.setRightAnchor(vbox, 0D);
                VBox.setVgrow(vbox, Priority.ALWAYS);
                vbox.setPadding(new Insets(25));

                this.contentPane.getChildren().setAll(vbox);
                this.contentPane.setVisible(true);
            } catch (Exception e) {
                log.error("Failed to show unified search results", e);
            }
        }
    }

    // Current Input 라벨의 클릭 가능/스타일을 토글하는 헬퍼
    private void setCurrentInputInteractive(boolean interactive) {
        if (interactive) {
            this.currentSearchLabel.setOnMouseClicked(this::onCurrentInputClicked);
            this.currentSearchLabel.setStyle("-fx-text-fill: #0066cc; -fx-underline: true; -fx-cursor: hand;");
        } else {
            this.currentSearchLabel.setOnMouseClicked(null);
            this.currentSearchLabel.setStyle("");
        }
    }

    /**
     * 트리에서 모든 RECORD 노드의 매치된 행 수를 합산
     */
    private long calculateTotalMatchedRows(TreeItem<TreeAttributeWrapper> rootItem) {
        long total = 0;
        if (rootItem == null) return total;
        
        // 모든 자식 노드를 재귀적으로 탐색
        for (TreeItem<TreeAttributeWrapper> child : rootItem.getChildren()) {
            TreeAttributeWrapper wrapper = child.getValue();
            if (wrapper != null && wrapper.getDatabaseAttribute() == TreeAttributeWrapper.DatabaseAttribute.RECORD) {
                // RECORD 노드인 경우 매치된 행 수 추가
                try {
                    val recordDataSource = new RowsOverviewForm.RecordDataSource(wrapper.getDatabaseTable(), currentSearchTerm);
                    long matchedRows = recordDataSource.getNumberOfItems();
                    total += matchedRows;
                    log.info("Table {} has {} matched rows", wrapper.getDatabaseTable().getName(), matchedRows);
                } catch (Exception e) {
                    log.error("Error calculating matched rows for table {}", wrapper.getDatabaseTable().getName(), e);
                }
            }
            // 재귀적으로 자식 노드들도 탐색
            total += calculateTotalMatchedRows(child);
        }
        return total;
    }

    // 새 트리에서 첫 번째 RECORD 노드를 찾는다.
    private TreeItem<TreeAttributeWrapper> findFirstRecordNode(TreeItem<TreeAttributeWrapper> root) {
        if (root == null) return null;
        if (root.getValue() != null && root.getValue().getDatabaseAttribute() == TreeAttributeWrapper.DatabaseAttribute.RECORD) {
            return root;
        }
        for (TreeItem<TreeAttributeWrapper> child : root.getChildren()) {
            TreeItem<TreeAttributeWrapper> found = findFirstRecordNode(child);
            if (found != null) return found;
        }
        return null;
    }

}
