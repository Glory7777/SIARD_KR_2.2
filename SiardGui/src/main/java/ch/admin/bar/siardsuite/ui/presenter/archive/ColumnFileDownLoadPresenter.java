package ch.admin.bar.siardsuite.ui.presenter.archive;

import ch.admin.bar.siard2.api.Archive;
import ch.admin.bar.siard2.api.ext.form.FormData;
import ch.admin.bar.siardsuite.framework.ServicesFacade;
import ch.admin.bar.siardsuite.framework.dialogs.Dialogs;
import ch.admin.bar.siardsuite.framework.errors.ErrorHandler;
import ch.admin.bar.siardsuite.framework.i18n.DisplayableText;
import ch.admin.bar.siardsuite.framework.i18n.keys.I18nKey;
import ch.admin.bar.siardsuite.framework.steps.StepperNavigator;
import ch.admin.bar.siardsuite.framework.view.LoadedView;
import ch.admin.bar.siardsuite.model.TreeAttributeWrapper;
import ch.admin.bar.siardsuite.model.TreeItemWrapper;
import ch.admin.bar.siardsuite.model.Tuple;
import ch.admin.bar.siardsuite.model.database.SiardArchive;
import ch.admin.bar.siardsuite.service.database.model.DbmsConnectionData;
import ch.admin.bar.siardsuite.ui.View;
import ch.admin.bar.siardsuite.ui.component.ButtonBox;
import ch.admin.bar.siardsuite.ui.presenter.archive.browser.ColumnFileDownloadPresenter;
import ch.admin.bar.siardsuite.ui.presenter.archive.browser.TreeBuilder;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import lombok.val;

import java.util.*;

import static ch.admin.bar.siardsuite.model.TreeItemWrapper.*;
import static ch.admin.bar.siardsuite.ui.component.ButtonBox.Type.DEFAULT;

/**
 * 선택한 엔티티 중 파일 다운로드할 컬럼 선택화면
 */
public class ColumnFileDownLoadPresenter {

    private static final I18nKey TITLE = I18nKey.of("columnFileDownload.view.title");
    private static final I18nKey TEXT = I18nKey.of("columnFileDownload.view.text");
    private final Set<FormData> formDataSet = new LinkedHashSet<>();

    private ButtonBox buttonsBox;
    private LoadedView<ColumnFileDownloadPresenter> loadedView;

    public Node getView() {
        return loadedView.getNode();
    }

    public void init(
            final Archive archive,
            final DbmsConnectionData connectionData,
            final StepperNavigator<Tuple<Archive, DbmsConnectionData>> navigator,
            final Dialogs dialogs,
            final ErrorHandler errorHandler
    ) {
        val archiveBrowserView = new TreeBuilder(new SiardArchive("", archive, true), true, true);
        TreeItem<TreeAttributeWrapper> rootItem = archiveBrowserView.createRootItemWithSelectedSchemas();

        this.buttonsBox = new ButtonBox().make(DEFAULT);

        // next 버튼 이벤트 등록
        buttonsBox.next().setOnAction(
                (event) -> {
                    setFormDataSet(rootItem);
                    archive.setFormDataSet(formDataSet);
                    navigator.next(new Tuple<>(archive, connectionData));
                }
        );

        buttonsBox.previous().setOnAction((event) -> navigator.previous());
        buttonsBox.cancel().setOnAction(
                (event) -> dialogs
                        .open(View.ARCHIVE_ABORT_DIALOG));

        this.loadedView = ColumnFileDownloadPresenter.load(
                dialogs,
                errorHandler,
                DisplayableText.of(TITLE),
                DisplayableText.of(TEXT),
                this.buttonsBox,
                rootItem
        );
    }

    private void setFormDataSet(TreeItem<TreeAttributeWrapper> root) {
        LinkedList<TreeItem<TreeAttributeWrapper>> tableTree = findTableTree(new LinkedList<>(), root);
        for (TreeItem<TreeAttributeWrapper> item : tableTree) {
            TreeAttributeWrapper value = item.getValue();
            if (value == null) return;
            FormData formData = value.getFormData();
            String table = value.getDisplayName();
            String schema = getSchemaName(item);

            item.getChildren()
                    .stream()
                    .flatMap(c -> c.getChildren().stream())
                    .map(TreeItem::getValue)
                    .filter(childValue -> childValue.isSelectedColumn() && childValue.notCheckedForDownload())
                    .forEach(childValue -> {
                        formData.updateTableData(schema, table, childValue.getDisplayName());
                        childValue.markAsCheckedForDownload();
                    });

            formDataSet.add(formData);
        }
    }

    private LinkedList<TreeItem<TreeAttributeWrapper>> findTableTree(LinkedList<TreeItem<TreeAttributeWrapper>> queue, TreeItem<TreeAttributeWrapper> item) {
        if (item == null) return queue;
        for (TreeItem<TreeAttributeWrapper> child : item.getChildren()) {
            TreeAttributeWrapper value = child.getValue();
            if (value.notCheckedForDownload() && value.isTableAttr()) {
                value.markAsCheckedForDownload();
                queue.add(child);
            }
            findTableTree(queue, child);
        }
        return queue;
    }

    public static LoadedView<ColumnFileDownLoadPresenter> load(
            final Tuple<Archive, DbmsConnectionData> data,
            final StepperNavigator<Tuple<Archive, DbmsConnectionData>> navigator,
            final ServicesFacade servicesFacade
    ) {
        val browser = new ColumnFileDownLoadPresenter();
        browser.init(
                data.getValue1(),
                data.getValue2(),
                navigator,
                servicesFacade.dialogs(),
                servicesFacade.errorHandler()
        );
        return new LoadedView<>(browser::getView, browser);
    }
}
