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
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

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

    private static final I18nKey META_SEARCH = I18nKey.of("tableContainer.metaSearchButton");
    private static final I18nKey TABLE_SEARCH = I18nKey.of("tableContainer.tableSearchButton");
    private static final I18nKey RECORD_SEARCH = I18nKey.of("tableContainer.recordSearchButton");
    private static final I18nKey RESET_SEARCH = I18nKey.of("tableContainer.resetSearchButton");
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

        this.archiveStep = archiveStep;
        this.resetSearchButton.textProperty().bind(DisplayableText.of(RESET_SEARCH).bindable());
        this.metaSearchButton.textProperty().bind(DisplayableText.of(META_SEARCH).bindable());
        this.tableSearchButton.textProperty().bind(DisplayableText.of(TABLE_SEARCH).bindable());
        this.recordSearchButton.textProperty().bind(DisplayableText.of(RECORD_SEARCH).bindable());
        this.currentSearchLabel.setText(DisplayableText.of(CURRENT_SEARCH).getText());
        this.resultsFound.setText(DisplayableText.of(RESULTS_FOUND).getText());
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

        resetSearchButton.setOnAction(event -> {
            TreeItem<TreeAttributeWrapper> rootNode = treeView.getRoot();
             TreeAttributeWrapper wrapper = rootNode.getValue();
              currentSearchLabel.setText("");
               refreshContentPane(wrapper); // 초기 상태로 리셋
                this.refreshTree(null);
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
        SiardArchive cachedArchive = treeBuilder.getSiardArchive();
        TreeItem<TreeAttributeWrapper> root = treeView.getRoot();
        List<TreeItem<TreeAttributeWrapper>> recordTreeItemList = findChildrenTreeItemList(root, new ArrayList<>(), TreeAttributeWrapper.DatabaseAttribute.RECORD);

        long resultsFound = recordTreeItemList.stream()
                .map(TreeItem::getValue)
                .mapToLong(
                        attr -> {
                            attr.setTableToDefault();
                            RowsOverviewForm.RecordDataSource recordDataSource = new RowsOverviewForm.RecordDataSource(attr.getDatabaseTable().getTable(), searchTerm);
                            recordDataSource.load(0, (int) attr.getDatabaseTable().getNumberOfRows());
                            return recordDataSource.getNumberOfItems();
                        }
                )
                .sum();

        System.out.println("resultsFound = " + resultsFound);
        this.resultsFound.setText(DisplayableText.of(RESULTS_FOUND).getText() + resultsFound);

        TreeBuilder newTreeBuilder = TreeBuilder.builder()
                .siardArchive(cachedArchive)
                .readonly(true)
                .columnSelectable(true)
                .searchTerm(searchTerm)
                .build();
        TreeItem<TreeAttributeWrapper> rootItem = newTreeBuilder.customCreateRootItem();
        treeView.setRoot(rootItem);
    }

    private void onSelectedTreeItemChanged(final DeactivatableListener.Change<TreeItem<TreeAttributeWrapper>> change) {
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

}
