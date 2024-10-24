package ch.admin.bar.siardsuite.model;

import javafx.scene.control.TreeItem;

public class TreeItemWrapper extends TreeItem<TreeAttributeWrapper> {

    public static String getSchemaName(TreeItem<TreeAttributeWrapper> treeItem) {
        if (treeItem == null) return null;
        boolean tableAttr = treeItem.getValue().isTableAttr();
        if (tableAttr) return treeItem.getParent().getParent().getValue().getDisplayName();
        return null;
    }

}
