package ch.admin.bar.siardsuite.ui.presenter.archive.browser.forms;

import ch.admin.bar.siard2.api.ext.form.FormData;
import ch.admin.bar.siardsuite.model.TreeAttributeWrapper;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileCopyFormData {

    private final TextField targetDirectoryField = new TextField();
    private final TreeAttributeWrapper treeAttributeWrapper;

    public FileCopyFormData(TreeAttributeWrapper treeAttributeWrapper) {
        this.treeAttributeWrapper = treeAttributeWrapper;
    }

    public Tab getFileCopyTab() {
        Tab fileCopyTab = new Tab("File Copy");
        VBox fileCopyLayout = new VBox(10);
        fileCopyLayout.setPadding(new Insets(30));
        fileCopyLayout.getChildren().addAll(getBrowseBox(), getSaveButton());
        fileCopyTab.setClosable(false);
        fileCopyTab.setContent(fileCopyLayout);
        return fileCopyTab;
    }

    private HBox getBrowseBox() {
        HBox directoryBox = new HBox(20);
        targetDirectoryField.setPromptText("No Directory Selected");
        targetDirectoryField.setPrefWidth(500);

        Button browseButton = new Button("Browse");

        browseButton.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select directory");

            File selectedDirectory = directoryChooser.showDialog(directoryBox.getScene().getWindow());

            if (selectedDirectory != null) {
                targetDirectoryField.setText(selectedDirectory.getAbsolutePath());
            }
        });

        directoryBox.getChildren().add(targetDirectoryField);
        directoryBox.getChildren().add(browseButton);
        return directoryBox;
    }

    private Button getSaveButton() {
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String targetDirectoryFieldText = targetDirectoryField.getText();
            FormData formData = FormData.builder()
                    .sftpConnection(null)
                    .sftp(false)
                    .copy(true)
                    .targetDirectory(targetDirectoryFieldText)
                    .build();
            treeAttributeWrapper.getFormData().updateConnectionData(formData);
            ((Stage) saveButton.getScene().getWindow()).close();
        });
        return saveButton;
    }

}
