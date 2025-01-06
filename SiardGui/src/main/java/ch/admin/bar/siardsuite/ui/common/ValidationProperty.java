package ch.admin.bar.siardsuite.ui.common;

import ch.admin.bar.siardsuite.util.I18n;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Control;
import javafx.scene.control.ComboBox;

public class ValidationProperty {
    private final Control field; // TextField나 ComboBox 등 다양한 컨트롤을 수용
    //private final TextField field;
    private final Label validationMsgField;
    private final String validationMsg;
    private boolean isValid = true;

    public ValidationProperty(Control field, Label validationMsgField, String validationMsg) {
        this.field = field;
        this.validationMsgField = validationMsgField;
        this.validationMsg = validationMsg;
    }

    public boolean validate() {
        if (field instanceof TextField) {
            // TextField 검증
            TextField textField = (TextField) field;
            if (textField.getText() == null || textField.getText().isEmpty()) {
                I18n.bind(this.validationMsgField.textProperty(), this.validationMsg);
                this.validationMsgField.setVisible(true);
                this.isValid = false;
            } else {
                this.validationMsgField.setVisible(false);
            }
        } else if (field instanceof ComboBox) {
            // ComboBox 검증
            ComboBox<?> comboBox = (ComboBox<?>) field;
            if (comboBox.getValue() == null) {
                I18n.bind(this.validationMsgField.textProperty(), this.validationMsg);
                this.validationMsgField.setVisible(true);
                this.isValid = false;
            } else {
                this.validationMsgField.setVisible(false);
            }
        }
        return this.isValid;
    }


    public void reset() {
        isValid = true;
        this.validationMsgField.setVisible(false);
    }
}
