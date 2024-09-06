package ch.admin.bar.siardsuite.ui.presenter.archive.browser;

import ch.admin.bar.siardsuite.model.TreeAttributeWrapper;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

/**
 * <p>체크박스 팩토리 </p>
 * 스키마와 테이블에 체크 박스를 생성
 */
public class TableCheckBoxTreeCellFactory implements Callback<TreeView<TreeAttributeWrapper>, TreeCell<TreeAttributeWrapper>> {

    @Override
    public TreeCell<TreeAttributeWrapper> call(TreeView<TreeAttributeWrapper> treeView) {
        return CustomCheckBoxTreeCell.initCheckBoxTree(param -> {
            if (param != null && param.getValue().shouldHaveCheckBox()) {
                return param.getValue().selectedProperty();
            } else {
                return null;
            }
        });
    }

}
