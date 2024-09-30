package ch.admin.bar.siardsuite.ui.common;

import ch.admin.bar.siardsuite.ui.component.IconView;
import ch.admin.bar.siardsuite.ui.component.LabelIcon;
import javafx.collections.ObservableList;
import javafx.scene.Node;

// understands a progress item (downlaod, preview, export)
public class ProgressItem {
    private final int pos;
    private ObservableList<Node> children;
    private final LabelIcon labelIcon;

    private final IconView.IconType loading = IconView.IconType.LOADING;
    private final IconView.IconType ok = IconView.IconType.OK;

    public ProgressItem(int pos, String text) {
        this.pos = pos;
        this.labelIcon = new LabelIcon(text, pos, IconView.IconType.LOADING);
    }

    public LabelIcon icon() {
        return this.labelIcon;
    }

    public void ok() {
        this.labelIcon.setGraphic(new IconView(this.pos, ok));
    }
}
