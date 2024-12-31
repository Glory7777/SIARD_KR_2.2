package ch.admin.bar.siardsuite.ui.presenter.archive.browser;

import ch.admin.bar.siard2.cmd.utils.ByteFormatter;
import ch.admin.bar.siardsuite.model.TreeAttributeWrapper;
import ch.admin.bar.siardsuite.model.TreeItemUtil;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.util.Callback;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ch.admin.bar.siardsuite.model.TreeAttributeWrapper.DatabaseAttribute.TABLE;
import static ch.admin.bar.siardsuite.model.TreeItemUtil.findChildrenTreeItemList;

/**
 * <p>{@link CheckBoxTreeCell} 을 상속하는 클래스.</p>
 * {@link CheckBoxTreeCell#updateItem(Object, boolean)}를 재정의하여 체크박스가 필요한 목록과 체크박스를 연동한다
 */
public class CustomCheckBoxTreeCell extends CheckBoxTreeCell<TreeAttributeWrapper> {

    private final GenericArchiveBrowserPresenter genericArchiveBrowserPresenter;
    private final TreeView<TreeAttributeWrapper> treeView;

     public static long selectedTotalSize = 0;

    private CustomCheckBoxTreeCell(Callback<TreeItem<TreeAttributeWrapper>, ObservableValue<Boolean>> callback,
                                   GenericArchiveBrowserPresenter genericArchiveBrowserPresenter,
                                   TreeView<TreeAttributeWrapper> treeView) {
        super(callback);
        this.genericArchiveBrowserPresenter = genericArchiveBrowserPresenter;
        this.treeView = treeView;
        bindNodes();
    }

    public static CustomCheckBoxTreeCell initCheckBoxTree(Callback<TreeItem<TreeAttributeWrapper>, ObservableValue<Boolean>> callback,
                                                          GenericArchiveBrowserPresenter genericArchiveBrowserPresenter,
                                                          TreeView<TreeAttributeWrapper> treeView) {
        return new CustomCheckBoxTreeCell(callback, genericArchiveBrowserPresenter, treeView);
    }

    /**
     * 동작이 일어날 때 노드와 트리를 그리는 메서드
     */
    @Override
    public void updateItem(TreeAttributeWrapper item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
            return;
        }

        setText(getDisplayText(item));

        if (item.shouldHaveCheckBox() && item.isTransferable(item.getDatabaseAttribute())) {
            CheckBox checkBox = new CheckBox();

            // 중복 바인딩 방지
            checkBox.selectedProperty().unbindBidirectional(item.selectedProperty());
            checkBox.selectedProperty().bindBidirectional(item.selectedProperty());

            checkBox.selectedProperty().addListener((observable, wasSelected, isNowSelected) -> {
                updateTotalSelectedSize();
            });
            setGraphic(checkBox);
        } else {
            setGraphic(null);
        }

    }

    /**
     * 엔티티, 스키마 별로 용량 표기
     *
     * @param item
     * @return
     */
    private String getDisplayText(TreeAttributeWrapper item) {
        String displayName = item.getDisplayName();
        String formattedSize = getFormattedSize(item);
        return formattedSize == null ? displayName : displayName + formattedSize;
    }

    private String getFormattedSize(TreeAttributeWrapper item) {
        String formatted = item.getFormattedSize();
        return formatted == null || formatted.isBlank() ? null : " (" + item.getFormattedSize() + ")";
    }

    /**
     * 스키마 - 테이블 간 노드 상태 전파
     * 스키마에 이벤트 리스너를 붙여 스키마가 선택되면 전체 테이블이 선택. 
     * 테이블만 단독 선택도 가능
     */
    private void bindNodes() {
        TreeItem<TreeAttributeWrapper> root = treeView.getRoot();
        findChildrenTreeItemList(root,
                new ArrayList<>(),
                TreeAttributeWrapper.DatabaseAttribute.SCHEMA)
                .forEach(this::bindParentToChildren);
    }

    // schema -> table
    private void bindParentToChildren(TreeItem<TreeAttributeWrapper> parent) {
        if (parent == null) return;
        Optional.ofNullable(parent.getValue())
                .filter(TreeAttributeWrapper::isSchemaAttr)
                .ifPresent(parentTreeAttribute -> {
                            List<TreeItem<TreeAttributeWrapper>> children = findChildrenTreeItemList(parent, new ArrayList<>(), TABLE);
                            parentTreeAttribute.selectedProperty().addListener((observable, wasSelected, isNowSelected) -> {
                                // Update all children when parent changes
                                children.forEach(child -> {
                                    TreeAttributeWrapper childAttribute = child.getValue();
                                    if (childAttribute != null) {
                                        childAttribute.setSelected(isNowSelected);
                                    }
                                });
                            });
                        }
                );
    }

    private void updateTotalSelectedSize() {
        long totalSize = calculateTotalSize(getTreeView().getRoot());
        updateTotalSizeLabel(totalSize);
        selectedTotalSize = totalSize;
    }

    private long calculateTotalSize(TreeItem<TreeAttributeWrapper> root) {
        long totalSize = 0;

        if (root == null || root.getChildren().isEmpty()) {
            return totalSize;
        }

        for (TreeItem<TreeAttributeWrapper> child : root.getChildren()) {
            TreeAttributeWrapper value = child.getValue();
            if (value != null && value.isTransferable() && value.isSelected()) {
                totalSize += value.getSize();
            }
            totalSize += calculateTotalSize(child);
        }
        return totalSize;
    }

    private void updateTotalSizeLabel(long totalSize) {
        genericArchiveBrowserPresenter.getTotalSizeLabel().setText(ByteFormatter.convertToBestFitUnit(totalSize));
    }

}
