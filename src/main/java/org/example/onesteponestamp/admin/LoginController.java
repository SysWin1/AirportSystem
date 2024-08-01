package org.example.onesteponestamp.admin;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class LoginController {

  private TextField idField;
  private PasswordField passwordField;
  private BorderPane pane;

  private final AuthManager authManager;

  private final LoginService loginService;

  public LoginController(BorderPane pane) {
    this.pane = pane;
    loginService = new LoginService();
    authManager = AuthManager.getInstance();
  }

  public void initialize(TextField idField, PasswordField passwordField) {
    this.idField = idField;
    this.passwordField = passwordField;
  }

  public void handleLoginButtonAction() throws IOException {
    String id = idField.getText();
    String password = passwordField.getText();

    boolean success = loginService.login(id, password);
    if (success) {
      showAlert("로그인 성공!!", "환영합니다, " + id + "님 !");
      showAdminMenu();
    } else {
      showAlert("로그인 실패", "ID나 비밀번호를 틀렸습니다.");
    }
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);

    ImageView icon = new ImageView(
        new Image(
            "file:src/main/resources/org/example/onesteponestamp/images/teamlogo.png")); //content부분 아이콘 셋팅
    icon.setFitHeight(100); //아이콘 사이즈 조정
    icon.setFitWidth(100);
    alert.setGraphic(icon);

    DialogPane dialogPane = alert.getDialogPane();
    dialogPane.getStylesheets()
        .add(getClass().getResource("/org/example/onesteponestamp/css/alert.css").toExternalForm());
    dialogPane.getStyleClass().add("alert");

    alert.showAndWait();
  }

  private void showAdminMenu() throws IOException {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/org/example/onesteponestamp/javafx/AdminMain.fxml"));
    BorderPane adminMainpain = loader.load();
    pane.setTop(adminMainpain);
    pane.setCenter(null);
  }
}
