package ch.admin.bar.siardsuite.ui.presenter;

import ch.admin.bar.siardsuite.framework.i18n.DisplayableText;
import ch.admin.bar.siardsuite.framework.i18n.keys.I18nKey;
import ch.admin.bar.siardsuite.framework.ServicesFacade;
import ch.admin.bar.siardsuite.framework.dialogs.Dialogs;
import ch.admin.bar.siardsuite.framework.navigation.Navigator;
import ch.admin.bar.siardsuite.framework.view.FXMLLoadHelper;
import ch.admin.bar.siardsuite.framework.view.LoadedView;
import ch.admin.bar.siardsuite.ui.View;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.Optional;

/**
 * SIARD 파일 열기 실패 시 표시되는 오류 페이지
 */
@Slf4j
public class OpenFailedPresenter {

    private static final I18nKey FAILED_TITLE = I18nKey.of("openFailed.view.title");
    private static final I18nKey FAILED_MESSAGE = I18nKey.of("openFailed.view.message");

    @FXML
    public BorderPane borderPane;
    @FXML
    public Label title;
    @FXML
    public Label message;
    @FXML
    public Button homeButton;

    public void init(Optional<Void> unused, Dialogs dialogs, Navigator navigator) {
        log.info("OpenFailedPresenter.init() called");
        // 제목과 메시지 설정
        title.textProperty().bind(DisplayableText.of(FAILED_TITLE).bindable());
        message.textProperty().bind(DisplayableText.of(FAILED_MESSAGE).bindable());
        
        // 스타일 클래스 설정 (오류 아이콘)
        title.getStyleClass().setAll("x-circle-icon", "h2", "label-icon-left");
        
        // 홈 버튼 설정
        homeButton.setOnAction(event -> {
            try {
                navigator.navigate(View.START);
            } catch (Exception e) {
                log.error("Failed to navigate to START", e);
            }
        });
        log.info("OpenFailedPresenter.init() completed");
    }

    public static LoadedView<OpenFailedPresenter> load(ServicesFacade servicesFacade) {
        log.info("OpenFailedPresenter.load() called");
        val loaded = FXMLLoadHelper.<OpenFailedPresenter>load("fxml/open-failed.fxml");
        log.info("FXML loaded successfully");
        loaded.getController().init(
                Optional.empty(),
                servicesFacade.dialogs(),
                servicesFacade.navigator());
        log.info("OpenFailedPresenter.load() completed");
        return loaded;
    }
}
