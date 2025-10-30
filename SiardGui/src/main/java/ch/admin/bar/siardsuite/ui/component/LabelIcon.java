package ch.admin.bar.siardsuite.ui.component;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class LabelIcon extends Label {

  public LabelIcon(String text, Integer pos, IconView.IconType type) {
    this.setText(text);
    this.getStyleClass().addAll("view-text", "label-icon-left");
    this.setContentDisplay(ContentDisplay.LEFT);
    ImageView imageView;
    if (type != null) {
      imageView = new IconView(pos, type);
    } else {
      imageView = new IconView(pos, IconView.IconType.PLACEHOLDER);
    }
    this.setGraphic(imageView);
  }

}
