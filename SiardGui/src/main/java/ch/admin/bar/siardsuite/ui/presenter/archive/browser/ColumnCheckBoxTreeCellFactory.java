package ch.admin.bar.siardsuite.ui.presenter.archive.browser;

import ch.admin.bar.siardsuite.model.TreeAttributeWrapper;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

/**
 * <p>체크박스 팩토리 </p>
 * 스키마와 테이블에 체크 박스의 동작을 정의하는 인스턴스 생성
 */
public class ColumnCheckBoxTreeCellFactory implements Callback<TreeView<TreeAttributeWrapper>, TreeCell<TreeAttributeWrapper>> {

    @Override
    public TreeCell<TreeAttributeWrapper> call(TreeView<TreeAttributeWrapper> treeView) {
        return ColumnCheckBoxTreeCell.initCheckBoxTree(param -> {
                    if (param != null && param.getValue().shouldHaveCheckBox()) {
                        return param.getValue().selectedProperty();
                    } else {
                        return null;
                    }
                }
        );
    }

}
