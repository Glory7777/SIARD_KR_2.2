package ch.admin.bar.siardsuite.ui.presenter.export;

import ch.admin.bar.siardsuite.framework.ServicesFacade;
import ch.admin.bar.siardsuite.framework.dialogs.DialogCloser;
import ch.admin.bar.siardsuite.framework.view.FXMLLoadHelper;
import ch.admin.bar.siardsuite.framework.view.LoadedView;
import ch.admin.bar.siardsuite.ui.View;
import ch.admin.bar.siardsuite.ui.component.LabelIcon;
import ch.admin.bar.siardsuite.ui.component.IconView;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.val;
import java.util.List;

public class ExportInProgressDialogPresenter {

    @FXML
    public Label title;
    @FXML
    public MFXProgressBar progressBar;
    @FXML
    public Label percentLabel;
    @FXML
    public VBox itemBox;

    private DialogCloser dialogCloser;
    private ServicesFacade servicesFacade;

    private static volatile ExportInProgressDialogPresenter instance;

    private int totalTables = 0;
    private int completedTables = 0;

    public void init(DialogCloser dialogCloser, ServicesFacade servicesFacade) {
        this.dialogCloser = dialogCloser;
        this.servicesFacade = servicesFacade;
        title.setText("Export in progress");
        progressBar.setProgress(0);
        if (percentLabel != null) percentLabel.setText("0%");
        instance = this;
    }

    public void updateProgress(double progress, String tableName) {
        Platform.runLater(() -> {
            if (tableName != null && !tableName.isEmpty()) {
                // 해당 항목을 OK 아이콘으로 바꾸기
                itemBox.getChildren().stream()
                        .filter(n -> n instanceof LabelIcon)
                        .map(n -> (LabelIcon) n)
                        .filter(l -> tableName.equals(l.getText()))
                        .findFirst()
                        .ifPresent(l -> l.setGraphic(new IconView(0, IconView.IconType.OK)));

                completedTables++;
                // 전체 진행률 = 완료 테이블 수 / 총 테이블 수
                double overall = totalTables == 0 ? 0 : ((double) completedTables / (double) totalTables);
                progressBar.setProgress(overall);
                if (percentLabel != null) {
                    int pct = (int) Math.round(overall * 100);
                    percentLabel.setText(pct + "%");
                }
                if (completedTables >= totalTables) {
                    dialogCloser.closeDialog();
                    servicesFacade.dialogs().open(View.EXPORT_SUCCESS);
                    return;
                }
            }
        });
    }

    public static LoadedView<ExportInProgressDialogPresenter> load(final List<String> tableNames,
                                                                   final ServicesFacade servicesFacade) {
        val loaded = FXMLLoadHelper.<ExportInProgressDialogPresenter>load("fxml/export/export-in-progress-dialog.fxml");
        loaded.getController().init(servicesFacade.dialogs(), servicesFacade);
        // 초기 목록(빈 원=LOADING) 채우기
        Platform.runLater(() -> {
            loaded.getController().itemBox.getChildren().clear();
            int i = 0;
            for (String name : tableNames) {
                // 초기에는 아이콘 없이 텍스트만 표시 (미완료 상태)
                loaded.getController().itemBox.getChildren().add(new LabelIcon(name, i++, null));
            }
            loaded.getController().totalTables = tableNames.size();
            loaded.getController().completedTables = 0;
        });
        return loaded;
    }

    public static ExportInProgressDialogPresenter getInstance() { return instance; }
}


