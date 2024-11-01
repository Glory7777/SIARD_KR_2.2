package ch.admin.bar.siardsuite.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.util.List;
import java.util.stream.Collectors;

public class TreeItemUtil extends TreeItem<TreeAttributeWrapper> {

    public static String getSchemaName(TreeItem<TreeAttributeWrapper> treeItem) {
        if (treeItem == null) return null;
        boolean tableAttr = treeItem.getValue().isTableAttr();
        if (tableAttr) return treeItem.getParent().getParent().getValue().getDisplayName();
        return null;
    }

    public static ObservableList<TreeItem<TreeAttributeWrapper>> getActualChildren(TreeItem<TreeAttributeWrapper> treeItem) {
        if (treeItem == null || treeItem.isLeaf()) return null;
        boolean tableAttr = treeItem.getValue().isTableAttr();
        if (tableAttr) {
            return treeItem.getChildren()
                    .stream()
                    .flatMap(t -> t.getChildren().stream())
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }
        return FXCollections.observableArrayList();
    }

    public static List<TreeItem<TreeAttributeWrapper>> getSchemaFromRoot(TreeItem<TreeAttributeWrapper> root) {
        if (root.getParent() != null) {
            throw new RuntimeException("root item must be given");
        }

        return root.getChildren()
                .stream().filter(c -> c.getValue().isSchemaAttr())
                .toList();
    }

    public static List<TreeItem<TreeAttributeWrapper>> findChildrenTreeItemList(TreeItem<TreeAttributeWrapper> treeItem,
                                                                                List<TreeItem<TreeAttributeWrapper>> childrenTreeItemList,
                                                                                TreeAttributeWrapper.DatabaseAttribute childAttribute) {
        if (treeItem == null || treeItem.isLeaf()) return childrenTreeItemList;
        for (TreeItem<TreeAttributeWrapper> child : treeItem.getChildren()) {
            TreeAttributeWrapper value = child.getValue();
            if (value == null) break;
            if (value.getDatabaseAttribute() == childAttribute) childrenTreeItemList.add(child);
            findChildrenTreeItemList(child, childrenTreeItemList, childAttribute);
        }
        return childrenTreeItemList;
    }

}
