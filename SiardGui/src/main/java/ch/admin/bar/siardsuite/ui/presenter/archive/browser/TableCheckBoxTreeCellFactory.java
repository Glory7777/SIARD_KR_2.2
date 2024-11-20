package ch.admin.bar.siardsuite.ui.presenter.archive.browser;

import ch.admin.bar.siardsuite.model.TreeAttributeWrapper;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

/**
 * <p>체크박스 팩토리 </p>
 * 스키마와 테이블에 체크 박스를 생성
 */
public class TableCheckBoxTreeCellFactory implements Callback<TreeView<TreeAttributeWrapper>, TreeCell<TreeAttributeWrapper>> {

    private final GenericArchiveBrowserPresenter genericArchiveBrowserPresenter;
    private final TreeView<TreeAttributeWrapper> treeView;

    public TableCheckBoxTreeCellFactory(GenericArchiveBrowserPresenter presenter, TreeView<TreeAttributeWrapper> treeView) {
        this.genericArchiveBrowserPresenter = presenter;
        this.treeView = treeView;
    }

    @Override
    public TreeCell<TreeAttributeWrapper> call(TreeView<TreeAttributeWrapper> treeViews) {
        return CustomCheckBoxTreeCell.initCheckBoxTree(param -> {
                    if (param != null && param.getValue().shouldHaveCheckBox()) {
                        return param.getValue().selectedProperty();
                    } else {
                        return null;
                    }
                },
                genericArchiveBrowserPresenter,
                treeView);
    }

}
