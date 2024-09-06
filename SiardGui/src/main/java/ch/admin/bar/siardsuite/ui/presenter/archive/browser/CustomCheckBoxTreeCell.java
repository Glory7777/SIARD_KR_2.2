package ch.admin.bar.siardsuite.ui.presenter.archive.browser;

import ch.admin.bar.siardsuite.model.TreeAttributeWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.util.Callback;

/**
 * <p>{@link CheckBoxTreeCell} 을 상속하는 클래스.</p>
 * {@link CheckBoxTreeCell#updateItem(Object, boolean)}를 재정의하여 체크박스가 필요한 목록과 체크박스를 연동한다
 */
public class CustomCheckBoxTreeCell extends CheckBoxTreeCell<TreeAttributeWrapper> {

    private CustomCheckBoxTreeCell(Callback<TreeItem<TreeAttributeWrapper>, ObservableValue<Boolean>> callback) {
        super(callback);
    }

    public static CustomCheckBoxTreeCell initCheckBoxTree(Callback<TreeItem<TreeAttributeWrapper>, ObservableValue<Boolean>> callback) {
        return new CustomCheckBoxTreeCell(callback);
    }

    /**
     * 체크박스에 하위 트리까지 전부 상위 노드의 상태로 업데이트
     */
    @Override
    public void updateItem(TreeAttributeWrapper item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item.getDisplayName());

            if (item.shouldHaveCheckBox() && item.isTransferable(item.getDatabaseAttribute())) {
                CheckBox checkBox = new CheckBox();

                checkBox.selectedProperty().bindBidirectional(item.selectedProperty());
                checkBox.selectedProperty().addListener((observable, wasSelected, isNowSelected) -> {
                    // 속성을 아래 노드에도 세팅하기 위해 TreeItem을 호출
                    propagateSelection(getTreeItem(), isNowSelected);
                });
                setGraphic(checkBox);
            } else {
                setGraphic(null);
            }
        }
    }
    
    private void propagateSelection(TreeItem<TreeAttributeWrapper> currentTreeItem, boolean isSelected) {
        if (currentTreeItem == null || currentTreeItem.getChildren().isEmpty()) {
            return;
        }

        // 현재 선택된 박스를 업데이트
        setSelection(currentTreeItem.getValue(), isSelected);
        
        // 자식 노드 체크박스 상태 업데이트
        for (TreeItem<TreeAttributeWrapper> child : currentTreeItem.getChildren()) {
            TreeAttributeWrapper childValue = child.getValue();
            if (childValue != null) {
                if (childValue.isTransferable()) {
                    setSelection(childValue, isSelected);
                }
                if (childValue.shouldPropagate()) { // 부모 노드인 경우 하위 노드까지 체크 박스 상태 업데이트
                    propagateSelection(child, isSelected);
                }
            }
        }
    }

    private void setSelection(TreeAttributeWrapper currentItem, boolean isSelected) {
        if (currentItem == null) return;
        currentItem.setSelected(isSelected);
    }

}
